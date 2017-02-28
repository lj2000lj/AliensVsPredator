package org.avp.packets.server;

import org.avp.DamageSources;
import org.avp.block.BlockHiveResin;
import org.avp.item.ItemFirearm;
import org.avp.world.hives.HiveHandler;

import com.arisux.mdxlib.lib.world.entity.Entities;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketFirearmSync implements IMessage, IMessageHandler<PacketFirearmSync, PacketFirearmSync>
{
    public int    hitType;
    public int    entityId;
    public int    hitX;
    public int    hitY;
    public int    hitZ;
    public String soundKey;
    public double damage;

    public PacketFirearmSync()
    {
        ;
    }

    // TODO: Firearm Type Enum ordinal bleh (replacement for soundkey and damage)
    public PacketFirearmSync(MovingObjectPosition.MovingObjectType hitType, Entity entity, int hitX, int hitY, int hitZ, double damage, String soundKey)
    {
        this.hitType = hitType.ordinal();
        this.entityId = entity != null ? entity.getEntityId() : -1;
        this.hitX = hitX;
        this.hitY = hitY;
        this.hitZ = hitZ;
        this.damage = damage;
        this.soundKey = soundKey;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hitType = buf.readInt();
        this.entityId = buf.readInt();
        this.hitX = buf.readInt();
        this.hitY = buf.readInt();
        this.hitZ = buf.readInt();
        this.damage = buf.readDouble();
        this.soundKey = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.hitType);
        buf.writeInt(this.entityId);
        buf.writeInt(this.hitX);
        buf.writeInt(this.hitY);
        buf.writeInt(this.hitZ);
        buf.writeDouble(this.damage);
        ByteBufUtils.writeUTF8String(buf, this.soundKey);
    }

    @Override
    public PacketFirearmSync onMessage(PacketFirearmSync packet, MessageContext ctx)
    {
        if (ctx.getServerHandler().playerEntity.getHeldItemMainhand() != null)
        {
            World world = ctx.getServerHandler().playerEntity.worldObj;
            MovingObjectPosition.MovingObjectType hitType = Entities.getMovingObjectType(packet.hitType);
            ItemFirearm firearm = (ItemFirearm) ctx.getServerHandler().playerEntity.getHeldItemMainhand().getItem();

            if (firearm != null && firearm.canSoundPlay())
            {
                ctx.getServerHandler().playerEntity.worldObj.playSoundAtEntity(ctx.getServerHandler().playerEntity, packet.soundKey, 0.5F, 1F);
                firearm.setLastSoundPlayed(System.currentTimeMillis());
            }

            if (hitType == MovingObjectType.ENTITY)
            {
                if (packet.entityId != -1)
                {
                    Entity entity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(packet.entityId);

                    if (entity != null)
                    {
                        entity.hurtResistantTime = 0;
                        entity.attackEntityFrom(DamageSources.causeBulletDamage(ctx.getServerHandler().playerEntity), (float) packet.damage);
                    }
                }
            }

            if (hitType == MovingObjectType.BLOCK)
            {
                int targetX = packet.hitX;
                int targetY = packet.hitY;
                int targetZ = packet.hitZ;
                int blockIndex = targetX * targetY * targetZ;
                Block target = world.getBlock(targetX, targetY, targetZ);
                float hardness = 1F / target.getBlockHardness(world, targetX, targetY, targetZ) / 100F;

                firearm.setBreakProgress(firearm.getBreakProgress() + hardness);
                
                if (blockIndex != firearm.getBlockBreakingIndex())
                {
                    firearm.setBreakProgress(0F);
                }
                
                world.destroyBlockInWorldPartially(blockIndex, targetX, targetY, targetZ, (int) (firearm.getBreakProgress() * 10.0F) - 1);

                if (firearm.getBreakProgress() >= 1F)
                {
                    
                    if (target != null && blockIndex == firearm.getBlockBreakingIndex())
                    {
                        if (target instanceof BlockHiveResin)
                        {
                            HiveHandler.breakResinAt(world, targetX, targetY, targetZ);
                        }
                        else
                        {
                            world.breakBlock(targetX, targetY, targetZ, false);
                        }
                        
                        world.playAuxSFX(2001, targetX, targetY, targetZ, Block.getIdFromBlock(target));
                        firearm.setBreakProgress(0F);
                    }
                }
                firearm.setBlockBreakingIndex(blockIndex);
            }
        }

        return null;
    }
}
