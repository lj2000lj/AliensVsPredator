package org.avp.entities.mob.render;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderXenomorph extends RenderLivingWrapper
{
    private float renderScale;

    public RenderXenomorph(TexturedModel<? extends Model> modelTexMap)
    {
        this(modelTexMap, 1F);
    }

    public RenderXenomorph(TexturedModel<? extends Model> modelTexMap, float renderScale)
    {
        super(modelTexMap);
        this.renderScale = renderScale;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float renderPartialTicks)
    {
        OpenGL.scale(this.renderScale, this.renderScale, this.renderScale);
        super.preRenderCallback(entity, renderPartialTicks);
    }
    
    @Override
    public ResourceLocation getEntityTexture(Entity entity)
    {
        return model.getTexture();
    }

    public RenderXenomorph setScale(float renderScale)
    {
        this.renderScale = renderScale;
        return this;
    }
}
