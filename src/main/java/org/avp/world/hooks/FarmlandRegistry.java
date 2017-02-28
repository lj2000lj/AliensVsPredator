package org.avp.world.hooks;

import java.util.ArrayList;

import org.avp.AliensVsPredator;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.getEntity().player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FarmlandRegistry
{
    public static FarmlandRegistry instance = new FarmlandRegistry();
    private ArrayList<Block> farmlandRegistry = new ArrayList<Block>();

    private FarmlandRegistry()
    {
        this.farmlandRegistry.add(Blocks.dirt);
        this.farmlandRegistry.add(Blocks.grass);
        this.farmlandRegistry.add(AliensVsPredator.blocks().terrainUniDirt);
    }

    @SubscribeEvent
    public void onUseHoe(UseHoeEvent event)
    {
        Block block = event.world.getBlock(event.getX(), event.getY(), event.getZ());

        if (event.world.getBlock(event.getX(), event.getY() + 1, event.getZ()).isAir(event.world, event.getX(), event.getY() + 1, event.getZ()) && (farmlandRegistry.contains(block)))
        {
            Block farmland = Blocks.farmland;
            event.world.playSoundEffect((double) ((float) event.getX() + 0.5F), (double) ((float) event.getY() + 0.5F), (double) ((float) event.getZ() + 0.5F), farmland.stepSound.getStepSound(), (farmland.stepSound.getVolume() + 1.0F) / 2.0F, farmland.stepSound.getFrequency() * 0.8F);

            if (event.world.isRemote)
            {
                event.setResult(Result.ALLOW);
            }
            else
            {
                event.world.setBlock(event.getX(), event.getY(), event.getZ(), farmland);
                event.current.damageItem(1, event.getEntity()Player);
                event.setResult(Result.ALLOW);
            }
        }
        else
        {
            event.setResult(Result.DENY);
        }
    }
}
