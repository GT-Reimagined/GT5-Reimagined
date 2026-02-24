package org.gtreimagined.gt5r.integration;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import dan200.computercraft.shared.ModRegistry.RecipeSerializers;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.builder.GTShapedRecipeBuilder;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class CCTweakedRegistrar {
    public static void craftingRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        provider.addItemRecipe(output, "computers", getCCItem("computer_normal"),
                ImmutableMap.of('S', PLATE.getMaterialTag(Steel), 'C', GT5RItems.ComputerMonitor, 'c', GTCoreTags.CIRCUITS_BASIC), "SSS", "ScS", "SCS");
        provider.addItemRecipe(output, "computers", getCCItem("computer_advanced"),
                ImmutableMap.of('S', PLATE.getMaterialTag(Gold), 'C', GT5RItems.ComputerMonitor, 'c', GTCoreTags.CIRCUITS_ADVANCED), "SSS", "ScS", "SCS");
        output.accept(new CCShapedRecipe(new ResourceLocation("computercraft", "computer_advanced_upgrade"), new ItemStack(getCCItem("computer_advanced")), "computers",
                ImmutableMap.of('S', PLATE.getMaterialIngredient(Gold, 1), 'C', Ingredient.of(getCCItem("computer_normal")), 'c', Ingredient.of(GTCoreTags.CIRCUITS_ADVANCED)), List.of("SSS", "SCS", "ScS"), RecipeSerializers.COMPUTER_UPGRADE.get()));
        output.accept(new CCShapedRecipe(new ResourceLocation("computercraft", "turtle_normal"), new ItemStack(getCCItem("turtle_normal")), "computers",
                ImmutableMap.of('S', PLATE.getMaterialIngredient(TFCRegistrar.getIron(), 1), 'C', Ingredient.of(getCCItem("computer_normal")), 'c', Ingredient.of(Items.CHESTS_WOODEN)), List.of("SSS", "SCS", "ScS"), RecipeSerializers.TURTLE.get()));
        output.accept(new CCShapedRecipe(new ResourceLocation("computercraft", "turtle_advanced"), new ItemStack(getCCItem("turtle_advanced")), "computers",
                ImmutableMap.of('S', PLATE.getMaterialIngredient(Gold, 1), 'C', Ingredient.of(getCCItem("computer_advanced")), 'c', Ingredient.of(Items.CHESTS_WOODEN)), List.of("SSS", "SCS", "ScS"), RecipeSerializers.TURTLE.get()));
        output.accept(new CCShapedRecipe(new ResourceLocation("computercraft", "turtle_advanced_upgrade"), new ItemStack(getCCItem("computer_advanced")), "computers",
                ImmutableMap.of('S', PLATE.getMaterialIngredient(Gold, 1), 'C', Ingredient.of(getCCItem("turtle_normal")), 'G', BLOCK.getMaterialIngredient(Gold, 1)), List.of("SSS", "SCS", " G "), RecipeSerializers.COMPUTER_UPGRADE.get()));
    }

    public static Item getCCItem(String id){
        return RegistryUtils.getItemFromID("computercraft", id);
    }

    public static Block getCCBlock(String id){
        return RegistryUtils.getBlockFromId("computercraft", id);
    }

    private static class CCShapedRecipe extends GTShapedRecipeBuilder.Result {
        private RecipeSerializer<?> recipeSerializer;

        public CCShapedRecipe(ResourceLocation id, ItemStack result, String group, Map<Character, Ingredient> key, List<String> pattern, RecipeSerializer<?> serializer) {
            super(id, result, group, pattern, key, null, null, false);
            this.recipeSerializer = serializer;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);
            json.addProperty("category", "redstone");
            json.addProperty("show_notification", true);
        }

        @Override
        public RecipeSerializer<?> getType() {
            return recipeSerializer;
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
