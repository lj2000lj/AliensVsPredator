package org.avp;

import com.arisux.amdxlib.lib.game.IPreInitEvent;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RenderTypes implements IPreInitEvent
{
    public static RenderTypes instance = new RenderTypes();

    public int RENDER_TYPE_SHAPED;

    @Override
    public void pre(FMLPreInitializationEvent event)
    {
        RENDER_TYPE_SHAPED = RenderingRegistry.getNextAvailableRenderId();
    }
}
