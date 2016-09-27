package org.avp.entities.mob.render;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.entities.EntityMedpod;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.event.client.RenderMedpodEvent;
import org.avp.util.EntityRenderTransforms;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderFacehugger extends RenderLivingWrapper
{
    public static ArrayList<EntityRenderTransforms> transforms = new ArrayList<EntityRenderTransforms>();

    public RenderFacehugger(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    public RenderFacehugger()
    {
        this(AliensVsPredator.resources().models().FACEHUGGER);
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float partialTicks)
    {
        EntityFacehugger facehugger = (EntityFacehugger) entityliving;

        this.scale(facehugger, 0.9F);

        if (facehugger != null && facehugger.ridingEntity == null)
        {
            if (facehugger.motionY > 0 || facehugger.motionY < -0.1)
            {
                OpenGL.rotate(-45F, 1, 0, 0);
            }
        }

        if (facehugger != null && facehugger.ridingEntity != null && facehugger.ridingEntity.ridingEntity != null && facehugger.ridingEntity.ridingEntity instanceof EntityMedpod)
        {
            Entity entity = facehugger.ridingEntity;
            EntityMedpod medpod = (EntityMedpod) entity.ridingEntity;

            OpenGL.rotate(medpod.getTileEntity());
            RenderMedpodEvent.instance.getRenderer().transformEntity(medpod, entity, partialTicks);
        }

        if (facehugger.ridingEntity != null && facehugger.ridingEntity instanceof EntityLivingBase)
        {
            for (EntityRenderTransforms transform : transforms)
            {
                if (transform.isApplicable(facehugger.ridingEntity))
                {
                    transform.post(facehugger, partialTicks);
                    break;
                }
            }
        }
    }

    protected void scale(EntityFacehugger facehugger, float glScale)
    {
        if (facehugger != null && !facehugger.isFertile() && facehugger.ridingEntity == null)
        {
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0F, 0.25F, 0F);
        }

        OpenGL.scale(glScale, glScale, glScale);
    }
}
