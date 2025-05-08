package org.gtreimagined.gt5r.loader;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gt5r.integration.SpaceModRegistrar;
import org.gtreimagined.gt5r.worldgen.OilSpoutFluid;
import org.gtreimagined.gt5r.worldgen.OilSpoutSavedData;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTLibConfig;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.event.GTWorldGenEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.TagUtils;
import org.gtreimagined.gtlib.worldgen.bedrockore.BedrockVein;
import org.gtreimagined.gtlib.worldgen.smallore.SmallOreBuilder;
import org.gtreimagined.gtlib.worldgen.stonelayer.StoneLayerBuilder;
import org.gtreimagined.gtlib.worldgen.stonelayer.StoneLayerOre;
import org.gtreimagined.gtlib.worldgen.vanillaore.VanillaVeinBuilder;
import org.gtreimagined.gtlib.worldgen.vein.VeinBuilder;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.Level.END;
import static net.minecraft.world.level.Level.NETHER;
import static net.minecraft.world.level.Level.OVERWORLD;
import static org.gtreimagined.gt5r.data.GT5RBlocks.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtcore.data.GTCoreBlocks.*;
import static org.gtreimagined.gtlib.Ref.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE_STONE;
import static org.gtreimagined.gtlib.data.VanillaStoneTypes.*;

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
        if (GT5RConfig.DEFAULT_STONE_LAYERS.get()) {
            initStoneVeins(ev);
        }
        if (GT5RConfig.DEFAULT_ORE_VEINS.get()) {
            initOreVeins(ev);
        }
        if (GT5RConfig.DEFAULT_SMALL_ORES.get() && !GTAPI.isModLoaded(MOD_TFC)){
            initSmallOres(ev);
        }
        if (GT5RConfig.DEFAULT_BEDROCK_VEINS.get()) {
            initBedrockVeins(ev);
        }
        OilSpoutSavedData.clearFluidMap();
        OilSpoutFluid.resetTotalWeight();
        new OilSpoutFluid("oil", Oil.getLiquid(), 20, 625, 4, 5);
        new OilSpoutFluid("light_oil", OilLight.getLiquid(), 20, 625, 3, 6);
        new OilSpoutFluid("heavy_oil", OilHeavy.getLiquid(), 20, 625, 5, 4);
        new OilSpoutFluid("natural_gas", NaturalGas.getGas(), 20, 625, 4, 7);
        if (GTAPI.isModLoaded(MOD_TWILIGHT) && GT5RConfig.DEFAULT_TWILIGHT_ORE_GEN.get()){
            initTwilightForestOres(ev);
        }
    }

    private static void initBedrockVeins(GTWorldGenEvent ev) {
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"diamond"), 128000, Diamond, true, PANDANUS_CANDELABRUM, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"tungstate"), 96000, Tungstate, true, TUNGSTUS, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"scheelite"), 96000, Scheelite, true, TUNGSTUS, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"uraninite"), 60000, Uraninite, true, TUFTED_EVENING_PRIMROSE, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"pitchblende"), 60000, Pitchblende, true, THOMPSONS_LOCOWEED, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"gold_a"), 32000, Gold, true, ALTERED_ANDESITE_BUCKWHEAT, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"gold_b"), 32000, Gold, true, DESERT_TRUMPET, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"sheldonite"), 16000, Sheldonite, true, NARCISSUS_SHELDONIA, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"copper"), 16000, Copper, true, BECIUM_HOMBLEI, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"monzanite"), 16000, Monazite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"powellite"), 14000, Powellite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"bastnasite"), 8000, Bastnasite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"redstone"), 7000, Redstone, true, PRINCES_PLUME, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"vanadium_magnetite"), 6000, VanadiumMagnetite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"galena"), 6000, Galena, true, CROSBY_BUCKWHEAT, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"coal"), 5000, Coal, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"graphite"), 5000, Graphite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"stibnite"), 4000, Stibnite, true, ATRIPLEX_CANESCENS, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"hematite"), 4000, Hematite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"sphalerite"), 3000, Sphalerite, true, VIOLA_CALAMINARIA, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"pentlandite"), 3000, Pentlandite, true, THLASPI_LERESCHIANUM, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"saltpeter"), 3000, Saltpeter, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"bauxite"), 2000, Bauxite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"cassiterite"), 2000, Cassiterite, true, ORECHID, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"chalcopyrite"), 2000, Chalcopyrite, true, ALPINE_CATCHFLY, OVERWORLD, JAMD_MINING));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"naquadah"), 10000, Naquadah, true, BE_MARS, AA_MARS));
        ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"adamantine"), 10000, Adamantine, true, BE_MARS, AA_MARS));
        if (SpaceModRegistrar.INSTANCE.isEnabled()){
            ev.bedrockOre(BedrockVein.create(new ResourceLocation(GT5RRef.ID,"desh"), 2000, SpaceModRegistrar.Desh, true, BE_MARS, AA_MARS));
        }
    }

    private static void initTwilightForestOres(GTWorldGenEvent event){
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "coal_twilight")).withMaterial(Coal).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "lignite_twilight")).withMaterial(Lignite).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "salt_twilight")).withMaterial(Salt).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "sylvite_twilight")).withMaterial(Sylvite).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "bauxite_twilight")).withMaterial(Bauxite).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "oil_shale_twilight")).withMaterial(OilShale).withMaterialType(ORE_STONE).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "milky_quartz_twilight")).withMaterial(MilkyQuartz).withSize(50).withWeight(1).atHeight(-16, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());

        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "sulfur_twilight")).withMaterial(Sulfur).withSize(16).withProbability(100).atHeight(-32, -24).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "apatite_twilight")).withMaterial(Apatite).withSize(16).withProbability(50).atHeight(-8, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "ruby_twilight")).withMaterial(Ruby).withSize(12).withProbability(100).atHeight(8, 22).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "amber_twilight")).withMaterial(Amber).withSize(12).withProbability(100).atHeight(8, 22).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "amethyst_twilight")).withMaterial(Amethyst).withSize(12).withProbability(100).atHeight(8, 22).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "galena_twilight")).withMaterial(Galena).withSize(24).withProbability(25).atHeight(-24, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "tetrahedrite_twilight")).withMaterial(Tetrahedrite).withSize(24).withProbability(25).atHeight(-24, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "cassiterite_twilight")).withMaterial(Cassiterite).withSize(24).withProbability(25).atHeight(-24, 0).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "sheldonite_twilight")).withMaterial(Sheldonite).withSize(6).withProbability(100).atHeight(8, 22).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "pentlandite_twilight")).withMaterial(Pentlandite).withSize(16).withProbability(25).atHeight(-24, -8).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "scheelite_twilight")).withMaterial(Scheelite).withSize(12).withProbability(25).atHeight(-24, -8).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "rutile_twilight")).withMaterial(Rutile).withSize(6).withProbability(25).atHeight(-24, -8).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "bastnasite_twilight")).withMaterial(Bastnasite).withSize(16).withProbability(100).atHeight(8, 22).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "graphite_twilight")).withMaterial(Graphite).withSize(6).withProbability(50).atHeight(-32, -24).inDimensions(TWILIGHT_FOREST).buildMaterial());
        event.vanillaOre(new VanillaVeinBuilder(new ResourceLocation(GT5RRef.ID, "pitchblende_twilight")).withMaterial(Pitchblende).withSize(16).withProbability(100).atHeight(-24, -16).inDimensions(TWILIGHT_FOREST).buildMaterial());
    }

    private static void initSmallOres(GTWorldGenEvent event){
        List<ResourceKey<Level>> mars = List.of(BE_MARS, AA_MARS);
        List<ResourceKey<Level>> moon = List.of(BE_MOON, AA_MOON);
        event.smallOre(new SmallOreBuilder(id("copper")).withMaterial(Copper).withAmountPerChunk(32).atHeight(16, 126).inDimensions(OVERWORLD, JAMD_MINING, NETHER, END).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("tin")).withMaterial(Tin).withAmountPerChunk(32).atHeight(16, 126).inDimensions(OVERWORLD, JAMD_MINING, NETHER, END).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("bismuth")).withMaterial(Bismuth).withAmountPerChunk(8).atHeight(76, 196).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("coal")).withMaterial(Coal).withAmountPerChunk(24).atHeight(16, 126).inDimensions(OVERWORLD, JAMD_MINING).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("iron")).withMaterial(Iron).withAmountPerChunk(16).atHeight(16, 61).inDimensions(OVERWORLD, JAMD_MINING, NETHER, END).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("lead")).withMaterial(Lead).withAmountPerChunk(16).atHeight(16, 61).inDimensions(OVERWORLD, JAMD_MINING, NETHER, END).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("zinc")).withMaterial(Zinc).withAmountPerChunk(12).atHeight(16, 96).inDimensions(OVERWORLD, JAMD_MINING, NETHER, END).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("gold")).withMaterial(Gold).withAmountPerChunk(8).atHeight(-34, 16).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("silver")).withMaterial(Silver).withAmountPerChunk(8).atHeight(-34, 16).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("nickel")).withMaterial(Nickel).withAmountPerChunk(8).atHeight(-34, 16).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("gold_nether_end")).withMaterial(Gold).withAmountPerChunk(8).atHeight(20, 40).inDimensions(NETHER, END).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("silver_nether_end")).withMaterial(Silver).withAmountPerChunk(8).atHeight(20, 40).inDimensions(NETHER, END).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("nickel_nether_end")).withMaterial(Nickel).withAmountPerChunk(8).atHeight(20, 40).inDimensions(NETHER, END).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("lapis")).withMaterial(Lapis).withAmountPerChunk(4).atHeight(-34, 16).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("diamond")).withMaterial(Diamond).withAmountPerChunk(3).atHeight(-59, -52).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(moon).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("diamond_nether")).withMaterial(Diamond).withAmountPerChunk(3).atHeight(5, 10).inDimensions(NETHER).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("emerald")).withMaterial(Emerald).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("ruby")).withMaterial(Ruby).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("sapphire")).withMaterial(Sapphire).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("green_sapphire")).withMaterial(GreenSapphire).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("olivine")).withMaterial(Olivine).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("topaz")).withMaterial(Topaz).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("tanzanite")).withMaterial(Tanzanite).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("amethyst")).withMaterial(Amethyst).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("opal")).withMaterial(Opal).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("jade")).withMaterial(Jade).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("blue_topaz")).withMaterial(BlueTopaz).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("amber")).withMaterial(Amber).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        //event.smallOre(new SmallOreBuilder().withMaterial(FoolsRuby).withAmountPerChunk(1).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("red_garnet")).withMaterial(RedGarnet).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("yellow_garnet")).withMaterial(YellowGarnet).withAmountPerChunk(2).inDimensions(OVERWORLD, JAMD_MINING, NETHER).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("redstone")).withMaterial(Redstone).withAmountPerChunk(8).atHeight(-59, -34).inDimensions(OVERWORLD, JAMD_MINING).inDimensions(mars).inDimensions(moon).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("redstone_nether")).withMaterial(Redstone).withAmountPerChunk(8).atHeight(5, 20).inDimension(NETHER).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("chromite")).withMaterial(Chromite).withAmountPerChunk(8).atHeight(20, 50).inDimensions(END).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("platinum")).withMaterial(Platinum).withAmountPerChunk(8).atHeight(20, 40).inDimensions(END).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("iridium")).withMaterial(Iridium).withAmountPerChunk(8).atHeight(20, 40).inDimensions(END).inDimensions(mars).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("quartz")).withMaterial(Quartz).withAmountPerChunk(64).atHeight(30, 120).inDimensions(NETHER).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("saltpeter")).withMaterial(Saltpeter).withAmountPerChunk(8).atHeight(10, 60).inDimensions(NETHER).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("sulfur")).withMaterial(Sulfur).withAmountPerChunk(32).atHeight(5, 60).inDimensions(NETHER).buildMaterial());
        event.smallOre(new SmallOreBuilder(id("sulfur_overworld")).withMaterial(Sulfur).withAmountPerChunk(8).atHeight(-59, -34).inDimensions(OVERWORLD, JAMD_MINING).buildMaterial());
    }

    private static void initStoneVeins(GTWorldGenEvent ev) {
        List<ResourceKey<Level>> overworld = List.of(OVERWORLD, JAMD_MINING);
        ev.stoneLayer(new StoneLayerBuilder(id("black_granite")).withStone(BLACK_GRANITE).withWeight(1).maxY(0, SHALE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("red_granite")).withStone(RED_GRANITE).withWeight(1).maxY(0, GRANITE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("komatiite")).withStone(KOMATIITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("basalt")).withStone(BASALT).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("marble")).withStone(MARBLE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("limestone")).withStone(LIMESTONE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("green_schist")).withStone(GREEN_SCHIST).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("blue_schist")).withStone(BLUE_SCHIST).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("kimberlite")).withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("quartzite")).withStone(QUARTZITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("shale")).withStone(SHALE).withWeight(1).minY(0, BLACK_GRANITE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("slate")).withStone(SLATE).withWeight(1).minY(0, DEEPSLATE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("deepslate")).withStone(DEEPSLATE).withWeight(1).maxY(0, SLATE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("granite")).withStone(GRANITE).withWeight(1).minY(0, RED_GRANITE.getState().getBlock()).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("diorite")).withStone(DIORITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("andesite")).withStone(ANDESITE).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("tuff")).withStone(TUFF).withWeight(1).inDimensions(overworld).buildVein());
        if (GT5RConfig.GT6_ORE_GEN.get()){
            ev.stoneLayer(new StoneLayerBuilder(id("kimberlite_2")).withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).buildVein());
            /*ev.stoneLayer(new StoneLayerBuilder("deepslate_ores").withStone(STONE).withWeight(1).addOres(
                    new StoneLayerOre(Emerald, U64, -32, 0).addFilteredBiome(BiomeTags.IS_MOUNTAIN),
                    new StoneLayerOre(Diamond, U64, -64, -52).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Lapis, U12, -32, 0).addFilteredBiome(BiomeTags.IS_TAIGA).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES),
                    new StoneLayerOre(Amber, U32, 16, 24).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN),
                    new StoneLayerOre(Redstone, U16, 0, 20),
                    new StoneLayerOre(Cinnabar, U64, 0, 20).addFilteredBiome(BiomeTags.IS_TAIGA).addFilteredBiome(Biomes.BADLANDS).addFilteredBiome(Biomes.ERODED_BADLANDS),
                    new StoneLayerOre(Uraninite, U64, 0, 12).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Thorium, U64, 0, 12).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Scheelite, U64, 0, 12).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES)).buildVein());*/
            ev.stoneLayer(new StoneLayerBuilder(id("granite_ores")).withStone(GRANITE).withWeight(1).minY(0, RED_GRANITE.getState().getBlock()).inDimensions(overworld).addOres(
                    new StoneLayerOre(BlueTopaz, U64, -24, 0).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Topaz, U64, -8, 16).addFilteredBiome(Biomes.FROZEN_PEAKS).addFilteredBiome(Biomes.ICE_SPIKES)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("granite_ores_2")).withStone(GRANITE).withWeight(1).minY(0, RED_GRANITE.getState().getBlock()).inDimensions(overworld).addOres(
                    new StoneLayerOre(Apatite, U8, 32, 64),
                    new StoneLayerOre(Phosphate, U24, 36, 60),
                    new StoneLayerOre(TricalciumPhosphate, U24, 40, 56)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("diorite_ores")).withStone(DIORITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Sapphire, U64, -24, 0).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(GreenSapphire, U64, -8, 16).addFilteredBiome(BiomeTags.IS_JUNGLE),
                    new StoneLayerOre(Ruby, U64, -8, 16).addFilteredBiome(TagUtils.getBiomeTag(new ResourceLocation("is_desert"))).addFilteredBiome(Biomes.DESERT)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("diorite_ores_2")).withStone(DIORITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Garnierite, U8, 16, 48),
                    new StoneLayerOre(Pentlandite, U8, 24, 56),
                    new StoneLayerOre(Cobaltite, U8, 32, 64),
                    new StoneLayerOre(Amethyst, U64, 24, 48).addFilteredBiome(BiomeTags.IS_TAIGA)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("andesite_ores")).withStone(ANDESITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Gold, U12, -64, -32),
                    new StoneLayerOre(Gold, U8, 32, 64).addFilteredBiome(Biomes.BADLANDS).addFilteredBiome(Biomes.ERODED_BADLANDS)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("tuff_ores")).withStone(TUFF).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Magnetite, U4, -16, 84),
                    new StoneLayerOre(Hematite, U6, -16, 64),
                    new StoneLayerOre(VanadiumMagnetite, U64, -16, 16)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("black_granite_ores")).withStone(BLACK_GRANITE).withWeight(1).maxY(0, SHALE.getState().getBlock()).inDimensions(overworld).addOres(
                    new StoneLayerOre(Sheldonite, U32, -64, -48),
                    new StoneLayerOre(Sperrylite, U32, -64, -48),
                    new StoneLayerOre(Iridium, U64, -64, -56),
                    new StoneLayerOre(Emerald, U64, -40, -16)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("red_granite_ores")).withStone(RED_GRANITE).withWeight(1).maxY(0, GRANITE.getState().getBlock()).inDimensions(overworld).addOres(
                    new StoneLayerOre(Pitchblende, U32, -32, 0),
                    new StoneLayerOre(Uraninite, U32, -32, 0),
                    new StoneLayerOre(Tantalite, U16, -32, 0)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("komatiite_ores")).withStone(KOMATIITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Magnesite, U16, -54, -9),
                    new StoneLayerOre(Cinnabar, U12, -64, -19),
                    new StoneLayerOre(Redstone, U8, -54, -9),
                    new StoneLayerOre(Pyrite, U12, 5, 66)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("kimberlite_ores")).withStone(KIMBERLITE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Diamond, U48, -64, -52)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("basalt_ores")).withStone(BASALT).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Olivine, U32, -48, -16),
                    new StoneLayerOre(Uvarovite, U32, -40, -8),
                    new StoneLayerOre(Grossular, U32, -32, 0),
                    new StoneLayerOre(Chromite, U8, -16, 16)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("basalt_ores_2")).withStone(BASALT).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Bastnasite, U24, 24, 32),
                    new StoneLayerOre(Monazite, U32, 24, 32),
                    new StoneLayerOre(Manganese, U8, 16, 48)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("marble_ores")).withStone(MARBLE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Cassiterite, U16, 20, 120),
                    new StoneLayerOre(Tin, U16, 10, 100),
                    new StoneLayerOre(Sphalerite, U8 , 10, 50),
                    new StoneLayerOre(Chalcopyrite, U8 ,  0, 40),
                    new StoneLayerOre(Pyrite, U12,  0, 50)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("limestone_ores")).withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Stibnite, U24, 10, 30),
                    new StoneLayerOre(Galena, U8, 30, 120),
                    new StoneLayerOre(Lead, U16, 50, 70)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("limestone_ores_2")).withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Pyrite, U16, 0, 45),
                    new StoneLayerOre(Galena, U8, 5, 25),
                    new StoneLayerOre(Galena, U8, 80, 120),
                    new StoneLayerOre(Wulfenite, U32, -34, -19),
                    new StoneLayerOre(Powellite, U32, -29, -14),
                    new StoneLayerOre(Molybdenite, U128, -34, -14),
                    new StoneLayerOre(Tetrahedrite, U8, 40, 100),
                    new StoneLayerOre(Copper, U16, 40, 100)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("limestone_ores_3")).withStone(LIMESTONE).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Scheelite, U64, -64, -48),
                    new StoneLayerOre(Tungstate, U64, -64, -48),
                    new StoneLayerOre(YellowLimonite, U8, -48, -16),
                    new StoneLayerOre(BrownLimonite, U8, -32, 0),
                    new StoneLayerOre(Malachite, U12, -48, 0)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("green_schist_ores")).withStone(GREEN_SCHIST).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Andradite, U32, -40, 8),
                    new StoneLayerOre(Almandine, U32, -32, 0)
            ).buildVein());
            ev.stoneLayer(new StoneLayerBuilder(id("blue_schist_ores")).withStone(BLUE_SCHIST).withWeight(1).inDimensions(overworld).addOres(
                    new StoneLayerOre(Spessartine, U32, -40, 8),
                    new StoneLayerOre(Pyrope, U32, -32, 0)
            ).buildVein());
            if (GTAPI.isModLoaded(MOD_AE)){
                ev.stoneLayer(new StoneLayerBuilder(id("quartzite_ores")).withStone(QUARTZITE).withWeight(1).inDimensions(overworld).addOres(
                        new StoneLayerOre(CertusQuartz, U16, 16, 48),
                        new StoneLayerOre(MilkyQuartz, U16, 16, 48),
                        new StoneLayerOre(Barite, U32, 0, 32)
                ).buildVein());
            } else {
                ev.stoneLayer(new StoneLayerBuilder(id("quartzite_ores")).withStone(QUARTZITE).withWeight(1).inDimensions(overworld).addOres(
                        new StoneLayerOre(MilkyQuartz, U16, 16, 48),
                        new StoneLayerOre(Barite, U32, 0, 32)
                ).buildVein());
            }

            ev.addCollisionBothSides(ORE_STONE.get().get(Coal).asState(), SLATE.getState(),
                    new StoneLayerOre(Amber, U4, 30, 70).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Amber, U8, 30, 70).addFilteredBiome(BiomeTags.IS_RIVER));
            ev.addCollisionBothSides(ORE_STONE.get().get(Lignite).asState(), SLATE.getState(),
                    new StoneLayerOre(Amber, U4, 30, 70).addFilteredBiome(BiomeTags.IS_OCEAN).addFilteredBiome(BiomeTags.IS_DEEP_OCEAN).addFilteredBiome(BiomeTags.IS_BEACH),
                    new StoneLayerOre(Amber, U8, 30, 70).addFilteredBiome(BiomeTags.IS_RIVER));
            ev.addCollisionBothSides(ORE_STONE.get().get(OilShale).asState(), SLATE.getState(),
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
        ev.stoneLayer(new StoneLayerBuilder(id("coal")).withStone(ORE_STONE.get().get(Coal).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("lignite")).withStone(ORE_STONE.get().get(Lignite).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("salt")).withStone(ORE_STONE.get().get(Salt).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("sylvite")).withStone(ORE_STONE.get().get(Sylvite).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("bauxite")).withStone(ORE_STONE.get().get(Bauxite).asState()).withWeight(1).inDimensions(overworld).buildVein());
        ev.stoneLayer(new StoneLayerBuilder(id("oil_shale")).withStone(ORE_STONE.get().get(OilShale).asState()).withWeight(1).inDimensions(overworld).buildVein());

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
        List<ResourceKey<Level>> mars = List.of(BE_MARS, AA_MARS);
        List<ResourceKey<Level>> moon = List.of(BE_MOON, AA_MOON);

        ev.vein(new VeinBuilder(id("gold")).asOreVein(-4, 26, 160, 3, 32, Magnetite, Magnetite, VanadiumMagnetite, Gold)
                .inDimensions(moon).inDimensions(mars).inDimensions(overworld).buildVein());
        ev.vein(new VeinBuilder(id("iron")).asOreVein(-14, 51, 120, 4, 24, BrownLimonite, YellowLimonite, Hematite, Malachite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("cassiterite")).asOreVein(6, 126, 50, 5, 24, Tin, Tin, Cassiterite, Tin)
                .inDimensions(overworld).inDimension(END).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("tetrahedrite")).asOreVein(51, 131, 70, 4, 24, Tetrahedrite, Tetrahedrite, Copper, Stibnite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(moon).inDimensions(mars).buildVein());
        Material sporadic = !GTLibConfig.STONE_LAYERS.get() ? Calcite : Alumina;
        ev.vein(new VeinBuilder(id("magnetite")).asOreVein(-14, 91, 160, 3, 32, Magnetite, Magnetite, Iron, VanadiumMagnetite)
                .inDimensions(overworld).inDimension(NETHER).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("copper_nether")).asOreVein(10, 30, 80, 4, 24, Chalcopyrite, Iron, Pyrite, Copper)
                .inDimension(NETHER).buildVein());
        ev.vein(new VeinBuilder(id("copper")).asOreVein(-30, 0, 80, 4, 24, Chalcopyrite, Iron, Pyrite, Copper)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("salts")).asOreVein(51, 66, 50, 3, 24, Sylvite, Salt, Lepidolite, Spodumene)
                .inDimensions(overworld).inDimensions(moon).buildVein());
        ev.vein(new VeinBuilder(id("redstone")).asOreVein(-54, -9, 60, 3, 24, Redstone, Redstone, Ruby, Cinnabar)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("pitchblend")).asOreVein(-54, -9, 20, 3, 20, Pitchblende, Pitchblende, Uraninite, Uraninite)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("soapstone")).asOreVein(-54, -9, 40, 3, 16, Soapstone, Talc, Glauconite, Pentlandite)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("nickel")).asOreVein(11, 56, 40, 3, 16, Garnierite, Nickel, Cobaltite, Pentlandite)
                .inDimensions(overworld).inDimensions(NETHER, END).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("platinum")).asOreVein(-24, -9, 5, 3, 16, Sheldonite, Sperrylite, Platinum, Iridium)
                .inDimensions(overworld).inDimensions(mars).buildVein());
        /*ev.vein(new VeinBuilder("uranium").asOreVein(-44, -29, 20, 3, 16, Uraninite, Uraninite, Uraninite, Uraninite)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());*/
        ev.vein(new VeinBuilder(id("monazite")).asOreVein(-44, -14, 30, 3, 16, Bastnasite, Bastnasite, Monazite, Neodymium)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("molybdenum")).asOreVein(-44, 1, 5, 3, 16, Wulfenite, Molybdenite, Molybdenum, Powellite)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("tungstate")).asOreVein(-44, 1, 10, 3, 16, Scheelite, Scheelite, Tungstate, Lithium)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("sapphire")).asOreVein(-54, -9, 60, 3, 16, Almandine, Pyrope, Sapphire, GreenSapphire)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("manganese")).asOreVein(-44, -29, 20, 3, 16, Grossular, Spessartine, Pyrolusite, Tantalite)
                .inDimensions(overworld).inDimensions(moon).buildVein());
        Material third = CertusQuartz.enabled ? CertusQuartz : Barite;
        ev.vein(new VeinBuilder(id("quartz")).asOreVein(6, 66, 60, 3, 16, MilkyQuartz, Barite, third, third)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("diamond")).asOreVein(-59, -48, 40, 2, 16, Graphite, Graphite, Diamond, Coal)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("diamond_nether")).asOreVein(5, 10, 40, 2, 16, Graphite, Graphite, Diamond, Coal)
                .inDimension(NETHER).buildVein());
        ev.vein(new VeinBuilder(id("olivine")).asOreVein(-54, -9, 60, 3, 16, Bentonite, Magnesite, Olivine, Glauconite)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("apatite")).asOreVein(-4, 41, 60, 3, 16, Apatite, Apatite, TricalciumPhosphate, Phosphate)
                .inDimensions(overworld).buildVein());
        ev.vein(new VeinBuilder(id("galena")).asOreVein(6, 51, 40, 5, 16, Galena, Galena, Silver, Lead)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("lapis")).asOreVein(-44, 1, 40, 5, 16, Lazurite, Sodalite, Lapis, sporadic)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
        ev.vein(new VeinBuilder(id("beryllium")).asOreVein(-59, -21, 30, 3, 16, Beryllium, Beryllium, Emerald, Thorium)
                .inDimensions(overworld).inDimensions(moon).inDimensions(mars).buildVein());
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
        if (!GTAPI.isModLoaded(MOD_TFC) && !GT5RConfig.DEFAULT_STONE_LAYERS.get()){
            overworldTFCStoneLayers.add(OVERWORLD);
        }
        if (!GT5RConfig.DEFAULT_STONE_LAYERS.get()) {
            overworldTFCStoneLayers.add(JAMD_MINING);
        }
        if (!overworldTFCStoneLayers.isEmpty()){
            ev.vein(new VeinBuilder(id("bauxite")).asOreVein(-14, 46, 80, 4, 24, Bauxite, Bauxite, Alumina, Ilmenite)
                    .inDimensions(overworldTFCStoneLayers).inDimensions(moon).inDimensions(mars).buildVein());
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
