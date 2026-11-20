package org.gtreimagined.gt5r.client

import dev.latvian.mods.rhino.mod.util.NBTUtils.ba
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.Material
import net.minecraft.client.resources.model.ModelBaker
import net.minecraft.client.resources.model.ModelState
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.geometry.IGeometryBakingContext
import org.gtreimagined.gtlib.client.IGTModel
import java.util.function.Function

class BathingPotModel(val baseModel: UnbakedModel, val insideModel: UnbakedModel): IGTModel<BathingPotModel> {
    override fun bakeModel(configuration: IGeometryBakingContext, bakery: ModelBaker, getter: Function<Material, TextureAtlasSprite>, transform: ModelState, overrides: ItemOverrides, loc: ResourceLocation): BakedModel {
        val bakedBaseModel = baseModel.bake(bakery, getter, transform, loc)
        val bakedInsideModel = insideModel.bake(bakery, getter, transform, loc)
        if (bakedBaseModel == null || bakedInsideModel == null) error("BathingPotModelBaking failed")
        return BathingPotBakedModel(bakedBaseModel.particleIcon, bakedBaseModel, bakedInsideModel)
    }

    override fun resolveParents(modelGetter: Function<ResourceLocation, UnbakedModel>, context: IGeometryBakingContext) {
        baseModel.resolveParents(modelGetter)
        insideModel.resolveParents(modelGetter)
    }
}