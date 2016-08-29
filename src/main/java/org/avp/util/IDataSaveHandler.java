package org.avp.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IDataSaveHandler
{
    public boolean saveData(World world, NBTTagCompound nbt);
    
    public boolean loadData(World world, NBTTagCompound nbt);
}
