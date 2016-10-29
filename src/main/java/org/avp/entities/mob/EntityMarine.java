package org.avp.entities.mob;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.avp.AliensVsPredator;
import org.avp.EntityItemDrops;
import org.avp.Sounds;
import org.avp.entities.EntityBullet;
import org.avp.entities.EntityLiquidPool;
import org.avp.util.MarineTypes;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMarine extends EntityCreature implements IMob, IRangedAttackMob, IEntitySelector
{
    private MarineTypes  marineType;
    private EntityAIBase aiRangedAttack;
    private boolean      isFiring;
    private long         lastShotFired;

    public EntityMarine(World world)
    {
        super(world);
        this.marineType = MarineTypes.getTypeForId(new Random(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())).nextInt(MarineTypes.values().length));
        this.aiRangedAttack = new EntityAIArrowAttack(this, 0.4D, (int) getMarineType().getFirearmItem().getFireRate(), 24);
        this.experienceValue = 5;
        this.dataWatcher.addObject(18, new Integer(15));
        this.dataWatcher.addObject(17, "false");
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
        this.tasks.addTask(1, this.aiRangedAttack);
        this.tasks.addTask(2, new EntityAIWander(this, this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
        this.tasks.addTask(3, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, Entity.class, /** targetChance **/
            0, /** shouldCheckSight **/
            false, /** nearbyOnly **/
            false, this));
    }

    @Override
    public boolean isEntityApplicable(Entity entity)
    {
        if (entity instanceof EntitySpeciesAlien)
            return true;

        if (entity instanceof EntityMob)
            return true;

        if (entity instanceof EntityYautja)
            return true;

        if (entity instanceof EntityGolem)
            return true;

        if (entity instanceof EntityXenomorph)
            return true;

        if (entity instanceof EntityLiquidPool)
            return false;

        if (entity instanceof EntityPlayer)
            return false;

        if (entity instanceof EntityMarine)
            return false;

        if (entity instanceof EntityCombatSynthetic)
            return false;

        return false;
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    public MarineTypes getMarineType()
    {
        return marineType;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6499999761581421D);
    }

    @Override
    public float getBlockPathWeight(int posX, int posY, int posZ)
    {
        return 0.5F - this.worldObj.getLightBrightness(posX, posY, posZ);
    }

    @Override
    public int getTotalArmorValue()
    {
        return 10;
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_MARINE_HURT.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_MARINE_DEATH.getKey();
    }

    @Override
    public ItemStack getHeldItem()
    {
        return new ItemStack(AliensVsPredator.items().itemM41A);
    }

    @Override
    protected void updateAITick()
    {
        super.updateAITick();

        this.dataWatcher.updateObject(18, Integer.valueOf(15));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.isFiring = !(System.currentTimeMillis() - getLastShotFired() >= 1000 * 3);

        if (!this.worldObj.isRemote)
        {
            this.getDataWatcher().updateObject(17, String.valueOf(this.isFiring));
        }

        if (this.worldObj.isRemote)
        {
            this.isFiring = Boolean.parseBoolean(getDataWatcher().getWatchableObjectString(17));
        }
    }
    
    @Override
    public void onDeath(DamageSource source)
    {
        super.onDeath(source);
        
        EntityItemDrops.AMMUNITION.tryDrop(this);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase targetEntity, float damage)
    {
        if (this.getAttackTarget() != null)
        {
            this.lastShotFired = System.currentTimeMillis();
            EntityBullet entityBullet = new EntityBullet(this.worldObj, this, targetEntity, 10F, 0.0000001F);
            this.worldObj.spawnEntityInWorld(entityBullet);
            this.playSound(getMarineType().getGunfireSound(), 0.7F, 1F);
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + this.getEyeHeight(), this.posZ, 1, 1, 1);
        }
    }

    public boolean isFiring()
    {
        return isFiring;
    }

    public long getLastShotFired()
    {
        return lastShotFired;
    }
}
