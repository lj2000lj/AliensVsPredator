package org.avp.event;

import org.avp.entities.extended.Organism;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class EntityImpregnationHandler
{
    public static final EntityImpregnationHandler instance = new EntityImpregnationHandler();

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event)
    {
        for (int x = 0; x < event.world.loadedEntityList.size(); ++x)
        {
            Entity entity = (Entity) event.world.loadedEntityList.get(x);

            if (entity != null && entity instanceof EntityLivingBase)
            {
                EntityLivingBase host = (EntityLivingBase) entity;
                Organism hostOrganism = (Organism) host.getExtendedProperties(Organism.IDENTIFIER);
                EntityPlayer player = null;

                if (host instanceof EntityPlayer)
                {
                    player = (EntityPlayer) host;
                }

                hostOrganism.onTick(event.world);

                if (host.isEntityAlive() && hostOrganism.hasEmbryo())
                {
                    if (player != null && !player.capabilities.isCreativeMode || player == null)
                    {
                        hostOrganism.gestate();
                    }

                    if (hostOrganism.getEmbryo().getAge() >= hostOrganism.getEmbryo().getGestationPeriod())
                    {
                        if (hostOrganism.getEmbryo().getNascenticOrganism() != null)
                        {
                            hostOrganism.getEmbryo().getNascenticOrganism().vitalize(host);
                        }
                    }

                    if (hostOrganism.getEmbryo() != null && hostOrganism.getEmbryo().getAge() <= hostOrganism.getEmbryo().getGestationPeriod())
                    {
                        if (player == null || player != null && !player.capabilities.isCreativeMode)
                        {
                            host.addPotionEffect(new PotionEffect(Potion.blindness.getId(), hostOrganism.getEmbryo().getGestationPeriod() / 2));
                        }

                        if (player != null && player.capabilities.isCreativeMode)
                        {
                            player.clearActivePotions();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void respawnEvent(PlayerRespawnEvent event)
    {
        EntityLivingBase living = (EntityLivingBase) event.player;
        Organism livingProperties = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);

        if (livingProperties.hasEmbryo())
        {
            livingProperties.removeEmbryo();
        }
    }

    @SubscribeEvent
    public void despawnEvent(LivingSpawnEvent.AllowDespawn event)
    {
        EntityLivingBase living = (EntityLivingBase) event.entityLiving;
        Organism livingProperties = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);

        if (livingProperties.hasEmbryo())
        {
            event.setResult(Result.DENY);
        }
    }
}
