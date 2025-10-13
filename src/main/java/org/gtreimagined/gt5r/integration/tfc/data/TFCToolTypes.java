package org.gtreimagined.gt5r.integration.tfc.data;

import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.Blocks;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.item.MaterialJavelin;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.tool.GTToolType;

import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.UNSPLIT_FUNCTION;

public class TFCToolTypes {
    //public static final GTToolType PROSPECTING_PICK = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "prospecting_pick", 1, 2, 10, 1.0F, -2.8F, false)).setHasContainer(false).setMaterialTypeItem(PICKAXE_HEAD).setMaterialTypeItemPredicate(m -> !m.has(FLINT));
    public static final MaterialTypeItem<?> JAVELIN_HEAD = new MaterialTypeItem<>("javelin_head", 2, true, U);
    public static final GTToolType JAVELIN = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "javelin", 2, 1, 10, 3.0f, -2.2f, false)).addEffectiveBlocks(Blocks.COBWEB).setUseAction(UseAnim.SPEAR).setToolSupplier(MaterialJavelin::new).setMaterialTypeItem(JAVELIN_HEAD);

    public static void init(){
        JAVELIN_HEAD.unSplitName().setLang(UNSPLIT_FUNCTION).setIgnoreTextureSets();
        GTTools.SCYTHE.addTags("hoe").addBehaviour(ScythHarvestBehaviour.INSTANCE);
    }
}
