package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemMotionTracker extends ItemRenderer
{
    public RenderMotionTrackerScreen motionTracker = new RenderMotionTrackerScreen();

    public RenderItemMotionTracker()
    {
        super(AliensVsPredator.resources().models().MOTIONTRACKER);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        float glScale = 1.6F;

        OpenGL.rotate(10F, 0F, 0F, 1F);
        OpenGL.rotate(12F, 0F, 1F, 0F);
        OpenGL.translate(0.4F, -0.1F, 0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.scale(glScale, -glScale, glScale);
        this.getModelTexMap().draw();
        this.drawDisplay();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float glScale = 0.8F;

        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.translate(-0.1F, 0.6F, -1.4F);
            OpenGL.rotate(102F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(115.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(79F, 0.0F, 0.0F, 1.0F);
            OpenGL.translate(0.027F, 0F, 0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(glScale, glScale, glScale);
            this.getModelTexMap().draw();
            this.drawDisplay();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        float glScale = 20F;
        OpenGL.translate(8F, 8F, 0F);
        OpenGL.translate(0F, 0F, -5F);
        OpenGL.scale(glScale, glScale, glScale);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    private void drawDisplay()
    {
        float displayScale = 0.004F;
        OpenGL.disable(GL11.GL_LIGHTING);
        OpenGL.scale(displayScale, displayScale, displayScale);
        OpenGL.rotate(90F, 0F, 1F, 0F);
        OpenGL.translate(-89.122F, -35F, 21F);
        OpenGL.rotate(-90F, 0F, 1F, 0F);
        OpenGL.scale(0.4F, 0.4F, 0.4F);
        OpenGL.disableLight();
        motionTracker.draw(0, 0, 128, 96);
        OpenGL.enableLight();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }
}
