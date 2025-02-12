package org.gtreimagined.gt5r.loader.machines;

import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;

public class ScannerLoader {
    public static void init(){
        ItemStack rawDataStick = new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("Raw Prospection Data"));
        ItemStack analyzedDataStick = new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("Analyzed Prospection Data"));
        RecipeMaps.SCANNER.RB().ii(RecipeIngredient.of(rawDataStick)).io(analyzedDataStick).fake().add("prospection_data_stick", 1000, 32);
        ItemStack emptyDataStick = new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("Stick to save it to"));
        ItemStack outputDataStick = new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("Scanned Book Data"));
        RecipeMaps.SCANNER.RB().ii(RecipeIngredient.of(Items.WRITTEN_BOOK), RecipeIngredient.of(emptyDataStick)).io(outputDataStick).fake().add("book_data_stick", 128, 32);
        RecipeMaps.SCANNER.RB().ii(RecipeIngredient.of(GTCoreItems.Blueprint), RecipeIngredient.of(emptyDataStick)).io(new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("Scanned Blueprint Data"))).fake().add("blueprint_data_stick", 128, 32);
    }
}
