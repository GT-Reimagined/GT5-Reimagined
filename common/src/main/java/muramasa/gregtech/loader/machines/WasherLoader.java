package muramasa.gregtech.loader.machines;

import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialTags;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import net.minecraft.world.item.ItemStack;

import static muramasa.gregtech.data.RecipeMaps.ORE_WASHER;

public class WasherLoader {
    public static void init() {
        AntimatterMaterialTypes.CRUSHED.all().forEach(m -> {
            if (!m.has(AntimatterMaterialTypes.CRUSHED_PURIFIED)) return;
            ItemStack stoneDust = AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Stone, 1);

            Material aOreByProduct1 = m.getByProducts().size() >= 1 ? m.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(m);
            //Material aOreByProduct2 = m.getByProducts().size() >= 2 ? m.getByProducts().get(1) : aOreByProduct1;
            ORE_WASHER.RB().fi(AntimatterMaterials.Water.getLiquid(1000)).ii(RecipeIngredient.of(AntimatterMaterialTypes.CRUSHED.get(m,1))).io(AntimatterMaterialTypes.CRUSHED_PURIFIED.get(m,1), AntimatterMaterialTypes.DUST_TINY.get(aOreByProduct1,1), stoneDust).add("crushed_" + m.getId(),200, 24);
        });
    }
}
