package org.avp.items.render.parts;

import org.avp.items.render.ItemRendererGroup;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class RenderItemSniperPeripherals extends ItemRendererGroup
{
    public RenderItemSniperPeripherals(TexturedModel<? extends Model> model, ModelRenderer... modelRenderers)
    {
        super(model, modelRenderers);
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        super.renderInInventory(item, data);

        OpenGL.pushMatrix();
        {
            float glScale = 32F;
            OpenGL.translate(8F, 8F, 0F);

            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.translate(0F, -0.075F, -0.25F);
            this.renderPart();
        }
        OpenGL.popMatrix();
    }
    
    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.rotate((this.mc.theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.renderPart();
        }
        OpenGL.popMatrix();
    }
}
