package org.avp.items.render;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.client.render.Texture;
import com.arisux.amdxlib.lib.world.entity.Entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class RenderItemSummoner extends ItemRenderer
{
    private Class<? extends Entity> entityClass;
    private float                   scale;
    private float                   x;
    private float                   y;
    private ModelBase               modelCache;
    private Texture                 textureCache;
    private Render                  renderCache;

    public RenderItemSummoner(Class<? extends Entity> entityClass)
    {
        super(null);
        this.entityClass = entityClass;
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
        this.updateCache();
        super.renderItem(type, item, data);
    }

    public void updateCache()
    {
        if (renderCache == null)
        {
            renderCache = RenderManager.instance.getEntityClassRenderObject(entityClass);
        }

        if (renderCache instanceof RendererLivingEntity)
        {
            if (modelCache == null)
            {
                modelCache = Model.getMainModel((RendererLivingEntity) renderCache);
            }

            if (textureCache == null)
            {
                textureCache = new Texture(Entities.getEntityTexture(renderCache, null));
            }
        }
    }

    public void renderCachedModel()
    {
        if (modelCache != null && textureCache != null)
        {
            OpenGL.pushMatrix();
            {
                OpenGL.enableLighting();
                OpenGL.scale(1F, -1F, 1F);
                OpenGL.translate(0F, -1F, 0F);
                OpenGL.rotate(180F, 0F, 0F, 1F);
                OpenGL.rotate(-45F, 0F, 1F, 0F);
                textureCache.bind();
                OpenGL.disableCullFace();
                modelCache.render(null, 0F, 0F, 0F, 0F, 0F, Model.DEFAULT_BOX_TRANSLATION);
                OpenGL.disableLighting();
            }
            OpenGL.popMatrix();
        }
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
        scale = 7.5F;
        OpenGL.translate(8F + x, -1.77F + y, -4F);
        OpenGL.rotate(0F, 1.0F, 0.0F, 0.0F);
        OpenGL.scale(scale, scale, scale);
        this.renderCachedModel();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        OpenGL.rotate(180F, 0.0F, 0.0F, 1F);
        OpenGL.rotate(90F, 0.0F, 1F, 0F);

        this.renderCachedModel();
    }
}
