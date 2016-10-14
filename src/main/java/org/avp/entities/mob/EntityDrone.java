package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.Sounds;
import org.avp.block.BlockHiveResin;
import org.avp.entities.tile.TileEntityHiveResin;

import com.arisux.amdxlib.lib.world.CoordData;
import com.arisux.amdxlib.lib.world.entity.Entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDrone extends EntityXenomorph
{
    public int  mobType;
    private int resinLevel;

    public EntityDrone(World world)
    {
        super(world);

        this.experienceValue = 100;
        this.setSize(0.8F, 1.8F);
        this.mobType = this.rand.nextInt(2);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
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
    protected void dropRareDrop(int par1)
    {
        if (new Random().nextInt(4) == 1)
        {
            this.entityDropItem(new ItemStack(AliensVsPredator.items().helmXeno), 1);
        }

        if (new Random().nextInt(4) == 1)
        {
            this.entityDropItem(new ItemStack(AliensVsPredator.items().plateXeno), 1);
        }

        if (new Random().nextInt(4) == 1)
        {
            this.entityDropItem(new ItemStack(AliensVsPredator.items().legsXeno), 1);
        }

        if (new Random().nextInt(4) == 1)
        {
            this.entityDropItem(new ItemStack(AliensVsPredator.items().bootsXeno), 1);
        }

        super.dropRareDrop(par1);
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

        this.tickResinLevelAI();
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

    @SuppressWarnings("unchecked")
    public void tickResinLevelAI()
    {
        this.resinLevel += 1;

        ArrayList<EntityItem> entityItemList = (ArrayList<EntityItem>) Entities.getEntitiesInCoordsRange(worldObj, EntityItem.class, new CoordData(this), 12);

        for (EntityItem entityItem : entityItemList)
        {
            if (entityItem.delayBeforeCanPickup <= 0)
            {
                ItemStack stack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);

                if (stack != null && stack.getItem() == AliensVsPredator.items().itemRoyalJelly)
                {
                    this.getNavigator().setPath(this.getNavigator().getPathToEntityLiving(entityItem), this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());

                    if (this.getDistanceToEntity(entityItem) < 1)
                    {
                        this.resinLevel += 1000;
                        entityItem.setDead();
                    }
                    break;
                }
            }
        }
    }

    public void tickHiveBuildingAI()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.getHive() != null && this.worldObj.getWorldTime() % 10 == 0 && rand.nextInt(3) == 0)
            {
                if (this.resinLevel >= 64)
                {
                    CoordData coord = findNextSuitableResinLocation(2);

                    if (coord != null)
                    {
                        Block block = coord.getBlock(this.worldObj);

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
                                resin.setBlockCovering(block);
                                this.hive.addResin(resin);
                            }

                            this.resinLevel -= 64;
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

        this.resinLevel = nbt.getInteger("resinLevel");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        nbt.setInteger("resinLevel", this.resinLevel);
    }

    public int getResinLevel()
    {
        return resinLevel;
    }
}
