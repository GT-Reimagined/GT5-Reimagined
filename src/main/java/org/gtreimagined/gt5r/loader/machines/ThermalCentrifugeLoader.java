package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

import static org.gtreimagined.gt5r.data.Materials.Stone;
import static org.gtreimagined.gt5r.data.RecipeMaps.THERMAL_CENTRIFUGE;

public class ThermalCentrifugeLoader {
    public static void init() {
        GTMaterialTypes.PURIFIED_ORE.all().forEach(m -> {
            Material aOreByProduct1 = m.getByProducts().size() >= 1 ? m.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(m);
            Material aOreByProduct2 = m.has(GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT) ? GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT.getMapping(m) : m.getByProducts().size() >= 2 ? m.getByProducts().get(1) : aOreByProduct1;
            ItemStack stoneDust = GTMaterialTypes.DUST.get(Stone, 1);

            THERMAL_CENTRIFUGE.RB().ii(RecipeIngredient.of(GTMaterialTypes.PURIFIED_ORE.get(m),1)).io(GTMaterialTypes.REFINED_ORE.get(m, 1), GTMaterialTypes.TINY_DUST.get(aOreByProduct2, 1)).add("purified_" + m.getId(),500, 48,0,2);
            THERMAL_CENTRIFUGE.RB().ii(RecipeIngredient.of(GTMaterialTypes.CRUSHED_ORE.get(m),1)).io(GTMaterialTypes.REFINED_ORE.get(m, 1), GTMaterialTypes.TINY_DUST.get(aOreByProduct2, 1), stoneDust).add("crushed_" + m.getId(),500, 48,0,2);
        });
    }
}
