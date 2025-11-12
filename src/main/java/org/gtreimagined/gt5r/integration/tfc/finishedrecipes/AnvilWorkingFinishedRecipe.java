package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gtlib.recipe.RecipeUtil;
import org.jetbrains.annotations.Nullable;

public record AnvilWorkingFinishedRecipe(ResourceLocation id, Ingredient input, ItemStack output, int tier, boolean applyForgingBonus, String... rules) implements FinishedRecipe {
    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.add("input", this.input.toJson());
        jsonObject.add("result", RecipeUtil.itemstackToJson(output));
        JsonArray array = new JsonArray();
        for (String s : rules){
            array.add(s);
        }
        jsonObject.add("rules", array);
        jsonObject.addProperty("tier", tier);
        jsonObject.addProperty("apply_forging_bonus", applyForgingBonus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.ANVIL.get();
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
