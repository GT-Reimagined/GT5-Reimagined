package org.gtreimagined.gt5r.blockentity.multi;

import org.gtreimagined.gtlib.blockentity.multi.BlockEntityBasicMultiMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityCokeOven extends BlockEntityBasicMultiMachine<BlockEntityCokeOven> {

    public BlockEntityCokeOven(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean allowsFakeTiles() {
        return true;
    }

    @Override
    public int maxShares() {
       return 0;
    }
}
