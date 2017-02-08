package org.avp.entities.tile;

import java.util.ArrayList;

import org.avp.util.IVoltageProvider;

import com.arisux.mdxlib.lib.world.Pos;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
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

        ArrayList<Pos> locations = new ArrayList<Pos>();
        Pos loc = new Pos(x, y, z);
        Pos right = loc.add(1, 0, 0);
        Pos left = loc.add(-1, 0, 0);
        Pos front = loc.add(0, 0, 1);
        Pos back = loc.add(0, 0, -1);
        Pos up = loc.add(0, 1, 0);
        Pos down = loc.add(0, -1, 0);

        locations.add(right);
        locations.add(left);
        locations.add(front);
        locations.add(back);
        locations.add(up);
        locations.add(down);

        this.isActiveRedstoneWireAttached = false;

        for (Pos location : locations)
        {
            if (location.getBlock(world).getMaterial() == Material.circuits)
            {
                if (location.getBlock(world) == Blocks.redstone_wire && location.getBlockMetadata(world) > 0)
                {
                    this.isActiveRedstoneWireAttached = true;
                    break;
                }
                if (location.getBlock(world) == Blocks.lever && location.getBlockMetadata(world) >= 9)
                {
                    this.isActiveRedstoneWireAttached = true;
                    break;
                }
                if (location.getBlock(world) == Blocks.detector_rail && location.getBlockMetadata(world) >= 8)
                {
                    this.isActiveRedstoneWireAttached = true;
                    break;
                }
                if (location.getBlock(world) == Blocks.powered_repeater)
                {
                    this.isActiveRedstoneWireAttached = true;
                    break;
                }
                if (location.getBlock(world) == Blocks.redstone_torch)
                {
                    this.isActiveRedstoneWireAttached = true;
                    break;
                }
            }
        }

        return this.isActiveRedstoneWireAttached;
    }
}
