package org.avp.util;

import java.util.ArrayList;

import org.avp.api.parasitoidic.INascentic;
import org.avp.entities.extended.Organism;
import org.avp.entities.mob.EntityAqua;
import org.avp.entities.mob.EntityChestburster;
import org.avp.entities.mob.EntityDrone;
import org.avp.entities.mob.EntityEngineer;
import org.avp.entities.mob.EntityPredalien;
import org.avp.entities.mob.EntityQueen;
import org.avp.entities.mob.EntityRunnerDrone;
import org.avp.entities.mob.EntitySpaceJockey;
import org.avp.entities.mob.EntitySpitter;
import org.avp.entities.mob.EntityUltramorph;
import org.avp.entities.mob.EntityYautja;
import org.avp.entities.mob.EntityYautjaBerserker;

import com.arisux.mdxlib.lib.world.entity.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Embryo implements Cloneable
{
    public static final ArrayList<Embryo> registeredTypes = new ArrayList<Embryo>();
    private static int                    nextAvailableId = 1;

    public static final Embryo            STANDARD        = new Embryo(EntityChestburster.class, EntityDrone.class, EntityLiving.class);
    public static final Embryo            QUEEN           = new Embryo(EntityChestburster.class, EntityQueen.class, EntityLiving.class).setGestationPeriod(10 * 60 * 20);

    static
    {
        new Embryo(EntityChestburster.class, EntitySpitter.class, EntityCreeper.class).register();
        new Embryo(EntityChestburster.class, EntityAqua.class, EntitySquid.class).register();
        new Embryo(EntityChestburster.class, EntityPredalien.class, EntityYautja.class, EntityYautjaBerserker.class).register();
        new Embryo(EntityChestburster.class, EntityRunnerDrone.class, EntityCow.class, EntityHorse.class, EntityWolf.class).register();
        new Embryo(EntityChestburster.class, EntityUltramorph.class, EntityEngineer.class, EntitySpaceJockey.class).register();
    }

    private int                         id;
    private int                         age;
    private int                         gestationPeriod;
    private Class<? extends INascentic> nascenticType;
    private Class<? extends Entity>     result;
    private Class<? extends Entity>[]   hosts;
    private INascentic                  nascenticOrganism;

    public Embryo(Class<?> nascenticType, Class<?> result, Class<?>... hosts)
    {
        this.nascenticType = (Class<? extends INascentic>) nascenticType;
        this.result = (Class<? extends Entity>) result;
        this.hosts = (Class<? extends Entity>[]) hosts;
        this.gestationPeriod = 6000;
    }

    public Embryo register()
    {
        this.id = nextAvailableId++;
        registeredTypes.add(this);
        return this;
    }

    public void grow(Organism host)
    {
        this.age++;

        if (this.getNascenticOrganism() != null)
        {
            this.getNascenticOrganism().grow(host.getEntity());
        }
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

    public Class<? extends Entity> getResultingOrganism()
    {
        return result;
    }

    public INascentic createNasenticOrganism(World world)
    {
        if (this.nascenticOrganism == null)
        {
            return this.nascenticOrganism = (INascentic) Entities.constructEntity(world, (Class<? extends Entity>) this.nascenticType);
        }

        return this.nascenticOrganism;
    }

    public INascentic getNascenticOrganism()
    {
        return nascenticOrganism;
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
        int id = nbt.getInteger("EmbryoId");

        if (id != 0)
        {
            try
            {
                Embryo embryo = (Embryo) fromId(id).clone();
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

    public static Embryo fromId(int id)
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

    public static Embryo createFromHost(EntityLivingBase host)
    {
        for (Embryo embryo : registeredTypes)
        {
            for (Class c : embryo.hosts)
            {
                if (c == host.getClass())
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
}
