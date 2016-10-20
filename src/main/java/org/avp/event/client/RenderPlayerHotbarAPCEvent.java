package org.avp.event.client;

import org.avp.entities.EntityAPC;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class RenderPlayerHotbarAPCEvent
{
    public static final RenderPlayerHotbarAPCEvent instance = new RenderPlayerHotbarAPCEvent();

    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (Game.minecraft().thePlayer.isRiding() && Game.minecraft().thePlayer.ridingEntity instanceof EntityAPC)
        {
            if (event.type == ElementType.HOTBAR)
            {
                event.setCanceled(true);
            }
        }
    }
}
