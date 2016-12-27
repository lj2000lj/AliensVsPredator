package org.avp.event;

import org.avp.DamageSources;
import org.avp.entities.extended.ExtendedEntityLivingBase;
import org.avp.entities.mob.EntityChestburster;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
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
                
                ExtendedEntityLivingBase livingProperties = (ExtendedEntityLivingBase) living.getExtendedProperties(ExtendedEntityLivingBase.IDENTIFIER);

                if (livingProperties.doesEntityContainEmbryo())
                {
                    if (living instanceof EntityPlayer && !((EntityPlayer) living).capabilities.isCreativeMode || !(living instanceof EntityPlayer))
                    {
                        livingProperties.tickEmbryo();
                    }

                    if (event.world.getWorldTime() % 60 == 0)
                    {
                        livingProperties.syncClients();
                    }

                    if (!entity.isDead && livingProperties.getEmbryo() != null)
                    {
                        if (livingProperties.getEmbryo().getTicksExisted() >= livingProperties.getEmbryo().getGestationPeriod())
                        {
                            EntityChestburster chestburster = new EntityChestburster(event.world);
                            chestburster.setHostParasiteType(livingProperties.getEmbryo().getType());
                            chestburster.setLocationAndAngles(living.posX, living.posY, living.posZ, 0.0F, 0.0F);
                            event.world.spawnEntityInWorld(chestburster);
                            entity.attackEntityFrom(DamageSources.causeChestbursterDamage(chestburster, entity), 100000F);
                            living.getActivePotionEffects().clear();
                            livingProperties.setEmbryo(null);
                        }

                        if (livingProperties.getEmbryo() != null && livingProperties.getEmbryo().getTicksExisted() <= livingProperties.getEmbryo().getGestationPeriod())
                        {
                            if (player == null || player != null && !player.capabilities.isCreativeMode)
                            {
                                living.addPotionEffect(new PotionEffect(Potion.blindness.getId(), livingProperties.getEmbryo().getGestationPeriod() / 2));
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
        ExtendedEntityLivingBase livingProperties = (ExtendedEntityLivingBase) living.getExtendedProperties(ExtendedEntityLivingBase.IDENTIFIER);

        if (livingProperties.doesEntityContainEmbryo())
        {
            livingProperties.setEmbryo(null);
        }
    }

    @SubscribeEvent
    public void despawnEvent(net.minecraftforge.event.entity.living.LivingSpawnEvent.AllowDespawn event)
    {
        EntityLivingBase living = (EntityLivingBase) event.entityLiving;
        ExtendedEntityLivingBase livingProperties = (ExtendedEntityLivingBase) living.getExtendedProperties(ExtendedEntityLivingBase.IDENTIFIER);

        if (livingProperties.doesEntityContainEmbryo())
        {
            event.setResult(Result.DENY);
        }
    }
}
