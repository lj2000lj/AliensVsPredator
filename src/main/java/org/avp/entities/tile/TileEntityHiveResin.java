package org.avp.entities.tile;

import java.util.UUID;

import org.avp.util.IHiveSignature;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHiveResin extends TileEntity implements IHiveSignature
{
    private UUID  signature;
    private Block blockCovering;

    @Override
    public void updateEntity()
    {
        ;
    }

    @Override
    public UUID getHiveSignature()
    {
        return this.signature;
    }

    @Override
    public void setHiveSignature(UUID signature)
    {
        this.signature = signature;
    }

    public void setBlockCovering(Block blockCovering)
    {
        this.blockCovering = blockCovering;
    }

    public Block getBlockCovering()
    {
        return this.blockCovering;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        String blockString = compound.getString("BlockCovered");

        if (blockString != null && blockString.contains(":"))
        {
            String[] identifier = blockString.split(":");
            this.blockCovering = GameRegistry.findBlock(identifier[0], identifier[1]);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (blockCovering != null)
        {
            UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(this.blockCovering);

            if (identifier != null)
            {
                compound.setString("BlockCovered", String.format("%s:%s", identifier.modId, identifier.name));
            }
        }
    }
}
