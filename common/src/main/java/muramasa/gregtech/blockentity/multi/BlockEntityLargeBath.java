package muramasa.gregtech.blockentity.multi;

import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.machine.types.Machine;
import muramasa.gregtech.machine.caps.ParallelRecipeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityLargeBath extends BlockEntityMultiMachine<BlockEntityLargeBath> {
    public BlockEntityLargeBath(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 64));
    }
}
