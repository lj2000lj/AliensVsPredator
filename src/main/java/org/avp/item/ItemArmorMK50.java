package org.avp.item;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.render.Draw;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorMK50 extends ItemAntiVacuumArmor
{
    public ItemArmorMK50(ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
    {
        switch (slot)
        {
            case 0:
                return Draw.getResourcePath(AliensVsPredator.resources().MK501);
            case 1:
                return Draw.getResourcePath(AliensVsPredator.resources().MK501);
            case 2:
                return Draw.getResourcePath(AliensVsPredator.resources().MK502);
            case 3:
                return Draw.getResourcePath(AliensVsPredator.resources().MK501);
            default:
                return Draw.getResourcePath(AliensVsPredator.resources().MK502);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        ;
    }
}
