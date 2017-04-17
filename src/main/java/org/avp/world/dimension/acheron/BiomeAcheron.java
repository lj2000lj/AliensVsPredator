package org.avp.world.dimension.acheron;

import org.avp.AliensVsPredator;
import org.avp.world.dimension.BiomeGenLV;

public class BiomeAcheron extends BiomeGenLV
{
    public static final BiomeAcheron acheron = new BiomeAcheron(new BiomeProperties("Acheron").setBaseHeight(0.1F).setHeightVariation(0.4F).setTemperature(0.5F).setRainDisabled());

    public BiomeAcheron(BiomeProperties properties)
    {
        super(properties);
        this.topBlock = AliensVsPredator.blocks().terrainUniDirt.getDefaultState();
        this.fillerBlock = AliensVsPredator.blocks().terrainUniStone.getDefaultState();
        this.theBiomeDecorator = new BiomeDecoratorAcheron(this);
    }
}
