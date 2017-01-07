package org.avp.entities.ai;

import java.util.ArrayList;

import org.avp.AliensVsPredator;
import org.avp.api.blocks.IAcidResistant;

import com.arisux.mdxlib.lib.game.GameSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumDifficulty;
import scala.actors.threadpool.Arrays;

public class EntityAIMeltBlock extends EntityAIYOffsetBlockInteract
{
    private static final ArrayList<Block> blockBlacklist = new ArrayList<Block>();
    private EntityLiving theEntity;
    private int          breakingTime;
    private int          breakProgress = -1;

    public EntityAIMeltBlock(EntityLiving theEntity)
    {
        this(theEntity, 0);
    }

    public EntityAIMeltBlock(EntityLiving theEntity, int yOffset)
    {
        super(theEntity, yOffset);
        this.theEntity = theEntity;
        this.yOffset = yOffset;
        blacklist(Blocks.obsidian);
        blacklist(Blocks.bedrock);
        blacklist(Blocks.end_portal_frame);
        blacklist(AliensVsPredator.blocks().plasticShapes);
        blacklist(AliensVsPredator.blocks().plasticCircleShapes);
        blacklist(AliensVsPredator.blocks().plasticTileShapes);
        blacklist(AliensVsPredator.blocks().plasticTriShapes);
        blacklist(AliensVsPredator.blocks().engineerShipBrick0Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipBrick1Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipBrick2Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipBrick3Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipColumn1Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipColumn2Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipFloorShapes);
        blacklist(AliensVsPredator.blocks().engineerShipGravelShapes);
        blacklist(AliensVsPredator.blocks().engineerShipMaterial0Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipMaterial1Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipMaterial2Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipRock0Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipRock1Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipRock2Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipRock3Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipWall0Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipWall1Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipWall2Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipWall3Shapes);
        blacklist(AliensVsPredator.blocks().engineerShipWall4Shapes);
    }
    
    public static Block blacklist(Block block)
    {
        blockBlacklist.add(block);
        return block;
    }

    public static Block[] blacklist(Block[] blocks)
    {
        for (Block block : new ArrayList<Block>(Arrays.asList(blocks)))
        {
            blockBlacklist.add(block);
            
        }
        return blocks;
    }

    @Override
    public boolean shouldExecute()
    {
        return this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
    }

    @Override
    public void startExecuting()
    {
        super.startExecuting();
        this.breakingTime = 0;
    }

    @Override
    public boolean continueExecuting()
    {
        return this.breakingTime <= (12 * 20) && this.theEntity.getDistanceSq((int) this.theEntity.posX, (int) this.theEntity.posY + yOffset, (int) this.theEntity.posZ) < 4.0D && block != Blocks.air && block != AliensVsPredator.blocks().terrainHiveResin && block != Blocks.bedrock;
    }

    @Override
    public void resetTask()
    {
        super.resetTask();
        this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), (int) this.theEntity.posX, (int) this.theEntity.posY + yOffset, (int) this.theEntity.posZ, -1);
    }

    @Override
    public void updateTask()
    {
        super.updateTask();

        int targetX = (int) Math.floor(this.theEntity.posX);
        int targetY = (int) this.theEntity.posY - 1;
        int targetZ = (int) Math.floor(this.theEntity.posZ);
        Block target = this.theEntity.worldObj.getBlock(targetX, targetY, targetZ);

        if (this.theEntity.getRNG().nextInt(20) == 0)
        {
            GameSounds.fxMinecraftFizz.playSound(this.theEntity.worldObj, this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ);
        }
        
        if (blockBlacklist.contains(target) || target instanceof IAcidResistant && ((IAcidResistant) target).canAcidDestroy(this.theEntity.worldObj, targetX, targetY, targetZ, this.theEntity))
        {
            return;
        }

        this.breakingTime++;

        int progress = (int) (this.breakingTime / 240.0F * 10.0F);

        if (progress != this.breakProgress)
        {
            this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), (int) Math.floor(this.theEntity.posX), (int) this.theEntity.posY + yOffset, (int) Math.floor(this.theEntity.posZ), progress);
            this.breakProgress = progress;
        }

        if (this.breakingTime == 240 && (this.theEntity.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.theEntity.worldObj.difficultySetting == EnumDifficulty.HARD))
        {
            if (block != Blocks.air)
            {
                this.theEntity.worldObj.setBlockToAir(targetX, targetY, targetZ);
                this.theEntity.worldObj.playAuxSFX(2001, (int) Math.floor(this.theEntity.posX), (int) this.theEntity.posY + yOffset, (int) Math.floor(this.theEntity.posZ), Block.getIdFromBlock(this.block));
                this.resetTask();
                this.theEntity = (EntityLiving) this.theEntity.worldObj.getEntityByID(this.theEntity.getEntityId());
            }
        }
    }
}
