package org.gtreimagined.gt5r;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;

public class GT5RConfig {

    //TODO needed?
    public static boolean MORE_COMPLICATED_CHEMICAL_RECIPES = true;
    public static boolean HARDER_CIRCUITS = false;
    public static boolean HARD_SETTINGS = false;
    public static ConfigEntry.BoolValue HARDER_ALUMINIUM_PROCESSING;
    public static ConfigEntry.BoolValue GT5U_OIL;
    public static ConfigEntry.BoolValue COMPLICATED_CHEMICAL_PROCESSING;
    public static ConfigEntry.BoolValue HARD_CARBON;
    public static ConfigEntry.BoolValue MORE_LOSSY_FORGE_HAMMER;
    public static ConfigEntry.BoolValue NO_NATIVE_IRON;
    public static ConfigEntry.BoolValue GT6_ORE_GEN;
    public static ConfigEntry.BoolValue DEFAULT_ORE_VEINS;
    public static ConfigEntry.BoolValue DEFAULT_STONE_LAYERS;
    public static ConfigEntry.BoolValue DEFAULT_SMALL_ORES;
    public static ConfigEntry.BoolValue DEFAULT_BEDROCK_VEINS;
    public static ConfigEntry.BoolValue DEFAULT_TWILIGHT_ORE_GEN;
    public static ConfigEntry.DoubleValue ASPHALT_MULTIPLIER;
    public static ConfigEntry.BoolValue ADD_LOOT;
    static ConfigHandler CONFIG;

    public static void createConfig(){
        Config config = new Config("gt5r");
        ConfigSection general = config.add("general");
        /*MORE_COMPLICATED_CHEMICAL_RECIPES = section.addBool("more_complicated_chemical_recipes", false, "Enables more complicated chemical recipes. - Default: false");
        HARDER_CIRCUITS = section.addBool("harder_circuits", false, "Enables more complicated circuit recipes added in versions of gt5u after 509.25 - Default: false");*/
        GT5U_OIL = general.addBool("gt5u_oil", false, "Enables gt5u oil processing, if false gt6 oil processing is used instead. - Default: false");
        HARDER_ALUMINIUM_PROCESSING = general.addBool("harder_aluminium_processing", true, "Enables gt6's alumina processing, if disabled alumina reverts back to just being in the blast furnace - Default: true");

        ASPHALT_MULTIPLIER = general.addDouble("asphalt_multiplier", 1.1, "Default speed multiplier applied by concrete.");
        HARD_CARBON = general.addBool("hard_carbon", false, "Makes carbon fibre require the hard recipe from gt5u. - Default: false");
        MORE_LOSSY_FORGE_HAMMER = general.addBool("more_lossy_forge_hammer", true, "Makes forge hammer makes plates in a ratio of 2:1 instead of 3:2 ingots to plates. Default- true");
        COMPLICATED_CHEMICAL_PROCESSING = general.addBool("complicated_chemical_processing", false, "Enables complicated chemical recipes");
        ADD_LOOT = general.addBool("add_loot", true, "Enables chest loot for GT5R. - Default: true");
        ConfigSection worldgen = config.add("worldgen");
        NO_NATIVE_IRON = worldgen.addBool("no_native_iron", false, "Replaces all spawns of native iron with hematite. This config will be true if gt6_ore_gen is true. - Default: false");
        GT6_ORE_GEN = worldgen.addBool("gt6_ore_gen", false, "Enables gt6 style veins instead of gt5 style veins. Requires default_stone_layers to be true. - Default: false");
        DEFAULT_STONE_LAYERS = worldgen.addBool("default_stone_layers", true, "Enables default stone layers. - Default: true");
        DEFAULT_ORE_VEINS = worldgen.addBool("default_ore_veins", true, "Enables default ore veins. Will not generate in the overworld when gt6_ore_gen is true. Default: true");
        DEFAULT_SMALL_ORES = worldgen.addBool("default_small_ores", true, "Enables default small ore gen. - Default: true");
        DEFAULT_BEDROCK_VEINS = worldgen.addBool("default_bedrock_veins", true, "Enables default bedrock veins. - Default: true");
        DEFAULT_TWILIGHT_ORE_GEN = worldgen.addBool("default_twilight_ore_gen", true, "Enables default twilight ore gen. - Default: true");
        CONFIG = CarbonConfig.CONFIGS.createConfig(config);
        CONFIG.register();
    }
}
