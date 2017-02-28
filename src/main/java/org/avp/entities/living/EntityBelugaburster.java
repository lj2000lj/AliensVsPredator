package org.avp.entities.living;

import org.avp.api.parasitoidic.INascentic;
import org.avp.client.Sounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityBelugaburster extends EntityChestburster implements IMob, INascentic
{
    private Class<? extends Entity> matureState;

    public EntityBelugaburster(World world)
    {
        super(world);
        this.matureState = EntityBelugamorph.class;
        this.setSize(1.0F, 0.4F);
        this.experienceValue = 16;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityYautja.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEngineer.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityMarine.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 0.800000011920929D, true));
        this.tasks.addTask(8, new EntityAIWander(this, 0.800000011920929D));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 0.8F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6499999761581421D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_CHESTBURSTER_DEATH.getKey();
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_CHESTBURSTER_HURT.getKey();
    }

    @Override
    public Class<? extends Entity> getMatureState()
    {
        return this.matureState;
    }

    @Override
    public int getMaturityTime()
    {
        return (8 * 60) * 20;
    }
    
    @Override
    protected void findRoyalJelly()
    {
        ;
    }
    
    @Override
    public void identifyHive()
    {
        ;
    }
}
