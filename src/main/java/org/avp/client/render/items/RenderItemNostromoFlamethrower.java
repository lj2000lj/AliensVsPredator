package org.avp.client.render.items;

import org.avp.AliensVsPredator;
import org.avp.item.ItemFirearm;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemNostromoFlamethrower extends ItemRenderer
{
    public RenderItemNostromoFlamethrower()
    {
        super(AliensVsPredator.resources().models().FLAMETHROWER_NOSTROMO);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.translate(0F, 0.5F, 0F);
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.scale(0.5F, -0.5F, 0.5F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.rotate(15.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(15.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(190.0F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(-0.5F, -0.27F, 1.2F);
        float glScale = 1.0F;
        OpenGL.scale(glScale, glScale, -glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 0.6F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.rotate(10.0F, 1.0F, 0.0F, 0.0F);

            if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
            {
                OpenGL.translate(0.8F, 0.7F, -0.76F);
                OpenGL.rotate(94.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(117.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(77.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(-0.26F, 0F, 0F);
            }
            else
            {
                OpenGL.translate(0.9F, 0.95F, -0.6F);
                OpenGL.rotate(70.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(100.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0.2F, 0F, 0F);
            }

            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(glScale, glScale, -glScale);
            this.getModelTexMap().draw();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.translate(11F, 7F, 0F);
        OpenGL.rotate(220F, 0F, 1F, 0F);
        OpenGL.scale(7F, 7F, 7F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    public String getAmmoCountDisplayString()
    {
        int ammoCount = ((ItemFirearm) mc.thePlayer.inventory.getCurrentItem().getItem()).getAmmoCount();
        return (ammoCount < 10 ? "0" + ammoCount : String.valueOf(ammoCount));
    }
}
