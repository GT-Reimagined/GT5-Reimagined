package org.gtreimagined.gt5r.integration.recipeviewer.widget;

import brachy.modularui.integration.recipeviewer.RecipeSlotRole;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemStackList;
import brachy.modularui.integration.recipeviewer.entry.item.ItemTagList;
import brachy.modularui.widget.ParentWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.gtreimagined.gt5r.integration.recipeviewer.OreByProduct;
import org.gtreimagined.gtlib.util.Utils;

import java.util.Collections;

import static org.gtreimagined.gtlib.data.GTMaterialTypes.ORE;

public class OreByProductWidget extends ParentWidget<OreByProductWidget> {
    public OreByProductWidget(OreByProduct byProduct){
        ParentWidget<?> inputWidget = new ParentWidget<>();
        ParentWidget<?> outputWidget = new ParentWidget<>();
        this.child(inputWidget);
        this.child(outputWidget);
        inputWidget.child(RecipeViewerSlotWidget.create(ItemStack.class)
                .recipeSlotRole(RecipeSlotRole.INPUT).pos(1, 1)
                .value(ItemTagList.of(ORE.getMaterialTag(byProduct.material()), 1, null)));
        byProduct.getSlots().forEach(r -> {

            RecipeViewerSlotWidget<?, ?> widget = null;
            if (r.item()){
                widget = RecipeViewerSlotWidget.create(ItemStack.class).value(ItemStackList.of(r.stacks()));
            } else {
                widget = RecipeViewerSlotWidget.create(FluidStack.class).value(FluidStackList.of(r.fluidStacks()));
            }
            widget.recipeSlotRole(r.input() ? RecipeSlotRole.INPUT : RecipeSlotRole.OUTPUT);
            widget.pos(r.x(), r.y());
            if (r.chance() > 0){
                widget.tooltipBuilder(t -> {
                    t.addLine(Utils.literal("Output Chance: " + ((float)r.chance() / 100) + "%").withStyle(ChatFormatting.WHITE));
                });
            }
        });
    }
}
