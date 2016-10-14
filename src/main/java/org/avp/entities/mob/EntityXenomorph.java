package org.avp.entities.mob;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.entities.ai.alien.EntitySelectorXenomorph;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityXenomorph extends EntitySpeciesAlien implements IMob
{
    protected boolean canClimb;
    protected boolean canBelongToHive;
    public int        hitRange;

    public EntityXenomorph(World world)
    {
        super(world);
        this.hitRange = 1;
        this.jumpMovementFactor = 0.045F;
        this.canClimb = false;
        this.canBelongToHive = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        // this.tasks.addTask(1, new EntityAIClimb(this, 0.03F));
        this.tasks.addTask(8, new EntityAIWander(this, 0.8D));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 0.6F));
        this.targetTasks.addTask(3, new EntityAIAttackOnCollide(this, 0.8D, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Entity.class, 0, true, false, EntitySelectorXenomorph.instance));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1F);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4700000238418579D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
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

    public boolean canClimb()
    {
        return this.canClimb;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.hive != null && !this.worldObj.isRemote)
        {
            if (this.hive.getQueen() != null && !this.hive.getQueen().isDead && !(this instanceof EntityQueen))
            {
                if (this.hive.getQueen().getOvipositorSize() < EntityQueen.OVIPOSITOR_THRESHOLD_SIZE || this.hive.getQueen().reproducing)
                {
                    if (this.hive.getQueen().getJellyLevel() < EntityQueen.OVIPOSITOR_JELLYLEVEL_THRESHOLD * 2)
                    {
                        this.hive.getQueen().jellyLevel++;
                        this.jellyLevel--;
                    }
                }
            }
            else
            {
                this.hive = null;
            }
        }

        this.fallDistance = 0F;

        if (this.isCollidedHorizontally)
        {
            // this.motionY += 0.25F;
        }

        if (this.getAttackTarget() != null && this.getAttackTarget().isDead)
        {
            this.setAttackTarget(null);
        }

        if (this.getAttackTarget() != null)
        {
            if (this.getDistanceToEntity(this.getAttackTarget()) <= hitRange)
            {
                this.attackEntityAsMob(this.getAttackTarget());
            }
        }
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (!this.worldObj.isRemote)
        {
            if (new Random().nextInt(75) == 0) // 1 in 75 chance of dropping
                this.entityDropItem(new ItemStack(AliensVsPredator.items().helmXeno), 0.0F);
            if (new Random().nextInt(66) == 0)
                this.entityDropItem(new ItemStack(AliensVsPredator.items().plateXeno), 0.0F);
            if (new Random().nextInt(55) == 0)
                this.entityDropItem(new ItemStack(AliensVsPredator.items().legsXeno), 0.0F);
            if (new Random().nextInt(50) == 0)
                this.entityDropItem(new ItemStack(AliensVsPredator.items().bootsXeno), 0.0F);
        }
    }

    @Override
    protected void attackEntity(Entity entity, float damage)
    {
        super.attackEntity(entity, damage);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        return super.attackEntityAsMob(entity);
    }

    public boolean canBelongToHive()
    {
        return this.canBelongToHive;
    }
}
