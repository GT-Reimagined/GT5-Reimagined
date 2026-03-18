package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gtlib.blockentity.BlockEntityBase;
import org.gtreimagined.gtlib.blockentity.pipe.BlockEntityItemPipe;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.gtreimagined.gt5r.cover.base.CoverFilter;
import org.gtreimagined.gt5r.gui.ButtonOverlays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverItemFilter extends CoverFilter {
    public CoverItemFilter(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.getGuiProperties().getSlots().add(SlotType.DISPLAY_SETTABLE, 88, 53);
        addGuiCallback(t -> {
            t.addSwitchButton(70, 34, 16, 16, ButtonOverlay.WHITELIST, ButtonOverlay.BLACKLIST, h -> blacklist, true, b -> "tooltip.gt5r." + (b ? "blacklist" : "whitelist"));
            t.addSwitchButton(88, 34, 16, 16, ButtonOverlays.NBT_OFF, ButtonOverlays.NBT_ON, h -> !ignoreNBT, true, b -> "tooltip.gt5r.nbt." + (b ? "on" : "off"));
            t.addCycleButton(106, 34, 16, 15, h -> ((CoverItemFilter)h).filterMode, true, i -> "tooltip.gt5r.filter_mode." + i, ButtonOverlay.EXPORT_IMPORT, ButtonOverlay.IMPORT, ButtonOverlay.EXPORT);
        });;
    }

    @Override
    public void clearFilter(){
        super.clearFilter();
        getInventory(SlotType.DISPLAY_SETTABLE).clearContent();
    }

    @Override
    public boolean canPlace() {
        if (this.handler.getTile() instanceof BlockEntityItemPipe<?> itemPipe && itemPipe.getCachedBlockEntity(this.side) instanceof BlockEntityItemPipe<?>){
            return false;
        }
        return super.canPlace();
    }

    @Override
    public boolean blockConnection(Direction side) {
        if (this.handler.getTile() instanceof BlockEntityItemPipe<?> itemPipe && itemPipe.getCachedBlockEntity(side) instanceof BlockEntityItemPipe<?>){
            return true;
        }
        return super.blockConnection(side);
    }

    @Override
    public <T> boolean blocksCapability(Class<T> cap, @Nullable Direction side) {
        return false;
    }

    @Override
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        super.onTransfer(object, inputSide, simulate);
        if (object instanceof ItemStack item) {
            if ((filterMode == 1 && !inputSide) || (filterMode == 2 && inputSide)) return false;
            ItemStack filter = getInventory(SlotType.DISPLAY_SETTABLE).getStackInSlot(0);
            boolean empty = filter.isEmpty();
            if (empty) {
                if (!blacklist) {
                    return true;
                }
            }
            boolean matches = ignoreNBT ? item.is(filter.getItem()) : ItemHandlerHelper.canItemStacksStack(item, filter);
            if (blacklist == matches){
                return true;
            }
        }
        return false;
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
            } else if (ev.data[1] == 2){
                if (filterMode == 0){
                    filterMode = 1;
                } else if (filterMode == 1){
                    filterMode = 2;
                } else {
                    filterMode = 0;
                }
                if (this.handler.getTile() instanceof BlockEntityBase<?> base){
                    base.sidedSync(true);
                }
            }
        }
    }

    @Override
    protected String getRenderId() {
        return "item_filter";
    }

    @Override
    public String getId() {
        return "item_filter";
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }
}
