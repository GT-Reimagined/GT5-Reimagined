package org.gtreimagined.gt5r.cover;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.blockentity.pipe.BlockEntityFluidPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class CoverFluidDetector extends CoverBasicRedstoneOutput {
    public CoverFluidDetector(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return (handler.getTile() instanceof BlockEntityMachine<?> machine && machine.fluidHandler.side(side).isPresent()) || handler.getTile() instanceof BlockEntityFluidPipe;
    }

    @Override
    public String getId() {
        return "fluid_detector";
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public void onUpdate() {
        if (handler.getTile().getLevel() == null || handler.getTile().getLevel().isClientSide) return;
        IFluidHandler fluidContainer = handler.getTile() instanceof BlockEntityMachine<?> machine ? machine.fluidHandler.map(f -> f).orElse(null) : ((BlockEntityFluidPipe<?>)handler.getTile()).getFluidHandler().orElse(null);
        if (fluidContainer != null){
            int scale = IntStream.range(0, fluidContainer.getTanks()).map(fluidContainer::getTankCapacity).sum() / 15;
            int totalFluid = IntStream.range(0, fluidContainer.getTanks()).map(tankSlot -> {
                FluidStack fluidHolder = fluidContainer.getFluidInTank(tankSlot);
                return fluidHolder.getAmount();
            }).sum();
            if (scale > 0){
                setOutputRedstone(inverted ? (15 - totalFluid / scale) : (totalFluid / scale));
            } else {
                setOutputRedstone(inverted ? 15 : 0);
            }
        }
    }
}
