package org.avp.block;

import java.util.Random;

import org.avp.tile.TileEntitySkull;

import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.Texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockSkull extends Block
{
    public BlockSkull()
    {
        super(Material.iron);
        this.setLightOpacity(2);
    }

    @SideOnly(Side.CLIENT)
    public void preRenderTransforms()
    {
        float scale = 3.0F;
        OpenGL.scale(scale, scale, scale);
        OpenGL.translate(0F, 0.05F, 0F);
    }
    
    @SideOnly(Side.CLIENT)
    public ModelRenderer[] getSkullModelRenderers()
    {
        return new ModelRenderer[] {};
    }
    
    @SideOnly(Side.CLIENT)
    public Texture getSkullTexture()
    {
        return null;
    }
    
    @Override
    public void registerIcons(IIconRegister reg)
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
    public void updateTick(World world, int posX, int posY, int posZ, Random random)
    {
        super.updateTick(world, posX, posY, posZ, random);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntitySkull();
    }

    @Override
    public void onBlockPlacedBy(World world, int posX, int posY, int posZ, EntityLivingBase placer, ItemStack itemstack)
    {
        TileEntity tile = world.getTileEntity(posX, posY, posZ);

        if (tile != null && tile instanceof TileEntitySkull && placer != null)
        {
            TileEntitySkull skull = (TileEntitySkull) tile;
            skull.setDirection(getFacing(placer));
        }
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockaccess, int posX, int posY, int posZ, int side)
    {
        return false;
    }

    public static ForgeDirection getFacing(Entity entity)
    {
        int dir = MathHelper.floor_double((entity.rotationYaw / 90) + 0.5) & 3;
        return ForgeDirection.VALID_DIRECTIONS[Direction.directionToFacing[dir]];
    }
}
