package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.items.ItemFirearm;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;

import net.minecraft.item.ItemStack;

public class RenderItemM240ICU extends ItemRenderer
{
    public RenderItemM240ICU()
    {
        super(AliensVsPredator.resources().models().M240ICU);
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
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        OpenGL.rotate(15.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(15.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(190.0F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(-0.35F, -0.27F, 0.7F);
        float glScale = 1.9F;
        OpenGL.scale(glScale, glScale, glScale);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float displayScale = 0.005F;
        float glScale = 1.6F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.rotate(10.0F, 1.0F, 0.0F, 0.0F);

            if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
            {
                OpenGL.translate(0.8F, 0.7F, -0.7F);
                OpenGL.rotate(91.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(117.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(-0.26F, 0F, 0F);
            }
            else
            {
                OpenGL.translate(0.8F, 0.85F, -0.5F);
                OpenGL.rotate(70.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(100.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0.2F, 0F, 0F);
            }

            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(glScale, glScale, glScale);
            this.getModelTexMap().draw();

            if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFirearm)
            {
                OpenGL.disable(GL11.GL_LIGHTING);
                OpenGL.translate(-0.3439F, 0.6F, 0.04F);
                OpenGL.scale(displayScale, displayScale, displayScale);
                OpenGL.rotate(90F, 0F, 1F, 0F);
                Draw.drawRect(-2, -2, 16, 11, 0xFF000000);
                OpenGL.translate(0F, 0F, -0.01F);
                OpenGL.disableLightMapping();
                Draw.drawString(getAmmoCountDisplayString(), 0, 0, 0xFFFF0000);
                OpenGL.enable(GL11.GL_LIGHTING);
                OpenGL.color(1F, 1F, 1F, 1F);
            }
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.translate(8F, 1F, 0F);
        OpenGL.translate(0F, 5F, 0F);
        OpenGL.scale(10F, 10F, 10F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    public String getAmmoCountDisplayString()
    {
        int ammoCount = ((ItemFirearm) mc.thePlayer.inventory.getCurrentItem().getItem()).getAmmoCount();
        return (ammoCount < 10 ? "0" + ammoCount : String.valueOf(ammoCount));
    }
}
