package org.avp.world.dimension.acheron;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.WorldChunkManager;

@SuppressWarnings("all")
public class ChunkManagerAcheron extends WorldChunkManager
{
    private Biome biome;
    private float hellTemperature;
    private float rainfall;

    public ChunkManagerAcheron(Biome biome)
    {
        this.biome = biome;
    }

    @Override
    public Biome getBiomeGenAt(int chunkX, int chunkZ)
    {
        return this.biome;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomeList, int posX, int posZ, int width, int height)
    {
        if ((biomeList == null) || (biomeList.length < width * height))
        {
            biomeList = new Biome[width * height];
        }

        Arrays.fill(biomeList, 0, width * height, this.biome);
        return biomeList;
    }

    @Override
    public Biome[] loadBlockGeneratorData(Biome[] biomeList, int posX, int posZ, int width, int height)
    {
        if ((biomeList == null) || (biomeList.length < width * height))
        {
            biomeList = new Biome[width * height];
        }

        Arrays.fill(biomeList, 0, width * height, this.biome);
        return biomeList;
    }

    @Override
    public float getTemperatureAtHeight(float temp, int height)
    {
        return super.getTemperatureAtHeight(temp, height);
    }

    @Override
    public float[] getRainfall(float[] rainfallValues, int posX, int posZ, int width, int height)
    {
        if ((rainfallValues == null) || (rainfallValues.length < width * height))
        {
            rainfallValues = new float[width * height];
        }

        Arrays.fill(rainfallValues, 0, width * height, this.rainfall);
        return rainfallValues;
    }

    @Override
    public Biome[] getBiomeGenAt(Biome[] biomeList, int posX, int posZ, int width, int height, boolean checkCache)
    {
        return loadBlockGeneratorData(biomeList, posX, posZ, width, height);
    }

    @Override
    public ChunkPosition findBiomePosition(int posX, int posY, int posZ, List biomeList, Random seed)
    {
        return biomeList.contains(this.biome) ? new ChunkPosition(posX - posZ + seed.nextInt(posZ * 2 + 1), 0, posY - posZ + seed.nextInt(posZ * 2 + 1)) : null;
    }

    @Override
    public boolean areBiomesViable(int posX, int posY, int posZ, List biomeList)
    {
        return biomeList.contains(this.biome);
    }
}
