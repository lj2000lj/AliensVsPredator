package org.avp.entities.tile.render;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSatelliteDish extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.translate(posX, posY, posZ);
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0.5F, -1.525F, 0.5F);
            OpenGL.disableCullFace();
            AliensVsPredator.resources().models().SATELLITE_DISH.draw(tileEntity);
        }
        OpenGL.popMatrix();
    }
}
