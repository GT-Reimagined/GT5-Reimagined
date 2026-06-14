package org.gtreimagined.gt5r.mui.widgets;

import brachy.modularui.api.widget.Interactable;
import net.minecraft.resources.ResourceLocation;
import org.gtreimagined.gtlib.integration.xei.GTLibXEIPlugin;
import org.gtreimagined.gtlib.machine.Tier;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.widgets.GTProgressWidget;
import org.jetbrains.annotations.NotNull;

public class AutocrafterProgressWidget extends GTProgressWidget {
    public AutocrafterProgressWidget(Machine<?> machine, Tier tier) {
        super(machine, tier);
    }

    @Override
    public @NotNull Result onMousePressed(int button) {
        GTLibXEIPlugin.showCategories(new ResourceLocation("crafting"));
        Interactable.playButtonClickSound();
        return Result.SUCCESS;
    }
}
