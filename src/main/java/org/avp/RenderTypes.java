package org.avp;

import com.arisux.mdxlib.lib.game.IPreInitEvent;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


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
