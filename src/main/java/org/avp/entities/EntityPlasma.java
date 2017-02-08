package org.avp.entities;

import java.util.List;

import org.avp.DamageSources;
import org.avp.Sounds;
import org.avp.entities.fx.EntityFXElectricArc;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityPlasma extends EntityThrowable
{
    public float    syncSize;
    public boolean  synced;
    private boolean impacted;

    public EntityPlasma(World world)
    {
        super(world);
    }

    public EntityPlasma(World world, EntityLivingBase shootingEntity, float size)
    {
        super(world, shootingEntity);
        this.setSize(size, size);
        this.syncSize = size;
        this.noClip = true;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(30, 1F);
        this.dataWatcher.addObject(31, -1);
    }

    @Override
    public void onUpdate()
    {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (!this.worldObj.isRemote && !this.synced)
        {
            this.dataWatcher.updateObject(30, this.syncSize);
            this.synced = true;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.getImpactTimer() == this.getMaxImpactTimer())
            {
                this.dataWatcher.updateObject(31, this.getImpactTimer());
            }
        }

        if (this.getImpactTimer() > 0)
        {
            if (!this.worldObj.isRemote)
            {
                this.updateImpactTimer(this.getImpactTimer() - 1);
            }
        }

        if (!this.worldObj.isRemote)
        {
            if (this.getImpactTimer() == -1 && this.ticksExisted >= 20 * 20 || this.getImpactTimer() == 0)
            {
                this.setDead();
            }
        }

        MovingObjectPosition movingObjectPosition = this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY, this.posZ), Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ));

        if (!this.worldObj.isRemote)
        {
            Entity entityHit = Entities.getEntityInCoordsRange(worldObj, EntityLiving.class, new Pos(this), 1, 1);

            if (entityHit != null)
            {
                this.onImpact(movingObjectPosition);
            }
        }

        if (movingObjectPosition != null || this.isCollidedHorizontally)
        {
            this.onImpact(movingObjectPosition);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("PlasmaSize", this.getPlasmaSize());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.syncSize = nbt.getFloat("PlasmaSize");
    }

    @Override
    protected void doBlockCollisions()
    {
        super.doBlockCollisions();
    }

    @Override
    public void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            Sounds.SOUND_WEAPON_PLASMA_EXPLOSION.playSound(this, 7F, 1.0F);

            @SuppressWarnings("unchecked")
            List<Entity> entities = (List<Entity>) Entities.getEntitiesInCoordsRange(worldObj, Entity.class, new Pos(this.posX, this.posY, this.posZ), (int) Math.ceil(this.getPlasmaSize()));

            for (Entity entity : entities)
            {
                if (entity != this.getThrower())
                {
                    entity.attackEntityFrom(DamageSources.plasmacaster, 20F * this.getPlasmaSize());
                    entity.hurtResistantTime = 0;
                }
            }

            if (!this.impacted)
            {
                this.updateImpactTimer(this.getMaxImpactTimer());
                this.impacted = true;
            }

            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        }

        if (this.worldObj.isRemote)
        {
            this.specialEffect();
        }
    }

    @SideOnly(Side.CLIENT)
    private void specialEffect()
    {
        float spread = this.getPlasmaSize() * 5F;

        for (int i = (int) Math.round(spread); i > 0; i--)
        {
            double pX = this.posX + (this.rand.nextDouble() * spread) - (this.rand.nextDouble() * spread);
            double pY = this.posY + (this.rand.nextDouble() * spread) - (this.rand.nextDouble() * spread);
            double pZ = this.posZ + (this.rand.nextDouble() * spread) - (this.rand.nextDouble() * spread);

            Game.minecraft().effectRenderer.addEffect(new EntityFXElectricArc(this.worldObj, this.posX, this.posY, this.posZ, pX, pY, pZ, 10, 0xFF66AAFF));
        }
    }

    public float getPlasmaSize()
    {
        return this.dataWatcher.getWatchableObjectFloat(30);
    }

    public int getImpactTimer()
    {
        return this.dataWatcher.getWatchableObjectInt(31);
    }

    public void updateImpactTimer(int time)
    {
        this.dataWatcher.updateObject(31, time);
    }

    public int getImpactPrev()
    {
        return this.getImpactTimer() - 1;
    }

    public int getMaxImpactTimer()
    {
        return 6;
    }
}
