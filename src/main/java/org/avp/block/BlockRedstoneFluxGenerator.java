package org.avp.block;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityRedstoneFluxGenerator;
import org.avp.packets.client.PacketRotateRotatable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

            ArrayList<ForgeDirection> forgeDirections = new ArrayList<ForgeDirection>();

            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            {
                if (dir != ForgeDirection.UP && dir != ForgeDirection.DOWN)
                {
                    forgeDirections.add(dir);
                }
            }

            if (generator.getDirection() != null)
            {
                int index = forgeDirections.indexOf(generator.getDirection());

                if (index + 1 >= forgeDirections.size())
                {
                    index = -1;
                }

                if (forgeDirections.get(index + 1) != null)
                {
                    generator.setDirection(forgeDirections.get(index + 1));
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
