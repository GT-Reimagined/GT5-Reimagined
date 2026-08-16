package org.gtreimagined.gt5r.integration.recipeviewer.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gt5r.integration.recipeviewer.jei.OreProcessingCategory;
import org.gtreimagined.gtlib.data.GTMaterialTypes;

import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.machine.Tier.NONE;

@EmiEntrypoint
public class GT5REmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry emiRegistry) {

        emiRegistry.addCategory(OreProcessingRecipe.CATEGORY);
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.MACERATOR.getItem(LV)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.ORE_WASHER.getItem(LV)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.CENTRIFUGE.getItem(LV)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.THERMAL_CENTRIFUGE.getItem(LV)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.BATH.getItem(NONE)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.ELECTROMAGNETIC_SEPARATOR.getItem(LV)));
        emiRegistry.addWorkstation(OreProcessingRecipe.CATEGORY, EmiStack.of(GT5RMachines.SIFTER.getItem(LV)));
        GTMaterialTypes.ORE.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.CRUSHED_ORE)) return;
            if (m.has(GT5RMaterialTags.BATH_PERSULFATE) || m.has(GT5RMaterialTags.BATH_MERCURY)){
                if (m.has(GT5RMaterialTags.BATH_MERCURY)) emiRegistry.addRecipe(new OreProcessingRecipe(new OreByProduct(m, BathingMode.MERCURY)));
                if (m.has(GT5RMaterialTags.BATH_PERSULFATE)) emiRegistry.addRecipe(new OreProcessingRecipe(new OreByProduct(m, BathingMode.PERSULFATE)));
            } else {
                emiRegistry.addRecipe(new OreProcessingRecipe(new OreByProduct(m, BathingMode.NONE)));
            }
        });
    }
}
