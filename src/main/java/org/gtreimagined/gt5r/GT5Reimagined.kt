package org.gtreimagined.gt5r

import com.terraformersmc.terraform.utils.TerraformFlammableBlockRegistry
import com.terraformersmc.terraform.utils.TerraformFuelRegistry
import guideme.Guide
import guideme.GuidesCommon
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.world.BiomeModifier
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.gtreimagined.gt5r.block.BlockAsphalt
import org.gtreimagined.gt5r.blockentity.single.BlockEntityScanner
import org.gtreimagined.gt5r.client.GT5RModelManager
import org.gtreimagined.gt5r.data.ClientData
import org.gtreimagined.gt5r.data.GT5RBlocks
import org.gtreimagined.gt5r.data.GT5RCovers
import org.gtreimagined.gt5r.data.GT5RData
import org.gtreimagined.gt5r.data.GT5RFluids
import org.gtreimagined.gt5r.data.GT5RItems
import org.gtreimagined.gt5r.data.GT5RMachines
import org.gtreimagined.gt5r.data.GT5RMaterialTypes
import org.gtreimagined.gt5r.data.GT5RRecipeTypes
import org.gtreimagined.gt5r.data.GT5RSounds
import org.gtreimagined.gt5r.data.GT5RTags
import org.gtreimagined.gt5r.data.Guis
import org.gtreimagined.gt5r.data.Materials
import org.gtreimagined.gt5r.data.MenuHandlers
import org.gtreimagined.gt5r.data.Models
import org.gtreimagined.gt5r.data.StructureInfo
import org.gtreimagined.gt5r.data.Structures
import org.gtreimagined.gt5r.data.TierMaps
import org.gtreimagined.gt5r.data.ToolTypes
import org.gtreimagined.gt5r.datagen.GT5RBlockLootProvider
import org.gtreimagined.gt5r.datagen.GT5RBlockTagProvider
import org.gtreimagined.gt5r.datagen.GT5RFluidTagProvider
import org.gtreimagined.gt5r.datagen.GT5RItemTagProvider
import org.gtreimagined.gt5r.datagen.GT5RLocalizations
import org.gtreimagined.gt5r.datagen.GT5RTwilightStalctites
import org.gtreimagined.gt5r.datagen.ProgressionAdvancements
import org.gtreimagined.gt5r.events.forge.ForgeEvents
import org.gtreimagined.gt5r.integration.BluepowerRegistrar
import org.gtreimagined.gt5r.integration.CCTweakedRegistrar
import org.gtreimagined.gt5r.integration.ModRecipeRemovals
import org.gtreimagined.gt5r.integration.SpaceModRegistrar
import org.gtreimagined.gt5r.integration.ae2.AppliedEnergisticsRegistrar
import org.gtreimagined.gt5r.integration.botania.BotaniaRegistrar
import org.gtreimagined.gt5r.integration.enderio.EnderIORecipes
import org.gtreimagined.gt5r.integration.forestry.ForestryRegistrar
import org.gtreimagined.gt5r.integration.ie.IERegistrar
import org.gtreimagined.gt5r.integration.mekanism.MekanismRegistrar
import org.gtreimagined.gt5r.integration.railcraft.RailcraftRegistrar
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar
import org.gtreimagined.gt5r.integration.thermal.ThermalRegistrar
import org.gtreimagined.gt5r.items.ItemWoodenBucket
import org.gtreimagined.gt5r.loader.LootLoader
import org.gtreimagined.gt5r.loader.WorldGenLoader
import org.gtreimagined.gt5r.loader.crafting.BlockParts
import org.gtreimagined.gt5r.loader.crafting.ElectricToolRecipes
import org.gtreimagined.gt5r.loader.crafting.MachineRecipes
import org.gtreimagined.gt5r.loader.crafting.MaterialCrafting
import org.gtreimagined.gt5r.loader.crafting.Miscellaneous
import org.gtreimagined.gt5r.loader.crafting.Parts
import org.gtreimagined.gt5r.loader.crafting.Smelting
import org.gtreimagined.gt5r.loader.crafting.SteamMachines
import org.gtreimagined.gt5r.loader.crafting.VanillaExtensions
import org.gtreimagined.gt5r.loader.crafting.WireCablesPlates
import org.gtreimagined.gt5r.loader.crafting.WoodCrafting
import org.gtreimagined.gt5r.loader.items.Circuitry
import org.gtreimagined.gt5r.loader.machines.AlloySmelterLoader
import org.gtreimagined.gt5r.loader.machines.ArcFurnaceLoader
import org.gtreimagined.gt5r.loader.machines.AssemblerLoader
import org.gtreimagined.gt5r.loader.machines.AutoclaveLoader
import org.gtreimagined.gt5r.loader.machines.BenderLoader
import org.gtreimagined.gt5r.loader.machines.CannerLoader
import org.gtreimagined.gt5r.loader.machines.CentrifugeLoader
import org.gtreimagined.gt5r.loader.machines.ChemicalReactorLoader
import org.gtreimagined.gt5r.loader.machines.CompressorLoader
import org.gtreimagined.gt5r.loader.machines.CrystallizationChamberLoader
import org.gtreimagined.gt5r.loader.machines.CutterLoader
import org.gtreimagined.gt5r.loader.machines.DehydratorLoader
import org.gtreimagined.gt5r.loader.machines.DistilleryLoader
import org.gtreimagined.gt5r.loader.machines.ElectrolyzerLoader
import org.gtreimagined.gt5r.loader.machines.ElectromagneticSeparatorLoader
import org.gtreimagined.gt5r.loader.machines.ExtractorLoader
import org.gtreimagined.gt5r.loader.machines.FermenterLoader
import org.gtreimagined.gt5r.loader.machines.FluidCannerLoader
import org.gtreimagined.gt5r.loader.machines.FluidHeaterLoader
import org.gtreimagined.gt5r.loader.machines.FluidPressLoader
import org.gtreimagined.gt5r.loader.machines.FluidSolidifierLoader
import org.gtreimagined.gt5r.loader.machines.ForgeHammerLoader
import org.gtreimagined.gt5r.loader.machines.FormingPressLoader
import org.gtreimagined.gt5r.loader.machines.LaserEngraverLoader
import org.gtreimagined.gt5r.loader.machines.LatheLoader
import org.gtreimagined.gt5r.loader.machines.MaceratorLoader
import org.gtreimagined.gt5r.loader.machines.MixerLoader
import org.gtreimagined.gt5r.loader.machines.MortarLoader
import org.gtreimagined.gt5r.loader.machines.OreByproducts
import org.gtreimagined.gt5r.loader.machines.OreWasherLoader
import org.gtreimagined.gt5r.loader.machines.PackagerLoader
import org.gtreimagined.gt5r.loader.machines.PolarizerLoader
import org.gtreimagined.gt5r.loader.machines.PrinterLoader
import org.gtreimagined.gt5r.loader.machines.RecyclingLoader
import org.gtreimagined.gt5r.loader.machines.RoasterLoader
import org.gtreimagined.gt5r.loader.machines.RockBreakerLoader
import org.gtreimagined.gt5r.loader.machines.ScannerLoader
import org.gtreimagined.gt5r.loader.machines.SifterLoader
import org.gtreimagined.gt5r.loader.machines.SmelterLoader
import org.gtreimagined.gt5r.loader.machines.ThermalCentrifugeLoader
import org.gtreimagined.gt5r.loader.machines.UUMatterLoader
import org.gtreimagined.gt5r.loader.machines.UnpackagerLoader
import org.gtreimagined.gt5r.loader.machines.WiremillLoader
import org.gtreimagined.gt5r.loader.machines.generator.Fuels
import org.gtreimagined.gt5r.loader.machines.generator.LargeBoilerLoader
import org.gtreimagined.gt5r.loader.machines.generator.SolidFuelBoilerLoader
import org.gtreimagined.gt5r.loader.machines.initBath
import org.gtreimagined.gt5r.loader.machines.initExtruder
import org.gtreimagined.gt5r.loader.multi.BedrockDrillLoader
import org.gtreimagined.gt5r.loader.multi.BlastFurnaceLoader
import org.gtreimagined.gt5r.loader.multi.CokeOvenLoader
import org.gtreimagined.gt5r.loader.multi.CrackingUnitLoader
import org.gtreimagined.gt5r.loader.multi.DistillationTowerLoader
import org.gtreimagined.gt5r.loader.multi.FusionReactorLoader
import org.gtreimagined.gt5r.loader.multi.HeatExchangerLoader
import org.gtreimagined.gt5r.loader.multi.ImplosionCompressorLoader
import org.gtreimagined.gt5r.loader.multi.PyrolysisOvenLoader
import org.gtreimagined.gt5r.loader.multi.TreeGrowthSimulatorLoader
import org.gtreimagined.gt5r.loader.multi.VacuumFreezerLoader
import org.gtreimagined.gt5r.machine.caps.BronzeCauldronWrapper
import org.gtreimagined.gt5r.machine.recipe.FusionRecipeSerializer
import org.gtreimagined.gt5r.proxy.ClientHandler
import org.gtreimagined.gt5r.proxy.CommonHandler
import org.gtreimagined.gtcore.BookRegistration
import org.gtreimagined.gtcore.GTCore
import org.gtreimagined.gtlib.GTAPI
import org.gtreimagined.gtlib.GTLibConfig
import org.gtreimagined.gtlib.GTMod
import org.gtreimagined.gtlib.Ref
import org.gtreimagined.gtlib.common.event.PlayerTickCallback
import org.gtreimagined.gtlib.data.GTMaterialTypes
import org.gtreimagined.gtlib.datagen.GTLibDynamics
import org.gtreimagined.gtlib.datagen.providers.GTAdvancementProvider
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider
import org.gtreimagined.gtlib.event.GTCraftingEvent
import org.gtreimagined.gtlib.event.GTLoaderEvent
import org.gtreimagined.gtlib.event.GTProvidersEvent
import org.gtreimagined.gtlib.event.GTWorldGenEvent
import org.gtreimagined.gtlib.integration.recipeviewer.GTLibRecipeViewerPlugin
import org.gtreimagined.gtlib.machine.Tier
import org.gtreimagined.gtlib.mixin.LivingEntityAccessor
import org.gtreimagined.gtlib.recipe.loader.IRecipeRegistrate
import org.gtreimagined.gtlib.registration.RegistrationEvent
import org.gtreimagined.gtlib.tool.IGTTool
import org.gtreimagined.gtlib.util.FluidUtils
import org.gtreimagined.gtlib.worldgen.GTLibWorldGenerator
import org.gtreimagined.gtlib.worldgen.IGTWorldgenFunction
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(GT5Reimagined.ID)
object GT5Reimagined : GTMod(){
    const val NAME: String = "GT5 Reimagined"

    /**
     * Mod Data
     */
    const val ID: String = "gt5r"
    @JvmField
    var LOGGER: Logger = LogManager.getLogger(ID)
    private val GUIDE_ID = ResourceLocation(ID, "guide")
    private val guide: Guide

    init {
        GT5RConfig.createConfig()
        AppliedEnergisticsRegistrar()
        SpaceModRegistrar()
        GT5RPostRegistrar()
        TFCRegistrar()
        IERegistrar()
        RailcraftRegistrar()
        ThermalRegistrar()
        BluepowerRegistrar()
        MekanismRegistrar()
        if (GTAPI.isModLoaded(Ref.MOD_FR)) ForestryRegistrar()
        BotaniaRegistrar()
        LOGGER.info("Loading GT5Reimagined")
        GTLibDynamics.clientProvider(ID) { GTBlockStateProvider(ID, "$NAME BlockStates") }
        GTLibDynamics.clientProvider(ID) { GTItemModelProvider(ID, "$NAME Item Models") }
        GTLibDynamics.clientProvider(ID) { GT5RLocalizations.en_US() }
        MOD_BUS.addListener(this::setup)

        FORGE_BUS.register(ForgeEvents::class.java)
        FORGE_BUS.addListener(this::registerRecipeLoaders)
        MOD_BUS.addListener(this::registerCraftingLoaders)
        MOD_BUS.addListener(this::onProviders)
        FORGE_BUS.addListener(this::onWorldGen)
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) {
            Runnable {
                MOD_BUS.addListener(this::clientSetup)
                MOD_BUS.addListener(ClientHandler::registerLayerDefinitions)
                MOD_BUS.addListener(ClientHandler::onPackEvent)
                FORGE_BUS.addListener(ClientHandler::onTooltipEvent)
            }
        }
        this.guide = Guide.builder(GUIDE_ID).build()
    }

    @OnlyIn(Dist.CLIENT)
    @JvmStatic
    fun openGuideAtPreviousPage(initialPage: ResourceLocation) {
        try {
            GuidesCommon.openGuide(Minecraft.getInstance().player, GUIDE_ID)
        } catch (e: Exception) {
            LOGGER.error("Failed to open guide.", e)
        }
    }


    private fun clientSetup(e: FMLClientSetupEvent) {
        e.enqueueWork { ClientHandler.setup() }
    }

    private fun setup(e: FMLCommonSetupEvent?) {
    }

    fun onProviders(ev: GTProvidersEvent) {
        val blockProvider = GT5RBlockTagProvider(ID, "$NAME Block Tags", false)
        ev.addProvider { blockProvider }
        ev.addProvider {
            GT5RItemTagProvider(ID, "$NAME Item Tags", false, blockProvider)
        }
        ev.addProvider {
            GT5RFluidTagProvider(ID, "$NAME Fluid Tags", false)
        }
        ev.addProvider {
            GTAdvancementProvider(ID, "$NAME Advancements", ProgressionAdvancements())
        }
        ev.addProvider { GT5RBlockLootProvider(ID, "$NAME Loot generator") }
    }

    fun registerCraftingLoaders(event: GTCraftingEvent) {
        event.addLoader(Miscellaneous::loadRecipes)
        event.addLoader(Smelting::loadRecipes)
        event.addLoader(WireCablesPlates::loadRecipes)
        event.addLoader(VanillaExtensions::loadRecipes)
        event.addLoader(MachineRecipes::loadRecipes)
        event.addLoader(SteamMachines::loadRecipes)
        event.addLoader(BlockParts::loadRecipes)
        event.addLoader(Parts::loadRecipes)
        event.addLoader(Circuitry::loadCraftingRecipes)
        event.addLoader(ElectricToolRecipes::loadRecipes)
        event.addLoader(MaterialCrafting::loadRecipes)
        event.addLoader(WoodCrafting::loadRecipes)
        event.addLoader(ModRecipeRemovals::init)
        if (GTAPI.isModLoaded(Ref.MOD_AE)){
            event.addLoader(AppliedEnergisticsRegistrar::craftingRecipes)
        }
        if (GTAPI.isModLoaded("computercraft")){
            event.addLoader(CCTweakedRegistrar::craftingRecipes)
        }
    }

    fun registerRecipeLoaders(event: GTLoaderEvent) {
        val loader = { a: String, b: IRecipeRegistrate.IRecipeLoader ->
            event.registrat.add(ID, a, b)
        }
        loader("alloy_smelter", AlloySmelterLoader::init)
        loader("arc_furnace", ArcFurnaceLoader::init)
        loader("assembler", AssemblerLoader::init)
        loader("autoclave", AutoclaveLoader::init)
        loader("bath", ::initBath)
        loader("bender", BenderLoader::init)
        loader("canner", CannerLoader::init)
        loader("centrifuge", CentrifugeLoader::init)
        loader("chemical_reactor", ChemicalReactorLoader::init)
        loader("circuitry", Circuitry::init)
        loader("solid_fuel_boiler", SolidFuelBoilerLoader::init)
        loader("compressor", CompressorLoader::init)
        loader("crystallization_chamber", CrystallizationChamberLoader::init)
        loader("cutter", CutterLoader::init)
        loader("dehydrator", DehydratorLoader::init)
        loader("distillery", DistilleryLoader::init)
        loader("electrolyzer", ElectrolyzerLoader::init)
        loader("electromagnetic_separator", ElectromagneticSeparatorLoader::init)
        loader("extractor", ExtractorLoader::init)
        loader("extruder", ::initExtruder)
        loader("fermenter", FermenterLoader::init)
        loader("fluid_canner", FluidCannerLoader::init)
        loader("fluid_heater", FluidHeaterLoader::init)
        loader("fluid_press", FluidPressLoader::init)
        loader("fluid_solidifier", FluidSolidifierLoader::init)
        loader("forge_hammer", ForgeHammerLoader::init)
        loader("forming_press", FormingPressLoader::init)
        loader("fuels", Fuels::init)
        loader("laser_engraver", LaserEngraverLoader::init)
        loader("lathe", LatheLoader::init)
        loader("macerator_auto", MaceratorLoader::initAuto)
        loader("macerator", MaceratorLoader::init)
        loader("mixer", MixerLoader::init)
        loader("mortar", MortarLoader::init)
        loader("ore_byproducts", OreByproducts::init)
        loader("ore_washer", OreWasherLoader::init)
        loader("packager", PackagerLoader::init)
        loader("polarizer", PolarizerLoader::init)
        loader("printer", PrinterLoader::init)
        loader("recycling", RecyclingLoader::initRecyclingRecipes)
        loader("roaster", RoasterLoader::init)
        loader("rock_breaker", RockBreakerLoader::init)
        loader("scanner", ScannerLoader::init)
        loader("sifter", SifterLoader::init)
        loader("smelter", SmelterLoader::init)
        loader("thermal_centrifuge", ThermalCentrifugeLoader::init)
        loader("uu_matter", UUMatterLoader::init)
        loader("unpackager", UnpackagerLoader::init)
        loader("wiremill", WiremillLoader::init)
        //multiblocks
        loader("bedrock_drill", BedrockDrillLoader::init)
        loader("blast_furnace", BlastFurnaceLoader::init)
        loader("coke_oven", CokeOvenLoader::init)
        loader("cracking_unit", CrackingUnitLoader::init)
        loader("distillation_tower", DistillationTowerLoader::init)
        loader("fusion_reactor", FusionReactorLoader::init)
        loader("heat_exchanger", HeatExchangerLoader::init)
        loader("implosion_compressor", ImplosionCompressorLoader::init)
        loader("large_boiler", LargeBoilerLoader::init)
        loader("pyrolysis_oven", PyrolysisOvenLoader::init)
        loader("tree_growth_simulator", TreeGrowthSimulatorLoader::init)
        loader("vacuum_freezer", VacuumFreezerLoader::init)
        if (GTAPI.isModLoaded(Ref.MOD_AE)) {
            loader("ae2", AppliedEnergisticsRegistrar::machineRecipes)
        }
        if (GTAPI.isModLoaded("enderio")) loader("enderio", EnderIORecipes::init)
        if (GTAPI.isModLoaded(Ref.MOD_FR)) loader("forestry", ForestryRegistrar::init)
        if (GTAPI.isModLoaded("thermal")) {
            loader("thermal", ThermalRegistrar::thermalMachineRecipes)
        }
    }

    private fun onWorldGen(event: GTWorldGenEvent?) {
        WorldGenLoader.init(event)
    }

    @JvmStatic
    fun <T> get(clazz: Class<out T>, id: String): T? {
        return GTAPI.get(clazz, id, ID)
    }

    inline fun <reified T> get(id: String): T?{
        return get(T::class.java, id)
    }

    override fun onRegistrationEvent(event: RegistrationEvent, side: Dist) {
        when (event) {
            RegistrationEvent.DATA_INIT -> {
                GT5RRecipeTypes.init()
                FusionRecipeSerializer.init()
                GT5RMaterialTypes.init()
                ToolTypes.init()
                Materials.init()
                TierMaps.init()
                GT5RData.init(side)
                GT5RCovers.init()
                GT5RFluids.init()
                GT5RItems.init()
                GT5RBlocks.init()
                GT5RMachines.init()
                MenuHandlers.init()
                Guis.init(side)
                Models.init()
                GT5RSounds.init()
                val function = IGTWorldgenFunction { phase, _, _, _, gen, _, _ ->
                        if (GTLibConfig.VANILLA_ORE_GEN.get() && phase == BiomeModifier.Phase.REMOVE) {
                            GTLibWorldGenerator.removeDecoratedFeatureFromAllBiomes(
                                gen,
                                GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                Feature.ORE,
                                Blocks.NETHER_QUARTZ_ORE.defaultBlockState(),
                                Blocks.NETHER_GOLD_ORE.defaultBlockState()
                            )
                        }
                    }
                GTAPI.register<IGTWorldgenFunction>("removed_ores", ID, function)
                PlayerTickCallback.PLAYER_TICK_CALLBACKS.add { end, logicalServer, player ->
                    if (!end && logicalServer && ((player as LivingEntityAccessor).getLastPos() == null || (player as LivingEntityAccessor).getLastPos() != player.blockPosition())) {
                        var state = player.level().getBlockState(player.onPos)
                        val attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED)
                        if (state.isAir) state = player.level().getBlockState(player.onPos.below())
                        if (attributeinstance == null) {
                            return@add
                        }
                        if (state.`is`(GT5RTags.ASPHALT)) {
                            if (attributeinstance.getModifier(BlockAsphalt.SPEED_MODIFIER) == null) {
                                attributeinstance.addTransientModifier(
                                    AttributeModifier(BlockAsphalt.SPEED_MODIFIER, "Asphalt speed modification",
                                        GT5RConfig.ASPHALT_MULTIPLIER.get(), AttributeModifier.Operation.MULTIPLY_BASE)
                                )
                            }
                        } else {
                            if (attributeinstance.getModifier(BlockAsphalt.SPEED_MODIFIER) != null) {
                                attributeinstance.removeModifier(BlockAsphalt.SPEED_MODIFIER)
                            }
                        }
                    }
                }
            }

            RegistrationEvent.DATA_READY -> {
                CauldronInteraction.WATER.put(GT5RItems.WOODEN_BUCKET, ItemWoodenBucket::fillBucket)
                CauldronInteraction.EMPTY.put(GT5RItems.WOODEN_WATER_BUCKET, ItemWoodenBucket::emptyBucket)
                FluidUtils.addStateFluidHandler { state: BlockState?, level: Level?, pos: BlockPos? ->
                    if (state!!.block === GT5RBlocks.BRONZE_WATER_CAULDRON || state.block === GT5RBlocks.BRONZE_CAULDRON) {
                        return@addStateFluidHandler BronzeCauldronWrapper(state, level, pos)
                    }
                    null
                }
                Structures.init()
                if (side.isClient) {
                    StructureInfo.init()
                }
                GT5RTwilightStalctites.init()
                GT5RRemapping.init()
                LootLoader.init()
                BlockEntityScanner.initDefaultScannerFunctions()
                BookRegistration.registerBookTexture(
                    GT5RItems.PrintedPages,
                    ResourceLocation(GTCore.ID, "block/books/folder_red_back"),
                    ResourceLocation(GTCore.ID, "block/books/folder_red_side")
                )
                GTLibRecipeViewerPlugin.addItemsToHide { l: MutableList<ItemLike?>? ->
                    val screwdriverMv = GTAPI.get<IGTTool>("electric_screwdriver_mv", GTCore.ID)
                    val screwdriverHv = GTAPI.get<IGTTool>("electric_screwdriver_hv", GTCore.ID)
                    l!!.addAll(listOf(screwdriverMv!!.item, screwdriverHv!!.item))
                    if (!GT5RConfig.HARDER_CIRCUITS.get()) {
                        l.addAll(listOf(
                            GT5RItems.CircuitBoardPhenolic, GT5RItems.CircuitBoardPlastic, GT5RItems.CircuitBoardEpoxy, GT5RItems.CircuitBoardFiber,
                            GT5RItems.CircuitBoardMultiFiber, GT5RItems.CircuitBoardWetware, GTMaterialTypes.PLATE.get(Materials.FiberReinforcedEpoxyResin),
                            GT5RItems.GlassTube, GT5RItems.VacuumTube, GT5RItems.Transistor, GT5RItems.SMDTransistor, GT5RItems.Resistor, GT5RItems.SMDResistor,
                            GT5RItems.Diode, GT5RItems.SMDDiode, GT5RItems.Capacitor, GT5RItems.SMDCapacitor, GT5RItems.SmallCoil, GT5RItems.PetriDish
                        ))
                        l.addAll(listOf(GT5RItems.CircuitWetware, GT5RItems.Mainframe, GT5RItems.QuantumProcessorAssembly, GT5RItems.CrystalProcessor))
                        l.addAll(listOf(GT5RItems.PhosphorusDopedSiliconBoule, GT5RItems.IndiumDopedSiliconBoule, GT5RItems.PhosphorusDopedWafer,
                            GT5RItems.IndiumDopedWafer, GT5RItems.ASoCWafer, GT5RItems.ASoC, GT5RItems.CentralProcessingUnitWafer, GT5RItems.CentralProcessingUnit,
                            GT5RItems.HPICWafer, GT5RItems.HighPowerIC, GT5RItems.IntegratedLogicCircuitWafer, GT5RItems.IntegratedLogicCircuit,
                            GT5RItems.NANDMemoryChipWafer, GT5RItems.NANDMemoryChip, GT5RItems.NanoCpuWafer, GT5RItems.NanoCpu, GT5RItems.NorMemoryChipWafer,
                            GT5RItems.NorMemoryChip, GT5RItems.PICWafer, GT5RItems.PowerIC, GT5RItems.QBitWafer, GT5RItems.QBitProcessingUnit,
                            GT5RItems.RandomAccessMemoryChipWafer, GT5RItems.RandomAccessMemoryChip, GT5RItems.SOCWafer, GT5RItems.SOC))
                        l.addAll(
                            GT5RMachines.CIRCUIT_ASSEMBLER.getTiers().stream()
                                .map{ tier -> GT5RMachines.CIRCUIT_ASSEMBLER.getBlockState(tier) }
                                .toList())
                    } else {
                        l.addAll(listOf(GT5RItems.BasicCircuitParts, GT5RItems.GoodCircuitParts, GT5RItems.AdvancedCircuitParts,
                            GT5RItems.ComplexCircuitParts, GT5RItems.BasicCircuitBoard, GT5RItems.GoodCircuitBoard, GT5RItems.AdvancedCircuitBoard,
                            GT5RItems.ComplexCircuitBoard, GT5RItems.EmptyCircuitPlate, GT5RItems.CopperEtchedWiring, GT5RItems.CopperCircuitPlate,
                            GT5RItems.GoldEtchedWiring, GT5RItems.GoldCircuitPlate, GT5RItems.PlatinumEtchedWiring, GT5RItems.PlatinumCircuitPlate))
                    }
                    if (!GT5RConfig.HARD_SETTINGS.get()) {
                        l.add(GT5RMachines.ASSEMBLY_LINE.getItem(Tier.IV))
                        l.add(GT5RBlocks.ADVANCED_ASSEMBLER_CASING)
                        l.add(GT5RBlocks.ASSEMBLY_LINE_CASING)
                    }
                    if (!GT5RConfig.COMPLICATED_CHEMICAL_PROCESSING.get()) {
                        l.addAll(
                            GT5RMachines.FERMENTER.getTiers().stream()
                                .map { tier -> GT5RMachines.FERMENTER.getBlockState(tier) }
                                .toList())
                    }
                }
                GTLibRecipeViewerPlugin.addFluidsToHide { l ->
                    if (!GT5RConfig.COMPLICATED_CHEMICAL_PROCESSING.get()) {
                        l.addAll(listOf(Materials.DinitrogenTetroxide.gas, Materials.Dimethylhydrazine.liquid, Materials.Chloramine.liquid,
                            Materials.Dimethylamine.gas))
                        l.addAll(listOf(Materials.Chlorobenzene.liquid, Materials.Acetone.liquid, Materials.CalciumAcetateSolution.liquid,
                            Materials.FermentedBiomass.liquid, Materials.WoodTar.liquid, Materials.WoodVinegar.liquid, Materials.DilutedHydrochloricAcid.liquid,
                            Materials.Methanol.liquid, Materials.Propanol.liquid, Materials.Butanol.liquid, Materials.Heptanol.liquid, Materials.Ethenol.liquid,
                            Materials.Propenol.liquid, Materials.Butenol.liquid, Materials.Ethanediol.liquid, Materials.Propanediol.liquid,
                            Materials.Butanediol.liquid, Materials.AceticAcid.liquid, Materials.AllylChloride.liquid, Materials.Benzaldehyde.liquid,
                            Materials.BenzoylChloride.liquid, Materials.Chloroform.liquid, Materials.Cumene.liquid, Materials.Dichloroethane.liquid,
                            Materials.Dimethyldichlorosilane.liquid, Materials.MethylAcetate.liquid, Materials.Phenol.liquid, Materials.PolyvinylAcetate.liquid,
                            Materials.Styrene.liquid, Materials.VinylAcetate.liquid, Materials.HydrofluoricAcid.liquid, Materials.HydrogenPeroxide.liquid,
                            Materials.HypochlorousAcid.liquid, Materials.PeroxydisulfuricAcid.liquid, Materials.PhosphoricAcid.liquid,
                            Materials.CharcoalByproducts.gas, Materials.Butene.gas, Materials.Chloromethane.gas, Materials.CalciumAcetateSolution.liquid,
                            Materials.Gasoline.liquid, Materials.Ammonia.gas, Materials.HydrogenSulfide.gas))
                    }
                    if (!GT5RConfig.SULFURIC_OIL_OUTPUTS.get()) {
                        l.addAll(listOf(Materials.SulfuricGas.gas, Materials.SulfuricNaphtha.liquid, Materials.SulfuricFuelOil.liquid,
                            Materials.SulfuricDiesel.liquid, Materials.SulfuricKerosene.liquid))
                    }
                }
                TerraformFuelRegistry.addFuel(GT5RBlocks.SOLID_SUPER_FUEL.asItem(), 100000)
                TerraformFuelRegistry.addFuel(GT5RItems.WoodPellet, 200)
                TerraformFlammableBlockRegistry.addFlammableBlock(GT5RBlocks.WOOD_WALL, 5, 20)
                TerraformFlammableBlockRegistry.addFlammableBlock(GT5RMachines.WOOD_TANK.getBlockState(Tier.NONE), 5, 20)
                CommonHandler.setup()
                //  if (side == Dist.CLIENT) StructureInfo.init();
                TierMaps.providerInit()
            }

            RegistrationEvent.CLIENT_DATA_INIT -> {
                ClientData.init()
                GT5RModelManager.init()
            }
            else -> {}
        }
    }

    override fun getId(): String {
        return ID
    }
}