package org.gtreimagined.gt5r.cover;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverExtenderOutput extends BaseCover {
    public CoverExtenderOutput(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        return getBasicModel();
    }
}
