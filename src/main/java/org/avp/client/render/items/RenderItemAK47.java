package org.avp.client.render.items;

import org.avp.AliensVsPredator;
import org.avp.URLs;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.PlayerResource;
import com.arisux.mdxlib.lib.client.render.Texture;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.util.Remote;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemAK47 extends ItemRenderer
{
    public RenderItemAK47()
    {
        super(AliensVsPredator.resources().models().AK47);
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
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        PlayerResource player = resourceStorage.create(((EntityPlayer) data[1]).getName());

        if (player != null)
        {
            OpenGL.translate(0.2F, 0.3F, -0.17F);
            OpenGL.rotate(97.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(130.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(1.3F, 1.3F, 1.3F);
            new Texture(Remote.downloadResource(String.format(URLs.SKIN_AK47, player.playerUUID()), this.getModelTexMap().getTexture())).bind();
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
                OpenGL.translate(-0.5F, 0.44F, -1.23F);
                OpenGL.rotate(101.3F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(117.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.disable(GL11.GL_CULL_FACE);
            }
            else
            {
                OpenGL.translate(0.1F, 0.35F, -0.1F);
                OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(80.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.disable(GL11.GL_CULL_FACE);
            }

            float glScale = 2.0F;
            OpenGL.scale(glScale, glScale, glScale);
            new Texture(Remote.downloadResource(String.format(URLs.SKIN_AK47, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.disable(GL11.GL_CULL_FACE);
        OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(15F, -8F, -18F);
        float glScale = 22F;
        OpenGL.scale(glScale, glScale, glScale);
        new Texture(Remote.downloadResource(String.format(URLs.SKIN_AK47, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
        this.getModelTexMap().getModel().render();
    }
}
