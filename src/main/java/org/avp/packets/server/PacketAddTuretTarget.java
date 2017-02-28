package org.avp.packets.server;

import org.avp.tile.TileEntityTurret;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketAddTuretTarget implements IMessage, IMessageHandler<PacketAddTuretTarget, PacketAddTuretTarget>
{
    public int x, y, z;
    public String entityIdentifier;

    public PacketAddTuretTarget()
    {
        ;
    }

    public PacketAddTuretTarget(int x, int y, int z, String globalID)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityIdentifier = globalID;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.entityIdentifier = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        ByteBufUtils.writeUTF8String(buf, this.entityIdentifier);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PacketAddTuretTarget onMessage(PacketAddTuretTarget packet, MessageContext ctx)
    {
        TileEntityTurret tile = (TileEntityTurret) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(packet.x, packet.y, packet.z);

        if (tile != null)
        {
            tile.addTargetType((Class<? extends Entity>) EntityList.stringToClassMapping.get(packet.entityIdentifier));
        }

        return null;
    }
}
