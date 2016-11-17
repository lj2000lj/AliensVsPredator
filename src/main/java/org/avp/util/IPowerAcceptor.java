package org.avp.util;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerAcceptor extends IPowerNode
{
    /**
     * Distribute power to this IPowerAcceptor.
     *
     * @param from - Direction from which the energy is received.
     * @param maxExtract - Amount of voltage to receive.
     * @param simulate - Will only be simulated. Will not actually use resources.
     * @return Amount of voltage that was (or would have been, if simulated) received.
     */
    public double acceptVoltageFrom(ForgeDirection from, double maxReceive, boolean simulate);
}
