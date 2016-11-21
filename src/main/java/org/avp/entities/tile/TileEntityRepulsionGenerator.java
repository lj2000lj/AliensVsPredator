package org.avp.entities.tile;

import org.avp.util.IPowerSource;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRepulsionGenerator extends TileEntityElectrical implements IPowerSource
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
        this.setAmperage(80);
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
