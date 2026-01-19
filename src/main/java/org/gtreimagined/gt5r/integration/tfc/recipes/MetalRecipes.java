package org.gtreimagined.gt5r.integration.tfc.recipes;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal.ItemType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gt5r.integration.tfc.Metals;
import org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.AnvilWorkingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.CastingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.HeatingFinishedRecipe;
import org.gtreimagined.gt5r.integration.tfc.finishedrecipes.WeldingFinishedRecipe;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.datagen.providers.GTRecipeProvider;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.material.data.ToolData;
import org.gtreimagined.gtlib.recipe.ingredient.PropertyIngredient;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.*;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.JAVELIN;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.PROPICK;
import static org.gtreimagined.gtlib.Ref.U;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTTools.*;
import static org.gtreimagined.gtlib.data.GTTools.AXE;
import static org.gtreimagined.gtlib.data.GTTools.FILE;
import static org.gtreimagined.gtlib.data.GTTools.HAMMER;
import static org.gtreimagined.gtlib.data.GTTools.HOE;
import static org.gtreimagined.gtlib.data.GTTools.KNIFE;
import static org.gtreimagined.gtlib.data.GTTools.SAW;
import static org.gtreimagined.gtlib.data.GTTools.SCYTHE;
import static org.gtreimagined.gtlib.material.MaterialTags.MELTING_POINT;
import static org.gtreimagined.gtlib.material.MaterialTags.TOOLS;

public class MetalRecipes {
    private static final Map<MaterialTypeItem<?>, List<String>> FORGING_RULES = new Object2ObjectOpenHashMap<>();

    static {
        FORGING_RULES.put(SWORD_BLADE, List.of("hit_last", "bend_second_last", "bend_third_last"));
        FORGING_RULES.put(PICKAXE_HEAD, List.of("punch_last", "bend_not_last", "draw_not_last"));
        FORGING_RULES.put(SHOVEL_HEAD, List.of("punch_last", "hit_not_last"));
        FORGING_RULES.put(AXE_HEAD, List.of("punch_last", "hit_second_last", "upset_third_last"));
        FORGING_RULES.put(HOE_HEAD, List.of("punch_last", "hit_not_last", "bend_not_last"));
        FORGING_RULES.put(HAMMER_HEAD, List.of("punch_last", "shrink_not_last"));
        FORGING_RULES.put(SAW_BLADE, List.of("hit_last", "hit_second_last"));
        FORGING_RULES.put(FILE_HEAD, List.of("hit_last", "punch_second_last"));
        FORGING_RULES.put(KNIFE_BLADE, List.of("hit_last", "draw_second_last", "draw_third_last"));
        FORGING_RULES.put(SCYTHE_BLADE, List.of("hit_last", "draw_second_last", "bend_third_last"));
        FORGING_RULES.put(SCREWDRIVER_TIP, List.of("hit_last", "hit_second_last", "hit_third_last"));
        FORGING_RULES.put(PROPICK_HEAD, List.of("punch_last", "draw_not_last", "bend_not_last"));
        FORGING_RULES.put(JAVELIN_HEAD, List.of("hit_last", "hit_second_last", "draw_third_last"));
        FORGING_RULES.put(CHISEL_HEAD, List.of("hit_last", "hit_not_last", "draw_not_last"));
        FORGING_RULES.put(MACE_HEAD, List.of("hit_last", "shrink_not_last", "bend_not_last"));
        FORGING_RULES.put(SHEET, List.of("hit_last", "hit_second_last", "hit_third_last"));
        FORGING_RULES.put(ROD, List.of("bend_last", "draw_second_last", "draw_third_last"));
        FORGING_RULES.put(RING, List.of("hit_last", "bend_second_last", "bend_third_last"));
    }

    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        List<Material> chains = List.of(BismuthBronze, BlackBronze, Bronze, Copper, WroughtIron, Steel, BlackSteel, BlueSteel, RedSteel);
        Metals.METALS.forEach((m, i) -> {
            int meltTemp = MaterialTags.MELTING_POINT.get(m) - 273;
            boolean magnetic = m == IronMagnetic || m == SteelMagnetic;
            boolean tfc = i.getA() || magnetic;
            String fluidId = m == WroughtIron || m == IronMagnetic ? "cast_iron" : m == SteelMagnetic ? "steel" : m.getId();
            Function<Integer, FluidStack> fluid = amount -> new FluidStack(RegistryUtils.getFluidFromID(new ResourceLocation(tfc ? "tfc" : Ref.SHARED_ID, (tfc ? "metal/" : "") + fluidId)), amount);
            if (m.has(TOOLS)) {
                GTToolType[] toolTypes = new GTToolType[]{SWORD, PICKAXE, SHOVEL, AXE, HOE, HAMMER, SAW, FILE, SCREWDRIVER, KNIFE, SCYTHE, PROPICK, JAVELIN};
                ToolData t = TOOLS.get(m);
                for(GTToolType toolType : toolTypes){
                    if (toolType.getReplacements().containsKey(m.getId())) continue;
                    if (t.toolTypes().contains(toolType)){
                        MaterialTypeItem<?> toolHead = toolType.getMaterialTypeItem();
                        if (toolType == SCREWDRIVER && !m.has(LONG_ROD)) continue;
                        double ratio = (double)toolHead.getUnitValue() / U;
                        consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + toolType.getId()), Ingredient.of(toolType.getToolItem(m)), fluid.apply((int)(ratio * 100)), meltTemp));
                        consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + toolHead.getId()), Ingredient.of(toolType.getMaterialTypeItem().getMaterialTag(m)), fluid.apply((int)(ratio * 100)), meltTemp));
                        if (!magnetic && toolType != FILE && toolType != SCREWDRIVER) consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_" + toolHead.getId()), RegistryUtils.getItemFromID(new ResourceLocation("tfc", "ceramic/" + toolHead.getId() + "_mold")), fluid.apply((int)(ratio * 100)), toolHead.get(m), 1));
                        if (toolHead.getUnitValue() == U || toolHead.getUnitValue() == U * 2){
                            MaterialTypeItem<?> input = toolHead == SCREWDRIVER_TIP ? LONG_ROD : toolHead.getUnitValue() == U ? INGOT : DOUBLE_INGOT;
                            consumer.accept(new AnvilWorkingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "anvil/" + m.getId() + "_" + toolHead.getId()), input.getMaterialIngredient(m, 1), toolHead.get(m, 1), i.getB(), true, FORGING_RULES.get(toolHead).toArray(String[]::new)));
                        }
                    }
                }
            }
            MaterialTypeItem<?>[] types = new MaterialTypeItem[]{RAW_ORE, BEARING_ROCK, INGOT, DOUBLE_INGOT, SHEET, PLATE, ROD, LONG_ROD, RING, CHUNK, DUST};
            for(MaterialTypeItem<?> type : types){
                if (m.has(type) && !type.hasReplacement(m)){
                    double ratio = type == RAW_ORE ? .5 : type == BEARING_ROCK ? .1 : (double)type.getUnitValue() / U;
                    consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + type.getId()), Ingredient.of(type.getMaterialTag(m)), fluid.apply((int) (100 * ratio)), meltTemp));
                    if (!magnetic && type == INGOT){
                        consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_ingot"), TFCItems.MOLDS.get(ItemType.INGOT).get(), fluid.apply(100), INGOT.get(m), 0.1f));
                        consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_fire_ingot"), TFCItems.FIRE_INGOT_MOLD.get(), fluid.apply(100), INGOT.get(m), 0.01f));
                    }
                    if (type == SHEET || type == ROD || type == RING){
                        int amount = type == ROD || type == RING ? 2 : 1;
                        MaterialTypeItem<?> in = type == ROD ? INGOT : type == RING ? ROD :  DOUBLE_INGOT;
                        consumer.accept(new AnvilWorkingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "anvil/" + m.getId() + "_" + type.getId()), in.getMaterialIngredient(m, 1), type.get(m, amount), i.getB(), false, FORGING_RULES.get(type).toArray(String[]::new)));
                    }
                }
            }
            if (!DOUBLE_INGOT.hasReplacement(m)) {
                consumer.accept(new WeldingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "welding/" + m.getId() + "_double_ingot"), INGOT.getMaterialIngredient(m, 1), INGOT.getMaterialIngredient(m, 1),  DOUBLE_INGOT.get(m, 1), i.getB()));
            }
            if (m.has(ROD) && m.has(LONG_ROD)){
                consumer.accept(new WeldingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "welding/" + m.getId() + "_long_rod"), ROD.getMaterialIngredient(m, 1), ROD.getMaterialIngredient(m, 1), LONG_ROD.get(m,1), i.getB()));
            }
            if (m.has(RING) && chains.contains(m)){
                consumer.accept(new WeldingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "welding/" + m.getId() + "_chain"), RING.getMaterialIngredient(m, 1), RING.getMaterialIngredient(m, 1), new ItemStack(RegistryUtils.getItemFromID("tfc", "metal/chain/" + m.getId())), i.getB()));
            }
        });

        for (Material chain : chains){
            String fluidId = chain == WroughtIron ? "cast_iron" : chain.getId();
            int meltTemp = MaterialTags.MELTING_POINT.get(chain) - 273;
            consumer.accept(new HeatingFinishedRecipe(new ResourceLocation("tfc", "heating/metal/" + chain.getId() + "_chain"), Ingredient.of(RegistryUtils.getItemFromID("tfc", "metal/chain/" + chain.getId())), new FluidStack(RegistryUtils.getFluidFromID(new ResourceLocation("tfc", "metal/" + fluidId)), 50), meltTemp));
            provider.removeRecipe(new ResourceLocation("tfc", "anvil/" +  chain.getId() + "_chain"));
        }
    }
}
