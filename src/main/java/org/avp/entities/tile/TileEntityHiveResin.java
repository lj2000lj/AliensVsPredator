package org.avp.entities.tile;

import java.util.Random;
import java.util.UUID;

import org.avp.event.HiveHandler;
import org.avp.util.XenomorphHive;

import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.world.Worlds;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHiveResin extends TileEntity
{
    private ResinVariant variant;
    private UUID         signature;
    private Block        blockCovering;
    public Block         northBlock;
    public Block         northTopBlock;
    public Block         northBottomBlock;
    public Block         southBlock;
    public Block         southTopBlock;
    public Block         southBottomBlock;
    public Block         eastBlock;
    public Block         eastTopBlock;
    public Block         eastBottomBlock;
    public Block         westBlock;
    public Block         westTopBlock;
    public Block         westBottomBlock;
    public Block         bottomBlock;
    public Block         topBlock;

    public enum ResinVariant
    {
        VARIANT1(1, 0, -1, +1, 0, 0, +1, -1, 0),
        VARIANT2(2, -1, 0, 0, -1, +1, 0, 0, +1),
        VARIANT3(3, 0, +1, -1, 0, 0, -1, +1, 0),
        VARIANT4(4, +1, 0, 0, +1, -1, 0, 0, -1);

        public int id;
        public int nX;
        public int nZ;
        public int sX;
        public int sZ;
        public int eX;
        public int eZ;
        public int wX;
        public int wZ;

        ResinVariant(int id, int nX, int nZ, int eX, int eZ, int sX, int sZ, int wX, int wZ)
        {
            this.id = id;
            this.nX = nX;
            this.nZ = nZ;
            this.sX = sX;
            this.sZ = sZ;
            this.eX = eX;
            this.eZ = eZ;
            this.wX = wX;
            this.wZ = wZ;
        }

        public static ResinVariant fromId(int id)
        {
            for (ResinVariant rotation : values())
            {
                if (rotation.id == id)
                {
                    return rotation;
                }
            }

            return null;
        }
    }

    @Override
    public void updateEntity()
    {
        if (Game.minecraft().theWorld != null && this.worldObj.getWorldTime() % 20 == 0)
        {
            if (variant != null)
            {
                bottomBlock = Game.minecraft().theWorld.getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
                topBlock = Game.minecraft().theWorld.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);

                northBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.nX, this.yCoord, this.zCoord + variant.nZ);
                northTopBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.nX, this.yCoord + 1, this.zCoord + variant.nZ);
                northBottomBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.nX, this.yCoord - 1, this.zCoord + variant.nZ);

                southBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.sX, this.yCoord, this.zCoord + variant.sZ);
                southTopBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.sX, this.yCoord + 1, this.zCoord + variant.sZ);
                southBottomBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.sX, this.yCoord - 1, this.zCoord + variant.sZ);

                eastBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.eX, this.yCoord, this.zCoord + variant.eZ);
                eastTopBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.eX, this.yCoord + 1, this.zCoord + variant.eZ);
                eastBottomBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.eX, this.yCoord - 1, this.zCoord + variant.eZ);

                westBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.wX, this.yCoord, this.zCoord + variant.wZ);
                westTopBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.wX, this.yCoord + 1, this.zCoord + variant.wZ);
                westBottomBlock = Game.minecraft().theWorld.getBlock(this.xCoord + variant.wX, this.yCoord - 1, this.zCoord + variant.wZ);
            }
            else
            {
                this.variant = ResinVariant.fromId(1 + new Random().nextInt(ResinVariant.values().length));
            }
        }
    }

    public XenomorphHive getHive()
    {
        return HiveHandler.instance.getHiveForUUID(this.signature);
    }

    public UUID getHiveSignature()
    {
        return this.signature;
    }

    public void setHiveSignature(UUID signature)
    {
        this.signature = signature;
    }

    public void setBlockCovering(Block blockCovering, int meta)
    {
        this.blockCovering = blockCovering;
    }

    public Block getBlockCovering()
    {
        return this.blockCovering;
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
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        int variantOrdinal = compound.getInteger("RandomVariant");
        String blockString = compound.getString("BlockCovered");

        if (blockString != null && blockString.contains(":"))
        {
            String[] identifier = blockString.split(":");
            this.blockCovering = GameRegistry.findBlock(identifier[0], identifier[1]);
        }

        this.signature = Worlds.uuidFromNBT(compound, "HiveSignature");
        this.variant = ResinVariant.fromId(variantOrdinal == 0 ? 1 + new Random().nextInt(ResinVariant.values().length) : variantOrdinal);
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

        compound.setString("HiveSignature", signature != null ? this.signature.toString() : "");
        compound.setInteger("RandomVariant", this.variant != null ? this.variant.id : 0);
    }

    public ResinVariant getVariant()
    {
        return this.variant;
    }

    public void setVariant(ResinVariant variant)
    {
        this.variant = variant;
    }
}
