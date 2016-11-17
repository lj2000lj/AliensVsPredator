package org.avp.entities.tile;

import org.avp.util.IPowerAcceptor;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLightPanel extends TileEntityElectrical implements IPowerAcceptor
{
    public TileEntityLightPanel()
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
        return 0;
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
