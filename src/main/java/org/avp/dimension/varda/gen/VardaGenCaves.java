package org.avp.dimension.varda.gen;

import java.util.Random;

import org.avp.AliensVsPredator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

public class VardaGenCaves extends MapGenBase
{
    protected void generateLargeCaveNode(long seed, int chunkX, int chunkZ, Block[] blocks, double startX, double startY, double startZ)
    {
        generateCaveNode(seed, chunkX, chunkZ, blocks, startX, startY, startZ, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void generateCaveNode(long seed, int chunkX, int chunkZ, Block[] blocks, double startX, double startY, double startZ, float diameterVariance, float diameter, float aggressiveness, int currentDist, int caveLength, double caveHeight)
    {
        double chunkCenterX = chunkX * 16 + 8;
        double chunkCenterZ = chunkZ * 16 + 8;
        float var23 = 0.0F;
        float var24 = 0.0F;
        Random rand = new Random(seed);

        if (caveLength <= 0)
        {
            int var26 = this.range * 16 - 16;
            caveLength = var26 - rand.nextInt(var26 / 4);
        }

        boolean endOfCave = false;

        if (currentDist == -1)
        {
            currentDist = caveLength / 2;
            endOfCave = true;
        }

        int variatedCaveLength = rand.nextInt(caveLength / 2) + caveLength / 4;

        for (boolean steep = rand.nextInt(6) == 0; currentDist < caveLength; currentDist++)
        {
            double variation = 1.5D + MathHelper.sin(currentDist * 3.141593F / caveLength) * diameterVariance * 1.0F;
            double heightVariation = variation * caveHeight;
            float widthVariation = MathHelper.cos(aggressiveness);
            float verticalClimb = MathHelper.sin(aggressiveness);
            startX += MathHelper.cos(diameter) * widthVariation;
            startY += verticalClimb;
            startZ += MathHelper.sin(diameter) * widthVariation;

            if (steep)
            {
                aggressiveness *= 0.92F;
            }
            else
            {
                aggressiveness *= 0.7F;
            }

            aggressiveness += var24 * 0.1F;
            diameter += var23 * 0.1F;
            var24 *= 0.9F;
            var23 *= 0.75F;
            var24 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
            var23 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;

            if ((!endOfCave) && (currentDist == variatedCaveLength) && (diameterVariance > 1.0F) && (caveLength > 0))
            {
                generateCaveNode(rand.nextLong(), chunkX, chunkZ, blocks, startX, startY, startZ, rand.nextFloat() * 0.5F + 0.5F, diameter - 1.570796F, aggressiveness / 3.0F, currentDist, caveLength, 1.0D);
                generateCaveNode(rand.nextLong(), chunkX, chunkZ, blocks, startX, startY, startZ, rand.nextFloat() * 0.5F + 0.5F, diameter + 1.570796F, aggressiveness / 3.0F, currentDist, caveLength, 1.0D);
                return;
            }

            if ((!endOfCave) && (rand.nextInt(4) == 0))
                continue;
            double deltaToCenterX = startX - chunkCenterX;
            double deltaToCenterZ = startZ - chunkCenterZ;
            double remainingLength = caveLength - currentDist;
            double tunnelDiameter = diameterVariance + 2.0F + 16.0F;

            if (deltaToCenterX * deltaToCenterX + deltaToCenterZ * deltaToCenterZ - remainingLength * remainingLength > tunnelDiameter * tunnelDiameter)
            {
                return;
            }

            if ((startX < chunkCenterX - 16.0D - variation * 2.0D) || (startZ < chunkCenterZ - 16.0D - variation * 2.0D) || (startX > chunkCenterX + 16.0D + variation * 2.0D) || (startZ > chunkCenterZ + 16.0D + variation * 2.0D))
                continue;
            int minX = MathHelper.floor_double(startX - variation) - chunkX * 16 - 1;
            int maxX = MathHelper.floor_double(startX + variation) - chunkX * 16 + 1;
            int minHeight = MathHelper.floor_double(startY - heightVariation) - 1;
            int maxHeight = MathHelper.floor_double(startY + heightVariation) + 1;
            int minZ = MathHelper.floor_double(startZ - variation) - chunkZ * 16 - 1;
            int maxZ = MathHelper.floor_double(startZ + variation) - chunkZ * 16 + 1;

            if (minX < 0)
            {
                minX = 0;
            }

            if (maxX > 16)
            {
                maxX = 16;
            }

            if (minHeight < 1)
            {
                minHeight = 1;
            }

            if (maxHeight > 120)
            {
                maxHeight = 120;
            }

            if (minZ < 0)
            {
                minZ = 0;
            }

            if (maxZ > 16)
            {
                maxZ = 16;
            }

            boolean underwater = false;

            for (int x = minX; (!underwater) && (x < maxX); x++)
            {
                for (int z = minZ; (!underwater) && (z < maxZ); z++)
                {
                    for (int height = maxHeight + 1; (!underwater) && (height >= minHeight - 1); height--)
                    {
                        int at = (x * 16 + z) * 128 + height;

                        if ((height < 0) || (height >= 128))
                            continue;
                        
                        if ((blocks[at] == Blocks.flowing_water) || (blocks[at] == Blocks.water))
                        {
                            underwater = true;
                        }

                        if ((height == minHeight - 1) || (x == minX) || (x == maxX - 1) || (z == minZ) || (z == maxZ - 1))
                            continue;
                        
                        height = minHeight;
                    }

                }

            }

            if (underwater)
                continue;
            
            for (int somethingX = minX; somethingX < maxX; somethingX++)
            {
                double var63 = (somethingX + chunkX * 16 + 0.5D - startX) / variation;

                for (int somethingZ = minZ; somethingZ < maxZ; somethingZ++)
                {
                    double var54 = (somethingZ + chunkZ * 16 + 0.5D - startZ) / variation;
                    int at = (somethingX * 16 + somethingZ) * 128 + maxHeight;
                    boolean atSurface = false;

                    if (var63 * var63 + var54 * var54 >= 1.0D)
                        continue;
                    
                    for (int var58 = maxHeight - 1; var58 >= minHeight; var58--)
                    {
                        double var59 = (var58 + 0.5D - startY) / heightVariation;

                        if ((var59 > -0.7D) && (var63 * var63 + var59 * var59 + var54 * var54 < 1.0D))
                        {
                            Block ceilBlock = blocks[at];

                            if (ceilBlock == AliensVsPredator.blocks().terrainUniDirt)
                            {
                                atSurface = true;
                            }

                            if ((ceilBlock != AliensVsPredator.blocks().terrainUniStone) || (ceilBlock != AliensVsPredator.blocks().terrainUniDirt) || (ceilBlock != AliensVsPredator.blocks().terrainUniDirt))
                            {
                                if (var58 < 10)
                                {
                                    blocks[at] = Blocks.flowing_lava;
                                }
                                else
                                {
                                    blocks[at] = Blocks.air;

                                    if ((atSurface) && (blocks[(at - 1)] == AliensVsPredator.blocks().terrainUniDirt))
                                    {
                                        blocks[(at - 1)] = this.worldObj.getBiomeGenForCoords(somethingX + chunkX * 16, somethingZ + chunkZ * 16).topBlock;
                                    }
                                }
                            }
                        }

                        at--;
                    }
                }

            }

            if (endOfCave)
                break;
        }
    }

    /**
     * recursiveGenerate()
     */
    @Override
    protected void func_151538_a(World world, int chunkStartX, int chunkStartZ, int chunkX, int chunkZ, Block[] blocks)
    {
        int caves = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);

        if (this.rand.nextInt(15) != 0)
        {
            caves = 0;
        }

        for (int cave = 0; cave < caves; cave++)
        {
            double startX = chunkStartX * 16 + this.rand.nextInt(16);
            double startY = this.rand.nextInt(this.rand.nextInt(120) + 8);
            double startZ = chunkStartZ * 16 + this.rand.nextInt(16);
            int branchedCaves = 1;

            if (this.rand.nextInt(2) == 0)
            {
                generateLargeCaveNode(this.rand.nextLong(), chunkX, chunkZ, blocks, startX, startY, startZ);
                branchedCaves += this.rand.nextInt(4);
            }

            for (int branchedCave = 0; branchedCave < branchedCaves; branchedCave++)
            {
                float diameter = this.rand.nextFloat() * 3.141593F * 2.0F;
                float aggressiveness = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float diameterVariation = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

                if (this.rand.nextInt(10) == 0)
                {
                    diameterVariation *= (this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F);
                }

                generateCaveNode(this.rand.nextLong(), chunkX, chunkZ, blocks, startX, startY, startZ, diameterVariation, diameter, aggressiveness, 0, 0, 1.0D);
            }
        }
    }
}
