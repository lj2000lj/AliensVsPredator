package org.avp.world.fluids;

import net.minecraftforge.fluids.Fluid;

public class FluidBlackGoo extends Fluid
{
    public FluidBlackGoo()
    {
        super("blackGoo");
        this.setDensity(3000);
        this.setViscosity(6000);
        this.setGaseous(false);
    }
}
