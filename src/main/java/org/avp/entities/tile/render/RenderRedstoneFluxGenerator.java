package org.avp.entities.tile.render;

import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderRedstoneFluxGenerator extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        OpenGL.pushMatrix();
        {
            int color = 0xFF00AAFF;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(posX, posY, posZ);
            Draw.drawRect(0, 0, 1, 1, color);
            OpenGL.translate(0, 0, 1);
            Draw.drawRect(0, 0, 1, 1, color);
            OpenGL.rotate(90, 1, 0, 0);
            Draw.drawRect(0, 0, 1, -1, color);
            OpenGL.translate(0, 0, -1);
            Draw.drawRect(0, 0, 1, -1, color);
            OpenGL.rotate(90, 0, 1, 0);
            Draw.drawRect(0, 0, -1, -1, color);
            OpenGL.translate(0, 0, 1);
            Draw.drawRect(0, 0, -1, -1, color);
        }
        OpenGL.popMatrix();
    }
}
