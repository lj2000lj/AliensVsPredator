package org.avp.entities.tile;

import org.avp.util.IPowerAcceptor;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySatelliteModem extends TileEntityElectrical implements IPowerAcceptor
{
    public TileEntitySatelliteModem()
    {
        super();
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return super.canConnect(from);
    }

    @Override
    public double acceptVoltageFrom(ForgeDirection from, double maxReceive, boolean simulate)
    {
        return maxReceive;
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
