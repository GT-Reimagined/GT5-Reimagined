package org.gtreimagined.gt5r.integration.recipeviewer.widget;

import brachy.modularui.ModularUI.Mods;
import brachy.modularui.api.drawable.IDrawable;
import brachy.modularui.integration.recipeviewer.RecipeSlotRole;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemTagList;
import brachy.modularui.widget.ParentWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.GT5Reimagined;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct.BathingMode;
import org.gtreimagined.gt5r.mui.GT5RGuiTextures;
import org.gtreimagined.gtlib.integration.recipeviewer.widgets.ChanceOverlay;
import org.gtreimagined.gtlib.util.Utils;

import java.util.Collections;
import java.util.Locale;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE;

public class OreByProductWidget extends ParentWidget<OreByProductWidget> {
    public OreByProductWidget(OreByProduct byProduct){
        this.size(186, 256);
        this.child(GT5RGuiTextures.BASE_BYPRODUCTS.asWidget().size(186, 166));
        if (byProduct.bathingMode() != BathingMode.NONE) this.child(GT5RGuiTextures.MERCURY_BYPRODUCTS.asWidget().size(186, 166));
        if (byProduct.hasSiftingRecipe()) this.child(GT5RGuiTextures.SIFT_BYPRODUCTS.asWidget().size(186, 166));
        if (byProduct.hasSepRecipes()) this.child(GT5RGuiTextures.SEP_BYPRODUCTS.asWidget().size(186, 166));
        if (byProduct.hasFurnaceSmeltingRecipe()) this.child(GT5RGuiTextures.FURNACE_BYPRODUCTS.asWidget().size(186, 166));
        ParentWidget<?> inputWidget = new ParentWidget<>();
        ParentWidget<?> outputWidget = new ParentWidget<>();
        this.child(inputWidget);
        this.child(outputWidget);
        inputWidget.child(RecipeViewerSlotWidget.create(ItemStack.class)
                .recipeSlotRole(RecipeSlotRole.INPUT).pos(3, 3)
                .value(ItemTagList.of(ORE.getMaterialTag(byProduct.material()), 1, null)));
        byProduct.getSlots().forEach(r -> {

            RecipeViewerSlotWidget<?, ?> widget;
            if (r.item()){
                widget = RecipeViewerSlotWidget.create(ItemStack.class).value(ItemStackList.of(r.stacks()));
            } else {
                widget = RecipeViewerSlotWidget.create(FluidStack.class).value(FluidStackList.of(r.fluidStacks()));
            }
            widget.recipeSlotRole(r.input() ? RecipeSlotRole.INPUT : RecipeSlotRole.OUTPUT);
            if (r.input()) widget.background(IDrawable.NONE);
            widget.pos(r.x() + 2, r.y() + 2);
            if (r.chance() > 0){
                widget.tooltipBuilder(t -> {
                    t.addLine(Utils.literal("Output Chance: " + ((float)r.chance() / 100) + "%").withStyle(ChatFormatting.WHITE));
                });
                widget.overlay(new ChanceOverlay(Utils.literal(((float)r.chance() / 100) + "%").withStyle(ChatFormatting.YELLOW)));
            }
            if (r.input()) inputWidget.child(widget);
            else outputWidget.child(widget);
        });
    }

    public static ResourceLocation id(OreByProduct oreByProduct){
        String slash = Mods.EMI.isLoaded() ? "/" : "";
        return new ResourceLocation(GT5Reimagined.ID, slash + "ore_byproduct/" + oreByProduct.material().getId() + (oreByProduct.bathingMode() == BathingMode.NONE ? "" : "_" + oreByProduct.bathingMode().name().toLowerCase(Locale.ROOT)));
    }
}
