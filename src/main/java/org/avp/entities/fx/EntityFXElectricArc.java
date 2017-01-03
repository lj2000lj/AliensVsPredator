package org.avp.entities.fx;

import java.awt.Color;
import java.util.Random;

import org.avp.AliensVsPredator;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFXElectricArc extends EntityFX
{
    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");

    private Random                        rand             = new Random();
    private Color                         color;
    private float                         rotYaw;
    private float                         rotPitch;
    private float                         density;
    private double                        targetX;
    private double                        targetY;
    private double                        targetZ;
    private double                        displacement;
    private double                        detail;

    public EntityFXElectricArc(World world, double x, double y, double z, double targetX, double targetY, double targetZ, int age, Color c)
    {
        this(world, x, y, z, targetX, targetY, targetZ, 1.6D, 0.2D, age, c, 0.1F);
    }

    public EntityFXElectricArc(World world, double x, double y, double z, double targetX, double targetY, double targetZ, double displacement, double detail, int age, Color c, float density)
    {
        super(world, x, y, z);
        this.color = c;
        this.particleMaxAge = age;
        this.particleAge = 0;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.density = density;
        this.displacement = displacement;
        this.detail = detail;
        this.changeDirection((float) (this.posX - this.targetX), (float) (this.posY - this.targetY), (float) (this.posZ - this.targetZ));
    }

    @Override
    public void renderParticle(Tessellator tessellator, float x, float y, float z, float motionX, float motionY, float motionZ)
    {
        tessellator.draw();
        super.renderParticle(tessellator, x, y, z, motionX, motionY, motionZ);
        drawArc(tessellator, posX, posY, posZ, targetX, targetY, targetZ, displacement, detail, density);
        Draw.bindTexture(particleTextures);
        tessellator.startDrawingQuads();
    }

    private void changeDirection(float x, float y, float z)
    {
        double variance = MathHelper.sqrt_double(x * x + z * z);
        this.rotYaw = ((float) (Math.atan2(x, z) * 180.0D / Math.PI));
        this.rotPitch = ((float) (Math.atan2(y, variance) * 180.0D / Math.PI));
    }

    private void drawArc(Tessellator tessellator, double x1, double y1, double z1, double x2, double y2, double z2, double displacement, double detail, float density)
    {
        if (displacement < detail)
        {
            float x = (float) (x1 - interpPosX);
            float y = (float) (y1 - interpPosY);
            float z = (float) (z1 - interpPosZ);

            float xd = (float) (x1 - x2);
            float yd = (float) (y1 - y2);
            float zd = (float) (z1 - z2);

            this.changeDirection(xd, yd, zd);

            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, z);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_CURRENT_BIT);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(180.0F + this.rotYaw, 0.0F, 0.0F, -1.0F);
            GL11.glRotatef(this.rotPitch, 1.0F, 0.0F, 0.0F);
            
            AliensVsPredator.resources().BLANK.bind();

            double xx = density * -0.15;
            double xx2 = density * -0.15 * 1.0;
            double yy = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);

            for (int i = 0; i < 3; i++)
            {
                GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                tessellator.addVertexWithUV(xx2, yy, 0.0, 0.0, 1.0);
                tessellator.addVertexWithUV(xx, 0, 0.0, 0.0, 0.0);
                tessellator.addVertexWithUV(-xx, 0, 0.0, 1.0, 0.0);
                tessellator.addVertexWithUV(-xx2, yy, 0.0, 1.0, 1.0);
                tessellator.draw();
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
        else
        {
            double midX = (x2 + x1) / 2;
            double midY = (y2 + y1) / 2;
            double midZ = (z2 + z1) / 2;
            midX += (rand.nextFloat() - 0.5) * displacement;
            midY += (rand.nextFloat() - 0.5) * displacement;
            midZ += (rand.nextFloat() - 0.5) * displacement;
            drawArc(tessellator, x1, y1, z1, midX, midY, midZ, displacement / 2, detail, density);
            drawArc(tessellator, x2, y2, z2, midX, midY, midZ, displacement / 2, detail, density);
        }
    }

    @Override
    public void onUpdate()
    {
        particleAge++;

        if (particleAge > particleMaxAge)
        {
            this.setDead();
        }
    }
}
