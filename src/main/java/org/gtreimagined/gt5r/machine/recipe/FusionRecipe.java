package org.gtreimagined.gt5r.machine.recipe;

import lombok.Getter;
import lombok.Setter;
import org.gtreimagined.gtlib.recipe.Recipe;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RRecipeTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Setter
@Getter
public class FusionRecipe extends Recipe {
    int huOutput;
    public FusionRecipe(@NotNull List<Ingredient> stacksInput, List<ItemStack> stacksOutput, @NotNull List<FluidIngredient> fluidsInput, List<FluidStack> fluidsOutput, int duration, long power, int special, int amps) {
        super(stacksInput, stacksOutput, fluidsInput, fluidsOutput, duration, power, special, amps);
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return GT5RRecipeTypes.FUSION_RECIPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FusionRecipeSerializer.INSTANCE;
    }
}
