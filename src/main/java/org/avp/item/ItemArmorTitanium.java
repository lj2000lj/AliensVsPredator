package org.avp.item;

import org.avp.AliensVsPredator;
import org.avp.client.render.VisionModeRenderEvent;
import org.avp.entities.SharedPlayer;
import org.lwjgl.input.Keyboard;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorTitanium extends ItemArmor
{
    public ItemArmorTitanium(ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        switch (slot)
        {
            case 0:
                return Draw.getResourcePath(AliensVsPredator.resources().TITANIUM1);
            case 1:
                return Draw.getResourcePath(AliensVsPredator.resources().TITANIUM1);
            case 2:
                return Draw.getResourcePath(AliensVsPredator.resources().TITANIUM2);
            case 3:
                return Draw.getResourcePath(AliensVsPredator.resources().TITANIUM1);
            default:
                return Draw.getResourcePath(AliensVsPredator.resources().TITANIUM1);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == AliensVsPredator.items().helmTitanium)
        {
            SharedPlayer specialPlayer = SharedPlayer.get(player);
            player.fallDistance = 0.0F;

            if (world.isRemote)
            {
                this.controlledAbility(specialPlayer);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void controlledAbility(SharedPlayer specialPlayer)
    {
        if (Game.minecraft().inGameHasFocus)
        {
            ItemStack helmSlot = Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer);

            if (helmSlot != null && helmSlot.getItem() == AliensVsPredator.items().helmTitanium && AliensVsPredator.keybinds().genericSpecial.isPressed() && Keyboard.getEventKeyState())
            {
                VisionModeRenderEvent.instance.switchMode();
            }
        }
    }
}
