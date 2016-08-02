package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.URLs;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.client.render.PlayerResource;
import com.arisux.amdxlib.lib.client.render.Texture;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.util.Remote;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemM4 extends ItemRenderer
{
    public RenderItemM4()
    {
        super(AliensVsPredator.resources().models().M4);
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
        OpenGL.translate(0.3F, 1F, 0F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        PlayerResource resource = resourceStorage.create(((EntityPlayer) data[1]).getCommandSenderName());

        if (resource != null)
        {
            OpenGL.translate(0.2F, 1.15F, 0.25F);
            OpenGL.rotate(97.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(130.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(1.2F, 1.2F, 1.2F);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinM4, resource.playerUUID()), this.getModelTexMap().getTexture())).bind();
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
                OpenGL.translate(0.3F, 2.0F, -0.409F);
                OpenGL.rotate(103.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(114.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(78.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0.0F, 0.0F, -0.46F);
            }
            else
            {
                OpenGL.translate(0.6F, 1.85F, 0.9F);
                OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            }

            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(2.0F, 2.0F, 2.0F);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinM4, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.rotate(0F, 1.0F, 0.0F, 0.0F);
        OpenGL.rotate(-40F, 0.0F, 1.0F, 0.0F);
        OpenGL.rotate(0F, 0.0F, 0.0F, 1.0F);
        OpenGL.translate(0F, -5.77F, -20.85F);
        OpenGL.scale(20F, 20F, 20F);
        new Texture(Remote.downloadResource(String.format(URLs.urlSkinM4, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
        this.getModelTexMap().getModel().render();
    }
}
