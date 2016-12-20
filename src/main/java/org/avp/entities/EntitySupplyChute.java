package org.avp.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.avp.entities.tile.TileEntitySupplyCrate;
import org.avp.items.ItemSupplyChute.SupplyChuteType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySupplyChute extends Entity
{
    public int            metadata;
    public int            fallTime;
    public boolean        shouldDropItem;
    private boolean       hurtEntities;
    private int           fallHurtMax;
    private float         fallHurtAmount;
    public NBTTagCompound tileEntityData;

    public EntitySupplyChute(World world)
    {
        super(world);
        this.shouldDropItem = true;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
    }
    
    public EntitySupplyChute(World world, double posX, double posY, double posZ)
    {
        this(world, posX, posY, posZ, 0);
    }

    public EntitySupplyChute(World world, double posX, double posY, double posZ, int meta)
    {
        super(world);
        this.shouldDropItem = true;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
        this.metadata = meta;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(posX, posY, posZ);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
    }

    protected void entityInit()
    {
        ;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    protected void fall(float distance)
    {
        if (this.hurtEntities)
        {
            int i = MathHelper.ceiling_float_int(distance - 1.0F);

            if (i > 0)
            {
                ArrayList arraylist = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
                DamageSource damagesource = DamageSource.fallingBlock;
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext())
                {
                    Entity entity = (Entity) iterator.next();
                    entity.attackEntityFrom(damagesource, (float) Math.min(MathHelper.floor_float((float) i * this.fallHurtAmount), this.fallHurtMax));
                }
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setByte("Data", (byte) this.metadata);
        tagCompound.setByte("Time", (byte) this.fallTime);
        tagCompound.setBoolean("DropItem", this.shouldDropItem);
        tagCompound.setBoolean("HurtEntities", this.hurtEntities);
        tagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
        tagCompound.setInteger("FallHurtMax", this.fallHurtMax);

        if (this.tileEntityData != null)
        {
            tagCompound.setTag("TileEntityData", this.tileEntityData);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        this.metadata = tagCompund.getByte("Data") & 255;
        this.fallTime = tagCompund.getByte("Time") & 255;

        if (tagCompund.hasKey("HurtEntities", 99))
        {
            this.hurtEntities = tagCompund.getBoolean("HurtEntities");
            this.fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
            this.fallHurtMax = tagCompund.getInteger("FallHurtMax");
        }

        if (tagCompund.hasKey("DropItem", 99))
        {
            this.shouldDropItem = tagCompund.getBoolean("DropItem");
        }

        if (tagCompund.hasKey("TileEntityData", 10))
        {
            this.tileEntityData = tagCompund.getCompoundTag("TileEntityData");
        }
    }

    public void setHurtEntities(boolean hurtEntities)
    {
        this.hurtEntities = hurtEntities;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    public World getWorldObj()
    {
        return this.worldObj;
    }

    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

    public Block getBlock()
    {
        return this.getType().getBlock();
    }

    @Override
    public void onUpdate()
    {
        if (this.getBlock() == null || this.getBlock().getMaterial() == Material.air)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY -= 0.03999999910593033D;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;

            if (!this.worldObj.isRemote)
            {
                int x = MathHelper.floor_double(this.posX);
                int y = MathHelper.floor_double(this.posY);
                int z = MathHelper.floor_double(this.posZ);

                if (this.fallTime == 1)
                {
                    if (this.worldObj.getBlock(x, y, z) != this.getBlock())
                    {
                        this.setDead();
                        return;
                    }

                    this.worldObj.setBlockToAir(x, y, z);
                }

                if (this.onGround)
                {
                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= -0.5D;

                    if (this.worldObj.getBlock(x, y, z) != Blocks.piston_extension)
                    {
                        this.setDead();

                        if (this.worldObj.canPlaceEntityOnSide(this.getBlock(), x, y, z, true, 1, (Entity) null, (ItemStack) null) && !BlockFalling.canFallBelow(this.worldObj, x, y - 1, z) && this.worldObj.setBlock(x, y, z, this.getBlock(), this.metadata, 3))
                        {
                            if (this.getBlock() instanceof BlockFalling)
                            {
                                ((BlockFalling) this.getBlock()).playSoundWhenFallen(this.worldObj, x, y, z, this.metadata);
                            }

                            if (this.tileEntityData != null && this.getBlock() instanceof ITileEntityProvider)
                            {
                                TileEntitySupplyCrate crate = (TileEntitySupplyCrate) this.worldObj.getTileEntity(x, y, z);

                                if (crate != null)
                                {
                                    crate.setType(this.getType());

                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    crate.writeToNBT(nbttagcompound);
                                    Iterator iterator = this.tileEntityData.getKeySet().iterator();

                                    while (iterator.hasNext())
                                    {
                                        String s = (String) iterator.next();
                                        NBTBase nbtbase = this.tileEntityData.getTag(s);

                                        if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                                        {
                                            nbttagcompound.setTag(s, nbtbase.copy());
                                        }
                                    }

                                    crate.readFromNBT(nbttagcompound);
                                    crate.markDirty();
                                }
                            }
                        }
                        else if (this.shouldDropItem)
                        {
                            this.entityDropItem(new ItemStack(this.getBlock(), 1, this.getBlock().damageDropped(this.metadata)), 0.0F);
                        }
                    }
                }
                else if (this.fallTime > 100 && !this.worldObj.isRemote && (y < 1 || y > 256) || this.fallTime > 600)
                {
                    if (this.shouldDropItem)
                    {
                        this.entityDropItem(new ItemStack(this.getBlock(), 1, this.getBlock().damageDropped(this.metadata)), 0.0F);
                    }

                    this.setDead();
                }
            }
        }
    }

    public SupplyChuteType getType()
    {
        return SupplyChuteType.get(this.getClass());
    }
}
