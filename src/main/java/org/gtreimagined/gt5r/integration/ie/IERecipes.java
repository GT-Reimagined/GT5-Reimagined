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
        AlloyRecipeBuilder.builder(INGOT.get(SterlingSilver, 5))
                .addInput(INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Silver), 4))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iealloysmelter/sterling_silver"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(SterlingSilver, 5))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Silver), 4))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iearcfurnace/alloy_sterling_silver"));
        AlloyRecipeBuilder.builder(INGOT.get(BismuthBronze, 5))
                .addInput(INGOT.getMaterialTag(Bismuth))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Bronze), 4))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iealloysmelter/bismuth_bronze"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(BismuthBronze, 5))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", INGOT.getMaterialTag(Bismuth))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Bronze), 4))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iearcfurnace/alloy_bismuth_bronze"));
        AlloyRecipeBuilder.builder(INGOT.get(BlackBronze, 5))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Electrum), 2))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iealloysmelter/black_bronze"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(BlackBronze, 5))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Electrum), 2))
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iearcfurnace/alloy_black_bronze"));
        provider.removeRecipe(new ResourceLocation("immersiveengineering", "crusher/sandstone"));
        provider.removeRecipe(new ResourceLocation("immersiveengineering", "crusher/red_sandstone"));
    }
}
