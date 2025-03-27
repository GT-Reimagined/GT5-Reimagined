package org.gtreimagined.gt5r.blockentity.multi;

import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.machine.caps.AssemblyLineMultiItemHandler;

public class BlockEntityAssemblyLine extends BlockEntityMultiMachine<BlockEntityAssemblyLine> {
    public BlockEntityAssemblyLine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new AssemblyLineMultiItemHandler<>(this));
    }
}
