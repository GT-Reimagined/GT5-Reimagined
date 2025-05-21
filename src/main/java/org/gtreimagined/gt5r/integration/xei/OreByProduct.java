package org.gtreimagined.gt5r.integration.xei;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Triple;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gt5r.data.GT5RMachines.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.material.MaterialTags.SMELTING_MULTI;

public record OreByProduct(Material material, BathingMode bathingMode) {
    public Material getMacerateInto(){
        return MaterialTags.MACERATE_INTO.getMapping(material);
    }

    public Material getByproduct(int index){
        if (material.getByProducts().isEmpty()) return getMacerateInto();
        if (material.getByProducts().size() <= index) return material.getByProducts().get(material.getByProducts().size() - 1);
        return material.getByProducts().get(index);
    }

    public Material getThermalByproduct(){
        if (material.has(GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT)) return GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT.getMapping(material);
        return getByproduct(1);
    }

    public boolean hasSiftingRecipe() {
        return material.has(GEM);
    }

    public boolean hasSepRecipes(){
        return material.has(GT5RMaterialTags.ELECSEPI) || material.has(GT5RMaterialTags.ELECSEPG) || material.has(GT5RMaterialTags.ELECSEPN);
    }

    public boolean hasFurnaceSmeltingRecipe() {
        return !material.has(GT5RMaterialTags.NEEDS_BLAST_FURNACE) && (MaterialTags.SMELT_INTO.getMapping(material).has(INGOT) || MaterialTags.SMELT_INTO.getMapping(material).has(GEM));
    }

    public List<SlotResult> getSlots(){
        List<SlotResult> slots = new ArrayList<>(getMainSlots());
        if (hasFurnaceSmeltingRecipe()) slots.addAll(getSmeltSlots());
        if (bathingMode != BathingMode.NONE) slots.addAll(getChemSlots());
        if (hasSiftingRecipe()) slots.addAll(getSiftSlots());
        if (hasSepRecipes()) slots.addAll(getSepSlots());
        return slots;
    }

    public List<Triple<Integer, Integer, Integer>> getChanceOverlays(){
        List<Triple<Integer, Integer, Integer>> overlays = new ArrayList<>(List.of(
                Triple.of(1, 63, 1000),
                Triple.of(22, 108, 1000),
                Triple.of(163, 45, 1000),
                Triple.of(69, 117, 1000)
        ));
        if (material.getByProducts().size() > 3){
            overlays.add(Triple.of(87, 99, 1000));
        }
        if (material.getByProducts().size() > 4){
            overlays.add(Triple.of(87, 117, 1000));
        }
        if (bathingMode != BathingMode.NONE) {
            overlays.add(Triple.of(87, 45, 7000));
        }
        if (hasSiftingRecipe()){
            boolean e = material.has(GEM_EXQUISITE);
            overlays.addAll(List.of(
                    Triple.of(127, 1, e ? 300 : 100),
                    Triple.of(145, 1, e ? 1200 : 400),
                    Triple.of(163, 1, e ? 4500 : 1500),
                    Triple.of(127, 19, e ? 1400 : 2000),
                    Triple.of(145, 19, e ? 2800 : 4000),
                    Triple.of(163, 19, e ? 3500 : 5000)
            ));
        }
        if (hasSepRecipes()){
            overlays.add(Triple.of(163, 108, 4000));
            overlays.add(Triple.of(163, 126, 2000));
        }
        return overlays;
    }

    public List<SlotResult> getMainSlots(){
        List<SlotResult> slots = new ArrayList<>(List.of(
                mch(1, 23, MACERATOR),
                createOutput(1, 45, CRUSHED, getMacerateInto(), 2 * MaterialTags.ORE_MULTI.get(material)),
                createOutput(1, 63, DUST, getByproduct(0), 1, 1000),
                mch(22, 69, MACERATOR),
                createOutput(22, 90, DUST_IMPURE, getMacerateInto(), 1),
                createOutput(22, 108, DUST, getByproduct(0), 1, 1000),
                mch(26, 23, ORE_WASHER),
                new SlotResult(47, 23, true, List.of(Materials.Water.getLiquid(1000))),
                createOutput(69, 23, CRUSHED_PURIFIED, getMacerateInto(), 1),
                createOutput(87, 23, DUST_TINY, getByproduct(0), 3),
                mch(117, 45, MACERATOR),
                createOutput(145, 45, DUST_PURE, getMacerateInto(), 1),
                createOutput(163, 45, DUST, getByproduct(1), 1, 1000),
                mch(123, 69, THERMAL_CENTRIFUGE),
                createOutput(123, 90, CRUSHED_REFINED, getMacerateInto(), 1),
                createOutput(123, 111, DUST_TINY, getThermalByproduct(), 3),
                mch(145, 69, CENTRIFUGE),
                createOutput(145, 90, DUST, getMacerateInto(), 1),
                createOutput(145, 108, DUST_TINY, getByproduct(1), 1),
                mch(47, 78, CENTRIFUGE),
                createOutput(47, 99, DUST, getMacerateInto(), 1),
                createOutput(47, 117, DUST_TINY, getByproduct(1), 1),
                mch(69, 78, MACERATOR),
                createOutput(69, 99, DUST, getMacerateInto(), 1),
                createOutput(69, 117, DUST, getByproduct(2), 1, 1000),
                createOutput(1, 103, CRUSHED, getMacerateInto(), 1),
                new SlotResult(1, 122, List.of(new ItemStack(Items.CAULDRON), new ItemStack(ORE_WASHER.getItem(LV))), true),
                createOutput(1, 143, CRUSHED_PURIFIED, getMacerateInto(), 1),
                createOutput(22, 143, DUST_IMPURE, getMacerateInto(), 1),
                new SlotResult(40, 143, List.of(new ItemStack(Items.CAULDRON), new ItemStack(ORE_WASHER.getItem(LV))), true),
                createOutput(62, 143, DUST, getMacerateInto(), 1),
                createOutput(83, 143, DUST_PURE, getMacerateInto(), 1),
                new SlotResult(101, 143, List.of(new ItemStack(Items.CAULDRON), new ItemStack(ORE_WASHER.getItem(LV))), true),
                createOutput(123, 143, DUST, getMacerateInto(), 1)
        ));
        if (material.getByProducts().size() > 3){
            slots.add(createOutput(87, 99, DUST, getByproduct(3), 1, 1000));
        }
        if (material.getByProducts().size() > 4){
            slots.add(createOutput(87, 117, DUST, getByproduct(4), 1, 1000));
        }
        return slots;
    }

    private List<SlotResult> getSmeltSlots(){
        return List.of(
                new SlotResult(26, 1, List.of(new ItemStack(Items.FURNACE), new ItemStack(Items.BLAST_FURNACE)), true),
                createOutput(47, 1, MaterialTags.SMELT_INTO.get(material).has(GEM) ? GEM : INGOT, MaterialTags.SMELT_INTO.get(material), SMELTING_MULTI.getInt(material))
        );
    }

    private List<SlotResult> getChemSlots(){
        Material bathOutput = bathingMode == BathingMode.MERCURY ? GT5RMaterialTags.BATH_MERCURY.getMapping(material) : GT5RMaterialTags.BATH_PERSULFATE.getMapping(material);
        Material fluidOutput = bathingMode == BathingMode.MERCURY ? Materials.Mercury : Materials.SodiumPersulfateSolution;
        return List.of(
                mch(26, 45, BATH),
                new SlotResult(47, 45, true, List.of(fluidOutput.getLiquid(1000))),
                createOutput(87, 45, DUST, bathOutput, 1, 7000),
                createOutput(69, 45, CRUSHED_PURIFIED, getMacerateInto(), 1)
        );
    }

    private List<SlotResult> getSiftSlots(){
        boolean e = material.has(GEM_EXQUISITE);
        return List.of(
                mch(107, 22, SIFTER),
                createOutput(127, 1, e ? GEM_EXQUISITE : GEM, getMacerateInto(), 1, e ? 300 : 100),
                createOutput(145, 1, e ? GEM_FLAWLESS : GEM, getMacerateInto(), 1, e ? 1200 : 400),
                createOutput(163, 1, GEM, getMacerateInto(), 1, e ? 4500 : 1500),
                createOutput(127, 19, e ? GEM_FLAWED : GEM, getMacerateInto(), 1, e ? 1400 : 2000),
                createOutput(145, 19, e ? GEM_CHIPPED : GEM, getMacerateInto(), 1, e ? 2800 : 4000),
                createOutput(163, 19, DUST, getMacerateInto(), 1, e ? 3500 : 5000)
        );
    }

    private List<SlotResult> getSepSlots(){
        Material byProduct = material.has(GT5RMaterialTags.ELECSEPI) ? Materials.Iron : material.has(GT5RMaterialTags.ELECSEPG) ? Materials.Gold : Materials.Neodymium;
        return List.of(
                mch(163, 69, ELECTROMAGNETIC_SEPARATOR),
                createOutput(163, 90, DUST, getMacerateInto(), 1),
                createOutput(163, 108, DUST_SMALL, byProduct, 2, 4000),
                createOutput(163, 126, NUGGET, byProduct, 1, 2000)
        );
    }

    private SlotResult mch(int x, int y, Machine<?> machine){
        return new SlotResult(x, y, List.of(new ItemStack(machine.getItem(machine.getFirstTier()))), true);
    }

    private SlotResult createOutput(int x, int y, MaterialTypeItem<?> outputType, Material material, int amount) {
        return new SlotResult(x, y, List.of(outputType.get(material, amount)), false);
    }

    private SlotResult createOutput(int x, int y, MaterialTypeItem<?> outputType, Material material, int amount, int chance) {
        return new SlotResult(x, y, List.of(outputType.get(material, amount)), false, chance);
    }

    public record SlotResult(int x, int y, List<ItemStack> stacks, List<FluidStack> fluidStacks, boolean input, int chance){
        public SlotResult(int x, int y, List<ItemStack> stacks, boolean input){
            this(x, y, stacks, List.of(), input, -1);
        }

        public SlotResult(int x, int y, List<ItemStack> stacks, boolean input, int chance){
            this(x, y, stacks, List.of(), input, chance);
        }

        public SlotResult(int x, int y, boolean input, List<FluidStack> fluidStacks){
            this(x, y, List.of(), fluidStacks, input, -1);
        }
    }


    public enum SepMode {
        NONE,
        IRON,
        GOLD,
        NEODYMIUM
    }

    public enum BathingMode {
        NONE,
        MERCURY,
        PERSULFATE
    }
}
