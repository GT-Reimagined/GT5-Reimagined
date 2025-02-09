package org.gtreimagined.gt5r.gui.widgets;

import muramasa.antimatter.gui.GuiInstance;
import muramasa.antimatter.gui.IGuiElement;
import muramasa.antimatter.gui.widget.ProgressWidget;
import muramasa.antimatter.gui.widget.WidgetSupplier;
import muramasa.antimatter.integration.jeirei.AntimatterJEIREIPlugin;
import net.minecraft.resources.ResourceLocation;

public class AutocrafterProgressWidget extends ProgressWidget {
    public AutocrafterProgressWidget(GuiInstance instance, IGuiElement parent) {
        super(instance, parent);
    }

    public static WidgetSupplier build() {
        return builder(AutocrafterProgressWidget::new);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        super.onClick(mouseX, mouseY, button);
        AntimatterJEIREIPlugin.showCategories(new ResourceLocation("crafting"));
    }
}
