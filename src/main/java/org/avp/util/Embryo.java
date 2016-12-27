package org.avp.util;

import java.util.ArrayList;

import org.avp.entities.extended.Organism;
import org.avp.entities.mob.EntityAqua;
import org.avp.entities.mob.EntityDrone;
import org.avp.entities.mob.EntityEngineer;
import org.avp.entities.mob.EntityPredalien;
import org.avp.entities.mob.EntityRunnerDrone;
import org.avp.entities.mob.EntitySpaceJockey;
import org.avp.entities.mob.EntitySpitter;
import org.avp.entities.mob.EntityUltramorph;
import org.avp.entities.mob.EntityYautja;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;

public class Embryo implements Cloneable
{
    public static final ArrayList<Embryo> registeredTypes = new ArrayList<Embryo>();
    public static int                     nextAvailableId = 1;

    public static final Embryo            STANDARD        = new Embryo(EntityDrone.class, EntityLiving.class);

    static
    {
        new Embryo(EntitySpitter.class, EntityCreeper.class).register();
        new Embryo(EntityAqua.class, EntitySquid.class).register();
        new Embryo(EntityPredalien.class, EntityYautja.class).register();
        new Embryo(EntityRunnerDrone.class, EntityCow.class, EntityHorse.class, EntityWolf.class).register();
        new Embryo(EntityUltramorph.class, EntityEngineer.class, EntitySpaceJockey.class).register();
    }

    private int                       id;
    private int                       age;
    private int                       gestationPeriod;
    private Class<? extends Entity>[] hosts;
    private Class<? extends Entity>   result;

    public Embryo(Class<?> result, Class<?>... hosts)
    {
        this.hosts = (Class<? extends Entity>[]) hosts;
        this.result = (Class<? extends Entity>) result;
        this.gestationPeriod = 1000;
    }

    public Embryo register()
    {
        this.id = nextAvailableId++;
        registeredTypes.add(this);
        return this;
    }

    public void tick(Organism extendedEntity)
    {
        this.age++;
    }

    /**
     * @return The id this embryo was registered with. Returns 0 if this embryo was not registered.
     */
    public int getRegistrationId()
    {
        return id;
    }

    public int getAge()
    {
        return this.age;
    }

    public boolean isPremature()
    {
        return this.getAge() < this.getGestationPeriod() - this.getGestationPeriod() / 8;
    }

    public int getGestationPeriod()
    {
        return this.gestationPeriod;
    }

    public Embryo setGestationPeriod(int gestationPeriod)
    {
        this.gestationPeriod = gestationPeriod;
        return this;
    }

    public Class<? extends Entity> getResult()
    {
        return result;
    }

    public Class<?>[] getHosts()
    {
        return hosts;
    }

    public static void save(Embryo embryo, NBTTagCompound nbt)
    {
        if (embryo != null)
        {
            nbt.setInteger("EmbryoId", embryo.id);
            nbt.setInteger("Age", embryo.age);
        }
    }

    public static Embryo load(NBTTagCompound nbt)
    {
        int id = nbt.getInteger("EmbryoType");

        if (id != 0)
        {
            try
            {
                Embryo embryo = (Embryo) get(id).clone();
                embryo.age = nbt.getInteger("Age");

                return embryo;
            }
            catch (Exception e)
            {
                System.out.println("ERROR during embryo construction: " + e);
                System.out.println("ID: " + id);
            }
        }
        return null;
    }

    public static Embryo get(int id)
    {
        for (Embryo embryo : registeredTypes)
        {
            if (embryo.id == id)
            {
                try
                {
                    return (Embryo) embryo.clone();
                }
                catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return STANDARD;
    }

    public static Embryo getMappingFromHost(Class<? extends EntityLivingBase> host)
    {
        for (Embryo embryo : registeredTypes)
        {
            for (Class c : embryo.hosts)
            {
                if (c == host)
                {
                    try
                    {
                        return (Embryo) embryo.clone();
                    }
                    catch (CloneNotSupportedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        try
        {
            return (Embryo) STANDARD.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Embryo getMappingFromResult(Class<? extends EntityLivingBase> result)
    {
        for (Embryo embryo : registeredTypes)
        {
            if (embryo.result == result)
            {
                try
                {
                    return (Embryo) embryo.clone();
                }
                catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        try
        {
            return (Embryo) STANDARD.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
