package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.Collections;

import org.avp.entities.extended.ExtendedEntityLivingBase;
import org.avp.util.Embryo;
import org.avp.util.EmbryoType;
import org.avp.util.IParasiticHost;
import org.avp.util.IParasitoid;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
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
                                                           ExtendedEntityLivingBase entityExt = (ExtendedEntityLivingBase) potentialTarget.getExtendedProperties(ExtendedEntityLivingBase.IDENTIFIER);

                                                           if (potentialTarget instanceof IParasiticHost)
                                                           {
                                                               IParasiticHost host = (IParasiticHost) potentialTarget;

                                                               if (!host.canHostParasite() || !host.canParasiteAttach())
                                                               {
                                                                   return false;
                                                               }
                                                           }

                                                           if (potentialTarget instanceof IParasitoid)
                                                           {
                                                               return false;
                                                           }

                                                           if (entityExt.doesEntityContainEmbryo())
                                                           {
                                                               return false;
                                                           }

                                                           if (potentialTarget instanceof EntityPlayer && !((EntityPlayer) potentialTarget).capabilities.isCreativeMode)
                                                           {
                                                               return false;
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
    public void onUpdate()
    {
        super.onUpdate();

        if (this.isFertile())
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

        this.negateFallDamage();

        if (this.isAttachedToHost())
        {
            this.ticksOnHost++;
        }

        if (this.getTicksOnHost() > this.getDetachTime())
        {
            this.detachFromHost();
        }
    }

    @Override
    protected void generateJelly()
    {
        if (this.worldObj.getWorldTime() % (20 * 8) == 0 && this.isFertile() && this.jellyLevel <= 256)
        {
            this.jellyLevel++;
        }
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
        ExtendedEntityLivingBase extendedLiving = (ExtendedEntityLivingBase) living.getExtendedProperties(ExtendedEntityLivingBase.IDENTIFIER);
        extendedLiving.setEmbryo(new Embryo(EmbryoType.getMappingFromHost(extendedLiving.getEntityLivingBase().getClass())){
            
        });
        extendedLiving.syncClients();
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

        if (isFertile)
        {
            this.addTasks();
        }
        else
        {
            this.tasks.taskEntries.clear();
            this.targetTasks.taskEntries.clear();
        }
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
        this.setFertility(nbt.getInteger("IsFertile") == 1);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("IsFertile", this.isFertile() ? 1 : 0);
    }
}
