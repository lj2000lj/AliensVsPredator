package org.avp.dimension.varda;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.dimension.BiomeLVBase;
import org.avp.dimension.DimensionUtil;
import org.avp.event.VardaStormHandler;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.Color;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.GameResources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;

public class SkyProviderVarda extends IRenderHandler
{
    private Tessellator tessellator    = Tessellator.instance;
    private Color       skyColor       = new Color(0.11F, 0.225F, 0.265F, 1F);
    protected Color     cloudColor     = new Color(0.075F, 0.1F, 0.15F, 0.75F);
    private float[]     stormXCoords   = null;
    private float[]     stormZCoords   = null;

    public int          starGLCallList = GLAllocation.generateDisplayLists(3);
    public int          glSkyList;

    public SkyProviderVarda()
    {
        GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
        DimensionUtil.renderStars(new Random(10842L), 6000, 100);
        GL11.glEndList();

        this.glSkyList = (this.starGLCallList + 1);
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        {
            int levels = 64;
            int size = 256 / levels + 2;
            float skylineHeight = 60.0F;

            for (int x = -levels * size; x <= levels * size; x += levels)
            {
                for (int z = -levels * size; z <= levels * size; z += levels)
                {
                    tessellator.startDrawingQuads();
                    tessellator.addVertex(x + 0.000F, skylineHeight, z + 0.000F);
                    tessellator.addVertex(x + levels, skylineHeight, z + 0.000F);
                    tessellator.addVertex(x + levels, skylineHeight, z + levels);
                    tessellator.addVertex(x + 0.000F, skylineHeight, z + levels);
                    tessellator.draw();
                }
            }
        }
        GL11.glEndList();
    }

    @Override
    public void render(float renderPartialTicks, WorldClient world, Minecraft mc)
    {
        if (world.provider instanceof ProviderVarda)
        {
            ProviderVarda provider = (ProviderVarda) world.provider;

            if (provider.getStormHandler().isStormActive(world))
            {
                this.renderStorm(renderPartialTicks);
            }

            OpenGL.disable(GL11.GL_TEXTURE_2D);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glDepthMask(false);
            OpenGL.enable(GL11.GL_FOG);
            GL11.glColor3f(skyColor.r, skyColor.g, skyColor.b);

            /** Render Sky **/
            GL11.glCallList(this.glSkyList);
            OpenGL.disable(GL11.GL_FOG);
            OpenGL.disable(GL11.GL_ALPHA_TEST);
            OpenGL.enable(GL11.GL_BLEND);
            OpenGL.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            OpenGL.color(1.0F, 1.0F, 1.0F, provider.getStarBrightness(renderPartialTicks) * 2);

            /** Render Stars **/
            GL11.glCallList(this.starGLCallList);
            OpenGL.enable(GL11.GL_TEXTURE_2D);
            OpenGL.blendFunc(GL11.GL_SRC_ALPHA, 1);

            OpenGL.pushMatrix();
            {
                float scale = 30.0F;
                OpenGL.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
                OpenGL.rotate(world.getCelestialAngle(renderPartialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
                Draw.bindTexture(GameResources.SKY_SUN);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-scale, 150.0D, -scale, 0.0D, 0.0D);
                tessellator.addVertexWithUV(scale, 150.0D, -scale, 1.0D, 0.0D);
                tessellator.addVertexWithUV(scale, 150.0D, scale, 1.0D, 1.0D);
                tessellator.addVertexWithUV(-scale, 150.0D, scale, 0.0D, 1.0D);
                tessellator.draw();
            }
            OpenGL.popMatrix();

            OpenGL.pushMatrix();
            {
                float scale = 275.0F;
                OpenGL.translate(30F, 0F, 0F);
                OpenGL.rotate(DimensionUtil.calculateCelestialAngle(world.getWorldTime(), renderPartialTicks) * 360.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
                OpenGL.rotate(DimensionUtil.calculateCelestialAngle(world.getWorldTime(), renderPartialTicks) * 360.0F, 10.0F, -6.0F, -20.0F);
                OpenGL.rotate(135F, 0.0F, 1.0F, 0.0F);
                Draw.bindTexture(AliensVsPredator.resources().SKY_CALPAMOS);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-scale, 150.0D, -scale, 0.0D, 0.0D);
                tessellator.addVertexWithUV(scale, 150.0D, -scale, 1.0D, 0.0D);
                tessellator.addVertexWithUV(scale, 150.0D, scale, 1.0D, 1.0D);
                tessellator.addVertexWithUV(-scale, 150.0D, scale, 0.0D, 1.0D);
                tessellator.draw();
            }
            OpenGL.popMatrix();

            OpenGL.disable(GL11.GL_BLEND);
            OpenGL.enable(GL11.GL_ALPHA_TEST);
            OpenGL.enable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);

            if (Game.minecraft().gameSettings.shouldRenderClouds())
            {
                OpenGL.pushMatrix();
                {
                    if (Game.minecraft().gameSettings.fancyGraphics)
                    {
                        OpenGL.enable(GL11.GL_FOG);
                    }

                    this.renderClouds(renderPartialTicks);
                    OpenGL.disable(GL11.GL_FOG);
                }
                OpenGL.popMatrix();
            }
        }
    }

    public void renderClouds(float renderPartialTicks)
    {
        for (int cloudPass = 1; cloudPass > 0; cloudPass--)
        {
            float relativeHeight = (float) (Game.minecraft().renderViewEntity.lastTickPosY + (Game.minecraft().renderViewEntity.posY - Game.minecraft().renderViewEntity.lastTickPosY) * renderPartialTicks);
            float cloudSpan = 18.0F;
            float cloudHeight = 7.0F * cloudPass;
            float cloudSpeed = 10;
            double time = Game.minecraft().theWorld.getWorldTime() * cloudSpeed + renderPartialTicks;
            double viewX = (Game.minecraft().renderViewEntity.prevPosX + (Game.minecraft().renderViewEntity.posX - Game.minecraft().renderViewEntity.prevPosX) * renderPartialTicks + time * 0.029999999329447746D) / cloudSpan;
            double viewZ = (Game.minecraft().renderViewEntity.prevPosZ + (Game.minecraft().renderViewEntity.posZ - Game.minecraft().renderViewEntity.prevPosZ) * renderPartialTicks) / cloudSpan + 0.33000001311302185D;
            float cloudY = Game.minecraft().theWorld.provider.getCloudHeight() - relativeHeight;
            viewX -= (MathHelper.floor_double(viewX / 2048.0D)) * 2048;
            viewZ -= (MathHelper.floor_double(viewZ / 2048.0D)) * 2048;
            float scaleUV = 0.00390625F;
            float offsetU = MathHelper.floor_double(viewX) * scaleUV;
            float offsetV = MathHelper.floor_double(viewZ) * scaleUV;
            byte dist = (byte) (Game.minecraft().gameSettings.renderDistanceChunks);
            byte cloudSections = 2;

            OpenGL.disableCullFace();
            Draw.bindTexture(AliensVsPredator.resources().SKY_VARDA_CLOUDS);
            OpenGL.enableBlend();
            OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            OpenGL.scale(cloudSpan, 1.0F, cloudSpan);

            for (int pass = 0; pass < 2; pass++)
            {
                if (pass == 0)
                {
                    GL11.glColorMask(false, false, false, false);
                }
                else
                {
                    GL11.glColorMask(true, true, true, true);
                }

                for (int x = -cloudSections + 1; x <= cloudSections; ++x)
                {
                    for (int z = -cloudSections + 1; z <= cloudSections; ++z)
                    {
                        float cloudU = x * dist;
                        float cloudV = z * dist;
                        float cloudX = cloudU - ((float) (viewX - MathHelper.floor_double(viewX)));
                        float cloudZ = cloudV - ((float) (viewZ - MathHelper.floor_double(viewZ)));

                        tessellator.startDrawingQuads();

                        if (cloudY > -cloudHeight - 1.0F)
                        {
                            tessellator.setColorRGBA_F(cloudColor.r * 0.7F, cloudColor.g * 0.7F, cloudColor.b * 0.7F, cloudColor.a + 0.1F);
                            tessellator.setNormal(0.0F, -1.0F, 0.0F);
                            tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + 0.0F, cloudZ + dist, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + dist, cloudY + 0.0F, cloudZ + dist, (cloudU + dist) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + dist, cloudY + 0.0F, cloudZ + 0.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + 0.0F, cloudZ + 0.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                        }

                        if (cloudY <= cloudHeight + 1.0F)
                        {
                            tessellator.setColorRGBA_F(cloudColor.r, cloudColor.g, cloudColor.b, cloudColor.a + 0.15F);
                            tessellator.setNormal(0.0F, 1.0F, 0.0F);
                            tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + cloudHeight, cloudZ + dist, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + dist, cloudY + cloudHeight, cloudZ + dist, (cloudU + dist) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + dist, cloudY + cloudHeight, cloudZ + 0.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                            tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + cloudHeight, cloudZ + 0.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                        }

                        tessellator.setColorRGBA_F(cloudColor.r * 0.9F, cloudColor.g * 0.9F, cloudColor.b * 0.9F, cloudColor.a);

                        if (x > -1)
                        {
                            tessellator.setNormal(-1.0F, 0.0F, 0.0F);

                            for (int size = 0; size < dist; ++size)
                            {
                                tessellator.addVertexWithUV(cloudX + size + 0.0F, cloudY + 0.0F, cloudZ + dist, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 0.0F, cloudY + cloudHeight, cloudZ + dist, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 0.0F, cloudY + cloudHeight, cloudZ + 0.0F, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 0.0F, cloudY + 0.0F, cloudZ + 0.0F, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                            }
                        }

                        if (x <= 1)
                        {
                            tessellator.setNormal(1.0F, 0.0F, 0.0F);

                            for (int size = 0; size < dist; ++size)
                            {
                                tessellator.addVertexWithUV(cloudX + size + 1.0F, cloudY + 0.0F, cloudZ + dist, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 1.0F, cloudY + cloudHeight, cloudZ + dist, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + dist) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 1.0F, cloudY + cloudHeight, cloudZ + 0.0F, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + size + 1.0F, cloudY + 0.0F, cloudZ + 0.0F, (cloudU + size + 0.5F) * scaleUV + offsetU, (cloudV + 0.0F) * scaleUV + offsetV);
                            }
                        }

                        tessellator.setColorRGBA_F(cloudColor.r * 0.8F, cloudColor.g * 0.8F, cloudColor.b * 0.8F, 0.8F);

                        if (z > -1)
                        {
                            tessellator.setNormal(0.0F, 0.0F, -1.0F);

                            for (int size = 0; size < dist; ++size)
                            {
                                tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + cloudHeight, cloudZ + size + 0.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + dist, cloudY + cloudHeight, cloudZ + size + 0.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + dist, cloudY + 0.0F, cloudZ + size + 0.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + 0.0F, cloudZ + size + 0.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                            }
                        }

                        if (z <= 1)
                        {
                            tessellator.setNormal(0.0F, 0.0F, 1.0F);

                            for (int size = 0; size < dist; ++size)
                            {
                                tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + cloudHeight, cloudZ + size + 1.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + dist, cloudY + cloudHeight, cloudZ + size + 1.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + dist, cloudY + 0.0F, cloudZ + size + 1.0F, (cloudU + dist) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                                tessellator.addVertexWithUV(cloudX + 0.0F, cloudY + 0.0F, cloudZ + size + 1.0F, (cloudU + 0.0F) * scaleUV + offsetU, (cloudV + size + 0.5F) * scaleUV + offsetV);
                            }
                        }

                        tessellator.draw();
                    }
                }
            }

            OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGL.disable(GL11.GL_BLEND);
            OpenGL.enable(GL11.GL_CULL_FACE);
        }
    }

    public void renderStorm(float renderPartialTicks)
    {
        float size = 1F;
        float windSpeed = 1F;
        int stormSize = 32;
        Game.minecraft().entityRenderer.enableLightmap(renderPartialTicks);

        stormXCoords = null;

        if (stormXCoords == null || stormZCoords == null)
        {
            stormXCoords = new float[stormSize * stormSize];
            stormZCoords = new float[stormSize * stormSize];

            for (int zCoord = 0; zCoord < stormSize; ++zCoord)
            {
                for (int xCoord = 0; xCoord < stormSize; ++xCoord)
                {
                    float x = xCoord - 16;
                    float z = zCoord - 16;
                    float sq = MathHelper.sqrt_float(x * x + z * z);
                    stormXCoords[zCoord << 5 | xCoord] = -z / sq;
                    stormZCoords[zCoord << 5 | xCoord] = x / sq;
                }
            }
        }

        EntityLivingBase renderViewEntity = Game.minecraft().renderViewEntity;
        WorldClient worldclient = Game.minecraft().theWorld;
        int posX = MathHelper.floor_double(renderViewEntity.posX);
        int posY = MathHelper.floor_double(renderViewEntity.posY);
        int posZ = MathHelper.floor_double(renderViewEntity.posZ);
        double renderPartialX = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * renderPartialTicks;
        double renderPartialY = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * renderPartialTicks;
        double renderPartialZ = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * renderPartialTicks;
        int renderYFloor = MathHelper.floor_double(renderPartialY);
        byte layers = (byte) (Game.minecraft().gameSettings.fancyGraphics ? (stormSize / 2) - 1 : (stormSize / 4));

        OpenGL.disable(GL11.GL_CULL_FACE);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        OpenGL.enable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
        Draw.bindTexture(AliensVsPredator.resources().SKY_SILICA);

        for (int vZ = posZ - layers; vZ <= posZ + layers; ++vZ)
        {
            for (int vX = posX - layers; vX <= posX + layers; ++vX)
            {
                int j1 = (vZ - posZ + 16) * 32 + vX - posX + 16;
                float rotationX = stormXCoords[j1] * 0.5F;
                float rotationZ = stormZCoords[j1] * 0.5F;
                BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(vX, vZ);

                if (biomegenbase == BiomeLVBase.varda)
                {
                    int stormHeight = worldclient.getPrecipitationHeight(vX, vZ);
                    int minY = posY - (Game.minecraft().gameSettings.fancyGraphics ? 32 : 16);
                    int maxY = posY + (Game.minecraft().gameSettings.fancyGraphics ? 32 : 16);

                    if (minY < stormHeight)
                    {
                        minY = stormHeight;
                    }

                    if (maxY < stormHeight)
                    {
                        maxY = stormHeight;
                    }

                    int vY = stormHeight;

                    if (stormHeight < renderYFloor)
                    {
                        vY = renderYFloor;
                    }

                    if (maxY >= worldclient.provider.getCloudHeight())
                    {
                        maxY = (int) worldclient.provider.getCloudHeight();
                    }

                    if (minY != maxY)
                    {
                        float movement = 0F;
                        movement = ((VardaStormHandler.instance.getStormUpdateCount() + vX * vX * 3121 + vX * 45238971 + vZ * vZ * 418711 + vZ * 13761 & 31) + renderPartialTicks) / 16.0F;
                        movement = movement * windSpeed;
                        tessellator.startDrawingQuads();
                        tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(vX, vY, vZ, 0));
                        tessellator.setColorRGBA_F(0.3F, 0.3F, 0.3F, 1F);
                        tessellator.setTranslation(-renderPartialX * 1.0D, -renderPartialY * 1.0D, -renderPartialZ * 1.0D);
                        tessellator.addVertexWithUV(vX - rotationX + 0.5D, minY, vZ - rotationZ + 0.5D + movement * size, 0.0F * size, minY * size / 4.0F + movement * size);
                        tessellator.addVertexWithUV(vX + rotationX + 0.5D, minY, vZ + rotationZ + 0.5D + movement * size, 1.0F * size, minY * size / 4.0F + movement * size);
                        tessellator.addVertexWithUV(vX + rotationX + 0.5D, maxY, vZ + rotationZ + 0.5D + movement * size, 1.0F * size, maxY * size / 4.0F + movement * size);
                        tessellator.addVertexWithUV(vX - rotationX + 0.5D, maxY, vZ - rotationZ + 0.5D + movement * size, 0.0F * size, maxY * size / 4.0F + movement * size);
                        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                        tessellator.draw();
                    }
                }
            }
        }

        OpenGL.enable(GL11.GL_CULL_FACE);
        OpenGL.disable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        Game.minecraft().entityRenderer.disableLightmap(renderPartialTicks);
    }
}
