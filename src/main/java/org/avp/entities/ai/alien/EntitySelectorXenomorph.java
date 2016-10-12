package org.avp.entities.ai.alien;

import org.avp.entities.EntityLiquidPool;
import org.avp.entities.extended.ExtendedEntityLivingBase;
import org.avp.entities.mob.EntitySpeciesAlien;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorXenomorph implements IEntitySelector
{
    public static final EntitySelectorXenomorph instance = new EntitySelectorXenomorph();

    @Override
    public boolean isEntityApplicable(Entity entity)
    {
        if (entity instanceof EntitySpeciesAlien)
            return false;
        
        if (entity instanceof EntityLiquidPool)
            return false;
        
        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) entity;
            ExtendedEntityLivingBase properties = ExtendedEntityLivingBase.get(livingBase);
            
            if (properties.doesEntityContainEmbryo())
            {
                return false;
            }
        }
        
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            
            if (player.capabilities.isCreativeMode)
            {
                return false;
            }
        }
        
        return true;
    }
}
