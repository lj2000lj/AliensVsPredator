package org.avp.entities.tile;

import org.avp.util.IPowerDrain;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneEmitter extends TileEntityElectrical implements IPowerDrain
{
    public TileEntityRedstoneEmitter()
    {
        super();
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
    public boolean canConnect(ForgeDirection from)
    {
        return false;
    }

    @Override
    public double getVoltageThreshold()
    {
        return 10000;
    }
}
