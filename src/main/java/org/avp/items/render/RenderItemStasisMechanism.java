package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;

import net.minecraft.item.ItemStack;

public class RenderItemStasisMechanism extends ItemRenderer
{
    public RenderItemStasisMechanism()
    {
        super(AliensVsPredator.resources().models().STASIS_MECHANISM);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.translate(-0.1F, 0.3F, 0F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        float glScale = 1.6F;

        OpenGL.rotate(90F, 0F, 0F, 1F);
        OpenGL.rotate(12F, 0F, 1F, 0F);
        OpenGL.translate(0.4F, -0.5F, 0.7F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, -glScale, glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 0.5F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.translate(0.2F, 0.55F, -0.4F);
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
        float glScale = 13F;
        OpenGL.translate(8F, 9F, 0F);
        OpenGL.rotate(90, -1F, 2F, 0F);
        OpenGL.translate(0F, 0F, 0F);
        OpenGL.rotate(-180F, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, glScale, glScale);
        this.getModelTexMap().draw();
    }
}
