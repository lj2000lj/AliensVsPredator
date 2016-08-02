package org.avp.entities.tile.render;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityCryostasisTube;
import org.avp.entities.tile.model.ModelCryostasisTube;
import org.lwjgl.opengl.GL12;

import com.arisux.amdxlib.lib.client.render.OpenGL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RenderCryostasisTube extends TileEntitySpecialRenderer
{
    public ModelCryostasisTube model = new ModelCryostasisTube();
    public static CryostasisTubeRenderer cryostasisRenderer = new CryostasisTubeRenderer();

    @SideOnly(Side.CLIENT)
    public interface ICustomCryostasisRenderer
    {
        @SideOnly(Side.CLIENT)
        public CryostasisTubeRenderer getCustomCryostasisRenderer();
    }

    public static class CryostasisTubeRenderer
    {
        public void renderChassis(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
        {
            OpenGL.enable(GL_BLEND);
            OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            OpenGL.translate(posX + 0.5F, posY + 1.125F, posZ + 0.5F);
            OpenGL.rotate(tile);
            OpenGL.enable(GL12.GL_RESCALE_NORMAL);
            OpenGL.scale(0.75F, -0.75F, 0.75F);
            OpenGL.enable(GL_ALPHA_TEST);
            OpenGL.disableCullFace();
            AliensVsPredator.resources().models().CRYOSTASIS_TUBE.draw(tile);
            OpenGL.enableCullFace();
        }

        public void renderTube(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
        {
            if (tile.getVoltage() > 0)
            {
                OpenGL.disableLightMapping();
                OpenGL.disableLight();
            }

            OpenGL.enableCullFace();
            
            if (tile.isShattered())
            {
                AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_SHATTERED.draw();
            }
            else if (tile.isCracked())
            {
                AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_CRACKED.draw();
            }
            else
            {
                AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK.draw();
            }
            
            OpenGL.enableLightMapping();
            OpenGL.enableLight();
            OpenGL.enableDepthTest();
        }

        public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
        {
            if (tile.stasisEntity != null)
            {
                OpenGL.pushMatrix();
                {
                    if (tile.getVoltage() > 0)
                    {
                        OpenGL.disableLight();
                    }

                    RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                }
                OpenGL.popMatrix();
            }
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityCryostasisTube tile = (TileEntityCryostasisTube) tileEntity;

        OpenGL.pushMatrix();
        {
            CryostasisTubeRenderer tubeRenderer = null;

            if (tile != null && tile.stasisEntity != null)
            {
                Render entityRenderer = RenderManager.instance.getEntityRenderObject(tile.stasisEntity);

                if (entityRenderer != null && entityRenderer instanceof ICustomCryostasisRenderer)
                {
                    ICustomCryostasisRenderer customRenderer = (ICustomCryostasisRenderer) entityRenderer;
                    tubeRenderer = customRenderer.getCustomCryostasisRenderer();
                }
            }

            tubeRenderer = tubeRenderer == null ? cryostasisRenderer : tubeRenderer;

            tubeRenderer.renderChassis(this, tile, posX, posY, posZ);
            tubeRenderer.renderEntity(this, tile, posX, posY, posZ);
            tubeRenderer.renderTube(this, tile, posX, posY, posZ);

            OpenGL.disable(GL_BLEND);
            OpenGL.enableLight();
            OpenGL.enableLightMapping();
        }
        OpenGL.popMatrix();
    }
}
