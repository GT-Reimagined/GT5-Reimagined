package org.gtreimagined.gt5r;

import com.terraformersmc.terraform.utils.TerraformFlammableBlockRegistry;
import com.terraformersmc.terraform.utils.TerraformFuelRegistry;
import guideme.Guide;
import guideme.GuidesCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.world.BiomeModifier.Phase;
import net.minecraftforge.fml.DistExecutor;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityScanner;
import org.gtreimagined.gt5r.data.GT5RFluids;
import org.gtreimagined.gt5r.data.StructureInfo;
import org.gtreimagined.gt5r.integration.CCTweakedRegistrar;
import org.gtreimagined.gt5r.integration.ModRecipeRemovals;
import org.gtreimagined.gt5r.integration.botania.BotaniaRegistrar;
import org.gtreimagined.gt5r.integration.forestry.ForestryRegistrar;
import org.gtreimagined.gt5r.integration.ie.IERegistrar;
import org.gtreimagined.gt5r.integration.mekanism.MekanismRegistrar;
import org.gtreimagined.gt5r.integration.railcraft.RailcraftRegistrar;
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar;
import org.gtreimagined.gt5r.items.ItemWoodenBucket;
import org.gtreimagined.gt5r.loader.machines.MortarLoader;
import org.gtreimagined.gt5r.loader.machines.RecyclingLoader;
import org.gtreimagined.gt5r.machine.caps.BronzeCauldronWrapper;
import org.gtreimagined.gtcore.BookRegistration;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTLibConfig;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.common.event.PlayerTickCallback;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.datagen.providers.GTAdvancementProvider;
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider;
import org.gtreimagined.gtlib.datagen.providers.GTBlockTagProvider;
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider;
import org.gtreimagined.gtlib.event.GTCraftingEvent;
import org.gtreimagined.gtlib.event.GTLoaderEvent;
import org.gtreimagined.gtlib.event.GTProvidersEvent;
import org.gtreimagined.gtlib.event.GTWorldGenEvent;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.mixin.LivingEntityAccessor;
import org.gtreimagined.gtlib.recipe.loader.IRecipeRegistrate;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.tool.IGTTool;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.worldgen.IGTWorldgenFunction;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gtreimagined.gt5r.block.BlockAsphalt;
import org.gtreimagined.gt5r.client.GT5RModelManager;
import org.gtreimagined.gt5r.data.ClientData;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.GT5RData;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTypes;
import org.gtreimagined.gt5r.data.GT5RRecipeTypes;
import org.gtreimagined.gt5r.data.GT5RSounds;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gt5r.data.Guis;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.data.MenuHandlers;
import org.gtreimagined.gt5r.data.Models;
import org.gtreimagined.gt5r.data.Structures;
import org.gtreimagined.gt5r.data.TierMaps;
import org.gtreimagined.gt5r.data.ToolTypes;
import org.gtreimagined.gt5r.datagen.GT5RBlockLootProvider;
import org.gtreimagined.gt5r.datagen.GT5RBlockTagProvider;
import org.gtreimagined.gt5r.datagen.GT5RFluidTagProvider;
import org.gtreimagined.gt5r.datagen.GT5RItemTagProvider;
import org.gtreimagined.gt5r.datagen.GT5RLocalizations;
import org.gtreimagined.gt5r.datagen.GT5RTwilightStalctites;
import org.gtreimagined.gt5r.datagen.ProgressionAdvancements;
import org.gtreimagined.gt5r.events.forge.ForgeEvents;
import org.gtreimagined.gt5r.integration.ae2.AppliedEnergisticsRegistrar;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gt5r.integration.thermal.ThermalRegistrar;
import org.gtreimagined.gt5r.integration.rei.REIRegistrar;
import org.gtreimagined.gt5r.loader.LootLoader;
import org.gtreimagined.gt5r.loader.WorldGenLoader;
import org.gtreimagined.gt5r.loader.crafting.BlockParts;
import org.gtreimagined.gt5r.loader.crafting.ElectricToolRecipes;
import org.gtreimagined.gt5r.loader.crafting.MachineRecipes;
import org.gtreimagined.gt5r.loader.crafting.MaterialCrafting;
import org.gtreimagined.gt5r.loader.crafting.Miscellaneous;
import org.gtreimagined.gt5r.loader.crafting.Parts;
import org.gtreimagined.gt5r.loader.crafting.Smelting;
import org.gtreimagined.gt5r.loader.crafting.SteamMachines;
import org.gtreimagined.gt5r.loader.crafting.VanillaExtensions;
import org.gtreimagined.gt5r.loader.crafting.WireCablesPlates;
import org.gtreimagined.gt5r.loader.crafting.WoodCrafting;
import org.gtreimagined.gt5r.loader.items.Circuitry;
import org.gtreimagined.gt5r.loader.machines.AlloySmelterLoader;
import org.gtreimagined.gt5r.loader.machines.ArcFurnaceLoader;
import org.gtreimagined.gt5r.loader.machines.AssemblerLoader;
import org.gtreimagined.gt5r.loader.machines.AutoclaveLoader;
import org.gtreimagined.gt5r.loader.machines.BathLoader;
import org.gtreimagined.gt5r.loader.machines.BenderLoader;
import org.gtreimagined.gt5r.loader.machines.CannerLoader;
import org.gtreimagined.gt5r.loader.machines.CentrifugeLoader;
import org.gtreimagined.gt5r.loader.machines.ChemicalReactorLoader;
import org.gtreimagined.gt5r.loader.machines.CompressorLoader;
import org.gtreimagined.gt5r.loader.machines.CrystallizationChamberLoader;
import org.gtreimagined.gt5r.loader.machines.CutterLoader;
import org.gtreimagined.gt5r.loader.machines.DehydratorLoader;
import org.gtreimagined.gt5r.loader.machines.DistilleryLoader;
import org.gtreimagined.gt5r.loader.machines.ElectrolyzerLoader;
import org.gtreimagined.gt5r.loader.machines.ElectromagneticSeparatorLoader;
import org.gtreimagined.gt5r.loader.machines.ExtractorLoader;
import org.gtreimagined.gt5r.loader.machines.ExtruderLoader;
import org.gtreimagined.gt5r.loader.machines.FermenterLoader;
import org.gtreimagined.gt5r.loader.machines.FluidCannerLoader;
import org.gtreimagined.gt5r.loader.machines.FluidHeaterLoader;
import org.gtreimagined.gt5r.loader.machines.FluidPressLoader;
import org.gtreimagined.gt5r.loader.machines.FluidSolidifierLoader;
import org.gtreimagined.gt5r.loader.machines.ForgeHammerLoader;
import org.gtreimagined.gt5r.loader.machines.FormingPressLoader;
import org.gtreimagined.gt5r.loader.machines.LaserEngraverLoader;
import org.gtreimagined.gt5r.loader.machines.LatheLoader;
import org.gtreimagined.gt5r.loader.machines.MaceratorLoader;
import org.gtreimagined.gt5r.loader.machines.MixerLoader;
import org.gtreimagined.gt5r.loader.machines.OreByproducts;
import org.gtreimagined.gt5r.loader.machines.OreWasherLoader;
import org.gtreimagined.gt5r.loader.machines.PackagerLoader;
import org.gtreimagined.gt5r.loader.machines.PolarizerLoader;
import org.gtreimagined.gt5r.loader.machines.PrinterLoader;
import org.gtreimagined.gt5r.loader.machines.RoasterLoader;
import org.gtreimagined.gt5r.loader.machines.RockBreakerLoader;
import org.gtreimagined.gt5r.loader.machines.ScannerLoader;
import org.gtreimagined.gt5r.loader.machines.SifterLoader;
import org.gtreimagined.gt5r.loader.machines.SmelterLoader;
import org.gtreimagined.gt5r.loader.machines.ThermalCentrifugeLoader;
import org.gtreimagined.gt5r.loader.machines.UUMatterLoader;
import org.gtreimagined.gt5r.loader.machines.UnpackagerLoader;
import org.gtreimagined.gt5r.loader.machines.WiremillLoader;
import org.gtreimagined.gt5r.loader.machines.generator.Fuels;
import org.gtreimagined.gt5r.loader.machines.generator.LargeBoilerLoader;
import org.gtreimagined.gt5r.loader.machines.generator.SolidFuelBoilerLoader;
import org.gtreimagined.gt5r.loader.multi.BedrockDrillLoader;
import org.gtreimagined.gt5r.loader.multi.BlastFurnaceLoader;
import org.gtreimagined.gt5r.loader.multi.CokeOvenLoader;
import org.gtreimagined.gt5r.loader.multi.CrackingUnitLoader;
import org.gtreimagined.gt5r.loader.multi.DistillationTowerLoader;
import org.gtreimagined.gt5r.loader.multi.FusionReactorLoader;
import org.gtreimagined.gt5r.loader.multi.HeatExchangerLoader;
import org.gtreimagined.gt5r.loader.multi.ImplosionCompressorLoader;
import org.gtreimagined.gt5r.loader.multi.PyrolysisOvenLoader;
import org.gtreimagined.gt5r.loader.multi.TreeGrowthSimulatorLoader;
import org.gtreimagined.gt5r.loader.multi.VacuumFreezerLoader;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipeSerializer;
import org.gtreimagined.gt5r.proxy.ClientHandler;
import org.gtreimagined.gt5r.proxy.CommonHandler;
import org.gtreimagined.gtcore.GTCore;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.PLATE;
import static org.gtreimagined.gtlib.machine.Tier.IV;
import static org.gtreimagined.gtlib.worldgen.GTLibWorldGenerator.removeDecoratedFeatureFromAllBiomes;

@Mod(GT5Reimagined.ID)
public class GT5Reimagined extends GTMod {

    public static final String NAME = "GT5 Reimagined";
    public static GT5Reimagined INSTANCE;
    /**
     * Mod Data
     **/
    public static final String ID = "gt5r";
    public static Logger LOGGER = LogManager.getLogger(ID);
    private static ResourceLocation GUIDE_ID = new ResourceLocation(GT5Reimagined.ID, "guide");
    private final Guide guide;

    public GT5Reimagined() {
        super();
        GT5RConfig.createConfig();
        new AppliedEnergisticsRegistrar();
        new SpaceModRegistrar();
        new GT5RPostRegistrar();
        new TFCRegistrar();
        new IERegistrar();
        new RailcraftRegistrar();
        new ThermalRegistrar();
        new MekanismRegistrar();
        if (GTAPI.isModLoaded(Ref.MOD_FR)) new ForestryRegistrar();
        new BotaniaRegistrar();
        LOGGER.info("Loading GT5Reimagined");
        INSTANCE = this;
        GTLibDynamics.clientProvider(ID,
                () -> new GTBlockStateProvider(ID, NAME + " BlockStates"));
        GTLibDynamics.clientProvider(ID,
                () -> new GTItemModelProvider(ID, NAME + " Item Models"));
        GTLibDynamics.clientProvider(ID, GT5RLocalizations.en_US::new);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(ForgeEvents.class);
        MinecraftForge.EVENT_BUS.addListener(GT5Reimagined::registerRecipeLoaders);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(GT5Reimagined::registerCraftingLoaders);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(GT5Reimagined::onProviders);
        MinecraftForge.EVENT_BUS.addListener(GT5Reimagined::onWorldGen);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::registerLayerDefinitions);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::onPackEvent);
            MinecraftForge.EVENT_BUS.addListener(ClientHandler::onTooltipEvent);
        });
        this.guide = Guide.builder(GUIDE_ID).build();
    }


    @OnlyIn(Dist.CLIENT)
    public static void openGuideAtPreviousPage(ResourceLocation initialPage) {
        try {
            GuidesCommon.openGuide(Minecraft.getInstance().player, GUIDE_ID);
        } catch (Exception e) {
            LOGGER.error("Failed to open guide.", e);
        }
    }


    private void clientSetup(final FMLClientSetupEvent e) {
        e.enqueueWork(ClientHandler::setup);
    }

    private void setup(final FMLCommonSetupEvent e) {
    }

    public static void onProviders(GTProvidersEvent ev) {
        final GTBlockTagProvider[] p = new GTBlockTagProvider[1];
        ev.addProvider(() -> {
            p[0] = new GT5RBlockTagProvider(ID, NAME.concat(" Block Tags"), false);
            return p[0];
        });
        ev.addProvider(() -> new GT5RItemTagProvider(ID, NAME.concat(" Item Tags"),
                false, p[0]));
        ev.addProvider(() -> new GT5RFluidTagProvider(ID,
                NAME.concat(" Fluid Tags"), false));
        ev.addProvider(() -> new GTAdvancementProvider(ID,
                NAME.concat(" Advancements"), new ProgressionAdvancements()));
        ev.addProvider(() -> new GT5RBlockLootProvider(ID, NAME.concat(" Loot generator")));
    }
    
    public static void registerCraftingLoaders(GTCraftingEvent event) {
        event.addLoader(Miscellaneous::loadRecipes);
        event.addLoader(Smelting::loadRecipes);
        event.addLoader(WireCablesPlates::loadRecipes);
        event.addLoader(VanillaExtensions::loadRecipes);
        event.addLoader(MachineRecipes::loadRecipes);
        event.addLoader(SteamMachines::loadRecipes);
        event.addLoader(BlockParts::loadRecipes);
        event.addLoader(Parts::loadRecipes);
        event.addLoader(Circuitry::loadCraftingRecipes);
        event.addLoader(ElectricToolRecipes::loadRecipes);
        event.addLoader(MaterialCrafting::loadRecipes);
        event.addLoader(WoodCrafting::loadRecipes);
        event.addLoader(ModRecipeRemovals::init);
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            event.addLoader(AppliedEnergisticsRegistrar::craftingRecipes);
        }
        if (GTAPI.isModLoaded("computercraft")){
            event.addLoader(CCTweakedRegistrar::craftingRecipes);
        }
    }

    public static void registerRecipeLoaders(GTLoaderEvent event) {
        BiConsumer<String, IRecipeRegistrate.IRecipeLoader> loader = (a, b) -> event.registrat.add(ID, a, b);
        loader.accept("alloy_smelter", AlloySmelterLoader::init);
        loader.accept("arc_furnace", ArcFurnaceLoader::init);
        loader.accept("assembler", AssemblerLoader::init);
        loader.accept("autoclave", AutoclaveLoader::init);
        loader.accept("bath", BathLoader::init);
        loader.accept("bender", BenderLoader::init);
        loader.accept("canner", CannerLoader::init);
        loader.accept("centrifuge", CentrifugeLoader::init);
        loader.accept("chemical_reactor", ChemicalReactorLoader::init);
        loader.accept("circuitry", Circuitry::init);
        loader.accept("solid_fuel_boiler", SolidFuelBoilerLoader::init);
        loader.accept("compressor", CompressorLoader::init);
        loader.accept("crystallization_chamber", CrystallizationChamberLoader::init);
        loader.accept("cutter", CutterLoader::init);
        loader.accept("dehydrator", DehydratorLoader::init);
        loader.accept("distillery", DistilleryLoader::init);
        loader.accept("electrolyzer", ElectrolyzerLoader::init);
        loader.accept("electromagnetic_separator", ElectromagneticSeparatorLoader::init);
        loader.accept("extractor", ExtractorLoader::init);
        loader.accept("extruder", ExtruderLoader::init);
        loader.accept("fermenter", FermenterLoader::init);
        loader.accept("fluid_canner", FluidCannerLoader::init);
        loader.accept("fluid_heater", FluidHeaterLoader::init);
        loader.accept("fluid_press", FluidPressLoader::init);
        loader.accept("fluid_solidifier", FluidSolidifierLoader::init);
        loader.accept("forge_hammer", ForgeHammerLoader::init);
        loader.accept("forming_press", FormingPressLoader::init);
        loader.accept("fuels", Fuels::init);
        loader.accept("laser_engraver", LaserEngraverLoader::init);
        loader.accept("lathe", LatheLoader::init);
        loader.accept("macerator_auto", MaceratorLoader::initAuto);
        loader.accept("macerator", MaceratorLoader::init);
        loader.accept("mixer", MixerLoader::init);
        loader.accept("mortar", MortarLoader::init);
        loader.accept("ore_byproducts", OreByproducts::init);
        loader.accept("ore_washer", OreWasherLoader::init);
        loader.accept("packager", PackagerLoader::init);
        loader.accept("polarizer", PolarizerLoader::init);
        loader.accept("printer", PrinterLoader::init);
        loader.accept("recycling", RecyclingLoader::initRecyclingRecipes);
        loader.accept("roaster", RoasterLoader::init);
        loader.accept("rock_breaker", RockBreakerLoader::init);
        loader.accept("scanner", ScannerLoader::init);
        loader.accept("sifter", SifterLoader::init);
        loader.accept("smelter", SmelterLoader::init);
        loader.accept("thermal_centrifuge", ThermalCentrifugeLoader::init);
        loader.accept("uu_matter", UUMatterLoader::init);
        loader.accept("unpackager", UnpackagerLoader::init);
        loader.accept("wiremill", WiremillLoader::init);
        //multiblocks
        loader.accept("bedrock_drill", BedrockDrillLoader::init);
        loader.accept("blast_furnace", BlastFurnaceLoader::init);
        loader.accept("coke_oven", CokeOvenLoader::init);
        loader.accept("cracking_unit", CrackingUnitLoader::init);
        loader.accept("distillation_tower", DistillationTowerLoader::init);
        loader.accept("fusion_reactor", FusionReactorLoader::init);
        loader.accept("heat_exchanger", HeatExchangerLoader::init);
        loader.accept("implosion_compressor", ImplosionCompressorLoader::init);
        loader.accept("large_boiler", LargeBoilerLoader::init);
        loader.accept("pyrolysis_oven", PyrolysisOvenLoader::init);
        loader.accept("tree_growth_simulator", TreeGrowthSimulatorLoader::init);
        loader.accept("vacuum_freezer", VacuumFreezerLoader::init);
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            loader.accept("ae2", AppliedEnergisticsRegistrar::machineRecipes);
        }
        if (GTAPI.isModLoaded(Ref.MOD_FR)) loader.accept("forestry", ForestryRegistrar::init);
        if (GTAPI.isModLoaded("thermal")){
            loader.accept("thermal", ThermalRegistrar::thermalMachineRecipes);
        }
    }

    private static void onWorldGen(GTWorldGenEvent event){
        WorldGenLoader.init(event);
    }

    public static <T> T get(Class<? extends T> clazz, String id) {
        return GTAPI.get(clazz, id, ID);
    }

    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {
        switch (event) {
            case DATA_INIT -> {
                GT5RRecipeTypes.init();
                FusionRecipeSerializer.init();
                GT5RMaterialTypes.init();
                ToolTypes.init();
                Materials.init();
                TierMaps.init();
                GT5RData.init(side);
                GT5RCovers.init();
                GT5RFluids.init();
                GT5RItems.init();
                GT5RBlocks.init();
                GT5RMachines.init();
                MenuHandlers.init();
                Guis.init(side);
                Models.init();
                GT5RSounds.init();
                IGTWorldgenFunction function = (phase, name, climate, effects, gen , spawns, registry) -> {
                    if (GTLibConfig.VANILLA_ORE_GEN.get() && phase == Phase.REMOVE) {
                        removeDecoratedFeatureFromAllBiomes(gen, GenerationStep.Decoration.UNDERGROUND_DECORATION, Feature.ORE, Blocks.NETHER_QUARTZ_ORE.defaultBlockState(), Blocks.NETHER_GOLD_ORE.defaultBlockState());
                    }
                };
                GTAPI.register(IGTWorldgenFunction.class, "removed_ores", ID, function);
                if (GTAPI.isModLoaded(Ref.MOD_REI) && side.isClient()){
                    REIRegistrar.init();
                }
                PlayerTickCallback.PLAYER_TICK_CALLBACKS.add((end, logicalServer, player) -> {
                    if (!end && logicalServer && (((LivingEntityAccessor)player).getLastPos() == null || !((LivingEntityAccessor)player).getLastPos().equals(player.blockPosition()))){
                        BlockState state = player.level().getBlockState(player.getOnPos());
                        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
                        if (state.isAir()) state = player.level().getBlockState(player.getOnPos().below());
                        if (attributeinstance == null) {
                            return;
                        }
                        if (state.is(GT5RTags.ASPHALT)){
                            if (attributeinstance.getModifier(BlockAsphalt.SPEED_MODIFIER) == null){
                                attributeinstance.addTransientModifier(new AttributeModifier(BlockAsphalt.SPEED_MODIFIER, "Asphalt speed modification", GT5RConfig.ASPHALT_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_BASE));
                            } 
                        } else {
                            if (attributeinstance.getModifier(BlockAsphalt.SPEED_MODIFIER) != null){
                                attributeinstance.removeModifier(BlockAsphalt.SPEED_MODIFIER);
                            }
                        }
                    }
                });
            }
            case DATA_READY -> {
                CauldronInteraction.WATER.put(GT5RItems.WOODEN_BUCKET, ItemWoodenBucket::fillBucket);
                CauldronInteraction.EMPTY.put(GT5RItems.WOODEN_WATER_BUCKET, ItemWoodenBucket::emptyBucket);
                FluidUtils.addStateFluidHandler((state, level, pos) -> {
                    if (state.getBlock() == GT5RBlocks.BRONZE_WATER_CAULDRON || state.getBlock() == GT5RBlocks.BRONZE_CAULDRON){
                        return new BronzeCauldronWrapper(state, level, pos);
                    }
                    return null;
                });
                Structures.init();
                StructureInfo.init();
                GT5RTwilightStalctites.init();
                GT5RRemapping.init();
                LootLoader.init();
                BlockEntityScanner.initDefaultScannerFunctions();
                BookRegistration.registerBookTexture(GT5RItems.PrintedPages, new ResourceLocation(GTCore.ID, "block/books/folder_red_back"), new ResourceLocation(GTCore.ID, "block/books/folder_red_side"));
                GTLibXEIPlugin.addItemsToHide(l -> {
                    IGTTool screwdriver_mv = GTAPI.get(IGTTool.class, "electric_screwdriver_mv", GTCore.ID);
                    IGTTool screwdriver_hv = GTAPI.get(IGTTool.class, "electric_screwdriver_hv", GTCore.ID);
                    l.addAll(Arrays.asList(screwdriver_mv.getItem(), screwdriver_hv.getItem()));
                    if (!GT5RConfig.HARDER_CIRCUITS.get()){
                        l.addAll(Arrays.asList(GT5RItems.CircuitBoardPhenolic, GT5RItems.CircuitBoardPlastic, GT5RItems.CircuitBoardFiber,
                                GT5RItems.CircuitBoardMultiFiber, GT5RItems.CircuitBoardWetware, PLATE.get(Materials.FiberReinforcedEpoxyResin),
                                GT5RItems.GlassTube, GT5RItems.VacuumTube, GT5RItems.Transistor, GT5RItems.SMDTransistor, GT5RItems.Resistor,
                                GT5RItems.SMDResistor, GT5RItems.Diode, GT5RItems.SMDDiode, GT5RItems.Capacitor, GT5RItems.SMDCapacitor,
                                GT5RItems.SmallCoil, GT5RItems.PetriDish));
                        l.addAll(Arrays.asList(GT5RItems.CircuitWetware,
                                GT5RItems.Mainframe, GT5RItems.QuantumProcessorAssembly, GT5RItems.CrystalProcessor));
                        l.addAll(Arrays.asList(GT5RItems.GlowstoneDopedSiliconBoule, GT5RItems.NaquadahDopedSiliconBoule, GT5RItems.GlowstoneDopedWafer, GT5RItems.NaquadahDopedWafer,
                                GT5RItems.ASoCWafer, GT5RItems.ASoC, GT5RItems.CentralProcessingUnitWafer, GT5RItems.CentralProcessingUnit, GT5RItems.HPICWafer, GT5RItems.HighPowerIC,
                                GT5RItems.IntegratedLogicCircuitWafer, GT5RItems.IntegratedLogicCircuit, GT5RItems.NANDMemoryChipWafer, GT5RItems.NANDMemoryChip,
                                GT5RItems.NanoCpuWafer, GT5RItems.NanoCpu, GT5RItems.NorMemoryChipWafer, GT5RItems.NorMemoryChip, GT5RItems.PICWafer, GT5RItems.PowerIC,
                                GT5RItems.QBitWafer, GT5RItems.QBitProcessingUnit, GT5RItems.RandomAccessMemoryChipWafer, GT5RItems.RandomAccessMemoryChip, GT5RItems.SOCWafer, GT5RItems.SOC));
                        l.addAll(GT5RMachines.CIRCUIT_ASSEMBLER.getTiers().stream().map(GT5RMachines.CIRCUIT_ASSEMBLER::getBlockState).toList());
                    } else {
                        l.addAll(List.of(GT5RItems.BasicCircuitParts, GT5RItems.GoodCircuitParts, GT5RItems.AdvancedCircuitParts, GT5RItems.ComplexCircuitParts,
                                GT5RItems.BasicCircuitBoard, GT5RItems.GoodCircuitBoard, GT5RItems.AdvancedCircuitBoard, GT5RItems.ComplexCircuitBoard,
                                GT5RItems.EmptyCircuitPlate, GT5RItems.CopperEtchedWiring, GT5RItems.CopperCircuitPlate, GT5RItems.GoldEtchedWiring,
                                GT5RItems.GoldCircuitPlate, GT5RItems.PlatinumEtchedWiring, GT5RItems.PlatinumCircuitPlate));
                    }
                    if (!GT5RConfig.HARD_SETTINGS){
                        l.add(GT5RMachines.ASSEMBLY_LINE.getItem(IV));
                        l.add(GT5RBlocks.ADVANCED_ASSEMBLER_CASING);
                        l.add(GT5RBlocks.ASSEMBLY_LINE_CASING);
                    }
                    if (!GT5RConfig.COMPLICATED_CHEMICAL_PROCESSING.get()){
                        l.addAll(GT5RMachines.FERMENTER.getTiers().stream().map(GT5RMachines.FERMENTER::getBlockState).toList());
                    }
                });
                GTLibXEIPlugin.addFluidsToHide(l -> {
                    if (!GT5RConfig.COMPLICATED_CHEMICAL_PROCESSING.get()){
                        l.addAll(Arrays.asList(Materials.DinitrogenTetroxide.getGas(), Materials.Dimethylhydrazine.getLiquid(), Materials.Chloramine.getLiquid(), Materials.Dimethylamine.getGas()));
                        l.addAll(Arrays.asList(Materials.Chlorobenzene.getLiquid(), Materials.Acetone.getLiquid(), Materials.CalciumAcetateSolution.getLiquid(), Materials.FermentedBiomass.getLiquid(),
                                Materials.WoodTar.getLiquid(), Materials.WoodVinegar.getLiquid(), Materials.DilutedHydrochloricAcid.getLiquid(), Materials.Methanol.getLiquid(), Materials.Propanol.getLiquid(),
                                Materials.Butanol.getLiquid(), Materials.Heptanol.getLiquid(), Materials.Ethenol.getLiquid(), Materials.Propenol.getLiquid(), Materials.Butenol.getLiquid(),
                                Materials.Ethanediol.getLiquid(), Materials.Propanediol.getLiquid(), Materials.Butanediol.getLiquid(),
                                Materials.AceticAcid.getLiquid(), Materials.AllylChloride.getLiquid(), Materials.Benzaldehyde.getLiquid(), Materials.BenzoylChloride.getLiquid(),
                                Materials.BisphenolA.getLiquid(), Materials.Chloroform.getLiquid(), Materials.Cumene.getLiquid(), Materials.Dichloroethane.getLiquid(), Materials.Dimethyldichlorosilane.getLiquid(),
                                Materials.MethylAcetate.getLiquid(), Materials.Phenol.getLiquid(), Materials.PolyvinylAcetate.getLiquid(), Materials.Styrene.getLiquid(), Materials.SulfuricNaphtha.getLiquid(),
                                Materials.VinylAcetate.getLiquid(), Materials.HydrofluoricAcid.getLiquid(), Materials.HydrogenPeroxide.getLiquid(), Materials.HypochlorousAcid.getLiquid(),
                                Materials.PeroxydisulfuricAcid.getLiquid(), Materials.PhosphoricAcid.getLiquid(), Materials.CharcoalByproducts.getGas(), Materials.Butene.getGas(),
                                Materials.Chloromethane.getGas(), Materials.SulfuricGas.getGas(), Materials.CalciumAcetateSolution.getLiquid(), Materials.Gasoline.getLiquid(),
                                Materials.Ammonia.getGas(), Materials.HydrogenSulfide.getGas()));
                    }
                });
                TerraformFuelRegistry.addFuel(GT5RBlocks.SOLID_SUPER_FUEL.asItem(), 100000);
                TerraformFuelRegistry.addFuel(GT5RItems.WoodPellet, 200);
                TerraformFlammableBlockRegistry.addFlammableBlock(GT5RBlocks.WOOD_WALL, 5, 20);
                TerraformFlammableBlockRegistry.addFlammableBlock(GT5RMachines.WOOD_TANK.getBlockState(Tier.NONE), 5, 20);
                CommonHandler.setup();
              //  if (side == Dist.CLIENT) StructureInfo.init();
                TierMaps.providerInit();
            }
            case CLIENT_DATA_INIT -> {
                ClientData.init();
                GT5RModelManager.init();
            }
        }
    }

    @Override
    public String getId() {
        return ID;
    }
}
