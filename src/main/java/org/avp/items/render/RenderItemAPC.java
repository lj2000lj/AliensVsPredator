package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.wavefront.Part;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.item.ItemStack;

public class RenderItemAPC extends ItemRenderer
{
    public RenderItemAPC()
    {
        super(null);
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        super.renderFirstPerson(item, data);

        OpenGL.pushMatrix();
        {
            float scale = 0.25F;
            OpenGL.scale(scale, scale, scale);
            OpenGL.translate(6.5F, 1.5F, 0F);
            OpenGL.rotate(15F, 0F, 1F, 1F);
            OpenGL.disable(GL11.GL_CULL_FACE);

            for (Part p : AliensVsPredator.resources().models().M577_APC.parts.values())
            {
                p.draw();
            }
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        super.renderThirdPerson(item, data);

        OpenGL.pushMatrix();
        {
            float scale = 0.75F;
            OpenGL.scale(scale, scale, scale);
            OpenGL.translate(-0.5F, 4.0F, 0F);
            OpenGL.rotate(110F, 0F, 0F, 1F);
            OpenGL.rotate(16F, 1F, 0F, 0F);
            OpenGL.disable(GL11.GL_CULL_FACE);

            for (Part p : AliensVsPredator.resources().models().M577_APC.parts.values())
            {
                p.draw();
            }
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        super.renderInInventory(item, data);

        OpenGL.pushMatrix();
        {
            float scale = 2.5F;
            OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
            OpenGL.translate(20F, -7F, -8F);
            OpenGL.scale(scale, -scale, scale);

            for (Part p : AliensVsPredator.resources().models().M577_APC.parts.values())
            {
                p.draw();
            }
        }
        OpenGL.popMatrix();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);

        OpenGL.pushMatrix();
        {
            OpenGL.scale(0.2F, 0.2F, 0.2F);
            OpenGL.translate(0, -1F, 0);
            OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);

            for (Part p : AliensVsPredator.resources().models().M577_APC.parts.values())
            {
                p.draw();
            }
        }
        OpenGL.popMatrix();
    }
}
