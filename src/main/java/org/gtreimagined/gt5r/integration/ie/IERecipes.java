package org.gtreimagined.gt5r.integration.ie;

import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.builders.AlloyRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.ArcFurnaceRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT;

public class IERecipes {

    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        AlloyRecipeBuilder.builder(INGOT.get(Brass, 4))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(INGOT.getMaterialTag(Zinc))
                .build(consumer, new ResourceLocation("immersiveengineering", "alloysmelter/brass"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(Brass, 4))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(INGOT.getMaterialTag(Zinc))
                .build(consumer, new ResourceLocation("immersiveengineering", "arcfurnace/alloy_brass"));
        AlloyRecipeBuilder.builder(INGOT.get(RoseGold, 5))
                .addInput(INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Gold), 4))
                .build(consumer, new ResourceLocation("immersiveengineering", "alloysmelter/rose_gold"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(RoseGold, 5))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Gold), 4))
                .build(consumer, new ResourceLocation("immersiveengineering", "arcfurnace/alloy_rose_gold"));
    }
}
