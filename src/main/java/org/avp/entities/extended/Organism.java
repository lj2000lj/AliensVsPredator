package org.avp.entities.extended;

import org.avp.AliensVsPredator;
import org.avp.packets.client.OrganismClientSync;
import org.avp.packets.server.OrganismServerSync;
import org.avp.util.Embryo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Organism implements IExtendedEntityProperties
{
    public static final String IDENTIFIER = "Organism";
    private EntityLivingBase   living;
    private Embryo             embryo;

    public Organism(EntityLivingBase living)
    {
        super();
        this.living = living;
    }

    public static final Organism get(EntityLivingBase living)
    {
        return (Organism) living.getExtendedProperties(IDENTIFIER);
    }

    @Override
    public void init(Entity entity, World world)
    {
        this.embryo = null;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        Embryo.save(this.getEmbryo(), nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        this.embryo = Embryo.load(nbt);
    }

    private NBTTagCompound asCompoundTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        this.saveNBTData(tag);

        return tag;
    }

    public void syncWithClients()
    {
        AliensVsPredator.network().sendToAll(new OrganismClientSync(this.getEntity().getEntityId(), this.asCompoundTag()));
    }

    public void syncWithServer()
    {
        AliensVsPredator.network().sendToServer(new OrganismServerSync(this.getEntity().getEntityId(), this.asCompoundTag()));
    }

    public EntityLivingBase getEntity()
    {
        return this.living;
    }

    public boolean hasEmbryo()
    {
        return this.embryo != null;
    }

    public Embryo getEmbryo()
    {
        return this.embryo;
    }

    public void setEmbryo(Embryo embryo)
    {
        this.embryo = embryo;
    }

    public void removeEmbryo()
    {
        this.setEmbryo(null);
    }
}
