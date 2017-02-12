package org.avp.api.material;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public interface IMaterialPhysics
{
    public default void onFluidCollision(Entity entity)
    {
        entity.fallDistance = 0.0F;
        entity.extinguish();
    }

    public default void pushEntity(Entity entity, double velocity, Vec3 motion)
    {
        if (velocity == 0D)
        {
            return;
        }
        
        entity.motionX += motion.xCoord * velocity;
        entity.motionY += motion.yCoord * velocity;
        entity.motionZ += motion.zCoord * velocity;
    }

    public default void adjustEntitySpeed(Entity entity, double velocity)
    {
        if (velocity == 0D)
        {
            return;
        }
        
        entity.motionX *= velocity;
        entity.motionY *= velocity * 2;
        entity.motionZ *= velocity;
    }

    public default double getEntityPushStrength()
    {
        return 0.014D;
    }

    public default double getEntitySpeedMultiplier()
    {
        return 0.4D;
    }
    
    public default boolean ignoresPushableCheck()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public IMaterialRenderer getMaterialRenderer();
}