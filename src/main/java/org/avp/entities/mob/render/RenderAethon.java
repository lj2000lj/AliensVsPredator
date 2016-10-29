package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.TexturedModel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderAethon extends RenderLivingWrapper
{
    public RenderAethon()
    {
        super(AliensVsPredator.resources().models().AETHON);
    }
    
    public RenderAethon(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float renderPartialTicks)
    {
        super.preRenderCallback(entityliving, renderPartialTicks);
    }
}
