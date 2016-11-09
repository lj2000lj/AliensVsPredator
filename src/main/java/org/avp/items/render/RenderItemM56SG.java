package org.avp.items.render;

import org.avp.AliensVsPredator;
import org.avp.URLs;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.client.render.ItemRenderer;
import com.arisux.amdxlib.lib.client.render.PlayerResource;
import com.arisux.amdxlib.lib.client.render.Texture;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.util.Remote;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemM56SG extends ItemRenderer
{
    public RenderItemM56SG()
    {
        super(AliensVsPredator.resources().models().M56SG);
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
        OpenGL.rotate((Game.minecraft().theWorld.getWorldTime() + Game.partialTicks() % 360) * 10, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(-0.1F, 0.5F, -0.5F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        if (firstPersonRenderCheck(data[1]))
        {
            OpenGL.translate(0.1F, 0.15F, 0.2F);
            OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(2.0F, 2.0F, 2.0F);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinM56sg, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        PlayerResource player = resourceStorage.create(((EntityPlayer) data[1]).getCommandSenderName());

        if (player != null)
        {
            OpenGL.translate(0.25F, -0.3F, -0.1F);
            OpenGL.rotate(280.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(-93.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.scale(1.3F, 1.3F, 1.3F);
            new Texture (Remote.downloadResource(String.format(URLs.urlSkinM56sg, player.playerUUID()), this.getModelTexMap().getTexture())).bind();;
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(16F, -8F, -14F);
        OpenGL.scale(20F, 20F, 20F);
        new Texture(Remote.downloadResource(String.format(URLs.urlSkinM56sg, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
        this.getModelTexMap().getModel().render();
    }
}
