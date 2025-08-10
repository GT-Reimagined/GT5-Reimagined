package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;

import java.util.function.Predicate;

public class BridgeSidedItemWrapper extends ExtenderSidedItemWrapper {
    public BridgeSidedItemWrapper(BlockEntityMachine<?> blockEntity, Direction side, CoverHandler<?> coverHandler, Predicate<Direction> inputFunction, Predicate<Direction> outputFunction, IItemHandlerModifiable... itemHandler) {
        super(blockEntity, side, coverHandler, inputFunction, outputFunction, itemHandler);
    }

    @Override
    LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        BlockEntity entity = blockEntity.getCachedBlockEntity(facing.getOpposite());
        if (entity == null) return LazyOptional.empty();
        return entity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing);
    }
}
