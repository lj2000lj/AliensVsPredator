package org.avp.entities.tile.render;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityBlastdoor;

import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBlastdoor extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityBlastdoor tile = (TileEntityBlastdoor) tileEntity;

        if (tile != null && !tile.isChild())
        {
            OpenGL.pushMatrix();
            {
                OpenGL.disable(GL_CULL_FACE);
                OpenGL.translate(posX + 0.5F, posY + 1.5F, posZ + 0.5F);
                OpenGL.scale(1.0F, -1.0F, 1.0F);
                OpenGL.rotate(tile);
                AliensVsPredator.resources().models().BLASTDOOR.draw(tile);
            }
            OpenGL.popMatrix();
        }
    }
}
