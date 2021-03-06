package org.avp.item;

import java.util.ArrayList;
import java.util.HashMap;

import org.avp.AliensVsPredator;
import org.avp.DamageSources;
import org.avp.client.Sounds;
import org.avp.inventory.ContainerWristbracer;
import org.avp.packets.server.PacketSpawnNuke;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.item.HookedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ItemWristbracer extends HookedItem
{
    public static final String TAG_WRISTBRACER_ITEMS = "WristbracerItems";
    public static final String TAG_WRISTBRACER_ITEMS_SLOT = "Slot";

    private static final HashMap<String, IActionCode> codes = new HashMap<String, IActionCode>();

    public static interface IActionCode
    {
        public void onAction(String combonation, Object... args);
    }

    public static String code(int d1, int d2, int d3, int d4, int d5, int d6)
    {
        return String.format("%s%s%s%s%s%s", d1, d2, d3, d4, d5, d6);
    }

    public static IActionCode getAction(String combonation)
    {
        return codes.get(combonation);
    }

    public static boolean isCodeValid(String combonation)
    {
        return codes.get(combonation) != null;
    }

    public static void addCode(String combonation, IActionCode action)
    {
        if (!isCodeValid(combonation))
        {
            codes.put(combonation, action);
        }
    }
    
    static
    {
        addCode("009001", new IActionCode()
        {
            @Override
            public void onAction(String combonation, Object... args)
            {
                AliensVsPredator.network().sendToServer(new PacketSpawnNuke());
                Game.minecraft().currentScreen = null;
            }
        });
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (equippedHasBlades(player))
        {
            Sounds.SOUND_WEAPON_WRISTBLADES.playSound(entity, 1.0F, 1.0F );
            entity.attackEntityFrom(DamageSources.causeWristbracerDamage(player), getDamageToApply());

            if (!player.worldObj.isRemote && !player.capabilities.isCreativeMode)
            {
                ItemStack bladesStack = getBlades(player.getCurrentEquippedItem());
                NBTTagCompound nbt = player.getCurrentEquippedItem().getTagCompound();
                NBTTagList wristbracerContents = nbt.getTagList(TAG_WRISTBRACER_ITEMS, Constants.NBT.TAG_COMPOUND);

                if (bladesStack != null && !player.worldObj.isRemote)
                {
                    for (int s = 0; s < wristbracerContents.tagCount(); s++)
                    {
                        NBTTagCompound slot = wristbracerContents.getCompoundTagAt(s);
                        ItemStack slotstack = ItemStack.loadItemStackFromNBT(slot);

                        if (slotstack != null && slotstack.getItem() == AliensVsPredator.items().itemWristbracerBlades)
                        {
                            wristbracerContents.removeTag(s);
                            bladesStack.writeToNBT(slot);
                            slot.setShort("Damage", (short) (bladesStack.getCurrentDurability() + 1));
                            slot.setByte(TAG_WRISTBRACER_ITEMS_SLOT, (byte) s);
                            wristbracerContents.appendTag(slot);
                            break;
                        }
                    }
                }

                nbt.setTag(TAG_WRISTBRACER_ITEMS, wristbracerContents);
                player.getCurrentEquippedItem().setTagCompound(nbt);
                ((ContainerWristbracer) getNewContainer(player)).saveToNBT();
            }
        }

        return super.onLeftClickEntity(stack, player, entity);
    }

    public Container getNewContainer(EntityPlayer player)
    {
        return new ContainerWristbracer(player);
    }

    public static float getDamageToApply()
    {
        return AliensVsPredator.materials().tools().celtic.getDamageVsEntity() * 1.5F;
    }

    public static ItemStack getBlades(ItemStack wristbracer)
    {
        return get(wristbracer, AliensVsPredator.items().itemWristbracerBlades);
    }

    public static ItemStack getPlasmaCannon(ItemStack wristbracer)
    {
        return get(wristbracer, AliensVsPredator.items().itemPlasmaCannon);
    }

    public static ItemStack get(ItemStack wristbracer, Item item)
    {
        if (wristbracer != null && wristbracer.getTagCompound() != null)
        {
            NBTTagList contents = wristbracer.getTagCompound().getTagList(TAG_WRISTBRACER_ITEMS, Constants.NBT.TAG_COMPOUND);

            if (contents != null)
            {
                for (byte x = 0; x < contents.tagCount(); x++)
                {
                    NBTTagCompound itemTag = contents.getCompoundTagAt(x);
                    ItemStack stack = ItemStack.loadItemStackFromNBT(itemTag);

                    if (stack != null && stack.getItem() == item)
                    {
                        return stack;
                    }
                }
            }
        }

        return null;
    }

    public static boolean equippedHasBlades(EntityPlayer player)
    {
        return hasBlades(currentWristbracer(player));
    }
    
    public static boolean hasBlades(ItemStack wristbracer)
    {
        return getBlades(wristbracer) != null;
    }
    
    public static boolean hasPlasmaCannon(ItemStack wristbracer)
    {
        return getPlasmaCannon(wristbracer) != null;
    }
    
    public static boolean hasWristbracer(EntityPlayer player)
    {
        return wristbracer(player) != null;
    }

    public static ItemStack currentWristbracer(EntityPlayer player)
    {
        if (player.getCurrentEquippedItem() != null)
        {
            if (player.getCurrentEquippedItem().getItem() instanceof ItemWristbracer)
            {
                return player.getCurrentEquippedItem();
            }
        }

        return null;
    }
    
    public static ItemStack wristbracer(EntityPlayer player)
    {
        ArrayList<ItemStack> stacks = wristbracers(player);
        return stacks.size() > 0 ? stacks.get(0) : null;
    }
    
    public static ArrayList<ItemStack> wristbracers(EntityPlayer player)
    {
        ArrayList<ItemStack> wristbracers = new ArrayList<ItemStack>();
        
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (stack != null && stack.getItem() instanceof ItemWristbracer)
            {
                wristbracers.add(stack);
            }
        }
        
        return wristbracers;
    }
}
