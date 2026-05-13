package org.gtreimagined.gt5r.integration.botania;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gt5r.data.GT5RBlocks;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.block.PetalApothecary.State;
import vazkii.botania.common.block.PetalApothecaryBlock;
import vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity;

import java.util.Objects;

public final class PetalApothecaryWrapper implements IFluidHandler {
    private BlockState state;
    private final Level level;
    private final BlockPos pos;

    public PetalApothecaryWrapper(BlockState state, Level level, BlockPos pos) {
        this.state = state;
        this.level = level;
        this.pos = pos;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int i) {
        state = level.getBlockState(pos);
        if (state.getBlock() instanceof PetalApothecaryBlock) {
            return new FluidStack(state.getValue(PetalApothecaryBlock.FLUID).asVanilla(), 1000);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int i) {
        return 1000;
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return fluidStack.getFluid() == Fluids.WATER || fluidStack.getFluid() == Fluids.WATER;
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        this.state = level.getBlockState(pos);
        if (fluidStack.getAmount() < 1000) return 0;
        if (state.getValue(PetalApothecaryBlock.FLUID) == State.EMPTY){
            if (fluidStack.getFluid() == Fluids.WATER){
                if (fluidAction.execute()) this.level.setBlock(pos, state.setValue(PetalApothecaryBlock.FLUID, State.WATER), 3);
                return  1000;
            }
            if (fluidStack.getFluid() == Fluids.LAVA){
                if (fluidAction.execute()) this.level.setBlock(pos, state.setValue(PetalApothecaryBlock.FLUID, State.LAVA), 3);
                return  1000;
            }
        }
        return 0;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        if (fluidStack.getFluid() == Fluids.WATER || fluidStack.getFluid() == Fluids.LAVA){
            return drain(fluidStack.getAmount(), fluidAction);
        }
        return FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int i, FluidAction fluidAction) {
        this.state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof PetalApothecaryBlock)) return FluidStack.EMPTY;
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof PetalApothecaryBlockEntity petalApothecaryBlockEntity){
            if (!petalApothecaryBlockEntity.isEmpty()){
                return FluidStack.EMPTY;
            }
        }
        Fluid fluid = state.getValue(PetalApothecaryBlock.FLUID).asVanilla();
        if (fluidAction.execute()) this.level.setBlock(pos, state.setValue(PetalApothecaryBlock.FLUID, State.EMPTY), 3);
        return new FluidStack(fluid, 1000);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PetalApothecaryWrapper) obj;
        return Objects.equals(this.state, that.state) &&
                Objects.equals(this.level, that.level) &&
                Objects.equals(this.pos, that.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, level, pos);
    }

    @Override
    public String toString() {
        return "PetalApothecaryWrapper[" +
                "state=" + state + ", " +
                "level=" + level + ", " +
                "pos=" + pos + ']';
    }

}
