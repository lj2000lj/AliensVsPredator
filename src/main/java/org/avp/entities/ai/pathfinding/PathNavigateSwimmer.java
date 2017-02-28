package org.avp.entities.ai.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigateSwimmer extends PathNavigate
{
    public PathNavigateSwimmer(EntityLiving entityLiving, World worldIn)
    {
        super(entityLiving, worldIn);
    }

    @Override
    protected PathFinder getPathFinder()
    {
        return new PathFinder(new SwimNodeProcessor());
    }

    @Override
    protected boolean canNavigate()
    {
        return this.isInLiquid();
    }

    @Override
    protected Vec3d getEntityPosition()
    {
        return new Vec3d(this.theEntity.posX, this.theEntity.posY + (double) this.theEntity.height * 0.5D, this.theEntity.posZ);
    }

    @Override
    protected void pathFollow()
    {
        Vec3d entityPos = this.getEntityPosition();
        float widthSquared = this.theEntity.width * this.theEntity.width;

        if (entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < (double) widthSquared)
        {
            this.currentPath.incrementPathIndex();
        }

        for (int i = Math.min(this.currentPath.getCurrentPathIndex() + 6, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath.getCurrentPathIndex(); --i)
        {
            Vec3d currentPathVec = this.currentPath.getVectorFromIndex(this.theEntity, i);

            if (currentPathVec.squareDistanceTo(entityPos) <= 36.0D && this.isDirectPathBetweenPoints(entityPos, currentPathVec, 0, 0, 0))
            {
                this.currentPath.setCurrentPathIndex(i);
                break;
            }
        }

        this.checkForStuck(entityPos);
    }

    @Override
    protected void removeSunnyPath()
    {
        super.removeSunnyPath();
    }

    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec3d1, Vec3d posVec3d2, int sizeX, int sizeY, int sizeZ)
    {
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(posVec3d1, new Vec3d(posVec3d2.xCoord, posVec3d2.yCoord + (double) this.theEntity.height * 0.5D, posVec3d2.zCoord), false);
        return movingobjectposition == null || movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
    }
}
