package org.avp.block;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.packets.client.PacketRotateRotatable;
import org.avp.tile.TileEntityTransformer;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;


public class BlockTransformer extends Block
{
    public BlockTransformer(Material material)
    {
        super(material);
        this.setTickRandomly(true);
    }
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[0])
        {
            @Override
            protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties)
            {
                return new StateImplementation(block, properties)
                {
                    @Override
                    public boolean isOpaqueCube()
                    {
                        return false;
                    }
                    
                    @Override
                    public EnumBlockRenderType getRenderType()
                    {
                        return EnumBlockRenderType.INVISIBLE;
                    }
                };
            }
        };
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityTransformer();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te != null && te instanceof TileEntityTransformer)
        {
            TileEntityTransformer transformer = (TileEntityTransformer) te;

            ArrayList<EnumFacing> EnumFacings = new ArrayList<EnumFacing>();

            for (EnumFacing dir : EnumFacing.VALUES)
            {
                if (dir != EnumFacing.UP && dir != EnumFacing.DOWN)
                {
                    EnumFacings.add(dir);
                }
            }

            if (transformer.getDirection() != null)
            {
                int index = EnumFacings.indexOf(transformer.getDirection());

                if (index + 1 >= EnumFacings.size())
                {
                    index = -1;
                }

                if (EnumFacings.get(index + 1) != null)
                {
                    transformer.setDirection(EnumFacings.get(index + 1));
                }

                if (!world.isRemote)
                {
                    AliensVsPredator.network().sendToAll(new PacketRotateRotatable(transformer.getDirection().ordinal(), transformer.getPos().getX(), transformer.getPos().getY(), transformer.getPos().getZ()));
                }
            }

            transformer.getUpdatePacket();
        }
        return super.onBlockActivated(world, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
