package org.gtreimagined.gt5r.client

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.resources.model.UnbakedModel
import org.gtreimagined.gtlib.client.model.loader.GTModelLoader

object BathingPotModelLoader: GTModelLoader<BathingPotModel>(GT5RModelManager.LOADER_BATHING_POT) {
    override fun read(json: JsonObject, context: JsonDeserializationContext): BathingPotModel {
        val baseJson = json.deepCopy()
        baseJson.remove("loader")
        val base: UnbakedModel = context.deserialize<BlockModel>(baseJson, BlockModel::class.java)
        val insideJson = json.getAsJsonObject("inside")
        val inside: UnbakedModel = context.deserialize<BlockModel>(insideJson, BlockModel::class.java)
        return BathingPotModel(base, inside)
    }

    fun init() {}
}