package org.avp.client.input;

import java.util.ArrayList;

import org.avp.client.input.handlers.InputHandlerFirearm;
import org.avp.client.input.handlers.InputHandlerPlasmaCannon;
import org.avp.client.input.handlers.InputHandlerPulseRifle;
import org.avp.client.input.handlers.InputHandlerWristbracer;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class InputHandler
{
    public static final InputHandler instance      = new InputHandler();
    private ArrayList<IInputHandler> inputHandlers = null;

    public InputHandler()
    {
        this.inputHandlers = new ArrayList<IInputHandler>();
        this.inputHandlers.add(InputHandlerPlasmaCannon.instance);
        this.inputHandlers.add(InputHandlerFirearm.instance);
        this.inputHandlers.add(InputHandlerPulseRifle.instance);
        this.inputHandlers.add(InputHandlerWristbracer.instance);
    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            for (IInputHandler ih : this.inputHandlers)
            {
                ih.handleInput();
            }
        }
    }
}
