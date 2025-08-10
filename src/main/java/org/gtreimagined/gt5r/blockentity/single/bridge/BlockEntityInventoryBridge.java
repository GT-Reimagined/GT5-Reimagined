package org.gtreimagined.gt5r.blockentity.single.bridge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.machine.caps.BridgeSidedItemWrapper;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityInventoryBridge extends BlockEntityBridge {
    public BlockEntityInventoryBridge(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler.set(() -> new MachineItemHandler<>(this){
            @Override
            public LazyOptional<IItemHandler> forSide(Direction side) {
                return LazyOptional.of(() -> new BridgeSidedItemWrapper(tile, side, tile.coverHandler.map(c -> c).orElse(null), this::allowsInput, this::allowsOutput));
            }
        });
    }

    @Override
    protected boolean canBridgeBlockEntity(BlockEntity entity) {
        return entity != null && entity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
    }

    @Override
    protected <U> boolean canBridgeCapability(Capability<U> capability) {
        return false;
    }
}
