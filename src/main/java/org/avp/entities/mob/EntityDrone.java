package org.avp.entities.mob;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.Sounds;
import org.avp.block.BlockHiveResin;
import org.avp.entities.tile.TileEntityHiveResin;

import com.arisux.amdxlib.lib.world.CoordData;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDrone extends EntityXenomorph
{
    public int  mobType;

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

    public void tickHiveBuildingAI()
    {
        if (!this.worldObj.isRemote)
        {
            System.out.println(this.jellyLevel);
            
            if (this.getHive() != null && this.worldObj.getWorldTime() % 10 == 0 && rand.nextInt(3) == 0)
            {
                if (this.jellyLevel >= 16)
                {
                    CoordData coord = findNextSuitableResinLocation(2);

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
                                this.worldObj.setBlockMetadataWithNotify((int)coord.x, (int)coord.y, (int)coord.z, meta, 3);
                                this.hive.addResin(resin);
                            }

                            this.jellyLevel -= 16;
                        }
                    }
                }
            }
        }
    }

    public CoordData findNextSuitableResinLocation(int range)
    {
        ArrayList<CoordData> data = new ArrayList<CoordData>();

        for (int x = (int) (posX - range); x < posX + range * 2; x++)
        {
            for (int y = (int) (posY - range); y < posY + range * 2; y++)
            {
                for (int z = (int) (posZ - range); z < posZ + range * 2; z++)
                {
                    CoordData location = new CoordData(x, y, z);
                    Block block = location.getBlock(this.worldObj);

                    if (!(block == net.minecraft.init.Blocks.air) && !(block instanceof BlockHiveResin) && block.isOpaqueCube())
                    {
                        if (location.isAnySurfaceEmpty(this.worldObj) && (this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(x, y, z)) == null))
                        {
                            data.add(location);
                        }
                    }
                }
            }
        }

        return data.size() > 0 ? data.get(this.rand.nextInt(data.size())) : null;
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
}
