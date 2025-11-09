package org.gtreimagined.gt5r.integration.tfc.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.List;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.KNIFE_BLADE;

public class TFCMaterialEvents {
    public static void onMaterialEvent(MaterialEvent<?> event){
        event.setMaterial(Flint).flags(AXE_HEAD, SHOVEL_HEAD, HOE_HEAD, KNIFE_BLADE)
                .tool(Flint).toolDurability(32).toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 1))
                .allowedToolTypes(List.of(GTTools.AXE, GTTools.SHOVEL, GTTools.HOE, GTTools.MORTAR, GTTools.KNIFE, TFCToolTypes.JAVELIN)).build();
        event.setMaterial(Osmium).flags(TFCMaterialTypes.CHISEL_HEAD, TFCMaterialTypes.MACE_HEAD);
        Material[] materials = new Material[]{Bismuth, Brass, Copper, Gold, Nickel, RoseGold, Silver, SterlingSilver, Tin, Zinc};
        for (Material material : materials){
            INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/ingot/"+material.getId()));
            ROD.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/rod/"+material.getId()));
        }
        materials = new Material[]{BismuthBronze, BlackBronze, BlackSteel,
                BlueSteel, Bronze, RedSteel, Steel, WroughtIron};
        for (Material material : materials){
            INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/ingot/"+material.getId()));
            ROD.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/rod/"+material.getId()));
            if (material.has(MaterialTags.TOOLS) && material != Gold && material != Nickel && material != RoseGold && material != SterlingSilver){
                MaterialTypeItem<?>[] types = new  MaterialTypeItem<?>[]{PICKAXE_HEAD, TFCMaterialTypes.PROPICK_HEAD, AXE_HEAD, SHOVEL_HEAD,
                        HOE_HEAD, TFCMaterialTypes.CHISEL_HEAD, HAMMER_HEAD, SAW_BLADE, TFCMaterialTypes.JAVELIN_HEAD, SWORD_BLADE,
                        TFCMaterialTypes.MACE_HEAD, KNIFE_BLADE, SCYTHE_BLADE};
                for (MaterialTypeItem<?> type : types){
                    String typeId = type == KNIFE_BLADE ? "knife_blade" : type.getId();
                    type.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/" + typeId + "/" + material.getId()));
                }
            }
        }
    }
}
