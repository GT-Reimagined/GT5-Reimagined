package org.gtreimagined.gt5r.blockentity.single;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.MachineFlag;
import org.gtreimagined.gtlib.machine.types.Machine;

public class BlockEntityMassFabricator extends BlockEntityMachine<BlockEntityMassFabricator> {
    public BlockEntityMassFabricator(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new MachineRecipeHandler<>(this){
            @Override
            public long getPower() {
                if (activeRecipe == null) return 0;
                if (overclock == 0 || tile.has(MachineFlag.FE)) return activeRecipe.getPower();
                //half the duration => overclock ^ 2.
                //so if overclock is 2 tiers, we have 1/4 the duration(200 -> 50) but for e.g. 8eu/t this would be
                //8*4*4 = 128eu/t.
                return (activeRecipe.getPower() * (1L << overclock));
            }
        });
    }
}
