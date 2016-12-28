package org.avp.packets.server;

import org.avp.AliensVsPredator;
import org.avp.init.Assembler;
import org.avp.init.Assembler.AssemblerSchematic;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class PacketAssembleCurrentSchematic implements IMessage, IMessageHandler<PacketAssembleCurrentSchematic, PacketAssembleCurrentSchematic>
{
    public String schematicId;
    public int    amountToAssemble;

    public PacketAssembleCurrentSchematic()
    {
        ;
    }

    public PacketAssembleCurrentSchematic(String schematicId, int amountToAssemble)
    {
        this.schematicId = schematicId;
        this.amountToAssemble = amountToAssemble;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.schematicId = ByteBufUtils.readUTF8String(buf);
        this.amountToAssemble = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.schematicId);
        buf.writeInt(this.amountToAssemble);
    }

    @Override
    public PacketAssembleCurrentSchematic onMessage(PacketAssembleCurrentSchematic packet, MessageContext ctx)
    {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null)
        {
            AssemblerSchematic schematic = AliensVsPredator.assembler().getSchematicForId(packet.schematicId);

            int assembled = 0;

            for (int i = 0; i < packet.amountToAssemble; i++)
            {
                assembled = i;
                
                if (!Assembler.instance.assembleSchematicAsPlayer(schematic, player))
                {
                    assembled = i;
                    break;
                }
                else
                {
                    assembled = i + 1;
                }
            }

            if (assembled != 0)
            {
                player.addChatMessage(new ChatComponentText(String.format("Assembled %sx %s", assembled, schematic.getItemStackAssembled().getDisplayName())));
            }
            else
            {
                player.addChatMessage(new ChatComponentText(String.format("Not enough materials to assemble %sx %s", packet.amountToAssemble, schematic.getItemStackAssembled().getDisplayName())));
            }
        }

        return null;
    }
}
