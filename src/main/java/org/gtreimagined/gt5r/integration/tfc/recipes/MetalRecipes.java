package org.gtreimagined.gt5r.integration.tfc.recipes;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal.ItemType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
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

import static org.gtreimagined.gt5r.data.Materials.RoseGold;
import static org.gtreimagined.gt5r.data.Materials.SterlingSilver;
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
        FORGING_RULES.put(PROPICK_HEAD, List.of("punch_last", "draw_not_last", "bend_not_last"));
        FORGING_RULES.put(JAVELIN_HEAD, List.of("hit_last", "hit_second_last", "draw_third_last"));
        FORGING_RULES.put(CHISEL_HEAD, List.of("hit_last", "hit_not_last", "draw_not_last"));
        FORGING_RULES.put(MACE_HEAD, List.of("hit_last", "shrink_not_last", "bend_not_last"));
        FORGING_RULES.put(SHEET, List.of("hit_last", "hit_second_last", "hit_third_last"));
        FORGING_RULES.put(ROD, List.of("bend_last", "draw_second_last", "draw_third_last"));
    }

    public static void init(Consumer<FinishedRecipe> consumer, GTRecipeProvider provider){
        Metals.METALS.forEach((m, i) -> {
            int meltTemp = MaterialTags.MELTING_POINT.get(m) - 273;
            boolean tfc = m == RoseGold || m == SterlingSilver;
            Function<Integer, FluidStack> fluid = amount -> new FluidStack(RegistryUtils.getFluidFromID(new ResourceLocation(tfc ? "tfc" : Ref.SHARED_ID, (tfc ? "metal/" : "") + m.getId())), amount);
            if (m.has(TOOLS)) {
                GTToolType[] toolTypes = new GTToolType[]{SWORD, PICKAXE, SHOVEL, AXE, HOE, HAMMER, SAW, FILE, KNIFE, SCYTHE, PROPICK, JAVELIN};
                ToolData t = TOOLS.get(m);
                for(GTToolType toolType : toolTypes){
                    if (t.toolTypes().contains(toolType)){
                        MaterialTypeItem<?> toolHead = toolType.getMaterialTypeItem();
                        double ratio = (double)toolHead.getUnitValue() / U;
                        consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + toolType.getId()), Ingredient.of(toolType.getToolItem(m)), fluid.apply((int)(ratio * 100)), meltTemp));
                        consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + toolHead.getId()), Ingredient.of(toolType.getMaterialTypeItem().getMaterialTag(m)), fluid.apply((int)(ratio * 100)), meltTemp));
                        if (toolType != FILE) consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_" + toolHead.getId()), RegistryUtils.getItemFromID(new ResourceLocation("tfc", "ceramic/" + toolHead.getId() + "_mold")), fluid.apply((int)(ratio * 100)), toolHead.get(m), 1));
                        if (toolHead.getUnitValue() == U || toolHead.getUnitValue() == U * 2){
                            MaterialTypeItem<?> input = toolHead.getUnitValue() == U ? INGOT : DOUBLE_INGOT;
                            consumer.accept(new AnvilWorkingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "anvil/" + m.getId() + "_" + toolHead.getId()), input.getMaterialIngredient(m, 1), toolHead.get(m, 1), i, true, FORGING_RULES.get(toolHead).toArray(String[]::new)));
                        }
                    }
                }
            }
            MaterialTypeItem<?>[] types = new MaterialTypeItem[]{RAW_ORE, INGOT, DOUBLE_INGOT, SHEET, PLATE, ROD, CHUNK, DUST};
            for(MaterialTypeItem<?> type : types){
                if (m.has(type) && (!tfc || (type != INGOT && type != SHEET && type != ROD && type != DOUBLE_INGOT))){
                    double ratio = type == RAW_ORE ? .5 : (double)type.getUnitValue() / U;
                    consumer.accept(new HeatingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "heating/" + m.getId() + "_" + type.getId()), Ingredient.of(type.getMaterialTag(m)), fluid.apply((int) (100 * ratio)), meltTemp));
                    if (type == INGOT){
                        consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_ingot"), TFCItems.MOLDS.get(ItemType.INGOT).get(), fluid.apply(100), INGOT.get(m), 0.1f));
                        consumer.accept(new CastingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "casting/" + m.getId() + "_fire_ingot"), TFCItems.FIRE_INGOT_MOLD.get(), fluid.apply(100), INGOT.get(m), 0.01f));
                    }
                    if (type == SHEET || type == ROD){
                        int amount = type == ROD ? 2 : 1;
                        MaterialTypeItem<?> in = type == ROD ? INGOT : DOUBLE_INGOT;
                        consumer.accept(new AnvilWorkingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "anvil/" + m.getId() + "_" + type.getId()), in.getMaterialIngredient(m, 1), type.get(m, amount), i, false, FORGING_RULES.get(type).toArray(String[]::new)));
                    }
                }
            }
            consumer.accept(new WeldingFinishedRecipe(new ResourceLocation(GT5Reimagined.ID, "welding/" + m.getId() + "_double_ingot"), INGOT.getMaterialIngredient(m, 1), INGOT.getMaterialIngredient(m, 1),  DOUBLE_INGOT.get(m, 1), i));
        });
    }
}
