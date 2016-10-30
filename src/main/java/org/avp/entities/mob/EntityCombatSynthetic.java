package org.avp.entities.mob;

import org.avp.AliensVsPredator;
import org.avp.EntityItemDrops;
import org.avp.Sounds;
import org.avp.entities.EntityBullet;
import org.avp.entities.EntityLiquidLatexPool;
import org.avp.entities.EntityLiquidPool;
import org.avp.util.IFacehugSelector;

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

public class EntityCombatSynthetic extends EntityCreature implements IMob, IRangedAttackMob, IFacehugSelector, IEntitySelector
{
    private EntityAIBase aiRangedAttack;
    
    public EntityCombatSynthetic(World word)
    {
        super(word);
        this.setSize(1, 2);
        this.experienceValue = 40;
        this.aiRangedAttack = new EntityAIArrowAttack(this, 0.4D, 8, 24);
        this.dataWatcher.addObject(18, new Integer(15));
        this.dataWatcher.addObject(17, "");
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
        this.tasks.addTask(1, this.aiRangedAttack);
        this.tasks.addTask(2, new EntityAIWander(this, this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
        this.tasks.addTask(3, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, Entity.class, /** targetChance **/ 0, /** shouldCheckSight **/ false, /** nearbyOnly **/ false, this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5499999761581421D);
    }
    
    @Override
    protected boolean canDespawn()
    {
        return false;
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
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    public int getTotalArmorValue()
    {
        return 5;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.getAttackTarget() != null && this.worldObj.getWorldTime() % 20 == 0 && this.aiRangedAttack.shouldExecute() && this.canEntityBeSeen(this.getAttackTarget()))
        {
            Sounds.SOUND_WEAPON_PULSERIFLE.playSound(this);
        }
    }

    @Override
    public float getBlockPathWeight(int posX, int posY, int posZ)
    {
        return 0.5F - this.worldObj.getLightBrightness(posX, posY, posZ);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase targetEntity, float damage)
    {
        if (this.getAttackTarget() != null)
        {
            EntityBullet entityBullet = new EntityBullet(this.worldObj, this, targetEntity, 10F, 0.0000001F);
            this.worldObj.spawnEntityInWorld(entityBullet);
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + this.getEyeHeight(), this.posZ, 1, 1, 1);
        }
    }

    @Override
    protected void updateAITick()
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(15));
    }

    @Override
    public boolean canFacehuggerAttach()
    {
        return false;
    }
    
    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);
        
        EntityItemDrops.AMMUNITION.tryDrop(this);

        if (!this.worldObj.isRemote)
        {
            EntityLiquidLatexPool pool = new EntityLiquidLatexPool(this.worldObj);
            pool.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(pool);
        }
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
}
