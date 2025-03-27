package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.multi.BlockEntityHatch;
import org.gtreimagined.gtlib.capability.machine.MachineCoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.machine.types.HatchMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityInputHatch extends BlockEntityHatch<BlockEntityInputHatch> {
    public BlockEntityInputHatch(HatchMachine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidHandler.set(() -> new MachineFluidHandler<>(this, 8000 * (getMachineTier().getIntegerId() + 1)){
            @Override
            public boolean canInput(Direction direction) {
                return super.canInput(direction) && direction == coverHandler.map(MachineCoverHandler::getOutputFacing).orElse(null);
            }
        });
    }
}
