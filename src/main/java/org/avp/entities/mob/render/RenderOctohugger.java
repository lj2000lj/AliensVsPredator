package org.avp.entities.mob.render;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.entities.EntityMedpod;
import org.avp.entities.mob.EntityOctohugger;
import org.avp.entities.mob.model.ModelOctohugger;
import org.avp.event.client.RenderMedpodEvent;
import org.avp.util.EntityRenderTransforms;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.RenderLivingWrapper;
import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class RenderOctohugger extends RenderLivingWrapper
{
    public static ArrayList<EntityRenderTransforms> transforms = new ArrayList<EntityRenderTransforms>();

    public RenderOctohugger(TexturedModel<? extends Model> model)
    {
        super(model);
    }

    public RenderOctohugger()
    {
        this(AliensVsPredator.resources().models().OCTOHUGGER);
    }

    @Override
    public void doRender(Entity entity, double posX, double posY, double posZ, float yaw, float renderPartialTicks)
    {
        super.doRender(entity, posX, posY, posZ, yaw, renderPartialTicks);
        
        if (entity instanceof EntityOctohugger)
        {
            EntityOctohugger octohugger = (EntityOctohugger) entity;
            
            if (octohugger.getHangingLocation() != null)
            {
                GL11.glLineWidth(4.0F);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glBegin(GL11.GL_LINES); 
                GL11.glVertex3d(posX, posY + 0.83 + ((ModelOctohugger)this.mainModel).getYOffset(), posZ);
                GL11.glVertex3d(posX + octohugger.getHangingLocation().x - octohugger.posX, posY + octohugger.getHangingLocation().y - octohugger.posY, posZ + octohugger.getHangingLocation().z - octohugger.posZ);
                GL11.glEnd();
                GL11.glLineWidth(1.0F);
            }
        }
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float partialTicks)
    {
        EntityOctohugger octohugger = (EntityOctohugger) entityliving;

        this.scale(octohugger, 1.1F);

        if (octohugger != null && octohugger.ridingEntity != null && octohugger.ridingEntity.ridingEntity != null && octohugger.ridingEntity.ridingEntity instanceof EntityMedpod)
        {
            Entity entity = octohugger.ridingEntity;
            EntityMedpod medpod = (EntityMedpod) entity.ridingEntity;

            OpenGL.rotate(medpod.getTileEntity());
            RenderMedpodEvent.instance.getRenderer().transformEntity(medpod, entity, partialTicks);
        }

        if (octohugger.ridingEntity != null && octohugger.ridingEntity instanceof EntityLivingBase)
        {
            for (EntityRenderTransforms transform : transforms)
            {
                if (transform.isApplicable(octohugger.ridingEntity))
                {
                    transform.post(octohugger, partialTicks);
                    break;
                }
            }
        }
    }

    protected void scale(EntityOctohugger octohugger, float glScale)
    {
        OpenGL.translate(0F, 1.2F, 0F);
        
        if (octohugger != null && !octohugger.isFertile() && octohugger.ridingEntity == null)
        {
            OpenGL.scale(1F, -1F, 1F);
            OpenGL.translate(0F, 0.25F, 0F);
        }

        OpenGL.scale(glScale, glScale, glScale);
    }
}
