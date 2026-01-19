package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

public record HeatingFinishedRecipe(ResourceLocation id, Ingredient input, FluidStack output,
                                    int temperature) implements FinishedRecipe {

    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        jsonObject.add("ingredient", this.input.toJson());
        JsonObject resultObj = new JsonObject();
        resultObj.addProperty("fluid", RegistryUtils.getIdFromFluid(this.output.getFluid()).toString());
        if (this.output.getAmount() > 1) {
            resultObj.addProperty("amount", this.output.getAmount());
        }
        jsonObject.add("result_fluid", resultObj);
        if (this.output.hasTag()) {
            resultObj.addProperty("nbt", this.output.getTag().toString());
        }
        jsonObject.addProperty("temperature", this.temperature);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.HEATING.get();
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
