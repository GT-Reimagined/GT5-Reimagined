package org.gtreimagined.gt5r.integration.tfc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.Pair;
import net.devtech.arrp.json.tags.JTag;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Ore.Grade;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.GTLibDynamics;
import org.gtreimagined.gtlib.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
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
        if (GT5RConfig.ENABLE_GT_TFC_VEINS.get()){
            initTFCReplacements();
            initAdditions();
            JTag tag = JTag.tag();
            for (String vein : veins){
                tag.add(new ResourceLocation(GT5Reimagined.ID, "vein/" + vein));
            }
            GTLibDynamics.RUNTIME_DATA_PACK.addTag(new ResourceLocation("tfc", "worldgen/placed_feature/in_biome/veins"), tag);
        }
        if (GT5RConfig.TFC_VEIN_REMOVALS.get()){
            String[] tfcVeinsToRemove = new String[]{
                    "normal_hematite", "deep_hematite", "normal_garnierite", "normal_malachite",
                    "normal_magnetite", "deep_magnetite", "normal_limonite", "deep_limonite",
                    "normal_sphalerite", "surface_sphalerite", "normal_tetrahedrite", "surface_tetrahedrite", "normal_native_copper",
                    "surface_native_copper", "normal_native_silver", "cinnabar", "lapis_lazuli"
            };
            for (String vein : tfcVeinsToRemove){
                veinsToRemove.add(new ResourceLocation(Ref.MOD_TFC, "vein/" + vein));
            }
            if (GTAPI.isModLoaded("firmalife")){
                veinsToRemove.add(new ResourceLocation("firmalife", "vein/normal_chromite"));
                veinsToRemove.add(new ResourceLocation("firmalife", "vein/deep_chromite"));
            }
        }
    }


    public static void initTFCReplacements() {
        //surface
        createClusterVein("surface_tetrahedrite", 20, 20, 0.5, 60, 210,
                ofM(ofOre(TETRAHEDRITE, POOR, 36), ofOre(TETRAHEDRITE, NORMAL, 18), ofOre(TETRAHEDRITE, RICH, 6),
                        ofOre(NATIVE_COPPER, POOR, 12), ofOre(NATIVE_COPPER, NORMAL, 6),
                        ofOre(NATIVE_COPPER, RICH, 2), ofOre(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, ofRock(TETRAHEDRITE, 80), ofRock(NATIVE_COPPER, 20)));
        createClusterVein("surface_sphalerite", 20, 20, 0.5, 60, 210,
                ofM(ofOre(SPHALERITE, POOR, 36), ofOre(SPHALERITE, NORMAL, 18), ofOre(SPHALERITE, RICH, 6),
                        ofOre(Chalcopyrite, 20), ofOre(PYRITE, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, ofRock(SPHALERITE, 60), ofRock(Chalcopyrite, 20), ofRock(PYRITE, 20)));
        createClusterVein("surface_native_copper", 60, 20, 0.6, 60, 210,
                ofM(ofOre(NATIVE_COPPER, POOR, 42), ofOre(NATIVE_COPPER, NORMAL, 21),
                        ofOre(NATIVE_COPPER, RICH, 7), ofOre(TETRAHEDRITE, POOR, 12),
                        ofOre(TETRAHEDRITE, NORMAL, 6), ofOre(TETRAHEDRITE, RICH, 2), ofOre(Stibnite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                new Indicator(12, ofRock(NATIVE_COPPER, 70), ofRock(TETRAHEDRITE, 20), ofRock(Stibnite, 10)));

        //deep veins
        createClusterVein("deep_magnetite", 100, 35, 0.7, -64, 30,
                ofM(ofOre(MAGNETITE, POOR, 7), ofOre(MAGNETITE, NORMAL, 21), ofOre(MAGNETITE, RICH, 42),
                        ofOre(HEMATITE, POOR, 2), ofOre(HEMATITE, NORMAL, 6), ofOre(HEMATITE, RICH, 12),
                        ofOre(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(MAGNETITE, 70), ofRock(HEMATITE, 20), ofRock(VanadiumMagnetite, 10)));
        createClusterVein("deep_hematite", 100, 35, 0.7, -64, 30,
                ofM(ofOre(HEMATITE, POOR, 7), ofOre(HEMATITE, NORMAL, 21), ofOre(HEMATITE, RICH, 42),
                        ofOre(MAGNETITE, POOR, 2), ofOre(MAGNETITE, NORMAL, 6), ofOre(MAGNETITE, RICH, 12),
                        ofOre(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(12, ofRock(HEMATITE, 70), ofRock(MAGNETITE, 20), ofRock(VanadiumMagnetite, 10)));
        createClusterVein("deep_limonite", 100, 35, 0.7, -64, 30,
                ofM(ofOre(LIMONITE, POOR, 4), ofOre(LIMONITE, NORMAL, 12), ofOre(LIMONITE, RICH, 24),
                        ofOre(BrownLimonite, 40),
                        ofOre(HEMATITE, NORMAL, 4), ofOre(HEMATITE, RICH, 6),
                        ofOre(MALACHITE, NORMAL, 4), ofOre(MALACHITE, RICH, 6)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(LIMONITE, 40), ofRock(BrownLimonite, 40), ofRock(HEMATITE, 10), ofRock(MALACHITE, 10)));


        //normal veins
        createClusterVein("normal_tetrahedrite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(TETRAHEDRITE, POOR, 12), ofOre(TETRAHEDRITE, NORMAL, 30), ofOre(TETRAHEDRITE, RICH, 18),
                        ofOre(NATIVE_COPPER, POOR, 4), ofOre(NATIVE_COPPER, NORMAL, 10),
                        ofOre(NATIVE_COPPER, RICH, 6), ofOre(Stibnite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, ofRock(TETRAHEDRITE, 80), ofRock(NATIVE_COPPER, 20)));
        createClusterVein("normal_sphalerite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(SPHALERITE, POOR, 12), ofOre(SPHALERITE, NORMAL, 30), ofOre(SPHALERITE, RICH, 18),
                        ofOre(PYRITE, 20), ofOre(Chalcopyrite, 20)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(12, ofRock(SPHALERITE, 60), ofRock(Chalcopyrite, 20), ofRock(PYRITE, 20)));
        createClusterVein("normal_malachite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(MALACHITE, POOR, 16), ofOre(MALACHITE, NORMAL, 40), ofOre(MALACHITE, RICH, 24),
                        ofOre(LIMONITE, POOR, 4), ofOre(LIMONITE, NORMAL, 10), ofOre(LIMONITE, RICH, 6),
                        ofOre(GYPSUM, null, 11, s -> s.equals("limestone"))),
                new String[]{"marble", "limestone"},
                new Indicator(12, ofRock(MALACHITE, 80), ofRock(LIMONITE, 20)));
        createClusterVein("normal_magnetite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(MAGNETITE, POOR, 14), ofOre(MAGNETITE, NORMAL, 35), ofOre(MAGNETITE, RICH, 21),
                        ofOre(HEMATITE, POOR, 4), ofOre(HEMATITE, NORMAL, 10), ofOre(HEMATITE, RICH, 6),
                        ofOre(VanadiumMagnetite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(MAGNETITE, 70), ofRock(HEMATITE, 20), ofRock(VanadiumMagnetite, 10)));
        createClusterVein("normal_hematite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(HEMATITE, POOR, 14), ofOre(HEMATITE, NORMAL, 35), ofOre(HEMATITE, RICH, 21),
                        ofOre(MAGNETITE, POOR, 4), ofOre(MAGNETITE, NORMAL, 10), ofOre(MAGNETITE, RICH, 6),
                        ofOre(VanadiumMagnetite, 10)),
                new String[]{"rhyolite", "andesite", "basalt", "dacite"},
                new Indicator(12, ofRock(HEMATITE, 70), ofRock(MAGNETITE, 20), ofRock(VanadiumMagnetite, 10)));
        createClusterVein("normal_limonite", 60, 25, 0.6, -32, 75,
                ofM(ofOre(LIMONITE, POOR, 8), ofOre(LIMONITE, NORMAL, 20), ofOre(LIMONITE, RICH, 12),
                        ofOre(BrownLimonite, 40),
                        ofOre(HEMATITE, POOR, 2), ofOre(HEMATITE, NORMAL, 5), ofOre(HEMATITE, RICH, 3),
                        ofOre(MALACHITE, POOR, 2), ofOre(MALACHITE, NORMAL, 5), ofOre(MALACHITE, POOR, 3)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(LIMONITE, 40), ofRock(BrownLimonite, 40), ofRock(HEMATITE, 10), ofRock(MALACHITE, 10)));
        createClusterVein("normal_garnierite", 70, 20, 0.6, -32, 60,
                ofM(ofOre(GARNIERITE, POOR, 14), ofOre(GARNIERITE, NORMAL, 35), ofOre(GARNIERITE, RICH, 21),
                        ofOre(Cobaltite, 20), ofOre(Pentlandite, 10)),
                new String[]{"gabbro"},
                new Indicator(12, ofRock(GARNIERITE, 70), ofRock(Cobaltite, 20), ofRock(Pentlandite, 10)));
        createClusterVein("normal_native_silver", 60, 22, 0.6, -32, 75,
                ofM(ofOre(NATIVE_SILVER, POOR, 16), ofOre(NATIVE_SILVER, NORMAL, 40),
                        ofOre(NATIVE_SILVER, RICH, 24), ofOre(Lead, 20)),
                new String[]{"granite", "gneiss"},
                new Indicator(12, ofRock(NATIVE_SILVER, 80), ofRock(Lead, 20)));
        createClusterVein("normal_native_copper", 60, 25, 0.6, -32, 75,
                ofM(ofOre(NATIVE_COPPER, POOR, 14), ofOre(NATIVE_COPPER, NORMAL, 35),
                        ofOre(NATIVE_COPPER, RICH, 21), ofOre(TETRAHEDRITE, POOR, 4),
                        ofOre(TETRAHEDRITE, NORMAL, 10), ofOre(TETRAHEDRITE, RICH, 6), ofOre(Stibnite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                new Indicator(12, ofRock(NATIVE_COPPER, 70), ofRock(TETRAHEDRITE, 20), ofRock(Stibnite, 10)));
        //other veins
        createClusterVein("cinnabar", 120, 20, 0.6, -48, 100,
                ofM(ofOre(Redstone, 60), ofOre(Cinnabar, 30), ofOre(Ruby, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "quartzite", "shale"},
                new Indicator(12, ofRock(Redstone, 60), ofRock(Cinnabar, 30), ofRock(Ruby, 10)));
        createClusterVein("lapis_lazuli", 120, 20, 0.6, -48, 100,
                ofM(ofOre(Lazurite, 35), ofOre(Sodalite, 35),
                        ofOre(Lapis, 30)),
                new String[]{"limestone", "marble"},
                null);
    }

    public static void initAdditions(){
        createClusterVein("chalcopyrite", 90, 30, 0.6, -32, 60,
                ofM(ofOre(Chalcopyrite, 30), ofOre(PYRITE, 30),
                        ofOre(HEMATITE, POOR, 6), ofOre(HEMATITE, NORMAL, 15), ofOre(HEMATITE, RICH, 9),
                        ofOre(NATIVE_COPPER, NORMAL, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, ofRock(Chalcopyrite, 30), ofRock(HEMATITE, 30), ofRock(PYRITE, 30), ofRock(NATIVE_COPPER, 10)));
        createClusterVein("salts", 100, 15, 0.6, 0, 90,
                ofM(ofOre(SYLVITE, 35), ofOre(HALITE, 35), ofOre(Lepidolite, 20), ofOre(Spodumene, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, ofRock(Sylvite, 35), ofRock(Salt, 35), ofRock(Lepidolite, 20), ofRock(Spodumene, 10)));
        createClusterVein("pitchblende", 220, 24, 0.45, -64, -20,
                ofM(ofOre(Pitchblende, 60), ofOre(Uraninite, 40)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createClusterVein("soapstone", 120, 35, 0.6, -32, 48,
                ofM(ofOre(Soapstone, 35), ofOre(Talc, 35), ofOre(Glauconite, 20), ofOre(Pentlandite, 10)),
                new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(40, 12, ofRock(Soapstone, 35), ofRock(Talc, 35), ofRock(Glauconite, 20), ofRock(Pentlandite, 10)));
        createClusterVein("platinum", 300, 30, 0.3, -64, 0,
                ofM(ofOre(Sheldonite, 35), ofOre(Sperrylite, 35), ofOre(Platinum, 20), ofOre(Iridium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createClusterVein("chromite", 140, 25, 0.2, -32, 60,
                ofM(ofOre(Chromite, 1)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                null);
        createClusterVein("monazite", 185, 40, 0.25, -32, 50,
                ofM(ofOre(Bastnasite, 70), ofOre(Monazite, 20), ofOre(Neodymium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                new Indicator(30, 12, ofRock(Bastnasite, 70), ofRock(Monazite, 20), ofRock(Neodymium, 10)));
        createClusterVein("molybdenum", 245, 26, 0.45, -64, 10,
                ofM(ofOre(Wulfenite, 35), ofOre(Molybdenite, 35),
                        ofOre(Molybdenum, 20), ofOre(Powellite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "granite", "diorite", "gabbro"},
                null);
        createClusterVein("scheelite", 220, 28, 0.35, -64, 0,
                ofM(ofOre(Scheelite, 65), ofOre(Tungstate, 25), ofOre(Lithium, 10)),
                new String[]{"granite", "diorite", "gabbro"},
                null);
        createDiscVein("sapphire", 180, 28, 8, 0.35, -64, 26,
                ofM(ofOre(Almandine, 35), ofOre(Pyrope, 35), ofOre(Sapphire, 15), ofOre(GreenSapphire, 15)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                null);
        createClusterVein("manganese", 195, 40, 0.3, -32, 60,
                ofM(ofOre(Grossular, 35), ofOre(Spessartine, 35), ofOre(Pyrolusite, 20), ofOre(Tantalite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(40, 12, ofRock(Grossular, 35), ofRock(Spessartine, 35), ofRock(Pyrolusite, 20), ofRock(Tantalite, 10)));
        WeightedBlock[] array = CertusQuartz.enabled ? ofM(ofOre(MilkyQuartz, 35), ofOre(CertusQuartz, 35), ofOre(Barite, 15), ofOre(Quartz, 15)) :
                ofM(ofOre(MilkyQuartz, 70), ofOre(Barite, 15), ofOre(Quartz, 15));
        createClusterVein("quartz", 150, 40, 0.3, -32, 100,
                array,
                new String[]{"shale", "quartzite", "schist", "gneiss"},
                new Indicator(40, 15, array));
        createClusterVein("olivine", 180, 26, 0.25, -40, 30,
                ofM(ofOre(Bentonite, 35), ofOre(Magnesite, 35), ofOre(Olivine, 20), ofOre(Glauconite, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite", "granite", "diorite", "gabbro"},
                new Indicator(12, ofRock(Bentonite, 35), ofRock(Magnesite, 35), ofRock(Olivine, 20), ofRock(Glauconite, 10)));
        createClusterVein("apatite", 110, 20, 0.5, 0, 180,
                ofM(ofOre(Apatite, 70), ofOre(TricalciumPhosphate, 20), ofOre(Phosphate, 10)),
                new String[]{"granite", "diorite", "gabbro", "quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                new Indicator(40, 12, ofRock(Apatite, 70), ofRock(TricalciumPhosphate, 20), ofRock(Phosphate, 10)));
        createClusterVein("galena", 160, 40, 0.4, -32, 75,
                ofM(ofOre(Galena, 60), ofOre(Lead, 10),
                        ofOre(NATIVE_SILVER, POOR, 6), ofOre(NATIVE_SILVER, NORMAL, 15), ofOre(NATIVE_SILVER, RICH, 9)),
                new String[]{"granite", "gneiss"},
                new Indicator(40, 12, ofRock(Galena, 60), ofRock(NATIVE_SILVER, 30), ofRock(Lead, 10)));
        createPipeVein("beryllium", 180, 10, 60, 6, 18, 0, 4, 0, 0.35, -32, 50,
                ofM(ofOre(Beryllium, 60), ofOre(EMERALD, 30), ofOre(Thorium, 10)),
                new String[]{"rhyolite", "basalt", "andesite", "dacite"},
                new Indicator(40, 15, ofRock(Beryllium, 60), ofRock(Emerald, 30), ofRock(Thorium, 10)));
        createClusterVein("bauxite", 185, 40, 0.3, -32, 60,
                ofM(ofOre(Bauxite, 70), ofOre(Alumina, 20), ofOre(Ilmenite, 10)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(Bauxite, 70), ofRock(Alumina, 20), ofRock(Ilmenite, 10)));
        createClusterVein("oil_shale", 185, 55, 0.2, 0, 60,
                ofM(ofOre(OilShale, 1)),
                new String[]{"shale", "claystone", "limestone", "conglomerate", "dolomite", "chert", "chalk"},
                new Indicator(12, ofRock(OilShale, 1)));
        if (!GTAPI.isModLoaded("beneath") && !TFCConfig.SERVER.enableNetherPortals.get()){
            createClusterVein("nether_materials", 150, 20, 0.6, 0, 60,
                    ofM(new WeightedBlock(s -> "minecraft:glowstone", 90, s -> true),
                            new WeightedBlock(s -> "minecraft:ancient_debris", 10, s -> true)),
                    new String[]{"quartzite", "slate", "phyllite", "schist", "gneiss", "marble"},
                    null);
        }
        if (!SpaceModRegistrar.INSTANCE.isEnabled()){
            createClusterVein("naquadah", 310, 32, 0.3, -64, -20,
                    ofM(ofOre(Naquadah, 1)),
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
            if (!material.filter.test(stone)) continue;
            JsonObject materialBlock = new JsonObject();
            materialBlock.addProperty("block", material.block.apply(stone));
            materialBlock.addProperty("weight", material.weight);
            with.add(materialBlock);
        }
        block.add("with", with);
        return block;
    }

    private static WeightedBlock[] ofM(WeightedBlock... materials){
        return materials;
    }

    private static WeightedBlock ofOre(Ore ore, @Nullable Grade grade, int weight, Predicate<String> filter){
        return new WeightedBlock(s -> "tfc:ore/" + (grade == null ? "" : grade.name().toLowerCase() + "_") + ore.name().toLowerCase() + "/" + s, weight, filter);
    }

    private static WeightedBlock ofOre(Ore ore, int weight){
        return ofOre(ore, null, weight, s -> true);
    }

    private static WeightedBlock ofOre(Ore ore, Grade grade, int weight){
        return ofOre(ore, grade, weight, s -> true);
    }

    private static WeightedBlock ofOre(Material material, int weight, Predicate<String> filter){
        return new WeightedBlock(s -> Ref.SHARED_ID + ":ore_" + material.getId() + "_raw_" + s, weight, filter);
    }

    private static WeightedBlock ofOre(Material material, int weight){
        return ofOre(material, weight, s -> true);
    }

    private static WeightedBlock ofRock(Material material, int weight){
        return new WeightedBlock(s -> Ref.SHARED_ID + ":surface_rock_" + material.getId() + "_stone", weight, s -> true);
    }

    private static WeightedBlock ofRock(Ore ore, int weight){
        return new WeightedBlock(s -> Ref.MOD_TFC + ":ore/small_" + ore.name().toLowerCase(), weight, s -> true);
    }

    public record WeightedBlock(Function<String, String> block, int weight, Predicate<String> filter) {
    }

    public record Indicator(int depth, int rarity, List<Pair<String, Integer>> blocks) {
        public Indicator(int rarity, WeightedBlock... blocks){
            this(35, rarity, blocks);
        }

        public Indicator(int depth, int rarity, WeightedBlock... blocks) {
            this(depth, rarity, Stream.of(blocks).map(w -> {
                return Pair.of(w.block.apply(""), w.weight);
            }).toList());
        }
    }


}
