package org.avp.client.render.tile;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderLightPanel extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL_CULL_FACE);
            OpenGL.translate(posX + 0.5F, posY - 0.5, posZ + 0.5F);
            OpenGL.rotate(0F, 0F, 1F, 0F);
            OpenGL.scale(1.0F, 1.0F, 1.0F);
            AliensVsPredator.resources().models().LIGHT_PANEL.draw(tileEntity);
        }
        OpenGL.popMatrix();
    }
}
