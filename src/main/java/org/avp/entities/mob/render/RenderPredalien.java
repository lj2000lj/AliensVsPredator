package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;

import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderPredalien extends RenderLivingWrapper
{
    public RenderPredalien()
    {
        super(AliensVsPredator.resources().models().PREDALIEN);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase living, float renderPartialTicks)
    {
        OpenGL.scale(0.75F, 0.75F, 0.75F);
    }
}
