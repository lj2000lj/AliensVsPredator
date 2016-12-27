package org.avp.event;

import org.avp.entities.extended.Organism;
import org.avp.entities.extended.SpecialPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ExtendedEntityRegistrationHandler
{
    public static final ExtendedEntityRegistrationHandler instance = new ExtendedEntityRegistrationHandler();
    
    @SubscribeEvent
    public void onEntityTrackEvent(PlayerEvent.StartTracking event)
    {
        this.syncEntity(event.target);
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer thePlayer = (EntityPlayer) event.entity;

            if (thePlayer != null)
            {
                SpecialPlayer extendedPlayer = new SpecialPlayer(thePlayer);
                thePlayer.registerExtendedProperties(SpecialPlayer.IDENTIFIER, extendedPlayer);
            }
        }

        if (event.entity instanceof EntityLivingBase)
        {
            EntityLivingBase entityLiving = (EntityLivingBase) event.entity;

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
        if (event.entity != null && !event.entity.worldObj.isRemote)
        {
            this.syncEntity(event.entity);
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
                    SpecialPlayer specialPlayer = (SpecialPlayer) target.getExtendedProperties(SpecialPlayer.IDENTIFIER);

                    if (specialPlayer != null)
                    {
                        specialPlayer.syncWithClients();
                    }
                }
            }
        }
    }
}
