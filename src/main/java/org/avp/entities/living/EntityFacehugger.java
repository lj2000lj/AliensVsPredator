package org.avp.entities.living;

import org.avp.api.parasitoidic.IParasitoid;
import org.avp.client.Sounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFacehugger extends EntityParasitoid implements IMob, IParasitoid
{
    private int refertilizationJelly = 0;

    public EntityFacehugger(World world)
    {
        super(world);
        this.setSize(0.8F, 0.8F);
        this.experienceValue = 10;
        this.jumpMovementFactor = 0.3F;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.addTasks();
    }

    @Override
    protected void addTasks()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 0.55D, true));
        this.tasks.addTask(8, new EntityAIWander(this, 0.55D));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 0.8F));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Entity.class, 0, false, false, this.getEntitySelector()));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.50D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public boolean isOnLadder()
    {
        return this.motionY > 1.0099999997764826D;
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

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean canMoveToJelly()
    {
        return super.canMoveToJelly() && this.isFertile();
    }

    @Override
    protected void onPickupJelly(EntityItem entityItem)
    {
        super.onPickupJelly(entityItem);

        refertilizationJelly += entityItem.getEntityItem().stackSize;

        if (refertilizationJelly >= (5 + this.rand.nextInt(5)))
        {
            this.setFertility(true);
        }
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_FACEHUGGER_DEATH.getKey();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
    }
}
