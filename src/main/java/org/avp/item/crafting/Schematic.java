package org.avp.item.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class Schematic
{
    private String      id;
    private ItemStack   item;
    private ItemStack[] items;

    public Schematic(String id, ItemStack item, ItemStack... items)
    {
        this.id = id;
        this.item = item;
        this.items = items;
    }

    public String getId()
    {
        return id;
    }

    public ItemStack getItemStackAssembled()
    {
        return item;
    }

    public ItemStack[] getItemsRequired()
    {
        return items;
    }

    public boolean isComplete(EntityPlayer player)
    {
        int progress = 0;
        int maxProgress = 0;

        for (ItemStack stack : this.getItemsRequired())
        {
            int amountOfStack = AssemblyManager.amountForMatchingStack(player, stack);
            maxProgress += stack.stackSize;

            if (amountOfStack > 0)
            {
                progress += amountOfStack > stack.stackSize ? stack.stackSize : amountOfStack;
            }
        }

        return progress == maxProgress;
    }
}