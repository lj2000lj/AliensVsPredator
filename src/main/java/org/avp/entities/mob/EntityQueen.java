package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.AliensVsPredator;
import org.avp.Sounds;
import org.avp.entities.ai.alien.EntitySelectorXenomorph;
import org.avp.event.HiveHandler;
import org.avp.packets.server.PacketSpawnEntity;
import org.avp.util.XenomorphHive;

import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.world.CoordData;
import com.arisux.amdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityQueen extends EntityXenomorph implements IMob
{
    public boolean               growingOvipositor;
    public boolean               reproducing;

    private ArrayList<CoordData> pathPoints = new ArrayList<CoordData>();

    public EntityQueen(World world)
    {
        super(world);
        this.setSize(2.0F, 5.0F);
        this.growingOvipositor = true;
        this.experienceValue = 40000;
        this.jumpMovementFactor = 0.1F;
        this.hurtResistantTime = 0;
        this.ignoreFrustumCheck = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(8, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 0.8D, true));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 1.6F));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, Entity.class, 0, false, false, EntitySelectorXenomorph.instance));
        this.dataWatcher.addObject(14, 0F);
    }

    public float getOvipositorSize()
    {
        return this.dataWatcher.getWatchableObjectFloat(14);
    }

    public void setOvipositorSize(float value)
    {
        this.dataWatcher.updateObject(14, value);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
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
        this.jumpMovementFactor = 0.2F;
//        this.rotationYaw = 0;
        this.reproducing = this.getOvipositorSize() >= 1.3F;

        if (this.reproducing)
        {
            if (!this.tasks.taskEntries.isEmpty() || !this.targetTasks.taskEntries.isEmpty())
            {
                this.tasks.taskEntries.clear();
                this.targetTasks.taskEntries.clear();
            }

            if (this.worldObj.isRemote && this.worldObj.getWorldTime() % (20 * 120) == 0)
            {
                EntityOvamorph ovamorph = new EntityOvamorph(this.worldObj);

                int ovipositorDist = 10;
                double rotationYawRadians = Math.toRadians(this.rotationYawHead - 90);
                double ovamorphX = (this.posX + (ovipositorDist * (Math.cos(rotationYawRadians))));
                double ovamorphZ = (this.posZ + (ovipositorDist * (Math.sin(rotationYawRadians))));

                AliensVsPredator.network().sendToServer(new PacketSpawnEntity((int)ovamorphX, (int)this.posY, (int)ovamorphZ, Entities.getEntityRegistrationId(EntityOvamorph.class)));

                System.out.println(String.format("ovamorph laid at %s, %s, %s from queen at %s, %s, %s", (int) ovamorph.posX, (int) ovamorph.posY, (int) ovamorph.posZ, (int) this.posX, (int) this.posY, (int) this.posZ));
            }
        }

        if (!this.worldObj.isRemote)
        {
            if (this.hive != null)
            {
                this.growingOvipositor = this.hive.getResinInHive().size() > 1000;

                if (this.growingOvipositor)
                {
                    if (this.getOvipositorSize() < 1.3F)
                    {
                        this.setOvipositorSize(this.getOvipositorSize() + 0.0025F);
                    }
                    else
                    {
                        this.setOvipositorSize(1.3F);
                    }
                }
            }
            
            if (this.posY < -32)
            {
                this.setDead();
            }

            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                this.hive = HiveHandler.instance.getHiveForUUID(this.getUniqueID());

                if (this.hive == null)
                {
                    HiveHandler.instance.getHives().add(new XenomorphHive(this.worldObj, this.getUniqueID()).setLocation(this.posX, this.posY, this.posZ));
                }
            }

            if (this.hive != null)
            {
                CoordData coordQueen = new CoordData(this);
                CoordData coordHive = new CoordData(this.hive.xCoord(), this.hive.yCoord(), this.hive.zCoord());

                int pathRange = (int) (this.getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue());
                int hiveDist = (int) this.getDistance(coordHive.x, coordHive.y, coordHive.z);

                if (hiveDist > this.hive.getMaxHiveRadius() * 0.5 && this.getAttackTarget() == null || this.getAttackTarget() == null && this.getNavigator().tryMoveToXYZ(coordHive.x, coordHive.y, coordHive.z, 1.55D))
                {
                    this.pathPoints = CoordData.getPointsBetween(coordQueen, coordHive, hiveDist / 12);

                    if (this.pathPoints != null && !this.pathPoints.isEmpty())
                    {
                        CoordData closestPoint = this.pathPoints.get(0);

                        for (CoordData point : this.pathPoints)
                        {
                            if (closestPoint != null && point.distanceFrom(this) < closestPoint.getLastDistanceCalculated())
                            {
                                closestPoint = point;
                            }
                        }

                        if (!this.getNavigator().tryMoveToXYZ(closestPoint.x, closestPoint.y, closestPoint.z, 1.55D))
                        {
                            if (Game.isDevEnvironment() && this.worldObj.getWorldTime() % (20 * 3) == 0)
                            {
                                System.out.println("Unable to pathfind to closest point, too far: " + this.pathPoints.size() + " Points, " + ((int) closestPoint.distanceFrom(this)) + " Meters, " + closestPoint);
                                System.out.println(this.pathPoints);
                            }
                        }
                        else
                        {
                            if (this.getDistance(closestPoint.x, closestPoint.y, closestPoint.z) < 1.0D)
                            {
                                System.out.println("Arrived at closest point. Moving on to next point.");
                                this.pathPoints.remove(closestPoint);
                            }
                        }
                    }
                }
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
            this.addVelocity(0, 0.3D, 0);
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

        // System.out.println(this.ovipositorSize);
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return super.attackEntityFrom(source, amount);
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

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }
}
