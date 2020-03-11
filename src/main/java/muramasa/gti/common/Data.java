package muramasa.gti.common;

import muramasa.antimatter.Configs;
import muramasa.antimatter.blocks.BlockCasing;
import muramasa.antimatter.blocks.BlockCasingMachine;
import muramasa.antimatter.blocks.BlockCoil;
import muramasa.antimatter.blocks.pipe.BlockCable.BlockCableBuilder;
import muramasa.antimatter.blocks.pipe.BlockFluidPipe.BlockFluidPipeBuilder;
import muramasa.antimatter.blocks.pipe.BlockItemPipe.BlockItemPipeBuilder;
import muramasa.antimatter.cover.Cover;
import muramasa.antimatter.items.ItemBasic;
import muramasa.antimatter.machines.Tier;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.texture.Texture;
import muramasa.gti.Ref;
import muramasa.gti.block.BlockFusionCasing;
import muramasa.gti.block.BlockTurbineCasing;
import muramasa.gti.cover.CoverConveyor;
import muramasa.gti.cover.CoverPlate;
import muramasa.gti.cover.CoverPump;
import muramasa.gti.cover.CoverSolar;
import muramasa.gti.data.Materials;
import muramasa.gti.tree.BlockRubberLeaves;
import muramasa.gti.tree.BlockRubberLog;
import muramasa.gti.tree.BlockRubberSapling;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;

import static muramasa.gti.data.Materials.*;

public class Data {

    private static boolean HC = Configs.GAMEPLAY.HARDCORE_CABLES;

    public static void init() {

    }

    public static final Cover COVER_PLATE = new CoverPlate();
    public static final Cover COVER_CONVEYOR = new CoverConveyor(Tier.ULV);
    public static final Cover COVER_PUMP = new CoverPump(Tier.ULV);
    public static final Cover COVER_SOLAR = new CoverSolar(Tier.ULV);

    public static ItemBasic StickyResin = new ItemBasic(Ref.ID, "sticky_resin");
    public static ItemBasic ComputerMonitor = new ItemBasic(Ref.ID, "computer_monitor", "Can be placed on machines as a cover");

    //public static ItemFluidCell CellTin = new ItemFluidCell(Tin, 1000);
    //public static ItemFluidCell CellSteel = new ItemFluidCell(Steel, 16000);
    //public static ItemFluidCell CellTungstensteel = new ItemFluidCell(TungstenSteel, 64000);

    public static ItemBasic ItemFilter = new ItemBasic(Ref.ID, "item_filter");
    public static ItemBasic DiamondSawBlade = new ItemBasic(Ref.ID, "diamond_saw_blade");
    public static ItemBasic DiamondGrindHead = new ItemBasic(Ref.ID, "diamond_grind_head");
    public static ItemBasic TungstenGrindHead = new ItemBasic(Ref.ID, "tungsten_grind_head");
    public static ItemBasic IridiumAlloyIngot = new ItemBasic(Ref.ID, "iridium_alloy_ingot", "Used to make Iridium Plates");
    public static ItemBasic IridiumReinforcedPlate = new ItemBasic(Ref.ID, "iridium_reinforced_plate", "GT2s Most Expensive Component");
    public static ItemBasic IridiumNeutronReflector = new ItemBasic(Ref.ID, "iridium_neutron_reflector", "Indestructible");
    public static ItemBasic QuantumEye = new ItemBasic(Ref.ID, "quantum_eye", "Improved Ender Eye");
    public static ItemBasic QuantumStat = new ItemBasic(Ref.ID, "quantum_star", "Improved Nether Star");
    public static ItemBasic GraviStar = new ItemBasic(Ref.ID, "gravi_star", "Ultimate Nether Star");

    public static ItemBasic MotorULV = new ItemBasic(Ref.ID, "motor_ulv");
    public static ItemBasic MotorLV = new ItemBasic(Ref.ID, "motor_lv");
    public static ItemBasic MotorMV = new ItemBasic(Ref.ID, "motor_mv");
    public static ItemBasic MotorHV = new ItemBasic(Ref.ID, "motor_hv");
    public static ItemBasic MotorEV = new ItemBasic(Ref.ID, "motor_ev");
    public static ItemBasic MotorIV = new ItemBasic(Ref.ID, "motor_iv");
    public static ItemBasic MotorLUV = new ItemBasic(Ref.ID, "motor_luv");
    public static ItemBasic MotorZPM = new ItemBasic(Ref.ID, "motor_zpm");
    public static ItemBasic MotorUV = new ItemBasic(Ref.ID, "motor_uv");
    public static ItemBasic MotorUHV = new ItemBasic(Ref.ID, "motor_uhv");
    public static ItemBasic MotorUEU = new ItemBasic(Ref.ID, "motor_ueu");

    public static ItemBasic PumpULV = new ItemBasic(Ref.ID, "pump_ulv", "160 L/s (as Cover)");
    public static ItemBasic PumpLV = new ItemBasic(Ref.ID, "pump_lv", "640 L/s (as Cover)");
    public static ItemBasic PumpMV = new ItemBasic(Ref.ID, "pump_mv", "2,560 L/s (as Cover)");
    public static ItemBasic PumpHV = new ItemBasic(Ref.ID, "pump_hv", "10,240 L/s (as Cover)");
    public static ItemBasic PumpEV = new ItemBasic(Ref.ID, "pump_ev", "40,960 L/s (as Cover)");
    public static ItemBasic PumpIV = new ItemBasic(Ref.ID, "pump_iv", "163,840 L/s (as Cover)");
    public static ItemBasic PumpLUV = new ItemBasic(Ref.ID, "pump_luv", "655,360 L/s (as Cover)");
    public static ItemBasic PumpZPM = new ItemBasic(Ref.ID, "pump_zpm", "2,621,440 L/s (as Cover)");
    public static ItemBasic PumpUV = new ItemBasic(Ref.ID, "pump_uv", "10,485,760 L/s (as Cover)");
    public static ItemBasic PumpUHV = new ItemBasic(Ref.ID, "pump_uhv", "41,943,040 L/s (as Cover)");
    public static ItemBasic PumpUEU = new ItemBasic(Ref.ID, "pump_ueu", "167,772,160 L/s (as Cover)");

    public static ItemBasic Solar = new ItemBasic(Ref.ID, "solar", "1 EU/t (as Cover)");
    public static ItemBasic SolarULV = new ItemBasic(Ref.ID, "solar_ulv", "8 EU/t (as Cover)");
    public static ItemBasic SolarLV = new ItemBasic(Ref.ID, "solar_lv", "32 EU/t (as Cover)");
    public static ItemBasic SolarMV = new ItemBasic(Ref.ID, "solar_mv", "128 EU/t (as Cover)");
    public static ItemBasic SolarHV = new ItemBasic(Ref.ID, "solar_hv", "512 EU/t (as Cover)");
    public static ItemBasic SolarEV = new ItemBasic(Ref.ID, "solar_ev", "2048 EU/t (as Cover)");
    public static ItemBasic SolarIV = new ItemBasic(Ref.ID, "solar_iv", "8192 EU/t (as Cover)");
    public static ItemBasic SolarLUV = new ItemBasic(Ref.ID, "solar_luv", "32,768 EU/t (as Cover)");
    public static ItemBasic SolarZPM = new ItemBasic(Ref.ID, "solar_zpm", "131,072 EU/t (as Cover)");
    public static ItemBasic SolarUV = new ItemBasic(Ref.ID, "solar_uv", "524,288 EU/t (as Cover)");
    public static ItemBasic SolarUHV = new ItemBasic(Ref.ID, "solar_uhv", "2,097,152 EU/t (as Cover)");
    public static ItemBasic SolarUEU = new ItemBasic(Ref.ID, "solar_ueu", "8,388,608 EU/t (as Cover)");

    public static ItemBasic FluidRegulatorULV = new ItemBasic(Ref.ID, "fluid_regulator_ulv", "Configurable up to 160 L/s (as Cover)");
    public static ItemBasic FluidRegulatorLV = new ItemBasic(Ref.ID, "fluid_regulator_lv", "Configurable up to 640 L/s (as Cover)");
    public static ItemBasic FluidRegulatorMV = new ItemBasic(Ref.ID, "fluid_regulator_mv", "Configurable up to 2,560 L/s (as Cover)");
    public static ItemBasic FluidRegulatorHV = new ItemBasic(Ref.ID, "fluid_regulator_hv", "Configurable up to 10,240 L/s (as Cover)");
    public static ItemBasic FluidRegulatorEV = new ItemBasic(Ref.ID, "fluid_regulator_ev", "Configurable up to 40,960 L/s (as Cover)");
    public static ItemBasic FluidRegulatorIV = new ItemBasic(Ref.ID, "fluid_regulator_iv", "Configurable up to 163,840 L/s (as Cover)");
    public static ItemBasic FluidRegulatorLUV = new ItemBasic(Ref.ID, "fluid_regulator_luv", "Configurable up to 655,360 L/s (as Cover)");
    public static ItemBasic FluidRegulatorZPM = new ItemBasic(Ref.ID, "fluid_regulator_zpm", "Configurable up to 2,621,440 L/s (as Cover)");
    public static ItemBasic FluidRegulatorUV = new ItemBasic(Ref.ID, "fluid_regulator_uv", "Configurable up to 10,485,760 L/s (as Cover)");
    public static ItemBasic FluidRegulatorUHV = new ItemBasic(Ref.ID, "fluid_regulator_uhv", "Configurable up to 41,943,040 L/s (as Cover)");
    public static ItemBasic FluidRegulatorUEU = new ItemBasic(Ref.ID, "fluid_regulator_ueu", "Configurable up to 167,772,160 L/s (as Cover)");

    public static ItemBasic ConveyorULV = new ItemBasic(Ref.ID, "conveyor_ulv", "1 Stack every 80s (as Cover)");
    public static ItemBasic ConveyorLV = new ItemBasic(Ref.ID, "conveyor_lv", "1 Stack every 20s (as Cover)");
    public static ItemBasic ConveyorMV = new ItemBasic(Ref.ID, "conveyor_mv", "1 Stack every 5s (as Cover)");
    public static ItemBasic ConveyorHV = new ItemBasic(Ref.ID, "conveyor_hv", "1 Stack every 1s (as Cover)");
    public static ItemBasic ConveyorEV = new ItemBasic(Ref.ID, "conveyor_ev", "1 Stack every 0.5s (as Cover)");
    public static ItemBasic ConveyorIV = new ItemBasic(Ref.ID, "conveyor_iv", "1 Stack every 0.05s (as Cover)");
    public static ItemBasic ConveyorLUV = new ItemBasic(Ref.ID, "conveyor_luv", "1 Stack every 0.05s (as Cover)");
    public static ItemBasic ConveyorZPM = new ItemBasic(Ref.ID, "conveyor_zpm", "4 Stack every 0.05s (as Cover)");
    public static ItemBasic ConveyorUV = new ItemBasic(Ref.ID, "conveyor_uv", "8 Stack every 0.05s (as Cover)");
    public static ItemBasic ConveyorUHV = new ItemBasic(Ref.ID, "conveyor_uhv", "16 Stack every 0.05s (as Cover)");
    public static ItemBasic ConveyorUEU = new ItemBasic(Ref.ID, "conveyor_ueu", "32 Stack every 0.05s (as Cover)");

    public static ItemBasic PistonULV = new ItemBasic(Ref.ID, "piston_ulv");
    public static ItemBasic PistonLV = new ItemBasic(Ref.ID, "piston_lv");
    public static ItemBasic PistonMV = new ItemBasic(Ref.ID, "piston_mv");
    public static ItemBasic PistonHV = new ItemBasic(Ref.ID, "piston_hv");
    public static ItemBasic PistonEV = new ItemBasic(Ref.ID, "piston_ev");
    public static ItemBasic PistonIV = new ItemBasic(Ref.ID, "piston_iv");
    public static ItemBasic PistonLUV = new ItemBasic(Ref.ID, "piston_luv");
    public static ItemBasic PistonZPM = new ItemBasic(Ref.ID, "piston_zpm");
    public static ItemBasic PistonUV = new ItemBasic(Ref.ID, "piston_uv");
    public static ItemBasic PistonUHV = new ItemBasic(Ref.ID, "piston_uhv");
    public static ItemBasic PistonUEU = new ItemBasic(Ref.ID, "piston_ueu");

    public static ItemBasic RobotArmULV = new ItemBasic(Ref.ID, "robot_arm_ulv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmLV = new ItemBasic(Ref.ID, "robot_arm_lv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmMV = new ItemBasic(Ref.ID, "robot_arm_mv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmHV = new ItemBasic(Ref.ID, "robot_arm_hv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmEV = new ItemBasic(Ref.ID, "robot_arm_ev", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmIV = new ItemBasic(Ref.ID, "robot_arm_iv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmLUV = new ItemBasic(Ref.ID, "robot_arm_luv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmZPM = new ItemBasic(Ref.ID, "robot_arm_zpm", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmUV = new ItemBasic(Ref.ID, "robot_arm_uv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmUHV = new ItemBasic(Ref.ID, "robot_arm_uhv", "Insets into specific Slots (as Cover)");
    public static ItemBasic RobotArmUEU = new ItemBasic(Ref.ID, "robot_arm_ueu", "Insets into specific Slots (as Cover)");

    public static ItemBasic FieldGenLV = new ItemBasic(Ref.ID, "field_gen_lv");
    public static ItemBasic FieldGenMV = new ItemBasic(Ref.ID, "field_gen_mv");
    public static ItemBasic FieldGenHV = new ItemBasic(Ref.ID, "field_gen_hv");
    public static ItemBasic FieldGenEV = new ItemBasic(Ref.ID, "field_gen_ev");
    public static ItemBasic FieldGenIV = new ItemBasic(Ref.ID, "field_gen_iv");
    public static ItemBasic FieldGenLUV = new ItemBasic(Ref.ID, "field_gen_luv");
    public static ItemBasic FieldGenZPM = new ItemBasic(Ref.ID, "field_gen_zpm");
    public static ItemBasic FieldGenUV = new ItemBasic(Ref.ID, "field_gen_uv");
    public static ItemBasic FieldGenUHV = new ItemBasic(Ref.ID, "field_gen_uhv");
    public static ItemBasic FieldGenUEU = new ItemBasic(Ref.ID, "field_gen_iv");

    public static ItemBasic EmitterLV = new ItemBasic(Ref.ID, "emitter_lv");
    public static ItemBasic EmitterMV = new ItemBasic(Ref.ID, "emitter_mv");
    public static ItemBasic EmitterHV = new ItemBasic(Ref.ID, "emitter_hv");
    public static ItemBasic EmitterEV = new ItemBasic(Ref.ID, "emitter_ev");
    public static ItemBasic EmitterIV = new ItemBasic(Ref.ID, "emitter_iv");
    public static ItemBasic EmitterLUV = new ItemBasic(Ref.ID, "emitter_luv");
    public static ItemBasic EmitterZPM = new ItemBasic(Ref.ID, "emitter_zpm");
    public static ItemBasic EmitterUV = new ItemBasic(Ref.ID, "emitter_uv");
    public static ItemBasic EmitterUHV = new ItemBasic(Ref.ID, "emitter_uhv");
    public static ItemBasic EmitterUEU = new ItemBasic(Ref.ID, "emitter_ueu");

    public static ItemBasic SensorLV = new ItemBasic(Ref.ID, "sensor_lv");
    public static ItemBasic SensorMV = new ItemBasic(Ref.ID, "sensor_mv");
    public static ItemBasic SensorHV = new ItemBasic(Ref.ID, "sensor_hv");
    public static ItemBasic SensorEV = new ItemBasic(Ref.ID, "sensor_ev");
    public static ItemBasic SensorIV = new ItemBasic(Ref.ID, "sensor_iv");
    public static ItemBasic SensorLUV = new ItemBasic(Ref.ID, "sensor_luv");
    public static ItemBasic SensorZPM = new ItemBasic(Ref.ID, "sensor_zpm");
    public static ItemBasic SensorUV = new ItemBasic(Ref.ID, "sensor_uv");
    public static ItemBasic SensorUHV = new ItemBasic(Ref.ID, "sensor_uhv");
    public static ItemBasic SensorUEU = new ItemBasic(Ref.ID, "sensor_ueu");

    public static ItemBasic NandChip = new ItemBasic(Ref.ID, "nand_chip", "A very simple circuit");
    public static ItemBasic AdvCircuitParts = new ItemBasic(Ref.ID, "adv_circuit_parts", "Used for making Advanced Circuits");
    public static ItemBasic EtchedWiringMV = new ItemBasic(Ref.ID, "etched_wiring_mv", "Circuit board parts");
    public static ItemBasic EtchedWiringHV = new ItemBasic(Ref.ID, "etched_wiring_hv", "Circuit board parts");
    public static ItemBasic EtchedWiringEV = new ItemBasic(Ref.ID, "etched_wiring_ev", "Circuit board parts");
    public static ItemBasic EngravedCrystalChip = new ItemBasic(Ref.ID, "engraved_crystal_chip", "Needed for Circuits");
    public static ItemBasic EngravedLapotronChip = new ItemBasic(Ref.ID, "engraved_lapotron_chip", "Needed for Circuits");
    public static ItemBasic CurcuitVacuumTube = new ItemBasic(Ref.ID, "circuit_vacuum_tube", "A very simple vacuum tube");
    public static ItemBasic CircuitBoardEmpty = new ItemBasic(Ref.ID, "circuit_board_empty", "A board Part");
    public static ItemBasic CircuitBoardBasic = new ItemBasic(Ref.ID, "circuit_board_basic", "A basic Board");
    public static ItemBasic CircuitBoardAdv = new ItemBasic(Ref.ID, "circuit_board_adv", "An advanced Board");
    public static ItemBasic CircuitBoardProcessorEmpty = new ItemBasic(Ref.ID, "circuit_board_processor_empty", "A Processor Board Part");
    public static ItemBasic CircuitBoardProcessor = new ItemBasic(Ref.ID, "circuit_board_processor", "A Processor Board");
    public static ItemBasic CircuitBasic = new ItemBasic(Ref.ID, "circuit_basic", "A basic Circuit");
    public static ItemBasic CircuitGood = new ItemBasic(Ref.ID, "circuit_good", "A good Circuit");
    public static ItemBasic CircuitAdv = new ItemBasic(Ref.ID, "circuit_adv", "An advanced Circuit");
    public static ItemBasic CircuitDataStorage = new ItemBasic(Ref.ID, "circuit_data_storage", "A Data Storage Chip");
    public static ItemBasic CircuitDataControl = new ItemBasic(Ref.ID, "circuit_data_control", "A Data Control Processor");
    public static ItemBasic CircuitEnergyFlow = new ItemBasic(Ref.ID, "circuit_energy_flow", "A High Voltage Processor");
    public static ItemBasic CircuitDataOrb = new ItemBasic(Ref.ID, "circuit_data_orb", "A High Capacity Data Storage");
    public static ItemBasic DataStick = new ItemBasic(Ref.ID, "data_stick", "A Low Capacity Data Storage");

    public static ItemBasic BatteryTantalum = new ItemBasic(Ref.ID, "battery_tantalum", "Reusable");
    public static ItemBasic BatteryHullSmall = new ItemBasic(Ref.ID, "battery_hull_small", "An empty LV Battery Hull");
    public static ItemBasic BatteryHullMedium = new ItemBasic(Ref.ID, "battery_hull_medium", "An empty MV Battery Hull");
    public static ItemBasic BatteryHullLarge = new ItemBasic(Ref.ID, "battery_hull_large", "An empty HV Battery Hull");
    public static ItemBasic BatterySmallAcid = new ItemBasic(Ref.ID, "battery_small_acid", "Single Use");
    public static ItemBasic BatterySmallMercury = new ItemBasic(Ref.ID, "battery_small_mercury", "Single Use");
    public static ItemBasic BatterySmallCadmium = new ItemBasic(Ref.ID, "battery_small_cadmium", "Reusable");
    public static ItemBasic BatterySmallLithium = new ItemBasic(Ref.ID, "battery_small_lithium", "Reusable");
    public static ItemBasic BatterySmallSodium = new ItemBasic(Ref.ID, "battery_small_sodium", "Reusable");
    public static ItemBasic BatteryMediumAcid = new ItemBasic(Ref.ID, "battery_medium_acid", "Single Use");
    public static ItemBasic BatteryMediumMercury = new ItemBasic(Ref.ID, "battery_medium_mercury", "Single Use");
    public static ItemBasic BatteryMediumCadmium = new ItemBasic(Ref.ID, "battery_medium_cadmium", "Reusable");
    public static ItemBasic BatteryMediumLithium = new ItemBasic(Ref.ID, "battery_medium_lithium", "Reusable");
    public static ItemBasic BatteryMediumSodium = new ItemBasic(Ref.ID, "battery_medium_sodium", "Reusable");
    public static ItemBasic BatteryLargeAcid = new ItemBasic(Ref.ID, "battery_large_acid", "Single Use");
    public static ItemBasic BatteryLargeMercury = new ItemBasic(Ref.ID, "battery_large_mercury", "Single Use");
    public static ItemBasic BatteryLargeCadmium = new ItemBasic(Ref.ID, "battery_large_cadmium", "Reusable");
    public static ItemBasic BatteryLargeLithium = new ItemBasic(Ref.ID, "battery_large_lithium", "Reusable");
    public static ItemBasic BatteryLargeSodium = new ItemBasic(Ref.ID, "battery_large_sodium", "Reusable");
    public static ItemBasic BatteryEnergyOrb = new ItemBasic(Ref.ID, "battery_energy_orb");
    public static ItemBasic BatteryEnergyOrbCluster = new ItemBasic(Ref.ID, "battery_energy_orb_cluster");

    public static ItemBasic EmptyShape = new ItemBasic(Ref.ID, "empty_shape_plate", "Raw plate to make Molds and Extruder Shapes");
    public static ItemBasic MoldPlate = new ItemBasic(Ref.ID, "mold_plate", "Mold for making Plates");
    public static ItemBasic MoldGear = new ItemBasic(Ref.ID, "mold_gear", "Mold for making Gears");
    public static ItemBasic MoldGearSmall = new ItemBasic(Ref.ID, "mold_small_gear", "Mold for making Small Gears");
    public static ItemBasic MoldCoinage = new ItemBasic(Ref.ID, "mold_coinage", "Secure Mold for making Coins (Don't lose it!)");
    public static ItemBasic MoldBottle = new ItemBasic(Ref.ID, "mold_bottle", "Mold for making Bottles");
    public static ItemBasic MoldIngot = new ItemBasic(Ref.ID, "mold_ingot", "Mold for making Ingots");
    public static ItemBasic MoldBall = new ItemBasic(Ref.ID, "mold_ball", "Mold for making Balls");
    public static ItemBasic MoldBlock = new ItemBasic(Ref.ID, "mold_block", "Mold for making Blocks");
    public static ItemBasic MoldNugget = new ItemBasic(Ref.ID, "mold_nugget", "Mold for making Nuggets");
    public static ItemBasic MoldAnvil = new ItemBasic(Ref.ID, "mold_anvil", "Mold for making Anvils");
    public static ItemBasic ShapePlate = new ItemBasic(Ref.ID, "shape_plate", "Shape for making Plates");
    public static ItemBasic ShapeRod = new ItemBasic(Ref.ID, "shape_rod", "Shape for making Rods");
    public static ItemBasic ShapeBolt = new ItemBasic(Ref.ID, "shape_bolt", "Shape for making Bolts");
    public static ItemBasic ShapeRing = new ItemBasic(Ref.ID, "shape_ring", "Shape for making Rings");
    public static ItemBasic ShapeCell = new ItemBasic(Ref.ID, "shape_cell", "Shape for making Cells");
    public static ItemBasic ShapeIngot = new ItemBasic(Ref.ID, "shape_ingot", "Shape for making Ingots");
    public static ItemBasic ShapeWire = new ItemBasic(Ref.ID, "shape_wire", "Shape for making Wires");
    public static ItemBasic ShapePipeTiny = new ItemBasic(Ref.ID, "shape_pipe_tiny", "Shape for making Tiny Pipes");
    public static ItemBasic ShapePipeSmall = new ItemBasic(Ref.ID, "shape_pipe_small", "Shape for making Small Pipes");
    public static ItemBasic ShapePipeNormal = new ItemBasic(Ref.ID, "shape_pipe_normal", "Shape for making Normal Pipes");
    public static ItemBasic ShapePipeLarge = new ItemBasic(Ref.ID, "shape_pipe_large", "Shape for making Large Pipes");
    public static ItemBasic ShapePipeHuge = new ItemBasic(Ref.ID, "shape_pipe_huge", "Shape for making Huge Pipes");
    public static ItemBasic ShapeBlock = new ItemBasic(Ref.ID, "shape_block", "Shape for making Blocks");
    public static ItemBasic ShapeHeadSword = new ItemBasic(Ref.ID, "shape_head_sword", "Shape for making Sword Blades");
    public static ItemBasic ShapeHeadPickaxe = new ItemBasic(Ref.ID, "shape_head_pickaxe", "Shape for making Pickaxe Heads");
    public static ItemBasic ShapeHeadShovel = new ItemBasic(Ref.ID, "shape_head_shovel", "Shape for making Shovel Heads");
    public static ItemBasic ShapeHeadAxe = new ItemBasic(Ref.ID, "shape_head_axe", "Shape for making Axe Heads");
    public static ItemBasic ShapeHeadHoe = new ItemBasic(Ref.ID, "shape_head_hoe", "Shape for making Hoe Heads");
    public static ItemBasic ShapeHeadHammer = new ItemBasic(Ref.ID, "shape_head_hammer", "Shape for making Hammer Heads");
    public static ItemBasic ShapeHeadFile = new ItemBasic(Ref.ID, "shape_head_file", "Shape for making File Heads");
    public static ItemBasic ShapeHeadSaw = new ItemBasic(Ref.ID, "shape_head_saw", "Shape for making Saw Heads");
    public static ItemBasic ShapeGear = new ItemBasic(Ref.ID, "shape_gear", "Shape for making Gears");
    public static ItemBasic ShapeGearSmall = new ItemBasic(Ref.ID, "shape_gear_small", "Shape for making Small Gears");
    public static ItemBasic ShapeBottle = new ItemBasic(Ref.ID, "shape_bottle", "Shape for making Bottles"); //TODO needed?

    //TODO optional items (register anyway, but don't show in JEI?)
    //TODO move to IC2+IC2C Registrar
    public static ItemBasic DropTin = new ItemBasic(Ref.ID, "drop_tin", "Source of Tin")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropLead = new ItemBasic(Ref.ID, "drop_lead", "Source of Lead")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropSilver = new ItemBasic(Ref.ID, "drop_silver", "Source of Silver")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropIron = new ItemBasic(Ref.ID, "drop_iron", "Source of Iron")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropGold = new ItemBasic(Ref.ID, "drop_gold", "Source of Gold")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropAluminium = new ItemBasic(Ref.ID, "drop_aluminium", "Source of Aluminium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropTitanium = new ItemBasic(Ref.ID, "drop_titanium", "Source of Titanium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropUranium = new ItemBasic(Ref.ID, "drop_uranium", "Source of Uranium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropUranite = new ItemBasic(Ref.ID, "drop_uranite", "Source of Uranite")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropThorium = new ItemBasic(Ref.ID, "drop_thorium", "Source of Thorium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropNickel = new ItemBasic(Ref.ID, "drop_nickel", "Source of Nickel")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropZinc = new ItemBasic(Ref.ID, "drop_zinc", "Source of Zinc")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropManganese = new ItemBasic(Ref.ID, "drop_manganese", "Source of Manganese")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropCopper = new ItemBasic(Ref.ID, "drop_copper", "Source of Copper")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropTungsten = new ItemBasic(Ref.ID, "drop_tungsten", "Source of Tungsten")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropPlatinum = new ItemBasic(Ref.ID, "drop_platinum", "Source of Platinum")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropIridium = new ItemBasic(Ref.ID, "drop_iridium", "Source of Iridium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropOsmium = new ItemBasic(Ref.ID, "drop_osmium", "Source of Osmium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropNaquadah = new ItemBasic(Ref.ID, "drop_naquadah", "Source of Naquadah")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropEmerald = new ItemBasic(Ref.ID, "drop_emerald", "Source of Emeralds")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropOil = new ItemBasic(Ref.ID, "drop_oil", "Source of Oil")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropUUM = new ItemBasic(Ref.ID, "drop_uum", "Source of UU Matter")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    public static ItemBasic DropUUA = new ItemBasic(Ref.ID, "drop_uua", "Source of UU Amplifier")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;

    //TODO move to Forestry Registrar
    public static ItemBasic CombLignite = new ItemBasic(Ref.ID, "comb_lignite", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombCoal = new ItemBasic(Ref.ID, "comb_coal", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombResin = new ItemBasic(Ref.ID, "comb_resin", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombOil = new ItemBasic(Ref.ID, "comb_oil", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombStone = new ItemBasic(Ref.ID, "comb_stone", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombCertus = new ItemBasic(Ref.ID, "comb_certus", "")/*.required(Ref.MOD_FR, Ref.MOD_AE)*/;
    public static ItemBasic CombRedstone = new ItemBasic(Ref.ID, "comb_redstone", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombLapis = new ItemBasic(Ref.ID, "comb_lapis", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombRuby = new ItemBasic(Ref.ID, "comb_ruby", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombSapphire = new ItemBasic(Ref.ID, "comb_sapphire", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombDiamond = new ItemBasic(Ref.ID, "comb_diamond", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombOlivine = new ItemBasic(Ref.ID, "comb_olivine", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombEmerald = new ItemBasic(Ref.ID, "comb_emerald", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombSlag = new ItemBasic(Ref.ID, "comb_slag", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombCopper = new ItemBasic(Ref.ID, "comb_copper", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombTin = new ItemBasic(Ref.ID, "comb_tin", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombLead = new ItemBasic(Ref.ID, "comb_lead", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombIron = new ItemBasic(Ref.ID, "comb_iron", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombSteel = new ItemBasic(Ref.ID, "comb_steel", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombNickel = new ItemBasic(Ref.ID, "comb_nickel", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombZinc = new ItemBasic(Ref.ID, "comb_zinc", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombSilver = new ItemBasic(Ref.ID, "comb_silver", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombGold = new ItemBasic(Ref.ID, "comb_gold", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombAluminium = new ItemBasic(Ref.ID, "comb_aluminium", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombManganese = new ItemBasic(Ref.ID, "comb_manganese", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombTitanium = new ItemBasic(Ref.ID, "comb_titanium", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombChrome = new ItemBasic(Ref.ID, "comb_chrome", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombTungsten = new ItemBasic(Ref.ID, "comb_tungsten", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombPlatinum = new ItemBasic(Ref.ID, "comb_platinum", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombIridium = new ItemBasic(Ref.ID, "comb_iridium", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombUranium = new ItemBasic(Ref.ID, "comb_uranium", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombPlutonium = new ItemBasic(Ref.ID, "comb_plutonium", "")/*.optional(Ref.MOD_FR)*/;
    public static ItemBasic CombNaquadah = new ItemBasic(Ref.ID, "comb_naquadah", "")/*.optional(Ref.MOD_FR)*/;

    //TODO
    //public static BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling();
    //public static BlockRubberLog RUBBER_LOG = new BlockRubberLog();
    //public static BlockLeavesBase RUBBER_LEAVES = new BlockLeavesBase("rubber_leaves", RUBBER_SAPLING);

    //STONE should be the only non-removable StoneType. It serves as the foundation. It is also used natively by BlockRock
    //TODO move vanilla stone types to Antimatter
    public static StoneType STONE = new StoneType(Ref.ID, "stone", Materials.Stone, new Texture("minecraft", "block/stone"), SoundType.STONE, false).setState(Blocks.STONE);

    public static StoneType GRANITE = new StoneType(Ref.ID, "granite", Granite, new Texture("minecraft", "block/granite"), SoundType.STONE, !muramasa.antimatter.Configs.WORLD.DISABLE_VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.GRANITE);
    public static StoneType DIORITE = new StoneType(Ref.ID, "diorite", Diorite, new Texture("minecraft", "block/diorite"), SoundType.STONE, !muramasa.antimatter.Configs.WORLD.DISABLE_VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.DIORITE);
    public static StoneType ANDESITE = new StoneType(Ref.ID, "andesite", Andesite, new Texture("minecraft", "block/andesite"),  SoundType.STONE, !muramasa.antimatter.Configs.WORLD.DISABLE_VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.ANDESITE);

    public static StoneType GRAVEL = new StoneType(Ref.ID, "gravel", Gravel, new Texture("minecraft", "block/gravel"), SoundType.GROUND, false).setState(Blocks.GRAVEL);
    public static StoneType SAND = new StoneType(Ref.ID, "sand", Sand, new Texture("minecraft", "block/sand"), SoundType.SAND, false).setState(Blocks.SAND);
    public static StoneType SAND_RED = new StoneType(Ref.ID, "sand_red", SandRed, new Texture("minecraft", "block/red_sand"), SoundType.SAND, false).setState(Blocks.RED_SAND);
    public static StoneType SANDSTONE = new StoneType(Ref.ID, "sandstone", Sandstone, new Texture("minecraft", "block/sandstone"), SoundType.STONE, false).setState(Blocks.SANDSTONE);

    public static StoneType NETHERRACK = new StoneType(Ref.ID, "netherrack", Materials.Netherrack, new Texture("minecraft", "block/netherrack"), SoundType.STONE, false).setState(Blocks.NETHERRACK);
    public static StoneType ENDSTONE = new StoneType(Ref.ID, "endstone", Materials.Endstone, new Texture("minecraft", "block/end_stone"), SoundType.STONE, false).setState(Blocks.END_STONE);

    public static StoneType GRANITE_RED = new StoneType(Ref.ID, "granite_red", Materials.GraniteRed, new Texture(Ref.ID, "block/stone/granite_red"), SoundType.STONE, true);
    public static StoneType GRANITE_BLACK = new StoneType(Ref.ID, "granite_black", Materials.GraniteBlack, new Texture(Ref.ID, "block/stone/granite_black"), SoundType.STONE, true);
    public static StoneType MARBLE = new StoneType(Ref.ID, "marble", Materials.Marble, new Texture(Ref.ID, "block/stone/marble"), SoundType.STONE, true);
    public static StoneType BASALT = new StoneType(Ref.ID, "basalt", Materials.Basalt, new Texture(Ref.ID, "block/stone/basalt"), SoundType.STONE, true);

    public static StoneType KOMATIITE = new StoneType(Ref.ID, "komatiite", Materials.Komatiite, new Texture(Ref.ID, "block/stone/komatiite"), SoundType.STONE, true);
    public static StoneType LIMESTONE = new StoneType(Ref.ID, "limestone", Limestone, new Texture(Ref.ID, "block/stone/limestone"), SoundType.STONE, true);
    public static StoneType GREEN_SCHIST = new StoneType(Ref.ID, "green_schist", GreenSchist, new Texture(Ref.ID, "block/stone/green_schist"), SoundType.STONE, true);
    public static StoneType BLUE_SCHIST = new StoneType(Ref.ID, "blue_schist", BlueSchist, new Texture(Ref.ID, "block/stone/blue_schist"), SoundType.STONE, true);
    public static StoneType KIMBERLITE = new StoneType(Ref.ID, "kimberlite", Kimberlite, new Texture(Ref.ID, "block/stone/kimberlite"), SoundType.STONE, true);
    public static StoneType QUARTZITE = new StoneType(Ref.ID, "quartzite", Quartzite, new Texture(Ref.ID, "block/stone/quartzite"), SoundType.STONE, true);

    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(Ref.ID, "anthracite_coal", new Texture(Ref.ID, "block/basic/anthracite_coal"));
    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(Ref.ID, "anthracite_coal", new Texture(Ref.ID, "block/basic/anthracite_coal"));

//    public static BlockStone STONE_GRANITE_RED = new BlockStone(Ref.ID, GRANITE_RED);
//    public static BlockStone STONE_GRANITE_BLACK = new BlockStone(Ref.ID, GRANITE_BLACK);
//    public static BlockStone STONE_MARBLE = new BlockStone(Ref.ID, MARBLE);
//    public static BlockStone STONE_BASALT = new BlockStone(Ref.ID, BASALT);
//
//    public static BlockStone STONE_KOMATIITE = new BlockStone(Ref.ID, KOMATIITE);
//    public static BlockStone STONE_LIMESTONE = new BlockStone(Ref.ID, LIMESTONE);
//    public static BlockStone STONE_GREEN_SCHIST = new BlockStone(Ref.ID, GREEN_SCHIST);
//    public static BlockStone STONE_BLUE_SCHIST = new BlockStone(Ref.ID, BLUE_SCHIST);
//    public static BlockStone STONE_KIMBERLITE = new BlockStone(Ref.ID, KIMBERLITE);
//    public static BlockStone STONE_QUARTZITE = new BlockStone(Ref.ID, QUARTZITE);

    public static BlockCasingMachine CASING_ULV = new BlockCasingMachine(Ref.ID, "ulv");
    public static BlockCasingMachine CASING_LV = new BlockCasingMachine(Ref.ID, "lv");
    public static BlockCasingMachine CASING_MV = new BlockCasingMachine(Ref.ID, "mv");
    public static BlockCasingMachine CASING_HV = new BlockCasingMachine(Ref.ID, "hv");
    public static BlockCasingMachine CASING_EV = new BlockCasingMachine(Ref.ID, "ev");
    public static BlockCasingMachine CASING_IV = new BlockCasingMachine(Ref.ID, "iv");
    public static BlockCasingMachine CASING_LUV = new BlockCasingMachine(Ref.ID, "luv");
    public static BlockCasingMachine CASING_ZPM = new BlockCasingMachine(Ref.ID, "zpm");
    public static BlockCasingMachine CASING_UV = new BlockCasingMachine(Ref.ID, "uv");
    public static BlockCasingMachine CASING_MAX = new BlockCasingMachine(Ref.ID, "max");

    public static BlockCasing CASING_FIRE_BRICK = new BlockCasing(Ref.ID, "fire_brick");
    public static BlockCasing CASING_BRONZE = new BlockCasing(Ref.ID, "bronze");
    public static BlockCasing CASING_BRICKED_BRONZE = new BlockCasing(Ref.ID, "bricked_bronze");
    public static BlockCasing CASING_BRONZE_PLATED_BRICK = new BlockCasing(Ref.ID, "bronze_plated_brick");
    public static BlockCasing CASING_STEEL = new BlockCasing(Ref.ID, "steel");
    public static BlockCasing CASING_BRICKED_STEEL = new BlockCasing(Ref.ID, "bricked_steel");
    public static BlockCasing CASING_SOLID_STEEL = new BlockCasing(Ref.ID, "solid_steel");
    public static BlockCasing CASING_STAINLESS_STEEL = new BlockCasing(Ref.ID, "stainless_steel");
    public static BlockCasing CASING_TITANIUM = new BlockCasing(Ref.ID, "titanium");
    public static BlockCasing CASING_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "tungstensteel");
    public static BlockCasing CASING_HEAT_PROOF = new BlockCasing(Ref.ID, "heat_proof");
    public static BlockCasing CASING_FROST_PROOF = new BlockCasing(Ref.ID, "frost_proof");
    public static BlockCasing CASING_RADIATION_PROOF = new BlockCasing(Ref.ID, "radiation_proof");
    public static BlockCasing CASING_FIREBOX_BRONZE = new BlockCasing(Ref.ID, "firebox_bronze");
    public static BlockCasing CASING_FIREBOX_STEEL = new BlockCasing(Ref.ID, "firebox_steel");
    public static BlockCasing CASING_FIREBOX_TITANIUM = new BlockCasing(Ref.ID, "firebox_titanium");
    public static BlockCasing CASING_FIREBOX_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "firebox_tungstensteel");
    public static BlockCasing CASING_GEARBOX_BRONZE = new BlockCasing(Ref.ID, "gearbox_bronze");
    public static BlockCasing CASING_GEARBOX_STEEL = new BlockCasing(Ref.ID, "gearbox_steel");
    public static BlockCasing CASING_GEARBOX_TITANIUM = new BlockCasing(Ref.ID, "gearbox_titanium");
    public static BlockCasing CASING_GEARBOX_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "gearbox_tungstensteel");
    public static BlockCasing CASING_PIPE_BRONZE = new BlockCasing(Ref.ID, "pipe_bronze");
    public static BlockCasing CASING_PIPE_STEEL = new BlockCasing(Ref.ID, "pipe_steel");
    public static BlockCasing CASING_PIPE_TITANIUM = new BlockCasing(Ref.ID, "pipe_titanium");
    public static BlockCasing CASING_PIPE_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "pipe_tungstensteel");
    public static BlockCasing CASING_ENGINE_INTAKE = new BlockCasing(Ref.ID, "engine_intake");

    public static BlockCasing CASING_FUSION_1 = new BlockFusionCasing(Ref.ID, "fusion_1");
    public static BlockCasing CASING_FUSION_2 = new BlockFusionCasing(Ref.ID, "fusion_2");
    public static BlockCasing CASING_FUSION_3 = new BlockFusionCasing(Ref.ID, "fusion_3");

    public static BlockCasing CASING_TURBINE_1 = new BlockTurbineCasing(Ref.ID, "turbine_1");
    public static BlockCasing CASING_TURBINE_2 = new BlockTurbineCasing(Ref.ID, "turbine_2");
    public static BlockCasing CASING_TURBINE_3 = new BlockTurbineCasing(Ref.ID, "turbine_3");
    public static BlockCasing CASING_TURBINE_4 = new BlockTurbineCasing(Ref.ID, "turbine_4");

    public static BlockCoil COIL_CUPRONICKEL = new BlockCoil(Ref.ID, "cupronickel", 113); //1808
    public static BlockCoil COIL_KANTHAL = new BlockCoil(Ref.ID, "kanthal", 169); //2704
    public static BlockCoil COIL_NICHROME = new BlockCoil(Ref.ID, "nichrome", 225); //3600
    public static BlockCoil COIL_TUNGSTENSTEEL = new BlockCoil(Ref.ID, "tungstensteel", 282); //4512
    public static BlockCoil COIL_HSSG = new BlockCoil(Ref.ID, "hssg", 338); //5408
    public static BlockCoil COIL_NAQUADAH = new BlockCoil(Ref.ID, "naquadah", 450); //7200
    public static BlockCoil COIL_NAQUADAH_ALLOY = new BlockCoil(Ref.ID, "naquadah_alloy", 563); //9008
    public static BlockCoil COIL_FUSION = new BlockCoil(Ref.ID, "fusion", 563); //9008
    public static BlockCoil COIL_SUPERCONDUCTOR = new BlockCoil(Ref.ID, "superconductor", 563); //9008

    static {
        new BlockCableBuilder(Ref.ID, RedAlloy, 0, 1, Tier.ULV).amps(1).build(); //ULV
        new BlockCableBuilder(Ref.ID, Cobalt, 2, 4, Tier.LV).amps(2).build(); //LV
        new BlockCableBuilder(Ref.ID, Lead, 2, 4, Tier.LV).amps(2).build();
        new BlockCableBuilder(Ref.ID, Tin, 1, 2, Tier.LV).amps(1).build();
        new BlockCableBuilder(Ref.ID, Zinc, 1, 2, Tier.LV).amps(1).build();
        new BlockCableBuilder(Ref.ID, SolderingAlloy, 1, 2, Tier.LV).amps(1).build();
        new BlockCableBuilder(Ref.ID, Iron, HC ? 3 : 4, HC ? 6 : 8, Tier.MV).amps(2).build(); //MV
        new BlockCableBuilder(Ref.ID, Nickel, HC ? 3 : 5, HC ? 6 : 10, Tier.MV).amps(3).build();
        new BlockCableBuilder(Ref.ID, Cupronickel, HC ? 3 : 4, HC ? 6 : 8, Tier.MV).amps(2).build();
        new BlockCableBuilder(Ref.ID, Copper, HC ? 2 : 3, HC ? 4 : 6, Tier.MV).amps(1).build();
        new BlockCableBuilder(Ref.ID, AnnealedCopper, HC ? 1 : 2, HC ? 2 : 4, Tier.MV).amps(1).build();
        new BlockCableBuilder(Ref.ID, Kanthal, HC ? 3 : 8, HC ? 6 : 16, Tier.HV).amps(4).build(); //HV
        new BlockCableBuilder(Ref.ID, Gold, HC ? 2 : 6, HC ? 4 : 12, Tier.HV).amps(3).build();
        new BlockCableBuilder(Ref.ID, Electrum, HC ? 2 : 5, HC ? 4 : 10, Tier.HV).amps(2).build();
        new BlockCableBuilder(Ref.ID, Silver, HC ? 1 : 4, HC ? 2 : 8, Tier.HV).amps(1).build();
        new BlockCableBuilder(Ref.ID, Nichrome, HC ? 4 : 32, HC ? 8 : 64, Tier.EV).amps(3).build(); //EV
        new BlockCableBuilder(Ref.ID, Steel, HC ? 2 : 16, HC ? 4 : 32, Tier.EV).amps(2).build();
        new BlockCableBuilder(Ref.ID, Titanium, HC ? 2 : 12, HC ? 4 : 24, Tier.EV).amps(4).build();
        new BlockCableBuilder(Ref.ID, Aluminium, HC ? 1 : 8, HC ? 2 : 16, Tier.EV).amps(1).build();
        new BlockCableBuilder(Ref.ID, Graphene, HC ? 1 : 16, HC ? 2 : 32, Tier.IV).amps(1).build(); //IV
        new BlockCableBuilder(Ref.ID, Osmium, HC ? 2 : 32, HC ? 4 : 64, Tier.IV).amps(4).build();
        new BlockCableBuilder(Ref.ID, Platinum, HC ? 1 : 16, HC ? 2 : 32, Tier.IV).amps(2).build();
        new BlockCableBuilder(Ref.ID, TungstenSteel, HC ? 1 : 14, HC ? 4 : 28, Tier.IV).amps(3).build();
        new BlockCableBuilder(Ref.ID, Tungsten, HC ? 2 : 12, HC ? 4 : 24, Tier.IV).amps(1).build();
        new BlockCableBuilder(Ref.ID, HSSG, HC ? 2 : 128, HC ? 4 : 256, Tier.LUV).amps(4).build(); //LUV
        new BlockCableBuilder(Ref.ID, NiobiumTitanium, HC ? 2 : 128, HC ? 4 : 256, Tier.LUV).amps(4).build();
        new BlockCableBuilder(Ref.ID, VanadiumGallium, HC ? 2 : 128, HC ? 4 : 256, Tier.LUV).amps(4).build();
        new BlockCableBuilder(Ref.ID, YttriumBariumCuprate, HC ? 4 : 256, HC ? 8 : 512, Tier.LUV).amps(4).build();
        new BlockCableBuilder(Ref.ID, Naquadah, HC ? 2 : 64, HC ? 4 : 128, Tier.ZPM).amps(2).build(); //ZPM
        new BlockCableBuilder(Ref.ID, NaquadahAlloy, HC ? 4 : 64, HC ? 8 : 128, Tier.ZPM).amps(2).build();
        new BlockCableBuilder(Ref.ID, Duranium, HC ? 8 : 64, HC ? 16 : 128, Tier.ZPM).amps(1).build();
        new BlockCableBuilder(Ref.ID, Superconductor, 1, 1, Tier.MAX).amps(4).build(); //MAX

        new BlockFluidPipeBuilder(Ref.ID, Wood, 30, false, PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(10, 10, 30, 60, 60, 60).build();
        new BlockFluidPipeBuilder(Ref.ID, Copper, 1000, true).caps(10).build();
        new BlockFluidPipeBuilder(Ref.ID, Bronze, 2000, true).caps(20).build();
        new BlockFluidPipeBuilder(Ref.ID, Steel, 2500, true).caps(40).build();
        new BlockFluidPipeBuilder(Ref.ID, StainlessSteel, 3000, true).caps(60).build();
        new BlockFluidPipeBuilder(Ref.ID, Titanium, 5000, true).caps(80).build();
        new BlockFluidPipeBuilder(Ref.ID, TungstenSteel, 7500, true).caps(100).build();
        new BlockFluidPipeBuilder(Ref.ID, Plastic, 250, true).caps(60).build();
        new BlockFluidPipeBuilder(Ref.ID, Polytetrafluoroethylene, 600, true).caps(480).build();
        new BlockFluidPipeBuilder(Ref.ID, HighPressure, 1500, true, PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(4800, 4800, 4800, 7200, 9600, 9600).build();
        new BlockFluidPipeBuilder(Ref.ID, PlasmaContainment, 100000, true, PipeSize.NORMAL).caps(240, 240, 240, 240, 240, 240).build();

        new BlockItemPipeBuilder(Ref.ID, Cupronickel).slots(1).steps(1).build();
        new BlockItemPipeBuilder(Ref.ID, CobaltBrass).slots(1).steps(1).build();
        new BlockItemPipeBuilder(Ref.ID, Brass).slots(1).steps(1).build();
        new BlockItemPipeBuilder(Ref.ID, Electrum).slots(2).steps(2).build();
        new BlockItemPipeBuilder(Ref.ID, RoseGold).slots(2).steps(2).build();
        new BlockItemPipeBuilder(Ref.ID, SterlingSilver).slots(2).steps(2).build();
        new BlockItemPipeBuilder(Ref.ID, Platinum).slots(4).steps(4).build();
        new BlockItemPipeBuilder(Ref.ID, Ultimet).slots(4).steps(4).build();
        new BlockItemPipeBuilder(Ref.ID, PolyvinylChloride).slots(4).steps(4).build();
        new BlockItemPipeBuilder(Ref.ID, Osmium).slots(8).steps(8).build();
    }

    // Rubber Tree
    public static BlockRubberLeaves RUBBER_LEAVES = new BlockRubberLeaves(Ref.ID);
    public static BlockRubberLog RUBBER_LOG = new BlockRubberLog(Ref.ID);
    public static BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling(Ref.ID);
}
