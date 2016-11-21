package org.avp.entities.tile.render;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityPowerline;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderPowerline extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityPowerline tile = (TileEntityPowerline) te;

        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL_CULL_FACE);
            OpenGL.disable(GL11.GL_TEXTURE_2D);

            OpenGL.pushMatrix();
            {
                OpenGL.translate(posX + 0.5F, posY + 1.5F, posZ + 0.5F);
                OpenGL.scale(1.0F, -1.0F, 1.0F);
                OpenGL.enable(GL_ALPHA_TEST);
                OpenGL.color4i(0xFF222222);
                AliensVsPredator.resources().models().CABLE.drawStandaloneModel(tile);
            }
            OpenGL.popMatrix();

            OpenGL.enable(GL11.GL_TEXTURE_2D);

            OpenGL.translate(posX + 0.5F, posY + 1.5F, posZ + 0.5F);

            if (Game.minecraft().objectMouseOver != null || Game.isDevEnvironment())
            {
                TileEntity tileOver = null;
                
                if (Game.minecraft().objectMouseOver != null)
                {
                    tileOver = Game.minecraft().thePlayer.worldObj.getTileEntity(Game.minecraft().objectMouseOver.blockX, Game.minecraft().objectMouseOver.blockY, Game.minecraft().objectMouseOver.blockZ);
                }

                if (tileOver != null && tileOver == tile || Game.isDevEnvironment())
                {
                    float scale = 0.02F;
                    OpenGL.scale(scale, scale, scale);
                    OpenGL.disableLight();

                    OpenGL.pushMatrix();
                    {
                        OpenGL.rotate(Game.minecraft().thePlayer.rotationYaw + 180, 0F, -1F, 0F);

                        OpenGL.pushMatrix();
                        {
                            OpenGL.translate(-10F, -45F, -5F);
                            OpenGL.rotate(Game.minecraft().thePlayer.rotationPitch + 180, -1F, 0F, 0F);
                            OpenGL.translate(0F, 0F, -35F);
                            Draw.drawString(((float) tile.getVoltage()) + "V", 0, 0, tile.getVoltage() <= 0 ? 0xFFFF0000 : 0xFF00FF00);
                            scale = 0.5F;
                            OpenGL.scale(scale, scale, scale);
                            OpenGL.translate(0F, 20.0F, 0F);
                            Draw.drawString(((float) tile.getAmperage()) + "A", 0, 0, 0xFF00FFFF);
                        }
                        OpenGL.popMatrix();
                    }
                    OpenGL.popMatrix();

                    OpenGL.enableLight();
                }
            }
        }
        OpenGL.popMatrix();
    }
}
