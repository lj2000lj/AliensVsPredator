package org.avp.packets.client;

import org.avp.entities.SharedPlayer;
import org.avp.world.playermode.PlayerMode;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketPlayerModeUpdate implements IMessage, IMessageHandler<PacketPlayerModeUpdate, PacketPlayerModeUpdate>
{
    public int mode;

    public PacketPlayerModeUpdate()
    {
        ;
    }

    public PacketPlayerModeUpdate(int mode)
    {
        this.mode = mode;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.mode = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.mode);
    }

    @Override
    public PacketPlayerModeUpdate onMessage(PacketPlayerModeUpdate packet, MessageContext ctx)
    {
        SharedPlayer playerExtension = (SharedPlayer) Game.minecraft().thePlayer.getExtendedProperties(SharedPlayer.IDENTIFIER);
        playerExtension.setPlayerMode(PlayerMode.get(packet.mode));

        return null;
    }
}
