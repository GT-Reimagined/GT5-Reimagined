package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;

import static org.gtreimagined.gt5r.data.Materials.Stone;
import static org.gtreimagined.gt5r.data.Materials.Water;
import static org.gtreimagined.gt5r.data.RecipeMaps.ORE_WASHER;

public class OreWasherLoader {
    public static void init() {
        GTMaterialTypes.CRUSHED.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.CRUSHED_PURIFIED)) return;
            ItemStack stoneDust = GTMaterialTypes.DUST.get(Stone, 1);

            Material aOreByProduct1 = m.getByProducts().size() >= 1 ? m.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(m);
            //Material aOreByProduct2 = m.getByProducts().size() >= 2 ? m.getByProducts().get(1) : aOreByProduct1;
            ORE_WASHER.RB().fi(Water.getLiquid(1000)).ii(RecipeIngredient.of(GTMaterialTypes.CRUSHED.get(m,1))).io(GTMaterialTypes.CRUSHED_PURIFIED.get(m,1), GTMaterialTypes.DUST_TINY.get(aOreByProduct1,1), stoneDust).add("crushed_" + m.getId(),200, 24);
        });
    }
}
