package org.avp.entities.mob.render;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntityDrone;
import org.avp.entities.mob.EntityPraetorian;
import org.avp.entities.mob.EntityQueen;
import org.avp.entities.tile.TileEntityCryostasisTube;
import org.avp.entities.tile.render.RenderCryostasisTube;
import org.avp.entities.tile.render.RenderCryostasisTube.CryostasisTubeRenderer;
import org.avp.entities.tile.render.RenderCryostasisTube.ICustomCryostasisRenderer;
import org.lwjgl.opengl.GL12;

import com.arisux.amdxlib.lib.client.Model;
import com.arisux.amdxlib.lib.client.TexturedModel;
import com.arisux.amdxlib.lib.client.RenderLivingWrapper;
import com.arisux.amdxlib.lib.client.render.Draw;
import com.arisux.amdxlib.lib.client.render.OpenGL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderXenomorph extends RenderLivingWrapper implements ICustomCryostasisRenderer
{
    private float renderScale;

    public RenderXenomorph(TexturedModel<? extends Model> modelTexMap)
    {
        this(modelTexMap, 1F);
    }

    public RenderXenomorph(TexturedModel<? extends Model> modelTexMap, float renderScale)
    {
        super(modelTexMap);
        this.renderScale = renderScale;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float renderPartialTicks)
    {
        this.renderScale = 1F;
        OpenGL.scale(this.renderScale, this.renderScale, this.renderScale);
        super.preRenderCallback(entity, renderPartialTicks);
    }
    
    @Override
    public ResourceLocation getEntityTexture(Entity entity)
    {
        return model.getTexture();
    }

    public RenderXenomorph setScale(float renderScale)
    {
        this.renderScale = renderScale;
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public CryostasisTubeRenderer getCustomCryostasisRenderer()
    {
        return new CryostasisTubeRenderer()
        {
            @Override
            public void renderChassis(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                OpenGL.disable(GL_CULL_FACE);
                OpenGL.enable(GL_BLEND);
                OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                OpenGL.translate(posX + 0.5F, posY + 1.7F, posZ + 0.5F);
                OpenGL.rotate(tile);
                OpenGL.enable(GL12.GL_RESCALE_NORMAL);
                OpenGL.scale(0.75F, -0.75F, 0.75F);
                OpenGL.enable(GL_ALPHA_TEST);
                OpenGL.pushMatrix();
                {
                    OpenGL.scale(4, 3, 4);
                    OpenGL.translate(0F, -0.75F, 0F);
                    AliensVsPredator.resources().models().CRYOSTASIS_TUBE.draw();
                }
                OpenGL.popMatrix();
            }

            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null && !(tile.stasisEntity instanceof EntityQueen))
                {
                    OpenGL.pushMatrix();
                    {
                        if (tile.getVoltage() > 0)
                        {
                            OpenGL.disableLight();
                        }

                        double depth = tile.stasisEntity instanceof EntityPraetorian ? -1.95 : tile.stasisEntity instanceof EntityDrone ? -1.0 : -1.5F;

                        OpenGL.translate(0F, -2.75F, depth);
                        OpenGL.rotate(90F, 1F, 0F, 0F);
                        RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    }
                    OpenGL.popMatrix();
                }
                else if (tile.stasisEntity instanceof EntityQueen)
                {
                    OpenGL.pushMatrix();
                    {
                        OpenGL.disableLight();
                        OpenGL.scale(0.25, 0.25, 0.25);
                        OpenGL.translate(-3.25, -16, 0);
                        Draw.drawString("\u26A0", 0, 0, 0xFFFF0000, false);
                        OpenGL.enableLight();
                    }
                    OpenGL.popMatrix();
                }
            }

            @Override
            public void renderTube(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.getVoltage() > 0)
                {
                    OpenGL.disableLightMapping();
                    OpenGL.disableLight();
                }

                OpenGL.disableCullFace();
                OpenGL.scale(4, 3, 4);
                OpenGL.translate(0F, -0.75F, 0F);
                
                if (tile.isShattered())
                {
                    AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_SHATTERED.draw();
                }
                else if (tile.isCracked())
                {
                    AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_CRACKED.draw();
                }
                else
                {
                    AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK.draw();
                }
                
                OpenGL.scale(0.5, 0.5, 0.5);
                OpenGL.enableLightMapping();
                OpenGL.enableLight();
            }
        };
    }
}
