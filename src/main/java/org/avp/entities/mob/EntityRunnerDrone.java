package org.avp.entities.mob;

import org.avp.api.parasitoidic.IMaturable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityRunnerDrone extends EntityDrone implements IMaturable
{
    public EntityRunnerDrone(World world)
    {
        super(world);
        this.addStandardXenomorphAISet();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.63D);
    }
    
    @Override
    public Class<? extends Entity> getMatureState()
    {
        return EntityRunnerWarrior.class;
    }
}
