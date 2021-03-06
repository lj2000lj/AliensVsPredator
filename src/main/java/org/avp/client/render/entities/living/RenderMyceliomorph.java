package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityMyceliomorph;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;

public class RenderMyceliomorph extends RenderLivingWrapper
{
    public RenderMyceliomorph()
    {
        super(AliensVsPredator.resources().models().MYCELIOMORPH);
        this.setRenderPassModel(this.getModelTexMap().getModel());
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLivingBase, float shadowSize)
    {
        super.preRenderCallback(entityLivingBase, shadowSize);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityLivingBase, int par2, float par3)
    {
        return this.setRenderPassModelBrightness((EntityMyceliomorph) entityLivingBase, par2);
    }

    protected int setRenderPassModelBrightness(EntityMyceliomorph entity, int brightness)
    {
        if (brightness != 0)
        {
            return -1;
        }
        else
        {
            AliensVsPredator.resources().models().MYCELIOMORPH_MASK.getTexture().bind();
            OpenGL.enable(GL11.GL_BLEND);
            OpenGL.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

            if (entity.isInvisible())
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            char light = 61680;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (light % 65536) / 1.0F, (light / 65536) / 1.0F);
            OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
            return 1;
        }
    }
}
