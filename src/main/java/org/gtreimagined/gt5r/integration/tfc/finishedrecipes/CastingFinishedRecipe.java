package org.gtreimagined.gt5r.integration.tfc.finishedrecipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gtlib.recipe.RecipeUtil;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

public record CastingFinishedRecipe(ResourceLocation id, Item mold, FluidStack fluid, Item output, float breakChance)  implements FinishedRecipe {
    @Override
    public void serializeRecipeData(JsonObject jsonObject) {
        JsonObject moldObject = new JsonObject();
        moldObject.addProperty("item", RegistryUtils.getIdFromItem(mold).toString());
        jsonObject.add("mold", moldObject);
        JsonObject fluidObject = new JsonObject();
        fluidObject.addProperty("ingredient", RegistryUtils.getIdFromFluid(fluid.getFluid()).toString());
        fluidObject.addProperty("amount", fluid.getAmount());
        jsonObject.add("fluid", fluidObject);
        jsonObject.add("result", RecipeUtil.itemstackToJson(output.getDefaultInstance()));
        jsonObject.addProperty("break_chance", breakChance);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return TFCRecipeSerializers.CASTING.get();
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
