package org.avp.entities.mob.render;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.render.RenderFacehugger.FaceMountRenderer;
import org.avp.entities.mob.render.RenderFacehugger.IFaceMountable;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderEngineer extends RenderLivingWrapper implements IFaceMountable
{
    public RenderEngineer()
    {
        super(AliensVsPredator.resources().models().ENGINEER);
    }
    
    public RenderEngineer(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float renderPartialTicks)
    {
        OpenGL.scale(1.25F, 1.25F, 1.25F);
    }

    @Override
    public FaceMountRenderer getFaceMountRenderer()
    {
        return new FaceMountRenderer(EntityMarine.class)
        {
            @Override
            public void render(EntityFacehugger facehugger, float renderPartialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(115.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0.2F);
            }
        };
    }
}
