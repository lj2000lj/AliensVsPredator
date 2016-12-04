package org.avp.event.client;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_CONSTANT_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glDepthMask;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.entities.extended.ExtendedEntityLivingBase;
import org.avp.entities.extended.ExtendedEntityPlayer;
import org.avp.gui.GuiTacticalHUDSettings;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.ScaledResolution;
import com.arisux.mdxlib.lib.client.render.Screen;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.entity.Entities;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class TacticalHUDRenderEvent
{
    public static final TacticalHUDRenderEvent instance = new TacticalHUDRenderEvent();
    private ArrayList<EntityPlayer> playersInHUD = new ArrayList<EntityPlayer>();
    private ExtendedEntityPlayer clientPlayerProperties;
    private GuiCustomButton buttonMarineHelmConfig = new GuiCustomButton(0, 0, 0, 50, 20, "", null);
    private boolean gammaRestored = true;
    private int viewportThreshold = 20;

    public TacticalHUDRenderEvent()
    {
        this.clientPlayerProperties = getProperties();
        this.buttonMarineHelmConfig = new GuiCustomButton(0, 0, 0, 50, 20, "", null);
    }

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event)
    {
        if (Game.minecraft().gameSettings.thirdPersonView == 0)
        {
            if (Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer) != null && Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer).getItem() == AliensVsPredator.items().helmMarine)
            {
                ArrayList<Entity> entities = (ArrayList<Entity>) Entities.getEntitiesInCoordsRange(Game.minecraft().thePlayer.worldObj, Entity.class, new CoordData(Game.minecraft().thePlayer), 30, 30);
                Vec3 p = Game.minecraft().thePlayer.getLookVec();
                float scale = 24.0F;

                OpenGL.pushMatrix();
                {
                    OpenGL.translate(p.xCoord, p.yCoord, p.zCoord);
                    OpenGL.scale(scale, scale, scale);

                    if (Game.minecraft().thePlayer != null && clientPlayerProperties != null)
                    {
                        for (Entity entity : entities)
                        {
                            if (entity != null && (Entities.canEntityBeSeenBy(entity, Game.minecraft().thePlayer) || !clientPlayerProperties.isEntityCullingEnabled()) && entity instanceof EntityLivingBase)
                            {
                                ExtendedEntityLivingBase livingProperties = ExtendedEntityLivingBase.get((EntityLivingBase) entity);

                                if (livingProperties.doesEntityContainEmbryo())
                                {
                                    int lifeTimeTicks = livingProperties.getEmbryo().getGestationPeriod() - livingProperties.getEmbryo().getTicksExisted();
                                    int lifeTimeSeconds = lifeTimeTicks / 20;

                                    Vec3 t = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ).addVector(0, entity.getEyeHeight() / 2, 0);
                                    t = t.subtract(Game.minecraft().thePlayer.getPosition(Game.partialTicks()));
                                    Vec3 tmp = p.addVector(t.xCoord, t.yCoord, t.zCoord).normalize();
                                    Vec3 res = p.addVector(tmp.xCoord, tmp.yCoord, tmp.zCoord);

                                    OpenGL.pushMatrix();
                                    {
                                        OpenGL.disable(GL11.GL_ALPHA_TEST);
                                        OpenGL.enable(GL_DEPTH_TEST);
                                        GL11.glDepthFunc(GL11.GL_ALWAYS);
                                        OpenGL.blendClear();
                                        OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
                                        OpenGL.disableLight();
                                        OpenGL.disableLightMapping();
                                        OpenGL.translate(p.xCoord, p.yCoord, p.zCoord);
                                        OpenGL.translate(-res.xCoord, -res.yCoord, -res.zCoord);
                                        OpenGL.rotate(-Game.minecraft().thePlayer.rotationYaw - 180, 0, 1, 0);
                                        OpenGL.rotate(-Game.minecraft().thePlayer.rotationPitch, 1, 0, 0);

                                        OpenGL.pushMatrix();
                                        {
                                            OpenGL.rotate(Game.minecraft().thePlayer.rotationYaw - 180, 0, 1, 0);
                                            float indicatorScale = 0.05F;
                                            OpenGL.blendClear();
                                            OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
                                            OpenGL.scale(indicatorScale, indicatorScale, indicatorScale);
                                            OpenGL.rotate(-Game.minecraft().thePlayer.rotationYaw, 0, 1, 0);

                                            if (livingProperties.doesEntityContainEmbryo())
                                            {
                                                OpenGL.color4i(0xFFFF0000);
                                                Draw.drawResourceCentered(AliensVsPredator.resources().INFECTION_INDICATOR, 0, -1, 2, 2, 255, 0, 0, 255);
                                            }

                                            int color = livingProperties.doesEntityContainEmbryo() || livingProperties.getEntityLivingBase() instanceof IMob ? 0xFFFF0000 : 0xFF00AAFF;
                                            int textMultiplier = 10;
                                            int textX = 15;
                                            int textY = -28 + textMultiplier;
                                            float textScale = 0.0625F;
                                            OpenGL.rotate(180F, 0F, 1F, 0F);
                                            OpenGL.scale(textScale, -textScale, textScale);

                                            // RenderUtil.drawString(livingProperties.getEntityLivingBase().getCommandSenderName(), textX, textY += textMultiplier, color, false);
                                            Draw.drawString(((int) livingProperties.getEntityLivingBase().getDistanceToEntity(Game.minecraft().thePlayer)) + "m", textX, textY += textMultiplier, color, false);

                                            if (livingProperties.doesEntityContainEmbryo())
                                            {
                                                Draw.drawString("Analysis: 1 Foreign Organism(s) Detected", textX, textY += textMultiplier, 0xFFFF0000, false);
                                                Draw.drawString(lifeTimeSeconds / 60 + "." + lifeTimeSeconds % 60 + " Minute(s) Estimated Life Time", textX, textY += textMultiplier, 0xFFFF0000, false);
                                            }
                                            else
                                            {
                                                Draw.drawCenteredRectWithOutline(0, 0, 16, 16, 1, 0x00000000, color);
                                            }
                                        }
                                        OpenGL.popMatrix();

                                        OpenGL.enableLightMapping();
                                        OpenGL.enableLight();
                                        OpenGL.enable(GL11.GL_DEPTH_TEST);
                                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                                        OpenGL.enable(GL11.GL_ALPHA_TEST);
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                                    }
                                    OpenGL.popMatrix();
                                }
                            }
                        }
                    }
                }
                OpenGL.popMatrix();
            }
        }
    }

    @SubscribeEvent
    public void renderTickOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
            {
                if (Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer) != null && Game.minecraft().gameSettings.thirdPersonView == 0 && Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer).getItem() == AliensVsPredator.items().helmMarine)
                {
                    ExtendedEntityPlayer playerProperties = ExtendedEntityPlayer.get(Game.minecraft().thePlayer);

                    this.gammaRestored = false;
                    LightmapUpdateEvent.instance.gammaValue = playerProperties.isNightvisionEnabled() ? 8F : 0F;
                    this.scanForNearbyPlayers();
                    OpenGL.disableLight();
                    OpenGL.disableLightMapping();

                    OpenGL.enable(GL_BLEND);
                    OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
                    OpenGL.disable(GL_DEPTH_TEST);
                    glDepthMask(false);
                    OpenGL.color(1F, 1F, 1F, 1F);
                    OpenGL.disable(GL_ALPHA_TEST);
                    AliensVsPredator.resources().BLUR_TACTICAL_HUD.bind();
                    Draw.drawQuad(0, 0, Screen.scaledDisplayResolution().getScaledWidth(), Screen.scaledDisplayResolution().getScaledHeight());
                    glDepthMask(true);
                    OpenGL.enable(GL_DEPTH_TEST);
                    OpenGL.enable(GL_ALPHA_TEST);
                    OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
                    OpenGL.disable(GL_BLEND);

                    this.drawInfoBar();
                    this.drawImpregnationIndicator(clientPlayerProperties);
                    this.drawPlayerScanner();
                }
                else if (!gammaRestored)
                {
                    this.gammaRestored = true;
                    LightmapUpdateEvent.instance.gammaValue = 0F;
                }
            }
        }
    }

    @SubscribeEvent
    public void renderTick(RenderTickEvent event)
    {
        if (Game.minecraft().thePlayer != null)
        {
            this.renderInventoryElements();
        }
    }

    public void renderInventoryElements()
    {
        if (Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer) != null && Inventories.getHelmSlotItemStack(Game.minecraft().thePlayer).getItem() == AliensVsPredator.items().helmMarine)
        {
            if (Game.minecraft().currentScreen instanceof GuiInventory || Game.minecraft().currentScreen instanceof GuiChat)
            {
                buttonMarineHelmConfig.displayString = "Configure";
                buttonMarineHelmConfig.tooltip = "Click to configure the Tactical HUD.";
                buttonMarineHelmConfig.width = 70;
                buttonMarineHelmConfig.baseColor = 0x44000000;
                buttonMarineHelmConfig.xPosition = Screen.scaledDisplayResolution().getScaledWidth() - buttonMarineHelmConfig.width;
                buttonMarineHelmConfig.yPosition = 0;
                buttonMarineHelmConfig.drawButton();
                buttonMarineHelmConfig.setAction(new IAction()
                {
                    @Override
                    public void perform(GuiCustomButton button)
                    {
                        Game.minecraft().displayGuiScreen(new GuiTacticalHUDSettings(Game.minecraft().currentScreen));
                    }
                });
            }
        }
    }

    public ExtendedEntityPlayer getProperties()
    {
        return Game.minecraft() != null ? Game.minecraft().thePlayer != null ? this.clientPlayerProperties = ExtendedEntityPlayer.get(Game.minecraft().thePlayer) : null : null;
    }

    public void changeChannel(String channel)
    {
        this.clientPlayerProperties.setBroadcastChannel(channel);
    }

    public void drawInfoBar()
    {
        ScaledResolution res = Screen.scaledDisplayResolution();
        int guiScale = Game.minecraft().gameSettings.guiScale;
        float scale = guiScale == 0 ? res.getScaleFactor() * 0.25F : (guiScale == 1 ? res.getScaleFactor() * 1F : res.getScaleFactor() * 0.5F);
        int batteryPercent = (int) 95; // (Game.minecraft().thePlayer.worldObj.getWorldTime() % 100 + 10) / 10 * 10;
        int barPadding = 90;

        int hourOfMinecraftDay = (int) (Math.floor(Game.minecraft().thePlayer.worldObj.getWorldTime() / 1000 + 8) % 24);
        int minuteOfMinecraftDay = (int) (60 * Math.floor(Game.minecraft().thePlayer.worldObj.getWorldTime() % 1000) / 1000);

        String timeString = String.format("%02dH%02dM", hourOfMinecraftDay, minuteOfMinecraftDay);
        String fpsString = Game.minecraft().debug.substring(0, Game.minecraft().debug.indexOf(" fps")) + " FPS";
        String barString = timeString + " [" + fpsString + "]";

        OpenGL.pushMatrix();
        {
            OpenGL.scale(scale, scale, scale);
            OpenGL.enable(GL_BLEND);
            OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
            Draw.drawString(barString, barPadding, 45, 0xFF00AAFF, false);
            OpenGL.pushMatrix();
            {
                float nameScale = 1.5F;
                OpenGL.scale(nameScale, nameScale, nameScale);
                Draw.drawString("[" + batteryPercent + "%%] " + Game.minecraft().thePlayer.getCommandSenderName(), (int) ((barPadding) / nameScale), (int) (30 / nameScale), 0xFF00AAFF, false);
            }
            OpenGL.popMatrix();

            OpenGL.pushMatrix();
            {
                int hOffset = 5;
                int btX = 6;
                int btY = 3;
                int btWidth = 128;
                int btHeight = 64;
                int batteryWidth = btWidth / 2 * (batteryPercent + hOffset) / 100;
                float maxU = (batteryPercent + hOffset) / 100F / 2F;
                AliensVsPredator.resources().BATTERY_INDICATOR.bind();
                Draw.drawQuad(btX, btY, btWidth, btHeight, 0F, 1F, 0F, 0.5F);
                Draw.drawQuad(btX, btY, batteryWidth, btHeight, 0F, maxU, 0.5F, 1F);
            }
            OpenGL.popMatrix();
        }
        OpenGL.popMatrix();
    }

    public void drawImpregnationIndicator(ExtendedEntityPlayer playerProperties)
    {
        ExtendedEntityLivingBase livingProperties = ExtendedEntityLivingBase.get(playerProperties.getPlayer());

        if (livingProperties.doesEntityContainEmbryo() && livingProperties.getEntityLivingBase().worldObj.getWorldTime() % 20 <= 10)
        {
            ScaledResolution res = Screen.scaledDisplayResolution();

            if (livingProperties.getEmbryo() != null)
            {
                int lifeTimeTicks = livingProperties.getEmbryo().getGestationPeriod() - livingProperties.getEmbryo().getTicksExisted();
                int lifeTimeSeconds = lifeTimeTicks / 20;
                int iconSize = 80;

                OpenGL.enable(GL_BLEND);
                OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
                OpenGL.pushMatrix();
                {
                    float scale = 1.5F;
                    OpenGL.scale(scale, scale, scale);
                    Draw.drawStringAlignRight("Analysis Complete:", (int) ((res.getScaledWidth() / scale) - (iconSize / scale)), (int) (30 / scale), 0xFFFF0000);
                }
                OpenGL.popMatrix();
                Draw.drawStringAlignRight("Foreign Organism(s) Detected (1)", res.getScaledWidth() - iconSize, 45, 0xFFFF0000);
                Draw.drawStringAlignRight("Xenomorphic Embryo Class A", res.getScaledWidth() - iconSize, 55, 0xFFFF0000);

                if (!playerProperties.getPlayer().capabilities.isCreativeMode)
                {
                    Draw.drawStringAlignRight(lifeTimeSeconds / 60 + " Minute(s) Estimated Until Death", res.getScaledWidth() - iconSize, 65, 0xFFFFFFFF);
                }

                OpenGL.color4i(0xFFFF0000);
                AliensVsPredator.resources().INFECTION_INDICATOR.bind();
                Draw.drawQuad(res.getScaledWidth() - iconSize, 0, iconSize, iconSize);
            }
        }
    }

    public void scanForNearbyPlayers()
    {
        EntityPlayer playerFound = (EntityPlayer) Game.minecraft().thePlayer.worldObj.findNearestEntityWithinAABB(EntityPlayer.class, Game.minecraft().thePlayer.boundingBox.expand(this.getProperties().getBroadcastRadius(), 128.0D, this.getProperties().getBroadcastRadius()), Game.minecraft().thePlayer);

        if (playerFound != null)
        {
            ExtendedEntityPlayer extendedPlayer = (ExtendedEntityPlayer) playerFound.getExtendedProperties(ExtendedEntityPlayer.IDENTIFIER);

            if (!isPlayerInHUD(playerFound) && extendedPlayer.getBroadcastChannel().equalsIgnoreCase(this.clientPlayerProperties.getBroadcastChannel()))
            {
                playersInHUD.add(playerFound);
            }
        }
    }

    public void drawPlayerScanner()
    {
        for (int x = 0; x < playersInHUD.size(); x++)
        {
            EntityPlayer player = playersInHUD.get(x);
            ExtendedEntityPlayer extendedPlayer = (ExtendedEntityPlayer) player.getExtendedProperties(ExtendedEntityPlayer.IDENTIFIER);

            if (player != null || player != null && !extendedPlayer.getBroadcastChannel().equalsIgnoreCase(this.clientPlayerProperties.getBroadcastChannel()))
            {
                playersInHUD.remove(x);
            }

            if (x <= viewportThreshold && player != null)
            {
                int barSpace = 15;
                int signal = (int) Game.minecraft().thePlayer.getDistanceToEntity(player);
                int maxSignal = extendedPlayer.getBroadcastRadius() <= this.clientPlayerProperties.getBroadcastRadius() ? extendedPlayer.getBroadcastRadius() : this.clientPlayerProperties.getBroadcastRadius();
                int pxMultiplier = signal >= maxSignal / 1.3 ? 5 : signal >= maxSignal / 2 ? 4 : signal >= maxSignal / 3 ? 3 : signal >= maxSignal / 4 ? 2 : signal >= maxSignal / 5 ? 1 : signal >= maxSignal / 6 ? 0 : 0;

                Draw.drawRect(Screen.scaledDisplayResolution().getScaledWidth() - 111, 40 + barSpace * x - 5, 120, 2, 0xAA00AAFF);
                Draw.drawRect(Screen.scaledDisplayResolution().getScaledWidth() - 111, 42 + barSpace * x - 5, 2, 9, 0xAA00AAFF);

                if (Game.minecraft().thePlayer.getDistanceToEntity(player) <= this.clientPlayerProperties.getBroadcastRadius() && signal <= maxSignal / 1.3)
                {
                    OpenGL.color(1F, 1F, 1F, 1F);
                    Draw.bindTexture(Gui.icons);
                    Draw.drawQuad(Screen.scaledDisplayResolution().getScaledWidth() - 135, 36 + barSpace * x, 10, 8, 0, (176 + pxMultiplier * 8));

                    Draw.drawProgressBar(player.getCommandSenderName(), (int) player.getMaxHealth(), (int) player.getHealth(), Screen.scaledDisplayResolution().getScaledWidth() - 105, 40 + barSpace * x, 100, 1, 0, 0xFF00AAFF, false);
                    Draw.drawPlayerFace(player.getCommandSenderName(), Screen.scaledDisplayResolution().getScaledWidth() - 122, 35 + barSpace * x, 11, 11);
                }
                else
                {
                    OpenGL.enable(GL_BLEND);
                    OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);
                    Draw.drawRect(Screen.scaledDisplayResolution().getScaledWidth() - 105, 40 + barSpace * x, 100, 5, 0x66EEEEEE);
                    Draw.drawString("Connection lost...", Screen.scaledDisplayResolution().getScaledWidth() - 100, 39 + barSpace * x, 0xFFFFFFFF, true);
                }
            }
        }
    }

    public boolean isPlayerInHUD(EntityPlayer player)
    {
        if (player != null)
        {
            for (int x = 0; x < playersInHUD.size(); x++)
            {
                if (playersInHUD.get(x) != null && player.getCommandSenderName().equals(playersInHUD.get(x).getCommandSenderName()))
                    return true;
            }
        }

        return false;
    }

    public int getViewportThreshold()
    {
        return viewportThreshold;
    }

    public void setViewportThreshold(int viewportThreshold)
    {
        this.viewportThreshold = viewportThreshold;
    }
}
