package org.avp.entities.living;

import org.avp.api.parasitoidic.IMaturable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityRunnerWarrior extends EntityWarrior implements IMaturable
{
    public EntityRunnerWarrior(World world)
    {
        super(world);
        this.addStandardXenomorphAISet();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
    }
    
    @Override
    public Class<? extends Entity> getMatureState()
    {
        return EntityCrusher.class;
    }
}
