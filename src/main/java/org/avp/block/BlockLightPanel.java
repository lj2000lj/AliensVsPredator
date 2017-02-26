package org.avp.block;

import org.avp.tile.TileEntityLightPanel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLightPanel extends Block
{
    public BlockLightPanel(Material material)
    {
        super(material);
    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        return;
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
    public int onBlockPlaced(World world, int posX, int posY, int posZ, int side, float subX, float subY, float subZ, int meta)
    {
        return super.onBlockPlaced(world, posX, posY, posZ, side, subX, subY, subZ, meta);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityLightPanel();
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }
}
