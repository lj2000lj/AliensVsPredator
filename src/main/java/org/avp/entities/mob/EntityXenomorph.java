package org.avp.entities.mob;

import org.avp.EntityItemDrops;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityXenomorph extends EntitySpeciesAlien implements IMob
{
    private static final int   JAW_PROGRESS_DATAWATCHER_ID   = 21;
    private static final float JAW_PROGRESS_INCR             = 0.3F;
    public static final float  JAW_PROGRESS_MAX              = 1.0F;

    private static final int   MOUTH_PROGRESS_DATAWATCHER_ID = 22;
    private static final float MOUTH_PROGRESS_INCR           = 0.175F;
    public static final float  MOUTH_PROGRESS_MAX            = 1.0F;

    private boolean            startBite                     = false;
    private boolean            retractMouth                  = false;
    protected boolean          ableToClimb;
    protected boolean          isDependant;

    public EntityXenomorph(World world)
    {
        super(world);
        this.jumpMovementFactor = 0.045F;
        this.ableToClimb = false;
        this.isDependant = true;
        this.getNavigator().setCanSwim(true);
        // this.tasks.addTask(0, new EntityAISwimming(this));
        // this.tasks.addTask(1, new EntityAIClimb(this, 0.03F));
        // this.tasks.addTask(8, new EntityAIWander(this, 0.8D));
        // this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.6F));
        // this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        // this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, Entity.class, 1000, true, false, EntitySelectorXenomorph.instance));

        this.dataWatcher.addObject(20, 0 /** Crawling **/
        );
        this.dataWatcher.addObject(JAW_PROGRESS_DATAWATCHER_ID, 0F /** Outer Jaw Progress **/
        );
        this.dataWatcher.addObject(MOUTH_PROGRESS_DATAWATCHER_ID, 0F /** Inner Jaw Progress **/
        );
    }

    public boolean isCrawling()
    {
        return this.dataWatcher.getWatchableObjectInt(20) == 1 ? true : false;
    }

    public boolean isStanding()
    {
        return this.dataWatcher.getWatchableObjectInt(20) == 0 ? true : false;
    }

    protected void setCrawling()
    {
        this.dataWatcher.updateObject(20, 1);
    }

    protected void setStanding()
    {
        this.dataWatcher.updateObject(20, 1);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }
    
    public float getOuterJawProgress()
    {
        return this.dataWatcher.getWatchableObjectFloat(JAW_PROGRESS_DATAWATCHER_ID);
    }

    public float getInnerJawProgress()
    {
        return this.dataWatcher.getWatchableObjectFloat(MOUTH_PROGRESS_DATAWATCHER_ID);
    }

    protected void decreaseOuterJawProgress()
    {
        float p = this.getOuterJawProgress();

        if (p > 0F)
        {
            this.dataWatcher.updateObject(JAW_PROGRESS_DATAWATCHER_ID, p - JAW_PROGRESS_INCR);
        }
    }

    protected void increaseOuterJawProgress()
    {
        float p = this.getOuterJawProgress();

        if (p < JAW_PROGRESS_MAX)
        {
            this.dataWatcher.updateObject(JAW_PROGRESS_DATAWATCHER_ID, p + JAW_PROGRESS_INCR);
        }
    }

    protected void decreaseInnerJawProgress()
    {
        float p = this.getInnerJawProgress();

        if (p > 0F)
        {
            this.dataWatcher.updateObject(MOUTH_PROGRESS_DATAWATCHER_ID, p - MOUTH_PROGRESS_INCR);
        }
    }

    protected void increaseInnerJawProgress()
    {
        float p = this.getInnerJawProgress();

        if (p < MOUTH_PROGRESS_MAX)
        {
            this.dataWatcher.updateObject(MOUTH_PROGRESS_DATAWATCHER_ID, p + MOUTH_PROGRESS_INCR);
        }
    }

    @Deprecated
    protected void resetInnerJawProgress()
    {
        this.dataWatcher.updateObject(MOUTH_PROGRESS_DATAWATCHER_ID, MOUTH_PROGRESS_MAX);
    }

    public void bite()
    {
        this.startBite = true;
    }

    protected void updateJaws()
    {
        if (!this.worldObj.isRemote)
        {
            float outerJawProgress = this.getOuterJawProgress();
            float innerJawProgress = this.getInnerJawProgress();

            if (startBite && outerJawProgress <= JAW_PROGRESS_MAX)
            {
                this.increaseOuterJawProgress();
            }

            if (outerJawProgress >= JAW_PROGRESS_MAX || !startBite)
            {
                this.startBite = false;

                if (outerJawProgress >= JAW_PROGRESS_MAX && innerJawProgress < MOUTH_PROGRESS_MAX && !retractMouth)
                {
                    this.increaseInnerJawProgress();

                    if (innerJawProgress + MOUTH_PROGRESS_INCR >= MOUTH_PROGRESS_MAX)
                    {
                        this.retractMouth = true;
                    }
                }

                if (innerJawProgress > 0)
                {
                    if (innerJawProgress >= MOUTH_PROGRESS_MAX || retractMouth)
                    {
                        this.decreaseInnerJawProgress();

                        if (this.getInnerJawProgress() <= 0)
                        {
                            this.retractMouth = false;
                        }
                    }
                }

                if (this.getInnerJawProgress() < 0.0F)
                {
                    this.decreaseOuterJawProgress();
                }
            }
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        /** Fall Damage Negation **/
        this.fallDistance = 0F;

        this.updateJaws();
        this.shareJelly();
    }

    private void shareJelly()
    {
        if (this.hive != null && !this.worldObj.isRemote)
        {
            if (this.hive.getQueen() != null && !this.hive.getQueen().isDead && !(this instanceof EntityQueen))
            {
                if (this.hive.getQueen().getOvipositorSize() < EntityQueen.OVIPOSITOR_THRESHOLD_SIZE || this.hive.getQueen().reproducing)
                {
                    if (this.hive.getQueen().getJellyLevel() < EntityQueen.OVIPOSITOR_JELLYLEVEL_THRESHOLD * 2)
                    {
                        this.hive.getQueen().jellyLevel++;
                        this.jellyLevel--;
                    }
                }
            }
            else
            {
                this.hive = null;
            }
        }
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        EntityItemDrops.XENO_FEET.tryDrop(this);
        EntityItemDrops.XENO_HELM.tryDrop(this);
        EntityItemDrops.XENO_LEGS.tryDrop(this);
        EntityItemDrops.XENO_TORSO.tryDrop(this);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        this.bite();
        return super.attackEntityAsMob(entity);
    }

    public boolean isDependantOnHive()
    {
        return this.isDependant;
    }

    public boolean isAbleToClimb()
    {
        return this.ableToClimb;
    }

}
