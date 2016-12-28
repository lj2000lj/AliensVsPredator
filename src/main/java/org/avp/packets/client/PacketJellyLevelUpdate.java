package org.avp.packets.client;

import org.avp.api.parasitoidic.IRoyalOrganism;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public class PacketJellyLevelUpdate implements IMessage, IMessageHandler<PacketJellyLevelUpdate, PacketJellyLevelUpdate>
{
    public int uuid;
    public int jellyLevel;

    public PacketJellyLevelUpdate()
    {
        ;
    }

    public PacketJellyLevelUpdate(int jellyLevel, IRoyalOrganism ro)
    {
        this.jellyLevel = jellyLevel;

        if (ro instanceof Entity)
        {
            Entity entity = (Entity) ro;
            this.uuid = Integer.valueOf(entity.getEntityId());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.jellyLevel = buf.readInt();
        this.uuid = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.jellyLevel);
        buf.writeInt(this.uuid);
    }

    @Override
    public PacketJellyLevelUpdate onMessage(PacketJellyLevelUpdate packet, MessageContext ctx)
    {
        IRoyalOrganism ro = ((IRoyalOrganism) Game.minecraft().thePlayer.worldObj.getEntityByID(packet.uuid));

        if (ro != null)
        {
            ro.setJellyLevel(packet.jellyLevel);
        }

        return null;
    }
}
