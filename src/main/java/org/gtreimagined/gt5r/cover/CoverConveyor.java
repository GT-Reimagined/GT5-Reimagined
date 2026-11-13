package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.IPanelHandler;
import brachy.modularui.api.drawable.Text;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.FluidSlotSyncHandler;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import brachy.modularui.widgets.slot.ItemSlot;
import brachy.modularui.widgets.slot.ModularSlot;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.cover.base.CoverBasicTransport;
import org.gtreimagined.gt5r.data.GT5RCovers;
import org.gtreimagined.gtlib.blockentity.BlockEntityBase;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.IGuiHandler;
import org.gtreimagined.gtlib.capability.fluid.FluidTanks;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.SlotData;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.gui.slot.AbstractSlot;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.drawable.GTDrawableStack;
import org.gtreimagined.gtlib.mui.widgets.GTFluidSlot;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;


public class CoverConveyor extends CoverBasicTransport implements IFilterableHandler {

    public static String ID = "conveyor";

    private boolean extracting = true;
    private final CoverItemFilter filter;

    public static final Map<Tier, Integer> speeds = ImmutableMap.<Tier, Integer>builder().
            put(Tier.LV, 400)
            .put(Tier.MV, 100)
            .put(Tier.HV, 20)
            .put(Tier.EV, 10)
            .put(Tier.IV, 1).build();

    public CoverConveyor(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.filter = new CoverItemFilter(source, null, side, GT5RCovers.COVER_ITEM_FILTER);
        filter.onCreate();
        Objects.requireNonNull(tier);
        this.gui.getSlots().add(SlotTypes.STORAGE, filterX(), filterY());
        //addGuiCallback(t -> t.addButton(106, 53, ButtonOverlay.ARROW_LEFT, true));
    }

    @Override
    protected int filterX() {
        return 79;
    }

    @Override
    protected int filterY() {
        return 53;
    }

    @Override
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
        ModularSlot slot = new AbstractSlot<>(SlotTypes.DISPLAY_SETTABLE, filter, filter.getInventory(SlotTypes.DISPLAY_SETTABLE), 0);
        ItemSlot itemSlot = ItemSlot.create(true);
        itemSlot.pos(18, 27);
        itemSlot.slot(slot);
        panel.child(itemSlot);
        return panel;
    }

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (object instanceof ItemStack stack){
            if (getInventory(SlotTypes.STORAGE).getStackInSlot(0).isEmpty()) return false;
            return filter.onTransfer(stack, inputSide, simulate);
        }
        return super.onTransfer(object, inputSide, simulate);
    }

    @Override
    public <T> boolean blocksCapability(Class<T> cap, Direction side) {
        return cap != IItemHandler.class;
    }

    //Useful for using the same model for multiple tiers where id is dependent on tier.

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicDepthModel();
    }

    @Override
    public void onTickPre() {
        if (!(handler.getTile() instanceof BlockEntityBase<?> base) || handler.getTile().getLevel().getGameTime() % (speeds.get(tier)) != 0)
            return;
        BlockState state = handler.getTile().getLevel().getBlockState(handler.getTile().getBlockPos().relative(side));
        //Drop into world.
        if (state == Blocks.AIR.defaultBlockState() && exportMode.isExport() && canMove(side)) {
            Level world = handler.getTile().getLevel();
            BlockPos pos = handler.getTile().getBlockPos();
            ItemStack stack = handler.getTile().getCapability(ForgeCapabilities.ITEM_HANDLER, side).map(Utils::extractAny).orElse(ItemStack.EMPTY);
            if (stack.isEmpty()) return;
            double x = pos.getX() + side.getStepX() + 0.5;
            double y = pos.getY() + side.getStepY() + 0.5;
            double z = pos.getZ() + side.getStepZ() + 0.5;
            ItemEntity entity = new ItemEntity(world, x, y, z, stack, 0.0, 0.0, 0.0);
            world.addFreshEntity(entity);
        }
        if (!(state.hasBlockEntity())) return;
        BlockEntity adjTile = base.getCachedBlockEntity(side);
        if (adjTile == null) {
            return;
        }
        BlockEntity from = handler.getTile();
        BlockEntity to = adjTile;
        Direction fromSide = side;
        if (exportMode == ImportExportMode.IMPORT || exportMode == ImportExportMode.IMPORT_EXPORT){
            from = adjTile;
            to = handler.getTile();
            fromSide = side.getOpposite();
        }
        BlockEntity finalTo = to;
        if (canMove(side)){
            Direction finalFromSide = fromSide;
            from.getCapability(ForgeCapabilities.ITEM_HANDLER, fromSide).ifPresent(ih -> finalTo.getCapability(ForgeCapabilities.ITEM_HANDLER, finalFromSide.getOpposite()).ifPresent(oh -> {
                Utils.transferItems(ih, oh, true);
            }));
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
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        return stack.getItem() == GT5RCovers.COVER_ITEM_FILTER.getItem().getItem();
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
