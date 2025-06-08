package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.UNPACKAGER;

public class UnpackagerLoader {
    public static void init() {
        for (Material material : DUST.all()) {
            if (material.has(TINY_DUST)) {
                UNPACKAGER.RB().ii(DUST.getMaterialIngredient(material, 1), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(9)).io(TINY_DUST.get(material, 9)).add("dust_tiny_" + material.getId(),100, 2);
            }
            if (material.has(SMALL_DUST)) {
                UNPACKAGER.RB().ii(DUST.getMaterialIngredient(material, 1), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(4)).io(SMALL_DUST.get(material, 4)).add("dust_small_" + material.getId(),100, 2);
            }
        }
        for (Material material : INGOT.all()) {
            if (material.has(NUGGET)) {
                UNPACKAGER.RB().ii(INGOT.getMaterialIngredient(material, 1), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(9)).io(NUGGET.get(material, 9)).add("nugget_" + material.getId(),100, 2);
            }
            if (material.has(CHUNK)) {
                UNPACKAGER.RB().ii(INGOT.getMaterialIngredient(material, 1), GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(4)).io(CHUNK.get(material, 4)).add("chunk_" + material.getId(),100, 2);
            }
        }

    }
}
