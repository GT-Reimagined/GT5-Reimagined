package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtlib.blockentity.BlockEntityBase;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityItemPipe;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe;
import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.texture.Texture;
import org.gtreimagined.gtlib.util.CodeUtils;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;

public class CoverItemRetriever extends BaseCover {
    protected boolean whitelist = false;
    protected boolean ignoreNBT = false;
    public CoverItemRetriever(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.getGuiProperties().getSlots().add(SlotTypes.DISPLAY_SETTABLE, 79, 53);
    }

    @Override
    public void addWidgets(ModularPanel<?> panel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("whitelist", new BooleanSyncValue(() -> this.whitelist, b -> this.whitelist = b).allowC2S());
        panelSyncManager.syncValue("nbt", new BooleanSyncValue(() -> this.ignoreNBT, b -> this.ignoreNBT = b).allowC2S());
        panel.child(new CycleButtonWidget().stateCount(2).pos(70, 34).size(16, 16).syncHandler("whitelist")
                .stateOverlay(false, GTGuiTextures.BLACKLIST)
                .stateOverlay(true, GTGuiTextures.WHITELIST)
                .addTooltip(0, Text.lang("tooltip.gt5r.blacklist"))
                .addTooltip(1, Text.lang("tooltip.gt5r.whitelist")));
        panel.child(new CycleButtonWidget().stateCount(2).pos(88, 34).size(16, 16).syncHandler("nbt")
                .stateOverlay(false, GT5RGuiTextures.NBT_ON)
                .stateOverlay(true, GT5RGuiTextures.NBT_OFF)
                .addTooltip(0, Text.lang("tooltip.gt5r.nbt.on"))
                .addTooltip(1, Text.lang("tooltip.gt5r.nbt.off")));
    }

    @Override
    public void onPlace() {
        super.onPlace();
        if (handler.getTile().getLevel() == null) return;
        if (handler.getTile() instanceof BlockEntityPipe<?> pipe){
            pipe.setConnection(this.side);
        }
    }

    @Override
    public CompoundTag serializeStack(CompoundTag tag) {
        super.serializeStack(tag);
        tag.putBoolean("whitelist", whitelist);
        tag.putBoolean("ignoreNBT", ignoreNBT);
        return tag;
    }

    @Override
    public void deserializeStack(@Nullable CompoundTag tag) {
        super.deserializeStack(tag);
        if (tag == null) return;
        whitelist = tag.getBoolean("whitelist");
        ignoreNBT = tag.getBoolean("ignoreNBT");
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putBoolean("whitelist", whitelist);
        tag.putBoolean("ignoreNBT", ignoreNBT);
        return tag;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        this.whitelist = nbt.getBoolean("whitelist");
        this.ignoreNBT = nbt.getBoolean("ignoreNBT");
        if (this.handler.getTile().getLevel() != null && this.handler.getTile().getLevel().isClientSide() && factory.getTextures().size() == 2) {
            if (this.handler instanceof CoverHandler<?> coverHandler && coverHandler.coverTexturer != null && coverHandler.coverTexturer.get(this.side) != null){
                coverHandler.coverTexturer.get(this.side).invalidate();
            }
        }
    }

    @Override
    public void onTickPre() {
        if (source().getTile() instanceof BlockEntityItemPipe<?> pipe){
            if (pipe.getLevel().getGameTime() % 20 == 15 && pipe.pipeCapacityCheck()){
                ArrayList<BlockEntityItemPipe<?>> tUsedPipes = new ArrayList<>();
                Set<BlockEntityItemPipe<?>> pipes = CodeUtils.sortByValuesAcending(BlockEntityItemPipe.scanPipes(pipe, new HashMap<>(), 0, true, false)).keySet();
                BlockState state = handler.getTile().getLevel().getBlockState(handler.getTile().getBlockPos().relative(side));
                if (state == Blocks.AIR.defaultBlockState()){
                    for (BlockEntityItemPipe<?> p : pipes){
                        if (tUsedPipes.add(p)){
                            for (Direction dir : Direction.values()){
                                if (p.canAcceptItemsFrom(dir, pipe) && (dir != this.side || p != pipe)){
                                    BlockEntity a = p.getCachedBlockEntity(dir);
                                    if (!(a instanceof BlockEntityItemPipe) && a != null){
                                        IItemHandler itemHandler = a.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).resolve().orElse(null);
                                        if (itemHandler != null) {
                                            Level world = handler.getTile().getLevel();
                                            BlockPos pos = handler.getTile().getBlockPos();
                                            ItemStack stack = Utils.extractAny(itemHandler);
                                            if (!stack.isEmpty()){
                                                double x = pos.getX() + side.getStepX() + 0.5;
                                                double y = pos.getY() + side.getStepY() + 0.5;
                                                double z = pos.getZ() + side.getStepZ() + 0.5;
                                                ItemEntity entity = new ItemEntity(world, x, y, z, stack, 0.0, 0.0, 0.0);
                                                world.addFreshEntity(entity);
                                                for (BlockEntityItemPipe<?> tUsedPipe : tUsedPipes) {
                                                    tUsedPipe.incrementTransferCounter(1);
                                                }
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
                BlockEntity adjacent = pipe.getCachedBlockEntity(this.side);
                if (adjacent == null) return;
                IItemHandler to = adjacent.getCapability(ForgeCapabilities.ITEM_HANDLER, this.side.getOpposite()).resolve().orElse(null);
                if (to == null) return;
                for (BlockEntityItemPipe<?> p : pipes){
                    if (tUsedPipes.add(p)){
                        for (Direction dir : Direction.values()){
                            if (p.canAcceptItemsFrom(dir, pipe) && (dir != this.side || p != pipe)){
                                BlockEntity a = p.getCachedBlockEntity(dir);
                                if (!(a instanceof BlockEntityItemPipe) && a != null){
                                    IItemHandler itemHandler = a.getCapability(ForgeCapabilities.ITEM_HANDLER, dir.getOpposite()).resolve().orElse(null);
                                    if (itemHandler != null && Utils.transferItems(itemHandler, to, true, s -> itemMatches(s, getInventory(SlotTypes.DISPLAY_SETTABLE).getStackInSlot(0)))){
                                        for (BlockEntityItemPipe<?> tUsedPipe : tUsedPipes){
                                            tUsedPipe.incrementTransferCounter(1);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private boolean itemMatches(ItemStack item, ItemStack filter) {
        boolean empty = filter.isEmpty();
        if (empty) {
            return !whitelist;
        }
        boolean matches = ignoreNBT ? item.is(filter.getItem()) : ItemHandlerHelper.canItemStacksStack(item, filter);
        return whitelist == matches;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){
            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            if (ev.data[1] == 0){
                whitelist = !whitelist;
                if (this.handler.getTile() instanceof BlockEntityBase<?> base){
                    base.sidedSync(true);
                }
            } else if (ev.data[1] == 1){
                ignoreNBT = !ignoreNBT;
                if (this.handler.getTile() instanceof BlockEntityBase<?> base){
                    base.sidedSync(true);
                }
            }
        }
    }

    @Override
    public void setTextures(BiConsumer<String, Texture> texer) {
        if (factory.getTextures().size() == 2){
            texer.accept("overlay", factory.getTextures().get(whitelist ? 0 : 1));
        } else {
            super.setTextures(texer);
        }
    }
}
