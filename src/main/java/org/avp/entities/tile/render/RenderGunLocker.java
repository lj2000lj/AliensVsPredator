package org.avp.entities.tile.render;

import org.avp.AliensVsPredator;
import org.avp.entities.tile.TileEntityLocker;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;

public class RenderGunLocker extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float renderPartialTicks)
    {
        TileEntityLocker tile = (TileEntityLocker) tileEntity;

        OpenGL.pushMatrix();
        {
            float scale = 0.95F;
            OpenGL.enable(GL11.GL_CULL_FACE);
            OpenGL.translate(posX + 0.5F, posY + 1.41F, posZ + 0.5F);
            OpenGL.scale(scale, -scale, scale);
            OpenGL.enable(GL11.GL_ALPHA_TEST);
            OpenGL.disableCullFace();
            OpenGL.rotate(tile);
            
            AliensVsPredator.resources().models().GUN_LOCKER.getModel().door.rotateAngleY = !tile.isOpen() ? 0 : -1.5F;
            AliensVsPredator.resources().models().GUN_LOCKER.draw(tile);

            if (tile != null)
            {
                OpenGL.pushMatrix();
                {
                    float itemScale = 0.06F;
                    OpenGL.scale(itemScale, itemScale, itemScale);
                    OpenGL.rotate(-90F, 0F, 0F, 1F);
                    OpenGL.translate(-27F, -7.5F, -4F);
                    OpenGL.rotate(-50F, 0F, 1F, 0F);
                    OpenGL.enableLight();
                    OpenGL.blendClear();

                    ItemStack stack = null;
                    int rows = 21;
                    int stackIndex = 0;

                    for (int rowX = 0; rowX < tile.inventory.getSizeInventory() / rows; rowX++)
                    {
                        for (int rowY = 0; rowY < rows; rowY++)
                        {
                            stack = tile.inventory.getStackInSlot(stackIndex++);

                            if (stack != null)
                            {
                                break;
                            }
                        }

                        if (stack != null)
                        {
                            break;
                        }
                    }

                    if (stack != null)
                    {
                        IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.INVENTORY);

                        if (renderer != null)
                        {
                            Object[] args = {};

                            OpenGL.pushMatrix();
                            {
                                OpenGL.translate(8F, 0F, 0F);
                                renderer.renderItem(ItemRenderType.INVENTORY, stack, args);
                                OpenGL.enableLight();
                            }
                            OpenGL.popMatrix();
                        }
                    }
                }
                OpenGL.popMatrix();
            }
        }
        OpenGL.popMatrix();
    }
}
