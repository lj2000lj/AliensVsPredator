package org.avp.api.material;

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
        entity.motionX += motion.xCoord * velocity;
        entity.motionY += motion.yCoord * velocity;
        entity.motionZ += motion.zCoord * velocity;
    }

    public default void adjustEntitySpeed(Entity entity, double velocity)
    {
        entity.motionX *= velocity;
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
}