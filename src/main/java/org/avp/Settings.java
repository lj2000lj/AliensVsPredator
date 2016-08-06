package org.avp;

import java.io.File;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class Settings
{
    public static final Settings instance        = new Settings();

    private File                 configFile;
    private Configuration        config;

    private final String         CATEGORY_OTHER  = "ETC";
    private final String         CATEGORY_DIM    = "DIMENSIONS";
    private final String         CATEGORY_BIOMES = "BIOMES";

    private boolean              explosionsEnabled;
    private boolean              updaterEnabled;
    private boolean              debugToolsEnabled;
    private boolean              nukesEnabled;
    private boolean              overworldSpawnsEnabled;
    private boolean              autoSpawnsEnabled;
    private int                  dimVarda;
    private int                  dimAcheron;
    private int                  biomeVarda;
    private int                  biomeAcheron;
    private int                  globalSoundVolume;

    @EventHandler
    public void preInitialize(FMLPreInitializationEvent evt)
    {
        configFile = new File(evt.getModConfigurationDirectory(), "AliensVsPredator.cfg");
        config = new Configuration(configFile);

        try
        {
            config.load();

            dimVarda = config.get(CATEGORY_DIM, "VARDA", 223).getInt();
            dimAcheron = config.get(CATEGORY_DIM, "ACHERON", 426).getInt();

            biomeVarda = config.get(CATEGORY_BIOMES, "VARDA", 223).getInt();
            biomeAcheron = config.get(CATEGORY_BIOMES, "ACHERON", 224).getInt();

            explosionsEnabled = config.get(CATEGORY_OTHER, "EXPLOSION_BLOCK_DAMAGE", true).getBoolean(true);
            nukesEnabled = config.get(CATEGORY_OTHER, "NUKES_ENABLED", true).getBoolean(true);
            overworldSpawnsEnabled = config.get(CATEGORY_OTHER, "OVERWORLD_MOB_SPAWNING", true).getBoolean(true);
            autoSpawnsEnabled = config.get(CATEGORY_OTHER, "AUTO_MOB_SPAWNING", true).getBoolean(true);
            updaterEnabled = config.get(CATEGORY_OTHER, "UPDATER_ENABLED", true, "Toggle the mod's updater.").getBoolean(true);
            debugToolsEnabled = config.get(CATEGORY_OTHER, "DEBUG_TOOLS", false, "Toggle the debugging tools.").getBoolean(false);
            globalSoundVolume = config.get(CATEGORY_OTHER, "GLOBAL_SOUND_VOLUME", 100, "Change the volume of this mod's sounds. EXAMPLES: 100 = 100% Volume, 50 = 50% Volume, 150 = 150% Volume").getInt();
        }
        finally
        {
            config.save();
        }
    }

    public File getConfigFile()
    {
        return configFile;
    }

    public Configuration getConfig()
    {
        return config;
    }

    public boolean areOverworldSpawnsEnabled()
    {
        return overworldSpawnsEnabled;
    }

    public boolean areAutoSpawnsEnabled()
    {
        return autoSpawnsEnabled;
    }

    public boolean areExplosionsEnabled()
    {
        return this.explosionsEnabled;
    }

    public boolean isUpdaterEnabled()
    {
        return this.updaterEnabled;
    }

    public boolean areDebugToolsEnabled()
    {
        return this.debugToolsEnabled;
    }

    public int dimensionIdVarda()
    {
        return this.dimVarda;
    }

    public int dimensionIdAcheron()
    {
        return this.dimAcheron;
    }

    public int biomeIdVarda()
    {
        return this.biomeVarda;
    }

    public int biomeIdAcheron()
    {
        return this.biomeAcheron;
    }

    public boolean areNukesEnabled()
    {
        return this.nukesEnabled;
    }

    public float globalSoundVolume()
    {
        return this.globalSoundVolume * 1.0F / 100;
    }
}
