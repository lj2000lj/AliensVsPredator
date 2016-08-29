/** AliensVsPredator Minecraft Mod - Copyright (C) 2012-2016 Arisux Technology Group **/
package org.avp;

import org.avp.api.AssemblerAPI;
import org.avp.api.WristbracerAPI;
import org.avp.event.HiveHandler;

import com.arisux.amdxlib.AMDXLib;
import com.arisux.amdxlib.lib.game.Game;
import com.arisux.amdxlib.lib.game.IMod;

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

@Mod(modid = AliensVsPredator.ID, acceptedMinecraftVersions = "1.7.10", canBeDeactivated = true, dependencies = "required-after:amdxlib")
public class AliensVsPredator implements IMod
{
    protected static final String   ID = "avp";

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

    public static LocalEventHandler events()
    {
        return LocalEventHandler.instance;
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

    @SideOnly(Side.CLIENT)
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

    public static AssemblerAPI assembler()
    {
        return AssemblerAPI.instance;
    }

    @SideOnly(Side.CLIENT)
    public static WristbracerAPI wristbracer()
    {
        return WristbracerAPI.instance;
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
        AMDXLib.log().info("[AliensVsPredator] Copyright(C) 2012-2016 Arisux Technology Group");
        AMDXLib.log().info("[AliensVsPredator] Pre-Initialization");

        settings().preInitialize(event);
        renderTypes().pre(event);
    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        AMDXLib.log().info("[AliensVsPredator] Initialization");

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
        AMDXLib.log().info("[AliensVsPredator] Post-Initialization");
        
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
