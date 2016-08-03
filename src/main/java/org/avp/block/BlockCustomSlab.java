package org.avp.block;

import com.arisux.amdxlib.lib.game.Game;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

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
