package org.gtreimagined.gt5r.integration.tfc.recipes;

import com.google.gson.JsonObject;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Ore.Grade;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.items.Powder;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.recipes.ChiselRecipe.Mode;
import net.dries007.tfc.util.Metal.Default;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.tfc.Metals;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.ChiselFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.HeatingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.QuernFinishedRecipe;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.DynamicDataPack;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gtcore.data.GTCoreItems.Plantball;

public class MiscTFCRecipes {
    public static void initRecipes(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        consumer.accept(new QuernFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "quern/raw_redstone"), Ingredient.of(GTMaterialTypes.RAW_ORE.getMaterialTag(Materials.Redstone)), new ItemStack(Items.REDSTONE, 8)));
        consumer.accept(new QuernFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "quern/raw_graphite"), Ingredient.of(GTMaterialTypes.RAW_ORE.getMaterialTag(Materials.Graphite)), new ItemStack(TFCItems.POWDERS.get(Powder.GRAPHITE).get(), 4)));
        for (Ore ore : Ore.values()){
            if (ore.isGraded()){
                TFCMetal tfcMetal = metalFromOre(ore);
                Fluid fluid = TFCFluids.METALS.get(tfcMetal.metal).getSource();
                for (Grade grade : Grade.values()){
                    consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(Ref.MOD_TFC, "heating/ore/" + grade.name().toLowerCase() + "_" + ore.name().toLowerCase()),
                            Ingredient.of(TFCItems.GRADED_ORES.get(ore).get(grade).get()), new FluidStack(fluid, grade == Grade.POOR ? 25 : grade == Grade.NORMAL ? 50 : 100), tfcMetal.temperature));
                }
            }
        }
        consumer.accept(new ChiselFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "chisel/firebricks"), TFCBlocks.FIRE_BRICKS.get(), GT5RBlocks.FIRE_BRICKS, Mode.SMOOTH));
        provider.shapeless(consumer, GT5Reimagined.ID, "firebricks", "bricks", new ItemStack(GT5RBlocks.FIRE_BRICKS), TFCBlocks.FIRE_BRICKS.get(), TFCTags.Items.CHISELS);
        SimpleCookingRecipeBuilder.smelting(RecipeIngredient.of(TFCItems.UNFIRED_FIRE_BRICK.get(), 1), TFCItems.FIRE_BRICK.get(), 0.5F, 200).unlockedBy("has_unfired_fire_brick", provider.hasSafeItem(TFCItems.UNFIRED_FIRE_BRICK.get())).save(consumer, GT5Reimagined.ID + ":firebrick");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "plantball_from_tfc_fruits", "misc", Plantball, of(
                'C', TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "foods/fruits"))
        ), "CCC", "C C", "CCC");
        provider.addItemRecipe(consumer, GT5Reimagined.ID, "plantball_from_tfc_seeds", "misc", Plantball, of(
                'S', TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "seeds"))
        ), "SSS", "S S", "SSS");
        provider.addStackRecipe(consumer, GT5Reimagined.ID, "plantball_from_tfc_saplings", "misc", new ItemStack(Plantball, 2), of(
                'S', TagUtils.getItemTag(new ResourceLocation(Ref.MOD_TFC, "saplings"))
        ), "SSS", "S S", "SSS");
    }

    private static TFCMetal metalFromOre(Ore ore){
        return switch (ore) {
            case BISMUTHINITE -> TFCMetal.BISMUTH;
            case HEMATITE, LIMONITE, MAGNETITE -> TFCMetal.CAST_IRON;
            case NATIVE_COPPER, MALACHITE, TETRAHEDRITE -> TFCMetal.COPPER;
            case GARNIERITE -> TFCMetal.NICKEL;
            case SPHALERITE -> TFCMetal.ZINC;
            case CASSITERITE -> TFCMetal.TIN;
            case NATIVE_GOLD -> TFCMetal.GOLD;
            case NATIVE_SILVER -> TFCMetal.SILVER;
            default -> TFCMetal.UNKNOWN;
        };
    }

    private enum TFCMetal{
        BISMUTH(Default.BISMUTH, 270),
        CAST_IRON(Default.CAST_IRON, 1535),
        COPPER(Default.COPPER, 1080),
        NICKEL(Default.NICKEL, 1453),
        ZINC(Default.ZINC, 420),
        TIN(Default.TIN, 230),
        GOLD(Default.GOLD, 1060),
        SILVER(Default.SILVER, 961),
        UNKNOWN(Default.UNKNOWN, -1),;


        private final Default metal;
        private final int temperature;

        private TFCMetal(Default metal, int temperature){
            this.metal = metal;
            this.temperature = temperature;
        }
    }


}
