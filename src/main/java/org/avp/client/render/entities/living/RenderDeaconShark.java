package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.entities.living.EntityDeaconShark;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderDeaconShark extends RenderLivingWrapper<EntityDeaconShark>
{
    public RenderDeaconShark()
    {
        super(AliensVsPredator.resources().models().DEACON_SHARK);
    }

    @Override
    protected void preRenderCallback(EntityDeaconShark entitylivingBase, float partialTicks)
    {
        super.preRenderCallback(entitylivingBase, shadowSize);
        float scale = 1.7F;
        OpenGL.scale(scale, scale, scale);
        OpenGL.translate(0, 1, 0);
    }
}
