package org.avp.entities.tile;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.DamageSources;
import org.avp.Sounds;
import org.avp.entities.EntityPlasma;
import org.avp.entities.mob.EntityAethon;
import org.avp.entities.mob.EntityAqua;
import org.avp.entities.mob.EntityBelugaburster;
import org.avp.entities.mob.EntityBelugamorph;
import org.avp.entities.mob.EntityChestburster;
import org.avp.entities.mob.EntityCrusher;
import org.avp.entities.mob.EntityDeacon;
import org.avp.entities.mob.EntityDeaconShark;
import org.avp.entities.mob.EntityDrone;
import org.avp.entities.mob.EntityEngineer;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.EntityGooMutant;
import org.avp.entities.mob.EntityHammerpede;
import org.avp.entities.mob.EntityOctohugger;
import org.avp.entities.mob.EntityOvamorph;
import org.avp.entities.mob.EntityPraetorian;
import org.avp.entities.mob.EntityPredalien;
import org.avp.entities.mob.EntityPredalienChestburster;
import org.avp.entities.mob.EntityQueen;
import org.avp.entities.mob.EntityQueenChestburster;
import org.avp.entities.mob.EntityRoyalFacehugger;
import org.avp.entities.mob.EntityRunnerChestburster;
import org.avp.entities.mob.EntityRunnerDrone;
import org.avp.entities.mob.EntityRunnerWarrior;
import org.avp.entities.mob.EntitySpaceJockey;
import org.avp.entities.mob.EntitySpitter;
import org.avp.entities.mob.EntityTrilobite;
import org.avp.entities.mob.EntityUltramorph;
import org.avp.entities.mob.EntityWarrior;
import org.avp.inventory.ContainerTurret;
import org.avp.packets.client.PacketTurretSync;
import org.avp.packets.server.PacketTurretTargetUpdate;
import org.avp.util.IDataDevice;
import org.avp.util.IVoltageReceiver;

import com.arisux.mdxlib.lib.client.render.Rotation;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.util.MDXMath;
import com.arisux.mdxlib.lib.world.Pos;
import com.arisux.mdxlib.lib.world.entity.Entities;
import com.arisux.mdxlib.lib.world.storage.NBTStorage;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTurret extends TileEntityElectrical implements IDataDevice, IVoltageReceiver
{
    private boolean                            ammoDisplayEnabled;
    private boolean                            isFiring;
    private int                                fireRate;
    private int                                range;
    private int                                cycleCount;
    private int                                curAmmo;
    private int                                rounds;
    private int                                roundsMax;
    private int                                direction;
    private int                                timeout;
    private int                                timeoutMax;
    private ArrayList<Class<? extends Entity>> targetTypes;
    public InventoryBasic                      inventoryAmmo;
    public InventoryBasic                      inventoryExpansion;
    public InventoryBasic                      inventoryDrive;
    private Entity                             targetEntity;
    private ContainerTurret                    container;
    private Pos                                pos;
    private Rotation                           rot;
    private Pos                                foc;
    private Rotation                           focrot;
    private Item                               itemAmmo;
    public int                                 beamColor;

    public TileEntityTurret()
    {
        super(false);
        this.targetTypes = new ArrayList<Class<? extends Entity>>();
        this.inventoryAmmo = new InventoryBasic("TurretAmmoBay", true, 9);
        this.inventoryExpansion = new InventoryBasic("TurretExpansionBay", true, 3);
        this.inventoryDrive = new InventoryBasic("TurretDriveBay", true, 1);
        this.fireRate = 2;
        this.range = 24;
        this.cycleCount = getBaseCycleCount();
        this.curAmmo = 0;
        this.rot = new Rotation(0F, 0F);
        this.focrot = new Rotation(0F, 0F);
        this.ammoDisplayEnabled = false;
        this.timeoutMax = 60;
        this.itemAmmo = AliensVsPredator.items().itemAmmoSMG;
        this.beamColor = 0xFFFF0000;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        super.updateEnergyAsReceiver();

        // System.out.println(this.getRotation().yaw);

        if (this.pos == null)
        {
            this.pos = new Pos(this.xCoord, this.yCoord, this.zCoord);
        }
        else
        {
            this.pos.x = this.xCoord;
            this.pos.y = this.yCoord;
            this.pos.z = this.zCoord;
        }

        this.isFiring = false;

        if (this.getVoltage() > 0)
        {
            this.timeout = this.timeout > 0 ? this.timeout - 1 : this.timeout;
            this.pickUpAmmunition();
            this.updateAmmunitionCount();
            this.reloadIfNecessary();
            this.targetAndAttack();
        }
    }

    // public Entity findTarget()
    // {
    // Entity newTarget = Entities.getRandomEntityInCoordsRange(this.worldObj, EntityLiving.class, this.pos, range, range);
    //
    // if (this.canTarget(newTarget) && canSee(newTarget))
    // {
    // System.out.println(newTarget);
    // this.targetEntity = newTarget;
    //
    // if (this.worldObj.isRemote)
    // {
    // AliensVsPredator.network().sendToAll(new PacketTurretTargetUpdate(this));
    // }
    //
    // return newTarget;
    // }
    //
    // return null;
    // }

    public boolean canTarget(Entity e)
    {
        if (e != null)
        {
            double distance = Pos.distance(this.xCoord, this.yCoord, this.zCoord, e.posX, e.posY, e.posZ);
            return !e.isDead && this.canTargetType(e.getClass()) && distance <= this.range;
        }

        return false;
    }

    private boolean canSee(Entity e)
    {
        double height = e.boundingBox.maxY - e.boundingBox.minY;
        double halfHeight = height / 2;

        Pos middlePos = new Pos(e.posX, e.boundingBox.maxY - (halfHeight), e.posZ);
        Pos topPos = new Pos(e.posX, e.boundingBox.maxY - (halfHeight + halfHeight), e.posZ);
        Pos botPos = new Pos(e.posX, e.boundingBox.maxY - (halfHeight - halfHeight), e.posZ);
        Pos newPos = this.pos.add(0.5, 1, 0.5);
        MovingObjectPosition middle = Entities.rayTraceBlocks(this.worldObj, middlePos, newPos, false, true, false);
        MovingObjectPosition top = Entities.rayTraceBlocks(this.worldObj, topPos, newPos, false, true, false);
        MovingObjectPosition bot = Entities.rayTraceBlocks(this.worldObj, botPos, newPos, false, true, false);

        if (middle == null || top == null || bot == null)
        {
            return true;
        }

        return false;
    }

    private void updatePosition(double x, double y, double z)
    {
        if (this.foc == null)
        {
            this.foc = new Pos(x, y, z);
        }
        else
        {
            this.foc.x = x;
            this.foc.y = y;
            this.foc.z = z;
        }
    }

    public void targetAndAttack()
    {
        if (this.targetEntity != null)
        {
            if (this.targetEntity.isDead)
            {
                this.targetEntity = null;
            }
        }

        if (!this.worldObj.isRemote && this.worldObj.getWorldTime() % 10 == 0)
        {
            if (this.getTargetEntity() == null || this.getTargetEntity() != null && !this.canTarget(targetEntity))
            {
                for (Class<? extends Entity> type : this.targetTypes)
                {
                    Entity newTarget = Entities.getRandomEntityInCoordsRange(this.worldObj, type, this.pos, range, range);

                    if (this.canTarget(newTarget) && canSee(newTarget))
                    {
                        this.targetEntity = newTarget;
                        break;
                    }
                }
            }
        }

        this.lookAtFocusPoint();

        if (targetEntity != null)
        {
            this.updatePosition(targetEntity.posX, targetEntity.posY, targetEntity.posZ);
            this.focrot = turnTurretToPoint(this.foc, this.focrot, 360F, 90F);

            if (!this.worldObj.isRemote)
            {
                AliensVsPredator.network().sendToAll(new PacketTurretTargetUpdate(this));

                if (worldObj.getWorldInfo().getWorldTime() % fireRate == 0L && this.rot.yaw == this.focrot.yaw)
                {
                    if (curAmmo-- > 0)
                    {
                        this.fire();
                    }
                    else
                    {
                        this.reload();
                    }
                }
            }
        }
    }

    public void lookAtFocusPoint()
    {
        if (this.foc != null)
        {
            for (int runCycles = this.cycleCount; runCycles > 0; runCycles--)
            {
                if (Math.ceil(this.getRotationYaw()) < Math.ceil(this.focrot.yaw))
                {
                    this.rot.yaw += 1;
                }
                else if (Math.ceil(this.getRotationYaw()) > Math.ceil(this.focrot.yaw))
                {
                    this.rot.yaw -= 1;
                }

                if (Math.ceil(this.getRotationPitch()) < Math.ceil(this.focrot.pitch))
                {
                    this.rot.pitch += 1;
                }
                else if (Math.ceil(this.getRotationPitch()) > Math.ceil(this.focrot.pitch))
                {
                    this.rot.pitch -= 1;
                }

                double focus = 1;

                if (Math.ceil(this.getRotationPitch()) >= Math.ceil(this.focrot.pitch - focus) && Math.ceil(this.getRotationPitch()) <= Math.ceil(this.focrot.pitch + focus) && Math.ceil(this.getRotationYaw()) >= Math.ceil(this.focrot.yaw - focus) && Math.ceil(this.getRotationYaw()) <= Math.ceil(this.focrot.yaw + focus))
                {
                    this.rot.pitch = this.focrot.pitch;
                    this.rot.yaw = this.focrot.yaw;
                }
            }
        }
    }

    public void reloadIfNecessary()
    {
        if (this.curAmmo < this.getMaxAmmo() && this.curAmmo <= 0)
        {
            this.reload();
        }
    }

    public void updateAmmunitionCount()
    {
        if (worldObj.getWorldInfo().getWorldTime() % 8L == 0L)
        {
            this.roundsMax = (9 * 64);
            this.rounds = 0;

            for (int i = 0; i < 9; i++)
            {
                ItemStack stack = this.inventoryAmmo.getStackInSlot(i);

                if (stack != null)
                {
                    if (stack.getItem() == this.itemAmmo)
                    {
                        this.rounds = this.rounds + (stack.stackSize);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void pickUpAmmunition()
    {
        if (this.worldObj != null && this.inventoryAmmo != null)
        {
            ArrayList<EntityItem> entityItemList = (ArrayList<EntityItem>) Entities.getEntitiesInCoordsRange(worldObj, EntityItem.class, new Pos(this), 1);

            for (EntityItem entityItem : entityItemList)
            {
                if (entityItem.delayBeforeCanPickup <= 0)
                {
                    ItemStack stack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);

                    if (stack.getItem() == this.itemAmmo)
                    {
                        for (int x = 0; x < 9; x++)
                        {
                            ItemStack invStack = this.inventoryAmmo.getStackInSlot(x);

                            if (invStack == null || invStack != null && invStack.stackSize < 64)
                            {
                                this.inventoryAmmo.setInventorySlotContents(x, stack);
                                entityItem.setDead();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void reload()
    {
        if (this.rounds >= 1)
        {
            this.curAmmo = this.getMaxAmmo();

            for (int x = 0; x < 9; x++)
            {
                ItemStack stack = this.inventoryAmmo.getStackInSlot(x);

                if (stack != null && stack.getItem() == this.getItemAmmo())
                {
                    if (stack.stackSize-- <= 0)
                    {
                        this.inventoryAmmo.setInventorySlotContents(x, null);
                    }

                    break;
                }
            }
        }
    }

    public void fire()
    {
        this.isFiring = true;
        this.timeout = this.timeoutMax;
        this.targetEntity.attackEntityFrom(DamageSources.bullet, 1F);
        this.targetEntity.hurtResistantTime = 0;
        // this.worldObj.spawnParticle("largesmoke", xCoord, yCoord, zCoord, 1, 1, 1);
        Sounds.SOUND_WEAPON_M56SG.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public Rotation turnTurretToPoint(Pos pos, Rotation rotation, float deltaYaw, float deltaPitch)
    {
        double x = pos.x - this.pos.x;
        double y = pos.y - this.pos.y;
        double z = pos.z - this.pos.z;
        double sq = MathHelper.sqrt_double(x * x + z * z);

        float newYaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(Math.atan2(y, sq) * 180.0D / Math.PI));

        return rotation.setYaw(MDXMath.wrapAngle(this.rot.yaw, newYaw, deltaYaw)).setPitch(MDXMath.wrapAngle(this.rot.pitch, f1, deltaPitch));
    }

    public void removeTargetType(Class<? extends Entity> entityClass)
    {
        this.setTargetEntity(null);

        if (this.targetTypes.contains(entityClass))
        {
            this.targetTypes.remove(entityClass);
        }
    }

    public void addTargetType(Class<? extends Entity> entityClass)
    {
        this.setTargetEntity(null);

        if (!this.targetTypes.contains(entityClass))
        {
            this.targetTypes.add(entityClass);
        }
    }

    public boolean canTargetType(Class<? extends Entity> entityClass)
    {
        if (this.targetTypes.contains(entityClass))
        {
            return true;
        }

        return false;
    }

    public void setPredefinedTargets()
    {
        this.addTargetType(EntityOvamorph.class);
        this.addTargetType(EntityFacehugger.class);
        this.addTargetType(EntityChestburster.class);
        this.addTargetType(EntityDrone.class);
        this.addTargetType(EntityWarrior.class);
        this.addTargetType(EntityPraetorian.class);
        this.addTargetType(EntityQueen.class);
        this.addTargetType(EntityCrusher.class);
        this.addTargetType(EntitySpitter.class);
        this.addTargetType(EntityAqua.class);
        this.addTargetType(EntityPredalien.class);
        this.addTargetType(EntitySlime.class);
        this.addTargetType(EntityAqua.class);
        this.addTargetType(EntityRunnerWarrior.class);
        this.addTargetType(EntityRunnerDrone.class);
        this.addTargetType(EntityDeacon.class);
        this.addTargetType(EntityUltramorph.class);
        this.addTargetType(EntityRunnerChestburster.class);
        this.addTargetType(EntityPredalienChestburster.class);
        this.addTargetType(EntityQueenChestburster.class);
        this.addTargetType(EntityBelugaburster.class);
        this.addTargetType(EntityHammerpede.class);
        this.addTargetType(EntityOvamorph.class);
        this.addTargetType(EntityDeaconShark.class);
        this.addTargetType(EntityOctohugger.class);
        this.addTargetType(EntityRoyalFacehugger.class);
        this.addTargetType(EntityTrilobite.class);
        this.addTargetType(EntityPredalien.class);
        this.addTargetType(EntitySpaceJockey.class);
        this.addTargetType(EntityEngineer.class);
        this.addTargetType(EntityBelugamorph.class);
        this.addTargetType(EntityGooMutant.class);
        this.addTargetType(EntityAethon.class);
    }

    public void applyUpgrades()
    {
        int cycles = this.getBaseCycleCount();
        this.setAmmoDisplayEnabled(false);

        for (int i = 0; i < 3; i++)
        {
            ItemStack pciSlot = this.inventoryExpansion.getStackInSlot(i);

            if (pciSlot != null && pciSlot.getItem() == AliensVsPredator.items().itemProcessor)
            {
                cycles += 1 * pciSlot.stackSize;
            }

            if (pciSlot != null && pciSlot.getItem() == AliensVsPredator.items().itemLedDisplay)
            {
                this.setAmmoDisplayEnabled(true);
            }
        }

        this.setCycleCount(cycles);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        this.direction = nbt.getInteger("Direction");
        this.focrot.setYaw(nbt.getFloat("FocusYaw")).setPitch(nbt.getFloat("FocusPitch"));
        this.readTargetListFromCompoundTag(nbt);
        this.readInventoryFromNBT(nbt, this.inventoryAmmo);
        this.readInventoryFromNBT(nbt, this.inventoryExpansion);
        this.readInventoryFromNBT(nbt, this.inventoryDrive);
        this.sendSyncPacket();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setInteger("Direction", this.direction);
        nbt.setFloat("FocusYaw", this.focrot.yaw);
        nbt.setFloat("FocusPitch", this.focrot.pitch);
        nbt.setTag("Targets", this.getTargetListTag());
        this.saveInventoryToNBT(nbt, this.inventoryAmmo);
        this.saveInventoryToNBT(nbt, this.inventoryExpansion);
        this.saveInventoryToNBT(nbt, this.inventoryDrive);
    }

    private void sendSyncPacket()
    {
        AliensVsPredator.network().sendToAll(new PacketTurretSync(this));
    }

    public void onReceiveInitPacket(PacketTurretSync packet, MessageContext ctx)
    {
        this.applyUpgrades();
        this.readTargetList(packet.targets);
        this.rot.yaw = packet.rotation.yaw;
        this.rot.pitch = packet.rotation.pitch;
    }

    @SideOnly(Side.CLIENT)
    public void onReceiveTargetUpdatePacket(PacketTurretTargetUpdate packet, MessageContext ctx)
    {
        Entity entity = Game.minecraft().theWorld.getEntityByID(packet.id);
        this.setTargetEntity(entity);
        this.foc = packet.foc;
        this.focrot = packet.focrot;
    }

    public NBTTagList getTargetListTag()
    {
        ArrayList<String> entityIDs = new ArrayList<String>();

        for (Class<? extends Entity> c : this.getDangerousTargets())
        {
            entityIDs.add(Entities.getEntityRegistrationId(c));
        }

        return NBTStorage.newStringNBTList(entityIDs);
    }

    public void readTargetListFromCompoundTag(NBTTagCompound nbt)
    {
        NBTTagList list = nbt.getTagList("Targets", NBT.TAG_STRING);

        if (list instanceof NBTTagList)
        {
            this.readTargetList(list);
        }
    }

    @SuppressWarnings("unchecked")
    public void readTargetList(NBTTagList list)
    {
        for (int i = 0; i < list.tagCount(); i++)
        {
            String id = list.getStringTagAt(i);

            Class<? extends Entity> c = (Class<? extends Entity>) EntityList.stringToClassMapping.get(id);
            this.addTargetType(c);
        }
    }

    private void saveInventoryToNBT(NBTTagCompound nbt, IInventory inventory)
    {
        NBTTagList items = new NBTTagList();

        for (byte x = 0; x < inventory.getSizeInventory(); x++)
        {
            ItemStack stack = inventory.getStackInSlot(x);

            if (stack != null)
            {
                NBTTagCompound item = new NBTTagCompound();
                item.setByte("Slot", x);
                stack.writeToNBT(item);
                items.appendTag(item);
            }
        }

        nbt.setTag(inventory.getInventoryName(), items);
    }

    private void readInventoryFromNBT(NBTTagCompound nbt, IInventory inventory)
    {
        NBTTagList items = nbt.getTagList(inventory.getInventoryName(), Constants.NBT.TAG_COMPOUND);

        for (byte x = 0; x < items.tagCount(); x++)
        {
            NBTTagCompound item = items.getCompoundTagAt(x);

            byte slot = item.getByte("Slot");

            if (slot >= 0 && slot <= inventory.getSizeInventory())
            {
                inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
            }
        }
    }

    public ContainerTurret getNewContainer(EntityPlayer player)
    {
        return (container = new ContainerTurret(player, this, worldObj, xCoord, yCoord, zCoord));
    }

    public ContainerTurret getContainer(EntityPlayer player)
    {
        return container == null && player != null ? container = getNewContainer(player) : container;
    }

    public long getFireRate()
    {
        return fireRate;
    }

    public void setFireRate(int fireRate)
    {
        this.fireRate = fireRate;
    }

    public int getRange()
    {
        return range;
    }

    public void setRange(int range)
    {
        this.range = range;
    }

    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public void setCycleCount(int count)
    {
        this.cycleCount = count;
    }

    public int getCycleCount()
    {
        return cycleCount;
    }

    public int getBaseCycleCount()
    {
        return 4;
    }

    public void setAmmoDisplayEnabled(boolean ammoDisplayEnabled)
    {
        this.ammoDisplayEnabled = ammoDisplayEnabled;
    }

    public boolean isAmmoDisplayEnabled()
    {
        return ammoDisplayEnabled;
    }

    public Entity getTargetEntity()
    {
        return targetEntity;
    }

    public void setTargetEntity(Entity targetEntity)
    {
        this.targetEntity = targetEntity;
    }

    public ArrayList<Class<? extends Entity>> getDangerousTargets()
    {
        return targetTypes;
    }

    public int getCurAmmo()
    {
        return curAmmo;
    }

    public int getMaxAmmo()
    {
        return 128;
    }

    public void setCurAmmo(int curAmmo)
    {
        this.curAmmo = curAmmo;
    }

    public Item getItemAmmo()
    {
        return itemAmmo;
    }

    public void setItemAmmo(Item itemAmmo)
    {
        this.itemAmmo = itemAmmo;
    }

    public int getCurRounds()
    {
        return rounds;
    }

    public int getMaxRounds()
    {
        return roundsMax;
    }

    public void setCurRounds(int curRounds)
    {
        this.rounds = curRounds;
    }

    public void setMaxRounds(int maxRounds)
    {
        this.roundsMax = maxRounds;
    }

    public float getRotationYaw()
    {
        // this.getDirection() * 90F +
        return this.rot.yaw;
    }

    public float getRotationPitch()
    {
        return this.rot.pitch;
    }

    public boolean isFiring()
    {
        return isFiring;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromOtherDevice(int ID)
    {
        StringBuilder builder = new StringBuilder();
        ItemStack devicePort = this.inventoryDrive.getStackInSlot(0);

        if (devicePort != null)
        {
            NBTTagCompound nbt = devicePort.getTagCompound();

            if (nbt != null)
            {
                NBTTagList list = nbt.getTagList("Targets", NBT.TAG_STRING);

                if (list != null)
                {
                    for (int i = 0; i < list.tagCount(); i++)
                    {
                        String id = list.getStringTagAt(i);

                        Class<? extends Entity> c = (Class<? extends Entity>) EntityList.stringToClassMapping.get(id);
                        this.addTargetType(c);
                        builder.append(id + "-");
                    }
                }
            }
        }

        this.sendSyncPacket();
    }

    @Override
    public void writeToOtherDevice(int ID)
    {
        if (container != null)
        {
            ItemStack devicePort = this.inventoryDrive.getStackInSlot(0);

            if (devicePort != null)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                ArrayList<String> entityIDs = new ArrayList<String>();

                for (Class<? extends Entity> c : this.getDangerousTargets())
                {
                    if (c != null)
                    {
                        entityIDs.add(Entities.getEntityRegistrationId(c));
                    }
                }

                nbt.setTag("Targets", NBTStorage.newStringNBTList(entityIDs));

                devicePort.setTagCompound(nbt);
                devicePort.setStackDisplayName("NBT Drive - " + "TURRET." + this.pos.x + "" + this.pos.y + "" + this.pos.z);
                this.inventoryDrive.setInventorySlotContents(0, devicePort);
            }
        }
    }

    @Override
    public Block getBlockType()
    {
        return Blocks.beacon;
    }

    @Override
    public boolean canConnectPower(ForgeDirection from)
    {
        return true;
    }

    @Override
    public double receiveVoltage(ForgeDirection from, double maxReceive, boolean simulate)
    {
        return super.receiveVoltage(from, maxReceive, simulate);
    }

    @Override
    public double getCurrentVoltage(ForgeDirection from)
    {
        return this.getVoltage();
    }

    @Override
    public double getMaxVoltage(ForgeDirection from)
    {
        return 220;
    }

    public Rotation getRotation()
    {
        return this.rot;
    }

    public Rotation getFocusRotation()
    {
        return this.focrot;
    }

    public Pos getFocusPosition()
    {
        return foc;
    }
}
