package org.avp.client.render.entities.living;

import org.avp.AliensVsPredator;
import org.avp.client.model.entities.living.ModelEngineer;
import org.avp.entities.living.EntitySpeciesEngineer;

import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

public class RenderEngineer extends RenderLivingWrapper<EntitySpeciesEngineer, ModelEngineer>
{
    public RenderEngineer()
    {
        super(AliensVsPredator.resources().models().ENGINEER);
    }
    
    public RenderEngineer(TexturedModel<ModelEngineer> model)
    {
        super(model);
    }

    @Override
    public void doRender(EntitySpeciesEngineer entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntitySpeciesEngineer entityliving, float renderPartialTicks)
    {
        OpenGL.scale(1.25F, 1.25F, 1.25F);
    }
}
