package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import org.gtreimagined.gtcore.data.GTCoreRecipeMaps;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtcore.data.GTCoreRecipeMaps.MORTAR;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class MortarLoader {
    public static void init(){
        MORTAR.RB().ii(INGOT.getMaterialIngredient(Copper, 1)).io(DUST.get(Copper)).add("copper_dust");
        MORTAR.RB().ii(INGOT.getMaterialIngredient(Tin, 1)).io(DUST.get(Tin)).add("tin_dust");
        MORTAR.RB().ii(Items.CLAY_BALL).io(SMALL_DUST.get(Clay, 2)).add("small_clay_dust");
        MORTAR.RB().ii(Ingredient.of(Tags.Items.GRAVEL)).io(Items.FLINT).add("flint");
        MORTAR.RB().ii(Ingredient.of(Tags.Items.GLASS_PANES)).io(DUST.get(Glass)).add("glass_dust");
        MORTAR.RB().ii(Ingredient.of(Tags.Items.GLASS)).io(DUST.get(Glass, 9)).add("glass_dust_from_block");
        MORTAR.RB().ii(Ingredient.of(Items.SUGAR_CANE)).io(DUST.get(Chad)).add("chad_dust");
        CRUSHED_ORE.all().forEach(m -> {
            if (m.has(IMPURE_DUST)){
                MORTAR.RB().ii(CRUSHED_ORE.getMaterialIngredient(m, 1)).io(IMPURE_DUST.get(m)).add(m.getId() + "_impure_dust");
            }
        });
        BEARING_ROCK.all().forEach(m -> {
            if (m.has(DUST)){
                MORTAR.RB().ii(BEARING_ROCK.getMaterialIngredient(m, 1)).io(SMALL_DUST.get(m)).add(m.getId() + "_small_dust_from_bearing_rock");
            }
        });
        ROCK.all().forEach(m -> {
            if (m.has(DUST)){
                MORTAR.RB().ii(ROCK.getMaterialIngredient(m, 1)).io(SMALL_DUST.get(m)).add(m.getId() + "_small_dust_from_rock");
            }
        });
    }
}
