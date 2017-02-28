package org.avp.world.dimension.varda;

import org.avp.AliensVsPredator;

import net.minecraft.world.biome.Biome;



public class BiomeVardaForest extends BiomeGenVarda
{
    public BiomeVardaForest(int biomeId)
    {
        super(biomeId);
        this.topBlock = AliensVsPredator.blocks().terrainUniDirt;
        this.fillerBlock = AliensVsPredator.blocks().terrainUniStone;
        this.setBiomeName(AliensVsPredator.properties().BIOME_NAME_VARDA_FOREST);
        this.setHeight(new Biome.Height(1.0F, 8.0F));
        this.setTemperatureRainfall(1.0F, 0.5F);
        this.theBiomeDecorator = new BiomeDecoratorVarda(this);
    }

    @Override
    public BiomeVardaForest setColor(int color)
    {
        this.func_150557_a(color, false);
        return this;
    }
}
