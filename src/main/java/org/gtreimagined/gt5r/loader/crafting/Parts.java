package org.gtreimagined.gt5r.loader.crafting;

import com.google.common.collect.ImmutableMap;
import com.gtnewhorizon.structurelib.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.ToolTypes;
import org.gtreimagined.gt5r.integration.tfc.TFCRegistrar;
import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreCables;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.ForgeTags;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.item.ItemBasic;
import org.gtreimagined.gtlib.item.ItemCover;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.pipe.PipeSize;
import org.gtreimagined.gtlib.recipe.ingredient.PropertyIngredient;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.GT5RItems.*;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.TierMaps.*;
import static org.gtreimagined.gtcore.data.GTCoreItems.*;
import static org.gtreimagined.gtcore.data.GTCoreTags.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.machine.Tier.*;

public class Parts {
  public static void loadRecipes(Consumer<FinishedRecipe> output, GTRecipeProvider provider) {
      tieredItems(output, provider);
      molds(output, provider);
      provider.shapeless(output, "nether_quartz_from_milky_quartz","parts", new ItemStack(Items.QUARTZ), GEM.getMaterialTag(MilkyQuartz));
      if (!GTAPI.isModLoaded(Ref.MOD_TFC)) {
          graniteTools(output, provider);
          provider.shapeless(output, "fire_clay_dust", "parts", GTMaterialTypes.DUST.get(Fireclay, 2),
                  GTMaterialTypes.DUST.getMaterialTag(Brick), GTMaterialTypes.DUST.getMaterialTag(Clay));
      }

      provider.addItemRecipe(output, "parts", Guide,
              of('B', Items.BOOK, 'C', CIRCUITS_BASIC), "BC");

      provider.addItemRecipe(output, GT5Reimagined.ID, "", "buckets", WOODEN_BUCKET,
              of('W', ItemTags.PLANKS, 'P', PLATE.getMaterialTag(Copper), 'H', HAMMER.getTag()), "WPW", " WH");

      provider.addStackRecipe(output, GT5Reimagined.ID, "drain_expensive", "parts",
              GT5RCovers.COVER_DRAIN.getItem(), of('A', PLATES_IRON_ALUMINIUM, 'B', Items.IRON_BARS, 'W', WRENCH.getTag()), "ABA", "BWB", "ABA");

      provider.addItemRecipe(output, "gtparts", SELECTOR_TAG_ITEMS.get(0),
              of('G', SMALL_GEAR.getMaterialTag(TFCRegistrar.getIron()), 'R', ROD.getMaterialTag(TFCRegistrar.getIron()), 'W', WRENCH.getTag(), 'H', HAMMER.getTag()), "GHG", "RRR", "GWG");

      provider.shapeless(output, GT5Reimagined.ID, "", "carbon", new ItemStack(CarbonMesh), CarbonFibre, CarbonFibre);
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "carbon", CoalBall,
              of('F', Items.FLINT, 'C', DUST.getMaterialTag(Coal)), "CCC", "CFC", "CCC");
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "carbon", CoalChunk,
              of('F', Items.OBSIDIAN, 'C', CompressedCoalBall), "CCC", "CFC", "CCC");
      provider.addItemRecipe(output, GT5Reimagined.ID, "","batteries", BatteryHullSmall, of(
              'P', PLATE.get(BatteryAlloy),
              'C', CABLE_GETTER.apply(PipeSize.VTINY, LV, false)
      ), "C", "P", "P");

      provider.addItemRecipe(output,  GT5Reimagined.ID, "","batteries", BatteryHullMedium, of(
              'P', PLATE.get(BatteryAlloy),
              'C', CABLE_GETTER.apply(PipeSize.VTINY, MV, false)
      ), "C C", "PPP", "PPP");
      provider.addStackRecipe(output, GT5Reimagined.ID, "", "batteries", DUST.get(Energium, 9),
              of('R', DUST.getMaterialTag(Redstone), 'r', DUST.getMaterialTag(Ruby)), "RrR", "rRr", "RrR");

      provider.addItemRecipe(output, GT5Reimagined.ID, "front_rotation_tool", "gtparts", Registry.FRONT_ROTATION_TOOL,
              of('R', ROD.getMaterialTag(Wood),
                      'C', ROD.getMaterialTag(Cobalt),
                      'P', PLATE.getMaterialTag(Cobalt),
                      'H', HAMMER.getTag(),
                      'F', FILE.getTag()), "FPC", " CP", "R H");


      provider.addItemRecipe(output, GT5Reimagined.ID, "diamondsaw_blade", "gtparts", DiamondSawBlade, of(
              'G', GEAR.get(CobaltBrass),
              'D', SMALL_DUST.get(Diamond)
      ), " D ", "DGD", " D ");

      provider.addItemRecipe(output, "mining_pipes", GT5RBlocks.MINING_PIPE_THIN,
              of('H', HAMMER.getTag(), 'P', GT5RBlocks.FLUID_PIPE_STEEL.getBlockItem(PipeSize.SMALL), 'F', FILE.getTag()), "HPF");
      provider.addStackRecipe(output, GT5Reimagined.ID, "", "matches", new ItemStack(Match, 4), of('P', DUST.getMaterialTag(Phosphor), 'S', ROD.getMaterialTag(Wood)), "P", "S");
      provider.shapeless(output, GT5Reimagined.ID, "tape_from_empty", "tapes", new ItemStack(Tape), TapeEmpty, TapeEmpty, TapeEmpty, TapeEmpty);
      provider.shapeless(output, GT5Reimagined.ID, "duct_tape_from_empty", "tapes", new ItemStack(DuctTape), DuctTapeEmpty, DuctTapeEmpty, DuctTapeEmpty, DuctTapeEmpty);
      provider.shapeless(output, GT5Reimagined.ID, "fal_duct_tape_from_empty", "tapes", new ItemStack(FALDuctTape), FALDuctTapeEmpty, FALDuctTapeEmpty, FALDuctTapeEmpty, FALDuctTapeEmpty);
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "tapes", Tape, of('P', Items.PAPER, 'G', Glue.getLiquid().getBucket()), "PPP", " G ");
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "tapes", DuctTape, of('P', FOIL.getMaterialTag(Plastic), 'G', Glue.getLiquid().getBucket()), "PPP", " G ");
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "tapes", FALDuctTape, of('P', FOIL.getMaterialTag(Tungsten), 'G', Glue.getLiquid().getBucket()), "PPP", " G ");
      provider.shapeless(output, GT5Reimagined.ID, "data_stick_clearing", "data_sticks", new ItemStack(GT5RItems.DataStick), GT5RItems.DataStick);
      provider.shapeless(output, GT5Reimagined.ID, "fluid_filter_reset", "filters", GT5RCovers.COVER_FLUID_FILTER.getItem(), GT5RCovers.COVER_FLUID_FILTER.getItem().getItem());
      provider.shapeless(output, GT5Reimagined.ID, "item_filter_reset", "filters", GT5RCovers.COVER_ITEM_FILTER.getItem(), GT5RCovers.COVER_ITEM_FILTER.getItem().getItem());
      provider.shapeless(output, GT5Reimagined.ID, "item_retriever_reset", "filters", GT5RCovers.COVER_ITEM_RETRIEVER.getItem(), GT5RCovers.COVER_ITEM_RETRIEVER.getItem().getItem());
      provider.addItemRecipe(output, "covers", GT5RCovers.COVER_PROGRESS_SENSOR.getItem().getItem(), of('W', CABLE_GETTER.apply(PipeSize.VTINY, LV, false), 'A', PLATE.getMaterialTag(Aluminium), 'G', SMALL_GEAR.getMaterialTag(Brass), 'C', CIRCUITS_GOOD), "WAW", "GCG");
      provider.addItemRecipe(output, "covers", GT5RCovers.COVER_REDSTONE_CONDUCTOR_ACCEPT.getItem().getItem(), of('W', GTCoreCables.WIRE_RED_ALLOY.getBlock(PipeSize.VTINY), 'A', PLATE.getMaterialTag(Aluminium)), "W", "A");
      provider.addItemRecipe(output, "covers", GT5RCovers.COVER_REDSTONE_CONDUCTOR_EMIT.getItem().getItem(), of('W', GTCoreCables.WIRE_RED_ALLOY.getBlock(PipeSize.VTINY), 'A', PLATE.getMaterialTag(Aluminium)), "A", "W");
      provider.shapeless(output, GT5Reimagined.ID, "redstone_conductor_accept_conversion", "covers", GT5RCovers.COVER_REDSTONE_CONDUCTOR_EMIT.getItem(), GT5RCovers.COVER_REDSTONE_CONDUCTOR_ACCEPT.getItem().getItem());
      provider.shapeless(output, GT5Reimagined.ID, "redstone_conductor_emit_conversion", "covers", GT5RCovers.COVER_REDSTONE_CONDUCTOR_ACCEPT.getItem(), GT5RCovers.COVER_REDSTONE_CONDUCTOR_EMIT.getItem().getItem());
      provider.addItemRecipe(output, "covers", GT5RCovers.COVER_ITEM_RETRIEVER.getItem().getItem(),
              of('C', CIRCUITS_ADVANCED, 'F', GT5RCovers.COVER_ITEM_FILTER.getItem().getItem(), 'E', PLATE.getMaterialTag(Electrum), 'P', PistonLV), "EPE", "CFC");
      provider.addItemRecipe(output, "misc", DiamondGrindHead, of('D', DUST.getMaterialTag(Diamond), 'G', GEM.getMaterialTag(Diamond), 'S', PLATE.getMaterialTag(Steel)), "DSD", "SGS", "DSD");
      provider.addItemRecipe(output, "misc", TungstenGrindHead, of('D', PLATE.getMaterialTag(Tungsten), 'G', GEM.getMaterialTag(Diamond), 'S', PLATE.getMaterialTag(Steel)), "DSD", "SGS", "DSD");
      provider.addItemRecipe(output, "hazmat", UniversalHazardSuitMask, of('L', PLATE.getMaterialTag(Lead), 'A', PLATE.getMaterialTag(Aluminium), 'C', Items.CHAINMAIL_HELMET, 'G', Items.GLASS_PANE), "ALA", "LCL", "AGA");
      provider.addItemRecipe(output, "hazmat", UniversalHazardSuitShirt, of('L', PLATE.getMaterialTag(Lead), 'A', PLATE.getMaterialTag(Aluminium), 'C', Items.CHAINMAIL_CHESTPLATE), "ALA", "LCL", "ALA");
      provider.addItemRecipe(output, "hazmat", UniversalHazardSuitPants, of('L', PLATE.getMaterialTag(Lead), 'A', PLATE.getMaterialTag(Aluminium), 'C', Items.CHAINMAIL_LEGGINGS), "ALA", "LCL", "ALA");
      provider.addItemRecipe(output, "hazmat", UniversalHazardSuitBoots, of('L', PLATE.getMaterialTag(Lead), 'A', PLATE.getMaterialTag(Aluminium), 'C', Items.CHAINMAIL_BOOTS), "ALA", "LCL", "ALA");
      provider.addItemRecipe(output, "misc", EmptyGeigerCounter,
              of('S', SCREW.getMaterialTag(Aluminium), 'P', PLATE.getMaterialTag(Aluminium), 'C', CellTin, 'c', TIER_CIRCUITS.apply(LV), 's', SCREWDRIVER.getTag()), "SCS", "PcP", "SsS");
      provider.addToolRecipe(ToolTypes.SCANNER_BUILDER.get("portable-scanner"), output, GT5Reimagined.ID, "scanner", "misc", new ItemStack(GT5RItems.PortableScanner),
              of('E', EmitterHV, 'A', PLATE.getMaterialTag(Aluminium), 'S', SensorHV, 'C', CIRCUITS_ADVANCED, 'c', ComputerMonitor, 'B',  PropertyIngredient.builder("battery").itemStacks(BatteryMediumLithium).build()), "EAS", "CcC", "ABA");
      provider.addItemRecipe(output, "misc", ComputerMonitor,
              of('A', PLATE.getMaterialTag(Aluminium), 'P', PLATE.getMaterialTag(Glass), 'g', ForgeTags.DYES_GREEN, 'b', ForgeTags.DYES_BLUE, 'r', ForgeTags.DYES_RED, 'G', DUST.getMaterialTag(Glowstone)), "AgA", "rPb", "AGA");
  }

  private static void tieredItems(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
      Arrays.stream(Tier.getStandardWithIV()).forEach(t -> {
          Material magnet = (t == Tier.ULV || t == LV) ? IronMagnetic
                  : (t == Tier.EV || t == Tier.IV ? NeodymiumMagnetic : SteelMagnetic);
          Object cable = CABLE_GETTER.apply(PipeSize.VTINY, t, false);
          Material mat = TIER_MATERIALS.get(t);
          if (mat == null) return;
          TagKey<Item> smallGear = SMALL_GEAR.getMaterialTag(mat);
          TagKey<Item> plate = PLATE.getMaterialTag(mat);
          TagKey<Item> rod = ROD.getMaterialTag(mat);
          TagKey<Item> circuit = TIER_CIRCUITS.apply(t);

          Item motor = GTAPI.get(ItemBasic.class, "motor_" + t.getId(), GTCore.ID);
          Item piston = GT5Reimagined.get(ItemBasic.class, "piston_" + t.getId());
          Item robotArm = GT5Reimagined.get(ItemCover.class, "robot_arm_" + t.getId());
          Item emitter = GT5Reimagined.get(ItemBasic.class, "emitter_" + t.getId());
          Item sensor = GT5Reimagined.get(ItemBasic.class, "sensor_" + t.getId());
          Item pump = GT5Reimagined.get(ItemCover.class, "pump_" + t.getId());
          Item conveyor = GT5Reimagined.get(ItemCover.class, "conveyor_" + t.getId());
          Item fieldGen = GT5Reimagined.get(ItemBasic.class, "field_gen_" + t.getId());
          Object emitterRod = ROD.getMaterialTag(EMITTER_RODS.get(t));
          Object wire = t == EV || t == IV ? GT5RBlocks.WIRE_ANNEALED_COPPER.getBlock(fromTier(t)) : WIRE_GETTER.apply(fromTier(t), LV);
          Object motorRod = t == LV ? RecipeIngredient.ofIngredient(1, rod, ROD.getMaterialTag(TFCRegistrar.getIron())) : rod;
          provider.addItemRecipe(output, "gtparts", motor,
                  of('M', ROD.get(magnet), 'C', cable, 'W', wire, 'R', motorRod), "CWR", "WMW", "RWC");
          provider.addItemRecipe(output, "gtparts", piston,
                  of('M', motor, 'C', cable, 'G', smallGear, 'P', plate, 'R', rod), "PPP", "CRR", "CMG");
          provider.addItemRecipe(output, "gtparts", conveyor,
                  of('M', motor, 'C', cable, 'P', PLATE.get(Rubber)), "PPP", "MCM", "PPP");
          provider.addItemRecipe(output, "gtparts", robotArm,
                  of('M', motor, 'C', cable, 'P', piston, 'I', circuit, 'R', rod), "CCC", "MRM", "PIR");
          provider.addItemRecipe(output, "gtparts", emitter,
                  of('R', emitterRod, 'G', ForgeTags.GEMS_QUARTZ_ALL, 'L', cable, 'C', circuit), "RRC", "LGR", "CLR");
          provider.addItemRecipe(output, "gtparts", sensor,
                  of('R', emitterRod, 'G', ForgeTags.GEMS_QUARTZ_ALL, 'C', circuit, 'P', plate), "P G", "PR ", "CPP");
          PipeSize osmium = t == IV ? PipeSize.HUGE : PipeSize.values()[t.getIntegerId() - 1];
          provider.addItemRecipe(output, "gtparts", fieldGen,
                  of('O', GT5RBlocks.WIRE_OSMIUM.getBlockItem(osmium), 'C', circuit, 'G', LONG_ROD.getMaterialTag(NeodymiumMagnetic)), "OCO", "CGC", "OCO");
          Material rotorMat = TIER_ROTORS.get(t);
          provider.addItemRecipe(output, "gtparts", pump,
                  ImmutableMap.<Character, Object>builder().put('M', motor).put('C', cable).put('W', WRENCH.getTag())
                          .put('S', SCREWDRIVER.getTag()).put('R', SCREW.getMaterialTag(rotorMat)).put('T', ROTOR.getMaterialTag(rotorMat))
                          .put('O', RING.get(Rubber)).put('P', PIPE_GETTER.apply(PipeSize.NORMAL, t))
                          .build(),
                  "RTO", "SPW", "OMC");
      });
  }

  private static void molds(Consumer<FinishedRecipe> output, GTRecipeProvider provider){
      provider.addItemRecipe(output, GT5Reimagined.ID, "empty_shape", "gtparts", EmptyShape, of(
              'P', PLATE.get(Steel),
              'H', HAMMER.getTag(),
              'F', FILE.getTag()
      ), "HF","PP", "PP");
      moldRecipe(output, provider, PlateMold, "H", "P");
      moldRecipe(output, provider, IngotMold, "P", "H");
      moldRecipe(output, provider, CasingMold, " H", "P ");
      moldRecipe(output, provider, GearMold, "PH");
      moldRecipe(output, provider, CoinageMold, "H ", " P");
      moldRecipe(output, provider, BottleMold, "P ", " H");
      moldRecipe(output, provider, BallMold, " P", "H ");
      moldRecipe(output, provider, BlockMold, "HP");
      moldRecipe(output, provider, NuggetMold, "P H");
      //moldRecipe(output, provider, MoldBuns, "P  ", "  H");
      //moldRecipe(output, provider, MoldBread, "P  ", "   ", "  H");
      //moldRecipe(output, provider, MoldBaguettes, "P ", "  ", " H");
      moldRecipe(output, provider, AnvilMold, " P", "  ", "H ");
      moldRecipe(output, provider, SmallGearMold, "H P");
      moldRecipe(output, provider, LongRodMold, "  H", "P  ");

      shapeRecipe(output, provider,FoilShape,PlateShape, "H ", " P");
      shapeRecipe(output, provider,RodShape,LongRodShape, " H", "P ");
      shapeRecipe(output, provider,RodShape, "PH");
      shapeRecipe(output, provider,RodShape,BoltShape, "H ", " P");
      shapeRecipe(output, provider,RingShape, "P", "H");
      shapeRecipe(output, provider,RingShape,CellShape, "PH");
      shapeRecipe(output, provider,IngotShape, "H ", " P");
      shapeRecipe(output, provider,RodShape,WireShape, "H", "P");
      shapeRecipe(output, provider,FoilShape,CasingShape, "H", "P");
      shapeRecipe(output, provider,TinyPipeShape, " H", "  ", "P ");
      shapeRecipe(output, provider,SmallPipeShape, "P  ", "  H");
      shapeRecipe(output, provider,NormalPipeShape, "P ", "  ", " H");
      shapeRecipe(output, provider,LargePipeShape, "P  ", "   ", "  H");
      shapeRecipe(output, provider,HugePipeShape, "  H", "   ", "P  ");
      shapeRecipe(output, provider,IngotShape,BlockShape, "H ", " P");
      shapeRecipe(output, provider,TinyPlateShape,SwordBladeShape, "PH");
      shapeRecipe(output, provider,IngotShape,PickaxeHeadShape, "H", "P");
      shapeRecipe(output, provider,TinyPlateShape,ShovelHeadShape, "H", "P");
      shapeRecipe(output, provider,TinyPlateShape,AxeHeadShape, "H ", " P");
      shapeRecipe(output, provider,IngotShape,HoeHeadShape, "PH");
      shapeRecipe(output, provider,IngotShape,HammerHeadShape, " H", "P ");
      shapeRecipe(output, provider,TinyPlateShape,FileHeadShape, " H", "P ");
      shapeRecipe(output, provider,TinyPlateShape,SawBladeShape, "P ", " H");
      shapeRecipe(output, provider,RingShape,GearShape, "H ", " P");
      shapeRecipe(output, provider,RingShape,BottleShape, " H", "P ");
      shapeRecipe(output, provider,RingShape,SmallGearShape, "H", "P");
      shapeRecipe(output, provider,FoilShape, "P ", " H");
      shapeRecipe(output, provider,TinyPlateShape, "H", "P");
      shapeRecipe(output, provider,RodShape,FineWireShape, "PH");
  }

  private static void graniteTools(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_pickaxe", "tools", PICKAXE.getToolItem(RedGranite), ImmutableMap.of('R', ROCK.getMaterialTag(RedGranite), 'S', Items.STICK), "RRR", " S ");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_axe", "tools", AXE.getToolItem(RedGranite), ImmutableMap.of('R', ROCK.getMaterialTag(RedGranite), 'S', Items.STICK), "RR", "RS");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_shovel", "tools", SHOVEL.getToolItem(RedGranite), ImmutableMap.of('R', ROCK.getMaterialTag(RedGranite), 'S', Items.STICK), "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_hoe", "tools", HOE.getToolItem(RedGranite), ImmutableMap.of('R', ROCK.getMaterialTag(RedGranite), 'S', Items.STICK), "RR", " S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_sword", "tools", SWORD.getToolItem(RedGranite), ImmutableMap.of('R', ROCK.getMaterialTag(RedGranite), 'S', Items.STICK), "R", "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_pickaxe", "tools", PICKAXE.getToolItem(BlackGranite), ImmutableMap.of('R', ROCK.getMaterialTag(BlackGranite), 'S', Items.STICK), "RRR", " S ");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_axe", "tools", AXE.getToolItem(BlackGranite), ImmutableMap.of('R', ROCK.getMaterialTag(BlackGranite), 'S', Items.STICK), "RR", "RS");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_shovel", "tools", SHOVEL.getToolItem(BlackGranite), ImmutableMap.of('R', ROCK.getMaterialTag(BlackGranite), 'S', Items.STICK), "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_hoe", "tools", HOE.getToolItem(BlackGranite), ImmutableMap.of('R', ROCK.getMaterialTag(BlackGranite), 'S', Items.STICK), "RR", " S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_sword", "tools", SWORD.getToolItem(BlackGranite), ImmutableMap.of('R', ROCK.getMaterialTag(BlackGranite), 'S', Items.STICK), "R", "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_pickaxe_cobble", "tools", PICKAXE.getToolItem(RedGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.RED_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RRR", " S ");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_axe_cobble", "tools", AXE.getToolItem(RedGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.RED_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RR", "RS");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_shovel_cobble", "tools", SHOVEL.getToolItem(RedGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.RED_GRANITE).getBlock("cobble"), 'S', Items.STICK), "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_hoe_cobble", "tools", HOE.getToolItem(RedGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.RED_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RR", " S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "red_granite_sword_cobble", "tools", SWORD.getToolItem(RedGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.RED_GRANITE).getBlock("cobble"), 'S', Items.STICK), "R", "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_pickaxe_cobble", "tools", PICKAXE.getToolItem(BlackGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.BLACK_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RRR", " S ");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_axe_cobble", "tools", AXE.getToolItem(BlackGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.BLACK_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RR", "RS");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_shovel_cobble", "tools", SHOVEL.getToolItem(BlackGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.BLACK_GRANITE).getBlock("cobble"), 'S', Items.STICK), "R", "S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_hoe_cobble", "tools", HOE.getToolItem(BlackGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.BLACK_GRANITE).getBlock("cobble"), 'S', Items.STICK), "RR", " S");
      provider.addItemRecipe(consumer, GT5Reimagined.ID, "black_granite_sword_cobble", "tools", SWORD.getToolItem(BlackGranite), ImmutableMap.of('R', ((CobbleStoneType)GTCoreBlocks.BLACK_GRANITE).getBlock("cobble"), 'S', Items.STICK), "R", "R", "S");
  }

  private static void moldRecipe(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Item mold, String... shapes){
      provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtparts", mold,
              of('P', EmptyShape, 'H', HAMMER.getTag()), shapes);
  }

    private static void shapeRecipe(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Item inputMold, Item mold, String... shapes){
        provider.addItemRecipe(output, GT5Reimagined.ID, "", "gtparts", mold,
                of('P', inputMold, 'H', WIRE_CUTTER.getTag()), shapes);
    }

    private static void shapeRecipe(Consumer<FinishedRecipe> output, GTRecipeProvider provider, Item mold, String... shapes){
        shapeRecipe(output, provider, EmptyShape, mold, shapes);
    }

  public static PipeSize fromTier(Tier tier){
      if (tier == LV) return PipeSize.VTINY;
      if (tier == MV) return PipeSize.TINY;
      if (tier == HV) return PipeSize.SMALL;
      if (tier == IV) return PipeSize.HUGE;
      return PipeSize.NORMAL;
  }
}
