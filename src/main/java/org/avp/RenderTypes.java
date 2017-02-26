package org.avp;

import com.arisux.mdxlib.lib.game.IPreInitEvent;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderTypes implements IPreInitEvent
{
    public static RenderTypes instance = new RenderTypes();

    public int RENDER_TYPE_SHAPED;
    public int RENDER_TYPE_RESIN;

    @Override
    public void pre(FMLPreInitializationEvent event)
    {
        RENDER_TYPE_SHAPED = RenderingRegistry.getNextAvailableRenderId();
        RENDER_TYPE_RESIN = RenderingRegistry.getNextAvailableRenderId();
    }
}
