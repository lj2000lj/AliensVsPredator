package org.avp.client.gui;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketAddTuretTarget;
import org.avp.packets.server.PacketReadFromDataDevice;
import org.avp.packets.server.PacketRemoveTurretTarget;
import org.avp.packets.server.PacketWriteToDataDevice;
import org.avp.tile.TileEntityTurret;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.arisux.mdxlib.lib.client.gui.GuiCustomButton;
import com.arisux.mdxlib.lib.client.gui.GuiCustomSlider;
import com.arisux.mdxlib.lib.client.gui.IAction;
import com.arisux.mdxlib.lib.client.gui.IGuiElement;
import com.arisux.mdxlib.lib.client.render.Color;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.entity.Entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiTurret extends GuiContainer
{
    private TileEntityTurret tile;
    private GuiCustomButton buttonScrollUp;
    private GuiCustomButton buttonScrollDown;
    private GuiCustomButton buttonAddAsTarget;
    private GuiCustomButton buttonSave;
    private GuiCustomButton buttonLoad;
    private GuiCustomSlider sliderColorR, sliderColorG, sliderColorB, sliderColorA;
    private int scroll = 0;
    private float modelRotation = -90.0F;
    private ArrayList<Class<? extends Entity>> entityList;
    private ArrayList<EntityLiving> entityLivingList;

    @SuppressWarnings("unchecked")
    public GuiTurret(EntityPlayer player, TileEntityTurret turret, World world, int x, int y, int z)
    {
        super(turret.getContainer(player));
        this.xSize = 225;
        this.ySize = 200;
        this.tile = turret;
        this.entityList = new ArrayList<Class<? extends Entity>>(EntityList.stringToClassMapping.values());
        this.entityLivingList = new ArrayList<EntityLiving>();

        for (Class<?> c : this.entityList)
        {
            Entity entity = null;
            try
            {
                Constructor<?> ctor = c.getConstructor(new Class[] { World.class });
                entity = (Entity) ctor.newInstance(new Object[] { Game.minecraft().theWorld });
            }
            catch (Exception e)
            {
                ;
            }

            if (entity != null && entity instanceof EntityLiving)
            {
                this.entityLivingList.add((EntityLiving) entity);
            }
        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.buttonScrollUp = new GuiCustomButton(0, 0, 0, 20, 20, "");
        this.buttonScrollDown = new GuiCustomButton(1, 0, 0, 20, 20, "");
        this.buttonAddAsTarget = new GuiCustomButton(2, 0, 0, 50, 20, "");
        this.buttonSave = new GuiCustomButton(3, 0, 0, 35, 20, "");
        this.buttonLoad = new GuiCustomButton(4, 0, 0, 35, 20, "");

        this.sliderColorA = new GuiCustomSlider(0, 0, 0, "Laser Color A", 0F, 255F);
        this.sliderColorR = new GuiCustomSlider(0, 0, 0, "Laser Color R", 0F, 255F);
        this.sliderColorG = new GuiCustomSlider(0, 0, 0, "Laser Color G", 0F, 255F);
        this.sliderColorB = new GuiCustomSlider(0, 0, 0, "Laser Color B", 0F, 255F);

        this.sliderColorA.sliderValue = ((this.tile.beamColor >> 24 & 255) / 255.0F);
        this.sliderColorR.sliderValue = ((this.tile.beamColor >> 16 & 255) / 255.0F);
        this.sliderColorG.sliderValue = ((this.tile.beamColor >> 8 & 255) / 255.0F);
        this.sliderColorB.sliderValue = ((this.tile.beamColor & 255) / 255.0F);
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();

        tile.applyUpgrades();

        for (Class<? extends Entity> c : this.tile.getDangerousTargets())
        {
            AliensVsPredator.network().sendToServer(new PacketAddTuretTarget(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord, Entities.getEntityRegistrationId(c)));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        AliensVsPredator.resources().GUI_TURRET.bind();
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        int stacksTotal = this.tile.getContainer(this.mc.thePlayer).getAmmoBay().getSizeInventory() * this.tile.getContainer(this.mc.thePlayer).getAmmoBay().getInventoryStackLimit();
        int stacksCurrent = 0;

        for (int i = 0; i < this.tile.getContainer(this.mc.thePlayer).getAmmoBay().getSizeInventory(); i++)
        {
            ItemStack stack = this.tile.getContainer(this.mc.thePlayer).getAmmoBay().getStackInSlot(i);

            if ((stack == null) || (stack.getItem() != this.tile.getItemAmmo()))
                continue;
            stacksCurrent += stack.stackSize;
        }

        Draw.drawProgressBar("Magazine " + (this.tile.getCurAmmo() <= 0 ? 0 : this.tile.getCurAmmo()) + "/" + this.tile.getMaxAmmo(), this.tile.getMaxAmmo(), this.tile.getCurAmmo() < 0 ? 1 : this.tile.getCurAmmo(), this.guiLeft + 7, this.guiTop + 20, this.xSize - 100, 3, 1, this.tile.getCurAmmo() < this.tile.getMaxAmmo() / 2 ? -22016 : this.tile.getCurAmmo() < this.tile.getMaxAmmo() / 6 ? -65536 : -16733441, false);
        Draw.drawProgressBar("Total " + stacksCurrent + "/" + stacksTotal, stacksTotal, stacksCurrent, this.guiLeft + 7, this.guiTop + 30, this.xSize - 100, 3, 1, stacksCurrent < stacksTotal / 2 ? -22016 : stacksCurrent < stacksTotal / 6 ? -65536 : -16733441, false);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if ((getCurrentSelectedEntity() instanceof EntityLivingBase))
        {
            int modelScale = 25;
            Draw.drawEntity(-40, 100, getCurrentSelectedEntity().height >= 4.0F ? modelScale / 2 : getCurrentSelectedEntity().height >= 8.0F ? modelScale / 4 : modelScale, this.modelRotation += 1F, 0.0F, getCurrentSelectedEntity());
            OpenGL.disableLight();
        }

        for (int x = 0; x < this.entityLivingList.size(); x++)
        {
            int yPos = 56;
            int yEntryPos = yPos + 11 * x;

            Entity entity = x + this.scroll < this.entityLivingList.size() ? (EntityLiving) this.entityLivingList.get(x + this.scroll) : null;

            if (entity != null && yEntryPos <= yPos + 50)
            {
                Draw.drawRectWithOutline(3, yEntryPos - 4, 134, 12, 1, 0x00000000, 0xFF444444);
                Draw.drawString(entity.getCommandSenderName(), 6, yEntryPos - 2, this.tile.canTargetType(entity.getClass()) ? (getCurrentSelectedEntity() == entity ? 0xFFFF8800 : 0xFFFF0000) : (getCurrentSelectedEntity() == entity ? 0xFFFFFFFF : 0xFF444444), false);
            }
        }

        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float p_73863_3_)
    {
        super.drawScreen(mouseX, mouseY, p_73863_3_);

        this.sliderColorA.yPosition = guiTop + 170 + 0;
        this.sliderColorA.xPosition = guiLeft + 3;
        this.sliderColorA.width = 219;
        this.sliderColorA.sliderButtonColor = 0xFFFFFFFF;
        this.sliderColorA.drawButton();

        this.sliderColorR.yPosition = guiTop + 170 + 25;
        this.sliderColorR.sliderButtonColor = 0xFFFF0000;
        this.sliderColorR.xPosition = guiLeft + 3;
        this.sliderColorR.width = 219;
        this.sliderColorR.drawButton();

        this.sliderColorG.yPosition = guiTop + 170 + 50;
        this.sliderColorG.sliderButtonColor = 0xFF00FF00;
        this.sliderColorG.xPosition = guiLeft + 3;
        this.sliderColorG.width = 219;
        this.sliderColorG.drawButton();

        this.sliderColorB.yPosition = guiTop + 170 + 75;
        this.sliderColorB.sliderButtonColor = 0xFF0000FF;
        this.sliderColorB.xPosition = guiLeft + 3;
        this.sliderColorB.width = 219;
        this.sliderColorB.drawButton();

        this.tile.beamColor = Color.createHexadecimal((int) (sliderColorA.sliderValue * sliderColorA.sliderMaxValue), (int) (sliderColorR.sliderValue * sliderColorR.sliderMaxValue), (int) (sliderColorG.sliderValue * sliderColorG.sliderMaxValue), (int) (sliderColorB.sliderValue * sliderColorB.sliderMaxValue));

        this.buttonScrollUp.xPosition = this.guiLeft + xSize + 5;
        this.buttonScrollUp.yPosition = this.guiTop + 42;
        this.buttonScrollUp.displayString = "\u21e7";
        this.buttonScrollUp.baseColor = this.getScroll() == 0 ? 0x22000000 : 0xAA000000;
        this.buttonScrollUp.drawButton();
        this.buttonScrollUp.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                scrollDown();
            }
        });

        this.buttonScrollDown.xPosition = this.guiLeft + this.xSize + 5;
        this.buttonScrollDown.yPosition = this.guiTop + 88;
        this.buttonScrollDown.displayString = "\u21e9";
        this.buttonScrollDown.baseColor = this.getScroll() >= this.entityLivingList.size() - 1 ? 0x22000000 : 0xAA000000;
        this.buttonScrollDown.drawButton();
        this.buttonScrollDown.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                scrollUp();
            }
        });

        this.buttonAddAsTarget.xPosition = (this.guiLeft + this.xSize + 5);
        this.buttonAddAsTarget.yPosition = this.guiTop + 65;
        this.buttonAddAsTarget.width = 20;
        this.buttonAddAsTarget.drawButton();
        this.buttonAddAsTarget.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                if (tile != null)
                {
                    if (!tile.canTargetType(getCurrentSelectedEntity().getClass()))
                    {
                        tile.addTargetType(getCurrentSelectedEntity().getClass());
                        AliensVsPredator.network().sendToServer(new PacketAddTuretTarget(tile.xCoord, tile.yCoord, tile.zCoord, Entities.getEntityRegistrationId(getCurrentSelectedEntity())));
                    }
                    else
                    {
                        tile.removeTargetType(getCurrentSelectedEntity().getClass());
                        AliensVsPredator.network().sendToServer(new PacketRemoveTurretTarget(tile.xCoord, tile.yCoord, tile.zCoord, Entities.getEntityRegistrationId(getCurrentSelectedEntity())));
                    }
                }
            }
        });

        this.buttonSave.xPosition = (this.guiLeft + this.xSize - 38);
        this.buttonSave.yPosition = (this.guiTop + 19);
        this.buttonSave.displayString = "S";
        this.buttonSave.width = 14;
        this.buttonSave.drawButton();
        this.buttonSave.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                AliensVsPredator.network().sendToServer(new PacketWriteToDataDevice(tile.xCoord, tile.yCoord, tile.zCoord, 0));
                tile.writeToOtherDevice(0);
            }
        });

        this.buttonLoad.xPosition = (this.guiLeft + this.xSize - 21);
        this.buttonLoad.yPosition = (this.guiTop + 19);
        this.buttonLoad.displayString = "L";
        this.buttonLoad.width = 14;
        this.buttonLoad.drawButton();
        this.buttonLoad.setAction(new IAction()
        {
            @Override
            public void perform(IGuiElement element)
            {
                tile.getDangerousTargets().clear();
                AliensVsPredator.network().sendToServer(new PacketReadFromDataDevice(tile.xCoord, tile.yCoord, tile.zCoord, 0));
                tile.readFromOtherDevice(0);
            }
        });

        if (!this.tile.canTargetType(getCurrentSelectedEntity().getClass()))
        {
            this.buttonAddAsTarget.displayString = "+";
            this.buttonAddAsTarget.overlayColorHover = 0xFF00FF77;
        }
        else
        {
            this.buttonAddAsTarget.displayString = "-";
            this.buttonAddAsTarget.overlayColorHover = 0xFFFF0033;
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        for (Entity entity : this.entityLivingList)
        {
            entity.onUpdate();
        }

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

    public EntityLiving getCurrentSelectedEntity()
    {
        return this.entityLivingList.get(getScroll());
    }

    public void scrollDown()
    {
        if (this.scroll > 0)
        {
            this.modelRotation = -90.0F;
            this.scroll -= 1;
        }
    }

    public void scrollUp()
    {
        if (this.scroll < this.entityLivingList.size() - 1)
        {
            this.modelRotation = -90.0F;
            this.scroll += 1;
        }
    }

    public int getScroll()
    {
        return this.scroll;
    }
}
