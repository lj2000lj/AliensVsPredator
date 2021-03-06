package org.avp.client.render.items;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemMedpod extends ItemRenderer
{
    public RenderItemMedpod()
    {
        super(AliensVsPredator.resources().models().MEDPOD);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        float glScale = 1.6F;
        OpenGL.rotate(20F, 1F, 0F, 0F);
        OpenGL.translate(0.4F, 1.75F, 0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, -glScale, glScale);
        this.getModelTexMap().draw();

        OpenGL.pushMatrix();
        {
            AliensVsPredator.resources().models().MEDPOD_MASK.draw();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 0.8F;

        OpenGL.enableBlend();
        OpenGL.blendClear();
        OpenGL.translate(0.1F, 1.0F, 0.2F);
        OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(79.0F, 0.0F, 0.0F, 1.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, glScale, glScale);
        this.getModelTexMap().draw();

        OpenGL.pushMatrix();
        {
            AliensVsPredator.resources().models().MEDPOD_MASK.draw();
        }
        OpenGL.popMatrix();
        OpenGL.disableBlend();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        float glScale = 10F;
        OpenGL.translate(8F, 2F, 0F);
        OpenGL.translate(0F, 0F, 0F);
        OpenGL.rotate(90F, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, glScale, glScale);
        OpenGL.enable(GL11.GL_BLEND);
        OpenGL.blendClear();
        this.getModelTexMap().draw();

        OpenGL.pushMatrix();
        {
            AliensVsPredator.resources().models().MEDPOD_MASK.draw();
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
            this.getModelTexMap().draw();
        }
        OpenGL.popMatrix();
    }
}
