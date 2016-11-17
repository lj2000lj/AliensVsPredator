package org.avp.util;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerProvider extends IPowerNode
{
    /**
     * Remove energy from an IPowerProvider. IPowerProviders do not distribute the power themselves, receivers pull power from providers.
     *
     * @param from - Direction the energy is extracted from.
     * @param threshold - The threshold amount that can be provided at once.
     * @param simulate - Will only be simulated. Will not actually use resources.
     * @return Amount of voltage that was (or would have been, if simulated) extracted.
     */
    public double provideVoltage(ForgeDirection from, double threshold, boolean simulate);
}
