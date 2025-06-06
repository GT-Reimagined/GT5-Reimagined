package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.gtreimagined.gt5r.machine.caps.ExtenderSidedWrapper;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryTankExtender extends BlockEntityExtender {
    public BlockEntityInventoryTankExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new MachineFluidHandler<>(this){
            @Override
            public LazyOptional<IFluidHandler> forSide(Direction side) {
               return LazyOptional.of(() -> new ExtenderSidedWrapper(this.tile, this, this.tile.coverHandler.orElse(null), side));
            }
        });
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }
}
