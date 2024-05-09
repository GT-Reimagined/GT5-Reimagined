package muramasa.gregtech.machine.caps;

import muramasa.antimatter.blockentity.BlockEntityMachine;
import muramasa.antimatter.capability.machine.MachineCoverHandler;
import muramasa.antimatter.cover.CoverFactory;
import muramasa.antimatter.cover.ICover;
import muramasa.gregtech.data.GregTechCovers;
import muramasa.gregtech.machine.ISecondaryOutputMachine;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SecondaryOutputCoverHandler<T extends BlockEntityMachine<T>> extends MachineCoverHandler<T> {
    public SecondaryOutputCoverHandler(T tile) {
        super(tile);
    }

    private CoverFactory getSecondaryOutputCoverFactory() {
        if (getTile().getMachineType() instanceof ISecondaryOutputMachine machine){
            return machine.getSecondaryOutputCover();
        }
        return ICover.emptyFactory;
    }

    public Direction getSecondaryOutputFacing() {
        return lookupSingle(getSecondaryOutputCoverFactory());
    }

    public ICover getSecondaryOutputCover() {
        return get(lookupSingle(getSecondaryOutputCoverFactory()));
    }

    public boolean setSecondaryOutputFacing(Player entity, Direction side) {
        Direction dir = getSecondaryOutputFacing();
        if (dir == null) return false;
        if (side == dir || side == getOutputFacing()) return false;
        boolean ok = moveCover(entity, dir, side);
        if (ok) {
            getTile().invalidateCaps();
        }
        return ok;
    }

    @Override
    public boolean setOutputFacing(Player entity, Direction side) {
        if (side == getSecondaryOutputFacing()) return false;
        return super.setOutputFacing(entity, side);
    }

    @Override
    protected boolean canRemoveCover(ICover cover) {
        return super.canRemoveCover(cover) && cover.getFactory() != getSecondaryOutputCoverFactory();
    }

    @Override
    public boolean isValid(@NotNull Direction side, @NotNull ICover replacement) {
        if (!validCovers.contains(replacement.getLoc())) return false;
        if (side == getOutputFacing()) return false;
        return (get(side).isEmpty() && !replacement.isEmpty()) || super.isValid(side, replacement);
    }
}
