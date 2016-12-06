package org.avp.dimension.varda;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.dimension.BiomeLVBase;
import org.avp.dimension.varda.worldgen.TerrainFormation;
import org.avp.dimension.varda.worldgen.TerrainFormation1;

import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.Worlds;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorVarda extends BiomeDecorator
{
    protected World        world;
    public BiomeLVBase     biome;
    public WorldGenerator  stalagmiteGen;
    public WorldGenLakes   lakeGeneration;
    public WorldGenLiquids caveWaterGen;

    public BiomeDecoratorVarda(BiomeLVBase biome)
    {
        super();
        this.biome = biome;
        this.stalagmiteGen = new WorldGenFlowers(AliensVsPredator.blocks().terrainStalagmite);
        this.lakeGeneration = new WorldGenLakes(Blocks.water);
        this.caveWaterGen = new WorldGenLiquids(Blocks.flowing_water);
    }

    @Override
    public void decorateChunk(World world, Random seed, BiomeGenBase biome, int chunkX, int chunkZ)
    {
        if (this.world != null)
        {
            return;
        }

        this.world = world;
        this.randomGenerator = seed;
        this.chunk_X = chunkX;
        this.chunk_Z = chunkZ;
        this.genDecorations(biome);
        this.world = null;
        this.randomGenerator = null;
    }

    @Override
    protected void genDecorations(BiomeGenBase biome)
    {
        this.generateOres();

        Worlds.generateInChunk(world, this.stalagmiteGen, this.randomGenerator, 10, new CoordData(chunk_X, 0, chunk_Z));

        for (int i = 0; i < 128; i++)
        {
            WorldGenerator landform = null;

            switch (this.randomGenerator.nextInt(2))
            {
                case 0:
                    landform = new TerrainFormation();
                    break;
                case 1:
                    landform = new TerrainFormation1();
                    break;
            }

            int randPosX1 = chunk_X + randomGenerator.nextInt(16);
            int randPosY1 = randomGenerator.nextInt(128);
            int randPosZ1 = chunk_Z + randomGenerator.nextInt(16);
            landform.generate(world, randomGenerator, randPosX1, randPosY1, randPosZ1);
        }
    }

    @Override
    protected void generateOres()
    {
        CoordData chunkCoords = new CoordData(chunk_X, 0, chunk_Z);

        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().terrainUniDirt, 32, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().terrainUniSand, 32, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().oreBauxite, 4, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 16, 128, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().oreCopper, 4, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().oreSilicon, 4, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 15, 0, 64, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().oreLithium, 3, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 1, 1, 48, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(Blocks.coal_ore, 16, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(Blocks.iron_ore, 8, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 20, 0, 64, chunkCoords);
        Worlds.generateInChunk(world, new WorldGenMinable(Blocks.diamond_ore, 3, AliensVsPredator.blocks().terrainUniStone), this.randomGenerator, 1, 0, 16, chunkCoords);
    }
}
