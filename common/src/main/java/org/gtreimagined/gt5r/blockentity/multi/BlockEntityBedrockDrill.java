package org.gtreimagined.gt5r.blockentity.multi;

import lombok.Getter;
import lombok.Setter;
import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityBedrockDrill extends BlockEntityMultiMachine<BlockEntityBedrockDrill> {
    @Getter
    @Setter
    int bedrockOresFound = 0;
    public BlockEntityBedrockDrill(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public boolean checkStructure() {
        bedrockOresFound = 0;
        return super.checkStructure();
    }
}
