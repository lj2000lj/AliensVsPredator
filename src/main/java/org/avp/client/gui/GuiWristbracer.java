package org.avp.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.inventory.ContainerWristbracer;
import org.avp.item.ItemWristbracer;
import org.lwjgl.opengl.GL11;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.gui.IGuiElement;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;

public class GuiWristbracer extends GuiContainer
{
    private HashMap<GuiButton, Integer> displays   = new HashMap<GuiButton, Integer>();
    protected List<GuiButton>           buttons = new ArrayList<GuiButton>();
    public ContainerWristbracer         container;

    public GuiWristbracer(EntityPlayer player, ContainerWristbracer container)
    {
        super(container);

        this.container = container;
        this.xSize = 192;
        this.ySize = 156;

        for (int x = 1; x <= 6; x++)
        {
            this.buttons.add(new GuiCustomButton(x, 0, 0, 50, 100, "X"));
            this.displays.put(this.buttons.get(x - 1), 0);
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        ;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        this.guiLeft = this.width / 2 - xSize / 2;
        this.guiTop = this.height / 2 - ySize / 2;
        AliensVsPredator.resources().GUI_WRISTBRACER.bind();
        Draw.drawQuad(guiLeft, guiTop, xSize, ySize - 30, 0, 0, 0);

        for (byte s = 0; s < 9; s++)
        {
            Draw.drawRect(guiLeft + 15 + (18 * s), guiTop + 136, 16, 16, 0xAA000000);
        }

        for (int x1 = 1; x1 <= 6; x1++)
        {
            GuiCustomButton button = (GuiCustomButton) this.buttons.get(x1 - 1);

            if (mc.gameSettings.difficulty == EnumDifficulty.EASY || mc.gameSettings.difficulty == EnumDifficulty.PEACEFUL)
            {
                button.tooltip = String.valueOf(this.displays.get(button));
            }
            else
            {
                button.tooltip = "";
            }

            button.displayString = "";
            button.xPosition = guiLeft + 15 + 27 * (x1 - 1);
            button.yPosition = guiTop + 49;
            button.width = 25;
            button.height = 28;
            button.baseColor = 0x00000000;
            button.overlayColorHover = 0x22FF0000;
            button.drawButton();
            button.drawTooltip();

            button.setAction(new IAction()
            {
                @Override
                public void perform(IGuiElement element)
                {
                    updateScreenDigit(button.id, displays.get(button) < 9 ? displays.get(button) + 1 : 0);
                }
            });

            drawYautjaDigit(this.displays.get(button), guiLeft + 13 + 27 * (x1 - 1), guiTop + 49);
        }

        String combonation = ItemWristbracer.code(displays.get(buttons.get(0)), displays.get(buttons.get(1)), displays.get(buttons.get(2)), displays.get(buttons.get(3)), displays.get(buttons.get(4)), displays.get(buttons.get(5)));

        if (ItemWristbracer.isCodeValid(combonation))
        {
            ItemWristbracer.getAction(combonation).onAction(combonation, container);
        }
    }

    public void updateScreenDigit(int displayId, int digit)
    {
        GuiCustomButton button = (GuiCustomButton) this.buttons.get(displayId - 1);
        displays.remove(button);
        displays.put(button, digit);
    }

    /** Display a hex number (not hexadecimal) spanned across all 6 displays **/
    public void displaySpannedHex(int hex)
    {
        String spannedInt = String.valueOf(hex);
        char[] splitSpannedInt = spannedInt.toCharArray();

        for (int x = 1; x <= 6; x++)
        {
            if (spannedInt.length() == 6)
            {
                this.updateScreenDigit(x, Integer.valueOf(String.valueOf(splitSpannedInt[x - 1])));
            }
        }
    }

    public static void drawYautjaDigit(int number, int xPos, int yPos)
    {
        for (int x = 1; x <= 9; x++)
        {
            if (x == number)
            {
                OpenGL.enable(GL11.GL_BLEND);
                AliensVsPredator.resources().GUI_WRISTBRACER.bind();
                Draw.drawQuad(xPos, yPos, 28, 50, 0, (27 * (x - 1)), 126);
            }
        }
    }
}
