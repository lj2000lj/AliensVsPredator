package org.avp.items.render;

import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemSummoner extends ItemRenderer
{
    private float                   scale;
    private float                   x;
    private float                   y;
    private TexturedModel<?>        model;

    public RenderItemSummoner(TexturedModel<?> model)
    {
        super(null);
        this.model = new TexturedModel<>(model);
    }

    public RenderItemSummoner setX(float x)
    {
        this.x = x;
        return this;
    }

    public RenderItemSummoner setY(float y)
    {
        this.y = y;
        return this;
    }

    public RenderItemSummoner setScale(float scale)
    {
        this.scale = scale;
        return this;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    public void renderCachedModel()
    {
        OpenGL.pushMatrix();
        {
            OpenGL.enableLighting();
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0F, -1F, 0F);
            OpenGL.rotate(180F, 0F, 0F, 1F);
            OpenGL.rotate(-45F, 0F, 1F, 0F);
            OpenGL.disableCullFace();
            this.model.draw();
            OpenGL.disableLighting();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        scale = 0.5F;
        OpenGL.rotate(195F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(180F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(30F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(-0.5F, 0F, 0F);
        OpenGL.scale(scale, scale, scale);
        this.renderCachedModel();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.rotate(195F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(180F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(30F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(-25F, 0F + y, 20.85F);
        OpenGL.scale(scale, scale, scale);
        this.renderCachedModel();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.translate(8F + x, -2F + y, 4F);
        OpenGL.rotate(0F, 1.0F, 0.0F, 0.0F);
        OpenGL.scale(scale, scale, scale);
        this.renderCachedModel();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        OpenGL.rotate(180F, 0.0F, 0.0F, 1F);
        OpenGL.rotate(90F, 0.0F, 1F, 0F);
        OpenGL.translate(0F, -2.5F, 0F);
        OpenGL.rotate((this.mc.theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);

        this.renderCachedModel();
    }
}
