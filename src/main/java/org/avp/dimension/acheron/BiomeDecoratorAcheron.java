package org.avp.dimension.acheron;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.dimension.BiomeLVBase;

import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.Worlds;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorAcheron extends BiomeDecorator
{
    protected World world;
    public BiomeLVBase biome;
    public WorldGenerator stalagmiteGen;

    public BiomeDecoratorAcheron(BiomeLVBase biome)
    {
        super();
        this.biome = biome;
        this.stalagmiteGen = new WorldGenFlowers(AliensVsPredator.blocks().terrainStalagmite);
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
    }

    @Override
    protected void generateOres()
    {
        Worlds.generateInChunk(world, new WorldGenMinable(AliensVsPredator.blocks().terrainUniDirt, 32), this.randomGenerator, 20, 0, 128, new CoordData(chunk_X, 0, chunk_Z));
    }
}
