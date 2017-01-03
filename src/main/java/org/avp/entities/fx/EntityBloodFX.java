package org.avp.entities.fx;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBloodFX extends EntityDropParticleFX
{
    private int bobTimer;
    private int color;

    public EntityBloodFX(World worldIn, double posX, double posY, double posZ, int color)
    {
        super(worldIn, posX, posY, posZ, null);
        this.particleMaxAge = ((60 * 20) * 3) + ((this.rand.nextInt(30 * 20)));
        this.color = color;
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
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
    public void renderParticle(Tessellator tessellator, float partialTicks, float rX, float rXZ, float rZ, float rYZ, float rXY)
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

        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);

        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        
        tessellator.setColorRGBA_F(r, g, b, 1F);
        tessellator.addVertexWithUV((double) (x - rX * scale - rYZ * scale), (double) (y - rXZ * scale), (double) (z - rZ * scale - rXY * scale), (double) maxU, (double) maxV);
        tessellator.addVertexWithUV((double) (x - rX * scale + rYZ * scale), (double) (y + rXZ * scale), (double) (z - rZ * scale + rXY * scale), (double) maxU, (double) minV);
        tessellator.addVertexWithUV((double) (x + rX * scale + rYZ * scale), (double) (y + rXZ * scale), (double) (z + rZ * scale + rXY * scale), (double) minU, (double) minV);
        tessellator.addVertexWithUV((double) (x + rX * scale - rYZ * scale), (double) (y - rXZ * scale), (double) (z + rZ * scale - rXY * scale), (double) minU, (double) maxV);
    }
}
