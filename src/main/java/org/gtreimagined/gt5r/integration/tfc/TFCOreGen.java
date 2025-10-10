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
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gtlib.GTAPI;
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
    public static List<ResourceLocation> veinsToRemove = new ArrayList<>();

    public static void init() {
        initTFCReplacements();
        initAdditions();
        JTag tag = JTag.tag();
        for (String vein : veins){
            tag.add(new ResourceLocation(GT5Reimagined.ID, "vein/" + vein));
        }
        String[] tfcVeinsToRemove = new String[]{
                "normal_hematite", "deep_hematite", "normal_garnierite", "normal_malachite",
                "normal_magnetite", "deep_magnetite", "normal_limonite", "deep_limonite",
                "normal_sphalerite", "surface_sphalerite", "normal_tetrahedrite", "surface_tetrahedrite",
                "cinnabar", "lapis_lazuli"
        };
        for (String vein : tfcVeinsToRemove){
            veinsToRemove.add(new ResourceLocation(Ref.MOD_TFC, "vein/" + vein));
        }
        if (GTAPI.isModLoaded("firmalife")){
            veinsToRemove.add(new ResourceLocation("firmalife", "vein/normal_chromite"));
            veinsToRemove.add(new ResourceLocation("firmalife", "vein/deep_chromite"));
        }
        GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation("tfc", "worldgen/placed_feature/in_biome/veins"), tag);

    }


    public static void initTFCReplacements() {
        //surface
        createClusterVein("surface_tetrahedrite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(TETRAHEDRITE, POOR, 36), new WeightedBlock(TETRAHEDRITE, NORMAL, 18), new WeightedBlock(TETRAHEDRITE, RICH, 6),
                        new WeightedBlock(NATIVE_COPPER, POOR, 12), new WeightedBlock(NATIVE_COPPER, NORMAL, 6),
                        new WeightedBlock(NATIVE_COPPER, RICH, 2), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, new WeightedBlock(TETRAHEDRITE, 80), new WeightedBlock(NATIVE_COPPER, 20)));
        createClusterVein("surface_sphalerite", 20, 20, 0.5, 60, 210,
                ofM(new WeightedBlock(SPHALERITE, POOR, 36), new WeightedBlock(SPHALERITE, NORMAL, 18), new WeightedBlock(SPHALERITE, RICH, 6),
                        new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, new WeightedBlock(SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)));

        //deep veins
        createClusterVein("deep_magnetite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(MAGNETITE, POOR, 7), new WeightedBlock(MAGNETITE, NORMAL, 21), new WeightedBlock(MAGNETITE, RICH, 42),
                        new WeightedBlock(HEMATITE, POOR, 2), new WeightedBlock(HEMATITE, NORMAL, 6), new WeightedBlock(HEMATITE, RICH, 12),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(MAGNETITE, 70), new WeightedBlock(HEMATITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("deep_hematite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(HEMATITE, POOR, 7), new WeightedBlock(HEMATITE, NORMAL, 21), new WeightedBlock(HEMATITE, RICH, 42),
                        new WeightedBlock(MAGNETITE, POOR, 2), new WeightedBlock(MAGNETITE, NORMAL, 6), new WeightedBlock(MAGNETITE, RICH, 12),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(12, new WeightedBlock(HEMATITE, 70), new WeightedBlock(MAGNETITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("deep_limonite", 100, 35, 0.7, -64, 30,
                ofM(new WeightedBlock(LIMONITE, POOR, 4), new WeightedBlock(LIMONITE, NORMAL, 12), new WeightedBlock(LIMONITE, RICH, 24),
                        new WeightedBlock(BrownLimonite, 40),
                        new WeightedBlock(HEMATITE, NORMAL, 4), new WeightedBlock(HEMATITE, RICH, 6),
                        new WeightedBlock(MALACHITE, NORMAL, 4), new WeightedBlock(MALACHITE, RICH, 6)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(LIMONITE, 40), new WeightedBlock(BrownLimonite, 40), new WeightedBlock(HEMATITE, 10), new WeightedBlock(MALACHITE, 10)));


        //normal veins
        createClusterVein("normal_tetrahedrite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(TETRAHEDRITE, POOR, 12), new WeightedBlock(TETRAHEDRITE, NORMAL, 30), new WeightedBlock(TETRAHEDRITE, RICH, 18),
                        new WeightedBlock(NATIVE_COPPER, POOR, 4), new WeightedBlock(NATIVE_COPPER, NORMAL, 10),
                        new WeightedBlock(NATIVE_COPPER, RICH, 6), new WeightedBlock(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, new WeightedBlock(TETRAHEDRITE, 80), new WeightedBlock(NATIVE_COPPER, 20)));
        createClusterVein("normal_sphalerite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(SPHALERITE, POOR, 12), new WeightedBlock(SPHALERITE, NORMAL, 30), new WeightedBlock(SPHALERITE, RICH, 18),
                        new WeightedBlock(PYRITE, 20), new WeightedBlock(Chalcopyrite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, new WeightedBlock(SPHALERITE, 60), new WeightedBlock(Chalcopyrite, 20), new WeightedBlock(PYRITE, 20)));
        createClusterVein("normal_malachite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(MALACHITE, POOR, 16), new WeightedBlock(MALACHITE, NORMAL, 40), new WeightedBlock(MALACHITE, RICH, 24),
                        new WeightedBlock(LIMONITE, POOR, 4), new WeightedBlock(LIMONITE, NORMAL, 10), new WeightedBlock(LIMONITE, RICH, 6),
                        new WeightedBlock(GYPSUM, "limestone", 11)),
                new String[]{"marble", "limestone"},
                new Indicator(12, new WeightedBlock(MALACHITE, 80), new WeightedBlock(LIMONITE, 20)));
        createClusterVein("normal_magnetite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(MAGNETITE, POOR, 14), new WeightedBlock(MAGNETITE, NORMAL, 35), new WeightedBlock(MAGNETITE, RICH, 21),
                        new WeightedBlock(HEMATITE, POOR, 4), new WeightedBlock(HEMATITE, NORMAL, 10), new WeightedBlock(HEMATITE, RICH, 6),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(MAGNETITE, 70), new WeightedBlock(HEMATITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("normal_hematite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(HEMATITE, POOR, 14), new WeightedBlock(HEMATITE, NORMAL, 35), new WeightedBlock(HEMATITE, RICH, 21),
                        new WeightedBlock(MAGNETITE, POOR, 4), new WeightedBlock(MAGNETITE, NORMAL, 10), new WeightedBlock(MAGNETITE, RICH, 6),
                        new WeightedBlock(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(12, new WeightedBlock(HEMATITE, 70), new WeightedBlock(MAGNETITE, 20), new WeightedBlock(VanadiumMagnetite, 10)));
        createClusterVein("normal_limonite", 60, 25, 0.6, -32, 75,
                ofM(new WeightedBlock(LIMONITE, POOR, 8), new WeightedBlock(LIMONITE, NORMAL, 20), new WeightedBlock(LIMONITE, RICH, 12),
                        new WeightedBlock(BrownLimonite, 40),
                        new WeightedBlock(HEMATITE, POOR, 2), new WeightedBlock(MAGNETITE, NORMAL, 5), new WeightedBlock(MAGNETITE, RICH, 3),
                        new WeightedBlock(MALACHITE, POOR, 2), new WeightedBlock(MALACHITE, NORMAL, 5), new WeightedBlock(MALACHITE, POOR, 3)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(LIMONITE, 40), new WeightedBlock(BrownLimonite, 40), new WeightedBlock(HEMATITE, 10), new WeightedBlock(MALACHITE, 10)));
        createClusterVein("normal_garnierite", 70, 20, 0.6, -32, 60,
                ofM(new WeightedBlock(GARNIERITE, POOR, 14), new WeightedBlock(GARNIERITE, NORMAL, 35), new WeightedBlock(GARNIERITE, RICH, 21),
                        new WeightedBlock(Cobaltite, 20), new WeightedBlock(Pentlandite, 10)),
                new String[]{"gabbro"},
                new Indicator(12, new WeightedBlock(GARNIERITE, 70), new WeightedBlock(Cobaltite, 20), new WeightedBlock(Pentlandite, 10)));
        //other veins
        createClusterVein("cinnabar", 120, 20, 0.6, -48, 100,
                ofM(new WeightedBlock(Redstone, 60), new WeightedBlock(Cinnabar, 30), new WeightedBlock(Ruby, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "quartzite", "shale"},
                null);
        createClusterVein("lapis_lazuli", 120, 20, 0.6, -48, 100,
                ofM(new WeightedBlock(Lazurite, 35), new WeightedBlock(Sodalite, 35),
                        new WeightedBlock(Lapis, 30)),
                new String[]{"limestone", "marble"},
                null);
    }

    public static void initAdditions(){
        createClusterVein("chalcopyrite", 90, 30, 0.6, -32, 60,
                ofM(new WeightedBlock(Chalcopyrite, 30), new WeightedBlock(PYRITE, 30),
                        new WeightedBlock(HEMATITE, POOR, 6), new WeightedBlock(HEMATITE, NORMAL, 15), new WeightedBlock(HEMATITE, RICH, 9),
                        new WeightedBlock(NATIVE_COPPER, NORMAL, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, new WeightedBlock(Chalcopyrite, 30), new WeightedBlock(HEMATITE, 30), new WeightedBlock(PYRITE, 30), new WeightedBlock(NATIVE_COPPER, 10)));
        createClusterVein("salts", 100, 15, 0.6, 0, 90,
                ofM(new WeightedBlock(SYLVITE, 35), new WeightedBlock(HALITE, 35), new WeightedBlock(Lepidolite, 20), new WeightedBlock(Spodumene, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, new WeightedBlock(Sylvite, 35), new WeightedBlock(Salt, 35), new WeightedBlock(Lepidolite, 20), new WeightedBlock(Spodumene, 10)));
        createClusterVein("pitchblende", 220, 24, 0.45, -64, -20,
                ofM(new WeightedBlock(Pitchblende, 60), new WeightedBlock(Uraninite, 40)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createClusterVein("soapstone", 120, 35, 0.6, -32, 48,
                ofM(new WeightedBlock(Soapstone, 35), new WeightedBlock(Talc, 35), new WeightedBlock(Glauconite, 20), new WeightedBlock(Pentlandite, 10)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(40, 12, new WeightedBlock(Soapstone, 35), new WeightedBlock(Talc, 35), new WeightedBlock(Glauconite, 20), new WeightedBlock(Pentlandite, 10)));
        createClusterVein("platinum", 300, 30, 0.3, -64, 0,
                ofM(new WeightedBlock(Sheldonite, 35), new WeightedBlock(Sperrylite, 35), new WeightedBlock(Platinum, 20), new WeightedBlock(Iridium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createClusterVein("chromite", 140, 25, 0.2, -32, 60,
                ofM(new WeightedBlock(Chromite, 1)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                null);
        createClusterVein("monazite", 185, 40, 0.25, -32, 50,
                ofM(new WeightedBlock(Bastnasite, 70), new WeightedBlock(Monazite, 20), new WeightedBlock(Neodymium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                new Indicator(30, 12, new WeightedBlock(Bastnasite, 70), new WeightedBlock(Monazite, 20), new WeightedBlock(Neodymium, 10)));
        createClusterVein("molybdenum", 245, 26, 0.45, -64, 10,
                ofM(new WeightedBlock(Wulfenite, 35), new WeightedBlock(Molybdenite, 35),
                        new WeightedBlock(Molybdenum, 20), new WeightedBlock(Powellite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "granite", "diorite", "gabbro"},
                null);
        createClusterVein("scheelite", 220, 28, 0.35, -64, 0,
                ofM(new WeightedBlock(Scheelite, 65), new WeightedBlock(Tungstate, 25), new WeightedBlock(Lithium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createDiscVein("sapphire", 180, 28, 8, 0.35, -64, 26,
                ofM(new WeightedBlock(Almandine, 35), new WeightedBlock(Pyrope, 35), new WeightedBlock(Sapphire, 15), new WeightedBlock(GreenSapphire, 15)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                null);
        createClusterVein("manganese", 195, 40, 0.3, -32, 60,
                ofM(new WeightedBlock(Grossular, 35), new WeightedBlock(Spessartine, 35), new WeightedBlock(Pyrolusite, 20), new WeightedBlock(Tantalite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, new WeightedBlock(Grossular, 35), new WeightedBlock(Spessartine, 35), new WeightedBlock(Pyrolusite, 20), new WeightedBlock(Tantalite, 10)));
        WeightedBlock[] array = CertusQuartz.enabled ? ofM(new WeightedBlock(MilkyQuartz, 35), new WeightedBlock(CertusQuartz, 35), new WeightedBlock(Barite, 15), new WeightedBlock(Quartz, 15)) :
                ofM(new WeightedBlock(MilkyQuartz, 70), new  WeightedBlock(Barite, 15), new WeightedBlock(Quartz, 15));
        createClusterVein("quartz", 150, 40, 0.3, -32, 100,
                array,
                new String[]{"shale", "quartzite", "schist", "gneiss"},
                new Indicator(40, 15, array));
        createClusterVein("olivine", 180, 26, 0.25, -40, 30,
                ofM(new WeightedBlock(Bentonite, 35), new WeightedBlock(Magnesite, 35), new WeightedBlock(Olivine, 20), new WeightedBlock(Glauconite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "granite", "diorite", "gabbro"},
                new Indicator(12, new WeightedBlock(Bentonite, 35), new WeightedBlock(Magnesite, 35), new WeightedBlock(Olivine, 20), new WeightedBlock(Glauconite, 10)));
        createClusterVein("apatite", 110, 20, 0.5, 0, 180,
                ofM(new WeightedBlock(Apatite, 70), new WeightedBlock(TricalciumPhosphate, 20), new WeightedBlock(Phosphate, 10)),
                new String[]{"granite", "diorite", "gabbro", "quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(40, 12, new WeightedBlock(Apatite, 70), new WeightedBlock(TricalciumPhosphate, 20), new WeightedBlock(Phosphate, 10)));
        createClusterVein("galena", 160, 40, 0.4, -32, 75,
                ofM(new WeightedBlock(Galena, 60), new WeightedBlock(Lead, 10),
                        new WeightedBlock(NATIVE_SILVER, POOR, 6), new WeightedBlock(NATIVE_SILVER, NORMAL, 15), new WeightedBlock(NATIVE_SILVER, RICH, 9)),
                new String[]{"granite", "gneiss"},
                new Indicator(40, 12, new WeightedBlock(Galena, 60), new WeightedBlock(NATIVE_SILVER, 30), new WeightedBlock(Lead, 10)));
        createPipeVein("beryllium", 180, 10, 60, 6, 18, 0, 4, 0, 0.35, -32, 50,
                ofM(new WeightedBlock(Beryllium, 60), new WeightedBlock(EMERALD, 30), new WeightedBlock(Thorium, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                new Indicator(40, 15, new WeightedBlock(Beryllium, 60), new WeightedBlock(Emerald, 30), new WeightedBlock(Thorium, 10)));
        createClusterVein("bauxite", 185, 40, 0.3, -32, 60,
                ofM(new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(Bauxite, 70), new WeightedBlock(Alumina, 20), new WeightedBlock(Ilmenite, 10)));
        createClusterVein("oil_shale", 185, 55, 0.2, 0, 60,
                ofM(new WeightedBlock(OilShale, 1)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, new WeightedBlock(OilShale, 1)));
        if (!SpaceModRegistrar.INSTANCE.isEnabled()){
            createClusterVein("naquadah", 310, 32, 0.3, -64, -20,
                    ofM(new WeightedBlock(Naquadah, 1)),
                    new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                    null);
        }
    }


    public static void createClusterVein(String id, int rarity, int size, double density, int minY, int maxY, WeightedBlock[] materials, String[] stones, Indicator indicator) {
        createVein("cluster", id, rarity, size, density, minY, maxY, materials, stones, indicator, new JsonObject());
    }

    public static void createDiscVein(String id, int rarity, int radius, int height, double density, int minY, int maxY, WeightedBlock[] materials, String[] stones, Indicator indicator) {
        JsonObject config = new JsonObject();
        config.addProperty("height", height);
        createVein("disc", id, rarity, radius, density, minY, maxY, materials, stones, indicator, config);
    }

    public static void createPipeVein(String id, int rarity, int radius, int height, int minSkew, int maxSkew, int minSlant, int maxSlant, double sign, double density, int minY, int maxY, WeightedBlock[] materials, String[] stones, Indicator indicator) {
        JsonObject config = new JsonObject();
        config.addProperty("radius", radius);
        config.addProperty("height", height);
        config.addProperty("minSkew", minSkew);
        config.addProperty("maxSkew", maxSkew);
        config.addProperty("minSlant", minSlant);
        config.addProperty("maxSlant", maxSlant);
        config.addProperty("sign", sign);
        createVein("disc", id, rarity, radius, density, minY, maxY, materials, stones, indicator, config);
    }

    public static void createVein(String type, String id, int rarity, int radius, double density, int minY, int maxY, WeightedBlock[] materials, String[] stones, Indicator indicator, JsonObject config) {
        JsonObject root = new JsonObject();
        root.addProperty("type", "tfc:" + type + "_vein");
        root.add("config", config);
        addCommonValues(id, rarity, radius, density, minY, maxY, config);
        JsonArray blocks = new JsonArray();
        for (String stone : stones) {
            JsonObject block = getBlock(materials, stone);
            blocks.add(block);
        }
        config.add("blocks", blocks);
        if (indicator != null){
            JsonObject jIndicator = getIndicator(indicator);
            config.add("indicator", jIndicator);
        }
        GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "worldgen/configured_feature/vein/" + id + ".json"), root.toString().getBytes());
        createPlacedFeature(id);
    }

    private static void addCommonValues(String id, int rarity, int size, double density, int minY, int maxY, JsonObject config) {
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
    }

    private static void createPlacedFeature(String id){
        JsonObject placed = new JsonObject();
        placed.addProperty("feature", GT5Reimagined.ID + ":vein/" + id);
        JsonArray placement = new JsonArray();
        placed.add("placement", placement);
        GTLibDynamics.RUNTIME_DATA_PACK.addData(new ResourceLocation(GT5Reimagined.ID, "worldgen/placed_feature/vein/" + id + ".json"), placed.toString().getBytes());
        veins.add(id);
    }

    private static @NotNull JsonObject getIndicator(@NotNull Indicator indicator) {
        JsonObject object = new JsonObject();
        object.addProperty("rarity", indicator.rarity);
        object.addProperty("depth", indicator.depth);
        JsonArray blocks = new JsonArray();
        for (var block : indicator.blocks){
            JsonObject b = new JsonObject();
            b.addProperty("block", block.key());
            b.addProperty("weight", block.value());
            blocks.add(b);
        }
        object.add("blocks", blocks);
        return object;
    }

    private static @NotNull JsonObject getBlock(WeightedBlock[] materials, String stone) {
        JsonObject block = new JsonObject();
        JsonArray replace = new JsonArray();
        replace.add("tfc:rock/raw/" + stone);
        block.add("replace", replace);
        JsonArray with = new JsonArray();
        for (WeightedBlock material : materials) {
            if (material.filter != null && !material.filter.equals(stone)) continue;
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

    public record WeightedBlock(Material material, Ore ore, Grade grade, int weight, String filter) {
        public WeightedBlock(Material material, int weight) {
            this(material, null, null, weight, null);
        }

        public WeightedBlock(Ore ore, Grade grade, int weight){
            this(null, ore, grade, weight, null);
        }

        public WeightedBlock(Ore ore, int weight) {
            this(null, ore, null, weight, null);
        }
        public WeightedBlock(Ore ore, String stoneFilter, int weight) {
            this(null, ore, null, weight, stoneFilter);
        }
    }

    public record Indicator(int depth, int rarity, List<Pair<String, Integer>> blocks) {
        public Indicator(int rarity, WeightedBlock... blocks){
            this(35, rarity, blocks);
        }

        public Indicator(int depth, int rarity, WeightedBlock... blocks) {
            this(depth, rarity, Stream.of(blocks).map(w -> {
                String domain = w.material == null ? "tfc" : Ref.SHARED_ID;
                String id = w.material == null ? "ore/small_" + w.ore.name().toLowerCase() : "surface_rock_" + w.material.getId() + "_stone";
                return Pair.of(domain + ":" + id, w.weight);
            }).toList());
        }
    }


}
