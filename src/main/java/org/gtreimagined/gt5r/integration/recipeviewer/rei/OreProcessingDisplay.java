package org.gtreimagined.gt5r.integration.recipeviewer.rei;

import brachy.modularui.integration.rei.recipe.ModularUIReiDisplay;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.widget.OreByProductWidget;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.integration.recipeviewer.rei.REIUtils;
import org.gtreimagined.gtlib.material.Material;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class OreProcessingDisplay extends ModularUIReiDisplay {
    @Getter
    OreByProduct oreByProduct;
    private final List<EntryIngredient> input, output;

    public OreProcessingDisplay(OreByProduct oreByProduct){
        super(OreByProductWidget.id(oreByProduct), () -> new OreByProductWidget(oreByProduct), OreProcessingCategory.id);
        this.oreByProduct = oreByProduct;
        ImmutableList.Builder<EntryIngredient> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<EntryIngredient> outputBuilder = ImmutableList.builder();
        inputBuilder.add(EntryIngredients.ofItemTag(ORE.getMaterialTag(oreByProduct.material())));
        oreByProduct.getSlots().forEach(r -> {
            var b = r.input() ? inputBuilder : outputBuilder;
            if (!r.stacks().isEmpty()){
                b.add(EntryIngredients.ofItemStacks(r.stacks()));
            }
            if (!r.fluidStacks().isEmpty()){
                b.add(EntryIngredient.of(r.fluidStacks().stream().map(REIUtils::toREIFLuidStack).map(EntryStacks::of).toList()));
            }
        });
        this.input = inputBuilder.build();
        this.output = outputBuilder.build();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

}
