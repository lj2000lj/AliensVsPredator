package org.avp.event.client.render;

import org.avp.entities.EntityMedpod;
import org.avp.entities.tile.render.RenderMedpod;
import org.avp.util.EntityRenderTransforms;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
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
            return null;
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
                float rotationYaw = com.arisux.mdxlib.lib.util.MDXMath.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
                float rotationYawHead = com.arisux.mdxlib.lib.util.MDXMath.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
                float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                float idleProgress = 0.17453292F;

                this.mainModel.isRiding = false;
                this.mainModel.isChild = entity.isChild();

                if (this.renderPassModel != null)
                {
                    this.renderPassModel.isChild = this.mainModel.isChild;
                }

                OpenGL.disableCullFace();
                OpenGL.translate(posX, posY, posZ);
                OpenGL.rotate(medpod.getTileEntity());
                OpenGL.scale(-1.0F, -1.0F, 1.0F);
                this.preRenderCallback(entity, partialTicks);
                OpenGL.enableAlphaTest();
                OpenGL.translate(0.0F, -24.0F * Model.DEFAULT_BOX_TRANSLATION - 0.0078125F, 0.0F);
                this.transformEntity(medpod, entity, partialTicks);
                this.mainModel.setLivingAnimations(entity, 0F, 0F, partialTicks);
                this.renderModel(entity, 0F, 0F, idleProgress, rotationYawHead - rotationYaw, rotationPitch, Model.DEFAULT_BOX_TRANSLATION);
                OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
                OpenGL.enableTexture2d();
                OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
                OpenGL.enableCullFace();
            }
            GL11.glPopMatrix();
            this.passSpecialRender(entity, posX, posY, posZ);
        }

        protected void renderModel(EntityLivingBase living, float swingProgress, float swingProgressPrev, float idleProgress, float rotationYawHead, float rotationPitch, float boxTranslation)
        {
            if (living instanceof AbstractClientPlayer)
            {
                AbstractClientPlayer clientPlayer = (AbstractClientPlayer) living;
                Draw.bindTexture(clientPlayer.getLocationSkin());
            }
            else
            {
                Draw.bindTexture(Entities.getEntityTexture(this.cache, living));
            }

            if (!living.isInvisible())
            {
                this.mainModel.render(living, swingProgress, swingProgressPrev, idleProgress, rotationYawHead, rotationPitch, boxTranslation);
            }
            else if (!living.isInvisibleToPlayer(Game.minecraft().thePlayer))
            {
                OpenGL.pushMatrix();
                OpenGL.color(1.0F, 1.0F, 1.0F, 0.15F);
                OpenGL.depthMask(false);
                OpenGL.enableBlend();
                OpenGL.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                this.mainModel.render(living, swingProgress, swingProgressPrev, idleProgress, rotationYawHead, rotationPitch, boxTranslation);
                OpenGL.disableBlend();
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                OpenGL.depthMask(true);
                OpenGL.popMatrix();
            }
            else
            {
                this.mainModel.setRotationAngles(swingProgress, swingProgressPrev, idleProgress, rotationYawHead, rotationPitch, boxTranslation, living);
            }
        }

        public void transformEntity(EntityMedpod medpod, Entity inMedpod, float partialTicks)
        {
            float rotation = (float) medpod.getTileEntity().getDoorProgress() * 45 / medpod.getTileEntity().getMaxDoorProgress();

            for (EntityRenderTransforms transform : RenderMedpod.transforms)
            {
                if (transform.isApplicable(inMedpod))
                {
                    transform.pre(inMedpod, partialTicks);
                    break;
                }
            }

            OpenGL.translate(0F, 0F, 0.225F);
            OpenGL.translate(0F, 1.5F, -0F);
            OpenGL.rotate(rotation, 1F, 0F, 0F);
            OpenGL.translate(0F, -1.75F + inMedpod.height, 0F);
            OpenGL.translate(0F, -0.5F, 0F);
            OpenGL.rotate(180F, 0F, 1F, 0);

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
