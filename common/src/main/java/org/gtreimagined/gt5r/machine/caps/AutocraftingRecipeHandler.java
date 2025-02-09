package org.gtreimagined.gt5r.machine.caps;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.recipe.IRecipe;
import muramasa.antimatter.recipe.Recipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.blockentity.IAutocrafter;

import java.util.List;

public class AutocraftingRecipeHandler<T extends BlockEntityMachine<T> & IAutocrafter> extends ParallelRecipeHandler<T>{
    public AutocraftingRecipeHandler(T tile, int maxSimultaneousRecipes) {
        super(tile, maxSimultaneousRecipes);
    }

    @Override
    protected boolean canRecipeContinue() {
        return super.canRecipeContinue() && (tile.getRecipe() != null && tile.getRecipe().getId().equals(tile.getOldRecipe().getId()));
    }

    @Override
    public IRecipe findRecipe() {
        IRecipe recipe = super.findRecipe();
        if (recipe == null) {
            if (tile.getRecipe() != null) {
                List<Ingredient> ingredients = tile.getRecipe().getIngredients().stream().filter(i -> !i.isEmpty()).toList();
                recipe = new Recipe(ingredients, new ItemStack[]{tile.getRecipe().getResultItem()}, List.of(), null, 1024, 16, 0, 1);
                recipe.setId(tile.getRecipe().getId());
            }
        }
        return recipe;
    }
}
