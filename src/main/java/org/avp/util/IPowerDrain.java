package org.avp.util;

public interface IPowerDrain extends IPowerNode
{
    /**
     * Distribute power to this IPowerAcceptor.
     *
     * @param from - Direction from which the energy is received.
     * @param maxExtract - Amount of voltage to receive.
     * @param simulate - Will only be simulated. Will not actually use resources.
     */
    public void drainPower(IPowerNode from, double voltage, double amperage);
}
