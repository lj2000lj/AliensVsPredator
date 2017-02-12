package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.AliensVsPredator;
import org.avp.DamageSources;
import org.avp.api.parasitoidic.IMaturable;
import org.avp.api.parasitoidic.IRoyalOrganism;
import org.avp.entities.EntityAcidPool;
import org.avp.event.HiveHandler;
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

public abstract class EntitySpeciesAlien extends EntityMob implements IMob, IRoyalOrganism
{
    protected final int     JELLY_LEVEL_DW_ID = 26;
    protected XenomorphHive hive;
    private UUID            signature;
    protected boolean         jellyLimitOverride;

    public EntitySpeciesAlien(World world)
    {
        super(world);
        this.jumpMovementFactor = 0.2F;
        this.jellyLimitOverride = false;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(JELLY_LEVEL_DW_ID, this.getJellyLevelStart());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        nbt.setString("HiveSignature", signature != null ? this.signature.toString() : "");
        nbt.setInteger("JellyLevel", this.getJellyLevel());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        this.signature = Worlds.uuidFromNBT(nbt, "HiveSignature");
        this.setJellyLevel(nbt.getInteger("JellyLevel"));
    }

    @Override
    public void onKillEntity(EntityLivingBase entity)
    {
        super.onKillEntity(entity);
        this.setJellyLevel(this.getJellyLevel() + 250);
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
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

            int adjustedLevel = this.getJellyLevel() / 4;

            if (this.jellyLimitOverride)
            {
                adjustedLevel = adjustedLevel < 64 ? adjustedLevel : 64;
            }

            ItemDrop dynamicJelly = new ItemDrop(100, new ItemStack(AliensVsPredator.items().itemRoyalJelly, adjustedLevel));
            dynamicJelly.tryDrop(this);
        }
    }

    public boolean isReadyToMature(IRoyalOrganism jellyProducer)
    {
        IMaturable maturable = (IMaturable) this;
        IRoyalOrganism ro = (IRoyalOrganism) this;
        return maturable.getMatureState() != null && maturable.getMaturityLevel() > 0 && ro.getJellyLevel() >= maturable.getMaturityLevel();
    }

    public void mature()
    {
        IMaturable maturable = (IMaturable) this;
        EntitySpeciesAlien alien = (EntitySpeciesAlien) Entities.constructEntity(this.worldObj, maturable.getMatureState());
        NBTTagCompound tag = new NBTTagCompound();

        alien.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
        this.worldObj.spawnEntityInWorld(alien);
        this.writeEntityToNBT(tag);
        alien.readEntityFromNBT(tag);
        alien.setJellyLevel(this.getJellyLevel() - maturable.getMaturityLevel());
        // TODO: Create a shell of the original entity.
        this.setDead();
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
        this.setJellyLevel(this.getJellyLevel() + (entityItem.getEntityItem().stackSize * 100));
        entityItem.setDead();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.canProduceJelly())
        {
            this.produceJelly();
        }

        if (this instanceof IMaturable)
        {
            IMaturable maturable = (IMaturable) this;

            if (!this.worldObj.isRemote)
            {
                if (this.worldObj.getWorldTime() % 20 == 0)
                {
                    if (maturable.isReadyToMature(this))
                    {
                        maturable.mature();
                    }
                }
            }
        }

        this.identifyHive();
        this.findRoyalJelly();
    }

    @Override
    public boolean canProduceJelly()
    {
        return this.worldObj.getWorldTime() % this.getJellyProductionRate() == 0;
    }

    @Override
    public int getJellyProductionRate()
    {
        return 8 * 20;
    }

    @Override
    public void produceJelly()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                this.setJellyLevel(this.getJellyLevel() + 20);
            }
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

    @Override
    public int getJellyLevel()
    {
        return this.dataWatcher.getWatchableObjectInt(JELLY_LEVEL_DW_ID);
    }

    @Override
    public void setJellyLevel(int level)
    {
        this.dataWatcher.updateObject(JELLY_LEVEL_DW_ID, level);
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
        return type == EnumCreatureType.monster ? true : false;
    }

    protected void negateFallDamage()
    {
        this.fallDistance = 0F;
    }

    protected int getJellyLevelStart()
    {
        return 0;
    }
}
