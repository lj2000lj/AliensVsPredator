package org.avp.client.render.entities.living;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.client.render.RenderLivingHook;
import org.avp.client.render.util.EntityRenderTransforms;
import org.avp.entities.EntityMedpod;
import org.avp.entities.living.EntityFacehugger;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderFacehugger extends RenderLivingWrapper<EntityFacehugger>
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
    public void doRender(EntityFacehugger entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
    }

    @Override
    protected void preRenderCallback(EntityFacehugger facehugger, float partialTicks)
    {
        this.scale(facehugger, 0.9F);

        if (facehugger != null && facehugger.getRidingEntity()== null)
        {
            if (facehugger.motionY > 0 || facehugger.motionY < -0.1)
            {
                OpenGL.rotate(-45F, 1, 0, 0);
            }
        }

        if (facehugger != null && facehugger.getRidingEntity()!= null && facehugger.getRidingEntity().getRidingEntity()!= null && facehugger.getRidingEntity().getRidingEntity()instanceof EntityMedpod)
        {
            Entity entity = facehugger.getRidingEntity();
            EntityMedpod medpod = (EntityMedpod) entity.getRidingEntity();

            OpenGL.rotate(medpod.getTileEntity());
            RenderLivingHook.instance.getRenderer().transformEntity(medpod, entity, partialTicks);
        }

        if (facehugger.getRidingEntity()!= null && facehugger.getRidingEntity()instanceof EntityLivingBase)
        {
            for (EntityRenderTransforms transform : transforms)
            {
                if (transform.isApplicable(facehugger.getRidingEntity()))
                {
                    transform.post(facehugger, partialTicks);
                    break;
                }
            }
        }
    }

    protected void scale(EntityFacehugger facehugger, float glScale)
    {
        if (facehugger != null && !facehugger.isFertile() && facehugger.getRidingEntity()== null)
        {
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0F, 0.25F, 0F);
        }

        OpenGL.scale(glScale, glScale, glScale);
    }
}
