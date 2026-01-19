package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.material.data.ToolData;
import org.gtreimagined.gtlib.tool.GTToolType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.DOUBLE_INGOT;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.SHEET;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.JAVELIN;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.PROPICK;
import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.data.GTTools.SCYTHE;
import static org.gtreimagined.gtlib.material.MaterialTags.TOOLS;

public class Metals {
    public static final Object2ObjectMap<Material, Tuple<Boolean, Integer>> METALS = new Object2ObjectOpenHashMap<>();
    public static void init(){
        addMetalFromMaterial(Aluminium, 3, 0.1, 3.333);
        addMetalFromMaterial(Antimony, 1, 0.012, 4);
        addMetalFromMaterial(Beryllium,3);
        addMetalFromMaterial(Lead, 1, 0.01364, 4.545);
        addMetalFromMaterial(BatteryAlloy, 1, 0.013312, 4.436);
        addMetalFromMaterial(Cupronickel, 2,0.00741, 2.47);
        addMetalFromMaterial(DamascusSteel, 4);
        addMetalFromMaterial(Electrum, 2, 0.005625, 1.875);
        addMetalFromMaterial(Invar, 3);
        addHeatableToolFromTFCMetal(RoseGold, 1, 960, 2.857, false);
        addHeatableToolFromTFCMetal(SterlingSilver, 1, 950, 2.857, false);
        addHeatableToolFromTFCMetal(BismuthBronze, 2, 985, 2.857, true);
        addHeatableToolFromTFCMetal(Bronze, 2, 950, 2.857, true);
        addHeatableToolFromTFCMetal(BlackBronze, 2, 1070, 2.857, true);
        addHeatableToolFromTFCMetal(WroughtIron, 3, 1535, 2.857, true);
        addHeatableToolFromTFCMetal(Steel, 4, 1540, 2.857, true);
        addHeatableToolFromTFCMetal(BlackSteel, 5, 1485, 2.857, true);
        addHeatableToolFromTFCMetal(BlueSteel, 6, 1540, 2.857, true);
        addHeatableToolFromTFCMetal(RedSteel, 6, 1540, 2.857, true);
        addHeatableToolFromTFCMetal(Bismuth, 1, 270, 7.143, false);
        addHeatableToolFromTFCMetal(Brass, 2, 930, 2.857, false);
        addHeatableToolFromTFCMetal(Copper, 1, 1080, 2.857, false);
        addHeatableToolFromTFCMetal(Gold, 1, 1060, 1.667, false);
        addHeatableToolFromTFCMetal(Nickel, 1, 1453, 2.083, false);
        addHeatableToolFromTFCMetal(Silver, 1, 961, 2.083, false);
        addHeatableToolFromTFCMetal(Tin, 1, 230, 7.143, false);
        addHeatableToolFromTFCMetal(Zinc, 1, 420, 4.762, false);
    }

    private static void addMetalFromMaterial(Material material, int tier){
        addMetalFromMaterial(material, tier, 0.00857, 2.857);
    }

    private static void addHeatableToolFromTFCMetal(Material material, int tier, int meltTemp, double heatCapacityForHeating, boolean fileOnly){
        METALS.put(material, new Tuple<>(true, tier));
        MaterialTypeItem<?>[] types = new MaterialTypeItem[]{RAW_ORE, BEARING_ROCK, PLATE, CHUNK, LONG_ROD, RING, DUST};
        Arrays.stream(types).forEach(t -> {
            if (!material.has(t)) return;
            JsonObject j = new JsonObject();
            JsonObject ingrediient = new JsonObject();
            ingrediient.addProperty("tag", t.getMaterialTag(material).location().toString());
            j.add("ingredient", ingrediient);
            double ratio = t.getUnitValue() == -1 ? 1 : (double) t.getUnitValue() / U;
            j.addProperty("heat_capacity", heatCapacityForHeating * ratio);
            j.addProperty("forging_temperature", meltTemp * 0.6);
            j.addProperty("welding_temperature", meltTemp * 0.8);
            GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + t.getId() + ".json"), j.toString().getBytes());
        });
        if (material.has(TOOLS)){
            ToolData td = TOOLS.get(material);
            GTToolType[] toolTypes = new GTToolType[]{SWORD, PICKAXE, SHOVEL, AXE, HOE, HAMMER, SAW, FILE, SCREWDRIVER, KNIFE, SCYTHE, PROPICK, JAVELIN};
            for(GTToolType toolType : toolTypes){
                if (fileOnly && toolType != FILE && toolType != SCREWDRIVER) continue;
                if (td.toolTypes().contains(toolType) && toolType.getMaterialTypeItem() != null) {
                    var toolHead = toolType.getMaterialTypeItem();
                    JsonObject j = new JsonObject();
                    double ratio = (double) toolHead.getUnitValue() / U;
                    j.addProperty("heat_capacity", heatCapacityForHeating * ratio);
                    j.addProperty("forging_temperature", meltTemp * 0.6);
                    j.addProperty("welding_temperature", meltTemp * 0.8);
                    JsonObject ingrediient = new JsonObject();
                    ingrediient.addProperty("tag", toolHead.getMaterialTag(material).location().toString());
                    j.add("ingredient", ingrediient);
                    GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + toolHead.getId() + ".json"), j.toString().getBytes());
                    ingrediient = new JsonObject();
                    ingrediient.addProperty("item", toolType.getDomain() + ":" + material.getId() + "_" + toolType.getId());
                    j.add("ingredient", ingrediient);
                    GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + toolType.getId() + ".json"), j.toString().getBytes());
                }
            }
        }
    }

    private static void addMetalFromMaterial(Material material, int tier, double specificHeatCapacity, double heatCapacityForHeating){
        METALS.put(material, new Tuple<>(false, tier));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tier", tier);
        jsonObject.addProperty("fluid", Ref.SHARED_ID + ":" + material.getId());
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
        MaterialTypeItem<?>[] types = new MaterialTypeItem[]{RAW_ORE, BEARING_ROCK, INGOT, DOUBLE_INGOT, SHEET, PLATE, ROD, LONG_ROD, RING, CHUNK, DUST};
        Arrays.stream(types).forEach(t -> {
            MaterialTypeItem<?> compare = t;
            if (t == DOUBLE_INGOT || t == SHEET) compare = INGOT;
            if (!material.has(compare)) return;
            JsonObject j = new JsonObject();
            JsonObject ingrediient = new JsonObject();
            ingrediient.addProperty("tag", t.getMaterialTag(material).location().toString());
            j.add("ingredient", ingrediient);
            double ratio = t.getUnitValue() == -1 ? 1 : (double) t.getUnitValue() / U;
            j.addProperty("heat_capacity", heatCapacityForHeating * ratio);
            j.addProperty("forging_temperature", meltTemp * 0.6);
            j.addProperty("welding_temperature", meltTemp * 0.8);
            GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + t.getId() + ".json"), j.toString().getBytes());
        });
        if (material.has(TOOLS)){
            ToolData td = TOOLS.get(material);
            GTToolType[] toolTypes = new GTToolType[]{SWORD, PICKAXE, SHOVEL, AXE, HOE, HAMMER, SAW, FILE, SCREWDRIVER, KNIFE, SCYTHE, PROPICK, JAVELIN};
            for(GTToolType toolType : toolTypes){
                if (td.toolTypes().contains(toolType) && toolType.getMaterialTypeItem() != null) {
                    var toolHead = toolType.getMaterialTypeItem();
                    JsonObject j = new JsonObject();
                    double ratio = (double) toolHead.getUnitValue() / U;
                    j.addProperty("heat_capacity", heatCapacityForHeating * ratio);
                    j.addProperty("forging_temperature", meltTemp * 0.6);
                    j.addProperty("welding_temperature", meltTemp * 0.8);
                    JsonObject ingrediient = new JsonObject();
                    ingrediient.addProperty("tag", toolHead.getMaterialTag(material).location().toString());
                    j.add("ingredient", ingrediient);
                    GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + toolHead.getId() + ".json"), j.toString().getBytes());
                    ingrediient = new JsonObject();
                    ingrediient.addProperty("item", toolType.getDomain() + ":" + material.getId() + "_" + toolType.getId());
                    j.add("ingredient", ingrediient);
                    GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "tfc/item_heats/metal/" + material.getId() + "_" + toolType.getId() + ".json"), j.toString().getBytes());
                }
            }
        }
    }
}
