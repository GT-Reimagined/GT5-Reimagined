package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.gtreimagined.gtlib.recipe.RecipeUtil;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public record AdvancedShapedCraftingFinishedRecipe(ResourceLocation id, ItemStack result, int inputRow, int inputColumn, String[] modifiers, ImmutableMap<Character, Object> inputs, String... pattern) implements FinishedRecipe {
    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        if (inputs.isEmpty()) Utils.onInvalidData("Inputs should not be empty!");
        if (pattern.length < 1 || pattern.length > 3)
            Utils.onInvalidData("Input pattern must have between 1 and 3 rows!");
        JsonArray patternObject = new JsonArray();
        for (String s : pattern) {
            if (s.length() > 3) Utils.onInvalidData("Input pattern rows must have between 0 and 3 characters!");
            patternObject.add(s);
        }
        jsonObject.add("pattern", patternObject);
        jsonObject.add("key", resolveKeys(inputs));
        JsonObject resultObject = new JsonObject();
        resultObject.add("stack", RecipeUtil.itemstackToJson(result));
        JsonArray modifiers = new JsonArray();
        for (String m : this.modifiers){
            modifiers.add(m);
        }
        if (!modifiers.isEmpty()) resultObject.add("modifiers", modifiers);
        jsonObject.add("result", resultObject);
        jsonObject.addProperty("input_row", inputRow);
        jsonObject.addProperty("input_column", inputColumn);
    }

    protected JsonObject resolveKeys(ImmutableMap<Character, Object> inputs) {
        JsonObject key = new JsonObject();
        for (Map.Entry<Character, Object> entry : inputs.entrySet()) {
            JsonObject object = new JsonObject();

            if (entry.getValue() instanceof ItemLike l) {
                object.addProperty("item", RegistryUtils.getIdFromItem(l.asItem()).toString());
            } else if (entry.getValue() instanceof ItemStack stack){
                object.addProperty("item", RegistryUtils.getIdFromItem(stack.getItem()).toString());
            } else if (entry.getValue() instanceof TagKey tagKey) {
                object.addProperty("tag", tagKey.location().toString());
            } else if (entry.getValue() instanceof Ingredient i) {
                object = i.toJson().getAsJsonObject();
            }
            key.add(entry.getKey().toString(), object);
        }
        return key;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.ADVANCED_SHAPED_CRAFTING.get();
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
