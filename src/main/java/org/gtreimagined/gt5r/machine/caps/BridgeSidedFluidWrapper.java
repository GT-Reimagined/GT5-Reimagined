package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.fluid.IFluidNode;

public class BridgeSidedFluidWrapper extends ExtenderSidedFluidWrapper {
    public BridgeSidedFluidWrapper(BlockEntityMachine<?> entity, IFluidNode fluidHandler, CoverHandler<?> coverHandler, Direction side) {
        super(entity, fluidHandler, coverHandler, side);
    }

    @Override
    LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        BlockEntity entity = blockEntity.getCachedBlockEntity(facing.getOpposite());
        if (entity == null) return LazyOptional.empty();
        return entity.getCapability(ForgeCapabilities.FLUID_HANDLER, facing);
    }
}
