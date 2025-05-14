package org.gtreimagined.gt5r.loader.crafting;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.pipe.PipeSize;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import org.gtreimagined.gt5r.data.GT5RBlocks;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;

public class WoodCrafting {

    public static void loadRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "tiny_wooden_fluid_pipe", "pipes",
                GT5RBlocks.FLUID_PIPE_WOOD.getBlockItem(PipeSize.TINY), of('S', GTTools.SAW.getTag(), 's', ItemTags.WOODEN_SLABS, 'H', GTTools.SOFT_HAMMER.getTag()), "  S", " s ", "H  ");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "small_wooden_fluid_pipe", "pipes",
                GT5RBlocks.FLUID_PIPE_WOOD.getBlockItem(PipeSize.SMALL), of('S', GTTools.SAW.getTag(), 's', ItemTags.PLANKS, 'H', GTTools.SOFT_HAMMER.getTag()), "  S", " s ", "H  ");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "normal_wooden_fluid_pipe", "pipes",
                GT5RBlocks.FLUID_PIPE_WOOD.getBlockItem(PipeSize.NORMAL), of('S', GTTools.SAW.getTag(), 's', ItemTags.PLANKS, 'H', GTTools.SOFT_HAMMER.getTag()), "  S", "sss", "H  ");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "large_wooden_fluid_pipe", "pipes",
                GT5RBlocks.FLUID_PIPE_WOOD.getBlockItem(PipeSize.LARGE), of('S', GTTools.SAW.getTag(), 's', ItemTags.PLANKS, 'H', GTTools.SOFT_HAMMER.getTag()), "ssS", "s s", "Hss");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "huge_wooden_fluid_pipe", "pipes",
                GT5RBlocks.FLUID_PIPE_WOOD.getBlockItem(PipeSize.HUGE), of('S', GTTools.SAW.getTag(), 's', ItemTags.LOGS, 'H', GTTools.SOFT_HAMMER.getTag()), "  S", "s s", "H  ");
    }
}
