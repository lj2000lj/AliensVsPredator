package org.avp.entities.tile.render;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityStasisMechanism;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderStasisMechanism extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityStasisMechanism tile = (TileEntityStasisMechanism) te;

        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL_CULL_FACE);
            OpenGL.translate(posX + 0.5F, posY, posZ + 0.5F);
            OpenGL.rotate(tile.getDirection() * (-90F), 0F, 1F, 0F);
            OpenGL.scale(1.0F, -1.0F, 1.0F);

            AliensVsPredator.resources().models().STASIS_MECHANISM.draw(tile);

            if (Game.minecraft().gameSettings.fancyGraphics)
            {
                OpenGL.disableLight();
                AliensVsPredator.resources().models().STASIS_MECHANISM_MASK.draw(tile);
                OpenGL.enableLight();
            }
        }
        OpenGL.popMatrix();
    }
}
