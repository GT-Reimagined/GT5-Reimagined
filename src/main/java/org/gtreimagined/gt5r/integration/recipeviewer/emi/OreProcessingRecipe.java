package org.gtreimagined.gt5r.integration.recipeviewer.emi;

import brachy.modularui.api.widget.IWidget;
import brachy.modularui.integration.emi.recipe.ModularUIEmiCategory;
import brachy.modularui.integration.emi.recipe.ModularUIEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.widget.OreByProductWidget;
import org.gtreimagined.gtlib.data.GTMaterialTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OreProcessingRecipe extends ModularUIEmiRecipe {
    public static final EmiRecipeCategory CATEGORY = new ModularUIEmiCategory(new ResourceLocation(GT5Reimagined.ID, "ore_byproducts_tree"), EmiStack.of(Items.IRON_ORE));
    final List<EmiIngredient> inputs;
    final List<EmiStack> outputs;

    public OreProcessingRecipe(OreByProduct byProduct) {
        super(OreByProductWidget.id(byProduct), () -> new OreByProductWidget(byProduct));
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.inputs.add(EmiIngredient.of(GTMaterialTypes.ORE.getBlockMaterialTag(byProduct.material())));
        byProduct.getSlots().forEach(r -> {
            if (!r.stacks().isEmpty()) {
                if (r.input()){
                    inputs.add(EmiIngredient.of(r.stacks().stream().map(EmiStack::of).toList()));
                } else {
                    outputs.addAll(r.stacks().stream().map(EmiStack::of).toList());
                }
            }
            if (!r.fluidStacks().isEmpty()){
                if (r.input()){
                    inputs.add(EmiIngredient.of(r.fluidStacks().stream().map(f -> EmiStack.of(f.getFluid(), f.getAmount())).toList()));
                } else {
                    outputs.addAll(r.fluidStacks().stream().map(f -> EmiStack.of(f.getFluid(), f.getAmount())).toList());
                }
            }
        });
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }
}
