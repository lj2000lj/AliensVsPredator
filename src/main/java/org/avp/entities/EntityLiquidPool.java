package org.avp.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityLiquidPool extends EntityCreature
{
    protected int lifetime;

    public EntityLiquidPool(World world)
    {
        super(world);
        this.lifetime = 600;
        this.isImmuneToFire = false;
        this.ignoreFrustumCheck = true;
        this.setSize(0.08F, 0.08F);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean isInRangeToRenderDist(double range)
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        
        this.motionX = 0;
        this.motionZ = 0;

        if (!this.worldObj.isRemote)
        {
            if (this.ticksExisted > this.lifetime)
            {
                this.setDead();
            }
        }
    }

    public int getLifetime()
    {
        return lifetime;
    }
    
    @Override
    protected String getHurtSound()
    {
        return null;
    }
    
    @Override
    protected String getDeathSound()
    {
        return null;
    }
}
