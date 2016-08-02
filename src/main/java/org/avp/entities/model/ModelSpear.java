package org.avp.entities.model;

import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;

public class ModelSpear extends Model
{
    private final float scale = 0.08F;

    @Override
    protected void render(IRenderObject renderObject, float boxTranslation)
    {
        OpenGL.scale(scale + 0.02F, scale, scale);

        for (int x = 0; x < 4; ++x)
        {
            OpenGL.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, scale);
            Draw.drawQuad(-20, 3, 40, -6, 0, 0F, 1F, 0, 0.155F);
        }
    }
}
