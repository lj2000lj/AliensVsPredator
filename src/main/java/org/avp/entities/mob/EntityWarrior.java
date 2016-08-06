package org.avp.entities.mob;

import org.avp.Sounds;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityWarrior extends EntityXenomorph implements IMob
{
    public EntityWarrior(World world)
    {
        super(world);
        this.experienceValue = 175;
        this.setSize(1.0F, 2.5F);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
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
        return Sounds.SOUND_WARRIOR_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_WARRIOR_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_WARRIOR_DEATH.getKey();
    }
}
