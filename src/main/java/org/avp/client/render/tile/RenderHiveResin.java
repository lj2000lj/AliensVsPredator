package org.avp.client.render.tile;

import org.avp.AliensVsPredator;
import org.avp.Settings.ClientSettings;
import org.avp.client.model.tile.ModelHiveResin;
import org.avp.tile.TileEntityHiveResin;

import com.arisux.mdxlib.config.GraphicsSetting;
import com.arisux.mdxlib.lib.client.Model;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class RenderHiveResin extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity tile, double posX, double posY, double posZ, float partialTicks)
    {
        GraphicsSetting hiveTessellation = ClientSettings.instance.hiveTessellation().value();

        if (hiveTessellation == GraphicsSetting.MEDIUM || hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
        {
            TileEntityHiveResin resin = (TileEntityHiveResin) tile;

            OpenGL.pushMatrix();
            {
                OpenGL.translate(posX + 0.5, posY - 0.5, posZ + 0.5);

                AliensVsPredator.resources().models().HIVE_RESIN.bindTexture();
                ModelHiveResin model = AliensVsPredator.resources().models().HIVE_RESIN.getModel();
                OpenGL.enableCullFace();

                if (resin.topBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.MEDIUM ||hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.blockFaceTop.offsetY = 2.002F;
                        model.blockFaceTop.rotateAngleX = (float) Math.toRadians(180);
                        model.blockFaceTop.render(Model.DEFAULT_SCALE);
                    }
                }

                if (resin.bottomBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.bResinRibCenter.offsetY = -0.9F;
                        model.bResinRibCenter.rotateAngleX = (float) Math.toRadians(180);
                        model.bResinRibCenter.render(Model.DEFAULT_SCALE);
                    }
                }

                OpenGL.rotate(resin.getVariant() == null ? 0 : (resin.getVariant().id - 1) * 90F, 0F, 1F, 0F);

                // if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                {
                    model.nBottomRoot01.isHidden = resin.northTopBlock == Blocks.air;
                    model.nTopRoot01.isHidden = resin.northBottomBlock == Blocks.air;
                }

                if (resin.northBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.nResinRipCenter.render(Model.DEFAULT_SCALE);
                    }

                    if (hiveTessellation == GraphicsSetting.MEDIUM || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.webbing01.render(Model.DEFAULT_SCALE);
                        model.webbing15.render(Model.DEFAULT_SCALE);
                        model.webbing07.render(Model.DEFAULT_SCALE);
                        model.webbing13.render(Model.DEFAULT_SCALE);
                        model.webbing09.render(Model.DEFAULT_SCALE);
                    }
                }

                // if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                {
                    model.wBottomRoot01.isHidden = resin.westTopBlock == Blocks.air;
                    model.wTopRoot01.isHidden = resin.westBottomBlock == Blocks.air;
                }

                if (resin.westBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.wResinRipCenter.render(Model.DEFAULT_SCALE);
                    }

                    if (hiveTessellation == GraphicsSetting.MEDIUM || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.webbing11.render(Model.DEFAULT_SCALE);
                        model.webbing04.render(Model.DEFAULT_SCALE);
                        model.webbing05.render(Model.DEFAULT_SCALE);
                    }
                }

                // if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                {
                    model.sBottomRoot01.isHidden = resin.southTopBlock == Blocks.air;
                    model.sTopRoot01.isHidden = resin.southBottomBlock == Blocks.air;
                }

                if (resin.southBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.sResinRipCenter.render(Model.DEFAULT_SCALE);
                    }

                    if (hiveTessellation == GraphicsSetting.MEDIUM || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.webbing14.render(Model.DEFAULT_SCALE);
                        model.webbing06.render(Model.DEFAULT_SCALE);
                        model.webbing10.render(Model.DEFAULT_SCALE);
                        model.webbing03.render(Model.DEFAULT_SCALE);
                    }
                }

//                if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                {
                    model.eBottomRoot01.isHidden = resin.eastTopBlock == Blocks.air;
                    model.eTopRoot01.isHidden = resin.eastBottomBlock == Blocks.air;
                }

                if (resin.eastBlock == Blocks.air)
                {
                    if (hiveTessellation == GraphicsSetting.HIGH || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.eResinRipCenter.render(Model.DEFAULT_SCALE);
                    }

                    if (hiveTessellation == GraphicsSetting.MEDIUM || hiveTessellation == GraphicsSetting.ULTRA)
                    {
                        model.webbing12.render(Model.DEFAULT_SCALE);
                        model.webbing08.render(Model.DEFAULT_SCALE);
                        model.webbing02.render(Model.DEFAULT_SCALE);
                    }
                }
            }
            OpenGL.popMatrix();
        }
    }
}
