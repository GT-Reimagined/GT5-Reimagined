package org.gtreimagined.gt5r.blockentity.single.bridge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityTankBridge extends BlockEntityBridge{
    public BlockEntityTankBridge(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected boolean canBridgeBlockEntity(BlockEntity entity) {
        return entity != null && entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent();
    }

    @Override
    protected <U> boolean canBridgeCapability(Capability<U> capability) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }
}
