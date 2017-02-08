package org.avp;

import static cpw.mods.fml.client.registry.ClientRegistry.bindTileEntitySpecialRenderer;
import static cpw.mods.fml.client.registry.RenderingRegistry.registerBlockHandler;
import static cpw.mods.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;
import static net.minecraftforge.client.MinecraftForgeClient.registerItemRenderer;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

import org.avp.ItemHandler.Summoners;
import org.avp.block.render.RenderResin;
import org.avp.block.render.RenderShape;
import org.avp.entities.EntityAPC;
import org.avp.entities.EntityAcidPool;
import org.avp.entities.EntityAcidProjectile;
import org.avp.entities.EntityBullet;
import org.avp.entities.EntityFlame;
import org.avp.entities.EntityGrenade;
import org.avp.entities.EntityLaserMine;
import org.avp.entities.EntityLiquidLatexPool;
import org.avp.entities.EntityMechanism;
import org.avp.entities.EntityMedpod;
import org.avp.entities.EntityNuke;
import org.avp.entities.EntityPlasma;
import org.avp.entities.EntityShuriken;
import org.avp.entities.EntitySmartDisc;
import org.avp.entities.EntitySpear;
import org.avp.entities.EntitySupplyChute;
import org.avp.entities.EntitySupplyChuteMarines;
import org.avp.entities.EntitySupplyChuteSeegson;
import org.avp.entities.mob.EntityAethon;
import org.avp.entities.mob.EntityAqua;
import org.avp.entities.mob.EntityBelugaburster;
import org.avp.entities.mob.EntityBelugamorph;
import org.avp.entities.mob.EntityChestburster;
import org.avp.entities.mob.EntityCombatSynthetic;
import org.avp.entities.mob.EntityCrusher;
import org.avp.entities.mob.EntityDeacon;
import org.avp.entities.mob.EntityDeaconShark;
import org.avp.entities.mob.EntityDrone;
import org.avp.entities.mob.EntityEngineer;
import org.avp.entities.mob.EntityFacehugger;
import org.avp.entities.mob.EntityGooMutant;
import org.avp.entities.mob.EntityHammerpede;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.EntityOctohugger;
import org.avp.entities.mob.EntityOvamorph;
import org.avp.entities.mob.EntityPraetorian;
import org.avp.entities.mob.EntityPredalien;
import org.avp.entities.mob.EntityPredalienChestburster;
import org.avp.entities.mob.EntityQueen;
import org.avp.entities.mob.EntityQueenChestburster;
import org.avp.entities.mob.EntityRoyalFacehugger;
import org.avp.entities.mob.EntityRunnerChestburster;
import org.avp.entities.mob.EntityRunnerDrone;
import org.avp.entities.mob.EntityRunnerWarrior;
import org.avp.entities.mob.EntitySpaceJockey;
import org.avp.entities.mob.EntitySpeciesYautja;
import org.avp.entities.mob.EntitySpitter;
import org.avp.entities.mob.EntityTrilobite;
import org.avp.entities.mob.EntityUltramorph;
import org.avp.entities.mob.EntityWarrior;
import org.avp.entities.mob.EntityXenomorph;
import org.avp.entities.mob.EntityYautja;
import org.avp.entities.mob.EntityYautjaBerserker;
import org.avp.entities.mob.render.RenderAethon;
import org.avp.entities.mob.render.RenderAqua;
import org.avp.entities.mob.render.RenderBelugaburster;
import org.avp.entities.mob.render.RenderChestburster;
import org.avp.entities.mob.render.RenderCombatSynthetic;
import org.avp.entities.mob.render.RenderDeaconShark;
import org.avp.entities.mob.render.RenderEngineer;
import org.avp.entities.mob.render.RenderFacehugger;
import org.avp.entities.mob.render.RenderGooMutant;
import org.avp.entities.mob.render.RenderHammerpede;
import org.avp.entities.mob.render.RenderMarine;
import org.avp.entities.mob.render.RenderOctohugger;
import org.avp.entities.mob.render.RenderOvamorph;
import org.avp.entities.mob.render.RenderPredalien;
import org.avp.entities.mob.render.RenderPredalienChestburster;
import org.avp.entities.mob.render.RenderQueen;
import org.avp.entities.mob.render.RenderQueenChestburster;
import org.avp.entities.mob.render.RenderRoyalFacehugger;
import org.avp.entities.mob.render.RenderRunnerChestburster;
import org.avp.entities.mob.render.RenderSpitter;
import org.avp.entities.mob.render.RenderTrilobite;
import org.avp.entities.mob.render.RenderXenomorph;
import org.avp.entities.mob.render.RenderYautja;
import org.avp.entities.mob.render.RenderYautjaBerserker;
import org.avp.entities.render.RenderAPC;
import org.avp.entities.render.RenderAcidPool;
import org.avp.entities.render.RenderAcidSpit;
import org.avp.entities.render.RenderBullet;
import org.avp.entities.render.RenderDisc;
import org.avp.entities.render.RenderFlame;
import org.avp.entities.render.RenderGrenade;
import org.avp.entities.render.RenderLaserMine;
import org.avp.entities.render.RenderLiquidLatexPool;
import org.avp.entities.render.RenderMechanism;
import org.avp.entities.render.RenderMedpodEntity;
import org.avp.entities.render.RenderNuke;
import org.avp.entities.render.RenderPlasmaBlast;
import org.avp.entities.render.RenderShuriken;
import org.avp.entities.render.RenderSpear;
import org.avp.entities.render.RenderSupplyChute;
import org.avp.entities.tile.TileEntityAmpule;
import org.avp.entities.tile.TileEntityAssembler;
import org.avp.entities.tile.TileEntityBlastdoor;
import org.avp.entities.tile.TileEntityCryostasisTube;
import org.avp.entities.tile.TileEntityGunLocker;
import org.avp.entities.tile.TileEntityHiveResin;
import org.avp.entities.tile.TileEntityLightPanel;
import org.avp.entities.tile.TileEntityLocker;
import org.avp.entities.tile.TileEntityMedpod;
import org.avp.entities.tile.TileEntityNegativeTransformer;
import org.avp.entities.tile.TileEntityPowercell;
import org.avp.entities.tile.TileEntityPowerline;
import org.avp.entities.tile.TileEntityRedstoneEmitter;
import org.avp.entities.tile.TileEntityRedstoneFluxGenerator;
import org.avp.entities.tile.TileEntityRedstoneSensor;
import org.avp.entities.tile.TileEntityRepulsionGenerator;
import org.avp.entities.tile.TileEntitySatelliteDish;
import org.avp.entities.tile.TileEntitySatelliteModem;
import org.avp.entities.tile.TileEntitySkull;
import org.avp.entities.tile.TileEntitySolarPanel;
import org.avp.entities.tile.TileEntityStasisMechanism;
import org.avp.entities.tile.TileEntitySupplyCrate;
import org.avp.entities.tile.TileEntityTransformer;
import org.avp.entities.tile.TileEntityTurret;
import org.avp.entities.tile.TileEntityWorkstation;
import org.avp.entities.tile.render.RenderAmpule;
import org.avp.entities.tile.render.RenderAssembler;
import org.avp.entities.tile.render.RenderBlastdoor;
import org.avp.entities.tile.render.RenderCryostasisTube;
import org.avp.entities.tile.render.RenderCryostasisTube.CryostasisEntityRenderer;
import org.avp.entities.tile.render.RenderGunLocker;
import org.avp.entities.tile.render.RenderHiveResin;
import org.avp.entities.tile.render.RenderLightPanel;
import org.avp.entities.tile.render.RenderLocker;
import org.avp.entities.tile.render.RenderMedpod;
import org.avp.entities.tile.render.RenderPowercell;
import org.avp.entities.tile.render.RenderPowerline;
import org.avp.entities.tile.render.RenderRedstoneEmitter;
import org.avp.entities.tile.render.RenderRedstoneFluxGenerator;
import org.avp.entities.tile.render.RenderRedstoneSensor;
import org.avp.entities.tile.render.RenderRepulsionGenerator;
import org.avp.entities.tile.render.RenderSatelliteDish;
import org.avp.entities.tile.render.RenderSatelliteModem;
import org.avp.entities.tile.render.RenderSkull;
import org.avp.entities.tile.render.RenderSolarPanel;
import org.avp.entities.tile.render.RenderStasisMechanism;
import org.avp.entities.tile.render.RenderSupplyCrate;
import org.avp.entities.tile.render.RenderTransformer;
import org.avp.entities.tile.render.RenderTurret;
import org.avp.entities.tile.render.RenderWorkstation;
import org.avp.items.model.Model88MOD4;
import org.avp.items.model.ModelAK47;
import org.avp.items.model.ModelM4;
import org.avp.items.model.ModelM41A;
import org.avp.items.model.ModelM56SG;
import org.avp.items.model.ModelSniper;
import org.avp.items.render.RenderItem88MOD4;
import org.avp.items.render.RenderItemAK47;
import org.avp.items.render.RenderItemAPC;
import org.avp.items.render.RenderItemAmpule;
import org.avp.items.render.RenderItemBlastDoor;
import org.avp.items.render.RenderItemCryostasisTube;
import org.avp.items.render.RenderItemGunLocker;
import org.avp.items.render.RenderItemLightPanel;
import org.avp.items.render.RenderItemLocker;
import org.avp.items.render.RenderItemM240ICU;
import org.avp.items.render.RenderItemM4;
import org.avp.items.render.RenderItemM40;
import org.avp.items.render.RenderItemM41A;
import org.avp.items.render.RenderItemM56SG;
import org.avp.items.render.RenderItemMedpod;
import org.avp.items.render.RenderItemMotionTracker;
import org.avp.items.render.RenderItemNostromoFlamethrower;
import org.avp.items.render.RenderItemPlasmaCannon;
import org.avp.items.render.RenderItemPowercell;
import org.avp.items.render.RenderItemPowerline;
import org.avp.items.render.RenderItemRepulsionGenerator;
import org.avp.items.render.RenderItemSatelliteDish;
import org.avp.items.render.RenderItemSkull;
import org.avp.items.render.RenderItemSniper;
import org.avp.items.render.RenderItemSolarPanel;
import org.avp.items.render.RenderItemSpear;
import org.avp.items.render.RenderItemStasisMechanism;
import org.avp.items.render.RenderItemSummoner;
import org.avp.items.render.RenderItemSupplyChute;
import org.avp.items.render.RenderItemSupplyCrate;
import org.avp.items.render.RenderItemTransformer;
import org.avp.items.render.RenderItemTurret;
import org.avp.items.render.RenderItemWorkstation;
import org.avp.items.render.RenderItemWristbracer;
import org.avp.items.render.RenderItemWristbracerBlades;
import org.avp.items.render.parts.RenderItem88Mod4Action;
import org.avp.items.render.parts.RenderItem88Mod4Barrel;
import org.avp.items.render.parts.RenderItem88Mod4Stock;
import org.avp.items.render.parts.RenderItemAK47Action;
import org.avp.items.render.parts.RenderItemAK47Barrel;
import org.avp.items.render.parts.RenderItemAK47Stock;
import org.avp.items.render.parts.RenderItemM41AAction;
import org.avp.items.render.parts.RenderItemM41ABarrel;
import org.avp.items.render.parts.RenderItemM41APeripherals;
import org.avp.items.render.parts.RenderItemM41AStock;
import org.avp.items.render.parts.RenderItemM4Action;
import org.avp.items.render.parts.RenderItemM4Barrel;
import org.avp.items.render.parts.RenderItemM4Stock;
import org.avp.items.render.parts.RenderItemM56SGAction;
import org.avp.items.render.parts.RenderItemM56SGAimingModule;
import org.avp.items.render.parts.RenderItemM56SGBarrel;
import org.avp.items.render.parts.RenderItemM56SGStock;
import org.avp.items.render.parts.RenderItemM56SGSupportFrame;
import org.avp.items.render.parts.RenderItemSniperAction;
import org.avp.items.render.parts.RenderItemSniperBarrel;
import org.avp.items.render.parts.RenderItemSniperPeripherals;
import org.avp.items.render.parts.RenderItemSniperScope;
import org.avp.items.render.parts.RenderItemSniperStock;
import org.avp.util.EntityRenderTransforms;
import org.lwjgl.opengl.GL12;

import com.arisux.mdxlib.lib.client.TexturedModel;
import com.arisux.mdxlib.lib.client.render.Draw;
import com.arisux.mdxlib.lib.client.render.OpenGL;
import com.arisux.mdxlib.lib.game.IPostInitEvent;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

@SideOnly(Side.CLIENT)
public class Renderers implements IPostInitEvent
{
    public static Renderers instance = new Renderers();

    @Override
    public void post(FMLPostInitializationEvent event)
    {
        registerSimpleBlockRenderingHandlers();
        registerTileEntitySpecialRenderers();
        registerItemRenderers(AliensVsPredator.items());
        registerLivingEntityRenderers();
        registerStandardEntityRenderers();
        registerCryostasisTubeRenderers();
        registerMedpodTransformations();
        registerEntityFaceTransformations();
    }

    private void registerLivingEntityRenderers()
    {
        registerEntityRenderingHandler(EntityEngineer.class, new RenderEngineer());
        registerEntityRenderingHandler(EntitySpaceJockey.class, new RenderEngineer(AliensVsPredator.resources().models().SPACE_JOCKEY));
        registerEntityRenderingHandler(EntityYautjaBerserker.class, new RenderYautjaBerserker());
        registerEntityRenderingHandler(EntityTrilobite.class, new RenderTrilobite());
        registerEntityRenderingHandler(EntityHammerpede.class, new RenderHammerpede());
        registerEntityRenderingHandler(EntityDeacon.class, new RenderXenomorph(AliensVsPredator.resources().models().PROTOMORPH, 1.4F));
        registerEntityRenderingHandler(EntityDrone.class, new RenderXenomorph(AliensVsPredator.resources().models().DRONE_ADVANCED, 0.9F));
        registerEntityRenderingHandler(EntityWarrior.class, new RenderXenomorph(AliensVsPredator.resources().models().WARRIOR, 1F));
        registerEntityRenderingHandler(EntityPraetorian.class, new RenderXenomorph(AliensVsPredator.resources().models().PRAETORIAN, 1.4F));
        registerEntityRenderingHandler(EntityRunnerDrone.class, new RenderXenomorph(AliensVsPredator.resources().models().RUNNER_DRONE, 0.9F));
        registerEntityRenderingHandler(EntityRunnerWarrior.class, new RenderXenomorph(AliensVsPredator.resources().models().RUNNER_WARRIOR, 1F));
        registerEntityRenderingHandler(EntityCrusher.class, new RenderXenomorph(AliensVsPredator.resources().models().CRUSHER, 1F));
        registerEntityRenderingHandler(EntityAqua.class, new RenderAqua());
        registerEntityRenderingHandler(EntityPredalien.class, new RenderPredalien());
        registerEntityRenderingHandler(EntitySpitter.class, new RenderSpitter());
        registerEntityRenderingHandler(EntityMarine.class, new RenderMarine());
        registerEntityRenderingHandler(EntityCombatSynthetic.class, new RenderCombatSynthetic());
        registerEntityRenderingHandler(EntityYautja.class, new RenderYautja());
        registerEntityRenderingHandler(EntityQueen.class, new RenderQueen());
        registerEntityRenderingHandler(EntityFacehugger.class, new RenderFacehugger());
        registerEntityRenderingHandler(EntityRoyalFacehugger.class, new RenderRoyalFacehugger());
        registerEntityRenderingHandler(EntityChestburster.class, new RenderChestburster());
        registerEntityRenderingHandler(EntityOvamorph.class, new RenderOvamorph());
        registerEntityRenderingHandler(EntityDeaconShark.class, new RenderDeaconShark());
        registerEntityRenderingHandler(EntityUltramorph.class,  new RenderXenomorph(AliensVsPredator.resources().models().ULTRAMORPH, 1.5F));
        registerEntityRenderingHandler(EntityGooMutant.class, new RenderGooMutant());
        registerEntityRenderingHandler(EntityAethon.class, new RenderAethon());
        registerEntityRenderingHandler(EntityOctohugger.class, new RenderOctohugger());
        registerEntityRenderingHandler(EntityBelugaburster.class, new RenderBelugaburster());
        registerEntityRenderingHandler(EntityBelugamorph.class,  new RenderXenomorph(AliensVsPredator.resources().models().BELUGAMORPH, 1F));
        registerEntityRenderingHandler(EntityPredalienChestburster.class, new RenderPredalienChestburster());
        registerEntityRenderingHandler(EntityQueenChestburster.class, new RenderQueenChestburster());
        registerEntityRenderingHandler(EntityRunnerChestburster.class, new RenderRunnerChestburster());
    }

    private void registerStandardEntityRenderers()
    {
        registerEntityRenderingHandler(EntitySpear.class, new RenderSpear());
        registerEntityRenderingHandler(EntityLaserMine.class, new RenderLaserMine());
        registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
        registerEntityRenderingHandler(EntityFlame.class, new RenderFlame());
        registerEntityRenderingHandler(EntityAcidPool.class, new RenderAcidPool());
        registerEntityRenderingHandler(EntityLiquidLatexPool.class, new RenderLiquidLatexPool());
        registerEntityRenderingHandler(EntityPlasma.class, new RenderPlasmaBlast());
        registerEntityRenderingHandler(EntityAcidProjectile.class, new RenderAcidSpit());
        registerEntityRenderingHandler(EntitySmartDisc.class, new RenderDisc());
        registerEntityRenderingHandler(EntityShuriken.class, new RenderShuriken());
        registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
        registerEntityRenderingHandler(EntityNuke.class, new RenderNuke());
        registerEntityRenderingHandler(EntityAPC.class, new RenderAPC());
        registerEntityRenderingHandler(EntityMechanism.class, new RenderMechanism());
        registerEntityRenderingHandler(EntityMedpod.class, new RenderMedpodEntity());
        registerEntityRenderingHandler(EntitySupplyChute.class, new RenderSupplyChute());
        registerEntityRenderingHandler(EntitySupplyChuteMarines.class, new RenderSupplyChute());
        registerEntityRenderingHandler(EntitySupplyChuteSeegson.class, new RenderSupplyChute());
    }

    private void registerItemRenderers(ItemHandler items)
    {
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSkullEngineer), new RenderItemSkull());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSkullSpaceJockey), new RenderItemSkull());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSkullXenomorph), new RenderItemSkull());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSkullXenomorphWarrior), new RenderItemSkull());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSkullYautja), new RenderItemSkull());
        
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockTurret), new RenderItemTurret());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockWorkstation), new RenderItemWorkstation());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockStasisMechanism), new RenderItemStasisMechanism());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockCryostasisTube), new RenderItemCryostasisTube());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockRepulsionGenerator), new RenderItemRepulsionGenerator());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockBlastdoor), new RenderItemBlastDoor());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockLightPanel), new RenderItemLightPanel());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockPowerline), new RenderItemPowerline());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSolarPanel), new RenderItemSolarPanel());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockPowercell), new RenderItemPowercell());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockTransformer), new RenderItemTransformer());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockNegativeTransformer), new RenderItemTransformer());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockAmpule), new RenderItemAmpule());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockLocker), new RenderItemLocker());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockGunLocker), new RenderItemGunLocker());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockMedpod), new RenderItemMedpod());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().blockSatelliteDish), new RenderItemSatelliteDish());
        registerItemRenderer(items.itemWristbracer, new RenderItemWristbracer());
        registerItemRenderer(items.itemWristbracerBlades, new RenderItemWristbracerBlades());
        registerItemRenderer(items.itemPlasmaCannon, new RenderItemPlasmaCannon());
        registerItemRenderer(items.itemSpear, new RenderItemSpear());
        registerItemRenderer(items.itemM240ICU, new RenderItemM240ICU());
        registerItemRenderer(items.itemNostromoFlamethrower, new RenderItemNostromoFlamethrower());
        registerItemRenderer(items.itemM41A, new RenderItemM41A());
        registerItemRenderer(items.itemM56SG, new RenderItemM56SG());
        registerItemRenderer(items.itemAK47, new RenderItemAK47());
        registerItemRenderer(items.itemM4, new RenderItemM4());
        registerItemRenderer(items.itemPistol, new RenderItem88MOD4());
        registerItemRenderer(items.itemSniper, new RenderItemSniper());
        registerItemRenderer(items.itemMotionTracker, new RenderItemMotionTracker());
        registerItemRenderer(items.itemAPC, new RenderItemAPC());
        registerItemRenderer(items.itemGrenade, new RenderItemM40(AliensVsPredator.resources().models().M40GRENADE));
        registerItemRenderer(items.itemIncendiaryGrenade, new RenderItemM40(AliensVsPredator.resources().models().M40GRENADE_INCENDIARY));

        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().supplyCrate), new RenderItemSupplyCrate());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().supplyCrateMarines), new RenderItemSupplyCrate());
        registerItemRenderer(Item.getItemFromBlock(AliensVsPredator.blocks().supplyCrateSeegson), new RenderItemSupplyCrate());
        registerItemRenderer(items.itemSupplyChute, new RenderItemSupplyChute());
        registerItemRenderer(items.itemSupplyChuteMarines, new RenderItemSupplyChute());
        registerItemRenderer(items.itemSupplyChuteSeegson, new RenderItemSupplyChute());
        
        Summoners summoners = items.summoners;
        
        registerItemRenderer(summoners.itemSummonerDrone, (new RenderItemSummoner(AliensVsPredator.resources().models().DRONE_ADVANCED)).setScale(7.5F).setY(6F));
        registerItemRenderer(summoners.itemSummonerProtomorph, (new RenderItemSummoner(AliensVsPredator.resources().models().PROTOMORPH)).setScale(16F).setY(0F));
        registerItemRenderer(summoners.itemSummonerWarrior, (new RenderItemSummoner(AliensVsPredator.resources().models().WARRIOR)).setScale(7.5F).setY(9F));
        registerItemRenderer(summoners.itemSummonerRunnerDrone, (new RenderItemSummoner(AliensVsPredator.resources().models().RUNNER_DRONE)).setScale(7.5F).setY(6F));
        registerItemRenderer(summoners.itemSummonerRunnerWarrior, (new RenderItemSummoner(AliensVsPredator.resources().models().RUNNER_WARRIOR)).setScale(7.5F).setY(9F));
        registerItemRenderer(summoners.itemSummonerPraetorian, (new RenderItemSummoner(AliensVsPredator.resources().models().PRAETORIAN)).setScale(7.5F).setY(7.5F));
        registerItemRenderer(summoners.itemSummonerSpitter, (new RenderItemSummoner(AliensVsPredator.resources().models().SPITTER)).setScale(7.5F).setY(9F));
        registerItemRenderer(summoners.itemSummonerCrusher, (new RenderItemSummoner(AliensVsPredator.resources().models().CRUSHER)).setScale(7.5F).setY(9.5F));
        registerItemRenderer(summoners.itemSummonerQueen, (new RenderItemSummoner(AliensVsPredator.resources().models().XENOQUEEN)).setScale(7.5F).setY(8F));
        registerItemRenderer(summoners.itemSummonerOvamorph, (new RenderItemSummoner(AliensVsPredator.resources().models().OVAMORPH)).setScale(20F).setY(-26F));
        registerItemRenderer(summoners.itemSummonerChestburster, (new RenderItemSummoner(AliensVsPredator.resources().models().CHESTBUSTER)).setScale(9F).setY(1F));
        registerItemRenderer(summoners.itemSummonerFacehugger, (new RenderItemSummoner(AliensVsPredator.resources().models().FACEHUGGER)).setScale(15F).setY(-18F));
        registerItemRenderer(summoners.itemSummonerRoyalFacehugger, (new RenderItemSummoner(AliensVsPredator.resources().models().ROYALFACEHUGGER)).setScale(15F).setY(-16F));
        registerItemRenderer(summoners.itemSummonerMarine, (new RenderItemSummoner(AliensVsPredator.resources().models().MARINE)).setScale(10F).setY(3F));
        registerItemRenderer(summoners.itemSummonerYautja, (new RenderItemSummoner(AliensVsPredator.resources().models().YAUTJA)).setScale(7.5F).setY(8F));
        registerItemRenderer(summoners.itemSummonerPredalien, (new RenderItemSummoner(AliensVsPredator.resources().models().PREDALIEN)).setScale(8F).setY(6F));
        registerItemRenderer(summoners.itemSummonerAqua, (new RenderItemSummoner(AliensVsPredator.resources().models().AQUA_XENOMORPH)).setScale(7.5F).setY(8F));
        registerItemRenderer(summoners.itemSummonerCombatSynthetic, (new RenderItemSummoner(AliensVsPredator.resources().models().COMBAT_SYNTHETIC)).setScale(16F).setY(-12F));
        registerItemRenderer(summoners.itemSummonerHammerpede, (new RenderItemSummoner(AliensVsPredator.resources().models().HAMMERPEDE)).setScale(10.5F).setY(-4F));
        registerItemRenderer(summoners.itemSummonerTrilobite, (new RenderItemSummoner(AliensVsPredator.resources().models().TRILOBITE)).setScale(8F).setY(4F));
        registerItemRenderer(summoners.itemSummonerSpaceJockey, (new RenderItemSummoner(AliensVsPredator.resources().models().SPACE_JOCKEY)).setScale(10F).setY(0F));
        registerItemRenderer(summoners.itemSummonerEngineer, (new RenderItemSummoner(AliensVsPredator.resources().models().ENGINEER)).setScale(10F).setY(0F));
        registerItemRenderer(summoners.itemSummonerYautjaBerserker, (new RenderItemSummoner(AliensVsPredator.resources().models().YAUTJA_BERSERKER)).setScale(7.5F).setY(8F));
        registerItemRenderer(summoners.itemSummonerDeaconShark, (new RenderItemSummoner(AliensVsPredator.resources().models().DEACON_SHARK)).setScale(7.5F).setY(8F));
        registerItemRenderer(summoners.itemSummonerUltramorph, (new RenderItemSummoner(AliensVsPredator.resources().models().ULTRAMORPH)).setScale(7.5F).setY(6F));
        registerItemRenderer(summoners.itemSummonerGooMutant, (new RenderItemSummoner(AliensVsPredator.resources().models().GOO_MUTANT)).setScale(10F).setY(3F));
        registerItemRenderer(summoners.itemSummonerAethon, (new RenderItemSummoner(AliensVsPredator.resources().models().AETHON)).setScale(7.5F).setY(6F));
        registerItemRenderer(summoners.itemSummonerOctohugger, (new RenderItemSummoner(AliensVsPredator.resources().models().OCTOHUGGER)).setScale(24F).setY(-10F));
        registerItemRenderer(summoners.itemSummonerBelugaburster, (new RenderItemSummoner(AliensVsPredator.resources().models().BELUGABURSTER)).setScale(9F).setY(1F));
        registerItemRenderer(summoners.itemSummonerBelugamorph, (new RenderItemSummoner(AliensVsPredator.resources().models().BELUGAMORPH)).setScale(7.5F).setY(6F));
        registerItemRenderer(summoners.itemSummonerPredalienChestburster, (new RenderItemSummoner(AliensVsPredator.resources().models().CHESTBUSTER_PREDALIEN)).setScale(9F).setY(1F));
        registerItemRenderer(summoners.itemSummonerQueenChestburster, (new RenderItemSummoner(AliensVsPredator.resources().models().CHESTBUSTER_QUEEN)).setScale(9F).setY(1F));
        registerItemRenderer(summoners.itemSummonerRunnerChestburster, (new RenderItemSummoner(AliensVsPredator.resources().models().CHESTBUSTER_RUNNER)).setScale(9F).setY(1F));

        TexturedModel<Model88MOD4> _88MOD4 = AliensVsPredator.resources().models()._88MOD4;
        registerItemRenderer(items.itemPistolBarrel, new RenderItem88Mod4Barrel(_88MOD4, _88MOD4.getModel().getBarrel()));
        registerItemRenderer(items.itemPistolAction, new RenderItem88Mod4Action(_88MOD4, _88MOD4.getModel().getAction()));
        registerItemRenderer(items.itemPistolStock, new RenderItem88Mod4Stock(_88MOD4, _88MOD4.getModel().getStock()));

        TexturedModel<ModelAK47> AK47 = AliensVsPredator.resources().models().AK47;
        registerItemRenderer(items.itemAK47Barrel, new RenderItemAK47Barrel(AK47, AK47.getModel().getBarrel()));
        registerItemRenderer(items.itemAK47Action, new RenderItemAK47Action(AK47, AK47.getModel().getAction()));
        registerItemRenderer(items.itemAK47Stock, new RenderItemAK47Stock(AK47, AK47.getModel().getStock()));

        TexturedModel<ModelM4> M4 = AliensVsPredator.resources().models().M4;
        registerItemRenderer(items.itemM4Barrel, new RenderItemM4Barrel(M4, M4.getModel().getBarrel()));
        registerItemRenderer(items.itemM4Action, new RenderItemM4Action(M4, M4.getModel().getAction()));
        registerItemRenderer(items.itemM4Stock, new RenderItemM4Stock(M4, M4.getModel().getStock()));

        TexturedModel<ModelM56SG> M56SG = AliensVsPredator.resources().models().M56SG;
        registerItemRenderer(items.itemM56SGAction, new RenderItemM56SGAction(M56SG, M56SG.getModel().getAction()));
        registerItemRenderer(items.itemM56SGAimingModule, new RenderItemM56SGAimingModule(M56SG, M56SG.getModel().getAccessories()));
        registerItemRenderer(items.itemM56SGBarrel, new RenderItemM56SGBarrel(M56SG, M56SG.getModel().getBarrel()));
        registerItemRenderer(items.itemM56SGStock, new RenderItemM56SGStock(M56SG, M56SG.getModel().getStock()));
        registerItemRenderer(items.itemM56SGSupportFrame, new RenderItemM56SGSupportFrame(M56SG, M56SG.getModel().getPeripherals()));

        TexturedModel<ModelM41A> M41A = AliensVsPredator.resources().models().M41A;
        registerItemRenderer(items.itemM41AAction, new RenderItemM41AAction(M41A, M41A.getModel().getAction()));
        registerItemRenderer(items.itemM41ABarrel, new RenderItemM41ABarrel(M41A, M41A.getModel().getBarrel()));
        registerItemRenderer(items.itemM41AStock, new RenderItemM41AStock(M41A, M41A.getModel().getStock()));
        registerItemRenderer(items.itemM41APeripherals, new RenderItemM41APeripherals(M41A, M41A.getModel().getPeripherals()));

        TexturedModel<ModelSniper> SNIPER = AliensVsPredator.resources().models().SNIPER;
        registerItemRenderer(items.itemSniperBarrel, new RenderItemSniperBarrel(SNIPER, SNIPER.getModel().getBarrel()));
        registerItemRenderer(items.itemSniperAction, new RenderItemSniperAction(SNIPER, SNIPER.getModel().getAction()));
        registerItemRenderer(items.itemSniperScope, new RenderItemSniperScope(SNIPER, SNIPER.getModel().getScope()));
        registerItemRenderer(items.itemSniperStock, new RenderItemSniperStock(SNIPER, SNIPER.getModel().getStock()));
        registerItemRenderer(items.itemSniperPeripherals, new RenderItemSniperPeripherals(SNIPER, SNIPER.getModel().getPeripherals()));
    }

    private void registerTileEntitySpecialRenderers()
    {
        bindTileEntitySpecialRenderer(TileEntityTurret.class, new RenderTurret());
        bindTileEntitySpecialRenderer(TileEntityWorkstation.class, new RenderWorkstation());
        bindTileEntitySpecialRenderer(TileEntityStasisMechanism.class, new RenderStasisMechanism());
        bindTileEntitySpecialRenderer(TileEntityPowerline.class, new RenderPowerline());
        bindTileEntitySpecialRenderer(TileEntityBlastdoor.class, new RenderBlastdoor());
        bindTileEntitySpecialRenderer(TileEntityCryostasisTube.class, new RenderCryostasisTube());
        bindTileEntitySpecialRenderer(TileEntityRepulsionGenerator.class, new RenderRepulsionGenerator());
        bindTileEntitySpecialRenderer(TileEntityAssembler.class, new RenderAssembler());
        bindTileEntitySpecialRenderer(TileEntityLightPanel.class, new RenderLightPanel());
        bindTileEntitySpecialRenderer(TileEntitySolarPanel.class, new RenderSolarPanel());
        bindTileEntitySpecialRenderer(TileEntitySatelliteModem.class, new RenderSatelliteModem());
        bindTileEntitySpecialRenderer(TileEntityTransformer.class, new RenderTransformer());
        bindTileEntitySpecialRenderer(TileEntityNegativeTransformer.class, new RenderTransformer());
        bindTileEntitySpecialRenderer(TileEntityRedstoneSensor.class, new RenderRedstoneSensor());
        bindTileEntitySpecialRenderer(TileEntityRedstoneEmitter.class, new RenderRedstoneEmitter());
        bindTileEntitySpecialRenderer(TileEntityRedstoneFluxGenerator.class, new RenderRedstoneFluxGenerator());
        bindTileEntitySpecialRenderer(TileEntityPowercell.class, new RenderPowercell());
        bindTileEntitySpecialRenderer(TileEntityAmpule.class, new RenderAmpule());
        bindTileEntitySpecialRenderer(TileEntityLocker.class, new RenderLocker());
        bindTileEntitySpecialRenderer(TileEntityGunLocker.class, new RenderGunLocker());
        bindTileEntitySpecialRenderer(TileEntityMedpod.class, new RenderMedpod());
        bindTileEntitySpecialRenderer(TileEntitySatelliteDish.class, new RenderSatelliteDish());
        bindTileEntitySpecialRenderer(TileEntitySupplyCrate.class, new RenderSupplyCrate());
        bindTileEntitySpecialRenderer(TileEntityHiveResin.class, new RenderHiveResin());
        bindTileEntitySpecialRenderer(TileEntitySkull.class, new RenderSkull());
    }

    private void registerSimpleBlockRenderingHandlers()
    {
        registerBlockHandler(new RenderShape(AliensVsPredator.renderTypes().RENDER_TYPE_SHAPED));
        registerBlockHandler(new RenderResin(AliensVsPredator.renderTypes().RENDER_TYPE_RESIN));
    }

    private void registerCryostasisTubeRenderers()
    {
        RenderCryostasisTube.renderers.add(new CryostasisEntityRenderer(EntityChestburster.class)
        {
            @Override
            public boolean isApplicable(Entity entity)
            {
                return entity instanceof EntityChestburster;
            }

            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null)
                {
                    OpenGL.pushMatrix();
                    if (tile.getVoltage() > 0)
                        OpenGL.disableLight();
                    OpenGL.translate(0F, -0.5F, 0F);
                    OpenGL.rotate(90F, 1F, 0F, 0F);
                    RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    OpenGL.popMatrix();
                }
            }
        });

        RenderCryostasisTube.renderers.add(new CryostasisEntityRenderer(EntityFacehugger.class)
        {
            @Override
            public boolean isApplicable(Entity entity)
            {
                return entity instanceof EntityFacehugger;
            }

            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null)
                {
                    OpenGL.pushMatrix();
                    if (tile.getVoltage() > 0)
                        OpenGL.disableLight();
                    OpenGL.translate(0F, -0.5F, 0F);
                    OpenGL.rotate(90F, 1F, 0F, 0F);
                    RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    OpenGL.popMatrix();
                }
            }
        });

        RenderCryostasisTube.renderers.add(new CryostasisEntityRenderer(EntityOvamorph.class)
        {
            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null)
                {
                    OpenGL.pushMatrix();
                    OpenGL.scale(0.875F, 0.875F, 0.875F);
                    if (tile.getVoltage() > 0)
                        OpenGL.disableLight();
                    OpenGL.translate(0F, 0.75F, 0F);
                    OpenGL.rotate(180F, 1F, 0F, 0F);
                    OpenGL.rotate(23.5F, 0F, 1F, 0F);
                    RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    OpenGL.popMatrix();
                }
            }
        });

        RenderCryostasisTube.renderers.add(new CryostasisEntityRenderer(EntityXenomorph.class)
        {
            @Override
            public boolean isApplicable(Entity entity)
            {
                return entity instanceof EntityXenomorph;
            }

            @Override
            public void renderChassis(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                OpenGL.disableCullFace();
                OpenGL.enableBlend();
                OpenGL.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                OpenGL.translate(posX + 0.5F, posY + 1.7F, posZ + 0.5F);
                OpenGL.rotate(tile);
                OpenGL.enable(GL12.GL_RESCALE_NORMAL);
                OpenGL.scale(0.75F, -0.75F, 0.75F);
                OpenGL.enable(GL_ALPHA_TEST);
                OpenGL.pushMatrix();
                OpenGL.scale(4, 3, 4);
                OpenGL.translate(0F, -0.75F, 0F);
                AliensVsPredator.resources().models().CRYOSTASIS_TUBE.draw();
                OpenGL.popMatrix();
            }

            @Override
            public void renderEntity(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                if (tile.stasisEntity != null && !(tile.stasisEntity instanceof EntityQueen))
                {
                    double depth = tile.stasisEntity instanceof EntityPraetorian ? -1.95 : tile.stasisEntity instanceof EntityDrone ? -1.0 : -1.5F;

                    OpenGL.pushMatrix();
                    if (tile.getVoltage() > 0)
                        OpenGL.disableLight();
                    OpenGL.translate(0F, -2.75F, depth);
                    OpenGL.rotate(90F, 1F, 0F, 0F);
                    RenderManager.instance.renderEntityWithPosYaw(tile.stasisEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    OpenGL.popMatrix();
                }
                else if (tile.stasisEntity instanceof EntityQueen)
                {
                    OpenGL.pushMatrix();
                    OpenGL.disableLight();
                    OpenGL.scale(0.25, 0.25, 0.25);
                    OpenGL.translate(-3.25, -16, 0);
                    Draw.drawString("\u26A0", 0, 0, 0xFFFF0000, false);
                    OpenGL.enableLight();
                    OpenGL.popMatrix();
                }
            }

            @Override
            public void renderTube(RenderCryostasisTube renderer, TileEntityCryostasisTube tile, double posX, double posY, double posZ)
            {
                TexturedModel<?> mask = null;

                if (tile.isShattered())
                {
                    mask = AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_SHATTERED;
                }
                else if (tile.isCracked())
                {
                    mask = AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK_CRACKED;
                }
                else
                {
                    mask = AliensVsPredator.resources().models().CRYOSTASIS_TUBE_MASK;
                }

                if (tile.getVoltage() > 0)
                {
                    OpenGL.disableLightMapping();
                    OpenGL.disableLight();
                }

                OpenGL.disableCullFace();
                OpenGL.scale(4, 3, 4);
                OpenGL.translate(0F, -0.75F, 0F);
                mask.draw();
                OpenGL.scale(0.5, 0.5, 0.5);
                OpenGL.enableLightMapping();
                OpenGL.enableLight();
            }
        });
    }

    private void registerMedpodTransformations()
    {
        RenderMedpod.transforms.add(new EntityRenderTransforms(EntityPlayerSP.class, EntityPlayerMP.class, EntityClientPlayerMP.class, EntityOtherPlayerMP.class, AbstractClientPlayer.class, EntityPlayer.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.translate(0F, -0.5F, 0F);
                OpenGL.rotate(90F, 1F, 0F, 0);
                OpenGL.rotate(180F, 0F, 1F, 0);
                OpenGL.translate(0F, -0.75F, 0F);
            }
        });

        RenderMedpod.transforms.add(new EntityRenderTransforms(EntityVillager.class, EntityMarine.class, EntitySpeciesYautja.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90F, 1F, 0F, 0);
            }
        });
    }
    
    private void registerEntityFaceTransformations()
    {
        this.registerVanillaFaceTransoformations();
        
        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityCombatSynthetic.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityEngineer.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(115.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0.2F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityMarine.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySpeciesYautja.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(110.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, 0F, 0.5F);
            }
        });
    }
    
    private void registerVanillaFaceTransoformations()
    {
        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityVillager.class, EntityWitch.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(110.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.1F, 0.15F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityPlayer.class, EntityPigZombie.class, EntityZombie.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);

                if (entity.ridingEntity instanceof EntityZombie)
                {
                    EntityZombie zombie = (EntityZombie) entity.ridingEntity;

                    if (zombie.isChild())
                    {
                        OpenGL.translate(0F, 0F, 0.85F);
                    }
                }
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityClientPlayerMP.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                EntityClientPlayerMP player = (EntityClientPlayerMP) entity.ridingEntity;
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.0F, 2.05F);
                OpenGL.rotate(-player.rotationPitch, 1, 0, 0);
                OpenGL.translate(0F, -0.1F, -0.15F);                
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityCow.class, EntityMooshroom.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(-110.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(5F, 1F, 0F, 0F);
                OpenGL.translate(0F, -0.8F, -0.15F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityPig.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.rotate(5F, 1F, 0F, 0F);
                OpenGL.translate(0F, -0.85F, 0.25F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityHorse.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(-150.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.translate(0F, -0.6F, -1.0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityCreeper.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.1F, -0.05F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySkeleton.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.1F, -0.1F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySpider.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.60F, 0.45F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySlime.class, EntityMagmaCube.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                EntitySlime slime = (EntitySlime) entity.ridingEntity;
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, slime.getSlimeSize() * -0.25F, 0.75F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityGhast.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityEnderman.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.1F, 0.0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityCaveSpider.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.3F, 0.4F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySilverfish.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
                OpenGL.translate(0F, 0.7F, 0.55F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityBlaze.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.15F, 0.25F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityBat.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.1F, 0.25F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySheep.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.8F, 0.25F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityChicken.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.3F, -0.45F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySquid.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(270.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.7F, 0.55F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityWolf.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(140.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.15F, 0.75F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityOcelot.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }
            
            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(140.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.15F, 0.9F);
            }
        });
    }
}
