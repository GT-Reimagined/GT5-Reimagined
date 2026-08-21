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

public record OreByProduct(Material material) {
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

    public boolean hasMercuryRecipes(){
        return material.has(GT5RMaterialTags.BATH_MERCURY);
    }

    public boolean hasPersulfateRecipes(){
        return material.has(GT5RMaterialTags.BATH_PERSULFATE);
    }

    public List<SlotResult> getSlots(){
        List<SlotResult> slots = new ArrayList<>(getMainSlots());
        if (hasFurnaceSmeltingRecipe()) slots.addAll(getSmeltSlots());
        if (hasMercuryRecipes()) slots.addAll(getBathSlots(true));
        if (hasPersulfateRecipes()) slots.addAll(getBathSlots(false));
        //if (hasSiftingRecipe()) slots.addAll(getSiftSlots());
        if (hasSepRecipes()) slots.addAll(getSepSlots());
        return slots;
    }

    public List<SlotResult> getMainSlots(){
        List<SlotResult> slots = new ArrayList<>(List.of(
                mch(3, 22, MACERATOR),
                createOutput(3, 45, CRUSHED_ORE, getMacerateInto(), 2 * MaterialTags.ORE_MULTI.get(material)),
                createOutput(3, 63, DUST, getByproduct(0), 1, 1000),
                mch(22, 23, FORGE_HAMMER),
                createOutput(43, 23, CRUSHED_ORE, getMacerateInto(), 2 * MaterialTags.ORE_MULTI.get(material)),
                mch(64, 3, FORGE_HAMMER),
                createOutput(85, 3, IMPURE_DUST, getMacerateInto(), 1),
                mch(64, 23, MACERATOR),
                createOutput(85, 23, IMPURE_DUST, getMacerateInto(), 1),
                createOutput(103, 23, DUST, getByproduct(0), 1, 1000),
                new SlotResult(126, 3, List.of(new ItemStack(Items.CAULDRON)), true),
                createOutput(147, 3, DUST, getMacerateInto(), 1),
                createOutput(165, 3, TINY_DUST, getByproduct(0), 1, 5000),
                mch(126, 23, CENTRIFUGE),
                createOutput(147, 23, DUST, getMacerateInto(), 1),
                createOutput(165, 23, TINY_DUST, getByproduct(0), 1),

                new SlotResult(25, 45, List.of(new ItemStack(Items.CAULDRON)), true),
                createOutput(49, 45, PURIFIED_ORE, getMacerateInto(), 1),
                createOutput(67, 45, TINY_DUST, getByproduct(0), 1, 5000),
                mch(25, 67, ORE_WASHER),
                new SlotResult(45, 67, true, List.of(Materials.Water.getLiquid(1000))),
                createOutput(67, 67, PURIFIED_ORE, getMacerateInto(), 1),
                createOutput(85, 67, TINY_DUST, getByproduct(0), 1),

                mch(117, 56, MACERATOR),
                createOutput(138, 56, PURE_DUST, getMacerateInto(), 1),
                createOutput(156, 56, DUST, getByproduct(0), 1, 1000),
                mch(117, 78, FORGE_HAMMER),
                createOutput(138, 78, PURE_DUST, getMacerateInto(), 1),

                new SlotResult(138, 99, List.of(new ItemStack(Items.CAULDRON)), true),
                createOutput(138, 120, DUST, getMacerateInto(), 1),
                createOutput(138, 138, TINY_DUST, getByproduct(1), 1, 5000),
                mch(156, 99, CENTRIFUGE),
                createOutput(156, 120, DUST, getMacerateInto(), 1),
                createOutput(156, 138, TINY_DUST, getByproduct(1), 1)
        ));
        if (material.getByProducts().size() > 3){
            //slots.add(createOutput(89, 101, DUST, getByproduct(3), 1, 1000));
        }
        if (material.getByProducts().size() > 4){
            //slots.add(createOutput(89, 119, DUST, getByproduct(4), 1, 1000));
        }
        return slots;
    }

    private List<SlotResult> getSmeltSlots(){
        return List.of(
                new SlotResult(22, 3, List.of(new ItemStack(Items.FURNACE), new ItemStack(Items.BLAST_FURNACE)), true),
                createOutput(43, 3, MaterialTags.SMELT_INTO.get(material).has(GEM) ? GEM : INGOT, MaterialTags.SMELT_INTO.get(material), SMELTING_MULTI.getInt(material))
        );
    }

    private List<SlotResult> getBathSlots(boolean mercury){

        Material bathOutput = mercury ? GT5RMaterialTags.BATH_MERCURY.getMapping(material) : GT5RMaterialTags.BATH_PERSULFATE.getMapping(material);
        Material fluidOutput = mercury ? Materials.Mercury : Materials.SodiumPersulfateSolution;
        int y = mercury ? 89 : 111;
        return List.of(
                mch(25, y, BATH),
                new SlotResult(45, y, true, List.of(fluidOutput.getLiquid(1000))),
                createOutput(67, y, PURIFIED_ORE, getMacerateInto(), 1),
                createOutput(85, y, DUST, bathOutput, 1, 7000)
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
                mch(120, 99, ELECTROMAGNETIC_SEPARATOR),
                createOutput(120, 120, DUST, getMacerateInto(), 1),
                createOutput(120, 138, SMALL_DUST, byProduct, 2, 4000),
                createOutput(120, 156, NUGGET, byProduct, 1, 2000)
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
