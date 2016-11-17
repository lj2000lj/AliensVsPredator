package org.avp.entities.tile;

import org.avp.util.IPowerProvider;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRepulsionGenerator extends TileEntityElectrical implements IPowerProvider
{
    public int rotation;

    public TileEntityRepulsionGenerator()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        this.setVoltage(220);
    }

    public void setDirection(byte direction)
    {
        this.rotation = direction;
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
