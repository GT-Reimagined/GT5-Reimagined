package org.gtreimagined.gt5r.cover;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.ItemDrawable;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.EnumSyncValue;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.ButtonWidget;
import brachy.modularui.widgets.CycleButtonWidget;
import brachy.modularui.widgets.TextWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Items;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.gtreimagined.gtlib.mui.GTMuiUtils;
import org.gtreimagined.gtlib.util.Utils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.cover.base.CoverBasicTransport;
import org.jetbrains.annotations.Nullable;

public class CoverRobotArm extends CoverBasicTransport {
    int slot = 0;
    int slotLimit = 0;
    String test = "";
    public CoverRobotArm(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public void addWidgets(ModularPanel<?> modularPanel, SidedPosGuiData sidedPosGuiData, PanelSyncManager syncManager, UISettings uiSettings) {
        syncManager.syncValue("redstone_mode", new EnumSyncValue<>(RedstoneMode.class, () -> this.redstoneMode, e -> this.redstoneMode = e).allowC2S());
        syncManager.syncValue("export_mode", new EnumSyncValue<>(ImportExportMode.class, () -> this.exportMode, e -> this.exportMode = e).allowC2S());
        syncManager.syncValue("slotLimit", new IntSyncValue(() -> this.slotLimit));
        syncManager.syncValue("slot", new IntSyncValue(() -> this.slot));
        modularPanel.child(new CycleButtonWidget()
                .pos(70, 16).size(16, 16)
                .stateCount(3).syncHandler("redstone_mode")
                .stateOverlay(RedstoneMode.NORMAL, GTGuiTextures.TORCH_OFF)
                .stateOverlay(RedstoneMode.INVERTED, GTGuiTextures.TORCH_ON)
                .stateOverlay(RedstoneMode.NO_WORK, new ItemDrawable(Items.REDSTONE))
                .addTooltip(0, Text.lang("tooltip.gt5r.redstone_mode.0"))
                .addTooltip(1, Text.lang("tooltip.gt5r.redstone_mode.1"))
                .addTooltip(2, Text.lang("tooltip.gt5r.redstone_mode.2")));
        modularPanel.child(new CycleButtonWidget()
                .pos(88, 16).size(16, 16)
                .stateCount(4).syncHandler("export_mode")
                .stateOverlay(ImportExportMode.EXPORT, GTGuiTextures.EXPORT)
                .stateOverlay(ImportExportMode.IMPORT, GTGuiTextures.IMPORT)
                .stateOverlay(ImportExportMode.EXPORT_IMPORT, GTGuiTextures.EXPORT_IMPORT)
                .stateOverlay(ImportExportMode.IMPORT_EXPORT, GTGuiTextures.IMPORT_EXPORT)
                .addTooltip(0, Text.lang("tooltip.gt5r.export_mode.0"))
                .addTooltip(1, Text.lang("tooltip.gt5r.export_mode.1"))
                .addTooltip(2, Text.lang("tooltip.gt5r.export_mode.2"))
                .addTooltip(3, Text.lang("tooltip.gt5r.export_mode.3")));
        modularPanel.child(new ButtonWidget<>()
                .overlay(Text.dynamic(() -> {
                    int slot = GTMuiUtils.getSyncedValue("slot", Integer.class, syncManager.getModularSyncManager()).orElse(0);
                    return Utils.literal("Slot: " + slot);
                }))
                .onMousePressed((context, mouseButton) -> {
                    syncManager.callSyncedAction("extra_button_event", packet -> {
                        packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, 2});
                    });
                    return true;
                })
                .size(36, 12).pos(70, 53));
        modularPanel.child(new ButtonWidget<>()
                .overlay(GTGuiTextures.MINUS)
                .onMousePressed((context, mouseButton) -> {
                    syncManager.callSyncedAction("extra_button_event", packet -> {
                        packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, 3});
                    });
                    return true;
                })
                .size(16).pos(61, 34));
        modularPanel.child(new ButtonWidget<>()
                .overlay(GTGuiTextures.PLUS)
                .onMousePressed((context, mouseButton) -> {
                    syncManager.callSyncedAction("extra_button_event", packet -> {
                        packet.writeVarIntArray(new int[]{Screen.hasShiftDown() ? 1 : 0, 4});
                    });
                    return true;
                })
                .size(16).pos(97, 34));
        modularPanel.child(new TextWidget<>(() -> {
            int fluidLimit = GTMuiUtils.getSyncedValue("slotLimit", Integer.class, syncManager.getModularSyncManager()).orElse(0);
            if (fluidLimit == 0) return Text.str("N/A");
            return Text.str(String.valueOf(fluidLimit)).color(4210752);
        }).pos(79, 34).size(18, 18));
    }

    @Override
    protected String getRenderId() {
        return "conveyor";
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicDepthModel();
    }

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        if (object instanceof ItemStack stack && !exportMode.isExport() && handler.getTile() instanceof BlockEntityMachine<?> machine && inputSide) {
            if (machine.itemHandler.isPresent()){
                if (stack.isEmpty()) return true;
                if (slotLimit > 0 && stack.getCount() < slotLimit) return true;
                ItemStack toInsert = slotLimit > 0 ? Utils.ca(slotLimit, stack) : stack.copy();
                MachineItemHandler<?> itemHandler = machine.itemHandler.get();
                if (itemHandler.getInputCount() > 0){
                    ItemStack inserted = itemHandler.getInputHandler().insertItem(slot, toInsert, true);
                    if (inserted.isEmpty()){
                        if (!simulate) {
                            itemHandler.getInputHandler().insertItem(slot, toInsert, false);
                        }
                        stack.setCount(0);
                    } else if (inserted.getCount() < toInsert.getCount()) {
                        if (!simulate) {
                            itemHandler.getInputHandler().insertItem(slot, toInsert, false);
                        }
                        stack.setCount(toInsert.getCount() - inserted.getCount());
                    }
                } else if (itemHandler.getHandler(SlotType.STORAGE).getSlots() > 0){
                    ItemStack inserted = itemHandler.getHandler(SlotType.STORAGE).insertItem(slot, toInsert, true);
                    if (inserted.isEmpty()){
                        if (!simulate) {
                            itemHandler.getHandler(SlotType.STORAGE).insertItem(slot, toInsert, false);
                        }
                        stack.setCount(0);
                    } else if (inserted.getCount() < toInsert.getCount()) {
                        if (!simulate) {
                            itemHandler.getHandler(SlotType.STORAGE).insertItem(slot, toInsert, false);
                        }
                        stack.setCount(toInsert.getCount() - inserted.getCount());
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        ImportExportMode previous = exportMode;
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){

            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            int button = ev.data[1];
            if (button == 1){
                if (previous.isExport() != exportMode.isExport()){
                    slot = 0;
                    handler.getTile().setChanged();
                }
            }
            if (button == 2){
                if (handler.getTile() instanceof BlockEntityMachine<?> machine){
                    machine.itemHandler.ifPresent(h -> {
                        if (exportMode.isExport()){
                            if (h.getOutputCount() > 0){
                                slot++;
                                if (slot >= h.getOutputCount()) slot = 0;
                            } else {
                                slot = 0;
                            }
                        }
                        if (!exportMode.isExport()){
                            if (h.getInputCount() > 0){
                                slot++;
                                if (slot >= h.getInputCount()) slot = 0;
                            } else if (h.getHandler(SlotType.STORAGE).getSlots() > 0){
                                slot++;
                                if (slot >= h.getHandler(SlotType.STORAGE).getSlots()) slot = 0;
                            }else {
                                slot = 0;
                            }
                        }
                        machine.setChanged();
                    });
                }
            }
            if (button == 3){
                if (slotLimit > 0){
                    slotLimit--;
                    handler.getTile().setChanged();
                }
            }
            if (button == 4){
                if (slotLimit < 64){
                    slotLimit++;
                    handler.getTile().setChanged();
                }
            }
        }
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        slot = nbt.getInt("slot");
        if (nbt.contains("slotLimit")) {
            slotLimit = nbt.getInt("slotLimit");
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putInt("slot", slot);
        nbt.putInt("slotLimit", slotLimit);
        return nbt;
    }
}
