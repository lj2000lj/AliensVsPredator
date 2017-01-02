package org.avp.event;

import java.util.ArrayList;
import java.util.UUID;

import org.avp.entities.mob.EntitySpeciesAlien;
import org.avp.entities.tile.TileEntityHiveResin;
import org.avp.util.IDataSaveHandler;
import org.avp.util.XenomorphHive;

import com.arisux.mdxlib.MDX;
import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.world.CoordData;
import com.arisux.mdxlib.lib.world.Worlds;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
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
    public ArrayList<CoordData>      burntResin;

    public HiveHandler()
    {
        this.hives = new ArrayList<XenomorphHive>();
        this.burntResin = new ArrayList<CoordData>();
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
            if (hive != null && hive.getUniqueIdentifier() != null && hive.getUniqueIdentifier().equals(uuid))
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
                int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
                event.world.setBlock(event.x, event.y, event.z, resin.getBlockCovering());
                event.world.setBlockMetadataWithNotify(event.x, event.y, event.z,  meta, 3);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void updateHives(TickEvent.WorldTickEvent event)
    {
        // TODO: Murder annoying slimes if this is a dev environment.
        if (Game.isDevEnvironment())
        {
            for (Object o : new ArrayList(event.world.loadedEntityList))
            {
                Entity entity = (Entity) o;

                if (o instanceof EntitySlime)
                {
                    entity.setDead();
                }
            }
        }

        for (CoordData coord : new ArrayList<CoordData>(this.burntResin))
        {
            int meta = event.world.getBlockMetadata((int) coord.x, (int) coord.y, (int) coord.z);
            event.world.setBlock((int) coord.x, (int) coord.y, (int) coord.z, coord.getBlock(event.world));
            event.world.setBlockMetadataWithNotify((int) coord.x, (int) coord.y, (int) coord.z,  meta, 3);
            this.burntResin.remove(coord);
        }

        for (XenomorphHive hive : (ArrayList<XenomorphHive>) this.hives.clone())
        {
            if (hive != null && hive.getDimensionId() == event.world.provider.dimensionId)
            {
                hive.update(event.world);
            }
        }
    }

    public void clearCaches()
    {
        System.out.println("Cleared hive cache.");
        this.hives.clear();
    }

    @Override
    public boolean saveData(World world, NBTTagCompound nbt)
    {
        int hiveCount = 0;

        if (nbt != null)
        {
            if (this.hives != null && !this.hives.isEmpty())
            {
                NBTTagList tagHives = new NBTTagList();

                for (XenomorphHive hive : this.hives)
                {
                    if (hive.getDimensionId() == world.provider.dimensionId)
                    {
                        hiveCount++;
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

        MDX.log().info(String.format("Saved %s hives for level '%s'/%s", hiveCount, world.getSaveHandler().getWorldDirectoryName(), world.provider.getDimensionName()));

        return true;
    }

    @Override
    public boolean loadData(World world, NBTTagCompound nbt)
    {
        int hiveCount = 0;

        if (nbt != null)
        {
            NBTTagList tagHives = nbt.getTagList("XenomorphHives", NBT.TAG_COMPOUND);

            if ((tagHives.tagCount()) > 0)
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
                        hiveCount++;
                        hive.load(world, uniqueIdentifier, tagHive);
                    }
                }
            }
        }
        else
        {
            return false;
        }

        MDX.log().info(String.format("%s hives have been loaded for level '%s'/%s. %s hives are globally accessable.", hiveCount, world.getSaveHandler().getWorldDirectoryName(), world.provider.getDimensionName(), this.hives.size()));
        System.out.println(this.hives);

        return true;
    }
}
