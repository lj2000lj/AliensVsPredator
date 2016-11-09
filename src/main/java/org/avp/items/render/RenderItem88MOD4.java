package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;

import net.minecraft.item.ItemStack;

public class RenderItem88MOD4 extends ItemRenderer
{
    public RenderItem88MOD4()
    {
        super(AliensVsPredator.resources().models()._88MOD4);
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
        OpenGL.translate(0.3F, 1F, 0F);
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.translate(0.37F, 0.25F, 0.25F);
        OpenGL.rotate(20, 1F, 0F, 0F);
        OpenGL.rotate(10, 0F, 0F, 1F);
        OpenGL.rotate(15, 0F, 1F, 0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(1.2F, -1.2F, -1.2F);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        if (firstPersonRenderCheck(data[1]))
        {
            if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
            {
                OpenGL.translate(1.7F, 1.5F, -0.885F);
                OpenGL.rotate(100.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(122.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(81.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, 0F, -0.45F);
            }
            else
            {
                OpenGL.translate(2F, 0.95F, 0.9F);
                OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            }

            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(2.0F, 2.0F, -2.0F);
            this.getModelTexMap().draw();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.translate(12F, 6F, 0F);
        OpenGL.scale(26F, 26F, 26F);
        OpenGL.rotate(220, 0F, 1F, 0F);
        this.getModelTexMap().draw();
    }
}
