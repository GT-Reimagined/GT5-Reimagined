package org.gtreimagined.gt5r.cover;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import muramasa.antimatter.blockentity.BlockEntityCache;
import muramasa.antimatter.capability.FluidHandler;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.capability.machine.MachineFluidHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.FluidPlatformUtils;
import muramasa.antimatter.util.Utils;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.gtreimagined.gt5r.blockentity.single.BlockEntitySmallHeatExchanger;
import org.gtreimagined.gt5r.data.Materials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractGraphWrappers;

public class CoverSecondaryOutput extends BaseCover {
    public CoverSecondaryOutput(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public <T> boolean blocksInput(Class<T> cap, @Nullable Direction side) {
        return true;
    }

    @Override
    public void onUpdate() {
        if (handler.getTile() instanceof BlockEntitySmallHeatExchanger heatExchanger){
            if (heatExchanger.fluidHandler.isPresent()){
                MachineFluidHandler<?> fluidHandler = heatExchanger.fluidHandler.get();
                int outputfluid = fluidHandler.getOutputTanks().getFirstAvailableTank(Materials.Steam.getGas(1), true);
                if (outputfluid >= 0){
                    FluidTank outputTank = fluidHandler.getOutputTanks().getTank(outputfluid);
                    FluidPlatformUtils.getFluidHandler(heatExchanger.getLevel(), heatExchanger.getBlockPos().relative(this.side), this.side.getOpposite()).ifPresent(f -> {
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
