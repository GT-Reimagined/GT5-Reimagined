package org.gtreimagined.gt5r.blockentity.multi;

import muramasa.antimatter.blockentity.multi.BlockEntityMultiMachine;
import muramasa.antimatter.capability.IComponentHandler;
import muramasa.antimatter.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.machine.caps.AssemblyLineMultiItemHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockEntityAssemblyLine extends BlockEntityMultiMachine<BlockEntityAssemblyLine> {
    public BlockEntityAssemblyLine(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new AssemblyLineMultiItemHandler<>(this));
    }
}
