package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.util.VisionMode;
import org.lwjgl.input.Keyboard;

import com.arisux.amdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.amdxlib.lib.client.gui.IAction;
import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.Screen;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.world.entity.player.inventory.Inventories;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.client.event.RenderLivingEvent;

public class VisionModeRenderEvent
{
    private Minecraft mc = Game.minecraft();
    public VisionMode currentVisionMode = VisionMode.NORMAL;
    private IAction switchVisionMode = new IAction()
    {
        @Override
        public void perform(GuiCustomButton button)
        {
            currentVisionMode = currentVisionMode.id < VisionMode.values().length - 1 ? VisionMode.get(currentVisionMode.id + 1) : VisionMode.get(0);            
        }
    };

    @SubscribeEvent
    public void renderTickOverlay(Pre event)
    {
        if (mc.thePlayer != null)
        {
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR && mc.gameSettings.thirdPersonView == 0)
            {
                if (Inventories.getHelmSlotItemStack(mc.thePlayer) != null && Inventories.getHelmSlotItemStack(mc.thePlayer).getItem() == AliensVsPredator.items().helmTitanium)
                {
                    OpenGL.pushMatrix();
                    this.currentVisionMode.render();
                    Draw.drawStringAlignCenter(currentVisionMode.modeName, Screen.scaledDisplayResolution().getScaledWidth() / 2, 5, currentVisionMode.color, false);
                    OpenGL.color4i(0xFFFFFFFF);
                    OpenGL.popMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(ClientTickEvent event)
    {
        if (mc.thePlayer != null)
        {
            ItemStack helmSlot = Inventories.getHelmSlotItemStack(mc.thePlayer);

            if (helmSlot != null && helmSlot.getItem() == AliensVsPredator.items().helmTitanium && AliensVsPredator.keybinds().KEYBIND_VISION_MODE.isPressed() && mc.inGameHasFocus && Keyboard.getEventKeyState())
            {
                this.switchVisionMode.perform(null);
            }
        }
    }

    @SubscribeEvent
    public void entityRenderEvent(RenderLivingEvent.Pre event)
    {
        ItemStack helmSlot = Inventories.getHelmSlotItemStack(mc.thePlayer);

        if (mc.gameSettings.thirdPersonView == 0 && helmSlot != null && helmSlot.getItem() == AliensVsPredator.items().helmTitanium)
        {
            this.currentVisionMode.renderEntityPre(event);
        }
    }

    @SubscribeEvent
    public void entityRenderEvent(RenderLivingEvent.Post event)
    {
        ItemStack helmSlot = Inventories.getHelmSlotItemStack(mc.thePlayer);

        if (mc.gameSettings.thirdPersonView == 0 && helmSlot != null && helmSlot.getItem() == AliensVsPredator.items().helmTitanium)
        {
            this.currentVisionMode.renderEntityPost(event);
        }
    }
}
