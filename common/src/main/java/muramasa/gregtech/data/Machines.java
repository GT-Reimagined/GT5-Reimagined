package muramasa.gregtech.data;

import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import io.github.gregtechintergalactical.gtcore.machine.DrumMachine;
import muramasa.antimatter.Ref;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.blockentity.single.BlockEntityBatteryBuffer;
import muramasa.antimatter.blockentity.single.BlockEntityDigitalTransformer;
import muramasa.antimatter.blockentity.single.BlockEntityTransformer;
import muramasa.antimatter.cover.ICover;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.machine.BlockMachine;
import muramasa.antimatter.machine.MachineState;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.types.*;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.GT5RRef;
import muramasa.gregtech.GT5Reimagined;
import muramasa.gregtech.block.BlockCasing;
import muramasa.gregtech.blockentity.miniportals.BlockEntityMiniEndPortal;
import muramasa.gregtech.blockentity.miniportals.BlockEntityMiniNetherPortal;
import muramasa.gregtech.blockentity.miniportals.BlockEntityMiniTwilightPortal;
import muramasa.gregtech.blockentity.multi.*;
import muramasa.gregtech.blockentity.single.*;
import muramasa.gregtech.client.GT5RModelManager;
import muramasa.gregtech.items.IItemReactorRod;
import muramasa.gregtech.machine.MiniPortalMachine;
import muramasa.gregtech.machine.MultiblockTankMachine;
import muramasa.gregtech.machine.SecondaryOutputMachine;
import muramasa.gregtech.machine.SteamMachine;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

import static muramasa.antimatter.Data.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.machine.MachineFlag.*;
import static muramasa.antimatter.machine.Tier.*;
import static muramasa.gregtech.data.GT5RCovers.*;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.*;

public class Machines {
    public static BasicMachine HULL = new BasicMachine(GT5RRef.ID, "hull").setTiers(Tier.getAllElectric()).overlayTexture(Textures.STATE_IGNORANT_TIER_SPECIFIC_OVERLAY_HANDLER).noCovers().addFlags(GUI, ITEM, FLUID).setTile(BlockEntityHull::new).addTooltipInfo((machine, stack, world, tooltip, flag) -> {
        tooltip.remove(tooltip.size() - 1);
        tooltip.remove(tooltip.size() - 1);
        tooltip.add(Utils.translatable("machine.voltage.in").append(": ").append(Utils.literal(machine.getTier().getVoltage() + " (" + machine.getTier().getId().toUpperCase() + ")")).withStyle(ChatFormatting.GREEN));
        tooltip.add(Utils.translatable("machine.voltage.out").append(": ").append(Utils.literal(machine.getTier().getVoltage() + " (" + machine.getTier().getId().toUpperCase() + ")")).withStyle(ChatFormatting.GREEN));
        //tooltip.add(Utils.translatable("generic.amp").append(": ").append(Utils.literal(String.valueOf(4)).withStyle(ChatFormatting.YELLOW)));
        tooltip.add(Utils.translatable("machine.power.capacity").append(": ").append(Utils.literal(String.valueOf(512L + machine.getTier().getVoltage() * 8L))).withStyle(ChatFormatting.BLUE));
    });

    /**
     ** Steam Singleblock Machines
     **/
    public static SteamMachine SOLID_FUEL_BOILER = new SteamMachine(GT5RRef.ID, "solid_fuel_boiler").setTiers(BRONZE, STEEL).setMap(SOLID_FUEL_BOILERS).addFlags(GUI, STEAM, ITEM, FLUID, CELL).baseTexture(Textures.BOILER_HANDLER).setTile(BlockEntityCoalBoiler::new).noCovers();
    public static SteamMachine LAVA_BOILER = new SteamMachine(GT5RRef.ID, "lava_boiler").setTiers(STEEL).addFlags(GUI, STEAM, ITEM, FLUID).setTile(BlockEntityLavaBoiler::new).noCovers();
    public static SteamMachine SOLAR_BOILER = new SteamMachine(GT5RRef.ID, "solar_boiler").setTiers(BRONZE).addFlags(GUI, STEAM, ITEM, FLUID).setTile(BlockEntitySolarBoiler::new).allowFrontIO().noCovers();
    public static SteamMachine STEAM_ALLOY_SMELTER = new SteamMachine(GT5RRef.ID, "steam_alloy_smelter").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_ALLOY_SMELTER).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.FURNACE,  0.6f).covers(COVER_STEAM_VENT);
    public static SteamMachine STEAM_COMPRESSOR = new SteamMachine(GT5RRef.ID, "steam_compressor").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_COMPRESSOR).addFlags(GUI, ITEM, FLUID).covers(COVER_STEAM_VENT);
    public static SteamMachine STEAM_EXTRACTOR = new SteamMachine(GT5RRef.ID, "steam_extractor").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_EXTRACTOR).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.EXTRACTOR,  0.6f).covers(COVER_STEAM_VENT);
    public static SteamMachine STEAM_FORGE_HAMMER = new SteamMachine(GT5RRef.ID, "steam_forge_hammer").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_FORGE_HAMMER).addFlags(GUI, ITEM, FLUID).covers(COVER_STEAM_VENT).setSound(SoundEvents.ANVIL_PLACE, 0.6f);
    public static SteamMachine STEAM_FURNACE = new SteamMachine(GT5RRef.ID, "steam_furnace").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_FURNACE).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.FURNACE,  0.6f).covers(COVER_STEAM_VENT);
    public static SteamMachine STEAM_MACERATOR = new SteamMachine(GT5RRef.ID, "steam_macerator").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_MACERATOR).addFlags(GUI, ITEM, FLUID).covers(COVER_STEAM_VENT).setSound(GT5RSounds.MACERATOR,  0.6f);
    public static SteamMachine STEAM_SIFTER = new SteamMachine(GT5RRef.ID, "steam_sifter").setTiers(BRONZE, STEEL).setMap(RecipeMaps.STEAM_SIFTER).addFlags(GUI, ITEM, FLUID).covers(COVER_STEAM_VENT);
    /**
     ** Hatchless Multiblock Machines (Steam Age)
     **/
    public static BasicMachine CHARCOAL_PIT = new BasicMachine(GT5RRef.ID, "charcoal_pit").setTiers(NONE).baseTexture(new Texture(GT5RRef.ID, "block/machine/base/charcoal_pit")).setTile(BlockEntityCharcoalPit::new).noCovers().setAmbientTicking();
    public static BasicMultiMachine<?> COKE_OVEN = new BasicMultiMachine<>(GT5RRef.ID, "coke_oven").setTiers(NONE).setMap(RecipeMaps.COKE_OVEN).addFlags(GUI, ITEM, FLUID).setTile(BlockEntityCokeOven::new);
    public static BasicMultiMachine<?> PRIMITIVE_BLAST_FURNACE = new BasicMultiMachine<>(GT5RRef.ID, "primitive_blast_furnace").setTiers(NONE).setMap(RecipeMaps.PRIMITIVE_BLAST_FURNACE).addFlags(GUI, ITEM).setTile(BlockEntityPrimitiveBlastFurnace::new);
    /**
     ** Electric Singleblock Machines
     **/
    /**
     * Processors
     **/
    public static BasicMachine ALLOY_SMELTER = new BasicMachine(GT5RRef.ID, "alloy_smelter").setMap(RecipeMaps.ALLOY_SMELTER).addFlags(GUI, ITEM).setSound(GT5RSounds.FURNACE,  0.6f);
    public static BasicMachine AMP_FABRICATOR = new BasicMachine(GT5RRef.ID, "amp_fabricator").setTiers(Tier.getStandardWithIV()).setMap(RecipeMaps.AMP_FABRICATOR).addFlags(GUI, ITEM);
    public static BasicMachine ARC_FURNACE = new BasicMachine(GT5RRef.ID, "arc_furnace").setMap(RecipeMaps.ARC_FURNACE).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.FURNACE,  0.6f).amps(3);
    public static BasicMachine ASSEMBLER = new BasicMachine(GT5RRef.ID, "assembler").setMap(RecipeMaps.ASSEMBLER).setTile(BlockEntityAssembler::new).addFlags(GUI, ITEM, FLUID).custom();
    public static BasicMachine AUTOCLAVE = new BasicMachine(GT5RRef.ID, "autoclave").setMap(RecipeMaps.AUTOCLAVE).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine BENDER = new BasicMachine(GT5RRef.ID, "bender").setMap(RecipeMaps.BENDER).addFlags(GUI, ITEM);
    public static BasicMachine CANNER = new BasicMachine(GT5RRef.ID, "canner").setMap(RecipeMaps.CANNER).addFlags(GUI, ITEM);
    public static BasicMachine CENTRIFUGE = new BasicMachine(GT5RRef.ID, "centrifuge").setMap(RecipeMaps.CENTRIFUGE).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine BATH = new BasicMachine(GT5RRef.ID, "bath").setTiers(NONE).removeFlags(EU).setMap(RecipeMaps.BATH).addFlags(GUI, ITEM, FLUID).setTile(BlockEntityBath::new).baseTexture(new Texture(GT5RRef.ID, "block/machine/base/hv"));
    public static BasicMachine DEHYDRATOR = new BasicMachine(GT5RRef.ID, "dehydrator").setMap(RecipeMaps.DEHYDRATOR).addFlags(GUI, ITEM, FLUID).setTile(BlockEntityIUpgradedBatchMachine::new).addTooltipInfo((machine, stack, world, tooltip, flag) -> {
        tooltip.add(Utils.translatable("machine.upgraded_batch.parallel", 1 << (machine.getTier().getIntegerId() - 1)));
    });
    public static BasicMachine CHEMICAL_REACTOR = new BasicMachine(GT5RRef.ID, "chemical_reactor").setMap(RecipeMaps.CHEMICAL_REACTOR).addFlags(GUI, ITEM, FLUID).renderContainedLiquids(true).custom();
    public static BasicMachine CIRCUIT_ASSEMBLER = new BasicMachine(GT5RRef.ID, "circuit_assembler").setTiers(Tier.getStandardWithIV()).setMap(RecipeMaps.CIRCUIT_ASSEMBLER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine COMPRESSOR = new BasicMachine(GT5RRef.ID, "compressor").setMap(RecipeMaps.COMPRESSOR).addFlags(GUI, ITEM);
    public static BasicMachine CRYSTALLIZATION_CHAMBER = new BasicMachine(GT5RRef.ID, "crystallization_chamber").setMap(RecipeMaps.CRYSTALLIZATION_CHAMBER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine CUTTER = new BasicMachine(GT5RRef.ID, "cutter").setMap(RecipeMaps.CUTTER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine DISASSEMBLER = new BasicMachine(GT5RRef.ID, "disassembler").setMap(RecipeMaps.DISASSEMBLER).addFlags(GUI, ITEM).custom();
    public static BasicMachine DISTILLERY = new BasicMachine(GT5RRef.ID, "distillery").setMap(RecipeMaps.DISTILLERY).addFlags(GUI, ITEM, FLUID).custom().renderContainedLiquids(true).setSound(GT5RSounds.EXTRACTOR,  0.6f);
    public static BasicMachine ELECTRIC_OVEN = new BasicMachine(GT5RRef.ID, "electric_oven").setMap(RecipeMaps.ELECTRIC_OVEN).addFlags(GUI, ITEM).setSound(GT5RSounds.FURNACE, 0.6f);
    public static BasicMachine ELECTROLYZER = new BasicMachine(GT5RRef.ID, "electrolyzer").setMap(RecipeMaps.ELECTROLYZER).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.MAGNETIZER, 0.6f);
    public static BasicMachine ELECTROMAGNETIC_SEPARATOR = new BasicMachine(GT5RRef.ID, "electromagnetic_separator").setMap(RecipeMaps.ELECTROMAGNETIC_SEPARATOR).addFlags(GUI, ITEM);
    public static BasicMachine EXTRACTOR = new BasicMachine(GT5RRef.ID, "extractor").setMap(RecipeMaps.EXTRACTOR).addFlags(GUI, ITEM).setSound(GT5RSounds.EXTRACTOR,  0.6f);
    public static BasicMachine EXTRUDER = new BasicMachine(GT5RRef.ID, "extruder").setMap(RecipeMaps.EXTRUDER).addFlags(GUI, ITEM).custom();
    public static BasicMachine FERMENTER = new BasicMachine(GT5RRef.ID, "fermenter").setMap(RecipeMaps.FERMENTER).addFlags(GUI, ITEM, FLUID).custom().renderContainedLiquids(true);
    public static BasicMachine FLUID_CANNER = new BasicMachine(GT5RRef.ID, "fluid_canner").setMap(RecipeMaps.FLUID_CANNER).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.EXTRACTOR,  0.6f);
    public static BasicMachine FLUID_PRESS = new BasicMachine(GT5RRef.ID, "fluid_press").setMap(RecipeMaps.FLUID_PRESS).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine FLUID_HEATER = new BasicMachine(GT5RRef.ID, "fluid_heater").setMap(RecipeMaps.FLUID_HEATER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine FLUID_SOLIDIFIER = new BasicMachine(GT5RRef.ID, "fluid_solidifier").setMap(FLUID_SOLIDIFYER).addFlags(GUI, ITEM, FLUID).setSound(GT5RSounds.EXTRACTOR,  0.6f);
    public static BasicMachine FORGE_HAMMER = new BasicMachine(GT5RRef.ID, "forge_hammer").setMap(RecipeMaps.FORGE_HAMMER).addFlags(GUI, ITEM).setSound(SoundEvents.ANVIL_PLACE, 0.6f);
    public static BasicMachine FORMING_PRESS = new BasicMachine(GT5RRef.ID, "forming_press").setMap(RecipeMaps.FORMING_PRESS).addFlags(GUI, ITEM);
    public static BasicMachine FURNACE = new BasicMachine(GT5RRef.ID, "furnace").setMap(ELECTRIC_FURNACE).addFlags(GUI, ITEM).setSound(GT5RSounds.FURNACE,  0.6f);
    public static BasicMachine LASER_ENGRAVER = new BasicMachine(GT5RRef.ID, "laser_engraver").setMap(RecipeMaps.LASER_ENGRAVER).addFlags(GUI, ITEM).setSound(GT5RSounds.MAGNETIZER,  0.6f);
    public static BasicMachine LATHE = new BasicMachine(GT5RRef.ID, "lathe").setMap(RecipeMaps.LATHE).addFlags(GUI, ITEM);
    public static BasicMachine MACERATOR = new BasicMachine(GT5RRef.ID, "macerator").setTiers(LV, MV, HV, EV).setMap(PULVERIZER).setTile(BlockEntityMacerator::new).addTooltipInfo("tooltip.macerator.0").setTierSpecificLang().custom().addFlags(GUI, ITEM).setSound(GT5RSounds.MACERATOR,  0.6f);
    public static BasicMachine MASS_FABRICATOR = new BasicMachine(GT5RRef.ID, "mass_fabricator").setTiers(Tier.getStandardWithIV()).setMap(RecipeMaps.MASS_FABRICATOR).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine MIXER = new BasicMachine(GT5RRef.ID, "mixer").setMap(RecipeMaps.MIXER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine ORE_WASHER = new BasicMachine(GT5RRef.ID, "ore_washer").setMap(RecipeMaps.ORE_WASHER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine PACKAGER = new BasicMachine(GT5RRef.ID, "packager").setMap(RecipeMaps.PACKAGER).addFlags(GUI, ITEM);
    public static BasicMachine POLARIZER = new BasicMachine(GT5RRef.ID, "polarizer").setMap(RecipeMaps.POLARIZER).addFlags(GUI, ITEM);
    public static BasicMachine PRINTER = new BasicMachine(GT5RRef.ID, "printer").setTiers(Tier.LV).setMap(PRINTING).addFlags(GUI, ITEM, FLUID).setTile(BlockEntityPrinter::new);
    public static BasicMachine ROASTER = new BasicMachine(GT5RRef.ID, "roaster").setMap(RecipeMaps.ROASTER).addFlags(GUI, ITEM, FLUID).amps(3).setTile(BlockEntityIUpgradedBatchMachine::new).addTooltipInfo((machine, stack, world, tooltip, flag) -> {
        tooltip.add(Utils.translatable("machine.upgraded_batch.parallel", 1 << (machine.getTier().getIntegerId() - 1)));
    });
    public static BasicMachine RECYCLER = new BasicMachine(GT5RRef.ID, "recycler").setMap(RecipeMaps.RECYCLER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine REPLICATOR = new BasicMachine(GT5RRef.ID, "replicator").setTiers(Tier.getStandardWithIV()).setMap(RecipeMaps.REPLICATOR).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine ROCK_BREAKER = new BasicMachine(GT5RRef.ID, "rock_breaker").setMap(RecipeMaps.ROCK_BREAKER).addFlags(GUI, ITEM).setTile(BlockEntityRockBreaker::new);
    public static BasicMachine SCANNER = new BasicMachine(GT5RRef.ID, "scanner").setTiers(Tier.getStandardWithIV()).setMap(RecipeMaps.SCANNER).addFlags(GUI, ITEM, FLUID).setTile(BlockEntityScanner::new).setSound(GT5RSounds.MAGNETIZER,  0.6f);
    public static BasicMachine SEISMIC_PROSPECTOR = new BasicMachine(GT5RRef.ID, "seismic_prospector").setTiers(LV, EV).setTile(BlockEntitySeismicProspector::new).setOutputCover(ICover.emptyFactory);
    public static BasicMachine SIFTER = new BasicMachine(GT5RRef.ID, "sifter").setMap(RecipeMaps.SIFTER).addFlags(GUI, ITEM);
    public static BasicMachine SMELTER = new BasicMachine(GT5RRef.ID, "smelter").setMap(RecipeMaps.SMELTER).addFlags(GUI, ITEM, FLUID);
    public static BasicMachine THERMAL_CENTRIFUGE = new BasicMachine(GT5RRef.ID, "thermal_centrifuge").setMap(RecipeMaps.THERMAL_CENTRIFUGE).addFlags(GUI,ITEM).amps(2);
    public static BasicMachine UNPACKAGER = new BasicMachine(GT5RRef.ID, "unpackager").setMap(RecipeMaps.UNPACKAGER).addFlags(GUI, ITEM);
    public static BasicMachine WIRE_MILL = new BasicMachine(GT5RRef.ID, "wire_mill").setMap(RecipeMaps.WIRE_MILL).addFlags(GUI, ITEM).custom();
    /**
     * Battery Buffers
     **/
    public static BasicMachine BATTERY_BUFFER_SIXTEEN = new BasicMachine(GT5RRef.ID, "16x_battery_buffer").setTiers(LV, MV, HV, EV, IV, LUV, ZPM, UV, UHV).addFlags(GUI, EU, ITEM).overlayTexture(Textures.TIER_SPECIFIC_OVERLAY_HANDLER).noCovers().setTile(BlockEntityBatteryBuffer::new).setVerticalFacingAllowed(true).allowFrontIO();
    public static BasicMachine BATTERY_BUFFER_EIGHT = new BasicMachine(GT5RRef.ID, "8x_battery_buffer").setTiers(LV, MV, HV, EV, IV, LUV, ZPM, UV, UHV).addFlags(GUI, EU, ITEM).overlayTexture(Textures.TIER_SPECIFIC_OVERLAY_HANDLER).noCovers().setTile(BlockEntityBatteryBuffer::new).setVerticalFacingAllowed(true).allowFrontIO();
    public static BasicMachine BATTERY_BUFFER_FOUR = new BasicMachine(GT5RRef.ID, "4x_battery_buffer").setTiers(LV, MV, HV, EV, IV, LUV, ZPM, UV, UHV).addFlags(GUI, EU, ITEM).overlayTexture(Textures.TIER_SPECIFIC_OVERLAY_HANDLER).noCovers().setTile(BlockEntityBatteryBuffer::new).setVerticalFacingAllowed(true).allowFrontIO();
    public static BasicMachine BATTERY_BUFFER_ONE = new BasicMachine(GT5RRef.ID, "1x_battery_buffer").setTiers(LV, MV, HV, EV, IV, LUV, ZPM, UV, UHV).addFlags(GUI, EU, ITEM).overlayTexture(Textures.TIER_SPECIFIC_OVERLAY_HANDLER).noCovers().setTile(BlockEntityBatteryBuffer::new).setVerticalFacingAllowed(true).allowFrontIO();
    /**
     * Filters
     **/
    public static BasicMachine ELECTRIC_ITEM_FILTER = new BasicMachine(GT5RRef.ID, "electric_item_filter").setTiers(Tier.getStandardWithIV()).addFlags(GUI, EU, ITEM).setTile(BlockEntityItemFilter::new).noCovers().frontCovers().allowFrontIO().setVerticalFacingAllowed(true).overlayTexture(Textures.LEFT_RIGHT_HANDLER);
    public static BasicMachine ELECTRIC_TYPE_FILTER = new BasicMachine(GT5RRef.ID, "electric_type_filter").setTiers(Tier.getStandardWithIV()).addFlags(GUI, EU, ITEM).setTile(BlockEntityTypeFilter::new).noCovers().frontCovers().allowFrontIO().setVerticalFacingAllowed(true).overlayTexture(Textures.LEFT_RIGHT_HANDLER);
    public static BasicMachine SUPER_BUFFER =new BasicMachine(GT5RRef.ID, "super_buffer").setTiers(Tier.getStandardWithIV()).addFlags(GUI, EU, ITEM).setTile(BlockEntityBuffer::new).setVerticalFacingAllowed(true).allowFrontIO().noCovers().frontCovers().overlayTexture(Textures.LEFT_RIGHT_HANDLER);
    public static BasicMachine CHEST_BUFFER =new BasicMachine(GT5RRef.ID, "chest_buffer").setTiers(Tier.getStandardWithIV()).addFlags(GUI, EU, ITEM).setTile(BlockEntityBuffer::new).setVerticalFacingAllowed(true).allowFrontIO().noCovers().frontCovers().overlayTexture(Textures.LEFT_RIGHT_HANDLER);
    /**
     * Drums
     */
    public static DrumMachine BRONZE_DRUM = GTCoreBlocks.createDrum(Materials.Bronze, 16000);
    public static DrumMachine STEEL_DRUM = GTCoreBlocks.createDrum(Steel, 48000);
    public static DrumMachine INVAR_DRUM = GTCoreBlocks.createDrum(Materials.Invar, 32000);
    public static DrumMachine STAINLESS_DRUM = GTCoreBlocks.createDrum(Materials.StainlessSteel, 64000).acidProof();
    public static DrumMachine TITANIUM_DRUM = GTCoreBlocks.createDrum(Materials.Titanium, 128000);
    public static DrumMachine NETHERRITE_DRUM = GTCoreBlocks.createDrum(AntimatterMaterials.Netherite, 128000).acidProof();
    public static DrumMachine TUNGSTENSTEEL_DRUM = GTCoreBlocks.createDrum(Materials.TungstenSteel, 256000);
    public static DrumMachine TUNGSTEN_DRUM = GTCoreBlocks.createDrum(Materials.Tungsten, 256000);

    public static MultiblockTankMachine WOOD_TANK = new MultiblockTankMachine(GT5RRef.ID, Wood, true, 432000);
    public static MultiblockTankMachine[] STEEL_TANKS = createTankMachine(Steel, 3);
    public static MultiblockTankMachine[] INVAR_TANKS = createTankMachine(Invar, 2);
    public static MultiblockTankMachine[] STAINLESS_STEEL_TANKS = createTankMachine(StainlessSteel, 4);
    public static MultiblockTankMachine[] TITANIUM_TANKS = createTankMachine(Titanium, 8);
    public static MultiblockTankMachine[] NETHERITE_TANKS = createTankMachine(Netherite, 8);
    public static MultiblockTankMachine[] TUNGSTENSTEEL_TANKS = createTankMachine(TungstenSteel, 16);
    public static MultiblockTankMachine[] TUNGSTEN_TANKS = createTankMachine(Tungsten, 16);

    /**
     * Transformers
     **/
    public static BasicMachine TRANSFORMER = new BasicMachine(GT5RRef.ID, "transformer").addFlags(EU).setTiers(ULV, LV, MV, HV, EV, IV, LUV, ZPM, UV).overlayTexture(Textures.TIER_SPECIFIC_OVERLAY_HANDLER).setTile((v, pos, state) -> new BlockEntityTransformer<>(v, pos, state, 1)).noCovers().allowFrontIO().setVerticalFacingAllowed(true).addTooltipInfo((machine, stack, world, tooltip, flag) -> {
        tooltip.remove(tooltip.size() - 1);
        tooltip.remove(tooltip.size() - 1);
        Tier upper = Tier.getTier(machine.getTier().getVoltage() * 4);
        tooltip.add(Utils.translatable("machine.transformer.voltage_info", Utils.literal(upper.getId().toUpperCase()), Utils.literal(machine.getTier().getId().toUpperCase())));
        tooltip.add(Utils.translatable("machine.voltage.in").append(": ").append(Utils.literal(upper.getVoltage() + " (" + upper.getId().toUpperCase() + ")")).withStyle(ChatFormatting.GREEN));
        tooltip.add(Utils.translatable("machine.voltage.out").append(": ").append(Utils.literal(machine.getTier().getVoltage() + " (" + machine.getTier().getId().toUpperCase() + ")")).withStyle(ChatFormatting.GREEN));
        tooltip.add(Utils.translatable("generic.amp").append(": ").append(Utils.literal(String.valueOf(4)).withStyle(ChatFormatting.YELLOW)));
        tooltip.add(Utils.translatable("machine.power.capacity").append(": ").append(Utils.literal(String.valueOf(512L + machine.getTier().getVoltage() * 8L))).withStyle(ChatFormatting.BLUE));
    });
    public static BasicMachine ADJUSTABLE_TRANSFORMER = new BasicMachine(GT5RRef.ID, "adjustable_transformer").setTiers(EV, IV).addFlags(GUI, EU).setTile(BlockEntityDigitalTransformer::new).noCovers().allowFrontIO();
    /**
     ** Generators
     **/
    public static GeneratorMachine COMBUSTION_GENERATOR = new GeneratorMachine(GT5RRef.ID, "combustion_generator").setTiers(LV, MV, HV).setMap(COMBUSTION_FUELS).addFlags(GUI, ITEM, FLUID, CELL).allowFrontIO();
    public static GeneratorMachine SEMIFLUID_GENERATOR = new GeneratorMachine(GT5RRef.ID, "semifluid_generator").setTiers(LV, MV, HV).setMap(SEMI_FUELS).addFlags(GUI, ITEM, FLUID, CELL).allowFrontIO();
    public static GeneratorMachine GAS_GENERATOR = new GeneratorMachine(GT5RRef.ID, "gas_turbine").setTiers(LV, MV, HV).setMap(GAS_FUELS).addFlags(GUI, ITEM, FLUID, CELL).allowFrontIO().custom();
    public static GeneratorMachine NAQUADAH_GENERATOR = new GeneratorMachine(GT5RRef.ID, "naquadah_reactor").setTiers(EV, IV, LUV).setMap(NAQUADAH_FUELS).addFlags(GUI, ITEM, FLUID, CELL).allowFrontIO();
    public static GeneratorMachine STEAM_GENERATOR = new GeneratorMachine(GT5RRef.ID, "steam_turbine").setTiers(LV, MV, HV).setMap(STEAM_FUELS).addFlags(GUI, ITEM, FLUID, CELL).setTile(BlockEntitySteamTurbine::new).efficiency(t -> {
        return (4 - t.getIntegerId()) + 6;
    }).allowFrontIO().custom();
    public static GeneratorMachine SOLAR_PANEL = new GeneratorMachine(GT5RRef.ID, "solar_panel").setTiers(NONE, ULV, LV).addFlags(GUI).removeFlags(COVERABLE).customShape(Shapes.box(0,0,0, 1, 0.5, 1)).itemModelParent(new ResourceLocation(GT5RRef.ID, "block/preset/solar_panel")).setVerticalFacingAllowed(false).setTile(BlockEntitySolarPanel::new).custom().addTooltipInfo((machine, stack, world, tooltip, flag) -> {
        if (machine.getTier() == NONE){
            tooltip.add(Utils.translatable("machine.voltage.out").append(": ").append(Utils.literal(1 + "")).withStyle(ChatFormatting.GREEN));
            tooltip.add(Utils.translatable("machine.power.capacity").append(": ").append(Utils.literal("" + 80).withStyle(ChatFormatting.BLUE)));
        }
    });
    public static BasicMachine NUCLEAR_REACTOR_CORE = new SecondaryOutputMachine(GT5RRef.ID, "nuclear_reactor_core").setSecondaryOutputCover(COVER_REACTOR_OUTPUT_SECONDARY).removeFlags(EU).setTiers(NONE).addFlags(GUI, ITEM, FLUID, UNCULLED).renderContainedLiquids(false).custom().overlayTexture(Textures.REACTOR_CORE_OVERLAY_HANDLER).baseTexture(Textures.REACTOR_CORE_BASE_HANDLER).modelLoader(GT5RModelManager.LOADER_REACTOR).setTile(BlockEntityNuclearReactorCore::new).blockColorHandler(Machines::getBlockColorNuclear).itemColorHandler((stack, block, i) -> i == 0 ? Lead.getRGB() : -1).frontCovers().allowFrontIO().setNoTextureRotation(true).setOutputCover(GT5RCovers.COVER_REACTOR_OUTPUT).covers(ICover.emptyFactory, ICover.emptyFactory, GT5RCovers.COVER_REACTOR_OUTPUT, GT5RCovers.COVER_REACTOR_OUTPUT_SECONDARY, ICover.emptyFactory, ICover.emptyFactory);
    public static BasicMachine SMALL_HEAT_EXCHANGER = new SecondaryOutputMachine(GT5RRef.ID, "small_heat_exchanger").setSecondaryOutputCover(COVER_OUTPUT_SECONDARY).removeFlags(EU).covers(ICover.emptyFactory, ICover.emptyFactory, ICover.emptyFactory, COVEROUTPUT, COVER_OUTPUT_SECONDARY, ICover.emptyFactory).setTiers(NONE).baseTexture(new Texture(GT5RRef.ID, "block/machine/base/small_heat_exchanger")).setMap(HEAT_EXCHANGER).addFlags(GUI, ITEM, FLUID).setTile(BlockEntitySmallHeatExchanger::new).frontCovers().allowFrontIO();

    /**
     ** Multiblock Hatch Machines (Electrical Age)
     **/
    public static MultiMachine ADVANCED_MINER = new MultiMachine(GT5RRef.ID, "advanced_miner").setTiers(LV).addFlags(GUI, ITEM, EU).setTile(BlockEntityAdvancedMiner::new).setTextureBlock(GT5RBlocks.CASING_SOLID_STEEL);
    public static MultiMachine BLAST_FURNACE = new MultiMachine(GT5RRef.ID, "electric_blast_furnace").setTiers(LV).setMap(E_BLAST_FURNACE).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(11).setTile(BlockEntityElectricBlastFurnace::new).custom().setTextureBlock(GT5RBlocks.CASING_HEAT_PROOF);
    public static MultiMachine COMBUSTION_ENGINE = new MultiMachine(GT5RRef.ID, "combustion_engine").setTiers(EV).setMap(COMBUSTION_FUELS).addFlags(GUI, FLUID, EU, GENERATOR).addStructureTooltip(13).setTile(BlockEntityCombustionEngine::new).custom().setTextureBlock(GT5RBlocks.CASING_TITANIUM);
    public static MultiMachine CRACKING_UNIT = new MultiMachine(GT5RRef.ID, "cracking_unit").setTiers(HV).setMap(CRACKING).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(9).setTile(BlockEntityOilCrackingUnit::new).custom().setTextureBlock(GT5RBlocks.CASING_STAINLESS_STEEL);
    public static MultiMachine DISTLLATION_TOWER = new MultiMachine(GT5RRef.ID, "distillation_tower").setTiers(HV).setMap(DISTILLATION).addStructureTooltip(8).addFlags(GUI, ITEM, FLUID, EU).setTile(BlockEntityDistillationTower::new).custom().setTextureBlock(GT5RBlocks.CASING_STAINLESS_STEEL);
    public static MultiMachine CRYO_DISTLLATION_TOWER = new MultiMachine(GT5RRef.ID, "cryo_distillation_tower").setTiers(HV).setMap(CRYO_DISTILLATION).addStructureTooltip(8).addFlags(GUI, ITEM, FLUID, EU).setTile(BlockEntityDistillationTower::new).custom().setTextureBlock(GT5RBlocks.CASING_FROST_PROOF);
    public static MultiMachine FUSION_REACTOR = new MultiMachine(GT5RRef.ID, "fusion_control_computer").setTiers(LUV).setMap(FUSION).addFlags(GUI, FLUID, ITEM, EU).addStructureTooltip(7).setTile(BlockEntityFusionReactor::new).setTextureBlock(GT5RBlocks.CASING_FUSION);
    public static MultiMachine LARGE_HEAT_EXCHANGER = new MultiMachine(GT5RRef.ID, "large_heat_exchanger").setTiers(NONE).setMap(RecipeMaps.HEAT_EXCHANGER).addFlags(GUI, FLUID, ITEM, EU).addStructureTooltip(7).setTile(BlockEntityLargeHeatExchanger::new).custom().setTextureBlock(GT5RBlocks.CASING_TITANIUM);
    public static MultiMachine IMPLOSION_COMPRESSOR = new MultiMachine(GT5RRef.ID, "implosion_compressor").setTiers(HV).setMap(RecipeMaps.IMPLOSION_COMPRESSOR).addFlags(GUI, ITEM, EU).addStructureTooltip(7).setTile(BlockEntityImplosionCompressor::new).setTextureBlock(GT5RBlocks.CASING_SOLID_STEEL);
    public static MultiMachine LARGE_AUTOCLAVE = new MultiMachine(GT5RRef.ID, "large_autoclave").setTiers(HV).setMap(RecipeMaps.AUTOCLAVE).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(9).setTile(BlockEntityLargeAutoclave::new).setTextureBlock(GT5RBlocks.CASING_STAINLESS_STEEL);
    public static MultiMachine LARGE_BATHING_VAT = new MultiMachine(GT5RRef.ID, "large_bathing_vat").setTiers(NONE).setMap(RecipeMaps.BATH).addFlags(GUI, ITEM, FLUID).addStructureTooltip(8).setTile(BlockEntityLargeBath::new).setTextureBlock(GT5RBlocks.STAINLESS_STEEL_WALL).blockColorHandler((state, world, pos, machine, i) -> i == 0 ? StainlessSteel.getRGB() : -1).itemColorHandler((stack, block, i) -> i == 0 ? StainlessSteel.getRGB() : -1);
    public static MultiMachine LARGE_BOILER = new MultiMachine(GT5RRef.ID, "large_boiler").setTiers(LV, MV, HV, EV).addFlags(GUI, ITEM, FLUID).setMap(LARGE_BOILERS).setTile(BlockEntityLargeBoiler::new).custom().setTierSpecificLang().addStructureTooltip(13, (machine, stack, world, flag, i) -> {
        if (i == 1){
            double total = machine.getTier() == LV ? 32000 : machine.getTier() == MV ? 36000 : machine.getTier() == HV ? 41600 : 48000;
            double production = machine.getTier() == LV ? 16000 : machine.getTier() == MV ? 24000 : machine.getTier() == HV ? 32000 : 40000;
            return new Object[]{total, production};
        }
        if (i >= 4 && i < 7){
            String tier = machine.getTier() == LV ? "bronze" : machine.getTier() == MV ? "steel" : machine.getTier() == HV ? "titanium" : "tungstensteel";
            String prefix = i == 5 && tier.equals("steel") ? "solid_" : "";
            String suffix = i == 5 && tier.equals("bronze") ? "plated_brick_" : i == 4 ? "firebox_" : i == 6 ? "pipe_" : "";
            return new Object[]{Utils.translatable(GT5Reimagined.get(BlockCasing.class, prefix + tier + "_" + suffix + "casing").getDescriptionId())};
        }
        if (i == 12){
            double seconds = machine.getTier() == LV ? 31.25 : machine.getTier() == MV ? 47.67 : machine.getTier() == HV ? 62.50 : 125;
            return new Object[]{seconds};
        }
        return new Object[0];
    });
    public static MultiMachine LARGE_CENTRIFUGE = new MultiMachine(GT5RRef.ID, "large_centrifuge").setTiers(HV).setMap(RecipeMaps.CENTRIFUGE).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(7).setTile(BlockEntityLargeCentrifuge::new).setTextureBlock(GT5RBlocks.CASING_TUNGSTENSTEEL);
    public static MultiMachine LARGE_CHEMICAL_REACTOR = new MultiMachine(GT5RRef.ID, "large_chemical_reactor").setTiers(HV).setMap(RecipeMaps.CHEMICAL_REACTOR).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(10).setTile(BlockEntityLargeChemicalReactor::new).custom().setTextureBlock(GT5RBlocks.CASING_CHEMICALLY_INERT);
    public static MultiMachine LARGE_ELECTROLYZER = new MultiMachine(GT5RRef.ID, "large_electrolyzer").setTiers(HV).setMap(RecipeMaps.ELECTROLYZER).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(9).setTile(BlockEntityLargeElectrolyzer::new).setTextureBlock(GT5RBlocks.CASING_STAINLESS_STEEL);
    public static MultiMachine LARGE_PULVERIZER = new MultiMachine(GT5RRef.ID, "large_pulverizer").setTiers(HV).setMap(PULVERIZER).addFlags(GUI, ITEM, EU).addStructureTooltip(8).setTile(BlockEntityLargeMacerator::new).setTextureBlock(GT5RBlocks.TUNGSTENSTEEL_WALL).blockColorHandler((state, world, pos, machine, i) -> i == 0 ? TungstenSteel.getRGB() : -1).itemColorHandler((stack, block, i) -> i == 0 ? TungstenSteel.getRGB() : -1);
    public static MultiMachine LARGE_ORE_WASHER = new MultiMachine(GT5RRef.ID, "large_ore_washer").setTiers(EV).setMap(RecipeMaps.ORE_WASHER).addFlags(GUI, ITEM, FLUID, EU).setTile(BlockEntityLargeOreWasher::new).setTextureBlock(GT5RBlocks.TITANIUM_WALL).blockColorHandler((state, world, pos, machine, i) -> i == 0 ? Titanium.getRGB() : -1).itemColorHandler((stack, block, i) -> i == 0 ? Titanium.getRGB() : -1);
    public static MultiMachine LARGE_TURBINE = new MultiMachine(GT5RRef.ID, "large_turbine").setTiers(HV, EV, IV).setMap(STEAM_FUELS, HV).setMap(HP_STEAM_FUELS, IV).setMap(GAS_FUELS, EV).addFlags(GUI, ITEM, FLUID, EU, GENERATOR).setTile(BlockEntityLargeTurbine::new).custom(Textures.TURBINE).setTierSpecificLang().addStructureTooltip(8, (machine, stack, world, flag, i) -> {
        if (i == 0){
            return new Object[]{machine.getDisplayName(new ItemStack(machine))};
        }
        if (i == 5){
            String tier = machine.getTier() == HV ? "steel" : machine.getTier() == EV ? "stainless_steel" : "titanium";
            return new Object[]{Utils.translatable(GT5Reimagined.get(BlockCasing.class, tier + "_turbine_casing").getDescriptionId())};
        }
        if (i == 7){
            String amount = machine.getTier() == HV ? "105-1680" : "210-3360";
            return new Object[]{amount};
        }
        return new Object[0];
    });
    public static MultiMachine MULTI_SMELTER = new MultiMachine(GT5RRef.ID, "multi_smelter").setTiers(HV).setMap(ELECTRIC_FURNACE).addFlags(GUI, ITEM, EU).addStructureTooltip(9).setTile(BlockEntityMultiSmelter::new).custom().setTextureBlock(GT5RBlocks.CASING_HEAT_PROOF);
    public static MultiMachine OIL_DRILLING_RIG = new MultiMachine(GT5RRef.ID, "oil_drilling_rig").setTiers(MV).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(9).setTile(BlockEntityOilDrillingRig::new).custom().setTextureBlock(GT5RBlocks.CASING_SOLID_STEEL);
    public static MultiMachine PROCESSING_ARRAY = new MultiMachine(GT5RRef.ID, "processing_array").setTiers(EV).addFlags(GUI, ITEM, FLUID, EU, RECIPE).addStructureTooltip(8).setTile(BlockEntityProcessingArray::new).custom().setTextureBlock(GT5RBlocks.CASING_TUNGSTENSTEEL);
    public static MultiMachine PYROLYSIS_OVEN = new MultiMachine(GT5RRef.ID, "pyrolysis_oven").setTiers(MV).setMap(RecipeMaps.PYROLYSIS_OVEN).addFlags(GUI, ITEM, FLUID, EU).setTile(BlockEntityPyrolysisOven::new).custom().setTextureBlock(GT5RBlocks.CASING_ULV);
    public static MultiMachine TREE_GROWTH_SIMULATOR = new MultiMachine(GT5RRef.ID, "tree_growth_simulator").setTiers(LV).setMap(RecipeMaps.TREE_GROWTH_SIMULATOR).addFlags(GUI, ITEM, FLUID, EU).setTile(BlockEntityTreeGrowthSimulator::new).setTextureBlock(GT5RBlocks.CASING_PLASTIC);
    public static MultiMachine VACUUM_FREEZER = new MultiMachine(GT5RRef.ID, "vacuum_freezer").setTiers(HV).setMap(RecipeMaps.VACUUM_FREEZER).addFlags(GUI, ITEM, FLUID, EU).addStructureTooltip(7).setTile(BlockEntityVacuumFreezer::new).setTextureBlock(GT5RBlocks.CASING_FROST_PROOF);
    /**
     * Long distance pipelines
     */
    public static BasicMultiMachine<?> LONG_DISTANCE_FLUID_ENDPOINT = new BasicMultiMachine<>(GT5RRef.ID,"long_distance_fluid_endpoint").allowFrontIO().setVerticalFacingAllowed(true).setTiers(NONE).addFlags(FLUID).setTile(BlockEntityLongDistancePipeEndpoint::new);
    public static BasicMultiMachine<?> LONG_DISTANCE_ITEM_ENDPOINT = new BasicMultiMachine<>(GT5RRef.ID,"long_distance_item_endpoint").allowFrontIO().setVerticalFacingAllowed(true).setTiers(NONE).addFlags(ITEM).setTile(BlockEntityLongDistancePipeEndpoint::new);
    public static BasicMultiMachine<?> LONG_DISTANCE_TRANSFORMER_ENDPOINT = new BasicMultiMachine<>(GT5RRef.ID,"long_distance_transformer_endpoint").allowFrontIO().setVerticalFacingAllowed(true).setTiers(EV, IV, LUV, ZPM, UV).addFlags(EU).setTile(BlockEntityLongDistancePipeEndpoint::new).overlayTexture(Textures.STATE_IGNORANT_TIER_SPECIFIC_OVERLAY_HANDLER).baseTexture((m, tier, state) -> new Texture[]{tier.getBaseTexture(m.getDomain())});

    /**
     ** Hatches
     **/
    public static HatchMachine HATCH_DYNAMO = new HatchMachine(GT5RRef.ID, "dynamo_hatch", COVER_DYNAMO_COLORED).addFlags(EU).overlayTexture(Textures.HATCH_OVERLAY_HANDLER);
    public static HatchMachine HATCH_ENERGY = new HatchMachine(GT5RRef.ID, "energy_hatch", COVER_ENERGY_COLORED).addFlags(EU).overlayTexture(Textures.HATCH_OVERLAY_HANDLER);
    public static HatchMachine HATCH_FLUID_I = new HatchMachine(GT5RRef.ID, "fluid_input_hatch", COVERINPUT).addFlags(GUI, FLUID, CELL);
    public static HatchMachine HATCH_FLUID_O = new HatchMachine(GT5RRef.ID, "fluid_output_hatch", COVEROUTPUT).addFlags(GUI, FLUID, CELL);
    //public static final HeatHatch HATCH_HEAT_COPPER = new HeatHatch(GT5RRef.ID, "copper_heat", Copper, 386);
    public static HatchMachine HATCH_ITEM_I = new HatchMachine(GT5RRef.ID, "item_input_hatch", COVERINPUT).addFlags(GUI, ITEM);
    public static HatchMachine HATCH_MUFFLER = new HatchMachine(GT5RRef.ID, "muffler_hatch", COVERMUFFLER).addFlags(GUI, ITEM).setClientTicking();
    public static HatchMachine HATCH_ITEM_O = new HatchMachine(GT5RRef.ID, "item_output_hatch", COVEROUTPUT).addFlags(GUI, ITEM);
    /**
     ** Tanks
     **/
    public static TankMachine QUANTUM_TANK = new TankMachine(GT5RRef.ID, "quantum_tank", t -> (int) (1602000 * Math.pow(6,  (t.getIntegerId() - 1)))).setTiers(Tier.getStandardWithIV()).addFlags(BASIC, GUI, CELL).frontCovers();

    public static BasicMachine PUMP = new BasicMachine(GT5RRef.ID, "electric_pump").addFlags(FLUID).setVerticalFacingAllowed(true).setTile(BlockEntityPump::new).noCovers();
    public static BasicMachine CROP_HARVESTER = new BasicMachine(GT5RRef.ID, "crop_harvester").setTiers(LV).addFlags(GUI, ITEM).setTile(BlockEntityCropHarvester::new);
    public static BasicMachine MINIATURE_NETHER_PORTAL = new MiniPortalMachine(GT5RRef.ID, "miniature_nether_portal").baseTexture(new Texture("block/obsidian")).overlayTexture(Textures.MINI_NETHER_PORTAL).setBlock((machine, tier) -> new BlockMachine(machine, tier, BlockBehaviour.Properties.of(WRENCH_MATERIAL).strength(1.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion())).setTile(BlockEntityMiniNetherPortal::new);
    public static BasicMachine MINIATURE_END_PORTAL = new MiniPortalMachine(GT5RRef.ID, "miniature_end_portal").baseTexture(new Texture("block/end_portal_frame_top")).overlayTexture(Textures.MINI_END_PORTAL).setBlock((machine, tier) -> new BlockMachine(machine, tier, BlockBehaviour.Properties.of(WRENCH_MATERIAL).strength(3.0f, 9.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion())).setTile(BlockEntityMiniEndPortal::new);
    public static BasicMachine MINIATURE_TWILIGHT_PORTAL = new MiniPortalMachine(GT5RRef.ID, "miniature_twilight_portal").baseTexture(new Texture("block/grass_block_top")).overlayTexture(Textures.MINI_TWILIGHT_PORTAL).setBlock((machine, tier) -> new BlockMachine(machine, tier, BlockBehaviour.Properties.of(WRENCH_MATERIAL).strength(1.0f, 10.0f).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion())).setTile(BlockEntityMiniTwilightPortal::new).blockColorHandler((state, world, pos, machine, i) -> {
        if (machine != null && i == 1){
            if (machine.getMachineState() != MachineState.ACTIVE){
                Biome biome = machine.getLevel().getBiome(pos).value();
                return biome.getWaterColor();//0x3f76e4;
            }
        }
        return i == 0 ? 0x00FF00 : -1;
    }).itemColorHandler((stack, block, i) -> i == 0 ? 0x00FF00 : -1);

    /**
     ** Creative Machines
     **/
    public static TankMachine INFINITE_STEAM = new TankMachine(GT5RRef.ID, "infinite_steam").addFlags(FLUID, CELL, GUI).setTile(BlockEntityInfiniteFluid::new).setTiers(LV);

    private static MultiblockTankMachine[] createTankMachine(Material material, int multiplier){
        MultiblockTankMachine[] multiblockTankMachines = {
                new MultiblockTankMachine(GT5RRef.ID, material, true, 432 * multiplier * 1000),
                new MultiblockTankMachine(GT5RRef.ID, material, false, 2000 * multiplier * 1000)
        };
        if (material == StainlessSteel || material == Netherite){
            multiblockTankMachines[0].acidProof();
            multiblockTankMachines[1].acidProof();
        }
        return multiblockTankMachines;
    }

    public static void init() {
    }

    private static int getBlockColorNuclear(BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable BlockEntityMachine<?> machine, int i) {
        if (i > 0 && i < 9 && world != null && pos != null && machine != null){
            int slot = i > 4 ? i - 5 : i - 1;
            if (machine instanceof BlockEntityNuclearReactorCore core){
                if (i < 5){
                    boolean on = core.getMachineState() == MachineState.ACTIVE && (core.mode & Ref.B[slot]) == 0;
                    return on ? -1 : Lead.getRGB();
                } else {
                    ItemStack rod = core.getRod(slot);
                    if (!rod.isEmpty() && rod.getItem() instanceof IItemReactorRod reactorRod){
                        return reactorRod.getItemColor(rod, state.getBlock(), 0);
                    }
                }
            }
        }
        if (i == 0) return Lead.getRGB();
        return -1;
    }
}
