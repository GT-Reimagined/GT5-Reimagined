package org.gtreimagined.gt5r.blockentity.single;

import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.gtreimagined.gt5r.data.GT5RItems;
import org.gtreimagined.gt5r.gui.ButtonOverlays;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.capability.IFilterableHandler;
import org.gtreimagined.gtlib.capability.machine.MachineEnergyHandler;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.event.IMachineEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static org.gtreimagined.gtlib.machine.MachineFlag.EU;

public class BlockEntityItemFilter extends BlockEntityLimitedOutput<BlockEntityItemFilter> implements IFilterableHandler {
    boolean blacklist = false;
    boolean nbt = true;
    boolean outputRedstone = false;
    boolean invertRedstone = false;
    boolean emitEnergy = false;
    public BlockEntityItemFilter(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (type.has(EU)) {
            energyHandler.set(() -> new MachineEnergyHandler<BlockEntityItemFilter>(this, 0L, this.getMachineTier().getVoltage() * 66L, this.getMachineTier().getVoltage(), this.getMachineTier().getVoltage(), 1, 1){
                @Override
                public boolean canOutput(Direction direction) {
                    return super.canOutput(direction) && direction == tile.getFacing().getOpposite() && tile.emitEnergy;
                }
            });
        }
    }

    @Override
    public InteractionResult onInteractServer(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, @Nullable GTToolType type) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == GT5RItems.DataStick){
            if (stack.getTagElement("displaySlots") == null){
                this.itemHandler.ifPresent(i -> {
                    CompoundTag displaySlots = i.getHandler(SlotType.DISPLAY_SETTABLE).serializeNBT();
                    displaySlots.putString("machineType", this.getMachineType().getLoc().toString());
                    stack.getOrCreateTag().put("displaySlots", displaySlots);
                });
                level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 10.f, 1.0f);
                return InteractionResult.SUCCESS;
            } else {
                CompoundTag displaySlots = stack.getTagElement("displaySlots");
                if (!displaySlots.isEmpty() && displaySlots.getString("machineType").equals(this.getMachineType().getLoc().toString())){
                    this.itemHandler.ifPresent(i -> i.getHandler(SlotType.DISPLAY_SETTABLE).deserializeNBT(displaySlots));
                    level.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 10.f, 1.0f);
                    return InteractionResult.SUCCESS;
                }

            }
        }
        return super.onInteractServer(state, world, pos, player, hand, hit, type);
    }

    @Override
    public boolean test(SlotType<?> slotType, int slot, ItemStack stack) {
        if (slotType == SlotType.STORAGE){
            boolean hasItem = itemHandler.map(h -> {
                List<Item> list = new ObjectArrayList<>();
                IItemHandler outputs = h.getHandler(SlotType.DISPLAY_SETTABLE);
                for (int i = 0; i < outputs.getSlots(); i++) {
                    ItemStack slotStack = outputs.getStackInSlot(i);
                    if (!slotStack.isEmpty()) {
                        if (slotStack.getItem() == stack.getItem()){
                            if (!nbt || Objects.equals(slotStack.getTag(), stack.getTag())) {
                                list.add(slotStack.copy().getItem());
                            }
                        }
                    }
                }
                return list.isEmpty() == blacklist;
            }).orElse(false);
            return hasItem;
        }
        return true;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON) {
            int[] data = ((GuiEvents.GuiEvent)event).data;
            switch (data[1]) {
                case 0:
                    emitEnergy = !emitEnergy;
                    playerEntity.displayClientMessage(Utils.literal( (emitEnergy ? "Emit energy to output side" : "Don't emit energy")), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 1:
                    outputRedstone = !outputRedstone;
                    playerEntity.displayClientMessage(Utils.literal( (outputRedstone ? "Emit redstone if slots contain something" : "Don't emit redstone")), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 2:
                    invertRedstone = !invertRedstone;
                    playerEntity.displayClientMessage(Utils.literal( (invertRedstone ? "I" : "Don't i") + "nvert redstone"), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                    break;
                case 3:
                    blacklist = !blacklist;
                    playerEntity.displayClientMessage(Utils.literal( (blacklist ? "I" : "Don't i") + "nvert filter"), false);
                    break;
                case 4:
                    nbt = !nbt;
                    playerEntity.displayClientMessage(Utils.literal( (nbt ? "NBT has to match" : "Ignore NBT")), false);
                    break;
            }
        }
    }

    @Override
    public void addWidgets(ModularPanel<?> panel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("emit_energy", new BooleanSyncValue(() -> emitEnergy, e -> emitEnergy = e).allowC2S());
        panelSyncManager.syncValue("output_redstone", new BooleanSyncValue(() -> outputRedstone, e -> outputRedstone = e).allowC2S());
        panelSyncManager.syncValue("invert_redstone", new BooleanSyncValue(() -> invertRedstone, e -> invertRedstone = e).allowC2S());
        panelSyncManager.syncValue("blacklist", new BooleanSyncValue(() -> blacklist, e -> blacklist = e).allowC2S());
        panelSyncManager.syncValue("nbt", new BooleanSyncValue(() -> nbt, e -> nbt = e).allowC2S());
        panel.child(new CycleButtonWidget().stateCount(2).pos(8, 63).size(16).syncHandler("emit_energy")
                .stateOverlay(false, GT5RGuiTextures.ENERGY_OFF)
                .stateOverlay(true, GT5RGuiTextures.ENERGY_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(26, 63).size(16).syncHandler("output_redstone")
                .stateOverlay(false, GT5RGuiTextures.REDSTONE_CONTROL_OFF)
                .stateOverlay(true, GT5RGuiTextures.REDSTONE_CONTROL_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(44, 63).size(16).syncHandler("invert_redstone")
                .stateOverlay(false, GT5RGuiTextures.INVERT_REDSTONE_OFF)
                .stateOverlay(true, GT5RGuiTextures.INVERT_REDSTONE_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(62, 63).size(16).syncHandler("blacklist")
                .stateOverlay(false, GT5RGuiTextures.BLACKLIST_OFF)
                .stateOverlay(true, GT5RGuiTextures.BLACKLIST_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(80, 63).size(16).syncHandler("nbt")
                .stateOverlay(false, GT5RGuiTextures.NBT_OFF)
                .stateOverlay(true, GT5RGuiTextures.NBT_ON));
    }


    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (getCover(this.getFacing().getOpposite()).isEmpty()){
            this.processItemOutput();
        }
    }
    @Override
    public void onMachineEvent(IMachineEvent event, Object... data) {
        super.onMachineEvent(event, data);
        if ((event == SlotType.IT_OUT || event == SlotType.IT_IN) && outputRedstone && !this.getLevel().isClientSide()){
         //   level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
            level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
        }
    }

    protected boolean processItemOutput() {
        Direction outputDir = this.getFacing().getOpposite();
        BlockEntity adjTile = this.getCachedBlockEntity(outputDir);
        if (adjTile == null) return false;
        boolean[] booleans = new boolean[1];
        booleans[0] = false;
        adjTile.getCapability(ForgeCapabilities.ITEM_HANDLER, outputDir.getOpposite()).ifPresent(adjHandler -> {
            booleans[0] = this.itemHandler.map(h -> transferItems(h.getHandler(SlotType.STORAGE), adjHandler,true)).orElse(false);
        });
        return booleans[0];
    }

    @Override
    public int getWeakRedstonePower(Direction facing) {
        if (outputRedstone){
            int[] redstone = new int[1];
            redstone[0] = this.itemHandler.map(i -> {
                for (int slot = 0; slot < i.getHandler(SlotType.STORAGE).getSlots(); slot++){
                    ItemStack stack = i.getHandler(SlotType.STORAGE).getStackInSlot(slot);
                    if (!stack.isEmpty()) return invertRedstone ? 0 : 15;
                }
                return invertRedstone ? 15 : 0;
            }).orElse(0);
            if (redstone[0] > 0){
                return redstone[0];
            }
        }
        return super.getWeakRedstonePower(facing);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains(Ref.KEY_MACHINE_ITEMS)){
            CompoundTag tag1 = tag.getCompound(Ref.KEY_MACHINE_ITEMS);
            if (tag1.contains("filterable")){
                CompoundTag filterable = tag1.getCompound("filterable");
                tag1.put(SlotType.STORAGE.getId(), filterable);
            }
        }
        super.load(tag);
        blacklist = tag.getBoolean("blacklist");
        nbt = tag.getBoolean("nbt");
        outputRedstone = tag.getBoolean("outputRedstone");
        invertRedstone = tag.getBoolean("invertRedstone");
        emitEnergy = tag.getBoolean("emitEnergy");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("blacklist", blacklist);
        tag.putBoolean("nbt", nbt);
        tag.putBoolean("outputRedstone", outputRedstone);
        tag.putBoolean("invertRedstone", invertRedstone);
        tag.putBoolean("emitEnergy", emitEnergy);
    }


    public boolean isBlacklist() {
        return blacklist;
    }

    public boolean isEmitEnergy() {
        return emitEnergy;
    }

    public boolean isInvertRedstone() {
        return invertRedstone;
    }

    public boolean isNbt() {
        return nbt;
    }

    public boolean isOutputRedstone() {
        return outputRedstone;
    }
}