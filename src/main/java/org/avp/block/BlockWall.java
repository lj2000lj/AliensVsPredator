package org.avp.block;

import org.avp.AliensVsPredator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWall extends Block
{
    public BlockWall(Material material)
    {
        super(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return AliensVsPredator.resources().ICONSET_WALLW.getIconForSide(side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        AliensVsPredator.resources().ICONSET_WALLW.registerIcons(register);
    }
}
