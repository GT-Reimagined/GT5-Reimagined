package org.gtreimagined.gt5r.loader.crafting;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.TagUtils;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreItems.Plantball;

public class Miscellaneous {
    public static void loadRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider) {
        provider.addItemRecipe(output, GT5Reimagined.ID, "plantball_from_crops", "misc", Plantball, of(
                'C', Tags.Items.CROPS
        ), "CCC", "C C", "CCC");
        provider.addItemRecipe(output, GT5Reimagined.ID, "plantball_from_seeds", "misc", Plantball, of(
                'S', Tags.Items.SEEDS
        ), "SSS", "S S", "SSS");
        provider.addStackRecipe(output, GT5Reimagined.ID, "plantball_from_saplings", "misc", new ItemStack(Plantball, 2), of(
                'S', TagUtils.getItemTag(new ResourceLocation("saplings"))
        ), "SSS", "S S", "SSS");
        provider.addStackRecipe(output, GT5Reimagined.ID, "plantball_from_chorus_fruit", "misc", new ItemStack(Plantball, 2), of(
                'S', Items.CHORUS_FRUIT
        ), "SSS", "S S", "SSS");
    }
}