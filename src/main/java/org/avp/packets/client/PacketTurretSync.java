package org.avp.packets.client;

import org.avp.tile.TileEntityTurret;

import com.arisux.mdxlib.lib.client.render.Rotation;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.storage.NBTStorage;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketTurretSync implements IMessage, IMessageHandler<PacketTurretSync, PacketTurretSync>
{
    public int        x, y, z;
    public NBTTagList targets;
    public Rotation   rotation;

    public PacketTurretSync()
    {
        ;
    }

    public PacketTurretSync(TileEntityTurret turret)
    {
        this.x = turret.xCoord;
        this.y = turret.yCoord;
        this.z = turret.zCoord;
        this.targets = turret.getTargetListTag();
        this.rotation = turret.getRotation();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.rotation = new Rotation(0F, 0F).readFromBuffer(buf);
        this.targets = (NBTTagList) NBTStorage.readFromBuffer(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        rotation.writeToBuffer(buf);
        NBTStorage.writeToBuffer(this.targets, buf);
    }

    @Override
    public PacketTurretSync onMessage(PacketTurretSync packet, MessageContext ctx)
    {
        TileEntityTurret tile = (TileEntityTurret) Game.minecraft().theWorld.getTileEntity(packet.x, packet.y, packet.z);

        if (tile != null)
        {
            tile.onReceiveInitPacket(packet, ctx);
        }
        return null;
    }
}
