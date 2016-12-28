package org.avp.entities.mob;

import java.util.ArrayList;

import org.avp.Sounds;
import org.avp.api.parasitoidic.IMaturable;
import org.avp.entities.EntityAcidPool;

import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.block.Blocks;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SuppressWarnings("all")
public class EntityAqua extends EntityXenomorph
{
    public EntityAqua(World world)
    {
        super(world);
        this.jumpMovementFactor = 0.2F;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(false);
        this.experienceValue = 100;
        this.setSize(0.8F, 1.8F);
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        
        this.addStandardXenomorphAISet();
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    public static IEntitySelector entitySelector = new IEntitySelector()
    {
        @Override
        public boolean isEntityApplicable(Entity entity)
        {
            return !(entity instanceof EntitySpeciesAlien) && !(entity instanceof EntityAqua) && !(entity instanceof EntityAcidPool);
        }
    };

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.lurkInWater();

        if (this.getAttackTarget() == null && this.worldObj.getWorldTime() % 60 == 0 && this.rand.nextInt(3) == 0)
        {
            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) Entities.getEntitiesInCoordsRange(worldObj, EntityLivingBase.class, new CoordData(this), (int) this.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue() / 2);

            for (EntityLivingBase entity : entities)
            {
                if (entitySelector.isEntityApplicable(entity) && Entities.canEntityBeSeenBy(entity, this) && (!entitySelector.isEntityApplicable(entity.getLastAttacker()) && (entity.ticksExisted - entity.getLastAttackerTime() > 150)))
                {
                    if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode)
                    {
                        this.setAttackTarget(entity);
                    }
                }
            }
        }
    }

    public void lurkInWater()
    {
        if (this.getAttackTarget() == null)
        {
            if (this.worldObj.getWorldTime() % 40 == 0 && this.rand.nextInt(4) == 0)
            {
                if (this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != net.minecraft.init.Blocks.water)
                {
                    double range = this.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue() / 2;
                    ArrayList<CoordData> coordData = Blocks.getCoordDataInRangeIncluding((int) this.posX, (int) this.posY, (int) this.posZ, (int) range, this.worldObj, net.minecraft.init.Blocks.water);

                    if (coordData.size() > 0)
                    {
                        CoordData selectedCoord = coordData.get(this.rand.nextInt(coordData.size()));
                        this.getNavigator().tryMoveToXYZ((double) selectedCoord.x, (double) selectedCoord.y, (double) selectedCoord.z, this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                    }
                }
            }
        }
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5500000238418579D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected String getHurtSound()
    {
        return Sounds.SOUND_ALIEN_HURT.getKey();
    }

    @Override
    protected String getLivingSound()
    {
        return Sounds.SOUND_ALIEN_LIVING.getKey();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_ALIEN_DEATH.getKey();
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }
}
