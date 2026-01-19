package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gtlib.recipe.RecipeUtil;
import org.jetbrains.annotations.Nullable;

public record WeldingFinishedRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, ItemStack output, int tier) implements FinishedRecipe {

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.add("first_input", this.input1.toJson());
        jsonObject.add("second_input", this.input2.toJson());
        jsonObject.add("result", RecipeUtil.itemstackToJson(output));
        jsonObject.addProperty("tier", tier);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.WELDING.get();
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
        return null;
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
        return null;
    }
}
