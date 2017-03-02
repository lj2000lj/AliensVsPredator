package org.avp.api.blocks.material;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public interface IMaterialPhysics
{
    public default void onCollision(Entity entity)
    {
        entity.fallDistance = 0.0F;
        entity.extinguish();
    }

    public default void handleForce(Entity entity, Vec3 motion)
    {
        if (this.getForceVelocity() == 0D)
        {
            return;
        }
        
        entity.motionX += motion.xCoord * this.getForceVelocity();
        entity.motionY += motion.yCoord * this.getForceVelocity();
        entity.motionZ += motion.zCoord * this.getForceVelocity();
    }

    public default void handleMovement(Entity entity)
    {
        if (this.getVelocity() == 0D)
        {
            return;
        }

        entity.motionX *= this.getVelocity();
        entity.motionY *= this.getVelocity() * this.getSinkMultiplier();
        entity.motionZ *= this.getVelocity();

        if (entity.isCollidedHorizontally && entity.isOffsetPositionInLiquid(entity.motionX, entity.motionY + (this.getJumpOffset() * 2) - entity.posY + entity.posY, entity.motionZ))
        {
            entity.motionY = this.getJumpOffset();
        }
    }

    public default double getForceVelocity()
    {
        return 0.014D;
    }

    public default double getVelocity()
    {
        return 0.4D;
    }
    
    public default double getSinkMultiplier()
    {
        return 2.25F;
    }
    
    public default double getJumpOffset()
    {
        return 0.30000001192092896D;
    }
    
    public default boolean ignoresPushableCheck()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IMaterialRenderer getMaterialRenderer();
}