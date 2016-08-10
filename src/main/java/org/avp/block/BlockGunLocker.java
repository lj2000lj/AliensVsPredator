package org.avp.block;

import org.avp.entities.tile.TileEntityGunLocker;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGunLocker extends BlockLocker
{
    public BlockGunLocker(Material material)
    {
        super(material);
        setTickRandomly(true);
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
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityGunLocker();
    }

    @Override
    public void onBlockPreDestroy(World worldIn, int x, int y, int z, int meta)
    {
        super.onBlockPreDestroy(worldIn, x, y, z, meta);
        
        TileEntityGunLocker locker = (TileEntityGunLocker) worldIn.getTileEntity(x, y, z);
        
        if (locker != null)
        {
            for (int i = locker.inventory.getSizeInventory(); i > 0; i--)
            {
                ItemStack stack = locker.inventory.getStackInSlot(i);
                
                if (stack != null)
                {
                    EntityItem entityItem = new EntityItem(worldIn, x, y, z, stack);
                    worldIn.spawnEntityInWorld(entityItem);
                }
            }
        }
    }
}
