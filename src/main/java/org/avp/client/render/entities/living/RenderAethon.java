package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityAethon;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderAethon extends RenderLivingWrapper<EntityAethon>
{
    public RenderAethon()
    {
        super(AliensVsPredator.resources().models().AETHON);
    }
    
    public RenderAethon(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    @Override
    public void doRender(EntityAethon entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityAethon entityliving, float renderPartialTicks)
    {
        float scale = 1.75F;
        OpenGL.scale(scale, scale, scale);
        super.preRenderCallback(entityliving, renderPartialTicks);
    }
}
