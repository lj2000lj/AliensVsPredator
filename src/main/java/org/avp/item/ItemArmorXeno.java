package org.avp.item;

import org.avp.AliensVsPredator;
import org.avp.entities.extended.ModPlayer;
import org.avp.entities.mob.model.ModelDrone;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.game.Game;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorXeno extends ItemArmor
{
    @SideOnly(Side.CLIENT)
    public ModelDrone mainModel;

    public ItemArmorXeno(ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        switch (slot)
        {
            case 0:
                return Draw.getResourcePath(AliensVsPredator.resources().XENO1);
            case 1:
                return Draw.getResourcePath(AliensVsPredator.resources().XENO1);
            case 2:
                return Draw.getResourcePath(AliensVsPredator.resources().XENO2);
            default:
                return Draw.getResourcePath(AliensVsPredator.resources().XENO1);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        return null;
    }

    public static boolean isPlayerWearingXenoArmorSet(EntityPlayer player)
    {
        if (player != null)
        {
            ItemStack helm = player.inventory.armorItemInSlot(3), chest = player.inventory.armorItemInSlot(2), legs = player.inventory.armorItemInSlot(1), boots = player.inventory.armorItemInSlot(0);
            return (helm != null && chest != null && legs != null && boots != null && (helm.getItem() == AliensVsPredator.items().helmXeno && chest.getItem() == AliensVsPredator.items().plateXeno && legs.getItem() == AliensVsPredator.items().legsXeno && boots.getItem() == AliensVsPredator.items().bootsXeno));
        }

        return false;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if (isPlayerWearingXenoArmorSet(player))
        {
            ModPlayer specialPlayer = ModPlayer.get(player);

            if (world.isRemote)
            {
                this.controlledAbility(specialPlayer);
            }

            player.fallDistance = 0.0F;

            if (specialPlayer.canClimb() && player.isCollidedHorizontally)
            {
                player.motionY += 0.03F;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void controlledAbility(ModPlayer specialPlayer)
    {
        boolean canClimbPrev = specialPlayer.canClimb();

        if (Game.minecraft().gameSettings.keyBindJump.isPressed())
        {
            specialPlayer.setCanClimb(true);
        }
        else if (!Game.minecraft().gameSettings.keyBindJump.getIsKeyPressed())
        {
            specialPlayer.setCanClimb(false);
        }

        if (canClimbPrev != specialPlayer.canClimb())
        {
            specialPlayer.syncWithServer();
        }
    }
}
