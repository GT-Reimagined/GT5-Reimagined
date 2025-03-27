package org.gtreimagined.gt5r.cover.base;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.gui.event.GuiEvents;
import org.gtreimagined.gtlib.gui.event.IGuiEvent;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.gtreimagined.gt5r.cover.RedstoneMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverBasicRedstone extends BaseCover {
    public RedstoneMode redstoneMode;
    public CoverBasicRedstone(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
        this.redstoneMode = RedstoneMode.NORMAL;
    }

    @Override
    public void onGuiEvent(IGuiEvent event, Player playerEntity) {
        if (event.getFactory() == GuiEvents.EXTRA_BUTTON){
            GuiEvents.GuiEvent ev = (GuiEvents.GuiEvent) event;
            if (ev.data[1] == 0){
                redstoneMode = ev.data[0] == 0 ? redstoneMode.next() : redstoneMode.previous();
                handler.getTile().setChanged();
            }
        }
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag nbt =  super.serialize();
        nbt.putInt("redstoneMode", redstoneMode.ordinal());
        return nbt;
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        super.deserialize(nbt);
        if (nbt.contains("redstoneMode")) {
            redstoneMode = RedstoneMode.values()[nbt.getInt("redstoneMode")];
        }
    }
}
