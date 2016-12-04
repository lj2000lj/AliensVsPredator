package org.avp.event.client;

import org.avp.AliensVsPredator;
import org.avp.items.ItemFirearm;
import org.avp.packets.server.PacketReloadFirearm;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class CommonFirearmEvents
{
    public static final CommonFirearmEvents instance = new CommonFirearmEvents();
    private int lastReload = 0;

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            this.lastReload++;

            if (Game.minecraft().inGameHasFocus && Game.minecraft().thePlayer.inventory.getCurrentItem() != null && Game.minecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFirearm)
            {
                ItemFirearm fireArm = (ItemFirearm) Game.minecraft().thePlayer.inventory.getCurrentItem().getItem();

                if (AliensVsPredator.keybinds().KEYBIND_FIREARM_RELOAD.isPressed() && this.lastReload > fireArm.getReloadRate())
                {
                    this.lastReload = 0;
                    AliensVsPredator.network().sendToServer(new PacketReloadFirearm());
                }
            }
        }
    }
}
