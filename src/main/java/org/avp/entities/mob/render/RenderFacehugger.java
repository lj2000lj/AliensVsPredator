package org.avp.entities.mob.render;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.entities.EntityMedpod;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.render.facehugger.LocalFaceTransforms;
import org.avp.entities.mob.render.facehugger.VanillaFaceTransforms;
import org.avp.entities.tile.TileEntityCryostasisTube;
import org.avp.entities.tile.render.RenderCryostasisTube;
import org.avp.entities.tile.render.RenderCryostasisTube.CryostasisTubeRenderer;
import org.avp.entities.tile.render.RenderCryostasisTube.ICustomCryostasisRenderer;
import org.avp.event.client.RenderMedpodEvent;
import org.avp.util.EntityRenderTransforms;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderFacehugger extends RenderLivingWrapper implements ICustomCryostasisRenderer
{
    public static ArrayList<EntityRenderTransforms> transforms = new ArrayList<EntityRenderTransforms>();

    public RenderFacehugger(TexturedModel<? extends Model> model)
    {
        super(model);
        new LocalFaceTransforms();
        new VanillaFaceTransforms();
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

    @SideOnly(Side.CLIENT)
    @Override
    public CryostasisTubeRenderer getCustomCryostasisRenderer()
    {
        return new CryostasisTubeRenderer()
        {
            @Override
            public void renderChassis(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                super.renderChassis(renderer, tile, posX, posY, posZ);
            }

            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null)
                {
                    OpenGL.pushMatrix();
                    {
                        if (tile.getVoltage() > 0)
                        {
                            OpenGL.disableLight();
                        }

                        OpenGL.translate(0F, -0.5F, 0F);
                        OpenGL.rotate(90F, 1F, 0F, 0F);
                        RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    }
                    OpenGL.popMatrix();
                }
            }

            @Override
            public void renderTube(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                super.renderTube(renderer, tile, posX, posY, posZ);
            }
        };
    }
}
