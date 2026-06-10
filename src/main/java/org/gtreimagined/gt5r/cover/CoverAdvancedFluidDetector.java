package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class CoverAdvancedFluidDetector extends BaseCover implements IFilterableHandler {
    boolean inverted = false;
    int outputRedstone = 0;

    private final CoverFluidFilter filter;
    public CoverAdvancedFluidDetector(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.filter = new CoverFluidFilter(source, null, side, GT5RCovers.COVER_FLUID_FILTER);
        filter.onCreate();
        this.gui.getSlots().add(SlotType.STORAGE, 88, 34);
    }

    @Override
    public void addWidgets(ModularPanel<?> panel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("redstone_mode", new BooleanSyncValue(() -> this.inverted, b -> this.inverted = b).allowC2S());
        panel.child(new CycleButtonWidget().stateCount(2).pos(70, 34).size(16, 16).syncHandler("redstone_mode")
                .stateOverlay(false, GTGuiTextures.TORCH_OFF)
                .stateOverlay(true, GTGuiTextures.TORCH_ON)
                .addTooltip(0, Text.lang("tooltip.gt5r.redstone_mode.normal"))
                .addTooltip(1, Text.lang("tooltip.gt5r.redstone_mode.inverted")));
    }

    @Override
    public boolean canPlace() {
        return handler.getTile() instanceof BlockEntityMachine<?> machine && machine.fluidHandler.side(side).isPresent();
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
    public boolean hasGui() {
        return true;
    }

    @Override
    public void onUpdate() {
        if (handler.getTile().getLevel() == null || handler.getTile().getLevel().isClientSide) return;
        if (handler.getTile() instanceof BlockEntityMachine<?> machine && machine.fluidHandler.side(side).isPresent()){
            IFluidHandler fluidContainer = machine.fluidHandler.side(side).resolve().get();
            int oldRedstone = outputRedstone;
            int scale = IntStream.range(0, fluidContainer.getTanks()).map(tankSlot -> {
                FluidStack fluidHolder = fluidContainer.getFluidInTank(tankSlot);
                if (!getInventory(SlotType.STORAGE).getStackInSlot(0).isEmpty() && filter.onTransfer(fluidHolder, true, true)) return 0;
                return fluidContainer.getTankCapacity(tankSlot);
            }).sum() / 15;
            int totalFluid = IntStream.range(0, fluidContainer.getTanks()).map(tankSlot -> {
                FluidStack fluidHolder = fluidContainer.getFluidInTank(tankSlot);
                if (!getInventory(SlotType.STORAGE).getStackInSlot(0).isEmpty() && filter.onTransfer(fluidHolder, true, true)) return 0;
                return fluidHolder.getAmount();
            }).sum();
            if (scale > 0){
                outputRedstone = inverted ? (15 - totalFluid / scale) : (totalFluid / scale);
            } else {
                outputRedstone = inverted ? 15 : 0;
            }
            if (outputRedstone != oldRedstone){
                markAndNotifySource();
            }
        }
    }

    @Override
    public int getWeakPower() {
        return outputRedstone;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.put("filter", filter.serialize());
        nbt.putBoolean("inverted", inverted);
        return nbt;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){
            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            if (ev.data[1] == 0){
                inverted = !inverted;
                this.handler.getTile().setChanged();
            }
        }
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        if (nbt.contains("filter")) {
            filter.deserialize(nbt.getCompound("filter"));
        }
        inverted = nbt.getBoolean("inverted");
    }
    @Override
    public void onMachineEvent(IGuiHandler tile, IMachineEvent event, int... data) {
        if (tile == this && event == SlotType.STORAGE){
            ItemStack slotStack = getInventory(SlotType.STORAGE).getStackInSlot(data[0]);
            if (slotStack.isEmpty()){
                filter.clearFilter();
            } else {
                filter.addInfoFromStack(slotStack);
            }
        }
        super.onMachineEvent(tile, event, data);
    }

    @Override
    public void addInfoFromStack(ItemStack stack) {
        super.addInfoFromStack(stack);
        onMachineEvent(this, SlotType.STORAGE, 0);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        return stack.getItem() == GT5RCovers.COVER_FLUID_FILTER.getItem().getItem();
    }
}
