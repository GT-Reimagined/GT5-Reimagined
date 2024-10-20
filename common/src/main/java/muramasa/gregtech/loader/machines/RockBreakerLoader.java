package muramasa.gregtech.loader.machines;

import net.minecraft.world.item.Items;
import org.gtreimagined.gtcore.data.GTCoreItems;

import static muramasa.gregtech.data.RecipeMaps.ROCK_BREAKER;

public class RockBreakerLoader {
    public static void init(){
        ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(0)).io(Items.COBBLESTONE).add("cobblestone", 16,32);
        ROCK_BREAKER.RB().ii(GTCoreItems.SELECTOR_TAG_INGREDIENTS.get(1)).io(Items.STONE).add("stone", 16,32);
    }
}
