package org.avp.client.render.materials;

import org.avp.api.material.IMaterialRenderer;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.util.Vec3;

public class RenderMaterialMist implements IMaterialRenderer
{
    @Override
    public void renderMaterialOverlay(Material material)
    {
        ;
    }
    
    @Override
    public Vec3 getFogColor()
    {
        return Vec3.createVectorHelper(0.55, 0.7, 0.9);
    }
    
    @Override
    public void renderFog(Material material)
    {
        GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
        GL11.glFogf(GL11.GL_FOG_DENSITY, 0.2F);
    }
}
