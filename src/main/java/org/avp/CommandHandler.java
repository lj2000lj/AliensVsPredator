package org.avp;

import org.avp.command.CommandGenerate;
import org.avp.command.CommandPlayerMode;
import org.avp.command.CommandSettings;

import com.arisux.mdxlib.lib.game.IInitEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandHandler implements IInitEvent
{
    public static final CommandHandler instance = new CommandHandler();
    public CommandPlayerMode commandPlayerMode;
    public CommandGenerate commandGenerate;
    public CommandSettings commandSettings;

    @Override
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(this);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(this.commandPlayerMode = new CommandPlayerMode());
        event.registerServerCommand(this.commandGenerate = new CommandGenerate());
        event.registerServerCommand(this.commandSettings = new CommandSettings());
    }
}
