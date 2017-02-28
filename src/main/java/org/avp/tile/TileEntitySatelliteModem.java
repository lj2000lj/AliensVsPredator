package org.avp.tile;

import org.avp.api.power.IVoltageReceiver;

import net.minecraft.util.EnumFacing;



public class TileEntitySatelliteModem extends TileEntityElectrical implements IVoltageReceiver
{
    public TileEntitySatelliteModem()
    {
        super(false);
    }

    @Override
    public boolean canConnectPower(EnumFacing from)
    {
        return false;
    }

    @Override
    public double receiveVoltage(EnumFacing from, double maxReceive, boolean simulate)
    {
        return maxReceive;
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
