package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityDracoEgg;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderDracoEgg extends RenderLivingWrapper<EntityDracoEgg>
{
    public RenderDracoEgg()
    {
        super(AliensVsPredator.resources().models().DRACO_OVAMORPH);
    }

    @Override
    protected void preRenderCallback(EntityDracoEgg entityLiving, float partialTicks)
    {
        super.preRenderCallback(entityLiving, partialTicks);
        OpenGL.scale(1.75F, 1.75F, 1.75F);
    }
}
