package org.avp.entities.mob;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.EntityItemDrops;
import org.avp.packets.client.PacketOvamorphContainsFacehugger;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOvamorph extends EntitySpeciesAlien implements IMob
{
    protected int hatchingTime;
    protected boolean hasHatched;
    protected boolean acceleratedHatching;
    protected int openProgress;
    protected int hatchWaitTimer;
    protected final int maxOpenProgress = 21;
    protected boolean containsFacehugger;
    protected boolean sendUpdates;

    public EntityOvamorph(World par1World)
    {
        super(par1World);
        this.setSize(1F, 1F);
        this.hatchingTime = 20 * 30 + (10 * rand.nextInt(24));
        this.experienceValue = 10;
        this.openProgress = -maxOpenProgress;
        this.hatchWaitTimer = 20 * 3 + (20 * rand.nextInt(5));
        this.containsFacehugger = true;
        this.sendUpdates = true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        this.containsFacehugger = nbt.getBoolean("containsFacehugger");
        this.openProgress = nbt.getInteger("openProgress");
        this.sendUpdates = true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);

        nbt.setBoolean("containsFacehugger", this.containsFacehugger);
        nbt.setInteger("openProgress", this.openProgress);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    
    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);
        
        EntityItemDrops.ROYAL_JELLY_GENERIC.tryDrop(this);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote && this.ticksExisted >= 20 && this.sendUpdates)
        {
            AliensVsPredator.network().sendToAll(new PacketOvamorphContainsFacehugger(this.containsFacehugger, this.getEntityId()));
            this.sendUpdates = false;
        }

        if (this.getHealth() < this.getMaxHealth())
        {
            this.acceleratedHatching = true;
        }

        if (!this.containsFacehugger)
        {
            this.openProgress = this.getMaxOpenProgress();
        }

        if (this.containsFacehugger)
        {
            int x = MathHelper.floor_double(this.posX);
            int y = MathHelper.floor_double(this.posY);
            int z = MathHelper.floor_double(this.posZ);

            if (this.worldObj.getBlock(x, y, z).getMaterial() != AliensVsPredator.materials().mist || this.acceleratedHatching)
            {
                int hatchAcceleration = this.acceleratedHatching ? 20 : 1;
                ArrayList<Entity> potentialHosts = (ArrayList<Entity>) Entities.getEntitiesInCoordsRange(this.worldObj, EntityLivingBase.class, new Pos(this), 8);

                for (Entity entity : new ArrayList<Entity>(potentialHosts))
                {
                    if (!EntityParasitoid.parasiteSelector.isEntityApplicable(entity))
                    {
                        potentialHosts.remove(entity);
                    }
                }

                if (this.hasHatched || potentialHosts.size() > 0)
                {
                    this.hasHatched = true;
                    
                    if (this.acceleratedHatching || this.hatchingTime <= 0)
                    {
                        this.openProgress = this.openProgress < (maxOpenProgress) ? openProgress + 1 : this.openProgress;
                    }

                    if ((this.hatchingTime -= hatchAcceleration) <= 1 || this.hasHatched)
                    {
                        if (this.hatchWaitTimer-- <= 0)
                        {
                            this.hatch();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);
    }

    @Override
    protected void damageEntity(DamageSource source, float amount)
    {
        super.damageEntity(source, amount);
        
        EntityItemDrops.ROYAL_JELLY_SINGLE.tryDrop(this);
    }

    protected void hatch()
    {
        if (!this.worldObj.isRemote)
        {
            EntityFacehugger facehugger = new EntityFacehugger(this.worldObj);
            Pos pos = new Pos(this).findSafePosAround(this.worldObj);

            facehugger.setLocationAndAngles(pos.x, pos.y, pos.z, 0F, 0F);
            worldObj.spawnEntityInWorld(facehugger);
            facehugger.motionY = 0.75F;

            this.setContainsFacehugger(false);
        }
    }

    public void setHatched(boolean hasHatched)
    {
        this.hasHatched = hasHatched;
    }

    public int getOpenProgress()
    {
        return this.openProgress;
    }

    public int getMaxOpenProgress()
    {
        return maxOpenProgress;
    }

    public boolean hasFacehugger()
    {
        return containsFacehugger;
    }

    public void setContainsFacehugger(boolean containsFacehugger)
    {
        this.containsFacehugger = containsFacehugger;
    }
}
