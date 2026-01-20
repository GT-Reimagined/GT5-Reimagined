package org.gtreimagined.gt5r.integration.tfc.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.KnappingFinishedRecipe;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.Flint;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.JAVELIN_HEAD;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class FlintKnappingRecipes {
    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider) {
        ResourceLocation fk = new ResourceLocation(GT5Reimagined.ID, "flint");
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_axe_head"), fk,
                new String[]{
                        " X   ",
                        "XXXX ",
                        "XXXXX",
                        "XXXX ",
                        " X   "
                },
                Ingredient.of(Items.FLINT), AXE_HEAD.get(Flint, 1)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_1"), fk,
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "XX   "
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head_2"), fk,
                new String[]{
                        "XXXXX",
                        "XX   ",
                        "     ",
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 2)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_hoe_head"), fk,
                new String[]{
                        "XXXXX",
                        "   XX"
                },
                Ingredient.of(Items.FLINT), HOE_HEAD.get(Flint, 1)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_1"), fk,
                new String[]{
                        "X  X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_2"), fk,
                new String[]{
                        "X   X",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head_3"), fk,
                new String[]{
                        " X X ",
                        "XX XX",
                        "XX XX",
                        "XX XX",
                        "XX XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 2)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_knife_head"), fk,
                new String[]{
                        "X ",
                        "XX",
                        "XX",
                        "XX",
                        "XX"
                },
                Ingredient.of(Items.FLINT), KNIFE_BLADE.get(Flint, 1)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_shovel_head"), fk,
                new String[]{
                        "XXX",
                        "XXX",
                        "XXX",
                        "XXX",
                        " X "
                },
                Ingredient.of(Items.FLINT), SHOVEL_HEAD.get(Flint, 1)));
        consumer.accept(new KnappingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "rock_knapping/flint_javelin_head"), fk,
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
