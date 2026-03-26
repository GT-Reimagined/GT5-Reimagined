package org.gtreimagined.gt5r.integration.botania;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.Manasteel;
import static org.gtreimagined.gt5r.data.Materials.Steel;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.BLOCK;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT;

public class BotaniaRecipes {
    public static void init(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        output.accept(new ManaInfusionRecipe(new ResourceLocation("botania", "mana_infusion/manasteel"), INGOT.getMaterialIngredient(Steel, 1), 3000, INGOT.get(Manasteel, 1)));
        output.accept(new ManaInfusionRecipe(new ResourceLocation("botania", "mana_infusion/manasteel_block"), BLOCK.getMaterialIngredient(Steel, 1), 27000, BLOCK.get().get(Manasteel).asStack()));
    }
}
