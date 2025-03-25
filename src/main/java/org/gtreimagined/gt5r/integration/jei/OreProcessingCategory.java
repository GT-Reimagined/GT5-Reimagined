package org.gtreimagined.gt5r.integration.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gt5r.integration.xei.OreByProduct;

public class OreProcessingCategory implements IRecipeCategory<OreByProduct> {
    @Override
    public Component getTitle() {
        return null;
    }

    @Override
    public IDrawable getBackground() {
        return null;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public ResourceLocation getUid() {
        return null;
    }

    @Override
    public Class<? extends OreByProduct> getRecipeClass() {
        return null;
    }
}
