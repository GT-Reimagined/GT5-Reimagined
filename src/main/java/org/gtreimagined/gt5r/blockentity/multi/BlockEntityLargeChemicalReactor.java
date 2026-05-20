package org.gtreimagined.gt5r.blockentity.multi;

import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;

public class BlockEntityLargeChemicalReactor extends BlockEntityParallelMultiblock<BlockEntityLargeChemicalReactor> {
    private BlockCoil.CoilData coilData;

    public BlockEntityLargeChemicalReactor(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 16));
    }

//    @Override
//    public void onRecipeFound() {
////        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
////        this.mEfficiencyIncrease = 10000;
//
//        int tier = Utils.getVoltageTier(getMaxInputVoltage());
//        EUt = (-4 * (1 << tier - 1) * (1 << tier - 1) * level / discount);
//        maxProgress = Math.max(1, 512 / (1 << tier - 1));
//    }

    public void setCoilData(BlockCoil.CoilData coilData) {
        this.coilData = coilData;
    }

    public BlockCoil.CoilData getCoilData() {
        return coilData;
    }
}
