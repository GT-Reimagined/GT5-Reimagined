package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.fluid.FluidHandlerSidedWrapper;
import org.gtreimagined.gtlib.capability.fluid.IFluidNode;
import org.jetbrains.annotations.NotNull;

public class ExtenderSidedFluidWrapper extends FluidHandlerSidedWrapper {

    BlockEntityMachine<?> blockEntity;

    public ExtenderSidedFluidWrapper(BlockEntityMachine<?> entity, IFluidNode fluidHandler, CoverHandler<?> coverHandler, Direction side) {
        super(fluidHandler, coverHandler, side);
        this.blockEntity = entity;
    }

    LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        Direction side = facing == blockEntity.getFacing() ? blockEntity.getOutputFacing() : blockEntity.getFacing();
        BlockEntity entity = blockEntity.getCachedBlockEntity(side);
        return entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (coverHandler != null) {
            if (coverHandler.blocksInput(IFluidHandler.class, side)) {
                return 0;
            }
            int oldAmount = resource.getAmount();
            if (coverHandler.onTransfer(resource, side, true, action.simulate()))
                return oldAmount - resource.getAmount();
        }
        return getFluidHandler(side).map(f -> f.fill(resource, action)).orElse(0);
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (coverHandler != null && (coverHandler.blocksOutput(IFluidHandler.class, side) || coverHandler.onTransfer(resource, side, false, action.simulate()))) {
            return FluidStack.EMPTY;
        }
        return getFluidHandler(side).map(f -> f.drain(resource, action)).orElse(FluidStack.EMPTY);
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return getFluidHandler(side).map(f -> f.getFluidInTank(tank)).orElse(FluidStack.EMPTY);
    }

    @Override
    public int getTanks() {
        return getFluidHandler(side).map(IFluidHandler::getTanks).orElse(0);
    }

    @Override
    public int getTankCapacity(int tank) {
        return getFluidHandler(side).map(f -> f.getTankCapacity(tank)).orElse(0);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return getFluidHandler(side).map(f -> f.isFluidValid(tank, stack)).orElse(false);
    }
}
