package org.gtreimagined.gt5r.integration.recipeviewer.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gtlib.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.*;
import static org.gtreimagined.gtlib.integration.recipeviewer.rei.REIUtils.toREIFLuidStack;

public class OreProcessingCategory implements DisplayCategory<OreProcessingDisplay> {
    protected static Renderer icon = EntryStacks.of(Items.IRON_ORE);
    private static final Component title = Utils.translatable(GT5Reimagined.ID + ".rei.tooltip.ore.byproducts");
    static CategoryIdentifier<? extends OreProcessingDisplay> id = CategoryIdentifier.of(GT5Reimagined.ID, "ore_byproducts");

    @Override
    public CategoryIdentifier<? extends OreProcessingDisplay> getCategoryIdentifier() {
        return id;
    }

    @Override
    public List<Widget> setupDisplay(OreProcessingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createDrawableWidget((helper, mouseX, mouseY, delta) -> {
            drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/background.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/base.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            if (display.oreByProduct.bathingMode() != OreByProduct.BathingMode.NONE){
                drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/chem.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            }
            if (display.oreByProduct.hasSiftingRecipe()){
                drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/sift.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            }
            if (display.oreByProduct.hasSepRecipes()){
                drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/sep.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            }
            if (display.oreByProduct.hasFurnaceSmeltingRecipe()){
                drawTexture(helper, new ResourceLocation(GT5Reimagined.ID, "textures/gui/ore_byproducts/smelt.png"), bounds.x, bounds.y, 0, 0, bounds.getWidth(), bounds.getHeight());
            }
        }));
        widgets.addAll(setupSlots(display, bounds));
        return widgets;
    }

    private List<Widget> setupSlots(OreProcessingDisplay display, Rectangle bounds){
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createSlot(xy(4, 4, bounds)).entries(EntryIngredients.ofIngredient(ORE.getMaterialIngredient(display.oreByProduct.material(), 1))).markInput().disableBackground());
        display.oreByProduct.getSlots().forEach(s -> {
            Slot slot = Widgets.createSlot(xy(s.x() + 3, s.y() + 3, bounds));
            if (!s.fluidStacks().isEmpty()){
                slot.entries(EntryIngredient.of(s.fluidStacks().stream().map(f -> EntryStack.of(VanillaEntryTypes.FLUID, toREIFLuidStack(f))).toList()));
            }
            if (!s.stacks().isEmpty()){
                slot.entries(EntryIngredient.of(s.stacks().stream().map(i -> EntryStack.of(VanillaEntryTypes.ITEM, i)).toList()));
            }
            if (s.input()) slot.markInput();
            else slot.markOutput();
            widgets.add(slot);
        });
        return widgets;
    }

    private Point xy(int x, int y, Rectangle bounds){
        int offsetX = 0, offsetY = 0;
        return new Point(offsetX + x + bounds.x, offsetY + y + bounds.y);
    }

    private static void drawTexture(GuiGraphics graphics, ResourceLocation loc, int left, int top, int x, int y, int sizeX, int sizeY) {
        graphics.blit(loc, left, top, 0, x, y, sizeX, sizeY, 186, 166);
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Renderer getIcon() {
        return icon;
    }


    @Override
    public int getDisplayHeight() {
        return 166;
    }

    @Override
    public int getDisplayWidth(OreProcessingDisplay display) {
        return 186;
    }
}
