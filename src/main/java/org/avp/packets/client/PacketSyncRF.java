package org.avp.packets.client;

import org.avp.tile.TileEntityRedstoneFluxGenerator;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSyncRF implements IMessage, IMessageHandler<PacketSyncRF, PacketSyncRF>
{
    public int rf;
    public int x;
    public int y;
    public int z;

    public PacketSyncRF()
    {
        ;
    }

    public PacketSyncRF(int rf, int x, int y, int z)
    {
        this.rf = rf;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.rf = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(rf);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public PacketSyncRF onMessage(PacketSyncRF packet, MessageContext ctx)
    {
        World world = Game.minecraft().thePlayer.worldObj;

        if (world != null)
        {
            TileEntity tile = world.getTileEntity(packet.x, packet.y, packet.z);

            if (tile != null && tile instanceof TileEntityRedstoneFluxGenerator)
            {
                TileEntityRedstoneFluxGenerator receiver = (TileEntityRedstoneFluxGenerator) tile;
                receiver.setRfEnergy(packet.rf);
            }
        }

        return null;
    }
}
