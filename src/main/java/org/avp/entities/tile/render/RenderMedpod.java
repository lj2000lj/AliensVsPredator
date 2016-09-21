package org.avp.entities.tile.render;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityMedpod;

import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMedpod extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityMedpod tile = (TileEntityMedpod) tileEntity;

        OpenGL.pushMatrix();
        {
            float newScale = 1.5F;
            OpenGL.translate(posX, posY, posZ);
            OpenGL.translate(0.5F, 2.25F, 0.5F);
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.scale(newScale, newScale, newScale);
            OpenGL.disableCullFace();
            OpenGL.rotateOpposite(tile);
            AliensVsPredator.resources().models().MEDPOD.draw(tile);

            OpenGL.enableBlend();
            OpenGL.blendClear();

            if (tile.getVoltage() > 0)
            {
                OpenGL.disableLight();
                OpenGL.disableLightMapping();
            }

            AliensVsPredator.resources().models().MEDPOD_MASK.draw(tile);
            OpenGL.enableLight();
            OpenGL.enableLightMapping();
            OpenGL.disableBlend();
        }
        OpenGL.popMatrix();
    }
}
