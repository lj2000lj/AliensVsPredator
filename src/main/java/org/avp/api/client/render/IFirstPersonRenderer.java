package org.avp.api.client.render;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface IFirstPersonRenderer
{
    public void renderFirstPerson(Event event, float partialTicks);
}
