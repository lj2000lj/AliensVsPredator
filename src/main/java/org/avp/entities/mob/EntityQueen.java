package org.avp.entities.mob;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.AliensVsPredator;
import org.avp.Sounds;
import org.avp.entities.ai.EntityAICustomAttackOnCollide;
import org.avp.entities.ai.alien.EntitySelectorXenomorph;
import org.avp.event.HiveHandler;
import org.avp.packets.server.PacketSpawnEntity;
import org.avp.util.XenomorphHive;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityQueen extends EntityXenomorph implements IMob
{
    public static final float    OVIPOSITOR_THRESHOLD_SIZE          = 1.3F;
    public static final float    OVIPOSITOR_PROGRESSIVE_GROWTH_SIZE = 0.00225F;
    public static final int      OVIPOSITOR_UNHEALTHY_THRESHOLD     = 50;
    public static final int      OVIPOSITOR_JELLYLEVEL_THRESHOLD    = 1000;
    public static final int      OVIPOSITOR_JELLYLEVEL_GROWTH_USE   = 1;

    public boolean               growingOvipositor;
    public boolean               reproducing;

    private ArrayList<Pos> pathPoints                         = new ArrayList<Pos>();

    public EntityQueen(World world)
    {
        super(world);
        this.setSize(2.0F, 5.0F);
        this.growingOvipositor = false;
        this.experienceValue = 40000;
        this.jellyLevel = OVIPOSITOR_JELLYLEVEL_THRESHOLD;
        this.jumpMovementFactor = 0.2F;
        this.hurtResistantTime = 0;
        this.ignoreFrustumCheck = true;
        this.getNavigator().setCanSwim(true);
        this.dataWatcher.addObject(14, 0F);
        this.addStandardXenomorphAISet();
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

    private void removeAI()
    {
        if (!this.tasks.taskEntries.isEmpty() || !this.targetTasks.taskEntries.isEmpty())
        {
            this.tasks.taskEntries.clear();
            this.targetTasks.taskEntries.clear();
        }
    }

    @Override
    protected void addStandardXenomorphAISet()
    {
        if (this.tasks.taskEntries.isEmpty() && this.targetTasks.taskEntries.isEmpty())
        {
            this.tasks.addTask(0, new EntityAISwimming(this));
            this.tasks.addTask(1, new EntityAIWander(this, 0.8D));
            this.tasks.addTask(4, new EntityAICustomAttackOnCollide(this, 0.8D, true));
            this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, Entity.class, 0, false, false, EntitySelectorXenomorph.instance));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
            this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 1.6F));
        }
    }

    private void constructHive()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                this.hive = HiveHandler.instance.getHiveForUUID(this.getUniqueID());

                if (this.hive == null)
                {
                    HiveHandler.instance.getHives().add(new XenomorphHive(this.worldObj, this.getUniqueID()).setLocation(this.posX, this.posY, this.posZ));
                }
            }
        }
    }

    private void reproduce()
    {
        if (this.reproducing)
        {
            if (!this.worldObj.isRemote && this.worldObj.getWorldTime() % (20 * 120) == 0 && this.jellyLevel >= OVIPOSITOR_UNHEALTHY_THRESHOLD)
            {
                int ovipositorDist = 10;
                double rotationYawRadians = Math.toRadians(this.rotationYawHead - 90);
                double ovamorphX = (this.posX + (ovipositorDist * (Math.cos(rotationYawRadians))));
                double ovamorphZ = (this.posZ + (ovipositorDist * (Math.sin(rotationYawRadians))));

                this.worldObj.playSoundAtEntity(this, AliensVsPredator.sounds().SOUND_QUEEN_HURT.getKey(), 1F, this.rand.nextInt(10) / 100);
                AliensVsPredator.network().sendToServer(new PacketSpawnEntity(ovamorphX, this.posY, ovamorphZ, Entities.getEntityRegistrationId(EntityOvamorph.class)));
                this.jellyLevel -= 100;
            }
        }
    }

    private void handleOvipositorGrowth()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.hive != null)
            {
                boolean ovipositorHealthy = this.jellyLevel >= OVIPOSITOR_UNHEALTHY_THRESHOLD;

                if (ovipositorHealthy)
                {
                    if (this.getOvipositorSize() < OVIPOSITOR_THRESHOLD_SIZE)
                    {
                        this.setOvipositorSize(this.getOvipositorSize() + OVIPOSITOR_PROGRESSIVE_GROWTH_SIZE);
                        this.jellyLevel -= OVIPOSITOR_JELLYLEVEL_GROWTH_USE;
                    }

                    this.removeAI();
                }
                else if (!ovipositorHealthy)
                {
                    this.setOvipositorSize(0F);
                    this.addStandardXenomorphAISet();
                }
            }
        }
    }

    private void jumpBoost()
    {
        if (!this.worldObj.isRemote)
        {
            if (isJumping)
            {
                this.addVelocity(0, 0.2D, 0);
            }
        }
    }

    private void pathfindToHive()
    {
        if (this.hive != null && !this.reproducing)
        {
            Pos coordQueen = new Pos(this);
            Pos coordHive = new Pos(this.hive.xCoord(), this.hive.yCoord(), this.hive.zCoord());

            int hiveDist = (int) this.getDistance(coordHive.x, coordHive.y, coordHive.z);

            if (hiveDist > this.hive.getMaxHiveRadius() * 0.5 && this.getAttackTarget() == null || this.getAttackTarget() == null && this.getNavigator().tryMoveToXYZ(coordHive.x, coordHive.y, coordHive.z, 1.55D))
            {
                this.pathPoints = Pos.getPointsBetween(coordQueen, coordHive, hiveDist / 12);

                if (this.pathPoints != null && !this.pathPoints.isEmpty())
                {
                    Pos closestPoint = this.pathPoints.get(0);

                    for (Pos point : this.pathPoints)
                    {
                        if (closestPoint != null && point.distanceFrom(this) < closestPoint.distanceFrom(this))
                        {
                            closestPoint = point;
                        }
                    }

                    if (!this.getNavigator().tryMoveToXYZ(closestPoint.x, closestPoint.y, closestPoint.z, 1.55D))
                    {
                        if (Game.isDevEnvironment() && this.worldObj.getWorldTime() % (20 * 3) == 0)
                        {
//                            System.out.println("Unable to pathfind to closest point, too far: " + this.pathPoints.size() + " Points, " + ((int) closestPoint.distanceFrom(this)) + " Meters, " + closestPoint);
//                            System.out.println(this.pathPoints);
                        }
                    }
                    else
                    {
                        if (this.getDistance(closestPoint.x, closestPoint.y, closestPoint.z) < 1.0D)
                        {
                            if (Game.isDevEnvironment())
                            {
                                System.out.println("Arrived at closest point. Moving on to next point.");
                            }

                            this.pathPoints.remove(closestPoint);
                        }
                    }
                }
            }
        }
    }

    private void heal()
    {
        if (!this.worldObj.isRemote)
        {
            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                if (this.getHealth() > this.getMaxHealth() / 4)
                {
                    this.heal(6F);
                }
                else if (this.worldObj.getWorldTime() % (20 * 2) == 0)
                {
                    this.heal(2F);
                }
            }
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.reproducing = this.getOvipositorSize() >= 1.3F;
        this.constructHive();
        this.reproduce();
        this.handleOvipositorGrowth();
        this.jumpBoost();
        this.pathfindToHive();
        this.heal();
        
        // this.getHive().destroy();
        // this.setDead();

        if (!this.worldObj.isRemote)
        {
            if (this.posY < -32)
            {
                this.setDead();
            }

            if (this.worldObj.getWorldTime() % 20 == 0)
            {
                @SuppressWarnings("unchecked")
                ArrayList<EntitySpeciesAlien> aliens = (ArrayList<EntitySpeciesAlien>) Entities.getEntitiesInCoordsRange(this.worldObj, EntitySpeciesAlien.class, new Pos(this), 16);

                if (this.getHive() != null)
                {
                    for (EntitySpeciesAlien alien : aliens)
                    {
                        if (this.rand.nextInt(3) == 0)
                        {
                            // if (alien != null && alien.getHive() != null && !(alien instanceof EntityQueen) && alien.getHive() == this.getHive())
                            // {
                            // if ((this.getAttackTarget() != null || this.getLastAttacker() != null))
                            // {
                            // if (this.rand.nextInt(6) == 0)
                            // {
                            // if (alien instanceof EntityOvamorph)
                            // {
                            // EntityOvamorph ovamorph = (EntityOvamorph) alien;
                            // ovamorph.setHatched(true);
                            // }
                            // }
                            //
                            // EntityLivingBase target = this.getAttackTarget() != null ? this.getAttackTarget() : this.getLastAttacker();
                            //
                            // alien.setAttackTarget(target);
                            // alien.getNavigator().tryMoveToEntityLiving(target, alien.getMoveHelper().getSpeed());
                            // }
                            // }

                            if (alien != null && alien.getHive() == null)
                            {
                                alien.setHiveSignature(this.hive.getUniqueIdentifier());
                            }
                        }
                    }
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
    
    @Override
    protected boolean canDespawn()
    {
        return false;
    }
}
