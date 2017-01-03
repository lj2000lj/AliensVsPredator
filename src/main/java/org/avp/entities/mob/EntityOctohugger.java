package org.avp.entities.mob;

import java.util.ArrayList;

import org.avp.Sounds;
import org.avp.api.parasitoidic.IParasitoid;
import org.avp.entities.extended.Organism;
import org.avp.util.Embryo;

import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.block.Blocks;
import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityOctohugger extends EntityParasitoid implements IMob, IParasitoid
{
    public EntityOctohugger(World world)
    {
        super(world);
        this.setSize(0.3F, 0.8F);
        this.experienceValue = 10;
        this.ignoreFrustumCheck = true;
        this.jumpMovementFactor = 0.3F;
        this.getNavigator().setCanSwim(true);
        this.getNavigator().setAvoidsWater(true);
        this.addTasks();
    }

    @Override
    protected void addTasks()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.55D, true));
        this.tasks.addTask(2, new EntityAIWander(this, 0.55D));
        // this.targetTasks.addTask(0, new EntityAILeapAtTarget(this, 0.8F));
        // this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, Entity.class, 0, false, false, this.getEntitySelector()));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(27, (float) 0);
        this.dataWatcher.addObject(28, (float) 0);
        this.dataWatcher.addObject(29, (float) 0);
        this.dataWatcher.addObject(31, 0);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.50D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(4.0D);
    }

    private CoordData hangingLocation = null;

    public boolean isHanging()
    {
        return this.dataWatcher.getWatchableObjectInt(31) == 1;
    }

    public void setHanging(boolean isHanging)
    {
        this.dataWatcher.updateObject(31, isHanging ? 1 : 0);
    }

    public CoordData getHangingLocation()
    {
        double x = this.dataWatcher.getWatchableObjectFloat(27);
        double y = this.dataWatcher.getWatchableObjectFloat(28);
        double z = this.dataWatcher.getWatchableObjectFloat(29);

        if (this.hangingLocation != null)
        {
            this.hangingLocation.x = x;
            this.hangingLocation.y = y;
            this.hangingLocation.z = z;
        }
        else
        {
            this.hangingLocation = new CoordData(x, y, z);
        }

        return this.hangingLocation;
    }

    public void updateHangingLocation(CoordData location)
    {
        if (location != null)
        {
            this.dataWatcher.updateObject(27, (float) location.x);
            this.dataWatcher.updateObject(28, (float) location.y);
            this.dataWatcher.updateObject(29, (float) location.z);
        }

        this.hangingLocation = location;
    }

    public boolean isHangingLocationStale()
    {
        return (this.getHangingLocation() == null || this.getHangingLocation().x() == 0 && this.getHangingLocation().y() == 0 && this.getHangingLocation().z() == 0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.fallDistance = 0;

        if (!this.worldObj.isRemote && this.worldObj.getWorldTime() % 60 == 0 && isHangingLocationStale())
        {
            ArrayList<CoordData> potentialLocations = Blocks.getCoordDataInRange((int) this.posX, (int) this.posY, (int) this.posZ, 8);

            for (int x = 0; x < potentialLocations.size(); x++)
            {
                CoordData loc = potentialLocations.get(this.rand.nextInt(potentialLocations.size()));

                if (loc.getBlock(this.worldObj) != net.minecraft.init.Blocks.air)
                {
                    if (this.worldObj.getBlock((int) loc.x, (int) loc.y - 1, (int) loc.z) == net.minecraft.init.Blocks.air)
                    {
                        if (this.worldObj.getBlock((int) loc.x - 1, (int) loc.y - 1, (int) loc.z) == net.minecraft.init.Blocks.air)
                        {
                            if (this.worldObj.getBlock((int) loc.x, (int) loc.y - 1, (int) loc.z - 1) == net.minecraft.init.Blocks.air)
                            {
                                if (this.worldObj.getBlock((int) loc.x + 1, (int) loc.y - 1, (int) loc.z) == net.minecraft.init.Blocks.air)
                                {
                                    if (this.worldObj.getBlock((int) loc.x, (int) loc.y - 1, (int) loc.z + 1) == net.minecraft.init.Blocks.air)
                                    {
                                        if (Entities.canCoordBeSeenBy(this, loc))
                                        {
                                            this.updateHangingLocation(loc.add(0.5D + (this.rand.nextDouble() / 2) - (this.rand.nextDouble() / 2), 0, 0.5D + (this.rand.nextDouble() / 2) - (this.rand.nextDouble() / 2)));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        double maxStringStrength = 0.085D;
        double stringStrength = maxStringStrength;

        if (this.boundingBox != null)
        {
            ArrayList<Entity> entities = (ArrayList<Entity>) worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(0, 16, 0));

            if (entities != null)
            {
                for (Entity entity : new ArrayList<Entity>(entities))
                {
                    if (!parasiteSelector.isEntityApplicable(entity) || entity instanceof EntityParasitoid)
                    {
                        entities.remove(entity);
                    }
                }

                Entity target = entities.size() >= 1 ? (Entity) entities.get(worldObj.rand.nextInt(entities.size())) : null;

                if (target != null)
                {
                    if (this.getDistanceToEntity(target) > 0)
                    {
                        stringStrength = 0.0F;
                    }
                }
            }
        }

        if (!this.isHangingLocationStale())
        {
            double hangingX = this.getHangingLocation().x;
            double hangingY = this.getHangingLocation().y;
            double hangingZ = this.getHangingLocation().z;
            this.motionX += (hangingX - this.posX) * stringStrength * 1.4;
            this.motionY += (hangingY - this.posY) * (stringStrength * 0.85);
            this.motionZ += (hangingZ - this.posZ) * stringStrength * 1.4;

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            double distance = this.getDistance(hangingX, hangingY, hangingZ);

            if (distance <= 1.1D)
            {
                this.setHanging(true);
            }

            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        }

        if (this.ridingEntity != null || !this.isFertile() || this.isHanging() && this.getHangingLocation() != null && this.getHangingLocation().getBlock(this.worldObj) == net.minecraft.init.Blocks.air || this.getHangingLocation() != null && !Entities.canCoordBeSeenBy(this, this.getHangingLocation()))
        {
            this.setHanging(false);
            this.updateHangingLocation(new CoordData(0, 0, 0));
        }

        if (this.isHanging())
        {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        }
    }

    @Override
    protected boolean isAIEnabled()
    {
        return true;
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
    public boolean canMoveToJelly()
    {
        return super.canMoveToJelly() && this.isFertile();
    }

    @Override
    protected String getDeathSound()
    {
        return Sounds.SOUND_FACEHUGGER_DEATH.getKey();
    }

    @Override
    public void implantEmbryo(EntityLivingBase living)
    {
        Organism organism = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);
        organism.impregnate(Embryo.BELUGA);
        organism.syncWithClients();
        this.setFertility(false);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.setHanging(nbt.getInteger("IsHanging") == 1);
        this.updateHangingLocation(new CoordData(nbt.getDouble("HangingX"), nbt.getDouble("HangingY"), nbt.getDouble("HangingZ")));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("IsHanging", this.isHanging() ? 1 : 0);

        if (!this.isHangingLocationStale())
        {
            nbt.setDouble("HangingX", this.getHangingLocation().x);
            nbt.setDouble("HangingY", this.getHangingLocation().y);
            nbt.setDouble("HangingZ", this.getHangingLocation().z);
        }
    }
}
