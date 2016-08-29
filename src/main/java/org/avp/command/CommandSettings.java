package org.avp.command;

import org.avp.gui.GuiModSettings;

import com.arisux.amdxlib.lib.world.entity.player.Players;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandSettings extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "avpsettings";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return "Opens an interface containing changeable settings for the AliensVsPredator mod.";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args)
    {
        EntityPlayer player = Players.getPlayerForCommandSender(commandSender);
        FMLCommonHandler.instance().showGuiScreen(new GuiModSettings(null));
    }
}
