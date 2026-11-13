package org.gtreimagined.gt5r;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;
import carbonconfiglib.impl.ReloadMode;

public class GT5RConfig {

    //TODO needed?
    public static boolean MORE_COMPLICATED_CHEMICAL_RECIPES = true;
    public static ConfigEntry.BoolValue HARD_SETTINGS;
    private static ConfigEntry.BoolValue REAL_HARDER_CIRCUITS;
    private static ConfigEntry.BoolValue REAL_HARDER_ALUMINIUM_PROCESSING;
    private static ConfigEntry.BoolValue REAL_SULFURIC_OIL_OUTPUTS;
    private static ConfigEntry.BoolValue REAL_COMPLICATED_CHEMICAL_PROCESSING;
    private static ConfigEntry.BoolValue REAL_HARD_CARBON;
    private static ConfigEntry.BoolValue REAL_FORCE_ROASTER;
    private static ConfigEntry.BoolValue REAL_MORE_LOSSY_FORGE_HAMMER;
    private static ConfigEntry.BoolValue REAL_NO_NATIVE_IRON;
    public static ConfigBooleanSupplier HARDER_CIRCUITS = () -> REAL_HARDER_CIRCUITS.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier HARDER_ALUMINIUM_PROCESSING = () -> REAL_HARDER_ALUMINIUM_PROCESSING.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier SULFURIC_OIL_OUTPUTS = () -> REAL_SULFURIC_OIL_OUTPUTS.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier COMPLICATED_CHEMICAL_PROCESSING = () -> REAL_COMPLICATED_CHEMICAL_PROCESSING.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier HARD_CARBON = () -> REAL_HARD_CARBON.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier FORCE_ROASTER = () -> REAL_FORCE_ROASTER.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier MORE_LOSSY_FORGE_HAMMER = () -> REAL_MORE_LOSSY_FORGE_HAMMER.get() || HARD_SETTINGS.get();
    public static ConfigBooleanSupplier NO_NATIVE_IRON = () -> REAL_NO_NATIVE_IRON.get() || HARD_SETTINGS.get();
    public static ConfigEntry.BoolValue GT6_ORE_GEN;
    public static ConfigEntry.BoolValue DEFAULT_ORE_VEINS;
    public static ConfigEntry.BoolValue DEFAULT_STONE_LAYERS;
    public static ConfigEntry.BoolValue DEFAULT_SMALL_ORES;
    public static ConfigEntry.BoolValue DEFAULT_BEDROCK_VEINS;
    public static ConfigEntry.BoolValue DEFAULT_TWILIGHT_ORE_GEN;
    public static ConfigEntry.DoubleValue ASPHALT_MULTIPLIER;
    public static ConfigEntry.BoolValue ADD_LOOT;
    private static ConfigBooleanSupplier supplier(ConfigEntry.BoolValue original){
        return () -> original.get() || HARD_SETTINGS.get();
    }

    //TFC Compat
    public static ConfigEntry.BoolValue ENABLE_TFC_COMPAT;
    public static ConfigEntry.BoolValue ENABLE_GT_TFC_VEINS;
    public static ConfigEntry.BoolValue TFC_VEIN_REMOVALS;

    //Mekanism Compat
    public static ConfigEntry.BoolValue ENABLE_MEKANISM_COMPAT;
    public static ConfigEntry.BoolValue DISABLE_MEKANISM_OREGEN;
    public static ConfigEntry.BoolValue REPLACE_MEK_OSMIUM_WITH_GERMANIUM;
    public static ConfigEntry.BoolValue GREGIFY_MEK_RECIPES;

    //CC Tweaked Compat
    public static ConfigEntry.BoolValue GREGIFY_CC_RECIPES;
    public static ConfigEntry.BoolValue REPLACE_COMPUTER_TEXTURES;

    static ConfigHandler CONFIG;

    public static void createConfig(){
        Config config = new Config("gt5r");
        ConfigSection general = config.add("general");
        ConfigSection hardSettings = general.addSubSection("hard_settings");
        HARD_SETTINGS = hardSettings.addBool("hard_settings", false, "If true all the below settings and no_native_iron will be enabled. - Default: false");
        /*MORE_COMPLICATED_CHEMICAL_RECIPES = section.addBool("more_complicated_chemical_recipes", false, "Enables more complicated chemical recipes. - Default: false");*/
        REAL_HARDER_CIRCUITS = hardSettings.addBool("harder_circuits", false, "Enables more complicated circuit recipes added in versions of gt5u after 509.25 - Default: false");
        REAL_SULFURIC_OIL_OUTPUTS = hardSettings.addBool("sulfuric_oil_outputs", false, "Enables oil distillation outputing sulfurized fuels instead of pure fuels. - Default: false");
        REAL_HARDER_ALUMINIUM_PROCESSING = hardSettings.addBool("harder_aluminium_processing", true, "Enables gt6's alumina processing, if disabled alumina reverts back to just being in the blast furnace - Default: true");
        REAL_HARD_CARBON = hardSettings.addBool("hard_carbon", false, "Makes carbon fibre require the hard recipe from gt5u. - Default: false");
        REAL_MORE_LOSSY_FORGE_HAMMER = hardSettings.addBool("more_lossy_forge_hammer", true, "Makes forge hammer makes plates in a ratio of 2:1 instead of 3:2 ingots to plates. Default- true");
        REAL_COMPLICATED_CHEMICAL_PROCESSING = hardSettings.addBool("complicated_chemical_processing", false, "Enables complicated chemical recipes");
        REAL_FORCE_ROASTER = hardSettings.addBool("force_roaster", false, "Requires sulfides to be processed in the roaster.");
        ASPHALT_MULTIPLIER = general.addDouble("asphalt_multiplier", 1.1, "Default speed multiplier applied by concrete.");
        ADD_LOOT = general.addBool("add_loot", true, "Enables chest loot for GT5R. - Default: true");
        ConfigSection worldgen = config.add("worldgen");
        REAL_NO_NATIVE_IRON = worldgen.addBool("no_native_iron", false, "Replaces all spawns of native iron with hematite. This config will be true if gt6_ore_gen is true. - Default: false");
        GT6_ORE_GEN = worldgen.addBool("gt6_ore_gen", false, "Enables gt6 style veins instead of gt5 style veins. Requires default_stone_layers to be true. - Default: false");
        DEFAULT_STONE_LAYERS = worldgen.addBool("default_stone_layers", true, "Enables default stone layers. - Default: true");
        DEFAULT_ORE_VEINS = worldgen.addBool("default_ore_veins", true, "Enables default ore veins. Will not generate in the overworld when gt6_ore_gen is true. Default: true");
        DEFAULT_SMALL_ORES = worldgen.addBool("default_small_ores", true, "Enables default small ore gen. - Default: true");
        DEFAULT_BEDROCK_VEINS = worldgen.addBool("default_bedrock_veins", true, "Enables default bedrock veins. - Default: true");
        DEFAULT_TWILIGHT_ORE_GEN = worldgen.addBool("default_twilight_ore_gen", true, "Enables default twilight ore gen. - Default: true");
        ConfigSection compat = config.add("mod_compat");
        ConfigSection tfc = compat.addSubSection("tfc");
        ENABLE_TFC_COMPAT = tfc.addBool("enable_tfc_compat", true, "Enables all TFC compat. I do not recommend turning this off unless you know what you are doing. Default: true").setRequiredReload(ReloadMode.GAME);
        ENABLE_GT_TFC_VEINS = tfc.addBool("enable_gt_tfc_veins", true, "Enables custom tfc type veins added by GT5R. - Default: true.");
        TFC_VEIN_REMOVALS = tfc.addBool("tfc_vein_removals", true, "Removes a few default tfc veins from the tfc:in_biome/veins placed feature tag. Default: true");
        ConfigSection mekanism = compat.addSubSection("mekanism");
        ENABLE_MEKANISM_COMPAT = mekanism.addBool("enable_mekanism_compat", true, "Enables all mekanism compat. Default: true").setRequiredReload(ReloadMode.GAME);
        DISABLE_MEKANISM_OREGEN = mekanism.addBool("disable_mekanism_oregen", true, "Disables mekanism ore gen. - Default: true");
        REPLACE_MEK_OSMIUM_WITH_GERMANIUM = mekanism.addBool("replace_mek_osmium_with_germanium", true, "Replaces all uses of osmium in mekanism with germanium for balance. Default: true");
        GREGIFY_MEK_RECIPES = mekanism.addBool("gregify_mek_recipes", true, "Gregifies all mekanism recipes, mostly replacing ingots with plates, Default: true");
        ConfigSection ccTweaked = compat.addSubSection("cc_tweaked");
        GREGIFY_CC_RECIPES = ccTweaked.addBool("gregify_cc_recipes", true, "Gregifies all the recipes for cc tweaked computer and other related items. Default: true");
        REPLACE_COMPUTER_TEXTURES = ccTweaked.addBool("replace_computer_textures", true, "Replaces the textures of basic computers with ones based off gt steel blocks. Requires gregify_cc_recipes to also be true. Default: true").setRequiredReload(ReloadMode.GAME);
        CONFIG = CarbonConfig.CONFIGS.createConfig(config);
        CONFIG.register();
    }

    @FunctionalInterface
    public interface ConfigBooleanSupplier{
        boolean get();
    }
}
