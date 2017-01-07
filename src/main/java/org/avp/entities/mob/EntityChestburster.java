package org.avp.entities.mob;

import org.avp.DamageSources;
import org.avp.Sounds;
import org.avp.api.parasitoidic.IMaturable;
import org.avp.api.parasitoidic.INascentic;
import org.avp.api.parasitoidic.IRoyalOrganism;
import org.avp.entities.extended.Organism;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityChestburster extends EntitySpeciesAlien implements IMob, INascentic
{
    private Class<? extends Entity> matureState;

    public EntityChestburster(World world)
    {
        super(world);
        this.matureState = EntityDrone.class;
        this.setSize(1.0F, 0.4F);
        this.experienceValue = 16;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityYautja.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEngineer.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityMarine.class, 16.0F, 0.23F, 0.4F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 0.800000011920929D, true));
        this.tasks.addTask(8, new EntityAIWander(this, 0.800000011920929D));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAILeapAtTarget(this, 0.8F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6499999761581421D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
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
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public boolean isReadyToMature(IRoyalOrganism jellyProducer)
    {
        IMaturable maturable = (IMaturable) this;
        IRoyalOrganism ro = (IRoyalOrganism) this;
        return this.ticksExisted >= maturable.getMaturityTime() || ro.getJellyLevel() >= maturable.getMaturityLevel();
    }

    @Override
    public void mature()
    {
        if (this.getJellyLevel() >= this.getMaturityLevel() && this.ticksExisted < this.getMaturityTime())
        {
            this.setJellyLevel(this.getJellyLevel() - this.getMaturityLevel());
        }

        EntityXenomorph matureState = (EntityXenomorph) Entities.constructEntity(this.worldObj, this.getMatureState());

        if (matureState != null)
        {
            matureState.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            this.worldObj.spawnEntityInWorld(matureState);

            for (int particleCount = 0; particleCount < 8; ++particleCount)
            {
                this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }

        }
        else if (Game.isDevEnvironment())
        {
            System.out.println("ERROR: NullPointerException during chestburster evolve state.");
            // System.out.println("INT TYPE: " + this.parasiteType);
            System.out.println("MATURE STATE: " + this.getMatureState());
            // System.out.println("EMBRYO TYPE: " + Embryo.get(this.parasiteType));
        }
        // TODO: Create a shell of the original entity.
        this.setDead();
    }

    protected Entity findPlayerToAttack(EntityPlayer entityplayer)
    {
        float brightness = this.getBrightness(1.0F);

        if (brightness < 0.5F)
        {
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, 32.0D);
        }
        else
        {
            return null;
        }
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_CHESTBURSTER_DEATH.getKey();
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_CHESTBURSTER_HURT.getKey();
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean isOnLadder()
    {
        return this.isCollidedHorizontally;
    }

    public boolean isClimbing()
    {
        return this.isOnLadder() && this.motionY > 1.0099999997764826D;
    }

    @Override
    protected void attackEntity(Entity entity, float damage)
    {
        super.attackEntity(entity, damage);
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potionEffect)
    {
        return potionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(potionEffect);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);

        String maturityState = nbt.getString("MaturityState");
        try
        {
            this.matureState = (Class<? extends Entity>) Class.forName(maturityState);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Unable to ready maturity state for chestburster: " + maturityState);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setString("MaturityState", this.matureState.getName());
    }

    @Override
    public void grow(EntityLivingBase host)
    {
        Organism hostOrganism = (Organism) host.getExtendedProperties(Organism.IDENTIFIER);
    }

    @Override
    public void vitalize(EntityLivingBase host)
    {
        Organism hostOrganism = (Organism) host.getExtendedProperties(Organism.IDENTIFIER);
        this.matureState = hostOrganism.getEmbryo().getResultingOrganism();
        this.setLocationAndAngles(host.posX, host.posY, host.posZ, 0.0F, 0.0F);
        host.worldObj.spawnEntityInWorld(this);
        hostOrganism.removeEmbryo();
        host.getActivePotionEffects().clear();
        host.attackEntityFrom(DamageSources.causeChestbursterDamage(this, host), 100000F);
    }

    @Override
    public Class<? extends Entity> getMatureState()
    {
        return this.matureState;
    }

    @Override
    public int getMaturityTime()
    {
        return (15 * 60) * 20;
    }

    @Override
    public int getMaturityLevel()
    {
        return 32;
    }
}
