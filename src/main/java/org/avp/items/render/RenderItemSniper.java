package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.URLs;
import org.avp.items.model.ModelSniper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.client.render.PlayerResource;
import com.arisux.amdxlib.lib.client.render.Texture;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.util.Remote;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemSniper extends ItemRenderer
{
    private float defaultFOV = mc.gameSettings.getOptionFloatValue(GameSettings.Options.FOV);

    public RenderItemSniper()
    {
        super(AliensVsPredator.resources().models().SNIPER);
    }
    
    @Override
    public ModelSniper getModel()
    {
        return (ModelSniper) this.getModelTexMap().getModel();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        super.renderItem(type, item, data);
        this.renderZoom();
    }

    @Override
    public void renderInWorld(ItemStack item, Object... data)
    {
        super.renderInWorld(item, data);
        OpenGL.translate(-0.1F, 0.5F, 0F);
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    public void renderZoom()
    {
        if (mc.gameSettings.thirdPersonView == 0 && mc.thePlayer.getHeldItem() != null)
        {
            if (mc.thePlayer.getHeldItem().getItem() == AliensVsPredator.items().itemSniper)
            {
                if (!mc.inGameHasFocus)
                {
                    this.defaultFOV = mc.gameSettings.getOptionFloatValue(GameSettings.Options.FOV);
                }

                if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
                {
                    mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, 9F);
                }
                else if (mc.inGameHasFocus)
                {
                    mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, defaultFOV);
                }
            }
        }
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        PlayerResource player = resourceStorage.create(((EntityPlayer) data[1]).getCommandSenderName());

        if (player != null)
        {
            OpenGL.translate(0.2F, 0.3F, -0.17F);
            OpenGL.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(40.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.translate(0.1F, -0.0F, 0.8F);
            float glScale = 1.2F;
            OpenGL.scale(glScale, glScale, glScale);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinSniper, player.playerUUID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        if (firstPersonRenderCheck(data[1]))
        {
            if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
            {
                this.getModel().setFirstPerson(true);
                OpenGL.translate(1.26F, 1.985F, -0.375F);
                OpenGL.rotate(102.4F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(115F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(78.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(-0.495F, 0.60F, -1.835F);
            }
            else
            {
                this.getModel().setFirstPerson(false);
                OpenGL.translate(1.5F, 0.95F, 0.35F);
                OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.scale(2.2F, 2.2F, 2.2F);
            }
            OpenGL.disable(GL11.GL_CULL_FACE);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinSniper, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();;
            this.getModel().render();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(12F, 0F, 0F);
        float glScale = 20F;
        OpenGL.scale(glScale, glScale, glScale);
        new Texture(Remote.downloadResource(String.format(URLs.urlSkinSniper, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();;
        this.getModel().render();
    }
}
