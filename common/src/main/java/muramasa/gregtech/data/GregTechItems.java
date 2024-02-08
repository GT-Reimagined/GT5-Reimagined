package muramasa.gregtech.data;

import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemBattery;
import muramasa.antimatter.item.ItemFluidCell;
import muramasa.antimatter.machine.Tier;
import muramasa.gregtech.GTIRef;
import muramasa.gregtech.GregTech;
import muramasa.gregtech.items.*;
import net.minecraft.world.item.Item;

import static muramasa.gregtech.data.Materials.*;

public class GregTechItems {
    public static ItemBasic<?> ComputerMonitor = new ItemBasic<>(GTIRef.ID, "computer_monitor").tip("Can be placed on machines as a cover");
    public static ItemPortableScanner PortableScanner = new ItemPortableScanner();
    public static ItemFluidCell CellTin = new ItemFluidCell(GTIRef.ID, Tin, 1000);
    public static ItemFluidCell CellSteel = new ItemFluidCell(GTIRef.ID, Steel, 16000);
    public static ItemFluidCell CellTungstensteel = new ItemFluidCell(GTIRef.ID, TungstenSteel, 64000);
    public static ItemBasic<?> Scrap = new ItemBasic<>(GTIRef.ID, "scrap");
    public static ItemBasic<?> WoodPellet = new ItemBasic<>(GTIRef.ID, "wood_pellet");
    public static ItemBasic<?> PrintedPages = new ItemPrintedPages(GTIRef.ID, "printed_pages").tip("Used to make written Books");
    public static ItemBasic<?> DataStick = new ItemDataStick(GTIRef.ID, "data_stick").tip("A Low Capacity Data Storage");
    public static ItemBasic<?> QuantumEye = new ItemBasic<>(GTIRef.ID, "quantum_eye").tip("Improved Ender Eye");
    public static ItemBasic<?> QuantumStar = new ItemBasic<>(GTIRef.ID, "quantum_star").tip("Improved Nether Star");
    public static ItemBasic<?> GraviStar = new ItemBasic<>(GTIRef.ID, "gravi_star").tip("Ultimate Nether Star");
    public static ItemBasic<?> EmptyGeigerCounter = new ItemBasic<>(GTIRef.ID, "empty_geiger_counter").tip("Fill with proper inert Gas");
    public static ItemBasic<?> GeigerCounter = new ItemBasic<>(GTIRef.ID, "geiger_counter").tip("Measures Neutron Count");
    public static ItemBasic<?> SuperFuelBinder = new ItemBasic<>(GTIRef.ID, "super_fuel_binder");
    public static ItemBasic<?> PistonLV = new ItemBasic<>(GTIRef.ID, "piston_lv");
    public static ItemBasic<?> PistonMV = new ItemBasic<>(GTIRef.ID, "piston_mv");
    public static ItemBasic<?> PistonHV = new ItemBasic<>(GTIRef.ID, "piston_hv");
    public static ItemBasic<?> PistonEV = new ItemBasic<>(GTIRef.ID, "piston_ev");
    public static ItemBasic<?> PistonIV = new ItemBasic<>(GTIRef.ID, "piston_iv");
    public static ItemBasic<?> FieldGenLV = new ItemBasic<>(GTIRef.ID, "field_gen_lv");
    public static ItemBasic<?> FieldGenMV = new ItemBasic<>(GTIRef.ID, "field_gen_mv");
    public static ItemBasic<?> FieldGenHV = new ItemBasic<>(GTIRef.ID, "field_gen_hv");
    public static ItemBasic<?> FieldGenEV = new ItemBasic<>(GTIRef.ID, "field_gen_ev");
    public static ItemBasic<?> FieldGenIV = new ItemBasic<>(GTIRef.ID, "field_gen_iv");
    public static ItemBasic<?> EmitterLV = new ItemBasic<>(GTIRef.ID, "emitter_lv");
    public static ItemBasic<?> EmitterMV = new ItemBasic<>(GTIRef.ID, "emitter_mv");
    public static ItemBasic<?> EmitterHV = new ItemBasic<>(GTIRef.ID, "emitter_hv");
    public static ItemBasic<?> EmitterEV = new ItemBasic<>(GTIRef.ID, "emitter_ev");
    public static ItemBasic<?> EmitterIV = new ItemBasic<>(GTIRef.ID, "emitter_iv");
    public static ItemBasic<?> SensorLV = new ItemBasic<>(GTIRef.ID, "sensor_lv");
    public static ItemBasic<?> SensorMV = new ItemBasic<>(GTIRef.ID, "sensor_mv");
    public static ItemBasic<?> SensorHV = new ItemBasic<>(GTIRef.ID, "sensor_hv");
    public static ItemBasic<?> SensorEV = new ItemBasic<>(GTIRef.ID, "sensor_ev");
    public static ItemBasic<?> SensorIV = new ItemBasic<>(GTIRef.ID, "sensor_iv");
    /** CIRCUIT ITEMS **/

    public static ItemBasic<?> MicroProcessor = new ItemBasic<>(GTIRef.ID, "microprocessor", "circuits/").tip("A Basic Circuit");
    public static ItemBasic<?> IntegratedProcessor = new ItemBasic<>(GTIRef.ID, "integrated_processor", "circuits/").tip("A Good Circuit");
    public static ItemBasic<?> ProcessorAssembly = new ItemBasic<>(GTIRef.ID, "processor_assembly", "circuits/").tip("An advanced Circuit");
    public static ItemBasic<?> NanoProcessor = new ItemBasic<>(GTIRef.ID, "nanoprocessor", "circuits/").tip("An advanced Circuit");
    public static ItemBasic<?> Workstation = new ItemBasic<>(GTIRef.ID, "workstation", "circuits/").tip("An extreme Circuit");
    public static ItemBasic<?> QuantumProcessor = new ItemBasic<>(GTIRef.ID, "quantumprocessor", "circuits/").tip("An extreme Circuit");
    public static ItemBasic<?> NanoprocessorAssembly = new ItemBasic<>(GTIRef.ID, "nanoprocessor_assembly", "circuits/").tip("An extreme Circuit");
    public static ItemBasic<?> Mainframe = new ItemBasic<>(GTIRef.ID, "mainframe", "circuits/").tip("An elite circuit");
    public static ItemBasic<?> QuantumProcessorAssembly = new ItemBasic<>(GTIRef.ID, "quantumprocessor_assembly", "circuits/").tip("An elite circuit");
    public static ItemBasic<?> CrystalProcessor = new ItemBasic<>(GTIRef.ID, "crystal_processor", "circuits/").tip("An elite Circuit");
    public static ItemBasic<?> CircuitWetware = new ItemBasic<>(GTIRef.ID, "wetware_circuit", "circuits/").tip("You feel like it's watching you");
    public static ItemBasic<?> SmallCoil = new ItemBasic<>(GTIRef.ID, "small_coil").tip("A part for circuits");
    public static ItemBasic<?> PetriDish = new ItemBasic<>(GTIRef.ID, "petri_dish");
    public static ItemBasic<?> SiliconBoule = new ItemBasic<>(GTIRef.ID, "monocrystalline_silicon_boule", "silicon/");
    public static ItemBasic<?> GlowstoneDopedSiliconBoule = new ItemBasic<>(GTIRef.ID, "glowstone_doped_monocrystalline_silicon_boule", "silicon/");
    public static ItemBasic<?> NaquadahDopedSiliconBoule = new ItemBasic<>(GTIRef.ID, "naquadah_doped_monocrystalline_silicon_boule", "silicon/");
    public static ItemBasic<?> Wafer = new ItemBasic<>(GTIRef.ID, "wafer", "silicon/");
    public static ItemBasic<?> SiliconChip = new ItemBasic<>(GTIRef.ID, "silicon_chip", "silicon/");
    public static ItemBasic<?> GlowstoneDopedWafer = new ItemBasic<>(GTIRef.ID, "glowstone_doped_wafer", "silicon/");
    public static ItemBasic<?> NaquadahDopedWafer = new ItemBasic<>(GTIRef.ID, "naquadah_doped_wafer", "silicon/");
    public static ItemBasic<?> ASoCWafer = new ItemBasic<>(GTIRef.ID, "asoc_wafer", "silicon/");
    public static ItemBasic<?> ASoC = new ItemBasic<>(GTIRef.ID, "asoc", "silicon/");
    public static ItemBasic<?> CentralProcessingUnitWafer = new ItemBasic<>(GTIRef.ID, "central_processing_unit_wafer", "silicon/");
    public static ItemBasic<?> CentralProcessingUnit = new ItemBasic<>(GTIRef.ID, "central_processing_unit", "silicon/");
    public static ItemBasic<?> HPICWafer = new ItemBasic<>(GTIRef.ID, "hpic_wafer", "silicon/");
    public static ItemBasic<?> HighPowerIC = new ItemBasic<>(GTIRef.ID, "high_power_ic", "silicon/");
    public static ItemBasic<?> IntegratedLogicCircuitWafer = new ItemBasic<>(GTIRef.ID, "integrated_logic_circuit_wafer", "silicon/");
    public static ItemBasic<?> IntegratedLogicCircuit = new ItemBasic<>(GTIRef.ID, "integrated_logic_circuit", "silicon/");
    public static ItemBasic<?> NANDMemoryChipWafer = new ItemBasic<>(GTIRef.ID, "nand_memory_chip_wafer", "silicon/");
    public static ItemBasic<?> NANDMemoryChip = new ItemBasic<>(GTIRef.ID, "nand_memory_chip", "silicon/");
    public static ItemBasic<?> NanoCpuWafer = new ItemBasic<>(GTIRef.ID, "nano_cpu_wafer", "silicon/");
    public static ItemBasic<?> NanoCpu = new ItemBasic<>(GTIRef.ID, "nanocomponent_central_processing_unit", "silicon/");
    public static ItemBasic<?> NorMemoryChipWafer = new ItemBasic<>(GTIRef.ID, "nor_memory_chip_wafer", "silicon/");
    public static ItemBasic<?> NorMemoryChip = new ItemBasic<>(GTIRef.ID, "nor_memory_chip", "silicon/");
    public static ItemBasic<?> PICWafer = new ItemBasic<>(GTIRef.ID, "pic_wafer", "silicon/");
    public static ItemBasic<?> PowerIC = new ItemBasic<>(GTIRef.ID, "power_ic", "silicon/");
    public static ItemBasic<?> QBitWafer = new ItemBasic<>(GTIRef.ID, "qbit_wafer", "silicon/");
    public static ItemBasic<?> QBitProcessingUnit = new ItemBasic<>(GTIRef.ID, "qbit_processing_unit", "silicon/");
    public static ItemBasic<?> RandomAccessMemoryChipWafer = new ItemBasic<>(GTIRef.ID, "random_access_memory_chip_wafer", "silicon/");
    public static ItemBasic<?> RandomAccessMemoryChip = new ItemBasic<>(GTIRef.ID, "random_access_memory_chip", "silicon/");
    public static ItemBasic<?> SOCWafer = new ItemBasic<>(GTIRef.ID, "soc_wafer", "silicon/");
    public static ItemBasic<?> SOC = new ItemBasic<>(GTIRef.ID, "soc", "silicon/");
    public static ItemBasic<?> BatteryTantalum = new ItemBattery(GTIRef.ID, "tantalum_capacitor", Tier.ULV, 10000, true).tip("Reusable");
    public static ItemComponentRod EmptyNuclearFuelRod = new ItemComponentRod(GTIRef.ID, "empty_nuclear_fuel_rod", muramasa.antimatter.material.Material.NULL, 1);
    public static ItemComponentRod NeutronAbsorberRod = new ItemComponentRod(GTIRef.ID, "neutron_absorber_rod", CdInAGAlloy, 1);
    public static ItemComponentRod NeutronReflectorRod = new ItemComponentRod(GTIRef.ID, "neutron_reflector_rod", Beryllium, 1);
    public static ItemComponentRod NeutronModeratorRod = new ItemComponentRod(GTIRef.ID, "neutron_moderator_rod", Graphite, 3);
    public static ItemNuclearFuelRod Thorium232Rod = new ItemNuclearFuelRod(GTIRef.ID, Thorium, 6_000_000_000L, 2,2,128, 32);
    public static ItemNuclearFuelRod Uranium238Rod = new ItemNuclearFuelRod(GTIRef.ID, Uranium, 3_000_000_000L, 4,4,512, 16);
    public static ItemNuclearFuelRod Uranium235Rod = new ItemNuclearFuelRod(GTIRef.ID, Uranium235, 600_000_000, 32,32,2048, 4);
    public static ItemNuclearFuelRod Uranium233Rod = new ItemNuclearFuelRod(GTIRef.ID, Uranium233, 3_000_000_000L, 32,32,2048, 4);
    public static ItemNuclearFuelRod Plutonium244Rod = new ItemNuclearFuelRod(GTIRef.ID, Plutonium, 600_000_000, 64,64,2048, 4);
    public static ItemNuclearFuelRod Plutonium241Rod = new ItemNuclearFuelRod(GTIRef.ID, Plutonium241, 600_000_000, 128,128,3072, 3);
    public static ItemNuclearFuelRod Plutonium243Rod = new ItemNuclearFuelRod(GTIRef.ID, Plutonium243, 600_000_000, 128,128,4096, 3);
    public static ItemNuclearFuelRod Plutonium239Rod = new ItemNuclearFuelRod(GTIRef.ID, Plutonium239, 1_200_000_000, 128,128,4096, 3);
    public static ItemNuclearFuelRod Americium245Rod = new ItemNuclearFuelRod(GTIRef.ID, Americium, 600_000_000, 64,64,4096, 4);
    public static ItemNuclearFuelRod Americium241Rod = new ItemNuclearFuelRod(GTIRef.ID, Americium241, 600_000_000, 128,128,4096, 3);
    public static ItemNuclearFuelRod Cobalt60Rod = new ItemNuclearFuelRod(GTIRef.ID, Cobalt60, 60_000_000, 0,8,256, 16);
    public static ItemNuclearFuelRod EnrichedNaquadahRod = new ItemNuclearFuelRod(GTIRef.ID, EnrichedNaquadah, 6_000_000_000L, 128,128,8192, 4);
    public static ItemNuclearFuelRod NaquadriaRod = new ItemNuclearFuelRod(GTIRef.ID, Naquadria, 6_000_000_000L, 512,512,16384, 3);
    public static ItemDepletedRod DepletedThorium232Rod = new ItemDepletedRod(GTIRef.ID, Thorium);
    public static ItemDepletedRod DepletedUranium238Rod = new ItemDepletedRod(GTIRef.ID, Uranium);
    public static ItemDepletedRod DepletedUranium235Rod = new ItemDepletedRod(GTIRef.ID, Uranium235);
    public static ItemDepletedRod DepletedUranium233Rod = new ItemDepletedRod(GTIRef.ID, Uranium233);
    public static ItemDepletedRod DepletedPlutonium244Rod = new ItemDepletedRod(GTIRef.ID, Plutonium);
    public static ItemDepletedRod DepletedPlutonium241Rod = new ItemDepletedRod(GTIRef.ID, Plutonium241);
    public static ItemDepletedRod DepletedPlutonium243Rod = new ItemDepletedRod(GTIRef.ID, Plutonium243);
    public static ItemDepletedRod DepletedPlutonium239Rod = new ItemDepletedRod(GTIRef.ID, Plutonium239);
    public static ItemDepletedRod DepletedAmericium245Rod = new ItemDepletedRod(GTIRef.ID, Americium);
    public static ItemDepletedRod DepletedAmericium241Rod = new ItemDepletedRod(GTIRef.ID, Americium241);
    public static ItemDepletedRod DepletedCobalt60Rod = new ItemDepletedRod(GTIRef.ID, Cobalt60);
    public static ItemDepletedRod DepletedEnrichedNaquadahRod = new ItemDepletedRod(GTIRef.ID, EnrichedNaquadah);
    public static ItemDepletedRod DepletedNaquadriaRod = new ItemDepletedRod(GTIRef.ID, Naquadria);
    public static ItemBreederRod Thorium232BreederRod = new ItemBreederRod(GTIRef.ID, Thorium, () -> GregTech.get(Item.class, "uranium_233_enriched_rod"), 1000, 64_000_000);
    public static ItemEnrichedRod Uranium233EnrichedRod = new ItemEnrichedRod(GTIRef.ID, Uranium233, () -> Thorium232BreederRod);
    public static ItemBreederRod Uranium238BreederRod = new ItemBreederRod(GTIRef.ID, Uranium, () -> GregTech.get(Item.class, "plutonium_239_enriched_rod"), 2500, 256_000_000);
    public static ItemEnrichedRod Plutonium239EnrichedRod = new ItemEnrichedRod(GTIRef.ID, Plutonium239, () -> Uranium238BreederRod);
    public static ItemBreederRod LithiumBreederRod = new ItemBreederRod(GTIRef.ID, Lithium, () -> GregTech.get(Item.class, "tritium_enriched_rod"), 250, 640_000);
    public static ItemEnrichedRod TritiumEnrichedRod = new ItemEnrichedRod(GTIRef.ID, Tritium, () -> LithiumBreederRod);
    public static ItemBreederRod NaquadahBreederRod = new ItemBreederRod(GTIRef.ID, Naquadah, () -> GregTech.get(Item.class, "enriched_naquadah_enriched_rod"), 10000, 4_096_000_000L);
    public static ItemEnrichedRod EnrichedNaquadahEnrichedRod = new ItemEnrichedRod(GTIRef.ID, EnrichedNaquadah, () -> NaquadahBreederRod);

    public static void init(){
    }
}
