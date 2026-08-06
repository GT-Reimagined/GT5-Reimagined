package org.gtreimagined.gt5r.blockentity.single;

import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.BooleanSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtlib.capability.machine.MachineEnergyHandler;
import org.gtreimagined.gtlib.gui.SlotTypes;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.util.Utils;

import static org.gtreimagined.gtlib.machine.MachineFlag.EU;

public class BlockEntityBuffer extends BlockEntityLimitedOutput<BlockEntityBuffer> {
    boolean emitEnergy = false;
    boolean outputRedstone = false;
    boolean invertRedstone = false;

    public BlockEntityBuffer(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (type.has(EU)) {
            energyHandler.set(() -> new MachineEnergyHandler<>(this, 0L, this.getMachineTier().getVoltage() * 66L, this.getMachineTier().getVoltage(), this.getMachineTier().getVoltage(), 1, 1){
                @Override
                public boolean canOutput(Direction direction) {
                    return super.canOutput(direction) && direction == tile.getFacing().getOpposite() && tile.emitEnergy;
                }
            });
        }
    }

    @Override
    public int getWeakRedstonePower(Direction facing) {
        if (outputRedstone){
            int[] redstone = new int[1];
            redstone[0] = this.itemHandler.map(i -> {
                for (int slot = 0; slot < i.getHandler(SlotTypes.STORAGE).getSlots(); slot++){
                    ItemStack stack = i.getHandler(SlotTypes.STORAGE).getStackInSlot(slot);
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
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        super.serverTick(level, pos, state);
        if (getCover(this.getFacing().getOpposite()).isEmpty()){
            processItemOutput();
        }
        this.itemHandler.ifPresent(h -> Utils.tryCondenseInventory(h.getHandler(SlotTypes.STORAGE)));
    }

    protected boolean processItemOutput() {
        Direction outputDir = this.getFacing().getOpposite();
        BlockEntity adjTile = this.getCachedBlockEntity(outputDir);
        if (adjTile == null) return false;
        boolean[] booleans = new boolean[1];
        booleans[0] = false;
        adjTile.getCapability(ForgeCapabilities.ITEM_HANDLER, outputDir.getOpposite()).ifPresent(adjHandler -> {
            booleans[0] = this.itemHandler.map(h -> transferItems(h.getHandler(SlotTypes.STORAGE), adjHandler,true)).orElse(false);
        });
        return booleans[0];
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("outputRedstone", outputRedstone);
        tag.putBoolean("invertRedstone", invertRedstone);
        tag.putBoolean("emitEnergy", emitEnergy);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        outputRedstone = tag.getBoolean("outputRedstone");
        invertRedstone = tag.getBoolean("invertRedstone");
        emitEnergy = tag.getBoolean("emitEnergy");
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON) {
            int[] data = ((GuiEvents.GuiEvent)event).data;
            switch (data[1]) {
                case 0 -> {
                    emitEnergy = !emitEnergy;
                    playerEntity.displayClientMessage(Utils.literal((emitEnergy ? "Emit energy to output side" : "Don't emit energy")), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                }
                case 1 -> {
                    outputRedstone = !outputRedstone;
                    playerEntity.displayClientMessage(Utils.literal( (outputRedstone ? "Emit redstone if slots contain something" : "Don't emit redstone")), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                }
                case 2 -> {
                    invertRedstone = !invertRedstone;
                    playerEntity.displayClientMessage(Utils.literal( (invertRedstone ? "I" : "Don't i") + "nvert redstone"), false);
                    level.markAndNotifyBlock(this.getBlockPos(), this.level.getChunkAt(this.getBlockPos()), this.getBlockState(), this.getBlockState(), 1, 512);
                }
            }
        }
    }

    @Override
    public void addWidgets(ModularPanel<?> panel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("emit_energy", new BooleanSyncValue(() -> emitEnergy, e -> emitEnergy = e).allowC2S());
        panelSyncManager.syncValue("output_redstone", new BooleanSyncValue(() -> outputRedstone, e -> outputRedstone = e).allowC2S());
        panelSyncManager.syncValue("invert_redstone", new BooleanSyncValue(() -> invertRedstone, e -> invertRedstone = e).allowC2S());
        panel.child(new CycleButtonWidget().stateCount(2).pos(7, 63).size(18).syncHandler("emit_energy")
                .stateOverlay(false, GT5RGuiTextures.ENERGY_OFF)
                .stateOverlay(true, GT5RGuiTextures.ENERGY_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(25, 63).size(18).syncHandler("output_redstone")
                .stateOverlay(false, GT5RGuiTextures.REDSTONE_CONTROL_OFF)
                .stateOverlay(true, GT5RGuiTextures.REDSTONE_CONTROL_ON));
        panel.child(new CycleButtonWidget().stateCount(2).pos(43, 63).size(18).syncHandler("invert_redstone")
                .stateOverlay(false, GT5RGuiTextures.INVERT_REDSTONE_OFF)
                .stateOverlay(true, GT5RGuiTextures.INVERT_REDSTONE_ON));
    }
}
