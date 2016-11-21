package org.avp.entities.tile;

import org.avp.util.IPowerSource;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityR2PConverter extends TileEntityElectrical implements IPowerSource
{
    public boolean isActiveRedstoneWireAttached;

    public TileEntityR2PConverter()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        if (this.canOutputPower())
        {
            this.setVoltage(120);
        }
        else
        {
            this.setVoltage(0);
        }
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

    public boolean canOutputPower()
    {
        World world = this.getWorld();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        if (world.getBlock(x + 1, y, z) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x + 1, y, z) != 0;
        }

        else if (world.getBlock(x, y + 1, z) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y + 1, z) != 0;
        }

        else if (world.getBlock(x, y, z + 1) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y, z + 1) != 0;
        }

        else if (world.getBlock(x - 1, y, z) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x - 1, y, z) != 0;
        }

        else if (world.getBlock(x, y - 1, z) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y - 1, z) != 0;
        }

        else if (world.getBlock(x, y, z - 1) instanceof BlockRedstoneWire)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y, z - 1) != 0;
        }

        else
        {
            this.isActiveRedstoneWireAttached = false;
        }

        return this.isActiveRedstoneWireAttached;
    }
}
