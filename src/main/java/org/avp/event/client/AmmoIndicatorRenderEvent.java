package org.avp.event.client;

import org.avp.items.ItemFirearm;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.Screen;
import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class AmmoIndicatorRenderEvent
{
    private Minecraft mc = Game.minecraft();
    public ItemStack helmSlot, chestplateSlot, leggingsSlot, bootsSlot;

    @SubscribeEvent
    public void renderTick(RenderGameOverlayEvent.Pre event)
    {
        if (mc.thePlayer != null && event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            helmSlot = mc.thePlayer.inventory.armorItemInSlot(3);
            chestplateSlot = mc.thePlayer.inventory.armorItemInSlot(2);
            leggingsSlot = mc.thePlayer.inventory.armorItemInSlot(1);
            bootsSlot = mc.thePlayer.inventory.armorItemInSlot(0);

            if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemFirearm)
            {
                ItemFirearm itemFireArm = (ItemFirearm) mc.thePlayer.getHeldItem().getItem();
                String displayStatus = " " + itemFireArm.getAmmoCount() + "/" + itemFireArm.getMaxAmmoCount();
                int barWidth = 0;

                if (!mc.thePlayer.capabilities.isCreativeMode && isWearingArmor())
                {
                    barWidth = 90;
                    Draw.drawProgressBar(displayStatus, itemFireArm.getMaxAmmoCount(), itemFireArm.getAmmoCount(), (Screen.scaledDisplayResolution().getScaledWidth() / 2), Screen.scaledDisplayResolution().getScaledHeight() - 48, barWidth, 1, 0, 0xFFFF0000, false);
                    Draw.drawItemIcon(itemFireArm.getAmmoType(), (Screen.scaledDisplayResolution().getScaledWidth() / 2) + barWidth / 2 - Draw.getStringRenderWidth(displayStatus) - 2, Screen.scaledDisplayResolution().getScaledHeight() - 53, 16, 16);
                }
                else if (!mc.thePlayer.capabilities.isCreativeMode && !isWearingArmor())
                {
                    barWidth = 182;
                    Draw.drawProgressBar(displayStatus, itemFireArm.getMaxAmmoCount(), itemFireArm.getAmmoCount(), (Screen.scaledDisplayResolution().getScaledWidth() / 2) - (182 / 2), Screen.scaledDisplayResolution().getScaledHeight() - 48, barWidth, 1, 0, 0xFF00DDFF, false);
                    Draw.drawItemIcon(itemFireArm.getAmmoType(), (Screen.scaledDisplayResolution().getScaledWidth() / 2) - (barWidth / 2) + barWidth / 2 - Draw.getStringRenderWidth(displayStatus) - 2, Screen.scaledDisplayResolution().getScaledHeight() - 53, 16, 16);
                }
                else
                {
                    barWidth = 182;
                    displayStatus = "\u221e";
                    Draw.drawProgressBar("", 1, 1, (Screen.scaledDisplayResolution().getScaledWidth() / 2) - (barWidth / 2), Screen.scaledDisplayResolution().getScaledHeight() - 35, barWidth, 1, 0, 0xFF00DDFF, false);
                    Draw.drawItemIcon(itemFireArm.getAmmoType(), (Screen.scaledDisplayResolution().getScaledWidth() / 2) - (barWidth / 2) + barWidth / 2 - Draw.getStringRenderWidth(displayStatus), Screen.scaledDisplayResolution().getScaledHeight() - 40, 16, 16);
                }
            }
        }
    }

    public boolean isWearingArmor()
    {
        return helmSlot != null || chestplateSlot != null || leggingsSlot != null || bootsSlot != null;
    }
}
