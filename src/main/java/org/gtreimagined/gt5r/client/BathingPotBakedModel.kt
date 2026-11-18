package org.gtreimagined.gt5r.client

import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gt5r.blockentity.single.BlockEntityBathingPot
import org.gtreimagined.gtlib.client.ModelUtils
import org.gtreimagined.gtlib.client.baked.GTBakedModel

class BathingPotBakedModel(p: TextureAtlasSprite, val baseModel: BakedModel, val insideModel: BakedModel): GTBakedModel<BathingPotBakedModel>(p) {
    override fun getBlockQuads(state: BlockState?, side: Direction?, rand: RandomSource, level: BlockAndTintGetter, pos: BlockPos): List<BakedQuad> {
        val list = mutableListOf<BakedQuad>()
        list.addAll(ModelUtils.getQuadsFromBaked(baseModel, state, side, rand, level, pos))
        val be = level.getBlockEntity(pos)
        if (be is BlockEntityBathingPot){
            be.itemHandler.ifPresent {
                if (!it.inputHandler.isEmpty || !it.outputHandler.isEmpty) {
                    list.addAll(ModelUtils.getQuadsFromBaked(insideModel, state, side, rand, level, pos))
                }
            }
        }
        return list
    }

    override fun useAmbientOcclusion(): Boolean {
        return false
    }

    override fun isGui3d(): Boolean {
        return true
    }

    override fun usesBlockLight(): Boolean {
        return true
    }

    override fun isCustomRenderer(): Boolean {
        return true
    }

    override fun getOverrides(): ItemOverrides {
        return ItemOverrides.EMPTY
    }
}