package org.avp.entities.ai.yautja;

import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.EntitySpeciesAlien;
import org.avp.entities.mob.EntitySpeciesEngineer;
import org.avp.items.ItemDisc;
import org.avp.items.ItemFirearm;
import org.avp.items.ItemPlasmaCaster;
import org.avp.items.ItemShuriken;
import org.avp.items.ItemWristbracer;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class EntitySelectorYautja implements IEntitySelector
{
    public static final EntitySelectorYautja instance = new EntitySelectorYautja();

    @Override
    public boolean isEntityApplicable(Entity entity)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack stack = player.getCurrentEquippedItem();
            Item item = stack.getItem();
            
            if (stack != null)
            {
                return item instanceof ItemSword || item instanceof ItemFirearm || item instanceof ItemWristbracer || item instanceof ItemPlasmaCaster || item instanceof ItemBow || item instanceof ItemDisc || item instanceof ItemShuriken;
            }
        }
        
        if ((entity instanceof EntitySpeciesAlien) || (entity instanceof EntitySpeciesEngineer) || (entity instanceof EntityMarine))
        {
            return true;
        }
        
        return false;
    }
}
