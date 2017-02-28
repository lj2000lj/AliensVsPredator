package org.avp.entities.ai.pathfinding;


import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate extends net.minecraft.pathfinding.PathNavigate
{
    protected EntityLiving theEntity;
    protected World worldObj;
    protected PathEntity currentPath;
    protected double speed;
    private final IAttributeInstance pathSearchRange;
    private int totalTicks;
    private int ticksAtLastPos;
    private Vec3d lastPosCheck = new Vec3d(0, 0, 0);
    private float heightRequirement = 1.0F;
    private final PathFinder pathFinder;

    public PathNavigate(EntityLiving entityLiving, World worldIn)
    {
        super(entityLiving, worldIn);
        this.theEntity = entityLiving;
        this.worldObj = worldIn;
        this.pathSearchRange = entityLiving.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        this.pathFinder = this.getPathFinder();
    }

    protected abstract PathFinder getPathFinder();

    @Override
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    @Override
    public float getPathSearchRange()
    {
        return (float) this.pathSearchRange.getAttributeValue();
    }

    @Override
    public final PathEntity getPathToXYZ(double posX, double posY, double posZ)
    {
        return this.getPathToCoord(new Pos(MathHelper.floor_double(posX), (int) posY, MathHelper.floor_double(posZ)));
    }

    public PathEntity getPathToCoord(Pos coord)
    {
        if (!this.canNavigate())
        {
            return null;
        }
        else
        {
            float searchRange = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            int i = (int) (searchRange + 8.0F);
            Pos subCoord = coord.subtract(i, i, i);
            Pos addCoord = coord.add(i, i, i);
            ChunkCache chunkcache = new ChunkCache(this.worldObj, (int) subCoord.x, (int) subCoord.y, (int) subCoord.z, (int) addCoord.x, (int) addCoord.y, (int) addCoord.z, 0);
            PathEntity pathentity = this.pathFinder.createEntityPathTo(chunkcache, this.theEntity, coord, searchRange);
            this.worldObj.theProfiler.endSection();
            return pathentity;
        }
    }

    @Override
    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn)
    {
        PathEntity pathentity = this.getPathToXYZ((double) MathHelper.floor_double(x), (double) ((int) y), (double) MathHelper.floor_double(z));
        return this.setPath(pathentity, speedIn);
    }

    public void setHeightRequirement(float heightRequirement)
    {
        this.heightRequirement = heightRequirement;
    }

    @Override
    public PathEntity getPathToEntityLiving(Entity entityLiving)
    {
        if (!this.canNavigate())
        {
            return null;
        }
        else
        {
            float searchRange = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            Pos coord = (new Pos(this.theEntity)).add(0, 1, 0);
            int i = (int) (searchRange + 16.0F);
            Pos subCoord = coord.subtract(i, i, i);
            Pos addCoord = coord.add(i, i, i);
            ChunkCache chunkcache = new ChunkCache(this.worldObj, (int) subCoord.x, (int) subCoord.y, (int) subCoord.z, (int) addCoord.x, (int) addCoord.y, (int) addCoord.z, 0);
            PathEntity pathentity = this.pathFinder.createEntityPathTo(chunkcache, this.theEntity, entityLiving, searchRange);
            this.worldObj.theProfiler.endSection();
            return pathentity;
        }
    }

    @Override
    public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn)
    {
        PathEntity pathentity = this.getPathToEntityLiving(entityIn);
        return pathentity != null ? this.setPath(pathentity, speedIn) : false;
    }

    @Override
    public boolean setPath(PathEntity pathEntityIn, double speedIn)
    {
        if (pathEntityIn == null)
        {
            this.currentPath = null;
            return false;
        }
        else
        {
            if (!pathEntityIn.isSamePath(this.currentPath))
            {
                this.currentPath = pathEntityIn;
            }

            this.removeSunnyPath();

            if (this.currentPath.getCurrentPathLength() == 0)
            {
                return false;
            }
            else
            {
                this.speed = speedIn;
                Vec3d Vec3d = this.getEntityPosition();
                this.ticksAtLastPos = this.totalTicks;
                this.lastPosCheck = Vec3d;
                return true;
            }
        }
    }

    @Override
    public PathEntity getPath()
    {
        return this.currentPath;
    }

    @Override
    public void onUpdateNavigation()
    {
        ++this.totalTicks;

        if (!this.noPath())
        {
            Vec3d Vec3d;

            if (this.canNavigate())
            {
                this.pathFollow();
            }
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength())
            {
                Vec3d = this.getEntityPosition();
                Vec3d Vec3d1 = this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());

                if (Vec3d.yCoord > Vec3d1.yCoord && !this.theEntity.onGround && MathHelper.floor_double(Vec3d.xCoord) == MathHelper.floor_double(Vec3d1.xCoord) && MathHelper.floor_double(Vec3d.zCoord) == MathHelper.floor_double(Vec3d1.zCoord))
                {
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                }
            }

            if (!this.noPath())
            {
                Vec3d = this.currentPath.getPosition(this.theEntity);

                if (Vec3d != null)
                {
                    this.theEntity.getMoveHelper().setMoveTo(Vec3d.xCoord, Vec3d.yCoord, Vec3d.zCoord, this.speed);
                }
            }
        }
    }

    protected void pathFollow()
    {
        Vec3d Vec3d = this.getEntityPosition();
        int i = this.currentPath.getCurrentPathLength();

        for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j)
        {
            if (this.currentPath.getPathPointFromIndex(j).yCoord != (int) Vec3d.yCoord)
            {
                i = j;
                break;
            }
        }

        float f = this.theEntity.width * this.theEntity.width * this.heightRequirement;
        int k;

        for (k = this.currentPath.getCurrentPathIndex(); k < i; ++k)
        {
            Vec3d Vec3d1 = this.currentPath.getVectorFromIndex(this.theEntity, k);

            if (Vec3d.squareDistanceTo(Vec3d1) < (double) f)
            {
                this.currentPath.setCurrentPathIndex(k + 1);
            }
        }

        k = MathHelper.ceiling_float_int(this.theEntity.width);
        int j1 = (int) this.theEntity.height + 1;
        int l = k;

        for (int i1 = i - 1; i1 >= this.currentPath.getCurrentPathIndex(); --i1)
        {
            if (this.isDirectPathBetweenPoints(Vec3d, this.currentPath.getVectorFromIndex(this.theEntity, i1), k, j1, l))
            {
                this.currentPath.setCurrentPathIndex(i1);
                break;
            }
        }

        this.checkForStuck(Vec3d);
    }

    protected void checkForStuck(Vec3d Vec3d)
    {
        if (this.totalTicks - this.ticksAtLastPos > 100)
        {
            if (Vec3d.squareDistanceTo(this.lastPosCheck) < 2.25D)
            {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = Vec3d;
        }
    }

    @Override
    public boolean noPath()
    {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    @Override
    public void clearPathEntity()
    {
        this.currentPath = null;
    }

    protected abstract Vec3d getEntityPosition();

    protected abstract boolean canNavigate();

    protected boolean isInLiquid()
    {
        return Entities.isInWater(this.theEntity) || Entities.isInLava(this.theEntity);
    }

    protected void removeSunnyPath()
    {
    }

    protected abstract boolean isDirectPathBetweenPoints(Vec3d posVec3d1, Vec3d posVec3d2, int sizeX, int sizeY, int sizeZ);
}
