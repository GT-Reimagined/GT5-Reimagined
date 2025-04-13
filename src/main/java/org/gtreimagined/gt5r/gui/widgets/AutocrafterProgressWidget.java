package org.gtreimagined.gt5r.gui.widgets;

import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.widget.ProgressWidget;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
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
        GTLibXEIPlugin.showCategories(new ResourceLocation("crafting"));
    }
}
