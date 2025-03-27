package org.gtreimagined.gt5r.blockentity.multi;

import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityOilCrackingUnit extends BlockEntityMultiMachine<BlockEntityOilCrackingUnit> {

    public BlockEntityOilCrackingUnit(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public int maxShares() {
        return 0;
    }
}
