package org.avp.entities.mob;

import org.avp.Sounds;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class EntityBoiler extends EntityXenomorph
{
    public EntityBoiler(World world)
    {
        super(world);
        this.experienceValue = 275;
        this.setSize(1.0F, 3.0F);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.addStandardXenomorphAISet();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5500000238418579D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_SPITTER_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_SPITTER_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_SPITTER_DEATH.getKey();
    }

    @Override
    public int getTotalArmorValue()
    {
        return 2;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }
    
    @Override
    public void identifyHive()
    {
        ;
    }
}
