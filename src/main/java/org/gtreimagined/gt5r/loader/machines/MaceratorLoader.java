package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gt5r.data.GT5RRecipeTags;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreTags;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.VanillaStoneTypes;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.ore.BlockOreStone;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.ore.StoneType;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.RecipeMap;
import org.gtreimagined.gtlib.util.RegistryUtils;
import org.gtreimagined.gtlib.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.data.RecipeMaps.PULVERIZER;
import static org.gtreimagined.gt5r.data.RecipeMaps.SIFTER;
import static org.gtreimagined.gtcore.data.GTCoreItems.Biochaff;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.material.Material.NULL;
import static org.gtreimagined.gtlib.material.MaterialTags.*;

public class MaceratorLoader {
    public static void initAuto() {
        ORE.all().forEach(m -> {
            GTAPI.all(StoneType.class).stream().filter(StoneType::doesGenerateOre).filter(s -> s != VanillaStoneTypes.BEDROCK).forEach(s -> {
                Material sm = s.getMaterial();
                if (!m.has(GTMaterialTypes.DUST) && !m.has(GTMaterialTypes.CRUSHED_ORE)) return;
                ItemStack stoneDust = sm.has(GTMaterialTypes.DUST) ? GTMaterialTypes.DUST.get(sm, 1) : ItemStack.EMPTY;
                TagKey<Item> oreTag = ORE.getMaterialTag(m, s);
                RecipeIngredient ore = RecipeIngredient.of(oreTag,1);
                ItemStack crushedStack = (m.has(CRUSHED_ORE) ? GTMaterialTypes.CRUSHED_ORE : DUST).get(m, ORE_MULTI.getInt(m));
                Material oreByProduct1 = m.getByProducts().size() > 0 ? m.getByProducts().get(0) : MACERATE_INTO.getMapping(m);
                RecipeMap<?> rm = s.isSandLike() ? SIFTER : PULVERIZER;
                List<ItemStack> stacks = new ArrayList<>();
                stacks.add(Utils.ca((ORE_MULTI.getInt(m)) * (rm == SIFTER ? 1 : 2), crushedStack));
                if (rm == SIFTER){
                    stacks.add(crushedStack);
                    stacks.add(crushedStack);
                    stacks.add(crushedStack);
                }
                stacks.add(GTMaterialTypes.DUST.get(oreByProduct1, 1));
                if (!stoneDust.isEmpty()) stacks.add(stoneDust);
                ItemStack[] stackArray = stacks.toArray(new ItemStack[0]);
                List<Double> ints = new ArrayList<>();
                if (rm == SIFTER){
                    ints.add(0.7);
                    ints.add(0.5);
                    ints.add(0.3);
                    ints.add(0.1);
                } else {
                    ints.add(1.0);
                }
                ints.add(0.1 * BY_PRODUCT_MULTI.getInt(m));
                if (!stoneDust.isEmpty()) ints.add(0.5);
                double[] chances = ints.stream().mapToDouble(i -> i).toArray();
                rm.RB().ii(ore).io(stackArray).outputChances(chances).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("ore_" + m.getId() + "_" + s.getId(),400, 2);
            });
        });
        GTMaterialTypes.CRUSHED_ORE.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.CRUSHED_ORE)) return;
            RecipeIngredient crushed = GTMaterialTypes.CRUSHED_ORE.getIngredient(m, 1);

            //TODO better way to do this
            Material aOreByProduct1 = !m.getByProducts().isEmpty() ? m.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(m);
            Material aOreByProduct2 = m.getByProducts().size() > 1 ? m.getByProducts().get(1) : aOreByProduct1;
            Material aOreByProduct3 = m.getByProducts().size() > 2 ? m.getByProducts().get(2) : aOreByProduct2;
            PULVERIZER.RB().ii(crushed).io(GTMaterialTypes.IMPURE_DUST.get(MaterialTags.MACERATE_INTO.getMapping(m), 1), GTMaterialTypes.DUST.get(aOreByProduct1, 1)).outputChances(1.0, 0.1).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("crushed_" + m.getId(),400, 2);

            if (m.has(GTMaterialTypes.REFINED_ORE)) {
                var rb = PULVERIZER.RB();
                rb.ii(RecipeIngredient.of(GTMaterialTypes.REFINED_ORE.get(m,1))).io(GTMaterialTypes.DUST.get(MaterialTags.MACERATE_INTO.getMapping(m), 1), GTMaterialTypes.DUST.get(aOreByProduct3, 1));
                List<Integer> chances = new ArrayList<>();
                chances.add(10000);
                chances.add(1000);
                if (m.getByProducts().size() > 3){
                    rb.io(DUST.get(m.getByProducts().get(3)));
                    chances.add(1000);
                }
                if (m.getByProducts().size() > 4){
                    rb.io(DUST.get(m.getByProducts().get(4)));
                    chances.add(1000);
                }
                rb.outputChances(chances.stream().mapToInt(i -> i).toArray()).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("refined_" + m.getId(),400, 2);
            }
            if (m.has(GTMaterialTypes.PURIFIED_ORE) && m.has(GTMaterialTypes.PURE_DUST)) {
                PULVERIZER.RB().ii(GTMaterialTypes.PURIFIED_ORE.getIngredient(m, 1)).io(GTMaterialTypes.PURE_DUST.get(MaterialTags.MACERATE_INTO.getMapping(m), 1), GTMaterialTypes.DUST.get(aOreByProduct1, 1)).outputChances(1.0, 0.1).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("purified_" + m.getId(),400, 2);
            }
        });
        RAW_ORE.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.DUST) && !m.has(GTMaterialTypes.CRUSHED_ORE)) return;
            Material aOreByProduct1 = !m.getByProducts().isEmpty() ? m.getByProducts().get(0) : MaterialTags.MACERATE_INTO.getMapping(m);
            ItemStack crushedStack = (m.has(CRUSHED_ORE) ? GTMaterialTypes.CRUSHED_ORE : DUST).get(m, ORE_MULTI.getInt(m));
            PULVERIZER.RB().ii(RecipeIngredient.of(GTMaterialTypes.RAW_ORE.getMaterialTag(m), 1)).io(Utils.ca((MaterialTags.ORE_MULTI.getInt(m)) * 2, crushedStack), GTMaterialTypes.DUST.get(aOreByProduct1, 1)).outputChances(1.0, 0.1 * MaterialTags.BY_PRODUCT_MULTI.getInt(m)).tags(GT5RRecipeTags.MACERATOR_ORE_PROCESING).add("raw_" + m.getId(),400, 2);
        });
        EXQUISITE_GEM.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.DUST)) return;
            PULVERIZER.RB().ii(EXQUISITE_GEM.getMaterialIngredient(m, 1)).io(DUST.get(MaterialTags.MACERATE_INTO.get(m), 4)).add(m.getId() + "_exquisite", m.getMass(), 4);
            PULVERIZER.RB().ii(FLAWLESS_GEM.getMaterialIngredient(m, 1)).io(DUST.get(MaterialTags.MACERATE_INTO.get(m), 2)).add(m.getId() + "_flawless", m.getMass(), 4);
            PULVERIZER.RB().ii(FLAWED_GEM.getMaterialIngredient(m, 1)).io(SMALL_DUST.get(MaterialTags.MACERATE_INTO.get(m), 2)).add(m.getId() + "_flawed", m.getMass(), 4);
            PULVERIZER.RB().ii(CHIPPED_GEM.getMaterialIngredient(m, 1)).io(SMALL_DUST.get(MaterialTags.MACERATE_INTO.get(m), 1)).add(m.getId() + "_chipped", m.getMass(), 4);
        });
        GTMaterialTypes.GEM.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.DUST)) return;
            PULVERIZER.RB().ii(GEM.getMaterialIngredient(m, 1)).io(GTMaterialTypes.DUST.get(MaterialTags.MACERATE_INTO.get(m),1)).add("gem_" + m.getId(),m.getMass(),4);
        });

        //INGOT -> DUST
        GTMaterialTypes.INGOT.all().forEach(t -> {
            if (!MACERATE_INTO.get(t).has(GTMaterialTypes.DUST)) return;
            PULVERIZER.RB().ii(RecipeIngredient.of(GTMaterialTypes.INGOT.getMaterialTag(t),1)).io(GTMaterialTypes.DUST.get(MACERATE_INTO.get(t),1)).add("dust_" + t.getId(),40,2);
            if (t.has(NUGGET)){
                PULVERIZER.RB().ii(RecipeIngredient.of(NUGGET.getMaterialTag(t),1)).io(TINY_DUST.get(MACERATE_INTO.get(t),1)).add("dust_tiny_" + t.getId(),10,2);
            }
            if (t.has(CHUNK)){
                PULVERIZER.RB().ii(RecipeIngredient.of(CHUNK.getMaterialTag(t),1)).io(SMALL_DUST.get(MACERATE_INTO.get(t),1)).add("dust_small_" + t.getId(),10,2);
            }
        });
        BEARING_ROCK.all().forEach(r -> {
            if (r.has(DUST)){
                PULVERIZER.RB().ii(RecipeIngredient.of(GTMaterialTypes.BEARING_ROCK.getMaterialTag(r),1)).io(SMALL_DUST.get(MACERATE_INTO.get(r),1)).add("dust_small_" + r.getId() + "_from_bearing_rock",20,2);
            }
        });
        ROCK.all().forEach(r -> {
            if (r.has(DUST)){
                PULVERIZER.RB().ii(RecipeIngredient.of(GTMaterialTypes.ROCK.getMaterialTag(r),1)).io(SMALL_DUST.get(MACERATE_INTO.get(r),1)).add("dust_small_" + r.getId() + "_from_rock",20,2);
            }
        });
        GTAPI.all(StoneType.class, s -> {
            if (s.getMaterial() == NULL || !s.getMaterial().has(DUST) || s.isSandLike() || s.getState().getBlock() instanceof BlockOreStone) return;
            PULVERIZER.RB().ii(RecipeIngredient.of(s.getState().getBlock().asItem(), 1)).io(DUST.get(s.getMaterial(), 9)).add(s.getId() + "_stone_to_" + s.getMaterial().getId() + "_dust",400, 2);
            if (s instanceof CobbleStoneType){
                PULVERIZER.RB().ii(RecipeIngredient.of(((CobbleStoneType)s).getBlock("cobble").asItem(), 1)).io(DUST.get(s.getMaterial(), 9)).add("cobbled_" + s.getId() + "_to_" + s.getMaterial().getId() + "_dust",400, 2);
            }
        });
        for (DyeColor color : DyeColor.values()) {
            PULVERIZER.RB().ii(RegistryUtils.getItemFromID(new ResourceLocation(color.getSerializedName().toLowerCase() + "_concrete"))).io(RegistryUtils.getItemFromID(new ResourceLocation(color.getSerializedName().toLowerCase() + "_concrete_powder"))).add(color.getId() + "_concrete",400,2);
        }
    }

    public static void init(){
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.STONE,1)).io(new ItemStack(Items.GRAVEL,1)).add("gravel",100,2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.COBBLESTONE,1)).io(new ItemStack(Items.SAND,1)).add("sand",100,2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.GRAVEL,1)).io(DUST.get(Stone,9)).add("stone_dust_from_gravel",100,2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.BRICK,1)).io(SMALL_DUST.get(Materials.Brick, 2)).add("brick_dust",50,4);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.COAL,1)).io(GTMaterialTypes.DUST.get(Coal, 1)).add("coal_dust",50,4);
        PULVERIZER.RB().ii(RecipeIngredient.of(ItemTags.LOGS, 1)).io(GTMaterialTypes.DUST.get(Wood, 2)).add("wood_dust",40, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.CLAY_BALL, 1)).io(SMALL_DUST.get(Clay, 2)).add("clay_dust_small",16, 4);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.CLAY, 1)).io(DUST.get(Clay, 2)).add("clay_dust",30, 4);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.TERRACOTTA, 1)).io(DUST.get(Clay, 1)).add("clay_dust_1",16, 4);
        PULVERIZER.RB().ii(RecipeIngredient.of(GTCoreItems.Plantball, 1)).io(new ItemStack(Biochaff, 1)).add("biochaff",32, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Biochaff, 1)).io(new ItemStack(Items.DIRT, 1)).add("dirt",32, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(GTCoreTags.RUBBER_LOGS)).io(DUST.get(Wood, 6), new ItemStack(GTCoreItems.StickyResin, 1)).outputChances(1.0, 0.33).add("rubber_log", 400, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Blocks.CALCITE)).io(DUST.get(Calcite)).add("calcite_from_mc_calcite", 400, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN)).io(DUST.get(Obsidian, 9)).add("obsidian_dust", 400, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.BLAZE_ROD)).io(new ItemStack(Items.BLAZE_POWDER, 5)).add("blaze_powder", 400, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.BONE)).io(new ItemStack(Items.BONE_MEAL, 5)).add("bone_meal", 400, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Items.QUARTZ_BLOCK)).io(DUST.get(Quartz, 4)).add("quartz_dust_from_block", Quartz.getMass(), 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Tags.Items.GLASS)).io(DUST.get(Glass)).add("glass_dust", 100, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(Tags.Items.GLASS_PANES)).io(TINY_DUST.get(Glass)).add("glass_dust_from_panes", 20, 2);
        PULVERIZER.RB().ii(RecipeIngredient.of(ItemTags.WOOL)).io(new ItemStack(Items.STRING, 4)).add("wool_to_string",400,2);
        PULVERIZER.RB().ii(GTCoreItems.CarbonMesh).io(DUST.get(Carbon, 16)).add("carbon_mesh_to_dust", 400, 2);
    }
}