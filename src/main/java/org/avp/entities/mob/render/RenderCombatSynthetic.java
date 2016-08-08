package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.render.RenderFacehugger.FaceMountRenderer;
import org.avp.entities.mob.render.RenderFacehugger.IFaceMountable;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.SpecialModelBiped;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderCombatSynthetic extends RenderLivingWrapper implements IFaceMountable
{
    public RenderCombatSynthetic()
    {
        super(AliensVsPredator.resources().models().COMBAT_SYNTHETIC);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase base, float partialTicks)
    {
        super.preRenderCallback(base, partialTicks);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityLiving, float partialTicks)
    {
        super.renderEquippedItems(entityLiving, partialTicks);

        float glScale = 1.2F;

        if (this.mainModel != null && this.mainModel instanceof SpecialModelBiped)
        {
            SpecialModelBiped model = (SpecialModelBiped) this.mainModel;

            OpenGL.pushMatrix();
            {
                model.aimedBow = true;
                model.bipedRightArm.postRender(Model.DEFAULT_BOX_TRANSLATION);
                OpenGL.translate(-0.35F, 0.8F, -0.85F);
                OpenGL.rotate(270.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(0.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.disable(GL11.GL_CULL_FACE);
                OpenGL.scale(glScale, glScale, glScale);
                AliensVsPredator.resources().models().M41A.draw();
            }
            OpenGL.popMatrix();
        }
    }

    @Override
    public FaceMountRenderer getFaceMountRenderer()
    {
        return new FaceMountRenderer(EntityMarine.class)
        {
            @Override
            public void render(EntityFacehugger facehugger, float renderPartialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);
            }
        };
    }
}
