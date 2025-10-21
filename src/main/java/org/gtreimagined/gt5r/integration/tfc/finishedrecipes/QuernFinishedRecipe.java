package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

public record QuernFinishedRecipe(ResourceLocation id, Ingredient input, ItemStack output) implements FinishedRecipe {

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.add("ingredient", this.input.toJson());
        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("item", RegistryUtils.getIdFromItem(this.output.getItem()).toString());
        if (this.output.getCount() > 1) {
            resultObj.addProperty("count", this.output.getCount());
        }
        jsonObject.add("result", resultObj);
        if (this.output.hasTag()) {
            resultObj.addProperty("nbt", this.output.getTag().toString());
        }
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.QUERN.get();
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
