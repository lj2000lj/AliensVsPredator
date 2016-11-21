package org.avp.util;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerNode
{
    public boolean canConnect(ForgeDirection from);
    
    /**
     * @return The voltage this node currently contains. Voltage is relative to the pressure of the energy.
     */
    public double getVoltage();
    
    /**
     * @return The maximum voltage this node can handle.
     */
    public double getVoltageThreshold();
    
    /**
     * @return The amount of amperage this node contains. Amperage is relative to the amount of energy transfer in a given amount of time.
     */
    public double getAmperage();
    
    /**
     * @return The maximum amount of amperage flow this node can handle.
     */
    public double getAmperageThreshold();

}
