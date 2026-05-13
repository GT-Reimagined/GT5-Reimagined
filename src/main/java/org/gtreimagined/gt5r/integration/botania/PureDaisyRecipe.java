package org.gtreimagined.gt5r.integration.botania;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.StateIngredientHelper;

import javax.annotation.Nullable;

public class PureDaisyRecipe implements net.minecraft.data.recipes.FinishedRecipe {
    public static final int DEFAULT_TIME = 150;

    protected final ResourceLocation id;
    protected final StateIngredient input;
    protected final BlockState outputState;
    protected final int time;
    @Nullable
    private final ResourceLocation function;

    public PureDaisyRecipe(ResourceLocation id, StateIngredient input, BlockState state) {
        this(id, input, state, DEFAULT_TIME);
    }

    public PureDaisyRecipe(ResourceLocation id, StateIngredient input, BlockState state, int time) {
        this(id, input, state, time, null);
    }

    public PureDaisyRecipe(ResourceLocation id, StateIngredient input, BlockState state, int time, @Nullable ResourceLocation function) {
        Preconditions.checkArgument(time >= 0, "Time must be nonnegative");
        this.id = id;
        this.input = input;
        this.outputState = state;
        this.time = time;
        this.function = function;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        json.add("input", input.serialize());
        json.add("output", StateIngredientHelper.serializeBlockState(outputState));
        if (time != DEFAULT_TIME) {
            json.addProperty("time", time);
        }
        if (function != null) {
            json.addProperty("success_function", function.toString());
        }
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
        return BotaniaRecipeTypes.PURE_DAISY_SERIALIZER;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
