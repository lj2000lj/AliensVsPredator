package org.avp.event;

import org.avp.entities.extended.Organism;
import org.avp.entities.mob.EntityChestburster;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EmbryoTickEvent
{
    public static final EmbryoTickEvent instance = new EmbryoTickEvent();

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event)
    {
        for (int i = 0; i < event.world.loadedEntityList.size(); ++i)
        {
            Entity entity = (Entity) event.world.loadedEntityList.get(i);

            if (entity != null && entity instanceof EntityLivingBase)
            {
                EntityLivingBase living = (EntityLivingBase) entity;
                EntityPlayer player = null;

                if (living instanceof EntityPlayer)
                {
                    player = (EntityPlayer) living;
                }
                
                Organism organism = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);

                if (organism.hasEmbryo())
                {
                    if (living instanceof EntityPlayer && !((EntityPlayer) living).capabilities.isCreativeMode || !(living instanceof EntityPlayer))
                    {
                        organism.getEmbryo().tick(organism);
                    }

                    if (event.world.getWorldTime() % 60 == 0)
                    {
                        organism.syncWithClients();
                    }

                    if (!entity.isDead && organism.getEmbryo() != null)
                    {
                        if (organism.getEmbryo().getAge() >= organism.getEmbryo().getGestationPeriod())
                        {
                            EntityChestburster.emergeFromHost(organism);
                        }

                        if (organism.getEmbryo() != null && organism.getEmbryo().getAge() <= organism.getEmbryo().getGestationPeriod())
                        {
                            if (player == null || player != null && !player.capabilities.isCreativeMode)
                            {
                                living.addPotionEffect(new PotionEffect(Potion.blindness.getId(), organism.getEmbryo().getGestationPeriod() / 2));
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
    }

    @SubscribeEvent
    public void respawnEvent(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
    {
        EntityLivingBase living = (EntityLivingBase) event.player;
        Organism livingProperties = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);

        if (livingProperties.hasEmbryo())
        {
            livingProperties.setEmbryo(null);
        }
    }

    @SubscribeEvent
    public void despawnEvent(net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn event)
    {
        EntityLivingBase living = (EntityLivingBase) event.entityLiving;
        Organism livingProperties = (Organism) living.getExtendedProperties(Organism.IDENTIFIER);

        if (livingProperties.hasEmbryo())
        {
            event.setResult(Result.DENY);
        }
    }
}
