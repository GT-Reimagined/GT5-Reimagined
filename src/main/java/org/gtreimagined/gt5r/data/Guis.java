package org.gtreimagined.gt5r.data;

import brachy.modularui.drawable.UITexture;
import brachy.modularui.drawable.progress.CompositeProgress;
import brachy.modularui.drawable.progress.ProgressDrawable.Direction;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.DoubleSyncValue;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.widgets.ButtonWidget;
import net.minecraft.client.gui.screens.Screen;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityCoalBoiler;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityLavaBoiler;
import org.gtreimagined.gt5r.blockentity.single.BlockEntitySolarBoiler;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtcore.machine.SteamMachine;
import org.gtreimagined.gtcore.mui.GTCoreThemes;
import org.gtreimagined.gtlib.blockentity.IFuelMachine;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.GuiProperties;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.slot.ISlotProvider;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.machine.IPanelFunction;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.gui.widgets.FusionButtonWidget;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.machine.types.MultiMachine;
import org.gtreimagined.gtlib.mui.BarDir;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.GTMuiUtils;
import org.gtreimagined.gtlib.mui.IInfoRenderer;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.util.Utils;
import org.gtreimagined.gtlib.util.int2;

import java.util.function.Function;

import static org.gtreimagined.gtcore.data.SlotTypes.PARK;
import static org.gtreimagined.gtlib.gui.SlotType.*;
import static org.gtreimagined.gtlib.gui.Widget.builder;
import static org.gtreimagined.gtlib.machine.Tier.*;
import static org.gtreimagined.gt5r.data.GT5RMachines.*;

public class Guis {

    // TODO move these to the API somehow
    public static GuiProperties MULTI_DISPLAY = new GuiProperties(GT5Reimagined.ID, "multi_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 17, 34).add(IT_IN, 35, 34)
            .add(IT_IN, 53, 34).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16).add(IT_OUT, 107, 34)
            .add(IT_OUT, 125, 34).add(IT_OUT, 143, 34).add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 63).add(FL_OUT, 125, 63).add(FL_OUT, 143, 63));

    public static GuiProperties ALLOY_SMELTER_DISPLAY = new GuiProperties(GT5Reimagined.ID, "alloy_smelter").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 35, 34).add(IT_IN, 53, 34)
            .add(IT_OUT, 107, 25));

    public static GuiProperties SIMPLE_DISPLAY = new GuiProperties(GT5Reimagined.ID, "simple_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 53, 25).add(IT_OUT, 107, 25));
    public static GuiProperties BEDROCK_DRILL_DISPLAY = new GuiProperties(GT5Reimagined.ID, "simple_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 53, 25).add(FL_IN, 53, 63)
            .add(IT_OUT, 107, 7).add(IT_OUT, 125, 7).add(IT_OUT, 143, 7)
            .add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25)
            .add(IT_OUT, 107, 43).add(IT_OUT, 125, 43).add(IT_OUT, 143, 43)
            .add(IT_OUT, 107, 61).add(IT_OUT, 125, 61).add(IT_OUT, 143, 61));

    public static GuiProperties MULTI_DISPLAY_FLUID = new GuiProperties(GT5Reimagined.ID, "multi_display_fluid").setSlots(ISlotProvider.DEFAULT()
            .add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 7).add(FL_OUT, 125, 7).add(FL_OUT, 143, 7)
            .add(FL_OUT, 107, 25).add(FL_OUT, 125, 25).add(FL_OUT, 143, 25)
            .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
            .add(FL_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61));

    public static GuiProperties MULTI_DISPLAY_DISTILLATION = new GuiProperties(GT5Reimagined.ID, "multi_display_distillation").setSlots(ISlotProvider.DEFAULT()
            .add(FL_IN, 53, 43)
            .add(IT_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61)
            .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
            .add(FL_OUT, 107, 25).add(FL_OUT, 125, 25).add(FL_OUT, 143, 25)
            .add(FL_OUT, 107, 7).add(FL_OUT, 125, 7).add(FL_OUT, 143, 7));
    public static GuiProperties MULTI_DISPLAY_COMPACT = new GuiProperties(GT5Reimagined.ID, "multi_display")
            .setSlots(ISlotProvider.DEFAULT().add(MULTI_DISPLAY.getSlots()));
    public static GuiProperties BASIC_TANK = new GuiProperties(GT5Reimagined.ID, "basic_tank")
            .setSlots(ISlotProvider.DEFAULT().add(CELL_IN, 8, 17).add(CELL_OUT, 8, 53).add("", new SlotData<>(FL_IN, 55, 43, GTGuiTextures.BLANK_SLOT, null)));

    public static GuiProperties MULTIBLOCK = new GuiProperties(GT5Reimagined.ID, "multiblock").setSlots(ISlotProvider.DEFAULT().add(STORAGE, 152, 5));

    public static GuiProperties ORE_BYPRODUCTS = new GuiProperties(GT5Reimagined.ID, "ore_byproducts") {
        @Override
        public ResourceLocation getTexture(Tier tier, String type) {
            return new ResourceLocation(loc.getNamespace(), "textures/gui/" + loc.getPath() + ".png");
        }
    }.setSlots(ISlotProvider.DEFAULT().add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16)
                    .add(IT_IN, 17, 34).add(IT_IN, 35, 34).add(IT_IN, 53, 34).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16)
                    .add(IT_OUT, 142, 16).add(IT_OUT, 107, 34).add(IT_OUT, 125, 34).add(IT_OUT, 143, 34));

    public static void init(Dist side) {
        slots();
        backgroundWidgets();
        machineData();
        widgets();
    }

    public static void slots(){
        HULL.add(STORAGE, 80, 40).add(FL_IN, 80, 63);
        ALLOY_SMELTER.add(IT_IN, 35, 25).add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(ENERGY, 80, 63);
        ARC_FURNACE.add(IT_IN, 53, 25).add(FL_IN, 53, 63).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16)
                .add(IT_OUT, 125, 34).add(IT_OUT, 107, 34).add(ENERGY, 80, 63);
        ASSEMBLER.add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 17, 34).add(IT_IN, 35, 34)
                .add(IT_IN, 53, 34).add(IT_OUT, 107, 25)
                .add(FL_IN, 53, 63)
                .add(ENERGY, 80, 63);
        BENDER.add(ALLOY_SMELTER);
        CANNER.add(IT_IN, 35, 25).add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(ENERGY, 80, 63);
        CIRCUIT_ASSEMBLER.add(ASSEMBLER);
        COMPRESSOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(ENERGY, 80, 63);
        CRYSTALLIZATION_CHAMBER.add(COMPRESSOR).add(FL_IN, 44, 63).add(FL_IN, 62, 63);
        CUTTER.add(IT_IN, 53, 25).add(FL_IN, 53, 63).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(ENERGY, 80, 63);
        FURNACE.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(ENERGY, 80, 63);
        EXTRACTOR.add(COMPRESSOR);
        EXTRUDER.add(ALLOY_SMELTER);
        LATHE.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(ENERGY, 80, 63);
        MACERATOR.add(COMPRESSOR);
        MACERATOR.add(HV, IT_IN, 53, 25).add(HV, IT_OUT, 107, 16).add(HV, IT_OUT, 125, 16).add(HV, IT_OUT, 107, 34)
                .add(HV, IT_OUT, 125, 34).add(HV, ENERGY, 80, 63);
        MACERATOR.add(EV, IT_IN, 53, 25).add(EV, IT_OUT, 107, 16).add(EV, IT_OUT, 125, 16).add(EV, IT_OUT, 107, 34)
                .add(EV, IT_OUT, 125, 34).add(EV, ENERGY, 80, 63);
        ROASTER.add(IT_IN, 53, 25).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 107, 34)
                .add(IT_OUT, 125, 34).add(FL_IN, 53, 63).add(FL_OUT, 107, 63);
        RECYCLER.add(COMPRESSOR).add(FL_IN, 53, 63);
        WIRE_MILL.add(COMPRESSOR);
        CENTRIFUGE.add(IT_IN, 35, 25)
                .add(FL_IN, 53, 25)
                .add(IT_OUT, 107, 7).add(IT_OUT, 125, 7).add(IT_OUT, 143, 7)
                .add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25)
                .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
                .add(FL_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61)
                .add(ENERGY, 17, 25);
        ELECTRIC_OVEN.add(FURNACE);
        ELECTROLYZER.add(CENTRIFUGE).add(IT_IN, 35, 43).add(FL_IN,53, 43);
        THERMAL_CENTRIFUGE.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25).add(ENERGY,
                80, 63);
        ORE_WASHER.add(THERMAL_CENTRIFUGE).add(FL_IN, 53, 63).add(FL_OUT, 107, 63);
        CHEMICAL_REACTOR.add(IT_IN, 26, 16).add(IT_IN, 44, 16)
                .add(FL_IN, 17, 34).add(FL_IN, 35, 34).add(FL_IN, 53, 34)
                .add(IT_OUT, 116, 16).add(IT_OUT, 134, 16)
                .add(FL_OUT, 107, 34).add(FL_OUT, 125, 34).add(FL_OUT, 143, 34)
                .add(ENERGY, 80, 63);
        FLUID_CANNER.add(COMPRESSOR).add(FL_IN, 53, 63).add(FL_OUT, 107, 63);
        DISASSEMBLER.add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 7).add(IT_OUT, 107 + 18, 7).add(IT_OUT, 107 + 18 * 2, 7)
                .add(IT_OUT, 107, 25).add(IT_OUT, 107 + 18, 25).add(IT_OUT, 107 + 18 * 2, 25)
                .add(IT_OUT, 107, 43).add(IT_OUT, 107 + 18, 43).add(IT_OUT, 107 + 18 * 2, 43)
                .add(ENERGY, 80, 63);
        MASS_FABRICATOR.add(COMPRESSOR).add(FL_IN, 53, 63).add(FL_OUT, 107, 63).add(FL_OUT, 125, 63);
        REPLICATOR.add(COMPRESSOR).add(FL_IN, 44, 63).add(FL_IN, 62, 63).add(FL_OUT, 107, 63);
        ROCK_BREAKER.add(COMPRESSOR);
        FERMENTER.add(FLUID_CANNER);
        FLUID_PRESS.add(COMPRESSOR).add(FL_OUT, 107, 63);
        SMELTER.add(IT_IN, 53, 25).add(ENERGY, 80, 63).add(FL_OUT, 107, 63);
        FLUID_HEATER.add(ENERGY, 80, 63).add(FL_IN, 53, 63).add(FL_OUT, 107, 63);
        FLUID_SOLIDIFIER.add(COMPRESSOR).add(FL_IN, 53, 63);
        SCANNER.add(FLUID_SOLIDIFIER).add(IT_IN, 125, 63);
        PRINTER.add(SCANNER);
        DISTILLERY.add(FLUID_CANNER);
        BATH.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25).add(FL_IN, 53, 63).add(FL_OUT, 107, 63).add(FL_OUT, 125, 63);
        AUTOCLAVE.add(IT_IN, 35, 25).add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 16).add(IT_OUT, 125, 16)
                .add(IT_OUT, 107, 34).add(IT_OUT, 125, 34)
                .add(ENERGY, 80, 63).add(FL_IN, 53, 63);
        UITexture craft = GT5RGuiTextures.CRAFTING_SLOT_OVERLAY;
        AUTOCRAFTER.add(IT_IN, 17, 7, craft).add(IT_IN, 35, 7).add(IT_IN, 53, 7, craft)
                .add(IT_IN, 17, 25).add(IT_IN, 35, 25, craft).add(IT_IN, 53, 25)
                .add(IT_IN, 17, 43, craft).add(IT_IN, 35, 43).add(IT_IN, 53, 43, craft)
                .add(STORAGE, 53, 63, GT5RGuiTextures.BLUEPRINT_SLOT_OVERLAY)
                .add(IT_OUT, 107, 7).add(IT_OUT, 125, 7).add(IT_OUT, 143, 7)
                .add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25)
                .add(IT_OUT, 107, 43).add(IT_OUT, 125, 43).add(IT_OUT, 143, 43)
                .add(IT_OUT, 107, 61).add(IT_OUT, 125, 61).add(IT_OUT, 143, 61);
        PACKAGER.add(COMPRESSOR);
        POLARIZER.add(COMPRESSOR);
        MIXER.add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 35, 34).add(IT_IN, 53, 34).add(FL_IN, 44, 63)
                .add(FL_IN, 62, 63).add(IT_OUT, 107, 25).add(FL_OUT, 107, 63).add(FL_OUT, 125, 63).add(ENERGY, 80, 63);
        LASER_ENGRAVER.add(ALLOY_SMELTER);
        FORMING_PRESS.add(IT_IN, 17, 25).add(IT_IN, 35, 25).add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(ENERGY, 80, 63);
        FORGE_HAMMER.add(FURNACE);
        UNPACKAGER.add(ALLOY_SMELTER);
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 9; x++){
                CHEST_BUFFER.add(STORAGE, 8 + (x * 18), 5 + (y * 18));
            }
        }
        SIFTER.add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16)
                .add(IT_OUT, 107, 34).add(IT_OUT, 125, 34).add(IT_OUT, 143, 34)
                .add(ENERGY, 80, 63);
        ELECTROMAGNETIC_SEPARATOR.add(SIFTER);
        DEHYDRATOR.add(IT_IN, 35, 25).add(IT_IN, 53, 25)
                .add(FL_IN,35,43).add(FL_IN,53,43)
                .add(IT_OUT, 107, 7).add(IT_OUT, 125, 7).add(IT_OUT, 143, 7)
                .add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25)
                .add(IT_OUT, 107, 43).add(IT_OUT, 125, 43).add(IT_OUT, 143, 43)
                .add(FL_OUT,107,63).add(FL_OUT,125,63).add(FL_OUT,143,63)
                .add(ENERGY,80,63);
        COKE_OVEN.add(IT_IN, 53, 25, GT5RGuiTextures.PRIMITIVE_INGOT_SLOT_OVERLAY)
                .add(IT_OUT, 107, 25, GT5RGuiTextures.PRIMITIVE_INGOT_SLOT_OVERLAY)
                .add(FL_OUT, 125, 25, GT5RGuiTextures.PRIMITIVE_CELL_SLOT_OVERLAY);
        UITexture bat = GT5RGuiTextures.BATTERY_SLOT_OVERLAY;
        BATTERY_BUFFER_FOUR.add(ENERGY, 71, 27, bat).add(ENERGY, 89, 27, bat).add(ENERGY, 71, 45, bat).add(ENERGY, 89, 45, bat);
        BATTERY_BUFFER_ONE.add(ENERGY, 80, 40, bat);
        BATTERY_BUFFER_EIGHT
                .add(ENERGY,53,27, bat).add(ENERGY,71,27, bat).add(ENERGY,89,27, bat).add(ENERGY,107,27, bat)
                .add(ENERGY,53,45, bat).add(ENERGY,71,45, bat).add(ENERGY,89,45, bat).add(ENERGY,107,45, bat);
        BATTERY_BUFFER_SIXTEEN
                .add(ENERGY,53,9, bat).add(ENERGY,71,9, bat).add(ENERGY,89,9, bat).add(ENERGY,107,9, bat)
                .add(ENERGY,53,27, bat).add(ENERGY,71,27, bat).add(ENERGY,89,27, bat).add(ENERGY,107,27, bat)
                .add(ENERGY,53,45, bat).add(ENERGY,71,45, bat).add(ENERGY,89,45, bat).add(ENERGY,107,45, bat)
                .add(ENERGY,53,63, bat).add(ENERGY,71,63, bat).add(ENERGY,89,63, bat).add(ENERGY,107,63, bat);

        SOLID_FUEL_BOILER.add(BRONZE, CELL_IN, 44, 26, GT5RGuiTextures.BRONZE_CELL_IN_SLOT_OVERLAY).add(BRONZE, CELL_OUT, 44, 62, GT5RGuiTextures.BRONZE_CELL_OUT_SLOT_OVERLAY)
                .add(BRONZE, IT_OUT, 116, 26, GT5RGuiTextures.BRONZE_DUST_SLOT_OVERLAY).add(BRONZE, IT_IN, 116, 62, GT5RGuiTextures.BRONZE_COAL_SLOT_OVERLAY);
        SOLID_FUEL_BOILER.add(STEEL, CELL_IN, 44, 26, GT5RGuiTextures.STEEL_CELL_IN_SLOT_OVERLAY).add(STEEL, CELL_OUT, 44, 62, GT5RGuiTextures.STEEL_CELL_OUT_SLOT_OVERLAY)
                .add(STEEL, IT_OUT, 116, 26, GT5RGuiTextures.STEEL_DUST_SLOT_OVERLAY).add(STEEL, IT_IN, 116, 62, GT5RGuiTextures.STEEL_COAL_SLOT_OVERLAY);
        LAVA_BOILER.add(CELL_IN, 44, 26, GT5RGuiTextures.STEEL_CELL_IN_SLOT_OVERLAY).add(CELL_OUT, 44, 62, GT5RGuiTextures.STEEL_CELL_OUT_SLOT_OVERLAY);
        SOLAR_BOILER.add(CELL_IN, 44, 26, GT5RGuiTextures.BRONZE_CELL_IN_SLOT_OVERLAY).add(CELL_OUT, 44, 62, GT5RGuiTextures.BRONZE_CELL_OUT_SLOT_OVERLAY);

        STEAM_ALLOY_SMELTER.add(IT_IN, 35, 25).add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_COMPRESSOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_FURNACE.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_EXTRACTOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_MACERATOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_FORGE_HAMMER.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_CUTTER.add(IT_IN, 53, 25).add(FL_IN, 53, 63).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(PARK, 80, 63).add(FL_IN, 35, 63);
        STEAM_SIFTER.add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16)
                .add(IT_OUT, 107, 34).add(IT_OUT, 125, 34).add(IT_OUT, 143, 34)
                .add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_TURBINE.add(BASIC_TANK.getSlots());
        GAS_TURBINE.add(BASIC_TANK.getSlots());
        COMBUSTION_GENERATOR.add(BASIC_TANK.getSlots());
        SEMIFLUID_GENERATOR.add(BASIC_TANK.getSlots());
        MAGIC_ENERGY_CONVERTER.add(IT_IN, 8, 17).add(IT_OUT, 8, 53).add("", new SlotData<>(FL_IN, 55, 43, GTGuiTextures.BLANK_SLOT, null));
        NUCLEAR_REACTOR_CORE.add(STORAGE, 70, 25).add(STORAGE, 70, 43).add(STORAGE, 88, 25).add(STORAGE, 88, 43).add(FL_IN, 70, 61).add(FL_OUT, 88, 61);

        CROP_HARVESTER.add(IT_OUT, 62, 16).add(IT_OUT, 80, 16).add(IT_OUT, 98, 16)
                .add(IT_OUT, 62, 34).add(IT_OUT, 80, 34).add(IT_OUT, 98, 34)
                .add(IT_OUT, 62, 52).add(IT_OUT, 80, 52).add(IT_OUT, 98, 52);

        QUANTUM_TANK.add(BASIC_TANK.getSlots());
        PRIMITIVE_BLAST_FURNACE.add(IT_IN, 53, 16, GT5RGuiTextures.PRIMITIVE_INGOT_SLOT_OVERLAY)
                .add(IT_IN, 53, 34, GT5RGuiTextures.PRIMITIVE_FIRE_SLOT_OVERLAY)
                .add(IT_IN, 53, 52, GT5RGuiTextures.PRIMITIVE_FIRE_SLOT_OVERLAY)
                .add(IT_OUT, 107, 25, GT5RGuiTextures.PRIMITIVE_INGOT_SLOT_OVERLAY)
                .add(IT_OUT, 125, 25, GT5RGuiTextures.PRIMITIVE_DUST_SLOT_OVERLAY)
                .add(IT_OUT, 143, 25, GT5RGuiTextures.PRIMITIVE_DUST_SLOT_OVERLAY);

        MUFFLER_HATCH.add(IT_IN, 79, 34);

        INPUT_BUS.add(ULV, IT_IN, 79, 34);
        INPUT_BUS.add(LV, IT_IN, 70, 25).add(LV, IT_IN, 88, 25).add(LV, IT_IN, 70, 43).add(LV, IT_IN, 88, 43);
        INPUT_BUS.add(MV, IT_IN, 61, 16).add(MV, IT_IN, 79, 16).add(MV, IT_IN, 97, 16).add(MV, IT_IN, 61, 34)
                .add(MV, IT_IN, 79, 34).add(MV, IT_IN, 97, 34).add(MV, IT_IN, 61, 52).add(MV, IT_IN, 79, 52)
                .add(MV, IT_IN, 97, 52);
        INPUT_BUS.add(HV, IT_IN, 52, 7).add(HV, IT_IN, 70, 7).add(HV, IT_IN, 88, 7).add(HV, IT_IN, 106, 7)
                .add(HV, IT_IN, 52, 25).add(HV, IT_IN, 70, 25).add(HV, IT_IN, 88, 25).add(HV, IT_IN, 106, 25)
                .add(HV, IT_IN, 52, 43).add(HV, IT_IN, 70, 43).add(HV, IT_IN, 88, 43).add(HV, IT_IN, 106, 43)
                .add(HV, IT_IN, 52, 61).add(HV, IT_IN, 70, 61).add(HV, IT_IN, 88, 61).add(HV, IT_IN, 106, 61);
        INPUT_BUS.add(EV, INPUT_BUS, HV);
        OUTPUT_BUS.add(ULV, IT_OUT, 79, 34);
        OUTPUT_BUS.add(LV, IT_OUT, 70, 25).add(LV, IT_OUT, 88, 25).add(LV, IT_OUT, 70, 43).add(LV, IT_OUT, 88, 43);
        OUTPUT_BUS.add(MV, IT_OUT, 61, 16).add(MV, IT_OUT, 79, 16).add(MV, IT_OUT, 97, 16).add(MV, IT_OUT, 61, 34)
                .add(MV, IT_OUT, 79, 34).add(MV, IT_OUT, 97, 34).add(MV, IT_OUT, 61, 52).add(MV, IT_OUT, 79, 52)
                .add(MV, IT_OUT, 97, 52);
        OUTPUT_BUS.add(HV, IT_OUT, 52, 7).add(HV, IT_OUT, 70, 7).add(HV, IT_OUT, 88, 7).add(HV, IT_OUT, 106, 7)
                .add(HV, IT_OUT, 52, 25).add(HV, IT_OUT, 70, 25).add(HV, IT_OUT, 88, 25).add(HV, IT_OUT, 106, 25)
                .add(HV, IT_OUT, 52, 43).add(HV, IT_OUT, 70, 43).add(HV, IT_OUT, 88, 43).add(HV, IT_OUT, 106, 43)
                .add(HV, IT_OUT, 52, 61).add(HV, IT_OUT, 70, 61).add(HV, IT_OUT, 88, 61).add(HV, IT_OUT, 106, 61);
        OUTPUT_BUS.add(EV, OUTPUT_BUS, HV);
        INPUT_HATCH.add(FL_IN, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        OUTPUT_HATCH.add(FL_OUT, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        HIGH_CAPACITY_INPUT_HATCH.add(FL_IN, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        HIGH_CAPACITY_OUTPUT_HATCH.add(FL_OUT, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        SECONDARY_INPUT_HATCH.add(FL_IN, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        SECONDARY_OUTPUT_HATCH.add(FL_OUT, 79, 34).add(CELL_IN, 9, 22).add(CELL_OUT, 9, 58);
        ELECTRIC_ITEM_FILTER
                .add("", new SlotData<>(DISPLAY_SETTABLE, 18, 6, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 35, 6, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 52, 6, GTGuiTextures.BLANK_SLOT, null))
                .add("", new SlotData<>(DISPLAY_SETTABLE, 18, 23, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 35, 23, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 52, 23, GTGuiTextures.BLANK_SLOT, null))
                .add("", new SlotData<>(DISPLAY_SETTABLE, 18, 40, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 35, 40, GTGuiTextures.BLANK_SLOT, null)).add("", new SlotData<>(DISPLAY_SETTABLE, 52, 40, GTGuiTextures.BLANK_SLOT, null))
                .add(STORAGE, 98, 5).add(STORAGE, 98 + 18, 5)
                .add(STORAGE, 98 + 18 * 2, 5)
                .add(STORAGE, 98, 23).add(STORAGE, 98 + 18, 23)
                .add(STORAGE, 98 + 18 * 2, 23)
                .add(STORAGE, 98, 41).add(STORAGE, 98 + 18, 41)
                .add(STORAGE, 98 + 18 * 2, 41);

        ELECTRIC_TYPE_FILTER
                .add("", new SlotData<>(DISPLAY_SETTABLE, 35, 23, GTGuiTextures.BLANK_SLOT, null))
                .add(STORAGE, 98, 5).add(STORAGE, 98 + 18, 5)
                .add(STORAGE, 98 + 18 * 2, 5)
                .add(STORAGE, 98, 23).add(STORAGE, 98 + 18, 23)
                .add(STORAGE, 98 + 18 * 2, 23)
                .add(STORAGE, 98, 41).add(STORAGE, 98 + 18, 41)
                .add(STORAGE, 98 + 18 * 2, 41);
        AUTOCRAFTER_ASSEMBLY_LINE.add(MULTIBLOCK.getSlots());
        BLAST_FURNACE.add(MULTIBLOCK.getSlots());
        IMPLOSION_COMPRESSOR.add(MULTIBLOCK.getSlots());
        VACUUM_FREEZER.add(MULTIBLOCK.getSlots());
        MULTI_SMELTER.add(MULTIBLOCK.getSlots());
        LARGE_BOILER.add(MULTIBLOCK.getSlots());
        LARGE_CHEMICAL_REACTOR.add(MULTIBLOCK.getSlots());
        LARGE_TURBINE.add(MULTIBLOCK.getSlots());
        LARGE_HEAT_EXCHANGER.add(MULTIBLOCK.getSlots());
        OIL_DRILLING_RIG.add(MULTIBLOCK.getSlots());
        ORE_MINING_RIG.add(MULTIBLOCK.getSlots());
        PROCESSING_ARRAY.add(MULTIBLOCK.getSlots());
        PYROLYSE_OVEN.add(MULTIBLOCK.getSlots());
        DISTLLATION_TOWER.add(MULTIBLOCK.getSlots());
        CRYO_DISTLLATION_TOWER.add(MULTIBLOCK.getSlots());
        CRACKING_UNIT.add(MULTIBLOCK.getSlots());
    }

    public static void backgroundWidgets(){
        SteamMachine[] steamMachines = new SteamMachine[]{SOLID_FUEL_BOILER, LAVA_BOILER, SOLAR_BOILER, STEAM_ALLOY_SMELTER, STEAM_COMPRESSOR, STEAM_CUTTER,
                STEAM_EXTRACTOR, STEAM_FORGE_HAMMER, STEAM_FURNACE, STEAM_MACERATOR, STEAM_SIFTER};
        for (SteamMachine steamMachine : steamMachines){
            steamMachine.getGuiProperties().setGTIcon(BRONZE, GT5RGuiTextures.BRONZE_GT_LOGO);
            steamMachine.getGuiProperties().setGTIcon(STEEL, GT5RGuiTextures.STEEL_GT_LOGO);
        }
        COKE_OVEN.getGuiProperties().setGTIcon(GT5RGuiTextures.PRIMITIVE_GT_LOGO);
        PRIMITIVE_BLAST_FURNACE.getGuiProperties().setGTIcon(GT5RGuiTextures.PRIMITIVE_GT_LOGO);
        addToBackgroundFunction(FORGE_HAMMER, (modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.FORGE_HAMMER_OVERLAY.asWidget().pos(78, 42).size(20, 6));
        });
        addToBackgroundFunction(MACERATOR,(modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.MACERATOR_OVERLAY.asWidget().pos(98, 34).size(1, 1));
        });
        addToBackgroundFunction(ROCK_BREAKER,(modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.MACERATOR_OVERLAY.asWidget().pos(98, 34).size(1, 1));
        });
        addToBackgroundFunction(CHEST_BUFFER,(modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.BUFFER_ARROW.asWidget().pos(62, 60).size(87, 22));
        });
        addToBackgroundFunction(SUPER_BUFFER, (modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.BUFFER_ARROW.asWidget().pos(62, 60).size(87, 22));
            modularPanel.child(GT5RGuiTextures.SUPER_BUFFER_OVERLAY.asWidget().pos(61, 4).size(54, 54));
        });
        addToBackgroundFunction(ELECTRIC_ITEM_FILTER, (modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.WHITE_FILTER_ARROW_BAR.asWidget().pos(6, 28).size(9, 6));
            modularPanel.child(GT5RGuiTextures.ITEM_FILTER_FAKE_SLOTS.asWidget().pos(16, 4).size(54, 54));
            modularPanel.child(GT5RGuiTextures.BLUE_FILTER_ARROW.asWidget().pos(71, 19).size(24, 24));
            modularPanel.child(GT5RGuiTextures.RED_FILTER_ARROW.asWidget().pos(152, 19).size(19, 24));
        });
        addToBackgroundFunction(ELECTRIC_TYPE_FILTER, (modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GT5RGuiTextures.WHITE_TYPE_FILTER_ARROW_BAR.asWidget().pos(6, 28).size(26, 6));
            modularPanel.child(GT5RGuiTextures.TYPE_FILTER_FAKE_SLOT.asWidget().pos(33, 21).size(20, 20));
            modularPanel.child(GT5RGuiTextures.BLUE_FILTER_ARROW.asWidget().pos(54, 19).size(41, 24));
            modularPanel.child(GT5RGuiTextures.RED_FILTER_ARROW.asWidget().pos(152, 19).size(19, 24));
        });
        AUTOCRAFTER.getGuiProperties().setGtIconPos(new int2(80, 64));
        CENTRIFUGE.getGuiProperties().setGtIconPos(new int2(80, 64));
        ELECTROLYZER.getGuiProperties().setGtIconPos(new int2(80, 64));
        COKE_OVEN.getGuiProperties().setTheme(GTCoreThemes.PRIMITIVE_THEME_ID);
        PRIMITIVE_BLAST_FURNACE.getGuiProperties().setTheme(GTCoreThemes.PRIMITIVE_THEME_ID);
        Machine<?>[] tanks = new Machine<?>[]{STEAM_TURBINE, GAS_TURBINE, COMBUSTION_GENERATOR, SEMIFLUID_GENERATOR, MAGIC_ENERGY_CONVERTER, QUANTUM_TANK};
        for (Machine<?> tank : tanks){
            addToBackgroundFunction(tank, (modularPanel, machine, guiData, syncManager, settings) -> {
                modularPanel.child(GT5RGuiTextures.TANK_BACKGROUND.asWidget().pos(53, 16).size(71, 45));
            });
        }
        MultiMachine[] machineWithConsole = new MultiMachine[]{
                ASSEMBLY_LINE, AUTOCRAFTER_ASSEMBLY_LINE, BEDROCK_DRILL, BLAST_FURNACE, IMPLOSION_COMPRESSOR, TREE_GROWTH_SIMULATOR, VACUUM_FREEZER,
                MULTI_SMELTER, LARGE_AUTOCLAVE, LARGE_BOILER, LARGE_BATHING_VAT, LARGE_CENTRIFUGE, LARGE_CHEMICAL_REACTOR, LARGE_ELECTROLYZER,
                LARGE_PULVERIZER, LARGE_ORE_WASHER, LARGE_SIFTER, LARGE_TURBINE, LARGE_HEAT_EXCHANGER, OIL_DRILLING_RIG, ORE_MINING_RIG,
                PROCESSING_ARRAY, PYROLYSE_OVEN, COMBUSTION_ENGINE, DISTLLATION_TOWER, CRYO_DISTLLATION_TOWER, CRACKING_UNIT
        };
        for (MultiMachine multiMachine : machineWithConsole){
            addToBackgroundFunction(multiMachine, (modularPanel, machine, guiData, syncManager, settings) -> {
                modularPanel.child(GT5RGuiTextures.MULTIBLOCK_BACKGROUND.asWidget().pos(7, 4).size(143, 75));
            });
        }
    }

    private static void addToBackgroundFunction(Machine<?> machine, IPanelFunction newFunction){
        IPanelFunction oldFunction = machine.getBackgroundFunction();
        machine.setBackgroundFunction((modularPanel, machine1, guiData, syncManager, settings) -> {
            oldFunction.modifyPanel(modularPanel, machine1, guiData, syncManager, settings);
            newFunction.modifyPanel(modularPanel, machine1, guiData, syncManager, settings);
        });
    }

    public static void machineData(){
        ASSEMBLER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ASSEMBLER_PROGRESS);
        CANNER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CANNER_PROGRESS);
        CIRCUIT_ASSEMBLER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ASSEMBLER_PROGRESS);
        COMPRESSOR.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.COMPRESSOR_PROGRESS);
        CUTTER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CUTTER_PROGRESS);
        EXTRACTOR.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.EXTRACTOR_PROGRESS);
        EXTRUDER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.EXTRUDER_PROGRESS);
        LATHE.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.LATHE_PROGRESS);
        MACERATOR.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.MACERATOR_PROGRESS);
        ROCK_BREAKER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.MACERATOR_PROGRESS);
        WIRE_MILL.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.WIREMILL_PROGRESS);
        CENTRIFUGE.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.EXTRACTOR_PROGRESS);
        ELECTROLYZER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.EXTRACTOR_PROGRESS);
        ORE_WASHER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ORE_WASHER_PROGRESS).setDir(BarDir.CW);
        CHEMICAL_REACTOR.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CHEMICAL_REACTOR_PROGRESS);
        FLUID_CANNER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CANNER_PROGRESS);
        FERMENTER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CHEMICAL_REACTOR_PROGRESS);
        FLUID_PRESS.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.EXTRACTOR_PROGRESS);
        SMELTER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.SMELTER_PROGRESS);
        DISTILLERY.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.CHEMICAL_REACTOR_PROGRESS);
        BATH.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ORE_WASHER_PROGRESS).setDir(BarDir.CW);
        POLARIZER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ELECTROMAGNETIC_SEPARATOR_PROGRESS);
        MIXER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.MIXER_PROGRESS).setDir(BarDir.CW);
        FORMING_PRESS.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.COMPRESSOR_PROGRESS);
        SIFTER.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.SIFTER_PROGRESS);
        ELECTROMAGNETIC_SEPARATOR.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.ELECTROMAGNETIC_SEPARATOR_PROGRESS);
        COKE_OVEN.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.COKE_OVEN_PROGRESS);
        PRIMITIVE_BLAST_FURNACE.getGuiProperties().getMachineData().setProgressLocation(GT5RGuiTextures.COKE_OVEN_PROGRESS);
        FORGE_HAMMER.setGuiProgressBarForJEI(BarDir.DOWN, false).getGuiProperties().getMachineData().setMachineStatePos(84, 46).setProgressLocation(GT5RGuiTextures.FORGE_HAMMER_PROGRESS);
        STEAM_FORGE_HAMMER.setGuiProgressBarForJEI(BarDir.DOWN, false).getGuiProperties().getMachineData().setMachineStatePos(80, 50);
        FUSION_REACTOR.getGuiProperties().setEnablePlayerSlots(false)
                .getMachineData().setProgressLocation(GT5RGuiTextures.FUSION_REACTOR_PROGRESS).setProgressPos(163, 4).setProgressSize(149, 16);
    }

    public static void widgets(){
        PRIMITIVE_BLAST_FURNACE.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (machine instanceof IFuelMachine fuelMachine){
                syncManager.syncValue("fuel", new DoubleSyncValue(() -> (double) fuelMachine.getFuel() / fuelMachine.getMaxFuel()));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.PBF_FLAME_OFF, GT5RGuiTextures.FLAME_ON, Direction.UP)
                        .syncHandler("fuel").pos(79, 51).size(18, 18));
            }
        });
        SOLID_FUEL_BOILER.getGuiProperties().getMachineData().setHasProgressWidget(false).setHasMachineStateWidget(false);
        SOLID_FUEL_BOILER.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (machine instanceof BlockEntityCoalBoiler fuelMachine){
                Tier tier = machine.getMachineTier();
                syncManager.syncValue("fuel", new DoubleSyncValue(() -> (double) fuelMachine.getFuel() / fuelMachine.getMaxFuel()));
                syncManager.syncValue("fuelInt", new IntSyncValue(fuelMachine::getFuel));
                syncManager.syncValue("heat", new IntSyncValue(fuelMachine::getHeat));
                syncManager.syncValue("maxHeat", new IntSyncValue(fuelMachine::getMaxHeat));
                syncManager.syncValue("steam", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getOutputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                syncManager.syncValue("water", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getInputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                Function<String, Integer> intGetter = s -> GTMuiUtils.getSyncedValue(s, Integer.class, syncManager.getModularSyncManager()).orElse(0);
                modularPanel.child((tier == BRONZE ? GT5RGuiTextures.BRONZE_TANK_ICON : GT5RGuiTextures.STEEL_TANK_ICON).asWidget().pos(43, 43).size(18, 18));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_FLAME_OFF : GT5RGuiTextures.STEEL_FLAME_OFF, GT5RGuiTextures.FLAME_ON, Direction.UP)
                        .syncHandler("fuel")
                        .tooltip(t -> t.addLine(Utils.literal("Show Recipes")))
                        .tooltipDynamic(t -> t.addLine(Utils.literal("Fuel: " + intGetter.apply("fuelInt")))).tooltipAutoUpdate(true)
                        .pos(115, 43).size(18, 18));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_STEAM_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("steam") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int steam = intGetter.apply("steam");
                            if (steam > 0){
                                tooltip.addLine(Utils.literal("Steam: " + steam + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(70, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_WATER_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("water") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int water = intGetter.apply("water");
                            if (water > 0){
                                tooltip.addLine(Utils.literal("Water: " + water + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(83, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_HEAT_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("heat") / intGetter.apply("maxHeat"))
                        .tooltipDynamic(tooltip -> {
                            int heat = intGetter.apply("heat");
                            int maxHeat = intGetter.apply("maxHeat");
                            tooltip.addLine(Utils.literal("Heat: " + heat + "C° out of " + maxHeat));
                        }).tooltipAutoUpdate(true)
                        .pos(96, 25).size(10, 54));
            }
        });

        LAVA_BOILER.getGuiProperties().getMachineData().setHasProgressWidget(false).setHasMachineStateWidget(false);
        LAVA_BOILER.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (machine instanceof BlockEntityLavaBoiler fuelMachine){
                Tier tier = machine.getMachineTier();
                syncManager.syncValue("heat", new IntSyncValue(fuelMachine::getHeat));
                syncManager.syncValue("maxHeat", new IntSyncValue(fuelMachine::getMaxHeat));
                syncManager.syncValue("steam", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getOutputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                syncManager.syncValue("water", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getInputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                syncManager.syncValue("lava", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getInputTanks().getFluidInTank(1).getAmount()).orElse(0)));
                Function<String, Integer> intGetter = s -> GTMuiUtils.getSyncedValue(s, Integer.class, syncManager.getModularSyncManager()).orElse(0);
                modularPanel.child(GT5RGuiTextures.STEEL_TANK_ICON.asWidget().pos(43, 43).size(18, 18));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_STEAM_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("steam") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int steam = intGetter.apply("steam");
                            if (steam > 0){
                                tooltip.addLine(Utils.literal("Steam: " + steam + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(70, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_WATER_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("water") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int water = intGetter.apply("water");
                            if (water > 0){
                                tooltip.addLine(Utils.literal("Water: " + water + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(83, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_HEAT_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("heat") / intGetter.apply("maxHeat"))
                        .tooltipDynamic(tooltip -> {
                            int heat = intGetter.apply("heat");
                            int maxHeat = intGetter.apply("maxHeat");
                            tooltip.addLine(Utils.literal("Heat: " + heat + "C° out of " + maxHeat));
                        }).tooltipAutoUpdate(true)
                        .pos(96, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_LAVA_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("lava") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int lava = intGetter.apply("lava");
                            if (lava > 0){
                                tooltip.addLine(Utils.literal("Lava: " + lava + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(122, 25).size(10, 54));
            }
        });

        SOLAR_BOILER.getGuiProperties().getMachineData().setHasProgressWidget(false).setHasMachineStateWidget(false);
        SOLAR_BOILER.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            if (machine instanceof BlockEntitySolarBoiler fuelMachine){
                Tier tier = machine.getMachineTier();
                syncManager.syncValue("sunlit", new BooleanSyncValue(fuelMachine::isAllowedToWork));
                syncManager.syncValue("heat", new IntSyncValue(fuelMachine::getHeat));
                syncManager.syncValue("maxHeat", new IntSyncValue(fuelMachine::getMaxHeat));
                syncManager.syncValue("steam", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getOutputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                syncManager.syncValue("water", new IntSyncValue(() -> machine.fluidHandler.map(f -> f.getInputTanks().getFluidInTank(0).getAmount()).orElse(0)));
                Function<String, Integer> intGetter = s -> GTMuiUtils.getSyncedValue(s, Integer.class, syncManager.getModularSyncManager()).orElse(0);
                modularPanel.child(GT5RGuiTextures.BRONZE_TANK_ICON.asWidget().pos(43, 43).size(18, 18));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(GT5RGuiTextures.SOLAR_BOILER_ICON, Direction.UP)
                        .clientValue(() -> {
                            boolean isSunlit = GTMuiUtils.getSyncedValue("sunlit", Boolean.class, syncManager.getModularSyncManager()).orElse(false);
                            return isSunlit ? 1.0 : 0.0;
                        }).pos(131, 45).size(12, 12));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_STEAM_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("steam") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int steam = intGetter.apply("steam");
                            if (steam > 0){
                                tooltip.addLine(Utils.literal("Steam: " + steam + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(70, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_WATER_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("water") / 16000)
                        .tooltipDynamic(tooltip -> {
                            int water = intGetter.apply("water");
                            if (water > 0){
                                tooltip.addLine(Utils.literal("Water: " + water + " MB"));
                            }
                        }).tooltipAutoUpdate(true)
                        .pos(83, 25).size(10, 54));
                modularPanel.child(new brachy.modularui.widgets.ProgressWidget().texture(tier == BRONZE ? GT5RGuiTextures.BRONZE_BOILER_EMPTY_BAR : GT5RGuiTextures.STEEL_BOILER_EMPTY_BAR, GT5RGuiTextures.BOILER_HEAT_BAR, Direction.UP)
                        .clientValue(() -> (double)intGetter.apply("heat") / intGetter.apply("maxHeat"))
                        .tooltipDynamic(tooltip -> {
                            int heat = intGetter.apply("heat");
                            int maxHeat = intGetter.apply("maxHeat");
                            tooltip.addLine(Utils.literal("Heat: " + heat + "C° out of " + maxHeat));
                        }).tooltipAutoUpdate(true)
                        .pos(96, 25).size(10, 54));
            }
        });
        ADJUSTABLE_TRANSFORMER.getGuiProperties().setHasGTIcon(false);
        ADJUSTABLE_TRANSFORMER.getGuiFunctions().add(((modularPanel, machine, guiData, syncManager, settings) -> {
            modularPanel.child(GTGuiTextures.CREATIVE_GENERATOR_OVERLAY.asWidget().size(158, 61).pos(9, 17));
            for (int i = 0; i < 16; i++){
                boolean leftSide = i < 8;
                boolean leftOuter = i % 2 == 0;
                UITexture texture = leftSide ? (leftOuter ? GTGuiTextures.APAD_LEFT : GTGuiTextures.PAD_LEFT) : (leftOuter ? GTGuiTextures.PAD_RIGHT : GTGuiTextures.APAD_RIGHT);
                int x = leftSide ? (leftOuter ? 10 : 25) : (leftOuter ? 137 : 152);
                int y = (i < 8 ? i : i - 8) / 2;
                int finalI = i;
                modularPanel.child(new ButtonWidget<>()
                        .overlay(texture.getSubArea(0f, 0f, 1.0f, 0.5f))
                        .hoverOverlay(texture.getSubArea(0f, 0.5f, 1f, 1f))
                        .onMousePressed((context, mouseButton) -> {
                            syncManager.callSyncedAction("extra_button_event", packet -> {
                                packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, finalI});
                            });
                            return true;
                        })
                        .size(14).pos(x, 18 + (15 * y)));
            }
            if (machine instanceof IInfoRenderer renderer){
                renderer.registerSyncHandlers(syncManager);
                modularPanel.child(new GTInfoRenderWidget(renderer)
                        .pos(renderer.getPos().x, renderer.getPos().y)
                        .size(renderer.getSize().x, renderer.getSize().y));
            }
        }));

        AUTOCRAFTER.getGuiProperties().getMachineData().setHasProgressWidget(false);
        AUTOCRAFTER.getGuiFunctions().add((modularPanel, machine, guiData, syncManager, settings) -> {
            GuiProperties guiProperties = AUTOCRAFTER.getGuiProperties();
            syncManager.syncValue("progress", new DoubleSyncValue(() -> machine.recipeHandler.map(r -> guiProperties.getMachineData().getProgressPercentFunction().apply(r.getCurrentProgress(), r.getMaxProgress())).orElse(0f)));
            BarDir direction = guiProperties.getMachineData().getDir();
            UITexture texture = guiProperties.getMachineData().getProgressTexture(machine.getMachineTier());
            brachy.modularui.widgets.ProgressWidget progressWidget = new org.gtreimagined.gt5r.mui.widgets.AutocrafterProgressWidget(machine.getMachineType(), machine.getMachineTier())
                    .tooltip(t -> t.addLine(Utils.translatable("gtlib.gui.show_recipes")))
                    .syncHandler("progress")
                    .pos(guiProperties.getMachineData().getProgressPos().x + 6, guiProperties.getMachineData().getProgressPos().y + 6);
            modularPanel.child(progressWidget);
            if (!direction.isCircular()) {
                progressWidget.texture(texture, direction.toRegularDirection());
            } else {
                progressWidget.progress(CompositeProgress.circularLike4Slice(
                        texture.getSubArea(0.0f, 0.0f, 1f, 0.5f),
                        texture.getSubArea(0f, 0.5f,1f, 1f),
                        direction.toCircularDirection()
                ));
            }
        });
        //ELECTRIC_ITEM_FILTER.getCallbacks().remove(1);
        //ELECTRIC_TYPE_FILTER.getCallbacks().remove(1);
        //CHEST_BUFFER.getCallbacks().remove(1);
        /*FUSION_REACTOR.addGuiCallback(t -> {
            t.addButton(155, 23, ButtonOverlay.NO_OVERLAY, false).addButton(155, 41, ButtonOverlay.NO_OVERLAY, false).addButton(155, 59, ButtonOverlay.NO_OVERLAY, false).addWidget(makeProgress()).addWidget(FusionButtonWidget.build());
        });*/
    }

    // }
}
