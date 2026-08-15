package org.gtreimagined.gt5r.integration.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.Triple;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gtlib.util.Utils;

import java.util.List;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE;

public class OreProcessingCategory implements IRecipeCategory<OreByProduct> {
    IDrawable icon = GT5RJEIPlugin.helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, Items.IRON_ORE.getDefaultInstance());
    IDrawable background = createDrawable("background");
    IDrawable base = createDrawable("base");
    IDrawable chemical = createDrawable("chem");
    IDrawable sep = createDrawable("sep");
    IDrawable sift = createDrawable("sift");
    IDrawable smelt = createDrawable("smelt");
    public static final RecipeType<OreByProduct> ORE_BYPRODUCTS = new RecipeType<>(new ResourceLocation(GT5Reimagined.ID, "ore_byproducts_tree"), OreByProduct.class);

    private static IDrawable createDrawable(String id) {
        return GT5RJEIPlugin.helper.drawableBuilder(new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/" + id + ".png"), 3, 3, 180, 160).setTextureSize(186, 166).build();
    }

    @Override
    public void draw(OreByProduct recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics stack, double mouseX, double mouseY) {
        base.draw(stack);
        if (recipe.bathingMode() != BathingMode.NONE) chemical.draw(stack);
        if (recipe.hasSiftingRecipe()) sift.draw(stack);
        if (recipe.hasSepRecipes()) sep.draw(stack);
        if (recipe.hasFurnaceSmeltingRecipe()) smelt.draw(stack);
        List<Triple<Integer, Integer, Integer>> chances = recipe.getChanceOverlays();
        for (var chancePositions : chances){
            int chance = chancePositions.getRight();
            if (chance > 0 && chance < 10000){
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                stack.pose().pushPose();
                stack.pose().scale(0.5f, 0.5f, 1);
                String ch = (chance / 100) + "%";
                stack.drawString(Minecraft.getInstance().font, ch, 2*((float)chancePositions.getLeft()), 2*((float) chancePositions.getMiddle()), 0xFFFF00, true);

                stack.pose().popPose();
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OreByProduct recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(VanillaTypes.ITEM_STACK, List.of(ORE.getMaterialIngredient(recipe.material(), 1).getItems()));
        recipe.getSlots().forEach(r -> {
            IRecipeSlotBuilder slotBuilder = builder.addSlot(r.input() ? RecipeIngredientRole.INPUT : RecipeIngredientRole.OUTPUT, r.x(), r.y());
            if (!r.stacks().isEmpty()) {
                slotBuilder.addIngredients(VanillaTypes.ITEM_STACK, r.stacks());
            }
            if (!r.fluidStacks().isEmpty()){
                slotBuilder.addIngredients(ForgeTypes.FLUID_STACK, r.fluidStacks());
            }
            if (r.chance() > 0){
                slotBuilder.addTooltipCallback((recipe1, tooltip) -> {
                    tooltip.add(Utils.literal("Output Chance: " + ((float)r.chance() / 100) + "%").withStyle(ChatFormatting.WHITE));
                });
            }
        });
    }

    @Override
    public Component getTitle() {
        return Utils.translatable("jei.category.gt5r.ore_byproducts_tree");
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
}
