package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.SIFTER;

public class SifterLoader {
    public static void init() {
        PURIFIED_ORE.all().forEach(m -> {
            if (!m.has(GEM)) return;
            ItemStack gem = GEM.get(m, 1);
            boolean e = m.has(EXQUISITE_GEM);
            double[] chances = e ? new double[]{0.03, 0.12, 0.45, 0.14, 0.28, 0.35} : new double[]{0.01, 0.04, 0.15, 0.2, 0.4, 0.5};
            ItemStack dustPurified = DUST.get(m, 1);
            SIFTER.RB().ii(PURIFIED_ORE.getMaterialIngredient(m,1)).io(e ? EXQUISITE_GEM.get(m, 1) : gem,
                    e ? FLAWLESS_GEM.get(m, 1) : gem, gem,
                    e ? FLAWED_GEM.get(m, 2) : gem,
                    e ? CHIPPED_GEM.get(m, 4) : gem, dustPurified).outputChances(chances).add("crushed_" + m.getId(),800, 16);
        });
        SIFTER.RB().ii(RecipeIngredient.of(Tags.Items.GRAVEL, 1)).io(Items.FLINT).add("flint", 40 * 20, 16);
    }
}
