package org.avp.world.dimension.varda.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class VardaGenStalagtites extends WorldGenerator
{
    private IBlockState state;

    public VardaGenStalagtites(IBlockState block)
    {
        this.state = block;
    }
    
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for (int x = 0; x < 64; x++)
        {
            BlockPos posRand = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (world.isAirBlock(posRand) && state.getBlock().canPlaceBlockAt(world, posRand))
            {
                world.setBlockState(posRand, this.state);
            }
        }
        
        return true;
    }
}
