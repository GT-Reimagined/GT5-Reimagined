package muramasa.gregtech.loader.machines;

import io.github.gregtechintergalactical.gtcore.data.GTCoreItems;
import net.minecraft.world.item.Items;

import static muramasa.gregtech.data.RecipeMaps.ROCK_BREAKER;

public class RockBreakerLoader {
    public static void init(){
        ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0)).io(Items.COBBLESTONE).add("cobblestone", 16,32);
        ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(1)).io(Items.STONE).add("stone", 16,32);
    }
}
