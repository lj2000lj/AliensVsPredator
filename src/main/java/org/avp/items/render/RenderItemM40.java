package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemM40 extends ItemRenderer
{
    public RenderItemM40()
    {
        super(AliensVsPredator.resources().models().M40GRENADE);
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
        OpenGL.translate(0.4F, 0.1F, 0.2F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, -glScale, glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 1F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.translate(0.4F, 0.6F, 0.2F);
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
        float glScale = 19F;
        OpenGL.translate(8F, 5F, 0F);
        OpenGL.translate(1.4F, 0F, 0.7F);
        OpenGL.rotate(-180F, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, glScale, glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.translate(0F, -0.5F, 0F);
        OpenGL.rotate(Game.minecraft().thePlayer.worldObj.getWorldTime() % 360 * 6, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }
}
