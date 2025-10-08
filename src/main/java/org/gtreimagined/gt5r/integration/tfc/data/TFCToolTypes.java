package org.gtreimagined.gt5r.integration.tfc.data;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.item.MaterialJavelin;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.tool.GTToolType;

import static net.minecraft.world.level.material.Material.ICE_SOLID;
import static net.minecraft.world.level.material.Material.PISTON;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.PICKAXE_HEAD;
import static org.gtreimagined.gtlib.material.MaterialTags.FLINT;

public class TFCToolTypes {
    //public static final GTToolType PROSPECTING_PICK = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "prospecting_pick", 1, 2, 10, 1.0F, -2.8F, false)).setHasContainer(false).setMaterialTypeItem(PICKAXE_HEAD).setMaterialTypeItemPredicate(m -> !m.has(FLINT));
    public static final GTToolType JAVELIN = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "javelin", 2, 1, 10, 2.0f, -2.8f, false)).setToolSupplier(MaterialJavelin::new);

    public static void init(){

    }
}
