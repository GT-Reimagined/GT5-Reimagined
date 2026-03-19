package org.gtreimagined.gt5r.integration;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

public class ModRecipeRemovals {
    public static void init(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        if (GTAPI.isModLoaded("railcraft")){
            provider.removeRecipe(new ResourceLocation("railcraft", "bronze_ingot_crafted_with_ingots"));
            provider.removeRecipe(new ResourceLocation("railcraft", "brass_ingot_crafted_with_ingots"));
            provider.removeRecipe(new ResourceLocation("railcraft", "invar_ingot_crafted_with_ingots"));
        }
        if (GTAPI.isModLoaded("forestry")){
            provider.removeRecipe(new ResourceLocation("forestry", "ingot_bronze_alloying"));
        }
        if (GTAPI.isModLoaded("thermal")){
            provider.removeRecipe(new ResourceLocation("thermal", "fire_charge/bronze_ingot_4"));
            provider.removeRecipe(new ResourceLocation("thermal", "fire_charge/constantan_ingot_2"));
            provider.removeRecipe(new ResourceLocation("thermal", "fire_charge/electrum_ingot_2"));
            provider.removeRecipe(new ResourceLocation("thermal", "fire_charge/invar_ingot_3"));
        }
    }
}
