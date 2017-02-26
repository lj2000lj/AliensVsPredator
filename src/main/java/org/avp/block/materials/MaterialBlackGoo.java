package org.avp.block.materials;

import org.avp.api.material.IMaterialPhysics;
import org.avp.api.material.IMaterialRenderer;
import org.avp.client.render.materials.RenderMaterialBlackGoo;

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
    public double getForceVelocity()
    {
        return 0.01D;
    }
    
    @Override
    public double getVelocity()
    {
        return 0.275D;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMaterialRenderer getMaterialRenderer()
    {
        return renderer;
    }
}