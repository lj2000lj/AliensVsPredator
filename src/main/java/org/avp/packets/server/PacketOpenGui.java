package org.avp.packets.server;

import org.avp.AliensVsPredator;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketOpenGui implements IMessage, IMessageHandler<PacketOpenGui, PacketOpenGui>
{
    public int guiIdentifier;

    public PacketOpenGui()
    {
        ;
    }

    public PacketOpenGui(int guiIdentifier)
    {
        this.guiIdentifier = guiIdentifier;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.guiIdentifier = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.guiIdentifier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public PacketOpenGui onMessage(PacketOpenGui packet, MessageContext ctx)
    {
        EntityPlayer player = Game.minecraft().thePlayer;
        FMLNetworkHandler.openGui(player, AliensVsPredator.instance(), packet.guiIdentifier, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return null;
    }
}
