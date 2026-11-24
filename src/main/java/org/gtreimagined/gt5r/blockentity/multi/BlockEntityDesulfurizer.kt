package org.gtreimagined.gt5r.blockentity.multi

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gt5r.machine.caps.DesulfurizerRecipeHandler
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler
import org.gtreimagined.gtlib.machine.MachineState
import org.gtreimagined.gtlib.machine.types.Machine
import java.util.function.Consumer

class BlockEntityDesulfurizer(type: Machine<*>, pos: BlockPos, state: BlockState) :
    BlockEntityMultiMachine<BlockEntityDesulfurizer>(type, pos, state) {
    init {
        recipeHandler.set { DesulfurizerRecipeHandler(this) }
    }

    override fun toggleMachine(): Boolean {
        if (!canBeDisabled()) return false
        if (getMachineState() == MachineState.DISABLED) {
            setMachineState(disabledState)
            disabledState = null
            if (getMachineState().allowRecipeCheck()) {
                recipeHandler.ifPresent {
                    it.checkRecipe()
                    val r = it as DesulfurizerRecipeHandler
                    r.secondRecipeHandler.checkRecipe()
                    r.thirdRecipeHandler.checkRecipe()
                    r.fourthRecipeHandler.checkRecipe()
                    r.fifthRecipeHandler.checkRecipe()
                }
            }
        } else {
            disableMachine()
        }
        return true
    }
}