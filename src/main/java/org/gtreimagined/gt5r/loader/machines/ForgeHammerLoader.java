package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gt5r.GT5RConfig;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.ore.CobbleStoneType;
import org.gtreimagined.gtlib.ore.StoneType;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

import java.util.function.ToLongFunction;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.material.MaterialTags.MACERATE_INTO;
import static org.gtreimagined.gtlib.material.MaterialTags.ORE_MULTI;
import static org.gtreimagined.gt5r.data.Materials.Brick;
import static org.gtreimagined.gt5r.data.Materials.Glass;
import static org.gtreimagined.gt5r.data.RecipeMaps.FORGE_HAMMER;

public class ForgeHammerLoader {
    public static void init() {
        ORE.all().forEach(m -> {
            RecipeIngredient ore = ORE.getMaterialIngredient(m, 1);
            Material macerateInto = MACERATE_INTO.getMapping(m);
            ItemStack crushedStack = macerateInto.has(CRUSHED_ORE) ? CRUSHED_ORE.get(macerateInto,1) : DUST.get(macerateInto, 1);
            FORGE_HAMMER.RB().ii(ore).io(Utils.ca(ORE_MULTI.getInt(m), crushedStack)).add(m.getId() + "_ore",16, 10);
            if (m.has(RAW_ORE)){
                FORGE_HAMMER.RB().ii(RecipeIngredient.of(RAW_ORE.getMaterialTag(m), 1)).io(Utils.ca(ORE_MULTI.getInt(m), crushedStack)).add(m.getId() + "_raw_ore",16, 10);
            }
            if (MACERATE_INTO.getMapping(m).has(CRUSHED_ORE)){
                FORGE_HAMMER.RB().ii(CRUSHED_ORE.getMaterialIngredient(macerateInto, 1)).io(IMPURE_DUST.get(MACERATE_INTO.getMapping(m), 1)).add(m.getId() + "_crushed_ore",16, 10);
                FORGE_HAMMER.RB().ii(PURIFIED_ORE.getMaterialIngredient(MACERATE_INTO.getMapping(m), 1)).io(PURE_DUST.get(MACERATE_INTO.getMapping(m), 1)).add(m.getId() + "_purified_ore",16, 10);
                if (m.has(REFINED_ORE)) {
                    FORGE_HAMMER.RB().ii(REFINED_ORE.getMaterialIngredient(MACERATE_INTO.getMapping(m), 1)).io(DUST.get(MACERATE_INTO.getMapping(m), 1)).add(m.getId() + "_refined_ore",16, 10);
                }
            }
        });
        ToLongFunction<Material> baseDuration = m -> {
            if (m.has(GT5RMaterialTags.RECIPE_MASS)) return GT5RMaterialTags.RECIPE_MASS.get(m);
            return m.getMass();
        };
        PLATE.all().forEach(plate -> {
            if (!plate.has(INGOT) || plate.has(MaterialTags.NOSMASH)) return;
            int input = GT5RConfig.MORE_LOSSY_FORGE_HAMMER.get() ? 2 : 3;
            int output = GT5RConfig.MORE_LOSSY_FORGE_HAMMER.get() ? 1 : 2;
            FORGE_HAMMER.RB().ii(INGOT.getMaterialIngredient(plate, input)).io(PLATE.get(plate, output)).add("plate_" + plate.getId(),baseDuration.applyAsLong(plate) * 2, 16);
        });
        LONG_ROD.all().stream().filter(m -> !m.has(MaterialTags.NOSMASH)).forEach(rod -> {
            FORGE_HAMMER.RB().ii(ROD.getMaterialIngredient(rod, 2)).io(LONG_ROD.get(rod, 1)).add("rod_long_" + rod.getId(), baseDuration.applyAsLong(rod) * 2, 16);
        });
        EXQUISITE_GEM.all().forEach(m -> {
            FORGE_HAMMER.RB().ii(EXQUISITE_GEM.getMaterialIngredient(m, 1)).io(FLAWLESS_GEM.get(m, 2)).add(m.getId() + "_exquisite", 64, 16);
            FORGE_HAMMER.RB().ii(FLAWLESS_GEM.getMaterialIngredient(m, 1)).io(GEM.get(m, 2)).add(m.getId() + "_flawless", 64, 16);
            FORGE_HAMMER.RB().ii(GEM.getMaterialIngredient(m, 1)).io(FLAWED_GEM.get(m, 2)).add(m.getId() + "_flawed", 64, 16);
            FORGE_HAMMER.RB().ii(FLAWED_GEM.getMaterialIngredient(m, 1)).io(CHIPPED_GEM.get(m, 2)).add(m.getId() + "_chipped", 64, 16);
        });
        GTAPI.all(StoneType.class, s -> {
            if (!(s instanceof CobbleStoneType cs)) return;
            FORGE_HAMMER.RB().ii(RecipeIngredient.of(cs.getBlock(""), 1)).io(new ItemStack(cs.getBlock("cobble"))).add(s.getId() + "_to_cobble",10, 16);
            FORGE_HAMMER.RB().ii(RecipeIngredient.of(cs.getBlock("bricks"), 1)).io(new ItemStack(cs.getBlock("cobble"))).add(s.getId() + "_bricks_to_cobble",10, 16);
            FORGE_HAMMER.RB().ii(RecipeIngredient.of(cs.getBlock("smooth"), 1)).io(new ItemStack(cs.getBlock("cobble"))).add("smooth_" + s.getId() + "_to_cobble",10, 16);
        });
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Items.STONE_BRICKS)).io(new ItemStack(Items.COBBLESTONE)).add("stone_bricks_to_cobble", 10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Items.SMOOTH_STONE)).io(new ItemStack(Items.COBBLESTONE)).add("smooth_stone_to_cobble", 10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Tags.Items.COBBLESTONE, 1)).io(new ItemStack(Items.GRAVEL)).add("gravel",10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Items.STONE, 1)).io(new ItemStack(Items.COBBLESTONE)).add("cobblestone",10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Tags.Items.GRAVEL, 1)).io(new ItemStack(Items.SAND)).add("sand",10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Tags.Items.GLASS, 1)).io(DUST.get(Glass)).add("glass_dust",10, 16);
        //Wrought Iron and Annealed Copper 2 to 1 (pre Arc Furnace)
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Items.BRICK, 1)).io(SMALL_DUST.get(Brick, 2)).add("brick_dust_small",10, 16);
        FORGE_HAMMER.RB().ii(RecipeIngredient.of(Items.BRICKS, 1)).io(DUST.get(Brick, 2)).add("brick_dust",40, 16);
    }
}
