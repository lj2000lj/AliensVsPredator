package org.avp.client.render.items;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemPowerline extends ItemRenderer
{
    public RenderItemPowerline()
    {
        super(AliensVsPredator.resources().models().CABLE);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        OpenGL.translate(0.375, 0F, 0);
        OpenGL.rotate(90, 1, 0, 0);
        OpenGL.rotate(35, 0, 1, 0);
        this.getModelTexMap().draw();
        OpenGL.popMatrix();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        OpenGL.translate(0.375, 0.4F, 0);
        OpenGL.rotate(90, 1, 0, 0);
        OpenGL.rotate(35, 0, 1, 0);
        this.getModelTexMap().draw();
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        float glScale = 22F;
        OpenGL.pushMatrix();
        OpenGL.scale(glScale, glScale, glScale);
        OpenGL.translate(0.375, 0.4F, 0);
        OpenGL.rotate(45, 0, 1, 0);
        OpenGL.disableCullFace();
        this.getModelTexMap().draw();
        OpenGL.enableCullFace();
        OpenGL.popMatrix();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.disable(GL11.GL_TEXTURE_2D);
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }
}
