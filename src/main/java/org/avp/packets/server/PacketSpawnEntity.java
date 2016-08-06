package org.avp.packets.server;

import org.avp.AliensVsPredator;

import com.arisux.amdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public class PacketSpawnEntity implements IMessage, IMessageHandler<PacketSpawnEntity, PacketSpawnEntity>
{
    public int x, y, z, entityId = 0;

    public PacketSpawnEntity()
    {
        ;
    }

    public PacketSpawnEntity(int x, int y, int z, int globalID)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityId = globalID;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.entityId = buffer.readInt();
    }

    @Override
    public PacketSpawnEntity onMessage(PacketSpawnEntity message, MessageContext ctx)
    {
        if (ctx.getServerHandler().playerEntity != null && ctx.getServerHandler().playerEntity.worldObj != null)
        {
            Entity entity = Entities.constructEntity(ctx.getServerHandler().playerEntity.worldObj, EntityRegistry.instance().lookupModSpawn(AliensVsPredator.instance().container(), message.entityId).getEntityClass());

            if (entity != null)
            {
                entity.setLocationAndAngles(message.x + 0.5D, message.y + 1D, message.z + 0.5D, 0.0F, 0.0F);
                ctx.getServerHandler().playerEntity.worldObj.spawnEntityInWorld(entity);
            }
        }

        return null;
    }
}
