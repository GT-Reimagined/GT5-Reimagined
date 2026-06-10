package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.util.Utils;

public abstract class BlockEntityParallelMultiblock<T extends BlockEntityParallelMultiblock<T>> extends BlockEntityMultiMachine<T> {
    public BlockEntityParallelMultiblock(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void drawInfo(GTInfoRenderWidget widget, ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        super.drawInfo(widget, context, widgetTheme);
        if (getMachineState() == MachineState.ACTIVE){
            widget.drawText(context, widgetTheme, 0, 32, Utils.literal("Concurrent Recipes: " +
                    widget.getSyncedValue("concurrentRecipes", Integer.class)), 0xFAFAFF);
        }
    }

    @Override
    public void registerSyncHandlers(PanelSyncManager manager) {
        super.registerSyncHandlers(manager);
        manager.syncValue("concurrentRecipes", new IntSyncValue(() -> recipeHandler.map(r -> ((ParallelRecipeHandler<?>)r).concurrentRecipes).orElse(0)));
    }
}
