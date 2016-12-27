package org.avp.gui;

import org.avp.entities.extended.SpecialPlayer;
import org.avp.event.client.TacticalHUDRenderEvent;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.GuiCustomScreen;
import com.arisux.mdxlib.lib.client.gui.GuiCustomSlider;
import com.arisux.mdxlib.lib.client.gui.GuiCustomTextbox;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.ScaledResolution;
import com.arisux.mdxlib.lib.client.render.Screen;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.client.gui.GuiScreen;

public class GuiTacticalHUDSettings extends GuiCustomScreen
{
    protected final SpecialPlayer properties;
    private GuiCustomTextbox textboxChannel;
    private GuiCustomButton buttonSave;
    private GuiCustomButton buttonNightvisionToggle;
    private GuiCustomButton buttonEntityCullingToggle;
    private GuiCustomSlider sliderTxPower;
    private GuiCustomSlider sliderViewportThreshold;

    public GuiTacticalHUDSettings(GuiScreen parent)
    {
        this.properties = SpecialPlayer.get(Game.minecraft().thePlayer);
        this.textboxChannel = new GuiCustomTextbox(this, 0, 0, 100, 15);
        this.buttonSave = new GuiCustomButton(0, 0, 0, 100, 15, "", null);
        this.buttonNightvisionToggle = new GuiCustomButton(0, 0, 0, 100, 15, "", null);
        this.buttonEntityCullingToggle = new GuiCustomButton(0, 0, 0, 100, 15, "", null);
        this.sliderTxPower = new GuiCustomSlider(0, 100, 100, "", 1, 1024);
        this.sliderViewportThreshold = new GuiCustomSlider(0, 100, 100, "", 1, 32);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.textboxChannel.setText("Default");
        this.sliderTxPower.sliderValue = properties.getBroadcastRadius() / sliderTxPower.sliderMaxValue;
        this.sliderTxPower.displayString = "Transmit Power: " + (int) (sliderTxPower.sliderValue * sliderTxPower.sliderMaxValue);

        this.sliderViewportThreshold.sliderValue = TacticalHUDRenderEvent.instance.getViewportThreshold() / sliderViewportThreshold.sliderMaxValue;
        this.sliderViewportThreshold.displayString = "Threshold: " + (int) (sliderViewportThreshold.sliderValue * sliderViewportThreshold.sliderMaxValue);

        this.buttonNightvisionToggle.setAction(new IAction()
        {
            @Override
            public void perform(GuiCustomButton button)
            {
                properties.setNightvisionEnabled(!properties.isNightvisionEnabled());
                properties.syncWithServer();
            }
        });

        this.buttonEntityCullingToggle.setAction(new IAction()
        {
            @Override
            public void perform(GuiCustomButton button)
            {
                properties.setEntityCullingEnabled(!properties.isEntityCullingEnabled());
                properties.syncWithServer();
            }
        });

        this.buttonSave.setAction(new IAction()
        {
            @Override
            public void perform(GuiCustomButton button)
            {
                String newChannel = textboxChannel.getText();
                int newRadius = (int) (sliderTxPower.sliderValue * sliderTxPower.sliderMaxValue);
                int newThreshold = (int) (sliderViewportThreshold.sliderValue * sliderViewportThreshold.sliderMaxValue);

                if (properties.getBroadcastChannel() != newChannel || properties.getBroadcastRadius() != newRadius || TacticalHUDRenderEvent.instance.getViewportThreshold() != newThreshold)
                {
                    properties.setBroadcastRadius(newRadius);
                    properties.setBroadcastChannel(newChannel);
                    TacticalHUDRenderEvent.instance.setViewportThreshold(newThreshold);
                    properties.syncWithServer();
                }

                mc.displayGuiScreen(null);
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ScaledResolution resolution = Screen.scaledDisplayResolution();

        int interfaceWidth = 250;
        int interfaceStartX = resolution.getScaledWidth() - interfaceWidth;
        int elementStart = 30;
        int elementSpacing = 21;

        Draw.drawRect(interfaceStartX, 0, interfaceWidth, height, 0xCC000000);
        Draw.drawString("Tactical HUD Configuration", interfaceStartX + 10, 10, 0xFF00AAFF);

        textboxChannel.setMaxStringLength(18);
        textboxChannel.xPosition = interfaceStartX + 10;
        textboxChannel.yPosition = elementStart;
        textboxChannel.height = 15;
        textboxChannel.width = 120;
        Draw.drawString("Broadcast Channel", textboxChannel.xPosition + textboxChannel.width + 10, textboxChannel.yPosition + 3, 0xFFCCCCCC);
        textboxChannel.drawTextBox();

        sliderTxPower.label = "TX Power";
        sliderTxPower.xPosition = interfaceStartX + 10;
        sliderTxPower.yPosition = elementStart += elementSpacing;
        sliderTxPower.width = 120;
        sliderTxPower.height = 15;
        sliderTxPower.sliderMaxValue = 1024;
        sliderTxPower.baseColor = 0x55000000;
        sliderTxPower.sliderButtonColor = 0x9900AAFF;
        sliderTxPower.tooltip = "The distance this tactical HUD will connect to other tactical HUDs.";
        Draw.drawString("Transmit Power", sliderTxPower.xPosition + sliderTxPower.width + 10, sliderTxPower.yPosition + 3, 0xFFCCCCCC);
        sliderTxPower.drawButton();

        sliderViewportThreshold.label = "Threshold";
        sliderViewportThreshold.xPosition = interfaceStartX + 10;
        sliderViewportThreshold.yPosition = elementStart += elementSpacing;
        sliderViewportThreshold.width = 120;
        sliderViewportThreshold.height = 15;
        sliderViewportThreshold.sliderMaxValue = 32;
        sliderViewportThreshold.baseColor = 0x55000000;
        sliderViewportThreshold.sliderButtonColor = 0x9900AAFF;
        sliderViewportThreshold.tooltip = "The amount of users with tactical HUDs to display in the viewport.";
        Draw.drawString("Viewport Threshold", sliderViewportThreshold.xPosition + sliderViewportThreshold.width + 10, sliderViewportThreshold.yPosition + 3, 0xFFCCCCCC);
        sliderViewportThreshold.drawButton();

        buttonNightvisionToggle.displayString = properties.isNightvisionEnabled() ? "Disable Nightvision" : "Enable Nightvision";
        buttonNightvisionToggle.xPosition = interfaceStartX + 10;
        buttonNightvisionToggle.yPosition = elementStart += elementSpacing;
        buttonNightvisionToggle.width = 120;
        buttonNightvisionToggle.height = 18;
        buttonNightvisionToggle.baseColor = 0xAA00AAFF;
        buttonNightvisionToggle.drawButton();
        buttonNightvisionToggle.tooltip = "Toggle nightvision on or off.";

        buttonEntityCullingToggle.displayString = properties.isEntityCullingEnabled() ? "Disable Entity Culling" : "Enable Entity Culling";
        buttonEntityCullingToggle.xPosition = interfaceStartX + 10;
        buttonEntityCullingToggle.yPosition = elementStart += elementSpacing;
        buttonEntityCullingToggle.width = 120;
        buttonEntityCullingToggle.height = 18;
        buttonEntityCullingToggle.baseColor = 0xAA00AAFF;
        buttonEntityCullingToggle.drawButton();
        buttonEntityCullingToggle.tooltip = "";

        buttonSave.displayString = "Save";
        buttonSave.xPosition = interfaceStartX + 10;
        buttonSave.yPosition = Screen.scaledDisplayResolution().getScaledHeight() - buttonSave.height - 10;
        buttonSave.width = 50;
        buttonSave.height = 20;
        buttonSave.baseColor = 0xAA00AAFF;
        buttonSave.drawButton();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);
        textboxChannel.textboxKeyTyped(c, i);
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

//        textboxChannel.remove();
//        buttonSave.remove();
//        sliderTxPower.remove();
//        sliderViewportThreshold.remove();
    }
}
