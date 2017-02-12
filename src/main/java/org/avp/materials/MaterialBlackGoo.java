package org.avp.materials;

import org.avp.api.material.IMaterialPhysics;
import org.avp.api.material.IMaterialRenderer;
import org.avp.materials.render.RenderMaterialBlackGoo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;

public class MaterialBlackGoo extends MaterialLiquid implements IMaterialPhysics
{
    private static final IMaterialRenderer renderer = new RenderMaterialBlackGoo();
    
    public MaterialBlackGoo()
    {
        super(MapColor.blackColor);
        this.setNoPushMobility();
    }
    
    public boolean blocksMovement()
    {
        return true;
    }
    
    @Override
    public double getEntityPushStrength()
    {
        return 0.01D;
    }
    
    @Override
    public double getEntitySpeedMultiplier()
    {
        return 0.25D;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMaterialRenderer getMaterialRenderer()
    {
        return renderer;
    }
}