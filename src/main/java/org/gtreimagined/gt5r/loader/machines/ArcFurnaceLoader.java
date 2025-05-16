package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class ArcFurnaceLoader {
    public static void init() {
        DUST.all().forEach(m -> {
            if (!m.has(INGOT) || m.has(MaterialTags.HAS_CUSTOM_SMELTING)) return;
            Material output = m == Iron ? WroughtIron : m == Copper ? AnnealedCopper : m;
            RecipeMaps.ARC_FURNACE.RB().ii(DUST.getMaterialIngredient(m, 1)).fi(Oxygen.getGas((int)m.getMass())).io(INGOT.get(output)).add(m.getId() + "_dust_to_" + output.getId() + "_ingot", m.getMass(), 30, 0, 3);
            if (m == Iron || m == Copper){
                RecipeMaps.ARC_FURNACE.RB().ii(INGOT.getMaterialIngredient(m, 1)).fi(Oxygen.getGas((int)m.getMass())).io(INGOT.get(output)).add(m.getId() + "_ingot_to_" + output.getId() + "_ingot", m.getMass(), 30, 0, 3);
            }
        });
    }
}
