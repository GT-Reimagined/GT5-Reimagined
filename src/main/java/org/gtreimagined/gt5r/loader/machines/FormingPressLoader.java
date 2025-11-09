package org.gtreimagined.gt5r.loader.machines;

import org.gtreimagined.gtlib.item.ItemBasic;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient.of;
import static org.gtreimagined.gt5r.data.RecipeMaps.FORMING_PRESS;

public class FormingPressLoader {
    public static void init() {
        molds(GTCoreItems.AnvilMold);
        molds(GTCoreItems.BallMold);
        molds(GTCoreItems.BlockMold);
        molds(GTCoreItems.BottleMold);
        molds(GTCoreItems.CoinageMold);
        molds(GTCoreItems.GearMold);
        molds(GTCoreItems.SmallGearMold);
        molds(GTCoreItems.IngotMold);
        molds(GTCoreItems.NuggetMold);
        molds(GTCoreItems.PlateMold);

        molds(GTCoreItems.BoltShape);
        molds(GTCoreItems.GearShape);
        molds(GTCoreItems.SmallGearShape);
        molds(GTCoreItems.RingShape);
        molds(GTCoreItems.RodShape);
        molds(GTCoreItems.HugePipeShape);
        molds(GTCoreItems.LargePipeShape);
        molds(GTCoreItems.NormalPipeShape);
        molds(GTCoreItems.SmallPipeShape);
        molds(GTCoreItems.TinyPipeShape);
        molds(GTCoreItems.WireShape);
        molds(GTCoreItems.PlateShape);
    }
    private static void molds(ItemBasic mold){
        FORMING_PRESS.RB().ii(of(GTCoreItems.EmptyShape,1),of(mold,1).setNoConsume()).io(new ItemStack(mold,1)).add(mold.getId(),120,20);
    }
}
