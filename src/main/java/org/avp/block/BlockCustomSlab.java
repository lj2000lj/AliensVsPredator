package org.avp.block;

import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomSlab extends BlockSlab
{
    public BlockCustomSlab(Material material)
    {
        super(false, material);
        this.setUnlocalizedName(getLocalizedName() + "Slab");
    }

    @Override
    public int getRenderType()
    {
        return 0;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public String getFullSlabName(int type)
    {
        return getUnlocalizedName() + "-slab";
    }

    public BlockCustomSlab setTexture(String textureName)
    {
        this.setTextureName(textureName);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z)
    {
        return Game.getItem(this);
    }
}
