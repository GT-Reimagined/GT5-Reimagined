package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gt5r.blockentity.single.bridge.BlockEntityBridge;
import org.gtreimagined.gt5r.blockentity.single.extender.BlockEntityExtender;

public class BridgeFluidHandler extends ExtenderFluidHandler<BlockEntityBridge> {
    public BridgeFluidHandler(BlockEntityBridge tile) {
        super(tile);
    }

    @Override
    LazyOptional<IFluidHandler> getFluidHandler(Direction facing) {
        BlockEntity entity = tile.getCachedBlockEntity(facing);
        return entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
    }
}
