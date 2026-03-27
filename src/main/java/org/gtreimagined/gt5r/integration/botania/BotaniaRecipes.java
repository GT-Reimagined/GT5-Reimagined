package org.gtreimagined.gt5r.integration.botania;

import com.google.common.collect.ImmutableMap;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

import static org.gtreimagined.gt5r.data.Materials.Manasteel;
import static org.gtreimagined.gt5r.data.Materials.Steel;
import static org.gtreimagined.gt5r.integration.botania.BotaniaRegistrar.botItem;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.BLOCK;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.INGOT;

public class BotaniaRecipes {
    public static void init(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
        output.accept(new ManaInfusionRecipe(new ResourceLocation("botania", "mana_infusion/manasteel"), INGOT.getMaterialIngredient(Steel, 1), 3000, INGOT.get(Manasteel, 1)));
        output.accept(new ManaInfusionRecipe(new ResourceLocation("botania", "mana_infusion/manasteel_block"), BLOCK.getMaterialIngredient(Steel, 1), 27000, BLOCK.get().get(Manasteel).asStack()));
        provider.addItemRecipe(output, GT5Reimagined.ID, "mana_gun", "mana_tools", botItem("mana_gun"),
                ImmutableMap.of(
                        'P', botItem("redstone_spreader"),
                        'R', botItem("rune_mana"),
                        'D', botItem("mana_diamond"),
                        'L', TagUtils.getItemTag(new ResourceLocation("botania", "livingwood_logs")),
                        'G', GT5RBlocks.POWDER_BARREL), "PRD", " LG", "  L");
    }
}
