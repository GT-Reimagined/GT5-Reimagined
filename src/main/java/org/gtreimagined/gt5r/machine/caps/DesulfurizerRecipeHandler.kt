package org.gtreimagined.gt5r.machine.caps

import org.gtreimagined.gt5r.blockentity.multi.BlockEntityDesulfurizer
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler
import org.gtreimagined.gtlib.recipe.IRecipe

class DesulfurizerRecipeHandler(tile: BlockEntityDesulfurizer) : MachineRecipeHandler<BlockEntityDesulfurizer>(tile) {
    val secondRecipeHandler: MachineRecipeHandler<BlockEntityDesulfurizer> = object: MachineRecipeHandler<BlockEntityDesulfurizer>(tile){
        override fun validateRecipe(r: IRecipe?): Boolean {
            if (r != null){
                if (this@DesulfurizerRecipeHandler.activeRecipe == r) return false
                if (thirdRecipeHandler.activeRecipe == r) return false
                if (fourthRecipeHandler.activeRecipe == r) return false
                if (fifthRecipeHandler.activeRecipe == r) return false
            }
            return super.validateRecipe(r)
        }
    }
    val thirdRecipeHandler: MachineRecipeHandler<BlockEntityDesulfurizer> = object: MachineRecipeHandler<BlockEntityDesulfurizer>(tile){
        override fun validateRecipe(r: IRecipe?): Boolean {
            if (r != null){
                if (this@DesulfurizerRecipeHandler.activeRecipe == r) return false
                if (secondRecipeHandler.activeRecipe == r) return false
                if (fourthRecipeHandler.activeRecipe == r) return false
                if (fifthRecipeHandler.activeRecipe == r) return false
            }
            return super.validateRecipe(r)
        }
    }
    val fourthRecipeHandler: MachineRecipeHandler<BlockEntityDesulfurizer> = object: MachineRecipeHandler<BlockEntityDesulfurizer>(tile){
        override fun validateRecipe(r: IRecipe?): Boolean {
            if (r != null){
                if (this@DesulfurizerRecipeHandler.activeRecipe == r) return false
                if (secondRecipeHandler.activeRecipe == r) return false
                if (thirdRecipeHandler.activeRecipe == r) return false
                if (fifthRecipeHandler.activeRecipe == r) return false
            }
            return super.validateRecipe(r)
        }
    }
    val fifthRecipeHandler: MachineRecipeHandler<BlockEntityDesulfurizer> = object: MachineRecipeHandler<BlockEntityDesulfurizer>(tile){
        override fun validateRecipe(r: IRecipe?): Boolean {
            if (r != null){
                if (this@DesulfurizerRecipeHandler.activeRecipe == r) return false
                if (secondRecipeHandler.activeRecipe == r) return false
                if (thirdRecipeHandler.activeRecipe == r) return false
                if (fourthRecipeHandler.activeRecipe == r) return false
            }
            return super.validateRecipe(r)
        }
    }
    override fun onServerUpdate() {
        super.onServerUpdate()
        secondRecipeHandler.onServerUpdate()
        thirdRecipeHandler.onServerUpdate()
        fourthRecipeHandler.onServerUpdate()
        fifthRecipeHandler.onServerUpdate()
    }

    override fun validateRecipe(r: IRecipe?): Boolean {
        if (r != null){
            if (secondRecipeHandler.activeRecipe == r) return false
            if (thirdRecipeHandler.activeRecipe == r) return false
            if (fourthRecipeHandler.activeRecipe == r) return false
            if (fifthRecipeHandler.activeRecipe == r) return false
        }
        return super.validateRecipe(r)
    }

    override fun checkRecipe() {
        super.checkRecipe()
        secondRecipeHandler.checkRecipe()
        thirdRecipeHandler.checkRecipe()
        fourthRecipeHandler.checkRecipe()
        fifthRecipeHandler.checkRecipe()
    }
}