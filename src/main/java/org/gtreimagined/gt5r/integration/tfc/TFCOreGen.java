package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Pair;
import net.devtech.arrp.json.tags.JTag;
import net.dries007.tfc.world.feature.vein.Indicator;
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
        createClusterVein("surface_tetrahedrite", 7, 50, 0.5, 60, 210,
                ofM(new WeightedMaterial(Tetrahedrite, 60), new WeightedMaterial(Copper, 20), new WeightedMaterial(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 8, List.of(Pair.of("tfc:ore/small_tetrahedrite", 80), Pair.of("tfc:ore/small_copper", 20))));

        createClusterVein("normal_bauxite", 185, 40, 0.3, -16, 44,
                ofM(new WeightedMaterial(Bauxite, 70), new WeightedMaterial(Alumina, 20), new WeightedMaterial(Ilmenite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(20, 15, ofM(new WeightedMaterial(Bauxite, 70), new WeightedMaterial(Alumina, 20), new WeightedMaterial(Ilmenite, 10))));
        GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation("tfc", "worldgen/placed_feature/in_biome/veins"), JTag.tag().add(new ResourceLocation(GT5Reimagined.ID, "vein/surface_tetrahedrite")));
    }


    public static void createClusterVein(String id, int rarity, int size, double density, int minY, int maxY, WeightedMaterial[] materials, String[] stones, Indicator indicator) {
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

    private static @NotNull JsonObject getBlock(WeightedMaterial[] materials, String stone) {
        JsonObject block = new JsonObject();
        JsonArray replace = new JsonArray();
        replace.add("tfc:rock/raw/" + stone);
        block.add("replace", replace);
        JsonArray with = new JsonArray();
        for (WeightedMaterial material : materials) {
            JsonObject materialBlock = new JsonObject();
            materialBlock.addProperty("block", Ref.SHARED_ID + ":" + "ore_" + material.material.getId() + "_raw_" + stone);
            materialBlock.addProperty("weight", material.weight);
            with.add(materialBlock);
        }
        block.add("with", with);
        return block;
    }

    private static WeightedMaterial[] ofM(WeightedMaterial... materials){
        return materials;
    }

    public record WeightedMaterial(Material material, int weight) {}

    public record Indicator(int depth, int rarity, List<Pair<String, Integer>> blocks) {
        public Indicator(int depth, int rarity, WeightedMaterial... blocks) {
            this(depth, rarity, Stream.of(blocks).map(w -> Pair.of("antimatter_shared:surface_rock_" + w.material.getId() + "_stone", w.weight)).toList());
        }
    }


}
