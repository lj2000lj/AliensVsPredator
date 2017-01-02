package org.avp.packets.client;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class PacketVardaStormMoveEntity implements IMessage, IMessageHandler<PacketVardaStormMoveEntity, PacketVardaStormMoveEntity>
{
    public int uuid;

    public PacketVardaStormMoveEntity()
    {
        ;
    }

    public PacketVardaStormMoveEntity(int uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.uuid = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.uuid);
    }

    @Override
    public PacketVardaStormMoveEntity onMessage(PacketVardaStormMoveEntity packet, MessageContext ctx)
    {
        Entity entity = (Game.minecraft().thePlayer.worldObj.getEntityByID(packet.uuid));

        if (entity != null)
        {
            if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode || !(entity instanceof EntityPlayer))
            {
                entity.motionZ += 0.04F;
                entity.motionY += MathHelper.sin(entity.worldObj.getWorldTime() * 0.4F) * 0.1F;
            }
        }

        return null;
    }
}
