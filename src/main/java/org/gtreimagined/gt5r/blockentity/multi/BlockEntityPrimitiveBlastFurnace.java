package org.gtreimagined.gt5r.blockentity.multi;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.gtreimagined.gtlib.blockentity.IFuelMachine;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityBasicMultiMachine;
import org.gtreimagined.gtlib.capability.machine.CookingRecipeHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import org.gtreimagined.gtlib.recipe.ingredient.RecipeIngredient;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import org.gtreimagined.gtlib.util.int3;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.data.GT5RBlocks;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.GEM;

public class BlockEntityPrimitiveBlastFurnace extends BlockEntityBasicMultiMachine<BlockEntityPrimitiveBlastFurnace> implements IFuelMachine {

    public BlockEntityPrimitiveBlastFurnace(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new CookingRecipeHandler<>(this, 2.0f){
            @Override
            protected Ingredient getBurnable() {
                return RecipeIngredient.ofIngredient(1, GEM.getMaterialTag(Charcoal), GEM.getMaterialTag(CoalCoke), GEM.getMaterialTag(LigniteCoke));
            }
        });
    }

    @Override
    public void onMachineStarted(IRecipe r) {
        super.onMachineStarted(r);
        int3 controller = new int3(getBlockPos(), getFacing());
        controller.back(1);
        getLevel().setBlock(controller, GT5RBlocks.LAVA.defaultBlockState(), 2);
        controller.above(1);
        getLevel().setBlock(controller, GT5RBlocks.LAVA.defaultBlockState(), 2);
    }

    @Override
    public void onMachineStop() {
        super.onMachineStop();
        int3 controller = new int3(getBlockPos(), getFacing());
        controller.back(1);
        getLevel().setBlock(controller, Blocks.AIR.defaultBlockState(), 2);
        controller.above(1);
        getLevel().setBlock(controller, Blocks.AIR.defaultBlockState(), 2);
    }

    @Override
    public int maxShares() {
       return 0;
    }

    @Override
    public int getFuel() {
        return recipeHandler.map(r -> ((CookingRecipeHandler<?>)r).getBurnDuration()).orElse(0);
    }

    @Override
    public int getMaxFuel() {
        return recipeHandler.map(r -> ((CookingRecipeHandler<?>)r).getMaxBurn()).orElse(0);
    }
}
