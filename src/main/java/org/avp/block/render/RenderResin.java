package org.avp.block.render;

import org.avp.AliensVsPredator;
import org.avp.Settings.ClientSettings;
import org.avp.entities.tile.TileEntityHiveResin;

import com.arisux.mdxlib.config.GraphicsSetting;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderResin implements ISimpleBlockRenderingHandler
{
    private int renderId;

    public RenderResin(int renderId)
    {
        this.renderId = renderId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        ;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        if (world != null)
        {
            GraphicsSetting hiveTessellation = ClientSettings.instance.getHiveTesselation();
            Block renderBlock = AliensVsPredator.blocks().blockStandardHiveResin;
            TileEntityHiveResin resin = (TileEntityHiveResin) world.getTileEntity(x, y, z);

            if (hiveTessellation != GraphicsSetting.LOW)
            {
                if (resin != null && resin.getBlockCovering() != null)
                {
                    renderBlock = resin.getBlockCovering();
                }
            }

            if (resin != null && renderBlock != null)
            {
                RenderBlocks.getInstance().blockAccess = world;
                RenderBlocks.getInstance().renderBlockAllFaces(renderBlock, x, y, z);
                RenderBlocks.getInstance().blockAccess = null;
            }
        }

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return this.renderId;
    }
}
