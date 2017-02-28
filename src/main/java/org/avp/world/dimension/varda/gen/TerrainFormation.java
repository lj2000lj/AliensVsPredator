package org.avp.world.dimension.varda.gen;

import java.util.Random;

import org.avp.AliensVsPredator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class TerrainFormation extends WorldGenerator
{
    protected Block[] getValidTargetBlocks()
    {
        return new Block[] { AliensVsPredator.blocks().terrainUniDirt };
    }

    public boolean isLocationValid(World world, int posX, int posY, int posZ)
    {
        int airDist = 0;
        Block checkID = world.getBlock(posX, posY, posZ);

        while (checkID != Blocks.AIR)
        {
            airDist++;
            checkID = world.getBlock(posX, posY + airDist, posZ);
        }

        if (airDist > 3)
        {
            return false;
        }
        posY += airDist - 1;

        Block block = world.getBlock(posX, posY, posZ);
        Block above = world.getBlock(posX, posY + 1, posZ);
        Block below = world.getBlock(posX, posY - 1, posZ);

        for (Block target : getValidTargetBlocks())
        {
            if (above != Blocks.AIR)
            {
                return false;
            }
            if (block == target)
            {
                return true;
            }
            if ((block == Blocks.snow) && (below == target))
            {
                return true;
            }
        }
        return false;
    }

    public boolean generate(World world, Random rand, int posX, int posY, int posZ)
    {
        if ((!isLocationValid(world, posX, posY, posZ)) || (!isLocationValid(world, posX + 2, posY, posZ)) || (!isLocationValid(world, posX + 2, posY, posZ + 2)) || (!isLocationValid(world, posX, posY, posZ + 2)))
        {
            return false;
        }

        Block blockDirt = AliensVsPredator.blocks().terrainUniDirt;

        world.setBlock(posX + 0, posY + 0, posZ + 1, blockDirt);
        world.setBlock(posX + 0, posY + 1, posZ + 1, blockDirt);
        world.setBlock(posX + 0, posY + 2, posZ + 1, blockDirt);
        world.setBlock(posX + 0, posY + 3, posZ + 1, blockDirt);
        world.setBlock(posX + 0, posY + 4, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 0, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 0, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 0, posZ + 2, blockDirt);
        world.setBlock(posX + 1, posY + 1, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 1, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 1, posZ + 2, blockDirt);
        world.setBlock(posX + 1, posY + 2, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 2, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 2, posZ + 2, blockDirt);
        world.setBlock(posX + 1, posY + 3, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 3, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 3, posZ + 2, blockDirt);
        world.setBlock(posX + 1, posY + 4, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 4, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 5, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 5, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 6, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 7, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 8, posZ + 1, blockDirt);
        world.setBlock(posX + 2, posY + 0, posZ + 1, blockDirt);
        world.setBlock(posX + 2, posY + 1, posZ + 1, blockDirt);
        world.setBlock(posX + 2, posY + 2, posZ + 1, blockDirt);

        return true;
    }
}
