package org.avp.items;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketSpawnEntity;

import com.arisux.amdxlib.lib.world.entity.Entities;
import com.arisux.amdxlib.lib.world.entity.player.inventory.Inventories;
import com.arisux.amdxlib.lib.world.item.HookedItem;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
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
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        Entity entity = createNewEntity(world);
        Inventories.consumeItem(player, this);

        if (world.isRemote && entity != null)
        {
            MovingObjectPosition ray = player.rayTrace(50D, 1F);
            EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(c, true);
            
            AliensVsPredator.network().sendToServer(new PacketSpawnEntity(ray.blockX, ray.blockY, ray.blockZ, registration.getModEntityId()));
        }

        return super.onItemRightClick(stack, world, player);
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
