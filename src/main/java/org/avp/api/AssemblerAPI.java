package org.avp.api;

import java.util.ArrayList;

import com.arisux.amdxlib.AMDXLib;
import com.arisux.amdxlib.lib.game.IInitEvent;
import com.arisux.amdxlib.lib.world.entity.player.inventory.Inventories;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AssemblerAPI implements IInitEvent
{
    public static final AssemblerAPI      instance             = new AssemblerAPI();
    private ArrayList<AssemblerSchematic> registeredSchematics = new ArrayList<AssemblerSchematic>();

    public static class AssemblerSchematic
    {
        private String      schematicId;
        private ItemStack   item;
        private ItemStack[] items;

        public AssemblerSchematic(String schematicId, ItemStack item, ItemStack... items)
        {
            this.schematicId = schematicId;
            this.item = item;
            this.items = items;
        }

        public String getSchematicId()
        {
            return schematicId;
        }

        public ItemStack getItemStackAssembled()
        {
            return item;
        }

        public ItemStack[] getItemsRequired()
        {
            return items;
        }
    }

    public ArrayList<AssemblerSchematic> getSchematicRegistry()
    {
        return this.registeredSchematics;
    }

    public ArrayList<AssemblerSchematic> getSchematicsForItem(Item item)
    {
        ArrayList<AssemblerSchematic> schematics = new ArrayList<AssemblerSchematic>();

        for (AssemblerSchematic schematic : this.getSchematicRegistry())
        {
            if (schematic.getItemStackAssembled() != null && schematic.getItemStackAssembled().getItem() == item)
            {
                schematics.add(schematic);
            }
        }

        return schematics;
    }

    public AssemblerSchematic getSchematicForId(String id)
    {
        for (AssemblerSchematic schematic : this.getSchematicRegistry())
        {
            if (schematic.getSchematicId().equalsIgnoreCase(id))
            {
                return schematic;
            }
        }

        return null;
    }

    public boolean isSchematicValid(AssemblerSchematic schematic)
    {
        for (AssemblerSchematic sch : this.registeredSchematics)
        {
            if (schematic == null || sch != null && schematic != null && sch.getSchematicId().equalsIgnoreCase(schematic.getSchematicId()))
            {
                return false;
            }
        }

        return true;
    }

    public void registerSchematic(AssemblerSchematic schematic)
    {
        if (isSchematicValid(schematic))
        {
            this.registeredSchematics.add(schematic);
        }
        else
        {
            AMDXLib.log().warn(String.format("[AVP/API/Assembler] Schematic for id '%s' is already registered.", schematic.getSchematicId()));
        }
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        // Schematic registration has been moved to CraftingHandler.addSchematics();
    }

    public boolean isSchematicComplete(AssemblerSchematic schematic, EntityPlayer player)
    {
        int progress = 0;
        int maxProgress = 0;

        for (ItemStack stack : schematic.getItemsRequired())
        {
            int amountOfStack = Inventories.getAmountOfItemPlayerHas(stack.getItem(), player);
            maxProgress += stack.stackSize;

            if (amountOfStack > 0)
            {
                progress += amountOfStack > stack.stackSize ? stack.stackSize : amountOfStack;
            }
        }

        return progress == maxProgress;
    }

    public boolean assembleSchematicAsPlayer(AssemblerSchematic schematic, EntityPlayer player)
    {
        if (schematic != null && isSchematicComplete(schematic, player))
        {
            for (ItemStack requirement : schematic.getItemsRequired())
            {
                int[] matchingIDs = OreDictionary.getOreIDs(requirement);
                boolean checkOreDictionary = matchingIDs.length > 0;

                if (checkOreDictionary)
                {
                    int id = matchingIDs[0];
                    String sharedName = OreDictionary.getOreName(id);

                    for (ItemStack potentialMatch : OreDictionary.getOres(sharedName))
                    {
                        if (Inventories.getAmountOfItemPlayerHas(potentialMatch.getItem(), player) >= requirement.stackSize || player.capabilities.isCreativeMode)
                        {
                            for (int x = 0; x < requirement.stackSize; x++)
                            {
                                if (!Inventories.consumeItem(player, requirement.getItem()))
                                {
                                    return false;
                                }
                            }

                            break;
                        }
                    }
                }
                else
                {
                    if (Inventories.getAmountOfItemPlayerHas(requirement.getItem(), player) >= requirement.stackSize || player.capabilities.isCreativeMode)
                    {
                        for (int x = 0; x < requirement.stackSize; x++)
                        {
                            if (!Inventories.consumeItem(player, requirement.getItem()))
                            {
                                return false;
                            }
                        }

                        break;
                    }
                }
            }

            if (player.inventory.addItemStackToInventory(schematic.getItemStackAssembled().copy()))
            {
                return true;
            }
            else
            {
                new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, schematic.getItemStackAssembled().copy());
                return true;
            }
        }

        return false;
    }
}
