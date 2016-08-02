package org.avp.entities.tile.render;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntitySupplyCrate;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSupplyCrate extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntitySupplyCrate tile = (TileEntitySupplyCrate) tileEntity;
        
        OpenGL.pushMatrix();
        {
            float scale = 1F;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(posX + 0.5F, posY + 1.5F, posZ + 0.5F);
            OpenGL.enable(GL12.GL_RESCALE_NORMAL);
            OpenGL.scale(scale, -scale, scale);
            OpenGL.enable(GL11.GL_ALPHA_TEST);
            OpenGL.disableCullFace();
            OpenGL.rotate(tile);
            AliensVsPredator.resources().models().SUPPLY_CHUTE.bindTexture();
            AliensVsPredator.resources().models().SUPPLY_CHUTE.getModel().drawCrate();
        }
        OpenGL.popMatrix();
    }
}
