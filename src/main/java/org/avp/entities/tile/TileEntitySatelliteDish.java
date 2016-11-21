package org.avp.entities.tile;

import org.avp.util.IPowerDrain;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySatelliteDish extends TileEntityElectrical implements IPowerDrain
{
    public TileEntitySatelliteDish()
    {
        super();
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
