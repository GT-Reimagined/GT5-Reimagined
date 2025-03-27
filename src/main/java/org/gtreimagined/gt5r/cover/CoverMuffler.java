package org.gtreimagined.gt5r.cover;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.cover.BaseCover;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverMuffler extends BaseCover {
    public CoverMuffler(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return handler.getTile() instanceof BlockEntityMachine<?> && !handler.hasCover(this.factory);
    }

    @Override
    public void onPlace() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine){
            if (!machine.isMuffled()){
                machine.setMuffled(true);
            }
        }
    }

    @Override
    public void onRemove() {
        if (handler.getTile() instanceof BlockEntityMachine<?> machine){
            if (machine.isMuffled()){
                machine.setMuffled(false);
            }
        }
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
       return getBasicModel();
    }
}
