package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gt5r.blockentity.single.BlockEntitySmallHeatExchanger;
import org.gtreimagined.gt5r.data.Materials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverSecondaryOutput extends BaseCover {
    public CoverSecondaryOutput(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public <T> boolean blocksInput(Class<T> cap, @Nullable Direction side) {
        return true;
    }

    @Override
    public void onTickPost() {
        if (handler.getTile() instanceof BlockEntitySmallHeatExchanger heatExchanger){
            if (heatExchanger.fluidHandler.isPresent()){
                MachineFluidHandler<?> fluidHandler = heatExchanger.fluidHandler.get();
                int outputfluid = fluidHandler.getOutputTanks().getFirstAvailableTank(Materials.Steam.getGas(1), true);
                if (outputfluid >= 0){
                    FluidTank outputTank = fluidHandler.getOutputTanks().getTank(outputfluid);
                    FluidUtils.getFluidHandler(heatExchanger.getLevel(), heatExchanger.getBlockPos().relative(this.side), this.side.getOpposite()).ifPresent(f -> {
                        tryFluidTransfer(f, outputTank, Integer.MAX_VALUE, true);
                    });
                }
            }
        }
    }

    public void tryFluidTransfer(IFluidHandler fluidDestination, IFluidHandler fluidSource, int maxAmount, boolean doTransfer) {
        for (int i = 0; i < fluidSource.getTanks(); i++) {
            FluidStack fluid = fluidSource.getFluidInTank(i);
            FluidUtil.tryFluidTransfer(fluidDestination, fluidSource, Utils.ca(Math.min(fluid.getAmount(), maxAmount), fluid), doTransfer);
        }
    }
}
