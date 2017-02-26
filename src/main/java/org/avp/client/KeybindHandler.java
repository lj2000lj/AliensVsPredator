package org.avp.client;

import org.lwjgl.input.Keyboard;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.IPostInitEvent;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.settings.KeyBinding;

public class KeybindHandler implements IPostInitEvent
{
    public static final KeybindHandler instance      = new KeybindHandler();
    private static final String        KEYBIND_GROUP = "keybind.group.avp";

    public KeyBinding                  specialSecondary;
    public KeyBinding                  specialPrimary;
    public KeyBinding                  genericSpecial;

    @Override
    public void post(FMLPostInitializationEvent event)
    {
        specialSecondary = Game.registerKeybinding("item.special.secondary", Keyboard.KEY_R, KEYBIND_GROUP);
        specialPrimary = Game.registerKeybinding("item.special.primary", Keyboard.KEY_F, KEYBIND_GROUP);
        genericSpecial = Game.registerKeybinding("generic.special", Keyboard.KEY_V, KEYBIND_GROUP);
    }
}
