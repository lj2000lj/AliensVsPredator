package org.avp.entities.tile;

import org.avp.util.IPowerDrain;
import org.avp.util.IPowerNode;
import org.avp.util.IPowerSource;

import com.arisux.mdxlib.lib.world.tile.IRotatable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTransformer extends TileEntityElectrical implements IPowerDrain, IRotatable, IPowerSource
{
    private ForgeDirection direction;

    public TileEntityTransformer()
    {
        super();
        this.direction = ForgeDirection.SOUTH;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (this.direction != null)
        {
            nbt.setInteger("Direction", this.direction.ordinal());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.direction = ForgeDirection.getOrientation(nbt.getInteger("Direction"));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.updateEnergyAsReceiver();

        if (this.voltage > 0)
        {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (tile != null && tile instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) tile;

                if (electrical instanceof IPowerSource)
                {
                    if (electrical.getVoltage() == 0)
                    {
                        this.voltage = 0;
                    }
                }
            }
            else
            {
                this.voltage = 0;
            }
        }
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return from == direction;
    }

    @Override
    public double getVoltage()
    {
        return this.voltage;
    }

    @Override
    public double provideVoltage(IPowerNode from)
    {
        TileEntity tile = (TileEntity) from;

        if (tile != null && tile instanceof TileEntityElectrical)
        {
            return (this.getVoltage() + 24) - this.getResistance();
        }

        return 0;
    }
    
    @Override
    public double provideAmperage(IPowerNode to)
    {
        TileEntity drain = (TileEntity) to;

        if (drain != null && drain instanceof TileEntityElectrical)
        {
            return 0 - 10;
        }

        return 0;
    }

    @Override
    public boolean canProvideEnergyToReceiver(ForgeDirection side)
    {
        return side == direction || side == direction.getOpposite();
    }

    @Override
    public double getVoltageThreshold()
    {
        return 10000;
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }
}
