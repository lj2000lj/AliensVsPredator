package org.avp.items;

import com.arisux.amdxlib.lib.world.CoordData;
import com.arisux.amdxlib.lib.world.Worlds;
import com.arisux.amdxlib.lib.world.entity.player.inventory.Inventories;
import com.arisux.amdxlib.lib.world.item.HookedItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemIngotLithium extends HookedItem
{
	public boolean isDepleting = true;
	public int depletionTicks = 20 * 60;

	public ItemIngotLithium()
	{
		this.setMaxDurability(20 * 60);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World worldObj, Entity entity, int slot, boolean selected)
	{
		super.onUpdate(itemstack, worldObj, entity, slot, selected);

		this.setDamage(itemstack, itemstack.getCurrentDurability() + 1);

		if (itemstack.getCurrentDurability() >= itemstack.getMaxDurability())
		{
			Worlds.createExplosion(entity, worldObj, new CoordData(entity), 1F, true, true, true);
			Inventories.consumeItem((EntityPlayer) entity, this, true);
		}
	}
}
