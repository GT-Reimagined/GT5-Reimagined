package org.gtreimagined.gt5r.loader;

import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTLibConfig;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.event.GTWorldGenEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.TagUtils;
import org.gtreimagined.gtlib.worldgen.StoneLayerOre;
import org.gtreimagined.gtlib.worldgen.bedrockore.WorldGenBedrockVein;
import org.gtreimagined.gtlib.worldgen.object.WorldGenStoneLayerBuilder;
import org.gtreimagined.gtlib.worldgen.smallore.SmallOreBuilder;
import org.gtreimagined.gtlib.worldgen.vanillaore.WorldGenVanillaOreBuilder;
import org.gtreimagined.gtlib.worldgen.vein.VeinBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gt5r.worldgen.OilSpoutFluid;
import org.gtreimagined.gt5r.worldgen.OilSpoutSavedData;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gtlib.Ref.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE_STONE;
import static org.gtreimagined.gtlib.data.GTLibMaterials.*;
import static org.gtreimagined.gtlib.data.VanillaStoneTypes.*;
import static net.minecraft.world.level.Level.END;
import static net.minecraft.world.level.Level.NETHER;
import static net.minecraft.world.level.Level.OVERWORLD;
import static org.gtreimagined.gt5r.data.GT5RBlocks.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.*;

public class WorldGenLoader {

    public static final ResourceKey<Level> TWILIGHT_FOREST = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Ref.TWILIGHT_FOREST));
    public static final ResourceKey<Level> BE_MOON = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("beyond_earth", "moon"));
    public static final ResourceKey<Level> AA_MOON = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("ad_astra", "moon"));
    public static final ResourceKey<Level> BE_MARS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("beyond_earth", "mars"));
    public static final ResourceKey<Level> AA_MARS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("ad_astra", "mars"));
    public static final ResourceKey<Level> BE_MERCURY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("beyond_earth", "mercury"));
    public static final ResourceKey<Level> AA_MERCURY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("ad_astra", "mercury"));
    public static final ResourceKey<Level> BE_VENUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("beyond_earth", "venus"));
    public static final ResourceKey<Level> AA_VENUS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("ad_astra", "venus"));
    public static final ResourceKey<Level> JAMD_MINING = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("jamd", "mining"));


    public static void init(GTWorldGenEvent ev) {
        if (GTLibConfig.STONE_LAYERS.get()) {
            initStoneVeins(ev);
        }
        if (GTLibConfig.ORE_VEINS.get()) {
            initOreVeins(ev);
        }
        if (GTLibConfig.SMALL_ORES.get() && !GTAPI.isModLoaded(MOD_TFC)){
            initSmallOres(ev);
        }
        if (GTLibConfig.BEDROCK_VEINS.get()) {
            initBedrockVeins(ev);
        }
        OilSpoutSavedData.clearFluidMap();
        OilSpoutFluid.resetTotalWeight();
        new OilSpoutFluid("oil", Oil.getLiquid(), 20, 625, 4, 5);
        new OilSpoutFluid("light_oil", OilLight.getLiquid(), 20, 625, 3, 6);
        new OilSpoutFluid("heavy_oil", OilHeavy.getLiquid(), 20, 625, 5, 4);
        new OilSpoutFluid("natural_gas", NaturalGas.getGas(), 20, 625, 4, 7);
        if (GTAPI.isModLoaded(MOD_TWILIGHT)){
            initTwilightForestOres(ev);
        }
    }

    private static void initBedrockVeins(GTWorldGenEvent ev) {
        ev.bedrockOre(WorldGenBedrockVein.create("diamond", 128000, Diamond, true, PANDANUS_CANDELABRUM, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("tungstate", 96000, Tungstate, true, TUNGSTUS, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("scheelite", 96000, Scheelite, true, TUNGSTUS, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("uraninite", 60000, Uraninite, true, TUFTED_EVENING_PRIMROSE, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("pitchblende", 60000, Pitchblende, true, THOMPSONS_LOCOWEED, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("gold_a", 32000, Gold, true, ALTERED_ANDESITE_BUCKWHEAT, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("gold_b", 32000, Gold, true, DESERT_TRUMPET, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("sheldonite", 16000, Sheldonite, true, NARCISSUS_SHELDONIA, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("copper", 16000, Copper, true, BECIUM_HOMBLEI, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("monzanite", 16000, Monazite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("powellite", 14000, Powellite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("bastnasite", 8000, Bastnasite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("redstone", 7000, Redstone, true, PRINCES_PLUME, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("vanadium_magnetite", 6000, VanadiumMagnetite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("galena", 6000, Galena, true, CROSBY_BUCKWHEAT, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("coal", 5000, Coal, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("graphite", 5000, Graphite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("stibnite", 4000, Stibnite, true, ATRIPLEX_CANESCENS, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("hematite", 4000, Hematite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("sphalerite", 3000, Sphalerite, true, VIOLA_CALAMINARIA, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("pentlandite", 3000, Pentlandite, true, THLASPI_LERESCHIANUM, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("saltpeter", 3000, Saltpeter, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("bauxite", 2000, Bauxite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("cassiterite", 2000, Cassiterite, true, ORECHID, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("chalcopyrite", 2000, Chalcopyrite, true, ALPINE_CATCHFLY, OVERWORLD.location(), JAMD_MINING.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("naquadah", 10000, Naquadah, true, BE_MARS.location(), AA_MARS.location()));
        ev.bedrockOre(WorldGenBedrockVein.create("adamantine", 10000, Adamantine, true, BE_MARS.location(), AA_MARS.location()));
        if (SpaceModRegistrar.INSTANCE.isEnabled()){
            ev.bedrockOre(WorldGenBedrockVein.create("desh", 2000, SpaceModRegistrar.Desh, true, BE_MARS.location(), AA_MARS.location()));
        }
    }

    private static void initTwilightForestOres(GTWorldGenEvent event){
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Coal).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Lignite).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Salt).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(RockSalt).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Bauxite).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(OilShale).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(MilkyQuartz).withSize(50).withWeight(1).atHeight(-16, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());

        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Sulfur).withSize(16).withProbability(100).atHeight(-32, -24).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Apatite).withSize(16).withProbability(50).atHeight(-8, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Ruby).withSize(12).withProbability(100).atHeight(8, 22).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Amber).withSize(12).withProbability(100).atHeight(8, 22).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Amethyst).withSize(12).withProbability(100).atHeight(8, 22).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Galena).withSize(24).withProbability(25).atHeight(-24, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Tetrahedrite).withSize(24).withProbability(25).atHeight(-24, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Cassiterite).withSize(24).withProbability(25).atHeight(-24, 0).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Sheldonite).withSize(6).withProbability(100).atHeight(8, 22).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Pentlandite).withSize(16).withProbability(25).atHeight(-24, -8).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Scheelite).withSize(12).withProbability(25).atHeight(-24, -8).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Rutile).withSize(6).withProbability(25).atHeight(-24, -8).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Bastnasite).withSize(16).withProbability(100).atHeight(8, 22).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Graphite).withSize(6).withProbability(50).atHeight(-32, -24).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
        event.vanillaOre(new WorldGenVanillaOreBuilder().withMaterial(Pitchblende).withSize(16).withProbability(100).atHeight(-24, -16).withDimensions(TWILIGHT_FOREST.location()).buildMaterial());
    }

    private static void initSmallOres(GTWorldGenEvent event){
        event.smallOre(new SmallOreBuilder().withMaterial(Copper).withAmountPerChunk(32).atHeight(16, 126).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Tin).withAmountPerChunk(32).atHeight(16, 126).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Bismuth).withAmountPerChunk(8).atHeight(76, 196).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Coal).withAmountPerChunk(24).atHeight(16, 126).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Iron).withAmountPerChunk(16).atHeight(16, 61).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Lead).withAmountPerChunk(16).atHeight(16, 61).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Zinc).withAmountPerChunk(12).atHeight(16, 96).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Gold).withAmountPerChunk(8).atHeight(-34, 16).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Silver).withAmountPerChunk(8).atHeight(-34, 16).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Nickel).withAmountPerChunk(8).atHeight(-34, 16).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Lapis).withAmountPerChunk(4).atHeight(-34, 16).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Diamond).withAmountPerChunk(3).atHeight(-59, -52).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Emerald).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Ruby).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Sapphire).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(GreenSapphire).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Olivine).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Topaz).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Tanzanite).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Amethyst).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Opal).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Jade).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(BlueTopaz).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Amber).withAmountPerChunk(2).buildMaterial());
        //event.smallOre(new SmallOreBuilder().withMaterial(FoolsRuby).withAmountPerChunk(1).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(RedGarnet).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(YellowGarnet).withAmountPerChunk(2).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Redstone).withAmountPerChunk(8).atHeight(-59, -34).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Chromite).withAmountPerChunk(8).atHeight(20, 50).withDimensions(END.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Platinum).withAmountPerChunk(8).atHeight(20, 40).withDimensions(END.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Iridium).withAmountPerChunk(8).atHeight(20, 40).withDimensions(END.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Quartz).withAmountPerChunk(64).atHeight(30, 120).withDimensions(NETHER.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Saltpeter).withAmountPerChunk(8).atHeight(10, 60).withDimensions(NETHER.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Sulfur).withAmountPerChunk(32).atHeight(5, 60).withDimensions(NETHER.location()).buildMaterial());
        event.smallOre(new SmallOreBuilder().withMaterial(Sulfur).withAmountPerChunk(8).atHeight(-59, -34).withCustomId("sulfur_overworld").buildMaterial());
    }

    private static void initStoneVeins(GTWorldGenEvent ev) {
        List<ResourceKey<Level>> overworld = List.of(OVERWORLD, JAMD_MINING);
        ev.stoneLayer(new WorldGenStoneLayerBuilder("stone").withStone(STONE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("black_granite").withStone(BLACK_GRANITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("red_granite").withStone(RED_GRANITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("komatiite").withStone(KOMATIITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("basalt").withStone(BASALT).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("marble").withStone(MARBLE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("limestone").withStone(LIMESTONE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("green_schist").withStone(GREEN_SCHIST).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("blue_schist").withStone(BLUE_SCHIST).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("kimberlite").withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("quartzite").withStone(QUARTZITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("shale").withStone(SHALE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("slate").withStone(SLATE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("granite").withStone(GRANITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("diorite").withStone(DIORITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("andesite").withStone(ANDESITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("tuff").withStone(TUFF).withWeight(1).inDimensions(overworld).buildVein());
        if (GT5RConfig.GT6_ORE_GEN.get()){
            ev.stoneLayer(new WorldGenStoneLayerBuilder("kimberlite_2").withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).buildVein());
            /*ev.stoneLayer(new WorldGenStoneLayerBuilder("deepslate_ores").withStone(STONE).withWeight(1).addOres(
                    new StoneLayerOre(Emerald, U64, -32, 0).addFilteredBiome(BiomeTags.IS_MOUNTAIN),
                    new StoneLayerOre(Diamond, U64, -64, -52).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Lapis, U12, -32, 0).addFilteredBiome(BiomeTags.IS_TAIGA).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES),
                    new StoneLayerOre(Amber, U32, 16, 24).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN),
                    new StoneLayerOre(Redstone, U16, 0, 20),
                    new StoneLayerOre(Cinnabar, U64, 0, 20).addFilteredBiome(BiomeTags.IS_TAIGA).addFilteredBiome(Biomes.BADLANDS).addFilteredBiome(Biomes.ERODED_BADLANDS),
                    new StoneLayerOre(Uraninite, U64, 0, 12).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Thorium, U64, 0, 12).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Scheelite, U64, 0, 12).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES)).buildVein());*/
            ev.stoneLayer(new WorldGenStoneLayerBuilder("granite_ores").withStone(GRANITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(BlueTopaz, U64, -24, 0).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Topaz, U64, -8, 16).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("granite_ores_2").withStone(GRANITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Apatite, U8, 32, 64),
                    new StoneLayerOre(Phosphate, U24, 36, 60),
                    new StoneLayerOre(TricalciumPhosphate, U24, 40, 56)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("diorite_ores").withStone(DIORITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Sapphire, U64, -24, 0).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(GreenSapphire, U64, -8, 16).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Ruby, U64, -8, 16).addFilteredBiome(TagUtils.getBiomeTag(new ResourceLocation("is_desert"))).addFilteredBiome(Biomes.DESERT)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("diorite_ores_2").withStone(DIORITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Garnierite, U8, 16, 48),
                    new StoneLayerOre(Pentlandite, U8, 24, 56),
                    new StoneLayerOre(Cobaltite, U8, 32, 64),
                    new StoneLayerOre(Amethyst, U64, 24, 48).addFilteredBiome(BiomeTags.IS_TAIGA)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("andesite_ores").withStone(ANDESITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Gold, U12, -64, -32),
                    new StoneLayerOre(Gold, U8, 32, 64).addFilteredBiome(Biomes.BADLANDS).addFilteredBiome(Biomes.ERODED_BADLANDS)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("tuff_ores").withStone(TUFF).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Magnetite, U4, -16, 84),
                    new StoneLayerOre(Hematite, U6, -16, 64),
                    new StoneLayerOre(VanadiumMagnetite, U64, -16, 16)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("black_granite_ores").withStone(BLACK_GRANITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Sheldonite, U32, -64, -48),
                    new StoneLayerOre(Sperrylite, U32, -64, -48),
                    new StoneLayerOre(Iridium, U64, -64, -56),
                    new StoneLayerOre(Emerald, U64, -40, -16)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("red_granite_ores").withStone(RED_GRANITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Pitchblende, U32, -32, 0),
                    new StoneLayerOre(Uraninite, U32, -32, 0),
                    new StoneLayerOre(Tantalite, U16, -32, 0)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("komatiite_ores").withStone(KOMATIITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Magnesite, U16, -54, -9),
                    new StoneLayerOre(Cinnabar, U12, -64, -19),
                    new StoneLayerOre(Redstone, U8, -54, -9),
                    new StoneLayerOre(Pyrite, U12, 5, 66)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("kimberlite_ores").withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Diamond, U48, -64, -52)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("basalt_ores").withStone(BASALT).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Olivine, U32, -48, -16),
                    new StoneLayerOre(Uvarovite, U32, -40, -8),
                    new StoneLayerOre(Grossular, U32, -32, 0),
                    new StoneLayerOre(Chromite, U8, -16, 16)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("basalt_ores_2").withStone(BASALT).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Bastnasite, U24, 24, 32),
                    new StoneLayerOre(Monazite, U32, 24, 32),
                    new StoneLayerOre(Manganese, U8, 16, 48)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("marble_ores").withStone(MARBLE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Cassiterite, U16, 20, 120),
                    new StoneLayerOre(Tin, U16, 10, 100),
                    new StoneLayerOre(Sphalerite, U8 , 10, 50),
                    new StoneLayerOre(Chalcopyrite, U8 ,  0, 40),
                    new StoneLayerOre(Pyrite, U12,  0, 50)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("limestone_ores").withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Stibnite, U24, 10, 30),
                    new StoneLayerOre(Galena, U8, 30, 120),
                    new StoneLayerOre(Lead, U16, 50, 70)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("limestone_ores_2").withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Pyrite, U16, 0, 45),
                    new StoneLayerOre(Galena, U8, 5, 25),
                    new StoneLayerOre(Galena, U8, 80, 120),
                    new StoneLayerOre(Wulfenite, U32, -34, -19),
                    new StoneLayerOre(Powellite, U32, -29, -14),
                    new StoneLayerOre(Molybdenite, U128, -34, -14),
                    new StoneLayerOre(Tetrahedrite, U8, 40, 100),
                    new StoneLayerOre(Copper, U16, 40, 100)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("limestone_ores_3").withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Scheelite, U64, -64, -48),
                    new StoneLayerOre(Tungstate, U64, -64, -48),
                    new StoneLayerOre(YellowLimonite, U8, -48, -16),
                    new StoneLayerOre(BrownLimonite, U8, -32, 0),
                    new StoneLayerOre(Malachite, U12, -48, 0)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("green_schist_ores").withStone(GREEN_SCHIST).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Andradite, U32, -40, 8),
                    new StoneLayerOre(Almandine, U32, -32, 0)
            ).buildVein());
            ev.stoneLayer(new WorldGenStoneLayerBuilder("blue_schist_ores").withStone(BLUE_SCHIST).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Spessartine, U32, -40, 8),
                    new StoneLayerOre(Pyrope, U32, -32, 0)
            ).buildVein());
            if (GTAPI.isModLoaded(MOD_AE)){
                ev.stoneLayer(new WorldGenStoneLayerBuilder("quartzite_ores").withStone(QUARTZITE).withWeight(1).inDimensions(overworld).addOres(
                        new StoneLayerOre(CertusQuartz, U16, 16, 48),
                        new StoneLayerOre(MilkyQuartz, U16, 16, 48),
                        new StoneLayerOre(Barite, U32, 0, 32)
                ).buildVein());
            } else {
                ev.stoneLayer(new WorldGenStoneLayerBuilder("quartzite_ores").withStone(QUARTZITE).withWeight(1).inDimensions(overworld).addOres(
                        new StoneLayerOre(MilkyQuartz, U16, 16, 48),
                        new StoneLayerOre(Barite, U32, 0, 32)
                ).buildVein());
            }

            ev.addCollisionBothSides(ORE_STONE.get().get(Coal).asState(), STONE.getState(),
                    new StoneLayerOre(Amber, U4, 30, 70).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Amber, U8, 30, 70).addFilteredBiome(BiomeTags.IS_RIVER));
            ev.addCollisionBothSides(ORE_STONE.get().get(Lignite).asState(), STONE.getState(),
                    new StoneLayerOre(Amber, U4, 30, 70).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Amber, U8, 30, 70).addFilteredBiome(BiomeTags.IS_RIVER));
            ev.addCollisionBothSides(ORE_STONE.get().get(OilShale).asState(), STONE.getState(),
                    new StoneLayerOre(Amber, U4, 30, 70).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Amber, U8, 30, 70).addFilteredBiome(BiomeTags.IS_RIVER));
            ev.addCollisionBothSides(BLACK_GRANITE.getState(), MARBLE.getState(),
                    new StoneLayerOre(Lapis, U8, 0, 48),
                    new StoneLayerOre(Sodalite, U16, 0, 48),
                    new StoneLayerOre(Lazurite, U16, 0, 48),
                    new StoneLayerOre(Pyrite, U16, 0, 48));
            ev.addCollisionTopBottom(BLACK_GRANITE.getState(), BASALT.getState(),
                    new StoneLayerOre(Diamond, U64, -64, -32),
                    new StoneLayerOre(Graphite, U8, -64, -32));
            ev.addCollisionBothSides(BLACK_GRANITE.getState(), GRANITE.getState(),
                    new StoneLayerOre(Zircon, U24, 0, 32));
            ev.addCollisionBothSides(BLACK_GRANITE.getState(), RED_GRANITE.getState(),
                    new StoneLayerOre(Zircon, U24, 0, 32));


        }
        ev.stoneLayer(new WorldGenStoneLayerBuilder("coal").withStone(ORE_STONE.get().get(Coal).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("lignite").withStone(ORE_STONE.get().get(Lignite).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("salt").withStone(ORE_STONE.get().get(Salt).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("rock_salt").withStone(ORE_STONE.get().get(RockSalt).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("bauxite").withStone(ORE_STONE.get().get(Bauxite).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new WorldGenStoneLayerBuilder("oil_shale").withStone(ORE_STONE.get().get(OilShale).asState()).withWeight(1).inDimensions(overworld).buildVein());

        ev.addCollisionBothSides(BASALT.getState(), LIMESTONE.getState(),
                new StoneLayerOre(Ilmenite, U8, -64, 0),
                new StoneLayerOre(Rutile, U12, -64, 0)
        );

    }

    private static void initOreVeins(GTWorldGenEvent ev) {
        List<ResourceKey<Level>> overworld = new ArrayList<>();
        if (!GTAPI.isModLoaded(MOD_TFC) && !GT5RConfig.GT6_ORE_GEN.get()){
            overworld.add(OVERWORLD);
        }
        if (!GT5RConfig.GT6_ORE_GEN.get()){
            overworld.add(JAMD_MINING);
        }
        List<ResourceKey<Level>> moonMars = List.of(BE_MOON, AA_MOON, BE_MARS, AA_MARS);
        List<ResourceKey<Level>> mars = List.of(BE_MARS, AA_MARS);
        List<ResourceKey<Level>> moon = List.of(BE_MOON, AA_MOON);

        ev.vein(new VeinBuilder(id("gold")).asOreVein(-4, 26, 160, 3, 32, Magnetite, Magnetite, VanadiumMagnetite, Gold)
                .inDimensions(moonMars).inDimensions(overworld).buildVein());
        ev.vein(new VeinBuilder(id("iron")).asOreVein(-14, 51, 120, 4, 24, BrownLimonite, YellowLimonite, Hematite, Malachite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("cassiterite")).asOreVein(6, 126, 50, 5, 24, Tin, Tin, Cassiterite, Tin)
                .inDimensions(overworld).inDimension(END).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("tetrahedrite")).asOreVein(51, 131, 70, 4, 24, Tetrahedrite, Tetrahedrite, Copper, Stibnite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(moonMars).buildVein());
        Material sporadic = !GTLibConfig.STONE_LAYERS.get() ? Calcite : Alumina;
        ev.vein(new VeinBuilder(id("magnetite")).asOreVein(-14, 91, 160, 3, 32, Magnetite, Magnetite, Iron, VanadiumMagnetite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("copper")).asOreVein(36, 66, 80, 4, 24, Chalcopyrite, Iron, Pyrite, Copper)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("salts")).asOreVein(51, 66, 50, 3, 24, RockSalt, Salt, Lepidolite, Spodumene)
                .inDimensions(overworld).inDimensions(moon).buildVein());
        ev.vein(new VeinBuilder(id("redstone")).asOreVein(-54, -9, 60, 3, 24, Redstone, Redstone, Ruby, Cinnabar)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("pitchblend")).asOreVein(-54, -9, 20, 3, 20, Pitchblende, Pitchblende, Uraninite, Uraninite)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("soapstone")).asOreVein(-54, -9, 40, 3, 16, Soapstone, Talc, Glauconite, Pentlandite)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("nickel")).asOreVein(11, 56, 40, 3, 16, Garnierite, Nickel, Cobaltite, Pentlandite)
                .inDimensions(overworld).inDimensions(NETHER, END).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("platinum")).asOreVein(-24, -9, 5, 3, 16, Sheldonite, Sperrylite, Platinum, Iridium)
                .inDimensions(overworld).inDimensions(mars).buildVein());
        /*ev.vein(new VeinBuilder("uranium").asOreVein(-44, -29, 20, 3, 16, Uraninite, Uraninite, Uraninite, Uraninite)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());*/
        ev.vein(new VeinBuilder(id("monazite")).asOreVein(-44, -14, 30, 3, 16, Bastnasite, Bastnasite, Monazite, Neodymium)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("molybdenum")).asOreVein(-44, 1, 5, 3, 16, Wulfenite, Molybdenite, Molybdenum, Powellite)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("tungstate")).asOreVein(-44, 1, 10, 3, 16, Scheelite, Scheelite, Tungstate, Lithium)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("sapphire")).asOreVein(-54, -9, 60, 3, 16, Almandine, Pyrope, Sapphire, GreenSapphire)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("manganese")).asOreVein(-44, -29, 20, 3, 16, Grossular, Spessartine, Pyrolusite, Tantalite)
                .inDimensions(overworld).inDimensions(moon).buildVein());
        Material third = CertusQuartz.enabled ? CertusQuartz : Barite;
        ev.vein(new VeinBuilder(id("quartz")).asOreVein(6, 66, 60, 3, 16, MilkyQuartz, Barite, third, third)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("diamond")).asOreVein(-59, -48, 40, 2, 16, Graphite, Graphite, Diamond, Coal)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("olivine")).asOreVein(-54, -9, 60, 3, 16, Bentonite, Magnesite, Olivine, Glauconite)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("apatite")).asOreVein(-4, 41, 60, 3, 16, Apatite, Apatite, TricalciumPhosphate, Phosphate)
                .inDimensions(overworld).buildVein());
        ev.vein(new VeinBuilder(id("galena")).asOreVein(6, 51, 40, 5, 16, Galena, Galena, Silver, Lead)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("lapis")).asOreVein(-44, 1, 40, 5, 16, Lazurite, Sodalite, Lapis, sporadic)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("beryllium")).asOreVein(-59, -21, 30, 3, 16, Beryllium, Beryllium, Emerald, Thorium)
                .inDimensions(overworld).inDimensions(moonMars).buildVein());
        ev.vein(new VeinBuilder(id("naquadah")).asOreVein(10, 60, 10, 5, 32, Naquadah, Naquadah, Naquadah, Naquadah)
                .inDimension(END).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("nether_quartz")).asOreVein(40, 80, 80, 5, 24, Quartz, Quartz, Quartz, Quartz,
                NETHER).buildVein());
        ev.vein(new VeinBuilder(id("sulfur")).asOreVein(5, 20, 100, 5, 24, Sulfur, Sulfur, Pyrite, Sphalerite)
                .inDimension(NETHER).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("redstone_nether")).asOreVein(10, 40, 60, 3, 24, Redstone, Redstone, Ruby, Cinnabar,
                NETHER).buildVein());
        ev.vein(new VeinBuilder(id("platinum_end")).asOreVein(40, 50, 5, 3, 16, Sheldonite, Sperrylite, Platinum, Iridium,
                END).buildVein());
        ev.vein(new VeinBuilder(id("molybdenum_end")).asOreVein(20, 50, 5, 3, 16, Wulfenite, Molybdenite, Molybdenum,
                Powellite , END).buildVein());
        ev.vein(new VeinBuilder(id("tungstate_end")).asOreVein(20, 50, 10, 3, 16, Scheelite, Scheelite, Tungstate, Lithium,
                END).buildVein());
        ev.vein(new VeinBuilder(id("manganese_end")).asOreVein(20, 30, 20, 3, 16, Grossular, Spessartine, Pyrolusite, Tantalite,
                END).buildVein());
        ev.vein(new VeinBuilder(id("olivine_end")).asOreVein(10, 40, 60, 3, 16, Bentonite, Magnesite, Olivine, Glauconite,
                END).buildVein());
        ev.vein(new VeinBuilder(id("lapis_end")).asOreVein(20, 50, 40, 5, 16, Lazurite, Sodalite, Lapis, sporadic,
                END).buildVein());
        ev.vein(new VeinBuilder(id("beryllium_end")).asOreVein(5, 30, 30, 3, 16, Beryllium, Beryllium, Emerald, Thorium,
                END).buildVein());
        if (GT5RConfig.GT6_ORE_GEN.get()) return;
        List<ResourceKey<Level>> overworldTFCStoneLayers = new ArrayList<>();
        if (!GTAPI.isModLoaded(MOD_TFC) && !GTLibConfig.STONE_LAYERS.get()){
            overworldTFCStoneLayers.add(OVERWORLD);
        }
        if (!GTLibConfig.STONE_LAYERS.get()) {
            overworldTFCStoneLayers.add(JAMD_MINING);
        }
        if (!overworldTFCStoneLayers.isEmpty()){
            ev.vein(new VeinBuilder(id("bauxite")).asOreVein(-14, 46, 80, 4, 24, Bauxite, Bauxite, Alumina, Ilmenite)
                    .inDimensions(overworldTFCStoneLayers).inDimensions(moonMars).buildVein());
            ev.vein(new VeinBuilder(id("oilshale")).asOreVein(-14, 31, 80, 6, 32, OilShale, OilShale, OilShale, OilShale)
                    .inDimensions(overworldTFCStoneLayers).buildVein());
            ev.vein(new VeinBuilder(id("lignite")).asOreVein(0, 200, 160, 8, 32, Lignite, Lignite, Lignite, Coal)
                    .inDimensions(overworldTFCStoneLayers).buildVein());
            ev.vein(new VeinBuilder(id("coal")).asOreVein(0, 200, 80, 6, 32, Coal, Coal, Coal, Lignite)
                    .inDimensions(overworldTFCStoneLayers).buildVein());
        }
    }

    public static ResourceLocation id(String id){
        return new ResourceLocation(GT5RRef.ID, id);
    }
}
