package org.gtreimagined.gt5r.integration.tfc.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.RockKnappingFinishedRecipe;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.Flint;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.JAVELIN_HEAD;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class RockKnappingRecipes {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_axe_head"),
                new String[]{
                        " X   ",
                        "XXXX ",
                        "XXXXX",
                        "XXXX ",
                        " X   "
                },
                Ingredient.of(Items.FLINT), AXE_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_1"),
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "XX   "
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_2"),
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head"),
                new String[]{
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_1"),
                new String[]{
                        "X  X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_2"),
                new String[]{
                        "X   X",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_3"),
                new String[]{
                        " X X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head"),
                new String[]{
                        "X ",
                        "XX",
                        "XX",
                        "XX",
                        "XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_shovel_head"),
                new String[]{
                        "XXX",
                        "XXX",
                        "XXX",
                        "XXX",
                        " X "
                },
                Ingredient.of(Items.FLINT), SHOVEL_HEAD.get(Flint, 1)));
        consumer.accept(new RockKnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_javelin_head"),
                new String[]{
                        "XXX  ",
                        "XXXX ",
                        "XXXXX",
                        " XXX ",
                        "  X  "
                },
                Ingredient.of(Items.FLINT), JAVELIN_HEAD.get(Flint, 1)));
    }
}
