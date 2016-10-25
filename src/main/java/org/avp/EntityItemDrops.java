package org.avp;

import com.arisux.amdxlib.lib.world.entity.ItemDrop;

import net.minecraft.item.ItemStack;

public class EntityItemDrops
{
    public static ItemDrop NBT_DRIVE = new ItemDrop(new ItemStack(AliensVsPredator.items().itemFlashDrive), 50);
    public static ItemDrop PHIAL = new ItemDrop(new ItemStack(AliensVsPredator.items().itemPhial), 50);
    public static ItemDrop PHIAL_EMPTY = new ItemDrop(new ItemStack(AliensVsPredator.items().itemPhialEmpty), 50);
    public static ItemDrop XENO_HELM = new ItemDrop(new ItemStack(AliensVsPredator.items().helmXeno), 5);
    public static ItemDrop XENO_TORSO = new ItemDrop(new ItemStack(AliensVsPredator.items().plateXeno), 5);
    public static ItemDrop XENO_LEGS = new ItemDrop(new ItemStack(AliensVsPredator.items().legsXeno), 5);
    public static ItemDrop XENO_FEET = new ItemDrop(new ItemStack(AliensVsPredator.items().bootsXeno), 5);
}
