package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderDeaconShark extends RenderLivingWrapper
{
    public RenderDeaconShark()
    {
        super(AliensVsPredator.resources().models().DEACON_SHARK);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entitylivingBase, float partialTicks)
    {
        super.preRenderCallback(entitylivingBase, shadowSize);
        float scale = 1.7F;
        OpenGL.scale(scale, scale, scale);
        OpenGL.translate(0, 1, 0);
    }
}
