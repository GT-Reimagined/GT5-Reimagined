package org.gtreimagined.gt5r.integration.tfc.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AlloyingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AlloyingFinishedRecipe.Alloy;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

public class AlloyingRecipes {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/sterling_silver"), "tfc:sterling_silver", new Alloy("tfc:copper", 0.2, 0.25), new Alloy("tfc:silver", 0.75, 0.8)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/rose_gold"), "tfc:rose_gold", new Alloy("tfc:copper", 0.2, 0.25), new Alloy("tfc:gold", 0.75, 0.8)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/bismuth_bronze"), "tfc:bismuth_bronze", new Alloy("tfc:zinc", 0.25, 0.3), new Alloy("tfc:copper", 0.5, 0.6), new Alloy("tfc:bismuth", 0.15, 0.2)));
        consumer.accept(new AlloyingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "alloy/black_bronze"), "tfc:black_bronze", new Alloy("tfc:copper", 0.6, 0.7), new Alloy("tfc:gold", 0.15, 0.2), new Alloy("tfc:silver", 0.15, 0.2)));
    }
}
