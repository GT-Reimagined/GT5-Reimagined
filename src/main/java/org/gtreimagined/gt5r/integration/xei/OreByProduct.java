package org.gtreimagined.gt5r.integration.xei;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.util.int2;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static org.gtreimagined.gt5r.data.GT5RMachines.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

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

    public List<Pair<int2, ItemStack>> getMainOutputs(){
        return List.of(
                createOutput(1, 45, CRUSHED, getMacerateInto(), 2 * MaterialTags.ORE_MULTI.get(material)),
                createOutput(1, 63, DUST, getByproduct(0), 1),
                createOutput(22, 90, DUST_IMPURE, getMacerateInto(), 1),
                createOutput(22, 108, DUST, getByproduct(0), 1),
                createOutput(69, 23, CRUSHED_PURIFIED, getMacerateInto(), 1),
                createOutput(87, 23, DUST_TINY, getByproduct(0), 3),
                createOutput(145, 45, DUST_PURE, getMacerateInto(), 1),
                createOutput(163, 45, DUST, getByproduct(1), 1),
                createOutput(94, 90, CRUSHED_REFINED, getMacerateInto(), 1),
                createOutput(94, 111, DUST_TINY, getThermalByproduct(), 3),
                createOutput(145, 90, DUST, getMacerateInto(), 1),
                createOutput(145, 108, DUST_TINY, getByproduct(1), 1),
                createOutput(47, 99, DUST, getMacerateInto(), 1),
                createOutput(47, 117, DUST_TINY, getByproduct(1), 1),
                createOutput(69, 99, DUST, getMacerateInto(), 1),
                createOutput(69, 117, DUST, getByproduct(2), 1)
        );
    }

    public List<Pair<int2, Machine<?>>> getMainMachines(){
        return List.of(
                mch(1, 23, MACERATOR), mch(22, 69, MACERATOR), mch(26, 23, ORE_WASHER),
                mch(117, 45, MACERATOR), mch(94, 69, THERMAL_CENTRIFUGE), mch(145, 69, CENTRIFUGE),
                mch(47, 78, CENTRIFUGE), mch(69, 78, MACERATOR)
        );
    }

    private Pair<int2, Machine<?>> mch(int x, int y, Machine<?> machine){
        return Pair.of(new int2(x, y), machine);
    }

    private Pair<int2, ItemStack> createOutput(int x, int y, MaterialTypeItem<?> outputType, Material material, int amount) {
        return Pair.of(new int2(x, y), outputType.get(material, amount));
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
