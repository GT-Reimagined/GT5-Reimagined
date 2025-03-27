package org.gtreimagined.gt5r.cover.redstone;

import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverActivityDetectorPossible extends CoverBasicRedstoneOutput {
    public CoverActivityDetectorPossible(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }
}
