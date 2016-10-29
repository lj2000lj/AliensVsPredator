package org.avp.block.skulls;

import org.avp.AliensVsPredator;
import org.avp.block.BlockSkull;
import org.avp.entities.mob.model.ModelEngineer;

import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.render.Texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;

public class BlockSkullSpaceJockey extends BlockSkull
{
    @SideOnly(Side.CLIENT)
    private static final TexturedModel<ModelEngineer> model = AliensVsPredator.resources().models().SPACE_JOCKEY;

    @Override
    public ModelRenderer[] getSkullModelRenderers()
    {
        ModelEngineer m = model.getModel();
        return new ModelRenderer[] { m.head1, m.head2, m.hose, m.rJaw, m.lJaw, m.nozzle1, m.nozzle2, m.nozzle3a, m.nozzle3b, m.nozzle3c, m.nozzle3d };
    }

    @Override
    public Texture getSkullTexture()
    {
        return model.getTexture();
    }
}
