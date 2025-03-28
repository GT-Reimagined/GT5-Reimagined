package org.gtreimagined.gt5r.material;

import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.IMaterialTag;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.data.ToolData;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

import static org.gtreimagined.gtlib.material.MaterialTags.*;

public class GregTechMaterialEvent extends MaterialEvent<GregTechMaterialEvent> {
    public GregTechMaterialEvent asSolid(int meltingPoint, int blastFurnaceTemp, IMaterialTag... tags) {
        super.asSolid(meltingPoint, tags);
        GT5RMaterialTags.BLAST_FURNACE_TEMP.add(material, blastFurnaceTemp);
        if (blastFurnaceTemp >= 2000){
            flags(GT5RMaterialTags.NEEDS_BLAST_FURNACE, HAS_CUSTOM_SMELTING);
        }
        if (blastFurnaceTemp > 2400) {
            flags(GTMaterialTypes.INGOT_HOT);
        }
        return this;
    }

    public GregTechMaterialEvent asMetal(int meltingPoint, int blastFurnaceTemp, IMaterialTag... tags) {
        flags(METAL, MOLTEN);
        asSolid(meltingPoint, blastFurnaceTemp, tags);
        return this;
    }

    public GregTechMaterialEvent forceBF(boolean hotIngot) {
        flags(GT5RMaterialTags.NEEDS_BLAST_FURNACE, HAS_CUSTOM_SMELTING);
        if (hotIngot) {
            flags(GTMaterialTypes.INGOT_HOT);
        }
        return this;
    }

    @Override
    public GregTechMaterialEvent asSolid(int meltingPoint, IMaterialTag... tags) {
        return asSolid(meltingPoint, meltingPoint, tags);
    }

    @Override
    public GregTechMaterialEvent asMetal(int meltingPoint, IMaterialTag... tags) {
        return asMetal(meltingPoint, meltingPoint, tags);
    }

    public GregTechMaterialEvent elecTicks(int ticks){
        GT5RMaterialTags.ELEC_TICKS.add(material, ticks);
        return this;
    }

    @Override
    protected GregTechMaterialEvent buildTool(ToolData builder) {
        flags(GTMaterialTypes.ROD_LONG);
        return super.buildTool(builder);
    }

    public GregTechMaterialEvent thermal(Material byProduct){
        GT5RMaterialTags.THERMAL_CENTRIFUGE_EXPLICIT.add(this.material, byProduct);
        return this;
    }

    public GregTechMaterialEvent bathMercury(Material byProduct){
        GT5RMaterialTags.BATH_MERCURY.add(this.material, byProduct);
        return this;
    }

    public GregTechMaterialEvent bathPersulfate(Material byProduct){
        GT5RMaterialTags.BATH_PERSULFATE.add(this.material, byProduct);
        return this;
    }
}
