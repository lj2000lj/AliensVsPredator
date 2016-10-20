package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

public class FacehuggerRenderEvent
{
    public static final FacehuggerRenderEvent instance = new FacehuggerRenderEvent();

    @SubscribeEvent
    public void renderTickOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
            {
                if (Game.minecraft().gameSettings.thirdPersonView == 0 && Game.minecraft().thePlayer.riddenByEntity != null && Game.minecraft().thePlayer.riddenByEntity instanceof EntityFacehugger)
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
