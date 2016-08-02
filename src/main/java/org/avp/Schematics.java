package org.avp;

import java.io.File;

import com.arisux.amdxlib.AMDXLib;
import com.arisux.amdxlib.lib.game.IInitEvent;
import com.arisux.amdxlib.lib.world.storage.Schematic;

import cpw.mods.fml.common.event.FMLInitializationEvent;

public class Schematics implements IInitEvent
{
    public static final Schematics instance = new Schematics();
    public static final File baseSchematicDir = new File("schematics/avp/");
    public Schematic schematicTest;
    public Schematic derelict;
    public Schematic derelictOld;

    @Override
    public void init(FMLInitializationEvent event)
    {
        if (!baseSchematicDir.exists())
        {
            baseSchematicDir.mkdirs();
        }

        this.schematicTest = AMDXLib.loadSchematic(new File(baseSchematicDir, "test.schematic"), AliensVsPredator.class.getResource("/assets/avp/schematics/test.schematic"));
        this.derelict = AMDXLib.loadSchematic(new File(baseSchematicDir, "derelict.schematic"), AliensVsPredator.class.getResource("/assets/avp/schematics/derelict.schematic"));
        this.derelictOld = AMDXLib.loadSchematic(new File(baseSchematicDir, "derelictold.schematic"), AliensVsPredator.class.getResource("/assets/avp/schematics/derelictold.schematic"));
    }
}
