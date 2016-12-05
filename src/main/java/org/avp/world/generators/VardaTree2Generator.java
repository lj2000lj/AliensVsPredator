package org.avp.world.generators;

import java.util.Random;

import net.minecraft.world.World;

public class VardaTree2Generator extends VardaTreeGenerator
{
    public VardaTree2Generator(boolean doBlockNotify)
    {
        super(doBlockNotify);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (!isLocationValid(world, x, y, z) || !isLocationValid(world, x + 2, y, z) || !isLocationValid(world, x + 2, y, z + 2) || !isLocationValid(world, x, y, z + 2))
        {
            return false;
        }

        z = z - 3;
        x = x - 4;
        
        this.setBlockAndNotifyAdequately(world, x + 0, y + 4, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 5, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 6, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 7, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 8, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 5, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 8, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 8, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 1, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 2, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 3, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 3, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 4, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 4, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 4, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 5, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 5, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 6, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 6, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 7, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 7, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 8, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 8, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 8, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 1, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 2, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 3, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 4, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 4, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 5, z + 5, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 5, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 5, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 5, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 4, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 5, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 1, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 2, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 3, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 4, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 4, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 4, z + 4, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 5, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 5, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 6, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 6, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 7, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 7, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 7, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 0, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 0, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 1, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 2, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 3, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 4, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 4, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 4, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 5, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 5, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 6, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 6, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 7, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 7, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 7, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 4, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 4, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 5, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 6, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 7, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 7, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 7, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 8, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 8, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 8, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 5, z + 5, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 5, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 6, z + 5, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 6, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 7, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 7, z + 5, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 7, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 8, z + 5, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 7, y + 8, z + 6, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 8, y + 4, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 8, y + 5, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 8, y + 6, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 8, y + 7, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 8, y + 8, z + 5, TREE_LEAVES, 0);
        
        return true;
    }
}
