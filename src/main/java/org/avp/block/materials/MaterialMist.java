package org.avp.block.materials;

import org.avp.api.material.IMaterialPhysics;
import org.avp.api.material.IMaterialRenderer;
import org.avp.client.render.materials.RenderMaterialMist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialTransparent;

public class MaterialMist extends MaterialTransparent implements IMaterialPhysics
{
    private static final IMaterialRenderer renderer = new RenderMaterialMist();

    public MaterialMist()
    {
        super(MapColor.lightBlueColor);
    }
    
    @Override
    public double getForceVelocity()
    {
        return 0D;
    }
    
    @Override
    public double getVelocity()
    {
        return 0D;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMaterialRenderer getMaterialRenderer()
    {
        return renderer;
    }
}
