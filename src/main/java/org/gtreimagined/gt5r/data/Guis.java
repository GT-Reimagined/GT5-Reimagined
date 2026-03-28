package org.gtreimagined.gt5r.data;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtcore.data.SlotTypes;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.cover.CoverOutput;
import org.gtreimagined.gtlib.gui.BarDir;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.GuiData;
import org.gtreimagined.gtlib.gui.MenuHandlerMachine;
import org.gtreimagined.gtlib.gui.container.ContainerBasicMachine;
import org.gtreimagined.gtlib.gui.container.ContainerMachine;
import org.gtreimagined.gtlib.gui.slot.ISlotProvider;
import org.gtreimagined.gtlib.gui.widget.FuelWidget;
import org.gtreimagined.gtlib.gui.widget.IOWidget;
import org.gtreimagined.gtlib.gui.widget.IconWidget;
import org.gtreimagined.gtlib.gui.widget.MachineStateWidget;
import org.gtreimagined.gtlib.gui.widget.ProgressWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityCoalBoiler;
import org.gtreimagined.gt5r.gui.widgets.AutocrafterProgressWidget;
import org.gtreimagined.gt5r.gui.widgets.CoalBoilerFuelWidget;
import org.gtreimagined.gt5r.gui.widgets.CoalBoilerWidget;
import org.gtreimagined.gt5r.gui.widgets.FusionButtonWidget;
import org.gtreimagined.gt5r.gui.widgets.LavaBoilerWidget;
import org.gtreimagined.gt5r.gui.widgets.SolarBoilerWidget;

import static org.gtreimagined.gtcore.data.SlotTypes.PARK;
import static org.gtreimagined.gtlib.gui.SlotType.*;
import static org.gtreimagined.gtlib.gui.Widget.builder;
import static org.gtreimagined.gtlib.machine.Tier.*;
import static org.gtreimagined.gt5r.data.GT5RMachines.*;

public class Guis {

    // TODO move these to the API somehow
    public static GuiData MULTI_DISPLAY = new GuiData(GT5Reimagined.ID, "multi_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 17, 34).add(IT_IN, 35, 34)
            .add(IT_IN, 53, 34).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16).add(IT_OUT, 107, 34)
            .add(IT_OUT, 125, 34).add(IT_OUT, 143, 34).add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 63).add(FL_OUT, 125, 63).add(FL_OUT, 143, 63));

    public static GuiData ALLOY_SMELTER_DISPLAY = new GuiData(GT5Reimagined.ID, "alloy_smelter").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 35, 16).add(IT_IN, 53, 16).add(IT_IN, 35, 34).add(IT_IN, 53, 34)
            .add(IT_OUT, 107, 25));

    public static GuiData SIMPLE_DISPLAY = new GuiData(GT5Reimagined.ID, "simple_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 53, 25).add(IT_OUT, 107, 25));
    public static GuiData BEDROCK_DRILL_DISPLAY = new GuiData(GT5Reimagined.ID, "simple_display").setSlots(ISlotProvider.DEFAULT()
            .add(IT_IN, 53, 25).add(FL_IN, 53, 63)
            .add(IT_OUT, 107, 7).add(IT_OUT, 125, 7).add(IT_OUT, 143, 7)
            .add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(IT_OUT, 143, 25)
            .add(IT_OUT, 107, 43).add(IT_OUT, 125, 43).add(IT_OUT, 143, 43)
            .add(IT_OUT, 107, 61).add(IT_OUT, 125, 61).add(IT_OUT, 143, 61));

    public static GuiData MULTI_DISPLAY_FLUID = new GuiData(GT5Reimagined.ID, "multi_display_fluid").setSlots(ISlotProvider.DEFAULT()
            .add(FL_IN, 17, 63).add(FL_IN, 35, 63).add(FL_IN, 53, 63)
            .add(FL_OUT, 107, 7).add(FL_OUT, 125, 7).add(FL_OUT, 143, 7)
            .add(FL_OUT, 107, 25).add(FL_OUT, 125, 25).add(FL_OUT, 143, 25)
            .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
            .add(FL_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61));

    public static GuiData MULTI_DISPLAY_DISTILLATION = new GuiData(GT5Reimagined.ID, "multi_display_distillation").setSlots(ISlotProvider.DEFAULT()
            .add(FL_IN, 53, 43)
            .add(IT_OUT, 107, 61).add(FL_OUT, 125, 61).add(FL_OUT, 143, 61)
            .add(FL_OUT, 107, 43).add(FL_OUT, 125, 43).add(FL_OUT, 143, 43)
            .add(FL_OUT, 107, 25).add(FL_OUT, 125, 25).add(FL_OUT, 143, 25)
            .add(FL_OUT, 107, 7).add(FL_OUT, 125, 7).add(FL_OUT, 143, 7));
    public static GuiData MULTI_DISPLAY_COMPACT = new GuiData(GT5Reimagined.ID, "multi_display")
            .setSlots(ISlotProvider.DEFAULT().add(MULTI_DISPLAY.getSlots()));
    public static GuiData BASIC_TANK = new GuiData(GT5Reimagined.ID, "basic_tank").setBackgroundTexture("basic_tank")
            .setSlots(ISlotProvider.DEFAULT().add(CELL_IN, 8, 17).add(CELL_OUT, 8, 53).add(FL_IN, 55, 43, new ResourceLocation(GT5Reimagined.ID, "blank")));

    public static GuiData MULTIBLOCK = new GuiData(GT5Reimagined.ID, "multiblock").setBackgroundTexture("multiblock").setSlots(ISlotProvider.DEFAULT().add(STORAGE, 152, 5));

    public static GuiData ORE_BYPRODUCTS = new GuiData(GT5Reimagined.ID, "ore_byproducts") {
        @Override
        public ResourceLocation getTexture(Tier tier, String type) {
            return new ResourceLocation(loc.getNamespace(), "textures/gui/" + loc.getPath() + ".png");
        }
    }.setSlots(ISlotProvider.DEFAULT().add(IT_IN, 17, 16).add(IT_IN, 35, 16).add(IT_IN, 53, 16)
                    .add(IT_IN, 17, 34).add(IT_IN, 35, 34).add(IT_IN, 53, 34).add(IT_OUT, 107, 16).add(IT_OUT, 125, 16)
                    .add(IT_OUT, 142, 16).add(IT_OUT, 107, 34).add(IT_OUT, 125, 34).add(IT_OUT, 143, 34));

    public static MenuHandlerMachine<BlockEntityCoalBoiler, ? extends ContainerMachine> COAL_BOILER_MENU_HANDLER = new MenuHandlerMachine(
            Ref.ID, "container_coal_boiler") {
        @Override
        public ContainerBasicMachine getMenu(IGuiHandler tile, Inventory playerInv, int windowId) {
            return tile instanceof BlockEntityMachine
                    ? new ContainerBasicMachine((BlockEntityMachine<?>) tile, playerInv, this, windowId)
                    : null;
        }

        @Override
        public String screenDomain() {
            return GT5Reimagined.ID;
        }

        @Override
        public String screenID() {
            return "coal";
        }
    };

    public static void init(Dist side) {
        slots();
        backgroundTextures();
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
        AUTOCLAVE.add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 16).add(IT_OUT, 125, 16)
                .add(IT_OUT, 107, 34).add(IT_OUT, 125, 34)
                .add(ENERGY, 80, 63).add(FL_IN, 53, 63);
        ResourceLocation craft = new ResourceLocation(GT5Reimagined.ID, "crafting");
        AUTOCRAFTER.add(IT_IN, 17, 7, craft).add(IT_IN, 35, 7).add(IT_IN, 53, 7, craft)
                .add(IT_IN, 17, 25).add(IT_IN, 35, 25, craft).add(IT_IN, 53, 25)
                .add(IT_IN, 17, 43, craft).add(IT_IN, 35, 43).add(IT_IN, 53, 43, craft)
                .add(STORAGE, 53, 63, new ResourceLocation(GT5Reimagined.ID, "blueprint"))
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
        COKE_OVEN.add(IT_IN, 53, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_ingot"))
                .add(IT_OUT, 107, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_ingot"))
                .add(FL_OUT, 125, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_cell"));
        ResourceLocation bat = new ResourceLocation(GT5Reimagined.ID, "battery");
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

        SOLID_FUEL_BOILER.add(CELL_IN, 44, 26).add(CELL_OUT, 44, 62).add(IT_OUT, 116, 26).add(IT_IN, 116, 62);
        LAVA_BOILER.add(CELL_IN, 44, 26).add(CELL_OUT, 44, 62);
        SOLAR_BOILER.add(CELL_IN, 44, 26).add(CELL_OUT, 44, 62);

        STEAM_ALLOY_SMELTER.add(IT_IN, 35, 25).add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_COMPRESSOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(COMPRESSOR).add(FL_IN, 53, 63);
        STEAM_FURNACE.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FURNACE).add(FL_IN, 53, 63);
        STEAM_EXTRACTOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(EXTRACTOR).add(FL_IN, 53, 63);
        STEAM_MACERATOR.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(MACERATOR).add(FL_IN, 53, 63);
        STEAM_FORGE_HAMMER.add(IT_IN, 53, 25).add(IT_OUT, 107, 25).add(PARK, 80, 63).add(FORGE_HAMMER).add(FL_IN, 53, 63);
        STEAM_CUTTER.add(IT_IN, 53, 25).add(FL_IN, 53, 63).add(IT_OUT, 107, 25).add(IT_OUT, 125, 25).add(PARK, 80, 63).add(FL_IN, 35, 63);
        STEAM_SIFTER.add(IT_IN, 53, 25)
                .add(IT_OUT, 107, 16).add(IT_OUT, 125, 16).add(IT_OUT, 143, 16)
                .add(IT_OUT, 107, 34).add(IT_OUT, 125, 34).add(IT_OUT, 143, 34)
                .add(PARK, 80, 63).add(FL_IN, 53, 63);
        STEAM_TURBINE.add(BASIC_TANK.getSlots());
        GAS_TURBINE.add(BASIC_TANK.getSlots());
        COMBUSTION_GENERATOR.add(BASIC_TANK.getSlots());
        SEMIFLUID_GENERATOR.add(BASIC_TANK.getSlots());
        MAGIC_ENERGY_CONVERTER.add(IT_IN, 8, 17).add(IT_OUT, 8, 53).add(FL_IN, 55, 43, new ResourceLocation(GT5Reimagined.ID, "blank"));
        NUCLEAR_REACTOR_CORE.add(STORAGE, 70, 25).add(STORAGE, 70, 43).add(STORAGE, 88, 25).add(STORAGE, 88, 43).add(FL_IN, 70, 61).add(FL_OUT, 88, 61);

        CROP_HARVESTER.add(IT_OUT, 62, 16).add(IT_OUT, 80, 16).add(IT_OUT, 98, 16)
                .add(IT_OUT, 62, 34).add(IT_OUT, 80, 34).add(IT_OUT, 98, 34)
                .add(IT_OUT, 62, 52).add(IT_OUT, 80, 52).add(IT_OUT, 98, 52);

        QUANTUM_TANK.add(BASIC_TANK.getSlots());
        PRIMITIVE_BLAST_FURNACE.add(IT_IN, 53, 16, new ResourceLocation(GT5Reimagined.ID, "primitive_ingot"))
                .add(IT_IN, 53, 34, new ResourceLocation(GT5Reimagined.ID, "primitive_fire"))
                .add(IT_IN, 53, 52, new ResourceLocation(GT5Reimagined.ID, "primitive_fire"))
                .add(IT_OUT, 107, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_ingot"))
                .add(IT_OUT, 125, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_dust"))
                .add(IT_OUT, 143, 25, new ResourceLocation(GT5Reimagined.ID, "primitive_dust"));

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
                .add(DISPLAY_SETTABLE, 18, 6, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 35, 6, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 52, 6, new ResourceLocation(GT5Reimagined.ID, "blank"))
                .add(DISPLAY_SETTABLE, 18, 23, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 35, 23, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 52, 23, new ResourceLocation(GT5Reimagined.ID, "blank"))
                .add(DISPLAY_SETTABLE, 18, 40, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 35, 40, new ResourceLocation(GT5Reimagined.ID, "blank")).add(DISPLAY_SETTABLE, 52, 40, new ResourceLocation(GT5Reimagined.ID, "blank"))
                .add(STORAGE, 98, 5).add(STORAGE, 98 + 18, 5)
                .add(STORAGE, 98 + 18 * 2, 5)
                .add(STORAGE, 98, 23).add(STORAGE, 98 + 18, 23)
                .add(STORAGE, 98 + 18 * 2, 23)
                .add(STORAGE, 98, 41).add(STORAGE, 98 + 18, 41)
                .add(STORAGE, 98 + 18 * 2, 41);

        ELECTRIC_TYPE_FILTER
                .add(DISPLAY_SETTABLE, 35, 23, new ResourceLocation(GT5Reimagined.ID, "blank"))
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

    public static void backgroundTextures(){
        MACERATOR.getGuiData().setBackgroundTexture("machine_macerator");
        ROCK_BREAKER.getGuiData().setBackgroundTexture("machine_macerator");
        //FORGE_HAMMER.getGui().setBackgroundTexture("machine_forge_hammer");
        AUTOCRAFTER.getGuiData().setBackgroundTexture("centrifuge");
        CENTRIFUGE.getGuiData().setBackgroundTexture("centrifuge");
        ELECTROLYZER.getGuiData().setBackgroundTexture("centrifuge");
        COKE_OVEN.getGuiData().setBackgroundTexture("coke_oven");
        PRIMITIVE_BLAST_FURNACE.getGuiData().setBackgroundTexture("primitive_blast_furnace");
        SUPER_BUFFER.getGuiData().setBackgroundTexture("super_buffer");
        CHEST_BUFFER.getGuiData().setBackgroundTexture("chest_buffer");
        COKE_OVEN.getGuiData().setBackgroundTexture("coke_oven");
        STEAM_TURBINE.getGuiData().setBackgroundTexture("basic_tank");
        GAS_TURBINE.getGuiData().setBackgroundTexture("basic_tank");
        COMBUSTION_GENERATOR.getGuiData().setBackgroundTexture("basic_tank");
        SEMIFLUID_GENERATOR.getGuiData().setBackgroundTexture("basic_tank");
        MAGIC_ENERGY_CONVERTER.getGuiData().setBackgroundTexture("basic_tank");
        QUANTUM_TANK.getGuiData().setBackgroundTexture("basic_tank");
        ELECTRIC_ITEM_FILTER.getGuiData().setBackgroundTexture("electric_item_filter");
        ELECTRIC_TYPE_FILTER.getGuiData().setBackgroundTexture("electric_type_filter");
        ASSEMBLY_LINE.getGuiData().setBackgroundTexture("multiblock");
        AUTOCRAFTER_ASSEMBLY_LINE.getGuiData().setBackgroundTexture("multiblock");
        BEDROCK_DRILL.getGuiData().setBackgroundTexture("multiblock");
        BLAST_FURNACE.getGuiData().setBackgroundTexture("multiblock");
        IMPLOSION_COMPRESSOR.getGuiData().setBackgroundTexture("multiblock");
        TREE_GROWTH_SIMULATOR.getGuiData().setBackgroundTexture("multiblock");
        VACUUM_FREEZER.getGuiData().setBackgroundTexture("multiblock");
        MULTI_SMELTER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_AUTOCLAVE.getGuiData().setBackgroundTexture("multiblock");
        LARGE_BOILER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_BATHING_VAT.getGuiData().setBackgroundTexture("multiblock");
        LARGE_CENTRIFUGE.getGuiData().setBackgroundTexture("multiblock");
        LARGE_CHEMICAL_REACTOR.getGuiData().setBackgroundTexture("multiblock");
        LARGE_ELECTROLYZER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_PULVERIZER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_ORE_WASHER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_SIFTER.getGuiData().setBackgroundTexture("multiblock");
        LARGE_TURBINE.getGuiData().setBackgroundTexture("multiblock");
        LARGE_HEAT_EXCHANGER.getGuiData().setBackgroundTexture("multiblock");
        OIL_DRILLING_RIG.getGuiData().setBackgroundTexture("multiblock");
        ORE_MINING_RIG.getGuiData().setBackgroundTexture("multiblock");
        PROCESSING_ARRAY.getGuiData().setBackgroundTexture("multiblock");
        PYROLYSE_OVEN.getGuiData().setBackgroundTexture("multiblock");
        COMBUSTION_ENGINE.getGuiData().setBackgroundTexture("multiblock");
        DISTLLATION_TOWER.getGuiData().setBackgroundTexture("multiblock");
        CRYO_DISTLLATION_TOWER.getGuiData().setBackgroundTexture("multiblock");
        CRACKING_UNIT.getGuiData().setBackgroundTexture("multiblock");
        FUSION_REACTOR.setGUI(MenuHandlers.FUSION_MENU_HANDLER);
        FUSION_REACTOR.getGuiData().setBackgroundTexture("fusion_control_computer");
    }

    public static void machineData(){
        ASSEMBLER.getGuiData().getMachineData().setProgressLocation("assembler");
        CANNER.getGuiData().getMachineData().setProgressLocation("canner");
        CIRCUIT_ASSEMBLER.getGuiData().getMachineData().setProgressLocation("assembler");
        COMPRESSOR.getGuiData().getMachineData().setProgressLocation("compressor");
        CUTTER.getGuiData().getMachineData().setProgressLocation("cutter");
        EXTRACTOR.getGuiData().getMachineData().setProgressLocation("extractor");
        EXTRUDER.getGuiData().getMachineData().setProgressLocation("extruder");
        LATHE.getGuiData().getMachineData().setProgressLocation("lathe");
        MACERATOR.getGuiData().getMachineData().setProgressLocation("macerator");
        ROCK_BREAKER.getGuiData().getMachineData().setProgressLocation("macerator");
        WIRE_MILL.getGuiData().getMachineData().setProgressLocation("wiremill");
        CENTRIFUGE.getGuiData().getMachineData().setProgressLocation("extractor");
        ELECTROLYZER.getGuiData().getMachineData().setProgressLocation("extractor");
        ORE_WASHER.getGuiData().getMachineData().setProgressLocation("ore_washer");
        CHEMICAL_REACTOR.getGuiData().getMachineData().setProgressLocation("chemical_reactor");
        FLUID_CANNER.getGuiData().getMachineData().setProgressLocation("canner");
        FERMENTER.getGuiData().getMachineData().setProgressLocation("chemical_reactor");
        FLUID_PRESS.getGuiData().getMachineData().setProgressLocation("extractor");
        SMELTER.getGuiData().getMachineData().setProgressLocation("smelter");
        DISTILLERY.getGuiData().getMachineData().setProgressLocation("chemical_reactor");
        BATH.getGuiData().getMachineData().setProgressLocation("ore_washer");
        POLARIZER.getGuiData().getMachineData().setProgressLocation("electromagnetic_separator");
        MIXER.getGuiData().getMachineData().setProgressLocation("mixer");
        FORMING_PRESS.getGuiData().getMachineData().setProgressLocation("compressor");
        SIFTER.getGuiData().getMachineData().setProgressLocation("sifter");
        ELECTROMAGNETIC_SEPARATOR.getGuiData().getMachineData().setProgressLocation("electromagnetic_separator");
        COKE_OVEN.getGuiData().getMachineData().setProgressLocation("coke_oven");
        PRIMITIVE_BLAST_FURNACE.getGuiData().getMachineData().setProgressLocation("coke_oven");
        FORGE_HAMMER.setGuiProgressBarForJEI(BarDir.BOTTOM, false).getGuiData().getMachineData().setMachineStatePos(84, 46).setProgressLocation("forge_hammer");
        STEAM_FORGE_HAMMER.setGuiProgressBarForJEI(BarDir.BOTTOM, false).getGuiData().getMachineData().setMachineStatePos(80, 50);
        FUSION_REACTOR.getGuiData().setEnablePlayerSlots(false)
                .getMachineData().setProgressLocation("fusion_reactor").setProgressPos(163, 4).setProgressSize(149, 16);
    }

    public static void widgets(){
        FORGE_HAMMER.addGuiCallback(t -> {
            t.addWidget(IconWidget.build(new ResourceLocation(GT5Reimagined.ID, "textures/gui/button/forge_hammer_overlay.png"), 78, 42, 20, 6));
        });
        PRIMITIVE_BLAST_FURNACE.addGuiCallback(t -> {
            t.addWidget(FuelWidget.build(new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/pbf_flame_off.png"), new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/flame_on.png")).setSize(79, 51, 18, 18));
        });
        SOLID_FUEL_BOILER.addGuiCallback(t -> {
            String tier = ((BlockEntityMachine<?>)t.handler).getMachineTier().getId();
            t.addWidget(CoalBoilerWidget.build().setSize(70, 25, 36, 54))
                    .addWidget(FuelWidget.build(new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/" + tier + "_flame_off.png"), new ResourceLocation(GT5Reimagined.ID, "textures/gui/icon/flame_on.png")).setSize(115, 43, 18, 18));
        });

        LAVA_BOILER.addGuiCallback(t -> {
            t.addWidget(LavaBoilerWidget.build().setSize(70, 25, 62, 54));
        });

        SOLAR_BOILER.addGuiCallback(t -> {
            t.addWidget(SolarBoilerWidget.build().setSize(70, 25, 62, 54));
        });
        ADJUSTABLE_TRANSFORMER.getGuiData().setBackgroundTexture(new ResourceLocation(Ref.ID, "creative_generator"));
        // if (side.isClient()) {
        ADJUSTABLE_TRANSFORMER.addGuiCallback(t -> {
            t.addButton(10, 18, ButtonOverlay.APAD_LEFT, false)
                    .addButton(25, 18, ButtonOverlay.PAD_LEFT, false)
                    .addButton(10, 33, ButtonOverlay.APAD_LEFT, false)
                    .addButton(25, 33, ButtonOverlay.PAD_LEFT, false)
                    .addButton(10, 48, ButtonOverlay.APAD_LEFT, false)
                    .addButton(25, 48, ButtonOverlay.PAD_LEFT, false)
                    .addButton(10, 63, ButtonOverlay.APAD_LEFT, false)
                    .addButton(25, 63, ButtonOverlay.PAD_LEFT, false)
                    .addButton(137, 18, ButtonOverlay.PAD_RIGHT, false)
                    .addButton(152, 18, ButtonOverlay.APAD_RIGHT, false)
                    .addButton(137, 33, ButtonOverlay.PAD_RIGHT, false)
                    .addButton(152, 33, ButtonOverlay.APAD_RIGHT, false)
                    .addButton(137, 48, ButtonOverlay.PAD_RIGHT, false)
                    .addButton(152, 48, ButtonOverlay.APAD_RIGHT, false)
                    .addButton(137, 63, ButtonOverlay.PAD_RIGHT, false)
                    .addButton(152, 63, ButtonOverlay.APAD_RIGHT, false);
        });

        AUTOCRAFTER.getCallbacks().remove(1);
        AUTOCRAFTER.addGuiCallback(t -> {
            t.addWidget(AutocrafterProgressWidget.build())
                    .addWidget(MachineStateWidget.build());
            t.addWidget(IOWidget.build(9, 63).onlyIf(u -> u.handler instanceof BlockEntityMachine<?> machine &&
                    machine.getOutputFacing() != null &&
                    machine.coverHandler.map(c -> c.getOutputCover() instanceof CoverOutput).orElse(false) &&
                    !(u.handler instanceof BlockEntityMultiMachine<?>)));
        });
        ELECTRIC_ITEM_FILTER.getCallbacks().remove(1);
        ELECTRIC_TYPE_FILTER.getCallbacks().remove(1);
        CHEST_BUFFER.getCallbacks().remove(1);
        FUSION_REACTOR.addGuiCallback(t -> {
            t.addButton(155, 23, ButtonOverlay.NO_OVERLAY, false).addButton(155, 41, ButtonOverlay.NO_OVERLAY, false).addButton(155, 59, ButtonOverlay.NO_OVERLAY, false).addWidget(makeProgress()).addWidget(FusionButtonWidget.build());
        });
    }

    public static WidgetSupplier makeProgress(){
        return builder(ProgressWidget::new);
    }
    // }
}
