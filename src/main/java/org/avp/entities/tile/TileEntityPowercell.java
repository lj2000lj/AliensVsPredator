package org.avp.entities.tile;

import org.avp.util.IPowerProvider;
import org.avp.util.IPowerAcceptor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPowercell extends TileEntityElectrical implements IPowerAcceptor, IPowerProvider
{
    public long   ampereHours;
    public double voltageCapacity;

    public TileEntityPowercell()
    {
        super();
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return super.canConnect(from);
    }

    @Override
    public void updateEntity()
    {
//        super.updateEntity();

        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (tile != null && tile instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) tile;

                if (electrical.getVoltage() > this.getVoltage())
                {
                    this.setVoltage(electrical.getVoltage());
                }

                if (electrical instanceof IPowerProvider)
                {
                    IPowerProvider provider = (IPowerProvider) electrical;

                    if (provider.getVoltage(direction.getOpposite()) > 0)
                    {
                        if (this.ampereHours < this.getMaxEnergyStored())
                        {
                            //TODO: Take amperage from provider
                            this.ampereHours++;
                        }
                    }
                }
            }
        }
        
//        if ()

        
        
        // if (this.ampereHours > 0)
        // {
        // this.setVoltage(this.voltageCapacity);
        // this.ampereHours = this.ampereHours - 1;
        // }
        // else
        // {
        // this.setVoltage(0);
        // }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.ampereHours = nbt.getLong("AmpereHours");
        this.voltageCapacity = nbt.getDouble("VoltageCapacity");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setLong("AmpereHours", this.ampereHours);
        nbt.setDouble("VoltageCapacity", this.voltageCapacity);
    }

    @Override
    public double acceptVoltageFrom(ForgeDirection from, double maxReceive, boolean simulate)
    {
        return super.acceptVoltageFrom(from, maxReceive, simulate);
    }

    @Override
    public double provideVoltage(ForgeDirection from, double maxExtract, boolean simulate)
    {
        this.ampereHours = this.ampereHours - 1;

        return super.provideVoltage(from, maxExtract, simulate);
    }

    @Override
    public double getVoltage(ForgeDirection from)
    {
        return this.voltage;
    }

    @Override
    public double getMaxVoltage(ForgeDirection from)
    {
        return 1000;
    }

    public long getAmpereHours()
    {
        return ampereHours;
    }

    public long getMaxEnergyStored()
    {
        return 10000;
    }

    public double getVoltageCapacity()
    {
        return voltageCapacity;
    }
}
