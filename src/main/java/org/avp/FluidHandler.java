package org.avp;

import org.avp.event.BucketHandlingEvent;
import org.avp.fluids.FluidBlackGoo;
import org.avp.fluids.FluidMist;

import com.arisux.mdxlib.lib.game.IInitEvent;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler implements IInitEvent
{
    public static FluidHandler instance = new FluidHandler();

    public Fluid fluidBlackGoo = new FluidBlackGoo().setUnlocalizedName("blackGoo");
    public Fluid fluidMist = new FluidMist().setUnlocalizedName("mist");

    @Override
    public void init(FMLInitializationEvent event)
    {
        this.registerFluids(event);
        this.registerFluidContainers(event);
        this.registerBucketEvents(event);
    }

    private void registerFluids(FMLInitializationEvent event)
    {
        FluidRegistry.registerFluid(fluidBlackGoo);
        FluidRegistry.registerFluid(fluidMist);
    }

    private void registerFluidContainers(FMLInitializationEvent event)
    {
        FluidContainerRegistry.registerFluidContainer(fluidBlackGoo, new ItemStack(AliensVsPredator.items().blackGooBucket), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(fluidMist, new ItemStack(AliensVsPredator.items().mistBucket), new ItemStack(Items.bucket));
    }

    private void registerBucketEvents(FMLInitializationEvent event)
    {
        BucketHandlingEvent.instance.buckets.put(AliensVsPredator.blocks().blockBlackGoo, AliensVsPredator.items().blackGooBucket);
        BucketHandlingEvent.instance.buckets.put(AliensVsPredator.blocks().blockMist, AliensVsPredator.items().mistBucket);
    }
}
