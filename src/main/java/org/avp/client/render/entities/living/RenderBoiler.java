package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityBoiler;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;

public class RenderBoiler extends RenderLivingWrapper
{
    public RenderBoiler()
    {
        super(AliensVsPredator.resources().models().BOILER);
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
        return this.setRenderPassModelBrightness((EntityBoiler) entityLivingBase, par2);
    }

    protected int setRenderPassModelBrightness(EntityBoiler entity, int brightness)
    {
        if (brightness != 0)
        {
            return -1;
        }
        else
        {
            char light = 61680;
            AliensVsPredator.resources().models().BOILER_MASK.getTexture().bind();
            OpenGL.enableBlend();
            OpenGL.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
            GL11.glDepthMask(!entity.isInvisible());
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (light % 65536) / 1.0F, (light / 65536) / 1.0F);
            OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
            return 1;
        }
    }
}
