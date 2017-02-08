package org.avp.util;


import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public interface IEventRenderer
{
    public void update(Event event, Minecraft game, World world);
    
    public void render(Event event, float partialTicks);
}