package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderRoyalFacehugger extends RenderFacehugger
{
    public RenderRoyalFacehugger()
    {
        super(AliensVsPredator.resources().models().ROYALFACEHUGGER);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float partialTicks)
    {
        super.preRenderCallback(entityliving, partialTicks);

        float glScale = 1.5F;
        OpenGL.scale(glScale, glScale, glScale);

        if (entityliving instanceof EntityFacehugger)
        {
            if (!((EntityFacehugger) entityliving).isFertile())
            {
                OpenGL.translate(0, 0.1, 0);
            }
        }
    }

    @Override
    protected void scale(EntityFacehugger facehugger, float glScale)
    {
        super.scale(facehugger, glScale);
    }
}
