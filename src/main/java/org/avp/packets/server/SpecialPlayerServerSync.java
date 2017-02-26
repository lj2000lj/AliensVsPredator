package org.avp.packets.server;

import org.avp.AliensVsPredator;
import org.avp.entities.SharedPlayer;
import org.avp.packets.client.SpecialPlayerClientSync;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class SpecialPlayerServerSync implements IMessage, IMessageHandler<SpecialPlayerServerSync, SpecialPlayerServerSync>
{
    public NBTTagCompound tag;
    private int entityId;

    public SpecialPlayerServerSync()
    {
        ;
    }

    public SpecialPlayerServerSync(int entityId, NBTTagCompound tag)
    {
        this.entityId = entityId;
        this.tag = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        ByteBufUtils.writeTag(buf, tag);
    }

    @Override
    public SpecialPlayerServerSync onMessage(SpecialPlayerServerSync packet, MessageContext ctx)
    {
        Entity entity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(packet.entityId);

        if (entity != null)
        {
            SharedPlayer extendedPlayer = (SharedPlayer) entity.getExtendedProperties(SharedPlayer.IDENTIFIER);

            if (extendedPlayer != null)
            {
                extendedPlayer.loadNBTData(packet.tag);
                AliensVsPredator.network().sendToAll(new SpecialPlayerClientSync(entity.getEntityId(), extendedPlayer.asCompoundTag()));
            }
        }

        return null;
    }
}
