package org.gtreimagined.gt5r.integration.recipeviewer.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gtlib.data.GTMaterialTypes;
import org.gtreimagined.gtlib.machine.Tier;

@REIPluginClient
public class GT5RREIPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {

        OreProcessingCategory cat = new OreProcessingCategory();
        registry.add(cat);
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.MACERATOR.getItem(Tier.LV))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.ORE_WASHER.getItem(Tier.LV))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.CENTRIFUGE.getItem(Tier.LV))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.THERMAL_CENTRIFUGE.getItem(Tier.LV))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.BATH.getItem(Tier.NONE))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.ELECTROMAGNETIC_SEPARATOR.getItem(Tier.LV))));
        registry.addWorkstations(cat.getCategoryIdentifier(), EntryStack.of(VanillaEntryTypes.ITEM,  new ItemStack(GT5RMachines.SIFTER.getItem(Tier.LV))));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        GTMaterialTypes.ORE.all().forEach(m -> {
            registry.add(new OreProcessingDisplay(new OreByProduct(m)));
        });

    }
}
