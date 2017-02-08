package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.Collections;

import org.avp.api.parasitoidic.IHost;
import org.avp.api.parasitoidic.IParasitoid;
import org.avp.entities.extended.Organism;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityParasitoid extends EntitySpeciesAlien implements IMob, IParasitoid
{
    private int                   ticksOnHost;

    public static IEntitySelector parasiteSelector = new IEntitySelector()
                                                   {
                                                       @Override
                                                       public boolean isEntityApplicable(Entity potentialTarget)
                                                       {
                                                           Organism organism = (Organism) potentialTarget.getExtendedProperties(Organism.IDENTIFIER);

                                                           if (potentialTarget instanceof IHost)
                                                           {
                                                               IHost host = (IHost) potentialTarget;

                                                               if (!host.canHostParasite() || !host.canParasiteAttach())
                                                               {
                                                                   return false;
                                                               }
                                                           }

                                                           if (potentialTarget instanceof IParasitoid)
                                                           {
                                                               return false;
                                                           }

                                                           if (organism != null && organism.hasEmbryo())
                                                           {
                                                               return false;
                                                           }

                                                           if (potentialTarget instanceof EntityPlayer && ((EntityPlayer) potentialTarget).capabilities.isCreativeMode)
                                                           {
                                                               return false;
                                                           }

                                                           if (potentialTarget instanceof EntityLiving)
                                                           {
                                                               EntityLiving living = (EntityLiving) potentialTarget;

                                                               if (living.isChild())
                                                               {
                                                                   return false;
                                                               }
                                                           }

                                                           if (potentialTarget instanceof EntitySpeciesAlien)
                                                               return false;

                                                           if (potentialTarget instanceof EntitySnowman)
                                                               return false;

                                                           if (potentialTarget instanceof EntityGolem)
                                                               return false;

                                                           if (potentialTarget instanceof EntitySkeleton)
                                                               return false;

                                                           if (potentialTarget instanceof EntityZombie)
                                                               return false;

                                                           if (potentialTarget instanceof EntitySpider)
                                                               return false;

                                                           if (potentialTarget instanceof EntitySilverfish)
                                                               return false;

                                                           if (potentialTarget instanceof EntityPigZombie)
                                                               return false;

                                                           if (potentialTarget instanceof EntityGhast)
                                                               return false;

                                                           return true;
                                                       }
                                                   };

    public EntityParasitoid(World world)
    {
        super(world);
        this.addTasks();
    }

    protected void addTasks()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 0.55D, true));
        this.tasks.addTask(8, new EntityAIWander(this, 0.55D));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 0.8F));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Entity.class, 0, false, false, this.getEntitySelector()));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(30, 1);
    }

    @Override
    protected boolean isMovementBlocked()
    {
        return !isFertile();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.worldObj.getWorldTime() % 20 == 0)
        {
            if (isFertile())
            {
                ArrayList<EntityLivingBase> targetHosts = (ArrayList<EntityLivingBase>) worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1.0D, 16.0D, 1.0D), parasiteSelector);

                if (targetHosts != null && targetHosts.size() > 0)
                {
                    Collections.sort(targetHosts, new EntityAINearestAttackableTarget.Sorter(this));

                    EntityLivingBase targetHost = targetHosts.get(0);
                    this.setAttackTarget(targetHost);

                    if (!targetHosts.contains(this.getAttackTarget()))
                    {
                        this.setAttackTarget(null);
                    }
                }
            }
        }

        this.negateFallDamage();

        if (this.isAttachedToHost())
        {
            this.ticksOnHost++;

            if (ridingEntity instanceof EntityLiving)
            {
                EntityLiving living = (EntityLiving) this.ridingEntity;

                living.motionX = 0;
                living.motionZ = 0;
                this.rotationYawHead = living.rotationYawHead;
                this.rotationYaw = living.rotationYaw;
                this.prevRotationYawHead = living.prevRotationYawHead;
                this.prevRotationYaw = living.prevRotationYaw;
            }
        }

        if (this.getTicksOnHost() > this.getDetachTime())
        {
            this.detachFromHost();
        }
    }

    @Override
    public boolean canProduceJelly()
    {
        return this.worldObj.getWorldTime() % this.getJellyProductionRate() == 0 && this.isFertile() && this.jellyLevel <= 256;
    }

    @Override
    public IEntitySelector getEntitySelector()
    {
        return EntityParasitoid.parasiteSelector;
    }

    @Override
    public int getTicksOnHost()
    {
        return this.ticksOnHost;
    }

    @Override
    public int getDetachTime()
    {
        return (2 * 60) * 20;
    }

    @Override
    public void detachFromHost()
    {
        this.mountEntity(null);
        this.targetTasks.taskEntries.clear();
        this.tasks.taskEntries.clear();
    }

    @Override
    public boolean canMoveToJelly()
    {
        return false;
    }

    @Override
    public void collideWithEntity(Entity entity)
    {
        if (this.isFertile() && this.canAttach(entity))
        {
            this.attachToEntity(entity);
        }
    }

    @Override
    public boolean isAttachedToHost()
    {
        return this.isRiding();
    }

    @Override
    public boolean canAttach(Entity entity)
    {
        return parasiteSelector.isEntityApplicable(entity);
    }

    @Override
    public void attachToEntity(Entity target)
    {
        if (target.riddenByEntity == null && target instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) target;

            this.mountEntity(living);
            this.implantEmbryo(living);
        }
    }

    @Override
    public void implantEmbryo(EntityLivingBase living)
    {
        Organism organism = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);
        organism.impregnate();
        organism.syncWithClients();
        this.setFertility(false);
    }

    @Override
    public boolean isFertile()
    {
        return this.dataWatcher.getWatchableObjectInt(30) == 1;
    }

    @Override
    public void setFertility(boolean isFertile)
    {
        this.dataWatcher.updateObject(30, isFertile ? 1 : 0);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @SuppressWarnings("all")
    @Override
    protected void attackEntity(Entity entity, float attackStrength)
    {
        return;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        return false;
    }

    public boolean isPotionApplicable(PotionEffect effect)
    {
        return effect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(effect);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        boolean isFertile = nbt.getInteger("IsFertile") == 1;
        this.setFertility(isFertile);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("IsFertile", this.isFertile() ? 1 : 0);
    }
}
