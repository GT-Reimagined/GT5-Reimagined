package org.gtreimagined.gt5r.integration.tfc.data;

import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.Blocks;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.tfc.item.MaterialJavelin;
import org.gtreimagined.gt5r.integration.tfc.item.ProspectingBehaviour;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.tool.behaviour.BehaviourTorchPlacing;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.UNSPLIT_FUNCTION;
import static org.gtreimagined.gtlib.material.MaterialTags.FLINT;

public class TFCToolTypes {
    public static final GTToolType PROPICK = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "propick", 1, 2, 10, 1.0F, -2.8F, false)).setHasContainer(false).setMaterialTypeItem(TFCMaterialTypes.PROPICK_HEAD).setMaterialTypeItemPredicate(m -> !m.has(FLINT));
    public static final GTToolType JAVELIN = GTAPI.register(GTToolType.class, new GTToolType(GT5Reimagined.ID, "javelin", 2, 1, 10, 3.0f, -2.2f, false)).addEffectiveBlocks(Blocks.COBWEB).setUseAction(UseAnim.SPEAR).setToolSupplier(MaterialJavelin::new).setMaterialTypeItem(TFCMaterialTypes.JAVELIN_HEAD);

    public static void init(){
        GTTools.SCYTHE.addTags("hoe").addBehaviour(ScythHarvestBehaviour.INSTANCE);
        PROPICK.setCustomName("Prospector's Pick").addBehaviour(ProspectingBehaviour.INSTANCE);
    }
}
