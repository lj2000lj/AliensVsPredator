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
import org.avp.block.BlockPortal;
import org.avp.block.BlockPowercell;
import org.avp.block.BlockPowerline;
import org.avp.block.BlockRedstoneEmitter;
import org.avp.block.BlockRedstoneFluxGenerator;
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

    public Block[] unidirtShapes = ShapedBlockUtil.construct(terrainUniDirt).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] unistoneShapes = ShapedBlockUtil.construct(terrainUniStone).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] unisandShapes = ShapedBlockUtil.construct(terrainUniSand).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] unigravelShapes = ShapedBlockUtil.construct(terrainUniGravel).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ceilingPanelShapes = ShapedBlockUtil.construct(blockCeiling).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ceilingFanShapes = ShapedBlockUtil.construct(blockCeilingFan).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ceilingVentShapes = ShapedBlockUtil.construct(blockCeiliingVent).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ceilingGrillShapes = ShapedBlockUtil.construct(blockCeilingGrill).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] floorGrillShapes = ShapedBlockUtil.construct(blockFloorGrill).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] industrialWallShapes = ShapedBlockUtil.construct(blockWall).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] industrialWallWShapes = ShapedBlockUtil.construct(blockWallW).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] industrialVentShapes = ShapedBlockUtil.construct(blockVent0).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] wallVentShapes = ShapedBlockUtil.construct(blockVent1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ceilingVent2Shapes = ShapedBlockUtil.construct(blockVent2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] industrialBrickShapes = ShapedBlockUtil.construct(blockIronBricks).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] industrialGlassShapes = ShapedBlockUtil.construct(blockIndustrialGlass).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] metalPanel1Shapes = ShapedBlockUtil.construct(blockVerticalMetal).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] metalPanel2Shapes = ShapedBlockUtil.construct(blockColumnMetal1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] metalPanel3Shapes = ShapedBlockUtil.construct(blockColumnMetal2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] hiveResinStandardShapes = ShapedBlockUtil.construct(blockStandardHiveResin).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] predatorShipMetal1Shapes = ShapedBlockUtil.construct(blockShipMetal1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] predatorShipMetal2Shapes = ShapedBlockUtil.construct(blockShipMetal2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] ovamorphShapes = ShapedBlockUtil.construct(blockOvamorph).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] facehuggerShapes = ShapedBlockUtil.construct(blockFacehuggerRelic).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] alienShapes = ShapedBlockUtil.construct(blockAlienRelic).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor1Shapes = ShapedBlockUtil.construct(blockShipDecor1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor2Shapes = ShapedBlockUtil.construct(blockShipDecor2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor3Shapes = ShapedBlockUtil.construct(blockShipDecor3).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor4Shapes = ShapedBlockUtil.construct(blockShipDecor4).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor5Shapes = ShapedBlockUtil.construct(blockShipDecor5).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] shipDecor6Shapes = ShapedBlockUtil.construct(blockShipDecor6).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] templeBrickSingleShapes = ShapedBlockUtil.construct(blockRelicTile, 0, blockSacrificialSpawner).setCreativeTab(AliensVsPredator.tabBlocks()).array();;
    public Block[] templeBrickShapes = ShapedBlockUtil.construct(blockTempleBrick).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] templeTileShapes = ShapedBlockUtil.construct(blockTempleTile).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] templeWall1Shapes = ShapedBlockUtil.construct(blockTempleWall1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] templeWall2Shapes = ShapedBlockUtil.construct(blockTempleWall2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] skullShapes = ShapedBlockUtil.construct(blockSkulls).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] plasticShapes = ShapedBlockUtil.construct(blockPlastic).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] paddingPanelShapes = ShapedBlockUtil.construct(blockPaddingPanel).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] plasticTileShapes = ShapedBlockUtil.construct(blockPlasticTile).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] plasticTriShapes = ShapedBlockUtil.construct(blockPlasticTri).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] plasticCircleShapes = ShapedBlockUtil.construct(blockPlasticCircle).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipFloorShapes = ShapedBlockUtil.construct(blockEngineerShipFloor).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipBrick0Shapes = ShapedBlockUtil.construct(blockEngineerShipBrick0).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipBrick1Shapes = ShapedBlockUtil.construct(blockEngineerShipBrick1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipBrick2Shapes = ShapedBlockUtil.construct(blockEngineerShipBrick2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipBrick3Shapes = ShapedBlockUtil.construct(blockEngineerShipBrick3).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipGravelShapes = ShapedBlockUtil.construct(blockEngineerShipGravel).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipWall0Shapes = ShapedBlockUtil.construct(blockEngineerShipWall0).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipWall1Shapes = ShapedBlockUtil.construct(blockEngineerShipWall1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipWall2Shapes = ShapedBlockUtil.construct(blockEngineerShipWall2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipWall3Shapes = ShapedBlockUtil.construct(blockEngineerShipWall3).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipWall4Shapes = ShapedBlockUtil.construct(blockEngineerShipWall4).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipRock0Shapes = ShapedBlockUtil.construct(blockEngineerShipRock0).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipRock1Shapes = ShapedBlockUtil.construct(blockEngineerShipRock1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipRock2Shapes = ShapedBlockUtil.construct(blockEngineerShipRock2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipRock3Shapes = ShapedBlockUtil.construct(blockEngineerShipRock3).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipColumn1Shapes = ShapedBlockUtil.construct(blockEngineerShipColumn1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipColumn2Shapes = ShapedBlockUtil.construct(blockEngineerShipColumn2).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipMaterial0Shapes = ShapedBlockUtil.construct(blockEngineerShipMaterial0).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipMaterial1Shapes = ShapedBlockUtil.construct(blockEngineerShipMaterial1).setCreativeTab(AliensVsPredator.tabBlocks()).array();
    public Block[] engineerShipMaterial2Shapes = ShapedBlockUtil.construct(blockEngineerShipMaterial2).setCreativeTab(AliensVsPredator.tabBlocks()).array();

    @Override
    public void init(FMLInitializationEvent event)
    {
        this.registerStandard();
        this.registerShaped();
    }
    
    private void registerStandard()
    {
        Game.register(AliensVsPredator.ID, blockSkullEngineer, "skull.engineer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullSpaceJockey, "skull.spacejockey").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullXenomorph, "skull.xenomorph").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullXenomorphWarrior, "skull.xenomorph.warrior").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSkullYautja, "skull.yautja").setCreativeTab(AliensVsPredator.tabMain());
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
        Game.register(AliensVsPredator.ID, blockTransformer, "transformer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockNegativeTransformer, "transformernegative").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockCeilingGrillStairs, "ceilinggrillstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockCeilingGrillSlab, "ceilinggrillslab").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockFloorGrillStairs, "floorgrillstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockFloorGrillSlab, "floorgrillslab").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, redstoneSensor, "redstonesensor").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, redstoneEmitter, "redstoneemitter").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, redstoneFluxGenerator, "redstonefluxgenerator").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockWallStairs, "industrialwallstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockWallSlab, "industrialslab").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIronBricksStairs, "industrialbrickstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIronBricksSlab, "industrialbrickslab").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIndustrialGlassStairs, "industrialglassstairs").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, blockIndustrialGlassSlab, "industrialglassslab").setCreativeTab(AliensVsPredator.tabBlocks());
        Game.register(AliensVsPredator.ID, terrainHiveResin, "hiveresin");
        Game.register(AliensVsPredator.ID, blockSacrificialSpawner, "spawner").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockSpawnerCreative, "spawnerc").setCreativeTab(AliensVsPredator.tabMain());
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
        Game.register(AliensVsPredator.ID, blockLightPanel, "lightpanel").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, mainframePanelShimmer, "mainframepanel.shimmer").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, mainframePanelFlicker, "mainframepanel.flicker").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockAmpule, "engineership.ampule").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockLocker, "locker").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockGunLocker, "gunlocker").setCreativeTab(AliensVsPredator.tabMain());
        Game.register(AliensVsPredator.ID, blockMedpod, "medpod").setCreativeTab(AliensVsPredator.tabMain());
    }
    
    private void registerShaped()
    {
        ShapedBlockUtil.register(AliensVsPredator.ID, unidirtShapes, "unidirt");
        ShapedBlockUtil.register(AliensVsPredator.ID, unistoneShapes, "unistone");
        ShapedBlockUtil.register(AliensVsPredator.ID, unisandShapes, "unisand");
        ShapedBlockUtil.register(AliensVsPredator.ID, unigravelShapes, "unigravel");
        ShapedBlockUtil.register(AliensVsPredator.ID, ceilingPanelShapes, "ceilingpanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, ceilingFanShapes, "ceilingfan");
        ShapedBlockUtil.register(AliensVsPredator.ID, ceilingVentShapes, "ceilingvent");
        ShapedBlockUtil.register(AliensVsPredator.ID, ceilingGrillShapes, "ceilinggrill");
        ShapedBlockUtil.register(AliensVsPredator.ID, floorGrillShapes, "floorgrill");
        ShapedBlockUtil.register(AliensVsPredator.ID, industrialWallShapes, "industrialwall");
        ShapedBlockUtil.register(AliensVsPredator.ID, industrialWallWShapes, "industrialwall2");
        ShapedBlockUtil.register(AliensVsPredator.ID, industrialVentShapes, "industrialvent");
        ShapedBlockUtil.register(AliensVsPredator.ID, wallVentShapes, "vent.wall");
        ShapedBlockUtil.register(AliensVsPredator.ID, ceilingVent2Shapes, "vent.ceiling");
        ShapedBlockUtil.register(AliensVsPredator.ID, industrialBrickShapes, "industrialbricks");
        ShapedBlockUtil.register(AliensVsPredator.ID, industrialGlassShapes, "industrialglass");
        ShapedBlockUtil.register(AliensVsPredator.ID, metalPanel1Shapes, "metalpanel1");
        ShapedBlockUtil.register(AliensVsPredator.ID, metalPanel2Shapes, "metalpanel2");
        ShapedBlockUtil.register(AliensVsPredator.ID, metalPanel3Shapes, "metalpanel3");
        ShapedBlockUtil.register(AliensVsPredator.ID, hiveResinStandardShapes, "hiveresin.standard");
        ShapedBlockUtil.register(AliensVsPredator.ID, predatorShipMetal1Shapes, "shippanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, predatorShipMetal2Shapes, "shippannelyautja");
        ShapedBlockUtil.register(AliensVsPredator.ID, ovamorphShapes, "tileovamorphdesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, facehuggerShapes, "tilefacehuggerdesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, alienShapes, "tilealiendesign");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor1Shapes, "shipwallbase");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor2Shapes, "shipsupportpillar");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor3Shapes, "shipdecor1");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor5Shapes, "shipdecor2");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor6Shapes, "shipdecor3");
        ShapedBlockUtil.register(AliensVsPredator.ID, shipDecor4Shapes, "shipbrick");
        ShapedBlockUtil.register(AliensVsPredator.ID, templeBrickSingleShapes, "templebricksingle");
        ShapedBlockUtil.register(AliensVsPredator.ID, templeBrickShapes, "templebrick");
        ShapedBlockUtil.register(AliensVsPredator.ID, templeTileShapes, "templetile");
        ShapedBlockUtil.register(AliensVsPredator.ID, templeWall1Shapes, "templewallbase");
        ShapedBlockUtil.register(AliensVsPredator.ID, templeWall2Shapes, "templefloor");
        ShapedBlockUtil.register(AliensVsPredator.ID, skullShapes, "skulls");
        ShapedBlockUtil.register(AliensVsPredator.ID, plasticShapes, "plasticblock");
        ShapedBlockUtil.register(AliensVsPredator.ID, paddingPanelShapes, "paddingpanel");
        ShapedBlockUtil.register(AliensVsPredator.ID, plasticTileShapes, "plastictile");
        ShapedBlockUtil.register(AliensVsPredator.ID, plasticTriShapes, "plastictiletri");
        ShapedBlockUtil.register(AliensVsPredator.ID, plasticCircleShapes, "plastictilecircle");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipFloorShapes, "engineershipfloor");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipBrick0Shapes, "engineershipbrick");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipBrick1Shapes, "engineershipbrick1");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipBrick2Shapes, "engineershipbrick2");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipBrick3Shapes, "engineershipbrick3");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipGravelShapes, "engineershipgravel");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipWall0Shapes, "engineershipwall");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipWall1Shapes, "engineershipwall1");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipWall2Shapes, "engineershipwall2");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipWall3Shapes, "engineershipwall3");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipWall4Shapes, "engineershipwall4");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipRock0Shapes, "engineershiprock");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipRock1Shapes, "engineershiprock1");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipRock2Shapes, "engineershiprock2");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipRock3Shapes, "engineershiprock3");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipColumn1Shapes, "engineershipcolumn1");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipColumn2Shapes, "engineershipcolumn2");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipMaterial0Shapes, "engineershipmaterial0");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipMaterial1Shapes, "engineershipmaterial1");
        ShapedBlockUtil.register(AliensVsPredator.ID, engineerShipMaterial2Shapes, "engineershipmaterial2");
    }
}
