package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gt5r.blockentity.single.BlockEntityNuclearReactorCore;
import org.jetbrains.annotations.Nullable;

public class CoverReactorOutputSecondary extends BaseCover {


    public CoverReactorOutputSecondary(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return super.canPlace() && handler.getTile() instanceof BlockEntityNuclearReactorCore;
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return new ResourceLocation(GT5RRef.ID + ":block/cover/nuclear");
    }

    @Override
    public void onUpdate() {
        if (handler.getTile() instanceof BlockEntityNuclearReactorCore core){
            if (core.fluidHandler.isPresent()){
                MachineFluidHandler<?> fluidHandler = core.fluidHandler.get();
                FluidStack inputfluid = fluidHandler.getInputTanks().getFluidInTank(0);
                if (inputfluid.getAmount() > fluidHandler.getInputTanks().getTankCapacity(0) / 2){
                    int extra = inputfluid.getAmount() - (fluidHandler.getInputTanks().getTankCapacity(0) / 2);
                    FluidUtils.getFluidHandler(core.getLevel(), core.getBlockPos().relative(this.side), this.side.getOpposite()).ifPresent(f -> {
                        fluidHandler.drainInput(f.fill(Utils.ca(extra, inputfluid), FluidAction.EXECUTE), FluidAction.EXECUTE);
                    });
                }
            }
        }
    }
}
