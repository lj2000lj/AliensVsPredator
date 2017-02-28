package org.avp.world;

import org.avp.entities.Organism;
import org.avp.entities.SharedPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.getEntity().Entityevent.getEntity()Constructing;
import net.minecraftforge.event.getEntity().EntityJoinWorldEvent;
import net.minecraftforge.event.getEntity().player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExtendedEntityHandler
{
    public static final ExtendedEntityHandler instance = new ExtendedEntityHandler();
    
    @SubscribeEvent
    public void onEntityTrackEvent(PlayerEvent.StartTracking event)
    {
        this.syncEntity(event.target);
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer thePlayer = (EntityPlayer) event.getEntity();

            if (thePlayer != null)
            {
                SharedPlayer extendedPlayer = new SharedPlayer(thePlayer);
                thePlayer.registerExtendedProperties(SharedPlayer.IDENTIFIER, extendedPlayer);
            }
        }

        if (event.getEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase entityLiving = (EntityLivingBase) event.getEntity();

            if (entityLiving != null)
            {
                Organism extendedLiving = new Organism(entityLiving);
                entityLiving.registerExtendedProperties(Organism.IDENTIFIER, extendedLiving);
            }
        }
    }

    @SubscribeEvent
    public void onEntitySpawnInWorld(EntityJoinWorldEvent event)
    {
        if (event.getEntity() != null && !event.getEntity().worldObj.isRemote)
        {
            this.syncEntity(event.getEntity());
        }
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        ;
    }

    public void syncEntity(Entity target)
    {
        WorldServer worldServer = (WorldServer) target.worldObj;

        if (worldServer != null)
        {
            EntityTracker tracker = worldServer.getEntityTracker();

            if (tracker != null && target != null)
            {
                if (target instanceof EntityLivingBase)
                {
                    Organism organism = (Organism) target.getExtendedProperties(Organism.IDENTIFIER);

                    if (organism != null)
                    {
                        if (target instanceof EntityPlayer)
                        {
                            organism.syncWithClients();
                        }
                    }
                }

                if (target instanceof EntityPlayer)
                {
                    SharedPlayer specialPlayer = (SharedPlayer) target.getExtendedProperties(SharedPlayer.IDENTIFIER);

                    if (specialPlayer != null)
                    {
                        specialPlayer.syncWithClients();
                    }
                }
            }
        }
    }
}
