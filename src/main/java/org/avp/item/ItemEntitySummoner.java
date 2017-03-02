package org.avp.item;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketSpawnEntity;

import com.arisux.mdxlib.lib.world.entity.Entities;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;
import com.arisux.mdxlib.lib.world.item.HookedItem;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
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
            MovingObjectPosition ray = Entities.rayTraceSpecial(50F, 1F);

            if (ray != null)
            {
                if (ray.typeOfHit == MovingObjectType.ENTITY && ray.entityHit != null)
                {
                    AliensVsPredator.network().sendToServer(new PacketSpawnEntity(ray.entityHit.posX, ray.entityHit.posY + 1D, ray.entityHit.posZ, Entities.getEntityRegistrationId(c)));
                }
                else
                {
                    AliensVsPredator.network().sendToServer(new PacketSpawnEntity(ray.blockX + 0.5, ray.blockY + 1D, ray.blockZ + 0.5, Entities.getEntityRegistrationId(c)));
                }
            }
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
