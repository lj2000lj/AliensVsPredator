package org.avp.util;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface IParasitoid
{
    public void attachToEntity(Entity target);
    
    public void implantEmbryo(EntityLivingBase target);
    
    public void detachFromHost();
    
    public void setFertility(boolean fertility);
    
    public boolean isFertile();
    
    public boolean isAttachedToHost();
    
    public boolean canAttach(Entity entity);
    
    public int getTicksOnHost();
    
    public int getDetachTime();
    
    public IEntitySelector getEntitySelector();
}
