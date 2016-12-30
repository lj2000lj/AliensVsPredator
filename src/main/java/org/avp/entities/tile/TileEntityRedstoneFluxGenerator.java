package org.avp.entities.tile;

import org.avp.util.IVoltageProvider;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneFluxGenerator extends TileEntityElectrical implements IVoltageProvider, IEnergyReceiver
{
    protected int rfEnergy;
    protected int rfStoredPerTick;
    protected int rfUsedPerTick;
    
    public TileEntityRedstoneFluxGenerator()
    {
        super(true);
        this.rfStoredPerTick = 25;
        this.rfUsedPerTick = 20;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (rfEnergy >= rfUsedPerTick)
        {
            this.setVoltage(240);
            rfEnergy = rfEnergy - rfUsedPerTick;
        }
        else
        {
            this.setVoltage(0);
        }
    }

    @Override
    public boolean canConnectPower(ForgeDirection from)
    {
        return true;
    }

    @Override
    public double extractVoltage(ForgeDirection from, double maxExtract, boolean simulate)
    {
        return super.extractVoltage(from, maxExtract, simulate);
    }

    @Override
    public double getCurrentVoltage(ForgeDirection from)
    {
        return this.voltage;
    }

    @Override
    public double getMaxVoltage(ForgeDirection from)
    {
        return 10000;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        this.rfEnergy = this.rfEnergy + rfStoredPerTick;
        return rfStoredPerTick;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return this.rfEnergy;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return 10000;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("RFEnergy", this.rfEnergy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.rfEnergy = nbt.getInteger("RFEnergy");
    }
}
