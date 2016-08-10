package org.avp.event;

import org.avp.entities.tile.TileEntityHiveResin;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;

public class HiveHandler
{
    public static final HiveHandler INSTANCE = new HiveHandler();

    @SubscribeEvent
    public void breakResin(BlockEvent.BreakEvent event)
    {
        System.out.println(event.block.getLocalizedName());
        TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);

        if (tile instanceof TileEntityHiveResin)
        {
            TileEntityHiveResin resin = (TileEntityHiveResin) tile;

            if (resin != null && resin.getBlockCovering() != null)
            {
                event.world.setBlock(event.x, event.y, event.z, resin.getBlockCovering());
                event.setCanceled(true);
            }
        }
    }
}
