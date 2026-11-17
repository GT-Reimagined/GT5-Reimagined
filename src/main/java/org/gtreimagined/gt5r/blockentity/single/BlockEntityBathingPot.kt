package org.gtreimagined.gt5r.blockentity.single

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gtlib.blockentity.BlockEntityMachine
import org.gtreimagined.gtlib.capability.item.TrackedItemHandler
import org.gtreimagined.gtlib.capability.machine.MachineFluidHandler
import org.gtreimagined.gtlib.capability.machine.MachineItemHandler
import org.gtreimagined.gtlib.gui.SlotTypes
import org.gtreimagined.gtlib.machine.types.Machine

class BlockEntityBathingPot(type: Machine<*>, pos: BlockPos, state: BlockState) :
    BlockEntityMachine<BlockEntityBathingPot>(type, pos, state) {
    init {
        this.itemHandler.set {
            object : MachineItemHandler<BlockEntityBathingPot>(this){
                init {
                    this.inventories[SlotTypes.IT_IN] = TrackedItemHandler(this@BlockEntityBathingPot, SlotTypes.IT_IN, 1, false, true, SlotTypes.IT_IN.tester)
                    this.inventories[SlotTypes.IT_OUT] = TrackedItemHandler(this@BlockEntityBathingPot, SlotTypes.IT_OUT, 3, true, false, SlotTypes.IT_OUT.tester)
                }
            }
        }
        this.fluidHandler.set {
            MachineFluidHandler(this, 8000, 1, 2, 0)
        }
    }
}