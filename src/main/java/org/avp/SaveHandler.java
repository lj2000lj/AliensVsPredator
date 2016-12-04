package org.avp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.avp.event.HiveHandler;
import org.avp.util.IDataSaveHandler;

import com.arisux.mdxlib.AMDXLib;
import com.arisux.mdxlib.lib.world.storage.NBTStorage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class SaveHandler
{
    public static SaveHandler           instance = new SaveHandler();
    private ArrayList<IDataSaveHandler> dataHandlers;

    public SaveHandler()
    {
        this.dataHandlers = new ArrayList<IDataSaveHandler>();
        this.addDataHandlers();
    }

    public void addDataHandlers()
    {
        // this.dataHandlers.add(DerelictWorldData.instance);
        this.dataHandlers.add(HiveHandler.instance);
    }

    public File getSaveFile(World world)
    {
        return new File(world.getSaveHandler().getWorldDirectory(), String.format(this.getSaveFilename(), world.provider.dimensionId));
    }

    public String getSaveFilename()
    {
        return "aliensvspredator_%s.dat";
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        World world = event.world;
        File worldSave = this.getSaveFile(world);
        NBTTagCompound tag = new NBTTagCompound();

        try
        {
            for (IDataSaveHandler dataHandler : this.dataHandlers)
            {
                if (dataHandler != null)
                {
                    if (!dataHandler.saveData(world, tag))
                    {
                        AMDXLib.log().info(String.format("Unable to save world data: ", this.getSaveFilename()));
                    }
                }
            }

            NBTStorage.writeCompressed(tag, worldSave);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.world;
        NBTTagCompound tag = new NBTTagCompound();
        File worldSave = this.getSaveFile(world);

        if (world.getSaveHandler().getWorldDirectory() != null)
        {
            try
            {
                if (worldSave.getAbsoluteFile().exists())
                {
                    AMDXLib.log().info(String.format("Loading world data: ", worldSave.getAbsolutePath()));
                    NBTTagCompound read = NBTStorage.readCompressed(worldSave.getAbsoluteFile());
                    tag = read == null ? tag : read;

                    for (IDataSaveHandler dataHandler : this.dataHandlers)
                    {
                        if (dataHandler != null)
                        {
                            if (!dataHandler.loadData(world, tag))
                            {
                                AMDXLib.log().info(String.format("Unable to load world data: ", this.getSaveFilename()));
                            }
                        }
                    }
                }
            }
            catch (FileNotFoundException f)
            {
                System.out.println(String.format("Error loading data from: %s", worldSave.getAbsolutePath()));
                f.printStackTrace();
            }
            catch (IOException io)
            {
                io.printStackTrace();
            }
        }
    }
}
