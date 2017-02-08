package org.avp.event.client.render;

import org.avp.entities.EntityMedpod;
import org.avp.entities.tile.render.RenderMedpod;
import org.avp.util.EntityRenderTransforms;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.util.MDXMath;
import com.arisux.mdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

public class RenderLivingHook
{
    public static final RenderLivingHook instance           = new RenderLivingHook();
    public RenderLiving                  renderer           = new RenderLiving();
    public RenderPlayerPlasmaCannon      renderplasmacannon = new RenderPlayerPlasmaCannon();

    @SubscribeEvent
    public void update(ClientTickEvent event)
    {
        renderplasmacannon.update(event, Game.minecraft(), Game.minecraft().theWorld);
    }

    @SubscribeEvent
    public void renderHand(RenderHandEvent event)
    {
        renderplasmacannon.renderFirstPerson(event, event.partialTicks);
    }

    @SubscribeEvent
    public void entityRenderEventPre(RenderLivingEvent.Pre event)
    {
        if (event.entity != null)
        {
            if (event.entity instanceof EntityPlayer)
            {
                renderplasmacannon.render(event, Game.partialTicks());
            }

            if (event.entity.ridingEntity instanceof EntityMedpod)
            {
                event.setCanceled(true);
                renderer.render(event.entity, event.renderer, event.x, event.y, event.z, Game.partialTicks());
            }
        }
    }

    @SubscribeEvent
    public void entityRenderEventPost(RenderLivingEvent.Post event)
    {
        if (event.entity != null)
        {
            if (event.entity instanceof EntityPlayer)
            {
                renderplasmacannon.render(event, Game.partialTicks());
            }

            if (event.entity.ridingEntity instanceof EntityMedpod)
            {
                event.setCanceled(true);
            }
        }
    }

    public RenderLiving getRenderer()
    {
        return renderer;
    }

    public class RenderLiving extends RendererLivingEntity
    {
        private RendererLivingEntity cache;

        public RenderLiving()
        {
            super(null, 0F);
            this.renderManager = RenderManager.instance;
        }

        @Override
        protected ResourceLocation getEntityTexture(Entity entity)
        {
            ResourceLocation resource = Entities.getEntityTexture(this.cache, entity);

            if (entity instanceof AbstractClientPlayer)
            {
                resource = ((AbstractClientPlayer) entity).getLocationSkin();
            }

            return resource;
        }

        public void render(EntityLivingBase entity, RendererLivingEntity renderer, double posX, double posY, double posZ, float partialTicks)
        {
            EntityMedpod medpod = (EntityMedpod) entity.ridingEntity;

            if (this.cache != renderer)
            {
                this.cache = renderer;
                this.mainModel = Model.getMainModel(renderer);
            }

            OpenGL.pushMatrix();
            {
                float rotationYaw = MDXMath.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
                float rotationYawHead = MDXMath.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
                float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                float idleProgress = entity.ticksExisted + partialTicks;
                float swingProgressPrev = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                float swingProgress = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                {
                    swingProgress *= 3.0F;
                }

                if (swingProgressPrev > 1.0F)
                {
                    swingProgressPrev = 1.0F;
                }

                this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
                this.mainModel.isRiding = false;
                this.mainModel.isChild = entity.isChild();

                if (this.renderPassModel != null)
                {
                    this.renderPassModel.isChild = this.mainModel.isChild;
                }

                OpenGL.enableBlend();
                OpenGL.blendClear();
                OpenGL.translate(posX, posY, posZ);
                this.transformEntity(medpod, entity, partialTicks);
                this.mainModel.setLivingAnimations(entity, swingProgress, swingProgressPrev, partialTicks);
                Draw.bindTexture(this.getEntityTexture(entity));
                this.mainModel.render(entity, swingProgress, swingProgressPrev, idleProgress, rotationYawHead - rotationYaw, rotationPitch, Model.DEFAULT_BOX_TRANSLATION);
                OpenGL.disableBlend();
            }
            OpenGL.popMatrix();
        }

        @Deprecated
        protected void renderModel(EntityLivingBase entity, float swingProgress, float swingProgressPrev, float idleProgress, float rotationYawHead, float rotationPitch, float boxTranslation)
        {
            this.mainModel.setRotationAngles(swingProgress, swingProgressPrev, idleProgress, rotationYawHead, rotationPitch, boxTranslation, entity);
        }

        public void transformEntity(EntityMedpod medpod, Entity inMedpod, float partialTicks)
        {
            float rotation = (float) medpod.getTileEntity().getDoorProgress() * 45 / medpod.getTileEntity().getMaxDoorProgress();

            OpenGL.rotate(medpod.getTileEntity());
            OpenGL.scale(-1.0F, -1.0F, 1.0F);

            for (EntityRenderTransforms transform : RenderMedpod.transforms)
            {
                if (transform.isApplicable(inMedpod))
                {
                    transform.pre(inMedpod, partialTicks);
                    break;
                }
            }
            
            OpenGL.translate(0.0F, -24.0F * Model.DEFAULT_BOX_TRANSLATION + 1.5F, 0.0F);
            OpenGL.rotate(rotation - 90F, 1F, 0F, 0F);
            
            for (EntityRenderTransforms transform : RenderMedpod.transforms)
            {
                if (transform.isApplicable(inMedpod))
                {
                    transform.post(inMedpod, partialTicks);
                    break;
                }
            }
        }
    }
}
