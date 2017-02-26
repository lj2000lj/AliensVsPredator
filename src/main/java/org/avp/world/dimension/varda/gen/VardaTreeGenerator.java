package org.avp.world.dimension.varda.gen;

import java.util.Random;

import org.avp.AliensVsPredator;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class VardaTreeGenerator extends WorldGenerator implements IWorldGenerator
{
    public static final Block TREE_TENDONS = AliensVsPredator.blocks().terrainUniTreeTendon;
    public static final Block TREE_LOGS = AliensVsPredator.blocks().terrainUniTreeLog;
    public static final Block TREE_LEAVES = AliensVsPredator.blocks().terrainUniTreeLeaves;
    
    public VardaTreeGenerator(boolean doBlockNotify)
    {
        super(doBlockNotify);
    }
    
    protected Block[] getValidTargetBlocks()
    {
        return new Block[] { AliensVsPredator.blocks().terrainUniDirt, AliensVsPredator.blocks().terrainUniTreeSapling };
    }

    public boolean isLocationValid(World world, int x, int y, int z)
    {
        int airDist = 0;
        Block check = world.getBlock(x, y, z);

        while (check != Blocks.air)
        {
            if (airDist > 3)
            {
                return false;
            }

            airDist++;
            check = world.getBlock(x, y + airDist, z);
        }

        y += airDist - 1;

        Block block = world.getBlock(x, y, z);
        Block blockAbove = world.getBlock(x, y + 1, z);
        Block blockBelow = world.getBlock(x, y - 1, z);

        for (Block validBlock : getValidTargetBlocks())
        {
            if (blockAbove != Blocks.air)
            {
                return false;
            }
            if (block == validBlock)
            {
                return true;
            }
            else if (block == Blocks.snow && blockBelow == validBlock)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        ;
    }

    public void setBlock(World world, int x, int y, int z, Block block, int metadata)
    {
        Block b1 = world.getBlock(x, y, z);

        if (b1.isAir(world, x, y, z) || b1.isLeaves(world, x, y, z))
        {
            this.setBlockAndNotifyAdequately(world, x, y, z, block, metadata);
        }
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (!isLocationValid(world, x, y, z) || !isLocationValid(world, x + 2, y, z + 2) || !isLocationValid(world, x, y, z + 2) || !isLocationValid(world, x + 2, y, z))
        {
            return false;
        }

        z = z - 2;
        x = x - 3;
        
        this.setBlockAndNotifyAdequately(world, x + 0, y + 5, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 6, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 7, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 0, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 0, z + 0, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 0, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 6, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 7, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 8, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 9, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 9, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 10, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 10, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 10, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 1, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 0, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 0, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 0, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 1, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 1, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 2, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 3, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 4, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 5, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 8, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 10, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 10, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 10, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 11, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 11, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 2, y + 11, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 0, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 1, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 2, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 3, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 4, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 5, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 5, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 6, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 7, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 8, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 9, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 10, z + 5, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 11, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 11, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 3, y + 11, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 2, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 0, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 1, z + 3, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 2, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 3, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 4, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 5, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 8, z + 2, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 2, TREE_LOGS, 4);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 1, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 11, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 11, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 4, y + 11, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 0, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 0, z + 3, TREE_TENDONS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 0, z + 4, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 1, z + 1, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 6, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 7, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 7, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 8, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 9, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 0, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 1, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 3, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 5, y + 10, z + 4, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 0, z + 0, TREE_LOGS, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 6, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 7, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 8, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 9, z + 2, TREE_LEAVES, 0);
        this.setBlockAndNotifyAdequately(world, x + 6, y + 10, z + 2, TREE_LEAVES, 0);

        return true;
    }
}
