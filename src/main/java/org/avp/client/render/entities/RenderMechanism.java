package org.avp.client.render.entities;

import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMechanism extends Render
{
    protected RenderMechanism()
    {
        super(Game.renderManager());
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        ;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }
}
