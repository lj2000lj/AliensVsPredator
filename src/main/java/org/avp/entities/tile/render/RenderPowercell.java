package org.avp.entities.tile.render;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityPowercell;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderPowercell extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityPowercell tile = (TileEntityPowercell) tileEntity;

        OpenGL.pushMatrix();
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

            OpenGL.translate(posX + 0.5F, posY + 1.9F, posZ + 0.5F);

            if (Game.minecraft().objectMouseOver != null)
            {
                TileEntity tileOver = Game.minecraft().thePlayer.worldObj.getTileEntity(Game.minecraft().objectMouseOver.blockX, Game.minecraft().objectMouseOver.blockY, Game.minecraft().objectMouseOver.blockZ);

                if (tileOver != null && tileOver == tile)
                {
                    float scale = 0.02F;
                    OpenGL.scale(scale, scale, scale);
                    OpenGL.disableLight();

                    OpenGL.pushMatrix();
                    {
                        OpenGL.rotate(Game.minecraft().thePlayer.rotationYaw + 180, 0F, -1F, 0F);

                        OpenGL.pushMatrix();
                        {
                            OpenGL.rotate(Game.minecraft().thePlayer.rotationPitch + 180, -1F, 0F, 0F);
                            Draw.drawString(((float) tile.getAmpereHours()) + " Ah", -20, 30, tile.getAmpereHours() <= 0 ? 0xFFFF0000 : 0xFF00FF00);
                            scale = 0.5F;
                            OpenGL.scale(scale, scale, scale);
                            Draw.drawString(((float) tile.getVoltage()) + " V", -40, 80, tile.getVoltage() <= 0 ? 0xFFFF0000 : 0xFF00FFFF);
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
