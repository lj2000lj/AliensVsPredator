package org.avp.item;

import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import cr0s.warpdrive.api.IAirCanister;
import cr0s.warpdrive.api.IBreathingHelmet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public abstract class ItemAntiVacuumArmor extends ItemArmor implements IBreathingHelmet
{
    public ItemAntiVacuumArmor(ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
    }

    @Override
    public boolean canBreath(Entity entityPlayer)
    {
        if (entityPlayer instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) entityPlayer;

            ItemStack helmStack = Inventories.getHelmSlotItemStack(player);
            ItemStack bodyStack = Inventories.getChestSlotItemStack(player);
            ItemStack legStack = Inventories.getLegsSlotItemStack(player);
            ItemStack bootStack = Inventories.getBootSlotItemStack(player);

            if (bodyStack != null && legStack != null && bootStack != null)
            {
                Item helm = helmStack.getItem();
                Item body = bodyStack.getItem();
                Item legs = legStack.getItem();
                Item boots = bootStack.getItem();

                return helm instanceof IBreathingHelmet && body instanceof IBreathingHelmet && legs instanceof IBreathingHelmet && boots instanceof IBreathingHelmet;
            }
        }

        return true;
    }

    @Override
    public boolean removeAir(Entity player)
    {
        if (player instanceof EntityPlayerMP)
        {
            EntityPlayerMP playerMp = (EntityPlayerMP) player;
            ItemStack[] inventory = playerMp.inventory.mainInventory;

            for (int id = 0; id < inventory.length; id++)
            {
                ItemStack stack = inventory[id];

                if (stack != null && stack.getItem() instanceof IAirCanister)
                {
                    IAirCanister airCanister = (IAirCanister) stack.getItem();

                    if (airCanister.containsAir(stack))
                    {
                        if (stack.stackSize > 1)
                        {
                            stack.stackSize--;
                            ItemStack toAdd = stack.copy();
                            toAdd.stackSize = 1;
                            toAdd.damageItem(1, playerMp);

                            if (stack.getCurrentDurability() >= stack.getMaxDurability())
                            {
                                toAdd = airCanister.emptyDrop(stack);
                            }
                            if (!playerMp.inventory.addItemStackToInventory(toAdd))
                            {
                                EntityItem ie = new EntityItem(playerMp.worldObj, playerMp.posX, playerMp.posY, playerMp.posZ, toAdd);
                                playerMp.worldObj.spawnEntityInWorld(ie);
                            }
                        }
                        else
                        {
                            stack.damageItem(1, playerMp);
                            if (stack.getCurrentDurability() >= stack.getMaxDurability())
                            {
                                inventory[id] = airCanister.emptyDrop(stack);
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int ticksPerCanDamage()
    {
        return 40;
    }
}
