package org.avp.entities.tile;

import org.avp.util.IPowerDrain;
import org.avp.util.IPowerNode;
import org.avp.util.IPowerSource;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityElectrical extends TileEntity implements IPowerNode, IPowerDrain, IPowerSource
{
    protected double voltage;
    protected double amperage;
    protected double voltagePrev;
    protected double resistance;
    protected double operationVoltage;

    public TileEntityElectrical()
    {
        this.operationVoltage = 110;
        this.resistance = 0.001;
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
        nbt.setDouble("amperage", this.amperage);
    }

    /**
     * Loads the amount of voltage this component contains from the world.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.voltage = nbt.getDouble("voltage");
        this.amperage = nbt.getDouble("amperage");
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
        return this.resistance;
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

    /**
     * Used to determine if powerlines can connect to this node.
     */
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
        if (this instanceof IPowerSource)
        {
            IPowerSource provider = (IPowerSource) this;
            return provider.canConnect(side);
        }

        return false;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        this.voltagePrev = this.voltage;
    }

    /**
     * Updates the voltage of this component based on surrounding components.
     */
    public void updateEnergyAsReceiver()
    {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity t = this.worldObj.getTileEntity(this.xCoord + direction.offsetX, this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);

            if (t != null && t instanceof TileEntityElectrical)
            {
                TileEntityElectrical electrical = (TileEntityElectrical) t;

                if (electrical instanceof IPowerSource)
                {
                    IPowerSource provider = (IPowerSource) electrical;

                    if (electrical.canProvideEnergyToReceiver(direction))
                    {
                        this.drainPower(provider, provider.provideVoltage(this), provider.provideAmperage(this));

                        // if (electrical instanceof TileEntityPowerline && this.getVoltage() <= 0)
                        // electrical.setVoltage(0);
                    }
                }
            }
        }

        TileEntity src = null;

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity t = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

            if (t != null && t instanceof TileEntityElectrical)
            {
                TileEntityElectrical e = (TileEntityElectrical) t;

                if (t instanceof IPowerSource)
                {
                    if (e.getVoltage() > this.getVoltage())
                    {
                        src = e;
                    }
                }
            }
        }

        if (src == null)
        {
            this.setVoltage(0);
            this.setAmperage(0);
        }
    }

    @Override
    public double provideVoltage(IPowerNode to)
    {
        TileEntity drain = (TileEntity) to;

        if (drain != null && drain instanceof TileEntityElectrical)
        {
            return voltage - this.getResistance();
        }

        return 0;
    }

    @Override
    public double provideAmperage(IPowerNode to)
    {
        TileEntity drain = (TileEntity) to;

        if (drain != null && drain instanceof TileEntityElectrical)
        {
            return amperage - this.getResistance();
        }

        return 0;
    }

    @Override
    public void drainPower(IPowerNode from, double voltage, double amperage)
    {
        if (voltage > (this.getVoltage() + this.getResistance()))
        {
            this.setVoltage(voltage - this.getResistance());
        }

        if (amperage > (this.getAmperage() + this.getResistance()))
        {
            this.setAmperage(amperage - this.getResistance());
        }
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

    @Override
    public double getVoltageThreshold()
    {
        return 120;
    }

    @Override
    public double getAmperage()
    {
        return this.amperage;
    }

    public void setAmperage(double amperage)
    {
        this.amperage = amperage;
    }

    @Override
    public double getAmperageThreshold()
    {
        return 10000;
    }
}
