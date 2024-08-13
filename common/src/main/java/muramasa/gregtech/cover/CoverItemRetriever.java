package muramasa.gregtech.cover;

import muramasa.antimatter.blockentity.BlockEntityBase;
import muramasa.antimatter.blockentity.pipe.BlockEntityItemPipe;
import muramasa.antimatter.capability.CoverHandler;
import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.BaseCover;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.gui.ButtonOverlay;
import muramasa.antimatter.gui.SlotType;
import muramasa.antimatter.gui.event.GuiEvents;
import muramasa.antimatter.gui.event.IGuiEvent;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.texture.Texture;
import muramasa.antimatter.util.Utils;
import muramasa.gregtech.cover.base.CoverFilter;
import muramasa.gregtech.gui.ButtonOverlays;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import java.util.function.BiConsumer;

public class CoverItemRetriever extends BaseCover {
    protected boolean whitelist = false;
    protected boolean ignoreNBT = false;
    public CoverItemRetriever(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        getGui().getSlots().add(SlotType.DISPLAY_SETTABLE, 79, 53);
        addGuiCallback(t -> {
            t.addSwitchButton(70, 34, 16, 16, ButtonOverlay.WHITELIST, ButtonOverlay.BLACKLIST, h -> !whitelist, true, b -> "tooltip.gti." + (b ? "blacklist" : "whitelist"));
            t.addSwitchButton(88, 34, 16, 16, ButtonOverlays.NBT_OFF, ButtonOverlays.NBT_ON, h -> !ignoreNBT, true, b -> "tooltip.gti.nbt." + (b ? "on" : "off"));
        });;
    }

    @Override
    public ItemStack getDroppedStack() {
        ItemStack stack =  super.getDroppedStack();
        stack.getOrCreateTag().putBoolean("whitelist", whitelist);
        stack.getOrCreateTag().putBoolean("ignoreNBT", ignoreNBT);
        return stack;
    }

    @Override
    public void addInfoFromStack(ItemStack stack) {
        super.addInfoFromStack(stack);
        if (stack.getTag() == null) return;
        CompoundTag tag = stack.getTag();
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
            return !whitelist;
        }
        boolean matches = ignoreNBT ? item.is(filter.getItem()) : ItemHandlerUtils.canItemStacksStack(item, filter);
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
