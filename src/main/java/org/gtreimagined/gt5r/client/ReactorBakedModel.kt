package org.gtreimagined.gt5r.client

import com.google.common.collect.ImmutableMap
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import org.gtreimagined.gt5r.blockentity.single.BlockEntityNuclearReactorCore
import org.gtreimagined.gt5r.data.GT5RCovers
import org.gtreimagined.gtlib.Ref
import org.gtreimagined.gtlib.client.ModelUtils
import org.gtreimagined.gtlib.client.baked.MachineBakedModel
import org.gtreimagined.gtlib.client.quad.ITextureReferenceBakedQuad
import org.gtreimagined.gtlib.client.quad.RetexturedBakedQuad
import org.gtreimagined.gtlib.machine.MachineState

class ReactorBakedModel(particle: TextureAtlasSprite, sides: ImmutableMap<MachineState, Array<BakedModel>>, private val rodModels: Array<BakedModel>) : MachineBakedModel(particle, sides) {
    override fun getBlockQuads(state: BlockState, side: Direction?, rand: RandomSource, level: BlockAndTintGetter, pos: BlockPos): MutableList<BakedQuad> {
        if (side == null) {
            return mutableListOf()
        }
        val tile = level.getBlockEntity(pos)
        if (tile !is BlockEntityNuclearReactorCore) return mutableListOf()
        val props = getMachineProperty(tile) ?: return mutableListOf()
        val superBlockQuads: MutableList<BakedQuad> = ObjectArrayList(20)
        val coverQuads = getCoverQuads(state, side, rand, props, tile, level, pos)
        val cover = props.covers[side.get3DDataValue()]
        val isOutputCover =
            cover.getFactory() === GT5RCovers.COVER_REACTOR_OUTPUT || cover.getFactory() === GT5RCovers.COVER_REACTOR_OUTPUT_SECONDARY
        val model = getModel(state, side, props.state, props.type)
        for (dir in Ref.DIRS) {
            superBlockQuads.addAll(ModelUtils.getQuadsFromBaked(model, state, dir, rand, level, pos))
        }
        superBlockQuads.addAll(ModelUtils.getQuadsFromBaked(model, state, null, rand, level, pos))
        if (coverQuads.isNotEmpty()) {
            if (isOutputCover) {
                return coverQuads
            }
            val ft = tile.multiTexture
            for (i in superBlockQuads.indices) {
                val quad = superBlockQuads[i]
                if ((quad as ITextureReferenceBakedQuad).`gtLib$getTextureId`() == "#base" && quad.getDirection() == side.opposite) {
                    coverQuads.add(RetexturedBakedQuad(quad, ModelUtils.getSprite(ft.apply(side))))
                }
            }
            return coverQuads
        }

        if (side == Direction.UP) {
            val list: MutableList<BakedQuad> = ArrayList()
            for (i in 0..3) {
                val rod = tile.getRod(i)
                if (rod.isEmpty) continue
                val rodModel = rodModels[i]
                for (dir in Ref.DIRS) {
                    list.addAll(ModelUtils.getQuadsFromBaked(rodModel, state, dir, rand, level, pos))
                }
                list.addAll(ModelUtils.getQuadsFromBaked(rodModel, state, null, rand, level, pos))
            }
            list.addAll(superBlockQuads)
            return list
        }
        return superBlockQuads
    }
}
