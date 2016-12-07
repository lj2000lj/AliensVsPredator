package org.avp;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;

public class Properties
{
    public static final Properties instance                  = new Properties();

    public final ModContainer      CONTAINER                 = AliensVsPredator.instance().container();
    public final ModMetadata       METADATA                  = CONTAINER.getMetadata();
    public final String            DOMAIN                    = METADATA.modId + ":";

    public final int               GUI_ASSEMBLER             = 0;
    public final int               GUI_TURRET                = 1;
    public final int               GUI_WRISTBRACER           = 2;
    public final int               GUI_LOCKER                = 3;
    public final int               GUI_SUPPLYCRATE           = 4;
    public final int               GUI_GRAPHICSSETTINGS      = 5;

    public final String            DIMENSION_NAME_ACHERON    = "LV-426 (Acheron)";
    public final String            DIMENSION_ID_ACHERON      = "DIM_LV426";

    public final String            DIMENSION_NAME_VARDA      = "LV-223 (Varda)";
    public final String            DIMENSION_ID_VARDA        = "DIM_LV223";
    public final String            BIOME_NAME_VARDA_BADLANDS = "LV-223.B.1 (Varda Badlands)";
    public final String            BIOME_NAME_VARDA_FOREST   = "LV-223.B.2 (Varda Laurel Forest)";
}
