package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderDracoEgg extends RenderLivingWrapper
{
    public RenderDracoEgg()
    {
        super(AliensVsPredator.resources().models().DRACO_OVAMORPH);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLiving, float partialTicks)
    {
        super.preRenderCallback(entityLiving, partialTicks);
        OpenGL.scale(1.75F, 1.75F, 1.75F);
    }
}
