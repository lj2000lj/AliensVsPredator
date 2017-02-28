package org.avp.block;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.packets.client.PacketRotateRotatable;
import org.avp.tile.TileEntityRedstoneFluxGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockRedstoneFluxGenerator extends Block
{
    public BlockRedstoneFluxGenerator(Material material)
    {
        super(material);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
    public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
        super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }

    @Override
    public boolean onBlockActivated(World worldObj, int xCoord, int yCoord, int zCoord, EntityPlayer player, int side, float subX, float subY, float subZ)
    {
        TileEntity te = worldObj.getTileEntity(xCoord, yCoord, zCoord);

        if (te != null && te instanceof TileEntityRedstoneFluxGenerator)
        {
            TileEntityRedstoneFluxGenerator generator = (TileEntityRedstoneFluxGenerator) te;

            ArrayList<EnumFacing> EnumFacings = new ArrayList<EnumFacing>();

            for (EnumFacing dir : EnumFacing.VALID_DIRECTIONS)
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

                if (!worldObj.isRemote)
                {
                    AliensVsPredator.network().sendToAll(new PacketRotateRotatable(generator.getDirection().ordinal(), generator.xCoord, generator.yCoord, generator.zCoord));
                }
            }

            generator.getDescriptionPacket();
        }
        return super.onBlockActivated(worldObj, xCoord, yCoord, zCoord, player, side, subX, subY, subZ);
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta)
    {
        return new TileEntityRedstoneFluxGenerator();
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }
}
