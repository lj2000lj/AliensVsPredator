package org.avp.entities.living;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityYautjaBerserker extends EntitySpeciesYautja
{
    public EntityYautjaBerserker(World world)
    {
        super(world);
        this.experienceValue = 450;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(130.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5199999761581421D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(10.0D);
    }

    @Override
    public int getTotalArmorValue()
    {
        return 8;
    }
}
