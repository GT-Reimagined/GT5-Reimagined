package org.gtreimagined.gt5r.blockentity.single.extender;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import org.gtreimagined.gtlib.machine.types.Machine;
import tesseract.api.forge.TesseractCaps;

public class BlockEntityUniversalExtender extends BlockEntityInventoryTankExtender{
    public BlockEntityUniversalExtender(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected <U> boolean canExtendCapability(Capability<U> capability) {
        return super.canExtendCapability(capability) || capability == TesseractCaps.ENERGY_HANDLER_CAPABILITY;
    }
}
