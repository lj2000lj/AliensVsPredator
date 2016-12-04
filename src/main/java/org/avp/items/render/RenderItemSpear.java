package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemSpear implements IItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
                return true;

            case EQUIPPED_FIRST_PERSON:
                return true;

            case INVENTORY:
                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case EQUIPPED:
                OpenGL.rotate(175.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(55.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(-0.25F, 0.75F, 0.065F);
                OpenGL.scale(1F, 1F, 1F);
                OpenGL.enable(GL11.GL_CULL_FACE);
                if (Mouse.isButtonDown(1))
                {
                    OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                    OpenGL.translate(0.25F, -0.2F, 0F);
                }
                AliensVsPredator.resources().models().SPEAR.draw();
                break;

            case EQUIPPED_FIRST_PERSON:
                OpenGL.rotate(170.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(10.0F, 0.0F, 0.0F, 1.0F);

                if ((EntityPlayer) data[1] == Game.minecraft().renderViewEntity && Game.minecraft().gameSettings.thirdPersonView == 0 && (!(Game.minecraft().currentScreen instanceof GuiInventory) && !(Game.minecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F))
                {
                    OpenGL.translate(0.2F, -0.4F, -0.5F);
                    OpenGL.rotate(270.0F, 1.0F, 0.0F, 0.0F);
                    OpenGL.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                    OpenGL.rotate(9.0F, 0.0F, 0.0F, 1.0F);
                }
                else
                {
                    OpenGL.translate(0.45F, 0.0F, 0.0F);
                }

                OpenGL.scale(1.6F, 1.6F, 1.6F);
                AliensVsPredator.resources().models().SPEAR.draw();
                break;

            case INVENTORY:
                OpenGL.disable(GL11.GL_CULL_FACE);
                OpenGL.enable(GL11.GL_BLEND);
                OpenGL.translate(0.5F, 0F, 0F);
                OpenGL.rotate(-135, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(-12F, 0F, 0F);
                OpenGL.scale(10F, 10F, 10F);
                AliensVsPredator.resources().models().SPEAR.draw();
                break;

            case ENTITY:
                OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
                OpenGL.disable(GL11.GL_CULL_FACE);
                AliensVsPredator.resources().models().SPEAR.draw();
                break;

            default:
                break;
        }
    }
}
