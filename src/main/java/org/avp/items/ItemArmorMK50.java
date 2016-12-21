package org.avp.items;

import org.avp.AliensVsPredator;
import org.avp.ItemHandler;

import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import cr0s.warpdrive.api.IBreathingHelmet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmorMK50 extends ItemArmor implements IBreathingHelmet
{
    public ItemArmorMK50(ArmorMaterial material, int renderIndex, int armorType)
    {
        super(material, renderIndex, armorType);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
    {
        switch (slot)
        {
            case 0:
                return Draw.getResourcePath(AliensVsPredator.resources().MK501);
            case 1:
                return Draw.getResourcePath(AliensVsPredator.resources().MK501);
            case 2:
                return Draw.getResourcePath(AliensVsPredator.resources().MK502);
            case 3:
                return Draw.getResourcePath(AliensVsPredator.resources().MK502);
            default:
                return Draw.getResourcePath(AliensVsPredator.resources().MK502);
        }
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        ;
    }

    @Override
    public boolean canBreath(Entity entityPlayer)
    {
        ItemHandler items = AliensVsPredator.items();

        if (entityPlayer instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entityPlayer;
            ItemStack helmStack = Inventories.getHelmSlotItemStack(player);
            ItemStack bodyStack = Inventories.getHelmSlotItemStack(player);
            ItemStack legStack = Inventories.getHelmSlotItemStack(player);
            ItemStack bootStack = Inventories.getHelmSlotItemStack(player);

            if (helmStack != null && bodyStack != null && legStack != null && bootStack != null)
            {
                Item helm = helmStack.getItem();
                Item body = helmStack.getItem();
                Item legs = helmStack.getItem();
                Item boots = helmStack.getItem();
                
                return helm == items.mk50helmet && body == items.mk50body && legs == items.mk50pants && boots == items.mk50boots;
            }
        }

        return false;
    }

    @Override
    public boolean removeAir(Entity player)
    {
        return true;
        
//        if (player instanceof EntityPlayerMP)
//        {
//            EntityPlayerMP playerMp = (EntityPlayerMP) player;
//            ItemStack[] inventory = playerMp.inventory.mainInventory;
//
//            for (int id = 0; id < inventory.length; id++)
//            {
//                ItemStack stack = inventory[id];
//
//                if (stack != null && stack.getItem() instanceof IAirCanister)
//                {
//                    IAirCanister airCanister = (IAirCanister) stack.getItem();
//
//                    if (airCanister.containsAir(stack))
//                    {
//                        if (stack.stackSize > 1)
//                        {
//                            stack.stackSize--;
//                            ItemStack toAdd = stack.copy();
//                            toAdd.stackSize = 1;
//                            toAdd.damageItem(1, playerMp);
//
//                            if (stack.getCurrentDurability() >= stack.getMaxDurability())
//                            {
//                                toAdd = airCanister.emptyDrop(stack);
//                            }
//                            if (!playerMp.inventory.addItemStackToInventory(toAdd))
//                            {
//                                EntityItem ie = new EntityItem(playerMp.worldObj, playerMp.posX, playerMp.posY, playerMp.posZ, toAdd);
//                                playerMp.worldObj.spawnEntityInWorld(ie);
//                            }
//                        }
//                        else
//                        {
//                            stack.damageItem(1, playerMp);
//                            if (stack.getCurrentDurability() >= stack.getMaxDurability())
//                            {
//                                inventory[id] = airCanister.emptyDrop(stack);
//                            }
//                        }
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
    }

    @Override
    public int ticksPerCanDamage()
    {
        return 40;
    }
}
