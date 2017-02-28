package org.avp.block;

import org.avp.item.ItemEntitySummoner;
import org.avp.tile.TileEntityCryostasisTube;

import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCryostasisTube extends Block
{
    public BlockCryostasisTube(Material material)
    {
        super(material);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2F, 1.0F);
        this.setTickRandomly(true);
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
        return new TileEntityCryostasisTube();
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldObj, int xCoord, int yCoord, int zCoord, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof ItemEntitySummoner)
        {
            TileEntityCryostasisTube tile = (TileEntityCryostasisTube) worldObj.getTileEntity(xCoord, yCoord, zCoord);

            if (tile != null)
            {
                if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemEntitySummoner)
                {
                    ItemEntitySummoner item = (ItemEntitySummoner) player.getHeldItemMainhand().getItem();
                    tile.stasisItemstack = new ItemStack(item, 1);
                    tile.stasisEntity = item.createNewEntity(worldObj);
                    Inventories.consumeItem(player, item);
                }
                else if (player.getHeldItemMainhand() == null)
                {
                    player.inventory.addItemStackToInventory(tile.stasisItemstack);
                    tile.stasisEntity = null;
                    tile.stasisItemstack = null;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, living, stack);

        TileEntityCryostasisTube tile = (TileEntityCryostasisTube) world.getTileEntity(x, y, z);

        if (tile != null)
        {
            tile.setDirection(getFacing(living));
            world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
        }
    }

    public static EnumFacing getFacing(Entity entity)
    {
        int dir = MathHelper.floor_double((entity.rotationYaw / 90) + 0.5) & 3;
        return EnumFacing.VALID_DIRECTIONS[Direction.directionToFacing[dir]];
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 0;
    }

    @Override
    public boolean canRenderInPass(int pass)
    {
        return super.canRenderInPass(pass);
    }
}
