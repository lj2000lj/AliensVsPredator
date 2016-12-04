package org.avp.block.skulls;

import org.avp.AliensVsPredator;
import org.avp.block.BlockSkull;
import org.avp.entities.mob.model.ModelYautja;

import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.client.render.Texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;

public class BlockSkullYautja extends BlockSkull
{
    @SideOnly(Side.CLIENT)
    private static class Resources
    {
        private static final TexturedModel<ModelYautja> model = AliensVsPredator.resources().models().YAUTJA_SKULL;
    }

    @Override
    public ModelRenderer[] getSkullModelRenderers()
    {
        ModelYautja m = Resources.model.getModel();
        return new ModelRenderer[] { m.head, m.head1, m.head2, m.head3, m.head4, m.head5, m.head6, m.head7, m.head8, m.head9, m.head10, m.head11, m.head12, m.head13, m.head14, m.head15, m.head16, m.head17, m.head18, m.head19, m.head20, m.head21, m.head22, m.head23, m.head25, m.head26, m.head27, m.head28, m.head29, m.head30, m.head55 };
    }

    @Override
    public void preRenderTransforms()
    {
        float scale = 1.9F;
        OpenGL.scale(scale, scale, scale);
        OpenGL.translate(0F, 0.25F, 0F);
    }

    @Override
    public Texture getSkullTexture()
    {
        return Resources.model.getTexture();
    }
}
