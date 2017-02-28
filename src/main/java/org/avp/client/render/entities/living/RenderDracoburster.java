package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityDracoburster;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderDracoburster extends RenderLivingWrapper<EntityDracoburster>
{
    public RenderDracoburster()
    {
        super(AliensVsPredator.resources().models().DRACOBURSTER);
    }
    
    public RenderDracoburster(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    @Override
    public void doRender(EntityDracoburster entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityDracoburster entityliving, float renderPartialTicks)
    {
        float scale = 0.75F;
        OpenGL.scale(scale, scale, scale);
        super.preRenderCallback(entityliving, renderPartialTicks);
    }
}
