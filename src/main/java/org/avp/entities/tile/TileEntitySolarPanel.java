package org.avp.entities.tile;

import org.avp.util.IPowerSource;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySolarPanel extends TileEntityElectrical implements IPowerSource
{
    public TileEntitySolarPanel()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        if (this.worldObj.getWorldTime() < 12300 || this.worldObj.getWorldTime() > 23850)
        {
            this.setVoltage(120);
            this.setAmperage(10);
        }
        else
        {
            this.setVoltage(0);
            this.setAmperage(0);
        }
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return super.canConnect(from);
    }

    @Override
    public double getVoltage()
    {
        return this.voltage;
    }

    @Override
    public double getVoltageThreshold()
    {
        return 10000;
    }
}
