package org.avp.entities.tile;

import org.avp.util.IPowerProvider;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySolarPanel extends TileEntityElectrical implements IPowerProvider
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
        }
        else
        {
            this.setVoltage(0);
        }
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return super.canConnect(from);
    }

    @Override
    public double provideVoltage(ForgeDirection from, double maxExtract, boolean simulate)
    {
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
        return 10000;
    }
}
