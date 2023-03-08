package muramasa.gregtech.loader.crafting;

import com.github.gregtechintergalactical.gtrubber.GTRubberData;
import com.google.common.collect.ImmutableMap;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.data.AntimatterMaterialTypes;
import muramasa.antimatter.data.AntimatterMaterials;
import muramasa.antimatter.datagen.providers.AntimatterRecipeProvider;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemCover;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialItem;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Wire;
import muramasa.gregtech.GregTech;
import muramasa.gregtech.Ref;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.TagKey;
import java.util.Arrays;
import java.util.function.Consumer;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.gregtech.data.GregTechData.*;
import static muramasa.gregtech.data.GregTechTags.PLATES_IRON_ALUMINIUM;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.TierMaps.*;

public class Parts {
  public static void loadRecipes(Consumer<FinishedRecipe> output, AntimatterRecipeProvider provider) {
    Arrays.stream(Tier.getStandard()).forEach(t -> {
      Material magnet = (t == Tier.ULV || t == Tier.LV) ? IronMagnetic
          : (t == Tier.EV || t == Tier.IV ? NeodymiumMagnetic : SteelMagnetic);
      Item cable = TIER_CABLES.get(t);
      Item wire = TIER_WIRES.get(t);
      Material mat = TIER_MATERIALS.get(t);
      // Item smallGear = GEAR_SMALL.get(mat);
      Item smallGear = AntimatterMaterialTypes.GEAR.get(mat);
      TagKey<Item> plate = AntimatterMaterialTypes.PLATE.getMaterialTag(mat);
      TagKey<Item> rod = AntimatterMaterialTypes.ROD.getMaterialTag(mat);
      Item circuit = TIER_CIRCUITS.getOrDefault(t, CircuitBasic);

      Item motor = GregTech.get(ItemBasic.class, "motor_" + t.getId());
      Item piston = GregTech.get(ItemBasic.class, "piston_" + t.getId());
      Item robotArm = GregTech.get(ItemBasic.class, "robot_arm_" + t.getId());
      Item emitter = GregTech.get(ItemBasic.class, "emitter_" + t.getId());
      Item sensor = GregTech.get(ItemBasic.class, "sensor_" + t.getId());
      Item pump = GregTech.get(ItemCover.class, "pump_" + t.getId());
      Item conveyor = GregTech.get(ItemCover.class, "conveyor_" + t.getId());
      Object emitterRod = AntimatterMaterialTypes.ROD.getMaterialTag(EMITTER_RODS.get(t));
      Object emitterGem = EMITTER_GEMS.get(t);
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), motor,
          of('M', AntimatterMaterialTypes.ROD.get(magnet), 'C', cable, 'W', wire, 'R', rod), "CWR", "WMW", "RWC");
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), piston,
          of('M', motor, 'C', cable, 'G', smallGear, 'P', plate, 'R', rod), "PPP", "CRR", "CMG");
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), conveyor,
          of('M', motor, 'C', cable, 'P', AntimatterMaterialTypes.PLATE.get(Rubber)), "PPP", "MCM", "PPP");
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), robotArm,
          of('M', motor, 'C', cable, 'P', piston, 'I', circuit, 'R', rod), "CCC", "MRM", "PIR");
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(circuit), emitter,
          of('R', emitterRod, 'G', emitterGem, 'L', cable, 'C', circuit), "RRC", "LGR", "CLR");
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(circuit), sensor,
          of('R', emitterRod, 'G', emitterGem, 'C', circuit, 'P', plate), "P G", "PR ", "CPP");
      Material rotorMat = ((MaterialItem) TIER_ROTORS.get(t)).getMaterial();
      provider.addItemRecipe(output, "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), pump,
          ImmutableMap.<Character, Object>builder().put('M', motor).put('C', cable).put('W', AntimatterDefaultTools.WRENCH.getTag())
              .put('S', AntimatterDefaultTools.SCREWDRIVER.getTag()).put('R', AntimatterMaterialTypes.SCREW.get(rotorMat)).put('T', TIER_ROTORS.get(t))
              .put('O', t == Tier.IV ? AntimatterMaterialTypes.RING.get(StyreneButadieneRubber) : AntimatterMaterialTypes.RING.get(Rubber)).put('P', TIER_PIPES.get(t))
              .build(),
          "RTO", "SPW", "OMC");
    });

      provider.addStackRecipe(output, Ref.ID, "drain_expensive", "parts", "has_battery", provider.hasSafeItem(Items.IRON_BARS),
              new ItemStack(GregTech.get(ItemCover.class, "drain"), 1), of('A', PLATES_IRON_ALUMINIUM, 'B', Items.IRON_BARS), "ABA", "B B", "ABA");

    // MANUAL TIER 0 CIRCUIT CRAFTING
    provider.addItemRecipe(output, "circuit_basic", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), CircuitBasic,
        ImmutableMap.<Character, Object>builder()
                .put('V', VacuumTube).put('B', CircuitBoardCoated)
            .put('W',
                AntimatterAPI.getOrThrow(Wire.class, "wire_" + RedAlloy.getId(),
                    () -> new RuntimeException("Missing red alloy wire")).getBlockItem(PipeSize.VTINY))
                .put('R',Resistor).put('P', AntimatterMaterialTypes.PLATE.get(Steel))
            .build(),
        "RPR", "VBV", "WWW");

    // MANUAL COATED BOARD CRAFTING
    provider.addItemRecipe(output, "board_basic", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), CircuitBoardCoated,
        ImmutableMap.<Character, Object>builder()
                .put('R', GTRubberData.StickyResin)
                .put('P', AntimatterMaterialTypes.PLATE.get(AntimatterMaterials.Wood))
            .build(),
        " R ", "PPP", " R ");

      // MANUAL VAC TUBE CRAFTING
      provider.addItemRecipe(output, "vac_tube", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), VacuumTube,
              ImmutableMap.<Character, Object>builder()
                      .put('G', Items.GLASS)
                      .put('P', Items.PAPER)
                      .put('W',
                              AntimatterAPI.getOrThrow(Wire.class, "wire_" + AntimatterMaterials.Copper.getId(),
                                      () -> new RuntimeException("Missing copper wire")).getBlockItem(PipeSize.VTINY))
                      .build(),
              "   ", "PGP", "WWW");

      // MANUAL RESISTOR CRAFTING
      provider.addItemRecipe(output, "resistor", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), Resistor,
              ImmutableMap.<Character, Object>builder()
                      .put('C', AntimatterMaterialTypes.DUST.get(AntimatterMaterials.Coal))
                      .put('P', Items.PAPER)
                      .put('W',
                              AntimatterAPI.getOrThrow(Wire.class, "wire_" + AntimatterMaterials.Copper.getId(),
                                      () -> new RuntimeException("Missing copper wire")).getBlockItem(PipeSize.VTINY))
                      .build(),
              " P ", "WCW", " P ");



    provider.shapeless(output, "int_circuit", "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()),
            INT_CIRCUITS.get(0).getItems()[0], CircuitBasic);
    // INT_CIRCUITS.forEach((k, v) -> {
    Ingredient ing = INT_CIRCUITS.get(0);
    provider.shapeless(output, "int_circuit_to_circuit", "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()),
        CircuitBasic.getDefaultInstance(), ing);
    // });
      provider.shapeless(output, "int_circuit_1", "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()),
              INT_CIRCUITS.get(0).getItems()[0], CircuitBasic);

      provider.addItemRecipe(output, Ref.ID, "small_battery_hull","batteries", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), BatteryHullSmall, of(
              'P', AntimatterMaterialTypes.PLATE.get(BatteryAlloy),
              'C', TIER_CABLES.get(Tier.LV)
      ), "C", "P", "P");

      provider.addItemRecipe(output,  Ref.ID, "medium_battery_hull","batteries", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), BatteryHullMedium, of(
              'P', AntimatterMaterialTypes.PLATE.get(BatteryAlloy),
              'C', TIER_CABLES.get(Tier.MV)
      ), "C C", "PPP", "PPP");

      provider.addItemRecipe(output, Ref.ID, "empty_shape", "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), EmptyShape, of(
              'P', AntimatterMaterialTypes.PLATE.get(Steel),
              'H', AntimatterDefaultTools.HAMMER.getTag()
      ), "PPH", "PP ");

      provider.addItemRecipe(output, Ref.ID, "diamondsaw_blade", "gtparts", "has_wrench", provider.hasSafeItem(AntimatterDefaultTools.WRENCH.getTag()), DiamondSawBlade, of(
              'G', AntimatterMaterialTypes.GEAR.get(CobaltBrass),
              'D', AntimatterMaterialTypes.DUST_SMALL.get(AntimatterMaterials.Diamond)
      ), " D ", "DGD", " D ");
  }
}
