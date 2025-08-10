package org.gtreimagined.gt5r.cover;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.cover.base.CoverBasicTransport;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityFluidPipe;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.gui.widget.SyncableTextWidget;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class CoverFluidRegulator extends CoverBasicTransport {

    public static String ID = "pump";

    int fluidLimit;
    int usedFluidLimitIn = 0;
    int usedFluidLimitOut = 0;
    public CoverFluidRegulator(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        Objects.requireNonNull(tier);
        fluidLimit = CoverPump.speeds.get(tier);
        addGuiCallback(t -> {
            t.addButton(52,53, ButtonOverlay.MINUS, true);
            t.addButton(106,53, ButtonOverlay.PLUS, true);
            t.addWidget(SyncableTextWidget.build(i -> {
                CoverFluidRegulator itemRegulator = (CoverFluidRegulator) i;
                if (itemRegulator.fluidLimit == 0) return "N/A";
                return String.valueOf(itemRegulator.fluidLimit);
            }, 4210752, true).setSize(61, 58, 36, 18));
        });
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicDepthModel();
    }

    boolean transferring = false;

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (transferring) return false;
        if (fluidLimit <= 0) return false;
        if (object instanceof FluidStack stack) {
            if (exportMode.isExport() && !inputSide && handler.getTile() instanceof BlockEntityFluidPipe<?> fluidPipe) {
                int unusedFluidLimit = fluidLimit - usedFluidLimitOut;
                if (unusedFluidLimit <= 0) return true;
                if (!simulate){
                    BlockEntity neighbor = fluidPipe.getCachedBlockEntity(side);
                    if (neighbor != null){
                        LazyOptional<IFluidHandler> cap = neighbor.getCapability(ForgeCapabilities.FLUID_HANDLER, side.getOpposite());
                        transferring = true;
                        boolean transfer = transferFluid(stack, cap.orElse(null), usedFluidLimitOut, false, i -> usedFluidLimitOut += i);
                        transferring = false;
                        return transfer;
                    }
                }
            }
            if (!exportMode.isExport() && inputSide) {
                IFluidHandler handler1 = null;
                if (handler.getTile() instanceof BlockEntityMachine<?> machine) {
                    if (machine.fluidHandler.isPresent()) {
                        handler1 = machine.fluidHandler.side(side).orElse(null);
                    }
                }
                if (handler.getTile() instanceof BlockEntityFluidPipe<?> fluidPipe) {
                    if (fluidPipe.getFluidHandler().isPresent()) {
                        handler1 = fluidPipe.getFluidHandler().get();
                    }
                }
                if (stack.isEmpty()) return true;
                transferring = true;
                boolean transfer = transferFluid(stack, handler1, usedFluidLimitIn, simulate, i -> usedFluidLimitIn += i);
                transferring = false;
                return transfer;
            }
        }
        return super.onTransfer(object, inputSide, simulate);
    }

    private boolean transferFluid(FluidStack stack, IFluidHandler destination, int usedFluidLimit, boolean simulate, Consumer<Integer> usedFluidLimitSetter) {
        int unusedFluidLimit = fluidLimit - usedFluidLimit;
        if (unusedFluidLimit <= 0) return true;
        FluidStack toInsert = Utils.ca(unusedFluidLimit, stack);
        if (destination == null) return true;
        int inserted = destination.fill(toInsert, FluidAction.SIMULATE);
        if (inserted > 0) {
            if (!simulate) {
                destination.fill(toInsert, FluidAction.EXECUTE);
                usedFluidLimitSetter.accept(inserted);
            }
            stack.setAmount(stack.getAmount() - inserted);
        }
        return true;
    }

    @Override
    public <T> boolean blocksCapability(Class<T> cap, Direction side) {
        return cap != IFluidHandler.class;
    }

    @Override
    public void onUpdate() {
        //Pump acts on each tick.
        if (handler.getTile().getLevel().isClientSide) return;
        if (handler.getTile() == null) return;
        BlockPos from = handler.getTile().getBlockPos();
        BlockPos to = handler.getTile().getBlockPos().relative(side);
        Direction fromSide = side;
        if (exportMode == ImportExportMode.IMPORT || exportMode == ImportExportMode.IMPORT_EXPORT){
            from = handler.getTile().getBlockPos().relative(side);
            to = handler.getTile().getBlockPos();
            fromSide = side.getOpposite();
        }
        BlockPos finalTo = to;
        if (canMove(side)) {
            Direction finalFromSide = fromSide;
            FluidUtils.getFluidHandler(handler.getTile().getLevel(), from, fromSide).ifPresent(ih -> FluidUtils.getFluidHandler(handler.getTile().getLevel(), finalTo, finalFromSide.getOpposite()).ifPresent(other -> Utils.transferFluids(ih, other, fluidLimit > 0 ? fluidLimit : CoverPump.speeds.get(tier))));
        }
        usedFluidLimitIn = 0;
        usedFluidLimitOut = 0;
    }
    protected boolean canMove(Direction side){
        if (redstoneMode != RedstoneMode.NO_WORK){
            boolean powered = isPowered(side);
            return (redstoneMode == RedstoneMode.INVERTED) != powered;
        }
        return true;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        fluidLimit = nbt.getInt("fluidLimit");
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putInt("fluidLimit", fluidLimit);
        return nbt;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){

            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            int button = ev.data[1];
            boolean shiftHold = ev.data[0] != 0;
            if (button == 2){
                if (fluidLimit > 0){
                    fluidLimit-= shiftHold ? 16 : 1;
                    if (fluidLimit < 0) fluidLimit = 0;
                    handler.getTile().setChanged();
                }
            }
            if (button == 3){
                if (fluidLimit < CoverPump.speeds.get(tier)){
                    fluidLimit+= shiftHold ? 16 : 1;
                    if (fluidLimit > CoverPump.speeds.get(tier)) fluidLimit = CoverPump.speeds.get(tier);
                    handler.getTile().setChanged();
                }
            }
        }
    }
}
