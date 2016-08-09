package org.avp.packets.server;

import com.arisux.amdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public class PacketSpawnEntity implements IMessage, IMessageHandler<PacketSpawnEntity, PacketSpawnEntity>
{
    public int x, y, z;
    public String entityId;

    public PacketSpawnEntity()
    {
        ;
    }

    public PacketSpawnEntity(int x, int y, int z, String entityId)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityId = entityId;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        ByteBufUtils.writeUTF8String(buffer, entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.entityId = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public PacketSpawnEntity onMessage(PacketSpawnEntity message, MessageContext ctx)
    {
        if (ctx.getServerHandler().playerEntity != null && ctx.getServerHandler().playerEntity.worldObj != null)
        {
            Entity entity = Entities.constructEntity(ctx.getServerHandler().playerEntity.worldObj, Entities.getRegisteredEntityClass(message.entityId));

            if (entity != null)
            {
                entity.setLocationAndAngles(message.x + 0.5D, message.y + 1D, message.z + 0.5D, 0.0F, 0.0F);
                ctx.getServerHandler().playerEntity.worldObj.spawnEntityInWorld(entity);
            }
        }

        return null;
    }
}
