package org.avp.entities.tile;

import org.avp.util.IPowerDrain;
import org.avp.util.IPowerNode;
import org.avp.util.IPowerSource;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPowercell extends TileEntityElectrical implements IPowerDrain, IPowerSource
{
    public double ampereHours;
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
        // super.updateEntity();

        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (tile != null && tile instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) tile;

                if (electrical.getVoltage() > this.getVoltage())
                {
//                    this.setVoltage(electrical.getVoltage());
                    this.voltageCapacity = electrical.getVoltage();
                }
            }
        }

        if (this.ampereHours > 0)
        {
            this.setVoltage(this.voltageCapacity);
//            this.ampereHours = this.ampereHours - 1;
        }
        else
        {
            this.setVoltage(0);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.ampereHours = nbt.getDouble("AmpereHours");
        this.voltageCapacity = nbt.getDouble("VoltageCapacity");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setDouble("AmpereHours", this.ampereHours);
        nbt.setDouble("VoltageCapacity", this.voltageCapacity);
    }

    @Override
    public double provideVoltage(IPowerNode to)
    {
        return super.provideVoltage(to);
    }

    @Override
    public double provideAmperage(IPowerNode to)
    {
        TileEntityElectrical electrical = (TileEntityElectrical) to;

        if (electrical instanceof IPowerSource)
        {
            if (electrical.getAmperage() > 0)
            {
                if (this.ampereHours < this.getMaxEnergyStored())
                {
                    this.ampereHours = this.ampereHours + to.getAmperage();
                }
            }
        }

        return this.ampereHours;
    }

    @Override
    public double getVoltage()
    {
        return this.voltage;
    }

    @Override
    public double getVoltageThreshold()
    {
        return 1000;
    }

    public double getAmpereHours()
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
