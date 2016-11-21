package org.avp.entities.tile;

import org.avp.util.IPowerNode;

import net.minecraft.tileentity.TileEntity;

public class TileEntityNegativeTransformer extends TileEntityTransformer
{
    @Override
    public double provideVoltage(IPowerNode from)
    {
        TileEntity tile = (TileEntity) from;

        if (tile != null && tile instanceof TileEntityElectrical)
        {
            return (this.getVoltage() - 24) - this.getResistance();
        }

        return 0;
    }

    @Override
    public double provideAmperage(IPowerNode to)
    {
        TileEntity drain = (TileEntity) to;

        if (drain != null && drain instanceof TileEntityElectrical)
        {
            return 0 + 10;
        }

        return 0;
    }
}
