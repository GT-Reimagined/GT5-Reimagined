package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.data.GTLibMaterials;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Bedrock;
import static org.gtreimagined.gtlib.data.GTLibMaterials.Deepslate;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.ELECTROMAGNETIC_SEPARATOR;

public class ElectromagneticSeparatorLoader {
    public static void init(){
        GT5RMaterialTags.ELECSEPI.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(GTLibMaterials.Iron, 1), NUGGET.get(GTLibMaterials.Iron, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_iron", 400, 24);
        });
        GT5RMaterialTags.ELECSEPG.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(GTLibMaterials.Gold, 1), NUGGET.get(GTLibMaterials.Gold, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_gold", 400, 24);
        });
        GT5RMaterialTags.ELECSEPN.all().forEach(m -> {
            ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST_PURE.getIngredient(m, 1)).io(DUST.get(m, 1), DUST_SMALL.get(Materials.Neodymium, 1), NUGGET.get(Materials.Neodymium, 1))
                    .outputChances(1.0, 0.4, 0.2)
                    .add(m.getId() + "_neodymium", 400, 24);
        });
        ELECTROMAGNETIC_SEPARATOR.RB().ii(DUST.getMaterialIngredient(Bedrock, 1)).io(DUST.get(Deepslate), DUST_TINY.get(Adamantine), DUST_TINY.get(Monazite), DUST_TINY.get(Zircon), DUST_TINY.get(Graphite)).outputChances(7000, 3000, 4200, 4410, 4410).add("bedrock_dust", 144, 64);
    }
}
