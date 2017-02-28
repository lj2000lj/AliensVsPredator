package org.avp.entities.living;

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

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
    }
}
