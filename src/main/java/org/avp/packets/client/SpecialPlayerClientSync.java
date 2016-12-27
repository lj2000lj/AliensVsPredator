package org.avp.packets.client;

import org.avp.entities.extended.SpecialPlayer;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class SpecialPlayerClientSync implements IMessage, IMessageHandler<SpecialPlayerClientSync, SpecialPlayerClientSync>
{
    public NBTTagCompound tag;
    private int entityId;

    public SpecialPlayerClientSync()
    {
        ;
    }

    public SpecialPlayerClientSync(int entityId, NBTTagCompound tag)
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

    @SuppressWarnings("all")
    @Override
    public SpecialPlayerClientSync onMessage(SpecialPlayerClientSync packet, MessageContext ctx)
    {
        Entity entity = Game.minecraft().thePlayer.worldObj.getEntityByID(packet.entityId);

        if (entity != null)
        {
            SpecialPlayer extendedPlayer = (SpecialPlayer) entity.getExtendedProperties(SpecialPlayer.IDENTIFIER);

            if (extendedPlayer != null)
            {
                extendedPlayer.loadNBTData(packet.tag);
            }
        }

        return null;
    }
}
