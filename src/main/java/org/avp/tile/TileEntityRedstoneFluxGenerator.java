package org.avp.tile;

import org.avp.AliensVsPredator;
import org.avp.api.power.IVoltageProvider;
import org.avp.packets.client.PacketSyncRF;

import com.arisux.mdxlib.lib.world.tile.IRotatable;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRedstoneFluxGenerator extends TileEntityElectrical implements IVoltageProvider, IEnergyReceiver, IRotatable
{
    private ForgeDirection direction;
    protected int rfEnergy;
    protected int rfStoredPerTick;
    protected int rfUsedPerTick;

    public TileEntityRedstoneFluxGenerator()
    {
        super(true);
        this.rfStoredPerTick = 25;
        this.rfUsedPerTick = 20;
        this.direction = ForgeDirection.SOUTH;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        this.updateEnergyAsReceiver();

        if (rfEnergy >= rfUsedPerTick)
        {
            this.setVoltage(240);
            rfEnergy = rfEnergy - rfUsedPerTick;
        }
        else
        {
            this.setVoltage(0);
        }

        if (!this.worldObj.isRemote && this.worldObj.getWorldTime() % 20 == 0)
        {
            AliensVsPredator.network().sendToAll(new PacketSyncRF(this.getEnergyStored(null), this.xCoord, this.yCoord, this.zCoord));
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
    
    public void setRfEnergy(int rfEnergy)
    {
        this.rfEnergy = rfEnergy;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        int usedRF = 0;
        
        if (maxReceive >= this.rfStoredPerTick && this.rfEnergy < this.getMaxEnergyStored(from))
        {
            this.rfEnergy = this.rfEnergy + rfStoredPerTick;
            usedRF = Math.min(maxReceive, rfStoredPerTick);
        }
        
        return usedRF;
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
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("RFEnergy", this.rfEnergy);

        if (this.direction != null)
        {
            nbt.setInteger("Direction", this.direction.ordinal());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.rfEnergy = nbt.getInteger("RFEnergy");
        this.direction = ForgeDirection.getOrientation(nbt.getInteger("Direction"));
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }
}
