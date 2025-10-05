package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Pair;
import net.devtech.arrp.json.tags.JTag;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Ore.Grade;
import net.dries007.tfc.world.feature.vein.Indicator;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static net.dries007.tfc.common.blocks.rock.Ore.*;
import static net.dries007.tfc.common.blocks.rock.Ore.Grade.*;
import static net.dries007.tfc.common.blocks.rock.Ore.LIMONITE;
import static net.dries007.tfc.common.blocks.rock.Ore.MAGNETITE;
import static net.dries007.tfc.common.blocks.rock.Ore.NATIVE_COPPER;
import static net.dries007.tfc.common.blocks.rock.Ore.PYRITE;
import static net.dries007.tfc.common.blocks.rock.Ore.SPHALERITE;
import static net.dries007.tfc.common.blocks.rock.Ore.TETRAHEDRITE;
import static org.gtreimagined.gt5r.data.Materials.*;

public class TFCOreGen {

    public static List<String> veins = new ArrayList<>();

    public static void init() {
        initTFCReplacements();
        initAdditions();
        JTag tag = JTag.tag().replace();
        for (String vein : veins){
            tag.add(new ResourceLocation(GT5Reimagined.ID, "vein/" + vein));
        }
        String[] tfcVeins = new String[]{"gravel", "granite_dike", "diorite_dike", "gabbro_dike",
                "normal_native_copper", "surface_native_copper", "normal_native_gold", "deep_native_gold",
                "normal_native_silver", "poor_native_silver", "normal_cassiterite", "surface_cassiterite",
                "normal_bismuthinite", "surface_bismuthinite", "poor_garnierite", "poor_malachite",
                "bituminous_coal", "lignite", "kaolinite", "graphite", "cryolite", "saltpeter",
                "sulfur", "sylvite", "borax", "gypsum", "halite", "diamond", "emerald",
                "volcanic_sulfur", "amethyst", "opal"};
        for (String vein : tfcVeins){
            tag.add(new ResourceLocation(Ref.MOD_TFC, "vein/" + vein));
        }
        tag.add(new ResourceLocation("tfc:geode"));
        GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation("tfc", "worldgen/placed_feature/in_biome/veins"), tag);

    }


    public static void initTFCReplacements() {
        //surface
        createClusterVein("surface_tetrahedrite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(TETRAHEDRITE, POOR, 36), new WeightedBlock(TETRAHEDRITE, NORMAL, 18), new WeightedBlock(TETRAHEDRITE, RICH, 6),
                        new WeightedBlock(NATIVE_COPPER, POOR, 12), new WeightedBlock(NATIVE_COPPER, NORMAL, 6),
                        new WeightedBlock(NATIVE_COPPER, RICH, 2), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(TETRAHEDRITE, 80), new WeightedBlock(NATIVE_COPPER, 20)));
        createClusterVein("surface_sphalerite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(SPHALERITE, POOR, 36), new WeightedBlock(SPHALERITE, NORMAL, 18), new WeightedBlock(SPHALERITE, RICH, 6),
                        new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)));

        //deep veins
        createClusterVein("deep_magnetite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(MAGNETITE, POOR, 7), new WeightedBlock(MAGNETITE, NORMAL, 21), new WeightedBlock(MAGNETITE, RICH, 42),
                        new WeightedBlock(HEMATITE, POOR, 2), new WeightedBlock(HEMATITE, NORMAL, 6), new WeightedBlock(HEMATITE, RICH, 12),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(35, 12, new WeightedBlock(MAGNETITE, 70), new WeightedBlock(HEMATITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("deep_hematite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(HEMATITE, POOR, 7), new WeightedBlock(HEMATITE, NORMAL, 21), new WeightedBlock(HEMATITE, RICH, 42),
                        new WeightedBlock(MAGNETITE, POOR, 2), new WeightedBlock(MAGNETITE, NORMAL, 6), new WeightedBlock(MAGNETITE, RICH, 12),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(35, 12, new WeightedBlock(HEMATITE, 70), new WeightedBlock(MAGNETITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("deep_limonite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(LIMONITE, POOR, 4), new WeightedBlock(LIMONITE, NORMAL, 12), new WeightedBlock(LIMONITE, RICH, 24),
                        new WeightedBlock(BrownLimonite, 40),
                        new WeightedBlock(HEMATITE, NORMAL, 4), new WeightedBlock(HEMATITE, RICH, 6),
                        new WeightedBlock(MALACHITE, NORMAL, 4), new WeightedBlock(MALACHITE, RICH, 6)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(35, 12, new WeightedBlock(LIMONITE, 40), new WeightedBlock(BrownLimonite, 40), new WeightedBlock(HEMATITE, 10), new WeightedBlock(MALACHITE, 10)));


        //normal veins
        createClusterVein("normal_tetrahedrite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(TETRAHEDRITE, POOR, 12), new WeightedBlock(TETRAHEDRITE, NORMAL, 30), new WeightedBlock(TETRAHEDRITE, RICH, 18),
                        new WeightedBlock(NATIVE_COPPER, POOR, 4), new WeightedBlock(NATIVE_COPPER, NORMAL, 10),
                        new WeightedBlock(NATIVE_COPPER, RICH, 6), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(TETRAHEDRITE, 80), new WeightedBlock(NATIVE_COPPER, 20)));
        createClusterVein("normal_sphalerite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(SPHALERITE, POOR, 12), new WeightedBlock(SPHALERITE, NORMAL, 30), new WeightedBlock(SPHALERITE, RICH, 18),
                        new WeightedBlock(PYRITE, 20), new WeightedBlock(Chalcopyrite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(35, 12, new WeightedBlock(SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)));
        createClusterVein("normal_malachite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(MALACHITE, POOR, 16), new WeightedBlock(MALACHITE, NORMAL, 40), new WeightedBlock(MALACHITE, RICH, 24),
                        new WeightedBlock(LIMONITE, POOR, 4), new WeightedBlock(LIMONITE, NORMAL, 10), new WeightedBlock(LIMONITE, RICH, 6)),
                new String[]{"limestone", "marble"},
                new Indicator(35, 12, new WeightedBlock(MALACHITE, 80), new WeightedBlock(LIMONITE, 20)));
        createClusterVein("normal_magnetite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(MAGNETITE, POOR, 14), new WeightedBlock(MAGNETITE, NORMAL, 35), new WeightedBlock(MAGNETITE, RICH, 21),
                        new WeightedBlock(HEMATITE, POOR, 4), new WeightedBlock(HEMATITE, NORMAL, 10), new WeightedBlock(HEMATITE, RICH, 6),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(35, 12, new WeightedBlock(MAGNETITE, 70), new WeightedBlock(HEMATITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("normal_hematite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(HEMATITE, POOR, 14), new WeightedBlock(HEMATITE, NORMAL, 35), new WeightedBlock(HEMATITE, RICH, 21),
                        new WeightedBlock(MAGNETITE, POOR, 4), new WeightedBlock(MAGNETITE, NORMAL, 10), new WeightedBlock(MAGNETITE, RICH, 6),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(35, 12, new WeightedBlock(HEMATITE, 70), new WeightedBlock(MAGNETITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("normal_limonite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(LIMONITE, POOR, 8), new WeightedBlock(LIMONITE, NORMAL, 20), new WeightedBlock(LIMONITE, RICH, 12),
                        new WeightedBlock(BrownLimonite, 40),
                        new WeightedBlock(HEMATITE, POOR, 2), new WeightedBlock(MAGNETITE, NORMAL, 5), new WeightedBlock(MAGNETITE, RICH, 3),
                        new WeightedBlock(MALACHITE, POOR, 2), new WeightedBlock(MALACHITE, NORMAL, 5), new WeightedBlock(MALACHITE, POOR, 3)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(35, 12, new WeightedBlock(LIMONITE, 40), new WeightedBlock(BrownLimonite, 40), new WeightedBlock(HEMATITE, 10), new WeightedBlock(MALACHITE, 10)));
        createClusterVein("normal_garnierite", 70, 20, 0.6, -32, 60,
                ofM(new WeightedBlock(GARNIERITE, POOR, 14), new WeightedBlock(GARNIERITE, NORMAL, 35), new WeightedBlock(GARNIERITE, RICH, 21),
                        new WeightedBlock(Cobaltite, 20), new WeightedBlock(Pentlandite, 10)),
                new String[]{"gabbro"},
                new Indicator(35, 12, new WeightedBlock(GARNIERITE, 70), new WeightedBlock(Cobaltite, 20), new WeightedBlock(Pentlandite, 10)));
        //other veins
        createClusterVein("cinnabar", 120, 20, 0.6, -48, 100,
                ofM(new WeightedBlock(Redstone, 60), new WeightedBlock(CINNABAR, 30), new WeightedBlock(Ruby, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "quartzite", "shale"},
                new Indicator(35, 12));
        createClusterVein("lapis_lazuli", 120, 20, 0.6, -48, 100,
                ofM(new WeightedBlock(Lazurite, 35), new WeightedBlock(Sodalite, 35),
                        new WeightedBlock(Lapis, 20), new WeightedBlock(Alumina, 10)),
                new String[]{"limestone", "marble"},
                new Indicator(35, 12));
    }

    public static void initAdditions(){
        createClusterVein("normal_bauxite", 185, 40, 0.3, -16, 48,
                ofM(new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(20, 15, ofM(new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10))));
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
