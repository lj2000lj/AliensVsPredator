package org.avp.gui;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.GuiCustomScreen;
import com.arisux.mdxlib.lib.client.gui.GuiElement;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.ScaledResolution;
import com.arisux.mdxlib.lib.client.render.Screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiModSettings extends GuiCustomScreen
{
    private ArrayList<GuiElement> elements = new ArrayList<GuiElement>();
    private int                   scroll   = 0;

    public GuiModSettings(GuiScreen parent)
    {
        GuiCustomButton titleElement = new GuiCustomButton(0, 0, 0, 40, 10, "Graphics Settings");
        titleElement.tooltip = "Higher settings require more system resources.";
        titleElement.enabled = false;
        this.elements.add(titleElement);
        
        GuiCustomButton buttonElement = new GuiCustomButton(1, 0, 0, 40, 10, String.format("Hive Tessellation", AliensVsPredator.settings().getHiveTesselation()));
        buttonElement.tooltip = "Visual detail complexity of hive resin. - Low, High, Ultra";
        buttonElement.setAction(new IAction()
        {
            @Override
            public void perform(GuiCustomButton button)
            {
                AliensVsPredator.settings().toggleHiveTessellation();
            }
        });
        this.elements.add(buttonElement);
        
        buttonElement = new GuiCustomButton(2, 0, 0, 40, 10, "Apply");
        buttonElement.tooltip = "Apply your setting changes.";
        buttonElement.setAction(new IAction()
        {
            @Override
            public void perform(GuiCustomButton button)
            {
                AliensVsPredator.settings().saveClientSettings();
                Minecraft.getMinecraft().refreshResources();
                button.displayString = "Settings Saved";
            }
        });
        this.elements.add(buttonElement);
    }

    @Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ScaledResolution resolution = Screen.scaledDisplayResolution();

        Draw.drawRect(0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), 0xCC000000);

        for (GuiElement element : this.elements)
        {
            if (element instanceof GuiCustomButton)
            {
                GuiCustomButton button = (GuiCustomButton) element;
                button.drawButton();
                Draw.drawString(button.tooltip, button.xPosition + button.width + 10, button.yPosition + (button.height / 2) - 4, 0xFFFFFFFF);
            }
        }

        Draw.drawRect(0, 0, resolution.getScaledWidth(), 20, 0xFF121212);
        Draw.drawString("AliensVsPredator Mod Settings", 5, 6, 0xFF00AAFF, false);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        this.updateScrolling();

        int buttonWidth = 150;
        int buttonHeight = 15;
        int vPadding = 1;
        int hPadding = 5;

        for (GuiElement element : this.elements)
        {
            if (element instanceof GuiCustomButton)
            {
                GuiCustomButton button = (GuiCustomButton) element;

                int idx = this.elements.indexOf(element);

                button.xPosition = hPadding;
                button.yPosition = 25 + ((idx * (buttonHeight + vPadding)) - buttonHeight * scroll);
                button.width = buttonWidth;
                button.height = buttonHeight;
                button.baseColor = button.enabled ? 0xFF00AAFF : 0x00000000;
            }
            
            if (element.id == 1)
            {
                element.displayString = String.format("Hive Tessellation (%s)", AliensVsPredator.settings().getHiveTesselation());
            }
        }
    }

    private void updateScrolling()
    {
        int dWheel = Mouse.getDWheel();

        if (dWheel > 0)
        {
            scrollDown();
        }
        else if (dWheel < 0)
        {
            scrollUp();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            scrollDown();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            scrollUp();
        }
    }

    @Override
    public void drawBackground(int i)
    {
        super.drawBackground(i);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    public void scrollDown()
    {
        if (this.scroll > 0)
        {
            this.scroll -= 1;
        }
    }

    public void scrollUp()
    {
        if (this.scroll < this.elements.size() - 1)
        {
            this.scroll += 1;
        }
    }

    public int getScroll()
    {
        return this.scroll;
    }
}
