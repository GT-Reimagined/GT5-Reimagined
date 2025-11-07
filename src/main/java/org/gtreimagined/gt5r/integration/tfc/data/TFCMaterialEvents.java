package org.gtreimagined.gt5r.integration.tfc.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.List;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.KNIFE_HEAD;

public class TFCMaterialEvents {
    public static void onMaterialEvent(MaterialEvent<?> event){
        event.setMaterial(Flint).flags(AXE_HEAD, SHOVEL_HEAD, HOE_HEAD, KNIFE_HEAD)
                .tool(Flint).toolDurability(32).toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 1))
                .allowedToolTypes(List.of(GTTools.AXE, GTTools.SHOVEL, GTTools.HOE, GTTools.MORTAR, GTTools.KNIFE, TFCToolTypes.JAVELIN)).build();
        Material[] materials = new Material[]{Bismuth, BismuthBronze, BlackBronze, BlackSteel,
                BlueSteel, Brass, Bronze, Copper, Gold, Nickel, RedSteel, RoseGold, Silver, Steel, SterlingSilver, Tin, WroughtIron, Zinc};
        for (Material material : materials){
            INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/ingot/"+material.getId()));
        }
    }
}
