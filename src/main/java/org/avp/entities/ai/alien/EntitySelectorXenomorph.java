package org.avp.entities.ai.alien;

import org.avp.entities.EntityLiquidPool;
import org.avp.entities.Organism;
import org.avp.entities.living.EntitySpeciesAlien;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorXenomorph implements IEntitySelector
{
    public static final EntitySelectorXenomorph instance = new EntitySelectorXenomorph();

    @Override
    public boolean isEntityApplicable(Entity potentialTarget)
    {
        if (potentialTarget instanceof EntitySpeciesAlien)
            return false;
        
        if (potentialTarget instanceof EntityLiquidPool)
            return false;
        
        if (potentialTarget instanceof EntityLivingBase)
        {
            EntityLivingBase livingBase = (EntityLivingBase) potentialTarget;
            Organism livingProperties = Organism.get(livingBase);
            
            if (livingProperties.hasEmbryo())
            {
                return false;
            }
        }
        
        if (potentialTarget instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) potentialTarget;
            
            if (player.capabilities.isCreativeMode)
            {
                return false;
            }
        }
        
        return true;
    }
}
