package org.gtreimagined.gt5r.loader.machines;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.ore.StoneType;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.TagUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;

import static com.google.common.collect.ImmutableMap.of;
import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.CUTTER;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class CutterLoader {
    public static void init() {
        ToLongFunction<Material> baseDuration = m -> {
            if (m.has(GT5RMaterialTags.RECIPE_MASS)) return GT5RMaterialTags.RECIPE_MASS.get(m);
            return m.getMass();
        };
        for (Material mat : GTMaterialTypes.PLATE.all()) {
            int multiplier = 1;//mat.has(GTMaterialTypes.GEM) ? 8 : 3;
            if (mat == Diamond || mat == NetherizedDiamond)
                multiplier = 5;
            if (mat.has(GEM)){
                addCutterRecipe(GEM.getMaterialTag(mat), PLATE.get(mat, 1), "plate_" + mat.getId() + "_from_gem", (int) (baseDuration.applyAsLong(mat) * multiplier), 96);
                if (mat.has(FLAWLESS_GEM)) {
                    addCutterRecipe(FLAWLESS_GEM.getMaterialTag(mat), PLATE.get(mat, 2), "plate_" + mat.getId() + "_from_gem_flawless", (int) (baseDuration.applyAsLong(mat) * multiplier), 96);
                }
                if (mat.has(EXQUISITE_GEM)) {
                    addCutterRecipe(EXQUISITE_GEM.getMaterialTag(mat), PLATE.get(mat, 4), "plate_" + mat.getId() + "_from_gem_exquisite", (int) (baseDuration.applyAsLong(mat) * multiplier), 96);
                }
            }
            if (!mat.has(GTMaterialTypes.BLOCK))
                continue;
            int count = mat.has(MaterialTags.QUARTZ_LIKE_BLOCKS) ? 4 : 9;
            addCutterRecipe(BLOCK.getMaterialTag(mat), PLATE.get(mat, count), "plate_" + mat.getId(), (int) (baseDuration.applyAsLong(mat) * 8 * multiplier), 30);
            if (mat.has(ITEM_CASING)){
                addCutterRecipe(PLATE.getMaterialTag(mat), ITEM_CASING.get(mat, 2), "item_casing_" + mat.getId(), (int) (baseDuration.applyAsLong(mat) * 5 * multiplier), 16);
            }
        }
        GTAPI.all(StoneType.class, s -> {
            if (s instanceof CobbleStoneType c){
                for (String type : CobbleStoneType.SUFFIXES){
                    String id = (type.isEmpty() ? c.getId() : c.getId() + "_" + type) + "_cover";
                    Item cover = GTAPI.get(Item.class, id, Ref.SHARED_ID);
                    if (cover == null) continue;
                    addCutterRecipe(c.getBlock(type).asItem(), new ItemStack(cover, 8), DUST.get(c.getMaterial(), 1), id, 20, 2);
                }
            }
        });
        GTMaterialTypes.BOLT.all().forEach(t -> {
            if (t.has(GTMaterialTypes.ROD)) {
                addCutterRecipe(ROD.getMaterialTag(t), BOLT.get(t, 4), "bolt_" + t.getId(), (int) (baseDuration.applyAsLong(t) * 2), 4);
            }
        });
        GTMaterialTypes.LONG_ROD.all().stream().filter(m -> m.has(ROD)).forEach(m -> {
            addCutterRecipe(LONG_ROD.getMaterialTag(m), ROD.get(m, 2), "rod_" + m.getId(), (int) (baseDuration.applyAsLong(m) * 2), 4);
        });
        addWoodRecipes();
    }

    private static void addWoodRecipes(){
        Map<String, String> customSuffixes = new HashMap<>();
        Map<String, List<String>> modWoods = new Object2ObjectOpenHashMap<>();
        customSuffixes.put("crimson", "stems");
        customSuffixes.put("warped", "stems");
        modWoods.put("minecraft", List.of("oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "crimson", "warped"));
        if (GTAPI.isModLoaded("northstar")){
            modWoods.put("northstar", List.of("wilter", "argyre", "coiler", "calorian"));
        }
        if (GTAPI.isModLoaded("ad_astra")){
            modWoods.put("ad_astra", List.of("aeronos", "strophar", "glacian"));
            customSuffixes.put("aeronos", "caps");
            customSuffixes.put("strophar", "caps");
        }
        if (GTAPI.isModLoaded("terrestria")){
            modWoods.put ("terrestria", List.of("cypress", "hemlock", "japanese_maple", "rainbow_eucalyptus", "redwood", "rubber", "sakura", "willow", "yucca_palm"));
        }
        if (GTAPI.isModLoaded("undergarden")) modWoods.put("undergarden", List.of("smogstem", "wigglewood", "grongle"));
        if (GTAPI.isModLoaded("botania")) modWoods.put("botania", List.of("livingwood", "dreamwood"));
        if (GTAPI.isModLoaded("traverse")) modWoods.put("traverse", List.of("fir"));
        if (GTAPI.isModLoaded("forestry")){
            String domain = "forestry";
            modWoods.put(domain, List.of("larch", "teak", "acacia_desert", "lime", "chestnut", "wenge", "baobab", "sequoia", "kapok", "ebony", "mahogany",
                    "balsa", "willow", "walnut", "greenheart", "hill_cherry", "mahoe", "poplar", "palm", "papaya", "pine", "plum", "maple", "citrus", "giganteum", "ipe", "padauk",
                    "cocobolo", "zebrawood"));
            List<String> fireProofPlanks = new ArrayList<>(modWoods.get(domain));
            fireProofPlanks.addAll(modWoods.get("minecraft"));
            fireProofPlanks.removeAll(List.of("mangrove", "crimson", "warped"));
            for (String wood : fireProofPlanks){
                String suffix = customSuffixes.getOrDefault(wood, "logs");
                ResourceLocation planks = new ResourceLocation(domain, wood + "_fireproof_planks");
                addWoodRecipe(TagUtils.getItemTag(new ResourceLocation(domain, "fireproof_" + wood + "_" + suffix)), RegistryUtils.getItemFromID(planks), 1, planks, 200, 8);
            }

        }

        modWoods.forEach((domain, w) -> {
            if (domain.equals("minecraft") && GTAPI.isModLoaded("tfc")) return;
            for (String wood : w){
                String suffix = customSuffixes.getOrDefault(wood, "logs");
                ResourceLocation planks = new ResourceLocation(domain, wood + "_planks");
                addWoodRecipe(TagUtils.getItemTag(new ResourceLocation(domain, wood + "_" + suffix)), RegistryUtils.getItemFromID(planks), 1, planks, 200, 8);
            }
        });
    }

    private static void addCutterRecipe(TagKey<Item> input, ItemStack output, String id, int duration, int euPerTick){
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(new FluidStack(Fluids.WATER, Math.max(4, Math.min(1000, duration * euPerTick / 320))))
                .io(output).add(id + "_with_water", duration * 2L, euPerTick);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.Lubricant.getLiquid(Math.max(1, Math.min(250, duration * euPerTick / 1280))))
                .io(output).add(id + "_with_lubricant", duration, euPerTick);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.DistilledWater.getLiquid(Math.max(3, Math.min(750, duration * euPerTick / 426))))
                .io(output).add(id + "_with_distilled_water", duration * 2L, euPerTick);
    }

    private static void addCutterRecipe(Item input, ItemStack output, ItemStack dust, String id, int duration, int euPerTick){
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(new FluidStack(Fluids.WATER, Math.max(4, Math.min(1000, duration * euPerTick / 320))))
                .io(output, dust).add(id + "_with_water", duration * 2L, euPerTick);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.Lubricant.getLiquid(Math.max(1, Math.min(250, duration * euPerTick / 1280))))
                .io(output, dust).add(id + "_with_lubricant", duration, euPerTick);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.DistilledWater.getLiquid(Math.max(3, Math.min(750, duration * euPerTick / 426))))
                .io(output, dust).add(id + "_with_distilled_water", duration * 2L, euPerTick);
    }

    public static void addWoodRecipe(TagKey<Item> input, Item output, int multiplier, ResourceLocation id, int duration, int euPerTick){
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(new FluidStack(Fluids.WATER, Math.max(4, Math.min(1000, duration * euPerTick / 320))))
                .io(new ItemStack(output, 4 * multiplier), DUST.get(Wood, 2)).add(id.getNamespace(), id.getPath() + "_with_water", duration * 2L, euPerTick, 0, 1);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.Lubricant.getLiquid(Math.max(1, Math.min(250, duration * euPerTick / 1280))))
                .io(new ItemStack(output, 6 * multiplier), DUST.get(Wood, 1)).add(id.getNamespace(), id.getPath() + "_with_lubricant", duration, euPerTick, 0, 1);
        CUTTER.RB().ii(RecipeIngredient.of(input, 1))
                .fi(Materials.DistilledWater.getLiquid(Math.max(3, Math.min(750, duration * euPerTick / 426))))
                .io(new ItemStack(output, 4 * multiplier), DUST.get(Wood, 2)).add(id.getNamespace(), id.getPath() + "_with_distilled_water", duration * 2L, euPerTick, 0, 1);
    }
}
