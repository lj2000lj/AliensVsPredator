package org.avp.api.parasitoidic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface INascentic extends IMaturable
{
    public void emerge(EntityLivingBase host);

    public INascentic setMatureState(Class<? extends Entity> state);
}
