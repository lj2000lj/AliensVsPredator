package org.avp;

import java.io.File;

import com.arisux.mdxlib.config.GraphicsSetting;
import com.arisux.mdxlib.lib.game.IPreInitEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Settings implements IPreInitEvent
{
    public static final Settings instance        = new Settings();

    private Configuration        configuration;

    private final String         CATEGORY_OTHER  = "general";
    private final String         CATEGORY_DIM    = "dimensions";
    private final String         CATEGORY_BIOMES = "biomes";

    private Property             explosionsEnabled;
    private Property             plasmaCannonExplosions;
    private Property             updaterEnabled;
    private Property             debugToolsEnabled;
    private Property             nukesEnabled;
    private Property             overworldSpawnsEnabled;
    private Property             autoSpawnsEnabled;
    private Property             evolvedXenomorphSpawns;
    private Property             dimVarda;
    private Property             dimAcheron;
    private Property             biomeVarda;
    private Property             biomeVardaForest;
    private Property             biomeAcheron;
    private Property             globalSoundVolume;

    @SideOnly(Side.CLIENT)
    public static class ClientSettings
    {
        public static final ClientSettings instance          = new ClientSettings();

        private Configuration              configuration;

        private final String               CATEGORY_GRAPHICS = "graphics";

        private Property                   hiveTesselation;

        public void load(Configuration configuration)
        {
            this.configuration = configuration;
            hiveTesselation = configuration.get(CATEGORY_GRAPHICS, "HIVE_TESSELLATION", GraphicsSetting.ULTRA.ordinal());
        }

        public void saveClientSettings()
        {
            hiveTesselation.set(this.getHiveTesselation().level());
            configuration.save();
        }

        public GraphicsSetting getHiveTesselation()
        {
            return GraphicsSetting.level(hiveTesselation);
        }

        public void toggleHiveTessellation()
        {
            this.hiveTesselation.set(getHiveTesselation().next().level());
        }
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent evt)
    {
        configuration = new Configuration(new File(evt.getModConfigurationDirectory(), "aliensvspredator.config"));
        try
        {
            configuration.load();

            if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
            {
                ClientSettings.instance.load(configuration);
            }

            dimVarda = configuration.get(CATEGORY_DIM, "varda", 223);
            dimAcheron = configuration.get(CATEGORY_DIM, "acheron", 426);

            biomeVarda = configuration.get(CATEGORY_BIOMES, "varda_badlands", 223);
            biomeAcheron = configuration.get(CATEGORY_BIOMES, "acheron", 224);
            biomeVardaForest = configuration.get(CATEGORY_BIOMES, "varda_forest", 229);

            plasmaCannonExplosions = configuration.get(CATEGORY_OTHER, "plasma_cannon_explosions", false);
            explosionsEnabled = configuration.get(CATEGORY_OTHER, "explosion_block_damage", true);
            nukesEnabled = configuration.get(CATEGORY_OTHER, "nukes", true);
            overworldSpawnsEnabled = configuration.get(CATEGORY_OTHER, "overworld_spawning", true);
            autoSpawnsEnabled = configuration.get(CATEGORY_OTHER, "auto_spawning", true);
            evolvedXenomorphSpawns = configuration.get(CATEGORY_OTHER, "mature_spawns", true);
            updaterEnabled = configuration.get(CATEGORY_OTHER, "updater", true, "Toggle the mod's updater.");
            debugToolsEnabled = configuration.get(CATEGORY_OTHER, "debug_tools", false, "Toggle the debugging tools.");
            globalSoundVolume = configuration.get(CATEGORY_OTHER, "global_volume", 75, "Change the volume of this mod's sounds. EXAMPLES: 100 = 100% Volume, 50 = 50% Volume, 150 = 150% Volume");
        }
        finally
        {
            configuration.save();
        }
    }

    public Configuration getConfig()
    {
        return configuration;
    }

    public boolean areOverworldSpawnsEnabled()
    {
        return overworldSpawnsEnabled.getBoolean();
    }

    public boolean areAutoSpawnsEnabled()
    {
        return autoSpawnsEnabled.getBoolean();
    }
    
    public boolean arePlasmaCannonExplosionsEnabled()
    {
        return plasmaCannonExplosions.getBoolean();
    }

    public boolean shouldEvolvedXenomorphsSpawn()
    {
        return evolvedXenomorphSpawns.getBoolean();
    }

    public boolean areExplosionsEnabled()
    {
        return this.explosionsEnabled.getBoolean();
    }

    public boolean isUpdaterEnabled()
    {
        return this.updaterEnabled.getBoolean();
    }

    public boolean areDebugToolsEnabled()
    {
        return this.debugToolsEnabled.getBoolean();
    }

    public int dimensionIdVarda()
    {
        return this.dimVarda.getInt();
    }

    public int dimensionIdAcheron()
    {
        return this.dimAcheron.getInt();
    }

    public int biomeIdVardaBadlands()
    {
        return this.biomeVarda.getInt();
    }

    public int biomeIdVardaForest()
    {
        return this.biomeVardaForest.getInt();
    }

    public int biomeIdAcheron()
    {
        return this.biomeAcheron.getInt();
    }

    public boolean areNukesEnabled()
    {
        return this.nukesEnabled.getBoolean();
    }

    public float globalSoundVolume()
    {
        return this.globalSoundVolume.getInt() / 100F;
    }
}
