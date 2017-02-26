package org.avp.block;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityHiveResin;
import org.avp.world.hives.HiveHandler;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.Pos.BlockDataStore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHiveResin extends Block
{
    public BlockHiveResin(Material material)
    {
        super(material);
        this.setLightLevel(0.1F);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return 5;
    }

    @Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        return 5;
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
        TileEntity tile = world.getTileEntity(posX, posY, posZ);

        if (tile instanceof TileEntityHiveResin)
        {
            Pos coord = new Pos(posX, posY, posZ);
            TileEntityHiveResin resin = (TileEntityHiveResin) tile;

            if (resin != null)
            {
                if (coord.isAnySurfaceNextTo(world, Blocks.fire))
                {
                    if (resin != null && resin.getBlockCovering() != null)
                    {
                        HiveHandler.instance.burntResin.add(new Pos(posX, posY, posZ).store(new BlockDataStore(resin.getBlockCovering(), (byte)0)));
                    }
                }

                if (resin.getHive() != null)
                {
                    resin.getHive().getResinInHive().remove(resin);
                }
            }
        }

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
