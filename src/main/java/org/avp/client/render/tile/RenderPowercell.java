package org.avp.client.render.tile;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderPowercell extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(posX + 0.5, posY - 0.5, posZ + 0.5);
            AliensVsPredator.resources().models().POWERCELL.draw(tileEntity);
            OpenGL.disableLight();
            AliensVsPredator.resources().models().POWERCELL_LIQUID.draw(tileEntity);
            OpenGL.enableLight();
        }
        OpenGL.popMatrix();
    }
}
