package org.gtreimagined.gt5r.integration.railcraft;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

public class RailcraftRecipes {

    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {

        Block marble = ((CobbleStoneType)GTCoreBlocks.MARBLE).getBlock("");
        Block basalt = ((CobbleStoneType)GTCoreBlocks.BASALT).getBlock("");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RegistryUtils.getItemFromID("railcraft", "quarried_stone")), RecipeCategory.BUILDING_BLOCKS, marble)
                .unlockedBy("has_block", provider.hasSafeItem(RegistryUtils.getItemFromID("railcraft", "quarried_stone"))).save(consumer, new ResourceLocation(GT5Reimagined.ID, "stonecutting/rc_quarried_stone_to_marble"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(marble), RecipeCategory.BUILDING_BLOCKS, RegistryUtils.getItemFromID("railcraft", "quarried_stone"))
                .unlockedBy("has_block", provider.hasSafeItem(marble)).save(consumer, new ResourceLocation(GT5Reimagined.ID, "stonecutting/marble_to_rc_quarried_stone"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(RegistryUtils.getItemFromID("railcraft", "abyssal_stone")), RecipeCategory.BUILDING_BLOCKS, basalt)
                .unlockedBy("has_block", provider.hasSafeItem(RegistryUtils.getItemFromID("railcraft", "abyssal_stone"))).save(consumer, new ResourceLocation(GT5Reimagined.ID, "stonecutting/rc_abyssal_stone_to_basalt"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(basalt), RecipeCategory.BUILDING_BLOCKS, RegistryUtils.getItemFromID("railcraft", "abyssal_stone"))
                .unlockedBy("has_block", provider.hasSafeItem(basalt)).save(consumer, new ResourceLocation(GT5Reimagined.ID, "stonecutting/basalt_to_rc_abyssal_stone"));
    }
}
