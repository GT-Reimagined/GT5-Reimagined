package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.multi.BlockEntityHatch;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.machine.types.HatchMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityHighCapacityOutputHatch extends BlockEntityHatch<BlockEntityHighCapacityOutputHatch> {
    public BlockEntityHighCapacityOutputHatch(HatchMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidHandler.set(() -> new MachineFluidHandler<>(this, 32000 * (getMachineTier().getIntegerId() + 1)));
    }
}
