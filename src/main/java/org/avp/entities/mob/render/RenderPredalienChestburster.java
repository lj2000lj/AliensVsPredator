package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderPredalienChestburster extends RenderLivingWrapper
{
    public RenderPredalienChestburster()
    {
        super(AliensVsPredator.resources().models().CHESTBUSTER_PREDALIEN);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entitylivingBase, float partialTicks)
    {
        super.preRenderCallback(entitylivingBase, shadowSize);
        OpenGL.scale(0.55F, 0.55F, 0.55F);
    }
}
