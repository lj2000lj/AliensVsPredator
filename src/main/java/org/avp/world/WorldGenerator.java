package org.avp.world;

import java.util.Random;

import org.avp.AliensVsPredator;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.Worlds;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

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
        BiomeGenBase[] overworldBiomes = new BiomeGenBase[] { BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.jungle, BiomeGenBase.jungleEdge, BiomeGenBase.jungleHills, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills, BiomeGenBase.ocean, BiomeGenBase.deepOcean
        };
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreBauxite, 4), rand, 20, 16, 128, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreCopper, 4), rand, 20, 0, 128, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreSilicon, 4), rand, 15, 0, 64, chunkCoords, overworldBiomes);
        Worlds.generateInBiome(world, new WorldGenMinable(AliensVsPredator.blocks().oreLithium, 3), rand, 1, 1, 48, chunkCoords, overworldBiomes);
    }
}
