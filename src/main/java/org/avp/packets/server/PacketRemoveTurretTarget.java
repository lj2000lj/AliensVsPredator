package org.avp.packets.server;

import org.avp.tile.TileEntityTurret;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class PacketRemoveTurretTarget implements IMessage, IMessageHandler<PacketRemoveTurretTarget, PacketRemoveTurretTarget>
{
    public int x, y, z;
    public String entityIdentifier;

    public PacketRemoveTurretTarget()
    {
        ;
    }

    public PacketRemoveTurretTarget(int x, int y, int z, String entityIdentifier)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityIdentifier = entityIdentifier;
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
    public PacketRemoveTurretTarget onMessage(PacketRemoveTurretTarget packet, MessageContext ctx)
    {
        TileEntityTurret tile = (TileEntityTurret) ctx.getServerHandler().playerEntity.worldObj.getTileEntity(packet.x, packet.y, packet.z);

        if (tile != null)
        {
            tile.removeTargetType((Class<? extends Entity>) EntityList.stringToClassMapping.get(packet.entityIdentifier));
        }
        
        return null;
    }
}
