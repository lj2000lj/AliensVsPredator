package org.avp.gui;

import org.avp.entities.extended.ModPlayer;
import org.avp.event.client.render.TacticalHUDRenderEvent;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.GuiCustomScreen;
import com.arisux.mdxlib.lib.client.gui.GuiCustomSlider;
import com.arisux.mdxlib.lib.client.gui.GuiCustomTextbox;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.gui.IGuiElement;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.ScaledResolution;
import com.arisux.mdxlib.lib.client.render.Screen;
import com.arisux.mdxlib.lib.game.Game;

import net.minecraft.client.gui.GuiScreen;

public class GuiTacticalHUDSettings extends GuiCustomScreen
{
    protected final ModPlayer player;
    private GuiCustomTextbox channel;
    private GuiCustomButton save;
    private GuiCustomButton nightvision;
    private GuiCustomButton entityCulling;
    private GuiCustomSlider txPower;
    private GuiCustomSlider viewportThreshold;

    public GuiTacticalHUDSettings(GuiScreen parent)
    {
        this.player = ModPlayer.get(Game.minecraft().thePlayer);
        this.channel = new GuiCustomTextbox(this, 0, 0, 100, 15);
        this.save = new GuiCustomButton(0, 0, 0, 100, 15, "");
        this.nightvision = new GuiCustomButton(0, 0, 0, 100, 15, "");
        this.entityCulling = new GuiCustomButton(0, 0, 0, 100, 15, "");
        this.txPower = new GuiCustomSlider(0, 100, 100, "", 1, 1024);
        this.viewportThreshold = new GuiCustomSlider(0, 100, 100, "", 1, 32);
        this.channel.trackElement();
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.channel.setText(this.player.getBroadcastChannel());
        this.txPower.sliderValue = player.getBroadcastRadius() / txPower.sliderMaxValue;
        this.txPower.displayString = "Transmit Power: " + (int) (txPower.sliderValue * txPower.sliderMaxValue);

        this.viewportThreshold.sliderValue = TacticalHUDRenderEvent.instance.getViewportThreshold() / viewportThreshold.sliderMaxValue;
        this.viewportThreshold.displayString = "Threshold: " + (int) (viewportThreshold.sliderValue * viewportThreshold.sliderMaxValue);

        this.nightvision.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                player.setNightvisionEnabled(!player.isNightvisionEnabled());
                player.syncWithServer();
            }
        });

        this.entityCulling.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                player.setEntityCullingEnabled(!player.isEntityCullingEnabled());
                player.syncWithServer();
            }
        });

        this.save.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                String newChannel = channel.getText();
                int newRadius = (int) (txPower.sliderValue * txPower.sliderMaxValue);
                int newThreshold = (int) (viewportThreshold.sliderValue * viewportThreshold.sliderMaxValue);

                if (player.getBroadcastChannel() != newChannel || player.getBroadcastRadius() != newRadius || TacticalHUDRenderEvent.instance.getViewportThreshold() != newThreshold)
                {
                    player.setBroadcastRadius(newRadius);
                    player.setBroadcastChannel(newChannel);
                    TacticalHUDRenderEvent.instance.setViewportThreshold(newThreshold);
                    player.syncWithServer();
                    channel.setText(newChannel);
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

        channel.setMaxStringLength(18);
        channel.xPosition = interfaceStartX + 10;
        channel.yPosition = elementStart;
        channel.height = 15;
        channel.width = 120;
        Draw.drawString("Broadcast Channel", channel.xPosition + channel.width + 10, channel.yPosition + 3, 0xFFCCCCCC);
        channel.drawTextBox();

        txPower.label = "TX Power";
        txPower.xPosition = interfaceStartX + 10;
        txPower.yPosition = elementStart += elementSpacing;
        txPower.width = 120;
        txPower.height = 15;
        txPower.sliderMaxValue = 1024;
        txPower.baseColor = 0x55000000;
        txPower.sliderButtonColor = 0x9900AAFF;
        txPower.tooltip = "The distance this tactical HUD will connect to other tactical HUDs.";
        Draw.drawString("Transmit Power", txPower.xPosition + txPower.width + 10, txPower.yPosition + 3, 0xFFCCCCCC);
        txPower.drawButton();

        viewportThreshold.label = "Threshold";
        viewportThreshold.xPosition = interfaceStartX + 10;
        viewportThreshold.yPosition = elementStart += elementSpacing;
        viewportThreshold.width = 120;
        viewportThreshold.height = 15;
        viewportThreshold.sliderMaxValue = 32;
        viewportThreshold.baseColor = 0x55000000;
        viewportThreshold.sliderButtonColor = 0x9900AAFF;
        viewportThreshold.tooltip = "The amount of users with tactical HUDs to display in the viewport.";
        Draw.drawString("Viewport Threshold", viewportThreshold.xPosition + viewportThreshold.width + 10, viewportThreshold.yPosition + 3, 0xFFCCCCCC);
        viewportThreshold.drawButton();

        nightvision.displayString = player.isNightvisionEnabled() ? "Disable Nightvision" : "Enable Nightvision";
        nightvision.xPosition = interfaceStartX + 10;
        nightvision.yPosition = elementStart += elementSpacing;
        nightvision.width = 120;
        nightvision.height = 18;
        nightvision.baseColor = 0xAA00AAFF;
        nightvision.drawButton();
        nightvision.tooltip = "Toggle nightvision on or off.";

        entityCulling.displayString = player.isEntityCullingEnabled() ? "Disable Entity Culling" : "Enable Entity Culling";
        entityCulling.xPosition = interfaceStartX + 10;
        entityCulling.yPosition = elementStart += elementSpacing;
        entityCulling.width = 120;
        entityCulling.height = 18;
        entityCulling.baseColor = 0xAA00AAFF;
        entityCulling.drawButton();
        entityCulling.tooltip = "";

        save.displayString = "Save";
        save.xPosition = interfaceStartX + 10;
        save.yPosition = Screen.scaledDisplayResolution().getScaledHeight() - save.height - 10;
        save.width = 50;
        save.height = 20;
        save.baseColor = 0xAA00AAFF;
        save.drawButton();
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
        channel.textboxKeyTyped(c, i);
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
}
