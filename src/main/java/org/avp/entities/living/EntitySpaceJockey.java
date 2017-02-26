package org.avp.entities.living;

import org.avp.DamageSources;
import org.avp.EntityItemDrops;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySpaceJockey extends EntitySpeciesEngineer
{
    public EntitySpaceJockey(World world)
    {
        super(world);
        this.experienceValue = 250;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(160.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5199999761581421D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1F);
    }
    
    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (damagesource == DamageSources.wristbracer)
        {
            EntityItemDrops.SKULL_SPACEJOCKEY.tryDrop(this, 25);
        }
        else
        {
            EntityItemDrops.SKULL_SPACEJOCKEY.tryDrop(this);
        }
    }

    @Override
    public int getTotalArmorValue()
    {
        return 7;
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }
    
    @Override
    public boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean isWearingMask()
    {
        return true;
    }
}
