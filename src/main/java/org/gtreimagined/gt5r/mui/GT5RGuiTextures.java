package org.gtreimagined.gt5r.mui;

import brachy.modularui.drawable.ColorType;
import brachy.modularui.drawable.UITexture;
import org.gtreimagined.gt5r.GT5Reimagined;

public class GT5RGuiTextures {
    public static final UITexture MULTIBLOCK_BACKGROUND = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/widgets/multiblock_background.png").imageSize(143, 75).build();
    public static final UITexture TANK_BACKGROUND = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/widgets/basic_tank_background").imageSize(71, 45).build();

    public static final UITexture BATTERY_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/battery.png").colorType(null).build();
    public static final UITexture BLUEPRINT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/blueprint.png").build();
    public static final UITexture CRAFTING_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/crafting.png").build();
    public static final UITexture PRIMITIVE_CELL_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_cell.png").build();
    public static final UITexture PRIMITIVE_DUST_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_dust.png").build();
    public static final UITexture PRIMITIVE_FIRE_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_fire.png").build();
    public static final UITexture PRIMITIVE_INGOT_SLOT_OVERLAY = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/slots/overlays/primitive_ingot.png").build();
    //PROGRESS BARS
    private static final UITexture ASSEMBLER_PROGRESS = progressTexture("assembler");
    private static final UITexture BENDER_PROGRESS = progressTexture("bender");
    private static final UITexture CANNER_PROGRESS = progressTexture("canner");
    private static final UITexture CHEMICAL_REACTOR_PROGRESS = progressTexture("chemical_reactor");
    private static final UITexture COKE_OVEN_PROGRESS = progressTexture("coke_oven");
    private static final UITexture COMPRESSOR_PROGRESS = progressTexture("compressor");
    private static final UITexture CUTTER_PROGRESS = progressTexture("cutter");
    private static final UITexture ELECTROMAGNETIC_SEPARATOR_PROGRESS = progressTexture("electromagnetic_separator");
    private static final UITexture EXTRACTOR_PROGRESS = progressTexture("extractor");
    private static final UITexture EXTRUDER_PROGRESS = progressTexture("extruder");
    private static final UITexture FORGE_HAMMER_PROGRESS = progressTexture("forge_hammer");
    private static final UITexture FUSION_REACTOR_PROGRESS = UITexture.builder().location(GT5Reimagined.ID, "textures/gui/progress_bars/fusion_reactor.png").imageSize(149, 32).colorType(null).build();
    private static final UITexture LATHE_PROGRESS = progressTexture("lathe");
    private static final UITexture MACERATOR_PROGRESS = progressTexture("macerator");
    private static final UITexture MIXER_PROGRESS = progressTexture("mixer");
    private static final UITexture ORE_WASHER_PROGRESS = progressTexture("ore_washer");
    private static final UITexture RECYCLER_PROGRESS = progressTexture("recycler");
    private static final UITexture SIFTER_PROGRESS = progressTexture("sifter");
    private static final UITexture SMELTER_PROGRESS = progressTexture("smelter");
    private static final UITexture WIREMILL_PROGRESS = progressTexture("wiremill");

    private static UITexture progressTexture(String name){
        return UITexture.builder().location(GT5Reimagined.ID, "textures/gui/progress_bars/" + name + ".png").imageSize(20, 36).colorType(ColorType.DEFAULT).build();
    }

}
