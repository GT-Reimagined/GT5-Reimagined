package org.gtreimagined.gt5r.loader.machines;

import muramasa.antimatter.recipe.ingredient.FluidIngredient;
import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.antimatter.util.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.data.GT5RTags;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static muramasa.antimatter.Ref.L;
import static org.gtreimagined.gt5r.data.Materials.SquidInk;

public class PrinterLoader {
    public static void init(){
        RecipeMaps.PRINTING.RB().ii(RecipeIngredient.of(Items.PAPER, 3), RecipeIngredient.of(new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("With Scanned Book Data"))).setNoConsume()).fi(SquidInk.getLiquid(L)).io(GT5RItems.PrintedPages).fake().add("printed_pages", 400, 2);
        RecipeMaps.PRINTING.RB().ii(RecipeIngredient.of(Items.PAPER, 1), RecipeIngredient.of(new ItemStack(GT5RItems.DataStick).setHoverName(Utils.literal("With Scanned Blueprint Data"))).setNoConsume()).fi(FluidIngredient.of(GT5RTags.BLUE_DYE, L)).io(GTCoreItems.Blueprint).fake().add("blueprint", 400, 2);
    }
}
