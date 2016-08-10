package org.avp.entities.mob;

import org.avp.Sounds;
import org.avp.entities.EntityAcidPool;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPredalien extends EntityXenomorph implements IMob
{
    public EntityPredalien(World world)
    {
        super(world);
        this.jumpMovementFactor = 0.025F;
        this.experienceValue = 225;
        this.setSize(1.0F, 2.5F);
        this.ignoreFrustumCheck = true;
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityLiving.class, 16F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1F);
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
    protected String getHurtSound()
    {
        return Sounds.SOUND_PRAETORIAN_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_PRAETORIAN_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_PRAETORIAN_DEATH.getKey();
    }

    @Override
    protected void dropRareDrop(int par1)
    {
        ;
    }

    @Override
    public void onDeath(DamageSource damageSource)
    {
        super.onDeath(damageSource);

        if (this.worldObj.isRemote)
        {
            EntityAcidPool entity = new EntityAcidPool(this.worldObj);
            entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            this.worldObj.spawnEntityInWorld(entity);
        }
    }
}
