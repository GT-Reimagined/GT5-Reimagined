package org.gtreimagined.gt5r.integration.rei;

import lombok.Getter;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.integration.xei.OreByProduct;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class OreProcessingDisplay implements Display {
    @Getter
    OreByProduct oreByProduct;
    private final List<EntryIngredient> input, output;

    public OreProcessingDisplay(OreByProduct oreByProduct){
        this.oreByProduct = oreByProduct;
        this.input = createInputEntries(List.of(GTMaterialTypes.ORE.getMaterialIngredient(oreByProduct.material(), 1)));

        List<EntryStack<ItemStack>> outputs = new ArrayList<>();
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, CRUSHED_ORE.get(oreByProduct.getMacerateInto(), 1)));
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, PURIFIED_ORE.get(oreByProduct.getMacerateInto(), 1)));
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, REFINED_ORE.get(oreByProduct.getMacerateInto(), 1)));
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, IMPURE_DUST.get(oreByProduct.getMacerateInto(), 1)));
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, PURE_DUST.get(oreByProduct.getMacerateInto(), 1)));
        outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, DUST.get(oreByProduct.getMacerateInto(), 1)));
        if (!oreByProduct.material().getByProducts().isEmpty()) {
            for (Material byProduct : oreByProduct.material().getByProducts()) {
                outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, DUST.get(byProduct, 1)));
            }
        }
        if (oreByProduct.material().has(GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT)){
            outputs.add(EntryStack.of(VanillaEntryTypes.ITEM, DUST.get(GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT.get(oreByProduct.material()), 1)));
        }
        this.output = List.of(EntryIngredient.of(outputs));
    }

    public static List<EntryIngredient> createInputEntries(List<Ingredient> input) {
        return input.stream().map(i -> Arrays.stream(i.getItems()).map(EntryStacks::of).toList()).map(EntryIngredient::of).toList();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return OreProcessingCategory.id;
    }

}
