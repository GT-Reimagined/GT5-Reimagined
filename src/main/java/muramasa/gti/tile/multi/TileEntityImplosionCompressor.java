package muramasa.gti.tile.multi;

import muramasa.antimatter.machine.types.Machine;
import muramasa.antimatter.tile.multi.TileEntityMultiMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityImplosionCompressor extends TileEntityMultiMachine<TileEntityImplosionCompressor> {

    public TileEntityImplosionCompressor(Machine type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

}
