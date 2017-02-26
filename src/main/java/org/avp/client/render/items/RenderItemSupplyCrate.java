package org.avp.client.render.items;

import org.avp.client.model.entities.ModelSupplyChute;
import org.avp.item.ItemSupplyChute.SupplyChuteType;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class RenderItemSupplyCrate extends ItemRenderer
{
    public RenderItemSupplyCrate()
    {
        super(null);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.scale(-1F, 1F, 1F);
            OpenGL.rotate(90F, 0F, 0F, 1F);
            OpenGL.rotate(-45F, 0F, 1F, 0F);
            OpenGL.rotate(90F, 1F, 0F, 0F);
            OpenGL.translate(0F, -0.5F, -0.9F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            
            TexturedModel<ModelSupplyChute> texturedModel = SupplyChuteType.get(Block.getBlockFromItem(item.getItem())).getModel();
            texturedModel.bindTexture();
            texturedModel.getModel().drawCrate();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL11.GL_CULL_FACE);
            
            TexturedModel<ModelSupplyChute> texturedModel = SupplyChuteType.get(Block.getBlockFromItem(item.getItem())).getModel();
            texturedModel.bindTexture();
            texturedModel.getModel().drawCrate();
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 15F;
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(8F, -6F, 0F);
            OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
            OpenGL.scale(glScale, glScale, glScale);
            
            TexturedModel<ModelSupplyChute> texturedModel = SupplyChuteType.get(Block.getBlockFromItem(item.getItem())).getModel();
            texturedModel.bindTexture();
            texturedModel.getModel().drawCrate();
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
            
            TexturedModel<ModelSupplyChute> texturedModel = SupplyChuteType.get(Block.getBlockFromItem(item.getItem())).getModel();
            texturedModel.bindTexture();
            texturedModel.getModel().drawCrate();
        }
        OpenGL.popMatrix();
    }
}
