package org.avp;

import org.avp.block.BlockShape;
import org.avp.block.BlockShape.ShapeTypes;

import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.world.block.Blocks;

import net.minecraft.block.Block;

public class ShapedBlockUtil
{
    public static void register(String modid,  Block block, String identifier)
    {
        register(modid, block, identifier, 0);
    }

    public static void register(String modid, Block block, String identifier, int textureSide)
    {
        register(modid, block, identifier, 0, block);
    }

    public static void register(String modid, Block blockParent, String identifier, int textureSide, Block textureBlock)
    {
        Game.register(modid, blockParent, identifier).setCreativeTab(AliensVsPredator.tabBlocks());

        BlockShape blockSlope = new BlockShape(ShapeTypes.SLOPE);
        BlockShape blockCorner = new BlockShape(ShapeTypes.CORNER);
        BlockShape blockInvertedCorner = new BlockShape(ShapeTypes.INVERTED_CORNER);
        BlockShape blockRidge = new BlockShape(ShapeTypes.RIDGE);
        BlockShape blockInvertedRidge = new BlockShape(ShapeTypes.INVERTED_RIDGE);
        BlockShape blockSmartInvertedRidge = new BlockShape(ShapeTypes.SMART_INVERTED_RIDGE);
        BlockShape blockSmartRidge = new BlockShape(ShapeTypes.SMART_RIDGE);

        applyPropertiesToShapedBlock(blockSlope, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockCorner, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockInvertedCorner, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockRidge, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockInvertedRidge, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockSmartInvertedRidge, blockParent, textureBlock);
        applyPropertiesToShapedBlock(blockSmartRidge, blockParent, textureBlock);

        Game.register(modid, blockSlope, identifier + ".slope").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockCorner, identifier + ".corner").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockInvertedCorner, identifier + ".invertedcorner").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockRidge, identifier + ".ridge").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockInvertedRidge, identifier + ".invertedridge").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockSmartInvertedRidge, identifier + ".smartinvertedridge").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(modid, blockSmartRidge, identifier + ".smartridge").setCreativeTab(AliensVsPredator.tabBlocks());
    }

    public static void applyPropertiesToShapedBlock(BlockShape shaped, Block blockParent, Block textureBlock)
    {
        shaped.setIconsFromBlock(textureBlock);

        if (blockParent != null)
        {
            shaped.setResistance(Blocks.getBlockResistance(blockParent));
            shaped.setHardness(Blocks.getBlockHardness(blockParent));
            shaped.setLightOpacity(blockParent.getLightOpacity());
        }
    }
}
