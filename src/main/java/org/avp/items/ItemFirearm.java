package org.avp.items;

import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.packets.server.PacketFirearmSync;
import org.avp.packets.server.PacketReloadFirearm;

import com.arisux.mdxlib.lib.client.Sound;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.entity.Entities;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;
import com.arisux.mdxlib.lib.world.item.HookedItem;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

@SuppressWarnings("unchecked")
public class ItemFirearm extends HookedItem
{
    private ItemAmmo ammoType;
    private float    recoil;
    private double   fireRate;
    private int      maxAmmoCount;
    private int      ammoCount;
    private int      reloadRate;
    private int      reload;
    private int      ammoConsumptionRate;
    private Sound    sound;
    private double   soundLength;
    private long     lastSoundPlayed;
    private float    breakProgress;
    private int    breakingIndex;

    public ItemFirearm(int maxAmmoCount, float recoil, double fireRate, int reloadRate, ItemAmmo item, Sound sound)
    {
        super();
        this.setMaxStackSize(1);
        this.ammoType = item;
        this.maxAmmoCount = maxAmmoCount;
        this.ammoCount = 0;
        this.recoil = recoil;
        this.fireRate = fireRate;
        this.reloadRate = reloadRate;
        this.reload = 0;
        this.sound = sound;
        this.soundLength = 0;
        this.ammoConsumptionRate = 1;
        this.lastSoundPlayed = 0;
        this.soundLength = 0;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected)
    {
        super.onUpdate(itemstack, world, entity, slot, selected);

        this.reload--;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (world.isRemote)
        {
            if (this.ammoCount > 0 || player.capabilities.isCreativeMode)
            {
                MovingObjectPosition trace = Entities.rayTraceSpecial(128, 1.0F);

                this.renderRecoil();
                this.fixDelay();

                if (trace != null && trace.typeOfHit == MovingObjectType.BLOCK)
                {
                    AliensVsPredator.network().sendToServer(new PacketFirearmSync(trace.typeOfHit, trace.entityHit, trace.blockX, trace.blockY, trace.blockZ, this.getAmmoType().getInflictionDamage(), this.getFireSound().getKey()));
                }

                if (trace != null && trace.typeOfHit == MovingObjectType.ENTITY)
                {
                    AliensVsPredator.network().sendToServer(new PacketFirearmSync(trace.typeOfHit, trace.entityHit, 0, 0, 0, this.getAmmoType().getInflictionDamage(), this.getFireSound().getKey()));
                }

                if (!player.capabilities.isCreativeMode)
                {
                    this.ammoCount -= ammoConsumptionRate;
                }
            }
            else if (player.inventory.hasItem(this.ammoType))
            {
                this.reload(player);
            }
        }

        return itemstack;
    }

    public boolean canFire(EntityPlayer player)
    {
        return true;
    }

    public void reload(EntityPlayer player)
    {
        if (!player.worldObj.isRemote)
        {
            Inventories.consumeItem(player, this.ammoType);
        }

        if (player.worldObj.isRemote)
        {
            AliensVsPredator.network().sendToServer(new PacketReloadFirearm());
        }

        this.ammoCount = this.maxAmmoCount;
    }

    public ItemAmmo getAmmoType()
    {
        return ammoType;
    }

    public int getReload()
    {
        return reload;
    }

    public double getFireRate()
    {
        return this.fireRate;
    }

    public int getReloadRate()
    {
        return this.reloadRate;
    }

    public int getAmmoCount()
    {
        return this.ammoCount;
    }

    public int getMaxAmmoCount()
    {
        return this.maxAmmoCount;
    }

    public Sound getFireSound()
    {
        return this.sound;
    }

    public double getSoundLength()
    {
        return this.soundLength;
    }

    public ItemFirearm setSoundLength(double seconds)
    {
        this.soundLength = seconds;
        return this;
    }

    public long getLastSoundPlayTime()
    {
        return this.lastSoundPlayed;
    }

    public void setLastSoundPlayed(long lastSoundPlayed)
    {
        this.lastSoundPlayed = lastSoundPlayed;
    }

    public boolean canSoundPlay()
    {
        long major = System.currentTimeMillis() / 1000 - this.getLastSoundPlayTime() / 1000;
        long minor = Math.abs((System.currentTimeMillis() - this.getLastSoundPlayTime()) - (major * 1000));
        double time = Double.valueOf(String.format("%s.%s", major, minor));
        return this.getLastSoundPlayTime() == 0 ? true : (time >= this.getSoundLength());
    }

    public void setAmmoCount(int ammoCount)
    {
        this.ammoCount = ammoCount;
    }

    public ItemFirearm setAmmoConsumptionRate(int rate)
    {
        this.ammoConsumptionRate = rate;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void renderRecoil()
    {
        Minecraft client = FMLClientHandler.instance().getClient();
        client.thePlayer.renderArmPitch -= this.recoil * 40.0F;
        client.thePlayer.renderArmYaw += this.recoil * 5.0F;
        client.thePlayer.rotationPitch -= this.recoil * 1.4F;
    }

    @SideOnly(Side.CLIENT)
    public void fixDelay()
    {
        Game.setEquippedProgress(0.85F);
        Game.setRightClickDelayTimer((int) this.fireRate);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int posX, int posY, int posZ, EntityPlayer player)
    {
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add("Left click to aim, Right click to fire");
        par3List.add("Press R to reload");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    public float getBreakProgress()
    {
        return breakProgress;
    }

    public int getBlockBreakingIndex()
    {
        return breakingIndex;
    }

    public void setBlockBreakingIndex(int breakingIndex)
    {
        this.breakingIndex = breakingIndex;
    }

    public void setBreakProgress(float breakProgress)
    {
        this.breakProgress = breakProgress;
    }

    public static class ItemAmmo extends HookedItem
    {
        private float damage;

        public ItemAmmo(float damage)
        {
            super();
            this.damage = damage;
        }

        public float getInflictionDamage()
        {
            return damage;
        }
    }
}
