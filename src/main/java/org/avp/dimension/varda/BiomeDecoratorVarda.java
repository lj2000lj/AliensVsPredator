package org.avp.dimension.varda;

import java.util.Random;

import org.avp.AliensVsPredator;
import org.avp.dimension.BiomeGenLV;
import org.avp.dimension.varda.gen.TerrainFormation;
import org.avp.dimension.varda.gen.TerrainFormation1;
import org.avp.dimension.varda.gen.VardaTallTreeGenerator;
import org.avp.dimension.varda.gen.VardaTree2Generator;
import org.avp.dimension.varda.gen.VardaTree3Generator;
import org.avp.dimension.varda.gen.VardaTreeGenerator;

import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.Worlds;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorVarda extends BiomeDecorator
{
    protected World              world;
    private final WorldGenerator stalagmiteGen     = new WorldGenFlowers(AliensVsPredator.blocks().terrainStalagmite);
    private final WorldGenerator saplingGeneration = new WorldGenFlowers(AliensVsPredator.blocks().terrainUniTreeSapling);
    private final WorldGenerator lakeGeneration    = new WorldGenLakes(AliensVsPredator.blocks().blockBlackGoo);
    private final WorldGenerator terrainFormation1 = new TerrainFormation();
    private final WorldGenerator terrainFormation2 = new TerrainFormation1();
    private final WorldGenerator tree1             = new VardaTreeGenerator(true);
    private final WorldGenerator tree2             = new VardaTree2Generator(true);
    private final WorldGenerator tree3             = new VardaTree3Generator(true);
    private final WorldGenerator treeTall          = new VardaTallTreeGenerator(true);
    private final WorldGenerator genDirt           = new WorldGenMinable(AliensVsPredator.blocks().terrainUniDirt, 32, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator genSand           = new WorldGenMinable(AliensVsPredator.blocks().terrainUniSand, 32, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreBauxite        = new WorldGenMinable(AliensVsPredator.blocks().oreBauxite, 4, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreCopper         = new WorldGenMinable(AliensVsPredator.blocks().oreCopper, 4, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreSilicon        = new WorldGenMinable(AliensVsPredator.blocks().oreSilicon, 4, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreLithium        = new WorldGenMinable(AliensVsPredator.blocks().oreLithium, 3, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreCoal           = new WorldGenMinable(Blocks.coal_ore, 16, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreIron           = new WorldGenMinable(Blocks.iron_ore, 8, AliensVsPredator.blocks().terrainUniStone);
    private final WorldGenerator oreDiamond        = new WorldGenMinable(Blocks.diamond_ore, 3, AliensVsPredator.blocks().terrainUniStone);

    public BiomeDecoratorVarda(BiomeGenLV biome)
    {
        super();
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

        if (biome instanceof BiomeVardaForest)
        {
            this.generateForestDecorations((BiomeVardaForest) biome);
        }
        
        Worlds.generateInChunk(world, this.stalagmiteGen, this.randomGenerator, 10, new Pos(chunk_X, 0, chunk_Z));

        for (int i = 0; i < 128; i++)
        {
            WorldGenerator landform = null;

            switch (this.randomGenerator.nextInt(2))
            {
                case 0:
                    landform = terrainFormation1;
                    break;
                case 1:
                    landform = terrainFormation2;
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
        Pos chunkCoords = new Pos(chunk_X, 0, chunk_Z);

        Worlds.generateInChunk(world, genDirt, this.randomGenerator, 20, 0, 4, chunkCoords);
        Worlds.generateInChunk(world, genSand, this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, oreBauxite, this.randomGenerator, 20, 16, 128, chunkCoords);
        Worlds.generateInChunk(world, oreCopper, this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, oreSilicon, this.randomGenerator, 15, 0, 64, chunkCoords);
        Worlds.generateInChunk(world, oreLithium, this.randomGenerator, 1, 1, 48, chunkCoords);
        Worlds.generateInChunk(world, oreCoal, this.randomGenerator, 20, 0, 128, chunkCoords);
        Worlds.generateInChunk(world, oreIron, this.randomGenerator, 20, 0, 64, chunkCoords);
        Worlds.generateInChunk(world, oreDiamond, this.randomGenerator, 1, 0, 16, chunkCoords);
    }
    
    private void generateForestDecorations(BiomeVardaForest biome)
    {
        for (int i = 0; i < 2; i++)
        {
            int randPosX1 = chunk_X + randomGenerator.nextInt(16);
            int randPosY1 = randomGenerator.nextInt(128);
            int randPosZ1 = chunk_Z + randomGenerator.nextInt(16);
            this.lakeGeneration.generate(world, randomGenerator, randPosX1, randPosY1, randPosZ1);
        }

        for (int i = 0; i < 256; i++)
        {
            WorldGenerator tree = null;

            switch (randomGenerator.nextInt(4))
            {
                case 0:
                    tree = tree1;
                    break;
                case 1:
                    tree = tree2;
                    break;
                case 2:
                    tree = tree3;
                    break;
                case 3:
                    tree = treeTall;
                    break;
            }

            int randPosX1 = chunk_X + randomGenerator.nextInt(16);
            int randPosY1 = randomGenerator.nextInt(84);
            int randPosZ1 = chunk_Z + randomGenerator.nextInt(16);
            tree.generate(world, randomGenerator, randPosX1, randPosY1, randPosZ1);
        }

        for (int i = 0; i < 3; i++)
        {
            int randPosX1 = chunk_X + randomGenerator.nextInt(16);
            int randPosY1 = randomGenerator.nextInt(96);
            int randPosZ1 = chunk_Z + randomGenerator.nextInt(16);
            saplingGeneration.generate(world, randomGenerator, randPosX1, randPosY1, randPosZ1);
        }
    }
}
