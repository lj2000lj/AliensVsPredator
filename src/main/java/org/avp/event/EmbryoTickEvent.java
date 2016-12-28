package org.avp.event;

import org.avp.api.parasitoidic.INascentic;
import org.avp.entities.extended.Organism;
import org.avp.entities.mob.EntityChestburster;

import com.arisux.mdxlib.lib.world.entity.Entities;

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
                EntityLivingBase host = (EntityLivingBase) entity;
                EntityPlayer player = null;

                if (host instanceof EntityPlayer)
                {
                    player = (EntityPlayer) host;
                }

                Organism hostOrganism = (Organism) host.getExtendedProperties(Organism.IDENTIFIER);

                if (hostOrganism.hasEmbryo())
                {
                    if (host instanceof EntityPlayer && !((EntityPlayer) host).capabilities.isCreativeMode || !(host instanceof EntityPlayer))
                    {
                        hostOrganism.getEmbryo().tick(hostOrganism);
                    }

                    if (event.world.getWorldTime() % 60 == 0)
                    {
                        hostOrganism.syncWithClients();
                    }

                    if (!entity.isDead && hostOrganism.getEmbryo() != null)
                    {
                        if (hostOrganism.getEmbryo().getAge() >= hostOrganism.getEmbryo().getGestationPeriod())
                        {
                            Class<? extends INascentic> nascenticOrganismType = hostOrganism.getEmbryo().getNasenticOrganism();
                            Entity nascenticEntity = Entities.constructEntity(event.world, (Class<? extends Entity>) nascenticOrganismType);
                            INascentic nascenticOrganism = (INascentic) nascenticEntity;

                            // for (Object o : EntityList.stringToClassMapping.values())
                            // {
                            // if (o instanceof Class)
                            // {
                            // Class<? extends Entity> entityClass = (Class<? extends Entity>) o;
                            //
                            // if (host.getClass() == entityClass)
                            // {
                            //
                            // }
                            // }
                            // }

                            if (nascenticOrganism != null)
                            {
                                nascenticOrganism.setMatureState(hostOrganism.getEmbryo().getResultingOrganism());
                                nascenticOrganism.emerge(host);
                            }
                            else
                            {
                                System.out.println("embryo: " +  hostOrganism.getEmbryo());
                                System.out.println("nascenticOrganismType: " + nascenticOrganismType);
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
