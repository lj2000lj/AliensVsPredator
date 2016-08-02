package org.avp.items.render.parts;

import org.avp.items.render.ItemRendererGroup;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class RenderItemM41APeripherals extends ItemRendererGroup
{
    public RenderItemM41APeripherals(TexturedModel<? extends Model> model, ModelRenderer... modelRenderers)
    {
        super(model, modelRenderers);
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        super.renderInInventory(item, data);

        OpenGL.pushMatrix();
        {
            float glScale = 22F;
            OpenGL.translate(8F, 8F, 0F);

            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.translate(0.25F, -0.65F, -0F);
            this.renderPart();
        }
        OpenGL.popMatrix();
    }
}
