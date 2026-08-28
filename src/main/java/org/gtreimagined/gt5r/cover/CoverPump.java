package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.IPanelHandler;
import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.ItemDrawable;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.RichTooltip;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.FluidSlotSyncHandler;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.ButtonWidget;
import brachy.modularui.widgets.CycleButtonWidget;
import com.google.common.collect.ImmutableMap;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.drawable.GTDrawableStack;
import org.gtreimagined.gtlib.mui.widgets.GTFluidSlot;
import org.gtreimagined.gtlib.util.FluidUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.gtreimagined.gt5r.cover.base.CoverBasicTransport;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class CoverPump extends CoverBasicTransport implements IFilterableHandler {

    public static String ID = "pump";
    private final CoverFluidFilter filter;

    public static final Map<Tier, Integer> speeds = ImmutableMap.<Tier, Integer>builder().
            put(Tier.LV, 640 / 20)
            .put(Tier.MV, 2560 / 20)
            .put(Tier.HV, 10240 / 20)
            .put(Tier.EV, 40960 / 20)
            .put(Tier.IV, 163840 / 20).build();

    public CoverPump(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        Objects.requireNonNull(tier);
        this.filter = new CoverFluidFilter(source, null, side, GT5RCovers.COVER_FLUID_FILTER);
        filter.onCreate();
        this.gui.getSlots().add(SlotTypes.STORAGE, 88, 53);
    }

    @Override
    public void addWidgets(ModularPanel<?> modularPanel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        super.addWidgets(modularPanel, sidedPosGuiData, panelSyncManager, uiSettings);
        IPanelHandler panelSyncHandler = panelSyncManager.syncedPanel("other_panel", true, this::openSecondWindow);
        modularPanel.child(new ButtonWidget<>().overlay(new ItemDrawable(GT5RCovers.COVER_FLUID_FILTER.getItem()))
                .pos(70, 53).size(16)
                .onMousePressed((context, mouseButton) -> {
                    if (this.getInventory(SlotTypes.STORAGE).getStackInSlot(0).isEmpty()) return false;
                    panelSyncHandler.openPanel();
                    return true;
                })
                .tooltip(new RichTooltip().addLine("Open Filter Gui"))
        );
    }

    public ModularPanel<?> openSecondWindow(PanelSyncManager panelSyncManager, IPanelHandler syncHandler) {
        ModularPanel<?> panel = new ModularPanel<>("filter_window")
                .disablePanelsBelow(false)
                .closeOnOutOfBoundsClick(false)
                .draggable(true)
                .size(54, 54);

        panelSyncManager.syncValue("blacklist", new BooleanSyncValue(() -> filter.blacklist, b -> filter.blacklist = b).allowC2S());
        panelSyncManager.syncValue("filter_mode", new IntSyncValue(() -> filter.filterMode, i -> filter.filterMode = (byte) i).allowC2S());
        panel.child(new CycleButtonWidget().stateCount(2).pos(9, 9).size(16, 16).syncHandler("blacklist")
                .stateOverlay(false, GTGuiTextures.WHITELIST)
                .stateOverlay(true, GTGuiTextures.BLACKLIST)
                .addTooltip(0, Text.lang("tooltip.gt5r.whitelist"))
                .addTooltip(1, Text.lang("tooltip.gt5r.blacklist")));
        panel.child(new CycleButtonWidget().stateCount(3).pos(27, 9).size(16, 16).syncHandler("filter_mode")
                .stateOverlay(0, GTGuiTextures.EXPORT_IMPORT)
                .stateOverlay(1, GTGuiTextures.IMPORT)
                .stateOverlay(2, GTGuiTextures.EXPORT)
                .addTooltip(0, Text.lang("tooltip.gt5r.filter_mode.0"))
                .addTooltip(1, Text.lang("tooltip.gt5r.filter_mode.1"))
                .addTooltip(2, Text.lang("tooltip.gt5r.filter_mode.2"))
        );
        FluidTanks tanks = SlotTypes.FL_PHANTOM.fluidHandlerSupplier().apply(filter);
        GTFluidSlot fluidSlot = new GTFluidSlot();
        fluidSlot.pos(18, 27).alwaysShowFull(true)
                .syncHandler(new FluidSlotSyncHandler(tanks.getTank(0)).phantom(true));
        panel.child(fluidSlot);
        return panel;
    }
    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicDepthModel();
    }

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (object instanceof FluidStack stack){
            if (getInventory(SlotTypes.STORAGE).getStackInSlot(0).isEmpty()) return false;
            return filter.onTransfer(stack, inputSide, simulate);
        }
        return super.onTransfer(object, inputSide, simulate);
    }

    @Override
    public <T> boolean blocksCapability(Class<T> cap, Direction side) {
        return cap != IFluidHandler.class;
    }

    @Override
    public void onTickPre() {
        //Pump acts on each tick.
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
            FluidUtils.getFluidHandler(handler.getTile().getLevel(), from, fromSide).ifPresent(ih -> FluidUtils.getFluidHandler(handler.getTile().getLevel(), finalTo, finalFromSide.getOpposite()).ifPresent(other -> Utils.transferFluids(ih, other, speeds.get(tier))));
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
    public boolean test(SlotType<?> type, int slot, ItemStack stack) {
        return stack.getItem() == GT5RCovers.COVER_FLUID_FILTER.getItem().getItem();
    }

    @Override
    public void onMachineEvent(IGuiHandler tile, IMachineEvent event, int... data) {
        if (tile == this && event == SlotTypes.STORAGE){
            ItemStack slotStack = getInventory(SlotTypes.STORAGE).getStackInSlot(data[0]);
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
        onMachineEvent(this, SlotTypes.STORAGE, 0);
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        if (nbt.contains("filter")) {
            filter.deserialize(nbt.getCompound("filter"));
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.put("filter", filter.serialize());
        return nbt;
    }
}
