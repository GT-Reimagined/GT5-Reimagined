package org.gtreimagined.gt5r.integration.tfc.data;

import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.util.Utils;

import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class TFCMaterialTypes {

    public static final MaterialTypeItem<?> CHISEL_HEAD = new MaterialTypeItem<>("chisel_head", 2, true, U);
    public static final MaterialTypeItem<?> MACE_HEAD = new MaterialTypeItem<>("mace_head", 2, true, U * 2);
    public static final MaterialTypeItem<?> PROPICK_HEAD = new MaterialTypeItem<>("propick_head", 2, true, U);
    public static final MaterialTypeItem<?> JAVELIN_HEAD = new MaterialTypeItem<>("javelin_head", 2, true, U);

    public static void init(){
        JAVELIN_HEAD.unSplitName().setLang(UNSPLIT_FUNCTION).setIgnoreTextureSets();
        PROPICK_HEAD.unSplitName().setLang((t, m) -> m.getDisplayNameString() + " " + "Prospector's Pick Head").setIgnoreTextureSets();
        CHISEL_HEAD.unSplitName().setLang(UNSPLIT_FUNCTION).setIgnoreTextureSets();
        MACE_HEAD.unSplitName().setLang(UNSPLIT_FUNCTION).setIgnoreTextureSets();
        PICKAXE_HEAD.setUnitValue(U);
        AXE_HEAD.setUnitValue(U);
        SHOVEL_HEAD.setUnitValue(U);
        HOE_HEAD.setUnitValue(U);
        HAMMER_HEAD.setUnitValue(U);
        SAW_BLADE.setUnitValue(U);
        SCYTHE_BLADE.setUnitValue(U);
    }
}
