package org.avp.packets.server;

import org.avp.entities.Organism;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class OrganismServerSync implements IMessage, IMessageHandler<OrganismServerSync, OrganismServerSync>
{
    public NBTTagCompound tag;
    private int entityId;

    public OrganismServerSync()
    {
        ;
    }

    public OrganismServerSync(int entityId, NBTTagCompound tag)
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
    public OrganismServerSync onMessage(OrganismServerSync packet, MessageContext ctx)
    {
        Entity entity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(packet.entityId);

        if (entity != null)
        {
            Organism extendedLiving = (Organism) entity.getExtendedProperties(Organism.IDENTIFIER);

            if (extendedLiving != null)
            {
                extendedLiving.loadNBTData(packet.tag);
            }
        }

        return null;
    }
}
