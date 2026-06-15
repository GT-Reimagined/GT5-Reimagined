package org.gtreimagined.gt5r.items;

import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.GT5RMaterialTags.TurbineRotorData;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialItem;
import org.gtreimagined.gtlib.material.MaterialType;

public class ItemTurbine extends MaterialItem {
    float efficiency;
    float speed;
    public ItemTurbine(String domain, MaterialType<?> type, Material material) {
        super(domain, type, material, new Properties().durability(material.has(GT5RMaterialTags.TURBINE_DATA) ? GT5RMaterialTags.TURBINE_DATA.get(material).durability() : 1).setNoRepair());
        if (material.has(GT5RMaterialTags.TURBINE_DATA)){
            TurbineRotorData data = GT5RMaterialTags.TURBINE_DATA.get(material);
            this.efficiency = data.efficiency();
            this.speed = data.speed();
        } else {
            this.efficiency = 0;
            this.speed = 0;
        }
    }
}
