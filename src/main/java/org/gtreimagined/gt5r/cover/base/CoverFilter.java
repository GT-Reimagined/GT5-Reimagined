package org.gtreimagined.gt5r.cover.base;

import org.gtreimagined.gtlib.capability.CoverHandler;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.texture.Texture;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class CoverFilter extends BaseCover {
    protected boolean blacklist = false;
    protected boolean ignoreNBT = false;
    protected byte filterMode = 0;
    public CoverFilter(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    public void clearFilter(){
        blacklist = false;
        ignoreNBT = false;
    }

    @Override
    public CompoundTag serializeStack(CompoundTag tag) {
        super.serializeStack(tag);
        tag.putBoolean("blacklist", blacklist);
        tag.putBoolean("ignoreNBT", ignoreNBT);
        tag.putByte("filterMode", filterMode);
        return tag;
    }

    @Override
    public void deserializeStack(@Nullable CompoundTag tag) {
        super.deserializeStack(tag);
        if (tag == null) return;
        blacklist = tag.getBoolean("blacklist");
        ignoreNBT = tag.getBoolean("ignoreNBT");
        filterMode = tag.getByte("filterMode");
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putBoolean("blacklist", blacklist);
        tag.putBoolean("ignoreNBT", ignoreNBT);
        tag.putByte("filterMode", filterMode);
        return tag;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        this.blacklist = nbt.getBoolean("blacklist");
        this.ignoreNBT = nbt.getBoolean("ignoreNBT");
        this.filterMode = nbt.getByte("filterMode");
        if (this.handler.getTile().getLevel() != null && this.handler.getTile().getLevel().isClientSide() && factory.getTextures().size() == 2) {
            if (this.handler instanceof CoverHandler<?> coverHandler && coverHandler.coverTexturer != null && coverHandler.coverTexturer.get(this.side) != null){
                coverHandler.coverTexturer.get(this.side).invalidate();
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void setTextures(BiConsumer<String, Texture> texer) {
        if (factory.getTextures().size() == 2){
            texer.accept("overlay", factory.getTextures().get(blacklist ? 1 : 0));
        } else {
            super.setTextures(texer);
        }
    }
}
