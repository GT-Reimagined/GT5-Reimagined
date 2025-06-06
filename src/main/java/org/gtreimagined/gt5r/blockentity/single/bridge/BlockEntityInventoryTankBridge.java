package org.gtreimagined.gt5r.blockentity.single.bridge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.gtreimagined.gt5r.machine.caps.BridgeSidedWrapper;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryTankBridge extends BlockEntityBridge {
    public BlockEntityInventoryTankBridge(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler.set(() -> new MachineFluidHandler<>(this){
            @Override
            public LazyOptional<IFluidHandler> forSide(Direction side) {
                return LazyOptional.of(() -> new BridgeSidedWrapper(this.tile, this, this.tile.coverHandler.orElse(null), side));
            }
        });
    }

    @Override
    protected boolean canBridgeBlockEntity(BlockEntity entity) {
        return entity != null && (entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent() || entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent());
    }

    @Override
    protected <U> boolean canBridgeCapability(Capability<U> capability) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }
}
