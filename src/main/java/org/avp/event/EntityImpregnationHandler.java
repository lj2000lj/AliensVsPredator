package org.avp.event;

import org.avp.entities.extended.Organism;
import org.avp.entities.fx.EntityBloodFX;

import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class EntityImpregnationHandler
{
    public static final EntityImpregnationHandler instance = new EntityImpregnationHandler();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event)
    {
        this.tick(Game.minecraft().theWorld);
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event)
    {
        this.tick(event.world);
    }

    public void tick(World world)
    {
        if (world != null)
        {
            for (int x = 0; x < world.loadedEntityList.size(); ++x)
            {
                Entity entity = (Entity) world.loadedEntityList.get(x);

                if (entity != null && entity instanceof EntityLivingBase)
                {
                    EntityLivingBase host = (EntityLivingBase) entity;
                    Organism hostOrganism = (Organism) host.getExtendedProperties(Organism.IDENTIFIER);
                    EntityPlayer player = null;

                    if (host instanceof EntityPlayer)
                    {
                        player = (EntityPlayer) host;
                    }

                    hostOrganism.onTick(world);

                    if (host.isEntityAlive() && hostOrganism.hasEmbryo())
                    {

                        if (player != null && !player.capabilities.isCreativeMode || player == null)
                        {
                            if (!world.isRemote)
                            {
                                hostOrganism.gestate();
                            }
                            else if (world.isRemote)
                            {
                                if (!Game.minecraft().isGamePaused)
                                {
                                    hostOrganism.gestate();
                                }
                            }
                        }

                        if (hostOrganism.getEmbryo().getAge() >= hostOrganism.getEmbryo().getGestationPeriod())
                        {
                            if (hostOrganism.getEmbryo().getNascenticOrganism() != null)
                            {
                                if (!world.isRemote)
                                {
                                    hostOrganism.getEmbryo().getNascenticOrganism().vitalize(host);
                                }
                            }
                        }

                        if (hostOrganism.hasEmbryo() && hostOrganism.getEmbryo().getAge() > 0)
                        {
                            if (player == null || player != null && !player.capabilities.isCreativeMode)
                            {
                                int age = hostOrganism.getEmbryo().getAge();
                                int gestationPeriod = hostOrganism.getEmbryo().getGestationPeriod();
                                int timeLeft = gestationPeriod - age;
                                int timeBlind = gestationPeriod - (gestationPeriod / 2);
                                int timeBleed = gestationPeriod - (gestationPeriod / 10);

                                if (age >= timeBlind)
                                {
                                    if (!world.isRemote)
                                    {
                                        host.addPotionEffect(new PotionEffect(Potion.blindness.getId(), hostOrganism.getEmbryo().getGestationPeriod() / 2));
                                    }
                                }

                                if (world.isRemote && timeLeft <= 3)
                                {
                                    float spread = 0.5F;

                                    for (int i = 1024; i > 0; i--)
                                    {
                                        Game.minecraft().effectRenderer.addEffect(new EntityBloodFX(world, host.posX + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), host.posY + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), host.posZ + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), 0x610000));
                                    }
                                }

                                if (world.isRemote && age >= timeBleed)
                                {
                                    float spread = 0.5F;

                                    if (host.getRNG().nextInt(100) == 0)
                                    {
                                        for (int i = 64; i > 0; i--)
                                        {
                                            int particleColor = 0;

                                            switch (host.getRNG().nextInt(3))
                                            {
                                                case 0:
                                                    particleColor = 0x550000;
                                                    break;
                                                case 1:
                                                    particleColor = 0x440000;
                                                    break;
                                                default:
                                                    particleColor = 0x660000;
                                                    break;
                                            }
                                            Game.minecraft().effectRenderer.addEffect(new EntityBloodFX(world, host.posX + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), host.posY + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), host.posZ + (host.getRNG().nextDouble() * spread) - (host.getRNG().nextDouble() * spread), particleColor));
                                        }
                                    }
                                }
                            }

                            if (player != null && player.capabilities.isCreativeMode)
                            {
                                if (!world.isRemote)
                                {
                                    player.clearActivePotions();
                                }
                            }
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
