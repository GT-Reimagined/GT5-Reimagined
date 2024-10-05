package muramasa.gregtech.loader.machines;

import muramasa.antimatter.recipe.ingredient.RecipeIngredient;
import muramasa.gregtech.data.GT5RItems;
import muramasa.gregtech.data.RecipeMaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static muramasa.antimatter.Ref.L;
import static muramasa.gregtech.data.Materials.SquidInk;

public class PrinterLoader {
    public static void init(){
        ItemStack dataStick = new ItemStack(GT5RItems.DataStick);
        CompoundTag display = dataStick.getOrCreateTagElement("display");
        CompoundTag name = new CompoundTag();
        name.putString("text", "With Scanned Book Data");
        display.put("Name", name);
        RecipeMaps.PRINTING.RB().ii(RecipeIngredient.of(Items.PAPER, 3), RecipeIngredient.of(dataStick)).fi(SquidInk.getLiquid(L)).fake().add("printed_pages", 400, 2);
    }
}
