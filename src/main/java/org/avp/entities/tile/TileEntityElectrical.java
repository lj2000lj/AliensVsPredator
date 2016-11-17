package org.avp.entities.tile;

import org.avp.util.IPowerNode;
import org.avp.util.IPowerProvider;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityElectrical extends TileEntity implements IPowerNode
{
    protected double voltage;
    protected double operationVoltage;
    protected double resistance;
    protected double boost;

    public TileEntityElectrical()
    {
        this.operationVoltage = 110;
        /** 1000 / 50Hz = 20 Ticks **/
        this.resistance = 0.1;
        this.boost = 0;
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

    /**
     * Saves the amount of voltage this component contains to the world.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setDouble("voltage", this.voltage);
    }

    /**
     * Loads the amount of voltage this component contains from the world.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.voltage = nbt.getDouble("voltage");
    }

    /**
     * @return Returns true if this component is currently operational.
     */
    public boolean isOperational()
    {
        return this.getVoltage() >= this.getOperationVoltage();
    }

    /**
     * @return Returns the amount of resistance this component applies.
     */
    public double getResistance()
    {
        return resistance;
    }

    /**
     * @param resistance - The amount of resistance this component will apply 
     * on components extracting energy from it.
     */
    public void setResistance(double resistance)
    {
        this.resistance = resistance;
    }

    /**
     * @return The threshold voltage required for this component to operate.
     */
    public double getOperationVoltage()
    {
        return operationVoltage;
    }

    /**
     * @param thresholdVoltage - The threshold voltage required for this 
     * component to operate.
     */
    public void setOperationVoltage(double thresholdVoltage)
    {
        this.operationVoltage = thresholdVoltage;
    }
    
    @Override
    public boolean canConnect(ForgeDirection from)
    {
        return true;
    }

    /**
     * @param side - ForgeDirection from the receiver. 
     * @return If this side can provide energy to the receiver.
     */
    public boolean canProvideEnergyToReceiver(ForgeDirection side)
    {
        if (this instanceof IPowerProvider)
        {
            IPowerProvider provider = (IPowerProvider) this;
            return provider.canConnect(side);
        }
        
        return false;
    }

    /**
     * @return The amount of voltage this component currently contains.
     */
    public double getVoltage()
    {
        return this.voltage;
    }

    /**
     * @param voltage - The amount of voltage this component should contain.
     */
    public void setVoltage(double voltage)
    {
        this.voltage = voltage;
    }

    /**
     * @return The amount of boost this component currently contains.
     */
    public double getBoost()
    {
        return this.boost;
    }

    /**
     * @param voltage - The amount of boost this component should contain.
     */
    public void setBoost(double boost)
    {
        this.boost = boost;
    }

    /**
     * @return The Source Direction that a receiver can extract from
     */
    public ForgeDirection getSourcePowerDirection()
    {
        return null;
    }

    /**
     * Updates the voltage of this component based on surrounding components.
     */
    public void updateEnergyAsReceiver()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (tile != null && tile instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) tile;

                if (electrical instanceof IPowerProvider)
                {
                    IPowerProvider provider = (IPowerProvider) electrical;

                    if (electrical.canProvideEnergyToReceiver(direction) && electrical.getVoltage() > this.getVoltage())
                    {
                        this.acceptVoltageFrom(direction.getOpposite(), provider.provideVoltage(direction.getOpposite(), electrical.getVoltage() - this.getVoltage(), false), false);
                    }
                }
            }
        }

        TileEntity surroundingSource = null;

        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (tile != null && tile instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) tile;

                if (electrical.getBoost() == 0 && electrical.getVoltage() > this.getVoltage() && tile instanceof IPowerProvider)
                {
                    surroundingSource = electrical;
                }
                
                if (electrical.getVoltage() > 0 && electrical.getBoost() != 0 && direction == electrical.getSourcePowerDirection())
                {
                    surroundingSource = electrical;
                }
            }
        }

        if (surroundingSource == null || this.getVoltage() < 0)
        {
            this.setVoltage(0);
        }
    }

    /**
     * Returns the amount of energy to be extracted from this component.
     * 
     * @param from - The direction this request was sent from.
     * @param maxExtract - The amount of energy we're trying to extract.
     * @param simulate - If true, this request will only be simulated.
     * @return - The amount of energy to be extracted.
     */
    public double provideVoltage(ForgeDirection from, double maxExtract, boolean simulate)
    {
        TileEntity tile = this.worldObj.getTileEntity(this.xCoord + from.offsetX, this.yCoord + from.offsetY, this.zCoord + from.offsetZ);

        if (tile != null && tile instanceof TileEntityElectrical)
        {
            return maxExtract - this.getResistance();
        }

        return 0;
    }

    /**
     * Returns the amount of energy this component will contain after adding 
     * the specified amount of energy.
     * 
     * @param from - The direction this request was sent from.
     * @param maxReceive - The amount of energy this component is receiving.
     * @param simulate - If true, this request will only be simulated.
     * @return The amount of energy this component will contain after adding 
     * the specified amount of energy.
     */
    public double acceptVoltageFrom(ForgeDirection from, double maxReceive, boolean simulate)
    {
        double result = this.getVoltage() + maxReceive;

        if (!simulate)
        {
            this.setVoltage(result);

        }

        return result;
    }
}
