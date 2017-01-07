package org.avp.api.blocks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public interface IAcidResistant
{
    public boolean canAcidDestroy(World worldObj, int targetX, int targetY, int targetZ, EntityLiving acid);
}
