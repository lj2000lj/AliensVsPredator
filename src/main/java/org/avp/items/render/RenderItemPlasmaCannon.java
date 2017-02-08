package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.items.model.ModelPlasmaCannon;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemPlasmaCannon extends ItemRenderer
{
    public RenderItemPlasmaCannon()
    {
        super(AliensVsPredator.resources().models().PLASMACANNON);
    }

    @Override
    public ModelPlasmaCannon getModel()
    {
        return (ModelPlasmaCannon) this.getModelTexMap().getModel();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        super.renderFirstPerson(item, data);

        EntityPlayer playerToRender = (EntityPlayer) data[1];
        OpenGL.translate(1.75F, 1.45F, 0.1F);
        OpenGL.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(-45.0F, 0.0F, 0.0F, 1.0F);
        OpenGL.rotate(-100.0F, 0.0F, 1.0F, 0.0F);

        if (playerToRender == Game.minecraft().renderViewEntity && Game.minecraft().gameSettings.thirdPersonView == 0 && (!(Game.minecraft().currentScreen instanceof GuiInventory) && !(Game.minecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F))
        {
            ;
        }

        OpenGL.scale(1.6F, 1.6F, 1.6F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        super.renderThirdPerson(item, data);

        EntityPlayer playerToRender = (EntityPlayer) data[1];

        OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(-220.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.translate(0F, -0.025F, -1.25F);
        OpenGL.scale(0.75F, 0.75F, 0.75F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        super.renderInInventory(item, data);

        OpenGL.enable(GL11.GL_BLEND);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.translate(13.5F, -4F, -18F);
        OpenGL.rotate(-45F, 0.0F, 1.0F, 0.0F);
        OpenGL.scale(16F, 16F, 16F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.pushMatrix();
        {
            OpenGL.rotate((this.mc.theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            this.getModelTexMap().getTexture().bind();
            this.getModel().render();
        }
        OpenGL.popMatrix();
    }
}
