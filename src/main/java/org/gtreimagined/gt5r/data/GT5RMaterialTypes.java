package org.gtreimagined.gt5r.data;

import org.gtreimagined.gt5r.items.ItemTurbineRotor;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.material.MaterialTypeItem;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class GT5RMaterialTypes {

    public static final MaterialTypeItem<?> TURBINE_BLADE = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("turbine_blade", 2, true, (Ref.U * 3) + (Ref.U8 * 2)));//.unSplitName();
    public static final MaterialTypeItem<?> HUGE_BROKEN_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("huge_broken_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 8));
    public static final MaterialTypeItem<?> LARGE_BROKEN_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("large_broken_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 6));
    public static final MaterialTypeItem<?> BROKEN_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("broken_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 4));
    public static final MaterialTypeItem<?> SMALL_BROKEN_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("small_broken_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 2));
    public static final MaterialTypeItem<?> HUGE_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("huge_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 16, ItemTurbineRotor::new));
    public static final MaterialTypeItem<?> LARGE_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("large_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 12, ItemTurbineRotor::new));
    public static final MaterialTypeItem<?> TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 8, ItemTurbineRotor::new));
    public static final MaterialTypeItem<?> SMALL_TURBINE_ROTOR = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("small_turbine_rotor", 2, true, TURBINE_BLADE.getUnitValue() * 4, ItemTurbineRotor::new));
    public static final MaterialTypeItem<?> BOULE = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("boule", 2, true, Ref.U * 4));
    public static final MaterialTypeItem<?> CHAMBER = GTAPI.register(MaterialTypeItem.class, new MaterialTypeItem<>("chamber", 2, true, Ref.U * 7));

    public static void init(){
        BOULE.setIgnoreTextureSets();
        CHAMBER.setIgnoreTextureSets();
        TURBINE_BLADE.setLang(m -> m.getDisplayNameString() + " Turbine Blade").unSplitName().setIgnoreTextureSets();
        SMALL_BROKEN_TURBINE_ROTOR.setLang(m -> "Broken Small " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        BROKEN_TURBINE_ROTOR.setLang(m -> "Broken " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        LARGE_BROKEN_TURBINE_ROTOR.setLang(m -> "Broken Large " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        HUGE_BROKEN_TURBINE_ROTOR.setLang(m -> "Broken Huge " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        SMALL_TURBINE_ROTOR.setLang(m -> "Small " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        TURBINE_ROTOR.setLang(m -> m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        LARGE_TURBINE_ROTOR.setLang(m -> "Large " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        HUGE_TURBINE_ROTOR.setLang(m -> "Huge " + m.getDisplayNameString() + " Turbine Rotor").unSplitName().setIgnoreTextureSets();
        SMALL_BROKEN_TURBINE_ROTOR.dependents(TURBINE_BLADE);
        BROKEN_TURBINE_ROTOR.dependents(TURBINE_BLADE);
        LARGE_BROKEN_TURBINE_ROTOR.dependents(TURBINE_BLADE);
        HUGE_BROKEN_TURBINE_ROTOR.dependents(TURBINE_BLADE);
        SMALL_TURBINE_ROTOR.dependents(SMALL_BROKEN_TURBINE_ROTOR);
        TURBINE_ROTOR.dependents(BROKEN_TURBINE_ROTOR, SMALL_TURBINE_ROTOR);
        LARGE_TURBINE_ROTOR.dependents(LARGE_BROKEN_TURBINE_ROTOR, TURBINE_ROTOR);
        HUGE_TURBINE_ROTOR.dependents(HUGE_BROKEN_TURBINE_ROTOR, LARGE_TURBINE_ROTOR);
        TURBINE_BLADE.dependents(SCREW, PLATE);
    }
}
