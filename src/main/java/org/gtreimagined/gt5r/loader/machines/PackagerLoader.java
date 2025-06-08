package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.CHUNK;
import static org.gtreimagined.gt5r.data.RecipeMaps.PACKAGER;

public class PackagerLoader {
    public static void init() {
        for (Material material : GTMaterialTypes.DUST.all()) {
            if (material.has(GTMaterialTypes.TINY_DUST)) {
                PACKAGER.RB().ii(GTMaterialTypes.TINY_DUST.getIngredient(material, 9)).io(GTMaterialTypes.DUST.get(material).getDefaultInstance()).add("dust_" + material.getId() + "_from_tiny",100, 2);
            }
            if (material.has(GTMaterialTypes.SMALL_DUST)) {
                PACKAGER.RB().ii(GTMaterialTypes.SMALL_DUST.getIngredient(material, 4)).io(GTMaterialTypes.DUST.get(material).getDefaultInstance()).add("dust_" + material.getId() + "_from_small",100, 2);
            }
        }
        for (Material material : INGOT.all()){
            if (material.has(NUGGET)){
                PACKAGER.RB().ii(NUGGET.getMaterialIngredient(material, 9)).io(INGOT.get(material)).add("ingot_" + material.getId() + "_from_nugget",100, 2);
            }
            if (material.has(CHUNK)){
                PACKAGER.RB().ii(CHUNK.getMaterialIngredient(material, 4)).io(INGOT.get(material)).add("ingot_" + material.getId() + "_from_chunk",100, 2);
            }
        }

    }
}
