package muramasa.gti.data;

import muramasa.antimatter.AntimatterConfig;
import muramasa.antimatter.block.BlockCasing;
import muramasa.antimatter.block.BlockCoil;
import muramasa.antimatter.cover.Cover;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemCover;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.*;
import muramasa.antimatter.texture.Texture;
import muramasa.gti.Ref;
import muramasa.gti.block.BlockFusionCasing;
import muramasa.gti.block.BlockTurbineCasing;
import muramasa.gti.cover.CoverConveyor;
import muramasa.gti.cover.CoverPlate;
import muramasa.gti.cover.CoverPump;
import muramasa.gti.tree.BlockRubberLeaves;
import muramasa.gti.tree.BlockRubberLog;
import muramasa.gti.tree.BlockRubberSapling;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;

import static muramasa.gti.data.Materials.*;

public class Data {

    private static final boolean HC = AntimatterConfig.GAMEPLAY.HARDCORE_CABLES;

    public static void init() {

    }

    public static final Cover COVER_PLATE = new CoverPlate();
    public static final Cover COVER_PUMP = new CoverPump(Tier.LV);

    public static ItemBasic<?> StickyResin = new ItemBasic<>(Ref.ID, "sticky_resin");
    public static ItemBasic<?> ComputerMonitor = new ItemBasic<>(Ref.ID, "computer_monitor").tip("Can be placed on machines as a cover");

    //public static ItemFluidCell CellTin = new ItemFluidCell(Tin, 1000);
    //public static ItemFluidCell CellSteel = new ItemFluidCell(Steel, 16000);
    //public static ItemFluidCell CellTungstensteel = new ItemFluidCell(TungstenSteel, 64000);

    public static ItemBasic<?> ItemFilter = new ItemBasic<>(Ref.ID, "item_filter");
    public static ItemBasic<?> DiamondSawBlade = new ItemBasic<>(Ref.ID, "diamond_saw_blade");
    public static ItemBasic<?> DiamondGrindHead = new ItemBasic<>(Ref.ID, "diamond_grind_head");
    public static ItemBasic<?> TungstenGrindHead = new ItemBasic<>(Ref.ID, "tungsten_grind_head");
    public static ItemBasic<?> IridiumAlloyIngot = new ItemBasic<>(Ref.ID, "iridium_alloy_ingot").tip("Used to make Iridium Plates");
    public static ItemBasic<?> IridiumReinforcedPlate = new ItemBasic<>(Ref.ID, "iridium_reinforced_plate").tip("GT2s Most Expensive Component");
    public static ItemBasic<?> IridiumNeutronReflector = new ItemBasic<>(Ref.ID, "iridium_neutron_reflector").tip("Indestructible");
    public static ItemBasic<?> QuantumEye = new ItemBasic<>(Ref.ID, "quantum_eye").tip("Improved Ender Eye");
    public static ItemBasic<?> QuantumStat = new ItemBasic<>(Ref.ID, "quantum_star").tip("Improved Nether Star");
    public static ItemBasic<?> GraviStar = new ItemBasic<>(Ref.ID, "gravi_star").tip("Ultimate Nether Star");

    public static ItemBasic<?> MotorLV = new ItemBasic<>(Ref.ID, "motor_lv");
    public static ItemBasic<?> MotorMV = new ItemBasic<>(Ref.ID, "motor_mv");
    public static ItemBasic<?> MotorHV = new ItemBasic<>(Ref.ID, "motor_hv");
    public static ItemBasic<?> MotorEV = new ItemBasic<>(Ref.ID, "motor_ev");
    public static ItemBasic<?> MotorIV = new ItemBasic<>(Ref.ID, "motor_iv");
    public static ItemBasic<?> PumpLV = new ItemBasic<>(Ref.ID, "pump_lv").tip("640 L/s (as Cover)");
    public static ItemBasic<?> PumpMV = new ItemBasic<>(Ref.ID, "pump_mv").tip("2,560 L/s (as Cover)");
    public static ItemBasic<?> PumpHV = new ItemBasic<>(Ref.ID, "pump_hv").tip("10,240 L/s (as Cover)");
    public static ItemBasic<?> PumpEV = new ItemBasic<>(Ref.ID, "pump_ev").tip("40,960 L/s (as Cover)");
    public static ItemBasic<?> PumpIV = new ItemBasic<>(Ref.ID, "pump_iv").tip("163,840 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorLV = new ItemBasic<>(Ref.ID, "fluid_regulator_lv").tip("Configurable up to 640 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorMV = new ItemBasic<>(Ref.ID, "fluid_regulator_mv").tip("Configurable up to 2,560 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorHV = new ItemBasic<>(Ref.ID, "fluid_regulator_hv").tip("Configurable up to 10,240 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorEV = new ItemBasic<>(Ref.ID, "fluid_regulator_ev").tip("Configurable up to 40,960 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorIV = new ItemBasic<>(Ref.ID, "fluid_regulator_iv").tip("Configurable up to 163,840 L/s (as Cover)");
    public static  ItemCover  ConveyorLV = new  ItemCover(Ref.ID, "conveyor_lv",new CoverConveyor(Tier.LV)).tip("1 Stack every 20s (as Cover)");
    public static  ItemCover  ConveyorMV = new  ItemCover(Ref.ID, "conveyor_mv",new CoverConveyor(Tier.MV)).tip("1 Stack every 5s (as Cover)");
    public static  ItemCover  ConveyorHV = new ItemCover(Ref.ID, "conveyor_hv",new CoverConveyor(Tier.HV)).tip("1 Stack every 1s (as Cover)");
    public static  ItemCover  ConveyorEV = new  ItemCover(Ref.ID, "conveyor_ev",new CoverConveyor(Tier.EV)).tip("1 Stack every 0.5s (as Cover)");
    public static  ItemCover  ConveyorIV = new  ItemCover(Ref.ID, "conveyor_iv",new CoverConveyor(Tier.IV)).tip("1 Stack every 0.05s (as Cover)");
    public static ItemBasic<?> PistonLV = new ItemBasic<>(Ref.ID, "piston_lv");
    public static ItemBasic<?> PistonMV = new ItemBasic<>(Ref.ID, "piston_mv");
    public static ItemBasic<?> PistonHV = new ItemBasic<>(Ref.ID, "piston_hv");
    public static ItemBasic<?> PistonEV = new ItemBasic<>(Ref.ID, "piston_ev");
    public static ItemBasic<?> PistonIV = new ItemBasic<>(Ref.ID, "piston_iv");
    public static ItemBasic<?> RobotArmLV = new ItemBasic<>(Ref.ID, "robot_arm_lv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmMV = new ItemBasic<>(Ref.ID, "robot_arm_mv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmHV = new ItemBasic<>(Ref.ID, "robot_arm_hv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmEV = new ItemBasic<>(Ref.ID, "robot_arm_ev").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmIV = new ItemBasic<>(Ref.ID, "robot_arm_iv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> FieldGenLV = new ItemBasic<>(Ref.ID, "field_gen_lv");
    public static ItemBasic<?> FieldGenMV = new ItemBasic<>(Ref.ID, "field_gen_mv");
    public static ItemBasic<?> FieldGenHV = new ItemBasic<>(Ref.ID, "field_gen_hv");
    public static ItemBasic<?> FieldGenEV = new ItemBasic<>(Ref.ID, "field_gen_ev");
    public static ItemBasic<?> FieldGenIV = new ItemBasic<>(Ref.ID, "field_gen_iv");
    public static ItemBasic<?> EmitterLV = new ItemBasic<>(Ref.ID, "emitter_lv");
    public static ItemBasic<?> EmitterMV = new ItemBasic<>(Ref.ID, "emitter_mv");
    public static ItemBasic<?> EmitterHV = new ItemBasic<>(Ref.ID, "emitter_hv");
    public static ItemBasic<?> EmitterEV = new ItemBasic<>(Ref.ID, "emitter_ev");
    public static ItemBasic<?> EmitterIV = new ItemBasic<>(Ref.ID, "emitter_iv");
    public static ItemBasic<?> SensorLV = new ItemBasic<>(Ref.ID, "sensor_lv");
    public static ItemBasic<?> SensorMV = new ItemBasic<>(Ref.ID, "sensor_mv");
    public static ItemBasic<?> SensorHV = new ItemBasic<>(Ref.ID, "sensor_hv");
    public static ItemBasic<?> SensorEV = new ItemBasic<>(Ref.ID, "sensor_ev");
    public static ItemBasic<?> SensorIV = new ItemBasic<>(Ref.ID, "sensor_iv");

    public static ItemBasic<?> NandChip = new ItemBasic<>(Ref.ID, "nand_chip").tip("A very simple circuit");
    public static ItemBasic<?> AdvCircuitParts = new ItemBasic<>(Ref.ID, "adv_circuit_parts").tip("Used for making Advanced Circuits");
    public static ItemBasic<?> EtchedWiringMV = new ItemBasic<>(Ref.ID, "etched_wiring_mv").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringHV = new ItemBasic<>(Ref.ID, "etched_wiring_hv").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringEV = new ItemBasic<>(Ref.ID, "etched_wiring_ev").tip("Circuit board parts");
    public static ItemBasic<?> EngravedCrystalChip = new ItemBasic<>(Ref.ID, "engraved_crystal_chip").tip("Needed for Circuits");
    public static ItemBasic<?> EngravedLapotronChip = new ItemBasic<>(Ref.ID, "engraved_lapotron_chip").tip("Needed for Circuits");
    public static ItemBasic<?> CircuitBoardEmpty = new ItemBasic<>(Ref.ID, "circuit_board_empty").tip("A board Part");
    public static ItemBasic<?> CircuitBoardBasic = new ItemBasic<>(Ref.ID, "circuit_board_basic").tip("A basic Board");
    public static ItemBasic<?> CircuitBoardAdv = new ItemBasic<>(Ref.ID, "circuit_board_adv").tip("An advanced Board");
    public static ItemBasic<?> CircuitBoardProcessorEmpty = new ItemBasic<>(Ref.ID, "circuit_board_processor_empty").tip("A Processor Board Part");
    public static ItemBasic<?> CircuitBoardProcessor = new ItemBasic<>(Ref.ID, "circuit_board_processor").tip("A Processor Board");
    public static ItemBasic<?> CircuitBasic = new ItemBasic<>(Ref.ID, "circuit_basic").tip("A basic Circuit");
    public static ItemBasic<?> CircuitGood = new ItemBasic<>(Ref.ID, "circuit_good").tip("A good Circuit");
    public static ItemBasic<?> CircuitAdv = new ItemBasic<>(Ref.ID, "circuit_adv").tip("An advanced Circuit");
    public static ItemBasic<?> CircuitDataStorage = new ItemBasic<>(Ref.ID, "circuit_data_storage").tip("A Data Storage Chip");
    public static ItemBasic<?> CircuitDataControl = new ItemBasic<>(Ref.ID, "circuit_data_control").tip("A Data Control Processor");
    public static ItemBasic<?> CircuitEnergyFlow = new ItemBasic<>(Ref.ID, "circuit_energy_flow").tip("A High Voltage Processor");
    public static ItemBasic<?> CircuitDataOrb = new ItemBasic<>(Ref.ID, "circuit_data_orb").tip("A High Capacity Data Storage");
    public static ItemBasic<?> DataStick = new ItemBasic<>(Ref.ID, "data_stick").tip("A Low Capacity Data Storage");

    public static ItemBasic<?> BatteryTantalum = new ItemBasic<>(Ref.ID, "battery_tantalum").tip("Reusable");
    public static ItemBasic<?> BatteryHullSmall = new ItemBasic<>(Ref.ID, "battery_hull_small").tip("An empty LV Battery Hull");
    public static ItemBasic<?> BatteryHullMedium = new ItemBasic<>(Ref.ID, "battery_hull_medium").tip("An empty MV Battery Hull");
    public static ItemBasic<?> BatteryHullLarge = new ItemBasic<>(Ref.ID, "battery_hull_large").tip("An empty HV Battery Hull");
    public static ItemBasic<?> BatterySmallAcid = new ItemBasic<>(Ref.ID, "battery_small_acid").tip("Single Use");
    public static ItemBasic<?> BatterySmallMercury = new ItemBasic<>(Ref.ID, "battery_small_mercury").tip("Single Use");
    public static ItemBasic<?> BatterySmallCadmium = new ItemBasic<>(Ref.ID, "battery_small_cadmium").tip("Reusable");
    public static ItemBasic<?> BatterySmallLithium = new ItemBasic<>(Ref.ID, "battery_small_lithium").tip("Reusable");
    public static ItemBasic<?> BatterySmallSodium = new ItemBasic<>(Ref.ID, "battery_small_sodium").tip("Reusable");
    public static ItemBasic<?> BatteryMediumAcid = new ItemBasic<>(Ref.ID, "battery_medium_acid").tip("Single Use");
    public static ItemBasic<?> BatteryMediumMercury = new ItemBasic<>(Ref.ID, "battery_medium_mercury").tip("Single Use");
    public static ItemBasic<?> BatteryMediumCadmium = new ItemBasic<>(Ref.ID, "battery_medium_cadmium").tip("Reusable");
    public static ItemBasic<?> BatteryMediumLithium = new ItemBasic<>(Ref.ID, "battery_medium_lithium").tip("Reusable");
    public static ItemBasic<?> BatteryMediumSodium = new ItemBasic<>(Ref.ID, "battery_medium_sodium").tip("Reusable");
    public static ItemBasic<?> BatteryLargeAcid = new ItemBasic<>(Ref.ID, "battery_large_acid").tip("Single Use");
    public static ItemBasic<?> BatteryLargeMercury = new ItemBasic<>(Ref.ID, "battery_large_mercury").tip("Single Use");
    public static ItemBasic<?> BatteryLargeCadmium = new ItemBasic<>(Ref.ID, "battery_large_cadmium").tip("Reusable");
    public static ItemBasic<?> BatteryLargeLithium = new ItemBasic<>(Ref.ID, "battery_large_lithium").tip("Reusable");
    public static ItemBasic<?> BatteryLargeSodium = new ItemBasic<>(Ref.ID, "battery_large_sodium").tip("Reusable");
    public static ItemBasic<?> BatteryEnergyOrb = new ItemBasic<>(Ref.ID, "battery_energy_orb");
    public static ItemBasic<?> BatteryEnergyOrbCluster = new ItemBasic<>(Ref.ID, "battery_energy_orb_cluster");

    public static ItemBasic<?> EmptyShape = new ItemBasic<>(Ref.ID, "empty_shape_plate").tip("Raw plate to make Molds and Extruder Shapes");
    public static ItemBasic<?> MoldPlate = new ItemBasic<>(Ref.ID, "mold_plate").tip("Mold for making Plates");
    public static ItemBasic<?> MoldGear = new ItemBasic<>(Ref.ID, "mold_gear").tip("Mold for making Gears");
    public static ItemBasic<?> MoldGearSmall = new ItemBasic<>(Ref.ID, "mold_small_gear").tip("Mold for making Small Gears");
    public static ItemBasic<?> MoldCoinage = new ItemBasic<>(Ref.ID, "mold_coinage").tip("Secure Mold for making Coins (Don't lose it!)");
    public static ItemBasic<?> MoldBottle = new ItemBasic<>(Ref.ID, "mold_bottle").tip("Mold for making Bottles");
    public static ItemBasic<?> MoldIngot = new ItemBasic<>(Ref.ID, "mold_ingot").tip("Mold for making Ingots");
    public static ItemBasic<?> MoldBall = new ItemBasic<>(Ref.ID, "mold_ball").tip("Mold for making Balls");
    public static ItemBasic<?> MoldBlock = new ItemBasic<>(Ref.ID, "mold_block").tip("Mold for making Blocks");
    public static ItemBasic<?> MoldNugget = new ItemBasic<>(Ref.ID, "mold_nugget").tip("Mold for making Nuggets");
    public static ItemBasic<?> MoldAnvil = new ItemBasic<>(Ref.ID, "mold_anvil").tip("Mold for making Anvils");
    public static ItemBasic<?> ShapePlate = new ItemBasic<>(Ref.ID, "shape_plate").tip("Shape for making Plates");
    public static ItemBasic<?> ShapeRod = new ItemBasic<>(Ref.ID, "shape_rod").tip("Shape for making Rods");
    public static ItemBasic<?> ShapeBolt = new ItemBasic<>(Ref.ID, "shape_bolt").tip("Shape for making Bolts");
    public static ItemBasic<?> ShapeRing = new ItemBasic<>(Ref.ID, "shape_ring").tip("Shape for making Rings");
    public static ItemBasic<?> ShapeCell = new ItemBasic<>(Ref.ID, "shape_cell").tip("Shape for making Cells");
    public static ItemBasic<?> ShapeIngot = new ItemBasic<>(Ref.ID, "shape_ingot").tip("Shape for making Ingots");
    public static ItemBasic<?> ShapeWire = new ItemBasic<>(Ref.ID, "shape_wire").tip("Shape for making Wires");
    public static ItemBasic<?> ShapePipeTiny = new ItemBasic<>(Ref.ID, "shape_pipe_tiny").tip("Shape for making Tiny Pipes");
    public static ItemBasic<?> ShapePipeSmall = new ItemBasic<>(Ref.ID, "shape_pipe_small").tip("Shape for making Small Pipes");
    public static ItemBasic<?> ShapePipeNormal = new ItemBasic<>(Ref.ID, "shape_pipe_normal").tip("Shape for making Normal Pipes");
    public static ItemBasic<?> ShapePipeLarge = new ItemBasic<>(Ref.ID, "shape_pipe_large").tip("Shape for making Large Pipes");
    public static ItemBasic<?> ShapePipeHuge = new ItemBasic<>(Ref.ID, "shape_pipe_huge").tip("Shape for making Huge Pipes");
    public static ItemBasic<?> ShapeBlock = new ItemBasic<>(Ref.ID, "shape_block").tip("Shape for making Blocks");
    public static ItemBasic<?> ShapeHeadSword = new ItemBasic<>(Ref.ID, "shape_head_sword").tip("Shape for making Sword Blades");
    public static ItemBasic<?> ShapeHeadPickaxe = new ItemBasic<>(Ref.ID, "shape_head_pickaxe").tip("Shape for making Pickaxe Heads");
    public static ItemBasic<?> ShapeHeadShovel = new ItemBasic<>(Ref.ID, "shape_head_shovel").tip("Shape for making Shovel Heads");
    public static ItemBasic<?> ShapeHeadAxe = new ItemBasic<>(Ref.ID, "shape_head_axe").tip("Shape for making Axe Heads");
    public static ItemBasic<?> ShapeHeadHoe = new ItemBasic<>(Ref.ID, "shape_head_hoe").tip("Shape for making Hoe Heads");
    public static ItemBasic<?> ShapeHeadHammer = new ItemBasic<>(Ref.ID, "shape_head_hammer").tip("Shape for making Hammer Heads");
    public static ItemBasic<?> ShapeHeadFile = new ItemBasic<>(Ref.ID, "shape_head_file").tip("Shape for making File Heads");
    public static ItemBasic<?> ShapeHeadSaw = new ItemBasic<>(Ref.ID, "shape_head_saw").tip("Shape for making Saw Heads");
    public static ItemBasic<?> ShapeGear = new ItemBasic<>(Ref.ID, "shape_gear").tip("Shape for making Gears");
    public static ItemBasic<?> ShapeGearSmall = new ItemBasic<>(Ref.ID, "shape_gear_small").tip("Shape for making Small Gears");
    public static ItemBasic<?> ShapeBottle = new ItemBasic<>(Ref.ID, "shape_bottle").tip("Shape for making Bottles"); //TODO needed?
    //
    //    //TODO optional items (register anyway, but don't show in JEI?)
    //    //TODO move to IC2+IC2C Registrar
    //    public static final RegistryObject<Item> DropTin = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_tin", "Source of Tin")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropLead = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_lead", "Source of Lead")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropSilver = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_silver", "Source of Silver")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropIron = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_iron", "Source of Iron")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropGold = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_gold", "Source of Gold")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropAluminium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_aluminium", "Source of Aluminium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropTitanium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_titanium", "Source of Titanium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUranium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_uranium", "Source of Uranium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUranite = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_uranite", "Source of Uranite")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropThorium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_thorium", "Source of Thorium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropNickel = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_nickel", "Source of Nickel")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropZinc = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_zinc", "Source of Zinc")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropManganese = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_manganese", "Source of Manganese")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropCopper = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_copper", "Source of Copper")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropTungsten = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_tungsten", "Source of Tungsten")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropPlatinum = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_platinum", "Source of Platinum")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropIridium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_iridium", "Source of Iridium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropOsmium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_osmium", "Source of Osmium")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropNaquadah = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_naquadah", "Source of Naquadah")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropEmerald = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_emerald", "Source of Emeralds")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropOil = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_oil", "Source of Oil")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUUM = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_uum", "Source of UU Matter")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUUA = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "drop_uua", "Source of UU Amplifier")/*.optional(Ref.MOD_IC2, Ref.MOD_IC2C)*/;
    //
    //    //TODO move to Forestry Registrar
    //    public static final RegistryObject<Item> CombLignite = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_lignite", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCoal = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_coal", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombResin = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_resin", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombOil = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_oil", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombStone = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_stone", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCertus = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_certus", "")/*.required(Ref.MOD_FR, Ref.MOD_AE)*/;
    //    public static final RegistryObject<Item> CombRedstone = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_redstone", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombLapis = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_lapis", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombRuby = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_ruby", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSapphire = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_sapphire", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombDiamond = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_diamond", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombOlivine = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_olivine", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombEmerald = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_emerald", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSlag = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_slag", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCopper = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_copper", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTin = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_tin", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombLead = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_lead", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombIron = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_iron", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSteel = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_steel", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombNickel = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_nickel", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombZinc = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_zinc", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSilver = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_silver", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombGold = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_gold", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombAluminium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_aluminium", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombManganese = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_manganese", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTitanium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_titanium", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombChrome = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_chrome", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTungsten = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_tungsten", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombPlatinum = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_platinum", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombIridium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_iridium", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombUranium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_uranium", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombPlutonium = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_plutonium", "")/*.optional(Ref.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombNaquadah = new ItemBasic<>(Ref.ID, "").tip(Ref.ID, "comb_naquadah", "")/*.optional(Ref.MOD_FR)*/;

    //TODO
    //public static BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling();
    //public static BlockRubberLog RUBBER_LOG = new BlockRubberLog();
    //public static BlockLeavesBase RUBBER_LEAVES = new BlockLeavesBase("rubber_leaves", RUBBER_SAPLING);

    //STONE should be the only non-removable StoneType. It serves as the foundation. It is also used natively by BlockRock
    //TODO move vanilla stone types to Antimatter
    public static StoneType STONE = new StoneType(Ref.ID, "stone", Materials.Stone, new Texture("minecraft", "block/stone"), SoundType.STONE, false).setState(Blocks.STONE);

    public static StoneType GRANITE = new StoneType(Ref.ID, "granite", Granite, new Texture("minecraft", "block/granite"), SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.GRANITE);
    public static StoneType DIORITE = new StoneType(Ref.ID, "diorite", Diorite, new Texture("minecraft", "block/diorite"), SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.DIORITE);
    public static StoneType ANDESITE = new StoneType(Ref.ID, "andesite", Andesite, new Texture("minecraft", "block/andesite"),  SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || muramasa.antimatter.Ref.debugStones).setState(Blocks.ANDESITE);

    public static StoneType GRAVEL = new StoneType(Ref.ID, "gravel", Gravel, new Texture("minecraft", "block/gravel"), SoundType.GROUND, false).setState(Blocks.GRAVEL);
    public static StoneType SAND = new StoneType(Ref.ID, "sand", Sand, new Texture("minecraft", "block/sand"), SoundType.SAND, false).setState(Blocks.SAND);
    public static StoneType SAND_RED = new StoneType(Ref.ID, "sand_red", RedSand, new Texture("minecraft", "block/red_sand"), SoundType.SAND, false).setState(Blocks.RED_SAND);
    public static StoneType SANDSTONE = new StoneType(Ref.ID, "sandstone", Sandstone, new Texture("minecraft", "block/sandstone"), SoundType.STONE, false).setState(Blocks.SANDSTONE);

    public static StoneType NETHERRACK = new StoneType(Ref.ID, "netherrack", Materials.Netherrack, new Texture("minecraft", "block/netherrack"), SoundType.STONE, false).setState(Blocks.NETHERRACK);
    public static StoneType ENDSTONE = new StoneType(Ref.ID, "endstone", Materials.Endstone, new Texture("minecraft", "block/end_stone"), SoundType.STONE, false).setState(Blocks.END_STONE);

    public static StoneType GRANITE_RED = new StoneType(Ref.ID, "granite_red", Materials.RedGranite, new Texture(Ref.ID, "block/stone/granite_red"), SoundType.STONE, true);
    public static StoneType GRANITE_BLACK = new StoneType(Ref.ID, "granite_black", Materials.BlackGranite, new Texture(Ref.ID, "block/stone/granite_black"), SoundType.STONE, true);
    public static StoneType MARBLE = new StoneType(Ref.ID, "marble", Materials.Marble, new Texture(Ref.ID, "block/stone/marble"), SoundType.STONE, true);
    public static StoneType BASALT = new StoneType(Ref.ID, "basalt", Materials.Basalt, new Texture(Ref.ID, "block/stone/basalt"), SoundType.STONE, true);

    public static StoneType KOMATIITE = new StoneType(Ref.ID, "komatiite", Materials.Komatiite, new Texture(Ref.ID, "block/stone/komatiite"), SoundType.STONE, true);
    public static StoneType LIMESTONE = new StoneType(Ref.ID, "limestone", Limestone, new Texture(Ref.ID, "block/stone/limestone"), SoundType.STONE, true);
    public static StoneType GREEN_SCHIST = new StoneType(Ref.ID, "green_schist", GreenSchist, new Texture(Ref.ID, "block/stone/green_schist"), SoundType.STONE, true);
    public static StoneType BLUE_SCHIST = new StoneType(Ref.ID, "blue_schist", BlueSchist, new Texture(Ref.ID, "block/stone/blue_schist"), SoundType.STONE, true);
    public static StoneType KIMBERLITE = new StoneType(Ref.ID, "kimberlite", Kimberlite, new Texture(Ref.ID, "block/stone/kimberlite"), SoundType.STONE, true);
    public static StoneType QUARTZITE = new StoneType(Ref.ID, "quartzite", Quartzite, new Texture(Ref.ID, "block/stone/quartzite"), SoundType.STONE, true);

    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(Ref.ID, "anthracite_coal", new Texture(Ref.ID, "block/basic/anthracite_coal");
    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(Ref.ID, "anthracite_coal", new Texture(Ref.ID, "block/basic/anthracite_coal");

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

    public static final BlockCasing CASING_ULV = new BlockCasing(Ref.ID, "casing_ulv");
    public static final BlockCasing CASING_LV = new BlockCasing(Ref.ID, "casing_lv");
    public static final BlockCasing CASING_MV = new BlockCasing(Ref.ID, "casing_mv");
    public static final BlockCasing CASING_HV = new BlockCasing(Ref.ID, "casing_hv");
    public static final BlockCasing CASING_EV = new BlockCasing(Ref.ID, "casing_ev");
    public static final BlockCasing CASING_IV = new BlockCasing(Ref.ID, "casing_iv");
    public static final BlockCasing CASING_LUV = new BlockCasing(Ref.ID, "casing_luv");
    public static final BlockCasing CASING_ZPM = new BlockCasing(Ref.ID, "casing_zpm");
    public static final BlockCasing CASING_UV = new BlockCasing(Ref.ID, "casing_uv");
    public static final BlockCasing CASING_MAX = new BlockCasing(Ref.ID, "casing_max");

    public static final BlockCasing CASING_FIRE_BRICK = new BlockCasing(Ref.ID, "casing_fire_brick");
    public static final BlockCasing CASING_BRONZE = new BlockCasing(Ref.ID, "casing_bronze");
    public static final BlockCasing CASING_BRICKED_BRONZE = new BlockCasing(Ref.ID, "casing_bricked_bronze");
    public static final BlockCasing CASING_BRONZE_PLATED_BRICK = new BlockCasing(Ref.ID, "casing_bronze_plated_brick");
    public static final BlockCasing CASING_STEEL = new BlockCasing(Ref.ID, "casing_steel");
    public static final BlockCasing CASING_BRICKED_STEEL = new BlockCasing(Ref.ID, "casing_bricked_steel");
    public static final BlockCasing CASING_SOLID_STEEL = new BlockCasing(Ref.ID, "casing_solid_steel");
    public static final BlockCasing CASING_STAINLESS_STEEL = new BlockCasing(Ref.ID, "casing_stainless_steel");
    public static final BlockCasing CASING_TITANIUM = new BlockCasing(Ref.ID, "casing_titanium");
    public static final BlockCasing CASING_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "casing_tungstensteel");
    public static final BlockCasing CASING_HEAT_PROOF = new BlockCasing(Ref.ID, "casing_heat_proof");
    public static final BlockCasing CASING_FROST_PROOF = new BlockCasing(Ref.ID, "casing_frost_proof");
    public static final BlockCasing CASING_RADIATION_PROOF = new BlockCasing(Ref.ID, "casing_radiation_proof");
    public static final BlockCasing CASING_FIREBOX_BRONZE = new BlockCasing(Ref.ID, "casing_firebox_bronze");
    public static final BlockCasing CASING_FIREBOX_STEEL = new BlockCasing(Ref.ID, "casing_firebox_steel");
    public static final BlockCasing CASING_FIREBOX_TITANIUM = new BlockCasing(Ref.ID, "casing_firebox_titanium");
    public static final BlockCasing CASING_FIREBOX_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "casing_firebox_tungstensteel");
    public static final BlockCasing CASING_GEARBOX_BRONZE = new BlockCasing(Ref.ID, "casing_gearbox_bronze");
    public static final BlockCasing CASING_GEARBOX_STEEL = new BlockCasing(Ref.ID, "casing_gearbox_steel");
    public static final BlockCasing CASING_GEARBOX_TITANIUM = new BlockCasing(Ref.ID, "casing_gearbox_titanium");
    public static final BlockCasing CASING_GEARBOX_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "casing_gearbox_tungstensteel");
    public static final BlockCasing CASING_PIPE_BRONZE = new BlockCasing(Ref.ID, "casing_pipe_bronze");
    public static final BlockCasing CASING_PIPE_STEEL = new BlockCasing(Ref.ID, "casing_pipe_steel");
    public static final BlockCasing CASING_PIPE_TITANIUM = new BlockCasing(Ref.ID, "casing_pipe_titanium");
    public static final BlockCasing CASING_PIPE_TUNGSTENSTEEL = new BlockCasing(Ref.ID, "casing_pipe_tungstensteel");
    public static final BlockCasing CASING_ENGINE_INTAKE = new BlockCasing(Ref.ID, "casing_engine_intake");

    public static final BlockFusionCasing CASING_FUSION_1 = new BlockFusionCasing(Ref.ID, "casing_fusion_1");
    public static final BlockFusionCasing CASING_FUSION_2 = new BlockFusionCasing(Ref.ID, "casing_fusion_2");
    public static final BlockFusionCasing CASING_FUSION_3 = new BlockFusionCasing(Ref.ID, "casing_fusion_3");

    public static final BlockTurbineCasing CASING_TURBINE_1 = new BlockTurbineCasing(Ref.ID, "casing_turbine_1");
    public static final BlockTurbineCasing CASING_TURBINE_2 = new BlockTurbineCasing(Ref.ID, "casing_turbine_2");
    public static final BlockTurbineCasing CASING_TURBINE_3 = new BlockTurbineCasing(Ref.ID, "casing_turbine_3");
    public static final BlockTurbineCasing CASING_TURBINE_4 = new BlockTurbineCasing(Ref.ID, "casing_turbine_4");

    public static final BlockCoil COIL_CUPRONICKEL = new BlockCoil(Ref.ID, "coil_cupronickel", 113); //1808
    public static final BlockCoil COIL_KANTHAL = new BlockCoil(Ref.ID, "coil_kanthal", 169); //2704
    public static final BlockCoil COIL_NICHROME = new BlockCoil(Ref.ID, "coil_nichrome", 225); //3600
    public static final BlockCoil COIL_TUNGSTENSTEEL = new BlockCoil(Ref.ID, "coil_tungstensteel", 282); //4512
    public static final BlockCoil COIL_HSSG = new BlockCoil(Ref.ID, "coil_hssg", 338); //5408
    public static final BlockCoil COIL_NAQUADAH = new BlockCoil(Ref.ID, "coil_naquadah", 450); //7200
    public static final BlockCoil COIL_NAQUADAH_ALLOY = new BlockCoil(Ref.ID, "coil_naquadah_alloy", 563); //9008
    public static final BlockCoil COIL_FUSION = new BlockCoil(Ref.ID, "coil_fusion", 563); //9008
    public static final BlockCoil COIL_SUPERCONDUCTOR = new BlockCoil(Ref.ID, "coil_superconductor", 563); //9008

    public static final Cable<?> CABLE_RED_ALLOY = new Cable<>(Ref.ID, RedAlloy, 0, Tier.ULV).amps(1);
    public static final Cable<?> CABLE_COBALT = new Cable<>(Ref.ID, Cobalt, 2, Tier.LV).amps(2); //LV
    public static final Cable<?> CABLE_LEAD = new Cable<>(Ref.ID, Lead, 2, Tier.LV).amps(2);
    public static final Cable<?> CABLE_TIN = new Cable<>(Ref.ID, Tin, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_ZINC = new Cable<>(Ref.ID, Zinc, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_SOLDERING_ALLOY = new Cable<>(Ref.ID, SolderingAlloy, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_IRON = new Cable<>(Ref.ID, Iron, HC ? 3 : 4, Tier.MV).amps(2); //MV
    public static final Cable<?> CABLE_NICKEL = new Cable<>(Ref.ID, Nickel, HC ? 3 : 5, Tier.MV).amps(3);
    public static final Cable<?> CABLE_CUPRONICKEL = new Cable<>(Ref.ID, Cupronickel, HC ? 3 : 4, Tier.MV).amps(2);
    public static final Cable<?> CABLE_COPPER = new Cable<>(Ref.ID, Copper, HC ? 2 : 3, Tier.MV).amps(1);
    public static final Cable<?> CABLE_ANNEALED_COPPER = new Cable<>(Ref.ID, AnnealedCopper, HC ? 1 : 2, Tier.MV).amps(1);
    public static final Cable<?> CABLE_KANTHAL = new Cable<>(Ref.ID, Kanthal, HC ? 3 : 8, Tier.HV).amps(4); //HV
    public static final Cable<?> CABLE_GOLD = new Cable<>(Ref.ID, Gold, HC ? 2 : 6, Tier.HV).amps(3);
    public static final Cable<?> CABLE_ELECTRUM = new Cable<>(Ref.ID, Electrum, HC ? 2 : 5, Tier.HV).amps(2);
    public static final Cable<?> CABLE_SILVER = new Cable<>(Ref.ID, Silver, HC ? 1 : 4, Tier.HV).amps(1);
    public static final Cable<?> CABLE_NICHROME = new Cable<>(Ref.ID, Nichrome, HC ? 4 : 32, Tier.EV).amps(3); //EV
    public static final Cable<?> CABLE_STEEL = new Cable<>(Ref.ID, Steel, HC ? 2 : 16, Tier.EV).amps(2);
    public static final Cable<?> CABLE_TITANIUM = new Cable<>(Ref.ID, Titanium, HC ? 2 : 12, Tier.EV).amps(4);
    public static final Cable<?> CABLE_ALUMINIUM = new Cable<>(Ref.ID, Aluminium, HC ? 1 : 8, Tier.EV).amps(1);
    public static final Cable<?> CABLE_GRAPHENE = new Cable<>(Ref.ID, Graphene, HC ? 1 : 16, Tier.IV).amps(1); //IV
    public static final Cable<?> CABLE_OSMIUM = new Cable<>(Ref.ID, Osmium, HC ? 2 : 32, Tier.IV).amps(4);
    public static final Cable<?> CABLE_PLATINUM = new Cable<>(Ref.ID, Platinum, HC ? 1 : 16, Tier.IV).amps(2);
    public static final Cable<?> CABLE_TUNGSTEN_STEEL = new Cable<>(Ref.ID, TungstenSteel, HC ? 1 : 14, Tier.IV).amps(3);
    public static final Cable<?> CABLE_TUNGSTEN = new Cable<>(Ref.ID, Tungsten, HC ? 2 : 12, Tier.IV).amps(1);
    public static final Cable<?> CABLE_HSSG = new Cable<>(Ref.ID, HSSG, HC ? 2 : 128, Tier.LUV).amps(4); //LUV
    public static final Cable<?> CABLE_NIOBIUM_TITANIUM = new Cable<>(Ref.ID, NiobiumTitanium, HC ? 2 : 128, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_VANADIUM_GALLIUM = new Cable<>(Ref.ID, VanadiumGallium, HC ? 2 : 128, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_YTTRIUM_BARIUM_CUPRATE = new Cable<>(Ref.ID, YttriumBariumCuprate, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_NAQUADAH = new Cable<>(Ref.ID, Naquadah, HC ? 2 : 64, Tier.ZPM).amps(2); //ZPM
    public static final Cable<?> CABLE_NAQUADAH_ALLOY = new Cable<>(Ref.ID, NaquadahAlloy, HC ? 4 : 64, Tier.ZPM).amps(2);
    public static final Cable<?> CABLE_DURANIUM = new Cable<>(Ref.ID, Duranium, HC ? 8 : 64, Tier.ZPM).amps(1);
    public static final Cable<?> CABLE_SUPERCONDUCTOR = new Cable<>(Ref.ID, Superconductor, 0, Tier.MAX).amps(4); //MAX

    public static final Wire<?> WIRE_RED_ALLOY = new Wire<>(Ref.ID, RedAlloy, 1, Tier.ULV).amps(1);
    public static final Wire<?> WIRE_COBALT = new Wire<>(Ref.ID, Cobalt, 4, Tier.LV).amps(2); //LV
    public static final Wire<?> WIRE_LEAD = new Wire<>(Ref.ID, Lead, 4, Tier.LV).amps(2);
    public static final Wire<?> WIRE_TIN = new Wire<>(Ref.ID, Tin, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_ZINC = new Wire<>(Ref.ID, Zinc, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_SOLDERING_ALLOY = new Wire<>(Ref.ID, SolderingAlloy, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_IRON = new Wire<>(Ref.ID, Iron, HC ? 6 : 8, Tier.MV).amps(2); //MV
    public static final Wire<?> WIRE_NICKEL = new Wire<>(Ref.ID, Nickel, HC ? 6 : 10, Tier.MV).amps(3);
    public static final Wire<?> WIRE_CUPRONICKEL = new Wire<>(Ref.ID, Cupronickel, HC ? 6 : 8, Tier.MV).amps(2);
    public static final Wire<?> WIRE_COPPER = new Wire<>(Ref.ID, Copper, HC ? 4 : 6, Tier.MV).amps(1);
    public static final Wire<?> WIRE_ANNEALED_COPPER = new Wire<>(Ref.ID, AnnealedCopper, HC ? 2 : 4, Tier.MV).amps(1);
    public static final Wire<?> WIRE_KANTHAL = new Wire<>(Ref.ID, Kanthal, HC ? 6 : 16, Tier.HV).amps(4); //HV
    public static final Wire<?> WIRE_GOLD = new Wire<>(Ref.ID, Gold, HC ? 4 : 12, Tier.HV).amps(3);
    public static final Wire<?> WIRE_ELECTRUM = new Wire<>(Ref.ID, Electrum, HC ? 4 : 10, Tier.HV).amps(2);
    public static final Wire<?> WIRE_SILVER = new Wire<>(Ref.ID, Silver, HC ? 2 : 8, Tier.HV).amps(1);
    public static final Wire<?> WIRE_NICHROME = new Wire<>(Ref.ID, Nichrome, HC ? 8 : 64, Tier.EV).amps(3); //EV
    public static final Wire<?> WIRE_STEEL = new Wire<>(Ref.ID, Steel, HC ? 4 : 32, Tier.EV).amps(2);
    public static final Wire<?> WIRE_TITANIUM = new Wire<>(Ref.ID, Titanium, HC ? 4 : 24, Tier.EV).amps(4);
    public static final Wire<?> WIRE_ALUMINIUM = new Wire<>(Ref.ID, Aluminium, HC ? 2 : 16, Tier.EV).amps(1);
    public static final Wire<?> WIRE_GRAPHENE = new Wire<>(Ref.ID, Graphene, HC ? 2 : 32, Tier.IV).amps(1); //IV
    public static final Wire<?> WIRE_OSMIUM = new Wire<>(Ref.ID, Osmium, HC ? 4 : 64, Tier.IV).amps(4);
    public static final Wire<?> WIRE_PLATINUM = new Wire<>(Ref.ID, Platinum, HC ? 2 : 32, Tier.IV).amps(2);
    public static final Wire<?> WIRE_TUNGSTEN_STEEL = new Wire<>(Ref.ID, TungstenSteel, HC ? 2 : 28, Tier.IV).amps(3);
    public static final Wire<?> WIRE_TUNGSTEN = new Wire<>(Ref.ID, Tungsten, HC ? 2 : 12, Tier.IV).amps(1);
    public static final Wire<?> WIRE_HSSG = new Wire<>(Ref.ID, HSSG, HC ? 4 : 256, Tier.LUV).amps(4); //LUV
    public static final Wire<?> WIRE_NIOBIUM_TITANIUM = new Wire<>(Ref.ID, NiobiumTitanium, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_VANADIUM_GALLIUM = new Wire<>(Ref.ID, VanadiumGallium, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_YTTRIUM_BARIUM_CUPRATE = new Wire<>(Ref.ID, YttriumBariumCuprate, HC ? 8 : 512, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_NAQUADAH = new Wire<>(Ref.ID, Naquadah, HC ? 4 : 128, Tier.ZPM).amps(2); //ZPM
    public static final Wire<?> WIRE_NAQUADAH_ALLOY = new Wire<>(Ref.ID, NaquadahAlloy, HC ? 8 : 128, Tier.ZPM).amps(2);
    public static final Wire<?> WIRE_DURANIUM = new Wire<>(Ref.ID, Duranium, HC ? 16 : 128, Tier.ZPM).amps(1);
    public static final Wire<?> WIRE_SUPERCONDUCTOR = new Wire<>(Ref.ID, Superconductor, 1, Tier.MAX).amps(4); //MAX

    public static final FluidPipe<?> FLUID_PIPE_BRICK = new FluidPipe<>(Ref.ID, Brick, 1000, false).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(10, 10, 30, 60, 60, 60).pressures(400, 400, 400, 400, 400, 400);
    public static final FluidPipe<?> FLUID_PIPE_COPPER = new FluidPipe<>(Ref.ID, Copper, 700, true).caps(10).pressures(600);
    public static final FluidPipe<?> FLUID_PIPE_BISMUTH_BRONZE = new FluidPipe<>(Ref.ID, BismuthBronze, 950, true).caps(20).pressures(800);
    public static final FluidPipe<?> FLUID_PIPE_BLACK_STEEL = new FluidPipe<>(Ref.ID, BlackSteel, 1200, true).caps(10).pressures(900);
    public static final FluidPipe<?> FLUID_PIPE_STAINLESS_STEEL = new FluidPipe<>(Ref.ID, StainlessSteel, 1300, true).caps(60).pressures(1000);
    public static final FluidPipe<?> FLUID_PIPE_TITANIUM = new FluidPipe<>(Ref.ID, Titanium, 1668, true).caps(80).pressures(2500);
    public static final FluidPipe<?> FLUID_PIPE_TUNGSTEN_STEEL = new FluidPipe<>(Ref.ID, TungstenSteel, 3422, true).caps(100).pressures(5000);
    public static final FluidPipe<?> FLUID_PIPE_PLASTIC = new FluidPipe<>(Ref.ID, Plastic, 160, true).caps(60).pressures(2000);
    public static final FluidPipe<?> FLUID_PIPE_POLY = new FluidPipe<>(Ref.ID, Polytetrafluoroethylene, 327, true).caps(480).pressures(1000);
    public static final FluidPipe<?> FLUID_PIPE_HP = new FluidPipe<>(Ref.ID, HighPressure, 3422, true).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(4800, 4800, 4800, 7200, 9600, 9600).pressures(10000);
    public static final FluidPipe<?> FLUID_PIPE_PLASMA = new FluidPipe<>(Ref.ID, PlasmaContainment, 100000, true).sizes(PipeSize.NORMAL).caps(240, 240, 240, 240, 240, 240).pressures(100000);

    public static final ItemPipe<?> ITEM_PIPE_WOOD = new ItemPipe<>(Ref.ID, Wood).sizes(PipeSize.SMALL).caps(0, 0, 1, 0, 0, 0);
    public static final ItemPipe<?> ITEM_PIPE_WROUGHT_IRON = new ItemPipe<>(Ref.ID, WroughtIron).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(0, 0, 2, 3, 4, 0);
    public static final ItemPipe<?> ITEM_PIPE_COBALT_BRASS = new ItemPipe<>(Ref.ID, CobaltBrass).caps(2).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_BLACK_BRONZE = new ItemPipe<>(Ref.ID, BlackBronze).caps(3).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_STERLING_SILVER = new ItemPipe<>(Ref.ID, SterlingSilver).caps(4).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_ROSE_GOLD = new ItemPipe<>(Ref.ID, RoseGold).caps(4).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_PLATINUM = new ItemPipe<>(Ref.ID, Platinum).caps(5).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_TUNGSTEN_CARBIDE = new ItemPipe<>(Ref.ID, TungstenCarbide).caps(6);
    public static final ItemPipe<?> ITEM_PIPE_ULTIMET = new ItemPipe<>(Ref.ID, Ultimet).caps(8);
    public static final ItemPipe<?> ITEM_PIPE_HC = new ItemPipe<>(Ref.ID, HighCapacity).caps(10);
    public static final ItemPipe<?> ITEM_PIPE_OSMIRIDIUM = new ItemPipe<>(Ref.ID, Osmiridium).caps(20);

    // Rubber Tree
    public static final BlockRubberLeaves RUBBER_LEAVES = new BlockRubberLeaves(Ref.ID, "rubber_leaves");
    public static final BlockRubberLog RUBBER_LOG = new BlockRubberLog(Ref.ID, "rubber_log");
    public static final BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling(Ref.ID, "rubber_sapling");
}
