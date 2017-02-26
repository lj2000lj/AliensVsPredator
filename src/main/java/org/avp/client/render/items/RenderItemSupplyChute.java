package org.avp.client.render.items;

import org.avp.item.ItemSupplyChute;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemSupplyChute extends ItemRenderer
{
    public RenderItemSupplyChute()
    {
        super(null);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.scale(-1F, 1F, 1F);
            OpenGL.rotate(90F, 0F, 0F, 1F);
            OpenGL.rotate(-45F, 0F, 1F, 0F);
            OpenGL.rotate(90F, 1F, 0F, 0F);
            OpenGL.translate(0F, -0.5F, -0.9F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            ((ItemSupplyChute) item.getItem()).getType().getModel().draw();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL11.GL_CULL_FACE);
            ((ItemSupplyChute) item.getItem()).getType().getModel().draw();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 10F;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(8F, 4F, 0F);
            OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
            OpenGL.scale(glScale, glScale, glScale);
            ((ItemSupplyChute) item.getItem()).getType().getModel().draw();
        }
        OpenGL.popMatrix();
    }
    
    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.pushMatrix();
        {
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0F, -1.5F, 0F);
            OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            ((ItemSupplyChute) item.getItem()).getType().getModel().draw();
        }
        OpenGL.popMatrix();
    }
}
