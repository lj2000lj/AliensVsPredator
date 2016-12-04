package org.avp.entities.render;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSupplyChute extends Render
{
    public RenderSupplyChute()
    {
        super();
    }
    
    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        double renderX = (entity.posX - entity.lastTickPosX) * (double) renderPartialTicks;
        double renderY = (entity.posY - entity.lastTickPosY) * (double) renderPartialTicks;
        double renderZ = (entity.posZ - entity.lastTickPosZ) * (double) renderPartialTicks;
        
        OpenGL.pushMatrix();
        OpenGL.translate((float) posX, (float) posY + 0.75F, (float) posZ);
        OpenGL.translate((float) renderX, (float) renderY, (float) renderZ);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disableCullFace();
        OpenGL.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        AliensVsPredator.resources().models().SUPPLY_CHUTE.draw();
        OpenGL.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}
