package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.machine.caps.ExtenderSidedFluidWrapper;
import org.gtreimagined.gt5r.machine.caps.ExtenderSidedItemWrapper;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryTankExtender extends BlockEntityExtender {
    public BlockEntityInventoryTankExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new MachineFluidHandler<>(this){
            @Override
            public LazyOptional<IFluidHandler> forSide(Direction side) {
               return LazyOptional.of(() -> new ExtenderSidedFluidWrapper(this.tile, this, this.tile.coverHandler.orElse(null), side));
            }
        });
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            public LazyOptional<IItemHandler> forSide(Direction side) {
                return LazyOptional.of(() -> new ExtenderSidedItemWrapper(tile, side, tile.coverHandler.map(c -> c).orElse(null), this::allowsInput, this::allowsOutput));
            }
        });
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return false;
    }
}
