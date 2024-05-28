package muramasa.gregtech.material;

import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.event.MaterialEvent;
import muramasa.antimatter.material.IMaterialTag;
import muramasa.antimatter.material.data.ToolData;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.gregtech.data.GregTechMaterialTags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Arrays;

import static muramasa.antimatter.material.MaterialTags.*;

public class GregTechMaterialEvent extends MaterialEvent<GregTechMaterialEvent> {
    public GregTechMaterialEvent asSolid(int meltingPoint, int blastFurnaceTemp, IMaterialTag... tags) {
        asSolid(meltingPoint, tags);
        GregTechMaterialTags.BLAST_FURNACE_TEMP.add(material, blastFurnaceTemp);
        if (blastFurnaceTemp >= 1200){
            flags(GregTechMaterialTags.NEEDS_BLAST_FURNACE, HAS_CUSTOM_SMELTING);
        }
        if (blastFurnaceTemp > 1750) {
            flags(AntimatterMaterialTypes.INGOT_HOT);
        }
        return this;
    }

    public GregTechMaterialEvent asMetal(int meltingPoint, int blastFurnaceTemp, IMaterialTag... tags) {
        flags(METAL);
        asSolid(meltingPoint, blastFurnaceTemp, tags);
        return this;
    }

    public GregTechMaterialEvent elecTicks(int ticks){
        GregTechMaterialTags.ELEC_TICKS.add(material, ticks);
        return this;
    }

    @Override
    protected GregTechMaterialEvent buildTool(ToolData builder) {
        flags(AntimatterMaterialTypes.ROD_LONG);
        return super.buildTool(builder);
    }
}
