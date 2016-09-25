package org.avp.event.client;

import org.avp.entities.EntityMedpod;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.EntitySpeciesYautja;
import org.lwjgl.opengl.GL11;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderEntityInMedpodEvent
{
    private RenderLiving renderLiving = new RenderLiving();

    @SubscribeEvent
    public void renderPlayerTickPre(RenderPlayerEvent.Pre event)
    {
        ;
    }

    @SubscribeEvent
    public void renderPlayerTickPost(RenderPlayerEvent.Post event)
    {
        ;
    }

    @SubscribeEvent
    public void entityRenderEventPre(RenderLivingEvent.Pre event)
    {
        if (event.entity != null && event.entity.ridingEntity instanceof EntityMedpod)
        {
            event.setCanceled(true);
            renderLiving.render(event.entity, event.renderer, event.x, event.y, event.z, Game.partialTicks());
        }
    }

    @SubscribeEvent
    public void entityRenderEventPost(RenderLivingEvent.Post event)
    {
        if (event.entity != null && event.entity.ridingEntity instanceof EntityMedpod)
        {
            event.setCanceled(true);
        }
    }

    public static void transformMedpodEntity(EntityMedpod medpod, Entity entity)
    {
        float medpodRotation = (float) medpod.getTileEntity().getDoorProgress() * 45 / medpod.getTileEntity().getMaxDoorProgress();
        OpenGL.translate(0F, 0F, 0.225F);
        OpenGL.translate(0F, 1.5F, -0F);
        OpenGL.rotate(medpodRotation, 1F, 0F, 0F);
        OpenGL.translate(0F, -1.75F + entity.height, 0F);
        OpenGL.translate(0F, -0.5F, 0F);
        OpenGL.rotate(180F, 0F, 1F, 0);
        
        //Entity specific transforms
        
        if (entity instanceof EntityVillager || entity instanceof EntityMarine || entity instanceof EntitySpeciesYautja)
        {
            OpenGL.rotate(90F, 1F, 0F, 0);
        }
        
        if (entity instanceof EntityPlayer)
        {
            OpenGL.translate(0F, -0.5F, 0F);
            OpenGL.rotate(90F, 1F, 0F, 0);
            OpenGL.rotate(180F, 0F, 1F, 0);
            OpenGL.translate(0F, -0.75F, 0F);
        }
    }

    public class RenderLiving extends RendererLivingEntity
    {
        private RendererLivingEntity renderer;

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

        public void render(EntityLivingBase entity, RendererLivingEntity renderer, double posX, double posY, double posZ, float renderPartialTicks)
        {
            EntityMedpod medpod = (EntityMedpod) entity.ridingEntity;

            if (this.renderer != renderer)
            {
                this.renderer = renderer;
                this.mainModel = Model.getMainModel(renderer);
            }

            OpenGL.pushMatrix();
            {
                float rotationYaw = com.arisux.amdxlib.lib.util.Math.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, renderPartialTicks);
                float rotationYawHead = com.arisux.amdxlib.lib.util.Math.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, renderPartialTicks);
                float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * renderPartialTicks;
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
                this.preRenderCallback(entity, renderPartialTicks);
                OpenGL.enableAlphaTest();
                OpenGL.translate(0.0F, -24.0F * Model.DEFAULT_BOX_TRANSLATION - 0.0078125F, 0.0F);
                transformMedpodEntity(medpod, entity);
                this.mainModel.setLivingAnimations(entity, 0F, 0F, renderPartialTicks);
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
                Draw.bindTexture(Entities.getEntityTexture(this.renderer, living));
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
    }
}
