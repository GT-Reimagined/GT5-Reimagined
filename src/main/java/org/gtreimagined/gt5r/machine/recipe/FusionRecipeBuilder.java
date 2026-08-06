package org.gtreimagined.gt5r.machine.recipe;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.map.RecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;

public class FusionRecipeBuilder extends RecipeBuilder {
    int huOutput;
    @Override
    protected IRecipe buildRecipe() {
        FusionRecipe recipe = new FusionRecipe(
                ingredientInput,
                itemsOutput != null ? itemsOutput : Collections.emptyList(),
                fluidsInput != null ? fluidsInput : Collections.emptyList(),
                fluidsOutput != null ? fluidsOutput : Collections.emptyList(),
                duration, power, special, amps
        );
        if (outputChances != null) recipe.addOutputChances(outputChances);
        if (inputChances != null) recipe.addInputChances(inputChances);
        recipe.setHidden(hidden);
        recipe.setFake(fake);
        recipe.setHuOutput(huOutput);
        recipe.addTags(new ObjectOpenHashSet<>(tags));
        recipe.setId(this.id);
        recipe.setMapId(this.recipeMap.getLoc().toString());
        return recipe;
    }

    public FusionRecipeBuilder huOutput(int huOutput) {
        this.huOutput = huOutput;
        return this;
    }

    @Override
    public void clear() {
        super.clear();
        this.huOutput = 0;
    }
}
