package org.avp.dimension.varda.gen.layer;

import org.avp.dimension.varda.BiomeGenVarda;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerVardaBiomes extends GenLayer
{
    protected BiomeGenVarda[] allowedBiomes = { BiomeGenVarda.vardaBadlands, BiomeGenVarda.vardaForest };

    public GenLayerVardaBiomes(long seed, GenLayer genlayer)
    {
        super(seed);
        this.parent = genlayer;
    }

    public GenLayerVardaBiomes(long seed)
    {
        super(seed);
    }

    @Override

    public int[] getInts(int x, int z, int width, int depth)
    {
        int[] dest = IntCache.getIntCache(width * depth);

        for (int dz = 0; dz < depth; dz++)
        {
            for (int dx = 0; dx < width; dx++)
            {
                this.initChunkSeed(dx + x, dz + z);
                dest[(dx + dz * width)] = this.allowedBiomes[nextInt(this.allowedBiomes.length)].biomeID;
            }
        }

        return dest;
    }
}
