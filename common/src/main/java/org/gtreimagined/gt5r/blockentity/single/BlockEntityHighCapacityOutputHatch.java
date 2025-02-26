package org.gtreimagined.gt5r.blockentity.single;

import muramasa.antimatter.blockentity.multi.BlockEntityHatch;
import muramasa.antimatter.capability.machine.MachineCoverHandler;
import muramasa.antimatter.capability.machine.MachineFluidHandler;
import muramasa.antimatter.machine.types.HatchMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityHighCapacityOutputHatch extends BlockEntityHatch<BlockEntityHighCapacityOutputHatch> {
    public BlockEntityHighCapacityOutputHatch(HatchMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidHandler.set(() -> new MachineFluidHandler<>(this, 32000 * (getMachineTier().getIntegerId() + 1)));
    }
}
