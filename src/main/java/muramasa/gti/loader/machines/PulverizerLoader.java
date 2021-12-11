package muramasa.gti.loader.machines;

import muramasa.antimatter.material.Material;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static muramasa.antimatter.Data.*;
import static muramasa.gti.data.RecipeMaps.PULVERIZING;

public class PulverizerLoader {
    public static void init() {
        CRUSHED.all().forEach(m -> {
            if (!m.has(ORE)) return;
            if (!m.has(CRUSHED)) return;
            int multiplier = 1;
            RecipeIngredient ore = RecipeIngredient.of(ORE.getMaterialTag(m),1), crushed = CRUSHED.getIngredient(m, 1);
            ItemStack crushedStack = CRUSHED.get(m,1);
            ItemStack stoneDust = DUST.get(Stone, 1);
            ItemStack dustStack = DUST.get(Stone, 1);

            //TODO better way to do this
            Material aOreByProduct1 = m.getByProducts().size() >= 1 ? m.getByProducts().get(0) : m.getMacerateInto();
            Material aOreByProduct2 = m.getByProducts().size() >= 2 ? m.getByProducts().get(1) : aOreByProduct1;

            PULVERIZING.RB().ii(ore).io(Utils.ca((m.getOreMulti() * multiplier) * 2, crushedStack), m.getByProducts().size() > 0 ? DUST.get(m.getByProducts().get(0), 1) : dustStack, stoneDust).chances(100, 10 * multiplier * m.getByProductMulti(), 50).add(400, 2);
            PULVERIZING.RB().ii(crushed).io(DUST_IMPURE.get(m.getMacerateInto(), 1), DUST.get(aOreByProduct1, 1)).chances(100, 10).add(400, 2);

            if (m.has(CRUSHED_CENTRIFUGED)) {
                PULVERIZING.RB().ii(RecipeIngredient.of(CRUSHED_CENTRIFUGED.get(m,1))).io(DUST.get(m.getMacerateInto(), 1), DUST.get(aOreByProduct2, 1)).chances(100, 10).add(400, 2);
            }
            if (m.has(CRUSHED_PURIFIED) && m.has(DUST_PURE)) {
                PULVERIZING.RB().ii(CRUSHED_PURIFIED.getIngredient(m, 1)).io(DUST_PURE.get(m.getMacerateInto(), 1), DUST.get(aOreByProduct1, 1)).chances(100, 10).add(400, 2);
            }
            if (m.has(RAW_ORE)){
                PULVERIZING.RB().ii(RecipeIngredient.of(RAW_ORE.getMaterialTag(m), 1)).io(Utils.ca((m.getOreMulti() * multiplier) * 2, crushedStack), DUST.get(aOreByProduct1, 1)).chances(100, 10 * multiplier * m.getByProductMulti()).add(400, 2);
            }
        });
        GEM_BRITTLE.all().forEach(m -> {
            if (!m.has(DUST)) return;
            PULVERIZING.RB().ii(RecipeIngredient.of(GEM_BRITTLE.get(m),1)).io(DUST_SMALL.get(m,2)).add(40,8);
        });
        GEM_POLISHED.all().forEach(m -> {
            if (!m.has(DUST)) return;
            PULVERIZING.RB().ii(RecipeIngredient.of(GEM_POLISHED.get(m),1)).io(DUST.get(m,2)).add(60,16);
        });
        PULVERIZING.RB().ii(RecipeIngredient.of(Items.STONE,1)).io(new ItemStack(Items.COBBLESTONE,1)).add(100,2);
        PULVERIZING.RB().ii(RecipeIngredient.of(Items.COBBLESTONE,1)).io(new ItemStack(Items.GRAVEL,1)).add(100,2);
        PULVERIZING.RB().ii(RecipeIngredient.of(Items.GRAVEL,1)).io(new ItemStack(Items.SAND,1)).add(100,2);


        //INGOT -> DUST
        INGOT.all().forEach(t -> {
            if (!t.has(DUST)) return;
            PULVERIZING.RB().ii(RecipeIngredient.of(INGOT.getMaterialTag(t),1)).io(DUST.get(t,1)).add(40,2);
        });
    }
}
