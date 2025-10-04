package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.recipes.QuernRecipe;
import net.dries007.tfc.common.recipes.SimpleItemRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeSerializers;
import net.dries007.tfc.compat.jei.category.QuernRecipeCategory;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class TFCRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        consumer.accept(new QuernFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "quern/raw_redstone"), Ingredient.of(GTMaterialTypes.RAW_ORE.getMaterialTag(Materials.Redstone)), new ItemStack(Items.REDSTONE, 8)));
    }

    private record QuernFinishedRecipe(ResourceLocation id, Ingredient input, ItemStack output) implements FinishedRecipe {

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
}
