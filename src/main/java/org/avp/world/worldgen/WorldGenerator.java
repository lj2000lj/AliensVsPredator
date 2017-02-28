package org.avp.world.worldgen;

import java.util.Random;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.Worlds;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator
{
    @Override
    public void generate(Random seed, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateSurface(world, seed, chunkX * 16, chunkZ * 16);
    }

    private void generateSurface(World world, Random rand, int chunkX, int chunkZ)
    {
        Pos chunkCoords = new Pos(chunkX, 0, chunkZ);
        Biome[] overworldBiomes = new Biome[] { Biome.beach, Biome.birchForest, Biome.birchForestHills, Biome.coldBeach, Biome.coldTaiga, Biome.coldTaigaHills, Biome.desert, Biome.desertHills, Biome.extremeHills, Biome.extremeHillsEdge, Biome.extremeHillsPlus, Biome.forest, Biome.forestHills, Biome.frozenOcean, Biome.frozenRiver, Biome.iceMountains, Biome.icePlains, Biome.jungle, Biome.jungleEdge, Biome.jungleHills, Biome.plains, Biome.river, Biome.roofedForest, Biome.swampland, Biome.taiga, Biome.taigaHills, Biome.ocean, Biome.deepOcean
        };
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreBauxite, 4), rand, 20, 16, 128, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreCopper, 4), rand, 20, 0, 128, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreSilicon, 4), rand, 15, 0, 64, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreLithium, 3), rand, 1, 1, 48, chunkCoords, overworldBiomes);
    }
}
