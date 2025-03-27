package org.gtreimagined.gt5r.loader.machines;

import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.data.GTCoreBlocks;
import org.gtreimagined.gtcore.data.GTCoreItems;
import org.gtreimagined.gtcore.data.GTCoreTags;

import static org.gtreimagined.gtlib.data.AntimatterMaterialTypes.DUST;
import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;
import static org.gtreimagined.gt5r.data.GT5RItems.*;
import static org.gtreimagined.gt5r.data.Materials.RawRubber;
import static org.gtreimagined.gt5r.data.RecipeMaps.EXTRACTOR;

public class ExtractorLoader {
    public static void init() {
        EXTRACTOR.RB().ii(of(GTCoreTags.RUBBER_LOGS,1)).io(new ItemStack(DUST.get(RawRubber),2)).add("raw_rubber",200,8);
        EXTRACTOR.RB().ii(of(GTCoreBlocks.RUBBER_LEAVES.asItem(),1)).io(new ItemStack(DUST.get(RawRubber))).add("raw_rubber_1",150,8);
        EXTRACTOR.RB().ii(of(GTCoreBlocks.RUBBER_SAPLING.asItem(),1)).io(new ItemStack(DUST.get(RawRubber))).add("raw_rubber_2",150,8);
        EXTRACTOR.RB().ii(of(GTCoreItems.StickyResin,1)).io(new ItemStack(DUST.get(RawRubber),4)).add("raw_rubber_3",200,5);
        EXTRACTOR.RB().ii(of(BatteryLargeAcid, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullLarge)).add("large_acid_battery_recycling", 200, 5);
        EXTRACTOR.RB().ii(of(BatteryLargeMercury, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullLarge)).add("large_mercury_battery_recycling", 200, 5);
        EXTRACTOR.RB().ii(of(BatteryMediumAcid, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullMedium)).add("medium_acid_battery_recycling", 200, 5);
        EXTRACTOR.RB().ii(of(BatteryMediumMercury, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullMedium)).add("medium_mercury_battery_recycling", 200, 5);
        EXTRACTOR.RB().ii(of(BatterySmallAcid, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullSmall)).add("small_acid_battery_recycling", 200, 5);
        EXTRACTOR.RB().ii(of(BatterySmallMercury, 1).setIgnoreNbt()).io(new ItemStack(BatteryHullSmall)).add("small_mercury_battery_recycling", 200, 5);
    }
}
