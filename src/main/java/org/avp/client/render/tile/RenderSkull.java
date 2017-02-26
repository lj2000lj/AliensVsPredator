package org.avp.client.render.tile;

import org.avp.tile.TileEntitySkull;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSkull extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tile, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntitySkull skull = (TileEntitySkull) tile;

        OpenGL.pushMatrix();
        {
            float scale = 0.64F;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(posX + 0.5F, posY, posZ + 0.5F);
            OpenGL.scale(scale, -scale, scale);
            OpenGL.enable(GL11.GL_ALPHA_TEST);
            OpenGL.disableCullFace();
            OpenGL.rotate(skull);

            if (skull.getSkullBlock().getSkullTexture() != null)
            {
                skull.getSkullBlock().getSkullTexture().bind();
            }
            
            skull.getSkullBlock().preRenderTransforms();

            for (ModelRenderer renderer : skull.getSkullBlock().getSkullModelRenderers())
            {
                renderer.render(Model.DEFAULT_SCALE);
            }
            OpenGL.enableCullFace();
        }
        OpenGL.popMatrix();
    }
}
