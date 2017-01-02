package org.avp.entities.tile;

import java.util.ArrayList;

import org.avp.util.IPowerSource;

import com.arisux.mdxlib.lib.world.CoordData;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneSensor extends TileEntityElectrical implements IPowerSource
{
    public boolean isActiveRedstoneWireAttached;

    public TileEntityRedstoneSensor()
    {
        super();
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
    public boolean canConnect(ForgeDirection from)
    {
        return true;
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

        ArrayList<CoordData> locations = new ArrayList<CoordData>();
        CoordData loc = new CoordData(x, y, z);
        CoordData right = loc.add(1, 0, 0);
        CoordData left = loc.add(-1, 0, 0);
        CoordData front = loc.add(0, 0, 1);
        CoordData back = loc.add(0, 0, -1);
        CoordData up = loc.add(0, 1, 0);
        CoordData down = loc.add(0, -1, 0);

        locations.add(right);
        locations.add(left);
        locations.add(front);
        locations.add(back);
        locations.add(up);
        locations.add(down);

        this.isActiveRedstoneWireAttached = false;

        for (CoordData location : locations)
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
