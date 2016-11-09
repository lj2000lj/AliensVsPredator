package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.items.ItemWristbracer;
import org.avp.items.model.ModelWristBlade;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.game.Game;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemWristbracer extends ItemRenderer
{
    public RenderItemWristbracer()
    {
        super(AliensVsPredator.resources().models().WRISTBLADES);
    }
    
    @Override
    public ModelWristBlade getModel()
    {
        return (ModelWristBlade) this.getModelTexMap().getModel();
    }
    
    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        super.renderFirstPerson(item, data);
        
        EntityPlayer playerToRender = (EntityPlayer) data[1];
        OpenGL.rotate(186.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(3.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(-35.0F, 0.0F, 0.0F, 1.0F);

        if ((EntityPlayer) data[1] == Game.minecraft().renderViewEntity && Game.minecraft().gameSettings.thirdPersonView == 0 && (!(Game.minecraft().currentScreen instanceof GuiInventory) && !(Game.minecraft().currentScreen instanceof GuiContainerCreative) || RenderManager.instance.playerViewY != 180.0F))
        {
            OpenGL.translate(0.4F, 0.1F, -0.1F);
            OpenGL.rotate(340.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(-70.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
        }
        else
        {
            OpenGL.translate(0.45F, 0.0F, 0.0F);
        }

        OpenGL.scale(1.6F, 1.6F, 1.6F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();

        if (playerToRender != null && ItemWristbracer.playersWristbracerContainsBlades(playerToRender))
        {
            Model.draw(this.getModel().b6);
            Model.draw(this.getModel().bladeLeft);
        }
    }
    
    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        super.renderThirdPerson(item, data);
        
        EntityPlayer playerToRender = (EntityPlayer) data[1];

        OpenGL.rotate(-78.0F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(-165.0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(13.0F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(-0.25F, -0.15F, 0.3F);
        OpenGL.scale(2F, 2F, 2F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();

        if (playerToRender != null && ItemWristbracer.playersWristbracerContainsBlades(playerToRender))
        {
            Model.draw(this.getModel().b6);
            Model.draw(this.getModel().bladeLeft);
        }
    }
    
    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        super.renderInInventory(item, data);
        
        OpenGL.enable(GL11.GL_BLEND);
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.rotate(-45F, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(-16F, -7F, -18F);
        OpenGL.scale(33F, 33F, 33F);
        this.getModelTexMap().getTexture().bind();
        this.getModel().render();
        Model.draw(this.getModel().b6);
        Model.draw(this.getModel().bladeLeft);
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
            Model.draw(this.getModel().b6);
            Model.draw(this.getModel().bladeLeft);
        }
        OpenGL.popMatrix();
    }
}
