package org.gtreimagined.gt5r.machine.recipe;

import com.google.gson.JsonObject;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.FluidIngredient;
import org.gtreimagined.gtlib.recipe.serializer.GTRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RRecipeTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FusionRecipeSerializer extends GTRecipeSerializer<FusionRecipe> {
    public static final FusionRecipeSerializer INSTANCE = new FusionRecipeSerializer();
    public static void init(){
    }
    protected FusionRecipeSerializer() {
        super(GT5Reimagined.ID, "fusion");
    }

    @Override
    public RecipeType<FusionRecipe> getRecipeType() {
        return GT5RRecipeTypes.FUSION_RECIPE;
    }

    @Override
    public FusionRecipe createRecipe(@NotNull List<Ingredient> stacksInput, ItemStack[] stacksOutput, @NotNull List<FluidIngredient> fluidsInput, FluidStack[] fluidsOutput, int duration, long power, int special, int amps) {
        return new FusionRecipe(stacksInput, stacksOutput, fluidsInput, fluidsOutput, duration, power, special, amps);
    }

    @Override
    public void toJson(JsonObject json, IRecipe recipe) {
        super.toJson(json, recipe);
        if (recipe instanceof FusionRecipe fusionRecipe) {
            json.addProperty("huOutput", fusionRecipe.getHuOutput());
        }
    }

    @Override
    public FusionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        FusionRecipe fusionRecipe = super.fromJson(recipeId, json);
        if (fusionRecipe != null && json.has("huOutput")) {
            fusionRecipe.setHuOutput(json.get("huOutput").getAsInt());
        }
        return fusionRecipe;
    }

    @Override
    public @Nullable FusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        FusionRecipe fusionRecipe = super.fromNetwork(recipeId, buffer);
        if (fusionRecipe != null) {
            fusionRecipe.setHuOutput(buffer.readInt());
        }
        return fusionRecipe;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, FusionRecipe recipe) {
        super.toNetwork(buffer, recipe);
        buffer.writeInt(recipe.getHuOutput());
    }
}
