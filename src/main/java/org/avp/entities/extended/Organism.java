package org.avp.entities.extended;

import org.avp.AliensVsPredator;
import org.avp.entities.mob.EntitySpeciesAlien;
import org.avp.packets.client.OrganismClientSync;
import org.avp.packets.server.OrganismServerSync;
import org.avp.util.Embryo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
    
    public void onTick(World world)
    {
        if (!world.isRemote && this.hasEmbryo() && world.getWorldTime() % 60 == 0)
        {
            this.syncWithClients();
        }
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
    
    public void impregnate()
    {
        this.impregnate(Embryo.createFromHost(this.living));
    }

    public void impregnate(Embryo embryo)
    {
        this.embryo = embryo.createCopy();
    }

    public void removeEmbryo()
    {
        this.embryo = null;
    }

    public void gestate()
    {
        if (this.getEntity() != null)
        {
            this.getEmbryo().createNasenticOrganism(this.getEntity().worldObj);
            this.getEmbryo().grow(this);
        }
    }

    public void heal()
    {
        living.setHealth(living.getMaxHealth());

        if (!living.worldObj.isRemote)
        {
            living.curePotionEffects(new ItemStack(Items.milk_bucket, 1));
            living.getActivePotionEffects().clear();
        }

        if (this.hasEmbryo())
        {
            this.removeEmbryo();
        }

        if (living.riddenByEntity != null && living.riddenByEntity instanceof EntitySpeciesAlien)
        {
            living.riddenByEntity.setDead();
        }

        if (living instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) living;
            player.getFoodStats().setFoodLevel(20);
        }        
    }
}
