package org.avp.gui;

import java.util.ArrayList;

import org.apache.commons.lang3.text.WordUtils;
import org.avp.AliensVsPredator;
import org.avp.Settings;
import org.avp.Settings.ClientSettings;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.arisux.mdxlib.config.ConfigSetting;
import com.arisux.mdxlib.config.ConfigSettingBoolean;
import com.arisux.mdxlib.config.ConfigSettingGraphics;
import com.arisux.mdxlib.config.ConfigSettingInteger;
import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.GuiCustomScreen;
import com.arisux.mdxlib.lib.client.gui.GuiCustomTextbox;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.gui.IGuiElement;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.ScaledResolution;
import com.arisux.mdxlib.lib.client.render.Screen;
import com.arisux.mdxlib.lib.game.Chat;
import com.arisux.mdxlib.lib.util.SystemInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiModSettings extends GuiCustomScreen
{
    private ArrayList<IGuiElement> elements = new ArrayList<IGuiElement>();
    private int                    scroll   = 0;
    private String                 processorName;

    public GuiModSettings(GuiScreen parent)
    {
        /** Graphics Settings **/
        GuiCustomButton header = new GuiCustomButton(0, 0, 0, 0, 10, "Graphics");
        header.fontShadow = false;
        header.overlayColorNormal = 0xCC000000;
        header.fontColor = 0xFF00CCFF;
        header.enabled = false;
        this.elements.add(header);

        GuiCustomButton element = null;
        GuiCustomTextbox textbox = null;

        for (ConfigSetting setting : ClientSettings.instance.allSettings())
        {
            if (setting instanceof ConfigSettingGraphics)
            {
                element = new GuiCustomButton(2, 0, 0, 0, 10, setting.getStringValue());

                if (setting.property().comment == null || setting.property().comment != null && setting.property().comment.isEmpty())
                {
                    element.tooltip = Chat.format(String.format("&c%s", setting.property().getLanguageKey()));
                }
                else
                {
                    element.tooltip = Chat.format(String.format("&b%s&f &8%s", WordUtils.capitalize(setting.property().getLanguageKey().replace("_", " ")), setting.property().comment));
                }

                element.setAction(new IAction()
                {
                    @Override
                    public void perform(IGuiElement element)
                    {
                        setting.toggle();

                        if (element instanceof GuiCustomButton)
                        {
                            GuiCustomButton button = (GuiCustomButton) element;
                            button.displayString = setting.getStringValue();
                        }
                    }
                });
                this.elements.add(element);
            }

            if (setting instanceof ConfigSettingInteger)
            {
                textbox = new GuiCustomTextbox(0, 0, 0, 0);
                textbox.setText(setting.getStringValue());

                if (setting.property().comment == null || setting.property().comment != null && setting.property().comment.isEmpty())
                {
                    textbox.tooltip = Chat.format(String.format("&c%s", setting.property().getLanguageKey()));
                }
                else
                {
                    textbox.tooltip = Chat.format(String.format("&b%s&f &8%s", WordUtils.capitalize(setting.property().getLanguageKey().replace("_", " ")), setting.property().comment));
                }

                textbox.setAction(new IAction()
                {
                    @Override
                    public void perform(IGuiElement element)
                    {
                        if (element instanceof GuiCustomTextbox)
                        {
                            GuiCustomTextbox t = (GuiCustomTextbox) element;
                            System.out.println(setting.property().getName() + " " + setting.value() + " " + t.getText());
                            setting.property().set(t.getText());
                        }
                    }
                });
                textbox.trackElement();
                this.elements.add(textbox);
            }
        }

        /** Game Settings **/
        header = new GuiCustomButton(0, 0, 0, 0, 10, "Gameplay");
        header.overlayColorNormal = 0xCC000000;
        header.fontColor = 0xFF00CCFF;
        header.enabled = false;
        this.elements.add(header);

        for (ConfigSetting setting : AliensVsPredator.settings().allSettings())
        {
            if (setting instanceof ConfigSettingBoolean)
            {
                element = new GuiCustomButton(2, 0, 0, 0, 10, setting.getStringValue());

                if (setting.property().comment == null || setting.property().comment != null && setting.property().comment.isEmpty())
                {
                    element.tooltip = Chat.format(String.format("&c%s", setting.property().getLanguageKey()));
                }
                else
                {
                    element.tooltip = Chat.format(String.format("&b%s&f &8%s", WordUtils.capitalize(setting.property().getLanguageKey().replace("_", " ")), setting.property().comment));
                }

                element.setAction(new IAction()
                {
                    @Override
                    public void perform(IGuiElement element)
                    {
                        setting.toggle();

                        if (element instanceof GuiCustomButton)
                        {
                            GuiCustomButton button = (GuiCustomButton) element;
                            button.displayString = setting.getStringValue();
                        }
                    }
                });
                this.elements.add(element);
            }

            if (setting instanceof ConfigSettingInteger)
            {
                textbox = new GuiCustomTextbox(0, 0, 0, 0);
                textbox.setText(setting.getStringValue());

                if (setting.property().comment == null || setting.property().comment != null && setting.property().comment.isEmpty())
                {
                    textbox.tooltip = Chat.format(String.format("&c%s", setting.property().getLanguageKey()));
                }
                else
                {
                    textbox.tooltip = Chat.format(String.format("&b%s&f &8%s", WordUtils.capitalize(setting.property().getLanguageKey().replace("_", " ")), setting.property().comment));
                }

                textbox.setAction(new IAction()
                {
                    @Override
                    public void perform(IGuiElement element)
                    {
                        if (element instanceof GuiCustomTextbox)
                        {
                            GuiCustomTextbox t = (GuiCustomTextbox) element;
                            System.out.println(setting.property().getName() + " " + setting.value() + " " + t.getText());
                            setting.property().set(t.getText());
                        }
                    }
                });
                textbox.trackElement();
                this.elements.add(textbox);
            }
        }

        element = new GuiCustomButton(2, 0, 0, 0, 10, "Apply");
        element.tooltip = "Applies these settings without saving them to the config. Changes will be lost after restarting the game.";
        element.overlayColorHover = 0xAA444444;
        element.fontColor = 0xFF00CCFF;
        element.fontShadow = false;
        element.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                applySettings();
            }
        });
        this.elements.add(element);

        element = new GuiCustomButton(2, 0, 0, 0, 10, "Save and Apply");
        element.tooltip = "Applies these settings and saves them to the config.";
        element.overlayColorHover = 0xAA444444;
        element.fontColor = 0xFF00CCFF;
        element.fontShadow = false;
        element.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                applyAndSaveSettings();
            }
        });
        this.elements.add(element);
    }

    private void applyAndSaveSettings()
    {
        this.applySettings();
        this.saveSettings();
    }

    private void saveSettings()
    {
        Settings.instance.saveSettings();
        ClientSettings.instance.saveSettings();
    }

    private void applySettings()
    {
        for (IGuiElement e : elements)
        {
            if (e instanceof GuiCustomTextbox)
            {
                GuiCustomTextbox textbox = (GuiCustomTextbox) e;
                textbox.getAction().perform(textbox);
            }
        }
        Minecraft.getMinecraft().refreshResources();
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

        Draw.drawRect(0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), 0xAA000000);

        int idx = 1;
        int yStart = 15;
        int padding = 12;

        double memoryTotal = SystemInfo.toMBFromB(SystemInfo.vmMemoryTotalBytes());
        double memoryFree = SystemInfo.toMBFromB(SystemInfo.vmMemoryFreeBytes());
        double memoryMax = SystemInfo.toMBFromB(SystemInfo.vmMemoryMaxBytes());
        double memoryUsed = memoryTotal - memoryFree;
        int memoryPercent = (int) (memoryUsed * 100D / memoryTotal);
        int memoryPercentMax = (int) (memoryUsed * 100D / memoryMax);

        Draw.drawProgressBar(String.format("VM Memory %s/%s %s%%", memoryUsed, memoryTotal, memoryPercent), 100, memoryPercent, 5, yStart + (padding * idx++) - (15 * scroll), resolution.getScaledWidth() - 10, 5, 0, 0xFF00CCFF, false);
        Draw.drawProgressBar(String.format("VM Memory Total %s/%s %s%%", memoryUsed, memoryMax, memoryPercentMax), 100, memoryPercentMax, 5, yStart + (padding * idx++) - (15 * scroll), resolution.getScaledWidth() - 10, 5, 0, 0xFF00CCFF, false);

        Draw.drawString(String.format("VM Memory: %sMB/%sMB MAX: %sMB", Math.round(SystemInfo.toMBFromB(SystemInfo.vmMemoryTotalBytes()) - SystemInfo.toMBFromB(SystemInfo.vmMemoryFreeBytes())), Math.round(SystemInfo.toMBFromB(SystemInfo.vmMemoryTotalBytes())), Math.round(SystemInfo.toMBFromB(SystemInfo.vmMemoryMaxBytes()))), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);
        Draw.drawString(String.format("CPU: %s (%s Logical Cores)", SystemInfo.cpu(), SystemInfo.cpuCores()), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);
        Draw.drawString(String.format("GPU: %s (%s)", SystemInfo.gpu(), SystemInfo.gpuVendor()), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);
        if (SystemInfo.getMemoryCapacity() != 0)
        {
            Draw.drawString(String.format("RAM: %sGB", (int) SystemInfo.toGBFromB(SystemInfo.getMemoryCapacity())), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);
        }
        Draw.drawString(String.format("OS: %s (%s, %s)", SystemInfo.osName(), SystemInfo.osVersion(), SystemInfo.osArchitecture()), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);
        Draw.drawString(String.format("Java: %s", SystemInfo.javaVersion()), 5, yStart + (padding * idx++) - (15 * scroll), 0xFF00CCFF, false);

        for (IGuiElement element : this.elements)
        {
            if (element instanceof GuiCustomButton)
            {
                GuiCustomButton button = (GuiCustomButton) element;
                button.drawButton();
                Draw.drawString(button.tooltip, button.xPosition + button.width + 10, button.yPosition + (button.height / 2) - 4, 0xFFFFFFFF);
            }

            if (element instanceof GuiCustomTextbox)
            {
                GuiCustomTextbox textbox = (GuiCustomTextbox) element;
                textbox.drawTextBox();
                Draw.drawString(textbox.tooltip, textbox.xPosition + textbox.width + 10, textbox.yPosition + (textbox.height / 2) - 4, 0xFFFFFFFF);
            }

            element.drawTooltip();
        }

        Draw.drawRect(0, 0, resolution.getScaledWidth(), 20, 0xAA000000);
        Draw.drawString("Aliens Vs Predator Settings", 5, 6, 0xFF00CCFF, false);
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        this.updateScrolling();

        int yStart = 130;
        int controlWidth = 100;
        int controlHeight = 15;
        int vPadding = 1;
        int hPadding = 5;

        for (IGuiElement element : this.elements)
        {
            int idx = this.elements.indexOf(element);

            if (element instanceof GuiCustomButton)
            {
                GuiCustomButton button = (GuiCustomButton) element;

                button.xPosition = hPadding;
                button.yPosition = yStart + ((idx * (controlHeight + vPadding)) - controlHeight * scroll);
                button.width = controlWidth;
                button.height = controlHeight;

                if (!button.enabled)
                {
                    button.baseColor = 0x00000000;
                }
                else
                {
                    button.overlayColorHover = 0x22FFFFFF;
                    button.overlayColorNormal = 0x88000000;
                    button.baseColor = 0x00000000;
                }
            }

            if (element instanceof GuiCustomTextbox)
            {
                GuiCustomTextbox textbox = (GuiCustomTextbox) element;

                textbox.xPosition = hPadding;
                textbox.yPosition = yStart + ((idx * (controlHeight + vPadding)) - controlHeight * scroll);
                textbox.width = controlWidth;
                textbox.height = controlHeight;
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
    protected void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);

        for (IGuiElement element : this.elements)
        {
            if (element instanceof GuiCustomTextbox)
            {
                GuiCustomTextbox textbox = (GuiCustomTextbox) element;
                textbox.textboxKeyTyped(c, i);
            }
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
