package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.render.RenderFacehugger.FaceMountRenderer;
import org.avp.entities.mob.render.RenderFacehugger.IFaceMountable;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.SpecialModelBiped;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.EntityLivingBase;

public class RenderMarine extends RenderLivingWrapper implements IFaceMountable
{
    public RenderMarine()
    {
        super(AliensVsPredator.resources().models().MARINE);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase living, float partialTicks)
    {
        super.preRenderCallback(living, partialTicks);
    }
    
    public SpecialModelBiped getModel()
    {
        return (SpecialModelBiped) this.getModelTexMap().getModel();
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase living, float partialTicks)
    {
        super.renderEquippedItems(living, partialTicks);

        EntityMarine marine = (EntityMarine) living;
        TexturedModel<? extends Model> model = marine.getMarineType().getFirearmModelTexMap();

        OpenGL.pushMatrix();
        {
            this.getModel().bipedRightArm.postRender(Model.DEFAULT_BOX_TRANSLATION);
            if (marine.isFiring())
            {
                this.getModel().aimedBow = true;
            }
            else
            {
                this.getModel().aimedBow = false;
            }
            OpenGL.translate(-0.35F, 0.8F, -0.85F);
            OpenGL.rotate(270.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(0.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.scale(1.2F, 1.2F, 1.2F);
            OpenGL.disable(GL11.GL_CULL_FACE);

            switch (marine.getMarineType())
            {
                case AK47:
                    OpenGL.translate(-0.35F, 0.45F, -0.55F);
                    break;
                case SNIPER:
                    OpenGL.translate(-0.25F, 0.45F, 0.05F);
                    break;
                case M56SG:
                    OpenGL.translate(-0.15F, 0.7F, -0.73F);
                    this.getModel().aimedBow = false;
                    break;
                default:
                    break;
            }

            model.draw();
        }
        OpenGL.popMatrix();
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
