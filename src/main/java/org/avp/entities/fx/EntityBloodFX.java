package org.avp.entities.fx;

import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBloodFX extends EntityDropParticleFX
{
    //new EntityDropParticleFX(world, host.posX + host.getRNG().nextDouble() - host.getRNG().nextDouble(), host.posY + host.getRNG().nextDouble() - host.getRNG().nextDouble(), host.posZ + host.getRNG().nextDouble() - host.getRNG().nextDouble(), Material.lava)
    private int bobTimer;
    private int color;

    public EntityBloodFX(World worldIn, double posX, double posY, double posZ, int color)
    {
        super(worldIn, posX, posY, posZ, null);
        this.particleMaxAge = (60 * 20) * 3;
        this.color = color;
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
//        this.motionX = (double)(this.rand.nextFloat() * 0.4F + 0.05F);
        float particleSpread = 0.15F;
        this.motionY = (double)(this.rand.nextFloat() * 0.4F + 0.05F);
        this.motionZ = (double)(this.rand.nextFloat() * particleSpread) - (this.rand.nextFloat() * particleSpread);
        this.motionX = (double)(this.rand.nextFloat() * particleSpread) - (this.rand.nextFloat() * particleSpread);
    }


    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.particleAlpha = 1F;
        this.particleRed = 1F;
        this.particleGreen = 0F;
        this.particleBlue = 0F;

        this.motionY -= (double) this.particleGravity;

        if (this.bobTimer-- > 0)
        {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
            this.setParticleTextureIndex(113);
        }
        else
        {
            this.setParticleTextureIndex(112);
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.particleMaxAge-- <= 0)
        {
            this.setDead();
        }

        if (this.onGround)
        {
            this.setParticleTextureIndex(114);
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }

        Material material = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial();

        if (material.isLiquid() || material.isSolid())
        {
            double d0 = (double) ((float) (MathHelper.floor_double(this.posY) + 1) - BlockLiquid.getLiquidHeightPercent(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))));

            if (this.posY < d0)
            {
                this.setDead();
            }
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
        float minU = (float) this.particleTextureIndexX / 16.0F;
        float maxU = minU + 0.0624375F;
        float minV = (float) this.particleTextureIndexY / 16.0F;
        float maxV = minV + 0.0624375F;
        float scale = 0.1F * this.particleScale;

        if (this.particleIcon != null)
        {
            minU = this.particleIcon.getMinU();
            maxU = this.particleIcon.getMaxU();
            minV = this.particleIcon.getMinV();
            maxV = this.particleIcon.getMaxV();
        }

        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);

        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        
//        OpenGL.blendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_DST_ALPHA);
        tessellator.setColorRGBA_F(r, g, b, 1F);
        tessellator.addVertexWithUV((double) (x - p_70539_3_ * scale - p_70539_6_ * scale), (double) (y - p_70539_4_ * scale), (double) (z - p_70539_5_ * scale - p_70539_7_ * scale), (double) maxU, (double) maxV);
        tessellator.addVertexWithUV((double) (x - p_70539_3_ * scale + p_70539_6_ * scale), (double) (y + p_70539_4_ * scale), (double) (z - p_70539_5_ * scale + p_70539_7_ * scale), (double) maxU, (double) minV);
        tessellator.addVertexWithUV((double) (x + p_70539_3_ * scale + p_70539_6_ * scale), (double) (y + p_70539_4_ * scale), (double) (z + p_70539_5_ * scale + p_70539_7_ * scale), (double) minU, (double) minV);
        tessellator.addVertexWithUV((double) (x + p_70539_3_ * scale - p_70539_6_ * scale), (double) (y - p_70539_4_ * scale), (double) (z + p_70539_5_ * scale - p_70539_7_ * scale), (double) minU, (double) maxV);
    }
}
