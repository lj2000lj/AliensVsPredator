package org.avp.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPlasmaCannon extends Item
{
//    private EntityPlasma plasma;
    
    public ItemPlasmaCannon()
    {
        super();
        this.maxStackSize = 1;
        this.setMaxDurability(50);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
//        if (world.isRemote)
//        {
//            if (plasma != null)
//            {
//                plasma.increaseSize();
//            }
//        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
//        EntityPlasma plasma = new EntityPlasma(world);
//
//        if (plasma != null)
//        {
//            float speed = 1.5F * plasma.getPlasmaSize();
//            plasma.setLocationAndAngles(player.posX, player.posY + player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
//            plasma.motionX = -MathHelper.sin(plasma.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(plasma.rotationPitch / 180.0F * (float) Math.PI) * speed;
//            plasma.motionZ = MathHelper.cos(plasma.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(plasma.rotationPitch / 180.0F * (float) Math.PI) * speed;
//            plasma.motionY = -MathHelper.sin((plasma.rotationPitch) / 180.0F * (float) Math.PI) * speed;
//            world.spawnEntityInWorld(plasma);
//            Sounds.SOUND_WEAPON_PLASMACASTER.playSound(player);
//        }

        return super.onItemRightClick(stack, world, player);
    }
}
