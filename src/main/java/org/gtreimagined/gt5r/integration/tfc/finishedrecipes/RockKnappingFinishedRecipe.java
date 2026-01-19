package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gtlib.recipe.RecipeUtil;
import org.jetbrains.annotations.Nullable;

public record RockKnappingFinishedRecipe(ResourceLocation id, String[] pattern, Ingredient input,
                                         ItemStack output) implements FinishedRecipe {

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.add("ingredient", this.input.toJson());
        JsonArray array = new JsonArray();
        for (String s : pattern) {
            array.add(new JsonPrimitive(s));
        }
        jsonObject.add("pattern", array);
        jsonObject.addProperty("outside_slot_required", false);
        jsonObject.add("result", RecipeUtil.itemstackToJson(this.output));
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.ROCK_KNAPPING.get();
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
