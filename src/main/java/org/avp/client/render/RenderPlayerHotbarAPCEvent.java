package org.avp.client.render;

import org.avp.entities.EntityAPC;

import com.arisux.mdxlib.lib.game.Game;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayerHotbarAPCEvent
{
    public static final RenderPlayerHotbarAPCEvent instance = new RenderPlayerHotbarAPCEvent();

    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (Game.minecraft().thePlayer.isRiding() && Game.minecraft().thePlayer.getRidingEntity()instanceof EntityAPC)
        {
            if (event.getType() == ElementType.HOTBAR)
            {
                event.setCanceled(true);
            }
        }
    }
}
