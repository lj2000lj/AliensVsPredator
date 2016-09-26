package org.avp.util;

import java.util.ArrayList;
import java.util.Arrays;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public abstract class EntityRenderTransforms
{
    private ArrayList<Class<? extends Entity>> entities;

    @SuppressWarnings("all")
    public EntityRenderTransforms(Class<?>... entities)
    {
        this.entities = new ArrayList(Arrays.asList(entities));
    }

    /** 
     * Returns a list of entity types that this entity render transformation is applied to. 
     **/
    public ArrayList<Class<? extends Entity>> getHandledEntities()
    {
        return this.entities;
    }
    
    public boolean isApplicable(Entity entity)
    {
        return entities.contains(entity.getClass());
    }

    public abstract void pre(Entity entity, float partialTicks);
    
    public abstract void post(Entity entity, float partialTicks);
}
