package org.gtreimagined.gt5r.integration.botania;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import org.gtreimagined.gt5r.data.Materials;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.GTMod;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.data.GTTools;
import org.gtreimagined.gtlib.event.MaterialEvent;
import org.gtreimagined.gtlib.material.MaterialTags;
import org.gtreimagined.gtlib.registration.RegistrationEvent;
import org.gtreimagined.gtlib.util.RegistryUtils;

import static org.gtreimagined.gt5r.data.Materials.*;
import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;

public class BotaniaRegistrar extends GTMod {
    @Override
    public void onRegistrationEvent(RegistrationEvent event, Dist side) {

    }

    @Override
    public void onMaterialEvent(MaterialEvent event) {
        event.setMaterial(Materials.Manasteel).asMetal(1811, GTMaterialTypes.PLATE, MaterialTags.MAGIC)
                .tool(Iron).toolQuality(3).toolSpeed(12).toolDurability(300).toolEnchantments(ImmutableMap.of(Enchantments.MOB_LOOTING, 2, Enchantments.BLOCK_FORTUNE, 2)).handleMaterial(Livingwood).build()
                .mats(ImmutableMap.of(Iron, 1, Magic, 1));
        ROD.replacement(Livingwood, () -> botItem("livingwood_twig"));
        INGOT.replacement(Materials.Manasteel, () -> botItem("manasteel_ingot"));
        NUGGET.replacement(Materials.Manasteel, () -> botItem("manasteel_nugget"));
        GTTools.PICKAXE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_pick"));
        GTTools.AXE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_axe"));
        GTTools.SWORD.addReplacement(Materials.Manasteel, () -> botItem("manasteel_sword"));
        GTTools.SHOVEL.addReplacement(Materials.Manasteel, () -> botItem("manasteel_shovel"));
        GTTools.HOE.addReplacement(Materials.Manasteel, () -> botItem("manasteel_hoe"));
    }

    @Override
    public boolean isEnabled() {
        return GTAPI.isModLoaded(getId());
    }

    private Item botItem(String id) {
        return RegistryUtils.getItemFromID(getId(), id);
    }

    @Override
    public String getId() {
        return "botania";
    }
}
