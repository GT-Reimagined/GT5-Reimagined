package org.gtreimagined.gt5r.client

import net.minecraft.resources.ResourceLocation
import org.gtreimagined.gt5r.GT5Reimagined
import org.gtreimagined.gt5r.client.ReactorModelLoader

object GT5RModelManager {
    @JvmField
    val LOADER_REACTOR: ResourceLocation = ResourceLocation(GT5Reimagined.ID, "reactor")
    @JvmField
    val LOADER_BATHING_POT: ResourceLocation = ResourceLocation(GT5Reimagined.ID, "bathing_pot")

    @JvmStatic
    fun init() {
        ReactorModelLoader.init()
        BathingPotModelLoader.init()
    }
}
