package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityFluidPipe;
import org.gtreimagined.gtlib.capability.FluidHandler;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.data.Materials;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CoverAirVent extends BaseCover {
    public static String ID = "air_vent";
    public CoverAirVent(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public String getDomain() {
        return GT5Reimagined.ID;
    }

    @Override
    public void onUpdate() {
        BlockEntity tile = handler.getTile();
        if (tile == null) {
            return;
        }
        if (tile.getLevel().isClientSide) return;
        Level level = tile.getLevel();
        Optional<IFluidHandler> cap = Optional.empty();
        if (tile instanceof BlockEntityFluidPipe<?> pipe){
            cap = pipe.getPipeCapHolder().side(side).resolve();
        } else if (tile instanceof BlockEntityMachine<?> machine){
            cap = machine.fluidHandler.map(FluidHandler::getInputTanks);
        }
        BlockPos offset = tile.getBlockPos().relative(side);
        BlockState state = level.getBlockState(offset);
        if (state.isAir() && cap.isPresent()){
            if (level.getGameTime() % 360 == (30 + (60L * side.get3DDataValue()))){
                if (level.dimension() == Level.OVERWORLD){
                    cap.get().fill(Materials.Air.getGas(64000), FluidAction.EXECUTE);
                } else if (level.dimension() == Level.NETHER){
                    cap.get().fill(Materials.NetherAir.getGas(64000), FluidAction.EXECUTE);
                } else if (level.dimension() == Level.END){
                    cap.get().fill(Materials.EnderAir.getGas(64000), FluidAction.EXECUTE);
                }
            }
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected String getRenderId() {
        return ID;
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }
}
