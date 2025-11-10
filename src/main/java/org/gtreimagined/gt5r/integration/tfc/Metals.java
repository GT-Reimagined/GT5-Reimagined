package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.datagen.DynamicDataPack;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gt5r.data.Materials.Invar;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.SHEET;
import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class Metals {
    public static final Object2IntMap<Material> METALS = new Object2IntArrayMap<>();
    public static void init(){
        addMetalFromMaterial(Invar, 3);
    }

    private static void addMetalFromMaterial(Material material, int tier){
        addMetalFromMaterial(material, tier, 0.00857, 2.857);
    }

    private static void addMetalFromMaterial(Material material, int tier, double specificHeatCapacity, double heatCapacityForHeating){
        METALS.put(material, tier);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tier", tier);
        jsonObject.addProperty("fluid", "antimatter_shared:liquid_" + material.getId());
        int meltTemp = MaterialTags.MELTING_POINT.get(material) - 273;
        jsonObject.addProperty("melt_temperature", meltTemp);
        jsonObject.addProperty("specific_heat_capacity", specificHeatCapacity);
        JsonObject object = new JsonObject();
        object.addProperty("tag", INGOT.getMaterialTag(material).location().toString());
        jsonObject.add("ingots", object);
        object = new JsonObject();
        object.addProperty("tag", SHEET.getMaterialTag(material).location().toString());
        jsonObject.add("sheets", object);
        GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/metals/" + material.getId() + ".json"), jsonObject.toString().getBytes());
        MaterialTypeItem<?>[] types = new MaterialTypeItem[]{INGOT, SHEET, PLATE, ROD, CHUNK, DUST};
        Arrays.stream(types).forEach(t -> {
            if (!material.has(t)) return;
            JsonObject j = new JsonObject();
            JsonObject ingrediient = new JsonObject();
            ingrediient.addProperty("tag", t.getMaterialTag(material).location().toString());
            j.add("ingredient", ingrediient);
            double ratio = (double) t.getUnitValue() / U;
            j.addProperty("heat_capacity", heatCapacityForHeating * ratio);
            j.addProperty("forging_temperature", meltTemp * 0.6);
            j.addProperty("welding_temperature", meltTemp * 0.8);
            GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + t.getId()), j.toString().getBytes());
        });
    }
}
