package org.gtreimagined.gt5r.cover.redstone;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.ICoverHandler;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.cover.CoverFactory;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.cover.base.CoverBasicRedstoneOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoverProgressSensor extends CoverBasicRedstoneOutput {
    public CoverProgressSensor(@NotNull ICoverHandler<?> source, @Nullable Tier tier, Direction side, CoverFactory factory) {
        super(source, tier, side, factory);
    }

    @Override
    public boolean canPlace() {
        return handler.getTile() instanceof BlockEntityMachine<?> machine && machine.recipeHandler.side(side).isPresent();
    }

    @Override
    public ResourceLocation getModel(String type, Direction dir) {
        if (type.equals("pipe")) return PIPE_COVER_MODEL;
        return getBasicModel();
    }

    @Override
    public void onTickPost() {
        if (handler.getTile().getLevel() == null) return;
        MachineRecipeHandler<?> recipeHandler = handler.getTile() instanceof BlockEntityMachine<?> machine ? machine.recipeHandler.side(side).orElse(null) : null;
        if (recipeHandler != null){
            long scale = recipeHandler.getMaxProgress() > 0 ? recipeHandler.getMaxProgress() / 15L : 0;
            long currentProgress = recipeHandler.getCurrentProgress();
            if (scale > 0){
                setOutputRedstone(inverted ? (int) (15L - currentProgress / scale) : (int) (currentProgress / scale));
            } else {
                setOutputRedstone(inverted ? 15 : 0);
            }
        }
    }
}
