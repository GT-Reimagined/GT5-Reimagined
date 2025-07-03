package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.tesseract.api.forge.TesseractCaps;

public class BlockEntityUniversalExtender extends BlockEntityInventoryTankExtender{
    public BlockEntityUniversalExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return super.canExtendCapability(capability) || capability == TesseractCaps.ENERGY_HANDLER_CAPABILITY;
    }

    @Override
    public int getStrongRedstonePower(Direction facing) {
        if (facing == this.getOutputFacing() && getCachedBlockEntity(facing) instanceof BlockEntityMachine<?> machine){
            return machine.getStrongRedstonePower(this.getFacing().getOpposite());
        }
        return super.getStrongRedstonePower(facing);
    }

    @Override
    public int getWeakRedstonePower(Direction facing) {
        if (facing == this.getOutputFacing() && getCachedBlockEntity(facing) instanceof BlockEntityMachine<?> machine){
            return machine.getWeakRedstonePower(this.getFacing().getOpposite());
        }
        return super.getWeakRedstonePower(facing);
    }
}
