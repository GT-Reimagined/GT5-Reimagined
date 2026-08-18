package org.gtreimagined.gt5r.integration.recipeviewer;

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
        if (bathingMode != BathingMode.NONE) slots.addAll(getBathSlots());
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
            boolean e = material.has(EXQUISITE_GEM);
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
                mch(3, 25, MACERATOR),
                createOutput(3, 47, CRUSHED_ORE, getMacerateInto(), 2 * MaterialTags.ORE_MULTI.get(material)),
                createOutput(3, 65, DUST, getByproduct(0), 1, 1000),
                mch(24, 71, MACERATOR),
                createOutput(24, 92, IMPURE_DUST, getMacerateInto(), 1),
                createOutput(24, 110, DUST, getByproduct(0), 1, 1000),
                mch(28, 25, ORE_WASHER),
                new SlotResult(49, 25, true, List.of(Materials.Water.getLiquid(1000))),
                createOutput(71, 25, PURIFIED_ORE, getMacerateInto(), 1),
                createOutput(89, 25, TINY_DUST, getByproduct(0), 1),
                mch(119, 47, MACERATOR),
                createOutput(147, 47, PURE_DUST, getMacerateInto(), 1),
                createOutput(165, 47, DUST, getByproduct(1), 1, 1000),
                mch(125, 71, THERMAL_CENTRIFUGE),
                createOutput(125, 92, REFINED_ORE, getMacerateInto(), 1),
                createOutput(125, 110, TINY_DUST, getThermalByproduct(), 1),
                mch(147, 71, CENTRIFUGE),
                createOutput(147, 92, DUST, getMacerateInto(), 1),
                createOutput(147, 110, TINY_DUST, getByproduct(1), 1),
                mch(49, 80, CENTRIFUGE),
                createOutput(49, 101, DUST, getMacerateInto(), 1),
                createOutput(49, 119, TINY_DUST, getByproduct(0), 1),
                mch(71, 80, MACERATOR),
                createOutput(71, 101, DUST, getMacerateInto(), 1),
                createOutput(71, 119, DUST, getByproduct(2), 1, 1000),
                createOutput(3, 105, CRUSHED_ORE, getMacerateInto(), 1),
                new SlotResult(3, 124, List.of(new ItemStack(Items.CAULDRON), new ItemStack(ORE_WASHER.getItem(LV))), true),
                createOutput(3, 145, PURIFIED_ORE, getMacerateInto(), 1),
                createOutput(24, 145, IMPURE_DUST, getMacerateInto(), 1),
                new SlotResult(42, 145, List.of(new ItemStack(Items.CAULDRON), new ItemStack(CENTRIFUGE.getItem(LV))), true),
                createOutput(64, 145, DUST, getMacerateInto(), 1),
                createOutput(85, 145, PURE_DUST, getMacerateInto(), 1),
                new SlotResult(103, 145, List.of(new ItemStack(Items.CAULDRON), new ItemStack(CENTRIFUGE.getItem(LV))), true),
                createOutput(125, 145, DUST, getMacerateInto(), 1)
        ));
        if (material.getByProducts().size() > 3){
            slots.add(createOutput(89, 101, DUST, getByproduct(3), 1, 1000));
        }
        if (material.getByProducts().size() > 4){
            slots.add(createOutput(89, 119, DUST, getByproduct(4), 1, 1000));
        }
        return slots;
    }

    private List<SlotResult> getSmeltSlots(){
        return List.of(
                new SlotResult(28, 3, List.of(new ItemStack(Items.FURNACE), new ItemStack(Items.BLAST_FURNACE)), true),
                createOutput(49, 3, MaterialTags.SMELT_INTO.get(material).has(GEM) ? GEM : INGOT, MaterialTags.SMELT_INTO.get(material), SMELTING_MULTI.getInt(material))
        );
    }

    private List<SlotResult> getBathSlots(){
        Material bathOutput = bathingMode == BathingMode.MERCURY ? GT5RMaterialTags.BATH_MERCURY.getMapping(material) : GT5RMaterialTags.BATH_PERSULFATE.getMapping(material);
        Material fluidOutput = bathingMode == BathingMode.MERCURY ? Materials.Mercury : Materials.SodiumPersulfateSolution;
        return List.of(
                mch(28, 47, BATH),
                new SlotResult(49, 47, true, List.of(fluidOutput.getLiquid(1000))),
                createOutput(89, 47, DUST, bathOutput, 1, 7000),
                createOutput(71, 47, PURIFIED_ORE, getMacerateInto(), 1)
        );
    }

    private List<SlotResult> getSiftSlots(){
        boolean e = material.has(EXQUISITE_GEM);
        return List.of(
                mch(109, 22, SIFTER),
                createOutput(129, 3, e ? EXQUISITE_GEM : GEM, getMacerateInto(), 1, e ? 300 : 100),
                createOutput(147, 3, e ? FLAWLESS_GEM : GEM, getMacerateInto(), 1, e ? 1200 : 400),
                createOutput(165, 3, GEM, getMacerateInto(), 1, e ? 4500 : 1500),
                createOutput(129, 21, e ? FLAWED_GEM : GEM, getMacerateInto(), 1, e ? 1400 : 2000),
                createOutput(147, 21, e ? CHIPPED_GEM : GEM, getMacerateInto(), 1, e ? 2800 : 4000),
                createOutput(165, 21, DUST, getMacerateInto(), 1, e ? 3500 : 5000)
        );
    }

    private List<SlotResult> getSepSlots(){
        Material byProduct = material.has(GT5RMaterialTags.ELECSEPI) ? Materials.Iron : material.has(GT5RMaterialTags.ELECSEPG) ? Materials.Gold : Materials.Neodymium;
        return List.of(
                mch(165, 71, ELECTROMAGNETIC_SEPARATOR),
                createOutput(165, 92, DUST, getMacerateInto(), 1),
                createOutput(165, 110, SMALL_DUST, byProduct, 2, 4000),
                createOutput(165, 128, NUGGET, byProduct, 1, 2000)
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

    public record SlotResult(int x, int y, List<ItemStack> stacks, List<FluidStack> fluidStacks, boolean input, int chance, boolean item){
        public SlotResult(int x, int y, List<ItemStack> stacks, boolean input){
            this(x, y, stacks, List.of(), input, -1, true);
        }

        public SlotResult(int x, int y, List<ItemStack> stacks, boolean input, int chance){
            this(x, y, stacks, List.of(), input, chance, true);
        }

        public SlotResult(int x, int y, boolean input, List<FluidStack> fluidStacks){
            this(x, y, List.of(), fluidStacks, input, -1, false);
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
