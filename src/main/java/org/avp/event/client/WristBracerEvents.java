package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketOpenWristbracerContainer;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;

public class WristBracerEvents
{
    private Minecraft mc = Game.minecraft();

    @SubscribeEvent
    public void tick(ClientTickEvent event)
    {
        if (mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null)
        {
            if (mc.thePlayer.getCurrentEquippedItem().getItem() == AliensVsPredator.items().itemWristBlade && AliensVsPredator.keybinds().KEYBIND_ITEM_ACTION.isPressed())
            {
                AliensVsPredator.network().sendToServer(new PacketOpenWristbracerContainer());
            }
        }
    }
}
