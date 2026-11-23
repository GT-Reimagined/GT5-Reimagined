package org.gtreimagined.gt5r.blockentity.multi

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gt5r.machine.caps.DesulfurizerRecipeHandler
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine
import org.gtreimagined.gtlib.machine.types.Machine

class BlockEntityDesulfurizer(type: Machine<*>, pos: BlockPos, state: BlockState) :
    BlockEntityMultiMachine<BlockEntityDesulfurizer>(type, pos, state) {
    init {
        recipeHandler.set { DesulfurizerRecipeHandler(this) }
    }
}