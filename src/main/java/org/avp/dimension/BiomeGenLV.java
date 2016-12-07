package org.avp.dimension;

import org.avp.AliensVsPredator;
import org.avp.dimension.acheron.BiomeAcheron;

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenLV extends BiomeGenBase
{
    public static BiomeGenLV acheron     = new BiomeAcheron(AliensVsPredator.settings().biomeIdAcheron()).setColor(0x353825);
    
    public BiomeGenLV(int biomeId)
    {
        this(biomeId, true);
    }

    public BiomeGenLV(int biomeId, boolean register)
    {
        super(biomeId, register);
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
        return this.theBiomeDecorator;
    }
}
