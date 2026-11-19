package org.gtreimagined.gt5r.block

import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import org.gtreimagined.gt5r.GT5Reimagined
import org.gtreimagined.gtlib.datagen.providers.GTBlockStateProvider
import org.gtreimagined.gtlib.datagen.providers.GTItemModelProvider
import org.gtreimagined.gtlib.machine.BlockMachine
import org.gtreimagined.gtlib.machine.Tier
import org.gtreimagined.gtlib.machine.types.Machine

class BlockBathingPot(type: Machine<*>, tier: Tier): BlockMachine(type, tier,
    Properties.of().strength(1.0f).sound(SoundType.WOOD)
) {
    override fun onItemModelBuild(item: ItemLike, prov: GTItemModelProvider) {
        prov.blockItem(this)
    }

    override fun onBlockModelBuild(block: Block, prov: GTBlockStateProvider) {
        prov.state(block){prov.existing(GT5Reimagined.ID, "block/bathing_pot")}
    }
}