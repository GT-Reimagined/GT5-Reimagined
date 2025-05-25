package org.gtreimagined.gt5r.blockentity.single.bridge;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.gtreimagined.gt5r.blockentity.single.extender.BlockEntityExtender;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.IExtendingBlockEntity;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BlockEntityBridge extends BlockEntityMachine<BlockEntityBridge> implements IExtendingBlockEntity {
    public BlockEntityBridge(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public BlockEntity getExtendedBlockEntity(Direction side) {
        BlockEntity entity = getCachedBlockEntity(side);
        return canBridgeBlockEntity(entity) && isNotExtendingBlockEntity(entity) ? entity : null;
    }

    private boolean isNotExtendingBlockEntity(BlockEntity entity) {
        return !(entity instanceof BlockEntityBridge) && !(entity instanceof BlockEntityExtender);
    }

    protected abstract boolean canBridgeBlockEntity(BlockEntity entity);

    protected abstract <U> boolean canBridgeCapability(Capability<U> capability);

    @Override
    public @NotNull <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
        if (side != null && canBridgeCapability(cap)) {
            BlockEntity neighbor = getCachedBlockEntity(side);
            if (isNotExtendingBlockEntity(neighbor)) {
                if (neighbor != null) {
                    return neighbor.getCapability(cap, side);
                }
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onBlockUpdate(BlockPos neighbor) {
        super.onBlockUpdate(neighbor);
        Direction facing = Utils.getOffsetFacing(this.getBlockPos(), neighbor);
        BlockPos offset = getBlockPos().relative(facing.getOpposite());
        getLevel().neighborChanged(offset, getLevel().getBlockState(offset).getBlock(), getBlockPos());
    }
}
