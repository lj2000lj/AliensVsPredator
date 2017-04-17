package org.avp.block;

import java.util.Random;

import org.avp.DimensionHandler;

import com.arisux.mdxlib.lib.world.entity.Entities;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockPortal extends Block
{
    private int dimensionId;

    public BlockPortal(int dimensionId)
    {
        super(Material.PORTAL);
        setLightOpacity(100);
        setTickRandomly(true);
        this.dimensionId = dimensionId;
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
                    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos)
                    {
                        return null;
                    }
                };
            }
        };
    }
    
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, world, pos, rand);

        for (int i = 6; i > 0; --i)
        {
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
    {
        if ((entity.getRidingEntity()== null) && (Entities.getEntityRiddenBy(entity) == null) && ((entity instanceof EntityPlayerMP)))
        {
            EntityPlayerMP player = (EntityPlayerMP) entity;

            if (player.timeUntilPortal > 0)
            {
                player.timeUntilPortal = 10;
            }
            else if (player.dimension != this.dimensionId)
            {
                player.timeUntilPortal = 10;
                DimensionHandler.teleportPlayerToDimension(player, this.dimensionId);
            }
            else
            {
                player.timeUntilPortal = 10;
                DimensionHandler.teleportPlayerToDimension(player, 0);
            }
        }
    }
}
