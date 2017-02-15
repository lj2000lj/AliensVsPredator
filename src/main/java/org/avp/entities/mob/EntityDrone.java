package org.avp.entities.mob;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.Sounds;
import org.avp.api.parasitoidic.IMaturable;
import org.avp.block.BlockHiveResin;
import org.avp.entities.tile.TileEntityHiveResin;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDrone extends EntityXenomorph implements IMaturable
{
    public int             mobType;
    private EntityOvamorph targetOvamorph;

    public EntityDrone(World world)
    {
        super(world);

        this.experienceValue = 100;
        this.setSize(0.8F, 1.8F);
        this.mobType = this.rand.nextInt(2);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);

        this.addStandardXenomorphAISet();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.53D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_ALIEN_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_ALIEN_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_ALIEN_DEATH.getKey();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.tickRepurposingAI();
        this.tickHiveBuildingAI();

        if (this.hive != null)
        {
            if (!this.hive.isEntityWithinRange(this))
            {
                PathEntity path = this.getNavigator().getPathToXYZ(this.hive.xCoord(), this.hive.yCoord(), this.hive.zCoord());

                if (path != null)
                {
                    this.getNavigator().setPath(path, 0.8D);
                }
            }
        }
    }

    @Override
    protected void attackEntity(Entity entity, float damage)
    {
        super.attackEntity(entity, damage);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        return super.attackEntityAsMob(entity);
    }

    public void tickRepurposingAI()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                if (this.rand.nextInt(3) == 0)
                {
                    @SuppressWarnings("unchecked")
                    ArrayList<EntityOvamorph> ovamorphs = (ArrayList<EntityOvamorph>) Entities.getEntitiesInCoordsRange(this.worldObj, EntityOvamorph.class, new Pos(this), 16);

                    if (this.getHive() != null)
                    {
                        for (EntityOvamorph ovamorph : ovamorphs)
                        {
                            if (!ovamorph.containsFacehugger)
                            {
                                targetOvamorph = ovamorph;
                                this.getNavigator().tryMoveToEntityLiving(ovamorph, this.getMoveHelper().getSpeed());
                            }
                        }
                    }
                }

                if (this.targetOvamorph != null)
                {
                    double distance = this.getDistanceSqToEntity(targetOvamorph);

                    if (distance <= 2)
                    {
                        this.setJellyLevel(this.getJellyLevel() + targetOvamorph.getJellyLevel());
                        this.targetOvamorph.setDead();
                        this.targetOvamorph = null;
                    }
                }
            }
        }
    }

    public void tickHiveBuildingAI()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.targetOvamorph == null)
            {
                if (this.getHive() != null && this.worldObj.getWorldTime() % 10 == 0 && rand.nextInt(3) == 0)
                {
                    if (this.getJellyLevel() >= 16)
                    {
                        Pos coord = findNextSuitableResinLocation(3);

                        if (coord != null)
                        {
                            Block block = coord.getBlock(this.worldObj);
                            int meta = coord.getBlockMetadata(this.worldObj);

                            if (block != null)
                            {
                                PathEntity path = this.worldObj.getEntityPathToXYZ(this, (int) coord.x, (int) coord.y, (int) coord.z, 12, true, false, true, false);

                                if (path == null)
                                {
                                    return;
                                }

                                this.getNavigator().setPath(path, 0.8D);
                                this.worldObj.setBlock((int) coord.x, (int) coord.y, (int) coord.z, AliensVsPredator.blocks().terrainHiveResin);

                                TileEntity tileEntity = coord.getTileEntity(this.worldObj);

                                if (tileEntity != null && tileEntity instanceof TileEntityHiveResin)
                                {
                                    TileEntityHiveResin resin = (TileEntityHiveResin) tileEntity;
                                    resin.setHiveSignature(this.getHive().getUniqueIdentifier());
                                    resin.setBlockCovering(block, 0);
                                    this.worldObj.setBlockMetadataWithNotify((int) coord.x, (int) coord.y, (int) coord.z, meta, 3);
                                    this.hive.addResin(resin);
                                }

                                this.setJellyLevel(this.getJellyLevel() - 16);
                            }
                        }
                    }
                }
            }
        }
    }

    public Pos findNextSuitableResinLocation(int range)
    {
        ArrayList<Pos> data = new ArrayList<Pos>();

        for (int x = (int) (posX - range); x < posX + range; x++)
        {
            for (int y = (int) (posY - range); y < posY + range; y++)
            {
                for (int z = (int) (posZ - range); z < posZ + range; z++)
                {
                    Pos location = new Pos(x, y, z);
                    Block block = location.getBlock(this.worldObj);

                    if (this.canReplaceWithResin(block))
                    {
                        Vec3 start = Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ);
                        Vec3 end = Vec3.createVectorHelper(x, y, z);
                        MovingObjectPosition hit = this.worldObj.rayTraceBlocks(start, end);

                        if (hit != null && hit.typeOfHit == MovingObjectType.BLOCK || hit == null)
                        {
                            boolean canSeeCoord = true;

                            if (hit != null)
                            {
                                canSeeCoord = hit.blockX == x && hit.blockY == y && hit.blockZ == z;
                            }

                            if (location.isAnySurfaceEmpty(this.worldObj) && canSeeCoord)
                            {
                                data.add(location);
                            }
                        }
                    }
                }
            }
        }

        return data.size() > 0 ? data.get(this.rand.nextInt(data.size())) : null;
    }

    protected boolean canReplaceWithResin(Block block)
    {
        return !(block == net.minecraft.init.Blocks.air) && !(block instanceof BlockHiveResin) && block.isOpaqueCube();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
    }

    @Override
    public Class<? extends Entity> getMatureState()
    {
        return EntityWarrior.class;
    }

    @Override
    public int getMaturityLevel()
    {
        return 1024 * 6;
    }

    @Override
    public int getMaturityTime()
    {
        return (15 * 60) * 20;
    }
}
