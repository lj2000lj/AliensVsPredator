package org.avp.tile;

import org.avp.api.power.IVoltageProvider;

import net.minecraft.util.EnumFacing;



public class TileEntityRepulsionGenerator extends TileEntityElectrical implements IVoltageProvider
{
    public TileEntityRepulsionGenerator()
    {
        super(true);
    }

    public int rotation;

    @Override
    public void update()
    {
        if (this.getWorld().getWorldTime() % (1000 / this.getSourceHertz()) == 0)
        {
            this.setVoltage(220);
        }
    }

    public void setDirection(byte direction)
    {
        this.rotation = direction;
    }

    @Override
    public boolean canConnectPower(EnumFacing from)
    {
        return true;
    }

    @Override
    public double extractVoltage(EnumFacing from, double maxExtract, boolean simulate)
    {
        return super.extractVoltage(from, maxExtract, simulate);
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
