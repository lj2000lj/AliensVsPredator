package org.avp;

import org.avp.init.Assembler;
import org.avp.init.Assembler.AssemblerSchematic;

import com.arisux.mdxlib.lib.game.IInitEvent;
import com.arisux.mdxlib.lib.world.entity.player.inventory.Inventories;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHandler implements IInitEvent
{
    public static final CraftingHandler instance   = new CraftingHandler();
    private ItemStack                   charcoal   = new ItemStack(Items.coal, 1);

    public CraftingHandler()
    {
        charcoal.setMetadata(1);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        this.addRecipes();
        this.addSmelting();
        this.addSchematics();
    }

    public void addRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemCarbon, 2), "aa", "aa", 'a', Items.coal);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemCarbon, 2), "aa", "aa", 'a', new ItemStack(Items.coal, 1, 1));
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPolycarbonate, 2), "aaa", "bbb", "bbb", 'a', AliensVsPredator.items().itemSilicon, 'b', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemResistor, 1), " a ", " b ", " a ", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemDiode, 1), " a ", " b ", " c ", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemCarbon, 'c', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemLed, 1), " b ", " c ", " a ", 'a', AliensVsPredator.items().itemDiode, 'b', AliensVsPredator.items().itemPolycarbonate, 'c', Items.redstone);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemCapacitor, 1), " a ", " b ", " a ", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemIngotLithium);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemVoltageRegulator, 1), " a ", " b ", " c ", 'a', AliensVsPredator.items().itemDiode, 'b', AliensVsPredator.items().itemIngotCopper, 'c', AliensVsPredator.items().itemResistor);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemTransistor, 1), "  a", "bc ", "  a", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.items().itemSilicon, 'c', Blocks.lever);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemIntegratedCircuit, 1), "dbe", "cac", "fbg", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', AliensVsPredator.items().itemIngotCopper, 'd', AliensVsPredator.items().itemTransistor, 'e', AliensVsPredator.items().itemResistor, 'f', AliensVsPredator.items().itemVoltageRegulator, 'g', AliensVsPredator.items().itemDiode);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemProcessor, 1), "aaa", "aba", "aca", 'a', AliensVsPredator.items().itemIntegratedCircuit, 'b', AliensVsPredator.items().itemPolycarbonate, 'c', AliensVsPredator.items().itemIngotLithium);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemRAM, 1), "aaa", "cec", "dbd", 'a', AliensVsPredator.items().itemIntegratedCircuit, 'b', AliensVsPredator.items().itemPolycarbonate, 'c', AliensVsPredator.items().itemSilicon, 'd', AliensVsPredator.items().itemIngotCopper, 'e', AliensVsPredator.items().itemVoltageRegulator);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemMotherboard, 1), "aef", "gbc", "dbh", 'a', AliensVsPredator.items().itemIntegratedCircuit, 'b', AliensVsPredator.items().itemPolycarbonate, 'c', AliensVsPredator.items().itemTransistor, 'd', AliensVsPredator.items().itemCapacitor, 'e', AliensVsPredator.items().itemVoltageRegulator, 'f', AliensVsPredator.items().itemDiode, 'g', AliensVsPredator.items().itemResistor, 'h', AliensVsPredator.items().itemLed);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockPowerline, 2), " a ", "aba", " a ", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', AliensVsPredator.items().itemIngotCopper);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockTransformer, 1), "aaa", "bca", "aaa", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemVoltageRegulator, 'c', Blocks.iron_block);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockNegativeTransformer, 1), "aaa", "acb", "aaa", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemVoltageRegulator, 'c', Blocks.iron_block);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPowerSupply, 1), "abc", "dec", "abc", 'a', AliensVsPredator.items().itemDiode, 'b', AliensVsPredator.items().itemVoltageRegulator, 'c', AliensVsPredator.items().itemIngotAluminum, 'd', AliensVsPredator.items().itemCapacitor, 'e', AliensVsPredator.blocks().blockTransformer);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemSolidStateDrive, 1), "aaa", "dcb", "fgh", 'a', AliensVsPredator.items().itemRAM, 'b', AliensVsPredator.items().itemVoltageRegulator, 'c', AliensVsPredator.items().itemIntegratedCircuit, 'd', AliensVsPredator.items().itemIngotLithium, 'e', AliensVsPredator.items().itemVoltageRegulator, 'f', AliensVsPredator.items().itemTransistor, 'g', AliensVsPredator.items().itemPolycarbonate, 'h', AliensVsPredator.items().itemCapacitor);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockAssembler, 1), "aba", "dfh", "ceg", 'a', AliensVsPredator.items().itemRAM, 'b', AliensVsPredator.items().itemProcessor, 'c', AliensVsPredator.items().itemPowerSupply, 'd', AliensVsPredator.items().itemPolycarbonate, 'e', AliensVsPredator.items().itemSolidStateDrive, 'f', AliensVsPredator.items().itemLedDisplay, 'g', AliensVsPredator.items().itemMotherboard, 'h', AliensVsPredator.items().itemFlashDrive);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemLedDisplay, 1), "bdb", "bcb", "bab", 'a', AliensVsPredator.items().itemIntegratedCircuit, 'b', AliensVsPredator.items().itemLed, 'c', AliensVsPredator.items().itemPolycarbonate, 'd', AliensVsPredator.items().itemIngotLithium);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockSolarPanel, 1), "aaa", "bbb", "dcd", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', AliensVsPredator.items().itemSilicon, 'c', AliensVsPredator.items().itemIngotCopper, 'd', AliensVsPredator.items().itemIngotLithium);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().plateMarine, 1), "b b", "aba", "bab", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.wool);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().legsMarine, 1), "bab", "b b", "a a", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.wool);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().bootsMarine, 1), "b b", "a a", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.wool);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().pressureMask, 1), "aaa", "b b", "dcd", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.blocks().blockIndustrialGlass, 'c', AliensVsPredator.items().itemCarbon, 'd', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().pressureChest, 1), "b b", "aba", "bab", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().pressurePants, 1), "bab", "b b", "a a", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().pressureBoots, 1), "b b", "a a", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().mk50helmet, 1), "aaa", "b b", "dcd", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.blocks().blockIndustrialGlass, 'c', AliensVsPredator.items().itemCarbon, 'd', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().mk50body, 1), "b b", "aba", "bab", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().mk50pants, 1), "bab", "b b", "a a", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().mk50boots, 1), "b b", "a a", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.blocks().blockIndustrialGlass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPistol, 1), "bc", "a ", 'a', AliensVsPredator.items().itemPistolStock, 'b', AliensVsPredator.items().itemPistolAction, 'c', AliensVsPredator.items().itemPistolBarrel);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPistolStock, 1), "ccc", "ab ", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.items().itemSilicon, 'c', Blocks.planks);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPistolAction, 1), "ca", "ab", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.lever, 'c', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemPistolBarrel, 1), "aaa", "b  ", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAmmoPistol, 2), " a ", "bcb", "bcb", 'a', AliensVsPredator.items().itemIngotCopper, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Items.gunpowder);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemM4, 1), "bc", "a ", 'a', AliensVsPredator.items().itemM4Stock, 'b', AliensVsPredator.items().itemM4Action, 'c', AliensVsPredator.items().itemM4Barrel);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemM4Stock, 1), "ccc", "ab ", 'c', AliensVsPredator.items().itemIngotAluminum, 'a', AliensVsPredator.items().itemSilicon, 'b', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemM4Action, 1), "caa", "ab ", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.lever, 'c', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemM4Barrel, 1), "aaa", "bc ", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemSilicon, 'c', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAK47, 1), "bc", "a ", 'a', AliensVsPredator.items().itemAK47Stock, 'b', AliensVsPredator.items().itemAK47Action, 'c', AliensVsPredator.items().itemAK47Barrel);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAK47Stock, 1), "ccc", "ab ", 'c', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.items().itemSilicon, 'a', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAK47Action, 1), "caa", "db ", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Blocks.lever, 'c', AliensVsPredator.items().itemSilicon, 'd', Items.iron_ingot);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAK47Barrel, 1), "aaa", "cb ", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemSilicon, 'c', AliensVsPredator.items().itemCarbon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemAmmoAR, 1), " a ", "bcb", "bdb", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Items.gunpowder, 'd', AliensVsPredator.items().itemIngotCopper);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemGrenade, 1), " d ", "aca", " b ", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Items.gunpowder, 'd', AliensVsPredator.items().itemIngotCopper);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemIncendiaryGrenade, 1), " d ", "aca", " b ", 'a', Items.iron_ingot, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Items.blaze_powder, 'd', AliensVsPredator.items().itemIngotCopper);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemNostromoFlamethrower, 1), "e f", "ada", "bbc", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Blocks.lever, 'd', AliensVsPredator.items().itemSilicon, 'e', Items.iron_ingot, 'f', Items.flint_and_steel);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemFuelTank, 1), "dad", "bcb", "bbb", 'a', Items.slime_ball, 'b', AliensVsPredator.items().itemIngotAluminum, 'c', Items.blaze_powder, 'd', AliensVsPredator.items().itemPolycarbonate);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockWall, 16), "bbb", "aaa", "bbb", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.cobblestone);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockWallW, 16), "bbb", "aaa", "bbb", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.stone);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockWallStairs, 12), "b  ", "aa ", "bbb", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.cobblestone);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockIndustrialGlass, 16), "bbb", "aaa", "bbb", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.glass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockIndustrialGlassStairs, 12), "b  ", "aa ", "bbb", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.glass);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockIronBricks, 16), "bbb", "aaa", "bbb", 'a', Items.iron_ingot, 'b', Items.brick);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockIronBricksStairs, 12), "b  ", "aa ", "bbb", 'a', Items.iron_ingot, 'b', Items.brick);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockBlastdoor, 1), "aba", "cdc", "aba", 'a', AliensVsPredator.items().itemPolycarbonate, 'b', Blocks.obsidian, 'c', Items.iron_door, 'd', Blocks.sticky_piston);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemMaintenanceJack, 1), "a  ", " a ", "aab", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', AliensVsPredator.items().itemPolycarbonate);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockLocker, 1), "aaa", "aba", "aaa", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Items.wooden_door);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().blockGunLocker, 1), "aaa", "aba", "aaa", 'a', AliensVsPredator.items().itemIngotAluminum, 'b', Items.iron_door);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemDoritos, 3), "aaa", "a b", "bbb", 'a', Items.wheat, 'b', Items.baked_potato);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.items().itemDoritosCoolRanch, 1), "ab", "b ", 'a', AliensVsPredator.items().itemDoritos, 'b', Items.wheat);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().mainframePanelShimmer, 1), "aba", "bcb", "aba", 'a', Items.glowstone_dust, 'b', Items.redstone, 'c', AliensVsPredator.items().itemSilicon);
        GameRegistry.addRecipe(new ItemStack(AliensVsPredator.blocks().mainframePanelFlicker, 1), "bab", "aca", "bab", 'a', Items.glowstone_dust, 'b', Items.redstone, 'c', AliensVsPredator.items().itemSilicon);
    }

    public void addSmelting()
    {
        GameRegistry.addSmelting(AliensVsPredator.blocks().oreCopper, new ItemStack(AliensVsPredator.items().itemIngotCopper), 1.0F);
        GameRegistry.addSmelting(AliensVsPredator.blocks().oreLithium, new ItemStack(AliensVsPredator.items().itemIngotLithium), 1.0F);
        GameRegistry.addSmelting(AliensVsPredator.blocks().oreBauxite, new ItemStack(AliensVsPredator.items().itemIngotAluminum), 1.0F);
        GameRegistry.addSmelting(AliensVsPredator.blocks().oreSilicon, new ItemStack(AliensVsPredator.items().itemSilicon), 1.0F);
        GameRegistry.addSmelting(AliensVsPredator.blocks().terrainUniTreeLog, charcoal, 1.0F);
    }

    public void addSchematics()
    {
        ItemHandler items = AliensVsPredator.items();
        BlockHandler blocks = AliensVsPredator.blocks();

        Assembler.instance.registerSchematic(new AssemblerSchematic("turret", Inventories.newStack(blocks.blockTurret, 1), Inventories.newStack(items.itemM41A, 1), Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(items.itemIngotCopper, 4), Inventories.newStack(items.itemLedDisplay, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("redstonefluxGenerator", Inventories.newStack(blocks.redstoneFluxGenerator, 1), Inventories.newStack(blocks.blockTransformer, 4), Inventories.newStack(blocks.blockNegativeTransformer, 4), Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(Items.diamond, 4)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("cryostasisTube", Inventories.newStack(blocks.blockCryostasisTube, 1), Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(blocks.blockIndustrialGlass, 4), Inventories.newStack(blocks.blockLightPanel, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("lightPanel", Inventories.newStack(blocks.blockLightPanel, 1), Inventories.newStack(items.itemPolycarbonate, 2), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(blocks.blockIndustrialGlass, 2), Inventories.newStack(Items.glowstone_dust, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("pulserifle", Inventories.newStack(items.itemM41A, 1), Inventories.newStack(items.itemPolycarbonate, 8), Inventories.newStack(Items.iron_ingot, 8), Inventories.newStack(items.itemIngotAluminum, 6), Inventories.newStack(items.itemIngotCopper, 6), Inventories.newStack(Items.gold_ingot, 4), Inventories.newStack(items.itemIntegratedCircuit, 2), Inventories.newStack(items.itemLedDisplay, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("grenade", Inventories.newStack(items.itemGrenade, 2), Inventories.newStack(Items.iron_ingot, 2), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(Items.gunpowder, 1), Inventories.newStack(items.itemIngotCopper, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("fire_grenade", Inventories.newStack(items.itemIncendiaryGrenade, 2), Inventories.newStack(Items.iron_ingot, 2), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(Items.blaze_powder, 1), Inventories.newStack(items.itemIngotCopper, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("marineHelm", Inventories.newStack(items.helmMarine, 1), Inventories.newStack(Blocks.wool, 3), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemLedDisplay, 2), Inventories.newStack(items.itemPolycarbonate, 2), Inventories.newStack(items.itemProcessor, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("marinePlate", Inventories.newStack(items.plateMarine, 1), Inventories.newStack(Blocks.wool, 3), Inventories.newStack(items.itemIngotAluminum, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("marineLeggings", Inventories.newStack(items.legsMarine, 1), Inventories.newStack(Blocks.wool, 3), Inventories.newStack(items.itemIngotAluminum, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("marineBoots", Inventories.newStack(items.bootsMarine, 1), Inventories.newStack(Blocks.wool, 1), Inventories.newStack(items.itemIngotAluminum, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("sniperMagazine", Inventories.newStack(items.itemAmmoSniper, 1), Inventories.newStack(items.itemIngotAluminum, 5), Inventories.newStack(Items.gunpowder, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("pistolMagazine", Inventories.newStack(items.itemAmmoPistol, 3), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(Items.gunpowder, 2), Inventories.newStack(items.itemIngotCopper, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ARAmmo", Inventories.newStack(items.itemAmmoAR, 1), Inventories.newStack(items.itemIngotAluminum, 5), Inventories.newStack(Items.iron_ingot, 1), Inventories.newStack(Items.gunpowder, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ACAmmo", Inventories.newStack(items.itemAmmoAC, 1), Inventories.newStack(items.itemIngotAluminum, 5), Inventories.newStack(Items.gunpowder, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("SMGAmmo", Inventories.newStack(items.itemAmmoSMG, 1), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(Items.iron_ingot, 4), Inventories.newStack(Items.gunpowder, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("m56sg", Inventories.newStack(items.itemM56SG, 1), Inventories.newStack(items.itemM56SGAimingModule, 1), Inventories.newStack(items.itemM56SGStock, 1), Inventories.newStack(items.itemM56SGBarrel, 1), Inventories.newStack(items.itemM56SGSupportFrame, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("sniper", Inventories.newStack(items.itemSniper, 1), Inventories.newStack(items.itemSniperScope, 1), Inventories.newStack(items.itemSniperAction, 1), Inventories.newStack(items.itemSniperPeripherals, 1), Inventories.newStack(items.itemSniperBarrel, 1), Inventories.newStack(items.itemSniperStock, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("pistol", Inventories.newStack(items.itemPistol, 1), Inventories.newStack(items.itemPistolStock, 1), Inventories.newStack(items.itemPistolBarrel, 1), Inventories.newStack(items.itemPistolAction, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("m4", Inventories.newStack(items.itemM4, 1), Inventories.newStack(items.itemM4Stock, 1), Inventories.newStack(items.itemM4Barrel, 1), Inventories.newStack(items.itemM4Action, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ak47", Inventories.newStack(items.itemAK47, 1), Inventories.newStack(items.itemAK47Action, 1), Inventories.newStack(items.itemAK47Barrel, 1), Inventories.newStack(items.itemAK47Stock, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("doritos", Inventories.newStack(items.itemDoritos, 4), Inventories.newStack(Items.wheat, 4), Inventories.newStack(Items.baked_potato, 4)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("doritosCoolRanch", Inventories.newStack(items.itemDoritosCoolRanch, 4), Inventories.newStack(items.itemDoritos, 4), Inventories.newStack(Items.wheat, 3)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("motionTracker", Inventories.newStack(items.itemMotionTracker, 1), Inventories.newStack(items.itemPolycarbonate, 6), Inventories.newStack(items.itemIngotAluminum, 8), Inventories.newStack(items.itemIngotCopper, 6), Inventories.newStack(items.itemLedDisplay, 2), Inventories.newStack(items.itemProcessor, 2), Inventories.newStack(Items.diamond, 1), Inventories.newStack(Items.iron_ingot, 8)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("flamethrower", Inventories.newStack(items.itemM240ICU, 1), Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemIngotAluminum, 4), Inventories.newStack(items.itemIngotCopper, 3), Inventories.newStack(Items.blaze_rod, 1), Inventories.newStack(Items.iron_ingot, 6)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("nbtDrive", Inventories.newStack(items.itemFlashDrive, 1), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemRAM, 4), Inventories.newStack(items.itemIngotLithium, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticBiomask", Inventories.newStack(items.helmTitanium, 1), Inventories.newStack(items.itemArtifactTech, 2), Inventories.newStack(Items.diamond_helmet, 1), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemLedDisplay, 2), Inventories.newStack(items.itemPolycarbonate, 2), Inventories.newStack(items.itemProcessor, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticPlate", Inventories.newStack(items.plateTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_chestplate, 1), Inventories.newStack(items.itemIngotAluminum, 3), Inventories.newStack(items.itemPolycarbonate, 3)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticLegs", Inventories.newStack(items.legsTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_leggings, 1), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemPolycarbonate, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticBoots", Inventories.newStack(items.bootsTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_boots, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticAxe", Inventories.newStack(items.axeTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_axe, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticPickaxe", Inventories.newStack(items.pickaxeTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_pickaxe, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticHoe", Inventories.newStack(items.hoeTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_hoe, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticShovel", Inventories.newStack(items.shovelTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_shovel, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticSword", Inventories.newStack(items.swordTitanium, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Items.diamond_sword, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("wristBlade", Inventories.newStack(items.itemWristBlade, 1), Inventories.newStack(items.itemArtifactTech, 2), Inventories.newStack(Items.diamond, 4), Inventories.newStack(items.itemLedDisplay, 2), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemPolycarbonate, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("wristbracerBlades", Inventories.newStack(items.itemWristbracerBlades, 1), Inventories.newStack(items.itemArtifactTech, 2), Inventories.newStack(Items.shears, 1), Inventories.newStack(Items.diamond, 2), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemPolycarbonate, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("proximityMine", Inventories.newStack(items.itemProximityMine, 3), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(Blocks.tnt, 2), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticDisc", Inventories.newStack(items.itemDisc, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(Items.diamond, 1), Inventories.newStack(items.itemPolycarbonate, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticShuriken", Inventories.newStack(items.itemShuriken, 2), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemPolycarbonate, 2)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("celticSpear", Inventories.newStack(items.itemSpear, 1), Inventories.newStack(items.itemArtifactTech, 1), Inventories.newStack(items.itemIngotAluminum, 1), Inventories.newStack(items.itemPolycarbonate, 2), Inventories.newStack(Items.diamond, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("polycarbonate", Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemCarbon, 6), Inventories.newStack(items.itemSilicon, 3)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("carbon", Inventories.newStack(items.itemCarbon, 3), Inventories.newStack(Items.coal, 4)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("resistor", Inventories.newStack(items.itemResistor, 2), Inventories.newStack(items.itemIngotCopper, 2), Inventories.newStack(items.itemCarbon, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("capacitor", Inventories.newStack(items.itemCapacitor, 2), Inventories.newStack(items.itemIngotCopper, 2), Inventories.newStack(items.itemIngotLithium, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("diode", Inventories.newStack(items.itemDiode, 2), Inventories.newStack(items.itemIngotCopper, 1), Inventories.newStack(items.itemCarbon, 1), Inventories.newStack(items.itemSilicon, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("led", Inventories.newStack(items.itemLed, 2), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemDiode, 1), Inventories.newStack(Items.redstone, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("regulator", Inventories.newStack(items.itemVoltageRegulator, 2), Inventories.newStack(items.itemDiode, 1), Inventories.newStack(items.itemIngotCopper, 1), Inventories.newStack(items.itemResistor, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("transistor", Inventories.newStack(items.itemTransistor, 2), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemSilicon, 1), Inventories.newStack(Item.getItemFromBlock(Blocks.lever), 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ic", Inventories.newStack(items.itemIntegratedCircuit, 2), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(items.itemIngotCopper, 2), Inventories.newStack(items.itemSilicon, 1), Inventories.newStack(items.itemTransistor, 1), Inventories.newStack(items.itemResistor, 1), Inventories.newStack(items.itemVoltageRegulator, 1), Inventories.newStack(items.itemDiode, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("processor", Inventories.newStack(items.itemProcessor, 1), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemIntegratedCircuit, 5), Inventories.newStack(items.itemIngotLithium, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("motherboard", Inventories.newStack(items.itemMotherboard, 1), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemIntegratedCircuit, 1), Inventories.newStack(items.itemSilicon, 1), Inventories.newStack(items.itemTransistor, 1), Inventories.newStack(items.itemVoltageRegulator, 1), Inventories.newStack(items.itemDiode, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("powerline", Inventories.newStack(blocks.blockPowerline, 4), Inventories.newStack(items.itemPolycarbonate, 4), Inventories.newStack(items.itemIngotCopper, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("powersupply", Inventories.newStack(items.itemPowerSupply, 1), Inventories.newStack(items.itemDiode, 1), Inventories.newStack(items.itemVoltageRegulator, 1), Inventories.newStack(items.itemIngotAluminum, 2), Inventories.newStack(blocks.blockTransformer, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ledDisplay", Inventories.newStack(items.itemLedDisplay, 2), Inventories.newStack(items.itemPolycarbonate, 1), Inventories.newStack(items.itemLed, 6), Inventories.newStack(items.itemIntegratedCircuit, 1), Inventories.newStack(items.itemIngotLithium, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("ram", Inventories.newStack(items.itemRAM, 2), Inventories.newStack(items.itemIntegratedCircuit, 3), Inventories.newStack(items.itemVoltageRegulator, 1), Inventories.newStack(items.itemIngotCopper, 2), Inventories.newStack(items.itemSilicon, 2), Inventories.newStack(items.itemPolycarbonate, 1)));
        Assembler.instance.registerSchematic(new AssemblerSchematic("solidstatedrive", Inventories.newStack(items.itemSolidStateDrive, 1), Inventories.newStack(items.itemRAM, 2), Inventories.newStack(items.itemVoltageRegulator, 1), Inventories.newStack(items.itemIntegratedCircuit, 1), Inventories.newStack(items.itemIngotLithium, 1), Inventories.newStack(items.itemPolycarbonate, 1)));
    }
}
