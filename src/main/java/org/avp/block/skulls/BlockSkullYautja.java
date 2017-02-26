package org.avp.block.skulls;

import org.avp.AliensVsPredator;
import org.avp.block.BlockSkull;
import org.avp.client.model.entities.living.ModelYautja;

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
        return new ModelRenderer[] { m.face };
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
