package org.avp.event;

import org.avp.AliensVsPredator;
import org.avp.DamageSources;
import org.avp.dimension.varda.ProviderVarda;
import org.avp.packets.client.PacketVardaStormMoveEntity;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.Worlds;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class VardaStormHandler
{
    public static VardaStormHandler instance = new VardaStormHandler();
    private int stormUpdateCount = 0;
    private int cloudTickCounter = 0;

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        this.updateStorm();
        this.updateClouds(event.world);

        if (isStormActive(event.world))
        {
            if (event.world.isRemote)
            {
                Game.minecraft().thePlayer.motionZ += 0.04F;
                Game.minecraft().thePlayer.motionY += MathHelper.sin(event.world.getWorldTime() * 0.4F) * 0.1F;
                Game.minecraft().thePlayer.fallDistance = 0F;
                AliensVsPredator.network().sendToAll(new PacketVardaStormMoveEntity(Integer.valueOf(Game.minecraft().thePlayer.getEntityId())));
            }

            Object[] entities = event.world.loadedEntityList.toArray();

            for (Object o : entities)
            {
                if (o instanceof Entity)
                {
                    Entity entity = (Entity) o;

                    if (event.world != null && entity.worldObj.provider instanceof ProviderVarda && Worlds.canSeeSky(new Pos(entity), event.world))
                    {
                        entity.motionZ += 0.04F;
                        entity.motionY += MathHelper.sin(entity.worldObj.getWorldTime() * 0.4F) * 0.1F;
                        entity.fallDistance = 0F;

                        AliensVsPredator.network().sendToAll(new PacketVardaStormMoveEntity(Integer.valueOf(entity.getEntityId())));

                        entity.attackEntityFrom(DamageSources.silicaStorm, 0.5F);
                    }
                }
            }
        }
    }

    public boolean isStormActive(World worldObj)
    {
        return isStormActive(worldObj.getWorldTime());
    }

    public boolean isStormActive(long atTime)
    {
        return toHours(atTime) >= getStormStartTime() && toHours(atTime) <= getStormEndTime();
    }

    public long toHours(long time)
    {
        return (time % 24000L) / 1000L;
    }

    public long getStormStartTime()
    {
        return 3L;
    }

    public long getStormEndTime()
    {
        return 4L;
    }

    public void updateStorm()
    {
        this.stormUpdateCount++;
    }

    public void updateClouds(World world)
    {
        if (world.isRemote)
        {
            if (!Game.minecraft().isGamePaused())
            {
                this.cloudTickCounter++;
            }
        }
    }

    public int getStormUpdateCount()
    {
        return this.stormUpdateCount;
    }

    @SideOnly(Side.CLIENT)
    public int getCloudTickCounter()
    {
        return cloudTickCounter;
    }
}
