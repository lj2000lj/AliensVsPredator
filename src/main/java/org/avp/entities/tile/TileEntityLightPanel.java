package org.avp.entities.tile;

import org.avp.util.IPowerDrain;
import org.avp.util.IPowerNode;

import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLightPanel extends TileEntityElectrical implements IPowerDrain
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
    public void drainPower(IPowerNode from, double voltage, double amperage)
    {
        super.drainPower(from, voltage, amperage);
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
