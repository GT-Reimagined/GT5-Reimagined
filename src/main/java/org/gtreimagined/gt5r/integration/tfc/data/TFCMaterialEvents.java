package org.gtreimagined.gt5r.integration.tfc.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.enchantment.Enchantments;
import org.gtreimagined.gt5r.data.GT5RMaterialTypes;
import org.gtreimagined.gtlib.Data;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.material.MaterialTypeItem;
import org.gtreimagined.gtlib.tool.GTToolType;
import org.gtreimagined.gtlib.util.RegistryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCMaterialTypes.*;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.JAVELIN;
import static org.gtreimagined.gt5r.integration.tfc.data.TFCToolTypes.PROPICK;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.KNIFE_BLADE;
import static org.gtreimagined.gtlib.data.GTTools.*;

public class TFCMaterialEvents {
    public static void onMaterialEvent(MaterialEvent<?> event){
        MaterialTags.TOOLS.remove(Gold);
        MaterialTags.TOOLS.remove(Iron);
        event.setMaterial(Iron).remove(DRILL_BIT, CHAINSAW_BIT, WRENCH_BIT, BUZZSAW_BLADE, SMALL_GEAR, BOLT, SCREW, FRAME, GT5RMaterialTypes.TURBINE_BLADE,
                RING, SWORD_BLADE, PICKAXE_HEAD, SHOVEL_HEAD, AXE_HEAD, HOE_HEAD, HAMMER_HEAD, FILE_HEAD, KNIFE_BLADE, SAW_BLADE, SCREWDRIVER_TIP, SCYTHE_BLADE, PROPICK_HEAD, JAVELIN_HEAD,
                GT5RMaterialTypes.SMALL_BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.LARGE_BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.HUGE_BROKEN_TURBINE_ROTOR);
        event.setMaterial(Gold).remove(DRILL_BIT, CHAINSAW_BIT, WRENCH_BIT, BUZZSAW_BLADE, GT5RMaterialTypes.TURBINE_BLADE,
                SWORD_BLADE, PICKAXE_HEAD, SHOVEL_HEAD, AXE_HEAD, HOE_HEAD, HAMMER_HEAD, FILE_HEAD, KNIFE_BLADE, SAW_BLADE, SCREWDRIVER_TIP, SCYTHE_BLADE, PROPICK_HEAD, JAVELIN_HEAD,
                GT5RMaterialTypes.SMALL_BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.LARGE_BROKEN_TURBINE_ROTOR, GT5RMaterialTypes.HUGE_BROKEN_TURBINE_ROTOR);
        WroughtIron.setRgb(Iron.getRGB());
        IronMagnetic.setDisplayNameString("Magnetic Wrought Iron");
        event.setMaterial(WroughtIron).flags(SMALL_GEAR);
        event.setMaterial(Flint).flags(AXE_HEAD, SHOVEL_HEAD, HOE_HEAD, KNIFE_BLADE)
                .tool(Flint).toolDurability(32).toolEnchantments(ImmutableMap.of(Enchantments.FIRE_ASPECT, 1))
                .allowedToolTypes(List.of(GTTools.AXE, GTTools.SHOVEL, GTTools.HOE, GTTools.MORTAR, GTTools.KNIFE, TFCToolTypes.JAVELIN)).build();
        Material[] materials = new Material[]{Bismuth, Brass, Copper, Gold, Nickel, RoseGold, Silver, SterlingSilver, Tin, Zinc};
        for (Material material : materials){
            INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/ingot/"+material.getId()));
            DOUBLE_INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/double_ingot/" + material.getId()));
            ROD.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/rod/"+material.getId()));
            SHEET.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/sheet/"+ material.getId()));
        }
        Data.setMaterialTier(WroughtIron, 2);
        materials = new Material[]{BismuthBronze, BlackBronze, BlackSteel,
                BlueSteel, Bronze, RedSteel, Steel, WroughtIron};
        for (Material material : materials){
            INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/ingot/"+material.getId()));
            DOUBLE_INGOT.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/double_ingot/" + material.getId()));
            ROD.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/rod/"+material.getId()));
            SHEET.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/sheet/"+ material.getId()));
            if (material.has(MaterialTags.TOOLS)){
                GTToolType[] toolTypes = new GTToolType[]{PICKAXE, PROPICK, AXE, SHOVEL, HOE, HAMMER, SAW, JAVELIN, SWORD, KNIFE, SCYTHE};
                List<MaterialTypeItem<?>> types = new ArrayList<>(Arrays.stream(toolTypes).map(GTToolType::getMaterialTypeItem).toList());
                types.addAll(List.of(TFCMaterialTypes.CHISEL_HEAD, TFCMaterialTypes.MACE_HEAD));
                for (MaterialTypeItem<?> type : types){
                    type.replacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/" + type.getId() + "/" + material.getId()));
                }
                for (GTToolType toolType : toolTypes){
                    toolType.addReplacement(material, () -> RegistryUtils.getItemFromID("tfc", "metal/" + toolType.getId() + "/" + material.getId()));
                }
            }
        }
    }
}
