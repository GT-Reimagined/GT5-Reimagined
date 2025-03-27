package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.gui.widget.SyncableTextWidget;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import org.gtreimagined.gt5r.cover.base.CoverBasicTransport;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CoverFluidRegulator extends CoverBasicTransport {

    public static String ID = "pump";

    int fluidLimit;
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

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (object instanceof FluidStack stack && !exportMode.isExport() && handler.getTile() instanceof BlockEntityMachine<?> machine && inputSide){
            if (machine.fluidHandler.isPresent()){
                MachineFluidHandler<?> fluidHandler = machine.fluidHandler.get();
                if (stack.isEmpty()) return true;
                if (fluidLimit > 0 && stack.getAmount() < fluidLimit) return true;
                FluidStack toInsert = fluidLimit > 0 ? Utils.ca(fluidLimit, stack) : stack.copy();
                if (fluidHandler == null) return true;
                int inserted = fluidHandler.fill(toInsert, FluidAction.SIMULATE);
                if (fluidLimit > 0 && inserted < fluidLimit) return true;
                if (inserted > 0){
                    if (!simulate){
                        fluidHandler.fill(toInsert, FluidAction.EXECUTE);
                    }
                    stack.setAmount(stack.getAmount() - inserted);
                }
                return true;
            }
        }
        return super.onTransfer(object, inputSide, simulate);
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
