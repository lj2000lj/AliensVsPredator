package org.avp;

import com.arisux.amdxlib.lib.world.entity.ItemDrop;
import com.arisux.amdxlib.lib.world.entity.ItemDrop.DropType;

import net.minecraft.item.ItemStack;

public class EntityItemDrops
{
    private static final ItemHandler items = AliensVsPredator.items();

    public static ItemDrop ROYAL_JELLY_GENERIC = new ItemDrop(100, new ItemStack(items.itemRoyalJelly, 4));
    public static ItemDrop ROYAL_JELLY_SINGLE = new ItemDrop(100, new ItemStack(items.itemRoyalJelly, 1));
    public static ItemDrop NBT_DRIVE = new ItemDrop(50, new ItemStack(items.itemFlashDrive));
    public static ItemDrop PHIAL = new ItemDrop(50, new ItemStack(items.itemPhial));
    public static ItemDrop PHIAL_EMPTY = new ItemDrop(50, new ItemStack(items.itemPhialEmpty));
    public static ItemDrop XENO_HELM = new ItemDrop(5, new ItemStack(items.helmXeno));
    public static ItemDrop XENO_TORSO = new ItemDrop(5, new ItemStack(items.plateXeno));
    public static ItemDrop XENO_LEGS = new ItemDrop(5, new ItemStack(items.legsXeno));
    public static ItemDrop XENO_FEET = new ItemDrop(5, new ItemStack(items.bootsXeno));
    public static ItemDrop SKULL_ENGINEER = new ItemDrop(1, new ItemStack(AliensVsPredator.blocks().blockSkullEngineer));
    public static ItemDrop SKULL_SPACEJOCKEY = new ItemDrop(1, new ItemStack(AliensVsPredator.blocks().blockSkullSpaceJockey));
    public static ItemDrop SKULL_XENO_DRONE = new ItemDrop(1, new ItemStack(AliensVsPredator.blocks().blockSkullXenomorph));
    public static ItemDrop SKULL_XENO_WARRIOR = new ItemDrop(1, new ItemStack(AliensVsPredator.blocks().blockSkullXenomorphWarrior));
    public static ItemDrop SKULL_PREDATOR = new ItemDrop(1, new ItemStack(AliensVsPredator.blocks().blockSkullYautja));
    public static ItemDrop PREDATOR_ARTIFACT = new ItemDrop(50, new ItemStack(items.itemArtifactTech));
    public static ItemDrop AMMUNITION = new ItemDrop(75, DropType.RATE_PERSTACK_MULTIPLE, new ItemStack(items.itemAmmoAC), new ItemStack(items.itemAmmoAR), new ItemStack(items.itemAmmoPistol), new ItemStack(items.itemAmmoSMG), new ItemStack(items.itemAmmoSniper));
    public static ItemDrop FIREARMS = new ItemDrop(15, DropType.RATE_PERSTACK_SINGLE, new ItemStack(items.itemM56SG), new ItemStack(items.itemPistol), new ItemStack(items.itemM4), new ItemStack(items.itemM41A), new ItemStack(items.itemSniper));
//    public static ItemDrop  = new ItemDrop(new ItemStack(items.), 1);
}
