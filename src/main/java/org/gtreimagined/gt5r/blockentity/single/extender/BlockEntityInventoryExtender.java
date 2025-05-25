package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryExtender extends BlockEntityExtender {
    public BlockEntityInventoryExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }
}
