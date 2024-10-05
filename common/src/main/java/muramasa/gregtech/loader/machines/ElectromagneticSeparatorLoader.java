package muramasa.gregtech.loader.machines;

import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.gregtech.data.GT5RMaterialTags;
import muramasa.gregtech.data.Materials;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.gregtech.data.RecipeMaps.ELECTROMAGNETIC_SEPARATOR;

public class ElectromagneticSeparatorLoader {
    public static void init(){
        GT5RMaterialTags.ELECSEPI.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(AntimatterMaterials.Iron, 1), NUGGET.get(AntimatterMaterials.Iron, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_iron", 400, 24);
        });
        GT5RMaterialTags.ELECSEPG.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(AntimatterMaterials.Gold, 1), NUGGET.get(AntimatterMaterials.Gold, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_gold", 400, 24);
        });
        GT5RMaterialTags.ELECSEPN.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(Materials.Neodymium, 1), NUGGET.get(Materials.Neodymium, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_neodymium", 400, 24);
        });
    }
}
