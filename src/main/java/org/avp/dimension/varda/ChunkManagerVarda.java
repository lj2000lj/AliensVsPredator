package org.avp.dimension.varda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.avp.dimension.varda.gen.layer.GenLayerVarda;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class ChunkManagerVarda extends WorldChunkManager
{
    public static ArrayList<BiomeGenVarda> allowedBiomes = new ArrayList<BiomeGenVarda>(Arrays.asList(BiomeGenVarda.vardaBadlands, BiomeGenVarda.vardaForest));
    private GenLayer                       genBiomes;
    private GenLayer                       biomeIndexLayer;
    private BiomeCache                     biomeCache;
    private List<BiomeGenVarda>            biomesToSpawnIn;

    protected ChunkManagerVarda()
    {
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = new ArrayList<BiomeGenVarda>();
        this.biomesToSpawnIn.addAll(allowedBiomes);
    }

    public ChunkManagerVarda(long seed, WorldType worldType)
    {
        this();
        GenLayer[] biomeLayer = GenLayerVarda.generate(seed);
        this.genBiomes = biomeLayer[0];
        this.biomeIndexLayer = biomeLayer[1];
    }

    public ChunkManagerVarda(World world)
    {
        this(world.getSeed(), world.getWorldInfo().getTerrainType());
    }

    @Override
    public List getBiomesToSpawnIn()
    {
        return this.biomesToSpawnIn;
    }

    @Override
    public BiomeGenBase getBiomeGenAt(int chunkX, int chunkZ)
    {
        return this.biomeCache.getBiomeGenAt(chunkX, chunkZ);
    }

    @Override
    public float[] getRainfall(float[] rainfallValues, int chunkX, int chunkZ, int width, int depth)
    {
        IntCache.resetIntCache();

        if (rainfallValues == null || rainfallValues.length < width * depth)
        {
            rainfallValues = new float[width * depth];
        }

        int[] indexes = this.biomeIndexLayer.getInts(chunkX, chunkZ, width, depth);

        for (int idx = 0; idx < width * depth; ++idx)
        {
            try
            {
                float rainfall = (float) BiomeGenBase.getBiome(indexes[idx]).getIntRainfall() / 65536.0F;

                if (rainfall > 1.0F)
                {
                    rainfall = 1.0F;
                }

                rainfallValues[idx] = rainfall;
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
                crashreportcategory.addCrashSection("biome id", Integer.valueOf(idx));
                crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(rainfallValues.length));
                crashreportcategory.addCrashSection("x", Integer.valueOf(chunkX));
                crashreportcategory.addCrashSection("z", Integer.valueOf(chunkZ));
                crashreportcategory.addCrashSection("w", Integer.valueOf(width));
                crashreportcategory.addCrashSection("h", Integer.valueOf(depth));
                throw new ReportedException(crashreport);
            }
        }

        return rainfallValues;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getTemperatureAtHeight(float temperature, int height)
    {
        return temperature;
    }

    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int chunkX, int chunkZ, int width, int depth)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * depth)
        {
            biomes = new BiomeGenBase[width * depth];
        }

        int[] indexes = this.genBiomes.getInts(chunkX, chunkZ, width, depth);

        try
        {
            for (int idx = 0; idx < width * depth; ++idx)
            {
                biomes[idx] = BiomeGenVarda.getBiome(indexes[idx]);
            }

            return biomes;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(chunkX));
            crashreportcategory.addCrashSection("z", Integer.valueOf(chunkZ));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(depth));
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomes, int chunkX, int chunkZ, int width, int depth)
    {
        return this.getBiomeGenAt(oldBiomes, chunkX, chunkZ, width, depth, true);
    }

    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int chunkX, int chunkZ, int width, int depth, boolean checkCache)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * depth)
        {
            biomes = new BiomeGenBase[width * depth];
        }

        if (checkCache && width == 16 && depth == 16 && (chunkX & 15) == 0 && (chunkZ & 15) == 0)
        {
            BiomeGenBase[] abiomegenbase1 = this.biomeCache.getCachedBiomes(chunkX, chunkZ);
            System.arraycopy(abiomegenbase1, 0, biomes, 0, width * depth);
            return biomes;
        }
        else
        {
            int[] indexes = this.biomeIndexLayer.getInts(chunkX, chunkZ, width, depth);

            for (int idx = 0; idx < width * depth; ++idx)
            {
                biomes[idx] = BiomeGenBase.getBiome(indexes[idx]);
            }

            return biomes;
        }
    }

    @Override
    public boolean areBiomesViable(int chunkX, int chunkZ, int radius, List allowedBiomes)
    {
        IntCache.resetIntCache();
        int chunkMinX = chunkX - radius >> 2;
        int chunkMinZ = chunkZ - radius >> 2;
        int chunkMaxX = chunkX + radius >> 2;
        int chunkMaxZ = chunkZ + radius >> 2;
        int width = chunkMaxX - chunkMinX + 1;
        int depth = chunkMaxZ - chunkMinZ + 1;
        int[] indexes = this.genBiomes.getInts(chunkMinX, chunkMinZ, width, depth);

        try
        {
            for (int idx = 0; idx < width * depth; ++idx)
            {
                BiomeGenBase biomegenbase = BiomeGenBase.getBiome(indexes[idx]);

                if (!allowedBiomes.contains(biomegenbase))
                {
                    return false;
                }
            }

            return true;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
            crashreportcategory.addCrashSection("x", Integer.valueOf(chunkX));
            crashreportcategory.addCrashSection("z", Integer.valueOf(chunkZ));
            crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
            crashreportcategory.addCrashSection("allowed", allowedBiomes);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public ChunkPosition findBiomePosition(int chunkX, int chunkZ, int radius, List biomes, Random seed)
    {
        IntCache.resetIntCache();
        int chunkMinX = chunkX - radius >> 2;
        int chunkMinZ = chunkZ - radius >> 2;
        int chunkMaxX = chunkX + radius >> 2;
        int chunkMaxZ = chunkZ + radius >> 2;
        int width = chunkMaxX - chunkMinX + 1;
        int depth = chunkMaxZ - chunkMinZ + 1;
        int[] indexes = this.genBiomes.getInts(chunkMinX, chunkMinZ, width, depth);
        ChunkPosition chunkposition = null;
        int count = 0;

        for (int idx = 0; idx < width * depth; ++idx)
        {
            int biomeChunkX = chunkMinX + idx % width << 2;
            int biomeChunkZ = chunkMinZ + idx / width << 2;
            BiomeGenBase biomegen = BiomeGenBase.getBiome(indexes[idx]);

            if (biomes.contains(biomegen) && (chunkposition == null || seed.nextInt(count + 1) == 0))
            {
                chunkposition = new ChunkPosition(biomeChunkX, 0, biomeChunkZ);
                ++count;
            }
        }

        return chunkposition;
    }

    @Override
    public void cleanupCache()
    {
        this.biomeCache.cleanupCache();
    }
}
