package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;

import net.minecraft.item.ItemStack;

public class RenderItemTurret extends ItemRenderer
{
    public RenderItemTurret()
    {
        super(AliensVsPredator.resources().models().TURRET);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        ;
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.getModelTexMap().draw();
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
            OpenGL.translate(8F, -7.5F, 0F);
            OpenGL.rotate(0F, 1.0F, 0.0F, 0.0F);
            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.enableLight();
            this.getModelTexMap().draw();
        }
        OpenGL.popMatrix();
    }
}
