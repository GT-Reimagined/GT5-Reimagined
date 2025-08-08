package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.single.BlockEntityGenerator;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.IRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntitySteamTurbine extends BlockEntityGenerator<BlockEntitySteamTurbine> {
    public BlockEntitySteamTurbine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public int consumedFluidPerOperation(IRecipe r) {
                return getEfficiency();
            }

            @Override
            protected long calculateGeneratorProduction(IRecipe r) {
                return r.getPower() * 3;
            }
        });
    }
}
