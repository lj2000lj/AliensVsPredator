package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.entities.EntityAPC;
import org.avp.packets.server.PacketFireAPC;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class FireAPCEvent
{
    public static final FireAPCEvent instance = new FireAPCEvent();

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            if (Game.minecraft().thePlayer.isRiding() && Game.minecraft().thePlayer.ridingEntity instanceof EntityAPC)
            {
                if (AliensVsPredator.keybinds().KEYBIND_FIRE_APC.isPressed())
                {
                    AliensVsPredator.network().sendToServer(new PacketFireAPC());
                }
            }
        }
    }
}
