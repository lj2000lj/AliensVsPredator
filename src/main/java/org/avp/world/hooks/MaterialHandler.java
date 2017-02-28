package org.avp.world.hooks;

import org.avp.api.blocks.material.IMaterialPhysics;
import org.avp.api.blocks.material.IMaterialRenderer;

import com.arisux.mdxlib.MDX;
import com.arisux.mdxlib.lib.game.Game;


import cpw.mods.fml.common.gameevent.Tickevent.getType();
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.getEntity()ViewRenderEvent.FogColors;
import net.minecraftforge.client.event.getEntity()ViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialHandler
{
    public static final MaterialHandler instance = new MaterialHandler();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void render(RenderGameOverlayEvent event)
    {
        if (Game.minecraft().theWorld != null)
        {
            Material materialInside = getMaterialInside(Game.minecraft().thePlayer);

            if (materialInside != null && materialInside instanceof IMaterialPhysics)
            {
                IMaterialPhysics physics = (IMaterialPhysics) materialInside;
                IMaterialRenderer renderer = (IMaterialRenderer) physics.getMaterialRenderer();

                if (renderer != null)
                {
                    if (event.getType() == ElementType.HELMET)
                    {
                        if (Game.minecraft().thePlayer.isInsideOfMaterial(materialInside))
                        {
                            renderer.renderMaterialOverlay(materialInside);
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientUpdate(ClientTickEvent event)
    {
        if (Game.minecraft().theWorld != null && !Game.minecraft().isGamePaused())
        {
            this.update(Game.minecraft().theWorld);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogRenderEvent(RenderFogEvent event)
    {
        if (Game.minecraft().theWorld != null && !Game.minecraft().isGamePaused())
        {
            Material material = getMaterialInside(Game.minecraft().thePlayer);

            if (material instanceof IMaterialPhysics)
            {
                if (Game.minecraft().thePlayer.isInsideOfMaterial(material))
                {
                    IMaterialPhysics physics = (IMaterialPhysics) material;
                    IMaterialRenderer renderer = (IMaterialRenderer) physics.getMaterialRenderer();

                    if (renderer != null)
                    {
                        renderer.renderFog(material);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void fogColorUpdate(FogColors event)
    {
        if (Game.minecraft().theWorld != null && !Game.minecraft().isGamePaused())
        {
            Material material = getMaterialInside(Game.minecraft().thePlayer);

            if (material instanceof IMaterialPhysics)
            {
                if (Game.minecraft().thePlayer.isInsideOfMaterial(material))
                {
                    IMaterialPhysics physics = (IMaterialPhysics) material;
                    IMaterialRenderer renderer = (IMaterialRenderer) physics.getMaterialRenderer();

                    if (renderer != null)
                    {
                        Vec3d fogColor = renderer.getFogColor();

                        event.red = (float) fogColor.xCoord;
                        event.green = (float) fogColor.yCoord;
                        event.blue = (float) fogColor.zCoord;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(WorldTickEvent event)
    {
        if (event.getType() == Type.WORLD && event.phase == Phase.END)
        {
            this.update(event.world);
        }
    }

    private void update(World world)
    {
        for (int idx = 0; idx < world.loadedEntityList.size(); ++idx)
        {
            Entity entity = (Entity) world.loadedEntityList.get(idx);

            if (!entity.isDead)
            {
                try
                {
                    Material material = getMaterialInside(entity);

                    if (material instanceof IMaterialPhysics)
                    {
                        IMaterialPhysics physics = (IMaterialPhysics) material;
                        Vec3d motion = MaterialHandler.instance.handleMaterialAcceleration(entity, material, physics);

                        if (motion != null)
                        {
                            physics.onCollision(entity);

                            if (entity.isPushedByWater() || physics.ignoresPushableCheck())
                            {
                                motion = motion.normalize();
                                physics.handleMovement(entity);
                                physics.handleForce(entity, motion);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    MDX.log().warn("Error handling fluid physics update for entity: " + e);
                }
            }
        }
    }

    public static Material getMaterialInside(Entity entity)
    {
        AxisAlignedBB box = entity.boundingBox;
        int minX = MathHelper.floor_double(box.minX);
        int maxX = MathHelper.floor_double(box.maxX + 1.0D);
        int minY = MathHelper.floor_double(box.minY);
        int maxY = MathHelper.floor_double(box.maxY + 1.0D);
        int minZ = MathHelper.floor_double(box.minZ);
        int maxZ = MathHelper.floor_double(box.maxZ + 1.0D);

        if (!entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ))
        {
            return null;
        }
        else
        {
            for (int x = minX; x < maxX; ++x)
            {
                for (int y = minY; y < maxY; ++y)
                {
                    for (int z = minZ; z < maxZ; ++z)
                    {
                        Block block = entity.worldObj.getBlock(x, y, z);

                        if (block != null)
                        {
                            return block.getMaterial();
                        }
                    }
                }
            }
        }

        return null;
    }

    public Vec3d handleMaterialAcceleration(Entity entity, Material material, IMaterialPhysics physics)
    {
        AxisAlignedBB box = entity.boundingBox.expand(0.0D, -0.4D, 0.0D).contract(0.001D, 0.001D, 0.001D);

        int minX = MathHelper.floor_double(box.minX);
        int maxX = MathHelper.floor_double(box.maxX + 1.0D);
        int minY = MathHelper.floor_double(box.minY);
        int maxY = MathHelper.floor_double(box.maxY + 1.0D);
        int minZ = MathHelper.floor_double(box.minZ);
        int maxZ = MathHelper.floor_double(box.maxZ + 1.0D);

        if (!entity.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ))
        {
            return null;
        }
        else
        {
            Vec3d motion = null;

            for (int x = minX; x < maxX; ++x)
            {
                for (int y = minY; y < maxY; ++y)
                {
                    for (int z = minZ; z < maxZ; ++z)
                    {
                        Block block = entity.worldObj.getBlock(x, y, z);

                        if (block.getMaterial() == material)
                        {
                            double lhp = (double) ((float) (y + 1) - BlockLiquid.getLiquidHeightPercent(entity.worldObj.getBlockMetadata(x, y, z)));

                            if ((double) maxY >= lhp)
                            {
                                block.modifyEntityVelocity(entity.worldObj, x, y, z, entity, motion = new Vec3d(0.0D, 0.0D, 0.0D));
                            }
                        }
                    }
                }
            }

            return motion;
        }
    }
}
