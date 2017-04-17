package org.avp.block;

import org.avp.entities.living.EntityOvamorph;
import org.avp.entities.living.EntityQueen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTempleSpawner extends Block
{
    public boolean creativeOnly;

    public BlockTempleSpawner(Material material, boolean creativeOnly)
    {
        super(material);
        this.creativeOnly = creativeOnly;
    }

    @Override
    public void onNeighborChange(IBlockAccess access, BlockPos pos, BlockPos neighbor)
    {
        if (access instanceof World)
        {
            World worldObj = (World) access;
            int range = 25;

            boolean isQueenNear = worldObj.getEntitiesWithinAABB(EntityQueen.class, new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(range * 2, 50.0D, range * 2)).size() >= 1;

            if (!worldObj.isRemote)
            {
                if (!(worldObj.isBlockIndirectlyGettingPowered(pos) > 0))
                {
                    worldObj.scheduleBlockUpdate(pos, this, 4, 1);
                }
                else if (worldObj.isBlockIndirectlyGettingPowered(pos) > 0 && isQueenNear || worldObj.isBlockIndirectlyGettingPowered(pos) > 0 && creativeOnly)
                {
                    EntityOvamorph entityEgg = new EntityOvamorph(worldObj);
                    entityEgg.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.0F, 0.0F);
                    worldObj.spawnEntityInWorld(entityEgg);
                }
            }
        }
    }
}
