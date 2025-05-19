package org.gtreimagined.gt5r.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.data.GT5RMaterialTags;
import org.gtreimagined.gt5r.integration.rei.OreProcessingDisplay;
import org.gtreimagined.gt5r.integration.xei.OreByProduct;
import org.gtreimagined.gt5r.integration.xei.OreByProduct.BathingMode;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.data.GTMaterialTypes;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class GT5RJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(GT5Reimagined.ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        if (GTAPI.isModLoaded(Ref.MOD_REI)) return;
        registration.addRecipeCategories(new OreProcessingCategory());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<OreByProduct> oreByProducts = new ArrayList<>();
        GTMaterialTypes.ORE.all().forEach(m -> {
            if (!m.has(GTMaterialTypes.CRUSHED)) return;
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
