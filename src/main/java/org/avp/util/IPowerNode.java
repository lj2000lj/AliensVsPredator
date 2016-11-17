package org.avp.util;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerNode
{
    /**
     * Returns TRUE if the TileEntity can connect on a given side.
     */
    public boolean canConnect(ForgeDirection from);

    /**
     * Returns the amount of voltage currently stored.
     */
    public double getVoltage(ForgeDirection from);

    /**
     * Returns the maximum amount of voltage that can be stored.
     */
    public double getMaxVoltage(ForgeDirection from);
}
