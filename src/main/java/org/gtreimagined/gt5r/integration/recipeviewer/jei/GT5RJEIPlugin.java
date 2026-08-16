package org.gtreimagined.gt5r.integration.recipeviewer.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RMachines;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gtlib.machine.Tier.LV;
import static org.gtreimagined.gtlib.machine.Tier.NONE;

@JeiPlugin
public class GT5RJEIPlugin implements IModPlugin {
    static IGuiHelper helper;
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(GT5Reimagined.ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        if (GTAPI.isModLoaded(Ref.MOD_REI)) return;
        helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new OreProcessingCategory());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.MACERATOR.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.ORE_WASHER.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.CENTRIFUGE.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.THERMAL_CENTRIFUGE.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.BATH.getItem(NONE)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.ELECTROMAGNETIC_SEPARATOR.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
        registration.addRecipeCatalyst(new ItemStack(GT5RMachines.SIFTER.getItem(LV)), OreProcessingCategory.ORE_BYPRODUCTS);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<OreByProduct> oreByProducts = new ArrayList<>();
        GTMaterialTypes.ORE.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.CRUSHED_ORE)) return;
            if (m.has(GT5RMaterialTags.BATH_PERSULFATE) || m.has(GT5RMaterialTags.BATH_MERCURY)){
                if (m.has(GT5RMaterialTags.BATH_MERCURY)) oreByProducts.add(new OreByProduct(m, BathingMode.MERCURY));
                if (m.has(GT5RMaterialTags.BATH_PERSULFATE)) oreByProducts.add(new OreByProduct(m, BathingMode.PERSULFATE));
            } else {
                oreByProducts.add(new OreByProduct(m, BathingMode.NONE));
            }
        });
        registration.addRecipes(OreProcessingCategory.ORE_BYPRODUCTS, oreByProducts);
    }
}
