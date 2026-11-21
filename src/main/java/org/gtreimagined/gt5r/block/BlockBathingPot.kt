package org.gtreimagined.gt5r.block

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom.pos
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gt5r.GT5Reimagined
import org.gtreimagined.gt5r.blockentity.single.BlockEntityBathingPot
import org.gtreimagined.gtcore.data.GTCoreColors
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider
import org.gtreimagined.gtlib.machine.BlockMachine
import org.gtreimagined.gtlib.machine.Tier
import org.gtreimagined.gtlib.machine.types.Machine
import org.gtreimagined.gtlib.material.MaterialType

class BlockBathingPot(type: Machine<*>, tier: Tier): BlockMachine(type, tier,
    Properties.of().strength(1.0f).sound(SoundType.WOOD)
) {
    override fun onItemModelBuild(item: ItemLike, prov: GTItemModelProvider) {
        prov.blockItem(this)
    }

    override fun onBlockModelBuild(block: Block, prov: GTBlockStateProvider) {
        prov.state(block){prov.existing(GT5Reimagined.ID, "block/bathing_pot")}
    }

    override fun getBlockColor(state: BlockState, world: BlockGetter?, pos: BlockPos?, i: Int): Int {
        return when(i){
            0 -> GTCoreColors.ANY_WOOD
            1 -> {
                if (world == null || pos == null) return -1
                val be = world.getBlockEntity(pos)
                if (be is BlockEntityBathingPot){
                    be.itemHandler.map {
                        val stacks = arrayOf(it.outputHandler.getStackInSlot(0), it.inputHandler.getStackInSlot(0))
                        for (stack in stacks){
                            val material = MaterialType.getMaterialFromStackTypeless(stack)
                            if (material != null){
                                return@map material.rgb
                            }
                        }
                        -1
                    }.orElse(-1)
                } else{
                    -1
                }
            }
            2 -> GTCoreColors.NEI
            else -> -1
        }
    }

    override fun getItemColor(stack: ItemStack?, block: Block?, i: Int): Int {
        return when(i){
            0 -> 0x664f2f
            2 -> 0xffff00
            else -> -1
        }
    }
}