package org.gtreimagined.gt5r.integration.recipeviewer.jei;

import brachy.modularui.integration.jei.recipe.ModularUIJeiCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.widget.OreByProductWidget;

import java.util.List;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE;

public class OreProcessingCategory extends ModularUIJeiCategory<OreByProduct> {
    IDrawable icon = GT5RJEIPlugin.helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Items.IRON_ORE.getDefaultInstance());
    public static final RecipeType<OreByProduct> ORE_BYPRODUCTS = new RecipeType<>(new ResourceLocation(GT5Reimagined.ID, "ore_byproducts_tree"), OreByProduct.class);

    public OreProcessingCategory() {
        super(OreByProductWidget::new, OreByProductWidget::id);
    }

    @Override
    public int getMaxWidth() {
        return 186;
    }

    @Override
    public int getMaxHeight() {
        return 256;
    }

    @Override
    public void setupRecipeIngredients(IRecipeLayoutBuilder builder, OreByProduct byProduct, IFocusGroup iFocusGroup) {
        builder.addInputSlot().addIngredients(VanillaTypes.ITEM_STACK, List.of(ORE.getMaterialIngredient(byProduct.material(), 1).getItems()));
        byProduct.getSlots().forEach(r -> {
            IRecipeSlotBuilder slotBuilder = builder.addSlot(r.input() ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT);
            if (!r.stacks().isEmpty()) {
                slotBuilder.addIngredients(VanillaTypes.ITEM_STACK, r.stacks());
            }
            if (!r.fluidStacks().isEmpty()){
                slotBuilder.addIngredients(ForgeTypes.FLUID_STACK, r.fluidStacks());
            }
        });

    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public RecipeType<OreByProduct> getRecipeType() {
       return ORE_BYPRODUCTS;
    }
}
