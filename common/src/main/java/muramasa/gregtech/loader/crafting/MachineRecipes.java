package muramasa.gregtech.loader.crafting;

import com.google.common.collect.ImmutableMap;
import io.github.gregtechintergalactical.gtcore.GTCore;
import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import io.github.gregtechintergalactical.gtcore.data.GTCoreMaterials;
import io.github.gregtechintergalactical.gtcore.machine.*;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.ForgeCTags;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemCover;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Wire;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.GregTech;
import muramasa.gregtech.GregTechConfig;
import muramasa.gregtech.block.BlockCasing;
import muramasa.gregtech.data.*;
import muramasa.gregtech.machine.MultiblockTankMachine;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreTags.*;
import static muramasa.antimatter.data.AntimatterDefaultTools.*;
import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.machine.Tier.*;
import static io.github.gregtechintergalactical.gtcore.data.GTCoreItems.*;
import static muramasa.gregtech.data.Machines.*;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.TierMaps.*;

public class MachineRecipes {
    public static void loadRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
        addBasicMachineRecipes(output, provider);
        addHatchRecipes(output, provider);
        addMultiblockRecipes(output, provider);
        addUtilityBlockRecipes(output, provider);
        addStorageTransformerRecipes(output, provider);
    }

    private static void addBasicMachineRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        Arrays.stream(Tier.getAllElectric()).forEach(tier -> {
            Item motor = AntimatterAPI.get(ItemBasic.class, "motor_"+tier.getId(), GTCore.ID);
            if (motor == null) return;
            Item piston = GregTech.get(ItemBasic.class, "piston_"+tier.getId());
            if (piston == null) return;
            Item arm = GregTech.get(ItemCover.class, "robot_arm_"+tier.getId());
            if (arm == null) return;
            Item conveyor = GregTech.get(ItemCover.class, "conveyor_"+tier.getId());
            if (conveyor == null) return;
            Item pump = GregTech.get(ItemCover.class, "pump_"+tier.getId());
            if (pump == null) return;
            Item hull = Item.BY_BLOCK.get(GregTech.get(BlockCasing.class, "hull_" + tier.getId()));
            if (hull == null) return;
            Item sensor = GregTech.get(ItemBasic.class, "sensor_"+tier.getId());
            if (sensor == null) return;
            Item emitter = GregTech.get(ItemBasic.class, "emitter_"+tier.getId());
            if (emitter == null) return;
            Item field = GregTech.get(ItemBasic.class, "field_gen_"+tier.getId());
            if (field == null) return;
            TagKey<Item> circuit = TIER_CIRCUITS.apply(tier);
            if (circuit == null) return;
            Object cable = CABLE_GETTER.apply(PipeSize.VTINY, tier, true);
            if (cable == null) return;
            TagKey<Item> rotor = TierMaps.TIER_ROTORS.get(tier) == null ? null : ROTOR.getMaterialTag(TIER_ROTORS.get(tier));
            if (rotor == null) return;
            Item glass = Items.GLASS;
            Object diamond = Items.DIAMOND;
            Material material = TIER_MATERIALS.getOrDefault(tier, Material.NULL);

            add(ALLOY_SMELTER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('L', TierMaps.WIRE_GETTER.apply(PipeSize.SMALL, tier))
                            .put('H', hull)
                            .put('C', circuit)
                            .put('G', cable).build(), "CLC", "LHL", "GLG"));
            Tier tier2 = tier == LV ? tier : Tier.getTier(tier.getVoltage() * 4);
            add(AMP_FABRICATOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', TIER_CIRCUITS.apply(tier2))
                            .put('W', CABLE_GETTER.apply(PipeSize.SMALL, tier, true))
                            .put('H', hull)
                            .put('P', pump).build(), "WPW", "PHP", "CPC"));

            add(ARC_FURNACE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'C', circuit,
                            'W', DUST.get(Materials.Graphite),
                            'L', CABLE_GETTER.apply(PipeSize.SMALL, tier, true),
                            'H', PLATE.get(material),
                            'M', hull
                    ), "LWL", "CMC", "HHH"));
            add(ASSEMBLER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'R', arm,
                            'O', conveyor,
                            'C', circuit,
                            'L', cable,
                            'H', hull
                    ), "RCR", "OHO", "LCL"));


            add(AUTOCLAVE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('T', AntimatterMaterialTypes.PLATE.getMaterialTag(material))
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('P', pump).build(), "TGT", "THT", "CPC"));
            add(BATH, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('W', WRENCH.getTag())
                            .put('H', hull)
                            .put('C', ITEM_CASING.getMaterialTag(material))
                            .put('S', PLATE.getMaterialTag(material))
                            .build(), "CWC", "SHS", "SSS"));
            add(BENDER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'P', piston,
                            'M', motor,
                            'C', circuit,
                            'L', cable,
                            'H', hull
                    ), "PLP", "CHC", "MLM"));
            add(CANNER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('L', cable)
                            .put('P', pump)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('G', glass).build(), "LPL", "CHC", "GGG"));

            add(CENTRIFUGE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'M', motor,
                            'C', circuit,
                            'L', cable,
                            'H', hull
                    ), "CMC", "LHL", "CMC"));
            add(CHEMICAL_REACTOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('G', glass)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('R', rotor)
                            .put('L', cable).build(), "GRG", "LML", "CHC"));
            add(COMPRESSOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of('C', circuit, 'P', piston, 'L', cable, 'H', hull), "LCL", "PHP", "LCL"));
            add(CROP_HARVESTER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('R', arm)
                            .put('C', circuit)
                            .put('P', piston)
                            .put('H', hull)
                            .put('S', sensor)
                            .put('B', SWORD_BLADE.getMaterialTag(material))
                            .put('c', conveyor)
                            .build(), "RCR", "PHS", "BcB"));

            add(CUTTER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('C', circuit)
                            .put('L', cable)
                            .put('H', hull)
                            .put('D', DiamondSawBlade)
                            .put('V', conveyor)
                            .put('G', glass).build(), "LCG", "VHD", "CLM"));
            add(DEHYDRATOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('W', WIRE_GETTER.apply(PipeSize.SMALL, tier))
                            .put('C', CABLE_GETTER.apply(PipeSize.SMALL, tier, true))
                            .put('H', hull)
                            .put('c', circuit)
                            .put('P', PLATE.getMaterialTag(material))
                            .put('R', arm).build(), "WcW", "CHC", "PRP"));
            add(DISASSEMBLER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('R', arm)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', CABLE_GETTER.apply(PipeSize.VTINY, tier, false)).build(), "RCR", "LHL", "RCR"));
            add(DISTILLERY, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('Z', Items.BLAZE_ROD)
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('L', cable).build(), "GZG", "CHC", "LPL"));

            add(ELECTRIC_OVEN, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    of('W', WIRE_GETTER.apply(PipeSize.TINY, tier), 'c', cable, 'C', circuit, 'H', hull), "WCW", "WHW", "cCc"));

            Wire<?> electroWire = tier == LV ? GregTechBlocks.WIRE_GOLD : tier == MV ? GregTechBlocks.WIRE_SILVER : tier == HV ? GregTechBlocks.WIRE_ELECTRUM : tier == EV ? GregTechBlocks.WIRE_PLATINUM : GregTechBlocks.WIRE_OSMIUM;
            add(ELECTROLYZER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'C', circuit,
                            'W', electroWire.getBlockItem(PipeSize.VTINY),
                            'L', cable,
                            'H', hull,
                            'G', glass
                    ), "WGW", "WHW", "CLC"));
            Item esWire = tier == LV ? GregTechBlocks.WIRE_TIN.getBlockItem(PipeSize.TINY) : tier == MV ? GregTechBlocks.WIRE_COPPER.getBlockItem(PipeSize.TINY) : tier == HV ? GregTechBlocks.WIRE_COPPER.getBlockItem(PipeSize.SMALL) : GregTechBlocks.WIRE_ANNEALED_COPPER.getBlockItem(PipeSize.NORMAL);
            add(ELECTROMAGNETIC_SEPARATOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('c', conveyor)
                            .put('W', cable)
                            .put('w', esWire)
                            .put('H', hull)
                            .put('R', ROD.getMaterialTag(material))
                            .put('C', circuit)
                            .build(), "cWw", "WHR", "CWw"));
            add(EXTRACTOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', piston)
                            .put('M', pump)
                            .put('G', glass)
                            .put('C', circuit)
                            .put('L', cable)
                            .put('H', hull).build(), "GCG", "PHM", "LCL"));

            add(EXTRUDER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', piston)
                            .put('I', TIER_PIPES.get(tier).apply(PipeSize.NORMAL))
                            .put('W', WIRE_GETTER.apply(PipeSize.SMALL, tier))
                            .put('C', circuit)
                            .put('H', hull).build(), "WWC", "PHI", "WWC"));
            add(FERMENTER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('L', cable).build(), "LPL", "GHG", "LCL"));
            add(FLUID_CANNER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('G', glass)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable).build(), "GCG", "PHP", "LCL"));
            add(FLUID_HEATER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('W', WIRE_GETTER.apply(PipeSize.SMALL, tier))
                            .put('L', cable).build(), "WGW", "PHP", "LCL"));
            add(FLUID_PRESS, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('T', piston)
                            .put('L', cable).build(), "GCG", "PHT", "LCL"));
            add(FLUID_SOLIDIFIER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', pump)
                            .put('C', circuit)
                            .put('G', glass)
                            .put('H', hull)
                            .put('E', ForgeCTags.CHESTS)
                            .put('L', cable).build(), "PGP", "LHL", "CEC"));
            add(FORGE_HAMMER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('L', cable)
                            .put('P', piston)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('A', Items.ANVIL).build(), "LPL", "CHC", "LAL"));
            add(FORMING_PRESS, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('L', cable)
                            .put('P', piston)
                            .put('C', circuit)
                            .put('H', hull)
                            .build(), "LPL", "CHC", "LPL"));
            add(FURNACE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of('C', circuit, 'L', cable, 'H', hull, 'W', TierMaps.WIRE_GETTER.apply(PipeSize.TINY, tier)), "CWC", "WHW", "LWL"));

            add(LATHE, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', piston)
                            .put('M', motor)
                            .put('C', circuit)
                            .put('L', cable)
                            .put('H', hull)
                            .put('D', diamond).build(), "LCL", "MHD", "CLP"));
            add(LASER_ENGRAVER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', piston)
                            .put('E', emitter)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable).build(), "PEP", "CHC", "LCL"));
            TagKey<Item> grindHead = tier == LV || tier == MV ? GEM.getMaterialTag(Diamond) : GregTechTags.GRIND_HEADS;
            add(MACERATOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('P', piston)
                            .put('M', motor)
                            .put('C', circuit)
                            .put('L', cable)
                            .put('H', hull)
                            .put('D', grindHead).build(), "PMD", "LLH", "CCL"));
            add(MASS_FABRICATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('F', field)
                            .put('B', CABLE_GETTER.apply(PipeSize.SMALL, tier, true))
                            .put('C', circuit)
                            .put('H', hull).build(), "CFC", "BHB", "CFC"));
            add(MIXER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'M', motor,
                            'R', rotor,
                            'G', glass,
                            'C', circuit,
                            'H', hull
                    ), "GRG", "GMG", "CHC"));
            add(ORE_WASHER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('G', glass)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('R', rotor)
                            .put('L', cable).build(), "RGR", "CMC", "LHL"));
            add(PACKAGER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('c', conveyor)
                            .put('R', arm)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable)
                            .put('S', ForgeCTags.CHESTS).build(), "SCS", "RHc", "LCL"));
            Item wire = tier == LV ? GregTechBlocks.WIRE_TIN.getBlockItem(PipeSize.TINY) : tier == MV ? GregTechBlocks.WIRE_COPPER.getBlockItem(PipeSize.TINY) : tier == HV ? GregTechBlocks.WIRE_COPPER.getBlockItem(PipeSize.SMALL) : GregTechBlocks.WIRE_ANNEALED_COPPER.getBlockItem(PipeSize.NORMAL);
            Material rodMaterial = tier == LV ? Iron : tier == MV || tier == HV ? Steel : tier == EV ? Neodymium : VanadiumGallium;
            add(POLARIZER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('R', ROD.getMaterialTag(rodMaterial))
                            .put('W', wire)
                            .put('H', hull)
                            .put('L', cable).build(), "WRW", "LHL", "WRW"));
            add(PRINTER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable).build(), "MLM", "CHC", "LML"));

            add(PUMP, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('R', rotor)
                            .put('H', hull)
                            .put('M', motor)
                            .put('P', TIER_PIPES.get(tier).apply(PipeSize.LARGE))
                            .put('C', circuit).build(), "MCM", "PHP", "RPR"));
            add(RECYCLER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('G', Items.GLOWSTONE_DUST)
                            .put('P', piston)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable).build(), "GCG", "PHP", "LCL"));
            add(REPLICATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('F', field)
                            .put('E', emitter)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('B', CABLE_GETTER.apply(PipeSize.SMALL, tier, true))
                            .build(), "EFE", "CHC", "EBE"));
            add(ROASTER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('S', CABLE_GETTER.apply(PipeSize.SMALL, tier, true))
                            .put('V', CABLE_GETTER.apply(PipeSize.VTINY, tier, true))
                            .put('C', circuit)
                            .put('H', hull)
                            .put('W', WIRE_GETTER.apply(PipeSize.TINY, tier)).build(), "SVS", "CHC", "WWW"));
            add(SCANNER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('S', sensor)
                            .put('E', emitter)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', CABLE_GETTER.apply(PipeSize.VTINY, tier, false)).build(), "CEC", "LHL", "CSC"));
            TagKey<Item> plate = PLATE.getMaterialTag(tier == LV ? Steel : VanadiumSteel);
            add(SEISMIC_PROSPECTOR, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    of('P', plate, 'C', circuit, 'H', hull, 'S', sensor), "PPP", "CHC", "SSS"));
            add(SIFTER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', circuit)
                            .put('P', piston)
                            .put('L', cable)
                            .put('H', hull)
                            .put('F', GregTechCovers.COVER_ITEM_FILTER.getItem())
                            .build(), "LFL", "PHP", "CFC"));
            add(SMELTER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('W', WIRE_GETTER.apply(PipeSize.TINY, tier))
                            .put('C', circuit)
                            .put('H', hull).build(), "WWW", "WHW", "WCW"));
            add(THERMAL_CENTRIFUGE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('W', TierMaps.WIRE_GETTER.apply(tier == IV ? PipeSize.HUGE : PipeSize.SMALL, tier))
                            .put('L', cable).build(), "CMC", "WHW", "LML"));
            add(UNPACKAGER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('c', conveyor)
                            .put('R', arm)
                            .put('C', circuit)
                            .put('H', hull)
                            .put('L', cable)
                            .put('S', ForgeCTags.CHESTS).build(), "SCS", "cHR", "LCL"));
            add(WIRE_MILL, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.of(
                            'M', motor,
                            'C', circuit,
                            'L', cable,
                            'H', hull
                    ), "MLM", "CHC", "MLM"));



            add(STEAM_GENERATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('L', cable)
                            .put('H', hull)
                            .put('R', rotor)
                            .put('C', circuit)
                            .put('P',  TIER_PIPES.get(tier).apply(PipeSize.NORMAL))
                            .build(), "PCP", "RHR", "MLM"));
            add(COMBUSTION_GENERATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('L', cable)
                            .put('H', hull)
                            .put('G', GEAR.getMaterialTag(material))
                            .put('C', circuit)
                            .put('P',  piston)
                            .build(), "PCP", "MHM", "GLG"));
            add(SEMIFLUID_GENERATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('L', cable)
                            .put('H', hull)
                            .put('G', Blocks.MAGMA_BLOCK)
                            .put('C', circuit)
                            .put('P',  PLATE.getMaterialTag(Invar))
                            .build(), "PCP", "MHM", "GLG"));
            add(GAS_GENERATOR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('M', motor)
                            .put('L', cable)
                            .put('H', hull)
                            .put('R', rotor)
                            .put('C', circuit)
                            .build(), "CRC", "RHR", "MLM"));
        });
        provider.addItemRecipe(output, "trash_bin", GTCoreBlocks.ENDER_GARBAGE_BIN.getItem(NONE),
                of('O', PLATE.getMaterialTag(Obsidian), 'I', PLATE.getMaterialTag(Iron), 'E', Items.ENDER_EYE), "OOO", "OEO", "III");

        provider.addItemRecipe(output, "solar_panels", SOLAR_PANEL.getItem(NONE),
                of('S', GregTechItems.Wafer, 'G', Items.GLASS_PANE, 'C', CIRCUITS_BASIC,
                        'P', PLATE.getMaterialTag(Carbon), 'H', GregTechBlocks.HULL_ULV, 'W', GregTechBlocks.CABLE_SOLDERING_ALLOY.getBlockItem(PipeSize.VTINY)), "SGS", "CPC", "WHW");
        provider.addItemRecipe(output, "solar_panels", SOLAR_PANEL.getItem(ULV),
                of('S', SOLAR_PANEL.getItem(NONE), 'C', CIRCUITS_ADVANCED), "SSS", "SCS", "SSS");
        provider.addItemRecipe(output, "solar_panels", SOLAR_PANEL.getItem(LV),
                of('S', SOLAR_PANEL.getItem(ULV), 'T', TRANSFORMER.getItem(ULV), 'C', TIER_CIRCUITS.apply(EV)), "CSC", "STS","CSC");

        provider.addItemRecipe(output, "machines", NUCLEAR_REACTOR_CORE.getItem(NONE),
                of('C', TIER_CIRCUITS.apply(Tier.IV), 'P', GregTechItems.PistonEV, 'L', GregTechBlocks.CASING_DENSE_LEAD), "PCP", "CLC", "PCP");
    }

    private static void addStorageTransformerRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        Arrays.stream(Tier.getAllElectric()).forEach(tier -> {
            Item hull = Item.BY_BLOCK.get(GregTech.get(BlockCasing.class, "hull_" + tier.getId()));
            if (hull == null) return;
            add(BATTERY_BUFFER_ONE, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .put('L', TierMaps.TIER_WIRES.get(tier).getPipe().getType().getBlockItem(PipeSize.VTINY)).build(), "LCL", "LHL"));

            add(BATTERY_BUFFER_FOUR, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .put('L', TierMaps.TIER_WIRES.get(tier).getPipe().getType().getBlockItem(PipeSize.SMALL)).build(), "LCL", "LHL"));

            add(BATTERY_BUFFER_EIGHT, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .put('L', TierMaps.TIER_WIRES.get(tier).getPipe().getType().getBlockItem(PipeSize.NORMAL)).build(), "LCL", "LHL"));
            add(BATTERY_BUFFER_SIXTEEN, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .put('L', TierMaps.TIER_WIRES.get(tier).getPipe().getType().getBlockItem(PipeSize.HUGE)).build(), "LCL", "LHL"));
        });
        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.ULV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_ULV)
                        .put('C', GregTechBlocks.CABLE_SOLDERING_ALLOY.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_TIN.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");

        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.LV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_LV)
                        .put('C', GregTechBlocks.CABLE_TIN.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_COPPER.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");

        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(MV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_MV)
                        .put('C', GregTechBlocks.CABLE_COPPER.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_GOLD.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");

        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.HV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', GregTechBlocks.CABLE_GOLD.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_ALUMINIUM.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");

        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.EV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_EV)
                        .put('C', GregTechBlocks.CABLE_ALUMINIUM.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_TUNGSTEN.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");

        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.IV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_IV)
                        .put('C', GregTechBlocks.CABLE_TUNGSTEN.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_VANADIUM_GALLIUM.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");
        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(Tier.LUV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_LUV)
                        .put('C', GregTechBlocks.CABLE_VANADIUM_GALLIUM.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.CABLE_NAQUADAH.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");
        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(ZPM),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_ZPM)
                        .put('C', GregTechBlocks.CABLE_NAQUADAH.getBlockItem(PipeSize.VTINY))
                        .put('W', GregTechBlocks.WIRE_NAQUADAH_ALLOY.getBlockItem(PipeSize.SMALL)).build(), " CC", "WH ", " CC");
        provider.addItemRecipe(output, "machines", TRANSFORMER.getItem(UV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_UV)
                        .put('C', GregTechBlocks.WIRE_NAQUADAH_ALLOY.getBlockItem(PipeSize.SMALL))
                        .put('W', GregTechBlocks.WIRE_SUPERCONDUCTOR.getBlockItem(PipeSize.VTINY)).build(), " CC", "WH ", " CC");
    }

    private static void addUtilityBlockRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        Arrays.stream(Tier.getAllElectric()).forEach(tier -> {
            Item hull = Item.BY_BLOCK.get(GregTech.get(BlockCasing.class, "hull_" + tier.getId()));
            if (hull == null) return;
            Item conveyor = GregTech.get(ItemCover.class, "conveyor_"+tier.getId());
            if (conveyor == null) return;
            add(SUPER_BUFFER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('D', DataOrb)
                            .put('M', hull)
                            .put('C', conveyor).build(), "DMC"));
            add(SUPER_BUFFER, tier, (m, item) -> provider.addItemRecipe(output, GTIRef.ID, "super_buffer_" + tier.getId() +"_1", "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('D', GregTechItems.DataStick)
                            .put('M', hull)
                            .put('C', conveyor).build(), "DMC", "DDD"));
            add(CHEST_BUFFER, tier, (m, item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('D', ForgeCTags.CHESTS_WOODEN)
                            .put('M', hull)
                            .put('C', conveyor)
                            .put('c', TIER_CIRCUITS.apply(LV)).build(), "DMC", " c "));

            add(ELECTRIC_TYPE_FILTER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('H', hull)
                            .put('C', TIER_CIRCUITS.apply(HV))
                            .put('F', GregTechCovers.COVER_ITEM_FILTER.getItem())
                            .put('E', ForgeCTags.CHESTS)
                            .put('c', conveyor).build(), " F ", "EHc", " C "));
            add(ELECTRIC_ITEM_FILTER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('H', hull)
                            .put('C', TIER_CIRCUITS.apply(LV))
                            .put('F', GregTechCovers.COVER_ITEM_FILTER.getItem())
                            .put('E', ForgeCTags.CHESTS)
                            .put('c', conveyor).build(), " F ", "EHc", " C "));
        });
        provider.addItemRecipe(output, "mini_portals", MINIATURE_NETHER_PORTAL.getItem(NONE), of('O', ROD_LONG.get(Obsidian), 'S', SAW.getTag()), "OOO", "OSO", "OOO");
        provider.addItemRecipe(output, "mini_portals", MINIATURE_END_PORTAL.getItem(NONE), of('R', ROD_LONG.get(Endstone), 'G', Items.GHAST_TEAR, 'E', Items.ENDER_EYE), "ERE", "RGR", "ERE");
        if (AntimatterAPI.isModLoaded(Ref.MOD_TWILIGHT)){
            provider.addItemRecipe(output, "mini_portals", MINIATURE_TWILIGHT_PORTAL.getItem(NONE), of('R', Items.GRASS_BLOCK, 'G', Items.WATER_BUCKET, 'E', ItemTags.SMALL_FLOWERS), "ERE", "RGR", "ERE");
        }

        var circuit = GregTechConfig.HARDER_CIRCUITS ? CIRCUITS_ADVANCED : EngravedCrystalChip;
        provider.addItemRecipe(output, "machines", QUANTUM_TANK.getItem(Tier.LV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_LV)
                        .put('C', circuit)
                        .put('F', GregTechItems.FieldGenLV)
                        .put('P', PLATE.get(Steel)).build(), "CFC", "PHP", "CPC");

        circuit = GregTechConfig.HARDER_CIRCUITS ? CIRCUITS_COMPLEX : CIRCUITS_DATA;
        provider.addItemRecipe(output, "machines", QUANTUM_TANK.getItem(MV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_MV)
                        .put('C', circuit)
                        .put('F', GregTechItems.FieldGenMV)
                        .put('P', PLATE.get(Aluminium)).build(), "CFC", "PHP", "CPC");

        provider.addItemRecipe(output, "machines", QUANTUM_TANK.getItem(Tier.HV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', CIRCUITS_ELITE)
                        .put('F', GregTechItems.FieldGenHV)
                        .put('P', PLATE.get(StainlessSteel)).build(), "CFC", "PHP", "CPC");

        provider.addItemRecipe(output, "machines", QUANTUM_TANK.getItem(Tier.EV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_EV)
                        .put('C', CIRCUITS_MASTER)
                        .put('F', GregTechItems.FieldGenEV)
                        .put('P', PLATE.get(Titanium)).build(), "CFC", "PHP", "CPC");

        circuit = GregTechConfig.HARDER_CIRCUITS ? CIRCUITS_DATA_ORB : CIRCUITS_DATA_ORB;
        provider.addItemRecipe(output, "machines", QUANTUM_TANK.getItem(Tier.IV),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_IV)
                        .put('C', circuit)
                        .put('F', GregTechItems.FieldGenIV)
                        .put('P', PLATE.get(TungstenSteel)).build(), "CFC", "PHP", "CPC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_TRANSFORMER_ENDPOINT.getItem(EV),
                of('T', TRANSFORMER.getItem(EV), 'C', CABLE_GETTER.apply(PipeSize.SMALL, MV, false), 'W', WIRE_CUTTER.getTag()), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_TRANSFORMER_ENDPOINT.getItem(IV),
                of('T', TRANSFORMER.getItem(IV), 'C', CABLE_GETTER.apply(PipeSize.SMALL, MV, false), 'W', WIRE_CUTTER.getTag()), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_TRANSFORMER_ENDPOINT.getItem(LUV),
                of('T', TRANSFORMER.getItem(LUV), 'C', CABLE_GETTER.apply(PipeSize.SMALL, MV, false), 'W', WIRE_CUTTER.getTag()), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_TRANSFORMER_ENDPOINT.getItem(ZPM),
                of('T', TRANSFORMER.getItem(ZPM), 'C', CABLE_GETTER.apply(PipeSize.SMALL, MV, false), 'W', WIRE_CUTTER.getTag()), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_TRANSFORMER_ENDPOINT.getItem(UV),
                of('T', TRANSFORMER.getItem(UV), 'C', CABLE_GETTER.apply(PipeSize.SMALL, MV, false), 'W', WIRE_CUTTER.getTag()), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_FLUID_ENDPOINT.getItem(NONE),
                of('T', GregTechBlocks.FLUID_PIPE_TUNGSTEN.getBlock(PipeSize.NORMAL), 'C', PLATE.getMaterialTag(Plastic), 'W', GregTechBlocks.CASING_TUNGSTEN), "CTC", "TWT", "CTC");
        provider.addItemRecipe(output, "machines", LONG_DISTANCE_ITEM_ENDPOINT.getItem(NONE),
                of('T', GregTechBlocks.ITEM_PIPE_PLATINUM.getBlock(PipeSize.NORMAL), 'C', PLATE.getMaterialTag(Plastic), 'W', GregTechBlocks.CASING_PLATINUM), "CTC", "TWT", "CTC");
        AntimatterAPI.all(WorkbenchMachine.class).forEach(m -> {
            if (!m.getId().contains("charging")) {
                provider.addItemRecipe(output, GTIRef.ID, "", "machines", m.getItem(NONE),
                        of('P', PLATE.getMaterialTag(m.getMaterial()), 'C', ForgeCTags.CHESTS_WOODEN, 'c', Items.CRAFTING_TABLE, 'S', SCREWDRIVER.getTag()), "PSP", "PcP", "PCP");
            } else {
                provider.addItemRecipe(output, Ref.ID, "", "machines", m.getItem(HV),
                        of('S', SCREWDRIVER.getTag(), 'w', WIRE_CUTTER.getTag(), 'W', Machine.get(m.getId().replace("charging_", ""), GTCore.ID).map(mch -> mch.getItem(NONE)).orElse(Items.AIR), 'c', CABLE_GETTER.apply(PipeSize.SMALL, HV, false), 'C', CIRCUITS_ADVANCED, 'R', ROD.getMaterialTag(m.getMaterial())), "RCR", "SWw", "ccc");
            }
        });
        AntimatterAPI.all(LockerMachine.class).forEach(m -> {
            Material material = m.getMaterial();
            ChestMachine chest = AntimatterAPI.get(ChestMachine.class, material.getId() + "_chest", GTCore.ID);
            if (material.has(SCREW) && chest != null){
                if (!m.getId().contains("charging")) {
                    provider.addItemRecipe(output, GTIRef.ID, "", "machines", m.getItem(NONE),
                            of('s', SCREW.getMaterialTag(m.getMaterial()), 'C', chest.getItem(NONE), 'c', GregTechBlocks.CASING_SOLID_STEEL, 'S', SCREWDRIVER.getTag(), 'R', ROD.getMaterialTag(material), 'L', Items.LEATHER), "RSR", "LCL", "scs");
                } else {
                    provider.addItemRecipe(output, Ref.ID, "", "machines", m.getItem(HV),
                            of('W', Machine.get(m.getId().replace("charging_", ""), GTCore.ID).map(mch -> mch.getItem(NONE)).orElse(Items.AIR), 'c', CABLE_GETTER.apply(PipeSize.VTINY, HV, false), 'C', CIRCUITS_ADVANCED), "cCc", "cWc", "cCc");
                }
            }

        });
        AntimatterAPI.all(ChestMachine.class).forEach(m -> {
            Material material = m.getMaterial();
            if (material.has(RING) && material.has(PLATE)){
                provider.addItemRecipe(output, GTIRef.ID, "", "machines", m.getItem(NONE),
                        of('P', PLATE.getMaterialTag(material), 'R', ROD.getMaterialTag(material), 'r', RING.getMaterialTag(material), 'S', SAW.getTag(), 'W', WRENCH.getTag()), "SPW", "rRr", "PPP");
            }
        });
        AntimatterAPI.all(BarrelMachine.class).forEach(m -> {
            Material material = m.getMaterial();
            if (material.has(ROD) && material.has(PLATE)){
                provider.addItemRecipe(output, GTIRef.ID, "", "machines", m.getItem(NONE),
                        of('P', PLATE.getMaterialTag(material), 'R', ROD.getMaterialTag(material), 'S', SAW.getTag(), 'W', WRENCH.getTag()), "SPW", "PRP", " P ");
            }
        });
        AntimatterAPI.all(MassStorageMachine.class).forEach(m -> {
            Material material = m.getMaterial();
            ChestMachine chest = AntimatterAPI.get(ChestMachine.class, material.getId() + "_chest", GTCore.ID);
            if (material.has(SCREW) && material.has(PLATE) && !material.has(MaterialTags.WOOD) && chest != null){
                provider.addItemRecipe(output, GTIRef.ID, "", "machines", m.getItem(NONE),
                        of('C', chest.getItem(NONE), 'S', SCREW.getMaterialTag(material), 'c', GregTechBlocks.CASING_SOLID_STEEL, 's', SCREWDRIVER.getTag(), 'W', WRENCH.getTag()), "SCS", "Wcs", "SCS");
            }
        });

        AntimatterAPI.all(MultiblockTankMachine.class).forEach(m -> {
            if (m.isSmall()){
                Block block = AntimatterAPI.get(Block.class, m.getMaterial().getId() + "_wall", GTIRef.ID);
                if (block == null) return;
                Material ringMaterial = m.getMaterial() == Wood ? Lead : m.getMaterial();
                TagKey<Item> hammer = m.getMaterial() == Wood ? SOFT_HAMMER.getTag() : HAMMER.getTag();
                provider.addItemRecipe(output, "multiblock_tanks", m.getItem(NONE),
                        of('R', RING.getMaterialTag(ringMaterial), 'S', SAW.getTag(), 'H', hammer, 'W', block.asItem()), " R ", "HWS", " R ");
            } else {
                Block block = AntimatterAPI.get(Block.class, m.getId().replace("large", "small"), GTIRef.ID);
                if (block == null) return;
                provider.addItemRecipe(output, "multiblock_tanks", m.getItem(NONE),
                        of('P', PLATE.getMaterialTag(m.getMaterial()), 'S', SAW.getTag(), 'H', HAMMER.getTag(), 'W', block.asItem()), "PPP", "HWS", "PPP");
            }
        });
        provider.addItemRecipe(output, "item_barrels", GTCoreBlocks.WOOD_ITEM_BARREL.getItem(NONE), of('S', SOFT_HAMMER.getTag(), 'C', ForgeCTags.CHESTS, 'R', ROD_LONG.getMaterialTag(Lead), 'W', ItemTags.PLANKS, 's', SAW.getTag()), "SCs", "WRW", "WRW");
        if (GTCoreBlocks.IRONWOOD_ITEM_BARREL != null) {
            provider.addItemRecipe(output, "item_barrels", GTCoreBlocks.IRONWOOD_ITEM_BARREL.getItem(NONE), of('S', SOFT_HAMMER.getTag(), 'C', ForgeCTags.CHESTS, 'R', ROD_LONG.getMaterialTag(Iron), 'W', PLATE.getMaterialTag(GTCoreMaterials.Ironwood), 's', SAW.getTag()), "SCs", "WRW", "WRW");
        }
    }

    private static void addHatchRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        Arrays.stream(Tier.getAllElectric()).forEach(tier -> {
            Item hull = Item.BY_BLOCK.get(GregTech.get(BlockCasing.class, "hull_" + tier.getId()));
            if (hull == null) return;
            add(HATCH_ITEM_I, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .build(), "C", "H"));

            add(HATCH_FLUID_I, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('G', Items.GLASS)
                            .put('H', hull)
                            .build(), "G", "H"));

            add(HATCH_ITEM_O, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', ForgeCTags.CHESTS)
                            .put('H', hull)
                            .build(), "H", "C"));

            add(HATCH_FLUID_O, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('G', Items.GLASS)
                            .put('H', hull)
                            .build(), "H", "G"));

            add(HATCH_ENERGY, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', CABLE_GETTER.apply(PipeSize.VTINY, tier, false))
                            .put('H', hull)
                            .build(), "CH"));
            add(HATCH_DYNAMO, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', CABLE_GETTER.apply(PipeSize.VTINY, tier, false))
                            .put('H', hull)
                            .build(), "HC"));
            add(HATCH_MUFFLER, tier, (m,item) ->  provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('C', GregTechBlocks.FLUID_PIPE_STEEL.getBlockItem(PipeSize.NORMAL))
                            .put('H', hull)
                            .build(), "H", "C"));
        });
    }

    private static void addMultiblockRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider){
        add(BLAST_FURNACE, LV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('L', CABLE_GETTER.apply(PipeSize.VTINY, LV, true))
                        .put('H', GregTechBlocks.CASING_HEAT_PROOF)
                        .put('C', TIER_CIRCUITS.apply(LV))
                        .put('F', Items.FURNACE)
                        .build(), "FFF", "CHC", "LCL"));
        /*provider.addItemRecipe(output, "machines", "has_wrench", provider.hasSafeItem(WRENCH.getTag()), CHARCOAL_PIT.getItem(CHARCOAL_PIT.getFirstTier()),
                ImmutableMap.<Character, Object>builder()
                        .put('H', CASING_BRICKED_BRONZE)
                        .put('W', NUGGET.getMaterialTag(WroughtIron))
                        .put('F', Items.FLINT).build(), "WHW", "FFF");*/
        provider.addItemRecipe(output, "machines", COKE_OVEN.getItem(COKE_OVEN.getFirstTier()),
                ImmutableMap.<Character, Object>builder()
                        .put('H', FireBrick).build(), "HHH", "H H", "HHH");
        add(COMBUSTION_ENGINE, EV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('L', GregTechBlocks.CABLE_TUNGSTEN_STEEL.getBlockItem(PipeSize.VTINY))
                        .put('H', GregTechBlocks.HULL_EV)
                        .put('C', TIER_CIRCUITS.apply(EV))
                        .put('P', GregTechItems.PistonEV)
                        .put('G', GEAR.getMaterialTag(Titanium))
                        .put('M', MotorEV)
                        .build(), "PCP", "MHM", "GLG"));
        add(CRACKING_UNIT, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechCovers.COVER_PUMP.getItem(HV).getItem())
                        .put('O', GregTechBlocks.COIL_CUPRONICKEL)
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .build(), "OPO", "CHC", "OPO"));
        add(DISTLLATION_TOWER, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechCovers.COVER_PUMP.getItem(HV).getItem())
                        .put('I', GregTechBlocks.FLUID_PIPE_STAINLESS_STEEL.getBlock(PipeSize.LARGE))
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .build(), "CIC", "PHP", "CIC"));
        add(CRYO_DISTLLATION_TOWER, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechCovers.COVER_PUMP.getItem(HV).getItem())
                        .put('I', GregTechBlocks.FLUID_PIPE_COPPER.getBlock(PipeSize.LARGE))
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .build(), "CIC", "PHP", "CIC"));
        add(HEAT_EXCHANGER, EV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechCovers.COVER_PUMP.getItem(EV).getItem())
                        .put('I', GregTechBlocks.FLUID_PIPE_TITANIUM.getBlock(PipeSize.NORMAL))
                        .put('H', GregTechBlocks.CASING_PIPE_TITANIUM)
                        .build(), "PIP", "IHI", "PIP"));
        add(IMPLOSION_COMPRESSOR, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('L', CABLE_GETTER.apply(PipeSize.VTINY, HV, true))
                        .put('O', Items.OBSIDIAN)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .put('S', GregTechBlocks.CASING_SOLID_STEEL)
                        .build(), "OOO", "CSC", "LCL"));
        add(LARGE_CENTRIFUGE, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('M', MotorEV)
                        .put('H', GregTechBlocks.HULL_IV)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .build(), "CMC", "MHM", "CMC"));
        add(LARGE_CHEMICAL_REACTOR, HV, (m, item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .put('M', MotorHV)
                        .put('R', ROTOR.getMaterialTag(StainlessSteel))
                        .put('P', GregTechBlocks.FLUID_PIPE_PVC.getBlockItem(PipeSize.LARGE))
                        .put('H', GregTechBlocks.HULL_HV).build(), "CRC", "PMP", "CHC"));
        add(LARGE_ELECTROLYZER, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechBlocks.WIRE_PLATINUM.getBlockItem(PipeSize.SMALL))
                        .put('O', GregTechBlocks.COIL_NICHROME)
                        .put('H', GregTechBlocks.HULL_HV)
                        .put('C', TIER_CIRCUITS.apply(EV))
                        .build(), "OPO", "CHC", "OPO"));
        add(LARGE_MACERATOR, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('P', GregTechItems.PistonIV)
                        .put('M', MotorIV)
                        .put('T', PLATE.getMaterialTag(TungstenCarbide))
                        .put('G', GregTechTags.GRIND_HEADS)
                        .put('H', GregTechBlocks.HULL_IV)
                        .put('C', TIER_CIRCUITS.apply(IV))
                        .build(), "TGT", "PHP", "MCM"));
        Arrays.stream(getStandard()).filter(t -> t !=IV).forEach(tier -> {
            Block firebox = tier == LV ? GregTechBlocks.CASING_FIREBOX_BRONZE : tier == MV ? GregTechBlocks.CASING_FIREBOX_STEEL : tier == HV ? GregTechBlocks.CASING_FIREBOX_TITANIUM : GregTechBlocks.CASING_FIREBOX_TUNGSTENSTEEL;
            TagKey<Item> circuit2 = tier == LV ? TIER_CIRCUITS.apply(tier) : tier == MV ? CIRCUITS_ADVANCED : tier == HV ? CIRCUITS_ELITE : CIRCUITS_MASTER;
            add(LARGE_BOILER, tier, (m,item) -> provider.addItemRecipe(output, "machines", item,
                    ImmutableMap.<Character, Object>builder()
                            .put('L', CABLE_GETTER.apply(PipeSize.VTINY, tier, true))
                            .put('H', firebox)
                            .put('C', circuit2)
                            .build(), "LCL", "CHC", "LCL"));
        });

        Arrays.stream(new Tier[]{HV, EV, IV}).forEach(tier -> {
            Material gear = tier == HV ? Steel : tier == EV ? StainlessSteel : tier == IV ? Titanium : TungstenSteel;
            Tier pipe = tier == UV ? IV : Tier.getTier(tier.getVoltage() / 4);
            add(LARGE_TURBINE, tier, (m, item) -> {
                provider.addItemRecipe(output, "machines", item,
                        ImmutableMap.of('G', GEAR.getMaterialTag(gear),
                                'H', GregTech.get(BlockCasing.class, "hull_" + tier.getId()),
                                'C', TIER_CIRCUITS.apply(tier),
                                'P', TIER_PIPES.get(pipe).apply(PipeSize.LARGE)), "CGC", "GHG", "PGP");
            });
        });
        add(MULTI_SMELTER, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('L', CABLE_GETTER.apply(PipeSize.VTINY, HV, false))
                        .put('F', Items.FURNACE)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .put('H', GregTechBlocks.CASING_HEAT_PROOF)
                        .build(), "FFF", "CHC", "LCL"));
        add(OIL_DRILLING_RIG, MV, (m, item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('M', MotorMV)
                        .put('C', TIER_CIRCUITS.apply(MV))
                        .put('H', GregTechBlocks.HULL_MV)
                        .put('F', FRAME.getMaterialTag(Steel)).build(), "FFF", "CHC", "MMM"));
        provider.addItemRecipe(output, "machines", PRIMITIVE_BLAST_FURNACE.getItem(PRIMITIVE_BLAST_FURNACE.getFirstTier()),
                ImmutableMap.<Character, Object>builder()
                        .put('H', FireBrick)
                        .put('C', PLATE.getMaterialTag(Iron)).build(), "HHH", "HCH", "HHH");
        add(PROCESSING_ARRAY, EV, (m, item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('C', TIER_CIRCUITS.apply(IV))
                        .put('R', GregTechCovers.COVER_ROBOT_ARM.getItem(EV))
                        .put('L', BatteryEnergyOrb)
                        .put('M', GregTechBlocks.HULL_EV)
                        .put('S', GregTechBlocks.FLUID_PIPE_STAINLESS_STEEL.getBlockItem(PipeSize.LARGE)).build(), "CLC", "RMR", "CSC"));
        provider.addItemRecipe(output, "machines", PYROLYSIS_OVEN.getItem(PYROLYSIS_OVEN.getFirstTier()),
                ImmutableMap.<Character, Object>builder()
                        .put('H', GregTechBlocks.HULL_MV)
                        .put('C', CIRCUITS_GOOD)
                        .put('P', GregTechCovers.COVER_PUMP.getItem(MV).getItem())
                        .put('W', GregTechBlocks.WIRE_CUPRONICKEL.getBlockItem(PipeSize.SMALL))
                        .put('B', GregTechItems.PistonMV).build(), "BCW", "CHC", "BPW");
        provider.addItemRecipe(output, "machines", TREE_GROWTH_SIMULATOR.getItem(LV),
                ImmutableMap.<Character, Object>builder()
                        .put('E', GregTechItems.EmitterLV)
                        .put('S', GregTechItems.SensorLV)
                        .put('H',GregTechBlocks.CASING_BLACK_BRONZE)
                        .put('R', DiamondSawBlade)
                        .put('P', GregTechBlocks.FLUID_PIPE_PLASTIC.getBlockItem(PipeSize.SMALL))
                        .put('N', PLATE.getMaterialTag(Nickel)).build(), "ERE", "NHN", "SPS");
        add(VACUUM_FREEZER, HV, (m,item) -> provider.addItemRecipe(output, "machines", item,
                ImmutableMap.<Character, Object>builder()
                        .put('L', CABLE_GETTER.apply(PipeSize.VTINY, HV, true))
                        .put('F', GregTechBlocks.CASING_FROST_PROOF)
                        .put('C', TIER_CIRCUITS.apply(HV))
                        .put('P', GregTechCovers.COVER_PUMP.getItem(HV).getItem())
                        .build(), "PPP", "CFC", "LCL"));
    }

    private static <T extends Machine<T>> void add(T machine, Tier tier, BiConsumer<T, Item> callback) {
        Item item = machine.getItem(tier);
        if (item != null) callback.accept(machine, item);
    }
}
