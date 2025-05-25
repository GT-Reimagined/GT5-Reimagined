package org.gtreimagined.gt5r.loader.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.ForgeTags;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.SubTag;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.GT5RItems.*;
import static org.gtreimagined.gt5r.data.GT5RMaterialTags.SOLDER;
import static org.gtreimagined.gt5r.data.GT5RMaterialTypes.BOULE;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.*;
import static org.gtreimagined.gt5r.data.TierMaps.CABLE_GETTER;
import static org.gtreimagined.gtcore.data.GTCoreItems.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;
import static org.gtreimagined.gtlib.Ref.L;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.machine.Tier.MV;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;

public class Circuitry {
    public static void loadCraftingRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        // MANUAL COATED BOARD CRAFTING
        provider.addStackRecipe(output, GT5Reimagined.ID, "", "board_basic", new ItemStack(GT5RItems.CircuitBoardCoated, 3),
                ImmutableMap.<Character, Object>builder()
                        .put('R', GTCoreItems.StickyResin)
                        .put('P', PLATE.get(Wood))
                        .build(),
                " R ", "PPP", " R ");
        if (GT5RConfig.HARDER_CIRCUITS){
            bloodyCircuits(output, provider);
        } else {
            circuits(output, provider);
        }
    }

    private static void bloodyCircuits(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        // MANUAL TIER 0 CIRCUIT CRAFTING
        provider.addItemRecipe(output, "circuit_basic", BasicCircuit,
                ImmutableMap.<Character, Object>builder()
                        .put('V', GT5RItems.VacuumTube).put('B', GT5RItems.CircuitBoardCoated)
                        .put('W', GTCoreCables.WIRE_RED_ALLOY.getBlockItem(PipeSize.VTINY))
                        .put('R', GT5RItems.Resistor).put('P', ITEM_CASING.get(Steel))
                        .build(),
                "RPR", "VBV", "WWW");
        provider.addItemRecipe(output, "circuits", GoodCircuit,
                ImmutableMap.<Character, Object>builder()
                        .put('S', ITEM_CASING.getMaterialTag(Steel))
                        .put('C', CIRCUITS_BASIC)
                        .put('c', GTCoreCables.WIRE_RED_ALLOY.getBlockItem(PipeSize.VTINY))
                        .put('D', GT5RItems.Diode).build(), "SCc", "CDC", "cCS");

        var wire = TagUtils.getItemTag(new ResourceLocation(Ref.ID, SubTag.COPPER_WIRE.getId()+"_"+ PipeSize.VTINY.getId()));
        // MANUAL VAC TUBE CRAFTING
        provider.addItemRecipe(output, "vac_tube", GT5RItems.VacuumTube,
                ImmutableMap.<Character, Object>builder()
                        .put('G', GT5RItems.GlassTube)
                        .put('P', Items.PAPER)
                        .put('W', wire)
                        .build(),
                "PGP", "WWW");

        provider.addItemRecipe(output, GT5Reimagined.ID, "vacuum_tube_1", "vac_tube", GT5RItems.VacuumTube,
                ImmutableMap.<Character, Object>builder()
                        .put('G', GT5RItems.GlassTube)
                        .put('P', Items.PAPER)
                        .put('W', WIRE_FINE.getMaterialTag(Copper))
                        .build(),
                "PGP", "WWW");

        // MANUAL RESISTOR CRAFTING
        provider.addItemRecipe(output, "resistor", GT5RItems.Resistor,
                ImmutableMap.<Character, Object>builder()
                        .put('C', DUST_COALS)
                        .put('P', Items.PAPER)
                        .put('W', WIRE_FINE.getMaterialTag(Copper))
                        .build(),
                " P ", "WCW", " P ");
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "diodes", GT5RItems.Diode,
                ImmutableMap.<Character, Object>builder()
                        .put('B', ForgeTags.DYES_BLACK)
                        .put('T', GT5RBlocks.WIRE_TIN.getBlockItem(PipeSize.VTINY))
                        .put('W', GT5RItems.Wafer)
                        .put('G', Tags.Items.GLASS_PANES).build(), "BG ", "TWT", "BG ");
        provider.addItemRecipe(output, GT5Reimagined.ID, "diode_2", "diodes", GT5RItems.Diode,
                ImmutableMap.<Character, Object>builder()
                        .put('B', ForgeTags.DYES_BLACK)
                        .put('T', WIRE_FINE.getMaterialTag(Tin))
                        .put('W', GT5RItems.Wafer)
                        .put('G', Tags.Items.GLASS_PANES).build(), "BG ", "TWT", "BG ");
        provider.addStackRecipe(output, GT5Reimagined.ID, "diode_3", "diodes", new ItemStack(GT5RItems.Diode),
                ImmutableMap.<Character, Object>builder()
                        .put('B', ForgeTags.DYES_BLACK)
                        .put('T', GT5RBlocks.WIRE_TIN.getBlockItem(PipeSize.VTINY))
                        .put('W', DUST_TINY.getMaterialTag(Gallium))
                        .put('G', Tags.Items.GLASS_PANES).build(), "BG ", "TWT", "BG ");
        provider.addStackRecipe(output, GT5Reimagined.ID, "diode_4", "diodes", new ItemStack(GT5RItems.Diode),
                ImmutableMap.<Character, Object>builder()
                        .put('B', ForgeTags.DYES_BLACK)
                        .put('T', WIRE_FINE.getMaterialTag(Tin))
                        .put('W', DUST_TINY.getMaterialTag(Gallium))
                        .put('G', Tags.Items.GLASS_PANES).build(), "BG ", "TWT", "BG ");
        provider.addStackRecipe(output, GT5Reimagined.ID, "", "small_coils", new ItemStack(GT5RItems.SmallCoil, 2),
                ImmutableMap.of('W', WIRE_FINE.getMaterialTag(Copper), 'B', BOLT.getMaterialTag(Steel)), "WWW", "WBW", "WWW");
        provider.addStackRecipe(output, GT5Reimagined.ID, "small_coil_1", "small_coils", new ItemStack(GT5RItems.SmallCoil, 4),
                ImmutableMap.of('W', WIRE_FINE.getMaterialTag(Copper), 'B', BOLT.getMaterialTag(NickelZincFerrite)), "WWW", "WBW", "WWW");
    }

    private static void circuits(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        provider.addItemRecipe(output, GT5Reimagined.ID, "circuit_basic_h", "circuits", BasicCircuit,
                ImmutableMap.<Character, Object>builder()
                        .put('C', CABLE_GETTER.apply(PipeSize.VTINY, MV, false))
                        .put('N', BasicCircuitParts)
                        .put('S', GT5RItems.CircuitBoardCoated)
                        .build(), "CCC", "NSN", "CCC");
        provider.addItemRecipe(output, GT5Reimagined.ID, "circuit_basic_v", "circuits", BasicCircuit,
                ImmutableMap.<Character, Object>builder()
                        .put('C', CABLE_GETTER.apply(PipeSize.VTINY, MV, false))
                        .put('N', BasicCircuitParts)
                        .put('S', GT5RItems.CircuitBoardCoated)
                        .build(), "CNC", "CSC", "CNC");
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "circuits", BasicCircuitParts,
                ImmutableMap.of('C', ITEM_CASING.getMaterialTag(Steel), 'R', GTCoreCables.WIRE_RED_ALLOY.getBlockItem(PipeSize.VTINY), 'T', GT5RBlocks.WIRE_TIN.getBlockItem(PipeSize.VTINY)), "CR", "RT");
        /*provider.addItemRecipe(output, GT5RRef.ID, "lapotron_crystal_upgrade", "energy_orbs", GTCoreItems.LapotronCrystal,
                ImmutableMap.of('C', CIRCUITS_ADVANCED, 'L', DUST_LAPIS_LAZURITE, 'E', GTCoreItems.EnergyCrystal), "LCL", "LEL", "LCL");
        provider.addItemRecipe(output, GT5RRef.ID, "", "energy_orbs", GTCoreItems.LapotronCrystal,
                ImmutableMap.of('C', CIRCUITS_DATA, 'L', DUST_LAPIS_LAZURITE, 'S', GT5RTags.GEM_SAPPHIRES), "LCL", "LSL", "LCL");*/
    }

    public static void init() {

        silicon();
        //phenolic
        ASSEMBLER.RB().ii(DUST.getMaterialIngredient(Wood, 1), RecipeIngredient.of(MoldPlate, 1).setNoConsume())
                .fi(Glue.getLiquid(100))
                .io(new ItemStack(GT5RItems.CircuitBoardPhenolic,8))
                .add("phenolic_circuit_board",30, 8);
        if (!GT5RConfig.HARDER_CIRCUITS){
            boards();
            circuitParts();
            circuits();
        } else {
            bloodyBoards();
            bloodyCircuitParts();
            bloodyCircuits();
        }
    }

    private static void silicon(){
        //E_BLAST_FURNACE.RB().temperature(1784).ii(DUST.getMaterialIngredient(Silicon, 16), INT_CIRCUITS.get(16)).fi(Helium.getGas(1000)).io(GT5RItems.SiliconBoule).add("silicon_boule", 9000, 120);
        addCuttingRecipe(BOULE.get(Silicon), GT5RItems.Wafer, 16, 1600, 384, 1);
        addCuttingRecipe(GT5RItems.Wafer, GT5RItems.SiliconChip, 8, 600, 48, 22);
        if (GT5RConfig.HARDER_CIRCUITS){
            E_BLAST_FURNACE.RB().temperature(2484).ii(DUST.getMaterialIngredient(Silicon, 16), DUST.getMaterialIngredient(Glowstone, 1)).fi(Nitrogen.getGas(1000)).io(GT5RItems.GlowstoneDopedSiliconBoule).add("glowstone_doped_silicon_boule", 12000, 480);
            E_BLAST_FURNACE.RB().temperature(2484).ii(DUST.getMaterialIngredient(Silicon, 16), DUST.getMaterialIngredient(Naquadah, 1)).fi(Argon.getGas(1000)).io(GT5RItems.NaquadahDopedSiliconBoule).add("naquadah_doped_silicon_boule", 15000, 1920);
            addCuttingRecipe(GT5RItems.GlowstoneDopedSiliconBoule, GT5RItems.GlowstoneDopedWafer, 32, 800, 64, 20);
            addCuttingRecipe(GT5RItems.NaquadahDopedSiliconBoule, GT5RItems.NaquadahDopedWafer, 64, 1600, 64, 240);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.ASoCWafer, 1, 200, 1920, Amber, Topaz);
            addCuttingRecipe(GT5RItems.ASoCWafer, GT5RItems.ASoC, 8, 600, 48, 22);
            addLensRecipe(GT5RItems.Wafer, GT5RItems.CentralProcessingUnitWafer, 1, 900, 120, Diamond, Glass);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.CentralProcessingUnitWafer, 4, 500, 480, Diamond, Glass);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.CentralProcessingUnitWafer, 8, 200, 1920, Diamond, Glass);
            addCuttingRecipe(GT5RItems.CentralProcessingUnitWafer, GT5RItems.CentralProcessingUnit, 8, 600, 48, 22);
            CHEMICAL_REACTOR.RB().ii(of(GT5RItems.PICWafer), DUST.getMaterialIngredient(IndiumGalliumPhosphide, 2)).fi(RedAlloy.getLiquid(L * 2)).io(GT5RItems.HPICWafer).add("hpic_wafer", 1200, 1920);
            addCuttingRecipe(GT5RItems.HPICWafer, GT5RItems.HighPowerIC, 2, 600, 48, 22);
            addLensRecipe(GT5RItems.Wafer, GT5RItems.IntegratedLogicCircuitWafer, 1, 900, 120, Ruby, RedGarnet, Jade);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.IntegratedLogicCircuitWafer, 4, 500, 480, Ruby, RedGarnet, Jade);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.IntegratedLogicCircuitWafer, 8, 200, 1920, Ruby, RedGarnet, Jade);
            addCuttingRecipe(GT5RItems.IntegratedLogicCircuitWafer, GT5RItems.IntegratedLogicCircuit, 8, 600, 48, 22);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.NANDMemoryChipWafer, 1, 500, 480, EnderPearl);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.NANDMemoryChipWafer, 4, 200, 1920, EnderPearl);
            addCuttingRecipe(GT5RItems.NANDMemoryChipWafer, GT5RItems.NANDMemoryChip, 32, 600, 48, 22);
            CHEMICAL_REACTOR.RB().ii(of(GT5RItems.CentralProcessingUnitWafer), of(CarbonFibre, 16)).fi(Glowstone.getLiquid(L * 4)).io(GT5RItems.NanoCpuWafer).add("nano_cpu_wafer", 400, 1920);
            addCuttingRecipe(GT5RItems.NanoCpuWafer, GT5RItems.NanoCpu, 7, 600, 48, 22);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.NorMemoryChipWafer, 1, 500, 480, EnderEye);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.NorMemoryChipWafer, 4, 200, 1920, EnderEye);
            addCuttingRecipe(GT5RItems.NorMemoryChipWafer, GT5RItems.NorMemoryChip, 16, 600, 48, 22);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.PICWafer, 1, 500, 480, Opal, Sapphire, BlueTopaz);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.PICWafer, 4, 200, 1920, Opal, Sapphire, BlueTopaz);
            addCuttingRecipe(GT5RItems.PICWafer, GT5RItems.PowerIC, 4, 600, 48, 22);
            CHEMICAL_REACTOR.RB().ii(of(GT5RItems.NanoCpuWafer), of(GT5RItems.QuantumEye, 2)).fi(GalliumArsenide.getLiquid(L * 2)).io(GT5RItems.QBitWafer).add("qbit_wafer", 400, 1920);
            CHEMICAL_REACTOR.RB().ii(of(GT5RItems.NanoCpuWafer), DUST.getMaterialIngredient(IndiumGalliumPhosphide, 1)).fi(Radon.getGas(50)).io(GT5RItems.QBitWafer).add("qbit_wafer_2", 600, 1920);
            addCuttingRecipe(GT5RItems.QBitWafer, GT5RItems.QBitProcessingUnit, 5, 600, 48, 22);
            addLensRecipe(GT5RItems.Wafer, GT5RItems.RandomAccessMemoryChipWafer, 1, 900, 120, GreenSapphire);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.RandomAccessMemoryChipWafer, 4, 500, 480, GreenSapphire);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.RandomAccessMemoryChipWafer, 8, 200, 1920, GreenSapphire);
            addCuttingRecipe(GT5RItems.RandomAccessMemoryChipWafer, GT5RItems.RandomAccessMemoryChip, 32, 600, 48, 22);
            addLensRecipe(GT5RItems.GlowstoneDopedWafer, GT5RItems.SOCWafer, 1, 500, 480, YellowGarnet);
            addLensRecipe(GT5RItems.NaquadahDopedWafer, GT5RItems.SOCWafer, 4, 200, 1920, YellowGarnet);
            addCuttingRecipe(GT5RItems.SOCWafer, GT5RItems.SOC, 10, 600, 48, 22);

        }
    }

    private static void addLensRecipe(ItemBasic<?> input, ItemBasic<?> output, int count, int ticks, int power, Material... lenses){
        String extra = input.getId().replace("doped_", "").replace("wafer", "");
        for (Material lens : lenses){
            LASER_ENGRAVER.RB().ii(of(input), LENS.getMaterialIngredient(lens, 1).setNoConsume()).io(new ItemStack(output, count)).add(output.getId() + "_" + extra + lens.getId(), ticks, power);
        }
    }

    private static void addCuttingRecipe(Item input, ItemBasic<?> output, int amount, int ticks, int power, int liquidMultiplier){
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(new FluidStack(Fluids.WATER, 5 * liquidMultiplier))
                .io(new ItemStack(output, amount)).add(output.getId() + "_with_water", ticks, power);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(DistilledWater.getLiquid(3 * liquidMultiplier))
                .io(new ItemStack(output, amount)).add(output.getId() + "_with_distilled_water", ticks, power);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Lubricant.getLiquid(liquidMultiplier))
                .io(new ItemStack(output, amount)).add(output.getId() + "_with_lubricant", ticks / 2, power);
    }

    private static void circuitParts(){
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Germanium, 1), WIRE_FINE.getMaterialIngredient(Tin, 1), WIRE_FINE.getMaterialIngredient(RedAlloy, 1)).io(new ItemStack(BasicCircuitParts)).add("basic_circuit_parts_germanium", 32, 16);
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Silicon, 1), WIRE_FINE.getMaterialIngredient(Tin, 1), WIRE_FINE.getMaterialIngredient(RedAlloy, 1)).io(new ItemStack(BasicCircuitParts)).add("basic_circuit_parts_silicon", 32, 16);
        FORMING_PRESS.RB().ii(ITEM_CASING.getMaterialIngredient(Steel, 1), WIRE_FINE.getMaterialIngredient(Tin, 2), WIRE_FINE.getMaterialIngredient(RedAlloy, 2)).io(new ItemStack(BasicCircuitParts)).add("basic_circuit_parts_steel", 32, 16);
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Silicon, 1), WIRE_FINE.getMaterialIngredient(Copper, 1), WIRE_FINE.getMaterialIngredient(RedAlloy,1)).io(GoodCircuitParts).add("good_circuit_parts_silicon", 32, 16);
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Germanium, 1), WIRE_FINE.getMaterialIngredient(Copper, 1), WIRE_FINE.getMaterialIngredient(RedAlloy,1)).io(GoodCircuitParts).add("good_circuit_parts_germanium", 32, 16);
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Silicon, 1), WIRE_FINE.getMaterialIngredient(Electrum, 1), WIRE_FINE.getMaterialIngredient(Signalum, 1)).io(AdvancedCircuitParts).add("advanced_circuit_parts", 32, 64);
        FORMING_PRESS.RB().ii(FOIL.getMaterialIngredient(Niobium, 1), WIRE_FINE.getMaterialIngredient(Platinum, 1), WIRE_FINE.getMaterialIngredient(Signalum, 1)).io(new ItemStack(GT5RItems.ComplexCircuitParts)).add("complex_circuit_parts", 32, 256);
    }

    private static void boards(){
        FORMING_PRESS.RB().ii(PLATE.getMaterialIngredient(Plastic, 1), DUST.getMaterialIngredient(Silicon, 1)).io(new ItemStack(EmptyCircuitPlate)).add("empty_circuit_plate_silicon", 32, 16);
        FORMING_PRESS.RB().ii(PLATE.getMaterialIngredient(Plastic, 1), of(GT5RTags.DUST_SIO, 1)).io(new ItemStack(EmptyCircuitPlate)).add("empty_circuit_plate", 32, 16);
        FORMING_PRESS.RB().ii(of(EmptyCircuitPlate), of(CopperEtchedWiring, 4)).io(new ItemStack(CopperCircuitPlate)).add("copper_circuit_plate", 32, 16);
        FORMING_PRESS.RB().ii(of(EmptyCircuitPlate), of(GoldEtchedWiring, 4)).io(new ItemStack(GoldCircuitPlate)).add("gold_circuit_plate", 32, 16);
        FORMING_PRESS.RB().ii(of(EmptyCircuitPlate), of(PlatinumEtchedWiring, 4)).io(new ItemStack(PlatinumCircuitPlate)).add("platinum_circuit_plate", 32, 64);
        FORMING_PRESS.RB().ii(of(CopperCircuitPlate, 1), of(BasicCircuitParts, 4)).io(BasicCircuitBoard).add("basic_circuit_board", 32, 16);
        FORMING_PRESS.RB().ii(of(CopperCircuitPlate,1), of(GoodCircuitParts)).io(GoodCircuitBoard).add("good_circuit_board", 32, 16);
        FORMING_PRESS.RB().ii(of(GoldCircuitPlate, 1), of(AdvancedCircuitParts)).io(AdvancedCircuitBoard).add("advanced_circuit_board", 32, 16);
        FORMING_PRESS.RB().ii(of(PlatinumCircuitPlate, 1),of(GT5RItems.ComplexCircuitParts)).io(ComplexCircuitBoard).add("complex_circuit_board", 32, 64);
        FORMING_PRESS.RB().ii(of(EmptyProcessorCircuitBoard), of(PlatinumEtchedWiring, 4)).io(new ItemStack(ProcessorCircuitBoard)).add("processor_circuit_board", 32, 256);
        ASSEMBLER.RB().ii(of(GT5RItems.Wafer, 2), PLATE.getMaterialIngredient(Polytetrafluoroethylene, 1)).io(new ItemStack(EmptyProcessorCircuitBoard)).add("empty_processor_circuit_board", 32, 256);
    }

    private static void circuits(){
        BATH.RB().ii(of(BasicCircuitBoard)).fi(SolderingAlloy.getLiquid(L / 2)).io(BasicCircuit).add("basic_circuit", 64);
        BATH.RB().ii(of(GoodCircuitBoard)).fi(SolderingAlloy.getLiquid(L / 2)).io(GoodCircuit).add("good_circuit", 64);
        BATH.RB().ii(of(AdvancedCircuitBoard)).fi(SolderingAlloy.getLiquid(L / 2)).io(AdvancedCircuit).add("advanced_circuit", 64);
        BATH.RB().ii(of(ComplexCircuitBoard)).fi(SolderingAlloy.getLiquid(L / 2)).io(ComplexCircuit).add("complex_circuit", 64);

        ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.EngravedCrystalChip)).fi(Tin.getLiquid(L)).io(new ItemStack(DataStorageCircuit)).add("data_storage_circuit_tin", 32, 64);
        ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.EngravedCrystalChip)).fi(SolderingAlloy.getLiquid(L / 2)).io(new ItemStack(DataStorageCircuit)).add("data_storage__circuit_soldering_alloy", 32, 64);
        ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.EngravedCrystalChip)).fi(Lead.getLiquid(L * 2)).io(new ItemStack(DataStorageCircuit)).add("data_storage_circuit_lead", 32, 64);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(DataStorageCircuit, 3)).fi(Tin.getLiquid(L * 2)).io(new ItemStack(DataControlCircuit)).add("data_control_circuit_tin", 32, 256);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(DataStorageCircuit, 3)).fi(SolderingAlloy.getLiquid(L)).io(new ItemStack(DataControlCircuit)).add("data_control_circuit_soldering_alloy", 32, 256);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(DataStorageCircuit, 3)).fi(Lead.getLiquid(L * 4)).io(new ItemStack(DataControlCircuit)).add("data_control_circuit_lead", 32, 256);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(EngravedLapotronChip, 3)).fi(Tin.getLiquid(L * 2)).io(new ItemStack(EnergyFlowCircuit)).add("energy_flow_circuit_tin", 32, 256);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(EngravedLapotronChip, 3)).fi(SolderingAlloy.getLiquid(L)).io(new ItemStack(EnergyFlowCircuit)).add("energy_flow_circuit_soldering_alloy", 32, 256);
        ASSEMBLER.RB().ii(of(ProcessorCircuitBoard), of(EngravedLapotronChip, 3)).fi(Lead.getLiquid(L * 4)).io(new ItemStack(EnergyFlowCircuit)).add("energy_flow_circuit_lead", 32, 256);
        ASSEMBLER.RB().ii(of(CIRCUITS_ELITE, 2), of(EngravedCrystalChip, 18)).io(DataOrb).add("data_orb", 25 * 20, 256);
        ASSEMBLER.RB().ii(of(CIRCUITS_MASTER, 2), of(EngravedLapotronChip, 18)).io(BatteryEnergyOrb).add("lapotronic_energy_orb", 25 * 20, 1024);
        ASSEMBLER.RB().ii(PLATE.getMaterialIngredient(Europium, 4), of(BatteryEnergyOrb, 8)).io(BatteryEnergyOrbCluster).add("lapotronic_energy_orb_cluster", 102 * 20, 4096);
        ASSEMBLER.RB().ii(of(CIRCUITS_ADVANCED), of(BasicCircuitParts, 2), of(AdvancedCircuitParts, 2)).fi(Lead.getLiquid(L * 2)).io(GT5RItems.DataStick).add("data_stick_lead", 120, 64);
        ASSEMBLER.RB().ii(of(CIRCUITS_ADVANCED), of(BasicCircuitParts, 2), of(AdvancedCircuitParts, 2)).fi(Tin.getLiquid(L)).io(GT5RItems.DataStick).add("data_stick_tin", 120, 64);
        ASSEMBLER.RB().ii(of(CIRCUITS_ADVANCED), of(BasicCircuitParts, 2), of(AdvancedCircuitParts, 2)).fi(SolderingAlloy.getLiquid(L/2)).io(GT5RItems.DataStick).add("data_stick_soldering_alloy", 120, 64);
        CHEMICAL_REACTOR.RB().ii(FOIL.getMaterialIngredient(Copper, 4)).fi(EpoxyResin.getLiquid(L)).io(GT5RItems.CircuitBoardEpoxy).add("epoxy_circuit_board", 500, 10);
    }

    private static void bloodyBoards() {
        //Plastic
        CHEMICAL_REACTOR.RB().ii(PLATE.getMaterialIngredient(Plastic, 1), FOIL.getMaterialIngredient(Copper, 1))
                .fi(SulfuricAcid.getLiquid(125))
                .io(new ItemStack(GT5RItems.CircuitBoardPlastic,1))
                .add("plastic_circuit_board",25*20, 10);
        CHEMICAL_REACTOR.RB().ii(PLATE.getMaterialIngredient(PolyvinylChloride, 1), FOIL.getMaterialIngredient(Copper, 1))
                .fi(SulfuricAcid.getLiquid(125))
                .io(new ItemStack(GT5RItems.CircuitBoardPlastic,2))
                .add("plastic_circuit_board_2",25*20, 10);
        CHEMICAL_REACTOR.RB().ii(PLATE.getMaterialIngredient(Polytetrafluoroethylene, 1), FOIL.getMaterialIngredient(Copper, 1))
                .fi(SulfuricAcid.getLiquid(125))
                .io(new ItemStack(GT5RItems.CircuitBoardPlastic,4))
                .add("plastic_circuit_board_4",25*20, 10);
        //Epoxy
        CHEMICAL_REACTOR.RB().ii(PLATE.getMaterialIngredient(EpoxyResin, 1), FOIL.getMaterialIngredient(Copper, 1))
                .fi(SulfuricAcid.getLiquid(125))
                .io(new ItemStack(GT5RItems.CircuitBoardEpoxy,1))
                .add("epoxy_circuit_board",25*20, 10);
        //Fiber
        CHEMICAL_REACTOR.RB().ii(PLATE.getMaterialIngredient(FiberReinforcedEpoxyResin, 1), FOIL.getMaterialIngredient(Copper, 1))
                .fi(SulfuricAcid.getLiquid(125))
                .io(new ItemStack(GT5RItems.CircuitBoardFiber,1))
                .add("fiber_circuit_board",25*20, 10);
        //MultiFiber
        CHEMICAL_REACTOR.RB().ii(of(GT5RItems.CircuitBoardFiber,1), FOIL.getMaterialIngredient(Electrum, 16))
                .fi(SulfuricAcid.getLiquid(250))
                .io(new ItemStack(GT5RItems.CircuitBoardMultiFiber,1))
                .add("multi_fiber_circuit_board",5*20, 480);
        //Wetware
        ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardMultiFiber,1), of(CIRCUITS_GOOD,1), of(GT5RItems.PetriDish), of(GT5RItems.SensorLV), of(GT5RCovers.COVER_PUMP.getItem(LV)))
                .fi(Polystyrene.getLiquid(144))
                .io(new ItemStack(GT5RItems.CircuitBoardWetware,1))
                .add("wetware_circuit_board",8*20, 32768);
    }

    private static void bloodyCircuitParts(){
        ASSEMBLER.RB().ii(of(GT5RItems.SiliconChip, 1), WIRE_FINE.getMaterialIngredient(Tin, 6)).fi(Plastic.getLiquid(L)).io(new ItemStack(GT5RItems.Transistor,8)).add("transistor",80, 24);
        ASSEMBLER.RB().ii(PLATE.getMaterialIngredient(Plastic, 1), FOIL.getMaterialIngredient(Aluminium, 2)).io(new ItemStack(GT5RItems.Capacitor, 2)).add("capacitor", 80, 96);
        ASSEMBLER.RB().ii(PLATE.getMaterialIngredient(Gallium, 1), WIRE_FINE.getMaterialIngredient(AnnealedCopper, 6)).fi(Plastic.getLiquid(L * 2)).io(new ItemStack(GT5RItems.SMDTransistor,32)).add("smd_transistor",80, 96);
        ASSEMBLER.RB().ii(of(DUST.get(Carbon), 1), of(WIRE_FINE.get(Electrum), 4)).fi(Plastic.getLiquid(L)).io(new ItemStack(GT5RItems.SMDResistor,24)).add("smd_resistor",80, 96);
        ASSEMBLER.RB().ii(FOIL.getMaterialIngredient(PolyvinylChloride, 4), FOIL.getMaterialIngredient(Tantalum, 1)).fi(Plastic.getLiquid(L / 4)).io(new ItemStack(GT5RItems.SMDCapacitor,32)).add("smd_capacitor_tantalum",50, 96);
        ASSEMBLER.RB().ii(FOIL.getMaterialIngredient(PolyvinylChloride, 4), FOIL.getMaterialIngredient(Aluminium, 1)).fi(Plastic.getLiquid(L / 4)).io(new ItemStack(GT5RItems.SMDCapacitor,16)).add("smd_capacitor_aluminium",50, 96);
        ASSEMBLER.RB().ii(FOIL.getMaterialIngredient(SiliconeRubber, 4), FOIL.getMaterialIngredient(Tantalum, 1)).fi(Plastic.getLiquid(L / 4)).io(new ItemStack(GT5RItems.SMDCapacitor,32)).add("smd_capacitor_tantalum_rubber",60, 120);
        ASSEMBLER.RB().ii(FOIL.getMaterialIngredient(SiliconeRubber, 4), FOIL.getMaterialIngredient(Aluminium, 1)).fi(Plastic.getLiquid(L / 4)).io(new ItemStack(GT5RItems.SMDCapacitor,16)).add("smd_capacitor_aluminium_rubber",60, 120);
        ASSEMBLER.RB().ii(WIRE_FINE.getMaterialIngredient(AnnealedCopper, 4), DUST_SMALL.getMaterialIngredient(Gallium, 1)).fi(Plastic.getLiquid(L * 2)).io(new ItemStack(GT5RItems.Diode, 16)).add("diode", 400, 48);
        ASSEMBLER.RB().ii(WIRE_FINE.getMaterialIngredient(Copper, 8), BOLT.getMaterialIngredient(Steel, 1)).io(new ItemStack(GT5RItems.SmallCoil, 2)).add("small_coil_1", 80, 8);
        ASSEMBLER.RB().ii(WIRE_FINE.getMaterialIngredient(Copper, 8), BOLT.getMaterialIngredient(NickelZincFerrite, 1)).io(new ItemStack(GT5RItems.SmallCoil, 4)).add("small_coil_2", 80, 8);
        ASSEMBLER.RB().ii(WIRE_FINE.getMaterialIngredient(AnnealedCopper, 8), BOLT.getMaterialIngredient(Steel, 1)).io(new ItemStack(GT5RItems.SmallCoil, 2)).add("small_coil_3", 80, 8);
        ASSEMBLER.RB().ii(WIRE_FINE.getMaterialIngredient(AnnealedCopper, 8), BOLT.getMaterialIngredient(NickelZincFerrite, 1)).io(new ItemStack(GT5RItems.SmallCoil, 4)).add("small_coil_4", 80, 8);
    }

    private static void bloodyCircuits() {
        for (Material material : SOLDER.all()) {
            int base = L / 8;
            boolean hasGood = SOLDER.has(SubTag.GOOD_SOLDER, material);
            boolean hasBad = SOLDER.has(SubTag.BAD_SOLDER, material);
            base *= hasBad ? (hasGood ? 2 : 4) : 1;
            //Basic
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPhenolic, 1), of(GT5RTags.RESISTORS, 2),
                            WIRE_FINE.getMaterialIngredient(Copper, 4), of(GT5RItems.IntegratedLogicCircuit, 1))
                    .io(new ItemStack(BasicCircuit,1))
                    .fi(material.getLiquid(base * 4)).add("basic_circuit_using_" + material.getId(),200, 8);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.IntegratedLogicCircuit, 4),
                            of(GT5RTags.RESISTORS, 4), of(GT5RTags.CAPACITORS, 4),
                            of(GT5RTags.TRANSISTORS, 4), WIRE_FINE.getMaterialIngredient(Copper, 2))
                    .io(new ItemStack(GT5RItems.MicroProcessor, 4))
                    .fi(material.getLiquid(base * 4)).add("microprocessor_using_" + material.getId(), 200, 60);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.SOC, 4), WIRE_FINE.getMaterialIngredient(Copper, 2))
                    .io(new ItemStack(GT5RItems.MicroProcessor, 4))
                    .fi(material.getLiquid(base * 4)).add("microprocessor_soc_using_" + material.getId(), 200, 60);
            //Good
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPhenolic, 1), of(GT5RTags.RESISTORS, 4),of(BasicCircuit, 3),
                            WIRE_FINE.getMaterialIngredient(Electrum, 8))
                    .io(new ItemStack(GoodCircuit,1))
                    .fi(material.getLiquid(base * 4)).add("good_circuit_using_" + material.getId(),20*20, 16);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.CentralProcessingUnit, 1),
                            of(GT5RTags.RESISTORS, 2), of(GT5RTags.CAPACITORS, 2),
                            of(GT5RTags.TRANSISTORS, 2), WIRE_FINE.getMaterialIngredient(RedAlloy, 2))
                    .io(new ItemStack(GT5RItems.IntegratedProcessor, 4))
                    .fi(material.getLiquid(base * 4)).add("integrated_processor_using_" + material.getId(), 200, 60);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.SOC, 1), WIRE_FINE.getMaterialIngredient(RedAlloy, 2))
                    .io(new ItemStack(GT5RItems.IntegratedProcessor, 1))
                    .fi(material.getLiquid(base * 4)).add("integrated_processor_soc_using_" + material.getId(), 200, 60);
            //Advanced
            CIRCUIT_ASSEMBLER.RB().ii(of(GoodCircuit, 2), of(GT5RItems.IntegratedLogicCircuit, 3),of(GT5RItems.RandomAccessMemoryChip, 1),
                            of(GT5RTags.TRANSISTORS, 4), WIRE_FINE.getMaterialIngredient(Electrum, 16))
                    .io(new ItemStack(AdvancedCircuit,1))
                    .fi(material.getLiquid(base * 4)).add("advanced_circuit_using_" + material.getId(),800, 28);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.IntegratedProcessor, 2), of(GT5RItems.SmallCoil, 4),
                    of(GT5RTags.CAPACITORS, 4), of(GT5RItems.RandomAccessMemoryChip, 4), WIRE_FINE.getMaterialIngredient(RedAlloy, 12))
                    .io(GT5RItems.ProcessorAssembly)
                    .fi(material.getLiquid(base * 4)).add("processor_assembly_using_" + material.getId(), 200, 600);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.NanoCpu, 1),
                            of(GT5RTags.RESISTORS, 2), of(GT5RTags.CAPACITORS, 2),
                            of(GT5RTags.TRANSISTORS, 2), WIRE_FINE.getMaterialIngredient(Electrum, 2))
                    .io(new ItemStack(GT5RItems.NanoProcessor))
                    .fi(material.getLiquid(base * 4)).add("nano_processor_using_" + material.getId(), 200, 600);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic), of(GT5RItems.ASoC, 1), WIRE_FINE.getMaterialIngredient(Electrum, 2))
                    .io(new ItemStack(GT5RItems.NanoProcessor))
                    .fi(material.getLiquid(base * 4)).add("nano_processor_asoc_using_" + material.getId(), 300, 8192);

            //Extreme
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardPlastic, 2), of(GT5RItems.ProcessorAssembly, 3), of(GT5RTags.DIODES, 4),
                            of(GT5RItems.RandomAccessMemoryChip, 4), WIRE_FINE.getMaterialIngredient(Electrum, 6))
                    .io(new ItemStack(GT5RItems.Workstation))
                    .fi(material.getLiquid(base * 8)).add("workstation_using_" + material.getId(), 400, 90);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.NanoProcessor, 2), of(GT5RItems.SmallCoil, 4), of(GT5RItems.SMDCapacitor, 4),
                            of(GT5RItems.RandomAccessMemoryChip, 4), WIRE_FINE.getMaterialIngredient(Electrum, 6))
                    .io(new ItemStack(GT5RItems.NanoprocessorAssembly))
                    .fi(material.getLiquid(base * 8)).add("nanoprocessor_assembly_using_" + material.getId(), 400, 600);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardFiber), of(GT5RItems.QBitProcessingUnit), of(GT5RItems.NanoCpu), of(GT5RItems.SMDCapacitor, 2),
                            of(GT5RItems.SMDTransistor, 2), WIRE_FINE.getMaterialIngredient(Platinum, 2))
                    .io(new ItemStack(GT5RItems.QuantumProcessor))
                    .fi(material.getLiquid(base * 8)).add("quantum_processor_using_" + material.getId(), 200, 2400);
            CIRCUIT_ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardEpoxy), of(GT5RItems.ASoC, 1), WIRE_FINE.getMaterialIngredient(Platinum, 2))
                    .io(new ItemStack(GT5RItems.QuantumProcessor))
                    .fi(material.getLiquid(base * 4)).add("quantum_processor_asoc_using_" + material.getId(), 50, 8192);
            //Energy Flow
            ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardMultiFiber, 1), of(GT5RItems.Resistor, 8),of(GT5RItems.Transistor, 8),
                            of(GT5RItems.Capacitor, 8),of(GTMaterialTypes.WIRE_FINE.get(NiobiumTitanium), 4))
                    .io(new ItemStack(EnergyFlowCircuit,1))
                    .fi(material.getLiquid(base * 4)).add("energy_flow_circuit_using_" + material.getId(),20*20, 8192);
            //Wetware
            ASSEMBLER.RB().ii(of(GT5RItems.CircuitBoardWetware, 1), of(GT5RItems.Resistor, 8),of(GT5RItems.Transistor, 8),
                            of(GT5RItems.Capacitor, 8),of(GTMaterialTypes.WIRE_FINE.get(YttriumBariumCuprate), 4))
                    .io(new ItemStack(GT5RItems.CircuitWetware,1))
                    .fi(material.getLiquid(base * 4)).add("wetware_circuit_using_" + material.getId(),20*20, 32768);
        }
    }
}
