package org.avp.entities.living;

import com.arisux.mdxlib.lib.world.Pos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityDracoEgg extends EntityOvamorph
{
    public EntityDracoEgg(World world)
    {
        super(world);
        this.setSize(1F, 1F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    
    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);
    }
    
    @Override
    protected void hatch()
    {
        if (!this.worldObj.isRemote)
        {
            EntityFacehugger facehugger = new EntityFacehugger(this.worldObj);
            Pos pos = new Pos(this).findSafePosAround(this.worldObj);

            facehugger.setLocationAndAngles(pos.x, pos.y, pos.z, 0F, 0F);
            worldObj.spawnEntityInWorld(facehugger);
            facehugger.motionY = 0.75F;

            this.setContainsFacehugger(false);
        }
    }
}
