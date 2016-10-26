package org.avp;

import org.avp.entities.mob.model.ModelAqua;
import org.avp.entities.mob.model.ModelChestburster;
import org.avp.entities.mob.model.ModelCrusher;
import org.avp.entities.mob.model.ModelDeaconShark;
import org.avp.entities.mob.model.ModelDrone;
import org.avp.entities.mob.model.ModelEngineer;
import org.avp.entities.mob.model.ModelFacehugger;
import org.avp.entities.mob.model.ModelGooMutant;
import org.avp.entities.mob.model.ModelHammerpede;
import org.avp.entities.mob.model.ModelMarine;
import org.avp.entities.mob.model.ModelOvamorph;
import org.avp.entities.mob.model.ModelPraetorian;
import org.avp.entities.mob.model.ModelPredalien;
import org.avp.entities.mob.model.ModelProtomorph;
import org.avp.entities.mob.model.ModelQueen;
import org.avp.entities.mob.model.ModelRoyalFacehugger;
import org.avp.entities.mob.model.ModelRunnerDrone;
import org.avp.entities.mob.model.ModelRunnerWarrior;
import org.avp.entities.mob.model.ModelSpitter;
import org.avp.entities.mob.model.ModelTrilobite;
import org.avp.entities.mob.model.ModelUltramorph;
import org.avp.entities.mob.model.ModelWarrior;
import org.avp.entities.mob.model.ModelYautja;
import org.avp.entities.mob.model.ModelYautjaBerserker;
import org.avp.entities.model.ModelBullet;
import org.avp.entities.model.ModelLaserMine;
import org.avp.entities.model.ModelSpear;
import org.avp.entities.model.ModelSupplyChute;
import org.avp.entities.tile.model.ModelAmpule;
import org.avp.entities.tile.model.ModelBlastdoor;
import org.avp.entities.tile.model.ModelCable;
import org.avp.entities.tile.model.ModelCryostasisTube;
import org.avp.entities.tile.model.ModelDNASynthesizer;
import org.avp.entities.tile.model.ModelHiveResin;
import org.avp.entities.tile.model.ModelLightPanel;
import org.avp.entities.tile.model.ModelLocker;
import org.avp.entities.tile.model.ModelMedpod;
import org.avp.entities.tile.model.ModelPowercell;
import org.avp.entities.tile.model.ModelRepulsionGenerator;
import org.avp.entities.tile.model.ModelSatelliteDish;
import org.avp.entities.tile.model.ModelSolarPanel;
import org.avp.entities.tile.model.ModelStasisMechanism;
import org.avp.entities.tile.model.ModelTransformer;
import org.avp.entities.tile.model.ModelTurret;
import org.avp.entities.tile.model.ModelWorkstation;
import org.avp.items.model.Model88MOD4;
import org.avp.items.model.ModelAK47;
import org.avp.items.model.ModelM240ICU;
import org.avp.items.model.ModelM4;
import org.avp.items.model.ModelM40;
import org.avp.items.model.ModelM41A;
import org.avp.items.model.ModelM56SG;
import org.avp.items.model.ModelMotionTracker;
import org.avp.items.model.ModelNostromoFlamethrower;
import org.avp.items.model.ModelSniper;
import org.avp.items.model.ModelWristBlade;

import com.arisux.amdxlib.AMDXLib;
import com.arisux.amdxlib.lib.client.SpecialModelBiped;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.IconSet;
import com.arisux.amdxlib.lib.client.render.Texture;
import com.arisux.amdxlib.lib.client.render.IconSet.BlockIconSet;
import com.arisux.amdxlib.lib.client.render.IconSet.LiquidIconSet;
import com.arisux.amdxlib.lib.client.render.wavefront.TriangulatedWavefrontModel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;

@SideOnly(Side.CLIENT)
public class Resources
{
    public static final Resources instance = new Resources();
    private static final Models   models   = new Models();

    public Models models()
    {
        return models;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("all")
    public static class Models
    {
        public final TexturedModel<ModelRunnerDrone>          RUNNER_DRONE                   = new TexturedModel(new ModelRunnerDrone(), new Texture(AliensVsPredator.ID, "textures/mob/runner_drone.png"));
        public final TexturedModel<ModelRunnerWarrior>        RUNNER_WARRIOR                 = new TexturedModel(new ModelRunnerWarrior(), new Texture(AliensVsPredator.ID, "textures/mob/runner_warrior.png"));
        public final TexturedModel<Model88MOD4>               _88MOD4                        = new TexturedModel(new Model88MOD4(), new Texture(AliensVsPredator.ID, "textures/items/models/88mod4.png"));
        public final TexturedModel<ModelNostromoFlamethrower> FLAMETHROWER_NOSTROMO          = new TexturedModel(new ModelNostromoFlamethrower(), new Texture(AliensVsPredator.ID, "textures/items/models/flamethrower.nostromo.png"));
        public final TexturedModel<ModelEngineer>             ENGINEER                       = new TexturedModel(new ModelEngineer(), new Texture(AliensVsPredator.ID, "textures/mob/engineer_bio.png"));
        public final TexturedModel<ModelYautjaBerserker>      YAUTJA_BERSERKER               = new TexturedModel(new ModelYautjaBerserker(), new Texture(AliensVsPredator.ID, "textures/mob/yautja_berserker.png"));
        public final TexturedModel<ModelLocker>               LOCKER                         = new TexturedModel(new ModelLocker(), new Texture(AliensVsPredator.ID, "textures/tile/locker.png"));
        public final TexturedModel<ModelLocker>               GUN_LOCKER                     = new TexturedModel(new ModelLocker(), new Texture(AliensVsPredator.ID, "textures/tile/gunlocker.png"));
        public final TexturedModel<ModelEngineer>             SPACE_JOCKEY                   = new TexturedModel(new ModelEngineer(), new Texture(AliensVsPredator.ID, "textures/mob/engineer.png"));
        public final TexturedModel<ModelAmpule>               AMPULE                         = new TexturedModel(new ModelAmpule(), new Texture(AliensVsPredator.ID, "textures/tile/ampule.png"));
        public final TexturedModel<ModelTransformer>          TRANSFORMER                    = new TexturedModel(new ModelTransformer(), new Texture(AliensVsPredator.ID, "textures/tile/transformer.png"));
        public final TexturedModel<ModelSolarPanel>           SOLAR_PANEL                    = new TexturedModel(new ModelSolarPanel(), new Texture(AliensVsPredator.ID, "textures/tile/solarpanel.png"));
        public final TexturedModel<ModelSatelliteDish>        SATELLITE_DISH                 = new TexturedModel(new ModelSatelliteDish(), new Texture(AliensVsPredator.ID, "textures/tile/satellite-dish.png"));
        public final TexturedModel<ModelPowercell>            POWERCELL                      = new TexturedModel(new ModelPowercell(), new Texture(AliensVsPredator.ID, "textures/tile/powercell.png"));
        public final TexturedModel<ModelPowercell>            POWERCELL_LIQUID               = new TexturedModel(new ModelPowercell(), new Texture(AliensVsPredator.ID, "textures/tile/powercellliquid.png"));
        public final TexturedModel<ModelDrone>                DRONE_BASIC                    = new TexturedModel(new ModelDrone(), new Texture(AliensVsPredator.ID, "textures/mob/drone_basic.png"));
        public final TexturedModel<ModelDrone>                DRONE_ADVANCED                 = new TexturedModel(new ModelDrone(), new Texture(AliensVsPredator.ID, "textures/mob/drone_advanced.png"));
        public final TexturedModel<ModelBullet>               BULLET                         = new TexturedModel(new ModelBullet(), new Texture(AliensVsPredator.ID, "textures/misc/renderbullet.png"));
        public final TexturedModel<ModelSpear>                SPEAR                          = new TexturedModel(new ModelSpear(), new Texture(AliensVsPredator.ID, "textures/misc/renderspear.png"));
        public final TexturedModel<ModelWristBlade>           WRISTBLADES                    = new TexturedModel(new ModelWristBlade(), new Texture(AliensVsPredator.ID, "textures/items/models/wristblade.png"));
        public final TexturedModel<ModelM240ICU>              M240ICU                        = new TexturedModel(new ModelM240ICU(), new Texture(AliensVsPredator.ID, "textures/items/models/m240icu.png"));
        public final TexturedModel<ModelM41A>                 M41A                           = new TexturedModel(new ModelM41A(), new Texture(AliensVsPredator.ID, "textures/items/models/pulserifle.png"));
        public final TexturedModel<ModelM56SG>                M56SG                          = new TexturedModel(new ModelM56SG(), new Texture(AliensVsPredator.ID, "textures/items/models/m56sg.png"));
        public final TexturedModel<ModelAK47>                 AK47                           = new TexturedModel(new ModelAK47(), new Texture(AliensVsPredator.ID, "textures/items/models/ak-47.png"));
        public final TexturedModel<ModelM4>                   M4                             = new TexturedModel(new ModelM4(), new Texture(AliensVsPredator.ID, "textures/items/models/m4.png"));
        public final TexturedModel<ModelSniper>               SNIPER                         = new TexturedModel(new ModelSniper(), new Texture(AliensVsPredator.ID, "textures/items/models/sniper.png"));
        public final TexturedModel<ModelM40>                  M40GRENADE                     = new TexturedModel(new ModelM40(), new Texture(AliensVsPredator.ID, "textures/items/models/m40.png"));
        public final TexturedModel<ModelM40>                  M40GRENADE_INCENDIARY          = new TexturedModel(new ModelM40(), new Texture(AliensVsPredator.ID, "textures/items/models/m40incendiary.png"));
        public final TexturedModel<ModelMotionTracker>        MOTIONTRACKER                  = new TexturedModel(new ModelMotionTracker(), new Texture(AliensVsPredator.ID, "textures/items/models/motiontracker.png"));
        public final TexturedModel<ModelTurret>               TURRET                         = new TexturedModel(new ModelTurret(), new Texture(AliensVsPredator.ID, "textures/tile/turret.png"));
        public final TexturedModel<ModelWorkstation>          WORKSTATION                    = new TexturedModel(new ModelWorkstation(), new Texture(AliensVsPredator.ID, "textures/tile/workstation.png"));
        public final TexturedModel<ModelWorkstation>          WORKSTATION_MASK               = new TexturedModel(new ModelWorkstation(), new Texture(AliensVsPredator.ID, "textures/tile/workstation-on.png"));
        public final TexturedModel<ModelLightPanel>           LIGHT_PANEL                    = new TexturedModel(new ModelLightPanel(), new Texture(AliensVsPredator.ID, "textures/tile/lightpanel.png"));
        public final TexturedModel<ModelCryostasisTube>       CRYOSTASIS_TUBE                = new TexturedModel(new ModelCryostasisTube(), new Texture(AliensVsPredator.ID, "textures/tile/cryostasistube.png"));
        public final TexturedModel<ModelCryostasisTube>       CRYOSTASIS_TUBE_MASK           = new TexturedModel(new ModelCryostasisTube(), new Texture(AliensVsPredator.ID, "textures/tile/cryostasistube-mask.png"));
        public final TexturedModel<ModelCryostasisTube>       CRYOSTASIS_TUBE_MASK_CRACKED   = new TexturedModel(new ModelCryostasisTube(), new Texture(AliensVsPredator.ID, "textures/tile/cryostasistube-cracked-mask.png"));
        public final TexturedModel<ModelCryostasisTube>       CRYOSTASIS_TUBE_MASK_SHATTERED = new TexturedModel(new ModelCryostasisTube(), new Texture(AliensVsPredator.ID, "textures/tile/cryostasistube-shattered-mask.png"));
        public final TexturedModel<ModelStasisMechanism>      STASIS_MECHANISM               = new TexturedModel(new ModelStasisMechanism(), new Texture(AliensVsPredator.ID, "textures/tile/stasis-mechanism.png"));
        public final TexturedModel<ModelStasisMechanism>      STASIS_MECHANISM_MASK          = new TexturedModel(new ModelStasisMechanism(), new Texture(AliensVsPredator.ID, "textures/tile/stasis-mechanism-mask.png"));
        public final TexturedModel<ModelMedpod>               MEDPOD                         = new TexturedModel(new ModelMedpod(), new Texture(AliensVsPredator.ID, "textures/tile/medpod.png"));
        public final TexturedModel<ModelMedpod>               MEDPOD_MASK                    = new TexturedModel(new ModelMedpod(), new Texture(AliensVsPredator.ID, "textures/tile/medpod-on.png"));
        public final TexturedModel<ModelRepulsionGenerator>   REPULSION_GENERATOR            = new TexturedModel(new ModelRepulsionGenerator(), new Texture(AliensVsPredator.ID, "textures/tile/generator.png"));
        public final TexturedModel<ModelCable>                CABLE                          = new TexturedModel(new ModelCable(), new Texture(AliensVsPredator.ID, "textures/tile/cable.png"));
        public final TexturedModel<ModelBlastdoor>            BLASTDOOR                      = new TexturedModel(new ModelBlastdoor(), new Texture(AliensVsPredator.ID, "textures/tile/blastdoor.png"));
        public final TexturedModel<ModelChestburster>         CHESTBUSTER                    = new TexturedModel(new ModelChestburster(), new Texture(AliensVsPredator.ID, "textures/mob/chestbuster.png"));
        public final TexturedModel<ModelFacehugger>           FACEHUGGER                     = new TexturedModel(new ModelFacehugger(), new Texture(AliensVsPredator.ID, "textures/mob/facehugger.png"));
        public final TexturedModel<ModelRoyalFacehugger>      ROYALFACEHUGGER                = new TexturedModel(new ModelRoyalFacehugger(), new Texture(AliensVsPredator.ID, "textures/mob/royalfacehugger.png"));
        public final TexturedModel<ModelMarine>               MARINE                         = new TexturedModel(new SpecialModelBiped(), new Texture(AliensVsPredator.ID, "textures/mob/marine.png"));
        public final TexturedModel<ModelOvamorph>             OVAMORPH                       = new TexturedModel(new ModelOvamorph(), new Texture(AliensVsPredator.ID, "textures/mob/alienegg.png"));
        public final TexturedModel<ModelPredalien>            PREDALIEN                      = new TexturedModel(new ModelPredalien(), new Texture(AliensVsPredator.ID, "textures/mob/predalien.png"));
        public final TexturedModel<ModelPraetorian>           PRAETORIAN                     = new TexturedModel(new ModelPraetorian(), new Texture(AliensVsPredator.ID, "textures/mob/praetorian.png"));
        public final TexturedModel<ModelWarrior>              WARRIOR                        = new TexturedModel(new ModelWarrior(), new Texture(AliensVsPredator.ID, "textures/mob/warrior.png"));
        public final TexturedModel<ModelWarrior>              WARRIOR_BLOOD                  = new TexturedModel(new ModelWarrior(), new Texture(AliensVsPredator.ID, "textures/mob/warrior_blood.png"));
        public final TexturedModel<ModelDrone>                DRONE_BASIC_BLOOD              = new TexturedModel(new ModelDrone(), new Texture(AliensVsPredator.ID, "textures/mob/drone_basic_blood.png"));
        public final TexturedModel<ModelDrone>                DRONE_ADVANCED_BLOOD           = new TexturedModel(new ModelDrone(), new Texture(AliensVsPredator.ID, "textures/mob/drone_advanced_blood.png"));
        public final TexturedModel<ModelHammerpede>           HAMMERPEDE                     = new TexturedModel(new ModelHammerpede(), new Texture(AliensVsPredator.ID, "textures/mob/hammerpede.png"));
        public final TexturedModel<ModelTrilobite>            TRILOBITE                      = new TexturedModel(new ModelTrilobite(), new Texture(AliensVsPredator.ID, "textures/mob/trilobite.png"));
        public final TexturedModel<ModelDeaconShark>          DEACON_SHARK                   = new TexturedModel(new ModelDeaconShark(), new Texture(AliensVsPredator.ID, "textures/mob/deacon_shark.png"));
        public final TexturedModel<ModelProtomorph>           PROTOMORPH                     = new TexturedModel(new ModelProtomorph(), new Texture(AliensVsPredator.ID, "textures/mob/deacon.png"));
        public final TexturedModel<ModelAqua>                 AQUA_XENOMORPH                 = new TexturedModel(new ModelAqua(), new Texture(AliensVsPredator.ID, "textures/mob/aqua.png"));
        public final TexturedModel<ModelAqua>                 AQUA_XENOMORPH_MASK            = new TexturedModel(new ModelAqua(), new Texture(AliensVsPredator.ID, "textures/mob/aqua_glow.png"));
        public final TexturedModel<ModelQueen>                XENOQUEEN                      = new TexturedModel(new ModelQueen(), new Texture(AliensVsPredator.ID, "textures/mob/queen.png"));
        public final TexturedModel<ModelQueen>                XENOQUEEN_MASK                 = new TexturedModel(new ModelQueen(), new Texture(AliensVsPredator.ID, "textures/mob/queen_mask.png"));
        public final TexturedModel<ModelYautja>               YAUTJA                         = new TexturedModel(new ModelYautja(), new Texture(AliensVsPredator.ID, "textures/mob/yautja.png"));
        public final TexturedModel<ModelSpitter>              SPITTER                        = new TexturedModel(new ModelSpitter(), new Texture(AliensVsPredator.ID, "textures/mob/spitter.png"));
        public final TexturedModel<ModelSpitter>              SPITTER_MASK                   = new TexturedModel(new ModelSpitter(), new Texture(AliensVsPredator.ID, "textures/mob/spitter_glow.png"));
        public final TexturedModel<ModelCrusher>              CRUSHER                        = new TexturedModel(new ModelCrusher(), new Texture(AliensVsPredator.ID, "textures/mob/crusher.png"));
        public final TexturedModel<SpecialModelBiped>         COMBAT_SYNTHETIC               = new TexturedModel(new SpecialModelBiped(), new Texture(AliensVsPredator.ID, "textures/mob/combat_synthetic.png"));
        public final TexturedModel<ModelSupplyChute>          SUPPLY_CHUTE                   = new TexturedModel(new ModelSupplyChute(), new Texture(AliensVsPredator.ID, "textures/misc/supplychute.png"));
        public final TexturedModel<SpecialModelBiped>         BIPED                          = new TexturedModel(new SpecialModelBiped(), new Texture(AbstractClientPlayer.locationStevePng));
        public final TexturedModel<ModelLaserMine>            LASER_MINE                     = new TexturedModel(new ModelLaserMine(), new Texture(AliensVsPredator.ID, "textures/misc/proximity-mine.png"));
        public final TexturedModel<ModelDNASynthesizer>       DNA_SYNTHESIZER                = new TexturedModel(new ModelDNASynthesizer(), new Texture(AliensVsPredator.ID, "textures/tile/dna-synthesizer.png"));
        public final TexturedModel<ModelHiveResin>            HIVE_RESIN                     = new TexturedModel(new ModelHiveResin(), new Texture(AliensVsPredator.ID, "textures/tile/hive-resin.png"));
        public final TexturedModel<ModelGooMutant>            GOO_MUTANT                     = new TexturedModel(new ModelGooMutant(), new Texture(AliensVsPredator.ID, "textures/mob/goomutant.png"));
        public final TriangulatedWavefrontModel               M577_APC                       = AMDXLib.loadWavefrontModel(AliensVsPredator.class, AliensVsPredator.ID, "m577apc", "/assets/avp/models/m577apc");

        // Xenomorph models with the new universal format
        public final TexturedModel<ModelUltramorph>           ULTRAMORPH                     = new TexturedModel(new ModelUltramorph(), new Texture(AliensVsPredator.ID, "textures/mob/ultramorph.png"));
    }

    public final Texture SKY_VARDA_CLOUDS         = new Texture(AliensVsPredator.ID, "textures/misc/varda-clouds.png");
    public final Texture SKY_SILICA               = new Texture(AliensVsPredator.ID, "textures/misc/silica.png");
    public final Texture SKY_CALPAMOS             = new Texture(AliensVsPredator.ID, "textures/misc/calpamos.png");
    public final Texture SKY_VARDA                = new Texture(AliensVsPredator.ID, "textures/misc/varda.png");
    public final Texture SKY_ACHERON              = new Texture(AliensVsPredator.ID, "textures/misc/acheron.png");
    public final Texture TITANIUM1                = new Texture(AliensVsPredator.ID, "textures/armor/titanium_1.png");
    public final Texture TITANIUM2                = new Texture(AliensVsPredator.ID, "textures/armor/titanium_2.png");
    public final Texture PRESSURESUIT1            = new Texture(AliensVsPredator.ID, "textures/armor/suit_1.png");
    public final Texture PRESSURESUIT2            = new Texture(AliensVsPredator.ID, "textures/armor/suit_2.png");
    public final Texture XENO1                    = new Texture(AliensVsPredator.ID, "textures/armor/xeno_1.png");
    public final Texture XENO2                    = new Texture(AliensVsPredator.ID, "textures/armor/xeno_2.png");
    public final Texture MARINE1                  = new Texture(AliensVsPredator.ID, "textures/armor/marine_1.png");
    public final Texture MARINE2                  = new Texture(AliensVsPredator.ID, "textures/armor/marine_2.png");
    public final Texture ACID_POOL                = new Texture(AliensVsPredator.ID, "textures/misc/acidpool.png");
    public final Texture LIQUID_POOL              = new Texture(AliensVsPredator.ID, "textures/misc/liquidpool.png");
    public final Texture DISC                     = new Texture(AliensVsPredator.ID, "textures/misc/disc.png");
    public final Texture SHURIKEN                 = new Texture(AliensVsPredator.ID, "textures/misc/shuriken.png");
    public final Texture BLUR_CELTIC_HUD          = new Texture(AliensVsPredator.ID, "textures/misc/celtic-helm-overlay.png");
    public final Texture BLUR_TACTICAL_HUD        = new Texture(AliensVsPredator.ID, "textures/misc/marine-helm-overlay.png");
    public final Texture BLUR_FACEHUGGER          = new Texture(AliensVsPredator.ID, "textures/misc/facehugger.png");
    public final Texture BLUR_CHESTBURSTER_EMERGE = new Texture(AliensVsPredator.ID, "textures/misc/chestburster-emerge-overlay.png");
    public final Texture BATTERY_INDICATOR        = new Texture(AliensVsPredator.ID, "textures/misc/battery-indicator.png");
    public final Texture INFECTION_INDICATOR      = new Texture(AliensVsPredator.ID, "textures/misc/infection-indicator.png");
    public final Texture BLUR_GUNSCOPE            = new Texture(AliensVsPredator.ID, "textures/misc/scope.png");
    public final Texture GUI_BASIC                = new Texture(AliensVsPredator.ID, "textures/gui/background.png");
    public final Texture GUI_TURRET               = new Texture(AliensVsPredator.ID, "textures/gui/turret.png");
    public final Texture GUI_WRISTBRACER          = new Texture(AliensVsPredator.ID, "textures/gui/wristbracer.png");
    public final Texture GUI_LOCKER               = new Texture(AliensVsPredator.ID, "textures/gui/locker.png");
    public final Texture GUI_ASSEMBLER            = new Texture(AliensVsPredator.ID, "textures/gui/assembler.png");
    public final Texture GUI_SUPPLYCRATE          = new Texture(AliensVsPredator.ID, "textures/gui/supplycrate.png");
    public final Texture ICON_AMMO                = new Texture(AliensVsPredator.ID, "textures/misc/icon-ammo.png");
    public final Texture MOTIONTRACKER_BG         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/background.png");
    public final Texture MOTIONTRACKER_FG         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/foreground.png");
    public final Texture MOTIONTRACKER_PING       = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/ping.png");
    public final Texture MOTIONTRACKER_S1         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep1.png");
    public final Texture MOTIONTRACKER_S2         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep2.png");
    public final Texture MOTIONTRACKER_S3         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep3.png");
    public final Texture MOTIONTRACKER_S4         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep4.png");
    public final Texture MOTIONTRACKER_S5         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep5.png");
    public final Texture MOTIONTRACKER_S6         = new Texture(AliensVsPredator.ID, "textures/misc/motiontracker/sweep6.png");
    public final Texture QUEEN_BOSS_BAR           = new Texture(AliensVsPredator.ID, "textures/misc/queenbossbar.png");

    public final IconSet ICONSET_WALLW            = new BlockIconSet("avp:wall_top", "avp:wall_top", "avp:wall_top", "avp:wall_side", "avp:wall_side", "avp:wall_side", "avp:wall_side");
    public final IconSet ICONSET_SPAWNER          = new BlockIconSet("avp:spawner_side", "avp:spawner_top", "avp:spawner_bottom", "avp:spawner_side", "avp:spawner_side", "avp:spawner_side", "avp:spawner_side");
    public final IconSet ICONSET_ASSEMBLER        = new BlockIconSet("avp:assembler.top", "avp:assembler.top", "avp:assembler.top", "avp:assembler.side", "avp:assembler.side", "avp:assembler.side", "avp:assembler.side");
    public final IconSet ICONSET_BLACK_GOO        = new LiquidIconSet("avp:blackgoo.still", "avp:blackgoo.flowing", "avp:blackgoo.still");
    public final IconSet ICONSET_MIST             = new LiquidIconSet("avp:mist.still", "avp:mist.flowing", "avp:mist.still");
}
