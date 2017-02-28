package org.avp;

import net.minecraftforge.fml.common.ModMetadata;

public class Properties
{
    public static final Properties instance                  = new Properties();

    public final ModMetadata       METADATA                  = AliensVsPredator.instance().container().getMetadata();
    public final String            DOMAIN                    = METADATA.modId + ":";

    public final String            DIMENSION_NAME_ACHERON    = "LV-426 (Acheron)";
    public final String            DIMENSION_ID_ACHERON      = "DIM_LV426";

    public final String            DIMENSION_NAME_VARDA      = "LV-223 (Varda)";
    public final String            DIMENSION_ID_VARDA        = "DIM_LV223";
    
    public final String            BIOME_NAME_VARDA_BADLANDS = "LV-223.B.1 (Varda Badlands)";
    public final String            BIOME_NAME_VARDA_FOREST   = "LV-223.B.2 (Anomalistic Forest)";
}
