package org.avp.packets.server;

import org.avp.item.crafting.AssemblyManager;
import org.avp.item.crafting.Schematic;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class PacketAssemble implements IMessage, IMessageHandler<PacketAssemble, PacketAssemble>
{
    public String id;
    public int    count;

    public PacketAssemble()
    {
        ;
    }

    public PacketAssemble(String id, int count)
    {
        this.id = id;
        this.count = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.id = ByteBufUtils.readUTF8String(buf);
        this.count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.id);
        buf.writeInt(this.count);
    }

    @Override
    public PacketAssemble onMessage(PacketAssemble packet, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null)
        {
            Schematic schematic = AssemblyManager.instance.getSchematic(packet.id);

            int amount = 0;

            for (int i = 0; i < packet.count; i++)
            {
                if (AssemblyManager.handleAssembly(schematic, player))
                {
                    amount++;
                }
                else
                {
                    break;
                }
            }

            if (amount > 0)
            {
                player.addChatMessage(new ChatComponentText(String.format("Assembled %sx %s", amount, schematic.getItemStackAssembled().getDisplayName())));
            }
            else if (amount == 0)
            {
                player.addChatMessage(new ChatComponentText(String.format("Not enough materials to assemble %sx %s", packet.count, schematic.getItemStackAssembled().getDisplayName())));
            }
        }

        return null;
    }
}
