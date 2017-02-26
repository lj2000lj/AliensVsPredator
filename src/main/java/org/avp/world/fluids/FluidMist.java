package org.avp.world.fluids;

import net.minecraftforge.fluids.Fluid;

public class FluidMist extends Fluid
{
    public FluidMist()
    {
        super("mist");
        this.setDensity(1);
        this.setViscosity(8000);
        this.setGaseous(true);
    }
}
