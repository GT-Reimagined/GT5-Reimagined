package org.gtreimagined.gt5r.cover.base;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.drawable.ItemDrawable;
import brachy.modularui.factory.SidedPosGuiData;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.screen.UISettings;
import brachy.modularui.value.sync.EnumSyncValue;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import brachy.modularui.widgets.CycleButtonWidget;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityPipe;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gt5r.cover.ICoverRedstoneSensitive;
import org.gtreimagined.gt5r.cover.ImportExportMode;
import org.gtreimagined.gt5r.cover.RedstoneMode;
import org.gtreimagined.gtlib.mui.GTGuiTextures;
import org.jetbrains.annotations.Nullable;

public abstract class CoverBasicTransport extends CoverBasicRedstone implements ICoverRedstoneSensitive {

    protected ImportExportMode exportMode;
    int coverModeInt;

    public CoverBasicTransport(ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.exportMode = source.getTile() instanceof BlockEntityPipe<?> ? ImportExportMode.IMPORT : ImportExportMode.EXPORT;
        redstoneMode = RedstoneMode.NO_WORK;
        coverModeInt = exportMode.ordinal();
    }

    @Override
    public void addWidgets(ModularPanel<?> modularPanel, SidedPosGuiData sidedPosGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        panelSyncManager.syncValue("redstone_mode", new EnumSyncValue<>(RedstoneMode.class, () -> this.redstoneMode, e -> this.redstoneMode = e).allowC2S());
        panelSyncManager.syncValue("export_mode", new EnumSyncValue<>(ImportExportMode.class, () -> this.exportMode, e -> this.exportMode = e).allowC2S());
        modularPanel.child(new CycleButtonWidget()
                .pos(70, 34).size(16, 16)
                .stateCount(3).syncHandler("redstone_mode")
                .stateOverlay(RedstoneMode.NORMAL, GTGuiTextures.TORCH_OFF)
                .stateOverlay(RedstoneMode.INVERTED, GTGuiTextures.TORCH_ON)
                .stateOverlay(RedstoneMode.NO_WORK, new ItemDrawable(Items.REDSTONE))
                .addTooltip(0, Text.lang("tooltip.gt5r.redstone_mode.0"))
                .addTooltip(1, Text.lang("tooltip.gt5r.redstone_mode.1"))
                .addTooltip(2, Text.lang("tooltip.gt5r.redstone_mode.2")));
        modularPanel.child(new CycleButtonWidget()
                .pos(88, 34).size(16, 16)
                .stateCount(4).syncHandler("export_mode")
                .stateOverlay(ImportExportMode.EXPORT, GTGuiTextures.EXPORT)
                .stateOverlay(ImportExportMode.IMPORT, GTGuiTextures.IMPORT)
                .stateOverlay(ImportExportMode.EXPORT_IMPORT, GTGuiTextures.EXPORT_IMPORT)
                .stateOverlay(ImportExportMode.IMPORT_EXPORT, GTGuiTextures.IMPORT_EXPORT)
                .addTooltip(0, Text.lang("tooltip.gt5r.export_mode.0"))
                .addTooltip(1, Text.lang("tooltip.gt5r.export_mode.1"))
                .addTooltip(2, Text.lang("tooltip.gt5r.export_mode.2"))
                .addTooltip(3, Text.lang("tooltip.gt5r.export_mode.3")));
    }

    @Override
    public String getDomain() {
        return GT5Reimagined.ID;
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
    public <T> boolean blocksInput(Class<T> cap, @Nullable Direction side) {
        return exportMode == ImportExportMode.EXPORT;
    }

    @Override
    public <T> boolean blocksOutput(Class<T> cap, @Nullable Direction side) {
        return exportMode == ImportExportMode.IMPORT;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        super.onGuiEvent(event, playerEntity);
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){

            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            if (ev.data[1] == 1){
                exportMode = ev.data[0] == 0 ? exportMode.next() : exportMode.previous();
                handler.getTile().setChanged();
            }
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putInt("coverMode", exportMode.ordinal());
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        if (nbt.contains("coverMode")) {
            coverModeInt = nbt.getInt("coverMode");
            if (coverModeInt > 3) coverModeInt = 2;
            exportMode = ImportExportMode.values()[coverModeInt];
        }
    }
}
