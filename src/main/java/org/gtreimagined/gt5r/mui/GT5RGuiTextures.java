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
    public static final UITexture BUFFER_ARROW = widget("buffer_arrow", 87, 22, null);
    public static final UITexture FORGE_HAMMER_OVERLAY = widget("forge_hammer_overlay", 20, 6);
    public static final UITexture ITEM_FILTER_FAKE_SLOTS = widget("item_filter_fake_slots", ColorType.DEFAULT);
    public static final UITexture MACERATOR_OVERLAY = widget("macerator_overlay", ColorType.DEFAULT);
    public static final UITexture MULTIBLOCK_BACKGROUND = widget("multiblock_background.",143, 75);
    public static final UITexture RED_FILTER_ARROW = widget("red_filter_arrow", 19, 24, null);
    public static final UITexture STEEL_BOILER_EMPTY_BAR = widget("steel_boiler_empty_bar", 10, 54);
    public static final UITexture SOLAR_BOILER_ICON = widget("solar_boiler_icon", 12, 24);
    public static final UITexture SUPER_BUFFER_OVERLAY = widget("super_buffer_overlay", null);
    public static final UITexture TYPE_FILTER_FAKE_SLOT = widget("type_filter_fake_slot", null);
    public static final UITexture WHITE_FILTER_ARROW_BAR = widget("white_filter_arrow_bar", 9, 6, null);
    public static final UITexture WHITE_TYPE_FILTER_ARROW_BAR = widget("white_type_filter_arrow_bar", 26, 6, null);

    //ICONS
    public static final UITexture BRONZE_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/bronze_flame_off.png");
    public static final UITexture FLAME_ON = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/flame_on.png", null);
    public static final UITexture PBF_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/pbf_flame_off.png");
    public static final UITexture STEEL_FLAME_OFF = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/steel_flame_off.png");
    public static final UITexture BRONZE_TANK_ICON = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/bronze_tank_icon.png");
    public static final UITexture STEEL_TANK_ICON = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/steel_tank_icon.png");
    public static final UITexture BRONZE_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/bronze_gt_logo.png");
    public static final UITexture STEEL_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/steel_gt_logo.png");
    public static final UITexture PRIMITIVE_GT_LOGO = UITexture.fullImage(GT5Reimagined.ID, "textures/gui/icon/primitive_gt_logo.png");


    //SLOTS
    public static final UITexture BATTERY_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/battery.png").colorType(null).build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/blueprint.png").build();
    public static final UITexture CRAFTING_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/crafting.png").build();
    public static final UITexture PRIMITIVE_CELL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_cell.png").build();
    public static final UITexture PRIMITIVE_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_dust.png").build();
    public static final UITexture PRIMITIVE_FIRE_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_fire.png").build();
    public static final UITexture PRIMITIVE_INGOT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_ingot.png").build();
    public static final UITexture BRONZE_CELL_IN_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/bronze_cell_in.png").build();
    public static final UITexture BRONZE_CELL_OUT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/bronze_cell_out.png").build();
    public static final UITexture BRONZE_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/bronze_dust.png").build();
    public static final UITexture BRONZE_COAL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/bronze_coal.png").build();
    public static final UITexture STEEL_CELL_IN_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/steel_cell_in.png").build();
    public static final UITexture STEEL_CELL_OUT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/steel_cell_out.png").build();
    public static final UITexture STEEL_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/steel_dust.png").build();
    public static final UITexture STEEL_COAL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/steel_coal.png").build();
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
    public static final UITexture FUSION_REACTOR_PROGRESS = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/progress_bars/fusion_reactor.png").imageSize(149, 32).colorType(null).build();
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
        return UITexture.builder().location(GT5Reimagined.ID, "textures/gui/progress_bars/" + name + ".png").imageSize(20, 36).colorType(colorType).build();
    }

    private static UITexture widget(String name, ColorType colorType){
        return UITexture.builder().location(GT5Reimagined.ID, "textures/gui/widgets/" + name + ".png").colorType(colorType).build();
    }

    private static UITexture widget(String name, int width, int height){
        return UITexture.builder().location(GT5Reimagined.ID, "textures/gui/widgets/" + name + ".png").imageSize(width, height).build();
    }

    private static UITexture widget(String name, int width, int height, ColorType colorType){
        return UITexture.builder().location(GT5Reimagined.ID, "textures/gui/widgets/" + name + ".png").imageSize(width, height).colorType(colorType).build();
    }

}
