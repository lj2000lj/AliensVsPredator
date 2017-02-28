package org.avp.packets.server;

import org.avp.item.ItemFirearm;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketReloadFirearm implements IMessage, IMessageHandler<PacketReloadFirearm, PacketReloadFirearm>
{
    public PacketReloadFirearm()
    {
        ;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ;
    }

    @Override
    public PacketReloadFirearm onMessage(PacketReloadFirearm message, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null && player.inventory != null && player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemFirearm)
        {
            ((ItemFirearm) player.inventory.getCurrentItem().getItem()).reload(ctx.getServerHandler().playerEntity);
        }

        return null;
    }
}
