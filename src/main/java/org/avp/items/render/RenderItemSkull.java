package org.avp.items.render;

import org.avp.block.BlockSkull;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;

public class RenderItemSkull extends ItemRenderer
{
    public RenderItemSkull()
    {
        super(null);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.scale(-0.75F, 0.75F, 0.75F);
            OpenGL.rotate(90F, 0F, 0F, 1F);
            OpenGL.rotate(-45F, 0F, 1F, 0F);
            OpenGL.rotate(90F, 1F, 0F, 0F);
            OpenGL.translate(0F, 1.0F, -1.25F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.draw((BlockSkull) Block.getBlockFromItem(item.getItem()));
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 0.25F;
            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.translate(0.5F, 3F, -1.5F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.draw((BlockSkull) Block.getBlockFromItem(item.getItem()));
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 10F;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(8F, 1F, -16F);
            OpenGL.rotate(0F, 1.0F, 0.0F, 0.0F);
            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.enableLight();
            this.draw((BlockSkull) Block.getBlockFromItem(item.getItem()));
            OpenGL.disableLight();
        }
        OpenGL.popMatrix();
    }
    
    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 1F;
            OpenGL.disable(GL11.GL_CULL_FACE);
//            OpenGL.translate(8F, 1F, -16F);
            OpenGL.rotate((this.mc.theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
            OpenGL.scale(glScale, -glScale, glScale);
            OpenGL.enableLight();
            this.draw((BlockSkull) Block.getBlockFromItem(item.getItem()));
            OpenGL.disableLight();
        }
        OpenGL.popMatrix();
    }
    
    private void draw(BlockSkull skull)
    {
        if (skull.getSkullTexture() != null)
        {
            skull.getSkullTexture().bind();
        }
        
        skull.preRenderTransforms();

        for (ModelRenderer renderer : skull.getSkullModelRenderers())
        {
            renderer.render(Model.DEFAULT_BOX_TRANSLATION);
        }
    }
}
