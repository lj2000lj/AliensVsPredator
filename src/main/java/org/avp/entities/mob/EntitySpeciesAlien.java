package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.AliensVsPredator;
import org.avp.DamageSources;
import org.avp.entities.EntityAcidPool;
import org.avp.event.HiveHandler;
import org.avp.packets.client.PacketJellyLevelUpdate;
import org.avp.util.EvolutionType;
import org.avp.util.XenomorphHive;

import com.arisux.amdxlib.lib.world.CoordData;
import com.arisux.amdxlib.lib.world.Worlds;
import com.arisux.amdxlib.lib.world.entity.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntitySpeciesAlien extends EntityMob implements IMob
{
    protected XenomorphHive hive;
    private UUID            signature;
    protected int           jellyLevel;

    public EntitySpeciesAlien(World world)
    {
        super(world);
        this.jumpMovementFactor = 0.2F;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        nbt.setString("HiveSignature", signature != null ? this.signature.toString() : "");
        nbt.setInteger("jellyLevel", this.jellyLevel);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        this.signature = Worlds.uuidFromNBT(nbt, "HiveSignature");
        this.jellyLevel = nbt.getInteger("jellyLevel");
    }

    @Override
    public void onKillEntity(EntityLivingBase par1EntityLivingBase)
    {
        super.onKillEntity(par1EntityLivingBase);
        this.setJellyLevel(this.getJellyLevel() + 1);
    }

    @Override
    protected boolean canDespawn()
    {
        return this.getJellyLevel() < 1;
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (!this.worldObj.isRemote)
        {
            if (damagesource != DamageSource.onFire && damagesource != DamageSource.inFire && damagesource != DamageSources.flamethrower)
            {
                EntityAcidPool entity = new EntityAcidPool(this.worldObj);
                entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                this.worldObj.spawnEntityInWorld(entity);
            }

            if (this instanceof EntityQueen)
            {
                int randomJelly = this.rand.nextInt(196);
                this.dropItem(AliensVsPredator.items().itemRoyalJelly, 32 + (randomJelly / 2 + randomJelly));
            }

            if (this.rand.nextInt(4) == 0)
            {
                this.dropItem(AliensVsPredator.items().itemRoyalJelly, 1 + this.rand.nextInt(5));
            }
        }
    }

    @Override
    protected void dropRareDrop(int rate)
    {
        this.dropItem(AliensVsPredator.items().itemRoyalJelly, 4);
    }

    protected void tickEvolution()
    {
        if (this.worldObj.getWorldTime() % 40 == 0)
        {
            EvolutionType evolution = EvolutionType.getEvolutionMappingFor(this.getClass());

            if (!this.worldObj.isRemote && evolution != null && evolution.getEvolution() != null && evolution.getLevel() != 0 && this.jellyLevel >= evolution.getLevel())
            {
                EntitySpeciesAlien alien = (EntitySpeciesAlien) Entities.constructEntity(this.worldObj, evolution.getEvolution());
                alien.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(alien);
                NBTTagCompound tag = new NBTTagCompound();
                this.writeEntityToNBT(tag);
                alien.readEntityFromNBT(tag);
                this.setDead();
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void findRoyalJelly()
    {
        if (!this.worldObj.isRemote && !(this instanceof EntityOvamorph) && this.worldObj.getWorldTime() % 20 == 0)
        {
            ArrayList<EntityItem> entityItemList = (ArrayList<EntityItem>) Entities.getEntitiesInCoordsRange(worldObj, EntityItem.class, new CoordData(this), 8);

            for (EntityItem entityItem : entityItemList)
            {
                if (entityItem.delayBeforeCanPickup <= 0)
                {
                    ItemStack stack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);

                    if (stack.getItem() == AliensVsPredator.items().itemRoyalJelly)
                    {
                        if (this.canMoveToJelly())
                        {
                            this.getNavigator().setPath(this.getNavigator().getPathToEntityLiving(entityItem), 1);
                        }

                        if (this.getDistanceToEntity(entityItem) < 1)
                        {
                            this.onPickupJelly(entityItem);
                        }
                        break;
                    }
                }
            }
        }
    }

    public UUID getHiveSignature()
    {
        return this.signature;
    }

    public void setHiveSignature(UUID signature)
    {
        this.signature = signature;
    }

    public boolean canMoveToJelly()
    {
        return true;
    }

    protected void onPickupJelly(EntityItem entityItem)
    {
        this.setJellyLevel(this.getJellyLevel() + entityItem.getEntityItem().stackSize);
        entityItem.setDead();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.tickEvolution();
        this.identifyHive();
        this.findRoyalJelly();
    }

    public XenomorphHive getHive()
    {
        return hive;
    }

    public void identifyHive()
    {
        if (!(this instanceof EntityQueen))
        {
            if (this.signature != null)
            {
                this.hive = HiveHandler.instance.getHiveForUUID(this.signature);
            }

            if (this.hive != null)
            {
                this.hive.addMemberToHive(this);
            }
        }
    }

    public int getJellyLevel()
    {
        return this.jellyLevel;
    }

    public void setJellyLevel(int jellyLevel)
    {
        this.jellyLevel = jellyLevel;

        if (!this.worldObj.isRemote)
        {
            AliensVsPredator.network().sendToAll(new PacketJellyLevelUpdate(jellyLevel, Integer.valueOf(this.getEntityId())));
        }
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return type == EnumCreatureType.monster ? true : false;
    }
}
