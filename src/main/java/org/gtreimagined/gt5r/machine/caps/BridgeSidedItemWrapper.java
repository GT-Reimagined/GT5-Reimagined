package org.gtreimagined.gt5r.machine.caps;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.fluid.IFluidNode;

import java.util.function.Predicate;

public class BridgeSidedItemWrapper extends ExtenderSidedItemWrapper {
    public BridgeSidedItemWrapper(BlockEntityMachine<?> blockEntity, Direction side, CoverHandler<?> coverHandler, Predicate<Direction> inputFunction, Predicate<Direction> outputFunction, IItemHandlerModifiable... itemHandler) {
        super(blockEntity, side, coverHandler, inputFunction, outputFunction, itemHandler);
    }

    @Override
    LazyOptional<IItemHandler> getItemHandler(Direction facing) {
        BlockEntity entity = blockEntity.getCachedBlockEntity(facing.getOpposite());
        return entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
    }
}
