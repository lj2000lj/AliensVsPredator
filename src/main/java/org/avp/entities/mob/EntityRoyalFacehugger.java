package org.avp.entities.mob;

import org.avp.entities.extended.Organism;
import org.avp.util.Embryo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityRoyalFacehugger extends EntityFacehugger
{
    public EntityRoyalFacehugger(World world)
    {
        super(world);
        this.setSize(1F, 1F);
        this.experienceValue = 300;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(175.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
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
    public int getTotalArmorValue()
    {
        return 4;
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
    protected void attackEntity(Entity entity, float damage)
    {
        ;
    }
    
    @Override
    public void implantEmbryo(EntityLivingBase living)
    {
        Organism extendedLiving = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);
        extendedLiving.setEmbryo(new Embryo(EntityQueen.class, EntityLiving.class));
        extendedLiving.syncWithClients();
        this.setFertility(false);
    }
}
