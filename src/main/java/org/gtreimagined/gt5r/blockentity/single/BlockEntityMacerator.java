package org.gtreimagined.gt5r.blockentity.single;

import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.data.RecipeMaps;

import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.machine.Tier.MV;

public class BlockEntityMacerator extends BlockEntityMachine<BlockEntityMacerator> {
    public BlockEntityMacerator(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public IRecipeMap getRecipeMap() {
                if (tile.getMachineTier() == LV || tile.getMachineTier() == MV) return RecipeMaps.MACERATOR;
                return super.getRecipeMap();
            }
        });
    }
}
