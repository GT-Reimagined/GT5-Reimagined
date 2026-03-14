package org.gtreimagined.gt5r.blockentity.single;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.data.RecipeMaps;
import org.gtreimagined.gtcore.blockentity.BlockEntitySteamMachine;
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine;
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.recipe.map.IRecipeMap;

import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.machine.Tier.MV;

public class BlockEntitySteamMacerator extends BlockEntitySteamMachine {
    public BlockEntitySteamMacerator(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipeHandler.set(() -> new SteamMachineRecipeHandler(this){
            @Override
            public IRecipeMap getRecipeMap() {
                return RecipeMaps.MACERATOR;
            }
        });
    }
}
