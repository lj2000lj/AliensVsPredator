package org.avp.block;

import java.util.List;
import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.dimension.varda.worldgen.VardaTallTreeGenerator;
import org.avp.dimension.varda.worldgen.VardaTree2Generator;
import org.avp.dimension.varda.worldgen.VardaTree3Generator;
import org.avp.dimension.varda.worldgen.VardaTreeGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockUnidentifiedTreeSapling extends BlockSapling
{
    public BlockUnidentifiedTreeSapling()
    {
        super();
    }
    
    @Override
    protected boolean canPlaceBlockOn(Block ground)
    {
        return super.canPlaceBlockOn(ground) || ground == AliensVsPredator.blocks().terrainUniDirt;
    }

    @Override
    public int getRenderType()
    {
        return 1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(this.getTextureName());
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return this.blockIcon;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
    }

    public boolean isSapling(World world, int x, int y, int z)
    {
        return world.getBlock(x, y, z) == this;
    }

    @Override
    public void growTree(World world, int x, int y, int z, Random rand)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;
        
        int meta = world.getBlockMetadata(x, y, z) & 7;
        WorldGenerator tree = null;
        boolean largeTree = false;
        
        int rX = 0;
        int rZ = 0;
        
        for (rX = 0; rX >= -1; --rX)
        {
            for (rZ = 0; rZ >= -1; --rZ)
            {
                if (this.isSapling(world, x + rX, y, z + rZ) && this.isSapling(world, x + rX + 1, y, z + rZ) && this.isSapling(world, x + rX, y, z + rZ + 1) && this.isSapling(world, x + rX + 1, y, z + rZ + 1))
                {
                    largeTree = true;
                }
            }
        }
        
        if (largeTree)
        {
            tree = new VardaTallTreeGenerator(true);
        }
        else
        {
            switch(rand.nextInt(3))
            {
                case 0:
                    tree = new VardaTreeGenerator(true);
                    break;
                case 1:
                    tree = new VardaTree2Generator(true);
                    break;
                case 2:
                    tree = new VardaTree3Generator(true);
                    break;
            }
        }

        Block block = Blocks.air;

        if (largeTree)
        {
            world.setBlock(x + rX, y, z + rZ, block, 0, 4);
            world.setBlock(x + rX + 1, y, z + rZ, block, 0, 4);
            world.setBlock(x + rX, y, z + rZ + 1, block, 0, 4);
            world.setBlock(x + rX + 1, y, z + rZ + 1, block, 0, 4);
        }
        else
        {
            world.setBlock(x, y, z, block, 0, 4);
        }

        if (!tree.generate(world, rand, x, y, z))
        {
            if (largeTree)
            {
                world.setBlock(x + rX, y, z + rZ, this, meta, 4);
                world.setBlock(x + rX + 1, y, z + rZ, this, meta, 4);
                world.setBlock(x + rX, y, z + rZ + 1, this, meta, 4);
                world.setBlock(x + rX + 1, y, z + rZ + 1, this, meta, 4);
            }
            else
            {
                world.setBlock(x, y, z, this, meta, 4);
            }
        }
    }
}
