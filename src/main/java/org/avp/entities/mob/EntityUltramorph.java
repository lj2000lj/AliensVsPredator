package org.avp.entities.mob;

import org.avp.Sounds;
import org.avp.entities.ai.alien.EntitySelectorXenomorph;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityUltramorph extends EntityXenomorph
{
    public EntityUltramorph(World world)
    {
        super(world);

        this.jumpMovementFactor = 0.02F;
        this.experienceValue = 100;
        this.setSize(1F, 3F);
        this.ableToClimb = false;
        this.isDependant = false;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityLivingBase.class, 16F));
        this.tasks.addTask(1, new EntityAIWander(this, 0.8D));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(1, new EntityAILeapAtTarget(this, 0.6F));
        this.targetTasks.addTask(1, new EntityAIAttackOnCollide(this, 0.8D, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, Entity.class, 1000, true, false, EntitySelectorXenomorph.instance));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(230.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4700000238418579D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.5D);
    }

    @Override
    protected void dropRareDrop(int chance)
    {
        super.dropRareDrop(chance);
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_ALIEN_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_ALIEN_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_ALIEN_DEATH.getKey();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
    }
}
