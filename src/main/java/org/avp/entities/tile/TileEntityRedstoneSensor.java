package org.avp.entities.tile;

import org.avp.util.IVoltageProvider;

import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneSensor extends TileEntityElectrical implements IVoltageProvider
{
    public boolean isActiveRedstoneWireAttached;

    public TileEntityRedstoneSensor()
    {
        super(true);
    }

    @Override
    public void updateEntity()
    {
        if (this.canOutputPower())
        {
            this.setVoltage(12);
        }
        else
        {
            this.setVoltage(0);
        }
    }

    @Override
    public boolean canConnectPower(ForgeDirection from)
    {
        return true;
    }

    @Override
    public double extractVoltage(ForgeDirection from, double maxExtract, boolean simulate)
    {
        return super.extractVoltage(from, maxExtract, simulate);
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

    public boolean canOutputPower()
    {
        World world = this.getWorld();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;

        if (world.getBlock(x + 1, y, z).getMaterial() == Material.circuits)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x + 1, y, z) != 0;
        }
        else if (world.getBlock(x, y + 1, z).getMaterial() == Material.circuits)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y + 1, z) != 0;
        }
        else if (world.getBlock(x, y, z + 1).getMaterial() == Material.circuits)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y, z + 1) != 0;
        }
        else if (world.getBlock(x - 1, y, z).getMaterial() == Material.circuits)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x - 1, y, z) != 0;
        }
        else if (world.getBlock(x, y - 1, z).getMaterial() == Material.circuits)
        {
            this.isActiveRedstoneWireAttached = world.getBlockMetadata(x, y - 1, z) != 0;
        }
        else if (world.getBlock(x, y, z - 1).getMaterial() == Material.circuits)
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
