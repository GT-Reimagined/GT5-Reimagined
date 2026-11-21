package org.gtreimagined.gt5r.client

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import net.minecraft.client.renderer.block.model.BlockModel
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation
import org.gtreimagined.gtlib.GTAPI
import org.gtreimagined.gtlib.client.model.MachineModel
import org.gtreimagined.gtlib.client.model.loader.GTModelLoader
import org.gtreimagined.gtlib.machine.MachineState
import java.util.*
import java.util.function.Consumer

object ReactorModelLoader : GTModelLoader<MachineModel?>(GT5RModelManager.LOADER_REACTOR) {
    override fun read(json: JsonObject, context: JsonDeserializationContext): MachineModel {
        val particle = if (json.has("particle"))
            ResourceLocation(json.get("particle").asString)
        else MissingTextureAtlasSprite.getLocation()
        val m: MutableMap<MachineState, Array<UnbakedModel>> = EnumMap(MachineState::class.java)
        GTAPI.all<MachineState> {
            if (json.has(it.toString().lowercase(Locale.getDefault()))) {
                val arr = json.get(it.toString().lowercase(Locale.getDefault())).getAsJsonArray()
                val a = arrayOfNulls<UnbakedModel>(6)
                for (i in 0..5) {
                    a[i] = context.deserialize<UnbakedModel?>(arr.get(i), BlockModel::class.java)
                }
                m[it] = a.requireNoNulls()
            }
        }
        return ReactorModel(m, particle)
    }
    fun init() {}
}
