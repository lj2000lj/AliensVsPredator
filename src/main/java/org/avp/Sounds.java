package org.avp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.arisux.mdxlib.AMDXLib;
import com.arisux.mdxlib.lib.client.Sound;
import com.arisux.mdxlib.lib.game.IPostInitEvent;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class Sounds implements IPostInitEvent
{
    public static final Sounds instance                  = new Sounds();

    public static Sound        fxWristbracerAlarm        = new Sound("avp:weapon.blades.alarm");
    public static Sound        fxAlarm                   = new Sound("avp:alarm");
    public static Sound        fxAlarm0                  = new Sound("avp:alarm0");
    public static Sound        fxAlarm1                  = new Sound("avp:alarm1");
    public static Sound        fxAutomaticGun            = new Sound("avp:automatic-gun");
    public static Sound        fxBlastDoorOpen           = new Sound("avp:blast-door-open");
    public static Sound        fxLabDoorClose            = new Sound("avp:lab-door-close");
    public static Sound        fxLabDoorOpen             = new Sound("avp:lab-door-open");
    public static Sound        fxPistol                  = new Sound("avp:pistol");
    public static Sound        fxPistolHeavy             = new Sound("avp:pistol-heavy");
    public static Sound        fxPulserifle              = new Sound("avp:pulserifle");
    public static Sound        fxSmartGun                = new Sound("avp:smartgun");
    public static Sound        fxStepGlass               = new Sound("avp:step-glass");
    public static Sound        fxStepHive                = new Sound("avp:step-hive");
    public static Sound        fxTurret                  = new Sound("avp:turret");
    public static Sound        fxPowerBurnout            = new Sound("avp:burnout");
    public static Sound        fxPowerGenerator          = new Sound("avp:generator-humming");
    public static Sound        fxPowerLightFlicker       = new Sound("avp:light-flicker");
    public static Sound        fxPowerLightOn            = new Sound("avp:light-on");
    public static Sound        fxPowerPlasma             = new Sound("avp:plasma");
    public static Sound        fxPowerShortout           = new Sound("avp:shortout");

    public static Sound        SOUND_ALIEN_LIVING        = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.living");
    public static Sound        SOUND_ALIEN_HURT          = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.hurt");
    public static Sound        SOUND_ALIEN_DEATH         = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.death");
    public static Sound        SOUND_PRAETORIAN_HURT     = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.hurt");
    public static Sound        SOUND_PRAETORIAN_LIVING   = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.living");
    public static Sound        SOUND_PRAETORIAN_DEATH    = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.death");
    public static Sound        SOUND_QUEEN_HURT          = new Sound(AliensVsPredator.properties().DOMAIN + "queen.hurt");
    public static Sound        SOUND_QUEEN_LIVING        = new Sound(AliensVsPredator.properties().DOMAIN + "queen.living");
    public static Sound        SOUND_QUEEN_DEATH         = new Sound(AliensVsPredator.properties().DOMAIN + "queen.death");
    public static Sound        SOUND_SPITTER_HURT        = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.hurt");
    public static Sound        SOUND_SPITTER_LIVING      = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.living");
    public static Sound        SOUND_SPITTER_DEATH       = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.death");
    public static Sound        SOUND_WARRIOR_HURT        = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.hurt");
    public static Sound        SOUND_WARRIOR_LIVING      = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.living");
    public static Sound        SOUND_WARRIOR_DEATH       = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.death");
    public static Sound        SOUND_CRUSHER_HURT        = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.hurt");
    public static Sound        SOUND_CRUSHER_LIVING      = new Sound(AliensVsPredator.properties().DOMAIN + "praetorian.living");
    public static Sound        SOUND_CRUSHER_DEATH       = new Sound(AliensVsPredator.properties().DOMAIN + "xeno.death");
    public static Sound        SOUND_CHESTBURSTER_DEATH  = new Sound(AliensVsPredator.properties().DOMAIN + "chestburster.death");
    public static Sound        SOUND_CHESTBURSTER_HURT   = new Sound(AliensVsPredator.properties().DOMAIN + "chestburster.hurt");
    public static Sound        SOUND_CHESTBURSTER_ATTACK = new Sound(AliensVsPredator.properties().DOMAIN + "chestburster.attack");
    public static Sound        SOUND_CHESTBURSTER_BURST  = new Sound(AliensVsPredator.properties().DOMAIN + "chestburster.burst");
    public static Sound        SOUND_FACEHUGGER_DEATH    = new Sound(AliensVsPredator.properties().DOMAIN + "facehugger.death");
    public static Sound        SOUND_FACEHUGGER_HURT     = new Sound(AliensVsPredator.properties().DOMAIN + "facehugger.hurt");
    public static Sound        SOUND_FACEHUGGER_LIVING   = new Sound(AliensVsPredator.properties().DOMAIN + "facehugger.living");
    public static Sound        SOUND_MARINE_HURT         = new Sound(AliensVsPredator.properties().DOMAIN + "marine.hurt");
    public static Sound        SOUND_MARINE_DEATH        = new Sound(AliensVsPredator.properties().DOMAIN + "marine.death");
    public static Sound        SOUND_YAUTJA_LIVING       = new Sound(AliensVsPredator.properties().DOMAIN + "predator.living");
    public static Sound        SOUND_YAUTJA_HURT         = new Sound(AliensVsPredator.properties().DOMAIN + "predator.hurt");
    public static Sound        SOUND_YAUTJA_DEATH        = new Sound(AliensVsPredator.properties().DOMAIN + "predator.death");
    public static Sound        SOUND_WEAPON_FLAMETHROWER = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.flamethrower");
    public static Sound        SOUND_WEAPON_GUNSHOT      = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.gunshot");
    public static Sound        SOUND_WEAPON_PLASMACASTER = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.plasmacaster");
    public static Sound        SOUND_WEAPON_WRISTBLADES  = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.blades");
    public static Sound        SOUND_WEAPON_SNIPER       = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.sniper");
    public static Sound        SOUND_WEAPON_PULSERIFLE   = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.pulserifle");
    public static Sound        SOUND_WEAPON_M56SG        = new Sound(AliensVsPredator.properties().DOMAIN + "weapon.m56sg");
    public static Sound        SOUND_MOTIONTRACKER_PING  = new Sound(AliensVsPredator.properties().DOMAIN + "motiontracker.ping");
    public static Sound        SOUND_MOTIONTRACKER_PONG  = new Sound(AliensVsPredator.properties().DOMAIN + "motiontracker.pong");
    public static Sound        SOUND_BLASTDOOR_OPEN      = new Sound(AliensVsPredator.properties().DOMAIN + "blocks.blastdoor.open");

    @Override
    public void post(FMLPostInitializationEvent event)
    {
        float volume = AliensVsPredator.settings().globalSoundVolume();

        for (Field field : this.getClass().getDeclaredFields())
        {
            try
            {
                if (field.getType() == Sound.class)
                {
                    field.setAccessible(true);
                    Method method = Sound.class.getDeclaredMethod("setReflectedVolume", new Class[]{ Float.class });
                    method.setAccessible(true);
                    method.invoke(field.get(this), new Object[] { volume });
                    
                    Field f = Sound.class.getDeclaredField("volume");
                    f.setAccessible(true);
                    
                    AMDXLib.log().info(String.format("Adjusted the volume of %s to the globally configured volume setting of %s%%.", ((Sound) field.get(this)).getKey(), (f.getFloat(field.get(this)) * 100 / 1.0F)));
                }
            }
            catch (Exception e1)
            {
                try
                {
                    AMDXLib.log().info(String.format("Failed to adjust the volume of %s: %s", ((Sound) field.get(this)).getKey(), e1));
                }
                catch (Exception e2)
                {
                    e2.printStackTrace();
                }
            }
        }
    }
}
