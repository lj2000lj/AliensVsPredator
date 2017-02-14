package org.avp.entities.mob;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityBatXeno extends EntityXenomorph
{
    public EntityBatXeno(World world)
    {
        super(world);
        this.addStandardXenomorphAISet();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
    }
}
