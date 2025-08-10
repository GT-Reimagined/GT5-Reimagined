package org.gtreimagined.gt5r.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.machine.recipe.FusionRecipe;

public class GT5RRecipeTypes {
    public static final RecipeType<FusionRecipe> FUSION_RECIPE = RecipeType.simple(new ResourceLocation(GT5Reimagined.ID, "fusion"));
    public static void init(){
        ForgeRegistries.RECIPE_TYPES.register(new ResourceLocation(GT5Reimagined.ID, "fusion"), FUSION_RECIPE);
    }
}
