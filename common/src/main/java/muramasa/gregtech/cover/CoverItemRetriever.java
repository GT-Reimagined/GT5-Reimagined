package muramasa.gregtech.cover;

import muramasa.antimatter.blockentity.BlockEntityBase;
import muramasa.antimatter.blockentity.pipe.BlockEntityItemPipe;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.gui.ButtonOverlay;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.cover.base.CoverFilter;
import muramasa.gregtech.gui.ButtonOverlays;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tesseract.TesseractCapUtils;
import tesseract.api.item.PlatformItemHandler;
import tesseract.util.ItemHandlerUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class CoverItemRetriever extends CoverFilter {
    public CoverItemRetriever(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        getGui().getSlots().add(SlotType.DISPLAY_SETTABLE, 79, 53);
        addGuiCallback(t -> {
            t.addSwitchButton(70, 34, 16, 16, ButtonOverlay.WHITELIST, ButtonOverlay.BLACKLIST, h -> blacklist, true, b -> "tooltip.gti." + (b ? "blacklist" : "whitelist"));
            t.addSwitchButton(88, 34, 16, 16, ButtonOverlays.NBT_OFF, ButtonOverlays.NBT_ON, h -> !ignoreNBT, true, b -> "tooltip.gti.nbt." + (b ? "on" : "off"));
        });;
    }

    @Override
    public void clearFilter(){
        super.clearFilter();
        getInventory(SlotType.DISPLAY_SETTABLE).clearContent();
    }

    @Override
    public void onUpdate() {
        if (!source().getTile().getLevel().isClientSide() && source().getTile() instanceof BlockEntityItemPipe<?> pipe){
            if (pipe.getLevel().getGameTime() % 20 == 15 && pipe.pipeCapacityCheck()){
                ArrayList<BlockEntityItemPipe<?>> tUsedPipes = new ArrayList<>();
                BlockEntity adjacent = pipe.getCachedBlockEntity(this.side);
                if (adjacent == null) return;
                PlatformItemHandler to = TesseractCapUtils.INSTANCE.getItemHandler(adjacent, this.side.getOpposite()).orElse(null);
                if (to == null) return;
                for (BlockEntityItemPipe<?> p : BlockEntityItemPipe.scanPipes(pipe, new HashMap<>(), 0, true, false).keySet()){
                    if (tUsedPipes.add(p)){
                        for (Direction dir : Direction.values()){
                            if (p.canAcceptItemsFrom(dir, pipe) && (dir != this.side || p != pipe)){
                                BlockEntity a = p.getCachedBlockEntity(dir);
                                if (!(a instanceof BlockEntityItemPipe) && a != null){
                                    PlatformItemHandler itemHandler = TesseractCapUtils.INSTANCE.getItemHandler(a, dir.getOpposite()).orElse(null);
                                    if (itemHandler != null && Utils.transferItems(itemHandler, to, true, s -> itemMatches(s, getInventory(SlotType.DISPLAY_SETTABLE).getItem(0)))){
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
            if (!blacklist) {
                return true;
            }
        }
        boolean matches = ignoreNBT ? item.is(filter.getItem()) : ItemHandlerUtils.canItemStacksStack(item, filter);
        return blacklist == matches;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){
            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            if (ev.data[1] == 0){
                blacklist = !blacklist;
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
}
