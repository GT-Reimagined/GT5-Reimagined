package muramasa.gregtech.loader.multi;

import io.github.gregtechintergalactical.gtcore.data.GTCoreBlocks;
import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static muramasa.antimatter.data.AntimatterMaterials.Water;
import static muramasa.gregtech.data.RecipeMaps.TREE_GROWTH_SIMULATOR;

public class TreeGrowthSimulatorLoader {
    public static void init() {
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.OAK_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.OAK_LOG, 6), new ItemStack(Items.OAK_SAPLING, 3), new ItemStack(Items.STICK, 2), new ItemStack(Items.APPLE)).outputChances(1.0, 1.0, 1.0, 0.25).add("oak_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.BIRCH_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.BIRCH_LOG, 6), new ItemStack(Items.BIRCH_SAPLING, 3), new ItemStack(Items.STICK, 2)).add("birch_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.SPRUCE_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.SPRUCE_LOG, 10), new ItemStack(Items.SPRUCE_SAPLING, 5), new ItemStack(Items.STICK, 3)).add("spruce_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.JUNGLE_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.JUNGLE_LOG, 10), new ItemStack(Items.JUNGLE_SAPLING, 2), new ItemStack(Items.STICK, 2)).add("jungle_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.DARK_OAK_SAPLING, 4), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.DARK_OAK_LOG, 40), new ItemStack(Items.DARK_OAK_SAPLING, 5), new ItemStack(Items.STICK, 1), new ItemStack(Items.APPLE)).outputChances(1.0, 1.0, 1.0, 0.5).add("dark_oak_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(Items.ACACIA_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(Items.ACACIA_LOG, 8), new ItemStack(Items.ACACIA_SAPLING, 2), new ItemStack(Items.STICK, 2)).add("acacia_log", 400, 4);
        TREE_GROWTH_SIMULATOR.RB().ii(RecipeIngredient.of(GTCoreBlocks.RUBBER_SAPLING, 1), RecipeIngredient.of(GTCoreItems.Fertilizer)).fi(Water.getLiquid(1000)).io(new ItemStack(GTCoreBlocks.RUBBER_LOG, 8), new ItemStack(GTCoreBlocks.RUBBER_SAPLING, 2), new ItemStack(GTCoreItems.StickyResin)).add("rubber_log", 400, 4);
    }
}
