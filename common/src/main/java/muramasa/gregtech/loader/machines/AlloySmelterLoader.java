package muramasa.gregtech.loader.machines;

import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.MaterialStack;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.gregtech.data.GT5RMaterialTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreItems;

import java.util.List;

import static muramasa.antimatter.data.AntimatterMaterialTypes.*;
import static muramasa.antimatter.data.AntimatterMaterials.*;
import static muramasa.antimatter.material.MaterialTags.METAL;
import static muramasa.antimatter.material.MaterialTags.RUBBERTOOLS;
import static muramasa.antimatter.recipe.ingredient.RecipeIngredient.of;
import static muramasa.gregtech.data.Materials.*;
import static muramasa.gregtech.data.RecipeMaps.ALLOY_SMELTER;

public class AlloySmelterLoader {

    public static void init() {
        INGOT.all().forEach(t -> {
            if (t.has(GT5RMaterialTags.NEEDS_BLAST_FURNACE)) return;
            if (!t.has(METAL)) return;
            List<MaterialStack> stacks = t.getProcessInto();
            if (stacks.size() != 2) return;
            int cumulative = t == RedAlloy ? 1 : stacks.get(0).s + stacks.get(1).s;
            MaterialStack first = stacks.get(0);
            MaterialStack second = stacks.get(1);
            ALLOY_SMELTER.RB().ii(of(DUST.getMaterialTag(first.m),first.s),of(DUST.getMaterialTag(second.m),second.s)).io(new ItemStack(INGOT.get(t),cumulative)).add(t.getId() +"_ingot",100, 16);
            boolean firstIngot = first.m.has(INGOT);
            boolean secondIngot = second.m.has(INGOT);
            if (firstIngot && secondIngot) ALLOY_SMELTER.RB().ii(of(INGOT.getMaterialTag(first.m),first.s),of(INGOT.getMaterialTag(second.m),second.s)).io(new ItemStack(INGOT.get(t),cumulative)).add(t.getId() +"_ingot_2",100, 12);
            if (firstIngot) ALLOY_SMELTER.RB().ii(of(INGOT.getMaterialTag(first.m),first.s),of(DUST.getMaterialTag(second.m),second.s)).io(new ItemStack(INGOT.get(t),cumulative)).add(t.getId() +"_ingot_3",100, 12);
            if (secondIngot) ALLOY_SMELTER.RB().ii(of(DUST.getMaterialTag(first.m),first.s),of(INGOT.getMaterialTag(second.m),second.s)).io(new ItemStack(INGOT.get(t),cumulative)).add(t.getId() +"_ingot_4",100, 12);
        });
        addAlloyRecipes(Copper, 3,Electrum, 2, BlackBronze, 5);
        addAlloyRecipes(AnnealedCopper, 3,Electrum, 2, BlackBronze, 5);
        addAlloyRecipes(AnnealedCopper, 3, Tin, 1, Bronze, 4);
        addAlloyRecipes(AnnealedCopper, 3, Zinc, 1, Brass, 4);
        addAlloyRecipes(AnnealedCopper, 1, Silver, 4, SterlingSilver, 5);
        addAlloyRecipes(AnnealedCopper, 1, Gold, 4, RoseGold, 5);
        addAlloyRecipes(AnnealedCopper, 1, Nickel, 1, Cupronickel, 2);
        addAlloyRecipes(AnnealedCopper, 1, Redstone, 4, RedAlloy, 1);
        addAlloyRecipes(Bismuth, 1, Brass, 4, BismuthBronze, 5);
        //pre Chemical Reactor Rubber
        ALLOY_SMELTER.RB().ii(of(DUST.get(RawRubber), 3), of(DUST.getMaterialTag(Sulfur), 1))
                .io(INGOT.get(Rubber, 1)).add("rubber_via_alloy_smelter",20, 10);
        PLATE.all().stream().filter(m -> !m.has(GT5RMaterialTags.NEEDS_BLAST_FURNACE) && m.has(INGOT)).forEach(m ->{
            int euPerTick = m.has(RUBBERTOOLS) ? 8 : 32;
            ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(m, 2), RecipeIngredient.of(GTCoreItems.MoldPlate, 1).setNoConsume()).io(PLATE.get(m, 1)).add(m.getId() + "_plate", m.getMass() * 2, euPerTick);
            if (m.has(RUBBERTOOLS)) {
                ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(m, 2), RecipeIngredient.of(GTCoreItems.MoldPlate, 1).setNoConsume()).io(PLATE.get(m, 1)).add(m.getId() + "_plate_from_dust", m.getMass() * 2, euPerTick);
            }
        });
        INGOT.all().stream().filter(m -> !m.has(GT5RMaterialTags.NEEDS_BLAST_FURNACE)).forEach(m -> { //TODO other ingot recipes
            if (m.has(NUGGET)){
                ALLOY_SMELTER.RB().ii(NUGGET.getMaterialIngredient(m, 9), RecipeIngredient.of(GTCoreItems.MoldIngot, 1).setNoConsume()).io(INGOT.get(m, 1)).add(m.getId() + "_ingot_from_nugget", 200, 2);
            }
            if (m.has(RUBBERTOOLS)) {
                ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(m, 1), RecipeIngredient.of(GTCoreItems.MoldIngot, 1).setNoConsume()).io(INGOT.get(m, 1)).add(m.getId() + "_ingot_from_dust", m.getMass(), 10);
            }
        });
        ITEM_CASING.all().forEach(m -> {
            ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(m, 2), of(GTCoreItems.MoldCasing, 1).setNoConsume()).io(ITEM_CASING.get(m, 3)).add(m.getId() + "_item_casing", Math.max(m.getMass() * 2 / 3, 1), 16);
        });
        GEAR.all().stream().filter(m -> !m.has(GT5RMaterialTags.NEEDS_BLAST_FURNACE) && m.has(INGOT)).forEach(m ->{
            int euPerTick = m.has(RUBBERTOOLS) ? 8 : 32;
            ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(m, 8), RecipeIngredient.of(GTCoreItems.MoldGear, 1).setNoConsume()).io(GEAR.get(m, 1)).add(m.getId() + "_gear", m.getMass() * 8, euPerTick);
            if (m.has(RUBBERTOOLS)) {
                ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(m, 8), RecipeIngredient.of(GTCoreItems.MoldGear, 1).setNoConsume()).io(GEAR.get(m, 1)).add(m.getId() + "_gear_from_dust", m.getMass() * 8, euPerTick);
            }
        });
        ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(Glass, 1), RecipeIngredient.of(GTCoreItems.MoldBall, 1).setNoConsume()).io(GTCoreItems.GlassTube).add("glass_tube", 160, 8);
        ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(Glass, 1), RecipeIngredient.of(GTCoreItems.MoldBottle, 1).setNoConsume()).io(Items.GLASS_BOTTLE).add("glass_bottle", 64, 4);
        ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(Iron, 31), RecipeIngredient.of(GTCoreItems.MoldAnvil, 1).setNoConsume()).io(Items.ANVIL).add("anvil", 512, 64);
    }

    private static void addAlloyRecipes(Material input1, int count1, Material input2, int count2, Material output, int countO){
        String suffix = input1 == AnnealedCopper ? "_annealed" : "";
        if (input1.has(INGOT) && input2.has(INGOT)) {
            ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(input1, count1), INGOT.getMaterialIngredient(input2, count2)).io(INGOT.get(output, countO)).add(output.getId() + "_ingot" + suffix, 100, 12);
        }
        if (input2.has(INGOT)) {
            ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(input1, count1), INGOT.getMaterialIngredient(input2, count2)).io(INGOT.get(output, countO)).add(output.getId() + "_ingot_1" + suffix, 100, 12);
        }
        if (input1.has(INGOT)) {
            ALLOY_SMELTER.RB().ii(INGOT.getMaterialIngredient(input1, count1), DUST.getMaterialIngredient(input2, count2)).io(INGOT.get(output, countO)).add(output.getId() + "_ingot_2" + suffix, 100, 12);
        }
        ALLOY_SMELTER.RB().ii(DUST.getMaterialIngredient(input1, count1), DUST.getMaterialIngredient(input2, count2)).io(INGOT.get(output, countO)).add(output.getId() + "_ingot_3" + suffix, 100, 12);
    }
}
