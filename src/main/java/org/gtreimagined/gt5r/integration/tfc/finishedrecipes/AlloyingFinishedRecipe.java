package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public record AlloyingFinishedRecipe(ResourceLocation id, String output, Alloy... inputs) implements FinishedRecipe {

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        JsonArray array = new JsonArray();
        for (Alloy alloy : inputs) {
            JsonObject object = new JsonObject();
            object.addProperty("metal", alloy.metal);
            object.addProperty("min", alloy.min);
            object.addProperty("max", alloy.max);
            array.add(object);
        }
        jsonObject.add("contents", array);
        jsonObject.addProperty("result", output);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.ALLOY.get();
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
        return null;
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
        return null;
    }

    public record Alloy(String metal, double min, double max){}
}
