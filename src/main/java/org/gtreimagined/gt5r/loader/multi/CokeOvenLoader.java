package org.gtreimagined.gt5r.loader.multi;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.COKE_OVEN;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class CokeOvenLoader {
    public static void init() {
        COKE_OVEN.RB().ii(RecipeIngredient.of(ItemTags.LOGS, 1)).io(GEM.get(Charcoal, 1)).fo(Creosote.getLiquid(100)).add("charcoal",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(Items.COAL, 1)).io(GEM.get(CoalCoke, 1)).fo(Creosote.getLiquid(200)).add("coal_coke",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(GEM.getMaterialTag(Lignite), 1)).io(GEM.get(LigniteCoke, 1)).fo(Creosote.getLiquid(200)).add("lignite_coal_coke",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(CRUSHED_ORE.getMaterialTag(Coal), 1)).io(GEM.get(CoalCoke, 1)).fo(Creosote.getLiquid(200)).add("coal_coke_crushed",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(CRUSHED_ORE.getMaterialTag(Lignite), 1)).io(GEM.get(LigniteCoke, 1)).fo(Creosote.getLiquid(200)).add("lignite_coal_coke_crushed",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(PURIFIED_ORE.getMaterialTag(Coal), 1)).io(GEM.get(CoalCoke, 1)).fo(Creosote.getLiquid(200)).add("coal_coke_crushed_purified",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(PURIFIED_ORE.getMaterialTag(Lignite), 1)).io(GEM.get(LigniteCoke, 1)).fo(Creosote.getLiquid(200)).add("lignite_coal_coke_crushed_purified",600, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(Blocks.COAL_BLOCK, 1)).io(BLOCK.get().get(CoalCoke).asStack()).fo(Creosote.getLiquid(1800)).add("coal_coke_block",5400, 0);
        COKE_OVEN.RB().ii(RecipeIngredient.of(BLOCK.getMaterialTag(Lignite), 1)).io(BLOCK.get().get(LigniteCoke).asStack()).fo(Creosote.getLiquid(1800)).add("lignite_coal_coke_block",5400, 0);
    }
}
