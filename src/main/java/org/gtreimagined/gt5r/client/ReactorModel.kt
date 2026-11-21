package org.gtreimagined.gt5r.client

import com.google.common.collect.ImmutableMap
import net.ccbluex.fastutil.invoke
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.*
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.geometry.IGeometryBakingContext
import org.gtreimagined.gt5r.GT5Reimagined
import org.gtreimagined.gtlib.client.ModelUtils
import org.gtreimagined.gtlib.client.model.MachineModel
import org.gtreimagined.gtlib.machine.MachineState
import java.util.function.Function

class ReactorModel(models: MutableMap<MachineState, Array<UnbakedModel>>, particle: ResourceLocation?) :
    MachineModel(models, particle) {
    private var rodModels: Array<UnbakedModel>? = null
    override fun bakeModel(configuration: IGeometryBakingContext, bakery: ModelBaker, getter: Function<Material, TextureAtlasSprite>,
                           transform: ModelState, overrides: ItemOverrides, loc: ResourceLocation): BakedModel {
        val builder = ImmutableMap.builder<MachineState, Array<BakedModel>>()

        if (rodModels == null) {
            val rods = arrayOfNulls<UnbakedModel>(4)
            val array: Array<String> = arrayOf<String>("north-west", "south-west", "north-east", "south-east")
            //northwest is 0, southwest is 1, northeast is 2, southeast is 3
            for (i in 0..3) {
                val modelLocation = ResourceLocation(GT5Reimagined.ID, "block/machine/overlay/nuclear_reactor_core/${array[i]}-rod")
                try {
                    rods[i] = ModelUtils.getModel(modelLocation)
                } catch (e: Exception) {
                    e.printStackTrace()
                    //rods[i] = ModelUtils.getModel(modelLocation);
                }
            }
            rodModels = rods.requireNoNulls()
        }
        val rodModels = this.rodModels ?: error("rod models still null somehow, check log")
        for ((key, value) in this.models) {
            val mod = arrayOfNulls<BakedModel>(6)
            for (i in 0..5) {
                mod[i] = value[i].bake(bakery, getter, transform, loc)
            }
            builder.put(key, mod.requireNoNulls())
        }
        val rodModelsBaked = arrayOfNulls<BakedModel>(rodModels.size)
        for (i in rodModels.indices) {
            rodModelsBaked[i] = rodModels[i].bake(bakery, getter, transform, loc)
        }
        return ReactorBakedModel(
            getter(Material(TextureAtlas.LOCATION_BLOCKS, particle)), builder.build(), rodModelsBaked.requireNoNulls()
        )
    }
}
