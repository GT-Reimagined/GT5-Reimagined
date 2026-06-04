package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.ButtonWidget;
import brachy.modularui.widgets.TextWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import org.gtreimagined.gtlib.blockentity.BlockEntityBase;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.GTMuiUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

import static org.gtreimagined.gt5r.cover.CoverConveyor.speeds;

public class CoverItemRegulator extends CoverBasicTransport {
    int slotLimit = 0;
    public CoverItemRegulator(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void addWidgets(ModularPanel<?> modularPanel, SidedPosGuiData sidedPosGuiData, PanelSyncManager syncManager, UISettings uiSettings) {
        super.addWidgets(modularPanel, sidedPosGuiData, syncManager, uiSettings);
        syncManager.syncValue("slotLimit", new IntSyncValue(() -> this.slotLimit));
        modularPanel.child(new ButtonWidget<>()
                .overlay(GTGuiTextures.MINUS)
                .onMousePressed((context, mouseButton) -> {
                    syncManager.callSyncedAction("extra_button_event", packet -> {
                        packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, 2});
                    });
                    return true;
                })
                .size(16).pos(52, 53));
        modularPanel.child(new ButtonWidget<>()
                .overlay(GTGuiTextures.PLUS)
                .onMousePressed((context, mouseButton) -> {
                    syncManager.callSyncedAction("extra_button_event", packet -> {
                        packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, 3});
                    });
                    return true;
                })
                .size(16).pos(106, 53));
        modularPanel.child(new TextWidget<>(() -> {
            int fluidLimit = GTMuiUtils.getSyncedValue("slotLimit", Integer.class, syncManager.getModularSyncManager()).orElse(0);
            if (fluidLimit == 0) return Text.str("N/A");
            return Text.str(String.valueOf(fluidLimit)).color(4210752);
        }).pos(69, 53).size(36, 18));
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicDepthModel();
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){

            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            int button = ev.data[1];
            if (button == 2){
                if (slotLimit > 0){
                    slotLimit--;
                    handler.getTile().setChanged();
                }
            }
            if (button == 3){
                if (slotLimit < 64){
                    slotLimit++;
                    handler.getTile().setChanged();
                }
            }
        }
    }

    boolean transferring = false;

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (transferring) return false;
        if (object instanceof ItemStack stack && !exportMode.isExport() && handler.getTile() instanceof BlockEntityMachine<?> machine && inputSide) {
            if (machine.itemHandler.isPresent()){
                if (stack.isEmpty()) return true;
                if (slotLimit > 0 && stack.getCount() < slotLimit) return true;
                transferring  = true;
                ItemStack toInsert = slotLimit > 0 ? Utils.ca(slotLimit, stack) : stack.copy();
                ItemStack inserted = machine.itemHandler.side(side).map(i -> Utils.insertItem(i, toInsert, true)).orElse(toInsert);
                if (inserted.isEmpty()){
                    if (!simulate) {
                        machine.itemHandler.side(side).ifPresent(i -> Utils.insertItem(i, toInsert, false));
                    }
                    stack.setCount(0);
                } else if (inserted.getCount() < toInsert.getCount()) {
                    if (!simulate) {
                        machine.itemHandler.side(side).ifPresent(i -> Utils.insertItem(i, toInsert, false));
                    }
                    stack.setCount(stack.getCount() - inserted.getCount());
                }
                transferring = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUpdate() {
        if (handler.getTile().getLevel().isClientSide || !(handler.getTile() instanceof BlockEntityBase<?> base) || handler.getTile().getLevel().getGameTime() % (speeds.get(tier)) != 0)
            return;
        BlockState state = handler.getTile().getLevel().getBlockState(handler.getTile().getBlockPos().relative(side));
        //Drop into world.
        if (state == Blocks.AIR.defaultBlockState() && exportMode.isExport()) {
            Level world = handler.getTile().getLevel();
            BlockPos pos = handler.getTile().getBlockPos();
            ItemStack stack = handler.getTile().getCapability(ForgeCapabilities.ITEM_HANDLER, side).map(this::extractAny).orElse(ItemStack.EMPTY);
            if (stack.isEmpty()) return;
            world.addFreshEntity(new ItemEntity(world, pos.getX() + side.getStepX(), pos.getY() + side.getStepY(), pos.getZ() + side.getStepZ(), stack));
        }
        if (!(state.hasBlockEntity())) return;
        BlockEntity adjTile = base.getCachedBlockEntity(side);
        if (adjTile == null) {
            return;
        }
        BlockEntity from = handler.getTile();
        BlockEntity to = adjTile;
        Direction fromSide = side;
        boolean isImporting = exportMode == ImportExportMode.IMPORT || exportMode == ImportExportMode.IMPORT_EXPORT;
        if (isImporting){
            from = adjTile;
            to = handler.getTile();
            fromSide = side.getOpposite();
        }
        BlockEntity finalTo = to;
        if (canMove(side)){
            Direction finalFromSide = fromSide;
            from.getCapability(ForgeCapabilities.ITEM_HANDLER, fromSide).ifPresent(ih -> finalTo.getCapability(ForgeCapabilities.ITEM_HANDLER, finalFromSide.getOpposite()).ifPresent(oh -> {
                Predicate<ItemStack> filter = s -> {
                    if (slotLimit == 0) return true;
                    if (s.getCount() < slotLimit) return false;
                    s.setCount(slotLimit);
                    return true;
                };
                Utils.transferItems(ih, oh, true, filter);
            }));
        }
    }

    public ItemStack extractAny(IItemHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.extractItem(i, slotLimit > 0 ? slotLimit : 64, true);
            if (!stack.isEmpty() && (slotLimit == 0 || stack.getCount() == slotLimit)) {
                handler.extractItem(i, slotLimit > 0 ? slotLimit : 64, false);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean canMove(Direction side){
        if (redstoneMode != RedstoneMode.NO_WORK){
            boolean powered = isPowered(side);
            return (redstoneMode == RedstoneMode.INVERTED) != powered;
        }
        return true;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt = super.serialize();
        nbt.putInt("slotLimit", slotLimit);
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        slotLimit = nbt.getInt("slotLimit");
    }
}
