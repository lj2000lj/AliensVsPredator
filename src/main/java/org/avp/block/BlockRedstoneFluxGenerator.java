package org.avp.block;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.packets.client.PacketRotateRotatable;
import org.avp.tile.TileEntityRedstoneFluxGenerator;

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


public class BlockRedstoneFluxGenerator extends Block
{
    public BlockRedstoneFluxGenerator(Material material)
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
                    public EnumBlockRenderType getRenderType()
                    {
                        return EnumBlockRenderType.INVISIBLE;
                    }
                    
                    @Override
                    public boolean isOpaqueCube()
                    {
                        return false;
                    }
                };
            }
        };
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te != null && te instanceof TileEntityRedstoneFluxGenerator)
        {
            TileEntityRedstoneFluxGenerator generator = (TileEntityRedstoneFluxGenerator) te;

            ArrayList<EnumFacing> EnumFacings = new ArrayList<EnumFacing>();

            for (EnumFacing dir : EnumFacing.VALUES)
            {
                if (dir != EnumFacing.UP && dir != EnumFacing.DOWN)
                {
                    EnumFacings.add(dir);
                }
            }

            if (generator.getDirection() != null)
            {
                int index = EnumFacings.indexOf(generator.getDirection());

                if (index + 1 >= EnumFacings.size())
                {
                    index = -1;
                }

                if (EnumFacings.get(index + 1) != null)
                {
                    generator.setDirection(EnumFacings.get(index + 1));
                }

                if (!world.isRemote)
                {
                    AliensVsPredator.network().sendToAll(new PacketRotateRotatable(generator.getDirection().ordinal(), generator.getPos().getX(), generator.getPos().getY(), generator.getPos().getZ()));
                }
            }

            generator.getUpdatePacket();
        }
        return super.onBlockActivated(world, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityRedstoneFluxGenerator();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
}
