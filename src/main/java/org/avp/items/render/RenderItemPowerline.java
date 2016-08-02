package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.game.Game;

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
        ;
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {

        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.pushMatrix();
        {
            float glScale = 12F;
            OpenGL.disable(GL11.GL_TEXTURE_2D);
            OpenGL.scale(glScale, glScale, glScale);
            OpenGL.translate(1.1, -0.1, 0);
            OpenGL.rotate(45, 1, 0, 1);
            OpenGL.color4i(0xFF222222);
            OpenGL.enableLight();
            this.getModelTexMap().drawStandaloneModel();
            OpenGL.disableLight();
            OpenGL.enable(GL11.GL_TEXTURE_2D);
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.disable(GL11.GL_TEXTURE_2D);
        OpenGL.rotate(Game.minecraft().thePlayer.worldObj.getWorldTime() % 360 * 6, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.enableLight();
        OpenGL.color4i(0xFF222222);
        this.getModelTexMap().drawStandaloneModel();
        OpenGL.enable(GL11.GL_TEXTURE_2D);
    }
}
