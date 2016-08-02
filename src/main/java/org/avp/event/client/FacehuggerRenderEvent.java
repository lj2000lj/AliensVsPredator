package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

public class FacehuggerRenderEvent
{
    private Minecraft mc = Game.minecraft();

    @SubscribeEvent
    public void renderTickOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (mc.thePlayer != null)
        {
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
            {
                if (mc.gameSettings.thirdPersonView == 0 && mc.thePlayer.riddenByEntity != null && mc.thePlayer.riddenByEntity instanceof EntityFacehugger)
                {
                    OpenGL.pushMatrix();
                    {
                        Draw.drawOverlay(AliensVsPredator.resources().BLUR_FACEHUGGER);
                    }
                    OpenGL.popMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    public void entityRenderEvent(RenderLivingEvent.Pre event)
    {
        ;
    }

    @SubscribeEvent
    public void entityRenderEvent(RenderLivingEvent.Post event)
    {
        ;
    }
}
