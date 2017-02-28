package org.avp.entities;

import java.util.List;

import org.avp.DamageSources;
import org.avp.entities.living.EntityMarine;

import com.arisux.mdxlib.lib.game.GameSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBullet extends Entity
{
    private int     xTile;
    private int     yTile;
    private int     zTile;
    private Block   inTile;
    private int     inData;
    private int     arrowShake;
    private int     ticksInGround;
    private int     ticksInAir;
    private boolean inGround;
    private boolean doesArrowBelongToPlayer;
    private boolean arrowCritical;
    private boolean physics;
    public Entity   shootingEntity;
    public double   damage;

    public EntityBullet(World world)
    {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = Blocks.AIR;
        this.inData = 0;
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.arrowCritical = false;
        this.setSize(0.5F, 0.5F);
    }

    public EntityBullet(World world, double x, double y, double z)
    {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = Blocks.AIR;
        this.inData = 0;
        this.inGround = false;
        this.doesArrowBelongToPlayer = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.arrowCritical = true;
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
        this.yOffset = 0.0F;
    }

    public EntityBullet(World world, Object source, float velocity, double damage)
    {
        super(world);
        this.damage = damage;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = Blocks.AIR;
        this.inData = 0;
        this.inGround = false;
        this.physics = true;
        this.doesArrowBelongToPlayer = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.arrowCritical = true;
        this.setSize(0.5F, 0.5F);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5F, 1.0F);
        
        if (source instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) source;
            this.shootingEntity = living;
            this.setLocationAndAngles(living.posX, living.posY + living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);

            if (source instanceof EntityPlayer)
            {
                this.doesArrowBelongToPlayer = living instanceof EntityPlayer;
            }
        }
    }

    public EntityBullet(World world, Object source, Entity targetEntity, float velocity, double damage)
    {
        super(world);
        this.damage = damage;
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = Blocks.AIR;
        this.inData = 0;
        this.inGround = false;
        this.physics = true;
        this.doesArrowBelongToPlayer = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.arrowCritical = true;
        this.setSize(0.5F, 0.5F);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        
        double srcX = 0;
        double srcZ = 0;
        
        if (source instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase) source;
            this.shootingEntity = living;
            
            this.setLocationAndAngles(living.posX, living.posY + living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
            srcX = living.posX;
            srcZ = living.posZ;

            if (source instanceof EntityPlayer)
            {
                this.doesArrowBelongToPlayer = living instanceof EntityPlayer;
            }
        }

        double x = targetEntity.posX - srcX;
        double y = targetEntity.boundingBox.maxY - this.posY;
        double z = targetEntity.posZ - srcZ;
        double v = MathHelper.sqrt_double(x * x + z * z);

        if (v >= 1.0E-7D)
        {
            float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
            float pitch = (float) (-(Math.atan2(y, v) * 180.0D / Math.PI));
            double xOffset = x / v;
            double zOffset = z / v;
            this.setLocationAndAngles(srcX + xOffset, this.posY, srcZ + zOffset, yaw, pitch);
            this.yOffset = 0.0F;
            this.setThrowableHeading(x, y, z, velocity, damage);
        }
    }

    @Override
    protected void entityInit()
    {
        ;
    }

    public void setThrowableHeading(double posX, double posY, double posZ, float velocity, double damage)
    {
        float v = MathHelper.sqrt_double(posX * posX + posY * posY + posZ * posZ);
        posX /= v;
        posY /= v;
        posZ /= v;
        posX += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * damage;
        posY += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * damage;
        posZ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * damage;
        posX *= velocity;
        posY *= velocity;
        posZ *= velocity;
        this.motionX = posX;
        this.motionY = posY;
        this.motionZ = posZ;
        float v2 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(posX, posZ) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(posY, v2) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    @Override
    public void setVelocity(double motionX, double motionY, double motionZ)
    {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float velocity = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(motionY, velocity) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdate()
    {
        if (this.ticksInAir > 160 || this.ticksInGround > 20)
        {
            this.setDead();
        }

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float velocity = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, velocity) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block != Blocks.AIR)
        {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB Vec3dd = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (Vec3dd != null && Vec3dd.isVecInside(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.isInWater())
        {
            this.setDead();
        }

        if (this.inGround)
        {
            int meta = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

            if (block == Blocks.glass_pane)
            {
                this.worldObj.setBlockToAir(this.xTile, this.yTile, this.zTile);
                GameSounds.fxMinecraftGlassShatter3.playSound(this.worldObj, this.xTile, this.yTile, this.zTile);
                GameSounds.fxMinecraftGlassShatter1.playSound(this.shootingEntity);
            }

            if (block == this.inTile && meta == this.inData)
            {
                ++this.ticksInGround;

                if (!this.isInWater())
                {
                    this.setDead();
                }

                if (this.ticksInGround > 20)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;
            Vec3d vecAt = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vecNext = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vecAt, vecNext, false, true, false);
            vecAt = new Vec3d(this.posX, this.posY, this.posZ);
            vecNext = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vecNext = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            float radius;

            for (int index = 0; index < list.size(); ++index)
            {
                Entity e = list.get(index);

                if (e.canBeCollidedWith() && (e != this.shootingEntity || this.ticksInAir >= 5))
                {
                    radius = 0.3F;
                    AxisAlignedBB bounds = e.boundingBox.expand(radius, radius, radius);
                    MovingObjectPosition intercept = bounds.calculateIntercept(vecAt, vecNext);

                    if (intercept != null)
                    {
                        double d1 = vecAt.distanceTo(intercept.hitVec);

                        if (d1 < d || d == 0.0D)
                        {
                            entity = e;
                            d = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            float velocity;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    if (this.shootingEntity instanceof EntityTurret)
                    {
                        EntityTurret entityTurret = (EntityTurret) this.shootingEntity;

                        if (!entityTurret.getTileEntity().canTargetType(movingobjectposition.entityHit.getClass()))
                        {
                            entityTurret.getTileEntity().setTargetEntity(movingobjectposition.entityHit);
                        }
                    }

                    if (this.shootingEntity instanceof EntityMarine && (movingobjectposition.entityHit instanceof EntityMarine))
                    {
                        this.setDead();
                        return;
                    }

                    velocity = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int attackDamage = (int) Math.ceil(velocity * damage);

                    if (this.arrowCritical)
                    {
                        attackDamage += this.rand.nextInt(attackDamage / 2 + 2);
                    }

                    DamageSource damagesource = this.shootingEntity == null ? DamageSources.causeBulletDamage(this) : DamageSources.bullet;
                    movingobjectposition.entityHit.hurtResistantTime = 0;

                    if (movingobjectposition.entityHit instanceof EntityLivingBase)
                    {
                        ((EntityLivingBase) movingobjectposition.entityHit).getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9);
                        movingobjectposition.entityHit.attackEntityFrom(damagesource, attackDamage);
                        this.setDead();
                    }
                }
                else
                {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = ((float) (movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = ((float) (movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
                    velocity = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / velocity * 0.05000000074505806D;
                    this.posY -= this.motionY / velocity * 0.05000000074505806D;
                    this.posZ -= this.motionZ / velocity * 0.05000000074505806D;
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.arrowCritical = false;
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            velocity = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY, velocity) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float newVelocity = 0.99F;
            radius = 0.05F;

            if (!this.isDead)
            {
                for (int particles = 0; particles < 5; ++particles)
                {
                    this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
                }

                newVelocity = 0.8F;
            }

            if (this.isInWater())
            {
                for (int particles = 0; particles < 5; ++particles)
                {
                    this.worldObj.spawnParticle("bubble", this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
                }

                newVelocity = 0.8F;
            }

            if (this.physics)
            {
                this.motionX *= newVelocity;
                this.motionY *= newVelocity;
                this.motionZ *= newVelocity;
                this.motionY -= 0.05F;
            }
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    public void setPhysics(boolean physics)
    {
        this.physics = physics;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        var1.setShort("xTile", (short) this.xTile);
        var1.setShort("yTile", (short) this.yTile);
        var1.setShort("zTile", (short) this.zTile);
        var1.setByte("inData", (byte) this.inData);
        var1.setByte("shake", (byte) this.arrowShake);
        var1.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        var1.setBoolean("player", this.doesArrowBelongToPlayer);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        this.xTile = var1.getShort("xTile");
        this.yTile = var1.getShort("yTile");
        this.zTile = var1.getShort("zTile");
        this.inData = var1.getByte("inData") & 255;
        this.arrowShake = var1.getByte("shake") & 255;
        this.inGround = var1.getByte("inGround") == 1;
        this.doesArrowBelongToPlayer = var1.getBoolean("player");
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if (!this.doesArrowBelongToPlayer)
        {
            this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            this.setDead();
        }
    }

    @Override
    public float getShadowSize()
    {
        return 0.0F;
    }
}
