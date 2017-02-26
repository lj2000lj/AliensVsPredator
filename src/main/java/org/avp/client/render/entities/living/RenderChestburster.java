package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderChestburster extends RenderLivingWrapper
{
    public RenderChestburster()
    {
        super(AliensVsPredator.resources().models().CHESTBUSTER);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entitylivingBase, float partialTicks)
    {
        super.preRenderCallback(entitylivingBase, shadowSize);
        OpenGL.scale(0.55F, 0.55F, 0.55F);
    }
}
