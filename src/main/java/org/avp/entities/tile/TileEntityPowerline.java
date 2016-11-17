package org.avp.entities.tile;

import org.avp.util.IPowerProvider;
import org.avp.util.IPowerAcceptor;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPowerline extends TileEntityElectrical implements IPowerProvider, IPowerAcceptor
{
    public TileEntityPowerline()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.updateEnergyAsReceiver();
    }

    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return super.canConnect(from);
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
