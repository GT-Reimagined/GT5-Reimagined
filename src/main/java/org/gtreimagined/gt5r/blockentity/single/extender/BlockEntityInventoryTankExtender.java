package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryTankExtender extends BlockEntityExtender {
    public BlockEntityInventoryTankExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }
}
