package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.api.client.render.IEventRenderer;
import org.avp.api.client.render.IFirstPersonRenderer;
import org.avp.client.input.handlers.InputHandlerPlasmaCannon;
import org.avp.client.model.entities.ModelPlasma;
import org.avp.client.model.items.ModelPlasmaCannon;
import org.avp.entities.SharedPlayer;
import org.avp.entities.EntityMedpod;
import org.avp.item.ItemWristbracer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.Color;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.util.MDXMath;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

public class RenderPlayerPlasmaCannon implements IEventRenderer, IFirstPersonRenderer
{
    private static final TexturedModel<ModelPlasmaCannon> MODEL              = AliensVsPredator.resources().models().PLASMACANNON.clone();
    private static final TexturedModel<ModelPlasmaCannon> MODEL_FIRST_PERSON = MODEL.clone();
    private static final ModelPlasma                      MODEL_PLASMA       = new ModelPlasma().setColor(new Color(0.3F, 0.6F, 1F, 0.7F));

    @Override
    public void update(Event event, Minecraft game, World world)
    {
        if (event instanceof ClientTickEvent)
        {
            ClientTickEvent cte = ((ClientTickEvent) event);

            if (cte.phase == Phase.START)
            {
                ;
            }

            if (cte.phase == Phase.END)
            {
                ;
            }
        }
    }

    @Override
    public void render(Event event, float partialTicks)
    {
        if (event instanceof RenderLivingEvent.Pre)
        {
            RenderLivingEvent.Pre pre = (RenderLivingEvent.Pre) event;

            if (pre.entity instanceof EntityPlayer)
            {
                SharedPlayer player = SharedPlayer.get((EntityPlayer) pre.entity);

                if (!(player.getEntity().ridingEntity instanceof EntityMedpod))
                {
                    if (ItemWristbracer.hasPlasmaCannon(ItemWristbracer.wristbracer(player.getEntity())))
                    {
                        EntityPlayer entity = player.getEntity();
                        float rotationYaw = MDXMath.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
                        float rotationYawHead = MDXMath.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
                        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                        float idleProgress = entity.ticksExisted + partialTicks;
                        float swingProgress = entity.getSwingProgress(partialTicks);
                        float swingProgressPrev = entity.prevSwingProgress;
                        float scale = 0.5F;

                        OpenGL.pushMatrix();
                        OpenGL.scale(scale, -scale, -scale);
                        OpenGL.rotate(rotationYaw, 0F, 1F, 0F);
                        OpenGL.translate(-0.75F, -0.125F, -0.55F);
                        MODEL.bindTexture();
                        MODEL.getModel().render(entity);
                        OpenGL.popMatrix();
                    }
                }
            }
        }

        if (event instanceof RenderLivingEvent.Post)
        {
            RenderLivingEvent.Post pre = (RenderLivingEvent.Post) event;
        }
    }

    @Override
    public void renderFirstPerson(Event event, float partialTicks)
    {
        RenderHandEvent e = (RenderHandEvent) event;
        EntityLivingBase entity = Game.minecraft().thePlayer;
        EntityRenderer entityRenderer = Game.minecraft().entityRenderer;
        ItemRenderer ir = Game.minecraft().entityRenderer.itemRenderer;
        float rotationYawHead = MDXMath.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        float equippedProgress = ir.prevEquippedProgress + (ir.equippedProgress - ir.prevEquippedProgress) * partialTicks;

        if (ItemWristbracer.hasPlasmaCannon(ItemWristbracer.wristbracer(Game.minecraft().thePlayer)))
        {
            if (Game.minecraft().gameSettings.thirdPersonView == 0)
            {
                if (entityRenderer.debugViewDirection <= 0)
                {
                    entityRenderer.enableLightmap(partialTicks);
                    GL11.glMatrixMode(GL11.GL_PROJECTION);
                    GL11.glLoadIdentity();

                    if (entityRenderer.cameraZoom != 1.0D)
                    {
                        OpenGL.translate((float) entityRenderer.cameraYaw, (float) (-entityRenderer.cameraPitch), 0.0F);
                        GL11.glScaled(entityRenderer.cameraZoom, entityRenderer.cameraZoom, 1.0D);
                    }

                    Project.gluPerspective(entityRenderer.getFOVModifier(partialTicks, false), (float) entityRenderer.mc.displayWidth / (float) entityRenderer.mc.displayHeight, 0.05F, entityRenderer.farPlaneDistance * 2.0F);

                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glLoadIdentity();

                    if (entityRenderer.mc.gameSettings.viewBobbing)
                    {
                        entityRenderer.setupViewBobbing(partialTicks);
                    }
                }

                OpenGL.pushMatrix();
                {
                    int brightness = Game.minecraft().theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ), 0);
                    int ltu = brightness % 65536;
                    int ltv = brightness / 65536;
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) ltu / 1.0F, (float) ltv / 1.0F);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    OpenGL.rotate(rotationPitch, 1.0F, 0.0F, 0.0F);
                    OpenGL.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
                    OpenGL.enableStandardItemLighting();
                    OpenGL.popMatrix();
                    EntityPlayerSP entityplayersp = (EntityPlayerSP) entity;
                    float armPitch = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * partialTicks;
                    float armYaw = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * partialTicks;
                    OpenGL.rotate((entity.rotationPitch - armPitch) * 0.1F, 1.0F, 0.0F, 0.0F);
                    OpenGL.rotate((entity.rotationYaw - armYaw) * 0.1F, 0.0F, 1.0F, 0.0F);

                    OpenGL.pushMatrix();
                    {
                        float offset = 0.8F;
                        OpenGL.translate(0.8F * offset, -0.75F * offset - (1.0F - equippedProgress) * 0.6F, -0.9F * offset);
                        OpenGL.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                        OpenGL.enable(GL12.GL_RESCALE_NORMAL);
                        OpenGL.translate(-1.0F, 3.6F, 3.5F);
                        OpenGL.rotate(120.0F, 0.0F, 0.0F, 1.0F);
                        OpenGL.rotate(200.0F, 1.0F, 0.0F, 0.0F);
                        OpenGL.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
                        OpenGL.scale(1.0F, 1.0F, 1.0F);
                        OpenGL.translate(5.6F, 0.0F, 0.0F);
                        float prescale = 2.0F;
                        OpenGL.scale(prescale, prescale, prescale);

                        float modelscale = 0.15F;
                        OpenGL.pushMatrix();
                        OpenGL.translate(0F, 0F, 0F);
                        OpenGL.scale(modelscale, modelscale, modelscale);
                        OpenGL.rotate(50F, 1F, 0F, 0F);
                        OpenGL.rotate(-70F, 0F, 0F, 1F);
                        OpenGL.rotate(45F, 1F, 0F, 0F);
                        OpenGL.translate(-0.1F, -3.95F, 1.25F);
                        // OpenGL.rotate(rotationYawHead, 0F, 1F, 0F);
                        // OpenGL.rotate(rotationPitch, 1F, 0F, 0F);
                        // OpenGL.translate(0F, 0F, -1F);
                        // OpenGL.enableLight();
                        MODEL_FIRST_PERSON.draw();

                        float rotation = (entity.worldObj.getWorldTime() + partialTicks) % 360;
                        double wave = Math.sin(rotation);

                        OpenGL.pushMatrix();
                        {
                            float pp = InputHandlerPlasmaCannon.instance.getChargeSize();
                            float aps = 0.25F;
                            float ps = aps - (aps / (1F + pp));
                            OpenGL.translate(0F, 0.025F, 0.1F - ps);
                            OpenGL.disableCullFace();

                            OpenGL.pushMatrix();
                            OpenGL.translate(wave * 0.005F, 0F, 0F);
                            OpenGL.rotate(rotation * 30, 1, 0, 0);
                            MODEL_PLASMA.setScale(ps / 2.75F);
                            MODEL_PLASMA.render();
                            OpenGL.popMatrix();

                            OpenGL.pushMatrix();
                            OpenGL.translate(wave * -0.005F, 0F, 0F);
                            OpenGL.rotate(rotation * -20, 1, 0, 0);
                            MODEL_PLASMA.setScale(ps / 2.25F);
                            MODEL_PLASMA.render();
                            OpenGL.popMatrix();

                            OpenGL.pushMatrix();
                            OpenGL.translate(wave * 0.005F, 0F, 0F);
                            OpenGL.rotate(rotation * 10, 1, 0, 0);
                            OpenGL.rotate(rotation, 1, 0, 0);
                            MODEL_PLASMA.setScale(ps / 2);
                            MODEL_PLASMA.render();
                            OpenGL.popMatrix();

                            OpenGL.pushMatrix();
                            OpenGL.translate(wave * -0.005F, 0F, 0F);
                            OpenGL.rotate(rotation * -5, 1, 0, 0);
                            OpenGL.rotate(rotation, 1, 0, 0);
                            MODEL_PLASMA.setScale(ps / 1.25F);
                            MODEL_PLASMA.render();
                            OpenGL.popMatrix();

                            OpenGL.pushMatrix();
                            OpenGL.enableCullFace();
                            OpenGL.translate(wave * 0.005F, 0F, 0F);
                            OpenGL.rotate(rotation * 5, 1, 0, 0);
                            OpenGL.rotate(rotation, 1, 0, 0);
                            MODEL_PLASMA.setScale(ps);
                            MODEL_PLASMA.render();
                            OpenGL.popMatrix();
                        }
                        OpenGL.popMatrix();
                    }
                    OpenGL.popMatrix();
                }
                OpenGL.popMatrix();

                entityRenderer.enableLightmap(partialTicks);
                OpenGL.blendClear();
            }
        }
    }
}
