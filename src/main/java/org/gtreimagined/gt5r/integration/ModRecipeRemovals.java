package org.gtreimagined.gt5r.integration;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;

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
        if (GTAPI.isModLoaded("immersiveengineering")){
            Material[] plates = new Material[]{Iron, Gold, Copper, Aluminium, Nickel, Silver, Lead, Cupronickel, Electrum, Steel, Uranium};
            for (Material plate : plates){
                String plateId = plate == Aluminium ? "aluminum" : plate.getId();
                provider.removeRecipe(new ResourceLocation("immersiveengineering", "crafting/plate_" + plateId + "_hammering"));
                provider.removeRecipe(new ResourceLocation("immersiveengineering", "crafting/stick_" + plateId));
            }
        }
    }
}
