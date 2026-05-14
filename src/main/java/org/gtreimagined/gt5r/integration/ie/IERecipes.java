package org.gtreimagined.gt5r.integration.ie;

import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.api.IETags;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.builders.AlloyRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.ArcFurnaceRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.BlastFurnaceRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.BottlingMachineRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.CrusherRecipeBuilder;
import blusunrize.immersiveengineering.common.register.IEBlocks;
import blusunrize.immersiveengineering.common.register.IEBlocks.StoneDecoration;
import blusunrize.immersiveengineering.common.register.IEItems;
import blusunrize.immersiveengineering.common.register.IEItems.Ingredients;
import blusunrize.immersiveengineering.common.register.IEItems.Molds;
import blusunrize.immersiveengineering.common.register.IEItems.Tools;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class IERecipes {

    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        String ie = "immersiveengineering";
        AlloyRecipeBuilder.builder(INGOT.get(Brass, 4))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(INGOT.getMaterialTag(Zinc))
                .build(consumer, new ResourceLocation(ie, "alloysmelter/brass"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(Brass, 4))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", new IngredientWithSize(INGOT.getMaterialTag(Copper), 3))
                .addInput(INGOT.getMaterialTag(Zinc))
                .build(consumer, new ResourceLocation(ie, "arcfurnace/alloy_brass"));
        AlloyRecipeBuilder.builder(INGOT.get(RoseGold, 5))
                .addInput(INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Gold), 4))
                .build(consumer, new ResourceLocation(ie, "alloysmelter/rose_gold"));
        ArcFurnaceRecipeBuilder.builder(INGOT.get(RoseGold, 5))
                .setEnergy(51200).setTime(100)
                .addIngredient("input", INGOT.getMaterialTag(Copper))
                .addInput(new IngredientWithSize(INGOT.getMaterialTag(Gold), 4))
                .build(consumer, new ResourceLocation(ie, "arcfurnace/alloy_rose_gold"));
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
        provider.removeRecipe(new ResourceLocation(ie, "crusher/sandstone"));
        provider.removeRecipe(new ResourceLocation(ie, "crusher/red_sandstone"));
        provider.removeRecipe(new ResourceLocation(ie, "bottling/grindingdisc"));
        provider.removeRecipe(new ResourceLocation(ie, "bottling/empty_shell"));
        provider.removeRecipe(new ResourceLocation(ie, "bottling/duroplast_block"));
        provider.removeRecipe(new ResourceLocation(ie, "bottling/duroplast_plate"));
        provider.removeRecipe(new ResourceLocation(ie, "refinery/resin"));
        provider.removeRecipe(new ResourceLocation(ie, "refinery/acetaldehyde"));
        provider.removeRecipe(new ResourceLocation(ie, "blastfurnace/steel"));
        provider.removeRecipe(new ResourceLocation(ie, "blastfurnace/steel_block"));
        provider.removeRecipe(new ResourceLocation(ie, "smelting/ingot_steel_from_dust"));
        provider.removeRecipe(new ResourceLocation(ie, "smelting/ingot_steel_from_dust_from_blasting"));
        BottlingMachineRecipeBuilder.builder(new ItemStack(Ingredients.EMPTY_SHELL, 2))
                .setUseInputArray(2)
                .addInput(Molds.MOLD_BULLET_CASING)
                .addInput(new IngredientWithSize(NUGGET.getMaterialTag(Copper), 3))
                .addFluidTag(Plastic.getFluidTag(), Ref.L)
                .addResult(Molds.MOLD_BULLET_CASING)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iebottler/empty_shell"));
        BottlingMachineRecipeBuilder.builder(new ItemStack(Tools.GRINDINGDISK, 1))
                .setUseInputArray(3)
                .addInput(Molds.MOLD_GEAR)
                .addInput(new IngredientWithSize(DUST.getMaterialTag(Aluminium), 6))
                .addInput(IngredientWithSize.of(new ItemStack(Ingredients.HEMP_FIBER, 8 )))
                .addFluidTag(Plastic.getFluidTag(), Ref.L * 2)
                .addResult(Molds.MOLD_GEAR)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iebottler/grinding_disc"));
        BottlingMachineRecipeBuilder.builder(new ItemStack(StoneDecoration.DUROPLAST, 4))
                .addInput(Molds.MOLD_PACKING_4)
                .addFluidTag(Plastic.getFluidTag(), Ref.L * 16)
                .addResult(Molds.MOLD_PACKING_4)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iebottler/duroplast_block"));
        BlastFurnaceRecipeBuilder.builder(IETags.getTagsFor(EnumMetals.STEEL).ingot, 1)
                .addInput(INGOT.getMaterialTag(WroughtIron))
                .addSlag(IETags.slag, 1)
                .setTime(2400)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "ieblastfurnace/steel"));

        BlastFurnaceRecipeBuilder.builder(IETags.getItemTag(IETags.getTagsFor(EnumMetals.STEEL).storage), 1)
                .addInput(BLOCK.getMaterialTag(WroughtIron))
                .addSlag(IETags.slag, 9)
                .setTime(9*2400)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID,"ieblastfurnace/steel_block"));
        BlastFurnaceRecipeBuilder.builder(INGOT.getMaterialTag(WroughtIron), 1)
                .addInput(INGOT.getMaterialTag(Iron))
                .setTime(1800)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "ieblastfurnace/wrought_iron"));

        BlastFurnaceRecipeBuilder.builder(INGOT.getMaterialTag(WroughtIron), 1)
                .addInput(BLOCK.getMaterialTag(Iron))
                .setTime(9*1800)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID,"ieblastfurnace/wrought_iron_block"));
        CrusherRecipeBuilder.builder(new ItemStack(Items.SAND, 2))
                .addInput(IETags.getItemTag(IETags.colorlessSandstoneBlocks))
                .setEnergy(3200)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iecrusher/sandstone"));
        CrusherRecipeBuilder.builder(new ItemStack(Items.RED_SAND, 2))
                .addInput(IETags.getItemTag(IETags.redSandstoneBlocks))
                .setEnergy(3200)
                .build(consumer, new ResourceLocation(GT5Reimagined.ID, "iecrusher/red_sandstone"));
    }

    public static void initMachineRecipes(){
        Material[] wireMats = new Material[]{Copper, Electrum, Aluminium, Steel, Lead};
        for (Material mat: wireMats){
            String outId = mat == Aluminium ? "aluminum" : mat.getId();
            RecipeMaps.BENDER.RB().ii(Ingredient.of(GTAPI.get(Item.class, "1x_" + mat.getId() + "_wire", Ref.SHARED_ID)), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(1).get()).io(RegistryUtils.getItemFromID("immersiveengineering", "wire_" + outId)).add("ie_wire_" + mat.getId(), 100, 4);
        }
    }
}
