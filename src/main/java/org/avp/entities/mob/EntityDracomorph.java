package org.avp.entities.mob;

import org.avp.EntityItemDrops;
import org.avp.api.parasitoidic.IHost;
import org.avp.entities.EntityLiquidPool;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDracomorph extends EntitySpeciesAlien implements IMob, IHost
{
    public static final int FLYING_DATAWATCHER_ID = 19;

    IEntitySelector         mobSelector           = new IEntitySelector()
                                                  {
                                                      @Override
                                                      public boolean isEntityApplicable(Entity target)
                                                      {
                                                          if (target instanceof EntityQueen)
                                                          {
                                                              return true;
                                                          }
                                                          
                                                          if (target instanceof EntitySpeciesAlien)
                                                          {
                                                              return false;
                                                          }

                                                          if (target instanceof EntityLiquidPool)
                                                          {
                                                              return false;
                                                          }

                                                          return true;
                                                      }
                                                  };

    public EntityDracomorph(World world)
    {
        super(world);
        this.experienceValue = 150;
        this.setSize(6, 7);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityCreature.class, 1.0D, false));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCreature.class, 0, true, false, mobSelector));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, false, mobSelector));

        this.dataWatcher.addObject(FLYING_DATAWATCHER_ID, 0);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45199999761581421D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.75D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    @Override
    public boolean canParasiteAttach()
    {
        return this.canHostParasite();
    }
    
    @Override
    public boolean canHostParasite()
    {
        return false;
    }

    private ChunkCoordinates flyToPosition;

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.fallDistance = 0F;

        if (!this.worldObj.isRemote)
        {
            if (isFlying())
            {
                this.motionY *= 0.5000000238418579D;
            }

            if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ) <= 12)
            {
                this.dataWatcher.updateObject(FLYING_DATAWATCHER_ID, 0);
            }
            else if (this.getAttackTarget() == null && this.rand.nextInt(30) == 0)
            {
                this.dataWatcher.updateObject(FLYING_DATAWATCHER_ID, 1);
            }

            if (this.flyToPosition != null && (!this.worldObj.isAirBlock(this.flyToPosition.posX, this.flyToPosition.posY, this.flyToPosition.posZ) || this.flyToPosition.posY < 1))
            {
                this.flyToPosition = null;
            }

            if (this.flyToPosition == null || this.rand.nextInt(30) == 0 || this.flyToPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F)
            {
                if (this.getAttackTarget() != null)
                {
                    this.flyToPosition = new ChunkCoordinates((int) this.getAttackTarget().posX, (int) this.getAttackTarget().posY, (int) this.getAttackTarget().posZ);
                }
                else
                {
                    this.flyToPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(16) - this.rand.nextInt(16), (int) this.posY + this.rand.nextInt(14) - this.rand.nextInt(16), (int) this.posZ + this.rand.nextInt(16) - this.rand.nextInt(16));
                }
            }

            if (this.isFlying())
            {
                double d0 = (double) this.flyToPosition.posX + 0.5D - this.posX;
                double d1 = (double) this.flyToPosition.posY + 0.1D - this.posY;
                double d2 = (double) this.flyToPosition.posZ + 0.5D - this.posZ;
                this.motionX += (Math.signum(d0) * 0.65D - this.motionX) * 0.10000000149011612D;
                this.motionY += (Math.signum(d1) * 2.199999988079071D - this.motionY) * 0.10000000149011612D;
                this.motionZ += (Math.signum(d2) * 0.65D - this.motionZ) * 0.10000000149011612D;
                float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
                float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
                this.moveForward = 0.5F;
                this.rotationYaw += f1;
            }
        }
    }

    public boolean isFlying()
    {
        return this.dataWatcher.getWatchableObjectInt(FLYING_DATAWATCHER_ID) == 0 ? false : true;
    }

    @Override
    public int getTotalArmorValue()
    {
        return 6;
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    public boolean canDespawn()
    {
        return false;
    }

    @Override
    public void onDeath(DamageSource source)
    {
        super.onDeath(source);

        switch (this.rand.nextInt(4))
        {
            case 0:
                EntityItemDrops.SKULL_ENGINEER.tryDrop(this);
            case 1:
                EntityItemDrops.SKULL_SPACEJOCKEY.tryDrop(this);
            case 2:
                EntityItemDrops.SKULL_PREDATOR.tryDrop(this);
            case 3:
                EntityItemDrops.SKULL_XENO_DRONE.tryDrop(this);
            case 4:
                EntityItemDrops.SKULL_XENO_WARRIOR.tryDrop(this);
        }
    }
    
    @Override
    public void identifyHive()
    {
        ;
    }
    
    @Override
    protected void findRoyalJelly()
    {
        ;
    }
}
