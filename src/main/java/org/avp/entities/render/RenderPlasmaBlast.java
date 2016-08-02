package org.avp.entities.render;

import org.avp.entities.EntityPlasma;
import org.avp.entities.model.ModelPlasma;

import com.arisux.amdxlib.lib.client.render.Color;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPlasmaBlast extends Render
{
    private ModelPlasma model = new ModelPlasma();
    
    public RenderPlasmaBlast()
    {
        this.model.setColor(new Color(0.3F, 0.6F, 1F, 0.7F));
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        EntityPlasma plasma = (EntityPlasma) entity;
        float rotation = plasma.ticksExisted % 360;

        OpenGL.pushMatrix();
        {
            OpenGL.translate(posX, posY, posZ);
            OpenGL.rotate(entity.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(entity.rotationPitch - 90.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.scale(plasma.getPlasmaSize(), plasma.getPlasmaSize(), plasma.getPlasmaSize());

            OpenGL.pushMatrix();
            {
                OpenGL.rotate(rotation, 0.0F, 1.0F, 0.0F);
                this.model.setScale(0.1F);
                this.model.render();

                OpenGL.pushMatrix();
                {
                    OpenGL.rotate(rotation, 0.0F, 1.0F, 0.0F);
                    this.model.setScale(0.2F);
                    this.model.render();

                    OpenGL.pushMatrix();
                    {
                        OpenGL.rotate(rotation, 0.0F, 1.0F, 0.0F);
                        this.model.setScale(0.3F);
                        this.model.render();

                        OpenGL.pushMatrix();
                        {
                            OpenGL.rotate(rotation, 0.0F, 1.0F, 0.0F);
                            this.model.setScale(0.35F);
                            this.model.render();
                        }
                        OpenGL.popMatrix();
                    }
                    OpenGL.popMatrix();
                }
                OpenGL.popMatrix();
            }
            OpenGL.popMatrix();
        }
        OpenGL.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}
