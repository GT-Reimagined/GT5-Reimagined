package org.gtreimagined.gt5r.mui;

import brachy.modularui.drawable.ColorType;
import brachy.modularui.drawable.UITexture;
import org.gtreimagined.gt5r.GT5Reimagined;

public class GT5RGuiTextures {
    //WIDGETS
    public static final UITexture TANK_BACKGROUND = widget("basic_tank_background",71, 45);
    public static final UITexture BLUE_FILTER_ARROW = widget("blue_filter_arrow",null);
    public static final UITexture BLUE_TYPE_FILTER_ARROW = widget("blue_type_filter_arrow", 41, 24, null);
    public static final UITexture BOILER_HEAT_BAR = widget("boiler_heat_bar", 10, 54, null);
    public static final UITexture BOILER_LAVA_BAR = widget("boiler_lava_bar", 10, 54, null);
    public static final UITexture BOILER_STEAM_BAR = widget("boiler_steam_bar", 10, 54, null);
    public static final UITexture BOILER_WATER_BAR = widget("boiler_water_bar", 10, 54, null);
    public static final UITexture BRONZE_BOILER_EMPTY_BAR = widget("bronze_boiler_empty_bar", 10, 54, null);
    public static final UITexture BRONZE_FORGE_HAMMER_OVERLAY = widget("bronze_forge_hammer_overlay", 20, 6, null);
    public static final UITexture BRONZE_MACERATOR_OVERLAY = widget("bronze_macerator_overlay", null);
    public static final UITexture BUFFER_ARROW = widget("buffer_arrow", 87, 22, null);
    public static final UITexture COKE_OVEN_MULTIBLOCK_OVERLAY = widget("coke_oven_multiblock_overlay", 18, 38, null);
    public static final UITexture FORGE_HAMMER_OVERLAY = widget("forge_hammer_overlay", 20, 6);
    public static final UITexture FUSION_COMPUTER_BOTH_OVERLAY = widget("fusion_computer_both_overlay", null);
    public static final UITexture FUSION_COMPUTER_MIDDLE_OVERLAY = widget("fusion_computer_middle_overlay", null);
    public static final UITexture FUSION_COMPUTER_TOP_BOTTOM_OVERLAY = widget("fusion_computer_top_bottom_overlay", null);
    public static final UITexture ITEM_FILTER_FAKE_SLOTS = widget("item_filter_fake_slots", ColorType.DEFAULT);
    public static final UITexture MACERATOR_OVERLAY = widget("macerator_overlay", ColorType.DEFAULT);
    public static final UITexture MULTIBLOCK_BACKGROUND = widget("multiblock_background",143, 75);
    public static final UITexture PBF_MULTIBLOCK_OVERLAY = widget("pbf_multiblock_overlay", 18, 50, null);
    public static final UITexture RED_FILTER_ARROW = widget("red_filter_arrow", 19, 24, null);
    public static final UITexture STEEL_BOILER_EMPTY_BAR = widget("steel_boiler_empty_bar", 10, 54);
    public static final UITexture STEEL_FORGE_HAMMER_OVERLAY = widget("steel_forge_hammer_overlay", 20, 6, null);
    public static final UITexture STEEL_MACERATOR_OVERLAY = widget("steel_macerator_overlay", null);
    public static final UITexture SOLAR_BOILER_ICON = widget("solar_boiler_icon", 12, 24);
    public static final UITexture SUPER_BUFFER_OVERLAY = widget("super_buffer_overlay", null);
    public static final UITexture TYPE_FILTER_FAKE_SLOT = widget("type_filter_fake_slot", null);
    public static final UITexture WHITE_FILTER_ARROW_BAR = widget("white_filter_arrow_bar", 9, 6, null);
    public static final UITexture WHITE_TYPE_FILTER_ARROW_BAR = widget("white_type_filter_arrow_bar", 26, 6, null);
    //BYPRODUCTS TREE OVERLAYS
    public static final UITexture BASE_BYPRODUCTS = byproduct("base");
    public static final UITexture FURNACE_BYPRODUCTS = byproduct("smelt");
    public static final UITexture MERCURY_BYPRODUCTS = byproduct("chem");
    public static final UITexture PERSULFATE_BYPRODUCTS = byproduct("new/persulfate");
    public static final UITexture PGS_BYPRODUCTS = byproduct("new/pgs");
    public static final UITexture SEP_BYPRODUCTS = byproduct("sep");
    public static final UITexture SIFT_BYPRODUCTS = byproduct("sift");
    public static final UITexture VITRIOL_BYPRODUCTS = byproduct("new/vitriol");


    //ICONS
    public static final UITexture BRONZE_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/bronze_flame_off");
    public static final UITexture FLAME_ON = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/flame_on", null);
    public static final UITexture PBF_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/pbf_flame_off");
    public static final UITexture STEEL_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/steel_flame_off");
    public static final UITexture BRONZE_TANK_ICON = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/bronze_tank_icon");
    public static final UITexture STEEL_TANK_ICON = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/steel_tank_icon");
    public static final UITexture BRONZE_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/bronze_gt_logo");
    public static final UITexture STEEL_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/steel_gt_logo");
    public static final UITexture PRIMITIVE_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "gui/icon/primitive_gt_logo");

    //BUTTONS
    public static final UITexture BLACKLIST_OFF = button("blacklist_off");
    public static final UITexture BLACKLIST_ON = button("blacklist_on");
    public static final UITexture ENERGY_OFF = button("energy_off");
    public static final UITexture ENERGY_ON = button("energy_on");
    public static final UITexture INVERT_REDSTONE_OFF = button("invert_redstone_off");
    public static final UITexture INVERT_REDSTONE_ON = button("invert_redstone_on");
    public static final UITexture NBT_OFF = button("nbt_off" );
    public static final UITexture NBT_ON = button("nbt_on");
    public static final UITexture PULL_UP = button("pull_up");
    public static final UITexture REDSTONE_CONTROL_OFF = button("redstone_control_off");
    public static final UITexture REDSTONE_CONTROL_ON = button("redstone_control_on");

    //SLOTS
    public static final UITexture BATTERY_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/battery").colorType(null).build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/blueprint").build();
    public static final UITexture CRAFTING_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/crafting").build();
    public static final UITexture PRIMITIVE_CELL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/primitive_cell").build();
    public static final UITexture PRIMITIVE_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/primitive_dust").build();
    public static final UITexture PRIMITIVE_FIRE_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/primitive_fire").build();
    public static final UITexture PRIMITIVE_INGOT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/primitive_ingot").build();
    public static final UITexture BRONZE_CELL_IN_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/bronze_cell_in").build();
    public static final UITexture BRONZE_CELL_OUT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/bronze_cell_out").build();
    public static final UITexture BRONZE_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/bronze_dust").build();
    public static final UITexture BRONZE_COAL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/bronze_coal").build();
    public static final UITexture STEEL_CELL_IN_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/steel_cell_in").build();
    public static final UITexture STEEL_CELL_OUT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/steel_cell_out").build();
    public static final UITexture STEEL_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/steel_dust").build();
    public static final UITexture STEEL_COAL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "gui/slots/overlays/steel_coal").build();
    //PROGRESS BARS
    public static final UITexture ASSEMBLER_PROGRESS = progressTexture("assembler");
    public static final UITexture BENDER_PROGRESS = progressTexture("bender");
    public static final UITexture CANNER_PROGRESS = progressTexture("canner");
    public static final UITexture CHEMICAL_REACTOR_PROGRESS = progressTexture("chemical_reactor");
    public static final UITexture COKE_OVEN_PROGRESS = progressTexture("coke_oven", null);
    public static final UITexture COMPRESSOR_PROGRESS = progressTexture("compressor");
    public static final UITexture CUTTER_PROGRESS = progressTexture("cutter");
    public static final UITexture ELECTROMAGNETIC_SEPARATOR_PROGRESS = progressTexture("electromagnetic_separator");
    public static final UITexture EXTRACTOR_PROGRESS = progressTexture("extractor");
    public static final UITexture EXTRUDER_PROGRESS = progressTexture("extruder");
    public static final UITexture FORGE_HAMMER_PROGRESS = progressTexture("forge_hammer");
    public static final UITexture FUSION_REACTOR_PROGRESS = UITexture.builder().location(GT5Reimagined.ID, "gui/progress_bars/fusion_reactor").imageSize(149, 32).colorType(null).build();
    public static final UITexture LATHE_PROGRESS = progressTexture("lathe");
    public static final UITexture MACERATOR_PROGRESS = progressTexture("macerator");
    public static final UITexture MIXER_PROGRESS = progressTexture("mixer", null);
    public static final UITexture ORE_WASHER_PROGRESS = progressTexture("ore_washer", null);
    public static final UITexture RECYCLER_PROGRESS = progressTexture("recycler", null);
    public static final UITexture SIFTER_PROGRESS = progressTexture("sifter");
    public static final UITexture SMELTER_PROGRESS = progressTexture("smelter", null);
    public static final UITexture WIREMILL_PROGRESS = progressTexture("wiremill");

    public static final UITexture BRONZE_COMPRESSOR_PROGRESS = progressTexture("bronze_compressor", null);
    public static final UITexture BRONZE_CUTTER_PROGRESS = progressTexture("bronze_cutter", null);
    public static final UITexture BRONZE_DEFAULT_PROGRESS = progressTexture("bronze_default", null);
    public static final UITexture BRONZE_EXTRACTOR_PROGRESS = progressTexture("bronze_extractor", null);
    public static final UITexture BRONZE_FORGE_HAMMER_PROGRESS = progressTexture("bronze_forge_hammer", null);
    public static final UITexture BRONZE_MACERATOR_PROGRESS = progressTexture("bronze_macerator", null);
    public static final UITexture BRONZE_SIFTER_PROGRESS = progressTexture("bronze_sifter", null);

    public static final UITexture STEEL_COMPRESSOR_PROGRESS = progressTexture("steel_compressor", null);
    public static final UITexture STEEL_CUTTER_PROGRESS = progressTexture("steel_cutter", null);
    public static final UITexture STEEL_DEFAULT_PROGRESS = progressTexture("steel_default", null);
    public static final UITexture STEEL_EXTRACTOR_PROGRESS = progressTexture("steel_extractor", null);
    public static final UITexture STEEL_FORGE_HAMMER_PROGRESS = progressTexture("steel_forge_hammer", null);
    public static final UITexture STEEL_MACERATOR_PROGRESS = progressTexture("steel_macerator", null);
    public static final UITexture STEEL_SIFTER_PROGRESS = progressTexture("steel_sifter", null);

    private static UITexture progressTexture(String name){
        return progressTexture(name, ColorType.DEFAULT);
    }

    private static UITexture progressTexture(String name, ColorType colorType){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/progress_bars/" + name).imageSize(20, 36).colorType(colorType).build();
    }

    private static UITexture widget(String name, ColorType colorType){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/widgets/" + name).colorType(colorType).build();
    }

    private static UITexture widget(String name, int width, int height){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/widgets/" + name).imageSize(width, height).build();
    }

    private static UITexture widget(String name, int width, int height, ColorType colorType){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/widgets/" + name).imageSize(width, height).colorType(colorType).build();
    }

    private static UITexture button(String name){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/button/" + name).fullImage().colorType(null).build();
    }

    private static UITexture byproduct(String name){
        return UITexture.builder().location(GT5Reimagined.ID, "gui/ore_byproducts/" + name).imageSize(186, 166).colorType(null).build();
    }

}
