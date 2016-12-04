package org.avp;

import java.util.ArrayList;

import org.avp.event.BucketHandlingEvent;
import org.avp.event.EmbryoTickEvent;
import org.avp.event.ExtendedPropertiesEvents;
import org.avp.event.FarmlandRegistry;
import org.avp.event.HiveHandler;
import org.avp.event.VardaStormHandler;
import org.avp.event.client.AlienArmorEvents;
import org.avp.event.client.AmmoIndicatorRenderEvent;
import org.avp.event.client.BossBarEvent;
import org.avp.event.client.ChestbursterOverlayEvent;
import org.avp.event.client.CommonFirearmEvents;
import org.avp.event.client.FacehuggerRenderEvent;
import org.avp.event.client.FireAPCEvent;
import org.avp.event.client.LightmapUpdateEvent;
import org.avp.event.client.PlayerModeRenderEvent;
import org.avp.event.client.PressureHUDRenderEvent;
import org.avp.event.client.PulseRifleEvents;
import org.avp.event.client.RenderMedpodEvent;
import org.avp.event.client.RenderPlayerAPCEvent;
import org.avp.event.client.RenderPlayerHotbarAPCEvent;
import org.avp.event.client.TacticalHUDRenderEvent;
import org.avp.event.client.VisionModeRenderEvent;
import org.avp.event.client.WristBracerEvents;
import org.avp.items.render.RenderMotionTrackerScreen;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.IInitEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class EventHandlers implements IInitEvent
{
    public static final EventHandlers instance = new EventHandlers();
    public ArrayList<Object>              events   = new ArrayList<Object>();

    @Override
    public void init(FMLInitializationEvent event)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            this.registerEvent(AlienArmorEvents.instance);
            this.registerEvent(FireAPCEvent.instance);
            this.registerEvent(RenderPlayerHotbarAPCEvent.instance);
            this.registerEvent(ChestbursterOverlayEvent.instance);
            this.registerEvent(CommonFirearmEvents.instance);
            this.registerEvent(PlayerModeRenderEvent.instance);
            this.registerEvent(AmmoIndicatorRenderEvent.instance);
            this.registerEvent(TacticalHUDRenderEvent.instance);
            this.registerEvent(PressureHUDRenderEvent.instance);
            this.registerEvent(FacehuggerRenderEvent.instance);
            this.registerEvent(VisionModeRenderEvent.instance);
            this.registerEvent(RenderMotionTrackerScreen.instance);
            this.registerEvent(LightmapUpdateEvent.instance);
            this.registerEvent(RenderPlayerAPCEvent.instance);
            this.registerEvent(PulseRifleEvents.instance);
            this.registerEvent(WristBracerEvents.instance);
            this.registerEvent(BossBarEvent.instance);
            this.registerEvent(RenderMedpodEvent.instance);
        }

        if (FMLCommonHandler.instance().getSide() == Side.SERVER)
        {
            this.registerEvent(DimensionHandler.instance);
        }

        this.registerEvent(EmbryoTickEvent.instance);
        this.registerEvent(ExtendedPropertiesEvents.instance);
        this.registerEvent(SaveHandler.instance);
        this.registerEvent(VardaStormHandler.instance);
        this.registerEvent(FarmlandRegistry.instance);
        this.registerEvent(BucketHandlingEvent.instance);
        this.registerEvent(HiveHandler.instance);
    }

    public void registerEvent(Object event)
    {
        this.events.add(event);
        Game.registerEventHandler(event);
    }
}
