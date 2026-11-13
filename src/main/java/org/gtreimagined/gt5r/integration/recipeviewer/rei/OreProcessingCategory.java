package org.gtreimagined.gt5r.integration.recipeviewer.rei;

import brachy.modularui.integration.rei.recipe.ModularUIReiCategory;
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

public class OreProcessingCategory extends ModularUIReiCategory<OreProcessingDisplay> {
    protected static Renderer icon = EntryStacks.of(Items.IRON_ORE);
    static CategoryIdentifier<? extends OreProcessingDisplay> id = CategoryIdentifier.of(GT5Reimagined.ID, "ore_byproducts");

    @Override
    public CategoryIdentifier<? extends OreProcessingDisplay> getCategoryIdentifier() {
        return id;
    }

    @Override
    public Renderer getIcon() {
        return icon;
    }

    @Override
    public int getMaxDisplayHeight() {
        return 256;
    }
}
