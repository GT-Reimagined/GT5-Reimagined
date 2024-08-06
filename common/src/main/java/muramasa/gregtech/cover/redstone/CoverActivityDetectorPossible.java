package muramasa.gregtech.cover.redstone;

import muramasa.antimatter.capability.ICoverHandler;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.machine.Tier;
import muramasa.gregtech.cover.base.CoverBasicRedstoneOutput;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverActivityDetectorPossible extends CoverBasicRedstoneOutput {
    public CoverActivityDetectorPossible(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }
}
