package muramasa.gregtech.cover;

import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.blockentity.pipe.BlockEntityFluidPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.capability.IFilterableHandler;
import muramasa.antimatter.capability.IGuiHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.data.AntimatterDefaultTools;
import muramasa.antimatter.gui.ButtonOverlay;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.machine.event.IMachineEvent;
import muramasa.antimatter.tool.AntimatterToolType;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.cover.base.CoverBasicRedstoneOutput;
import muramasa.gregtech.data.GregTechCovers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
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
        FluidContainer fluidContainer = handler.getTile() instanceof BlockEntityMachine<?> machine ? machine.fluidHandler.side(side).orElse(null) : ((BlockEntityFluidPipe<?>)handler.getTile()).getFluidHandler().orElse(null);
        if (fluidContainer != null){
            long scale = IntStream.range(0, fluidContainer.getSize()).mapToLong(fluidContainer::getTankCapacity).sum() / 15L;
            long totalFluid = IntStream.range(0, fluidContainer.getSize()).mapToLong(tankSlot -> {
                FluidHolder fluidHolder = fluidContainer.getFluids().get(tankSlot);
                return fluidHolder.getFluidAmount();
            }).sum();
            if (scale > 0){
                setOutputRedstone(inverted ? (int) (15L - totalFluid / scale) : (int) (totalFluid / scale));
            } else {
                setOutputRedstone(inverted ? 15 : 0);
            }
        }
    }
}
