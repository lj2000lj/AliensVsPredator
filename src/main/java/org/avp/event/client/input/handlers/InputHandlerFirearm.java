package org.avp.event.client.input.handlers;

import org.avp.AliensVsPredator;
import org.avp.event.client.input.IInputHandler;
import org.avp.item.ItemFirearm;
import org.avp.packets.server.PacketReloadFirearm;

import com.arisux.mdxlib.lib.game.Game;

public class InputHandlerFirearm implements IInputHandler
{
    public static final InputHandlerFirearm instance   = new InputHandlerFirearm();
    private int                             lastReload = 0;

    @Override
    public void handleInput()
    {
        if (Game.minecraft().thePlayer != null)
        {
            this.lastReload++;

            if (Game.minecraft().inGameHasFocus && Game.minecraft().thePlayer.inventory.getCurrentItem() != null && Game.minecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFirearm)
            {
                ItemFirearm fireArm = (ItemFirearm) Game.minecraft().thePlayer.inventory.getCurrentItem().getItem();

                if (AliensVsPredator.keybinds().specialSecondary.isPressed() && this.lastReload > fireArm.getReloadRate())
                {
                    this.lastReload = 0;
                    AliensVsPredator.network().sendToServer(new PacketReloadFirearm());
                }
            }
        }
    }
}
