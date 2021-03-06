package org.avp;

import java.io.File;
import java.util.ArrayList;

import com.arisux.mdxlib.config.ConfigSetting;
import com.arisux.mdxlib.config.ConfigSettingBoolean;
import com.arisux.mdxlib.config.ConfigSettingGraphics;
import com.arisux.mdxlib.config.ConfigSettingInteger;
import com.arisux.mdxlib.config.GraphicsSetting;
import com.arisux.mdxlib.config.IFlexibleConfiguration;
import com.arisux.mdxlib.lib.game.IPreInitEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.config.Configuration;

public class Settings implements IPreInitEvent, IFlexibleConfiguration
{
    public static final Settings           instance        = new Settings();

    private Configuration                  configuration;
    private final ArrayList<ConfigSetting> allSettings     = new ArrayList<ConfigSetting>();

    private final String                   CATEGORY_OTHER  = "general";
    private final String                   CATEGORY_DIM    = "dimensions";
    private final String                   CATEGORY_BIOMES = "biomes";

    private ConfigSetting                  explosionsEnabled;
    private ConfigSetting                  plasmaCannonExplosions;
    private ConfigSetting                  updaterEnabled;
    private ConfigSetting                  debugToolsEnabled;
    private ConfigSetting                  nukesEnabled;
    private ConfigSetting                  overworldSpawnsEnabled;
    private ConfigSetting                  autoSpawnsEnabled;
    private ConfigSetting                  evolvedXenomorphSpawns;
    private ConfigSetting                  dimVarda;
    private ConfigSetting                  dimAcheron;
    private ConfigSetting                  biomeVarda;
    private ConfigSetting                  biomeVardaForest;
    private ConfigSetting                  biomeAcheron;
    private ConfigSetting                  globalSoundVolume;

    @Override
    public ArrayList<ConfigSetting> allSettings()
    {
        return this.allSettings;
    }

    @Override
    public void saveSettings()
    {
        this.configuration.save();
    }

    @SideOnly(Side.CLIENT)
    public static class ClientSettings implements IFlexibleConfiguration
    {
        public static final ClientSettings     instance          = new ClientSettings();
        private Configuration                  configuration;
        private final ArrayList<ConfigSetting> allSettings       = new ArrayList<ConfigSetting>();

        private final String                   CATEGORY_GRAPHICS = "graphics";

        private ConfigSettingGraphics          hiveTessellation;
        private ConfigSettingBoolean           bloodEffects;

        public void load(Configuration configuration)
        {
            this.configuration = configuration;
            this.hiveTessellation = new ConfigSettingGraphics(this, configuration.get(CATEGORY_GRAPHICS, "hive_tessellation", GraphicsSetting.ULTRA.ordinal(), "The visual complexity and detail of xenomorph hives."));
            this.bloodEffects = new ConfigSettingBoolean(this, configuration.get(CATEGORY_GRAPHICS, "blood_fx", true, "Turns blood particle effects on or off."));
        }

        public void saveSettings()
        {
            this.configuration.save();
        }

        public ConfigSettingGraphics hiveTessellation()
        {
            return this.hiveTessellation;
        }

        public ConfigSettingBoolean bloodFX()
        {
            return bloodEffects;
        }

        @Override
        public ArrayList<ConfigSetting> allSettings()
        {
            return this.allSettings;
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

            dimVarda = new ConfigSettingInteger(this, configuration.get(CATEGORY_DIM, "varda", 223, "Change the internal ID of this dimension. WARNING: Changing a dimension ID will prevent existing worlds from loading.")).setRequiresRestart();
            dimAcheron = new ConfigSettingInteger(this, configuration.get(CATEGORY_DIM, "acheron", 426, "Change the internal ID of this dimension. WARNING: Changing a dimension ID will prevent existing worlds from loading.")).setRequiresRestart();

            biomeVarda = new ConfigSettingInteger(this, configuration.get(CATEGORY_BIOMES, "varda_badlands", 223, "Change the internal ID of this biome. WARNING: Changing a biome ID will prevent existing worlds from loading.")).setRequiresRestart();
            biomeAcheron = new ConfigSettingInteger(this, configuration.get(CATEGORY_BIOMES, "acheron", 224, "Change the internal ID of this biome. WARNING: Changing a biome ID will prevent existing worlds from loading.")).setRequiresRestart();
            biomeVardaForest = new ConfigSettingInteger(this, configuration.get(CATEGORY_BIOMES, "varda_forest", 229, "Change the internal ID of this biome. WARNING: Changing a biome ID will prevent existing worlds from loading.")).setRequiresRestart();

            plasmaCannonExplosions = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "plasma_cannon_explosions", false, "If enabled, a plasma cannon's projectiles will explode upon impact."));
            explosionsEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "explosion_block_damage", true, "If disabled, all explosions triggered by this mod will be cancelled."));
            nukesEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "nukes", true, "If disabled, you will not be allowed to use any nuke-based functionality."));
            overworldSpawnsEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "overworld_spawning", true, "If disabled, no mobs from this mod will spawn in the overworld.")).setRequiresRestart();
            autoSpawnsEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "auto_spawning", true, "If disabled, no mobs from this mod will spawn.")).setRequiresRestart();
            evolvedXenomorphSpawns = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "mature_spawns", true, "If disabled, no mature alien states will spawn naturally.")).setRequiresRestart();
            updaterEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "updater", true, "Toggle the mod's update checking capabilities. Will not check for new updates if disabled.")).setRequiresRestart();
            debugToolsEnabled = new ConfigSettingBoolean(this, configuration.get(CATEGORY_OTHER, "debug_tools", false, "Toggle the built in debugging tools. Used primarily in development environments."));
            globalSoundVolume = new ConfigSettingInteger(this, configuration.get(CATEGORY_OTHER, "global_volume", 75, "Change the default volume of this mod's sounds. EXAMPLE: 100 = 100% Volume, 50 = 50% Volume, 150 = 150% Volume")).setRequiresRestart();
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
        return (boolean) overworldSpawnsEnabled.value();
    }

    public boolean areAutoSpawnsEnabled()
    {
        return (boolean) autoSpawnsEnabled.value();
    }

    public boolean arePlasmaCannonExplosionsEnabled()
    {
        return (boolean) plasmaCannonExplosions.value();
    }

    public boolean shouldEvolvedXenomorphsSpawn()
    {
        return (boolean) evolvedXenomorphSpawns.value();
    }

    public boolean areExplosionsEnabled()
    {
        return (boolean) this.explosionsEnabled.value();
    }

    public boolean isUpdaterEnabled()
    {
        return (boolean) this.updaterEnabled.value();
    }

    public boolean areDebugToolsEnabled()
    {
        return (boolean) this.debugToolsEnabled.value();
    }

    public boolean areNukesEnabled()
    {
        return (boolean) this.nukesEnabled.value();
    }

    public int dimensionIdVarda()
    {
        return (int) this.dimVarda.value();
    }

    public int dimensionIdAcheron()
    {
        return (int) this.dimAcheron.value();
    }

    public int biomeIdVardaBadlands()
    {
        return (int) this.biomeVarda.value();
    }

    public int biomeIdVardaForest()
    {
        return (int) this.biomeVardaForest.value();
    }

    public int biomeIdAcheron()
    {
        return (int) this.biomeAcheron.value();
    }

    public float globalSoundVolume()
    {
        return (float) ((int) this.globalSoundVolume.value()) / 100F;
    }
}
