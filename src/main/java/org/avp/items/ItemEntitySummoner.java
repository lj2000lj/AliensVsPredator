package org.avp.items;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketSpawnEntity;

import com.arisux.amdxlib.lib.world.entity.Entities;
import com.arisux.amdxlib.lib.world.entity.player.inventory.Inventories;
import com.arisux.amdxlib.lib.world.item.HookedItem;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEntitySummoner extends HookedItem
{
    private Class<? extends Entity> c;

    public ItemEntitySummoner(Class<? extends Entity> c)
    {
        super();
        this.c = c;
        this.setDescription("Summoner for " + c.getSimpleName().replace("Entity", ""));
        this.setUnlocalizedName(AliensVsPredator.properties().DOMAIN + "summon." + c.getSimpleName());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        Entity entity = createNewEntity(par2World);
        Inventories.consumeItem(par3EntityPlayer, this);

        if (par2World.isRemote && entity != null)
        {
            MovingObjectPosition ray = par3EntityPlayer.rayTrace(50D, 1F);
            AliensVsPredator.network().sendToServer(new PacketSpawnEntity(ray.blockX, ray.blockY, ray.blockZ, EntityList.getEntityID(entity)));
        }

        return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }

    public Class<? extends Entity> getEntityClass()
    {
        return c;
    }

    public Entity createNewEntity(World worldObj)
    {
        return Entities.constructEntity(worldObj, c);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        ;
    }
}
