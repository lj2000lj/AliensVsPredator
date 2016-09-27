package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;

import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderOvamorph extends RenderLivingWrapper
{
    public RenderOvamorph()
    {
        super(AliensVsPredator.resources().models().OVAMORPH);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLiving, float partialTicks)
    {
        super.preRenderCallback(entityLiving, partialTicks);
        OpenGL.scale(1.75F, 1.75F, 1.75F);
    }
}
