package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.ELECTROMAGNETIC_SEPARATOR;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class ElectromagneticSeparatorLoader {
    public static void init(){
        GT5RMaterialTags.ELECSEPI.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(PURE_DUST.getIngredient(m, 1)).io(DUST.get(m, 1), SMALL_DUST.get(Materials.Iron, 1), NUGGET.get(Materials.Iron, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_iron", 400, 24);
        });
        GT5RMaterialTags.ELECSEPG.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(PURE_DUST.getIngredient(m, 1)).io(DUST.get(m, 1), SMALL_DUST.get(Materials.Gold, 1), NUGGET.get(Materials.Gold, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_gold", 400, 24);
        });
        GT5RMaterialTags.ELECSEPN.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(PURE_DUST.getIngredient(m, 1)).io(DUST.get(m, 1), SMALL_DUST.get(Materials.Neodymium, 1), NUGGET.get(Materials.Neodymium, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_neodymium", 400, 24);
        });
        ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST.getMaterialIngredient(Bedrock, 1)).io(DUST.get(Deepslate), TINY_DUST.get(Adamantine), TINY_DUST.get(Monazite), TINY_DUST.get(Zircon), TINY_DUST.get(Graphite)).outputChances(7000, 3000, 4200, 4410, 4410).add("bedrock_dust", 144, 64);
    }
}
