package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityChestburster;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderChestburster extends RenderLivingWrapper<EntityChestburster>
{
    public RenderChestburster()
    {
        super(AliensVsPredator.resources().models().CHESTBUSTER);
    }

    @Override
    protected void preRenderCallback(EntityChestburster entitylivingBase, float partialTicks)
    {
        super.preRenderCallback(entitylivingBase, shadowSize);
        OpenGL.scale(0.55F, 0.55F, 0.55F);
    }
}
