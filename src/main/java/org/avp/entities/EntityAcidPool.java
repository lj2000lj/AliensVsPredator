package org.avp.entities;

import org.avp.DamageSources;
import org.avp.entities.ai.EntityAIMeltBlock;
import org.avp.entities.living.EntitySpeciesAlien;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityAcidPool extends EntityLiquidPool implements IMob, IEntitySelector
{
    public EntityAcidPool(World world)
    {
        super(world);
        this.isImmuneToFire = false;
        this.ignoreFrustumCheck = true;
        this.setSize(0.08F, 0.08F);
        this.tasks.addTask(0, new EntityAIMeltBlock(this, -1));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, Entity.class, /** targetChance **/
            0, /** shouldCheckSight **/
            false, /** nearbyOnly **/
            false, this));
    }

    @Override
    public boolean isEntityApplicable(Entity entity)
    {
        if (entity instanceof EntityLiquidPool)
        {
            return false;
        }
        
        if (entity instanceof EntitySpeciesAlien)
        {
            return false;
        }

        return true;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0D);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean isInRangeToRenderDist(double range)
    {
        return true;
    }

    public float getAcidIntensity()
    {
        return 1F - (1F / this.getLifetime() / (1F / this.ticksExisted));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            double range = 1.2;
            EntityLivingBase target = (EntityLivingBase) (this.worldObj.findNearestEntityWithinAABB(EntityLivingBase.class, this.boundingBox.expand(range, 0.1D, range), this));

            if (target != null && isEntityApplicable(target))
            {
                this.setAttackTarget(target);
                target.attackEntityFrom(DamageSources.acid, 4F);
            }
        }

        if (worldObj.isRemote && worldObj.getWorldTime() % 4 <= 0)
        {
            this.worldObj.spawnParticle("smoke", this.posX + this.rand.nextDouble(), this.posY + this.rand.nextDouble(), this.posZ + this.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if (!this.worldObj.isRemote)
        {
            if (!player.capabilities.isCreativeMode)
            {
                player.addPotionEffect(new PotionEffect(Potion.poison.id, (14 * 20), 0));
            }
        }
    }

    @Override
    protected void attackEntity(Entity entity, float damage)
    {
        super.attackEntity(entity, damage);
    }

    public int getLifetime()
    {
        return lifetime;
    }
}
