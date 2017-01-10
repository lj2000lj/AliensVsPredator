/** AliensVsPredator Minecraft Mod - Copyright (C) 2012-2017 Arisux Technology Group **/
package org.avp;

import org.avp.event.HiveHandler;
import org.avp.init.Assembler;
import org.avp.init.Wristbracer;

import com.arisux.mdxlib.MDX;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.IMod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

@Mod(name = "AliensVsPredator", modid = AliensVsPredator.ID, dependencies = "required-after:mdxlib")
public class AliensVsPredator implements IMod
{
    public static final String      ID = "avp";

    @Mod.Instance(AliensVsPredator.ID)
    private static AliensVsPredator instance;
    
    private ModContainer            container;

    @Override
    public ModContainer container()
    {
        return this.container == null ? this.container = Game.getModContainer(AliensVsPredator.ID) : this.container;
    }

    @Override
    public String domain()
    {
        return container().getModId() + ":";
    }

    public static AliensVsPredator instance()
    {
        return AliensVsPredator.instance;
    }

    public static ItemHandler items()
    {
        return ItemHandler.instance;
    }

    public static BlockHandler blocks()
    {
        return BlockHandler.instance;
    }

    public static OreHandler ores()
    {
        return OreHandler.instance;
    }

    public static FluidHandler fluids()
    {
        return FluidHandler.instance;
    }

    public static MaterialHandler materials()
    {
        return MaterialHandler.instance;
    }

    public static EventHandlers events()
    {
        return EventHandlers.instance;
    }

    public static NetworkHandler network()
    {
        return NetworkHandler.instance;
    }

    public static DimensionHandler dimensions()
    {
        return DimensionHandler.instance;
    }

    public static WorldHandler world()
    {
        return WorldHandler.instance;
    }

    public static EntityHandler entities()
    {
        return EntityHandler.instance;
    }

    public static RenderTypes renderTypes()
    {
        return RenderTypes.instance;
    }

    @SideOnly(Side.CLIENT)
    public static Renderers renderers()
    {
        return Renderers.instance;
    }

    @SideOnly(Side.CLIENT)
    public static KeybindHandler keybinds()
    {
        return KeybindHandler.instance;
    }

    @SideOnly(Side.CLIENT)
    public static Resources resources()
    {
        return Resources.instance;
    }

    public static Sounds sounds()
    {
        return Sounds.instance;
    }

    public static GuiHandler interfaces()
    {
        return GuiHandler.instance;
    }

    public static Properties properties()
    {
        return Properties.instance;
    }

    public static CraftingHandler crafting()
    {
        return CraftingHandler.instance;
    }

    public static PlayerModeHandler playermodehandler()
    {
        return PlayerModeHandler.instance;
    }

    public static Assembler assembler()
    {
        return Assembler.instance;
    }

    @SideOnly(Side.CLIENT)
    public static Wristbracer wristbracer()
    {
        return Wristbracer.instance;
    }

    public static Schematics schematics()
    {
        return Schematics.instance;
    }

    public static CommandHandler commands()
    {
        return CommandHandler.instance;
    }

    public static Settings settings()
    {
        return Settings.instance;
    }

    public static CreativeTabs tabMain()
    {
        return CreativeTab.tabMain;
    }

    public static CreativeTabs tabBlocks()
    {
        return CreativeTab.tabBlocks;
    }

    public static CreativeTabs tabEntities()
    {
        return CreativeTab.tabEntities;
    }

    public static CreativeTabs tabGunComponents()
    {
        return CreativeTab.tabGunParts;
    }

    public static CreativeTabs tabRecipeItems()
    {
        return CreativeTab.tabRecipeItems;
    }

    @Override
    @Mod.EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        MDX.log().info("[AliensVsPredator] Copyright(C) 2012-2017 Arisux Technology Group");
        MDX.log().info("[AliensVsPredator] Pre-Initialization");

        settings().preInitialize(event);
        renderTypes().pre(event);
        this.remapItemIdentities();
    }

    private void remapItemIdentities()
    {
        MDX.registerMappingInfo("summon.protomorph", "summon.deacon", AliensVsPredator.ID);
    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MDX.log().info("[AliensVsPredator] Initialization");

        fluids().init(event);
        network().init(event);
        materials().init(event);
        items().init(event);
        blocks().init(event);
        ores().init(event);
        dimensions().init(event);
        world().init(event);
        crafting().init(event);
        interfaces().init(event);
        events().init(event);
        commands().init(event);
        playermodehandler().init(event);
        schematics().init(event);
        assembler().init(event);
        entities().init(event);

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            wristbracer().init(event);
        }
    }

    @Override
    @Mod.EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        MDX.log().info("[AliensVsPredator] Post-Initialization");

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            sounds().post(event);
            renderers().post(event);
            keybinds().post(event);
        }
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        commands().onServerStarting(event);
    }

    @EventHandler
    public void onServerStopped(FMLServerStoppedEvent event)
    {
        HiveHandler.instance.clearCaches();
    }

    public boolean isDevCopy()
    {
        return true;
    }
}
