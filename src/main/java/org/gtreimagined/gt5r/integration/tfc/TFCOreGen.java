package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Pair;
import net.devtech.arrp.json.tags.JTag;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Ore.Grade;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.gtreimagined.gt5r.data.Materials.*;

public class TFCOreGen {

    public static List<String> veins = new ArrayList<>();

    public static void init(){
        //surface
        createClusterVein("surface_tetrahedrite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(Ore.TETRAHEDRITE, Grade.POOR, 36), new WeightedBlock(Ore.TETRAHEDRITE, Grade.NORMAL, 18), new WeightedBlock(Ore.TETRAHEDRITE, Grade.RICH, 6),
                        new WeightedBlock(Ore.NATIVE_COPPER, Grade.POOR, 12), new WeightedBlock(Ore.NATIVE_COPPER, Grade.NORMAL, 6),
                        new WeightedBlock(Ore.NATIVE_COPPER, Grade.RICH, 2), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(Ore.TETRAHEDRITE, 80), new WeightedBlock(Ore.NATIVE_COPPER, 20)));
        createClusterVein("surface_sphalerite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(Ore.SPHALERITE, Grade.POOR, 36), new WeightedBlock(Ore.SPHALERITE, Grade.NORMAL, 18), new WeightedBlock(Ore.SPHALERITE, Grade.RICH, 6),
                        new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(Ore.PYRITE, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(Ore.SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(Ore.PYRITE, 20)));

        createClusterVein("normal_tetrahedrite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(Ore.TETRAHEDRITE, Grade.POOR, 12), new WeightedBlock(Ore.TETRAHEDRITE, Grade.NORMAL, 30), new WeightedBlock(Ore.TETRAHEDRITE, Grade.RICH, 18),
                        new WeightedBlock(Ore.NATIVE_COPPER, Grade.POOR, 4), new WeightedBlock(Ore.NATIVE_COPPER, Grade.NORMAL, 10),
                        new WeightedBlock(Ore.NATIVE_COPPER, Grade.RICH, 6), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(Ore.TETRAHEDRITE, 80), new WeightedBlock(Ore.NATIVE_COPPER, 20)));
        createClusterVein("normal_sphalerite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(Ore.SPHALERITE, Grade.POOR, 12), new WeightedBlock(Ore.SPHALERITE, Grade.NORMAL, 30), new WeightedBlock(Ore.SPHALERITE, Grade.RICH, 18),
                        new WeightedBlock(Ore.PYRITE, 20), new WeightedBlock(Chalcopyrite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(Ore.SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(Ore.PYRITE, 20)));
        createClusterVein("normal_malachite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(Ore.MALACHITE, Grade.POOR, 16), new WeightedBlock(Ore.MALACHITE, Grade.NORMAL, 40), new WeightedBlock(Ore.MALACHITE, Grade.RICH, 24),
                        new WeightedBlock(Ore.LIMONITE, Grade.POOR, 4), new WeightedBlock(Ore.LIMONITE, Grade.NORMAL, 10), new WeightedBlock(Ore.LIMONITE, Grade.RICH, 6)),
                new String[]{"limestone", "marble"},
                new Indicator(35, 12, new WeightedBlock(Ore.MALACHITE, 80), new WeightedBlock(Ore.LIMONITE, 20)));

        createClusterVein("normal_magnetite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(Ore.MAGNETITE, Grade.POOR, 14), new WeightedBlock(Ore.MAGNETITE, Grade.NORMAL, 35), new WeightedBlock(Ore.MAGNETITE, Grade.RICH, 21),
                        new WeightedBlock(Ore.HEMATITE, Grade.POOR, 4), new WeightedBlock(Ore.HEMATITE, Grade.NORMAL, 10), new WeightedBlock(Ore.HEMATITE, Grade.RICH, 6),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(35, 12, new WeightedBlock(Ore.MAGNETITE, 70), new WeightedBlock(Ore.HEMATITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));

        createClusterVein("normal_bauxite", 185, 40, 0.3, -16, 48,
                ofM(new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(20, 15, ofM(new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10))));
        JTag tag = JTag.tag().replace();
        for (String vein : veins){
            tag.add(new ResourceLocation(GT5Reimagined.ID, "vein/" + vein));
        }
        GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation("tfc", "worldgen/placed_feature/in_biome/veins"), tag);
    }


    public static void createClusterVein(String id, int rarity, int size, double density, int minY, int maxY, WeightedBlock[] materials, String[] stones, Indicator indicator) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "tfc:cluster_vein");
        JsonObject config = new JsonObject();
        root.add("config", config);
        config.addProperty("rarity", rarity);
        config.addProperty("size", size);
        config.addProperty("density", density);
        JsonObject yObj = new JsonObject();
        yObj.addProperty("absolute", minY);
        config.add("min_y", yObj);
        yObj = new JsonObject();
        yObj.addProperty("absolute", maxY);
        config.add("max_y", yObj);
        config.addProperty("random_name", id);
        JsonArray blocks = new JsonArray();
        for (String stone : stones) {
            JsonObject block = getBlock(materials, stone);
            blocks.add(block);
        }
        config.add("blocks", blocks);
        JsonObject jIndicator = new JsonObject();
        jIndicator.addProperty("rarity", indicator.rarity);
        jIndicator.addProperty("depth", indicator.depth);
        blocks = new JsonArray();
        for (var block : indicator.blocks){
            JsonObject b = new JsonObject();
            b.addProperty("block", block.key());
            b.addProperty("weight", block.value());
            blocks.add(b);
        }
        jIndicator.add("blocks", blocks);
        config.add("indicator", jIndicator);
        GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "worldgen/configured_feature/vein/" + id + ".json"), root.toString().getBytes());
        JsonObject placed = new JsonObject();
        placed.addProperty("feature", GT5Reimagined.ID + ":vein/" + id);
        JsonArray placement = new JsonArray();
        placed.add("placement", placement);
        GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "worldgen/placed_feature/vein/" + id + ".json"), placed.toString().getBytes());
        veins.add(id);
    }

    private static @NotNull JsonObject getBlock(WeightedBlock[] materials, String stone) {
        JsonObject block = new JsonObject();
        JsonArray replace = new JsonArray();
        replace.add("tfc:rock/raw/" + stone);
        block.add("replace", replace);
        JsonArray with = new JsonArray();
        for (WeightedBlock material : materials) {
            JsonObject materialBlock = new JsonObject();
            String domain = material.material == null ? "tfc" : Ref.SHARED_ID;
            String id = material.material == null ? "ore/" + (material.grade == null ? "" : material.grade.name().toLowerCase() + "_") + material.ore.name().toLowerCase() + "/" : "ore_" + material.material.getId() + "_raw_";
            materialBlock.addProperty("block", domain + ":" + id + stone);
            materialBlock.addProperty("weight", material.weight);
            with.add(materialBlock);
        }
        block.add("with", with);
        return block;
    }

    private static WeightedBlock[] ofM(WeightedBlock... materials){
        return materials;
    }

    public record WeightedBlock(Material material, Ore ore, Grade grade, int weight) {
        public WeightedBlock(Material material, int weight) {
            this(material, null, null, weight);
        }

        public WeightedBlock(Ore ore, Grade grade, int weight){
            this(null, ore, grade, weight);
        }

        public WeightedBlock(Ore ore, int weight) {
            this(null, ore, null, weight);
        }
    }

    public record Indicator(int depth, int rarity, List<Pair<String, Integer>> blocks) {
        public Indicator(int depth, int rarity, WeightedBlock... blocks) {
            this(depth, rarity, Stream.of(blocks).map(w -> {
                String domain = w.material == null ? "tfc" : Ref.SHARED_ID;
                String id = w.material == null ? "ore/small_" + w.ore.name().toLowerCase() : "surface_rock_" + w.material.getId() + "_stone";
                return Pair.of(domain + ":" + id, w.weight);
            }).toList());
        }
    }


}
