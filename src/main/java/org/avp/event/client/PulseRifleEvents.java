package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketLaunchGrenade;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class PulseRifleEvents
{
    public static final PulseRifleEvents instance = new PulseRifleEvents();

    @SubscribeEvent
    public void onItemUse(ClientTickEvent event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            if (Game.minecraft().thePlayer.getCurrentEquippedItem() != null && Game.minecraft().thePlayer.getCurrentEquippedItem().getItem() == AliensVsPredator.items().itemM41A)
            {
                if (AliensVsPredator.keybinds().KEYBIND_ITEM_ACTION.isPressed())
                {
                    // ((ItemFirearm) mc.thePlayer.getCurrentEquippedItem().getItem()).cancelRightClick = true;
                    Game.setRightClickDelayTimer(20);
                    AliensVsPredator.network().sendToServer(new PacketLaunchGrenade());
                }
            }
        }
    }
}
