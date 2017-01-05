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

import com.arisux.mdxlib.lib.world.Worlds;
import com.arisux.mdxlib.lib.world.entity.Entities;
import com.arisux.mdxlib.lib.world.entity.ItemDrop;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
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
    public void onKillEntity(EntityLivingBase entity)
    {
        super.onKillEntity(entity);
        this.setJellyLevel(this.getJellyLevel() + 20);
    }

    @Override
    protected boolean canDespawn()
    {
        return this.getJellyLevel() < 20 && !this.hasCustomNameTag();
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

            ItemDrop dynamicJelly = new ItemDrop(100, new ItemStack(AliensVsPredator.items().itemRoyalJelly, this.jellyLevel / 4));
            dynamicJelly.tryDrop(this);
        }
    }

    protected void tickEvolution()
    {
        if (this.worldObj.getWorldTime() % 10 == 0)
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
                alien.setJellyLevel(this.getJellyLevel() - evolution.getLevel());
                this.setDead();
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void findRoyalJelly()
    {
        if (!this.worldObj.isRemote && this.worldObj.getWorldTime() % 40 == 0)
        {
            ArrayList<EntityItem> entityItemList = (ArrayList<EntityItem>) worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(8, 8, 8));

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

                        if (this.getDistanceToEntity(entityItem) <= 1)
                        {
                            this.onPickupJelly(entityItem);
                        }
                        break;
                    }
                }
            }
            
            entityItemList.clear();
            entityItemList = null;
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
        return !(this instanceof EntityOvamorph);
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

        if (this.worldObj.getWorldTime() % 80 == 0)
        {
            if (!this.worldObj.isRemote)
            {
                AliensVsPredator.network().sendToAll(new PacketJellyLevelUpdate(jellyLevel, Integer.valueOf(this.getEntityId())));
            }
        }

        this.generateJelly();
        this.tickEvolution();
        this.identifyHive();
        this.findRoyalJelly();
    }

    protected void generateJelly()
    {
        if (this.worldObj.getWorldTime() % (20 * 8) == 0)
        {
            this.jellyLevel++;
        }
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
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return type == EnumCreatureType.monster ? true : false;
    }
}
