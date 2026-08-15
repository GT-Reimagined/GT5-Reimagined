package org.gtreimagined.gt5r.integration.rei;

import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.integration.recipeviewer.rei.REIUtils;
import org.gtreimagined.gtlib.machine.Tier;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;

public class REIRegistrar {

    public static void init(){
        REIUtils.addExtraCategory(r -> {
            OreProcessingCategory cat = new OreProcessingCategory();
            r.add(cat);
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.MACERATOR.getItem(Tier.LV))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.ORE_WASHER.getItem(Tier.LV))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.CENTRIFUGE.getItem(Tier.LV))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.THERMAL_CENTRIFUGE.getItem(Tier.LV))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.BATH.getItem(Tier.NONE))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.ELECTROMAGNETIC_SEPARATOR.getItem(Tier.LV))));
            r.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.SIFTER.getItem(Tier.LV))));
        });
        REIUtils.addExtraDisplay(r -> {
            GTMaterialTypes.ORE.all().forEach(m -> {
                if (m.has(GT5RMaterialTags.BATH_PERSULFATE) || m.has(GT5RMaterialTags.BATH_MERCURY)){
                    if (m.has(GT5RMaterialTags.BATH_MERCURY)) r.add(new OreProcessingDisplay(new OreByProduct(m, BathingMode.MERCURY)));
                    if (m.has(GT5RMaterialTags.BATH_PERSULFATE)) r.add(new OreProcessingDisplay(new OreByProduct(m, BathingMode.PERSULFATE)));
                } else {
                    r.add(new OreProcessingDisplay(new OreByProduct(m, BathingMode.NONE)));
                }
            });
        });
    }
}
