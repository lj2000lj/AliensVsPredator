package org.avp.event;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.entities.mob.EntitySpeciesAlien;
import org.avp.entities.tile.TileEntityHiveResin;
import org.avp.util.IDataSaveHandler;
import org.avp.util.XenomorphHive;

import com.arisux.amdxlib.AMDXLib;
import com.arisux.amdxlib.lib.world.Worlds;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.world.BlockEvent;

public class HiveHandler implements IDataSaveHandler
{
    public static final HiveHandler  instance = new HiveHandler();
    private ArrayList<XenomorphHive> hives    = null;

    public HiveHandler()
    {
        this.hives = new ArrayList<XenomorphHive>();
    }

    public ArrayList<XenomorphHive> getHives()
    {
        return hives;
    }

    public XenomorphHive getHiveForAlien(EntitySpeciesAlien alien)
    {
        for (XenomorphHive hive : this.hives)
        {
            for (EntitySpeciesAlien a : hive.getAliensInHive())
            {
                if (a == alien)
                {
                    return hive;
                }
            }
        }

        return null;
    }

    public XenomorphHive getHiveForUUID(UUID uuid)
    {
        for (XenomorphHive hive : this.hives)
        {
            if (hive.getUniqueIdentifier().equals(uuid))
            {
                return hive;
            }
        }

        return null;
    }

    @SubscribeEvent
    public void breakResin(BlockEvent.BreakEvent event)
    {
        TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);

        if (tile instanceof TileEntityHiveResin)
        {
            TileEntityHiveResin resin = (TileEntityHiveResin) tile;

            if (resin != null && resin.getBlockCovering() != null)
            {
                event.world.setBlock(event.x, event.y, event.z, resin.getBlockCovering());
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void updateHives(TickEvent.WorldTickEvent event)
    {
        if (event.world.provider.dimensionId == 0 && event.world.getWorldTime() % (20 * 5) == 0)
        {
            System.out.println(hives.size() + " HIVES: " + hives);
        }

//        for (XenomorphHive hive : (ArrayList<XenomorphHive>) this.hives.clone())
//        {
//            if (hive != null && hive.getDimensionId() == event.world.provider.dimensionId)
//            {
//                //hive.update(event.world);
//            }
//        }
    }

    public void clearCaches()
    {
        this.hives.clear();
    }

    @Override
    public boolean saveData(World world, NBTTagCompound nbt)
    {
        AMDXLib.log().info(String.format("Saving %s hives for level '%s'/%s", this.hives.size(), world.getSaveHandler().getWorldDirectoryName(), world.provider.getDimensionName()));

        if (nbt != null)
        {
            if (this.hives != null && !this.hives.isEmpty())
            {
                NBTTagList tagHives = new NBTTagList();

                for (XenomorphHive hive : this.hives)
                {
                    if (hive.getDimensionId() == world.provider.dimensionId)
                    {
                        NBTTagCompound tagHive = new NBTTagCompound();
                        hive.save(world, tagHive);
                        tagHives.appendTag(tagHive);
                    }
                }

                nbt.setTag("XenomorphHives", tagHives);
            }
        }
        else
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean loadData(World world, NBTTagCompound nbt)
    {
        AMDXLib.log().info(String.format("Loading %s hives for level '%s'/%s", this.hives.size(), world.getSaveHandler().getWorldDirectoryName(), world.provider.getDimensionName()));

        if (nbt != null)
        {
            NBTTagList tagHives = nbt.getTagList("XenomorphHives_" + world.provider.dimensionId, NBT.TAG_COMPOUND);

            if (tagHives.tagCount() > 0)
            {
                for (int i = 0; i < tagHives.tagCount(); i++)
                {
                    NBTTagCompound tagHive = tagHives.getCompoundTagAt(i);
                    UUID uniqueIdentifier = Worlds.uuidFromNBT(tagHive, "UUID");
                    XenomorphHive hive = getHiveForUUID(uniqueIdentifier);

                    if (hive == null)
                    {
                        hive = new XenomorphHive(world, uniqueIdentifier);

                        if (!this.hives.contains(hive))
                        {
                            this.hives.add(hive);
                        }
                    }

                    if (hive != null)
                    {
                        hive.load(world, tagHive);
                    }
                }
            }
        }
        else
        {
            return false;
        }

        return true;
    }
}
