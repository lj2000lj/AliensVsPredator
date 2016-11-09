package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;

import net.minecraft.item.ItemStack;

public class RenderItemLightPanel extends ItemRenderer
{
    public RenderItemLightPanel()
    {
        super(AliensVsPredator.resources().models().LIGHT_PANEL);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        float glScale = 1F;

        OpenGL.rotate(10F, 0F, 0F, 1F);
        OpenGL.rotate(12F, 0F, 1F, 0F);
        OpenGL.translate(0.4F, 1F, 0.5F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, -glScale, glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 0.8F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.translate(0.1F, 1.0F, 0.2F);
            OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(79.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(glScale, glScale, glScale);
            this.getModelTexMap().draw();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        float glScale = 10F;
        OpenGL.blendClear();
        OpenGL.translate(8F, 6F, 0F);
        OpenGL.rotate(-10, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(-45, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(0F, -6F, 0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, glScale, glScale);
        this.getModelTexMap().draw();
    }
    
    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.pushMatrix();
        {
            OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.getModelTexMap().draw();
        }
        OpenGL.popMatrix();
    }
}
