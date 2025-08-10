package org.gtreimagined.gt5r.cover;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.cover.base.CoverFilter;
import org.gtreimagined.gtlib.blockentity.BlockEntityBase;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.ButtonOverlay;
import org.gtreimagined.gtlib.gui.SlotType;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverFluidFilter extends CoverFilter {
    public CoverFluidFilter(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        getGui().getSlots().add(SlotType.FLUID_DISPLAY_SETTABLE, 79, 53);
        addGuiCallback(t -> {
            t.addSwitchButton(70, 34, 16, 16, ButtonOverlay.WHITELIST, ButtonOverlay.BLACKLIST,h -> blacklist, true, b -> "tooltip.gt5r." + (b ? "blacklist" : "whitelist"));
            t.addCycleButton(88, 34, 16, 15, h -> ((CoverFluidFilter)h).filterMode, true, i -> "tooltip.gt5r.filter_mode." + i, ButtonOverlay.EXPORT_IMPORT, ButtonOverlay.IMPORT, ButtonOverlay.EXPORT);
        });
    }
    @Override
    public void clearFilter(){
        super.clearFilter();
        getInventory(SlotType.FLUID_DISPLAY_SETTABLE).clearContent();
    }

    @Override
    public <T> boolean blocksCapability(Class<T> cap, @Nullable Direction side) {
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
    public boolean onTransfer(Object object, boolean inputSide, boolean simulate) {
        super.onTransfer(object, inputSide, simulate);
        if (object instanceof FluidStack fluidHolder) {
            if ((filterMode == 1 && !inputSide) || (filterMode == 2 && inputSide)) return false;
            ItemStack filter = getInventory(SlotType.FLUID_DISPLAY_SETTABLE).getStackInSlot(0);
            boolean empty = filter.isEmpty() || filter.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(f -> {
                for (int i = 0; i < f.getTanks(); i++){
                    if (!f.getFluidInTank(i).isEmpty()){
                        return false;
                    }
                }
                return true;
            }).orElse(true);
            if (empty) {
                if (!blacklist) {
                    return true;
                }
            }
            boolean matches = filter.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(f -> {
                for (int i = 0; i < f.getTanks(); i++){
                    boolean match = ignoreNBT ? fluidHolder.getFluid() == f.getFluidInTank(i).getFluid() : f.getFluidInTank(i).isFluidEqual(fluidHolder);
                    if (match){
                        return true;
                    }
                }
                return false;
            }).orElse(false);
            return blacklist == matches;
        }
        return false;
    }

    @Override
    protected String getRenderId() {
        return "fluid_filter";
    }

    @Override
    public String getId() {
        return "fluid_filter";
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }
}
