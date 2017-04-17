package org.avp.world.dimension;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public abstract class BiomeGenLV extends Biome
{
    public BiomeGenLV(BiomeProperties properties)
    {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.clear();
    }

    @Override
    public float getSpawningChance()
    {
        return 0.12F;
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return super.createBiomeDecorator();
    }
}
