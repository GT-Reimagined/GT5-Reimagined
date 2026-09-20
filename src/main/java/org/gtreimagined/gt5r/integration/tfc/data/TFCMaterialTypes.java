package org.gtreimagined.gt5r.integration.tfc.data;

import org.gtreimagined.gtlib.material.MaterialTypeItem;

import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class TFCMaterialTypes {

    public static final MaterialTypeItem<?> DOUBLE_INGOT = new MaterialTypeItem<>("double_ingot", true, U * 2);
    public static final MaterialTypeItem<?> SHEET = new MaterialTypeItem<>("sheet", true, U * 2);
    public static final MaterialTypeItem<?> CHISEL_HEAD = new MaterialTypeItem<>("chisel_head", true, U);
    public static final MaterialTypeItem<?> MACE_HEAD = new MaterialTypeItem<>("mace_head", true, U * 2);
    public static final MaterialTypeItem<?> PROPICK_HEAD = new MaterialTypeItem<>("propick_head", true, U);
    public static final MaterialTypeItem<?> JAVELIN_HEAD = new MaterialTypeItem<>("javelin_head", true, U);

    public static void init(){
        JAVELIN_HEAD.unSplitName().lang(UNSPLIT_FUNCTION);
        PROPICK_HEAD.unSplitName().lang((t, m) -> m.getDisplayNameString() + " " + "Prospector's Pick Head");
        CHISEL_HEAD.unSplitName().lang(UNSPLIT_FUNCTION);
        MACE_HEAD.unSplitName().lang(UNSPLIT_FUNCTION);
        DOUBLE_INGOT.unSplitName().lang(UNSPLIT_FUNCTION);
        SHEET.dependents(DOUBLE_INGOT);
        PICKAXE_HEAD.setUnitValue(U);
        AXE_HEAD.setUnitValue(U);
        SHOVEL_HEAD.setUnitValue(U);
        HOE_HEAD.setUnitValue(U);
        HAMMER_HEAD.setUnitValue(U);
        SAW_BLADE.setUnitValue(U);
        SCYTHE_BLADE.setUnitValue(U);
    }
}
