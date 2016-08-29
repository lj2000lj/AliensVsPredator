package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.Sounds;
import org.avp.event.HiveHandler;
import org.avp.util.XenomorphHive;

import com.arisux.amdxlib.lib.world.CoordData;
import com.arisux.amdxlib.lib.world.entity.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityQueen extends EntityXenomorph
{
    public boolean isInStasis;
    private float  ovipositorSize;

    public EntityQueen(World world)
    {
        super(world);
        this.setSize(2.0F, 4.0F);
        this.isInStasis = true;
        this.experienceValue = 40000;
        this.jumpMovementFactor = 0.1F;
        this.hurtResistantTime = 0;
        this.ignoreFrustumCheck = true;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
    }

    protected void initializeHive(HiveHandler handler)
    {
        this.hive = handler.getHiveForUUID(this.getUniqueID());

        if (this.hive == null)
        {
            handler.getHives().add(new XenomorphHive(this.worldObj, this.getUniqueID()).setLocation(this.posX, this.posY, this.posZ));
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(14, 0F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.400000238418579D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1F);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            if (this.posY < -32)
            {
                this.setDead();
            }

            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                this.initializeHive(HiveHandler.instance);
            }

            if (this.hive != null)
            {
                this.getNavigator().setPath(this.getNavigator().getPathToXYZ(this.hive.xCoord(), this.hive.yCoord(), this.hive.zCoord()), 1.55D);
            }

            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                @SuppressWarnings("unchecked")
                ArrayList<EntitySpeciesAlien> aliens = (ArrayList<EntitySpeciesAlien>) Entities.getEntitiesInCoordsRange(this.worldObj, EntitySpeciesAlien.class, new CoordData(this), 16);

                if (this.getHive() != null)
                {
                    for (EntitySpeciesAlien alien : aliens)
                    {
                        if (this.rand.nextInt(3) == 0)
                        {
                            if (alien != null && alien.getHive() != null && !(alien instanceof EntityQueen) && alien.getHive() == this.getHive())
                            {
                                if ((this.getAttackTarget() != null || this.getLastAttacker() != null))
                                {
                                    if (this.rand.nextInt(6) == 0)
                                    {
                                        if (alien instanceof EntityOvamorph)
                                        {
                                            EntityOvamorph ovamorph = (EntityOvamorph) alien;
                                            ovamorph.setHatched(true);
                                        }
                                    }

                                    EntityLivingBase target = this.getAttackTarget() != null ? this.getAttackTarget() : this.getLastAttacker();

                                    alien.setAttackTarget(target);
                                    alien.getNavigator().tryMoveToEntityLiving(target, alien.getMoveHelper().getSpeed());
                                }
                            }

                            if (alien != null && alien.getHive() == null)
                            {
                                System.out.println("Set hive signature on alien with uuid: " + alien.getUniqueID());
                                alien.setHiveSignature(this.hive.getUniqueIdentifier());
                            }
                        }
                    }
                }
                else
                {
                    System.out.println("Queen hive instance is null, this is impossible.");
                }
            }
        }

        if (isJumping)
        {
            this.addVelocity(0, 0.35D, 0);
        }

        if (this.isInStasis)
        {
            if (this.getOvipositorSize() < 1.3F)
            {
                this.setOvipositorSize(this.getOvipositorSize() + 0.0001F);
            }
            else
            {
                this.setOvipositorSize(1.3F);
            }
        }

        if (this.worldObj.getWorldTime() % 10 == 0)
        {
            if (this.getHealth() > this.getMaxHealth() - (this.getMaxHealth() / 4))
            {
                this.heal(2F);
            }

            if (this.getHealth() > 0 && this.getHealth() < this.getMaxHealth() / 4 * 3)
            {
                this.heal(2F);
            }

            if (this.getHealth() > 0 && this.getHealth() < this.getMaxHealth() / 4 * 2)
            {
                this.heal(2F);
            }
        }
    }

    @Override
    public UUID getHiveSignature()
    {
        return this.hive != null ? this.hive.getUniqueIdentifier() : this.getUniqueID();
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_QUEEN_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return this.getHealth() > this.getMaxHealth() / 4 ? Sounds.SOUND_QUEEN_LIVING.getKey() + ".constant" : Sounds.SOUND_QUEEN_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_QUEEN_DEATH.getKey();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        this.setOvipositorSize(nbt.getFloat("ovipositorSize"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("ovipositorSize", this.getOvipositorSize());
    }

    public float getOvipositorSize()
    {
        return this.worldObj.getWorldTime() % 40 == 0 ? this.ovipositorSize = this.dataWatcher.getWatchableObjectFloat(14) : this.ovipositorSize;
    }

    public void setOvipositorSize(float size)
    {
        this.dataWatcher.updateObject(14, size);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }
}
