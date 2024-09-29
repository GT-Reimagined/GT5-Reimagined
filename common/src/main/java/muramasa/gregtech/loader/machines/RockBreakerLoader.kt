@file:JvmName("RockBreakerLoader")
package muramasa.gregtech.loader.machines

import io.github.gregtechintergalactical.gtcore.data.GTCoreItems
import muramasa.gregtech.data.RecipeMaps.ROCK_BREAKER
import net.minecraft.world.item.Items

fun init(){
    ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS[0]).io(Items.COBBLESTONE).add("cobblestone", 16,32)
    ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS[1]).io(Items.STONE).add("stone", 16,32)
}
