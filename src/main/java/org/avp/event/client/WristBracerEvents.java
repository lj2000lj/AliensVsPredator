package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketOpenContainer;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class WristBracerEvents
{
    public static final WristBracerEvents instance = new WristBracerEvents();

    @SubscribeEvent
    public void tick(ClientTickEvent event)
    {
        if (Game.minecraft().thePlayer != null && Game.minecraft().thePlayer.getCurrentEquippedItem() != null)
        {
            if (Game.minecraft().thePlayer.getCurrentEquippedItem().getItem() == AliensVsPredator.items().itemWristBlade && AliensVsPredator.keybinds().KEYBIND_ITEM_ACTION.isPressed())
            {
                AliensVsPredator.network().sendToServer(new PacketOpenContainer(AliensVsPredator.properties().GUI_WRISTBRACER));
            }
        }
    }
}
