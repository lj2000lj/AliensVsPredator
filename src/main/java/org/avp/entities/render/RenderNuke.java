package org.avp.entities.render;

import org.avp.AliensVsPredator;
import org.avp.entities.EntityNuke;
import org.avp.entities.model.ModelPlasma;

import com.arisux.mdxlib.lib.client.render.Color;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNuke extends Render
{
    private ModelPlasma model = new ModelPlasma();
    private float       rotation;
    private float       rotationPrev;
    private float       scale;
    private float       scalePrev;

    public RenderNuke()
    {
        this.model.setColor(new Color(0.3F, 0.6F, 1F, 0.7F));
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float partialTicks)
    {
        EntityNuke nuke = (EntityNuke) entity;
        rotationPrev = rotation;
        rotation = nuke.ticksExisted % 360;
        rotation = rotationPrev + (rotation - rotationPrev) * partialTicks;
        this.model.setColor(new Color(0.5F, 0.6F, 1F, 0.4F));

        scalePrev = scale;
        scale = nuke.getPostInitTicks() * 200 / nuke.getPostInitTicksMax();
        scale = scalePrev + (scale - scalePrev) * partialTicks;

        OpenGL.pushMatrix();
        {
            OpenGL.translate(posX, posY, posZ);
            OpenGL.rotate(180F, 1F, 0F, 0F);
            AliensVsPredator.resources().models().WRISTBLADES.draw();
            OpenGL.rotate(entity.rotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(entity.rotationPitch - 90.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.scale(scale, scale, scale);

            if (nuke.getPostInitTicks() > 1)
            {
                OpenGL.pushMatrix();
                {
                    OpenGL.rotate(rotation, 0.0F, 1.0F, 0.0F);
                    this.model.setScale(0.1F);
                    this.model.render();

                    OpenGL.pushMatrix();
                    {
                        OpenGL.rotate(rotation * 1.5F, 0.0F, 1.0F, 0.0F);
                        this.model.setScale(0.2F);
                        this.model.render();

                        OpenGL.pushMatrix();
                        {
                            OpenGL.rotate(rotation * 1.5F, 0.0F, 1.0F, 0.0F);
                            this.model.setScale(0.3F);
                            this.model.render();

                            OpenGL.pushMatrix();
                            {
                                OpenGL.rotate(rotation * 1.5F, 0.0F, 1.0F, 0.0F);
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
        }
        OpenGL.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}
