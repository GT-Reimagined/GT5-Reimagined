package org.gtreimagined.gt5r.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import muramasa.antimatter.Ref;
import muramasa.antimatter.integration.jei.category.RecipeMapCategory;
import muramasa.antimatter.util.Utils;
import muramasa.antimatter.worldgen.vein.WorldGenVeinLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5RRef;
import org.gtreimagined.gt5r.integration.xei.OreByProduct;

public class OreProcessingCategory implements IRecipeCategory<OreByProduct> {
    IDrawable icon = RecipeMapCategory.guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Items.IRON_ORE.getDefaultInstance());
    IDrawable background = createDrawable("background");
    IDrawable base = createDrawable("base");
    IDrawable chemical = createDrawable("chem");
    IDrawable sep = createDrawable("sep");
    IDrawable sift = createDrawable("sift");
    IDrawable smelt = createDrawable("smelt");
    IDrawable vac = createDrawable("vac");
    public static final RecipeType<OreByProduct> ORE_BYPRODUCTS = new RecipeType<>(new ResourceLocation(GT5RRef.ID, "ore_byproducts"), OreByProduct.class);

    private static IDrawable createDrawable(String id) {
        return RecipeMapCategory.guiHelper.drawableBuilder(new ResourceLocation(GT5RRef.ID, "textures/gui/ore_byproducts/" + id + ".png"), 3, 3, 180, 160).setTextureSize(186, 166).build();
    }

    @Override
    public Component getTitle() {
        return Utils.translatable("jei.category.gt5r.ore_byproducts");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public RecipeType<OreByProduct> getRecipeType() {
       return ORE_BYPRODUCTS;
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation("gt5r", "ore_byproducts");
    }

    @Override
    public Class<? extends OreByProduct> getRecipeClass() {
        return OreByProduct.class;
    }
}
