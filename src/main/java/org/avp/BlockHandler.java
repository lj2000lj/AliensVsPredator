package org.avp;

import org.avp.block.BlockAmpule;
import org.avp.block.BlockAssembler;
import org.avp.block.BlockBlackGoo;
import org.avp.block.BlockBlastdoor;
import org.avp.block.BlockCryostasisTube;
import org.avp.block.BlockCustomSlab;
import org.avp.block.BlockCustomStairs;
import org.avp.block.BlockGenerator;
import org.avp.block.BlockGunLocker;
import org.avp.block.BlockHiveResin;
import org.avp.block.BlockLightPanel;
import org.avp.block.BlockLocker;
import org.avp.block.BlockMedpod;
import org.avp.block.BlockMist;
import org.avp.block.BlockNegativeTransformer;
import org.avp.block.BlockRedstoneEmitter;
import org.avp.block.BlockRedstoneFluxGenerator;
import org.avp.block.BlockPortal;
import org.avp.block.BlockPowercell;
import org.avp.block.BlockPowerline;
import org.avp.block.BlockRedstoneSensor;
import org.avp.block.BlockSatelliteDish;
import org.avp.block.BlockSatelliteModem;
import org.avp.block.BlockSolarPanel;
import org.avp.block.BlockStalagmite;
import org.avp.block.BlockStasisMechanism;
import org.avp.block.BlockSupplyCrate;
import org.avp.block.BlockTempleSpawner;
import org.avp.block.BlockTransformer;
import org.avp.block.BlockTurret;
import org.avp.block.BlockUnidentifiedDirt;
import org.avp.block.BlockUnidentifiedLog;
import org.avp.block.BlockUnidentifiedTreeLeaves;
import org.avp.block.BlockUnidentifiedTreeSapling;
import org.avp.block.BlockUnidentifiedTreeTendon;
import org.avp.block.BlockWall;
import org.avp.block.BlockWorkstation;
import org.avp.block.skulls.BlockSkullEngineer;
import org.avp.block.skulls.BlockSkullSpaceJockey;
import org.avp.block.skulls.BlockSkullXenomorph;
import org.avp.block.skulls.BlockSkullXenomorphWarrior;
import org.avp.block.skulls.BlockSkullYautja;
import org.avp.items.ItemSupplyChute.SupplyChuteType;

import com.arisux.mdxlib.lib.game.Game;
import com.arisux.mdxlib.lib.game.IInitEvent;
import com.arisux.mdxlib.lib.world.block.BlockMaterial;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockHandler implements IInitEvent
{
    public static BlockHandler instance                   = new BlockHandler();

    public Block               terrainHiveResin           = (new BlockHiveResin(Material.wood)).setLightOpacity(255);
    public Block               blockStandardHiveResin     = (new BlockMaterial(Material.wood)).setHardness(5F).setResistance(10.0F).setLightOpacity(255);
    public Block               blockOvamorph              = (new BlockMaterial(Material.rock)).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               blockShipMetal1            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipMetal2            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockFacehuggerRelic       = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockAlienRelic            = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor1            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor2            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor3            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor4            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor5            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockShipDecor6            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockTempleBrick           = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockTempleTile            = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockTempleWall1           = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockTempleWall2           = (new BlockMaterial(Material.rock).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockWall                  = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               blockCeiling               = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockCeilingFan            = (new BlockMaterial(Material.iron)
                                                          {
                                                              public boolean isOpaqueCube()
                                                              {
                                                                  return false;
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F);
    public Block               blockCeiliingVent          = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockCeilingGrill          = ((new BlockMaterial(Material.iron)
                                                          {
                                                              public boolean isOpaqueCube()
                                                              {
                                                                  return false;
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F)).setLightOpacity(4);
    public Block               blockSkulls                = (new BlockMaterial(Material.rock));
    public Block               blockFloorGrill            = ((new BlockMaterial(Material.iron)
                                                          {
                                                              public boolean isOpaqueCube()
                                                              {
                                                                  return false;
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F)).setLightOpacity(4);
    public Block               blockIronBricks            = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockVerticalMetal         = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockColumnMetal1          = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockColumnMetal2          = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockPlasticCircle         = (new BlockMaterial(Material.clay)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockPlastic               = (new BlockMaterial(Material.clay)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockPaddingPanel          = (new BlockMaterial(Material.cloth)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockPlasticTri            = (new BlockMaterial(Material.clay)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockPlasticTile           = (new BlockMaterial(Material.clay)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               oreSilicon                 = (new BlockMaterial(Material.rock)).setHardness(2.2F).setResistance(1.4F).setLightOpacity(255);
    public Block               oreLithium                 = (new BlockMaterial(Material.iron)).setHardness(4.2F).setResistance(5.4F).setLightOpacity(255);
    public Block               oreCopper                  = (new BlockMaterial(Material.iron)).setHardness(3.2F).setResistance(2.6F).setLightOpacity(255);
    public Block               oreBauxite                 = (new BlockMaterial(Material.iron)).setHardness(3.2F).setResistance(2.6F).setLightOpacity(255);
    public Block               mainframePanelShimmer      = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(1F).setLightLevel(0.5F));
    public Block               mainframePanelFlicker      = (new BlockMaterial(Material.iron).setHardness(5F).setResistance(10F).setLightLevel(0.5F));
    public Block               blockVent0                 = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockVent1                 = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockVent2                 = (new BlockMaterial(Material.iron)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockEngineerShipFloor     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipBrick0    = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipBrick1    = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipBrick2    = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipBrick3    = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipGravel    = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipWall0     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipWall1     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipWall2     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipWall3     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipWall4     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipRock0     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipRock1     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipRock2     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipRock3     = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipColumn1   = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipColumn2   = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipMaterial1 = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockEngineerShipMaterial2 = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               terrainUniStone            = (new BlockMaterial(Material.rock)).setHardness(1.3F).setResistance(2.0F).setLightOpacity(255);
    public Block               terrainUniSand             = (new BlockMaterial(Material.sand)).setHardness(3.5F).setResistance(2.0F).setLightOpacity(255);
    public Block               terrainUniGravel           = (new BlockMaterial(Material.sand)).setHardness(3.0F).setLightOpacity(255);
    public Block               blockSatelliteDish         = (new BlockSatelliteDish()).setHardness(3.2F).setResistance(2.6F);
    public Block               blockEngineerShipMaterial0 = (new BlockMaterial(Material.iron).setHardness(10F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockSacrificialSpawner    = (new BlockTempleSpawner(Material.rock, false));
    public Block               blockSpawnerCreative       = (new BlockTempleSpawner(Material.rock, true));
    public Block               blockPortalVarda           = (new BlockPortal(AliensVsPredator.settings().dimensionIdVarda()).setHardness(-1.0F).setLightLevel(2.0F));
    public Block               blockPortalAcheron         = (new BlockPortal(AliensVsPredator.settings().dimensionIdAcheron()).setHardness(-1.0F).setLightLevel(2.0F));
    public Block               blockAssembler             = (new BlockAssembler(Material.iron).setHardness(1.5F).setResistance(10.0F));
    public Block               blockFloorGrillStairs      = (new BlockCustomStairs(blockFloorGrill)).setHardness(5F).setResistance(15.0F).setLightOpacity(4);
    public Block               blockCeilingGrillStairs    = (new BlockCustomStairs(blockCeilingGrill)).setHardness(5F).setResistance(15.0F).setLightOpacity(4);
    public Block               blockIronBricksStairs      = (new BlockCustomStairs(blockIronBricks)).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               blockWallStairs            = (new BlockCustomStairs(blockWall)).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               terrainUniDirt             = (new BlockUnidentifiedDirt()).setHardness(0.5F).setResistance(2.0F).setLightOpacity(255);
    public Block               terrainStalagmite          = (new BlockStalagmite(Material.plants)).setHardness(0.0F).setLightOpacity(0);
    public Block               terrainUniTreeLog          = (new BlockUnidentifiedLog()).setHardness(0.0F).setLightOpacity(0);
    public Block               terrainUniTreeTendon       = (new BlockUnidentifiedTreeTendon()).setHardness(0.0F).setLightOpacity(0);
    public Block               terrainUniTreeLeaves       = (new BlockUnidentifiedTreeLeaves()).setHardness(0.0F).setLightOpacity(0);
    public Block               terrainUniTreeSapling      = (new BlockUnidentifiedTreeSapling()).setHardness(0.0F).setLightOpacity(0);
    public Block               blockTurret                = (new BlockTurret(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               blockWorkstation           = (new BlockWorkstation(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               blockStasisMechanism       = (new BlockStasisMechanism(Material.iron)).setHardness(5.0F).setResistance(10.0F);
    public Block               blockRepulsionGenerator    = (new BlockGenerator(Material.iron)).setHardness(5.0F).setResistance(10.0F);
    public Block               blockPowerline             = (new BlockPowerline(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               blockBlastdoor             = (new BlockBlastdoor(Material.iron)).setHardness(10F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockCryostasisTube        = (new BlockCryostasisTube(Material.iron)).setHardness(10F).setResistance(15.0F).setLightOpacity(4);
    public Block               blockLightPanel            = (new BlockLightPanel(Material.iron)).setHardness(1.5F).setResistance(2.0F);
    public Block               blockSatelliteModem        = (new BlockSatelliteModem(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               blockPowercell             = (new BlockPowercell(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               redstoneSensor             = (new BlockRedstoneSensor(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               redstoneEmitter            = (new BlockRedstoneEmitter(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               redstoneFluxGenerator      = (new BlockRedstoneFluxGenerator(Material.iron)).setHardness(3.2F).setResistance(2.6F);
    public Block               blockBlackGoo              = (new BlockBlackGoo());
    public Block               blockMist                  = (new BlockMist());
    public Block               blockTransformer           = (new BlockTransformer(Material.iron)).setHardness(5.0F).setResistance(10.0F);
    public Block               blockNegativeTransformer   = (new BlockNegativeTransformer(Material.iron)).setHardness(5.0F).setResistance(10.0F);
    public Block               supplyCrate                = (new BlockSupplyCrate(SupplyChuteType.UNBRANDED));
    public Block               supplyCrateMarines         = (new BlockSupplyCrate(SupplyChuteType.MARINES));
    public Block               supplyCrateSeegson         = (new BlockSupplyCrate(SupplyChuteType.SEEGSON));
    public Block               blockSolarPanel            = (new BlockSolarPanel(Material.iron)).setHardness(5.0F).setResistance(10.0F);
    public Block               blockLocker                = (new BlockLocker(Material.iron).setHardness(1.5F).setResistance(10.0F));
    public Block               blockMedpod                = (new BlockMedpod(Material.iron).setHardness(1.5F).setResistance(10.0F));
    public Block               blockGunLocker             = (new BlockGunLocker(Material.iron).setHardness(1.5F).setResistance(10.0F));
    public Block               blockAmpule                = (new BlockAmpule().setHardness(5.0F).setResistance(10.0F));
    public Block               blockWallW                 = (new BlockWall(Material.iron).setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockRelicTile             = (new BlockMaterial(Material.rock)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:spawner");
                                                              };
                                                          }.setHardness(5F).setResistance(15.0F).setLightOpacity(255));
    public Block               blockIndustrialGlass       = (new BlockMaterial(Material.iron)
                                                          {
                                                              @Override
                                                              public boolean renderAsNormalBlock()
                                                              {
                                                                  return false;
                                                              }

                                                              @Override
                                                              public boolean isOpaqueCube()
                                                              {
                                                                  return false;
                                                              }
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockFloorGrillSlab        = (new BlockCustomSlab(Material.iron)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:floorgrill");
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(4);
    public Block               blockCeilingGrillSlab      = (new BlockCustomSlab(Material.iron)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:ceilinggrill");
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(4);
    public Block               blockWallSlab              = (new BlockCustomSlab(Material.iron)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:wall_top");
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               blockIronBricksSlab        = (new BlockCustomSlab(Material.iron)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:industrialbricks");
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(255);
    public Block               blockIndustrialGlassSlab   = (new BlockCustomSlab(Material.iron)
                                                          {
                                                              @Override
                                                              public void registerIcons(IIconRegister register)
                                                              {
                                                                  this.blockIcon = register.registerIcon("avp:industrialglass");
                                                              };
                                                          }).setHardness(5F).setResistance(15.0F).setLightOpacity(0);
    public Block               blockIndustrialGlassStairs = (new BlockCustomStairs(blockIndustrialGlass)).setHardness(5F).setResistance(15.0F).setLightOpacity(0);

    public Block               blockSkullEngineer         = new BlockSkullEngineer();
    public Block               blockSkullSpaceJockey      = new BlockSkullSpaceJockey();
    public Block               blockSkullXenomorph        = new BlockSkullXenomorph();
    public Block               blockSkullXenomorphWarrior = new BlockSkullXenomorphWarrior();
    public Block               blockSkullYautja           = new BlockSkullYautja();

    @Override
    public void init(FMLInitializationEvent event)
    {
        Game.register(AliensVsPredator.ID, blockSkullEngineer, "skull.engineer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullSpaceJockey, "skull.spacejockey").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullXenomorph, "skull.xenomorph").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullXenomorphWarrior, "skull.xenomorph.warrior").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullYautja, "skull.yautja").setCreativeTab(AliensVsPredator.tabMain());

        ShapedBlockUtil.register(AliensVsPredator.ID, terrainUniDirt, "unidirt");
        ShapedBlockUtil.register(AliensVsPredator.ID, terrainUniStone, "unistone");
        ShapedBlockUtil.register(AliensVsPredator.ID, terrainUniSand, "unisand");
        ShapedBlockUtil.register(AliensVsPredator.ID, terrainUniGravel, "unigravel");
        Game.register(AliensVsPredator.ID, terrainUniTreeLog, "unitree.wood").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, terrainUniTreeTendon, "unitree.tendons").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, terrainUniTreeLeaves, "unitree.leaves").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, terrainUniTreeSapling, "unitree.sapling").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, terrainStalagmite, "stalagmite").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, oreSilicon, "oresilicon").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, oreCopper, "orecopper").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, oreLithium, "orelithium").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, oreBauxite, "orebauxite").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSolarPanel, "solarpanel").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSatelliteModem, "satellitemodem").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSatelliteDish, "satellitedish").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockPowercell, "powercell").setCreativeTab(AliensVsPredator.tabMain());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockCeiling, "ceilingpanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockCeilingFan, "ceilingfan");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockCeiliingVent, "ceilingvent");
        Game.register(AliensVsPredator.ID, blockTransformer, "transformer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockNegativeTransformer, "transformernegative").setCreativeTab(AliensVsPredator.tabMain());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockCeilingGrill, "ceilinggrill");
        Game.register(AliensVsPredator.ID, blockCeilingGrillStairs, "ceilinggrillstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockCeilingGrillSlab, "ceilinggrillslab").setCreativeTab(AliensVsPredator.tabBlocks());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockFloorGrill, "floorgrill");
        Game.register(AliensVsPredator.ID, blockFloorGrillStairs, "floorgrillstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockFloorGrillSlab, "floorgrillslab").setCreativeTab(AliensVsPredator.tabBlocks());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockWall, "industrialwall");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockWallW, "industrialwall2");
        Game.register(AliensVsPredator.ID, redstoneSensor, "redstonesensor").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, redstoneEmitter, "redstoneemitter").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, redstoneFluxGenerator, "redstonefluxgenerator").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockWallStairs, "industrialwallstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockWallSlab, "industrialslab").setCreativeTab(AliensVsPredator.tabBlocks());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockVent0, "industrialvent");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockVent1, "vent.wall");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockVent2, "vent.ceiling");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockIronBricks, "industrialbricks");
        Game.register(AliensVsPredator.ID, blockIronBricksStairs, "industrialbrickstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIronBricksSlab, "industrialbrickslab").setCreativeTab(AliensVsPredator.tabBlocks());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockIndustrialGlass, "industrialglass");
        Game.register(AliensVsPredator.ID, blockIndustrialGlassStairs, "industrialglassstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIndustrialGlassSlab, "industrialglassslab").setCreativeTab(AliensVsPredator.tabBlocks());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockVerticalMetal, "metalpanel1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockColumnMetal1, "metalpanel2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockColumnMetal2, "metalpanel3");
        Game.register(AliensVsPredator.ID, terrainHiveResin, "hiveresin");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockStandardHiveResin, "hiveresin.standard");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipMetal1, "shippanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipMetal2, "shippannelyautja");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockOvamorph, "tileovamorphdesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockFacehuggerRelic, "tilefacehuggerdesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockAlienRelic, "tilealiendesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor1, "shipwallbase");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor2, "shipsupportpillar");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor3, "shipdecor1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor5, "shipdecor2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor6, "shipdecor3");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockShipDecor4, "shipbrick");
        Game.register(AliensVsPredator.ID, blockSacrificialSpawner, "spawner").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSpawnerCreative, "spawnerc").setCreativeTab(AliensVsPredator.tabMain());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockRelicTile, "templebricksingle", 0, blockSacrificialSpawner);
        ShapedBlockUtil.register(AliensVsPredator.ID, blockTempleBrick, "templebrick");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockTempleTile, "templetile");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockTempleWall1, "templewallbase");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockTempleWall2, "templefloor");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockSkulls, "skulls");
        Game.register(AliensVsPredator.ID, blockPortalVarda, "portal.varda").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockPortalAcheron, "portal.acheron").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockAssembler, "assembler").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockTurret, "turret").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockWorkstation, "terminal").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockStasisMechanism, "stasismechanism").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockRepulsionGenerator, "generator").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockPowerline, "powerline").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockBlastdoor, "blastdoor").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, supplyCrate, "supplychuteblock").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, supplyCrateMarines, "supplychuteblock.marines").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, supplyCrateSeegson, "supplychuteblock.seegson").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockBlackGoo, "blackgoo").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockMist, "mist").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockCryostasisTube, "cryostasistube").setCreativeTab(AliensVsPredator.tabMain());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockPlastic, "plasticblock");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockPaddingPanel, "paddingpanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockPlasticTile, "plastictile");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockPlasticTri, "plastictiletri");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockPlasticCircle, "plastictilecircle");
        Game.register(AliensVsPredator.ID, blockLightPanel, "lightpanel").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, mainframePanelShimmer, "mainframepanel.shimmer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, mainframePanelFlicker, "mainframepanel.flicker").setCreativeTab(AliensVsPredator.tabMain());
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipFloor, "engineershipfloor");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipBrick0, "engineershipbrick");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipBrick1, "engineershipbrick1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipBrick2, "engineershipbrick2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipBrick3, "engineershipbrick3");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipGravel, "engineershipgravel");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipWall0, "engineershipwall");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipWall1, "engineershipwall1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipWall2, "engineershipwall2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipWall3, "engineershipwall3");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipWall4, "engineershipwall4");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipRock0, "engineershiprock");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipRock1, "engineershiprock1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipRock2, "engineershiprock2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipRock3, "engineershiprock3");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipColumn1, "engineershipcolumn1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipColumn2, "engineershipcolumn2");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipMaterial0, "engineershipmaterial0");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipMaterial1, "engineershipmaterial1");
        ShapedBlockUtil.register(AliensVsPredator.ID, blockEngineerShipMaterial2, "engineershipmaterial2");
        Game.register(AliensVsPredator.ID, blockAmpule, "engineership.ampule").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockLocker, "locker").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockGunLocker, "gunlocker").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockMedpod, "medpod").setCreativeTab(AliensVsPredator.tabMain());
    }
}
