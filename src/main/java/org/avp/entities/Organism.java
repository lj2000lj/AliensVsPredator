package org.avp.entities;

import org.avp.AliensVsPredator;
import org.avp.client.render.TacticalHUDRenderEvent;
import org.avp.entities.living.EntitySpeciesAlien;
import org.avp.packets.client.OrganismClientSync;
import org.avp.packets.server.OrganismServerSync;
import org.avp.world.Embryo;

import net.minecraft.client.Minecraft;
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
    private int                bpm;

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
        this.bpm = 60;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt)
    {
        Embryo.save(this.getEmbryo(), nbt);
        nbt.setInteger("BPM", this.bpm);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt)
    {
        this.embryo = Embryo.load(nbt);
        this.bpm = nbt.getInteger("BPM");
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
        if (!world.isRemote && world.getWorldTime() % 60 == 0)
        {
            this.syncWithClients();
        }

        if (!world.isRemote)
        {
            if (this.getEntity().isSprinting())
            {
                this.bpm = 130 + this.getEntity().getRNG().nextInt(20);
            }
            else if (this.getEntity().motionX + this.getEntity().motionZ > 0)
            {
                this.bpm = 70 + this.getEntity().getRNG().nextInt(10);
            }
            else
            {
                this.bpm = 60 + this.getEntity().getRNG().nextInt(10);
            }
            
            if (this.hasEmbryo())
            {
                int age = this.getEmbryo().getAge();
                int gestationPeriod = this.getEmbryo().getGestationPeriod();
                int timeLeft = gestationPeriod - age;
                int timeBleed = gestationPeriod - (gestationPeriod / 10);

                if (age >= timeBleed)
                {
                    this.bpm = 60 + (250 - (timeLeft * 250 / (30 * 20)));
                    
                    if (world.getWorldTime() % 10 == 0)
                    {
                        this.syncWithClients();
                    }
                }
            }
        }

        if (world.isRemote)
        {
            if (this.getEntity() == Minecraft.getMinecraft().thePlayer)
            {
                TacticalHUDRenderEvent.instance.getElectrocardiogram().setRate(this.bpm);
            }
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

        if (living instanceof EntityPlayer && living.worldObj.isRemote)
        {
            EntityPlayer player = (EntityPlayer) living;
            player.getFoodStats().setFoodLevel(20);
        }
    }
}
