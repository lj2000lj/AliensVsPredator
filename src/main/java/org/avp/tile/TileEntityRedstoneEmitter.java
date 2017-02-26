package org.avp.tile;

import org.avp.api.power.IVoltageReceiver;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneEmitter extends TileEntityElectrical implements IVoltageReceiver
{
    public TileEntityRedstoneEmitter()
    {
        super(false);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.updateEnergyAsReceiver();

        World world = this.getWorld();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        if (this.voltagePrev != this.voltage)
        {
            if (world.getBlockMetadata(x, y, z) == 0 && this.voltage >= 1)
            {
                world.setBlockMetadataWithNotify(x, y, z, 15, 3);
            }
            if (world.getBlockMetadata(x, y, z) == 15 && this.voltage == 0)
            {
                world.setBlockMetadataWithNotify(x, y, z, 0, 3);
            }
        }
    }

    @Override
    public boolean canConnectPower(ForgeDirection from)
    {
        return false;
    }

    @Override
    public double getCurrentVoltage(ForgeDirection from)
    {
        return this.voltage;
    }

    @Override
    public double getMaxVoltage(ForgeDirection from)
    {
        return 10000;
    }
}
