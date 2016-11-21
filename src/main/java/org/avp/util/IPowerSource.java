package org.avp.util;

public interface IPowerSource extends IPowerNode
{
    public double provideVoltage(IPowerNode to);
    
    public double provideAmperage(IPowerNode to);
}
