package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.item.SidedCombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class ExtenderSidedItemWrapper extends SidedCombinedInvWrapper {

    protected BlockEntityMachine<?> blockEntity;
    public ExtenderSidedItemWrapper(BlockEntityMachine<?> blockEntity, Direction side, CoverHandler<?> coverHandler, Predicate<Direction> inputFunction, Predicate<Direction> outputFunction, IItemHandlerModifiable... itemHandler) {
        super(side, coverHandler, inputFunction, outputFunction, itemHandler);
        this.blockEntity = blockEntity;
    }

    LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        Direction side = facing == blockEntity.getFacing() ? blockEntity.getOutputFacing() : blockEntity.getFacing();
        BlockEntity entity = blockEntity.getCachedBlockEntity(side);
        if (entity == null) return LazyOptional.empty();
        return entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite());
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!inputFunction.test(side)) return stack;
        if (coverHandler != null) {
            if (coverHandler.blocksInput(IItemHandler.class, side)) {
                return stack;
            }
            ItemStack copy = stack.copy();
            if (coverHandler.onTransfer(copy, side, true, simulate)) {
                return copy;
            }
        }
        return getItemHandler(side).map(i -> i.insertItem(slot, stack, simulate)).orElse(stack);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!outputFunction.test(side)) return ItemStack.EMPTY;
        if (coverHandler != null && (coverHandler.blocksOutput(IItemHandler.class, side) || coverHandler.onTransfer(getStackInSlot(slot), side, false, simulate)))
            return ItemStack.EMPTY;
        return getItemHandler(side).map(i -> i.extractItem(slot, amount, simulate)).orElse(ItemStack.EMPTY);
    }

    @Override
    public int getSlots() {
        return getItemHandler(side).map(IItemHandler::getSlots).orElse(0);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return getItemHandler(side).map(i -> i.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    @Override
    public int getSlotLimit(int slot) {
        return getItemHandler(side).map(i -> i.getSlotLimit(slot)).orElse(0);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return getItemHandler(side).map(i -> i.isItemValid(slot, stack)).orElse(false);
    }
}
