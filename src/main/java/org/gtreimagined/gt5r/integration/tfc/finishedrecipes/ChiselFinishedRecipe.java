package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.ChiselRecipe.Mode;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

public record ChiselFinishedRecipe(ResourceLocation id, Block input, Block output, Mode mode) implements FinishedRecipe {
    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.addProperty("ingredient", RegistryUtils.getIdFromBlock(input).toString());
        jsonObject.addProperty("result", RegistryUtils.getIdFromBlock(output).toString());
        jsonObject.addProperty("mode", mode.getSerializedName());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.CHISEL.get();
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
