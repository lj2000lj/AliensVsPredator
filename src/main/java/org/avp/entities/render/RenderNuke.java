package org.avp.entities.render;

import org.avp.entities.EntityNuke;
import org.avp.entities.model.ModelPlasma;

import com.arisux.amdxlib.lib.client.render.Color;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNuke extends Render
{
    private ModelPlasma model = new ModelPlasma();
    
    public RenderNuke()
    {
        this.model.setColor(new Color(0.3F, 0.6F, 1F, 0.7F));
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        EntityNuke nuke = (EntityNuke) entity;
        float rotation = nuke.ticksExisted % 360;
        float scale = 0.3F * nuke.ticksExisted;

        if (nuke.ticksExisted > nuke.getDetonationTicks() - (nuke.getDetonationTicks() / 25))
        {
            scale = (float) ((float) (0.3F * (nuke.getDetonationTicks() - nuke.ticksExisted) * 2048) / (0.2F * (nuke.getDetonationTicks())));
        }

        OpenGL.pushMatrix();
        {
            OpenGL.translate(posX, posY, posZ);
            OpenGL.rotate(entity.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(entity.rotationPitch - 90.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.scale(scale, scale, scale);

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
