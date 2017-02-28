package org.avp.tile;

import org.avp.api.power.IVoltageProvider;
import org.avp.api.power.IVoltageReceiver;

import net.minecraft.util.EnumFacing;



public class TileEntityPowerline extends TileEntityElectrical implements IVoltageProvider, IVoltageReceiver
{
    public TileEntityPowerline()
    {
        super(false);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.updateEnergyAsReceiver();
    }

    @Override
    public boolean canConnectPower(EnumFacing from)
    {
        return true;
    }

    @Override
    public double getCurrentVoltage(EnumFacing from)
    {
        return this.voltage;
    }

    @Override
    public double getMaxVoltage(EnumFacing from)
    {
        return 10000;
    }
}
