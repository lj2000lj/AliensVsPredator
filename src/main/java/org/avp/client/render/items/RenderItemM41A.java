package org.avp.client.render.items;

import org.avp.AliensVsPredator;
import org.avp.URLs;
import org.avp.item.ItemFirearm;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.ItemRenderer;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.PlayerResource;
import com.arisux.mdxlib.lib.client.render.Texture;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.util.Remote;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderItemM41A extends ItemRenderer
{
    private RenderMotionTrackerScreen motionTracker = new RenderMotionTrackerScreen();

    public RenderItemM41A()
    {
        super(AliensVsPredator.resources().models().M41A);
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
        OpenGL.translate(0.3F, 1F, 0F);
        OpenGL.scale(1F, -1F, 1F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        this.getModelTexMap().draw();
    }

    @Override
    public void renderThirdPerson(ItemStack item, Object... data)
    {
        PlayerResource player = resourceStorage.create(((EntityPlayer) data[1]).getCommandSenderName());
        float glScale = 1.3F;

        if (player != null)
        {
            OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
            OpenGL.rotate(130.0F, 0.0F, 1.0F, 0.0F);
            OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            OpenGL.translate(0.28F, -0.77F, 0.85F);
            OpenGL.scale(glScale, glScale, glScale);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinM41a, player.playerUUID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();
        }
    }

    @Override
    public void renderFirstPerson(ItemStack item, Object... data)
    {
        float displayScale = 0.005F;
        float glScale = 1.6F;

        if (firstPersonRenderCheck(data[1]))
        {
            if (Mouse.isButtonDown(0) && mc.inGameHasFocus)
            {
                OpenGL.translate(-0.1F, 1.44F, -0.595F);
                OpenGL.rotate(102F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(115.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(79F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0.027F, 0F, 0F);
            }
            else
            {
                OpenGL.translate(0.1F, 1.55F, 0.2F);
                OpenGL.rotate(95.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(120.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(79.0F, 0.0F, 0.0F, 1.0F);
            }

            OpenGL.disable(GL11.GL_CULL_FACE);
            OpenGL.scale(glScale, glScale, glScale);
            new Texture(Remote.downloadResource(String.format(URLs.urlSkinM41a, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
            this.getModelTexMap().getModel().render();

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

            if (mc.thePlayer.inventory.hasItem(AliensVsPredator.items().itemMotionTracker))
            {
                OpenGL.translate(-50F, -20F, -50F);
                OpenGL.rotate(-90F, 0F, 1F, 0F);
                OpenGL.scale(0.4F, 0.4F, 0.4F);
                OpenGL.disableLight();
                motionTracker.draw(0, 0, 128, 96);
                OpenGL.enableLight();
            }
        }
    }

    @Override
    public void renderInInventory(ItemStack item, Object... data)
    {
        OpenGL.rotate(45F, 0.0F, 1.0F, 0.0F);
        OpenGL.translate(14F, -7F, 0F);
        OpenGL.scale(20F, 20F, 20F);
        OpenGL.disable(GL11.GL_CULL_FACE);
        new Texture(Remote.downloadResource(String.format(URLs.urlSkinM41a, Game.session().getPlayerID()), this.getModelTexMap().getTexture())).bind();
        this.getModelTexMap().getModel().render();
    }

    public String getAmmoCountDisplayString()
    {
        int ammoCount = ((ItemFirearm) mc.thePlayer.inventory.getCurrentItem().getItem()).getAmmoCount();
        return (ammoCount < 10 ? "0" + ammoCount : String.valueOf(ammoCount));
    }
}
