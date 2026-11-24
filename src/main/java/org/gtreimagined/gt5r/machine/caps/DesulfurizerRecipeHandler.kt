package org.gtreimagined.gt5r.machine.caps

import net.minecraft.nbt.CompoundTag
import org.gtreimagined.gt5r.blockentity.multi.BlockEntityDesulfurizer
import org.gtreimagined.gtlib.capability.machine.MachineRecipeHandler
import org.gtreimagined.gtlib.machine.event.IMachineEvent
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

    override fun init() {
        super.init()
        secondRecipeHandler.init()
        thirdRecipeHandler.init()
        fourthRecipeHandler.init()
        fifthRecipeHandler.init()
    }

    override fun onMultiBlockStateChange(isValid: Boolean, hardcore: Boolean) {
        super.onMultiBlockStateChange(isValid, hardcore)
        secondRecipeHandler.onMultiBlockStateChange(isValid, hardcore)
        thirdRecipeHandler.onMultiBlockStateChange(isValid, hardcore)
        fourthRecipeHandler.onMultiBlockStateChange(isValid, hardcore)
        fifthRecipeHandler.onMultiBlockStateChange(isValid, hardcore)
    }

    override fun onMachineEvent(event: IMachineEvent?, vararg data: Any?) {
        super.onMachineEvent(event, *data)
        secondRecipeHandler.onMachineEvent(event, data)
        thirdRecipeHandler.onMachineEvent(event, data)
        fourthRecipeHandler.onMachineEvent(event, data)
        fifthRecipeHandler.onMachineEvent(event, data)
    }

    override fun onRemove() {
        super.onRemove()
        secondRecipeHandler.onRemove()
        thirdRecipeHandler.onRemove()
        fourthRecipeHandler.onRemove()
        fifthRecipeHandler.onRemove()
    }

    override fun resetProgress() {
        super.resetProgress()
        secondRecipeHandler.resetProgress()
        thirdRecipeHandler.resetProgress()
        fourthRecipeHandler.resetProgress()
        fifthRecipeHandler.resetProgress()
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

    override fun serialize(): CompoundTag {
        val firstTag = super.serialize()
        val secondTag = secondRecipeHandler.serialize()
        val thirdTag = thirdRecipeHandler.serialize()
        val fourthTag = fourthRecipeHandler.serialize()
        val fifthTag = fifthRecipeHandler.serialize()
        val tag = CompoundTag()
        tag.put("first", firstTag)
        tag.put("second", secondTag)
        tag.put("third", thirdTag)
        tag.put("fourth", fourthTag)
        tag.put("fifth", fifthTag)
        return tag
    }

    override fun deserialize(nbt: CompoundTag) {
        super.deserialize(nbt.getCompound("first"))
        secondRecipeHandler.deserialize(nbt.getCompound("second"))
        thirdRecipeHandler.deserialize(nbt.getCompound("third"))
        fourthRecipeHandler.deserialize(nbt.getCompound("fourth"))
        fifthRecipeHandler.deserialize(nbt.getCompound("fifth"))
    }
}