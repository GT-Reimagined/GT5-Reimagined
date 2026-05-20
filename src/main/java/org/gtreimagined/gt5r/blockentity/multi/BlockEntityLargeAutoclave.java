package org.gtreimagined.gt5r.blockentity.multi;

import brachy.modularui.screen.viewport.ModularGuiContext;
import brachy.modularui.theme.WidgetThemeEntry;
import brachy.modularui.value.sync.IntSyncValue;
import brachy.modularui.value.sync.PanelSyncManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import org.gtreimagined.gtlib.blockentity.multi.BlockEntityMultiMachine;
import org.gtreimagined.gtlib.gui.GuiInstance;
import org.gtreimagined.gtlib.gui.ICanSyncData;
import org.gtreimagined.gtlib.gui.IGuiElement;
import org.gtreimagined.gtlib.gui.widget.WidgetSupplier;
import org.gtreimagined.gtlib.machine.MachineState;
import org.gtreimagined.gtlib.machine.types.Machine;
import net.minecraft.client.gui.Font;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.gtreimagined.gt5r.block.BlockCoil;
import org.gtreimagined.gt5r.machine.caps.ParallelRecipeHandler;
import org.gtreimagined.gtlib.mui.widgets.GTInfoRenderWidget;
import org.gtreimagined.gtlib.util.Utils;

public class BlockEntityLargeAutoclave extends BlockEntityParallelMultiblock<BlockEntityLargeAutoclave> {

    private BlockCoil.CoilData coilData;

    public BlockEntityLargeAutoclave(Machine<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.recipeHandler.set(() -> new ParallelRecipeHandler<>(this, 16){
            @Override
            protected void calculateDurations() {
                super.calculateDurations();
                float percentage = 1 + (.1f * coilData.autoclaveBoosts());
                maxProgress = (int) (maxProgress / percentage);
            }
        });
    }

    public BlockCoil.CoilData getCoilData() {
        return coilData;
    }

    public void setCoilData(BlockCoil.CoilData coilData) {
        this.coilData = coilData;
    }

//    @Override
//    public void onRecipeFound() {
////        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
////        this.mEfficiencyIncrease = 10000;
//
//        int tier = Utils.getVoltageTier(getMaxInputVoltage());
//        EUt = (-4 * (1 << tier - 1) * (1 << tier - 1) * level / discount);
//        maxProgress = Math.max(1, 512 / (1 << tier - 1));
//    }
}
