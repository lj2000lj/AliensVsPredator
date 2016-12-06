package org.avp.dimension.varda.worldgen;

import java.util.Random;

import org.avp.AliensVsPredator;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class TerrainFormation1 extends TerrainFormation
{
    protected Block[] getSpawnBlocks()
    {
        return new Block[] { AliensVsPredator.blocks().terrainUniDirt };
    }

    @Override
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
        world.setBlock(posX + 1, posY + 0, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 0, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 0, posZ + 2, blockDirt);
        world.setBlock(posX + 1, posY + 1, posZ + 0, blockDirt);
        world.setBlock(posX + 1, posY + 1, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 2, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 3, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 4, posZ + 1, blockDirt);
        world.setBlock(posX + 1, posY + 5, posZ + 1, blockDirt);
        world.setBlock(posX + 2, posY + 0, posZ + 1, blockDirt);
        world.setBlock(posX + 2, posY + 1, posZ + 1, blockDirt);

        return true;
    }
}
