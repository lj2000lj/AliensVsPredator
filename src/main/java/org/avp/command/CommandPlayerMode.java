package org.avp.command;

import org.avp.AliensVsPredator;
import org.avp.entities.SharedPlayer;
import org.avp.packets.client.PacketPlayerModeUpdate;
import org.avp.world.playermode.PlayerMode;

import com.arisux.mdxlib.lib.world.entity.player.Players;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandPlayerMode extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "playermode";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender)
    {
        return "Change the current player mode.";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args)
    {
        EntityPlayer player = Players.getPlayerForCommandSender(commandSender);
        SharedPlayer playerExtension = (SharedPlayer) player.getExtendedProperties(SharedPlayer.IDENTIFIER);
        PlayerMode playerMode = PlayerMode.get(Integer.valueOf(args[0]));

        playerExtension.setPlayerMode(playerMode);
        AliensVsPredator.network().sendTo(new PacketPlayerModeUpdate(playerMode.id), (EntityPlayerMP) Players.getPlayerForCommandSender(commandSender));
        commandSender.addChatMessage(new ChatComponentText("You have changed to the " + playerMode.toString().toLowerCase() + " player mode."));
    }
}
