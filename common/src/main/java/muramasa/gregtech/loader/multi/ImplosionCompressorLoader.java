package muramasa.gregtech.loader.multi;

import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.gregtech.data.GregTechMaterialTags;
import muramasa.gregtech.data.Materials;
import net.minecraft.world.item.Items;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.Diamond;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.IMPLOSION_COMPRESSOR;

public class ImplosionCompressorLoader {
    public static void init(){
        GEM.all().stream().filter(m -> !m.has(GregTechMaterialTags.CRYSTALLIZE) && !m.has(GregTechMaterialTags.NON_GEMS) && m.has(DUST)).forEach(m -> {
            int tnt = m == RedGarnet || m == YellowGarnet || m == Ruby ? 4 : m == Diamond ? 8: 6;
            IMPLOSION_COMPRESSOR.RB().ii(DUST.getMaterialIngredient(m, 4), RecipeIngredient.of(Items.TNT, tnt * 2)).io(GEM.get(m, 3), DUST_TINY.get(DarkAsh, tnt * 2)).add(m.getId() + "_from_tnt", 20, 30);
            if (m.has(GEM_EXQUISITE)) {
                IMPLOSION_COMPRESSOR.RB().ii(DUST.getMaterialIngredient(m, 6), RecipeIngredient.of(Items.TNT, tnt * 3)).io(GEM_EXQUISITE.get(m, 1), DUST_TINY.get(DarkAsh, tnt * 2)).add("exquisite_" + m.getId() + "_from_tnt", 30, 30);
            }
        });
    }
}
