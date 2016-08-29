package org.avp.block;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityHiveResin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHiveResin extends Block
{
    public BlockHiveResin(Material material)
    {
        super(material);
        this.setLightLevel(0.1F);
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
    public void updateTick(World worldObj, int posX, int posY, int posZ, Random rand)
    {
        super.updateTick(worldObj, posX, posY, posZ, rand);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityHiveResin();
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public void onBlockPreDestroy(World world, int posX, int posY, int posZ, int oldMetadata)
    {
        super.onBlockPreDestroy(world, posX, posY, posZ, oldMetadata);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(World world, int posX, int posY, int posZ, int metadata)
    {
        super.onBlockDestroyedByPlayer(world, posX, posY, posZ, metadata);
    }
    
    @Override
    public int getRenderType()
    {
        return AliensVsPredator.renderTypes().RENDER_TYPE_RESIN;
    }
}
