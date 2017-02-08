package org.avp.entities.extended;

import org.avp.AliensVsPredator;
import org.avp.packets.client.SpecialPlayerClientSync;
import org.avp.packets.server.SpecialPlayerServerSync;
import org.avp.util.PlayerMode;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ModPlayer implements IExtendedEntityProperties
{
    public static final String  IDENTIFIER                  = "SpecialPlayer";
    private static final String ID_INT_BROADCAST_RADIUS     = "broadcastRadius";
    private static final String ID_STRING_BROADCAST_CHANNEL = "broadcastChannel";
    private static final String ID_BOOLEAN_ENTITY_CULLING   = "entityCulling";
    private static final String ID_BOOLEAN_NIGHTVISION      = "nightvisionEnabled";
    private static final String ID_BOOLEAN_CAN_CLIMB        = "canClimb";
    private EntityPlayer        thePlayer;

    /** Fields that need syncing **/
    public int                  broadcastRadius;
    private String              broadcastChannel;
    private boolean             entityCulling;
    private boolean             nightvisionEnabled;
    private boolean             canClimb;
    private PlayerMode          playerMode                  = PlayerMode.NORMAL;

    public ModPlayer(EntityPlayer player)
    {
        super();
        this.thePlayer = player;
    }

    public static final ModPlayer get(EntityPlayer player)
    {
        return (ModPlayer) player.getExtendedProperties(IDENTIFIER);
    }

    @Override
    public void init(Entity entity, World world)
    {
        this.broadcastChannel = "Default";
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        nbt.setInteger(ID_INT_BROADCAST_RADIUS, this.broadcastRadius);
        nbt.setString(ID_STRING_BROADCAST_CHANNEL, this.broadcastChannel);
        nbt.setBoolean(ID_BOOLEAN_ENTITY_CULLING, this.entityCulling);
        nbt.setBoolean(ID_BOOLEAN_NIGHTVISION, this.nightvisionEnabled);
        nbt.setBoolean(ID_BOOLEAN_CAN_CLIMB, this.canClimb);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        this.broadcastChannel = nbt.getString(ID_STRING_BROADCAST_CHANNEL);
        this.broadcastRadius = nbt.getInteger(ID_INT_BROADCAST_RADIUS);
        this.entityCulling = nbt.getBoolean(ID_BOOLEAN_ENTITY_CULLING);
        this.nightvisionEnabled = nbt.getBoolean(ID_BOOLEAN_NIGHTVISION);
        this.canClimb = nbt.getBoolean(ID_BOOLEAN_CAN_CLIMB);
    }

    public NBTTagCompound asCompoundTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        this.saveNBTData(tag);

        return tag;
    }

    public void syncWithClients()
    {
        AliensVsPredator.network().sendToAll(new SpecialPlayerClientSync(this.getEntity().getEntityId(), this.asCompoundTag()));
    }

    public void syncWithServer()
    {
        AliensVsPredator.network().sendToServer(new SpecialPlayerServerSync(this.getEntity().getEntityId(), this.asCompoundTag()));
    }

    public String getBroadcastChannel()
    {
        return broadcastChannel;
    }

    public void setBroadcastChannel(String broadcastChannel)
    {
        this.broadcastChannel = broadcastChannel;
    }

    public int getBroadcastRadius()
    {
        return broadcastRadius;
    }

    public void setBroadcastRadius(int broadcastRadius)
    {
        this.broadcastRadius = broadcastRadius;
    }

    public void setPlayerMode(PlayerMode playerMode)
    {
        this.playerMode = playerMode;
    }

    public PlayerMode getPlayerMode()
    {
        return this.playerMode;
    }

    public EntityPlayer getEntity()
    {
        return thePlayer;
    }

    public boolean isEntityCullingEnabled()
    {
        return this.entityCulling;
    }

    public void setEntityCullingEnabled(boolean concelationToggle)
    {
        this.entityCulling = concelationToggle;
    }

    public boolean isNightvisionEnabled()
    {
        return nightvisionEnabled;
    }

    public void setNightvisionEnabled(boolean nightvisionEnabled)
    {
        this.nightvisionEnabled = nightvisionEnabled;
    }

    public boolean canClimb()
    {
        return this.canClimb;
    }
    
    public void setCanClimb(boolean canClimb)
    {
        this.canClimb = canClimb;
    }
}
