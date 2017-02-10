package org.avp;

import java.util.ArrayList;

import org.avp.event.BucketHandlingEvent;
import org.avp.event.EntityImpregnationHandler;
import org.avp.event.ExtendedEntityRegistrationHandler;
import org.avp.event.FarmlandRegistry;
import org.avp.event.HiveHandler;
import org.avp.event.client.input.InputHandler;
import org.avp.event.client.render.AmmoIndicatorRenderEvent;
import org.avp.event.client.render.BossBarEvent;
import org.avp.event.client.render.ChestbursterOverlayEvent;
import org.avp.event.client.render.FacehuggerRenderEvent;
import org.avp.event.client.render.LightmapUpdateEvent;
import org.avp.event.client.render.PlayerModeRenderEvent;
import org.avp.event.client.render.PressureHUDRenderEvent;
import org.avp.event.client.render.RenderLivingHook;
import org.avp.event.client.render.RenderPlayerAPCEvent;
import org.avp.event.client.render.RenderPlayerHotbarAPCEvent;
import org.avp.event.client.render.TacticalHUDRenderEvent;
import org.avp.event.client.render.VisionModeRenderEvent;
import org.avp.items.render.RenderMotionTrackerScreen;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.IInitEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class EventHandlers implements IInitEvent
{
    public static final EventHandlers instance = new EventHandlers();
    public ArrayList<Object>          events   = new ArrayList<Object>();

    @Override
    public void init(FMLInitializationEvent event)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            this.registerEvent(InputHandler.instance);
            this.registerEvent(RenderPlayerHotbarAPCEvent.instance);
            this.registerEvent(ChestbursterOverlayEvent.instance);
            this.registerEvent(PlayerModeRenderEvent.instance);
            this.registerEvent(AmmoIndicatorRenderEvent.instance);
            this.registerEvent(TacticalHUDRenderEvent.instance);
            this.registerEvent(PressureHUDRenderEvent.instance);
            this.registerEvent(FacehuggerRenderEvent.instance);
            this.registerEvent(VisionModeRenderEvent.instance);
            this.registerEvent(RenderMotionTrackerScreen.instance);
            this.registerEvent(LightmapUpdateEvent.instance);
            this.registerEvent(RenderPlayerAPCEvent.instance);
            this.registerEvent(BossBarEvent.instance);
            this.registerEvent(RenderLivingHook.instance);
        }

        if (FMLCommonHandler.instance().getSide() == Side.SERVER)
        {
            this.registerEvent(DimensionHandler.instance);
        }

        this.registerEvent(EntityImpregnationHandler.instance);
        this.registerEvent(ExtendedEntityRegistrationHandler.instance);
        this.registerEvent(SaveHandler.instance);
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
